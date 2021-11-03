//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.math;

import me.leon.trinityplus.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.utils.entity.*;
import java.math.*;

public class MathUtils implements Util
{
    public static double clamp(final double min, final double max, final double val) {
        if (val > max || min > max) {
            return max;
        }
        return Math.max(val, min);
    }
    
    public static double clamp(final double min, final double max, final double val, final double height) {
        if (val <= min || max <= min) {
            return min;
        }
        if (val + height >= max || min >= max) {
            return max - height;
        }
        return val;
    }
    
    public static double roundAvoid(final double value, final int places) {
        final double scale = Math.pow(10.0, places);
        return Math.round(value * scale) / scale;
    }
    
    public static Vec3d extrapolatePlayerPosition(final Entity player, final int ticks) {
        final Vec3d lastPos = new Vec3d(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ);
        final Vec3d currentPos = new Vec3d(player.posX, player.posY, player.posZ);
        final double distance = multiply(player.motionX) + multiply(player.motionY) + multiply(player.motionZ);
        final Vec3d tempVec = calculateLine(lastPos, currentPos, distance * ticks);
        return new Vec3d(tempVec.x, player.posY, tempVec.z);
    }
    
    public static double multiply(final double one) {
        return one * one;
    }
    
    public static Vec3d calculateLine(final Vec3d x1, final Vec3d x2, final double distance) {
        final double length = Math.sqrt(multiply(x2.x - x1.x) + multiply(x2.y - x1.y) + multiply(x2.z - x1.z));
        if (length != 0.0) {
            final double unitSlopeX = (x2.x - x1.x) / length;
            final double unitSlopeY = (x2.y - x1.y) / length;
            final double unitSlopeZ = (x2.z - x1.z) / length;
            final double x3 = x1.x + unitSlopeX * distance;
            final double y = x1.y + unitSlopeY * distance;
            final double z = x1.z + unitSlopeZ * distance;
            return new Vec3d(x3, y, z);
        }
        return x2;
    }
    
    public static Vec3d interpolate(final float y, final float p, final float delta, final float one, final float dist) {
        final float d1 = delta / one * dist;
        final double[] calc = calc3d(p, y, d1);
        return new Vec3d(calc[0], calc[1], calc[2]);
    }
    
    public static Vec3d interpolate(final float p, final float delta, final float one, final float dist) {
        final float d1 = delta / one * dist;
        final double[] calc = calc2d(p, d1);
        return new Vec3d(calc[0], 0.0, calc[1]);
    }
    
    public static Vec3d interpolateStrafe(final float p, final float delta, final float one, final float dist, final float mvF, final float mvS) {
        final float d1 = delta / one * dist;
        final double[] calc = MotionUtils.getMotion(d1, mvF, mvS, p - 180.0f);
        return new Vec3d(calc[0], 0.0, calc[1]);
    }
    
    public static Vec3d interpolateStrafe(final float p, final float delta, final float one, final float dist, final float mvF, final float mvS, final float up) {
        final float d1 = delta / one * dist;
        final double[] calc = MotionUtils.getMotion(d1, mvF, mvS, p - 180.0f);
        return new Vec3d(calc[0], (double)(up * d1), calc[1]);
    }
    
    public static double[] calc2d(final float p, final float dist) {
        final double x = dist * Math.cos(Math.toRadians(p));
        final double y = dist * Math.sin(Math.toRadians(p));
        return new double[] { x, y };
    }
    
    public static double[] calc3d(final float p, final float y, final float dist) {
        final double x = dist * Math.cos(Math.toRadians(p));
        final double z = dist * Math.sin(Math.toRadians(p));
        final double dist2 = Math.abs(0.0 - x);
        final double x2 = dist2 * Math.cos(Math.toRadians(y));
        final double z2 = dist2 * Math.sin(Math.toRadians(y));
        return new double[] { x2, z, z2 };
    }
    
    public static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
