//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.world;

import me.leon.trinityplus.utils.*;

public class Timer implements Util
{
    private long time;
    
    public Timer(final long time) {
        this.time = time;
    }
    
    public Timer() {
        this.time = System.currentTimeMillis();
    }
    
    public long getTime() {
        return this.time;
    }
    
    public void setTime(final long time) {
        this.time = time;
    }
    
    public long getDelta() {
        return this.time - System.currentTimeMillis();
    }
    
    public void reset() {
        this.time = System.currentTimeMillis();
    }
    
    public boolean hasPassed(final int millis) {
        return System.currentTimeMillis() - this.time >= millis;
    }
    
    public boolean hasPassAndReset(final int millis) {
        if (System.currentTimeMillis() - this.time >= millis) {
            this.reset();
            return true;
        }
        return false;
    }
}
