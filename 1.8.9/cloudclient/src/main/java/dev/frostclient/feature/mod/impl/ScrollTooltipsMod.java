/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;

public class ScrollTooltipsMod extends Mod {

    public ScrollTooltipsMod() {
        super(
                "ScrollTooltips",
                "Makes long tooltips which go offscreen, scrollable.",
                Type.Tweaks
        );
    }
}
