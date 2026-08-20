/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class SnaplookMod extends Mod {
    private boolean cameraToggled = false;

    public SnaplookMod() {
        super(
                "Snaplook",
                "Allows you to see you in 3rd person, by only holding a button.",
                Type.Mechanic
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Keybinding", this, Keyboard.KEY_F));
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent e) {
        if(Keyboard.isKeyDown(getKey()) && !cameraToggled){
            cameraToggled = true;
            Frost.INSTANCE.mc.gameSettings.thirdPersonView = 1;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e){
        if(!Keyboard.isKeyDown(getKey()) && cameraToggled){
            cameraToggled = false;
            Frost.INSTANCE.mc.gameSettings.thirdPersonView = 0;
        }
    }

    private int getKey(){
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Keybinding").getKey();
    }
}
