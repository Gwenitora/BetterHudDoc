# Textes & Placeholders

> [← Sommaire](../SOMMAIRE.md)

---

## Vue d'ensemble

Les éléments **texte** permettent d'afficher du texte dynamique dans le HUD : vie du joueur, nom, score, etc. Ils supportent des **placeholders** (variables remplacées à l'exécution) et divers styles de mise en forme.

---

## Comment le texte est-il rendu ?

### Contrainte technique

Dans Minecraft, les polices de base ont des **largeurs de caractères fixes** par la police système (`minecraft:default`). Pour positionner le texte précisément dans le HUD, BetterHud a besoin de connaître la largeur exacte de chaque caractère affiché.

BetterHud maintient une **table de largeurs** pour les polices connues (police par défaut, polices Unicode, etc.) afin de calculer l'espace total occupé par une chaîne de texte.

### Rendu du texte

```
Texte configuré : "♥ {player_health}"
                         │
                         ▼
        Résolution des placeholders (au tick)
        "♥ {player_health}" → "♥ 18"
                         │
                         ▼
        Calcul de la largeur totale du texte
        (char par char, selon la police)
                         │
                         ▼
        Ajout des espaces de positionnement
        [+X px] ["♥ 18", police default] [-totalWidth px]
                         │
                         ▼
        Insertion dans le composant BossBar
```

---

## Polices pour le texte

Le texte peut utiliser différentes polices :

| Police | Description |
|---|---|
| `minecraft:default` | Police par défaut de Minecraft |
| `minecraft:uniform` | Police Unicode uniforme |
| `minecraft:alt` | Police alternative (style galactique) |
| Polices custom | Définies dans le resource pack de BetterHud |

---

## Les placeholders

Les placeholders sont des **variables dynamiques** insérées dans le texte. Ils sont remplacés par leur valeur réelle à chaque tick de rendu.

### Syntaxe

```
Syntaxe générale : {nom_du_placeholder}

Exemples :
  {player_health}        → vie actuelle du joueur (ex: "18")
  {player_max_health}    → vie maximale (ex: "20")
  {player_name}          → nom du joueur
  {player_level}         → niveau d'expérience
  {world_time}           → heure dans le monde
  {player_x}             → coordonnée X
  {server_online}        → nombre de joueurs en ligne
```

### Sources de placeholders

BetterHud supporte plusieurs sources :

```
Sources intégrées :
├── Données du joueur (vie, faim, niveau, position…)
├── Données du monde (heure, météo…)
└── Données du serveur (joueurs en ligne…)

Sources externes (via intégration) :
├── PlaceholderAPI (PAPI) — des centaines de plugins compatibles
└── Variables custom définies par d'autres plugins via l'API
```

---

## Évaluation d'expressions

BetterHud intègre un **évaluateur d'expressions mathématiques** (exp4j). Cela permet de calculer des valeurs à partir de placeholders :

```
Exemples d'expressions :
  "{player_health} / {player_max_health} * 100"
    → Pourcentage de vie (ex: "90.0")

  "floor({player_health})"
    → Vie arrondie à l'entier inférieur

  "{mana} > 50 ? 1 : 0"
    → Condition binaire (1 ou 0)
```

Ces expressions sont évaluées côté serveur avant d'être envoyées au client.

---

## Formatage du texte

### Couleurs

BetterHud supporte les couleurs Minecraft standard (§a, §b…) et les **couleurs hex** via le format Adventure (`<#FF5500>`).

```
Formatage disponible :
├── Codes §  : §a (vert), §c (rouge), §l (gras)…
├── Hex      : <#RRGGBB>
├── Gradient : <gradient:#FF0000:#0000FF>
└── Styles   : <bold>, <italic>, <underline>, <strikethrough>
```

### Ombres

Le texte peut avoir une **ombre** pour améliorer la lisibilité sur fond varié. L'ombre est un décalage d'un pixel en bas à droite avec une couleur plus sombre.

---

## Alignement du texte

Comme pour les images, le texte supporte les alignements `LEFT`, `CENTER`, `RIGHT` au sein d'un layout.

```
Alignement CENTER avec un layout de largeur 200px :

              ←────── 200px ──────→
              │   "♥ 18"           │
              │    └──── centré ───┘│
              │    │               │
              ↑                    ↑
         bord gauche          bord droit
```

---

## Retour à la ligne (multiline)

BetterHud supporte le texte multi-lignes. Si le texte est trop long, il peut être coupé sur plusieurs lignes selon une largeur maximale configurée.

```
Configuration multiline :
  max-width: 150   ← largeur maximale en pixels
  line-spacing: 2  ← espacement entre les lignes

Résultat (texte "Bienvenue sur le serveur XYZ") :
  Ligne 1 : "Bienvenue sur le"
  Ligne 2 : "serveur XYZ"
```

Chaque ligne supplémentaire utilise un `ascent` différent dans la police pour être affichée plus bas.

---

## Textes conditionnels

Un texte peut avoir plusieurs **variantes** selon des conditions, similairement aux images. Par exemple : afficher "♥ FULL" quand la vie est au maximum, et "♥ {health}" sinon.

---

## Mise à jour asynchrone

Pour éviter de bloquer le thread principal du serveur, la résolution des placeholders peut être effectuée **de manière asynchrone**. Les valeurs sont calculées en arrière-plan et mises en cache jusqu'à la prochaine mise à jour.

```
Thread principal :
  → Déclenche la mise à jour du placeholder

Thread asynchrone :
  → Résout la valeur (appel à PlaceholderAPI, calcul…)
  → Stocke le résultat en cache

Thread principal (tick suivant) :
  → Lit la valeur depuis le cache
  → Construit le composant Adventure
```

---

> **Suite →** [Têtes de joueurs](./tetes-joueurs.md)
