package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;

import java.awt.*;

public class DirectionMod extends Mod {

    public DirectionMod() {
        super(
                "Direction",
                "Shows you the direction you are facing on the HUD.",
                Type.Hud
        );

        String[] mode = {"Modern", "Legacy"};
        Frost.INSTANCE.settingManager.addSetting(new Setting("Mode", this, "Modern", 0, mode));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Background", this, true));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Font Color", this, new Color(255, 255, 255), new Color(255, 0, 0), 0, new float[]{0, 0}));
    }
}
