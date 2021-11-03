//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui;

import me.leon.trinityplus.gui.particle.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.gui.frame.*;
import me.leon.trinityplus.hacks.client.*;
import net.minecraft.client.gui.*;
import me.leon.trinityplus.utils.rendering.*;
import java.awt.*;
import me.leon.trinityplus.utils.misc.*;
import java.util.*;
import java.io.*;
import org.lwjgl.input.*;

public class ClickGui extends GuiScreen
{
    private static ArrayList<IComponent> totalComponents;
    private static ArrayList<IFrame> frames;
    private static IComponent hovered;
    private static long endTime;
    private static long rawTime;
    private static float todo;
    private static final BezierCurve curve;
    private final ParticleSystem particleSystem;
    
    public ClickGui() {
        this.particleSystem = new ParticleSystem(100);
        ClickGui.totalComponents = new ArrayList<IComponent>();
        ClickGui.frames = new ArrayList<IFrame>();
        int x = 10;
        for (final Category value : Category.values()) {
            ClickGui.frames.add(new FrameComponent(value, (float)x, 10.0f));
            x += ClickGUI.width.intValue() + 3;
        }
    }
    
    public void initGui() {
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution sc = new ScaledResolution(this.mc);
        if (ClickGUI.background.is("Darken", "Both")) {
            this.drawDefaultBackground();
        }
        if (ClickGUI.gradient.getValue()) {
            RenderUtils.drawGradientRect(0.0f, 0.0f, (float)sc.getScaledWidth(), (float)sc.getScaledHeight(), ClickGUI.topLeftColor.getValue(), ClickGUI.topRightColor.getValue(), ClickGUI.bottomLeftColor.getValue(), ClickGUI.bottomRightColor.getValue());
        }
        if (ClickGUI.particlemode.is("Normal")) {
            this.particleSystem.tick(10);
            this.particleSystem.render();
        }
        ClickGui.hovered = null;
        this.scroll();
        for (final IFrame c : ClickGui.frames) {
            final Point p = new Point(mouseX, mouseY);
            c.update(p);
            c.render(p);
        }
        if (ClickGui.hovered != null) {
            FontUtil.drawString(ClickGui.hovered.description(), (float)(mouseX + 3), (float)(mouseY + 3), Color.WHITE);
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final IFrame c : ClickGui.frames) {
            if (c.buttonClick(mouseButton, new Point(mouseX, mouseY))) {
                break;
            }
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        if (keyCode == 18) {
            ClickGui.frames.forEach(e -> e.setY(10.0f));
        }
        if (keyCode == 1) {
            for (final IFrame c : ClickGui.frames) {
                c.unload();
            }
            this.mc.displayGuiScreen((GuiScreen)null);
            ClickGUI.stopShader();
            return;
        }
        for (final IFrame c : ClickGui.frames) {
            c.keyTyped(typedChar, keyCode);
        }
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final IFrame c : ClickGui.frames) {
            c.buttonRelease(state, new Point(mouseX, mouseY));
        }
    }
    
    public boolean doesGuiPauseGame() {
        return ClickGUI.pause.getValue();
    }
    
    private void scroll() {
        if (ClickGUI.scroll.getValue()) {
            final float x = (float)(Mouse.getDWheel() * ClickGUI.scrollSpeed.intValue() * 0.1);
            ClickGui.frames.forEach(e -> e.setY(e.getY() + x));
        }
    }
    
    public static ArrayList<IComponent> getTotalComponents() {
        return ClickGui.totalComponents;
    }
    
    public static ArrayList<IFrame> getFrames() {
        return ClickGui.frames;
    }
    
    static {
        ClickGui.endTime = 0L;
        ClickGui.rawTime = 0L;
        ClickGui.todo = 0.0f;
        curve = new BezierCurve(0.0f, 120.0f, 120.0f, 0.0f);
    }
}
