//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hud.components;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.hud.*;
import java.util.*;
import me.leon.trinityplus.managers.*;
import com.mojang.realmsclient.gui.*;
import me.leon.trinityplus.utils.misc.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import me.leon.trinityplus.utils.rendering.*;
import me.leon.trinityplus.utils.math.*;

public class ModuleListComponent extends Component
{
    public static ModeSetting mode;
    public static ModeSetting background;
    public static BooleanSetting line;
    public static ColorSetting color;
    public static ColorSetting BColor;
    private static int yL;
    private final Alpha alpha;
    private HashMap<Module, Float> map;
    private boolean updated;
    
    public ModuleListComponent() {
        super("ModuleList");
        this.alpha = new Alpha();
        this.map = new HashMap<Module, Float>();
        this.updated = false;
        this.visible = true;
        this.x = (float)this.res.getScaledWidth();
        this.y = 0.0f;
        this.anchorPoint = AnchorPoint.TOPRIGHT;
    }
    
    public static HashMap<Module, Float> sortByValue(final HashMap<Module, Float> hm, final boolean up) {
        final List<Map.Entry<Module, Float>> list = new LinkedList<Map.Entry<Module, Float>>(hm.entrySet());
        if (up) {
            list.sort((Comparator<? super Map.Entry<Module, Float>>)Map.Entry.comparingByValue());
        }
        else {
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        }
        final HashMap<Module, Float> temp = new LinkedHashMap<Module, Float>();
        for (final Map.Entry<Module, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    
    public void render() {
        if (ModuleListComponent.background.getValue().equalsIgnoreCase("Fill")) {
            this.drawBackground();
        }
        ModuleListComponent.yL = 0;
        this.map.clear();
        final int alphaBefore = this.alpha.alpha;
        final boolean up = this.alpha.up;
        for (final Module mod : ModuleManager.modules) {
            if (mod.isEnabled() && mod.isVisible()) {
                String name = mod.getName();
                if (mod.getHudInfo() != null) {
                    name = name.concat(" " + ChatFormatting.GRAY + "[" + ChatFormatting.RESET + mod.getHudInfo() + ChatFormatting.GRAY + "]" + ChatFormatting.RESET);
                }
                this.map.put(mod, FontUtil.getStringWidth(name));
            }
        }
        switch (this.getQuad()) {
            case TOPRIGHT: {
                this.map = sortByValue(this.map, false);
                for (final Module mod : this.map.keySet()) {
                    final Color color = this.getColor(ModuleListComponent.yL * 3);
                    String name2 = mod.getName();
                    if (mod.getHudInfo() != null) {
                        name2 = name2.concat(" " + ChatFormatting.GRAY + "[" + ChatFormatting.RESET + mod.getHudInfo() + ChatFormatting.GRAY + "]" + ChatFormatting.RESET);
                    }
                    final float a = this.x + this.width() - FontUtil.getStringWidth(name2);
                    if (ModuleListComponent.line.getValue()) {
                        this.drawBox(a - 1.0f, this.y + ModuleListComponent.yL + FontUtil.getFontHeight(), a - 3.0f, this.y + ModuleListComponent.yL, color);
                    }
                    if (ModuleListComponent.background.getValue().equalsIgnoreCase("Lines")) {
                        this.drawBox(this.x + this.width(), this.y + ModuleListComponent.yL + FontUtil.getFontHeight(), a - 1.0f, this.y + ModuleListComponent.yL, ModuleListComponent.BColor.getValue());
                    }
                    FontUtil.drawString(name2, a, this.y + ModuleListComponent.yL, color.getRGB());
                    ModuleListComponent.yL += FontUtil.getFontHeight();
                }
                break;
            }
            case TOPLEFT: {
                this.map = sortByValue(this.map, false);
                for (final Module mod : this.map.keySet()) {
                    final Color color = this.getColor(ModuleListComponent.yL * 3);
                    String name2 = mod.getName();
                    if (mod.getHudInfo() != null) {
                        name2 = name2.concat(" " + ChatFormatting.GRAY + "[" + ChatFormatting.RESET + mod.getHudInfo() + ChatFormatting.GRAY + "]" + ChatFormatting.RESET);
                    }
                    final float a = this.x + FontUtil.getStringWidth(name2);
                    if (ModuleListComponent.line.getValue()) {
                        this.drawBox(a + 3.0f, this.y + ModuleListComponent.yL + FontUtil.getFontHeight(), a + 1.0f, this.y + ModuleListComponent.yL, color);
                    }
                    if (ModuleListComponent.background.getValue().equalsIgnoreCase("Lines")) {
                        this.drawBox(a + 1.0f, this.y + ModuleListComponent.yL + FontUtil.getFontHeight(), this.x, this.y + ModuleListComponent.yL, ModuleListComponent.BColor.getValue());
                    }
                    FontUtil.drawString(name2, this.x, this.y + ModuleListComponent.yL, color.getRGB());
                    ModuleListComponent.yL += FontUtil.getFontHeight();
                }
                break;
            }
            case BOTTOMRIGHT: {
                this.map = sortByValue(this.map, true);
                for (final Module mod : this.map.keySet()) {
                    final Color color = this.getColor(ModuleListComponent.yL * 3);
                    String name2 = mod.getName();
                    if (mod.getHudInfo() != null) {
                        name2 = name2.concat(" " + ChatFormatting.GRAY + "[" + ChatFormatting.RESET + mod.getHudInfo() + ChatFormatting.GRAY + "]" + ChatFormatting.RESET);
                    }
                    final float a = this.x + this.width() - FontUtil.getStringWidth(name2);
                    if (ModuleListComponent.line.getValue()) {
                        this.drawBox(a - 1.0f, this.y + ModuleListComponent.yL + FontUtil.getFontHeight(), a - 3.0f, this.y + ModuleListComponent.yL, color);
                    }
                    if (ModuleListComponent.background.getValue().equalsIgnoreCase("Lines")) {
                        this.drawBox(this.x + this.width(), this.y + ModuleListComponent.yL + FontUtil.getFontHeight(), a - 1.0f, this.y + ModuleListComponent.yL, ModuleListComponent.BColor.getValue());
                    }
                    FontUtil.drawString(name2, this.x + this.width() - FontUtil.getStringWidth(name2), this.y + ModuleListComponent.yL, this.getColor(ModuleListComponent.yL * 3).getRGB());
                    ModuleListComponent.yL += FontUtil.getFontHeight();
                }
                break;
            }
            case BOTTOMLEFT: {
                this.map = sortByValue(this.map, true);
                for (final Module mod : this.map.keySet()) {
                    final Color color = this.getColor(ModuleListComponent.yL * 3);
                    String name2 = mod.getName();
                    if (mod.getHudInfo() != null) {
                        name2 = name2.concat(" " + ChatFormatting.GRAY + "[" + ChatFormatting.RESET + mod.getHudInfo() + ChatFormatting.GRAY + "]" + ChatFormatting.RESET);
                    }
                    final float a = this.x + FontUtil.getStringWidth(name2);
                    if (ModuleListComponent.line.getValue()) {
                        this.drawBox(a + 3.0f, this.y + ModuleListComponent.yL + FontUtil.getFontHeight(), a + 1.0f, this.y + ModuleListComponent.yL, color);
                    }
                    if (ModuleListComponent.background.getValue().equalsIgnoreCase("Lines")) {
                        this.drawBox(a + 1.0f, this.y + ModuleListComponent.yL + FontUtil.getFontHeight(), this.x, this.y + ModuleListComponent.yL, ModuleListComponent.BColor.getValue());
                    }
                    FontUtil.drawString(name2, this.x, this.y + ModuleListComponent.yL, this.getColor(ModuleListComponent.yL * 3).getRGB());
                    ModuleListComponent.yL += FontUtil.getFontHeight();
                }
                break;
            }
        }
        this.updated = false;
        if (ModuleListComponent.mode.getValue().equalsIgnoreCase("AlphaPulse")) {
            this.alpha.up = up;
            this.alpha.alpha = alphaBefore;
            this.alpha.updateAlpha(3);
        }
    }
    
    public float width() {
        return this.getMax();
    }
    
    public float height() {
        return (float)ModuleListComponent.yL;
    }
    
    private quads getQuad() {
        if (this.anchorPoint != null) {
            switch (this.anchorPoint) {
                case TOPLEFT: {
                    return quads.TOPLEFT;
                }
                case TOPRIGHT: {
                    return quads.TOPRIGHT;
                }
                case BOTTOMLEFT: {
                    return quads.BOTTOMLEFT;
                }
                case BOTTOMRIGHT: {
                    return quads.BOTTOMRIGHT;
                }
            }
        }
        else {
            final ScaledResolution sr = new ScaledResolution(this.mc);
            final float a = sr.getScaledWidth() / 2.0f;
            final float b = sr.getScaledHeight() / 2.0f;
            if (this.x <= a && this.y <= b) {
                return quads.TOPLEFT;
            }
            if (this.x >= a && this.y <= b) {
                return quads.TOPRIGHT;
            }
            if (this.x <= a && this.y >= b) {
                return quads.BOTTOMLEFT;
            }
            if (this.x >= a && this.y >= b) {
                return quads.BOTTOMRIGHT;
            }
        }
        return quads.TOPRIGHT;
    }
    
    private float getMax() {
        float max = 0.0f;
        for (final Float a : this.map.values()) {
            if (a > max) {
                max = a;
            }
        }
        return max + (ModuleListComponent.line.getValue() ? 3 : 0);
    }
    
    private Color getColor(final int off) {
        if (!this.updated && ModuleListComponent.mode.getValue().equalsIgnoreCase("AlphaPulseStatic")) {
            this.alpha.updateAlpha((int)ModuleListComponent.color.speed);
            this.updated = true;
        }
        if (ModuleListComponent.mode.getValue().equalsIgnoreCase("RainbowStatic")) {
            return ModuleListComponent.color.getValue();
        }
        if (ModuleListComponent.mode.getValue().equalsIgnoreCase("Rainbow")) {
            return Rainbow.getColorStatic((float)off, (float)ModuleListComponent.color.speed, ModuleListComponent.color.s, ModuleListComponent.color.br, ModuleListComponent.color.getA());
        }
        if (ModuleListComponent.mode.getValue().equalsIgnoreCase("AlphaPulse")) {
            this.alpha.updateAlpha((int)(ModuleListComponent.color.speed * 10.0));
            return new Color(ModuleListComponent.color.getR(), ModuleListComponent.color.getG(), ModuleListComponent.color.getB(), this.alpha.alpha);
        }
        if (ModuleListComponent.mode.getValue().equalsIgnoreCase("AlphaPulseStatic")) {
            return new Color(ModuleListComponent.color.getR(), ModuleListComponent.color.getG(), ModuleListComponent.color.getB(), this.alpha.alpha);
        }
        return null;
    }
    
    static {
        ModuleListComponent.mode = new ModeSetting("Mode", "AlphaPulse", new String[] { "Rainbow", "AlphaPulse", "AlphaPulseStatic", "RainbowStatic" });
        ModuleListComponent.background = new ModeSetting("Background", "Lines", new String[] { "Lines", "None", "Fill" });
        ModuleListComponent.line = new BooleanSetting("Line", true);
        ModuleListComponent.color = new ColorSetting("Color", 255, 255, 0, 255, true);
        ModuleListComponent.BColor = new ColorSetting("Back Color", 97, 97, 97, 97, false);
        ModuleListComponent.yL = 0;
    }
    
    private enum quads
    {
        TOPRIGHT, 
        TOPLEFT, 
        BOTTOMRIGHT, 
        BOTTOMLEFT;
    }
    
    private static final class Alpha
    {
        private boolean up;
        private int alpha;
        
        private Alpha() {
            this.up = true;
            this.alpha = 100;
        }
        
        private void updateAlpha(final int a) {
            if (this.alpha >= 255) {
                this.up = false;
            }
            else if (this.alpha <= 100) {
                this.up = true;
            }
            this.alpha = (int)MathUtils.clamp(100.0, 255.0, this.alpha + (this.up ? a : (-a)));
        }
    }
}
