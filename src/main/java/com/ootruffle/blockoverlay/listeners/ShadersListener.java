package com.ootruffle.blockoverlay.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import com.ootruffle.blockoverlay.BlockOverlay;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class ShadersListener {
   private boolean usingShaders;
   private boolean canCheck;

   public ShadersListener() {
      this.checkShaders();
   }

   @SubscribeEvent
   public void onGuiOpen(GuiOpenEvent event) {
      if (this.isShadersGui(event.gui)) {
         this.canCheck = true;
      }

   }

   @SubscribeEvent
   public void onTick(TickEvent.ClientTickEvent event) {
      if (event.phase != TickEvent.Phase.END && BlockOverlay.instance.getMc().thePlayer != null) {
         if (this.canCheck && !this.isShadersGui(BlockOverlay.instance.getMc().currentScreen)) {
            this.canCheck = false;
            this.checkShaders();
         }
      }
   }

   private void checkShaders() {
      boolean shaders = false;
      File shadersConfig = new File(BlockOverlay.instance.getMc().mcDataDir, "optionsshaders.txt");
      if (shadersConfig.exists()) {
         try {
            BufferedReader reader = new BufferedReader(new FileReader(shadersConfig));
            Throwable var4 = null;

            try {
               String text;
               try {
                  while((text = reader.readLine()) != null) {
                     if (text.startsWith("shaderPack=")) {
                        String[] split = text.split("=");
                        shaders = split.length > 1 && !split[1].equals("OFF");
                        break;
                     }
                  }
               } catch (Throwable var15) {
                  var4 = var15;
                  throw var15;
               }
            } finally {
               if (reader != null) {
                  if (var4 != null) {
                     try {
                        reader.close();
                     } catch (Throwable var14) {
                        var4.addSuppressed(var14);
                     }
                  } else {
                     reader.close();
                  }
               }

            }
         } catch (Exception exception) {
            BlockOverlay.instance.getLogger().error("Shaders configuration", exception);
         }
      }

      this.usingShaders = shaders;
   }

   private boolean isShadersGui(GuiScreen gui) {
      return gui != null && gui.getClass().getName().equals("net.optifine.shaders.gui.GuiShaders");
   }

   public boolean isUsingShaders() {
      return this.usingShaders;
   }
}
