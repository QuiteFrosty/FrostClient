# FrostClient (Classic) — Development Roadmap & Feature Specification
> **Project Mission:** To build the definitive open-source, community-driven Minecraft PvP client. FrostClient prioritizes zero bloat, maximum frame-rate optimization, crash-free mod compatibility, and transparent community governance—offering a true, open-source competitor to closed, proprietary clients like Lunar and Badlion.

---

## 📜 Table of Contents
1. [Core Philosophy & Architecture](#1-core-philosophy--architecture)
2. [User Interface & Mod Menu Design](#2-user-interface--mod-menu-design)
3. [FPS Optimization Engine ("Boosted Frames")](#3-fps-optimization-engine-boosted-frames)
4. [Mixin Architecture & Mod Compatibility Standards](#4-mixin-architecture--mod-compatibility-standards)
5. [Complete Module Library Specification](#5-complete-module-library-specification)
6. [Open-Source Cosmetics & Cape System](#6-open-source-cosmetics--cape-system)
7. [Profile & Preset Sharing System](#7-profile--preset-sharing-system)
8. [Automated Build & CI/CD Pipeline](#8-automated-build--cicd-pipeline)
9. [Future Multi-Version Roadmap](#9-future-multi-version-roadmap)

---

## 1. Core Philosophy & Architecture
FrostClient is built on the belief that software used by millions should be open, transparent, and user-controlled.

* **Open Source First:** Full source availability under the GNU LGPL-3.0 license allows the community to inspect, contribute, and modify the client.
* **Zero Telemetry & Bloat:** No background tracking, forced updates, or resource-heavy analytics wrappers.
* **Native Modloader Execution:** Operates strictly as a Forge `.jar` mod (for 1.8.9) without forcing users into a locked, proprietary launcher ecosystem.
* **Lightweight Footprint:** Instant startup times and minimal CPU/RAM overhead compared to commercial competitors.

---

## 2. User Interface & Mod Menu Design
FrostClient features a dark-themed, grid-based GUI inspired by modern competitive client menus, combining visual appeal with high functional clarity.

### 2.1 UI Layout Specification
* **Grid Layout:** 3-column responsive card grid inside a semi-transparent dark overlay (`rgba(15, 15, 15, 200)`).
* **Dynamic Card State Borders:**
  * **Enabled:** Neon Green outline (`#00E676`).
  * **Disabled:** Dark Crimson outline (`#FF1744`).
* **Interactive Elements:**
  * Left-clicking the main card body toggles the module state instantly.
  * Clicking the dedicated gear icon (`⚙`) on the right opens module-specific configuration sliders and toggles.
* **Navigation Header:** Categorized tabs (`All`, `Combat`, `Movement`, `Render`, `Status`, `QoL`) with real-time text filtering search bar.

---

## 3. FPS Optimization Engine ("Boosted Frames")
FrostClient incorporates bytecode-level rendering enhancements that eliminate Vanilla 1.8.9 performance bottlenecks and micro-stutters.

### 3.1 Key Optimization Patches
| Optimization Feature | Mechanism | Expected Performance Impact |
| --- | --- | --- |
| **Frustum Particle Culling** | Skips rendering particle effects outside the player's active camera FOV frustum. | +15-30% FPS in particle-heavy fights (e.g., Criticals, Potions). |
| **HUD String Caching** | Caches OpenGL font render batches for static HUD text to avoid redundant draw calls per frame. | Reduced CPU draw call overhead; smoother frame pacing. |
| **Entity Render Optimization** | Suppresses off-screen entity rendering calculations. | Major FPS gain on populated servers (Hypixel lobbies, Bedwars bases). |
| **Garbage Collection Reduction** | Replaces temporary `new BlockPos()` object allocations in render loops with reusable static instances. | Eliminates periodic GC micro-freezes during intense motion. |
| **Animated Texture Throttling** | Pauses tile texture updates (lava, water, fire) when blocks are not visible in the viewport. | Lower GPU memory bandwidth consumption. |

---

## 4. Mixin Architecture & Mod Compatibility Standards
A common complaint with proprietary PvP clients is frequent Mixin conflicts that cause crashes when adding third-party mods. FrostClient enforces strict bytecode modification rules to guarantee seamless Forge mod compatibility.

### 4.1 Strict Compatibility Guidelines
1. **Ban `@Overwrite`:** Never use `@Overwrite` unless absolute bytecode replacement is necessary. Use soft `@Inject` hooks at `HEAD`, `RETURN`, or specific instruction opcodes.
2. **Explicit Priority Assignment:** Set custom priorities (`priority = 999` or `1001`) on all Mixins to avoid conflicting with standard utility mods (e.g., OptiFine, ReplayMod).
3. **Per-Module Kill-Switches:** Wrap all bytecode injection paths in conditional checks (`if (FrostClient.config.isModuleEnabled("..."))`). If a conflict arises with an external mod, the user can disable the specific module without breaking the client.
4. **Graceful Fail-Safes:** Surround non-critical hook injections with `try-catch` blocks inside custom event dispatchers.

---

## 5. Complete Module Library Specification
FrostClient includes an expansive suite of toggleable HUD modules, PvP tools, visual customizations, and quality-of-life utilities.

### 5.1 Combat & PvP
* **Reach Display:** Shows exact block distance to target on landed hits.
* **Combo Counter:** Real-time streak tracking of consecutive landed blows.
* **CPS Counter:** Left/Right clicks-per-second indicator with customizable update intervals.
* **Hit Color & Hurt Cam:** Custom entity red overlay color and option to remove screen-tilt on hit.
* **Hitbox Customizer:** Toggles visible entity bounding boxes with custom RGB color and line width sliders.
* **Armor & Item HUD:** Displays remaining armor durability percentages and held item counts.

### 5.2 Movement & Control
* **Keystrokes:** On-screen WASD + Mouse button visualizer with chroma and press animation effects.
* **Toggle Sprint / Toggle Sneak:** Hands-free movement toggles with customizable HUD text (`[Sprinting (Toggled)]`).
* **Perspective / Freelook:** 360-degree camera rotation around character without changing movement vector.

### 5.3 Visuals & Rendering
* **Custom Crosshair:** Vector-rendered dots, circles, and crosshairs with outline and gap sliders.
* **Fullbright:** Overrides gamma table for full night vision across all dimensions.
* **Time & Weather Changer:** Custom client-side world time lock and weather particle toggles.
* **Motion Blur:** Configurable shader-based motion blur intensity.
* **Chunk Animator:** Smoothly animates newly loading world chunks rising from underground.

### 5.4 Status & Trackers
* **Potion Status HUD:** Active buff/debuff display with duration countdown timers.
* **Stats HUD:** Configurable widgets for FPS, Server Ping, Coordinates, Direction Vector, and Speedometer.
* **Item Counters:** Quick-glance indicators for Ender Pearls, Golden Apples, and Arrow counts.
* **Scoreboard Customizer:** Hides background red numbers, score shadows, and adjusts scoreboard position.

### 5.5 Quality of Life & Chat
* **Auto GG & Auto Respawn:** Sends automated end-game messages and bypasses death screens.
* **Chat Enhancer:** Infinite scrollback history, background opacity controls, and duplicate message grouping (`x2`, `x3`).
* **Screenshot Utility:** Copies captured screenshots directly to system clipboard or auto-uploads to custom endpoints.
* **Discord Rich Presence (RPC):** Displays server IP, current gamemode, and session elapsed time on Discord profiles.

---

## 6. Open-Source Cosmetics & Cape System
FrostClient provides a free, open-source cosmetic framework that allows players to upload custom capes and 3D cosmetics without subscription paywalls.

```
┌─────────────────┐       GET /api/v1/user/{uuid}       ┌────────────────────────┐
│  FrostClient    │ ──────────────────────────────────> │  Frost API Server      │
│  Client Engine  │ <────────────────────────────────── │  (Node.js / FastAPI)   │
└─────────────────┘     JSON Cosmetic Manifest          └────────────────────────┘
        │                                                           │
        ▼                                                           ▼
RenderPlayer Mixin                                           Microsoft OAuth2
Inject Cape Texture / 3D Model                               UUID Authentication
```

### 6.1 Backend Architecture
* **REST API:** Lightweight API built with Express or FastAPI.
* **Authentication:** Microsoft OAuth2 flow verifies official Minecraft account ownership (UUID) before allowing users to bind capes or cosmetics.
* **Client Mixin Hook:** Intercepts `RenderPlayer.getEntityTexture()` and `AbstractClientPlayer.getLocationCape()` to fetch and apply textures asynchronously.

### 6.2 Cosmetic Manifest JSON Schema
```json
{
  "uuid": "069a79f4-44e9-4726-a7be-fca83e38aaf5",
  "cape": {
    "enabled": true,
    "url": "https://cdn.frostclient.net/capes/community_dev.png"
  },
  "cosmetics": [
    {
      "id": "dragon_wings_3d",
      "type": "BACK",
      "texture": "https://cdn.frostclient.net/models/dragon_wings.png",
      "color": "#00E676"
    }
  ]
}
```

---

## 7. Profile & Preset Sharing System
To encourage community sharing, FrostClient serializes all UI layouts and module configurations into short, pasteable Base64 strings.

### 7.1 Configuration Flow
1. **JSON Serialization:** Client serializes `ModuleConfig` objects into a single JSON file (`config/frostclient/profiles/default.json`).
2. **Base64 Encoding:** Layout data is compressed and encoded into an alphanumeric string (e.g., `eyJ2ZXJzaW9uIjoxLCJtb2R1bGVzIjpb...`).
3. **One-Click Sharing:** Players can paste layout strings into the in-game HUD Editor to instantly mirror their favorite creator's HUD setup.

---

## 8. Automated Build & CI/CD Pipeline
FrostClient uses GitHub Actions to automatically compile `.jar` artifacts on every commit, ensuring access to cutting-edge nightly builds.

```yaml
name: Build FrostClient (Classic)
on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Repository
        uses: actions/checkout@v3
      - name: Set up JDK 8
        uses: actions/setup-java@v3
        with:
          java-version: '8'
          distribution: 'temurin'
      - name: Grant Execute Permission for Gradle
        run: chmod +x 1.8.9/cloudclient/gradlew
      - name: Build with Gradle
        run: |
          cd 1.8.9/cloudclient
          ./gradlew build --no-daemon
      - name: Upload Compiled Jar Artifact
        uses: actions/upload-artifact@v3
        with:
          name: FrostClient-1.8.9-Build
          path: 1.8.9/cloudclient/build/libs/*.jar
```

---

## 9. Future Multi-Version Roadmap
* **Phase 1 (Current):** Perfect the 1.8.9 Forge base codebase, implement high-performance Mixin optimization engine, and refine the dark grid GUI menu.
* **Phase 2:** Launch the open-source Cape & Cosmetic REST API alongside Microsoft OAuth authentication.
* **Phase 3 (Modern Versions):** Port the FrostClient HUD editor, module pipeline, and UI framework to modern Fabric (1.19+) using Sodium and Iris for current-generation PvP servers.
