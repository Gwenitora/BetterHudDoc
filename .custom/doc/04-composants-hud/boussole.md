# Boussole — Marqueurs directionnels

> [← Sommaire](../SOMMAIRE.md)

---

## Qu'est-ce que la Boussole ?

Le composant **Boussole** de BetterHud permet d'afficher une barre de navigation directionnelle dans le HUD. Elle indique visuellement la direction des points d'intérêt (marqueurs) placés dans le monde par rapport à la direction du regard du joueur.

---

## Concept général

```
Le joueur regarde vers le Nord (yaw = 0°)

Boussole affichée (vue horizontale de 180°) :

  ←──────────────────────────────────────→
  │  SO   S   SE   [E]  NE   N   NO  [O] │
  │             ▲                         │
  │         (marqueur)              (marqueur)
  └──────────────────────────────────────┘
              ↑
         Centre = direction actuelle du regard
```

La boussole défile horizontalement en fonction de la **rotation de la tête** du joueur (`yaw`).

---

## Structure d'une Boussole

```
Compass
├── Identifiant unique (ex: "main_compass")
├── Layout de fond (image de la barre de boussole)
├── Layout des marqueurs directionnels
│   ├── icône par défaut pour les marqueurs
│   └── icônes spécifiques par nom de marqueur
└── Champ de vision : degrés couverts par la barre
```

---

## Les points (marqueurs)

Les **points** (ou markers) sont des positions dans le monde que la boussole doit signaler. Ils sont ajoutés/supprimés dynamiquement par le serveur.

```
Structure d'un point :
├── location  : coordonnées dans le monde (x, y, z, world)
├── name      : identifiant du marqueur
└── icon      : (optionnel) icône custom pour ce marqueur
```

### Ajout/suppression de marqueurs

```
Via l'API BetterHud :
  Ajouter  : player.addCompassPoint(location, "base")
  Supprimer: player.removeCompassPoint("base")

Via Skript :
  Ajouter  : point add location at 0,0,0 in "world" named "base" to player
  Supprimer: point remove "base" to player
  Avec icône: point add ... named "enemy" with icon "skull" to player
```

---

## Calcul de la position d'un marqueur sur la boussole

La position d'un marqueur sur la barre de boussole est calculée à partir de :

1. **La direction du joueur** (yaw, angle horizontal du regard)
2. **La direction vers le marqueur** (angle entre le joueur et le marqueur)
3. **La différence angulaire** entre les deux

```
Schéma de calcul :

  Joueur à (0, 0) regarde vers le Nord (yaw = 0°)
  Marqueur à (100, 0) → direction Est (90°)

  Différence angulaire : 90° - 0° = 90° vers la droite

  Position sur la boussole :
    Centre = 50% de la largeur de la barre
    Décalage = (90° / champ_de_vision) × largeur_barre
    → Le marqueur apparaît à droite du centre
```

---

## Type de boussole : `CircleCompass`

BetterHud implémente la boussole sous forme de **cercle** : les 360° de direction sont représentés sur la barre, et la fenêtre visible défile selon le regard du joueur.

```
Vue "dépliée" de la boussole circulaire :

  N    NE    E    SE    S    SW    W    NW    N
  │────│────│────│────│────│────│────│────│────│
  0°   45°  90° 135° 180° 225° 270° 315° 360°

  Joueur regarde NE (yaw = 45°) :
  → La fenêtre visible centre sur NE
  
  NW    N    NE    E    SE
  │────[│────│────│]───│────│
               ↑
           Fenêtre visible
```

---

## Intégration GPS

BetterHud supporte l'intégration avec des plugins GPS externes pour obtenir des marqueurs automatiques. Les marqueurs GPS sont traités comme des points ordinaires une fois récupérés.

---

## La boussole comme layout

La boussole génère son propre contenu visuel sous forme de **layouts**, comme les autres composants. Elle peut donc utiliser les mêmes mécaniques de positionnement et de polices custom.

Les icônes de marqueurs sont des images intégrées au resource pack, assignées à des codepoints comme n'importe quelle image du HUD.

---

> **Suite →** [Format de configuration YAML](../05-configuration/format-yaml.md)
