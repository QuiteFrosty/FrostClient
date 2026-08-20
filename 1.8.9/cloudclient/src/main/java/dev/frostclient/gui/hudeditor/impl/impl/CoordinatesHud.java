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
import dev.frostclient.helpers.MathHelper;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CoordinatesHud extends HudMod {

    public CoordinatesHud(String name, int x, int y) {
        super(name, x, y);
        setW(120);
        setH(60);
    }

    @Override
    public void renderMod(int mouseX, int mouseY) {
        GLHelper.startScale(getX(), getY(), getSize());
        if (Frost.INSTANCE.modManager.getMod(getName()).isToggled()) {
            if (isModern()) {
                if (isBackground()) {
                    Helper2D.drawRoundedRectangle(getX(), getY(), getW(), getH(), 2, Style.getColor(50).getRGB(), 0);
                }
                Frost.INSTANCE.fontHelper.size20.drawString("X: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posX, 1), getX() + 5, getY() + 5, getColor());
                Frost.INSTANCE.fontHelper.size20.drawString("Y: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posY, 1), getX() + 5, getY() + 5 + 14, getColor());
                Frost.INSTANCE.fontHelper.size20.drawString("Z: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posZ, 1), getX() + 5, getY() + 5 + 28, getColor());
                Frost.INSTANCE.fontHelper.size20.drawString(isBiome() ? "Biome: " + getBiomeName() : "", getX() + 5, getY() + 5 + 42, getColor());
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), Style.getColor(50).getRGB());
                }
                Frost.INSTANCE.mc.fontRendererObj.drawString("X: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posX, 1), getX() + 5, getY() + 5, getColor());
                Frost.INSTANCE.mc.fontRendererObj.drawString("Y: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posY, 1), getX() + 5, getY() + 5 + 14, getColor());
                Frost.INSTANCE.mc.fontRendererObj.drawString("Z: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posZ, 1), getX() + 5, getY() + 5 + 28, getColor());
                Frost.INSTANCE.mc.fontRendererObj.drawString(isBiome() ? "Biome: " + getBiomeName() : "", getX() + 5, getY() + 5 + 42, getColor());
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
                Frost.INSTANCE.fontHelper.size20.drawString("X: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posX, 1), getX() + 5, getY() + 5, getColor());
                Frost.INSTANCE.fontHelper.size20.drawString("Y: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posY, 1), getX() + 5, getY() + 5 + 14, getColor());
                Frost.INSTANCE.fontHelper.size20.drawString("Z: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posZ, 1), getX() + 5, getY() + 5 + 28, getColor());
                Frost.INSTANCE.fontHelper.size20.drawString(isBiome() ? "Biome: " + getBiomeName() : "", getX() + 5, getY() + 5 + 42, getColor());
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), 0x50000000);
                }
                Frost.INSTANCE.mc.fontRendererObj.drawString("X: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posX, 1), getX() + 5, getY() + 5, getColor());
                Frost.INSTANCE.mc.fontRendererObj.drawString("Y: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posY, 1), getX() + 5, getY() + 5 + 14, getColor());
                Frost.INSTANCE.mc.fontRendererObj.drawString("Z: " + MathHelper.round(Frost.INSTANCE.mc.thePlayer.posZ, 1), getX() + 5, getY() + 5 + 28, getColor());
                Frost.INSTANCE.mc.fontRendererObj.drawString(isBiome() ? "Biome: " + getBiomeName() : "", getX() + 5, getY() + 5 + 42, getColor());
            }
        }
        GLHelper.endScale();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (isBiome()) {
            setW(120);
            setH(60);
        } else {
            setW(70);
            setH(45);
        }
    }

    private int getColor() {
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Font Color").getColor().getRGB();
    }

    private boolean isModern() {
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Mode").getCurrentMode().equalsIgnoreCase("Modern");
    }

    private boolean isBackground() {
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Background").isCheckToggled();
    }

    private boolean isBiome() {
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Biome").isCheckToggled();
    }

    private String getBiomeName() {
        return Frost.INSTANCE.mc.theWorld.getBiomeGenForCoords(new BlockPos(Frost.INSTANCE.mc.thePlayer.posX, Frost.INSTANCE.mc.thePlayer.posY, Frost.INSTANCE.mc.thePlayer.posZ)).biomeName;
    }
}
