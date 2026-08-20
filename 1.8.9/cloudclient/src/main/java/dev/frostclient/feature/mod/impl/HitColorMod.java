package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;

import java.awt.*;

public class HitColorMod extends Mod {
    public HitColorMod() {
        super(
                "Hit Color",
                "Changes the color of damaged entities.",
                Type.Visual
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Damage Color", this, new Color(255, 0, 0), new Color(255, 0, 0), 0, new float[]{145, 0}));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Alpha", this, 255, 80));
    }
}
