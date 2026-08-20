/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.mixins;

import dev.frostclient.Frost;
import dev.frostclient.gui.titlescreen.TitleScreen;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin {

    /**
     * Loads the custom Title Screen in dev.frostclient.gui.titlescreen
     */

    @Inject(method = "initGui", at = @At("HEAD"))
    public void initGui(CallbackInfo ci) {
        if (!Frost.INSTANCE.optionManager.getOptionByName("Disable Custom Title Screen").isCheckToggled()) {
            Frost.INSTANCE.mc.displayGuiScreen(new TitleScreen());
        }
    }
}
