package com.ootruffle.blockoverlay.gui.components.buttons;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import com.ootruffle.blockoverlay.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public class GuiButtonMenu extends GuiButtonTooltip {
   private int index;

   public GuiButtonMenu(int id, int index, int x, int y, String text, String... tooltips) {
      super(id, x, y, 100, 20, text, tooltips);
      this.index = index;
   }

   public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
      if (super.field_146125_m) {
         GuiUtils.drawRect((double)super.field_146128_h, (double)super.field_146129_i, (double)(super.field_146128_h + super.field_146120_f), (double)(super.field_146129_i + super.field_146121_g), Color.GRAY.getRGB());
         String text = super.field_146126_j;
         int textWidth = mc.field_71466_p.func_78256_a(text);
         int ellipsisWidth = mc.field_71466_p.func_78256_a("...");
         if (textWidth > super.field_146120_f - 6 && textWidth > ellipsisWidth) {
            text = mc.field_71466_p.func_78269_a(text, super.field_146120_f - 6 - ellipsisWidth).trim() + "...";
         }

         super.func_73732_a(mc.field_71466_p, text, super.field_146128_h + super.field_146120_f / 2, super.field_146129_i + (super.field_146121_g - 8) / 2, Color.WHITE.getRGB());
         super.func_146112_a(mc, mouseX, mouseY);
      }
   }

   public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
      if (super.func_146116_c(mc, mouseX, mouseY)) {
         if (++this.index >= this.tooltips.size()) {
            this.index = 0;
         }

         return true;
      } else {
         return false;
      }
   }

   protected List<String> getTooltips() {
      List<String> textLines = new ArrayList(super.tooltips);

      for(int i = 0; i < textLines.size(); ++i) {
         textLines.set(i, (i == this.index ? EnumChatFormatting.WHITE : EnumChatFormatting.GRAY) + (String)textLines.get(i));
      }

      return textLines;
   }

   public void setIndex(int index) {
      if (0 <= index && index < super.tooltips.size()) {
         this.index = index;
      }

   }
}
