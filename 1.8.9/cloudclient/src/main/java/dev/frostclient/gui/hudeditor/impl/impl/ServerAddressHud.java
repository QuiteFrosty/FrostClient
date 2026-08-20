/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.gui.hudeditor.impl.impl;

import dev.frostclient.Frost;
import dev.frostclient.gui.Style;
import dev.frostclient.gui.hudeditor.HudEditor;
import dev.frostclient.gui.hudeditor.impl.HudMod;
import dev.frostclient.helpers.render.GLHelper;
import dev.frostclient.helpers.render.Helper2D;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerAddressHud extends HudMod {

    public ServerAddressHud(String name, int x, int y) {
        super(name, x, y);
        setW(100);
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
                        Frost.INSTANCE.mc.isIntegratedServerRunning() ? "Singleplayer" : Frost.INSTANCE.mc.getCurrentServerData().serverIP,
                        getX() + getW() / 2f - (Frost.INSTANCE.fontHelper.size20.getStringWidth(Frost.INSTANCE.mc.isIntegratedServerRunning() ? "Singleplayer" : Frost.INSTANCE.mc.getCurrentServerData().serverIP)) / 2f,
                        getY() + 6,
                        getColor()
                );
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), Style.getColor(50).getRGB());
                }
                Frost.INSTANCE.mc.fontRendererObj.drawString(
                        Frost.INSTANCE.mc.isIntegratedServerRunning() ? "Singleplayer" : Frost.INSTANCE.mc.getCurrentServerData().serverIP,
                        getX() + getW() / 2 - (Frost.INSTANCE.mc.fontRendererObj.getStringWidth(Frost.INSTANCE.mc.isIntegratedServerRunning() ? "Singleplayer" : Frost.INSTANCE.mc.getCurrentServerData().serverIP)) / 2,
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
                        Frost.INSTANCE.mc.isIntegratedServerRunning() ? "Singleplayer" : Frost.INSTANCE.mc.getCurrentServerData().serverIP,
                        getX() + getW() / 2f - (Frost.INSTANCE.fontHelper.size20.getStringWidth(Frost.INSTANCE.mc.isIntegratedServerRunning() ? "Singleplayer" : Frost.INSTANCE.mc.getCurrentServerData().serverIP)) / 2f,
                        getY() + 6,
                        getColor()
                );
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), 0x50000000);
                }
                Frost.INSTANCE.mc.fontRendererObj.drawString(
                        Frost.INSTANCE.mc.isIntegratedServerRunning() ? "Singleplayer" : Frost.INSTANCE.mc.getCurrentServerData().serverIP,
                        getX() + getW() / 2 - (Frost.INSTANCE.mc.fontRendererObj.getStringWidth(Frost.INSTANCE.mc.isIntegratedServerRunning() ? "Singleplayer" : Frost.INSTANCE.mc.getCurrentServerData().serverIP)) / 2,
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
}
