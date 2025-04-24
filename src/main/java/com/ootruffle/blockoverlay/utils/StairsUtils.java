package com.ootruffle.blockoverlay.utils;

import java.awt.Color;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class StairsUtils {
   public StairsUtils() {}

   public static void drawStairs(BlockPos blockPos, IBlockState blockState, AxisAlignedBB box, EnumFacing side, double playerX, double playerY, double playerZ, int overlayStartColor, int overlayEndColor, int outlineStartColor, int outlineEndColor, boolean overlay, boolean outline) {
      EnumFacing blockFacing = blockState.getValue(BlockStairs.FACING);
      BlockStairs.EnumHalf blockHalf = blockState.getValue(BlockStairs.HALF);
      int blockX = blockPos.getX();
      int blockY = blockPos.getY();
      int blockZ = blockPos.getZ();
   }
}