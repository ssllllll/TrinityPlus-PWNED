//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.rendering;

import me.leon.trinityplus.utils.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import me.leon.trinityplus.utils.rendering.skeet.*;
import net.minecraft.client.gui.*;
import me.leon.trinityplus.utils.math.*;

public class RenderUtils implements Util
{
    private static final Tessellator tessellator;
    private static final BufferBuilder builder;
    
    public static void drawRect(final float x, final float y, final float w, final float h, final Color color) {
        final float alpha = color.getAlpha() / 255.0f;
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        prepare(1.0f, Mode.NORMAL);
        RenderUtils.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        RenderUtils.builder.pos((double)x, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.builder.pos((double)w, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.builder.pos((double)w, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.builder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.tessellator.draw();
        release(Mode.NORMAL);
    }
    
    public static void drawOutlineRect(final float x, final float y, final float w, final float h, final float width, final Color color) {
        final float alpha = color.getAlpha() / 255.0f;
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        prepare(width, Mode.NORMAL);
        RenderUtils.builder.begin(2, DefaultVertexFormats.POSITION_COLOR);
        RenderUtils.builder.pos((double)x, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.builder.pos((double)w, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.builder.pos((double)w, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.builder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.tessellator.draw();
        release(Mode.NORMAL);
    }
    
    public static void drawRainbowRectHorizontal(final float x, final float y, final float w, final float h, final float speed, final int alpha, final Color starting, final boolean it) {
        final Rainbow rainbow = new Rainbow(starting);
        prepare(1.0f, Mode.NORMAL);
        RenderUtils.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        for (float i = x; i < w; i += (float)(it ? 1.0 : 0.5)) {
            rainbow.update(speed);
            final float red = rainbow.getColor().getRed() / 255.0f;
            final float green = rainbow.getColor().getGreen() / 255.0f;
            final float blue = rainbow.getColor().getBlue() / 255.0f;
            RenderUtils.builder.pos((double)(i + (it ? 1.0f : 0.5f)), (double)h, 0.0).color(red, green, blue, (float)alpha).endVertex();
            RenderUtils.builder.pos((double)i, (double)h, 0.0).color(red, green, blue, (float)alpha).endVertex();
            RenderUtils.builder.pos((double)i, (double)y, 0.0).color(red, green, blue, (float)alpha).endVertex();
            RenderUtils.builder.pos((double)(i + (it ? 1.0f : 0.5f)), (double)y, 0.0).color(red, green, blue, (float)alpha).endVertex();
        }
        RenderUtils.tessellator.draw();
        release(Mode.NORMAL);
    }
    
    public static void drawRainbowRectHorizontal(final float x, final float y, final float w, final float h, final int speed, final Color starting, final boolean it) {
        final Rainbow rainbow = new Rainbow(starting);
        prepare(1.0f, Mode.NORMAL);
        RenderUtils.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        for (float i = x; i < w; i += (float)(it ? 1.0 : 0.5)) {
            rainbow.update(speed);
            final float red = rainbow.getColor().getRed() / 255.0f;
            final float green = rainbow.getColor().getGreen() / 255.0f;
            final float blue = rainbow.getColor().getBlue() / 255.0f;
            final float alpha = rainbow.getColor().getAlpha() / 255.0f;
            RenderUtils.builder.pos((double)(i + (it ? 1.0f : 0.5f)), (double)h, 0.0).color(red, green, blue, alpha).endVertex();
            RenderUtils.builder.pos((double)i, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
            RenderUtils.builder.pos((double)i, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
            RenderUtils.builder.pos((double)(i + (it ? 1.0f : 0.5f)), (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        }
        RenderUtils.tessellator.draw();
        release(Mode.NORMAL);
    }
    
    public static void drawRainbowRectVertical(final float x, final float y, final float w, final float h, final int speed, final int alpha, final Color starting) {
        final Rainbow rainbow = new Rainbow(starting);
        prepare(1.0f, Mode.NORMAL);
        RenderUtils.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        for (float i = y; i < h; i += 0.5) {
            rainbow.update(speed);
            final float red = rainbow.getColor().getRed() / 255.0f;
            final float green = rainbow.getColor().getGreen() / 255.0f;
            final float blue = rainbow.getColor().getBlue() / 255.0f;
            RenderUtils.builder.pos((double)(x + w), (double)(i + 0.5f), 0.0).color(red, green, blue, (float)alpha).endVertex();
            RenderUtils.builder.pos((double)x, (double)(i + 0.5f), 0.0).color(red, green, blue, (float)alpha).endVertex();
            RenderUtils.builder.pos((double)x, (double)i, 0.0).color(red, green, blue, (float)alpha).endVertex();
            RenderUtils.builder.pos((double)x, (double)i, 0.0).color(red, green, blue, (float)alpha).endVertex();
        }
        RenderUtils.tessellator.draw();
        release(Mode.NORMAL);
    }
    
    public static void drawEntityOnScreen(final int posX, final int posY, final int scale, final float mouseY, final EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0f);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-(float)Math.atan(mouseY / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        RenderUtils.mc.getRenderManager().setPlayerViewY(180.0f);
        RenderUtils.mc.getRenderManager().setRenderShadow(false);
        RenderUtils.mc.getRenderManager().renderEntity((Entity)ent, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        RenderUtils.mc.getRenderManager().setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public static void drawColorPickerSquare(final float x, final float y, final float w, final float h, final float hue, final int alpha) {
        prepare(1.0f, Mode.GRADIENT);
        RenderUtils.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        final Color starting = new Color(Color.HSBtoRGB(hue, 1.0f, 1.0f));
        RenderUtils.builder.pos((double)w, (double)y, 0.0).color(starting.getRed(), starting.getGreen(), starting.getBlue(), alpha).endVertex();
        RenderUtils.builder.pos((double)x, (double)y, 0.0).color(255, 255, 255, alpha).endVertex();
        RenderUtils.builder.pos((double)x, (double)h, 0.0).color(255, 255, 255, alpha).endVertex();
        RenderUtils.builder.pos((double)w, (double)h, 0.0).color(starting.getRed(), starting.getGreen(), starting.getBlue(), alpha).endVertex();
        RenderUtils.builder.pos((double)w, (double)y, 0.0).color(0, 0, 0, 0).endVertex();
        RenderUtils.builder.pos((double)x, (double)y, 0.0).color(0, 0, 0, 0).endVertex();
        RenderUtils.builder.pos((double)x, (double)h, 0.0).color(0, 0, 0, alpha).endVertex();
        RenderUtils.builder.pos((double)w, (double)h, 0.0).color(0, 0, 0, alpha).endVertex();
        RenderUtils.tessellator.draw();
        release(Mode.GRADIENT);
    }
    
    public static void drawAlphaRect(final float x, final float y, final float w, final float h, final Color color) {
        prepare(1.0f, Mode.NORMAL);
        RenderUtils.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        final float one = w / 255.0f;
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        for (int a = 0; a < 256; ++a) {
            final float alpha = a / 255.0f;
            final float cur = a / 255.0f * w;
            RenderUtils.builder.pos((double)(x + cur - one), (double)(y + h), 0.0).color(red, green, blue, alpha).endVertex();
            RenderUtils.builder.pos((double)(x + cur), (double)(y + h), 0.0).color(red, green, blue, alpha).endVertex();
            RenderUtils.builder.pos((double)(x + cur), (double)y, 0.0).color(red, green, blue, alpha).endVertex();
            RenderUtils.builder.pos((double)(x + cur - one), (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        }
        RenderUtils.tessellator.draw();
        release(Mode.NORMAL);
    }
    
    public static void drawHueRect(final float x, final float y, final float w, final float h) {
        prepare(1.0f, Mode.NORMAL);
        RenderUtils.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        final float oneW = w / 360.0f;
        for (int a = 0; a < 360; ++a) {
            final Color color = new Color(Color.HSBtoRGB(a / 360.0f, 1.0f, 1.0f));
            final int red = color.getRed();
            final int blue = color.getBlue();
            final int green = color.getGreen();
            final int alpha = color.getAlpha();
            final float cur = a / 360.0f * w;
            RenderUtils.builder.pos((double)(x + cur - oneW), (double)(y + h), 0.0).color(red, green, blue, alpha).endVertex();
            RenderUtils.builder.pos((double)(x + cur), (double)(y + h), 0.0).color(red, green, blue, alpha).endVertex();
            RenderUtils.builder.pos((double)(x + cur), (double)y, 0.0).color(red, green, blue, alpha).endVertex();
            RenderUtils.builder.pos((double)(x + cur - oneW), (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        }
        RenderUtils.tessellator.draw();
        release(Mode.NORMAL);
    }
    
    public static void drawLine(final float x, final float y, final float x1, final float y1, final float width, final Color color) {
        final float alpha = color.getAlpha() / 255.0f;
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        prepare(width, Mode.NORMAL);
        RenderUtils.builder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        RenderUtils.builder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.builder.pos((double)x1, (double)y1, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.tessellator.draw();
        release(Mode.NORMAL);
    }
    
    public static void drawGradientLine(final float x, final float y, final float x1, final float y1, final float width, final Color start, final Color end) {
        final float alpha = start.getAlpha() / 255.0f;
        final float red = start.getRed() / 255.0f;
        final float green = start.getGreen() / 255.0f;
        final float blue = start.getBlue() / 255.0f;
        final float alpha2 = end.getAlpha() / 255.0f;
        final float red2 = end.getRed() / 255.0f;
        final float green2 = end.getGreen() / 255.0f;
        final float blue2 = end.getBlue() / 255.0f;
        prepare(width, Mode.GRADIENT);
        RenderUtils.builder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        RenderUtils.builder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        RenderUtils.builder.pos((double)x1, (double)y1, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        RenderUtils.tessellator.draw();
        release(Mode.GRADIENT);
    }
    
    public static void drawGradientRect(final float x, final float y, final float x1, final float y1, final Color xy, final Color x1y, final Color xy1, final Color x1y1) {
        final float xyr = xy.getRed() / 255.0f;
        final float xyg = xy.getGreen() / 255.0f;
        final float xyb = xy.getBlue() / 255.0f;
        final float xya = xy.getAlpha() / 255.0f;
        final float x1yr = x1y.getRed() / 255.0f;
        final float x1yg = x1y.getGreen() / 255.0f;
        final float x1yb = x1y.getBlue() / 255.0f;
        final float x1ya = x1y.getAlpha() / 255.0f;
        final float xy1r = xy1.getRed() / 255.0f;
        final float xy1g = xy1.getGreen() / 255.0f;
        final float xy1b = xy1.getBlue() / 255.0f;
        final float xy1a = xy1.getAlpha() / 255.0f;
        final float x1y1r = x1y1.getRed() / 255.0f;
        final float x1y1g = x1y1.getGreen() / 255.0f;
        final float x1y1b = x1y1.getBlue() / 255.0f;
        final float x1y1a = x1y1.getAlpha() / 255.0f;
        prepare(1.0f, Mode.GRADIENT);
        RenderUtils.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        RenderUtils.builder.pos((double)x1, (double)y, 0.0).color(x1yr, x1yg, x1yb, x1ya).endVertex();
        RenderUtils.builder.pos((double)x, (double)y, 0.0).color(xyr, xyg, xyb, xya).endVertex();
        RenderUtils.builder.pos((double)x, (double)y1, 0.0).color(xy1r, xy1g, xy1b, xy1a).endVertex();
        RenderUtils.builder.pos((double)x1, (double)y1, 0.0).color(x1y1r, x1y1g, x1y1b, x1y1a).endVertex();
        RenderUtils.tessellator.draw();
        release(Mode.GRADIENT);
    }
    
    public static void drawCircle(final float x, final float y, final float r, final float w, final Color color) {
        prepare(w, Mode.NORMAL);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        RenderUtils.builder.begin(2, DefaultVertexFormats.POSITION_COLOR);
        final int red = color.getRed();
        final int blue = color.getBlue();
        final int green = color.getGreen();
        final int alpha = color.getAlpha();
        for (int a = 0; a < 360; ++a) {
            final double x2 = x + r * Math.sin(Math.toRadians(a));
            final double z1 = y + r * Math.cos(Math.toRadians(a));
            RenderUtils.builder.pos(x2, z1, 0.0).color(red, green, blue, alpha).endVertex();
        }
        RenderUtils.tessellator.draw();
        GlStateManager.shadeModel(7424);
        GL11.glDisable(2848);
        release(Mode.NORMAL);
    }
    
    public static void scissor(final Quad quad) {
        GL11.glPushAttrib(524288);
        GL11.glEnable(3089);
        final ScaledResolution res = new ScaledResolution(RenderUtils.mc);
        GL11.glScissor((int)quad.getX() * res.getScaleFactor(), (res.getScaledHeight() - (int)quad.getY1()) * res.getScaleFactor(), (int)quad.width() * res.getScaleFactor(), (int)quad.height() * res.getScaleFactor());
    }
    
    public static void scissor(final int x, final int y, final int x1, final int y1) {
        GL11.glPushAttrib(524288);
        GL11.glEnable(3089);
        final ScaledResolution res = new ScaledResolution(RenderUtils.mc);
        GL11.glScissor(x * res.getScaleFactor(), (res.getScaledHeight() - y1) * res.getScaleFactor(), (x1 - x) * res.getScaleFactor(), (y1 - y) * res.getScaleFactor());
    }
    
    public static void restoreScissor() {
        GL11.glPopAttrib();
        GL11.glDisable(3089);
    }
    
    public static Color lowerAlpha(final Color color, final int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)MathUtils.clamp(0.0, 255.0, (double)(color.getAlpha() - alpha)));
    }
    
    public static Color alpha(final Color color, final int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)MathUtils.clamp(0.0, 255.0, (double)alpha));
    }
    
    public static void glColor(final Color color) {
        GlStateManager.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
    }
    
    public static void prepare(final float width, final Mode mode) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.glLineWidth(width);
        if (mode == Mode.GRADIENT) {
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
        }
    }
    
    public static void release(final Mode mode) {
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        if (mode == Mode.GRADIENT) {
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
        }
    }
    
    static {
        tessellator = Tessellator.getInstance();
        builder = RenderUtils.tessellator.getBuffer();
    }
    
    public enum Mode
    {
        GRADIENT, 
        NORMAL;
    }
}
