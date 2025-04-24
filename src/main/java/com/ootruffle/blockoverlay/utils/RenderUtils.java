package com.ootruffle.blockoverlay.utils;

import java.awt.Color;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

public class RenderUtils {
   private static final Tessellator TESSELLATOR = Tessellator.getInstance();
   private static final WorldRenderer WORLD_RENDERER = TESSELLATOR.getWorldRenderer();

   public RenderUtils() {}

   public static void drawBlock(AxisAlignedBB box, EnumFacing side, int overlayStartColor, int overlayEndColor, int outlineStartColor, int outlineEndColor, boolean overlay, boolean outline) {
      if (side == null) {
         drawBlockFull(box, new Color(overlayStartColor, true), new Color(overlayEndColor, true), new Color(outlineStartColor, true), new Color(outlineEndColor, true), overlay, outline);
      } else {
         drawBlockSide(box, side, new Color(overlayStartColor, true), new Color(overlayEndColor, true), new Color(outlineStartColor, true), new Color(outlineEndColor, true), overlay, outline);
      }
   }

   public static void drawBlockSide(AxisAlignedBB box, EnumFacing side, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         WORLD_RENDERER.begin(7, DefaultVertexFormats.POSITION_COLOR);
         WORLD_RENDERER.pos(box.minX, box.maxY, box.maxZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
         WORLD_RENDERER.pos(box.maxX, box.maxY, box.maxZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
         TESSELLATOR.draw();
      }
   }
   public static void drawBlockTop(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         WORLD_RENDERER.begin(7, DefaultVertexFormats.POSITION_COLOR);
         WORLD_RENDERER.pos(box.minX, box.maxY, box.minZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
         WORLD_RENDERER.pos(box.maxX, box.maxY, box.minZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
         WORLD_RENDERER.pos(box.maxX, box.maxY, box.maxZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
         WORLD_RENDERER.pos(box.minX, box.maxY, box.maxZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
         TESSELLATOR.draw();
      }
   }
   public static void drawBlockBottom(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         WORLD_RENDERER.begin(7, DefaultVertexFormats.POSITION_COLOR);
         WORLD_RENDERER.pos(box.minX, box.minY, box.minZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
         WORLD_RENDERER.pos(box.maxX, box.minY, box.minZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
         TESSELLATOR.draw();
      }
   }
   private static void drawBlockFull(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         drawBlockTop(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
         drawBlockBottom(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
      }
      if (outline) {
         drawBlockTop(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
         drawBlockBottom(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
      }
   }
}