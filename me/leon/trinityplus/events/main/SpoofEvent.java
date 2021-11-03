//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import me.leon.trinityplus.events.*;

public class SpoofEvent extends TrinityEvent
{
    public float yaw;
    public float pitch;
    public double posX;
    public double posY;
    public double posZ;
    public boolean hasLocation;
    public boolean hasRotation;
    public boolean onGround;
    
    public SpoofEvent(final EventStage stage) {
        super(stage);
    }
}
