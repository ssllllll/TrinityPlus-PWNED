//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.entity;

import me.leon.trinityplus.utils.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.utils.world.*;
import me.leon.trinityplus.utils.world.Rotation.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;

public class BlockUtils implements Util
{
    public static boolean isInterceptedByOther(final BlockPos blockPos) {
        for (final Entity entity : BlockUtils.mc.world.loadedEntityList) {
            if (entity.equals((Object)BlockUtils.mc.player)) {
                continue;
            }
            if (new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isCollidedBlocks(final BlockPos pos) {
        return getBlockResistance(new BlockPos(BlockUtils.mc.player.posX, BlockUtils.mc.player.posY, BlockUtils.mc.player.posZ)) == BlockResistance.Resistant || isInterceptedByOther(pos) || InventoryUtil.getBlockInHotbar(Blocks.OBSIDIAN) == -1;
    }
    
    public static void placeBlock(final BlockPos pos, final boolean rotate, final boolean strict, final boolean raytrace, final boolean packet, final boolean swingArm, final boolean antiGlitch) {
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            if (getBlockResistance(pos.offset(enumFacing)) != BlockResistance.Blank && !EntityUtils.isIntercepted(pos)) {
                final Vec3d vec = new Vec3d(pos.getX() + 0.5 + enumFacing.getXOffset() * 0.5, pos.getY() + 0.5 + enumFacing.getYOffset() * 0.5, pos.getZ() + 0.5 + enumFacing.getZOffset() * 0.5);
                if (strict) {
                    SpoofingManager.rotationQueue.add(new Rotation((float)Math.toDegrees(Math.atan2(vec.z - BlockUtils.mc.player.posZ, vec.x - BlockUtils.mc.player.posX)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(vec.y - (BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight()), Math.sqrt((vec.x - BlockUtils.mc.player.posX) * (vec.x - BlockUtils.mc.player.posX) + (vec.z - BlockUtils.mc.player.posZ) * (vec.z - BlockUtils.mc.player.posZ))))), true, Priority.High));
                }
                if (rotate) {
                    RotationUtils.rotateTowards(vec, true);
                }
                BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                if (packet) {
                    BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, raytrace ? enumFacing : EnumFacing.UP, EnumHand.MAIN_HAND, (float)vec.x - pos.x, (float)vec.y - pos.y, (float)vec.z - pos.z));
                }
                else {
                    BlockUtils.mc.playerController.processRightClickBlock(BlockUtils.mc.player, BlockUtils.mc.world, pos.offset(enumFacing), raytrace ? enumFacing.getOpposite() : EnumFacing.UP, new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                }
                if (swingArm) {
                    BlockUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                if (antiGlitch) {
                    BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos.offset(enumFacing), enumFacing.getOpposite()));
                }
                return;
            }
        }
    }
    
    public static BlockResistance getBlockResistance(final BlockPos block) {
        if (BlockUtils.mc.world.isAirBlock(block)) {
            return BlockResistance.Blank;
        }
        if (BlockUtils.mc.world.getBlockState(block).getBlock().getBlockHardness(BlockUtils.mc.world.getBlockState(block), (World)BlockUtils.mc.world, block) != -1.0f && !BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) && !BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) && !BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) && !BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST)) {
            return BlockResistance.Breakable;
        }
        if (BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) || BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) || BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) || BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST)) {
            return BlockResistance.Resistant;
        }
        if (BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.BEDROCK)) {
            return BlockResistance.Unbreakable;
        }
        return null;
    }
    
    public static boolean placeBlock(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final boolean isSneaking) {
        boolean sneaking = false;
        final EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return isSneaking;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if (!BlockUtils.mc.player.isSneaking() && isSneaking) {
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BlockUtils.mc.player.setSneaking(true);
            sneaking = true;
        }
        if (rotate) {
            RotationUtils.rotateTowards(hitVec, true, Priority.High);
        }
        rightClickBlock(neighbour, hitVec, hand, opposite, packet);
        BlockUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
        BlockUtils.mc.rightClickDelayTimer = 4;
        if (!BlockUtils.mc.player.isSneaking() && isSneaking) {
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            BlockUtils.mc.player.setSneaking(false);
            sneaking = false;
        }
        return sneaking || isSneaking;
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final List<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (BlockUtils.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = BlockUtils.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(BlockUtils.mc.player.posX, BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight(), BlockUtils.mc.player.posZ);
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { BlockUtils.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - BlockUtils.mc.player.rotationYaw), BlockUtils.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - BlockUtils.mc.player.rotationPitch) };
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (packet) {
            final float f = (float)(vec.x - pos.getX());
            final float f2 = (float)(vec.y - pos.getY());
            final float f3 = (float)(vec.z - pos.getZ());
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        }
        else {
            BlockUtils.mc.playerController.processRightClickBlock(BlockUtils.mc.player, BlockUtils.mc.world, pos, direction, vec, hand);
        }
        BlockUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
        BlockUtils.mc.rightClickDelayTimer = 4;
    }
    
    public enum BlockResistance
    {
        Blank, 
        Breakable, 
        Resistant, 
        Unbreakable;
    }
}
