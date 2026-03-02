# Font & Positionnement — La mécanique de déplacement pixel par pixel

> [← Sommaire](../SOMMAIRE.md) | [← Vue d'ensemble](../01-introduction/vue-ensemble.md)

---

## Principe fondamental

La mécanique la plus importante de BetterHud est le **positionnement par police custom**.

Dans Minecraft, chaque caractère d'un composant de texte a un **avancement horizontal** (la largeur qu'il occupe). En définissant des polices avec des avancements négatifs ou positifs arbitraires, on peut déplacer le curseur de texte à n'importe quelle position horizontale de l'écran.

C'est cette astuce qui permet à BetterHud d'afficher des éléments **à la position exacte en pixels** souhaitée.

---

## Les polices dans Minecraft

Un resource pack peut définir des polices custom via des fichiers JSON dans :

```
assets/
└── <namespace>/
    └── font/
        └── <nom_de_la_police>.json
```

Chaque fichier de police contient une liste de **providers** (fournisseurs). Il en existe plusieurs types, les deux utilisés par BetterHud sont :

### Provider `bitmap`

Associe une image (texture PNG) à un ou plusieurs codepoints Unicode. Chaque caractère sera affiché avec cette image.

```
Provider bitmap
├── file  : chemin vers la texture PNG
├── chars : tableau de lignes de caractères Unicode mappés
├── height: hauteur de rendu en pixels
└── ascent: ligne de base (décalage vertical)
```

### Provider `space`

Définit des caractères dont la seule propriété est leur **largeur** (avancement horizontal). Ils ne sont pas visibles, mais font avancer (ou reculer) le curseur.

```
Provider space
└── advances: { "<codepoint>": <valeur_en_pixels> }
    Exemples :
      "\uD001": 10    → avance de 10 pixels vers la droite
      "\uCFFE": -50   → recule de 50 pixels vers la gauche
```

---

## Le système de codepoints d'espacement

BetterHud réserve une plage de codepoints Unicode pour ses caractères d'espacement :

```
Plage réservée : U+C0000 → U+D0000
Point de référence (zéro) : U+D0000

Formule :
  codepoint = U+D0000 + valeur_de_décalage

Exemples :
  décalage  0   → U+D0000  (aucun déplacement)
  décalage +100 → U+D0064  (avance de 100px vers la droite)
  décalage -50  → U+CFFCE  (recule de 50px vers la gauche)
```

Le resource pack généré contient un fichier de police `space` qui mappe chacun de ces codepoints à sa valeur d'avancement correspondante.

---

## Le mécanisme de positionnement en pratique

### Schéma général

```
Serveur calcule la position cible en pixels
               │
               ▼
  Convertit l'offset en codepoint Unicode
  (ex: décalage +150 → U+D0096)
               │
               ▼
  Crée un composant Adventure avec :
    - le caractère U+D0096
    - la police "betterhud:space"
               │
               ▼
  Envoie ce composant dans le texte de la BossBar
               │
               ▼
  Client lit le resource pack :
    U+D0096 avec la police "betterhud:space"
    → avance de 150 pixels
               │
               ▼
  Le prochain élément est affiché 150 pixels plus loin
```

### Schéma de composition d'une ligne de rendu

```
[Espace vers position X]  [Image/Texte à afficher]  [Retour au curseur de départ]

← curseur départ (gauche BossBar)
       │
       ├──[+150px espace]──▶ Affiche l'élément ici
       │                     │
       └─────────────────────┘
           [-150px espace] ◀── Retour au départ pour le prochain élément
```

---

## Le `WidthComponent` et le `PixelComponent`

BetterHud construit ses composants en chaînant deux types d'objets :

### WidthComponent

Un composant Adventure enrichi d'une métadonnée de **largeur réelle**. Cette largeur est nécessaire pour calculer les espaces de retour après l'affichage.

```
WidthComponent
├── composant Adventure (le contenu visuel)
└── width (largeur en pixels de ce contenu)
```

### PixelComponent

Un WidthComponent enrichi d'une **position pixel** (coordonnée X dans le HUD).

```
PixelComponent
├── WidthComponent (contenu + largeur)
└── position X en pixels
```

---

## Positionnement vertical

Le positionnement vertical est géré différemment : par l'`ascent` du provider `bitmap`.

- L'`ascent` définit à quelle hauteur au-dessus de la ligne de base l'image est affichée.
- En ajustant l'`ascent`, on peut placer des éléments à différentes hauteurs dans la BossBar.
- Les éléments à la même `height` mais avec des `ascent` différents se retrouvent sur des "lignes" différentes.

```
ascent élevé  → élément affiché plus haut
ascent faible → élément affiché plus bas
ascent négatif → possible pour des décalages importants vers le bas
```

---

## Le retour à la position initiale

Après avoir affiché un élément, il faut **réinitialiser le curseur** pour ne pas décaler les éléments suivants. BetterHud le fait en insérant un caractère d'espacement de valeur **négative** égale à la largeur du dernier élément affiché.

```
Séquence complète pour un élément de 32px à la position 100px :

[+100px]  [IMAGE 32px]  [-132px]
  ↑              ↑          ↑
Position    Contenu    Annulation (retour au début)
```

---

## Alignements

BetterHud supporte plusieurs modes d'alignement pour les éléments dans un layout :

| Mode | Comportement |
|---|---|
| `LEFT` | Le curseur commence à gauche de la position définie |
| `CENTER` | L'élément est centré autour de la position définie |
| `RIGHT` | L'élément se termine à la position définie |

L'alignement est calculé en ajustant les offsets d'espacement avant et après l'élément.

---

> **Suite →** [BossBar & Communication client](./bossbar.md)
