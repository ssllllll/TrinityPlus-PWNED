//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.movement;

import me.leon.trinityplus.hacks.*;
import net.minecraft.util.math.*;

public class NoVoid extends Module
{
    public NoVoid() {
        super("NoVoid", "avoids getting voided", Category.MOVEMENT);
    }
    
    public void onUpdate() {
        if (NoVoid.mc.player == null || NoVoid.mc.world == null) {
            return;
        }
        if (!NoVoid.mc.player.noClip && NoVoid.mc.player.posY <= 0.0) {
            final RayTraceResult trace = NoVoid.mc.world.rayTraceBlocks(NoVoid.mc.player.getPositionVector(), new Vec3d(NoVoid.mc.player.posX, 0.0, NoVoid.mc.player.posZ), false, false, false);
            if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                return;
            }
            NoVoid.mc.player.setVelocity(0.0, 0.0, 0.0);
            if (NoVoid.mc.player.getRidingEntity() != null) {
                NoVoid.mc.player.getRidingEntity().setVelocity(0.0, 0.0, 0.0);
            }
        }
    }
}
