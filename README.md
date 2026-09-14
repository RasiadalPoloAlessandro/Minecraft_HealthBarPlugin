# Cool Health Bar

[![Build Status](https://github.com/RasiadalPoloAlessandro/Minecraft_HealthBarPlugin/actions/workflows/main.yml/badge.svg)](https://github.com/RasiadalPoloAlessandro/Minecraft_HealthBarPlugin/actions/workflows/main.yml)
[![Static Analysis](https://img.shields.io/badge/Checkstyle-Passing-brightgreen)](https://github.com/RasiadalPoloAlessandro/Minecraft_HealthBarPlugin/actions)

**Cool Health Bar** is a lightweight server-side plugin for Paper/Spigot servers. It displays a dynamic, segmented health bar above living entities that updates health and transitions color in real time.

---

## Features

- **Custom Display:** Dynamic 10-segment health bar rendered smoothly above living entities.
- **Reactive Feedback:** The bar updates dynamically in real time, changing both symbols and colors based on remaining health.
- **Full Filter Control:** Granular commands allowing players/admins to hide or show health bars globally or for specific entity types.
- **Native Localization:** Command messages and UI support both English and Italian, leveraging client-side language detection.
- **Boss Protection:** Built-in exclusions so vanilla bosses remain unaffected.

---

## Commands

Base command: `/chb`

| Command | Description |
| :--- | :--- |
| `/chb show all` | Shows health bars for all supported entities |
| `/chb show <entity>` | Enables health bars for a specific entity type |
| `/chb hide all` | Hides all health bars globally |
| `/chb hide <entity>` | Disables health bars for a specific entity type |

---

## Requirements & Compatibility

- **Minecraft Version:** `26.2` (or Paper-compatible fork)
- **Java Version:** `Java 25` (built and tested on a Java 25 toolchain)

---

## Installation

1. Download the latest `.jar` from the [Releases](https://github.com/RasiadalPoloAlessandro/Minecraft_HealthBarPlugin/releases) section.
2. Drop the file into your server's `plugins/` directory.
3. Start or restart your server.
