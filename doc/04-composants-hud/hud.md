# HUD — Le composant principal

> [← Sommaire](../SOMMAIRE.md)

---

## Qu'est-ce qu'un HUD ?

Un **HUD** (Heads-Up Display) dans BetterHud est un ensemble d'éléments visuels persistants affichés en continu à l'écran d'un joueur. C'est le composant de haut niveau qui orchestre l'affichage de plusieurs layouts.

Exemples de HUDs : barre de vie stylisée, barre de mana, minimap, boussole, affichage de coordonnées…

---

## Structure d'un HUD

```
HUD
├── Identifiant unique (ex: "health_hud")
├── Conditions d'activation
│   ├── default: true/false   ← actif pour tous les joueurs par défaut ?
│   └── conditions            ← expressions pour activer/désactiver
├── tick                      ← fréquence de mise à jour (en ticks)
└── layouts[]                 ← liste des layouts (éléments visuels)
```

---

## Les layouts

Un **layout** est un groupe d'éléments (images, textes, têtes) organisés autour d'une position définie à l'écran.

```
Layout
├── name          : identifiant du layout
├── gui           : position en pourcentage de l'écran { x: 0-100, y: 0-100 }
├── pixel         : décalage supplémentaire en pixels { x, y }
├── align         : alignement (LEFT, CENTER, RIGHT)
├── opacity       : opacité globale (0.0 - 1.0)
├── animation     : métadonnées d'animation (type, vitesse)
│
├── images[]      : éléments image
├── texts[]       : éléments texte
└── heads[]       : éléments tête de joueur
```

---

## Schéma d'un écran avec plusieurs layouts

```
┌──────────────────────────────────────────────────────────┐
│                   ÉCRAN DU JOUEUR                        │
│                                                          │
│  ┌────────────────────────────────────────────────────┐  │
│  │               BossBar 1                            │  │
│  │  [bg_image][♥♥♥♥♥♥♥♥♥♥ 20/20][mana bar][◆◆◆◆ 80] │  │
│  └────────────────────────────────────────────────────┘  │
│                                                          │
│                                                          │
│                  ... vue 3D du jeu ...                   │
│                                                          │
│                                                          │
│  ┌────────────────────────────────────────────────────┐  │
│  │               BossBar 2 (si besoin)                │  │
│  │  [minimap_bg][coordonnées : 128, 64, -256]         │  │
│  └────────────────────────────────────────────────────┘  │
│                                                          │
│  ┌──────────────────────────────┐                        │
│  │  ActionBar (optionnel)       │                        │
│  │  ⚔ En combat — 14s restants  │                        │
│  └──────────────────────────────┘                        │
└──────────────────────────────────────────────────────────┘
```

---

## Gestion des HUDs par joueur

Chaque joueur a une **liste de HUDs actifs**. Un HUD peut être :

- **Actif par défaut** (`default: true`) : automatiquement activé pour tous les joueurs
- **Activé/désactivé manuellement** : via commande, API ou condition
- **Conditionnel** : activé/désactivé en fonction d'une expression (ex: uniquement en monde "world")

```
État des HUDs pour un joueur :

  Joueur "Steve" :
    ├── health_hud     [ACTIF]    ← default: true
    ├── mana_hud       [ACTIF]    ← activé via API (plugin RPG)
    ├── debug_hud      [INACTIF]  ← default: false, pas activé
    └── combat_hud     [INACTIF]  ← condition "in_combat" = false
```

---

## Le tick de mise à jour

Chaque HUD possède un paramètre `tick` qui définit la **fréquence de mise à jour** :

```
tick: 1   → mise à jour chaque tick (20 fois/seconde, temps réel)
tick: 2   → mise à jour toutes les 2 ticks (10 fois/seconde)
tick: 20  → mise à jour toutes les secondes

⚠ Un tick bas = plus fluide, mais plus de charge serveur
⚠ Un tick élevé = moins fluide, mais plus économique
```

La mise à jour est optimisée : si le composant rendu est identique au précédent, aucun paquet réseau n'est envoyé.

---

## Animations au niveau du layout

En plus des animations d'images (frame par frame), BetterHud supporte des **animations au niveau du layout entier** :

```
Types d'animation de layout :
├── FADE_IN   : le layout apparaît en fondu
├── FADE_OUT  : le layout disparaît en fondu
├── SLIDE_IN  : le layout glisse depuis un bord
└── NONE      : pas d'animation (instantané)
```

Ces animations sont gérées en modifiant l'opacité ou la position du layout sur plusieurs ticks.

---

## Conditions d'affichage d'un HUD

Un HUD entier peut avoir des conditions pour décider s'il doit être affiché :

```
Conditions d'un HUD (exemples no-code) :

  Condition 1 : Afficher seulement si le joueur est en vie
    → condition: player_health > 0

  Condition 2 : Afficher seulement dans le monde "world"
    → condition: world_name == "world"

  Condition 3 : Afficher seulement en mode survie
    → condition: player_gamemode == "SURVIVAL"
```

---

## Empilement de HUDs

Plusieurs HUDs peuvent être actifs simultanément. BetterHud les empile verticalement, en utilisant une BossBar différente par ligne si nécessaire.

L'ordre d'affichage est déterminé par l'ordre de définition dans la configuration.

---

> **Suite →** [Popup](./popup.md)
