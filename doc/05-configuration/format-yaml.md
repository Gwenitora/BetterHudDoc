# Format de Configuration YAML

> [← Sommaire](../SOMMAIRE.md)

---

## Structure des dossiers de configuration

```
plugins/BetterHud/
├── config.yml              ← configuration principale
├── database.yml            ← configuration base de données
├── font.yml                ← configuration des polices
├── shader.yml              ← configuration des shaders
│
├── images/                 ← définitions des images
│   ├── my_image.png        ← image statique
│   └── animated_bar/       ← dossier = animation
│       ├── 0.png
│       ├── 1.png
│       └── ...
│
├── texts/                  ← définitions des textes réutilisables
│   └── health_text.yml
│
├── huds/                   ← définitions des HUDs
│   └── main_hud.yml
│
├── popups/                 ← définitions des popups
│   └── level_up.yml
│
├── compasses/              ← définitions des boussoles
│   └── main_compass.yml
│
└── pack/                   ← resource pack généré (sortie)
    └── ...
```

---

## `config.yml` — Configuration principale

```
config.yml
├── debug             : active les logs détaillés (true/false)
├── auto-save-time    : intervalle de sauvegarde automatique (secondes)
├── tick-speed        : nombre de ticks entre chaque mise à jour du HUD
├── namespace         : namespace utilisé dans le resource pack
├── pack-type         : format du resource pack (zip / folder / none)
├── merge-boss-bar    : fusionner toutes les BossBars en une seule ?
│
├── default-hud       : liste des HUDs actifs par défaut pour tous les joueurs
├── default-popup     : liste des popups actifs par défaut
└── default-compass   : boussole active par défaut
```

---

## `font.yml` — Configuration des polices

```
font.yml
├── scale              : échelle de rendu des textures de police
├── height             : hauteur des caractères en pixels
├── ascent             : ligne de base verticale
├── merge-default-bitmap : fusionner avec la police Minecraft par défaut ?
└── use-unifont        : utiliser la police Unicode unifont ?
```

---

## `database.yml` — Configuration base de données

BetterHud peut stocker des données joueurs (HUDs activés, etc.) en base de données :

```
database.yml
├── type               : sqlite (fichier local) ou mysql (serveur SQL)
└── connection         : (si mysql)
    ├── host           : adresse du serveur MySQL
    ├── port           : port (défaut 3306)
    ├── database       : nom de la base de données
    ├── username       : identifiant
    └── password       : mot de passe
```

---

## Format d'un HUD (`huds/mon_hud.yml`)

```
<identifiant_hud>:
  default: true/false          ← actif pour tous les joueurs ?
  tick: <nombre>               ← fréquence de mise à jour
  
  layouts:
    - name: <nom_layout>
      gui:
        x: <0-100>             ← position horizontale (% de l'écran)
        y: <0-100>             ← position verticale (% de l'écran)
      pixel:
        x: <pixels>            ← décalage fin en pixels
        y: <pixels>
      align: LEFT/CENTER/RIGHT
      opacity: <0.0-1.0>
      
      images:
        <nom_image>:
          image: <id_image>
          scale: <facteur>
          animation-type: LOOP/PLAY_ONCE
          opacity: <0.0-1.0>
          outline: <couleur_hex>
      
      texts:
        <nom_texte>:
          pattern: "<texte avec {placeholders}>"
          scale: <facteur>
          color: <couleur>
          shadow: <0-1>
          align: LEFT/CENTER/RIGHT
          max-width: <pixels>
      
      heads:
        <nom_tete>:
          type: player/entity/custom
          scale: <facteur>
          pixel: <pixels>
```

---

## Format d'un Popup (`popups/mon_popup.yml`)

```
<identifiant_popup>:
  duration: <ticks>            ← durée (-1 = infini)
  group: <nom_groupe>
  max-stack: <nombre>
  unique: true/false
  queue: true/false
  push: true/false
  
  layouts:                     ← même format que les layouts de HUD
    - name: ...
      ...
```

---

## Format d'une Image (`images/mon_image.yml` ou PNG direct)

Pour une image simple, il suffit de placer un fichier `.png` dans le dossier `images/`. Pour les options avancées, un fichier YAML peut accompagner l'image :

```
<id_image>:
  file: <chemin_vers_png>     ← relatif au dossier images/
  scale: <facteur>
  pixel:
    x: <pixels>
    y: <pixels>
  outline: <couleur_hex>      ← optionnel, couleur de contour
```

Pour une animation, un **dossier** portant le nom de l'image contient les frames numérotées (`0.png`, `1.png`, …).

---

## Format d'un Texte réutilisable (`texts/mon_texte.yml`)

Les textes peuvent être définis séparément et référencés dans plusieurs layouts :

```
<id_texte>:
  pattern: "<texte avec {placeholders}>"
  color: <couleur>
  shadow: true/false
  scale: <facteur>
  align: LEFT/CENTER/RIGHT
  max-width: <pixels>
  line-spacing: <pixels>
```

---

## Placeholders disponibles dans les textes

```
Placeholders intégrés (exemples) :
  {player_name}           → Nom du joueur
  {player_health}         → Vie actuelle
  {player_max_health}     → Vie maximale
  {player_food}           → Niveau de faim
  {player_level}          → Niveau d'expérience
  {player_exp}            → Points d'expérience
  {player_x}              → Coordonnée X
  {player_y}              → Coordonnée Y
  {player_z}              → Coordonnée Z
  {player_world}          → Nom du monde
  {player_gamemode}       → Mode de jeu
  {server_online}         → Joueurs en ligne
  {world_time}            → Heure dans le monde
  {world_weather}         → Météo

Placeholders externes (via PlaceholderAPI) :
  {papi:placeholder_id}   → N'importe quel placeholder PAPI
```

---

## Commandes de gestion

```
Commandes disponibles (no-code) :

  /betterhud reload               → Recharge la configuration
  /betterhud hud <add/remove> <hud_id> <joueur>
                                  → Ajoute/retire un HUD pour un joueur
  /betterhud popup <show> <popup_id> <joueur>
                                  → Affiche un popup pour un joueur
  /betterhud compass <add/remove> <compass_id> <joueur>
                                  → Gère la boussole d'un joueur
```

---

> [← Retour au Sommaire](../SOMMAIRE.md)
