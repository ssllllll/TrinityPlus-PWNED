//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.client;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.settings.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import java.util.function.*;

public class HUDeditor extends Module
{
    public static BooleanSetting clamp;
    public static BooleanSetting anchor;
    public static BooleanSetting background;
    public static ColorSetting color;
    public static ColorSetting textColor;
    @EventHandler
    private final Listener<EventModeChange> toggleListener;
    
    public HUDeditor() {
        super("HUDeditor", "Edit the HUD", Category.CLIENT);
        this.toggleListener = new Listener<EventModeChange>(event -> ClickGUI.updateShader(event), (Predicate<EventModeChange>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        ClickGUI.loadShader();
        this.setEnabled(false);
    }
    
    static {
        HUDeditor.clamp = new BooleanSetting("Clamp", true);
        HUDeditor.anchor = new BooleanSetting("Anchor", true);
        HUDeditor.background = new BooleanSetting("Background", true, true);
        HUDeditor.color = new ColorSetting("Color", HUDeditor.background, 97, 97, 97, 97, false);
        HUDeditor.textColor = new ColorSetting("TextColor", 255, 255, 255, 255, false);
    }
}
