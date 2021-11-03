//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.client;

import me.leon.trinityplus.setting.rewrite.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.events.settings.*;
import me.leon.trinityplus.hacks.*;
import java.util.function.*;
import me.leon.trinityplus.events.*;
import net.minecraft.util.*;
import me.leon.trinityplus.main.*;
import net.minecraft.client.gui.*;

public class ClickGUI extends Module
{
    public static BooleanSetting main;
    public static ModeSetting background;
    public static BooleanSetting gradient;
    public static ColorSetting topLeftColor;
    public static ColorSetting topRightColor;
    public static ColorSetting bottomLeftColor;
    public static ColorSetting bottomRightColor;
    public static BooleanSetting scroll;
    public static SliderSetting scrollSpeed;
    public static BooleanSetting pause;
    public static BooleanSetting frame;
    public static ColorSetting frameColor;
    public static ColorSetting nameColorFrame;
    public static ModeSetting frameSeparator;
    public static ColorSetting separatorColor;
    public static SliderSetting width;
    public static BooleanSetting button;
    public static ColorSetting nameColorButton;
    public static ColorSetting disabledColor;
    public static ColorSetting enabledColor;
    public static BooleanSetting settings;
    public static ColorSetting backgroundColor;
    public static ColorSetting settingNameColor;
    public static ColorSetting disabledBooleanColor;
    public static ColorSetting enabledBooleanColor;
    public static ColorSetting sliderColor;
    public static ModeSetting barMode;
    public static ColorSetting barColor;
    public static BooleanSetting gear;
    public static ModeSetting mode;
    public static ColorSetting gearColor;
    public static BooleanSetting particle;
    public static ModeSetting particlemode;
    public static ColorSetting particleColor;
    @EventHandler
    private final Listener<EventModeChange> toggleListener;
    @EventHandler
    private final Listener<EventLoadConfig> loadPresetListener;
    
    public ClickGUI() {
        super("ClickGUI", "The ClickGUI of the client", Category.CLIENT);
        this.toggleListener = new Listener<EventModeChange>(event -> updateShader(event), (Predicate<EventModeChange>[])new Predicate[0]);
        this.loadPresetListener = new Listener<EventLoadConfig>(event -> {
            if (event.getStage() == EventStage.PRE) {
                stopShader();
            }
            else if (event.getStage() == EventStage.POST) {
                loadShader();
            }
        }, (Predicate<EventLoadConfig>[])new Predicate[0]);
    }
    
