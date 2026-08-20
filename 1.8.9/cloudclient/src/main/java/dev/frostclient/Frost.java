/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient;

import dev.frostclient.config.ConfigLoader;
import dev.frostclient.config.ConfigSaver;
import dev.frostclient.feature.mod.ModManager;
import dev.frostclient.feature.option.OptionManager;
import dev.frostclient.feature.setting.SettingManager;
import dev.frostclient.gui.hudeditor.HudEditor;
import dev.frostclient.helpers.CpsHelper;
import dev.frostclient.helpers.MessageHelper;
import dev.frostclient.helpers.font.FontHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.opengl.Display;

import java.io.FileNotFoundException;
import java.io.IOException;

@Mod(
        modid = Frost.modID,
        name = Frost.modName,
        version = Frost.modVersion,
        acceptedMinecraftVersions = "[1.8.9]"
)
public class Frost {

    @Mod.Instance()
    public static Frost INSTANCE;

    public static final String modID = "frostclient";
    public static final String modName = "Frost";
    public static final String modVersion = "1.4.1 [1.8.9]";

    public Minecraft mc = Minecraft.getMinecraft();

    public ModManager modManager;
    public SettingManager settingManager;
    public HudEditor hudEditor;
    public OptionManager optionManager;
    public FontHelper fontHelper;
    public CpsHelper cpsHelper;
    public MessageHelper messageHelper;

    /**
     * Initializes the client
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle(Frost.modName + " Client " + Frost.modVersion);
        registerEvents(
                cpsHelper = new CpsHelper(),
                settingManager = new SettingManager(),
                modManager = new ModManager(),
                optionManager = new OptionManager(),
                hudEditor = new HudEditor(),
                fontHelper = new FontHelper(),
                messageHelper = new MessageHelper()
        );

        try {
            if (!ConfigSaver.configExists()) {
                ConfigSaver.saveConfig();
            }
            ConfigLoader.loadConfig();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        fontHelper.init();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ConfigSaver.saveConfig();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }));
    }

    private void registerEvents(Object... events) {
        for (Object event : events) {
            MinecraftForge.EVENT_BUS.register(event);
        }
    }
}