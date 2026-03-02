# Resource Pack — Format, Structure et Génération

> [← Sommaire](../SOMMAIRE.md)

---

## Rôle du resource pack

Le resource pack est **indispensable** au fonctionnement de BetterHud. Sans lui, les caractères Unicode spéciaux envoyés dans les BossBars seraient soit invisibles, soit affichés comme des carrés de remplacement.

Le resource pack remplit deux rôles :

1. **Définir les polices d'espacement** : faire correspondre des codepoints Unicode à des valeurs de décalage en pixels.
2. **Définir les polices d'images** : faire correspondre des codepoints Unicode à des textures PNG (les images du HUD).

---

## Génération automatique

BetterHud **génère entièrement** le resource pack au démarrage du serveur, à partir des fichiers de configuration. L'administrateur n'a jamais à créer ces fichiers manuellement.

```
Démarrage
    │
    ▼
Parcours de tous les éléments configurés
(images, textes, têtes...)
    │
    ▼
Affectation de codepoints Unicode uniques
à chaque texture / frame d'animation
    │
    ▼
Écriture des fichiers JSON de polices
    │
    ▼
Copie des textures PNG dans le pack
    │
    ▼
Création du fichier pack.mcmeta
    │
    ▼
Compression en .zip (ou dossier, selon config)
    │
    ▼
Mise à disposition :
  - via un serveur HTTP intégré
  - ou via un lien URL externe (CDN, etc.)
```

---

## Structure du resource pack généré

```
resource_pack.zip
└── assets/
    ├── minecraft/
    │   └── font/
    │       └── default.json        ← Police Minecraft enrichie (optionnel)
    └── betterhud/
        ├── font/
        │   ├── space.json          ← Police des espaces de positionnement
        │   ├── <image_id>.json     ← Police pour chaque image/animation
        │   └── <head_id>.json      ← Police pour les têtes de joueurs
        ├── textures/
        │   ├── <image_id>.png      ← Textures des éléments du HUD
        │   └── split.png           ← Texture utilitaire (séparateur)
        └── shaders/                ← (optionnel) effets visuels custom
            └── ...

pack.mcmeta                         ← Métadonnées du pack
```

---

## Le fichier `pack.mcmeta`

Ce fichier est obligatoire pour tout resource pack Minecraft. Il indique la version du format et la description.

```
pack.mcmeta
├── pack_format   : numéro de version du format (ex: 48 pour 1.21)
├── supported_formats : plage de versions supportées [min, max]
└── description   : texte affiché dans le menu resource packs

Exemples de pack_format selon la version Minecraft :
  1.20.4 → 22
  1.21   → 34
  1.21.4 → 46
  1.21.5 → 48
```

---

## Le fichier de police `space.json`

C'est la police qui gère tout le positionnement. Elle contient un provider de type `space` :

```
space.json (structure)
└── providers
    └── type: "space"
        └── advances
            ├── "\uCFFCE": -50    (décalage -50px)
            ├── "\uD0000": 0      (décalage 0px)
            ├── "\uD0001": 1      (décalage +1px)
            ├── "\uD0064": 100    (décalage +100px)
            └── ... (une entrée par valeur de décalage utilisée)
```

La plage de valeurs générée couvre typiquement de `-8192` à `+8192` pixels, ce qui couvre largement toutes les résolutions d'écran.

---

## Les fichiers de police `<image_id>.json`

Chaque image (ou animation) du HUD possède son propre fichier de police. Il contient un provider de type `bitmap` :

```
<image_id>.json (structure)
└── providers
    └── type: "bitmap"
        ├── file   : "betterhud:textures/<image_id>.png"
        ├── height : hauteur en pixels de rendu
        ├── ascent : décalage vertical (ligne de base)
        └── chars  : [["<codepoint>"]]
                     Un codepoint unique est assigné à cette image
```

Pour une animation (plusieurs frames), chaque frame a soit :
- Son propre codepoint dans le même fichier de police (pour une feuille de sprites)
- Son propre fichier de police

---

## Les textures PNG

Les images configurées dans BetterHud sont copiées telles quelles dans le dossier `textures/` du resource pack. Elles doivent être au format PNG avec transparence (RGBA).

### Contraintes importantes

```
Textures PNG pour les polices bitmap :
├── Largeur   : multiple de 16 (recommandé)
├── Hauteur   : multiple de 16 (recommandé)
├── Couleur   : RGBA (transparence supportée)
└── Résolution: pas de limite, mais la "height" dans le JSON
                redimensionne l'image à l'affichage
```

### Feuille de sprites (spritesheet)

Pour les animations, il est courant d'utiliser une **feuille de sprites** : une image PNG unique contenant toutes les frames disposées en grille. Le provider `bitmap` permet de mapper plusieurs lignes de caractères sur les lignes de la feuille.

```
Feuille de sprites (4 frames, 2 colonnes × 2 lignes) :

┌─────────┬─────────┐
│ Frame 1 │ Frame 2 │   ← ligne 1 → chars[0] = ["<cp1><cp2>"]
├─────────┼─────────┤
│ Frame 3 │ Frame 4 │   ← ligne 2 → chars[1] = ["<cp3><cp4>"]
└─────────┴─────────┘
```

---

## Overlays (resource pack layers)

Minecraft supporte depuis la 1.20.2 les **overlays** dans les resource packs : des couches conditionnelles selon la version du client. BetterHud en tire parti pour supporter plusieurs versions de Minecraft avec un même pack.

```
pack.mcmeta avec overlays :
└── overlays
    └── entries
        ├── { formats: [32, 48], directory: "overlay_1_20_5" }
        └── { formats: [22, 31], directory: "overlay_1_20_4" }
```

---

## Distribution du resource pack aux joueurs

Minecraft ne peut pas envoyer un resource pack directement en binaire via le protocole de jeu : il doit être téléchargeable via une **URL HTTP**.

BetterHud offre deux options :

```
Option 1 : Serveur HTTP intégré
  Le plugin démarre un petit serveur HTTP local
  Les clients téléchargent le pack depuis :
  http://<ip_serveur>:<port>/pack.zip

Option 2 : URL externe
  L'admin héberge le pack sur un CDN ou serveur web
  et configure l'URL dans config.yml
  (utile pour les serveurs derrière un proxy ou pare-feu)
```

Le hash SHA-1 du pack est calculé et envoyé avec l'URL pour que le client sache si le pack a changé et s'il doit le re-télécharger.

---

> **Suite →** [Images & Animations](../03-elements/images-et-animations.md)