    public static void loadShader() {
        if (ClickGUI.background.getValue().equalsIgnoreCase("Blur") || ClickGUI.background.getValue().equalsIgnoreCase("Both")) {
            ClickGUI.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }
    
    public static void stopShader() {
        if (ClickGUI.background.getValue().equalsIgnoreCase("Blur") || ClickGUI.background.getValue().equalsIgnoreCase("Both") || ClickGUI.background.getValue().equalsIgnoreCase("None")) {
            ClickGUI.mc.entityRenderer.stopUseShader();
        }
    }
    
    public static void updateShader(final EventModeChange event) {
        if (event.getStage() != EventStage.POST) {
            return;
        }
        if (event.getSet() == ClickGUI.background) {
            if (!ClickGUI.background.getValue().equalsIgnoreCase("Blur") && !ClickGUI.background.getValue().equalsIgnoreCase("Both")) {
                ClickGUI.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            else if (ClickGUI.background.getValue().equalsIgnoreCase("Blur") || ClickGUI.background.getValue().equalsIgnoreCase("Both")) {
                ClickGUI.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
            }
        }
    }
    
    @Override
    public void onEnable() {
        ClickGUI.mc.displayGuiScreen((GuiScreen)Trinity.clickGui);
        loadShader();
        this.setEnabled(false);
    }
    
    @Override
    public void onUpdate() {
        if (ClickGUI.mc.world == null) {
            this.setEnabled(false);
        }
    }
    
    @Override
    public boolean shouldSave() {
        return false;
    }
    
    static {
        ClickGUI.main = new BooleanSetting("Main", true, false);
        ClickGUI.background = new ModeSetting("Background", ClickGUI.main, "Blur", new String[] { "Blur", "None", "Darken", "Both" });
        ClickGUI.gradient = new BooleanSetting("Gradient", ClickGUI.main, true, true);
        ClickGUI.topLeftColor = new ColorSetting("Top Left Color", ClickGUI.gradient, 181, 77, 235, 128, false);
        ClickGUI.topRightColor = new ColorSetting("Top Right Color", ClickGUI.gradient, 34, 40, 221, 164, true);
        ClickGUI.bottomLeftColor = new ColorSetting("Bottom Left Color", ClickGUI.gradient, 181, 77, 235, 128, false);
        ClickGUI.bottomRightColor = new ColorSetting("Bottom Right Color", ClickGUI.gradient, 34, 40, 221, 164, true);
        ClickGUI.scroll = new BooleanSetting("Scroll", ClickGUI.main, true);
        ClickGUI.scrollSpeed = new SliderSetting("Speed", ClickGUI.scroll, 0.0, 2.0, 50.0, true);
        ClickGUI.pause = new BooleanSetting("Pause Game", ClickGUI.main, false);
        ClickGUI.frame = new BooleanSetting("Frame", true, false);
        ClickGUI.frameColor = new ColorSetting("Frame Color", ClickGUI.frame, 194, 45, 148, 178, true);
        ClickGUI.nameColorFrame = new ColorSetting("Frame Name", ClickGUI.frame, 169, 183, 198, 255, false);
        ClickGUI.frameSeparator = new ModeSetting("Separator", ClickGUI.frame, "None", new String[] { "None", "Normal" });
        ClickGUI.separatorColor = new ColorSetting("Separator Color", ClickGUI.frame, 255, 255, 255, 255, true);
        ClickGUI.width = new SliderSetting("Width", ClickGUI.frame, 100.0, 121.0, 200.0, true);
        ClickGUI.button = new BooleanSetting("Button", true, false);
        ClickGUI.nameColorButton = new ColorSetting("Button Name", ClickGUI.button, 169, 183, 198, 255, false);
        ClickGUI.disabledColor = new ColorSetting("Disabled Color", ClickGUI.button, 43, 43, 43, 200, false);
        ClickGUI.enabledColor = new ColorSetting("Enabled Color", ClickGUI.button, 97, 97, 97, 200, true);
        ClickGUI.settings = new BooleanSetting("Settings", true, false);
        ClickGUI.backgroundColor = new ColorSetting("Background Color", ClickGUI.settings, 43, 43, 43, 200, false);
        ClickGUI.settingNameColor = new ColorSetting("Setting Name", ClickGUI.settings, 169, 183, 198, 255, false);
        ClickGUI.disabledBooleanColor = new ColorSetting("Disabled Color", ClickGUI.settings, 43, 43, 43, 200, false);
        ClickGUI.enabledBooleanColor = new ColorSetting("Enabled Color", ClickGUI.settings, 97, 97, 97, 200, true);
        ClickGUI.sliderColor = new ColorSetting("Slider Color", ClickGUI.settings, 97, 97, 97, 200, false);
        ClickGUI.barMode = new ModeSetting("Bar Mode", ClickGUI.settings, "Rainbow", new String[] { "Rainbow", "Static", "None" });
        ClickGUI.barColor = new ColorSetting("Bar Color", ClickGUI.settings, 217, 217, 217, 200, true);
        ClickGUI.gear = new BooleanSetting("Gear", true, false);
        ClickGUI.mode = new ModeSetting("Gear", ClickGUI.gear, "...", new String[] { "...", "Arrow", "None" });
        ClickGUI.gearColor = new ColorSetting("Gear Color", ClickGUI.gear, 255, 255, 255, 255, false);
        ClickGUI.particle = new BooleanSetting("Particle", true, false);
        ClickGUI.particlemode = new ModeSetting("Mode", ClickGUI.particle, "Normal", new String[] { "Normal", "None" });
        ClickGUI.particleColor = new ColorSetting("Particle Color", ClickGUI.particle, 194, 45, 148, 255, true);
    }
}
