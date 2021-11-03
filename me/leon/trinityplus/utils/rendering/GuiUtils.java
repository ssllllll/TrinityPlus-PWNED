//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.rendering;

import me.leon.trinityplus.utils.*;
import me.leon.trinityplus.gui.*;
import java.util.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.gui.frame.*;
import java.awt.*;
import me.leon.trinityplus.utils.math.*;
import me.leon.trinityplus.setting.rewrite.*;

public class GuiUtils implements Util
{
    public static ArrayList<IComponent> findSubComps(final IComponent comp) {
        final ArrayList<IComponent> toReturn = new ArrayList<IComponent>();
        for (final IComponent c : ClickGui.getTotalComponents()) {
            if (c.parent() == comp) {
                toReturn.add(c);
            }
        }
        return toReturn;
    }
    
    public static IFrame findFrame(final Category c) {
        for (final IFrame a : ClickGui.getFrames()) {
            if (a.getCategory() == c) {
                return a;
            }
        }
        return null;
    }
    
    public static IFrame findFrame(final String name) {
        for (final IFrame a : ClickGui.getFrames()) {
            if (a.getCategory().name().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }
    
    public static boolean onButton(final float x, final float y, final float x1, final float y1, final Point point) {
        return point.x >= x && point.x <= x1 && point.y >= y && point.y <= y1;
    }
    
    public static double slider(final double min, final double max, final int mousePos, final float x, final float width, final int places) {
        final double diff = Math.min(width, Math.max(0.0f, mousePos - x));
        return MathUtils.roundToPlace(diff / width * (max - min) + min, places);
    }
    
    public static void slider(final SliderSetting set, final int mousePos, final float x, final float width) {
        final double diff = Math.min(width, Math.max(0.0f, mousePos - x));
        final double min = set.getMin();
        final double max = set.getMax();
        final double newValue = MathUtils.roundToPlace(diff / width * (max - min) + min, set.isOnlyInt() ? 0 : 2);
        set.setValue(newValue);
    }
    
    public static float sliderWidth(final float min, final float value, final float max, final float width) {
        return (value - min) / (max - min) * width;
    }
}
