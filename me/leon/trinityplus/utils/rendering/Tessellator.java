//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.rendering;

import me.leon.trinityplus.utils.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.utils.misc.*;

public class Tessellator implements Util
{
    private static final net.minecraft.client.renderer.Tessellator tessellator;
    private static final BufferBuilder builder;
    
    public static void drawBBClawFromBlockpos(final BlockPos pos, final float width, final float height, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - Tessellator.mc.getRenderManager().viewerPosX, pos.getY() - Tessellator.mc.getRenderManager().viewerPosY, pos.getZ() - Tessellator.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - Tessellator.mc.getRenderManager().viewerPosX, pos.getY() + 1 - Tessellator.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - Tessellator.mc.getRenderManager().viewerPosZ);
        drawBBClaw(bb, width, height, color);
    }
    
    public static void drawBBFillFromBlockpos(final BlockPos pos, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - Tessellator.mc.getRenderManager().viewerPosX, pos.getY() - Tessellator.mc.getRenderManager().viewerPosY, pos.getZ() - Tessellator.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - Tessellator.mc.getRenderManager().viewerPosX, pos.getY() + 1 - Tessellator.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - Tessellator.mc.getRenderManager().viewerPosZ);
        drawBBFill(bb, color);
    }
    
    public static void drawBBSlabFromBlockpos(final BlockPos pos, final float height, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - Tessellator.mc.getRenderManager().viewerPosX, pos.getY() - Tessellator.mc.getRenderManager().viewerPosY, pos.getZ() - Tessellator.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - Tessellator.mc.getRenderManager().viewerPosX, pos.getY() + 1 - Tessellator.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - Tessellator.mc.getRenderManager().viewerPosZ);
        drawBBSlab(bb, height, color);
    }
    
    public static void drawBBOutlineFromBlockpos(final BlockPos pos, final float width, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - Tessellator.mc.getRenderManager().viewerPosX, pos.getY() - Tessellator.mc.getRenderManager().viewerPosY, pos.getZ() - Tessellator.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - Tessellator.mc.getRenderManager().viewerPosX, pos.getY() + 1 - Tessellator.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - Tessellator.mc.getRenderManager().viewerPosZ);
        drawBBOutline(bb, width, color);
    }
    
    public static void drawBBClaw(final AxisAlignedBB bb, final float width, final float height, final Color color) {
        GlStateManager.pushMatrix();
        start1();
        width(width);
        final double minX = bb.minX;
        final double minY = bb.minY;
        final double minZ = bb.minZ;
        final double maxX = bb.maxX;
        final double maxY = bb.maxY;
        final double maxZ = bb.maxZ;
        Tessellator.builder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        vertex(minX, minY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX, minY, minZ + height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, minY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX, minY, maxZ - height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX, minY, minZ + height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX, minY, maxZ - height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, minY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX + height, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, minY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX + height, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX - height, minY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX - height, minY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, minY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX, minY + height, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, minY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX, minY + height, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX, minY + height, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, minY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX, minY + height, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX, maxY, minZ + height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX, maxY, maxZ - height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX, maxY, minZ + height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX, maxY, maxZ - height).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX + height, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX + height, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX - height, maxY, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX - height, maxY, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX, maxY - height, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(minX, maxY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(minX, maxY - height, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, minZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX, maxY - height, minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        vertex(maxX, maxY, maxZ).color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.0f).endVertex();
        vertex(maxX, maxY - height, maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        Tessellator.tessellator.draw();
        end1();
        GlStateManager.popMatrix();
    }
    
    public static void drawBBFill(final AxisAlignedBB bb, final Color color) {
        GlStateManager.pushMatrix();
        start1();
        width(1.0f);
        final int r = color.getRed();
        final int b = color.getBlue();
        final int g = color.getGreen();
        final int a = color.getAlpha();
        Tessellator.builder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        final double minX = bb.minX;
        final double minY = bb.minY;
        final double minZ = bb.minZ;
        final double maxX = bb.maxX;
        final double maxY = bb.maxY;
        final double maxZ = bb.maxZ;
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, maxZ).color(r, g, b, a).endVertex();
        Tessellator.tessellator.draw();
        end1();
        GlStateManager.popMatrix();
    }
    
    public static void drawBBSlab(final AxisAlignedBB bb, final float height, final Color color) {
        final int r = color.getRed();
        final int g = color.getGreen();
        final int b = color.getBlue();
        final int a = color.getAlpha();
        final double minX = bb.minX;
        final double minY = bb.minY;
        final double minZ = bb.minZ;
        final double maxX = bb.maxX;
        final double maxY = bb.maxY + height;
        final double maxZ = bb.maxZ;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GL11.glDisable(2929);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableCull();
        GlStateManager.shadeModel(7425);
        Tessellator.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        vertex(minX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(maxX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(minX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(minX, minY, minZ).color(r, g, b, a).endVertex();
        vertex(minX, minY, maxZ).color(r, g, b, a).endVertex();
        vertex(minX, maxY, maxZ).color(0, 0, 0, 0).endVertex();
        vertex(minX, maxY, minZ).color(0, 0, 0, 0).endVertex();
        Tessellator.tessellator.draw();
        GL11.glEnable(2929);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
    
    public static void drawBBOutline(final AxisAlignedBB bb, final float width, final Color color) {
        start(width);
        final int r = color.getRed();
        final int b = color.getBlue();
        final int g = color.getGreen();
        final int a = color.getAlpha();
        Tessellator.builder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        vertex(bb.minX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.maxY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.maxY, bb.maxZ, r, g, b, a);
        vertex(bb.minX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.maxY, bb.maxZ, r, g, b, a);
        vertex(bb.minX, bb.maxY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.maxY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.maxY, bb.minZ, r, g, b, a);
        vertex(bb.maxX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.maxX, bb.maxY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.maxY, bb.minZ, r, g, b, a);
        Tessellator.tessellator.draw();
        end();
    }
    
    public static void drawGradientAlphaCubeOutline(final AxisAlignedBB bb, final float width, final Color start) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        width(width);
        final int r = start.getRed();
        final int b = start.getBlue();
        final int g = start.getGreen();
        final int a = start.getAlpha();
        Tessellator.builder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        vertex(bb.minX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.minX, bb.maxY, bb.minZ, r, g, b, 0);
        vertex(bb.minX, bb.maxY, bb.maxZ, r, g, b, 0);
        vertex(bb.minX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.minY, bb.maxZ, r, g, b, a);
        vertex(bb.maxX, bb.maxY, bb.maxZ, r, g, b, 0);
        vertex(bb.minX, bb.maxY, bb.maxZ, r, g, b, 0);
        vertex(bb.maxX, bb.maxY, bb.maxZ, r, g, b, 0);
        vertex(bb.maxX, bb.maxY, bb.minZ, r, g, b, 0);
        vertex(bb.maxX, bb.minY, bb.minZ, r, g, b, a);
        vertex(bb.maxX, bb.maxY, bb.minZ, r, g, b, 0);
        vertex(bb.minX, bb.maxY, bb.minZ, r, g, b, 0);
        Tessellator.tessellator.draw();
        GlStateManager.enableDepth();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void drawTracerLine(final Entity entity, final RenderWorldLastEvent event, final Color color, final float width, final boolean extra) {
        start2();
        GlStateManager.glLineWidth(width);
        GlStateManager.disableDepth();
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.getPartialTicks() - Tessellator.mc.renderManager.renderPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.getPartialTicks() - Tessellator.mc.renderManager.renderPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.getPartialTicks() - Tessellator.mc.renderManager.renderPosZ;
        final Vec3d eyeVector = ActiveRenderInfo.getCameraPosition();
        Tessellator.builder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        Tessellator.builder.pos(eyeVector.x, eyeVector.y, eyeVector.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        Tessellator.builder.pos(x, y, z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        if (extra) {
            Tessellator.builder.pos(x, y, z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
            Tessellator.builder.pos(x, y + entity.height, z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        }
        Tessellator.tessellator.draw();
        end2();
    }
    
    public static void drawLine(final double posX, final double posY, final double posZ, final double posX1, final double posY1, final double posZ1, final Color color, final float width) {
        start2();
        width(width);
        GlStateManager.disableDepth();
        Tessellator.builder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        Tessellator.builder.pos(posX1, posY1, posZ1).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        Tessellator.builder.pos(posX, posY, posZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        Tessellator.tessellator.draw();
        end2();
    }
    
    public static void drawTextFromBlock(final BlockPos pos, final String text, final int color, final float scale) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(pos.x + 0.5f, pos.y + 0.5f, pos.z + 0.5f, Tessellator.mc.renderViewEntity, scale);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(Tessellator.mc.fontRenderer.getStringWidth(text) / 2.0), 0.0, 0.0);
        FontUtil.drawString(text, 0.0f, 0.0f, color);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }
    
    public static void drawTextFromBlockWithBackground(final BlockPos pos, final String text, final int color, final float scale, final Color bColor, final boolean border, final float width) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(pos.x + 0.5f, pos.y + 0.5f, pos.z + 0.5f, Tessellator.mc.renderViewEntity, scale);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(Tessellator.mc.fontRenderer.getStringWidth(text) / 2.0), 0.0, 0.0);
        if (border) {
            RenderUtils.drawOutlineRect(-1.0f, -1.0f, FontUtil.getStringWidth(text) + 1.0f, (float)(FontUtil.getFontHeight() - 2), width, bColor);
        }
        RenderUtils.drawRect(-1.0f, -1.0f, FontUtil.getStringWidth(text) + 1.0f, (float)(FontUtil.getFontHeight() - 2), bColor);
        FontUtil.drawString(text, 0.0f, 0.0f, color);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }
    
    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final Entity player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.getDistance((double)x, (double)y, (double)z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }
    
    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.translate(x - Tessellator.mc.getRenderManager().renderPosX, y - Tessellator.mc.getRenderManager().renderPosY, z - Tessellator.mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-Tessellator.mc.renderViewEntity.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(Tessellator.mc.renderViewEntity.rotationPitch, (Tessellator.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-0.02666667f, -0.02666667f, 0.02666667f);
    }
    
    public static void start(final float width) {
        GlStateManager.pushMatrix();
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        width(width);
    }
    
    public static void start1() {
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
    }
    
    public static void start2() {
        GlStateManager.pushMatrix();
        width(1.0f);
        GL11.glEnable(2848);
        GL11.glEnable(34383);
        GL11.glHint(3154, 4354);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
    }
    
    public static void end2() {
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GL11.glDisable(34383);
        GL11.glDisable(2848);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        width(1.0f);
        GlStateManager.popMatrix();
    }
    
    public static void end1() {
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void end() {
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glEnable(2884);
        GlStateManager.popMatrix();
    }
    
    private static void width(final float width) {
        GlStateManager.glLineWidth(width);
    }
    
    private static void vertex(final double x, final double y, final double z, final int r, final int g, final int b, final int a) {
        Tessellator.builder.pos(x - Tessellator.mc.getRenderManager().viewerPosX, y - Tessellator.mc.getRenderManager().viewerPosY, z - Tessellator.mc.getRenderManager().viewerPosZ).color(r, g, b, a).endVertex();
    }
    
    private static void vertex(final int r, final int g, final int b, final int a) {
        Tessellator.builder.pos(0.0, (double)Tessellator.mc.player.getEyeHeight(), 0.0).color(r, g, b, a).endVertex();
    }
    
    private static BufferBuilder vertex(final double x, final double y, final double z) {
        return Tessellator.builder.pos(x - Tessellator.mc.getRenderManager().viewerPosX, y - Tessellator.mc.getRenderManager().viewerPosY, z - Tessellator.mc.getRenderManager().viewerPosZ);
    }
    
    public static void color(final Color color) {
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    static {
        tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
        builder = Tessellator.tessellator.getBuffer();
    }
}
