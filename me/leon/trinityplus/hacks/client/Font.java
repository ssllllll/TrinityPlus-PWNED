//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.client;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import java.awt.*;
import java.util.*;
import me.leon.trinityplus.utils.misc.*;
import me.leon.trinityplus.managers.*;

public class Font extends Module
{
    public static BooleanSetting vanilla;
    public static BooleanSetting shadow;
    public static ModeSetting style;
    public static ModeSetting families;
    
    public Font() {
        super("Font", "Customize font settings", Category.CLIENT);
        this.setEnabled(true);
        final ArrayList<String> fontNames = new ArrayList<String>(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        if (!fontNames.contains("Comfortaa")) {
            fontNames.add("Comfortaa");
        }
        if (!fontNames.contains("Ubuntu")) {
            fontNames.add("Ubuntu");
        }
        if (!fontNames.contains("Quicksand")) {
            fontNames.add("Quicksand");
        }
        Font.families.setValues(fontNames);
    }
    
    @Override
    public void onUpdate() {
        if (Font.vanilla.getValue()) {
            Font.mc.fontRenderer.FONT_HEIGHT = FontUtil.getFontHeight();
        }
    }
    
    @Override
    public void onDisable() {
        Font.mc.fontRenderer.FONT_HEIGHT = 9;
    }
    
    public static boolean enabled() {
        return ModuleManager.getMod(Font.class).isEnabled();
    }
    
    static {
        Font.vanilla = new BooleanSetting("Vanilla", false);
        Font.shadow = new BooleanSetting("Shadow", true);
        Font.style = new ModeSetting("Style", "Plain", new String[] { "Plain", "Bold", "Italic", "Bold-Italic" });
        Font.families = new ModeSetting("Family", "Comfortaa", new String[] { "Quicksand", "Ubuntu" });
    }
}
