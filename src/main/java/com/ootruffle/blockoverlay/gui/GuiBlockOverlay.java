package com.ootruffle.blockoverlay.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.ootruffle.blockoverlay.BlockOverlay;
import com.ootruffle.blockoverlay.config.Config;
import com.ootruffle.blockoverlay.config.PropertyRender;
import com.ootruffle.blockoverlay.gui.components.GuiColorPicker;
import com.ootruffle.blockoverlay.gui.components.buttons.GuiButtonMenu;
import com.ootruffle.blockoverlay.gui.components.buttons.GuiButtonToggle;
import com.ootruffle.blockoverlay.gui.components.sliders.GuiSlider;
import com.ootruffle.blockoverlay.gui.components.sliders.GuiSliderColor;
import com.ootruffle.blockoverlay.gui.components.sliders.GuiSliderOpacity;
import com.ootruffle.blockoverlay.utils.Animator;
import com.ootruffle.blockoverlay.utils.ColorUtils;
import com.ootruffle.blockoverlay.utils.EnumUtils;
import com.ootruffle.blockoverlay.utils.GuiUtils;
import com.ootruffle.blockoverlay.utils.enums.ColorMode;
import com.ootruffle.blockoverlay.utils.enums.RenderMode;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class GuiBlockOverlay extends GuiScreen {
   private final Config config;
   private final Animator animator;
   private final List<GuiButton> leftComponents;
   private final List<GuiButton> rightComponents;
   private static int propertyRenderIndex;
   private static int gradientColorIndex;
   private static int fadeColorIndex;
   private PropertyRender property;
   private GuiColorPicker colorPicker;
   private GuiSliderColor colorSlider;
   private GuiSliderOpacity opacitySlider;
   private GuiSlider fadeSpeedSlider;
   private GuiSlider chromaSpeedSlider;
   private GuiButtonMenu gradientColorButton;
   private GuiButtonMenu fadeColorButton;
   private GuiButtonMenu colorModeButton;
   private GuiButtonMenu propertyButton;
   private GuiButtonMenu renderModeButton;
   private GuiSlider thicknessSlider;
   private GuiButtonToggle outlineToggleButton;
   private GuiButtonToggle overlayToggleButton;
   private GuiButtonToggle persistenceToggleButton;
   private GuiButtonToggle depthToggleButton;
   private GuiButtonToggle barriersToggleButton;
   private GuiButtonToggle plantsToggleButton;
   private boolean closing;

   public GuiBlockOverlay() {
      this.config = BlockOverlay.instance.getConfig();
      this.animator = new Animator(350.0);
      this.animator.reset();
      this.leftComponents = new ArrayList();
      this.rightComponents = new ArrayList();
      this.property = propertyRenderIndex == 1 ? this.config.overlayRender : this.config.outlineRender;
   }

   public void func_73866_w_() {
      this.leftComponents.clear();
      this.rightComponents.clear();
      int x = 10;
      this.colorSlider = new GuiSliderColor(0, x, super.field_146295_m / 2 + 2, "Hue", 0.0, 360.0, (double)this.property.getHue(this.getColorIndex()), super.field_146297_k);
      this.leftComponents.add(this.colorSlider);
      this.colorPicker = new GuiColorPicker(1, x, super.field_146295_m / 2 - 107, this.colorSlider.getColor(), super.field_146297_k);
      this.colorPicker.setColor(this.property.getColor(this.getColorIndex()));
      this.leftComponents.add(this.colorPicker);
      this.opacitySlider = new GuiSliderOpacity(2, x, super.field_146295_m / 2 + 30, "Opacity", 0.07, 1.0, this.property.getOpacity(this.getColorIndex()));
      this.opacitySlider.setColor(this.colorPicker.getColor());
      this.leftComponents.add(this.opacitySlider);
      this.leftComponents.add(this.fadeSpeedSlider = new GuiSlider(3, x, super.field_146295_m / 2 - 164, "Speed", 1.0, 10.0, this.property.fadeSpeed.getDouble()));
      this.leftComponents.add(this.chromaSpeedSlider = new GuiSlider(4, x, super.field_146295_m / 2 + 2, "Speed", 1.0, 10.0, this.property.chromaSpeed.getDouble()));
      this.leftComponents.add(this.gradientColorButton = new GuiButtonMenu(5, gradientColorIndex, x, super.field_146295_m / 2 - 136, "Gradient: Color " + (gradientColorIndex + 1), new String[]{"Color 1", "Color 2"}));
      this.leftComponents.add(this.fadeColorButton = new GuiButtonMenu(6, fadeColorIndex, x, super.field_146295_m / 2 - 136, "Fade: Color " + (fadeColorIndex + 1), new String[]{"Color 1", "Color 2"}));
      this.leftComponents.add(this.colorModeButton = new GuiButtonMenu(7, EnumUtils.getOrdinal(ColorMode.class, this.property.colorMode.getString()), x, super.field_146295_m / 2 + 58, "Color: " + this.formatName(this.property.colorMode.getString()), (String[])Arrays.stream(EnumUtils.getNames(ColorMode.class)).map(this::formatName).toArray((x$0) -> new String[x$0])));
      this.leftComponents.add(this.propertyButton = new GuiButtonMenu(8, propertyRenderIndex, x, super.field_146295_m / 2 + 86, "Editing: " + this.property.name, new String[]{this.config.outlineRender.name, this.config.overlayRender.name}));
      x = super.field_146294_l - 110;
      this.rightComponents.add(this.renderModeButton = new GuiButtonMenu(9, EnumUtils.getOrdinal(RenderMode.class, this.config.renderMode.getString()), x, super.field_146295_m / 2 - 96, "Render: " + this.formatName(this.config.renderMode.getString()), (String[])Arrays.stream(EnumUtils.getNames(RenderMode.class)).map(this::formatName).toArray((x$0) -> new String[x$0])));
      this.rightComponents.add(this.thicknessSlider = new GuiSlider(10, x, super.field_146295_m / 2 - 68, "Outline Thickness", 1.0, 10.0, this.config.thickness.getDouble()));
      this.rightComponents.add(this.outlineToggleButton = new GuiButtonToggle(11, x, super.field_146295_m / 2 - 40, this.config.outlineRender.visible.getBoolean(), this.config.outlineRender.name, new String[]{"Show", "the outline"}));
      this.rightComponents.add(this.overlayToggleButton = new GuiButtonToggle(12, x, super.field_146295_m / 2 - 16, this.config.overlayRender.visible.getBoolean(), this.config.overlayRender.name, new String[]{"Show", "the overlay"}));
      this.rightComponents.add(this.persistenceToggleButton = new GuiButtonToggle(13, x, super.field_146295_m / 2 + 8, this.config.persistence.getBoolean(), "Persistent", new String[]{"Render in", "Adventure and", "Spectator mode"}));
      this.rightComponents.add(this.depthToggleButton = new GuiButtonToggle(14, x, super.field_146295_m / 2 + 32, this.config.depthless.getBoolean(), "Depthless", new String[]{"Render", "without depth"}));
      this.rightComponents.add(this.barriersToggleButton = new GuiButtonToggle(15, x, super.field_146295_m / 2 + 56, this.config.barriers.getBoolean(), "Barriers", new String[]{"Render on", "barrier blocks"}));
      this.rightComponents.add(this.plantsToggleButton = new GuiButtonToggle(16, x, super.field_146295_m / 2 + 80, this.config.hidePlants.getBoolean(), "Hide Plants", new String[]{"Prevent", "rendering", "on grass", "and flowers"}));
      this.updateComponents();
      super.field_146292_n.addAll(this.leftComponents);
      super.field_146292_n.addAll(this.rightComponents);
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      double panelWidth = (double)this.colorPicker.field_146120_f + 20.0;
      double panelX = this.animator.getValue(0.0, panelWidth, this.closing, false);
      GL11.glPushMatrix();
      GL11.glTranslated(-panelX, 0.0, 0.0);
      GuiUtils.drawRect(0.0, 0.0, panelWidth, (double)super.field_146295_m, -1089466352);
      if (EnumUtils.fromName(ColorMode.class, this.property.colorMode.getString()) == ColorMode.CHROMA) {
         GuiUtils.drawRect((double)this.colorPicker.field_146128_h, (double)this.colorPicker.field_146129_i, (double)(this.colorPicker.field_146128_h + this.colorPicker.field_146120_f), (double)(this.colorPicker.field_146129_i + this.colorPicker.field_146121_g), ColorUtils.getChroma(this.property.chromaSpeed.getDouble()));
         GuiUtils.drawHollowRect((double)this.colorPicker.field_146128_h, (double)this.colorPicker.field_146129_i, (double)(this.colorPicker.field_146128_h + this.colorPicker.field_146120_f), (double)(this.colorPicker.field_146129_i + this.colorPicker.field_146121_g), 0.5, Color.BLACK.getRGB());
         this.opacitySlider.setColor(ColorUtils.getChroma(this.property.chromaSpeed.getDouble()));
      }

      this.leftComponents.forEach((component) -> component.func_146112_a(super.field_146297_k, mouseX, mouseY));
      GL11.glTranslated(panelX, 0.0, 0.0);
      GL11.glTranslated(panelX, 0.0, 0.0);
      GuiUtils.drawRect((double)super.field_146294_l - panelWidth, 0.0, (double)super.field_146294_l, (double)super.field_146295_m, -1089466352);
      this.rightComponents.forEach((component) -> component.func_146112_a(super.field_146297_k, mouseX, mouseY));
      GL11.glTranslated(-panelX, 0.0, 0.0);
      GL11.glScaled(1.5, 1.5, 1.0);
      double alpha = this.animator.getValue(0.1, 1.0, !this.closing, true);
      GlStateManager.func_179147_l();
      super.func_73732_a(super.field_146289_q, "Block Overlay", (int)((double)(super.field_146294_l / 2) / 1.5), (int)((double)(super.field_146295_m / 15) / 1.5), ColorUtils.setAlpha(Color.WHITE.getRGB(), alpha));
      GlStateManager.func_179084_k();
      GL11.glPopMatrix();
      if (this.closing && panelX == panelWidth) {
         super.field_146297_k.func_147108_a((GuiScreen)null);
         if (super.field_146297_k.field_71462_r == null) {
            super.field_146297_k.func_71381_h();
         }
      }

   }

   protected void func_146284_a(GuiButton button) {
      switch (button.field_146127_k) {
         case 0:
            this.updateColorSlider();
            break;
         case 1:
            this.updateColorPicker();
            break;
         case 2:
            this.updateOpacitySlider();
            break;
         case 3:
            this.updateFadeSpeedSlider();
            break;
         case 4:
            this.updateChromaSpeedSlider();
            break;
         case 5:
            this.gradientColorButton.field_146126_j = "Gradient: Color " + ((gradientColorIndex ^= 1) + 1);
            this.updateComponents();
            break;
         case 6:
            this.fadeColorButton.field_146126_j = "Fade: Color " + ((fadeColorIndex ^= 1) + 1);
            this.updateComponents();
            break;
         case 7:
            this.property.colorMode.setValue(((ColorMode)EnumUtils.getNext(ColorMode.class, this.property.colorMode.getString())).toString());
            this.colorModeButton.field_146126_j = "Color: " + this.formatName(this.property.colorMode.getString());
            this.updateComponents();
            break;
         case 8:
            this.property = (propertyRenderIndex ^= 1) == 1 ? this.config.overlayRender : this.config.outlineRender;
            this.fadeSpeedSlider.setValue(this.property.fadeSpeed.getDouble());
            this.colorModeButton.setIndex(EnumUtils.getOrdinal(ColorMode.class, this.property.colorMode.getString()));
            this.colorModeButton.field_146126_j = "Color: " + this.formatName(this.property.colorMode.getString());
            this.propertyButton.field_146126_j = "Editing: " + this.property.name;
            this.updateComponents();
            break;
         case 9:
            this.config.renderMode.setValue(((RenderMode)EnumUtils.getNext(RenderMode.class, this.config.renderMode.getString())).toString());
            this.renderModeButton.field_146126_j = "Render: " + this.formatName(this.config.renderMode.getString());
            break;
         case 10:
            this.updateThicknessSlider();
            break;
         case 11:
            this.config.outlineRender.visible.setValue(this.outlineToggleButton.toggle());
            break;
         case 12:
            this.config.overlayRender.visible.setValue(this.overlayToggleButton.toggle());
            break;
         case 13:
            this.config.persistence.setValue(this.persistenceToggleButton.toggle());
            break;
         case 14:
            this.config.depthless.setValue(this.depthToggleButton.toggle());
            break;
         case 15:
            this.config.barriers.setValue(this.barriersToggleButton.toggle());
            break;
         case 16:
            this.config.hidePlants.setValue(this.plantsToggleButton.toggle());
      }

   }

   protected void func_146273_a(int mouseX, int mouseY, int mouseButton, long timeSinceLastClick) {
      if (this.colorSlider.dragging) {
         this.updateColorSlider();
      } else if (this.colorPicker.isDragging()) {
         this.updateColorPicker();
      } else if (this.opacitySlider.dragging) {
         this.updateOpacitySlider();
      } else if (this.fadeSpeedSlider.dragging) {
         this.updateFadeSpeedSlider();
      } else if (this.chromaSpeedSlider.dragging) {
         this.updateChromaSpeedSlider();
      } else if (this.thicknessSlider.dragging) {
         this.updateThicknessSlider();
      }

   }

   protected void func_73869_a(char typedChar, int keyCode) {
      if (keyCode == 1 && !this.closing) {
         this.closing = true;
         this.animator.reset();
         BlockOverlay.instance.getBlockOverlayListener().resetAnimation(true);
      }

   }

   public void func_146281_b() {
      this.config.save();
   }

   private String formatName(String name) {
      return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
   }

   private void updateComponents() {
      this.colorSlider.setValue((double)this.property.getHue(this.getColorIndex()));
      this.colorPicker.setHue(this.colorSlider.getColor());
      this.colorPicker.setColor(this.property.getColor(this.getColorIndex()));
      this.opacitySlider.setValue(this.property.getOpacity(this.getColorIndex()));
      this.opacitySlider.setColor(this.colorPicker.getColor());
      switch ((ColorMode)EnumUtils.fromName(ColorMode.class, this.property.colorMode.getString())) {
         case STATIC:
            this.colorPicker.field_146125_m = true;
            this.colorSlider.field_146125_m = true;
            this.fadeSpeedSlider.field_146125_m = false;
            this.chromaSpeedSlider.field_146125_m = false;
            this.gradientColorButton.field_146125_m = false;
            this.fadeColorButton.field_146125_m = false;
            break;
         case GRADIENT:
            this.colorPicker.field_146125_m = true;
            this.colorSlider.field_146125_m = true;
            this.fadeSpeedSlider.field_146125_m = false;
            this.chromaSpeedSlider.field_146125_m = false;
            this.gradientColorButton.field_146125_m = true;
            this.fadeColorButton.field_146125_m = false;
            break;
         case FADE:
            this.colorPicker.field_146125_m = true;
            this.colorSlider.field_146125_m = true;
            this.fadeSpeedSlider.field_146125_m = true;
            this.chromaSpeedSlider.field_146125_m = false;
            this.gradientColorButton.field_146125_m = false;
            this.fadeColorButton.field_146125_m = true;
            break;
         case CHROMA:
            this.colorPicker.field_146125_m = false;
            this.colorSlider.field_146125_m = false;
            this.fadeSpeedSlider.field_146125_m = false;
            this.chromaSpeedSlider.field_146125_m = true;
            this.gradientColorButton.field_146125_m = false;
            this.fadeColorButton.field_146125_m = false;
      }

   }

   private void updateColorSlider() {
      this.colorPicker.setHue(this.colorSlider.getColor());
      this.opacitySlider.setColor(this.colorPicker.getColor());
      this.property.setColor(this.getColorIndex(), this.colorPicker.getColor());
      this.property.setHue(this.getColorIndex(), this.colorSlider.getValueInt());
   }

   private void updateColorPicker() {
      this.opacitySlider.setColor(this.colorPicker.getColor());
      this.property.setColor(this.getColorIndex(), this.colorPicker.getColor());
   }

   private void updateOpacitySlider() {
      this.property.setOpacity(this.getColorIndex(), this.opacitySlider.getValue());
   }

   private void updateFadeSpeedSlider() {
      this.property.fadeSpeed.setValue(this.fadeSpeedSlider.getValue());
   }

   private void updateChromaSpeedSlider() {
      this.property.chromaSpeed.setValue(this.chromaSpeedSlider.getValue());
   }

   private void updateThicknessSlider() {
      this.config.thickness.setValue(this.thicknessSlider.getValue());
   }

   private int getColorIndex() {
      ColorMode colorMode = (ColorMode)EnumUtils.fromName(ColorMode.class, this.property.colorMode.getString());
      return colorMode == ColorMode.GRADIENT ? gradientColorIndex : fadeColorIndex;
   }
}
