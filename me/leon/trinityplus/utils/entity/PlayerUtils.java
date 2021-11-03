//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.entity;

import me.leon.trinityplus.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;

public class PlayerUtils implements Util
{
    public static BlockPos getPlayerPosFloored() {
        return new BlockPos(Math.floor(PlayerUtils.mc.player.posX), Math.floor(PlayerUtils.mc.player.posY), Math.floor(PlayerUtils.mc.player.posZ));
    }
    
    public static Vec3d getCenter(final double posX, final double posY, final double posZ) {
        return new Vec3d(Math.floor(posX) + 0.5, Math.floor(posY), Math.floor(posZ) + 0.5);
    }
    
    public static double getReach() {
        return Minecraft.getMinecraft().player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
    }
}
