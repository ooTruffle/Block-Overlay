package com.ootruffle.blockoverlay.gui.components.sliders;

import com.ootruffle.blockoverlay.utils.GuiUtils;

public class GuiSliderOpacity extends GuiSlider {
   private int color;

   public GuiSliderOpacity(int id, int x, int y, String text, double min, double max, double current) {
      super(id, x, y, text, min, max, current);
   }

   protected void drawBackground() {
      GuiUtils.drawGradientRect(super.field_146128_h, super.field_146129_i, super.field_146128_h + super.field_146120_f, super.field_146129_i + super.field_146121_g, 0, this.color);
   }

   public void setColor(int color) {
      this.color = color;
   }
}
