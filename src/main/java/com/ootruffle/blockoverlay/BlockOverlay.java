package com.ootruffle.blockoverlay;

import com.ootruffle.blockoverlay.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = "blockoverlay",
        name = "Block Overlay",
        version = "4.0.3",
        acceptedMinecraftVersions = "[1.8.9]"
)
public class BlockOverlay {
   public static final String MOD_ID = "blockoverlay";
   public static final String NAME = "Block Overlay";
   public static final String VERSION = "4.0.3";

   @Instance(MOD_ID)
   public static BlockOverlay instance;

   private Logger logger;
   private Minecraft mc;

   // Reference to ModConfig
   private ModConfig config;

   @EventHandler
   public void onPreInit(FMLPreInitializationEvent event) {
      this.logger = event.getModLog();

      // Initialize OneConfig with ModConfig
      this.config = new ModConfig();
      this.logger.info("OneConfig initialized for Block Overlay.");
   }

   @EventHandler
   public void onInit(FMLInitializationEvent event) {
      this.mc = Minecraft.getMinecraft();
      MinecraftForge.EVENT_BUS.register(new BlockOverlayListener());
      ClientCommandHandler.instance.registerCommand(new CommandBlockOverlay());
   }

   public Logger getLogger() {
      return this.logger;
   }

   public Minecraft getMc() {
      return this.mc;
   }
}