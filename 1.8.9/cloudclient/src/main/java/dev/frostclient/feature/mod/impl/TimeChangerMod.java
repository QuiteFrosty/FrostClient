package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;

public class TimeChangerMod extends Mod {
    public TimeChangerMod() {
        super(
                "TimeChanger",
                "Changes the time of the current World visually.",
                Type.Visual
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Offset", this, 12000, 0));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Speed", this, 50, 1));
    }
}
