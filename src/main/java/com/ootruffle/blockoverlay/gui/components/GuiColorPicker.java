package com.ootruffle.blockoverlay.gui.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.ootruffle.blockoverlay.utils.ColorUtils;
import com.ootruffle.blockoverlay.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class GuiColorPicker extends GuiButton {
   private static final int SIZE = 200;
   private final Minecraft mc;
   private BufferedImage backgroundImage;
   private ResourceLocation background;
   private boolean dragging;
   private int x;
   private int y;
   private int color;

   public GuiColorPicker(int id, int x, int y, Color hue, Minecraft mc) {
      super(id, x, y, 100, 100, "");
      this.x = x;
      this.y = y;
      this.mc = mc;
      this.setHue(hue);
   }

   public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
      if (super.field_146125_m) {
         mc.field_71446_o.func_110577_a(this.background);
         ColorUtils.color(Color.WHITE.getRGB());
         GuiUtils.drawTexturedRect((double)super.field_146128_h, (double)super.field_146129_i, (double)super.field_146120_f, (double)super.field_146121_g);
         this.func_146119_b(mc, mouseX, mouseY);
         GuiUtils.drawHollowRect((double)this.x, (double)this.y, (double)this.x + 5.0, (double)this.y + 5.0, 0.5, Color.BLACK.getRGB());
         GuiUtils.drawHollowRect((double)this.x + 0.5, (double)this.y + 0.5, (double)this.x + 4.5, (double)this.y + 4.5, 0.5, Color.WHITE.getRGB());
         GuiUtils.drawHollowRect((double)super.field_146128_h, (double)super.field_146129_i, (double)(super.field_146128_h + super.field_146120_f), (double)(super.field_146129_i + super.field_146121_g), 0.5, Color.BLACK.getRGB());
      }
   }

   public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
      if (super.func_146116_c(mc, mouseX, mouseY)) {
         this.dragging = true;
         this.updateColor(mouseX, mouseY);
         return true;
      } else {
         return false;
      }
   }

   protected void func_146119_b(Minecraft mc, int mouseX, int mouseY) {
      if (this.dragging && super.field_146125_m) {
         this.updateColor(mouseX, mouseY);
      }

   }

   public void func_146118_a(int mouseX, int mouseY) {
      this.dragging = false;
   }

   public int getColor() {
      return this.color;
   }

   public void setColor(int color) {
      for(int x = 0; x < this.backgroundImage.getWidth(); ++x) {
         for(int y = 0; y < this.backgroundImage.getHeight(); ++y) {
            if (this.backgroundImage.getRGB(x, y) == color) {
               this.color = color;
               double scale = (double)SIZE / (double)super.field_146120_f;
               this.x = super.field_146128_h + (int)((double)x / scale);
               this.y = super.field_146129_i + (int)((double)y / scale);
               this.checkBounds();
               break;
            }
         }
      }

   }

   public void setHue(Color hue) {
      BufferedImage image = new BufferedImage(SIZE, SIZE, 1);
      Graphics2D graphics = image.createGraphics();
      GradientPaint horizontal = new GradientPaint(0.0F, 0.0F, Color.WHITE, (float)SIZE, 0.0F, hue);
      GradientPaint vertical = new GradientPaint(0.0F, 0.0F, new Color(0, 0, 0, 0), 0.0F, (float)SIZE, Color.BLACK);
      graphics.setPaint(horizontal);
      graphics.fillRect(0, 0, SIZE, SIZE);
      graphics.setPaint(vertical);
      graphics.fillRect(0, 0, SIZE, SIZE);
      double scale = (double)SIZE / (double)super.field_146120_f;
      this.color = image.getRGB((int)((double)(this.x - super.field_146128_h) * scale), (int)((double)(this.y - super.field_146129_i) * scale));
      this.backgroundImage = image;
      this.background = this.mc.field_71446_o.func_110578_a("ColorPickerImage", new DynamicTexture(image));
   }

   public boolean isDragging() {
      return this.dragging;
   }

   private void updateColor(int mouseX, int mouseY) {
      this.x = mouseX - 3;
      this.y = mouseY - 3;
      this.checkBounds();
      double scale = (double)SIZE / (double)super.field_146120_f;
      this.color = this.backgroundImage.getRGB((int)((double)(this.x - super.field_146128_h) * scale), (int)((double)(this.y - super.field_146129_i) * scale));
   }

   private void checkBounds() {
      if (this.x < super.field_146128_h) {
         this.x = super.field_146128_h;
      } else if (this.x > super.field_146128_h + super.field_146120_f - 5) {
         this.x = super.field_146128_h + super.field_146120_f - 5;
      }

      if (this.y < super.field_146129_i) {
         this.y = super.field_146129_i;
      } else if (this.y > super.field_146129_i + super.field_146121_g - 5) {
         this.y = super.field_146129_i + super.field_146121_g - 5;
      }

   }
}
