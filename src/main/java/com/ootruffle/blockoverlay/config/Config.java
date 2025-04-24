package com.ootruffle.blockoverlay.config;

import java.io.File;
import java.util.Arrays;
import com.ootruffle.blockoverlay.BlockOverlay;
import com.ootruffle.blockoverlay.utils.EnumUtils;
import com.ootruffle.blockoverlay.utils.enums.RenderMode;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {
   public Property renderMode;
   public Property persistence;
   public Property depthless;
   public Property barriers;
   public Property hidePlants;
   public Property thickness;
   public PropertyRender overlayRender;
   public PropertyRender outlineRender;
   private final Configuration configFile;

   public Config(File file) {
      this.configFile = new Configuration(file, "4.0", true);
   }

   public void load() {
      try {
         String[] booleans = new String[]{"true", "false"};
         this.configFile.load();
         this.renderMode = this.loadValue(this.configFile.get("general", "renderMode", RenderMode.VANILLA.toString(), (String)null, EnumUtils.getNames(RenderMode.class)));
         this.persistence = this.loadValue(this.configFile.get("general", "persistence", false).setValidValues(booleans));
         this.depthless = this.loadValue(this.configFile.get("general", "depthless", false).setValidValues(booleans));
         this.barriers = this.loadValue(this.configFile.get("general", "barriers", false).setValidValues(booleans));
         this.hidePlants = this.loadValue(this.configFile.get("general", "hidePlants", false).setValidValues(booleans));
         this.thickness = this.loadValue(this.configFile.get("general", "thickness", 2.0, (String)null, 1.0, 10.0));
         this.overlayRender = this.loadValue(new PropertyRender(this.configFile, "Overlay"));
         this.outlineRender = this.loadValue(new PropertyRender(this.configFile, "Outline"));
         if (this.configFile.hasChanged()) {
            this.configFile.save();
         }
      } catch (Exception exception) {
         BlockOverlay.instance.getLogger().error("Load configuration", exception);
      }

   }

   public void save() {
      try {
         if (this.configFile.hasChanged()) {
            this.configFile.save();
         }
      } catch (Exception exception) {
         BlockOverlay.instance.getLogger().error("Save configuration", exception);
      }

   }

   private Property loadValue(Property property) {
      boolean valid = false;
      if (property.getValidValues().length != 0) {
         if (Arrays.asList(property.getValidValues()).contains(property.getString())) {
            valid = true;
         }
      } else {
         double value = property.getDouble();
         double minValue = Double.parseDouble(property.getMinValue());
         double maxValue = Double.parseDouble(property.getMaxValue());
         if (minValue <= value && value <= maxValue) {
            valid = true;
         }
      }

      if (!valid) {
         property.setValue(property.getDefault());
      }

      return property;
   }

   private PropertyRender loadValue(PropertyRender colorProperty) {
      colorProperty.getProperties().forEach(this::loadValue);
      return colorProperty;
   }
}
