//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.rendering.skeet;

import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import me.leon.trinityplus.utils.rendering.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class SkeetRenderer
{
    private static final Tessellator tessellator;
    private static final BufferBuilder builder;
    
    public static void drawBox(final Quad quad, final Color color) {
        setup();
        final double x = Math.min(quad.getX(), quad.getX1());
        final double y = Math.min(quad.getY(), quad.getY1());
        final double x2 = Math.max(quad.getX(), quad.getX1());
        final double y2 = Math.max(quad.getY(), quad.getY1());
        SkeetRenderer.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        SkeetRenderer.builder.pos(x2, y, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        SkeetRenderer.builder.pos(x, y, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        SkeetRenderer.builder.pos(x, y2, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        SkeetRenderer.builder.pos(x2, y2, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        SkeetRenderer.tessellator.draw();
        end();
    }
    
    public static void drawRainbowX(final Quad quad, final float hue, final float sat, final float bright, final float speed, final int alpha) {
        drawRainbowX(quad, hue, sat, bright, speed, 0.5f, alpha);
    }
    
    public static void drawRainbowX(final Quad quad, final float hue, final float sat, final float bright, final float speed, final float pixelSpeed, final int alpha) {
        final Rainbow rainbow = new Rainbow();
        rainbow.update(hue);
        for (float a = quad.getX() + pixelSpeed; a <= quad.getX1(); a += pixelSpeed) {
            rainbow.update(speed);
            final Color color = rainbow.getColor(0.0f, sat, bright);
            drawBox(new Quad(a - pixelSpeed, quad.getY(), a, quad.getY1()), alpha(alpha, color));
        }
    }
    
    public static Color alpha(final int alpha, final Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
    
    private static void setup() {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }
    
    private static void setupGradient() {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(6406);
        GL11.glDisable(2929);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glDisable(2884);
        GlStateManager.shadeModel(7425);
    }
    
    private static void endGradient() {
        GL11.glEnable(2929);
        GlStateManager.shadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glEnable(6406);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    private static void end() {
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }
    
    static {
        tessellator = Tessellator.getInstance();
        builder = SkeetRenderer.tessellator.getBuffer();
    }
}
