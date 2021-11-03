//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.entity;

import me.leon.trinityplus.utils.*;
import me.leon.trinityplus.events.main.*;
import net.minecraft.potion.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.utils.math.*;
import net.minecraft.client.*;

public class MotionUtils implements Util
{
    public static void doStrafe(final float speed) {
        final double[] motion = getMotion(speed);
        MotionUtils.mc.player.motionX = motion[0];
        MotionUtils.mc.player.motionZ = motion[1];
    }
    
    public static MoveEvent doStrafe(final MoveEvent event, final float speed) {
        final double[] motion = getMotion(speed);
        event.x = motion[0];
        event.z = motion[1];
        return event;
    }
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (MotionUtils.mc.player != null && MotionUtils.mc.player.isPotionActive(Potion.getPotionById(1))) {
            final int amplifier = MotionUtils.mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static boolean isMoving() {
        return MotionUtils.mc.player.movementInput.moveForward != 0.0f || MotionUtils.mc.player.movementInput.moveStrafe != 0.0f;
    }
    
    public static double getSpeed() {
        final double deltaX = MotionUtils.mc.player.posX - MotionUtils.mc.player.prevPosX;
        final double deltaZ = MotionUtils.mc.player.posZ - MotionUtils.mc.player.prevPosZ;
        return MathUtils.roundAvoid(MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ) / 1000.0f / 1.3888889E-5f, 1);
    }
    
    public static double[] forward(final double speed) {
        float forward = Minecraft.getMinecraft().player.movementInput.moveForward;
        float side = Minecraft.getMinecraft().player.movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().player.prevRotationYaw + (Minecraft.getMinecraft().player.rotationYaw - Minecraft.getMinecraft().player.prevRotationYaw) * Minecraft.getMinecraft().getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static double[] getMotion(final float speed) {
        final float moveForward = MotionUtils.mc.player.movementInput.moveForward;
        final float moveStrafe = MotionUtils.mc.player.movementInput.moveStrafe;
        return getMotion(speed, moveForward, moveStrafe, MotionUtils.mc.player.rotationYaw);
    }
    
    public static double[] getMotion(final float speed, final float mvF, final float mvS, float yaw) {
        double x = 0.0;
        double z = 0.0;
        if (mvF == 0.0f && mvS == 0.0f) {
            return new double[] { x, z };
        }
        yaw += 90.0f;
        if (mvF > 0.0f) {
            yaw += mvS * -45.0f;
        }
        else if (mvF < 0.0f) {
            yaw += mvS * 45.0f;
        }
        else if (mvF == 0.0f) {
            yaw += mvS * -90.0f;
        }
        if (mvF < 0.0f) {
            yaw -= 180.0f;
        }
        x = speed * Math.cos(Math.toRadians(yaw));
        z = speed * Math.sin(Math.toRadians(yaw));
        return new double[] { x, z };
    }
}
