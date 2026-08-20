/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ToggleSneakMod extends Mod {

    private static boolean toggled = false;

    public ToggleSneakMod() {
        super(
                "ToggleSneak",
                "Allows you to toggle the Sneak button instead of holding it.",
                Type.Mechanic
        );
        Frost.INSTANCE.settingManager.addSetting(new Setting("Keybinding", this, Keyboard.KEY_LSHIFT));

        String[] mode = {"Modern", "Legacy"};
        Frost.INSTANCE.settingManager.addSetting(new Setting("Mode", this, "Modern", 0, mode));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Background", this, true));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Font Color", this, new Color(255, 255, 255), new Color(255, 0, 0), 0, new float[]{0, 0}));
    }

    public static boolean isSneaking() {
        return toggled;
    }

    @Override
    public void onDisable(){
        super.onDisable();
        KeyBinding.setKeyBindState(Frost.INSTANCE.mc.gameSettings.keyBindSneak.getKeyCode(), false);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if (toggled) {
            if (!(Frost.INSTANCE.mc.currentScreen instanceof GuiContainer)) {
                KeyBinding.setKeyBindState(Frost.INSTANCE.mc.gameSettings.keyBindSneak.getKeyCode(), true);
            }
        }
        else {
            KeyBinding.setKeyBindState(Frost.INSTANCE.mc.gameSettings.keyBindSneak.getKeyCode(), false);
        }
    }

    @SubscribeEvent
    public void key(InputEvent.KeyInputEvent e) {
        if(Keyboard.isKeyDown(getKey())){
            toggled = !toggled;
        }
    }

    private int getKey(){
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Keybinding").getKey();
    }
}
