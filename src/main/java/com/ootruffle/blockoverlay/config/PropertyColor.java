package com.ootruffle.blockoverlay.config;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.ootruffle.blockoverlay.utils.ColorUtils;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class PropertyColor {
   private final List<Property> properties = new ArrayList();
   public final Property hue;
   public final Property rgb;
   public final Property opacity;

   public PropertyColor(Configuration config, String category) {
      this.properties.add(this.hue = config.get(category, "hue", 0, (String)null, 0, 360));
      this.properties.add(this.rgb = config.get(category, "rgb", Color.WHITE.getRGB()));
      this.properties.add(this.opacity = config.get(category, "opacity", 1.0, (String)null, 0.07, 1.0));
   }

   public int getColor() {
      return ColorUtils.setAlpha(this.rgb.getInt(), this.opacity.getDouble());
   }

   public List<Property> getProperties() {
      return Collections.unmodifiableList(this.properties);
   }
}
