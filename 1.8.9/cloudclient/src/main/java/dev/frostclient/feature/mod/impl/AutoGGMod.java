/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoGGMod extends Mod {

    public AutoGGMod() {
        super(
                "AutoGG",
                "Automatically sends a chat message and/or respawns as soon as the death screen opens.",
                Type.Mechanic
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Send Message", this, true));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Message", this, "gg", "gg", 2));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Auto Respawn", this, true));
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) {
        if (!(e.gui instanceof GuiGameOver) || Frost.INSTANCE.mc.thePlayer == null) {
            return;
        }

        if (Frost.INSTANCE.settingManager.getSettingByModAndName("AutoGG", "Send Message").isCheckToggled()) {
            String message = Frost.INSTANCE.settingManager.getSettingByModAndName("AutoGG", "Message").getText();
            if (message != null && !message.isEmpty()) {
                Frost.INSTANCE.mc.thePlayer.sendChatMessage(message);
            }
        }

        if (Frost.INSTANCE.settingManager.getSettingByModAndName("AutoGG", "Auto Respawn").isCheckToggled()) {
            Frost.INSTANCE.mc.thePlayer.respawnPlayer();
        }
    }
}
