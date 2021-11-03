//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.misc;

import me.leon.trinityplus.utils.*;
import java.awt.*;
import java.io.*;

public class FontUtil implements Util
{
    private static CFontRenderer fontRenderer;
    private static final Font comfortaa;
    private static final Font ubuntu;
    private static final Font quicksand;
    
    public static void load() {
    }
    
    public static float drawString(final String text, final float x, final float y, final Color color) {
        return drawString(text, x, y, color, me.leon.trinityplus.hacks.client.Font.shadow.getValue());
    }
    
    public static float drawString(final String text, final float x, final float y, final int color) {
        return drawString(text, x, y, color, me.leon.trinityplus.hacks.client.Font.shadow.getValue());
    }
    
    public static float drawString(final String text, final float x, final float y, final Color color, final boolean shadow) {
        final CFontRenderer renderer = getFontRenderer();
        if (renderer == null) {
            if (shadow) {
                return (float)FontUtil.mc.fontRenderer.drawStringWithShadow(text, (float)(int)x, (float)(int)y, color.getRGB());
            }
            return (float)FontUtil.mc.fontRenderer.drawString(text, (int)x, (int)y, color.getRGB());
        }
        else {
            if (shadow) {
                return getFontRenderer().drawStringWithShadow(text, (double)x, (double)y, color);
            }
            return getFontRenderer().drawString(text, x, y, color);
        }
    }
    
    public static float drawString(final String text, final float x, final float y, final int color, final boolean shadow) {
        final CFontRenderer renderer = getFontRenderer();
        if (renderer == null) {
            if (shadow) {
                return (float)FontUtil.mc.fontRenderer.drawStringWithShadow(text, (float)(int)x, (float)(int)y, color);
            }
            return (float)FontUtil.mc.fontRenderer.drawString(text, (int)x, (int)y, color);
        }
        else {
            if (shadow) {
                return getFontRenderer().drawStringWithShadow(text, (double)x, (double)y, new Color(color));
            }
            return getFontRenderer().drawString(text, x, y, new Color(color));
        }
    }
    
    public static float getStringWidth(final String string) {
        if (string.isEmpty()) {
            return 0.0f;
        }
        final CFontRenderer renderer = getFontRenderer();
        if (renderer != null) {
            return (float)renderer.getStringWidth(string);
        }
        return (float)FontUtil.mc.fontRenderer.getStringWidth(string);
    }
    
    public static int getFontHeight() {
        final CFontRenderer renderer = getFontRenderer();
        if (renderer != null) {
            return renderer.getHeight();
        }
        return FontUtil.mc.fontRenderer.FONT_HEIGHT;
    }
    
    private static CFontRenderer getFontRenderer() {
        if (me.leon.trinityplus.hacks.client.Font.enabled()) {
            if (FontUtil.fontRenderer == null || !me.leon.trinityplus.hacks.client.Font.families.getValue().equals(FontUtil.fontRenderer.fontName) || getStyle(me.leon.trinityplus.hacks.client.Font.style.getValue()) != FontUtil.fontRenderer.font.getStyle()) {
                final CFontRenderer renderer = FontUtil.fontRenderer = new CFontRenderer(getFont(), true, true);
                return renderer;
            }
            if (me.leon.trinityplus.hacks.client.Font.families.getValue().equals(FontUtil.fontRenderer.fontName)) {
                return FontUtil.fontRenderer;
            }
        }
        return null;
    }
    
    private static Font getFont(final String fontName, final float size) {
        try {
            final InputStream inputStream = FontUtil.class.getResourceAsStream("/assets/trinity/fonts/" + fontName);
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(getStyle(me.leon.trinityplus.hacks.client.Font.style.getValue()), size);
            inputStream.close();
            return awtClientFont;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Font("default", 0, (int)size);
        }
    }
    
    private static Font getFont() {
        final String value = me.leon.trinityplus.hacks.client.Font.families.getValue();
        switch (value) {
            case "Comfortaa": {
                return FontUtil.comfortaa.deriveFont(getStyle(me.leon.trinityplus.hacks.client.Font.style.getValue()), 18.0f);
            }
            case "Ubuntu": {
                return FontUtil.ubuntu.deriveFont(getStyle(me.leon.trinityplus.hacks.client.Font.style.getValue()), 18.0f);
            }
            case "Quicksand": {
                return FontUtil.quicksand.deriveFont(getStyle(me.leon.trinityplus.hacks.client.Font.style.getValue()), 18.0f);
            }
            default: {
                return new Font(me.leon.trinityplus.hacks.client.Font.families.getValue(), getStyle(me.leon.trinityplus.hacks.client.Font.style.getValue()), 18);
            }
        }
    }
    
    private static int getStyle(final String name) {
        switch (name) {
            case "Bold": {
                return 1;
            }
            case "Italic": {
                return 2;
            }
            case "Bold-Italic": {
                return 3;
            }
            default: {
                return 0;
            }
        }
    }
    
    static {
        FontUtil.fontRenderer = null;
        comfortaa = getFont("comfortaa.ttf", 1.0f);
        ubuntu = getFont("Ubuntu.ttf", 1.0f);
        quicksand = getFont("Quicksand.ttf", 1.0f);
    }
}
