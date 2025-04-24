package com.ootruffle.blockoverlay.utils;

import java.awt.Color;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import org.lwjgl.opengl.GL11;

public class StairsUtils {
   public StairsUtils() {
   }

   public static void drawStairs(BlockPos blockPos, IBlockState blockState, AxisAlignedBB box, EnumFacing side, double playerX, double playerY, double playerZ, int overlayStartColor, int overlayEndColor, int outlineStartColor, int outlineEndColor, boolean overlay, boolean outline) {
      EnumFacing blockFacing = (EnumFacing)blockState.func_177229_b(BlockStairs.field_176309_a);
      BlockStairs.EnumHalf blockHalf = (BlockStairs.EnumHalf)blockState.func_177229_b(BlockStairs.field_176308_b);
      int blockX = blockPos.func_177958_n();
      int blockY = blockPos.func_177956_o();
      int blockZ = blockPos.func_177952_p();
      int angleX = blockHalf == EnumHalf.TOP ? 270 : 0;
      int angleY = 0;
      switch (blockFacing) {
         case NORTH:
            angleY = 180;
            break;
         case EAST:
            angleY = 90;
            break;
         case WEST:
            angleY = 270;
      }

      GL11.glPushMatrix();
      GL11.glTranslated(-playerX, -playerY, -playerZ);
      GL11.glTranslated((double)blockX + 0.5, (double)blockY, (double)blockZ + 0.5);
      GL11.glRotated((double)angleY, 0.0, 1.0, 0.0);
      GL11.glTranslated(0.0, 0.5, 0.0);
      GL11.glRotated((double)angleX, 1.0, 0.0, 0.0);
      GL11.glTranslated((double)(-blockX) - 0.5, (double)(-blockY) - 0.5, (double)(-blockZ) - 0.5);
      if (side == null) {
         drawStairsFull(box, new Color(overlayStartColor, true), new Color(overlayEndColor, true), new Color(outlineStartColor, true), new Color(outlineEndColor, true), overlay, outline);
      } else {
         drawStairsSide(box, blockHalf, blockFacing, side, new Color(overlayStartColor, true), new Color(overlayEndColor, true), new Color(outlineStartColor, true), new Color(outlineEndColor, true), overlay, outline);
      }

      GL11.glPopMatrix();
   }

   private static void drawStairsFull(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      drawStairsTop(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      drawStairsBottom(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      drawStairsNorth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      drawStairsEast(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      drawStairsSouth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      drawStairsWest(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
   }

   private static void drawStairsSide(AxisAlignedBB box, BlockStairs.EnumHalf blockHalf, EnumFacing blockFacing, EnumFacing side, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      switch (getSide(blockHalf, blockFacing, side)) {
         case NORTH:
            drawStairsNorth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            break;
         case EAST:
            drawStairsEast(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            break;
         case WEST:
            drawStairsWest(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            break;
         case UP:
            drawStairsTop(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            break;
         case DOWN:
            drawStairsBottom(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            break;
         case SOUTH:
            drawStairsSouth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      }

   }

   private static void drawStairsTop(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      RenderUtils.drawBlockSide(box.func_72331_e(0.0, 0.0, 0.25).func_72317_d(0.0, 0.0, 0.25), EnumFacing.UP, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      RenderUtils.drawBlockSide(box.func_72331_e(0.0, 0.0, 0.25).func_72317_d(0.0, -0.5, -0.25), EnumFacing.UP, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
   }

   private static void drawStairsBottom(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      RenderUtils.drawBlockSide(box, EnumFacing.DOWN, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
   }

   private static void drawStairsNorth(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      RenderUtils.drawBlockSide(box.func_72331_e(0.0, 0.252, 0.0).func_72317_d(0.0, 0.252, 0.5), EnumFacing.NORTH, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      RenderUtils.drawBlockSide(box.func_72331_e(0.0, 0.25, 0.0).func_72317_d(0.0, -0.25, 0.0), EnumFacing.NORTH, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
   }

   private static void drawStairsEast(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      RenderUtils.drawBlockSide(box.func_72331_e(0.0, 0.252, 0.25).func_72317_d(0.0, 0.252, 0.25), EnumFacing.EAST, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      RenderUtils.drawBlockSide(box.func_72331_e(0.0, 0.25, 0.0).func_72317_d(0.0, -0.25, 0.0), EnumFacing.EAST, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
   }

   private static void drawStairsSouth(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      RenderUtils.drawBlockSide(box, EnumFacing.SOUTH, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
   }

   private static void drawStairsWest(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
      RenderUtils.drawBlockSide(box.func_72331_e(0.0, 0.252, 0.25).func_72317_d(0.0, 0.252, 0.25), EnumFacing.WEST, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      RenderUtils.drawBlockSide(box.func_72331_e(0.0, 0.25, 0.0).func_72317_d(0.0, -0.25, 0.0), EnumFacing.WEST, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
   }

   private static EnumFacing getSide(BlockStairs.EnumHalf blockHalf, EnumFacing blockFacing, EnumFacing side) {
      if (blockHalf == EnumHalf.TOP) {
         switch (blockFacing) {
            case NORTH:
               side = side.func_176732_a(Axis.X);
               side = side.func_176732_a(Axis.Y);
               side = side.func_176732_a(Axis.Y);
               break;
            case EAST:
               side = side.func_176732_a(Axis.Z);
               side = side.func_176732_a(Axis.Y);
               break;
            case WEST:
               side = side.func_176732_a(Axis.Z);
               side = side.func_176732_a(Axis.Y);
               side = side.func_176732_a(Axis.Z);
               side = side.func_176732_a(Axis.Z);
            case UP:
            case DOWN:
            default:
               break;
            case SOUTH:
               side = side.func_176732_a(Axis.X);
               side = side.func_176732_a(Axis.X);
               side = side.func_176732_a(Axis.X);
         }
      } else if (side != EnumFacing.UP && side != EnumFacing.DOWN) {
         switch (blockFacing) {
            case NORTH:
               side = side.func_176734_d();
               break;
            case EAST:
               side = side.func_176746_e();
               break;
            case WEST:
               side = side.func_176735_f();
         }
      }

      return side;
   }
}
