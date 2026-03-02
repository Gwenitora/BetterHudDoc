# Sommaire — Documentation Technique BetterHud

> Documentation technique expliquant les mécaniques internes du plugin BetterHud,
> dans le but de comprendre comment recréer un système équivalent de HUD custom côté serveur Minecraft.

---

## Structure de la documentation

### [1. Introduction — Vue d'ensemble](./01-introduction/vue-ensemble.md)
- Qu'est-ce que BetterHud ?
- Les grandes contraintes techniques de Minecraft
- Schéma général de l'architecture du plugin

---

### [2. Mécaniques fondamentales](./02-mecaniques-core/)

| Fichier | Contenu |
|---|---|
| [Font & Positionnement](./02-mecaniques-core/font-et-positionnement.md) | Comment déplacer des éléments à l'écran en utilisant des polices custom |
| [BossBar & Communication client](./02-mecaniques-core/bossbar.md) | Comment le plugin envoie le HUD au client sans mod |
| [Shaders](./02-mecaniques-core/shaders.md) | Pourquoi et comment BetterHud utilise des shaders GLSL |
| [Resource Pack](./02-mecaniques-core/resource-pack.md) | Format, structure et génération automatique du resource pack |

---

### [3. Types d'éléments](./03-elements/)

| Fichier | Contenu |
|---|---|
| [Images & Animations](./03-elements/images-et-animations.md) | Affichage d'images, séquences PNG et système d'animation |
| [Textes & Placeholders](./03-elements/textes-et-placeholders.md) | Rendu de texte dynamique avec variables et mises en forme |
| [Têtes de joueurs](./03-elements/tetes-joueurs.md) | Rendu de la tête d'un joueur en pixel art dans le HUD |

---

### [4. Composants de haut niveau](./04-composants-hud/)

| Fichier | Contenu |
|---|---|
| [HUD](./04-composants-hud/hud.md) | Le composant HUD principal — layouts, tick, condition d'affichage |
| [Popup](./04-composants-hud/popup.md) | Notifications temporaires empilables |
| [Boussole](./04-composants-hud/boussole.md) | Marqueurs directionnels sur une boussole custom |

---

### [5. Configuration](./05-configuration/format-yaml.md)
- Arborescence des fichiers de configuration
- Format YAML du plugin
- Les paramètres principaux

---

## Concepts clés à retenir

1. **Tout repose sur les polices custom** — c'est le cœur du positionnement pixel par pixel.
2. **La BossBar est le vecteur de transmission** — le texte de la bossbar transporte les composants visuels.
3. **Le resource pack est généré automatiquement** — le plugin construit les fichiers JSON de police et les textures au démarrage.
4. **Aucun mod client n'est requis** — tout fonctionne avec les mécaniques vanilla de Minecraft.
5. **Chaque joueur a son propre état** — le rendu est individualisé par joueur.
6. **Les shaders rendent la BossBar transparente** — sans eux, la barre de progression native s'affiche par-dessus le HUD.
