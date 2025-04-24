package com.ootruffle.blockoverlay;

import com.ootruffle.blockoverlay.commands.CommandBlockOverlay;
import com.ootruffle.blockoverlay.config.Config;
import com.ootruffle.blockoverlay.listeners.BlockOverlayListener;
import com.ootruffle.blockoverlay.listeners.ShadersListener;
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
   @Instance("blockoverlay")
   public static BlockOverlay instance;
   private Logger logger;
   private Minecraft mc;
   private Config config;
   private ShadersListener shadersListener;
   private BlockOverlayListener blockOverlayListener;

   public BlockOverlay() {
   }

   @EventHandler
   public void onPreInit(FMLPreInitializationEvent event) {
      this.logger = event.getModLog();
      this.config = new Config(event.getSuggestedConfigurationFile());
      this.config.load();
   }

   @EventHandler
   public void onInit(FMLInitializationEvent event) {
      this.mc = Minecraft.func_71410_x();
      MinecraftForge.EVENT_BUS.register(this.shadersListener = new ShadersListener());
      MinecraftForge.EVENT_BUS.register(this.blockOverlayListener = new BlockOverlayListener());
      ClientCommandHandler.instance.func_71560_a(new CommandBlockOverlay());
   }

   public Logger getLogger() {
      return this.logger;
   }

   public Minecraft getMc() {
      return this.mc;
   }

   public Config getConfig() {
      return this.config;
   }

   public ShadersListener getShadersListener() {
      return this.shadersListener;
   }

   public BlockOverlayListener getBlockOverlayListener() {
      return this.blockOverlayListener;
   }
}
