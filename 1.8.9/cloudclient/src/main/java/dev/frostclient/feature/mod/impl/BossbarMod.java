package dev.frostclient.feature.mod.impl;

import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;

public class BossbarMod extends Mod {
    public BossbarMod() {
        super(
                "Bossbar",
                "Adds tweaks to the Bossbar",
                Type.Tweaks
        );
    }
}
