package dev.frostclient.gui.modmenu.impl.sidebar.options.type;

import dev.frostclient.Frost;
import dev.frostclient.feature.option.Option;
import dev.frostclient.feature.setting.Setting;
import dev.frostclient.gui.Style;
import dev.frostclient.gui.modmenu.impl.Panel;
import dev.frostclient.gui.modmenu.impl.sidebar.mods.Button;
import dev.frostclient.gui.modmenu.impl.sidebar.mods.impl.Settings;
import dev.frostclient.gui.modmenu.impl.sidebar.options.Options;
import dev.frostclient.helpers.MathHelper;
import dev.frostclient.helpers.render.GLHelper;
import dev.frostclient.helpers.render.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class TextBox extends Options {

    public TextBox(Option option, Panel panel, int y) {
        super(option, panel, y);
    }

    private boolean focused;
    private boolean allSelected;

    private int x, y, w, h;

    @Override
    public void renderOption(int mouseX, int mouseY) {
        boolean roundedCorners = Frost.INSTANCE.optionManager.getOptionByName("Rounded Corners").isCheckToggled();
        int color = Frost.INSTANCE.optionManager.getOptionByName("Color").getColor().getRGB();

        x = panel.getX() + panel.getW() - 150 - 11;
        y = panel.getY() + panel.getH() + getY() + 2;
        w = 150;
        h = 20;

        Frost.INSTANCE.fontHelper.size30.drawString(
                option.getName(),
                panel.getX() + 20,
                panel.getY() + panel.getH() + getY() + 6,
                color
        );

        int offset = getH() / 2 - 10;

        GLHelper.startScissor(x, y, w, h);
        Helper2D.drawRoundedRectangle(x, y, w, h, 2, Style.getColor(isFocused() ? 50 : 30).getRGB(), roundedCorners ? 0 : -1);
        if (option.getText().equals("")) {
            Frost.INSTANCE.fontHelper.size20.drawString(option.getPlaceholderText(), x + offset + 5, y + offset + 4, 0x50ffffff);
        } else {
            Frost.INSTANCE.fontHelper.size20.drawString(option.getText(), x + offset + 5, y + offset + 4, -1);
            Helper2D.drawRectangle(x + offset + 5 + Frost.INSTANCE.fontHelper.size20.getStringWidth(option.getText().substring(0, option.getCursorPosition())), y + offset + 3, 1, 10, 0x80ffffff);
            if (allSelected) {
                Helper2D.drawRectangle(x + offset + 2, y + offset + 1, Frost.INSTANCE.fontHelper.size20.getStringWidth(option.getText()) + 4, 14, 0x503030ff);
            }
        }
        GLHelper.endScissor();
    }

    public void keyTyped(char typedChar, int keyCode) {
        if (!isFocused())
            return;

        switch (keyCode) {
            case Keyboard.KEY_BACK:
                removeText();
                break;
            case Keyboard.KEY_RIGHT:
                moveCursor(1);
                break;
            case Keyboard.KEY_LEFT:
                moveCursor(-1);
                break;
            case Keyboard.KEY_A:
                if (isCtrlKeyDown()) {
                    allSelected = true;
                    option.setCursorPosition(option.getText().length());
                }

            default:
                if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                    typeText(Character.toString(typedChar));
                }
        }
    }

    public static boolean isCtrlKeyDown() {
        return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
    }

    public void typeText(String currentText) {
        if (allSelected) {
            option.setCursorPosition(0);
            option.setText("");
        }
        StringBuilder builder = new StringBuilder(option.getText());
        builder.insert(option.getCursorPosition(), currentText);
        option.setText(builder.toString());
        moveCursor(1);
    }

    public void removeText() {
        if (option.getText().length() > 0) {
            if (allSelected) {
                allSelected = false;
                option.setCursorPosition(0);
                option.setText("");
            } else {
                moveCursor(-1);
                StringBuilder builder = new StringBuilder(option.getText());
                builder.deleteCharAt(option.getCursorPosition());
                option.setText(builder.toString());
            }
        }
    }

    public void moveCursor(int direction) {
        allSelected = false;
        if (direction < 0) {
            if (option.getCursorPosition() > 0) {
                option.setCursorPosition(option.getCursorPosition() - 1);
            }
        } else if (direction > 0) {
            if (option.getCursorPosition() < option.getText().length()) {
                option.setCursorPosition(option.getCursorPosition() + 1);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(isHovered(mouseX, mouseY)) {
            setFocused(true);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(!isHovered(mouseX, mouseY)) {
            setFocused(false);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return MathHelper.withinBox(x, y, w, h, mouseX, mouseY);
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isAllSelected() {
        return allSelected;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }
}
