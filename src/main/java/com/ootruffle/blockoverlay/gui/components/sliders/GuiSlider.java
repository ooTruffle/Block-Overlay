package com.ootruffle.blockoverlay.gui.components.sliders;

import java.awt.Color;
import com.ootruffle.blockoverlay.utils.GuiUtils;
import net.minecraft.client.Minecraft;

public class GuiSlider extends net.minecraftforge.fml.client.config.GuiSlider {
   public GuiSlider(int id, int x, int y, String text, double min, double max, double current) {
      super(id, x, y, 100, 20, text, "", min, max, current, false, true);
   }

   public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
      if (super.field_146125_m) {
         this.drawBackground();
         this.func_146119_b(mc, mouseX, mouseY);
         super.func_73732_a(mc.field_71466_p, super.dispString + super.suffix, super.field_146128_h + super.field_146120_f / 2, super.field_146129_i + (super.field_146121_g - 8) / 2, Color.WHITE.getRGB());
      }
   }

   protected void func_146119_b(Minecraft mc, int mouseX, int mouseY) {
      if (super.field_146125_m) {
         if (super.dragging) {
            super.sliderValue = ((double)mouseX - ((double)super.field_146128_h + 4.0)) / ((double)super.field_146120_f - 8.0);
            super.updateSlider();
         }

         int x = super.field_146128_h + (int)(super.sliderValue * ((double)super.field_146120_f - 8.0));
         GuiUtils.drawHollowRect((double)x + 0.5, (double)super.field_146129_i + 0.5, (double)x + 7.5, (double)(super.field_146129_i + super.field_146121_g) - 0.5, 0.5, Color.BLACK.getRGB());
         GuiUtils.drawHollowRect((double)x + 1.0, (double)super.field_146129_i + 1.0, (double)x + 7.0, (double)(super.field_146129_i + super.field_146121_g) - 1.0, 0.5, Color.WHITE.getRGB());
         GuiUtils.drawHollowRect((double)super.field_146128_h, (double)super.field_146129_i, (double)(super.field_146128_h + super.field_146120_f), (double)(super.field_146129_i + super.field_146121_g), 0.5, Color.BLACK.getRGB());
      }
   }

   protected void drawBackground() {
      GuiUtils.drawRect((double)super.field_146128_h, (double)super.field_146129_i, (double)(super.field_146128_h + super.field_146120_f), (double)(super.field_146129_i + super.field_146121_g), Color.GRAY.getRGB());
   }
}
