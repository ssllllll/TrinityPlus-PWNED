//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.world;

import me.leon.trinityplus.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class RaytraceUtils implements Util
{
    public static Vec3d rayTraceLeon(final Entity entity) {
        Vec3d toReturn = null;
        final AxisAlignedBB bb = entity.boundingBox;
        final Vec3d og = RaytraceUtils.mc.player.getPositionVector().add(0.0, (double)RaytraceUtils.mc.player.getEyeHeight(), 0.0);
        final Vec3d[] array;
        final Vec3d[] vecs = array = new Vec3d[] { entity.getPositionVector().add(0.0, (double)(entity.height / 2.0f), 0.0), new Vec3d(bb.minX, bb.minY, bb.minZ), new Vec3d(bb.minX, bb.minY, bb.maxZ), new Vec3d(bb.maxX, bb.minY, bb.maxZ), new Vec3d(bb.maxX, bb.minY, bb.minZ), new Vec3d(bb.minX, bb.minY, bb.minZ), new Vec3d(bb.minX, bb.maxY, bb.minZ), new Vec3d(bb.minX, bb.maxY, bb.maxZ), new Vec3d(bb.minX, bb.minY, bb.maxZ), new Vec3d(bb.maxX, bb.minY, bb.maxZ), new Vec3d(bb.maxX, bb.maxY, bb.maxZ), new Vec3d(bb.minX, bb.maxY, bb.maxZ), new Vec3d(bb.maxX, bb.maxY, bb.maxZ), new Vec3d(bb.maxX, bb.maxY, bb.minZ), new Vec3d(bb.maxX, bb.minY, bb.minZ), new Vec3d(bb.maxX, bb.maxY, bb.minZ), new Vec3d(bb.minX, bb.maxY, bb.minZ) };
        for (final Vec3d vec : array) {
            if (RaytraceUtils.mc.world.rayTraceBlocks(og, vec, false, true, false) == null) {
                toReturn = vec;
                break;
            }
        }
        return toReturn;
    }
    
    public static Vec3d rayTraceLeon(final BlockPos pos) {
        Vec3d toReturn = null;
        final AxisAlignedBB bb = new AxisAlignedBB(pos);
        final Vec3d og = RaytraceUtils.mc.player.getPositionVector().add(0.0, (double)RaytraceUtils.mc.player.getEyeHeight(), 0.0);
        final Vec3d[] array;
        final Vec3d[] vecs = array = new Vec3d[] { new Vec3d(bb.minX, bb.minY, bb.minZ), new Vec3d(bb.minX, bb.minY, bb.maxZ), new Vec3d(bb.maxX, bb.minY, bb.maxZ), new Vec3d(bb.maxX, bb.minY, bb.minZ), new Vec3d(bb.minX, bb.minY, bb.minZ), new Vec3d(bb.minX, bb.maxY, bb.minZ), new Vec3d(bb.minX, bb.maxY, bb.maxZ), new Vec3d(bb.minX, bb.minY, bb.maxZ), new Vec3d(bb.maxX, bb.minY, bb.maxZ), new Vec3d(bb.maxX, bb.maxY, bb.maxZ), new Vec3d(bb.minX, bb.maxY, bb.maxZ), new Vec3d(bb.maxX, bb.maxY, bb.maxZ), new Vec3d(bb.maxX, bb.maxY, bb.minZ), new Vec3d(bb.maxX, bb.minY, bb.minZ), new Vec3d(bb.maxX, bb.maxY, bb.minZ), new Vec3d(bb.minX, bb.maxY, bb.minZ) };
        for (final Vec3d vec : array) {
            if (RaytraceUtils.mc.world.rayTraceBlocks(og, vec, false, true, false) == null) {
                toReturn = vec;
                break;
            }
        }
        return toReturn;
    }
    
    public static boolean rayTraceSimple(final Entity entity) {
        return RaytraceUtils.mc.world.rayTraceBlocks(new Vec3d(entity.posX, entity.posY + entity.height / 2.0f, entity.posZ), RaytraceUtils.mc.player.getPositionVector().add(0.0, (double)RaytraceUtils.mc.player.getEyeHeight(), 0.0), false, true, false) == null;
    }
    
    public static boolean rayTraceSimple(final Entity entity, final double off) {
        return RaytraceUtils.mc.world.rayTraceBlocks(new Vec3d(entity.posX, entity.posY + off, entity.posZ), RaytraceUtils.mc.player.getPositionVector().add(0.0, (double)RaytraceUtils.mc.player.getEyeHeight(), 0.0), false, true, false) == null;
    }
    
    public static boolean rayTraceSimple(final BlockPos pos) {
        return RaytraceUtils.mc.world.rayTraceBlocks(new Vec3d(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5), RaytraceUtils.mc.player.getPositionVector().add(0.0, (double)RaytraceUtils.mc.player.getEyeHeight(), 0.0), false, true, false) == null;
    }
    
    public static boolean rayTraceSimple(final BlockPos pos, final double off) {
        return RaytraceUtils.mc.world.rayTraceBlocks(new Vec3d(pos.x + 0.5, pos.y + off, pos.z + 0.5), RaytraceUtils.mc.player.getPositionVector().add(0.0, (double)RaytraceUtils.mc.player.getEyeHeight(), 0.0), false, true, false) == null;
    }
    
    public static boolean rayTraceSimple(final Vec3d pos) {
        return RaytraceUtils.mc.world.rayTraceBlocks(pos, RaytraceUtils.mc.player.getPositionVector().add(0.0, (double)RaytraceUtils.mc.player.getEyeHeight(), 0.0), false, true, false) == null;
    }
    
    public static boolean rayTraceSimple(final Vec3d pos, final double off) {
        return RaytraceUtils.mc.world.rayTraceBlocks(pos.add(0.0, off, 0.0), RaytraceUtils.mc.player.getPositionVector().add(0.0, (double)RaytraceUtils.mc.player.getEyeHeight(), 0.0), false, true, false) == null;
    }
}
