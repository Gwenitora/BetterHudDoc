# Shaders — Pourquoi et comment BetterHud en a besoin

> [← Sommaire](../SOMMAIRE.md)

---

## Pourquoi des shaders dans un plugin Minecraft ?

BetterHud affiche son HUD via le texte de la **BossBar** (voir [BossBar & Communication client](./bossbar.md)). Mais la BossBar vanilla de Minecraft comporte un problème fondamental : elle possède une **barre de progression visible** (fond coloré + barre de remplissage) qui s'affiche en haut de l'écran, par-dessus les éléments du HUD.

Sans intervention, le résultat ressemblerait à ceci :

```
╔══════════════════════════════════════╗
║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║  ← Barre de boss Minecraft (gênante)
║  [Image HUD] ♥ 20  ◆ 80             ║  ← HUD voulu
╚══════════════════════════════════════╝
```

**Les shaders servent à rendre la barre de progression invisible**, laissant uniquement le texte du HUD visible. C'est la raison principale de leur présence dans BetterHud.

Mais les shaders remplissent aussi plusieurs autres rôles essentiels, détaillés ci-dessous.

---

## Rôle 1 — Masquer la barre de boss native

Minecraft dessine la BossBar en deux passes :
1. Le **fond** (texture de fond de la barre)
2. La **barre de progression** (texture qui se remplit selon la valeur `progress`)

BetterHud génère des **textures de remplacement transparentes** pour ces deux éléments et les injecte dans le resource pack. Cela rend la barre de boss entièrement invisible, ne laissant que son titre (le composant Adventure du HUD) s'afficher.

```
Resource pack généré par BetterHud :

  assets/minecraft/textures/gui/sprites/boss_bar/
  ├── <couleur>_background.png   ← texture transparente (remplace le fond)
  └── <couleur>_progress.png     ← texture transparente (remplace la barre)
```

La couleur choisie est configurable dans `shader.yml` (paramètre `bar-color`). BetterHud ne remplace que cette couleur, laissant les autres couleurs de BossBar disponibles pour les plugins tiers.

---

## Rôle 2 — Différencier les éléments BetterHud du rendu vanilla

Minecraft utilise le même pipeline de rendu pour **tous** les textes affichés à l'écran (texte d'inventaire, signes, HUD vanilla, BossBar…). Ce pipeline est géré par un shader interne appelé `rendertype_text`.

BetterHud **remplace ce shader** par une version modifiée. Le problème à résoudre : le shader doit savoir quels éléments à l'écran appartiennent à BetterHud et lesquels sont des éléments Minecraft ordinaires.

### La valeur Z comme identifiant

Minecraft attribue une **coordonnée Z** (profondeur) différente à chaque type d'élément GUI. Ces valeurs varient selon la version de Minecraft et la plateforme (Java, Forge, NeoForge…) :

```
Valeurs Z vanilla connues :

  Z = 0      → Texte GUI standard (≤ 1.20.4)
  Z = 1000   → Texte GUI standard (≥ 1.20.5)
  Z = -90    → Texte Forge (≤ 1.20.4)
  Z = 2800   → Texte NeoForge

  Tout autre Z → Élément BetterHud
```

Le shader modifié contient une fonction `checkElement(z)` qui reconnaît ces valeurs vanilla. Si la valeur Z est une valeur vanilla connue, l'élément est rendu normalement. Sinon, il est traité comme un élément BetterHud et reçoit des transformations spéciales.

```
Logique du shader (no-code) :

  Pour chaque vertex rendu :
    Si Z est une valeur vanilla connue :
      → Rendu normal (pas d'intervention)
    Sinon (élément BetterHud) :
      → Appliquer les transformations du HUD
         (position, effets, opacité, contours…)
```

---

## Rôle 3 — Positionner les éléments à l'écran

Le shader reçoit pour chaque élément BetterHud ses données de position (`xGui`, `yGui`) injectées au moment de la génération du resource pack. Ces coordonnées sont exprimées en pourcentage de l'écran.

```
Flux de positionnement :

  Configuration YAML :
    gui: { x: 50, y: 10 }   ← position en % de l'écran

          │
          ▼

  Génération du resource pack :
    Les coordonnées sont compilées dans le shader
    sous forme de constantes GLSL

          │
          ▼

  Shader (côté client) :
    Chaque vertex de l'élément est déplacé
    vers la position x=50%, y=10% de l'écran
```

C'est ce mécanisme qui permet à BetterHud de placer des éléments **n'importe où sur l'écran**, au-delà de la simple zone de texte de la BossBar.

---

## Rôle 4 — Gérer les calques (Z-layers)

Quand plusieurs éléments du HUD se superposent, il faut définir lequel s'affiche devant l'autre. BetterHud utilise la coordonnée Z comme **numéro de calque** dans le shader.

```
Calques (layer) :

  Layer 0 (fond)      → affiché en premier, derrière tout
  Layer 1             → s'affiche par-dessus le layer 0
  Layer 2             → s'affiche par-dessus le layer 1
  ...

  Exemple :
    background_image  → layer: 0
    health_bar        → layer: 1   (s'affiche devant le fond)
    health_text       → layer: 2   (s'affiche devant la barre)
```

