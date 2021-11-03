//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.misc;

import me.leon.trinityplus.utils.math.*;
import me.leon.trinityplus.utils.rendering.skeet.*;

public class BezierCurve
{
    private float a;
    private float b;
    private float c;
    private float d;
    private boolean finished;
    
    public BezierCurve(final float a, final float b, final float c, final float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    public double get(final double t) {
        final double rt = MathUtils.clamp(0.0, 1.0, 1.0 - MathUtils.clamp(0.0, 1.0, t));
        final double trip = rt * rt * rt;
        final double doub = rt * rt;
        if (rt == 1.0) {
            this.finished = true;
        }
        return trip * this.a + 3.0 * doub * t * this.b + 3.0 * rt * (t * t) * this.c + trip * this.d;
    }
    
    public boolean isFinished() {
        return this.finished;
    }
    
    public void setFinished(final boolean finished) {
        this.finished = finished;
    }
    
    public float getA() {
        return this.a;
    }
    
    public void setA(final float a) {
        this.a = a;
    }
    
    public float getB() {
        return this.b;
    }
    
    public void setB(final float b) {
        this.b = b;
    }
    
    public float getC() {
        return this.c;
    }
    
    public void setC(final float c) {
        this.c = c;
    }
    
    public float getD() {
        return this.d;
    }
    
    public void setD(final float d) {
        this.d = d;
    }
    
    public static double get(final double t, final Quad q) {
        final double rt = 1.0 - t;
        final double trip = rt * rt * rt;
        final double doub = rt * rt;
        return q.getX() * trip + q.getY() * 3.0f * t * doub + q.getX1() * 3.0f * doub * rt + q.getY1() * trip;
    }
}
