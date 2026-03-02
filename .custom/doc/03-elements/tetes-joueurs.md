# Têtes de joueurs

> [← Sommaire](../SOMMAIRE.md)

---

## Vue d'ensemble

BetterHud permet d'afficher la **tête d'un joueur** dans le HUD sous forme d'image pixel art. Cela est typiquement utilisé pour afficher l'avatar du joueur dans une fenêtre de statut, ou les têtes des membres d'une équipe.

---

## Comment fonctionne le rendu de tête ?

La tête d'un joueur Minecraft est définie par son **skin** (une texture PNG 64×64 pixels). La face de la tête est la zone comprise entre `(8, 8)` et `(16, 16)` sur la texture.

BetterHud extrait cette zone et la convertit en une image bitmap qu'il incorpore dans le resource pack comme n'importe quelle autre image.

```
Texture de skin (64×64) :

  ┌────────────────────────────────┐
  │  Tête (couche 1) [8,8]→[16,16]│ ← zone extraite
  ├────────────────────────────────┤
  │  Casque (couche 2)             │
  ├────────────────────────────────┤
  │  Corps / bras / jambes...      │
  └────────────────────────────────┘

Zone extraite → Image 8×8 pixels
                → Assignée à un codepoint dans le resource pack
```

---

## Récupération du skin

Pour afficher la tête, le plugin doit d'abord récupérer la texture de skin du joueur. Plusieurs sources sont supportées :

```
Sources de skin (par priorité) :
├── SkinsRestorer (plugin) → si installé, accès direct aux skins custom
├── GameProfile Minecraft  → skin officiel via le profil Mojang
└── Fallback HTTP          → requête à l'API Mojang si nécessaire
       (api.mojang.com/users/profiles/minecraft/{name})
       (sessionserver.mojang.com/session/minecraft/profile/{uuid})
```

---

## Cache des têtes

La récupération des skins peut être lente (requêtes réseau). BetterHud met les textures de têtes en **cache** pour éviter de les re-télécharger à chaque tick.

```
Flux de récupération d'une tête :

  Joueur A entre dans le HUD (première fois)
         │
         ▼
  Cache vide → Déclenche la récupération asynchrone
         │
         ▼
  [Async] Requête API Mojang / SkinsRestorer
         │
         ▼
  Texture reçue → Extraction de la face [8,8]→[16,16]
         │
         ▼
  Image ajoutée au resource pack (dynamiquement)
         │
         ▼
  Codepoint assigné → Stocké en cache
         │
         ▼
  Prochains ticks : utilisation directe du cache
```

---

## Injection dynamique dans le resource pack

La particularité des têtes est que le resource pack **doit être mis à jour** quand une nouvelle tête est ajoutée, car chaque tête correspond à une texture unique.

BetterHud gère cela de deux façons :

### Option 1 : Pré-chargement au démarrage

Les skins de tous les joueurs connus sont pré-récupérés au démarrage. Le resource pack est généré une seule fois avec toutes les têtes.

### Option 2 : Régénération à la connexion

Quand un joueur se connecte avec un skin non encore connu, le resource pack est régénéré et renvoyé. Cette approche peut causer un court délai.

---

## Affichage en mode "fancy" (3D)

BetterHud propose un mode d'affichage "fancy" pour les têtes qui simule un effet 3D en combinant plusieurs vues (face + côtés).

```
Mode normal :           Mode fancy (pseudo-3D) :

  ┌──────┐               ┌──────┐
  │ Face │               │ Face │ ← vue frontale
  │      │             ┌─┤      │
  └──────┘             │ └──────┘
                       └ côté (décalé, semi-transparent)
```

---

## Intégration dans les layouts

Les têtes s'intègrent dans les layouts comme n'importe quel autre élément, avec les mêmes propriétés de position (`pixel: x, y`) et d'échelle.

```
Configuration d'une tête dans un layout :

Layout "player_info"
└── heads
    └── my_head
        ├── pixel: { x: 5, y: 5 }   ← position dans le layout
        ├── scale: 2                  ← taille doublée (16×16 pixels rendus)
        └── type: player              ← tête du joueur courant
```

---

## Têtes d'entités

En plus des joueurs, BetterHud peut afficher des têtes d'entités Minecraft (mobs). Ces têtes sont des textures statiques issues des assets du jeu, sans besoin de requête externe.

```
Types de têtes supportés :
├── player   → tête du joueur actuel ou d'un joueur ciblé
├── entity   → tête d'une entité Minecraft (zombie, creeper…)
└── custom   → texture PNG fournie par l'admin
```

---

> **Suite →** [HUD — Composant principal](../04-composants-hud/hud.md)
