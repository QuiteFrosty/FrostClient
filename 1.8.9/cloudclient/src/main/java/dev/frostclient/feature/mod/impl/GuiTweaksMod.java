/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiTweaksMod extends Mod {

    public GuiTweaksMod() {
        super(
                "Gui Tweaks",
                "Adds Tweaks to the Gui like blur and transparency.",
                Type.Tweaks
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Blur Background", this, true));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Darken Background", this, true));
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) {
        if (Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Blur Background").isCheckToggled()) {
            if (!(e.gui instanceof GuiChat)) {
                try {
                    Frost.INSTANCE.mc.entityRenderer.loadShader(
                            new ResourceLocation("shaders/post/blur.json"));
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
            if (e.gui == null) {
                if (Frost.INSTANCE.mc.entityRenderer.getShaderGroup() != null) {
                    Frost.INSTANCE.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                }
            }
        }
    }
}