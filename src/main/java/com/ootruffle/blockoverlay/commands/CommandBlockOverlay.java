package com.ootruffle.blockoverlay.commands;

import com.ootruffle.blockoverlay.BlockOverlay;
import com.ootruffle.blockoverlay.gui.GuiBlockOverlay;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class CommandBlockOverlay extends CommandBase {
   public CommandBlockOverlay() {
   }

   public String func_71517_b() {
      return "blockoverlay";
   }

   public String func_71518_a(ICommandSender sender) {
      return EnumChatFormatting.RED + "Usage: /" + this.func_71517_b();
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      MinecraftForge.EVENT_BUS.register(this);
   }

   public boolean func_71519_b(ICommandSender sender) {
      return true;
   }

   @SubscribeEvent
   public void onTick(TickEvent.ClientTickEvent event) {
      if (event.phase == Phase.START) {
         BlockOverlay.instance.getMc().func_147108_a(new GuiBlockOverlay());
         BlockOverlay.instance.getBlockOverlayListener().resetAnimation(false);
         MinecraftForge.EVENT_BUS.unregister(this);
      }

   }
}
