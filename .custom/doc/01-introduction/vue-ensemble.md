# Vue d'ensemble — Architecture générale de BetterHud

> [← Sommaire](../SOMMAIRE.md)

---

## Qu'est-ce que BetterHud ?

BetterHud est un plugin Minecraft **côté serveur** qui permet d'afficher des HUD (Heads-Up Display) entièrement personnalisés à l'écran des joueurs. Il fonctionne sans aucun mod côté client, en exploitant des mécaniques vanilla du jeu.

Le plugin supporte plusieurs plateformes : Bukkit/Paper/Folia, Velocity (proxy) et Fabric (serveur).

---

## Les contraintes techniques de Minecraft

Pour comprendre BetterHud, il faut d'abord comprendre les limitations de Minecraft :

- Le client Minecraft n'autorise **aucun affichage HUD personnalisé** par défaut.
- Les seules zones de texte disponibles côté serveur sans mod sont :
  - La **barre de boss** (BossBar) — texte affiché en haut de l'écran
  - La **barre d'action** (ActionBar) — texte au-dessus de la barre d'inventaire
  - L'en-tête/pied de page du **tableau des joueurs** (Tab list)
  - Les **scoreboards**
- Les **resource packs** permettent de définir des polices custom avec des codepoints arbitraires.
- Les clients acceptent des **caractères Unicode spéciaux** dans les composants de texte.

BetterHud combine ces deux leviers — resource pack + bossbar — pour créer l'illusion d'un HUD complet.

---

## Schéma général de l'architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        SERVEUR MINECRAFT                        │
│                                                                 │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────────┐  │
│  │  Fichiers de │    │   Managers   │    │  Resource Pack   │  │
│  │ configuration│───▶│  (HUD, Text, │───▶│   Generator      │  │
│  │    (YAML)    │    │  Image, ...) │    │                  │  │
│  └──────────────┘    └──────┬───────┘    └──────────────────┘  │
│                             │                      │            │
│                    ┌────────▼────────┐             │            │
│                    │  HUD Renderer   │             │            │
│                    │ (par joueur,    │             │            │
│                    │  par tick)      │             │            │
│                    └────────┬────────┘             │            │
│                             │                      │            │
│                    ┌────────▼────────┐             │            │
│                    │  Composants     │             │            │
│                    │  Adventure      │             │            │
│                    │  (texte+polices)│             │            │
│                    └────────┬────────┘             │            │
│                             │                      │            │
└─────────────────────────────┼──────────────────────┼────────────┘
                              │                      │
                    ┌─────────▼──────────┐  ┌────────▼───────────┐
                    │  Paquet BossBar     │  │  Resource Pack      │
                    │  (réseau)           │  │  (.zip envoyé ou   │
                    └─────────┬──────────┘  │   lien URL)         │
                              │             └────────┬────────────┘
                              │                      │
                    ┌─────────▼──────────────────────▼────────────┐
                    │                 CLIENT MINECRAFT             │
                    │                                              │
                    │  Interprète le texte de la BossBar avec     │
                    │  les polices custom du resource pack pour   │
                    │  afficher les éléments aux bons endroits    │
                    └──────────────────────────────────────────────┘
```

---

## Les grands modules du plugin

| Module | Rôle |
|---|---|
| **API standard** | Interfaces publiques (HudObject, Hud, Popup, Compass, HudPlayer…) |
| **Distribution** | Implémentation complète de la logique de rendu |
| **Bootstrap Bukkit/Fabric/Velocity** | Adaptateurs spécifiques à chaque plateforme |
| **NMS (version-specific)** | Manipulation bas niveau des paquets réseau Minecraft |
| **Scheduler** | Abstraction de la planification de tâches multi-plateforme |
| **Bedrock** | Détection et support des joueurs Bedrock (via Geyser/Floodgate) |

---

## Le flux de démarrage

```
Démarrage du serveur
        │
        ▼
Chargement des fichiers YAML
(config, images, textes, huds, popups, boussoles)
        │
        ▼
Construction des objets internes
(HudImpl, PopupImpl, CompassImpl…)
        │
        ▼
Génération du Resource Pack
(polices JSON, textures, métadonnées)
        │
        ▼
Mise à disposition du pack
(zip téléchargeable, ou serveur HTTP intégré)
        │
        ▼
Connexion d'un joueur
        │
        ├── Envoi du resource pack au client
        │
        └── Démarrage du cycle de rendu
            (mise à jour selon le tick-speed configuré)
```

---

## Le cycle de rendu par joueur

À chaque tick de rendu, pour chaque joueur connecté :

1. L'état du joueur est récupéré (position, vie, events actifs, etc.)
2. Chaque HUD actif est évalué : ses **layouts** sont parcourus
3. Pour chaque layout, les éléments (image, texte, tête) produisent un **composant Adventure**
4. Les composants sont assemblés en une **chaîne de texte** avec des caractères de positionnement
5. La chaîne est envoyée comme nom d'une **BossBar** invisible au joueur

Le résultat : le client affiche les éléments aux positions pixel précises définies dans la configuration.

---

> **Suite →** [Mécaniques fondamentales : Font & Positionnement](../02-mecaniques-core/font-et-positionnement.md)
