<p align="center">
  <img src="/screenshots/FrostLogoV2.png" alt="Logo" width="200" height="200">
</p>

# FrostClient
FrostClient is a powerful, open-source Minecraft PvP client built for the Forge API, forked from [CloudClient](https://github.com/cloudclientdev/cloudclient). It's a zero-bloat, zero-telemetry alternative to closed-source clients like Lunar and Badlion — every line of the client is inspectable, forkable, and community-driven.

See [ROADMAP.md](ROADMAP.md) for the full project vision, module library, FPS optimization plans, and mod compatibility standards.

## Version Status
FrostClient currently targets three Minecraft versions, but they're at different stages of development:

| Version | Status | Notes |
| --- | --- | --- |
| **1.8.9** | Active — playable | Published on [Modrinth as "Frost"](https://modrinth.com/mod/frost). This is the version actively developed and maintained. |
| **1.7.10** | Not yet ported | Still stock CloudClient source, not rebranded to FrostClient yet. |
| **1.12.2** | Not yet ported | Still stock CloudClient source, not rebranded to FrostClient yet. |

If you're looking to actually play FrostClient today, use the **1.8.9** build. The 1.7.10 and 1.12.2 folders are placeholders for future porting work (see [ROADMAP.md](ROADMAP.md)) and contributions to help port them are very welcome.

## Download
https://modrinth.com/mod/frost

## Features
* **Dark, grid-based mod menu** — category tabs, live search, and per-module config panels.
* **HUD Editor** — drag-and-drop layout editor with shareable Base64 config strings.
* **PvP & Combat tools** — CPS counter, combo counter, reach display, hitboxes, and more.
* **FPS optimizations** — particle culling, HUD render caching, and reduced GC pressure in render loops.
* **Crash-safe Mixin architecture** — soft `@Inject` hooks and per-module kill-switches to stay compatible with other Forge mods.

## Screenshots
### Title Screen
![Title Screen](https://media.discordapp.net/attachments/1117324391869595680/1122617589688979496/image.png?width=1154&height=610)

### HUD Editor
![HUD Editor](https://media.discordapp.net/attachments/1117324391869595680/1122617883822936106/image.png?width=1157&height=610)

### Mod Menu
![Mod Menu](https://media.discordapp.net/attachments/1117324391869595680/1122618027217780827/image.png?width=1149&height=610)

## Workspace Setup
1. Clone or download the repository using Git or the ZIP download.
2. Open the folder for your target version (`1.7.10/cloudclient`, `1.8.9/cloudclient`, or `1.12.2/cloudclient`) and copy the path.
3. Open a command prompt or terminal and navigate to the copied path.
```
cd C:\User\Desktop\FrostClient-main\<version>\cloudclient
```
4. Setting up the workspace for your IDE:
- IntelliJ IDEA
```
gradlew setupDecompWorkspace idea
```
- Eclipse
```
gradlew setupDecompWorkspace eclipse
```
5. Open the project with your preferred IDE (do not import it as a Gradle project).
6. To enable Mixins in a development environment, add the following arguments to your program arguments.
```
--tweakClass org.spongepowered.asm.launch.MixinTweaker --mixin mixins.cloudmc.json
```

## Building
1. Open the `1.7.10/cloudclient`, `1.8.9/cloudclient`, or `1.12.2/cloudclient` folder and copy the path.
2. Open a command prompt or terminal and navigate to the copied path.
```
cd C:\User\Desktop\FrostClient-main\<version>\cloudclient
```
3. Build the project.
```
gradlew build
```
The new build will be located at
```
C:\User\Desktop\FrostClient-main\<version>\cloudclient\build\libs
```
4. Copy the `.jar` file to your mods folder and launch Forge with the corresponding Minecraft version.

## Contributions
Contributions are welcome! Feel free to fork this project, make changes, and submit a pull request to the `develop` branch. Porting the 1.7.10 and 1.12.2 versions to match the 1.8.9 FrostClient codebase is one of the biggest ways to help right now — see the [Version Status](#version-status) section above.

## License
This project is licensed under the GNU Lesser General Public License v3.0.

Permissions:
- Modification
- Distribution
- Private use

Conditions:
- License and copyright notice
- State changes
- Disclose source
- Same license

This project is a fork of [CloudClient](https://github.com/cloudclientdev/cloudclient) and includes code from:
- superblaubeere27 (Font Renderer) [GitHub](https://github.com/superblaubeere27)
- LaVache-FR (AnimationUtil) [GitHub](https://github.com/LaVache-FR)
- Moulberry (MotionBlur) [GitHub](https://github.com/Moulberry)