---

## Rôle 5 — Effets visuels par élément

Le shader utilise un entier `property` comme **champ de bits** pour activer des effets visuels optionnels sur chaque élément. Chaque bit correspond à un effet :

```
Bits de property :

  Bit 0 (valeur 1) → WAVE         : ondulation verticale animée
  Bit 1 (valeur 2) → RAINBOW      : cycle de couleurs arc-en-ciel sur l'élément entier
  Bit 2 (valeur 4) → TINY_RAINBOW : arc-en-ciel appliqué caractère par caractère
                                    (chaque caractère a une couleur différente)
```

Ces effets sont calculés entièrement dans le shader (côté client), sans charge supplémentaire côté serveur.

```
Schéma de l'effet WAVE :

  Sans effet :    [IMAGE STATIQUE]
  Avec WAVE  :    [IMAGE QUI ONDULE]
                     ↑↑↑↑↑
                  calculé via sin(temps + x)
                  dans le vertex shader
```

---

## Rôle 6 — Contours (outline)

L'effet de **contour** (bordure colorée autour des éléments) est géré via le shader en combinaison avec l'API de couleur d'ombre d'Adventure (depuis Minecraft 1.21.4).

### Avant 1.21.4 — Approche par duplication

Le contour était simulé en affichant l'image plusieurs fois avec un léger décalage (haut, bas, gauche, droite) avec la couleur de contour, puis l'image originale par-dessus.

```
Rendu d'un contour (approche décalage) :

  Passe 1 : image décalée (+1, 0)  couleur contour
  Passe 2 : image décalée (-1, 0)  couleur contour
  Passe 3 : image décalée (0, +1)  couleur contour
  Passe 4 : image décalée (0, -1)  couleur contour
  Passe 5 : image originale        couleur normale
```

### Depuis 1.21.4 — Shadow Color d'Adventure

Adventure API expose une propriété `ShadowColor` sur les composants de texte. BetterHud l'utilise pour appliquer la couleur de contour directement via le composant, et le shader détecte cet attribut pour rendre le contour correctement sans duplications.

---

## Rôle 7 — Compatibilité multi-versions

Les shaders générés par BetterHud sont adaptés à la version de Minecraft du serveur grâce au système d'**overlays** du resource pack. BetterHud génère jusqu'à 4 variantes du shader (versions 0 à 3) correspondant à des plages de versions Minecraft :

```
Versions du shader :

  Version 0 : Minecraft ≤ 1.20.4
  Version 1 : Minecraft 1.20.5 - 1.21.3
  Version 2 : Minecraft 1.21.4 (shadow color natif)
  Version 3 : Minecraft ≥ 1.21.5 (dynamic transforms)
```

Chaque version est placée dans le dossier `overlay` correspondant du resource pack. Le client Minecraft charge automatiquement la variante compatible avec sa version.

---

## Schéma récapitulatif

```
                    SERVEUR
  ┌──────────────────────────────────────────┐
  │  Configuration YAML                      │
  │  (positions gui, effets, couleurs…)      │
  │              │                           │
  │              ▼                           │
  │  ShaderManager                           │
  │  Génère les shaders GLSL                 │
  │  en injectant les données de layout      │
  │              │                           │
  │              ▼                           │
  │  PackGenerator                           │
  │  Inclut les shaders dans le .zip         │
  │  + textures BossBar transparentes        │
  └───────────────┬──────────────────────────┘
                  │ Resource Pack (.zip)
                  ▼
                CLIENT
  ┌──────────────────────────────────────────┐
  │  Charge le resource pack                 │
  │              │                           │
  │              ▼                           │
  │  rendertype_text.vsh / .fsh              │
  │  (shader GLSL modifié par BetterHud)     │
  │                                          │
  │  Pour chaque vertex :                    │
  │    checkElement(Z) ?                     │
  │    ├── Oui → rendu vanilla normal        │
  │    └── Non → appliquer transformations   │
  │              (position, effets, calques) │
  │                                          │
  │  Barre de boss :                         │
  │    texture transparente → invisible      │
  │    titre → HUD visible                   │
  └──────────────────────────────────────────┘
```

---

## Résumé des utilités

| Problème vanilla | Solution shader BetterHud |
|---|---|
| La BossBar affiche une barre visible | Textures transparentes dans le resource pack |
| Impossible de distinguer éléments HUD / vanilla | Valeur Z unique + fonction `checkElement()` |
| Positionnement limité à la zone de texte | Coordonnées GUI injectées dans le shader |
| Pas de gestion de calques pour les overlaps | Coordonnée Z = numéro de calque |
| Pas d'effets visuels sans mod client | Effets WAVE, RAINBOW via vertex shader |
| Contours/ombres inexistants | Duplication décalée ou `ShadowColor` Adventure |
| Incompatibilité entre versions MC | Overlays de shader par plage de versions |

---

> **Suite →** [Resource Pack](./resource-pack.md) | [← BossBar](./bossbar.md)
