package com.ootruffle.blockoverlay.utils;

import net.minecraft.client.renderer.GlStateManager;

public class ColorUtils {
   public static void setColor(float red, float green, float blue, float alpha) {
      GlStateManager.color(red, green, blue, alpha);
   }
}