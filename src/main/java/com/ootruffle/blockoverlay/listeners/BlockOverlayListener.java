package com.ootruffle.blockoverlay.listeners;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import com.ootruffle.blockoverlay.BlockOverlay;
import com.ootruffle.blockoverlay.config.Config;
import com.ootruffle.blockoverlay.gui.GuiBlockOverlay;
import com.ootruffle.blockoverlay.utils.Animator;
import com.ootruffle.blockoverlay.utils.ColorUtils;
import com.ootruffle.blockoverlay.utils.EnumUtils;
import com.ootruffle.blockoverlay.utils.RenderUtils;
import com.ootruffle.blockoverlay.utils.StairsUtils;
import com.ootruffle.blockoverlay.utils.enums.RenderMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class BlockOverlayListener {
   private final Minecraft mc;
   private final Config config;
   private final Animator blockAnimator;
   private final List<Block> plants;
   private final double padding;
   private boolean blockShrinking;

   public BlockOverlayListener() {
      this.mc = BlockOverlay.instance.getMc();
      this.config = BlockOverlay.instance.getConfig();
      this.blockAnimator = new Animator(350.0);
      this.plants = Arrays.asList(Blocks.field_150330_I, Blocks.field_150398_cm, Blocks.field_150328_O, Blocks.field_150329_H, Blocks.field_150327_N);
      this.padding = 0.0020000000949949026;
   }

   @SubscribeEvent
   public void onRenderBlockOverlay(DrawBlockHighlightEvent event) {
      switch ((RenderMode)EnumUtils.fromName(RenderMode.class, this.config.renderMode.getString())) {
         case HIDDEN:
            event.setCanceled(true);
         case VANILLA:
            this.renderBlockBreakOverlay(event.player, event.partialTicks);
            break;
         case SIDE:
         case FULL:
            event.setCanceled(true);
      }

   }

   @SubscribeEvent
   public void onRenderBlockOverlay(RenderWorldLastEvent event) {
      Entity entity = this.mc.func_175606_aa();
      if (entity != null) {
         if (FMLClientHandler.instance().isGUIOpen(GuiBlockOverlay.class)) {
            this.renderBlockOverlay(entity, event.partialTicks);
         } else if (!this.mc.field_71474_y.field_74319_N) {
            RenderMode renderMode = (RenderMode)EnumUtils.fromName(RenderMode.class, this.config.renderMode.getString());
            if (renderMode == RenderMode.SIDE || renderMode == RenderMode.FULL) {
               Block block = this.getFocusedBlock();
               if (block != null) {
                  if (BlockOverlay.instance.getShadersListener().isUsingShaders()) {
                     this.mc.field_71466_p.func_78276_b("shaders", 0, -200, Color.WHITE.getRGB());
                  }

                  if (this.mc.field_71442_b.func_178889_l().func_82752_c()) {
                     if (this.config.persistence.getBoolean() || this.canRenderBlockOverlay()) {
                        this.renderBlockOverlay(block, entity, event.partialTicks);
                     }
                  } else {
                     this.renderBlockOverlay(block, entity, event.partialTicks);
                  }

               }
            }
         }
      }
   }

   @SubscribeEvent
   public void onRenderCrosshair(RenderGameOverlayEvent.Pre event) {
      if (event.type == ElementType.CROSSHAIRS && FMLClientHandler.instance().isGUIOpen(GuiBlockOverlay.class)) {
         event.setCanceled(true);
      }

   }

   private void renderBlockOverlay(Block block, Entity entity, float partialTicks) {
      double entityX = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)partialTicks;
      double entityY = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)partialTicks;
      double entityZ = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)partialTicks;
      double thickness = this.config.thickness.getDouble();
      boolean overlay = this.config.overlayRender.visible.getBoolean();
      boolean outline = this.config.outlineRender.visible.getBoolean();
      int overlayStartColor = this.config.overlayRender.getStart();
      int overlayEndColor = this.config.overlayRender.getEnd();
      int outlineStartColor = this.config.outlineRender.getStart();
      int outlineEndColor = this.config.outlineRender.getEnd();
      MovingObjectPosition mouseOver = this.mc.field_71476_x;
      BlockPos blockPos = mouseOver.func_178782_a();
      AxisAlignedBB boundingBox = block.func_180646_a(this.mc.field_71441_e, blockPos).func_72314_b(this.padding, this.padding, this.padding);
      EnumFacing side = EnumUtils.fromName(RenderMode.class, this.config.renderMode.getString()) == RenderMode.SIDE ? mouseOver.field_178784_b : null;
      GL11.glPushMatrix();
      GlStateManager.func_179118_c();
      GlStateManager.func_179147_l();
      if (!BlockOverlay.instance.getShadersListener().isUsingShaders()) {
         GlStateManager.func_179120_a(770, 771, 1, 0);
      }

      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      if (this.config.depthless.getBoolean()) {
         GlStateManager.func_179097_i();
      }

      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      if (outline) {
         GL11.glLineWidth((float)thickness);
      }

      GL11.glShadeModel(7425);
      if (block instanceof BlockStairs) {
         StairsUtils.drawStairs(blockPos, this.mc.field_71441_e.func_180495_p(blockPos), boundingBox.func_72314_b(this.padding, this.padding, this.padding), side, entityX, entityY, entityZ, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      } else {
         RenderUtils.drawBlock(boundingBox.func_72317_d(-entityX, -entityY, -entityZ), side, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
      }

      GL11.glLineWidth(2.0F);
      GL11.glDisable(2848);
      GlStateManager.func_179126_j();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179141_d();
      GL11.glPopMatrix();
      this.renderBlockBreakOverlay(entity, partialTicks);
   }

   private void renderBlockOverlay(Entity entity, float partialTicks) {
      double lookX = entity.func_70676_i(partialTicks).field_72450_a * 2.0;
      double lookY = entity.func_70676_i(partialTicks).field_72448_b * 2.0;
      double lookZ = entity.func_70676_i(partialTicks).field_72449_c * 2.0;
      long time = System.currentTimeMillis();
      double rotation = ((double)time / 20.0 + (double)partialTicks) % 360.0;
      double height = (double)MathHelper.func_76126_a(((float)((double)time / 20.0 % 157.0) + partialTicks) / 25.0F) * 0.2;
      double distance = this.mc.field_71474_y.field_74320_O == 2 ? 0.5 : (this.mc.field_71474_y.field_74320_O == 1 ? -0.5 : 1.5);
      distance = this.blockAnimator.getValue(distance, Math.abs(distance * 20.0), this.blockShrinking, false);
      Block block = Blocks.field_150359_w;
      BlockPos blockPos = new BlockPos.MutableBlockPos();
      AxisAlignedBB boundingBox = block.func_180646_a(this.mc.field_71441_e, blockPos).func_72314_b(this.padding, this.padding, this.padding);
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      BlockRendererDispatcher blockRenderer = this.mc.func_175602_ab();
      RenderMode renderMode = (RenderMode)EnumUtils.fromName(RenderMode.class, this.config.renderMode.getString());
      EnumFacing side = entity.field_70125_A >= 50.0F ? EnumFacing.UP : (entity.field_70125_A >= -50.0F ? EnumFacing.NORTH : EnumFacing.DOWN);
      GL11.glPushMatrix();
      GlStateManager.func_179129_p();
      GlStateManager.func_179118_c();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GL11.glTranslated(0.0, height, 0.0);
      GL11.glTranslated(lookX * distance, lookY * distance + 1.11, lookZ * distance);
      GL11.glRotated(rotation, 0.0, 1.0, 0.0);
      GL11.glTranslated(-0.5, 0.0, -0.5);
      worldRenderer.func_181668_a(7, DefaultVertexFormats.field_176600_a);
      blockRenderer.func_175019_b().func_178267_a(this.mc.field_71441_e, blockRenderer.func_175022_a(block.func_176223_P(), this.mc.field_71441_e, blockPos), block.func_176223_P(), blockPos, worldRenderer, false);
      tessellator.func_78381_a();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179090_x();
      if (renderMode == RenderMode.VANILLA) {
         GL11.glLineWidth(1.0F);
         RenderUtils.drawBlock(boundingBox, (EnumFacing)null, -1, -1, Color.BLACK.getRGB(), Color.BLACK.getRGB(), false, true);
      } else if (renderMode != RenderMode.HIDDEN) {
         GL11.glEnable(2848);
         GL11.glHint(3154, 4354);
         if (this.config.thickness.getDouble() > 1.0 && this.config.outlineRender.visible.getBoolean()) {
            GL11.glLineWidth((float)this.config.thickness.getDouble());
         }

         GL11.glShadeModel(7425);
         RenderUtils.drawBlock(boundingBox, renderMode == RenderMode.SIDE ? side : null, this.config.overlayRender.getStart(), this.config.overlayRender.getEnd(), this.config.outlineRender.getStart(), this.config.outlineRender.getEnd(), this.config.overlayRender.visible.getBoolean(), this.config.outlineRender.visible.getBoolean());
         GL11.glShadeModel(7424);
         GL11.glLineWidth(1.0F);
         GL11.glDisable(2848);
      }

      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179126_j();
      GlStateManager.func_179141_d();
      GlStateManager.func_179089_o();
      ColorUtils.color(Color.WHITE.getRGB());
      GL11.glPopMatrix();
   }

   private Block getFocusedBlock() {
      MovingObjectPosition mouseOver = this.mc.field_71476_x;
      if (mouseOver != null && mouseOver.field_72313_a == MovingObjectType.BLOCK) {
         BlockPos blockPos = mouseOver.func_178782_a();
         if (!this.mc.field_71441_e.func_175723_af().func_177746_a(blockPos)) {
            return null;
         } else {
            Block block = this.mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
            if (block.func_149688_o() == Material.field_151579_a) {
               return null;
            } else if (this.config.hidePlants.getBoolean() && this.plants.contains(block)) {
               return null;
            } else if (!this.config.barriers.getBoolean() && block == Blocks.field_180401_cv) {
               return null;
            } else {
               block.func_180654_a(this.mc.field_71441_e, blockPos);
               return block;
            }
         }
      } else {
         return null;
      }
   }

   private boolean canRenderBlockOverlay() {
      Entity entity = this.mc.func_175606_aa();
      boolean flag = entity instanceof EntityPlayer;
      if (flag && !((EntityPlayer)entity).field_71075_bZ.field_75099_e) {
         ItemStack heldItem = ((EntityPlayer)entity).func_71045_bC();
         if (this.mc.field_71476_x != null && this.mc.field_71476_x.field_72313_a == MovingObjectType.BLOCK) {
            BlockPos blockPos = this.mc.field_71476_x.func_178782_a();
            Block block = this.mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
            if (this.mc.field_71442_b.func_78747_a()) {
               flag = block.hasTileEntity(this.mc.field_71441_e.func_180495_p(blockPos)) && this.mc.field_71441_e.func_175625_s(blockPos) instanceof IInventory;
            } else {
               flag = heldItem != null && (heldItem.func_179544_c(block) || heldItem.func_179547_d(block));
            }
         }
      }

      return flag;
   }

   private void renderBlockBreakOverlay(Entity entity, float partialTicks) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179120_a(770, 1, 1, 0);
      this.mc.func_110434_K().func_110581_b(TextureMap.field_110575_b).func_174936_b(false, false);
      this.mc.field_71438_f.func_174981_a(Tessellator.func_178181_a(), Tessellator.func_178181_a().func_178180_c(), entity, partialTicks);
      this.mc.func_110434_K().func_110581_b(TextureMap.field_110575_b).func_174935_a();
      GlStateManager.func_179084_k();
   }

   public void resetAnimation(boolean blockShrinking) {
      this.blockShrinking = blockShrinking;
      this.blockAnimator.reset();
   }
}
