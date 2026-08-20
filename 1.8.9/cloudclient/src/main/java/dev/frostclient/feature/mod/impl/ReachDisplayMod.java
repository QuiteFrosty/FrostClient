/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;

import java.awt.*;

public class ReachDisplayMod extends Mod {

    public ReachDisplayMod() {
        super(
                "ReachDisplay",
                "Shows you the distance from the player and the entity you attacked.",
                Type.Hud
        );

        String[] mode = {"Modern", "Legacy"};
        Frost.INSTANCE.settingManager.addSetting(new Setting("Mode", this, "Modern", 0, mode));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Background", this, true));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Font Color", this, new Color(255, 255, 255), new Color(255, 0, 0), 0, new float[]{0, 0}));
    }
}
