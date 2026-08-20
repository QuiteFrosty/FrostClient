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
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FullbrightMod extends Mod {

    private float oldGamma;

    public FullbrightMod() {
        super(
                "Fullbright",
                "Changes the Gamma of the game to a given value.",
                Type.Visual
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Brightness", this, 100, 10));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        oldGamma = Frost.INSTANCE.mc.gameSettings.gammaSetting;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Frost.INSTANCE.mc.gameSettings.gammaSetting = oldGamma;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        Frost.INSTANCE.mc.gameSettings.gammaSetting =
                Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Brightness").getCurrentNumber();
    }
}
