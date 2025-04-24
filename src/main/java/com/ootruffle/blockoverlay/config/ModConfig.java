package com.ootruffle.blockoverlay.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.core.OneColor;

public class ModConfig extends Config {

    @Info(
            text = "This configuration is powered by OneConfig. Customize the mod settings below.",
            size = 2,
            type = InfoType.INFO
    )
    public static boolean ignored;

    @Color(
            name = "Overlay Color",
            size = 1 // Single-column layout
    )
    public static OneColor overlayColor = new OneColor(255, 255, 255);

    @Slider(
            name = "Overlay Thickness",
            min = 1,
            max = 10,
            step = 1
    )
    public static int overlayThickness = 2;

    @Slider(
            name = "Overlay Transparency",
            min = 0,
            max = 255,
            step = 1
    )
    public static int overlayTransparency = 255;

    @Switch(
            name = "Hide Plants in Overlay",
            size = 1 // Optional: Single-column layout
    )
    public static boolean hidePlants = false;

    @Switch(
            name = "Enable Barriers",
            size = 1 // Optional: Single-column layout
    )
    public static boolean enableBarriers = false;

    @Dropdown(
            name = "Render Side",
            options = {"FULL", "SIDE", "HIDDEN"}
    )
    public static String renderSide = "FULL"; // Default value is "FULL"

    public static boolean outlineEnabled;

    public ModConfig() {
        super(new Mod("Block Overlay", ModType.UTIL_QOL, "/assets/blockoverlay/icon.png"), "blockoverlay.json");
        initialize();
    }
}