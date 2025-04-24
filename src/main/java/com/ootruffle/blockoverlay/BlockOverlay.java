package com.ootruffle.blockoverlay;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = BlockOverlay.MOD_ID, name = BlockOverlay.NAME, version = BlockOverlay.VERSION)
public class BlockOverlay {
   public static final String MOD_ID = "blockoverlay";
   public static final String NAME = "Block Overlay";
   public static final String VERSION = "4.0.3";

   @Mod.Instance(MOD_ID)
   public static BlockOverlay instance;

   private Logger logger;
   private Minecraft mc;

   public BlockOverlay() {
      instance = this; // Ensure instance is initialized
   }

   @Mod.EventHandler
   public void onPreInit(FMLPreInitializationEvent event) {
      this.logger = event.getModLog();
      this.mc = Minecraft.getMinecraft();
   }

   public Logger getLogger() {
      return logger;
   }

   public Minecraft getMc() {
      return mc;
   }
}