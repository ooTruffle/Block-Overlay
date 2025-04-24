package com.ootruffle.blockoverlay.config;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.ootruffle.blockoverlay.utils.ColorUtils;
import com.ootruffle.blockoverlay.utils.EnumUtils;
import com.ootruffle.blockoverlay.utils.enums.ColorMode;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class PropertyRender {
   private final List<Property> properties;
   public final String name;
   private final PropertyColor staticColor;
   private final PropertyColor gradientStartColor;
   private final PropertyColor gradientEndColor;
   private final PropertyColor fadeStartColor;
   private final PropertyColor fadeEndColor;
   private final Property chromaOpacity;
   public final Property colorMode;
   public final Property visible;
   public final Property fadeSpeed;
   public final Property chromaSpeed;

   public PropertyRender(Configuration config, String category) {
      String[] booleans = new String[]{"true", "false"};
      this.name = category;
      this.properties = new ArrayList();
      this.staticColor = new PropertyColor(config, category + "Static");
      this.gradientStartColor = new PropertyColor(config, category + "GradientStart");
      this.gradientEndColor = new PropertyColor(config, category + "GradientEnd");
      this.fadeStartColor = new PropertyColor(config, category + "FadeStart");
      this.fadeEndColor = new PropertyColor(config, category + "FadeEnd");
      this.properties.addAll(this.staticColor.getProperties());
      this.properties.addAll(this.gradientStartColor.getProperties());
      this.properties.addAll(this.gradientEndColor.getProperties());
      this.properties.addAll(this.fadeStartColor.getProperties());
      this.properties.addAll(this.fadeEndColor.getProperties());
      this.properties.add(this.chromaOpacity = config.get(category, "chromaOpacity", 1.0, (String)null, 0.07, 1.0));
      this.properties.add(this.colorMode = config.get(category, "colorMode", ColorMode.STATIC.toString()).setValidValues(EnumUtils.getNames(ColorMode.class)));
      this.properties.add(this.visible = config.get(category, "visible", true).setValidValues(booleans));
      this.properties.add(this.fadeSpeed = config.get(category, "fadeSpeed", 5.5, (String)null, 1.0, 10.0));
      this.properties.add(this.chromaSpeed = config.get(category, "chromaSpeed", 5.5, (String)null, 1.0, 10.0));
   }

   public List<Property> getProperties() {
      return Collections.unmodifiableList(this.properties);
   }

   public int getHue(int index) {
      switch ((ColorMode)EnumUtils.fromName(ColorMode.class, this.colorMode.getString())) {
         case STATIC:
            return this.staticColor.hue.getInt();
         case GRADIENT:
            return (index == 0 ? this.gradientStartColor.hue : this.gradientEndColor.hue).getInt();
         case FADE:
            return (index == 0 ? this.fadeStartColor.hue : this.fadeEndColor.hue).getInt();
         case CHROMA:
         default:
            return 0;
      }
   }

   public void setHue(int index, int hue) {
      Property property;
      switch ((ColorMode)EnumUtils.fromName(ColorMode.class, this.colorMode.getString())) {
         case STATIC:
            property = this.staticColor.hue;
            break;
         case GRADIENT:
            property = index == 0 ? this.gradientStartColor.hue : this.gradientEndColor.hue;
            break;
         case FADE:
            property = index == 0 ? this.fadeStartColor.hue : this.fadeEndColor.hue;
            break;
         case CHROMA:
         default:
            return;
      }

      property.setValue(hue);
   }

   public int getColor(int index) {
      switch ((ColorMode)EnumUtils.fromName(ColorMode.class, this.colorMode.getString())) {
         case STATIC:
            return this.staticColor.rgb.getInt();
         case GRADIENT:
            return (index == 0 ? this.gradientStartColor.rgb : this.gradientEndColor.rgb).getInt();
         case FADE:
            return (index == 0 ? this.fadeStartColor.rgb : this.fadeEndColor.rgb).getInt();
         case CHROMA:
            return ColorUtils.getChroma(this.chromaSpeed.getDouble());
         default:
            return Color.WHITE.getRGB();
      }
   }

   public void setColor(int index, int color) {
      Property property;
      switch ((ColorMode)EnumUtils.fromName(ColorMode.class, this.colorMode.getString())) {
         case STATIC:
            property = this.staticColor.rgb;
            break;
         case GRADIENT:
            property = index == 0 ? this.gradientStartColor.rgb : this.gradientEndColor.rgb;
            break;
         case FADE:
            property = index == 0 ? this.fadeStartColor.rgb : this.fadeEndColor.rgb;
            break;
         case CHROMA:
         default:
            return;
      }

      property.setValue(color);
   }

   public double getOpacity(int index) {
      switch ((ColorMode)EnumUtils.fromName(ColorMode.class, this.colorMode.getString())) {
         case STATIC:
            return this.staticColor.opacity.getDouble();
         case GRADIENT:
            return (index == 0 ? this.gradientStartColor.opacity : this.gradientEndColor.opacity).getDouble();
         case FADE:
            return (index == 0 ? this.fadeStartColor.opacity : this.fadeEndColor.opacity).getDouble();
         case CHROMA:
            return this.chromaOpacity.getDouble();
         default:
            return 1.0;
      }
   }

   public void setOpacity(int index, double opacity) {
      Property property;
      switch ((ColorMode)EnumUtils.fromName(ColorMode.class, this.colorMode.getString())) {
         case STATIC:
            property = this.staticColor.opacity;
            break;
         case GRADIENT:
            property = index == 0 ? this.gradientStartColor.opacity : this.gradientEndColor.opacity;
            break;
         case FADE:
            property = index == 0 ? this.fadeStartColor.opacity : this.fadeEndColor.opacity;
            break;
         case CHROMA:
            property = this.chromaOpacity;
            break;
         default:
            return;
      }

      property.setValue(opacity);
   }

   public int getStart() {
      switch ((ColorMode)EnumUtils.fromName(ColorMode.class, this.colorMode.getString())) {
         case STATIC:
            return this.staticColor.getColor();
         case GRADIENT:
            return this.gradientStartColor.getColor();
         case FADE:
            double percent = Math.sin((double)System.currentTimeMillis() / (1100.0 - this.fadeSpeed.getDouble() * 100.0)) * 0.5 + 0.5;
            return ColorUtils.interpolate(this.fadeStartColor.getColor(), this.fadeEndColor.getColor(), percent);
         case CHROMA:
            return ColorUtils.setAlpha(ColorUtils.getChroma(this.chromaSpeed.getDouble()), this.chromaOpacity.getDouble());
         default:
            return Color.WHITE.getRGB();
      }
   }

   public int getEnd() {
      switch ((ColorMode)EnumUtils.fromName(ColorMode.class, this.colorMode.getString())) {
         case STATIC:
         case CHROMA:
            return this.getStart();
         case GRADIENT:
            return this.gradientEndColor.getColor();
         case FADE:
            double percent = Math.sin((double)(System.currentTimeMillis() + 500L) / (1100.0 - this.fadeSpeed.getDouble() * 100.0)) * 0.5 + 0.5;
            return ColorUtils.interpolate(this.fadeStartColor.getColor(), this.fadeEndColor.getColor(), percent);
         default:
            return Color.WHITE.getRGB();
      }
   }
}
