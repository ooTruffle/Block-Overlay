package com.ootruffle.blockoverlay.listeners;

import com.ootruffle.blockoverlay.config.ModConfig;
import com.ootruffle.blockoverlay.utils.Animator;
import com.ootruffle.blockoverlay.utils.EnumUtils;
import com.ootruffle.blockoverlay.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class BlockOverlayListener {
   private final Minecraft mc;
   private final Animator blockAnimator;
   private final List<Block> plants;
   private final double padding;
   private boolean blockShrinking;

   public BlockOverlayListener() {
      this.mc = Minecraft.getMinecraft();
      this.blockAnimator = new Animator(350.0);
      this.plants = Arrays.asList(Blocks.tallgrass, Blocks.double_plant, Blocks.red_flower, Blocks.yellow_flower, Blocks.deadbush);
      this.padding = 0.002;
   }

   @SubscribeEvent
   public void onRenderBlockOverlay(DrawBlockHighlightEvent event) {
      String renderSide = ModConfig.renderSide;
      if ("HIDDEN".equals(renderSide)) {
         event.setCanceled(true);
         return;
      } else if ("FULL".equals(renderSide)) {
         renderBlockBreakOverlay(event.player, event.partialTicks);
      } else {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onRenderBlockOverlay(RenderWorldLastEvent event) {
      Entity entity = mc.getRenderViewEntity();
      if (entity != null) {
         String renderSide = ModConfig.renderSide;
         if ("SIDE".equals(renderSide) || "FULL".equals(renderSide)) {
            Block block = getFocusedBlock();
            if (block != null) {
               renderBlockOverlay(block, entity, event.partialTicks);
            }
         }
      }
   }

   @SubscribeEvent
   public void onRenderCrosshair(RenderGameOverlayEvent.Pre event) {
      if (event.type == ElementType.CROSSHAIRS) {
         event.setCanceled(true);
      }
   }

   private void renderBlockOverlay(Block block, Entity entity, float partialTicks) {
      double entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
      double entityY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
      double entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;

      double thickness = ModConfig.overlayThickness;
      int overlayColor = ModConfig.overlayColor.getRGB();
      boolean outline = ModConfig.outlineEnabled;

      MovingObjectPosition mouseOver = mc.objectMouseOver;
      BlockPos blockPos = mouseOver.getBlockPos();
      AxisAlignedBB boundingBox = block.getSelectedBoundingBox(mc.theWorld, blockPos).expand(padding, padding, padding);

      GL11.glPushMatrix();
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      GlStateManager.disableDepth();

      GL11.glLineWidth((float) thickness);
      RenderUtils.drawBlockSide(boundingBox.offset(-entityX, -entityY, -entityZ), null /* EnumFacing is not used here */,
              new Color(overlayColor, true), new Color(overlayColor, true),
              new Color(overlayColor, true), new Color(overlayColor, true), true, true);

      GlStateManager.enableDepth();
      GlStateManager.disableBlend();
      GlStateManager.enableTexture2D();
      GL11.glPopMatrix();
   }

   private Block getFocusedBlock() {
      MovingObjectPosition mouseOver = mc.objectMouseOver;
      if (mouseOver != null && mouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
         BlockPos blockPos = mouseOver.getBlockPos();
         Block block = mc.theWorld.getBlockState(blockPos).getBlock();

         if (block.getMaterial() == Material.air) return null;
         if (ModConfig.hidePlants && plants.contains(block)) return null;
         if (!ModConfig.enableBarriers && block == Blocks.barrier) return null;

         return block;
      }
      return null;
   }

   private void renderBlockBreakOverlay(Entity entity, float partialTicks) {
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      mc.getTextureManager().bindTexture(null); // Replace with actual texture if needed
      mc.renderGlobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), entity, partialTicks);
      GlStateManager.disableBlend();
   }

   public void resetAnimation(boolean blockShrinking) {
      this.blockShrinking = blockShrinking;
      blockAnimator.reset();
   }
}