//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.rendering.skeet;

import me.leon.trinityplus.utils.*;
import java.awt.*;

public class SkeetUtils implements Util
{
    public static Quad renderSkeetBox(final Quad quad) {
        return renderSkeetBox(quad, 255);
    }
    
    public static Quad renderSkeetBox(final Quad quad, final int alpha) {
        SkeetRenderer.drawBox(quad, new Color(25, 26, 26, alpha));
        quad.shrink(0.5f);
        SkeetRenderer.drawBox(quad, new Color(45, 45, 45, alpha));
        quad.shrink(0.5f);
        SkeetRenderer.drawBox(quad, new Color(51, 51, 51, alpha));
        quad.shrink(0.5f);
        SkeetRenderer.drawBox(quad, new Color(40, 40, 40, alpha));
        quad.shrink(1.5f);
        SkeetRenderer.drawBox(quad, new Color(51, 51, 51, alpha));
        quad.shrink(0.5f);
        SkeetRenderer.drawBox(quad, new Color(45, 45, 45, alpha));
        quad.shrink(0.5f);
        SkeetRenderer.drawBox(quad, new Color(16, 16, 16, alpha));
        final Quad rainbow = new Quad(quad.getX(), quad.getY(), quad.getX1(), quad.getY() + 0.5f);
        final Quad rainbow2 = new Quad(quad.getX(), quad.getY() + 0.5f, quad.getX1(), quad.getY() + 1.0f);
        SkeetRenderer.drawRainbowX(rainbow, 193.0f, 0.5f, 0.87f, 1.0f, 2.0f, alpha);
        SkeetRenderer.drawRainbowX(rainbow2, 193.0f, 0.5f, 0.5f, 1.0f, 2.0f, alpha);
        return quad;
    }
}
