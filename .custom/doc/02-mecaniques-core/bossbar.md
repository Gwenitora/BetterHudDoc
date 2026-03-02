# BossBar & Communication client

> [← Sommaire](../SOMMAIRE.md)

---

## Pourquoi la BossBar ?

Le HUD de BetterHud doit être affiché en **haut de l'écran** du joueur. Parmi les éléments disponibles en vanilla, la **barre de boss** est la seule dont le texte est rendu en haut de l'écran et accepte des composants Adventure riches (avec polices custom, couleurs, etc.).

La BossBar est en réalité **invisible** (transparente, sans barre de progression visible), seul son **titre** est utilisé comme vecteur d'affichage du HUD.

---

## Structure d'une BossBar dans Minecraft

Une BossBar Minecraft possède plusieurs propriétés :

```
BossBar
├── UUID         : identifiant unique
├── name         : composant Adventure (le "titre" de la barre)
├── progress     : float 0.0 → 1.0 (longueur de la barre)
├── color        : couleur de la barre (PINK, BLUE, RED...)
├── overlay      : style (PROGRESS, NOTCHED_6, NOTCHED_10...)
├── flags        : DARK_SKY, PLAY_BOSS_MUSIC, CREATE_FOG
```

BetterHud utilise :
- `progress = 0.0` pour que la barre elle-même soit invisible
- `color = PINK` (ou une couleur configurée mais non visible car progress = 0)
- Le champ `name` pour transporter le HUD complet sous forme de composant Adventure

---

## Combien de BossBars ?

Par défaut, Minecraft n'affiche qu'une BossBar à la fois. Cependant, le protocole réseau permet d'envoyer **plusieurs BossBars simultanément** à un joueur.

BetterHud exploite cela pour multiplier le nombre de "lignes" disponibles dans le HUD (une BossBar par ligne verticale du HUD). Le nombre de BossBars utilisées est configurable.

```
Joueur reçoit N BossBars simultanément :

BossBar 1 (ligne haute)  ─→ [ IMAGE BARRE DE VIE  ♥ 20 ]
BossBar 2 (ligne basse)  ─→ [ MANA ◆ 80          ]
BossBar 3 (popup actif)  ─→ [ ! Nouveau message   ]
```

---

## Le paquet réseau

Au niveau réseau, BetterHud manipule directement les paquets de BossBar via la couche **NMS** (Net Minecraft Server). Cela lui permet de :

- Créer/supprimer des BossBars sans passer par l'API Bukkit (plus de contrôle)
- Modifier uniquement le titre (`name`) sans affecter les autres propriétés
- Envoyer des mises à jour uniquement quand le contenu a changé (optimisation)

### Types de paquets utilisés

```
PacketPlayOutBoss (Java Edition)
├── ADD    : enregistre une nouvelle bossbar côté client
├── REMOVE : supprime une bossbar
└── UPDATE_NAME : met à jour uniquement le titre (plus léger)
```

---

## Le composant Adventure envoyé

Le titre d'une BossBar est un **composant Adventure**, qui est un objet texte structuré supportant :

- Du **texte brut** avec couleurs et styles (gras, italique…)
- Des **composants imbriqués** (liste de sous-composants)
- Des **polices custom** par caractère ou par segment

BetterHud construit un composant de ce type pour chaque ligne de rendu :

```
Composant racine (vide)
    │
    ├── Composant espace [+X pixels, police "betterhud:space"]
    ├── Composant image  [char U+E000, police "betterhud:image_abc"]
    ├── Composant espace [-width pixels, police "betterhud:space"]
    ├── Composant espace [+Y pixels, police "betterhud:space"]
    ├── Composant texte  ["♥ 20", police "minecraft:default"]
    └── Composant espace [-width pixels, police "betterhud:space"]
```

Chaque sous-composant a sa propre police, ce qui permet de mélanger images et textes sur la même ligne.

---

## Cycle de mise à jour

```
Serveur démarre le scheduler de rendu
          │
          ▼ (toutes les N ticks, selon tick-speed)
Pour chaque joueur connecté :
          │
          ▼
    Récupérer les HUDs actifs pour ce joueur
          │
          ▼
    Pour chaque HUD actif :
        Calculer le composant résultant
          │
          ▼
    Comparer avec le composant précédent
          │
     ┌────┴────┐
  Changé ?   Identique ?
     │             │
     ▼             ▼
  Envoyer     Ne rien faire
  le paquet   (économie réseau)
  UPDATE_NAME
```

---

## Gestion par joueur

Chaque joueur possède son propre **état de rendu** :

```
HudPlayer (par joueur)
├── UUID du joueur
├── Référence au joueur plateforme (Bukkit Player, etc.)
├── Tick counter (incrémenté à chaque mise à jour)
├── Map des BossBars actives (UUID → BossBar)
├── Dernier composant rendu (pour comparaison / cache)
├── Liste des HUDs actifs
├── Liste des Popups actifs
└── État de la boussole active
```

Cela garantit que le HUD d'un joueur n'affecte jamais celui d'un autre, et que les animations sont synchronisées individuellement.

---

## ActionBar comme alternative

Pour certains éléments (notifications rapides, messages temporaires), BetterHud peut aussi utiliser l'**ActionBar** (barre d'action, au-dessus de l'inventaire). Le mécanisme est identique : le même type de composant Adventure est envoyé, mais via un paquet différent.

```
BossBar  → Affichage persistant en haut de l'écran
ActionBar → Affichage temporaire au-dessus de l'inventaire
```

---

> **Suite →** [Resource Pack](./resource-pack.md)
