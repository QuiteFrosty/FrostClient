/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.helpers;

import dev.frostclient.Frost;

import java.io.File;

public class OSHelper {

    private static final String currentOS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() { return (currentOS.contains("windows")); }
    public static boolean isMac() { return (currentOS.contains("mac")); }
    public static boolean isLinux() { return (currentOS.contains("linux")); }

    /**
     * Returns the Location of the .minecraft directory
     *
     * @return Directory
     */

    public static String getMinecraftDirectory() {
        return Frost.INSTANCE.mc.mcDataDir.getAbsolutePath() + File.separator;
    }

    public static String getFrostDirectory() {
        return getMinecraftDirectory() + "frostclient" + File.separator;
    }
}
