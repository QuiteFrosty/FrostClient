/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class FreelookMod extends Mod {

    public static boolean cameraToggled = false;
    public static float cameraYaw;
    public static float cameraPitch;

    public FreelookMod() {
        super(
                "Freelook",
                "Allows you to see a 360 view around your Player.",
                Type.Mechanic
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Keybinding", this, Keyboard.KEY_R));
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent e) {
        if(Keyboard.isKeyDown(getKey()) && !cameraToggled){
            cameraYaw = Frost.INSTANCE.mc.thePlayer.rotationYaw + 180;
            cameraPitch = Frost.INSTANCE.mc.thePlayer.rotationPitch;
            cameraToggled = true;
            Frost.INSTANCE.mc.gameSettings.thirdPersonView = 1;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e){
        if(!Keyboard.isKeyDown(getKey()) && cameraToggled) {
            cameraToggled = false;
            Frost.INSTANCE.mc.gameSettings.thirdPersonView = 0;
        }
    }

    @SubscribeEvent
    public void cameraSetup(EntityViewRenderEvent.CameraSetup e) {
        if (cameraToggled) {
            e.yaw = cameraYaw;
            e.pitch = cameraPitch;
        }
    }

    private int getKey(){
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Keybinding").getKey();
    }
}
