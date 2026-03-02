# Images & Animations

> [← Sommaire](../SOMMAIRE.md)

---

## Vue d'ensemble

Les **images** sont les éléments visuels principaux d'un HUD BetterHud. Elles correspondent à des textures PNG affichées à une position précise de l'écran.

BetterHud supporte également les **animations** : une image peut être composée de plusieurs frames qui se succèdent dans le temps.

---

## Définition d'une image

Une image est définie dans un fichier de configuration séparé (dans le dossier `images/`). Elle référence un fichier PNG et définit ses propriétés de rendu.

### Propriétés d'une image

```
Image
├── file         : chemin vers le fichier PNG source
├── scale        : facteur d'échelle (1.0 = taille originale)
├── pixel        : décalage en pixels (x, y) depuis la position du layout
└── (optionnel) outline : couleur de contour (ARGB hex)
```

### Exemple de structure de dossier

```
plugins/BetterHud/
└── images/
    ├── health_bar.png         ← image statique
    ├── mana_bar/              ← dossier = animation PNG séquence
    │   ├── 0.png              ← frame 0
    │   ├── 1.png              ← frame 1
    │   ├── 2.png              ← frame 2
    │   └── ...
    └── compass_bg.png
```

---

## Images statiques vs animations

### Image statique

Une image statique est un simple fichier PNG. Un seul codepoint lui est assigné dans la police du resource pack.

```
Traitement d'une image statique :

  health_bar.png
       │
       ▼
  Assignation d'un codepoint unique (ex: U+E001)
       │
       ▼
  Fichier de police généré :
    health_bar.json → U+E001 = health_bar.png
       │
       ▼
  Rendu : toujours le même composant [U+E001]
```

### Animation (PNG séquence)

Une animation est un **dossier** contenant plusieurs fichiers PNG numérotés (frames). Chaque frame reçoit son propre codepoint.

```
Traitement d'une animation (4 frames) :

  mana_bar/0.png → U+E010
  mana_bar/1.png → U+E011
  mana_bar/2.png → U+E012
  mana_bar/3.png → U+E013
       │
       ▼
  À chaque tick de rendu :
    frame_index = tick % nb_frames
    composant = [U+E010 + frame_index]
```

---

## Types d'animation

BetterHud propose deux types d'animation :

### `LOOP` (boucle)

L'animation tourne en boucle indéfiniment.

```
Frames : [0] [1] [2] [3] [0] [1] [2] [3] [0] ...
Ticks  :  0   1   2   3   4   5   6   7   8  ...
```

### `PLAY_ONCE` (joue une fois)

L'animation se joue une seule fois, puis se fige sur la dernière frame.

```
Frames : [0] [1] [2] [3] [3] [3] [3] [3] ...
Ticks  :  0   1   2   3   4   5   6   7  ...
                            ↑
                      Bloqué sur la dernière frame
```

---

## Feuilles de sprites (spritesheet)

Au lieu d'utiliser des fichiers PNG séparés pour chaque frame, il est possible d'utiliser une **feuille de sprites** : une image unique contenant toutes les frames côte à côte ou en grille.

```
Spritesheet 4 frames (2×2) :
┌──────┬──────┐
│  F0  │  F1  │
├──────┼──────┤
│  F2  │  F3  │
└──────┴──────┘

Le provider bitmap mappe :
  ligne 1 → chars[0] = ["<U+E010><U+E011>"]
  ligne 2 → chars[1] = ["<U+E012><U+E013>"]
```

---

## Schéma de rendu d'une image dans le HUD

```
Layout défini en config :
  position gui: x=50%, y=10%
  image: health_bar, scale: 2.0

               ▼

Moteur de rendu (tick N) :
  1. Convertit gui (50%, 10%) en pixels écran
  2. Calcule l'offset horizontal (position X)
  3. Sélectionne la frame (si animation : tick % nb_frames)
  4. Construit le PixelComponent :
       [espace +X px]  [char U+E00N, police "betterhud:health_bar"]  [espace -totalWidth px]
  5. Le composant est inséré dans la chaîne de la BossBar

               ▼

Client reçoit la BossBar :
  - Interprète [espace +X px] → déplace le curseur de X pixels
  - Interprète [char U+E00N] → affiche la texture health_bar à taille 2×
  - Interprète [espace -totalWidth px] → remet le curseur en place
```

---

## Conditions d'affichage

Une image peut avoir une **condition** qui détermine si elle doit être affichée ou non. Cela permet de créer des affichages conditionnels sans recharger la configuration.

```
Conditions possibles :
├── Toujours afficher (par défaut)
├── Afficher si variable > seuil
├── Afficher si joueur a un certain effet
├── Afficher si joueur est en combat
└── Expression arbitraire (via eq4j : ex. "player_health > 10")
```

---

## Opacité et effets

BetterHud supporte l'**opacité** sur les images (de 0.0 transparent à 1.0 opaque). Techniquement, cela est géré en modifiant la couleur du composant Adventure associé à l'image (propriété `alpha` dans la couleur ARGB).

Les **contours** (outlines) sont générés en ajoutant des copies décalées de l'image en arrière-plan avec la couleur de contour désirée.

---

> **Suite →** [Textes & Placeholders](./textes-et-placeholders.md)
