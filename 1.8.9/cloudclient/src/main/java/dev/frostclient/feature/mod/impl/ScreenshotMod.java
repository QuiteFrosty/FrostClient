/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class ScreenshotMod extends Mod {

    public ScreenshotMod() {
        super(
                "Screenshot",
                "Copies a screenshot of the game window straight to your clipboard.",
                Type.Tweaks
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Keybinding", this, Keyboard.KEY_F4));
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent e) {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == getKey()) {
            copyScreenshotToClipboard();
        }
    }

    private void copyScreenshotToClipboard() {
        int width = Frost.INSTANCE.mc.displayWidth;
        int height = Frost.INSTANCE.mc.displayHeight;

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = (x + (height - y - 1) * width) * 4;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }

        Transferable transferable = new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DataFlavor.imageFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                if (!isDataFlavorSupported(flavor)) {
                    throw new UnsupportedFlavorException(flavor);
                }
                return image;
            }
        };

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(transferable, null);

        if (Frost.INSTANCE.mc.thePlayer != null) {
            Frost.INSTANCE.mc.thePlayer.addChatMessage(new ChatComponentText("Screenshot copied to clipboard!"));
        }
    }

    private int getKey() {
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Keybinding").getKey();
    }
}
