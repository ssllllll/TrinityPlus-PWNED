//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.rendering.skeet;

import me.leon.trinityplus.utils.*;
import net.minecraft.client.gui.*;

public class Quad implements Util
{
    private float x;
    private float y;
    private float x1;
    private float y1;
    
    public Quad() {
    }
    
    public Quad(final float x, final float y, final float x1, final float y1) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
    }
    
    public boolean isWithinIncluding(final float x, final float y) {
        return x >= this.x && x <= this.x1 && y >= this.y && y <= this.y1;
    }
    
    public boolean isWithin(final float x, final float y) {
        return x > this.x && x < this.x1 && y > this.y && y < this.y1;
    }
    
    public boolean insideScreen() {
        return this.insideScreenX() && this.insideScreenY();
    }
    
    public boolean insideScreenX() {
        final ScaledResolution res = new ScaledResolution(Quad.mc);
        return this.x >= 0.0f && this.x1 <= res.getScaledWidth();
    }
    
    public boolean insideScreenY() {
        final ScaledResolution res = new ScaledResolution(Quad.mc);
        return this.y >= 0.0f && this.y <= res.getScaledHeight();
    }
    
    public void shrink(final float amount) {
        this.setX(this.getX() + amount);
        this.setY(this.getY() + amount);
        this.setX1(this.getX1() - amount);
        this.setY1(this.getY1() - amount);
    }
    
    public Quad clone() {
        return new Quad(this.x, this.y, this.x1, this.y1);
    }
    
    public float width() {
        return this.x1 - this.x;
    }
    
    public float height() {
        return this.y1 - this.y;
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public float getX1() {
        return this.x1;
    }
    
    public void setX1(final float x1) {
        this.x1 = x1;
    }
    
    public float getY1() {
        return this.y1;
    }
    
    public void setY1(final float y1) {
        this.y1 = y1;
    }
}
