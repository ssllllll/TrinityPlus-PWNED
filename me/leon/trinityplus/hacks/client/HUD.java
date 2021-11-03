//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.client;

import me.leon.trinityplus.hacks.*;
import net.minecraftforge.client.event.*;
import me.leon.trinityplus.gui.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import me.leon.trinityplus.main.*;
import me.leon.trinityplus.hud.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class HUD extends Module
{
    public HUD() {
        super("HUD", "On screen overlay", Category.CLIENT);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Text event) {
        if (HUD.mc.currentScreen instanceof ClickGui || HUD.mc.gameSettings.showDebugInfo) {
            return;
        }
        final ScaledResolution sr = new ScaledResolution(HUD.mc);
        final HashMap<AnchorPoint, Point> map = new HashMap<AnchorPoint, Point>();
        map.put(AnchorPoint.TOPLEFT, new Point(0, 0));
        map.put(AnchorPoint.TOPRIGHT, new Point(sr.getScaledWidth(), 0));
        map.put(AnchorPoint.BOTTOMLEFT, new Point(0, sr.getScaledHeight()));
        map.put(AnchorPoint.BOTTOMRIGHT, new Point(sr.getScaledWidth(), sr.getScaledHeight()));
        for (final Component comp : Trinity.hudManager.comps) {
            comp.res = sr;
            float x = comp.x;
            float y = comp.y;
            if (HUDeditor.anchor.getValue() && comp.anchorPoint != null) {
                final Point p = map.get(comp.anchorPoint);
                if (comp.anchorPoint == AnchorPoint.BOTTOMLEFT) {
                    y = p.y - comp.height();
                    x = (float)p.x;
                }
                else if (comp.anchorPoint == AnchorPoint.BOTTOMRIGHT) {
                    y = p.y - comp.height();
                    x = p.x - comp.width();
                }
                else if (comp.anchorPoint == AnchorPoint.TOPRIGHT) {
                    y = (float)p.y;
                    x = p.x - comp.width();
                }
                else {
                    x = (float)map.get(comp.anchorPoint).x;
                    y = (float)map.get(comp.anchorPoint).y;
                }
            }
            comp.x = x;
            comp.y = y;
            if (comp.visible) {
                try {
                    comp.render();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private boolean intersects(final int x, final int y, final int x1, final int y1, final float x2, final float y2, final float x3, final float y3) {
        boolean found = false;
        final int[][] points = { { x, y }, { x1, y1 }, { x, y1 }, { x1, y } };
        final float[][] points2 = { { x2, y2 }, { x3, y3 }, { x3, y2 }, { x2, y3 } };
        for (final int[] point : points) {
            if (this.check(x2, y2, x2 + x3, y2 + y3, point[0], point[1])) {
                found = true;
            }
        }
        for (final float[] point2 : points2) {
            if (this.check((float)x, (float)y, (float)(x + x1), (float)(y + y1), (int)point2[0], (int)point2[1])) {
                found = true;
            }
        }
        return found;
    }
    
    private boolean check(final float x, final float y, final float x1, final float y1, final int mX, final int mY) {
        return mX >= x && mX <= x1 && mY >= y && mY <= y1;
    }
}
