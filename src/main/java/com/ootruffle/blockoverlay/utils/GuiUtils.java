package com.ootruffle.blockoverlay.utils;

import java.awt.Color;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class GuiUtils {
   private static final Tessellator TESSELLATOR = Tessellator.func_178181_a();
   private static final WorldRenderer WORLD_RENDERER;

   public GuiUtils() {
   }

   public static void drawRect(double left, double top, double right, double bottom, int color) {
      ColorUtils.color(color);
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      WORLD_RENDERER.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      WORLD_RENDERER.func_181662_b(left, bottom, 0.0).func_181675_d();
      WORLD_RENDERER.func_181662_b(right, bottom, 0.0).func_181675_d();
      WORLD_RENDERER.func_181662_b(right, top, 0.0).func_181675_d();
      WORLD_RENDERER.func_181662_b(left, top, 0.0).func_181675_d();
      TESSELLATOR.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
      float startRed = (float)(startColor >> 16 & 255) / 255.0F;
      float startGreen = (float)(startColor >> 8 & 255) / 255.0F;
      float startBlue = (float)(startColor & 255) / 255.0F;
      float startAlpha = (float)(startColor >> 24 & 255) / 255.0F;
      float endRed = (float)(endColor >> 16 & 255) / 255.0F;
      float endGreen = (float)(endColor >> 8 & 255) / 255.0F;
      float endBlue = (float)(endColor & 255) / 255.0F;
      float endAlpha = (float)(endColor >> 24 & 255) / 255.0F;
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179103_j(7425);
      WORLD_RENDERER.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      WORLD_RENDERER.func_181662_b((double)right, (double)top, 0.0).func_181666_a(endRed, endGreen, endBlue, endAlpha).func_181675_d();
      WORLD_RENDERER.func_181662_b((double)left, (double)top, 0.0).func_181666_a(startRed, startGreen, startBlue, startAlpha).func_181675_d();
      WORLD_RENDERER.func_181662_b((double)left, (double)bottom, 0.0).func_181666_a(startRed, startGreen, startBlue, startAlpha).func_181675_d();
      WORLD_RENDERER.func_181662_b((double)right, (double)bottom, 0.0).func_181666_a(endRed, endGreen, endBlue, endAlpha).func_181675_d();
      TESSELLATOR.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
   }

   public static void drawHollowRect(double left, double top, double right, double bottom, double thickness, int color) {
      drawRect(left, top, left + thickness, bottom, color);
      drawRect(left + thickness, top, right - thickness, top + thickness, color);
      drawRect(right - thickness, top, right, bottom, color);
      drawRect(left + thickness, bottom - thickness, right - thickness, bottom, color);
   }

   public static void drawTexturedRect(double x, double y, double width, double height) {
      double d = 1.0 / width;
      double d1 = 1.0 / height;
      WORLD_RENDERER.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      WORLD_RENDERER.func_181662_b(x, y + height, 0.0).func_181673_a(0.0, height * d1).func_181675_d();
      WORLD_RENDERER.func_181662_b(x + width, y + height, 0.0).func_181673_a(width * d, height * d1).func_181675_d();
      WORLD_RENDERER.func_181662_b(x + width, y, 0.0).func_181673_a(width * d, 0.0).func_181675_d();
      WORLD_RENDERER.func_181662_b(x, y, 0.0).func_181673_a(0.0, 0.0).func_181675_d();
      TESSELLATOR.func_78381_a();
   }

   public static void drawTooltip(Minecraft mc, List<String> textLines, int mouseX, int mouseY) {
      if (!textLines.isEmpty()) {
         FontRenderer fontRenderer = mc.field_71466_p;
         ScaledResolution resolution = new ScaledResolution(mc);
         int maxTextWidth = 0;

         for(String text : textLines) {
            int textWidth = fontRenderer.func_78256_a(text);
            if (textWidth > maxTextWidth) {
               maxTextWidth = textWidth;
            }
         }

         int tooltipX = mouseX + 12;
         if (tooltipX + maxTextWidth + 4 > resolution.func_78326_a()) {
            tooltipX = mouseX - 16 - maxTextWidth;
         }

         int tooltipY = mouseY - 12;
         int tooltipHeight = 8;
         if (textLines.size() > 1) {
            tooltipHeight += (textLines.size() - 1) * 10;
         }

         if (tooltipY + tooltipHeight + 6 > resolution.func_78328_b()) {
            tooltipY = resolution.func_78328_b() - tooltipHeight - 6;
         }

         GL11.glPushMatrix();
         GL11.glTranslated(0.0, 0.0, 100.0);
         int backgroundColor = -1089466352;
         drawRect((double)tooltipX - 3.0, (double)tooltipY - 4.0, (double)(tooltipX + maxTextWidth) + 3.0, (double)tooltipY - 3.0, -1089466352);
         drawRect((double)tooltipX - 3.0, (double)(tooltipY + tooltipHeight) + 3.0, (double)(tooltipX + maxTextWidth) + 3.0, (double)(tooltipY + tooltipHeight) + 4.0, -1089466352);
         drawRect((double)tooltipX - 3.0, (double)tooltipY - 3.0, (double)(tooltipX + maxTextWidth) + 3.0, (double)(tooltipY + tooltipHeight) + 3.0, -1089466352);
         drawRect((double)tooltipX - 4.0, (double)tooltipY - 3.0, (double)tooltipX - 3.0, (double)(tooltipY + tooltipHeight) + 3.0, -1089466352);
         drawRect((double)(tooltipX + maxTextWidth) + 3.0, (double)tooltipY - 3.0, (double)(tooltipX + maxTextWidth) + 4.0, (double)(tooltipY + tooltipHeight) + 3.0, -1089466352);
         int borderColor = Color.WHITE.getRGB();
         drawRect((double)tooltipX - 3.0, (double)tooltipY - 2.0, (double)tooltipX - 2.0, (double)(tooltipY + tooltipHeight) + 2.0, borderColor);
         drawRect((double)(tooltipX + maxTextWidth) + 2.0, (double)tooltipY - 2.0, (double)(tooltipX + maxTextWidth) + 3.0, (double)(tooltipY + tooltipHeight) + 2.0, borderColor);
         drawRect((double)tooltipX - 3.0, (double)tooltipY - 3.0, (double)(tooltipX + maxTextWidth) + 3.0, (double)tooltipY - 2.0, borderColor);
         drawRect((double)tooltipX - 3.0, (double)(tooltipY + tooltipHeight) + 2.0, (double)(tooltipX + maxTextWidth) + 3.0, (double)(tooltipY + tooltipHeight) + 3.0, borderColor);

         for(String text : textLines) {
            fontRenderer.func_175063_a(text, (float)tooltipX, (float)tooltipY, Color.WHITE.getRGB());
            tooltipY += 10;
         }

         GL11.glPopMatrix();
      }
   }

   static {
      WORLD_RENDERER = TESSELLATOR.func_178180_c();
   }
}
