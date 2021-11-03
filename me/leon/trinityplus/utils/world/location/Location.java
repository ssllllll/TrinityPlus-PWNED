//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.world.location;

import me.leon.trinityplus.utils.*;
import me.leon.trinityplus.utils.world.*;

public class Location implements Util
{
    private double posX;
    private double posY;
    private double posZ;
    private boolean onGround;
    private final Priority priority;
    
    public Location(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.onGround = Location.mc.player.onGround;
        this.priority = Priority.Normal;
    }
    
    public Location(final double posX, final double posY, final double posZ, final boolean onGround) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.onGround = onGround;
        this.priority = Priority.Normal;
    }
    
    public Location(final double posX, final double posY, final double posZ, final Priority priority) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.onGround = Location.mc.player.onGround;
        this.priority = priority;
    }
    
    public Location(final double posX, final double posY, final double posZ, final boolean onGround, final Priority priority) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.onGround = onGround;
        this.priority = priority;
    }
    
    public Priority getPriority() {
        return this.priority;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public double getPosX() {
        return this.posX;
    }
    
    public void setPosX(final double posX) {
        this.posX = posX;
    }
    
    public double getPosY() {
        return this.posY;
    }
    
    public void setPosY(final double posY) {
        this.posY = posY;
    }
    
    public double getPosZ() {
        return this.posZ;
    }
    
    public void setPosZ(final double posZ) {
        this.posZ = posZ;
    }
}
