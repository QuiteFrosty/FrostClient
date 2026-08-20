/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.gui.hudeditor.impl.impl.keystrokes.keys;

import dev.frostclient.Frost;
import dev.frostclient.helpers.render.Helper2D;
import dev.frostclient.helpers.animation.Animate;
import dev.frostclient.helpers.animation.Easing;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class MouseKey {

    private Animate animate = new Animate();

    public MouseKey() {
        animate.setEase(Easing.CUBIC_IN).setMin(0).setMax(100).setSpeed(1000);
    }

    public void renderKey(int x, int y, int width, int height, boolean modern, int mouseButton, int color, int fontColor, boolean background, boolean cps) {
        boolean mouseDown;
        if(Frost.INSTANCE.mc.currentScreen == null) {
            mouseDown = Mouse.isButtonDown(mouseButton);
        }
        else {
            mouseDown = false;
        }

        animate.setReversed(mouseDown).update();

        if (modern) {
            if (background) {
                Helper2D.drawRoundedRectangle(x, y, width, height, 2, color, 0);
            }

            if (mouseDown || !animate.hasFinished()) {
                Helper2D.drawRoundedRectangle(x, y, width, height, 2, new Color(255, 255, 255, 100 - animate.getValueI()).getRGB(), 0);
            }

            Frost.INSTANCE.fontHelper.size20.drawString(
                    getCPS(mouseButton) != 0 && cps ? getCPS(mouseButton) + " CPS" : mouseButton == 0 ? "LMB" : "RMB",
                    x - Frost.INSTANCE.fontHelper.size20.getStringWidth(getCPS(mouseButton) != 0 && cps ? getCPS(mouseButton) + " CPS" : mouseButton == 0 ? "LMB" : "RMB") / 2f + width / 2f,
                    y + height / 2f - 4,
                    fontColor
            );
        }
        else {
            if (background) {
                Helper2D.drawRectangle(x, y, width, height, color);
            }

            if (mouseDown) {
                Helper2D.drawRectangle(x, y, width, height, new Color(255, 255, 255, 100 - animate.getValueI()).getRGB());
            }

            Frost.INSTANCE.mc.fontRendererObj.drawString(
                    getCPS(mouseButton) != 0 && cps ? getCPS(mouseButton) + " CPS" : mouseButton == 0 ? "LMB" : "RMB",
                    x - Minecraft.getMinecraft().fontRendererObj.getStringWidth(getCPS(mouseButton) != 0 && cps ? getCPS(mouseButton) + " CPS" : mouseButton == 0 ? "LMB" : "RMB") / 2 + width / 2,
                    y + height / 2 - 4,
                    fontColor
            );
        }
    }

    private int getCPS(int mouseButton) {
        return Frost.INSTANCE.cpsHelper.getCPS(mouseButton);
    }
}
