//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.world.Rotation;

import me.leon.trinityplus.utils.*;
import me.leon.trinityplus.utils.world.*;

public class Rotation implements Util
{
    public float pitch;
    public float yaw;
    public boolean packet;
    public boolean stay;
    public Timer rotationStay;
    public int time;
    public Priority priority;
    
    public Rotation(final float pitch, final float yaw) {
        this.rotationStay = new Timer();
        this.pitch = pitch;
        this.yaw = yaw;
        this.packet = true;
        this.stay = true;
        this.priority = Priority.Normal;
        this.rotationStay.reset();
    }
    
    public Rotation(final float pitch, final float yaw, final boolean packet) {
        this.rotationStay = new Timer();
        this.pitch = pitch;
        this.yaw = yaw;
        this.packet = packet;
        this.stay = true;
        this.priority = Priority.Normal;
        this.rotationStay.reset();
    }
    
    public Rotation(final float pitch, final float yaw, final boolean packet, final Priority priority) {
        this.rotationStay = new Timer();
        this.pitch = pitch;
        this.yaw = yaw;
        this.packet = packet;
        this.stay = true;
        this.priority = priority;
        this.rotationStay.reset();
    }
    
    public Rotation(final float pitch, final float yaw, final boolean packet, final boolean stay, final int time, final Priority priority) {
        this.rotationStay = new Timer();
        this.pitch = pitch;
        this.yaw = yaw;
        this.packet = packet;
        this.stay = stay;
        this.time = time;
        this.priority = priority;
        this.rotationStay.reset();
    }
    
    public void updateRotations() {
        try {
            if (this.packet) {
                Rotation.mc.player.renderYawOffset = this.yaw;
                Rotation.mc.player.rotationYawHead = this.yaw;
            }
            else {
                Rotation.mc.player.rotationYaw = this.yaw;
                Rotation.mc.player.rotationPitch = this.pitch;
            }
        }
        catch (Exception ex) {}
    }
    
    public void cancel() {
        this.yaw = Rotation.mc.player.rotationYaw;
        this.pitch = Rotation.mc.player.rotationPitch;
        this.rotationStay.reset();
    }
}
