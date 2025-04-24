package com.ootruffle.blockoverlay.gui.components.buttons;

import java.util.Arrays;
import java.util.List;
import com.ootruffle.blockoverlay.utils.Animator;
import com.ootruffle.blockoverlay.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public abstract class GuiButtonTooltip extends GuiButton {
   protected final List<String> tooltips;
   private final Animator animator;
   private boolean prevHovered;
   private boolean animateIn;

   public GuiButtonTooltip(int id, int x, int y, int width, int height, String text, String... tooltips) {
      super(id, x, y, width, height, text);
      this.tooltips = Arrays.asList(tooltips);
      this.animator = new Animator(200.0);
   }

   public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
      if (super.field_146125_m) {
         super.field_146123_n = super.field_146128_h <= mouseX && mouseX < super.field_146128_h + super.field_146120_f && super.field_146129_i <= mouseY && mouseY < super.field_146129_i + super.field_146121_g;
         if (super.field_146123_n && !this.prevHovered) {
            this.animateIn = true;
            this.animator.reset();
         } else if (!super.field_146123_n && this.prevHovered) {
            this.animateIn = false;
            this.animator.reset();
         }

         this.prevHovered = super.field_146123_n;
         double scale = this.animator.getValue(0.1, 1.0, this.animateIn, false);
         if (super.field_146123_n || this.animateIn || scale > 0.1) {
            GL11.glPushMatrix();
            GL11.glTranslated((double)mouseX, (double)mouseY, 1.0);
            GL11.glScaled(scale, scale, 1.0);
            GuiUtils.drawTooltip(mc, this.getTooltips(), 0, 0);
            GL11.glPopMatrix();
         }

      }
   }

   protected List<String> getTooltips() {
      return this.tooltips;
   }
}
