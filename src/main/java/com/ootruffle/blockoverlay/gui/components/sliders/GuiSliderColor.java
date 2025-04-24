package com.ootruffle.blockoverlay.gui.components.sliders;

import java.awt.Color;
import java.awt.image.BufferedImage;
import com.ootruffle.blockoverlay.utils.ColorUtils;
import com.ootruffle.blockoverlay.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class GuiSliderColor extends GuiSlider {
   private final Minecraft mc;
   private ResourceLocation background;

   public GuiSliderColor(int id, int x, int y, String text, double min, double max, double current, Minecraft mc) {
      super(id, x, y, text, min, max, current);
      this.mc = mc;
      this.init();
   }

   protected void drawBackground() {
      this.mc.field_71446_o.func_110577_a(this.background);
      ColorUtils.color(Color.WHITE.getRGB());
      GuiUtils.drawTexturedRect((double)super.field_146128_h, (double)super.field_146129_i, (double)super.field_146120_f, (double)super.field_146121_g);
   }

   public Color getColor() {
      return Color.getHSBColor((float)super.getValueInt() / 360.0F, 1.0F, 1.0F);
   }

   private void init() {
      int width = 500;
      int height = 100;
      BufferedImage image = new BufferedImage(500, 100, 1);

      for(int x = 0; x < 500; ++x) {
         float hue = (float)x / 500.0F;
         int rgb = Color.getHSBColor(hue, 1.0F, 1.0F).getRGB();

         for(int y = 0; y < 100; ++y) {
            image.setRGB(x, y, rgb);
         }
      }

      this.background = this.mc.field_71446_o.func_110578_a("HueImage", new DynamicTexture(image));
   }
}
