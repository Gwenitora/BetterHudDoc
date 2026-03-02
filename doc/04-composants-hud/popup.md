# Popup — Notifications temporaires

> [← Sommaire](../SOMMAIRE.md)

---

## Qu'est-ce qu'un Popup ?

Un **Popup** est un élément HUD **temporaire** qui s'affiche pour une durée limitée en réponse à un événement. Contrairement au HUD persistant, un popup apparaît, reste visible un certain temps, puis disparaît.

Exemples : notification "niveau atteint", alerte de dégâts, message d'achievement…

---

## Structure d'un Popup

```
Popup
├── Identifiant unique (ex: "level_up_popup")
├── duration      : durée d'affichage en ticks (-1 = infini)
├── group         : groupe de popups (pour la gestion de pile)
├── max-stack     : nombre max de popups simultanés dans ce groupe
├── unique        : si true, un seul exemplaire à la fois par joueur
├── queue         : si true, les popups s'attendent dans une file
├── push          : si true, le plus ancien est retiré quand la pile est pleine
└── layouts[]     : mêmes layouts que le HUD (images, textes, têtes)
```

---

## Le système de groupes

Les popups sont organisés en **groupes**. Tous les popups d'un même groupe partagent la même zone d'affichage et obéissent aux mêmes règles d'empilement.

```
Exemple : groupe "notifications"

  État initial (aucun popup) :
    Zone vide
    
  Popup 1 apparaît :
    [✓ Quête complétée !]
    
  Popup 2 apparaît (max-stack: 2) :
    [✓ Quête complétée !]
    [⬆ Niveau 15 atteint !]
    
  Popup 3 tente d'apparaître (pile pleine) :
    → Si push: true  → Popup 1 est retiré, Popup 3 s'ajoute
    → Si queue: true → Popup 3 attend que la pile se libère
    → Sinon         → Popup 3 est ignoré
```

---

## Cycle de vie d'un Popup

```
Événement déclenche l'affichage d'un popup
              │
              ▼
        Vérification des règles :
        ├── unique: ce popup est-il déjà actif ?
        ├── max-stack: la pile du groupe est-elle pleine ?
        └── queue: ajouter à la file ou ignorer ?
              │
              ▼
        Ajout dans la liste des popups actifs du joueur
              │
              ▼
        Affichage à chaque tick (comme un HUD)
              │
              ▼
        Compteur de durée décrémenté à chaque tick
              │
        duration == 0 ?
         ┌────┴────┐
        OUI       NON
         │         │
         ▼         ▼
    Retrait      Continue
    du popup     l'affichage
         │
         ▼
    Si queue: prochain popup de la file est activé
```

---

## Popups avec données personnalisées

Un popup peut recevoir des **données contextuelles** lors de son déclenchement. Ces données sont disponibles comme placeholders dans les textes du popup.

```
Déclenchement d'un popup "item_obtained" avec données :
  item_name: "Épée en diamant"
  item_count: 1

Texte dans le popup :
  "Obtenu : {item_name} ×{item_count}"
  → "Obtenu : Épée en diamant ×1"
```

---

## Intégration Skript

BetterHud s'intègre avec Skript pour déclencher des popups depuis des scripts :

```
Déclenchement d'un popup via Skript (syntaxe no-code) :
  Action  : show popup "level_up" to player
  Avec    : variable de la liste {data::*}
  Résultat: le popup "level_up" s'affiche pour le joueur
             avec les données de {data::*} accessibles en placeholder
```

---

## Popups vs HUD persistant

| Caractéristique | HUD persistant | Popup |
|---|---|---|
| Durée | Infinie (tant qu'actif) | Limitée (`duration`) |
| Déclenchement | Automatique / conditionnel | Sur événement |
| Empilement | Non (un par BossBar) | Oui (groupes, max-stack) |
| Données contextuelles | Non | Oui |
| File d'attente | Non | Oui (si `queue: true`) |

---

> **Suite →** [Boussole](./boussole.md)
