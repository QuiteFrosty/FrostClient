package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;

public class ScoreboardMod extends Mod {

    public ScoreboardMod() {
        super(
                "Scoreboard",
                "Adds Tweaks to the Scoreboard",
                Type.Tweaks
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Background", this, true));
        Frost.INSTANCE.settingManager.addSetting(new Setting("Remove Red Numbers", this, false));
    }
}
