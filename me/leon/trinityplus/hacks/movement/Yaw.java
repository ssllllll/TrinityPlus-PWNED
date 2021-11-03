//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.movement;

import me.leon.trinityplus.hacks.*;

public class Yaw extends Module
{
    public Yaw() {
        super("Yaw", "locks your rotation", Category.MOVEMENT);
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        Yaw.mc.player.rotationYaw = Math.round(Yaw.mc.player.rotationYaw / 45.0f) * 45.0f;
    }
}
