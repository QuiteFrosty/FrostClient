package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;

import java.awt.*;

public class NameTagMod extends Mod {

    public NameTagMod() {
        super(
                "NameTag",
                "Adds tweaks to NameTags.",
                Type.Tweaks
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("NameTag in 3rd Person", this, true));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Opacity", this, 255, 64));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Size", this, 3, 1));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Y Position", this, 5, 2.5f));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Disable Player NameTags", this, false));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Font Color", this, new Color(255, 255, 255), new Color(255, 0, 0), 0, new float[]{0, 0}));
    }
}
