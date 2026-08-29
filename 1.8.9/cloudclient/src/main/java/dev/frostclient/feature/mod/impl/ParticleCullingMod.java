/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;

public class ParticleCullingMod extends Mod {

    public ParticleCullingMod() {
        super(
                "ParticleCulling",
                "Skips rendering particles outside the camera's field of view to improve FPS in particle-heavy fights.",
                Type.Visual
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Cull Angle", this, 150, 70));
    }

    public static float getCullAngle() {
        return Frost.INSTANCE.settingManager.getSettingByModAndName("ParticleCulling", "Cull Angle").getCurrentNumber();
    }
}
