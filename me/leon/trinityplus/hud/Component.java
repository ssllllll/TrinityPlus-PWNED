//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hud;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.util.*;
import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.client.*;
import java.awt.*;
import me.leon.trinityplus.utils.rendering.*;

public abstract class Component
{
    public String name;
    public float x;
    public float y;
    public boolean dragging;
    public boolean visible;
    public AnchorPoint anchorPoint;
    protected Minecraft mc;
    public ScaledResolution res;
    private ArrayList<Setting> settings;
    
    public Component(final String name) {
        this.anchorPoint = null;
        this.mc = Minecraft.getMinecraft();
        this.res = new ScaledResolution(this.mc);
        this.name = name;
        this.settings = new ArrayList<Setting>();
    }
    
    public abstract void render();
    
    public abstract float width();
    
    public abstract float height();
    
    public boolean onButton(final int x, final int y) {
        return this.ButtonCheck(this.x, this.y, this.width(), this.height(), x, y);
    }
    
    protected boolean pCheck() {
        return this.mc.player == null;
    }
    
    protected boolean wCheck() {
        return this.mc.world == null;
    }
    
    protected boolean nullCheck() {
        return this.mc.world == null && this.mc.player == null;
    }
    
    public boolean ButtonCheck(final float x, final float y, final float w, final float h, final int mX, final int mY) {
        return mX >= x && mX <= x + w && mY >= y && mY <= y + h;
    }
    
    public void drawBackground() {
        if (HUDeditor.background.getValue()) {
            this.drawBox((float)((int)(this.x + this.width()) + 1), (float)(int)(this.y + this.height() + 1.0f), (float)((int)this.x - 1), (float)((int)this.y - 1), HUDeditor.color.getValue());
        }
    }
    
    protected int getTextColor() {
        return HUDeditor.textColor.getValue().getRGB();
    }
    
    public void drawBox(final float x, final float y, final float x1, final float y1, final Color color) {
        RenderUtils.drawRect(x, y, x1, y1, color);
    }
    
    public void addSetting(final Setting setting) {
        this.settings.add(setting);
    }
    
    public ArrayList<Setting> getSettings() {
        return this.settings;
    }
    
    public Setting getSetting(final String name) {
        return this.settings.stream().filter(e -> e.getName().equals(name)).findFirst().get();
    }
}
