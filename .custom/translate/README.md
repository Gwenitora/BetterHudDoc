# BetterHud — Traduction Java / Maven

> Traduction complète du plugin BetterHud (Kotlin → Java), Bukkit uniquement, multiversion, sous Maven.

---

## Structure du projet

```
.custom/translate/
├── pom.xml                   ← POM parent (4 modules)
│
├── betterhud-api/            ← Interfaces publiques pures (pas de dépendance Bukkit)
│   └── src/main/java/kr/toxicity/hud/api/
│       ├── BetterHud.java          Interface principale
│       ├── BetterHudAPI.java       Singleton holder
│       ├── BetterHudBootstrap.java Services plateforme
│       ├── BetterHudLogger.java    Interface de log
│       ├── adapter/                LocationWrapper, WorldWrapper
│       ├── compass/                Compass
│       ├── component/              WidthComponent, PixelComponent
│       ├── configuration/          HudObject, HudObjectType, HudComponentSupplier
│       ├── database/               HudDatabase, HudDatabaseConnector
│       ├── hud/                    Hud
│       ├── listener/               HudListener
│       ├── manager/                CompassManager, ConfigManager, DatabaseManager,
│       │                           HudManager, ListenerManager, PlaceholderManager,
│       │                           PlayerManager, PopupManager, ShaderManager,
│       │                           TextManager, TriggerManager
│       ├── placeholder/            HudPlaceholder, PlaceholderContainer
│       ├── player/                 HudPlayer, HudPlayerHead, PointedLocation, PointedLocationProvider
│       ├── plugin/                 ReloadFlagType, ReloadInfo, ReloadState (sealed)
│       ├── popup/                  Popup, PopupIterator, PopupIteratorGroup,
│       │                           PopupSortType, PopupUpdater
│       ├── scheduler/              HudScheduler, HudTask
│       ├── trigger/                HudTrigger
│       ├── update/                 UpdateEvent, UpdateReason
│       ├── version/                MinecraftVersion (record, comparable)
│       ├── volatilecode/           VolatileCodeHandler
│       └── yaml/                   YamlConfiguration, YamlElement, YamlArray, YamlObject
│
├── betterhud-core/           ← Logique centrale (dépend de betterhud-api + Paper API)
│   └── src/main/java/kr/toxicity/hud/
│       ├── BetterHudImpl.java      Implémentation principale, orchestre les managers
│       ├── animation/              AnimationType
│       ├── compass/                CompassImpl
│       ├── hud/                    HudImpl
│       ├── image/                  LoadedImage
│       ├── layout/                 HudLayout, LayoutAlign
│       ├── location/               GuiLocation, PixelLocation
│       ├── manager/                BetterHudManager (interface cycle reload)
│       │                           + 11 singletons : ConfigManagerImpl,
│       │                             HudManagerImpl, PopupManagerImpl,
│       │                             CompassManagerImpl, PlayerManagerImpl,
│       │                             ShaderManagerImpl, DatabaseManagerImpl,
│       │                             ListenerManagerImpl, PlaceholderManagerImpl,
│       │                             TriggerManagerImpl, TextManagerImpl
│       ├── pack/                   PackGenerator, PackMeta, PackType
│       ├── player/                 HudPlayerImpl (abstraite)
│       ├── popup/                  PopupImpl
│       ├── shader/                 HudShader (record), ShaderProperty
│       └── yaml/                   YamlObjectImpl, YamlArrayImpl,
│                                   YamlScalarImpl, YamlConfigurationImpl
│
├── betterhud-nms/            ← Abstraction NMS multiversion via réflexion
│   └── src/main/java/kr/toxicity/hud/nms/
│       ├── NMSVersion.java         Enum 9 révisions (1.20.3 → 1.21.11)
│       ├── NMSHandler.java         Interface d'accès NMS
│       ├── NMSLoader.java          Détecte la version et instancie le bon handler
│       └── ReflectionNMSHandler.java  Implémentation par réflexion (pas d'import NMS)
│
└── betterhud-plugin/         ← Bootstrap Bukkit — produit le jar final (fat jar)
    └── src/main/
        ├── java/kr/toxicity/hud/bukkit/
        │   ├── BetterHudPlugin.java    JavaPlugin principal
        │   ├── event/                  5 events Bukkit (Join, Quit, Update, ReloadStart, Reloaded)
        │   ├── listener/               HudPlayerListener (join/quit/serverLoad)
        │   ├── player/                 HudPlayerBukkit
        │   └── scheduler/              BukkitScheduler, BukkitTaskWrapper
        └── resources/
            ├── plugin.yml
            └── config.yml
```

---

## Compilation

```bash
cd .custom/translate
mvn compile          # Compile les 4 modules
mvn package          # Produit betterhud-plugin/target/betterhud-plugin-*-shaded.jar
```

**Prérequis :** Java 17+, Maven 3.8+, accès Internet (téléchargement des dépendances depuis papermc.io au premier build).

---

## Versions Minecraft supportées

| NMSVersion | Minecraft        |
|------------|------------------|
| V1_20_R3   | 1.20.3 – 1.20.4  |
| V1_20_R4   | 1.20.5 – 1.20.6  |
| V1_21_R1   | 1.21 – 1.21.1    |
| V1_21_R2   | 1.21.2 – 1.21.3  |
| V1_21_R3   | 1.21.4           |
| V1_21_R4   | 1.21.5           |
| V1_21_R5   | 1.21.6 – 1.21.8  |
| V1_21_R6   | 1.21.9 – 1.21.10 |
| V1_21_R7   | 1.21.11          |

La détection est automatique au démarrage via `NMSLoader` (réflexion sur `Bukkit.getVersion()`).

---

## Conventions de documentation

Chaque **classe**, **champ** et **méthode** possède au minimum un commentaire Javadoc sommaire (`/** ... */`).
