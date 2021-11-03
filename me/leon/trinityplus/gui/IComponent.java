//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui;

import net.minecraft.client.*;
import me.leon.trinityplus.hacks.client.*;
import java.awt.*;
import me.leon.trinityplus.utils.rendering.*;

public interface IComponent
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    
    void render(final Point p0);
    
    void update(final Point p0);
    
    void unload();
    
    boolean buttonClick(final int p0, final Point p1);
    
    boolean buttonRelease(final int p0, final Point p1);
    
    boolean keyTyped(final char p0, final int p1);
    
    float height();
    
    float xOffset();
    
    IComponent parent();
    
    String description();
    
    default float getWidth() {
        return ClickGUI.width.floatValue();
    }
    
    default void drawRect(final int x, final int y, final int x1, final int y1, final Color color) {
        RenderUtils.drawRect((float)Math.min(x, x1), (float)Math.min(y, y1), (float)Math.max(x, x1), (float)Math.max(y, y1), color);
    }
    
    default void drawRect(final float x, final float y, final float x1, final float y1, final Color color) {
        RenderUtils.drawRect(Math.min(x, x1), Math.min(y, y1), Math.max(x, x1), Math.max(y, y1), color);
    }
}
