//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.movement;

import me.leon.trinityplus.hacks.*;

public class AutoSprint extends Module
{
    public AutoSprint() {
        super("Sprint", "Automatically sprints", Category.MOVEMENT);
    }
    
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (AutoSprint.mc.player.movementInput.moveForward > 0.0f && !AutoSprint.mc.player.isSneaking() && !AutoSprint.mc.player.collidedHorizontally) {
            AutoSprint.mc.player.setSprinting(true);
        }
    }
}
