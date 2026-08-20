package dev.frostclient.helpers;

import dev.frostclient.Frost;
import net.minecraft.client.gui.ScaledResolution;

public class ResolutionHelper {

    private static ScaledResolution scaledResolution;

    public static int getHeight() {
        scaledResolution = new ScaledResolution(Frost.INSTANCE.mc);
        return scaledResolution.getScaledHeight();
    }

    public static int getWidth() {
        scaledResolution = new ScaledResolution(Frost.INSTANCE.mc);
        return scaledResolution.getScaledWidth();
    }

    public static int getFactor() {
        scaledResolution = new ScaledResolution(Frost.INSTANCE.mc);
        return scaledResolution.getScaleFactor();
    }
}