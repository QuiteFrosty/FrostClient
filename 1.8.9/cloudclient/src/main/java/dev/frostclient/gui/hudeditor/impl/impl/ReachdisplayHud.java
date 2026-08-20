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
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ReachdisplayHud extends HudMod {

    private double range;

    public ReachdisplayHud(String name, int x, int y) {
        super(name, x, y);
        setW(80);
        setH(20);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        if (Frost.INSTANCE.mc.objectMouseOver != null &&
                Frost.INSTANCE.mc.objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.ENTITY)
        ) {
            Vec3 vec3 = Frost.INSTANCE.mc.getRenderViewEntity().getPositionEyes(1);
            range = Frost.INSTANCE.mc.objectMouseOver.hitVec.distanceTo(vec3);
        }
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
                        MathHelper.round(range, 2) + " Blocks",
                        getX() + getW() / 2f - (Frost.INSTANCE.fontHelper.size20.getStringWidth(MathHelper.round(range, 2) + " Blocks")) / 2f,
                        getY() + 6,
                        getColor()
                );
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), Style.getColor(50).getRGB());
                }
                Frost.INSTANCE.mc.fontRendererObj.drawString(
                        MathHelper.round(range, 2) + " Blocks",
                        getX() + getW() / 2 - (Frost.INSTANCE.mc.fontRendererObj.getStringWidth(MathHelper.round(range, 2) + " Blocks")) / 2,
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
                        MathHelper.round(range, 2) + " Blocks",
                        getX() + getW() / 2f - (Frost.INSTANCE.fontHelper.size20.getStringWidth(MathHelper.round(range, 2) + " Blocks")) / 2f,
                        getY() + 6,
                        getColor()
                );
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), 0x50000000);
                }
                Frost.INSTANCE.mc.fontRendererObj.drawString(
                        MathHelper.round(range, 2) + " Blocks",
                        getX() + getW() / 2 - (Frost.INSTANCE.mc.fontRendererObj.getStringWidth(MathHelper.round(range, 2) + " Blocks")) / 2,
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
