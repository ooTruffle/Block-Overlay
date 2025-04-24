package com.ootruffle.blockoverlay.utils;

import java.awt.Color;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;

public class RenderUtils {
   private static final Tessellator TESSELLATOR = Tessellator.func_178181_a();
   private static final WorldRenderer WORLD_RENDERER;

   public RenderUtils() {
   }

   public static void drawBlock(AxisAlignedBB box, EnumFacing side, int overlayStartColor, int overlayEndColor, int outlineStartColor, int outlineEndColor, boolean overlay, boolean outline) {
      if (side == null) {
         drawBlockFull(box, new Color(overlayStartColor, true), new Color(overlayEndColor, true), new Color(outlineStartColor, true), new Color(outlineEndColor, true), overlay, outline);
      } else {
         drawBlockSide(box, side, new Color(overlayStartColor, true), new Color(overlayEndColor, true), new Color(outlineStartColor, true), new Color(outlineEndColor, true), overlay, outline);
      }

   }

   private static void drawBlockFull(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         drawBlockTop(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
         drawBlockBottom(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
         drawBlockNorth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
         drawBlockEast(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
         drawBlockSouth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
         drawBlockWest(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
      }

      if (outline) {
         drawBlockTop(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
         drawBlockBottom(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
         drawBlockNorth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
         drawBlockEast(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
         drawBlockSouth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
         drawBlockWest(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
      }

   }

   public static void drawBlockSide(AxisAlignedBB box, EnumFacing side, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      switch (side) {
         case UP:
            drawBlockTop(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            break;
         case DOWN:
            drawBlockBottom(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            break;
         case NORTH:
            drawBlockNorth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            break;
         case EAST:
            drawBlockEast(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            break;
         case SOUTH:
            drawBlockSouth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            break;
         case WEST:
            drawBlockWest(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      }

   }

   private static void drawBlockTop(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         WORLD_RENDERER.func_181668_a(7, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72334_f).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72339_c).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

      if (outline) {
         WORLD_RENDERER.func_181668_a(2, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72334_f).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72339_c).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

   }

   private static void drawBlockBottom(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         WORLD_RENDERER.func_181668_a(7, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72334_f).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72334_f).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

      if (outline) {
         WORLD_RENDERER.func_181668_a(2, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72334_f).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72334_f).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

   }

   private static void drawBlockNorth(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         WORLD_RENDERER.func_181668_a(7, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72339_c).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

      if (outline) {
         WORLD_RENDERER.func_181668_a(2, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72339_c).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

   }

   private static void drawBlockEast(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         WORLD_RENDERER.func_181668_a(7, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72334_f).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

      if (outline) {
         WORLD_RENDERER.func_181668_a(2, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72334_f).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

   }

   private static void drawBlockSouth(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         WORLD_RENDERER.func_181668_a(7, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72334_f).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72334_f).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72334_f).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

      if (outline) {
         WORLD_RENDERER.func_181668_a(2, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72334_f).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72334_f).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72334_f).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

   }

   private static void drawBlockWest(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      if (overlay) {
         WORLD_RENDERER.func_181668_a(7, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72334_f).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72339_c).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181669_b(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72334_f).func_181669_b(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

      if (outline) {
         WORLD_RENDERER.func_181668_a(2, DefaultVertexFormats.field_181706_f);
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72334_f).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72339_c).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181669_b(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).func_181675_d();
         WORLD_RENDERER.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72334_f).func_181669_b(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).func_181675_d();
         TESSELLATOR.func_78381_a();
      }

   }

   static {
      WORLD_RENDERER = TESSELLATOR.func_178180_c();
   }
}
