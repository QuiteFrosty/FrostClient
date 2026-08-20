/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.config;

import com.google.gson.Gson;
import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.option.Option;
import dev.frostclient.gui.Style;
import dev.frostclient.helpers.OSHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ConfigSaver {

    /**
     * Copies a config.json left behind by the pre-rebrand ".minecraft/cloud"
     * directory into the new ".minecraft/frostclient" location, so upgrading
     * users don't lose their settings. No-op if there's nothing to migrate
     * or a frostclient config already exists.
     */

    public static void migrateLegacyConfig() {
        if (configExists()) {
            return;
        }

        File legacyFile = new File(OSHelper.getLegacyCloudDirectory() + "config.json");
        if (!legacyFile.exists()) {
            return;
        }

        try {
            createDir();
            Files.copy(legacyFile.toPath(), new File(OSHelper.getFrostDirectory() + "config.json").toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates and saves a configuration in .minecraft/frostclient/config.json
     */

    public static void saveConfig() throws IOException {
        createDir();

        FileWriter writer = new FileWriter(OSHelper.getFrostDirectory() + "config.json");

        Config config = new Config();

        for (Mod mod : Frost.INSTANCE.modManager.getMods()) {
            ModConfig modConfig = new ModConfig(
                    mod.getName(),
                    mod.isToggled(),
                    Frost.INSTANCE.settingManager.getSettingsByMod(mod),
                    Frost.INSTANCE.hudEditor.getHudMod(mod.getName()) != null ?
                            new int[] { Frost.INSTANCE.hudEditor.getHudMod(mod.getName()).getX(), Frost.INSTANCE.hudEditor.getHudMod(mod.getName()).getY() } :
                            new int[] { 0, 0 },
                    Frost.INSTANCE.hudEditor.getHudMod(mod.getName()) != null ?
                            Frost.INSTANCE.hudEditor.getHudMod(mod.getName()).getSize() : 1
            );
            config.addConfig(modConfig);
        }

        for(Option option : Frost.INSTANCE.optionManager.getOptions()){
            config.addConfigOption(option);
        }

        config.setDarkMode(Style.isDarkMode());
        config.setSnapping(Style.isSnapping());

        String json = new Gson().toJson(config);
        writer.write(json);
        writer.close();
    }

    /**
     * Creates the .minecraft/frostclient directory if it cannot be found
     */

    private static void createDir() {
        File file = new File(OSHelper.getFrostDirectory());
        if (!file.exists()) {
            try {
                Files.createDirectory(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        createFile();
    }

    /**
     * Creates the .minecraft/frostclient/config.json file if it cannot be found
     */

    private static void createFile() {
        File file = new File(OSHelper.getFrostDirectory() + "config.json");
        if (!file.exists()) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Checks if the config .minecraft/frostclient/config.json file exists
     *
     * @return Config can be found
     */

    public static boolean configExists() {
        File file = new File(OSHelper.getFrostDirectory() + "config.json");
        return file.exists();
    }
}