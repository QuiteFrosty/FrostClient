/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.gui.modmenu.impl.sidebar.mods.impl.type;

import dev.frostclient.Frost;
import dev.frostclient.feature.setting.Setting;
import dev.frostclient.gui.Style;
import dev.frostclient.gui.modmenu.impl.sidebar.mods.Button;
import dev.frostclient.gui.modmenu.impl.sidebar.mods.impl.Settings;
import dev.frostclient.helpers.render.Helper2D;
import dev.frostclient.helpers.MathHelper;
import dev.frostclient.helpers.animation.Animate;
import dev.frostclient.helpers.animation.Easing;

public class CheckBox extends Settings {

    private final Animate animCheckBox = new Animate();

    public CheckBox(Setting setting, Button button, int y) {
        super(setting, button, y);
        animCheckBox.setEase(Easing.CUBIC_IN_OUT).setMin(0).setMax(10).setSpeed(100);
    }

    /**
     * Renders the Checkbox Setting
     *
     * @param mouseX The current X position of the mouse
     * @param mouseY The current Y position of the mouse
     */

    @Override
    public void renderSetting(int mouseX, int mouseY) {
        boolean roundedCorners = Frost.INSTANCE.optionManager.getOptionByName("Rounded Corners").isCheckToggled();
        int color = Frost.INSTANCE.optionManager.getOptionByName("Color").getColor().getRGB();

        animCheckBox.setReversed(!setting.isCheckToggled()).update();

        Frost.INSTANCE.fontHelper.size30.drawString(
                setting.getName(),
                button.getPanel().getX() + 20,
                button.getPanel().getY() + button.getPanel().getH() + getY() + 6,
                color
        );

        Helper2D.drawRoundedRectangle(
                button.getPanel().getX() + button.getPanel().getW() - 40,
                button.getPanel().getY() + button.getPanel().getH() + getY() + 2,
                20, 20, 2,
                Style.getColor(setting.isCheckToggled() ? 80 : 50).getRGB(),
                roundedCorners ? 0 : -1
        );

        Helper2D.drawPicture(
                button.getPanel().getX() + button.getPanel().getW() - 30 - animCheckBox.getValueI(),
                button.getPanel().getY() + button.getPanel().getH() + 12 + getY() - animCheckBox.getValueI(),
                animCheckBox.getValueI() * 2,
                animCheckBox.getValueI() * 2,
                color,
                "icon/check.png"
        );
    }

    /**
     * Toggles the state of the setting if it is pressed
     *
     * @param mouseX The current X position of the mouse
     * @param mouseY The current Y position of the mouse
     * @param mouseButton The current mouse button which is pressed
     */

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0){
            if (MathHelper.withinBox(
                    button.getPanel().getX() + button.getPanel().getW() - 40,
                    button.getPanel().getY() + button.getPanel().getH() + 2 + getY(),
                    20, 20, mouseX, mouseY)
            ) {
                setting.setCheckToggled(!setting.isCheckToggled());
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }
}
