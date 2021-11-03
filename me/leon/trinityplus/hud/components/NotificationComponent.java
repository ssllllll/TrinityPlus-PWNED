//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hud.components;

import me.leon.trinityplus.hud.*;
import me.leon.trinityplus.setting.rewrite.*;
import net.minecraft.client.gui.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.utils.misc.*;
import me.leon.trinityplus.notification.*;
import me.leon.trinityplus.utils.rendering.*;
import java.util.*;
import me.leon.trinityplus.utils.rendering.skeet.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class NotificationComponent extends Component
{
    public static ColorSetting infoColor;
    public static ColorSetting warningColor;
    public static ColorSetting textColor;
    public static SliderSetting width;
    public static SliderSetting height;
    public static SliderSetting fadeInTime;
    public static SliderSetting time;
    public static SliderSetting dieTime;
    private final BezierCurve curve;
    
    public NotificationComponent() {
        super("Notifications");
        this.curve = new BezierCurve(0.0f, 175.0f, 150.0f, 200.0f);
        this.x = (float)(new ScaledResolution(this.mc).getScaledWidth() - 300);
        this.y = (float)(new ScaledResolution(this.mc).getScaledHeight() - 300);
    }
    
    public void render() {
        if (!NotificationManager.queue.isEmpty()) {
            int off = 0;
            NotificationManager.queue.forEach(e -> {
                if (e.isOverTime()) {
                    e.setMode(1);
                }
                return;
            });
            NotificationManager.queue.forEach(e -> {
                if (e.isOverTime1()) {
                    e.setMode(2);
                }
                return;
            });
            NotificationManager.queue.removeIf(Notification::isOverTime2);
            for (final Notification not : NotificationManager.queue) {
                String des = "";
                String des2 = "";
                final String[] split = not.getDescription().split(" ");
                for (int i = 0; i < split.length; ++i) {
                    final String next = (i == split.length - 1) ? null : split[i + 1];
                    if (next != null) {
                        split[i] = split[i].concat(" ");
                    }
                }
                boolean flag = false;
                for (final String s : split) {
                    if (!flag) {
                        if (FontUtil.getStringWidth(des.concat(s)) < 134.0f) {
                            des = des.concat(s);
                        }
                        else {
                            des2 = des2.concat(s);
                            flag = true;
                        }
                    }
                    else {
                        des2 = des2.concat(s);
                    }
                }
                if (not.getMode() == 2) {
                    final double deathPercent = 1.0 - not.getDeathTime() / NotificationComponent.dieTime.getValue();
                    final Quad quad = this.drawNotificationBase(off, (int)(deathPercent * 255.0));
                    if (not.getType() == NotifType.INFO) {
                        this.drawInfoIcon(quad.getX() + 6.0f, quad.getY() + 6.0f, deathPercent);
                    }
                    else {
                        this.drawWarningIcon(quad.getX() + 6.0f, quad.getY() + 6.0f, deathPercent);
                    }
                    RenderUtils.scissor(quad);
                    FontUtil.drawString(not.getName(), quad.getX() + 40.0f, quad.getY() + 6.0f, RenderUtils.lowerAlpha((not.getType() == NotifType.INFO) ? NotificationComponent.infoColor.getValue() : NotificationComponent.warningColor.getValue(), (int)(not.getDeathTime() / NotificationComponent.dieTime.getValue() * 255.0)).getRGB());
                    FontUtil.drawString(des, quad.getX() + 40.0f, quad.getY() + 10.0f + FontUtil.getFontHeight(), RenderUtils.lowerAlpha(NotificationComponent.textColor.getValue(), (int)(not.getDeathTime() / NotificationComponent.dieTime.getValue() * 255.0)).getRGB());
                    FontUtil.drawString(des2, quad.getX() + 40.0f, quad.getY() + 10.0f + FontUtil.getFontHeight() * 2, RenderUtils.lowerAlpha(NotificationComponent.textColor.getValue(), (int)(not.getDeathTime() / NotificationComponent.dieTime.getValue() * 255.0)).getRGB());
                }
                else if (not.getMode() == 0) {
                    final double fadeTime = -(not.getFadeTime() / NotificationComponent.fadeInTime.getValue());
                    final Quad quad = this.drawNotificationBase((float)this.curve.get(fadeTime), (float)off, 255);
                    if (not.getType() == NotifType.INFO) {
                        this.drawInfoIcon(quad.getX() + 6.0f, quad.getY() + 6.0f, 255.0);
                    }
                    else {
                        this.drawWarningIcon(quad.getX() + 6.0f, quad.getY() + 6.0f, 255.0);
                    }
                    RenderUtils.scissor(quad);
                    FontUtil.drawString(not.getName(), quad.getX() + 40.0f, quad.getY() + 6.0f, ((not.getType() == NotifType.INFO) ? NotificationComponent.infoColor.getValue() : NotificationComponent.warningColor.getValue()).getRGB());
                    FontUtil.drawString(des, quad.getX() + 40.0f, quad.getY() + 10.0f + FontUtil.getFontHeight(), NotificationComponent.textColor.getValue().getRGB());
                    FontUtil.drawString(des2, quad.getX() + 40.0f, quad.getY() + 10.0f + FontUtil.getFontHeight() * 2, NotificationComponent.textColor.getValue().getRGB());
                }
                else {
                    final Quad quad2 = this.drawNotificationBase(off, 255);
                    if (not.getType() == NotifType.INFO) {
                        this.drawInfoIcon(quad2.getX() + 6.0f, quad2.getY() + 6.0f, 255.0);
                    }
                    else {
                        this.drawWarningIcon(quad2.getX() + 6.0f, quad2.getY() + 6.0f, 255.0);
                    }
                    RenderUtils.scissor(quad2);
                    FontUtil.drawString(not.getName(), quad2.getX() + 40.0f, quad2.getY() + 6.0f, ((not.getType() == NotifType.INFO) ? NotificationComponent.infoColor.getValue() : NotificationComponent.warningColor.getValue()).getRGB());
                    FontUtil.drawString(des, quad2.getX() + 40.0f, quad2.getY() + 10.0f + FontUtil.getFontHeight(), NotificationComponent.textColor.getValue().getRGB());
                    FontUtil.drawString(des2, quad2.getX() + 40.0f, quad2.getY() + 10.0f + FontUtil.getFontHeight() * 2, NotificationComponent.textColor.getValue().getRGB());
                }
                RenderUtils.restoreScissor();
                off -= 64;
            }
        }
    }
    
    private Quad drawNotificationBase(final int off, final int alpha) {
        return SkeetUtils.renderSkeetBox(new Quad(this.x, this.y + off, this.x + 180.0f, this.y + 50.0f + off), alpha);
    }
    
    private Quad drawNotificationBase(final float xOff, final float off, final int alpha) {
        return SkeetUtils.renderSkeetBox(new Quad(this.x + xOff, this.y + off, this.x + 180.0f + xOff, this.y + 50.0f + off), alpha);
    }
    
    private void drawInfoIcon(final double x, final double y, final double grey) {
        try {
            this.mc.getTextureManager().bindTexture(new ResourceLocation("trinity", "info.png"));
            GlStateManager.enableTexture2D();
            GL11.glColor4d(grey, grey, grey, grey);
            this.drawTexturedRect(x, y);
        }
        catch (Exception ex) {}
    }
    
    private void drawWarningIcon(final double x, final double y, final double grey) {
        try {
            this.mc.getTextureManager().bindTexture(new ResourceLocation("trinity", "warning.png"));
            GlStateManager.enableTexture2D();
            GL11.glColor4d(grey, grey, grey, grey);
            this.drawTexturedRect(x, y);
        }
        catch (Exception ex) {}
    }
    
    private void drawTexturedRect(final double x, final double y) {
        final float f = 0.125f;
        final float f2 = 0.125f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + 30.0, 0.0).tex((double)(16.0f * f), (double)(24.0f * f2)).endVertex();
        bufferbuilder.pos(x + 30.0, y + 30.0, 0.0).tex((double)(24.0f * f), (double)(24.0f * f2)).endVertex();
        bufferbuilder.pos(x + 30.0, y, 0.0).tex((double)(24.0f * f), (double)(16.0f * f2)).endVertex();
        bufferbuilder.pos(x, y, 0.0).tex((double)(16.0f * f), (double)(16.0f * f2)).endVertex();
        tessellator.draw();
    }
    
    public float width() {
        return 180.0f;
    }
    
    public float height() {
        return 50.0f;
    }
    
    static {
        NotificationComponent.infoColor = new ColorSetting("InfoColor", 32, 140, 61, 255, false);
        NotificationComponent.warningColor = new ColorSetting("WarningColor", 255, 0, 0, 255, false);
        NotificationComponent.textColor = new ColorSetting("TextColor", 255, 255, 255, 255, false);
        NotificationComponent.width = new SliderSetting("Width", 50.0, 300.0, 600.0, true);
        NotificationComponent.height = new SliderSetting("Height", 50.0, 150.0, 400.0, true);
        NotificationComponent.fadeInTime = new SliderSetting("FadeTime", 100.0, 400.0, 2000.0, true);
        NotificationComponent.time = new SliderSetting("LiveTime", 500.0, 3500.0, 7000.0, true);
        NotificationComponent.dieTime = new SliderSetting("DieTime", 500.0, 1000.0, 2000.0, true);
    }
}
