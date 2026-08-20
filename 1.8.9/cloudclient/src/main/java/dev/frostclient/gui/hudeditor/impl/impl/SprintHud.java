/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.gui.hudeditor.impl.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.impl.ToggleSprintMod;
import dev.frostclient.gui.Style;
import dev.frostclient.gui.hudeditor.HudEditor;
import dev.frostclient.gui.hudeditor.impl.HudMod;
import dev.frostclient.helpers.render.GLHelper;
import dev.frostclient.helpers.render.Helper2D;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SprintHud extends HudMod {

    public SprintHud(String name, int x, int y) {
        super(name, x, y);
        setW(90);
        setH(20);
    }

    @Override
    public void renderMod(int mouseX, int mouseY) {
        GLHelper.startScale(getX(), getY(), getSize());
        if (Frost.INSTANCE.modManager.getMod(getName()).isToggled()) {
            if (isModern()) {
                if (isBackground()) {
                    Helper2D.drawRoundedRectangle(getX(), getY(), getW(), getH(), 2, Style.getColor(50).getRGB(), 0);
                }
                Frost.INSTANCE.fontHelper.size20.drawString(
                        "Sprint: " + (isSprinting() ? "Toggled" : "Vanilla"),
                        getX() + getW() / 2f - Frost.INSTANCE.fontHelper.size20.getStringWidth("Sprint: " + (isSprinting() ? "Toggled" : "Vanilla")) / 2f,
                        getY() + 6,
                        getColor()
                );
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), Style.getColor(50).getRGB());
                }
                Frost.INSTANCE.mc.fontRendererObj.drawString(
                        "Sprint: " + (isSprinting() ? "Toggled" : "Vanilla"),
                        getX() + getW() / 2 - Frost.INSTANCE.mc.fontRendererObj.getStringWidth("Sprint: " + (isSprinting() ? "Toggled" : "Vanilla")) / 2,
                        getY() + 6,
                        getColor()
                );
            }
            super.renderMod(mouseX, mouseY);
        }
        GLHelper.endScale();
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent.Pre.Text e) {
        GLHelper.startScale(getX(), getY(), getSize());
        if (Frost.INSTANCE.modManager.getMod(getName()).isToggled() && !(Frost.INSTANCE.mc.currentScreen instanceof HudEditor)) {
            if (isModern()) {
                if (isBackground()) {
                    Helper2D.drawRoundedRectangle(getX(), getY(), getW(), getH(), 2, 0x50000000, 0);
                }
                Frost.INSTANCE.fontHelper.size20.drawString(
                        "Sprint: " + (isSprinting() ? "Toggled" : "Vanilla"),
                        getX() + getW() / 2f - Frost.INSTANCE.fontHelper.size20.getStringWidth("Sprint: " + (isSprinting() ? "Toggled" : "Vanilla")) / 2f,
                        getY() + 6,
                        getColor()
                );
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), 0x50000000);
                }
                Frost.INSTANCE.mc.fontRendererObj.drawString(
                        "Sprint: " + (isSprinting() ? "Toggled" : "Vanilla"),
                        getX() + getW() / 2 - Frost.INSTANCE.mc.fontRendererObj.getStringWidth("Sprint: " + (isSprinting() ? "Toggled" : "Vanilla")) / 2,
                        getY() + 6,
                        getColor()
                );
            }
        }
        GLHelper.endScale();
    }

    public int getColor() {
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Font Color").getColor().getRGB();
    }

    private boolean isModern() {
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Mode").getCurrentMode().equalsIgnoreCase("Modern");
    }

    private boolean isBackground() {
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Background").isCheckToggled();
    }

    private boolean isSprinting() {
        return ToggleSprintMod.isSprinting();
    }
}
