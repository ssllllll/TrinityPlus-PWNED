//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.movement;

import me.leon.trinityplus.hacks.*;
import net.minecraft.entity.*;

public class Parkour extends Module
{
    public Parkour() {
        super("Parkour", "Automatically jumps for you", Category.MOVEMENT);
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (Parkour.mc.player.onGround && !Parkour.mc.player.isSneaking() && !Parkour.mc.gameSettings.keyBindSneak.isPressed() && !Parkour.mc.gameSettings.keyBindJump.isPressed() && Parkour.mc.world.getCollisionBoxes((Entity)Parkour.mc.player, Parkour.mc.player.getEntityBoundingBox().offset(0.0, -0.5, 0.0).expand(-0.001, 0.0, -0.001)).isEmpty()) {
            Parkour.mc.player.jump();
        }
    }
}
