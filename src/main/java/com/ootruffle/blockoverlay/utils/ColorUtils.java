package com.ootruffle.blockoverlay.utils;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;

public class ColorUtils {
   public ColorUtils() {
   }

   public static void color(int rgb) {
      float red = (float)(rgb >> 16 & 255) / 255.0F;
      float green = (float)(rgb >> 8 & 255) / 255.0F;
      float blue = (float)(rgb & 255) / 255.0F;
      float alpha = (float)(rgb >> 24 & 255) / 255.0F;
      GlStateManager.func_179131_c(red, green, blue, alpha);
   }

   public static void color(int rgb, double alpha) {
      color(setAlpha(rgb, alpha));
   }

   public static int setAlpha(int rgb, double alpha) {
      return rgb & 16777215 | (int)(alpha * 255.0) << 24;
   }

   public static int getChroma(double step) {
      double time = (double)System.currentTimeMillis() % (18000.0 / step) / (18000.0 / step);
      return Color.getHSBColor((float)time, 1.0F, 1.0F).getRGB();
   }

   public static int interpolate(int i, int i1, double percent) {
      Color color = new Color(i, true);
      Color color1 = new Color(i1, true);
      double percent1 = 1.0 - percent;
      int red = (int)((double)color.getRed() * percent + (double)color1.getRed() * percent1);
      int green = (int)((double)color.getGreen() * percent + (double)color1.getGreen() * percent1);
      int blue = (int)((double)color.getBlue() * percent + (double)color1.getBlue() * percent1);
      int alpha = (int)((double)color.getAlpha() * percent + (double)color1.getAlpha() * percent1);
      return (new Color(red, green, blue, alpha)).getRGB();
   }
}
