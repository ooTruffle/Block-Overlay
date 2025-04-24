package com.ootruffle.blockoverlay.gui.components.buttons;

import java.awt.Color;
import com.ootruffle.blockoverlay.utils.Animator;
import com.ootruffle.blockoverlay.utils.ColorUtils;
import com.ootruffle.blockoverlay.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButtonToggle extends GuiButtonTooltip {
   private static final ResourceLocation BACKGROUND = new ResourceLocation("blockoverlay", "button.png");
   private static final ResourceLocation INDICATOR = new ResourceLocation("blockoverlay", "circle.png");
   private final Animator animator = new Animator(250.0);
   private boolean toggled;

   public GuiButtonToggle(int id, int x, int y, boolean toggled, String text, String... tooltips) {
      super(id, x, y, 40, 15, text, tooltips);
      this.toggled = toggled;
   }

   public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
      if (super.field_146125_m) {
         GL11.glPushMatrix();
         GlStateManager.func_179147_l();
         GlStateManager.func_179120_a(770, 771, 1, 0);
         mc.field_71446_o.func_110577_a(BACKGROUND);
         ColorUtils.color(Color.RED.getRGB(), 0.5);
         GuiUtils.drawTexturedRect((double)super.field_146128_h, (double)super.field_146129_i, (double)super.field_146120_f, (double)super.field_146121_g);
         double alpha = this.animator.getValue(0.0, 1.0, this.toggled, true);
         double x = this.animator.getValue(0.0, 23.0, this.toggled, true);
         if (alpha == 0.0 || alpha == 1.0) {
            super.field_146124_l = true;
         }

         if (alpha > 0.05) {
            ColorUtils.color(Color.GREEN.getRGB(), alpha);
            GuiUtils.drawTexturedRect((double)super.field_146128_h, (double)super.field_146129_i, (double)super.field_146120_f, (double)super.field_146121_g);
         }

         mc.field_71446_o.func_110577_a(INDICATOR);
         ColorUtils.color(Color.WHITE.getRGB());
         GuiUtils.drawTexturedRect((double)super.field_146128_h + x + 2.5, (double)super.field_146129_i + 1.5, 12.0, 12.0);
         super.func_73731_b(mc.field_71466_p, super.field_146126_j, super.field_146128_h + super.field_146120_f + 8, super.field_146129_i + 3, Color.WHITE.getRGB());
         GlStateManager.func_179084_k();
         GL11.glPopMatrix();
         super.func_146112_a(mc, mouseX, mouseY);
      }
   }

   public boolean toggle() {
      super.field_146124_l = false;
      this.toggled ^= true;
      this.animator.reset();
      return this.toggled;
   }
}
