/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;

public class NickHiderMod extends Mod {

    public NickHiderMod() {
        super(
                "NickHider",
                "Hides your nickname in game by replacing it.",
                Type.Visual
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Nickname", this, "Name", "You", 3));
    }

    public static String replaceNickname(String nick) {
        if(Frost.INSTANCE != null) {
            if (Frost.INSTANCE.modManager != null) {
                if (Frost.INSTANCE.modManager.getMod("NickHider").isToggled()) {
                    return nick.replace(
                            Frost.INSTANCE.mc.getSession().getUsername(),
                            Frost.INSTANCE.settingManager.getSettingByModAndName("NickHider", "Nickname").getText()
                    );
                }
            }
        }
        return nick;
    }
}
