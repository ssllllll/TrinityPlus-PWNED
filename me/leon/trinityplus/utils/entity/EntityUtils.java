//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.entity;

import me.leon.trinityplus.utils.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.utils.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import me.leon.trinityplus.main.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;

public class EntityUtils implements Util
{
    public static double GetDistance(final double p_X, final double p_Y, final double p_Z, final double x, final double y, final double z) {
        final double d0 = p_X - x;
        final double d2 = p_Y - y;
        final double d3 = p_Z - z;
        return MathHelper.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
    }
    
    public static EntityLivingBase getTarget(final boolean players, final boolean neutral, final boolean friends, final boolean hostile, final boolean passive, final double range, final int mode) {
        EntityLivingBase entity2 = null;
        if (mode == 0) {
            entity2 = (EntityLivingBase)EntityUtils.mc.world.loadedEntityList.stream().filter(entity1 -> isValid(entity1, players, neutral, friends, hostile, passive, range)).min(Comparator.comparing(entity1 -> EntityUtils.mc.player.getPositionVector().squareDistanceTo(entity1.getPositionVector()))).orElse(null);
        }
        else if (mode == 1) {
            entity2 = (EntityLivingBase)EntityUtils.mc.world.loadedEntityList.stream().filter(entity1 -> isValid(entity1, players, neutral, friends, hostile, passive, range)).map(entity1 -> entity1).min(Comparator.comparing((Function<? super T, ? extends Comparable>)EntityLivingBase::getHealth)).orElse(null);
        }
        else if (mode == 2) {
            entity2 = (EntityLivingBase)EntityUtils.mc.world.loadedEntityList.stream().filter(entity1 -> isValid(entity1, players, neutral, friends, hostile, passive, range)).map(entity1 -> entity1).max(Comparator.comparing((Function<? super T, ? extends Comparable>)EntityLivingBase::getHealth)).orElse(null);
        }
        return entity2;
    }
    
    public static ArrayList<EntityLivingBase> getTargets(final boolean players, final boolean neutral, final boolean friends, final boolean hostile, final boolean passive, final double range) {
        return (ArrayList<EntityLivingBase>)EntityUtils.mc.world.loadedEntityList.stream().filter(entity1 -> isValid(entity1, players, neutral, friends, hostile, passive, range)).map(entity1 -> entity1).collect(Collectors.toList());
    }
    
    public static ArrayList<Entity> getESPTargets(final boolean players, final boolean neutral, final boolean hostile, final boolean vehicles, final boolean passive, final boolean items, final boolean crystals, final double range) {
        return (ArrayList<Entity>)EntityUtils.mc.world.loadedEntityList.stream().filter(entity1 -> isValidESP(entity1, players, neutral, vehicles, hostile, passive, items, crystals, range)).collect(Collectors.toList());
    }
    
    private static boolean isValidESP(final Entity entity, final boolean players, final boolean neutral, final boolean vehicles, final boolean hostile, final boolean passive, final boolean items, final boolean crystals, final double range) {
        return basicCheck(entity, items, players, crystals) && (!isPassive(entity) || passive) && (!isHostileMob(entity) || hostile) && (!isNeutralMob(entity) || neutral) && (!isVehicle(entity) || vehicles) && ((Entity)((EntityUtils.mc.renderViewEntity == null) ? EntityUtils.mc.player : EntityUtils.mc.renderViewEntity)).getDistanceSq(entity) <= range * range;
    }
    
    private static boolean basicCheck(final Entity entity, final boolean items, final boolean players, final boolean crystals) {
        return !(entity instanceof EntityFallingBlock) && (!(entity instanceof EntityItem) || items) && (!(entity instanceof EntityPlayer) || players) && (!(entity instanceof EntityEnderCrystal) || crystals) && !((Entity)((EntityUtils.mc.renderViewEntity == null) ? EntityUtils.mc.player : EntityUtils.mc.renderViewEntity)).equals(entity);
    }
    
    public static Vec3d getVec(final Entity entity) {
        return new Vec3d(entity.posX, entity.posY, entity.posZ);
    }
    
    public static ArrayList<Block> isColliding(final double posX, final double posY, final double posZ, final Entity entity) {
        final ArrayList<Block> block = new ArrayList<Block>();
        if (entity != null) {
            final AxisAlignedBB bb = (entity.ridingEntity != null) ? entity.ridingEntity.getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(posX, posY, posZ) : entity.getEntityBoundingBox().contract(0.0, 1.0, 0.0).offset(posX, posY, posZ);
            final int y = (int)bb.minY;
            for (int x = (int)Math.floor(bb.minX); x < Math.floor(bb.maxX) + 1.0; ++x) {
                for (int z = (int)Math.floor(bb.minZ); z < Math.floor(bb.maxZ) + 1.0; ++z) {
                    block.add(EntityUtils.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock());
                }
            }
        }
        return block;
    }
    
    public static ArrayList<BlockPos> getPos(final double posX, final double posY, final double posZ, final Entity entity) {
        final ArrayList<BlockPos> block = new ArrayList<BlockPos>();
        if (entity != null) {
            final AxisAlignedBB bb = (entity.ridingEntity != null) ? entity.ridingEntity.getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(posX, posY, posZ) : entity.getEntityBoundingBox().contract(0.01, 1.0, 0.01).offset(posX, posY, posZ);
            final int y = (int)bb.minY;
            for (int x = (int)Math.floor(bb.minX); x < Math.floor(bb.maxX) + 1.0; ++x) {
                for (int z = (int)Math.floor(bb.minZ); z < Math.floor(bb.maxZ) + 1.0; ++z) {
                    block.add(new BlockPos(x, y, z));
                }
            }
        }
        return block;
    }
    
    public static boolean isTrapped(final EntityLivingBase entity, final boolean antiStep) {
        final HoleUtils.Hole hole = HoleUtils.getHole(getEntityPosFloored((Entity)entity), -1);
        final boolean isInHole = isInHole(entity);
        final BlockPos entityPos = getEntityPosFloored((Entity)entity);
        if (isInHole && hole != null) {
            if (hole instanceof HoleUtils.DoubleHole) {
                final HoleUtils.DoubleHole dHole = (HoleUtils.DoubleHole)hole;
                final int[][] offsets = { { 1, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 } };
                int checked = 0;
                for (final int[] off : offsets) {
                    final BlockPos pos = dHole.pos.add(off[0], off[1], off[2]);
                    if (!pos.equals((Object)dHole.pos1)) {
                        final Block block = WorldUtils.getBlock(pos);
                        if (!WorldUtils.empty.contains(block)) {
                            ++checked;
                        }
                        final BlockPos pos2 = dHole.pos.add(off[0], off[1] + 1, off[2]);
                        if (!pos2.equals((Object)dHole.pos1.add(0, off[1] + 1, 0))) {
                            final Block block2 = WorldUtils.getBlock(pos2);
                            if (!WorldUtils.empty.contains(block2)) {
                                ++checked;
                            }
                        }
                    }
                }
                for (final int[] off : offsets) {
                    final BlockPos pos = dHole.pos1.add(off[0], off[1], off[2]);
                    if (!pos.equals((Object)dHole.pos)) {
                        final Block block = WorldUtils.getBlock(pos);
                        if (!WorldUtils.empty.contains(block)) {
                            ++checked;
                        }
                        final BlockPos pos2 = dHole.pos1.add(off[0], off[1] + 1, off[2]);
                        if (!pos2.equals((Object)dHole.pos.add(0, off[1] + 1, 0))) {
                            final Block block2 = WorldUtils.getBlock(pos2);
                            if (!WorldUtils.empty.contains(block2)) {
                                ++checked;
                            }
                        }
                    }
                }
                if (!WorldUtils.empty.contains(WorldUtils.getBlock(dHole.pos.add(0, 2, 0)))) {
                    ++checked;
                }
                if (!WorldUtils.empty.contains(WorldUtils.getBlock(dHole.pos1.add(0, 2, 0)))) {
                    ++checked;
                }
                if (antiStep) {
                    if (!WorldUtils.empty.contains(WorldUtils.getBlock(dHole.pos.add(0, 3, 0)))) {
                        ++checked;
                    }
                    if (!WorldUtils.empty.contains(WorldUtils.getBlock(dHole.pos1.add(0, 3, 0)))) {
                        ++checked;
                    }
                }
                if (checked == (antiStep ? 16 : 14)) {
                    return true;
                }
            }
            if (hole instanceof HoleUtils.SingleHole) {
                int checked2 = 0;
                final int[][] array3;
                final int[][] offsets = array3 = new int[][] { { 1, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 1, 0 }, { 0, 1, -1 }, { 0, 1, 1 }, { -1, 1, 0 }, { 0, 2, 0 } };
                for (final int[] off2 : array3) {
                    if (!WorldUtils.empty.contains(WorldUtils.getBlock(((HoleUtils.SingleHole)hole).pos.add(off2[0], off2[1], off2[2])))) {
                        ++checked2;
                    }
                }
                if (antiStep && !WorldUtils.empty.contains(WorldUtils.getBlock(((HoleUtils.SingleHole)hole).pos.add(0, 3, 0)))) {
                    ++checked2;
                }
                return checked2 == (antiStep ? 10 : 9);
            }
        }
        return false;
    }
    
    public static boolean isBurrowed(final EntityLivingBase entity) {
        return WorldUtils.getBlock(getEntityPosFloored((Entity)entity)) == Blocks.OBSIDIAN || WorldUtils.getBlock(getEntityPosFloored((Entity)entity)) == Blocks.BEDROCK || WorldUtils.getBlock(getEntityPosFloored((Entity)entity)) == Blocks.ENDER_CHEST;
    }
    
    public static boolean isInHole(final EntityLivingBase entity) {
        final ArrayList<BlockPos> blocks = getPos(0.0, 0.0, 0.0, (Entity)entity);
        if (blocks.size() == 2) {
            BlockPos pos2 = null;
            BlockPos pos3 = null;
            for (final BlockPos block : blocks) {
                for (final EnumFacing facing : EnumFacing.values()) {
                    if (facing != EnumFacing.DOWN) {
                        if (facing != EnumFacing.UP) {
                            if (WorldUtils.getBlock(block) == Blocks.AIR && WorldUtils.getBlock(block.offset(facing)) == Blocks.AIR) {
                                pos3 = block;
                                pos2 = block.offset(facing);
                                break;
                            }
                        }
                    }
                }
                if (pos3 != null) {
                    break;
                }
            }
            if (pos3 == null) {
                return false;
            }
            final BlockPos[] offsets = { pos3.north(), pos3.south(), pos3.east(), pos3.west() };
            final BlockPos[] offsets2 = { pos2.north(), pos2.south(), pos2.east(), pos2.west() };
            int checked = 0;
            for (final BlockPos off : offsets) {
                if (off != pos2) {
                    final Block block2 = WorldUtils.getBlock(off);
                    if (block2 == Blocks.OBSIDIAN || block2 == Blocks.BEDROCK) {
                        ++checked;
                    }
                }
            }
            for (final BlockPos off : offsets2) {
                if (off != pos3) {
                    final Block block2 = WorldUtils.getBlock(off);
                    if (block2 == Blocks.OBSIDIAN || block2 == Blocks.BEDROCK) {
                        ++checked;
                    }
                }
            }
            return checked >= 6;
        }
        else {
            if (blocks.size() == 1) {
                final BlockPos pos4 = getEntityPosFloored((Entity)entity);
                final BlockPos[] offsets3 = { pos4.north(), pos4.south(), pos4.east(), pos4.west() };
                int checked2 = 0;
                for (final BlockPos pos5 : offsets3) {
                    final Block block3 = WorldUtils.getBlock(pos5);
                    if (block3 == Blocks.BEDROCK || block3 == Blocks.OBSIDIAN) {
                        ++checked2;
                    }
                }
                return checked2 >= 4;
            }
            return false;
        }
    }
    
    public static boolean getArmor(final EntityPlayer target, final double durability) {
        for (final ItemStack stack : target.getArmorInventoryList()) {
            if (stack == null || stack.getItem() == Items.AIR) {
                return true;
            }
            if (durability >= (stack.getMaxDamage() - stack.getItemDamage()) / (float)stack.getMaxDamage() * 100.0f) {
                return true;
            }
        }
        return false;
    }
    
    public static int getTotalArmor(final EntityPlayer target) {
        int damage = 0;
        for (final ItemStack stack : target.getArmorInventoryList()) {
            if (stack != null) {
                if (stack.getItem() == Items.AIR) {
                    continue;
                }
                damage += stack.getItemDamage();
            }
        }
        return damage;
    }
    
    private static boolean isValid(final Entity entity, final boolean players, final boolean neutral, final boolean friends, final boolean hostile, final boolean passive, final double range) {
        if (entity.isDead) {
            return false;
        }
        if (!(entity instanceof EntityLivingBase) || entity == EntityUtils.mc.player || entity.getDistanceSq((Entity)EntityUtils.mc.player) > range * range) {
            return entity instanceof EntityPlayer && ((EntityPlayer)entity).getHealth() <= 0.0f;
        }
        if (entity instanceof EntityPlayer && players) {
            return friends || !Trinity.friendManager.isFriend((EntityPlayer)entity);
        }
        return (isHostileMob(entity) && hostile) || (isNeutralMob(entity) && neutral) || (isPassive(entity) && passive);
    }
    
    public static boolean isPassive(final Entity entity) {
        return (!(entity instanceof EntityWolf) || !((EntityWolf)entity).isAngry()) && (entity instanceof EntityAgeable || entity instanceof EntityAmbientCreature || entity instanceof EntitySquid || (entity instanceof EntityIronGolem && ((EntityIronGolem)entity).getRevengeTarget() == null));
    }
    
    public static boolean isVehicle(final Entity entity) {
        return entity instanceof EntityBoat || entity instanceof EntityMinecart;
    }
    
    public static boolean isHostileMob(final Entity entity) {
        return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(entity)) || entity instanceof EntitySpider;
    }
    
    public static boolean isNeutralMob(final Entity entity) {
        return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
    }
    
    public static int toMode(final String mode) {
        if (mode.equalsIgnoreCase("Closest")) {
            return 0;
        }
        if (mode.equalsIgnoreCase("Lowest Health")) {
            return 1;
        }
        if (mode.equalsIgnoreCase("Highest Health")) {
            return 2;
        }
        throw new IllegalArgumentException(mode);
    }
    
    public static double getRange(final Entity entity) {
        return EntityUtils.mc.player.getPositionVector().add(0.0, (double)EntityUtils.mc.player.eyeHeight, 0.0).distanceTo(entity.getPositionVector().add(0.0, entity.height / 2.0, 0.0));
    }
    
    public static Vec3d interpolateEntity(final Entity entity, final float n) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n);
    }
    
    public static Vec3d interpolateEntityTime(final Entity entity, final float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }
    
    public static Vec3d getInterpolatedPos(final Entity entity, final float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final Vec3d vec) {
        return getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
    }
    
    public static double calculateDistanceWithPartialTicks(final double originalPos, final double finalPos, final float renderPartialTicks) {
        return finalPos + (originalPos - finalPos) * EntityUtils.mc.getRenderPartialTicks();
    }
    
    public static Vec3d interpolateEntityByTicks(final Entity entity, final float renderPartialTicks) {
        return new Vec3d(calculateDistanceWithPartialTicks(entity.posX, entity.lastTickPosX, renderPartialTicks) - EntityUtils.mc.getRenderManager().renderPosX, calculateDistanceWithPartialTicks(entity.posY, entity.lastTickPosY, renderPartialTicks) - EntityUtils.mc.getRenderManager().renderPosY, calculateDistanceWithPartialTicks(entity.posZ, entity.lastTickPosZ, renderPartialTicks) - EntityUtils.mc.getRenderManager().renderPosZ);
    }
    
    public static double getDistance(final double x, final double y, final double z, final double finalX, final double finalY, final double finalZ) {
        final double interpolationX = x - finalX;
        final double interpolationY = y - finalY;
        final double interpolationZ = z - finalZ;
        return MathHelper.sqrt(interpolationX * interpolationX + interpolationY * interpolationY + interpolationZ * interpolationZ);
    }
    
    public static BlockPos getEntityPosFloored(final Entity entity) {
        return new BlockPos(Math.floor(entity.posX), Math.floor(entity.posY), Math.floor(entity.posZ));
    }
    
    public static boolean isIntercepted(final BlockPos pos) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos);
        for (final Entity entity : EntityUtils.mc.world.loadedEntityList) {
            if (bb.intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean placeable(final BlockPos pos) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos);
        final Block block = WorldUtils.getBlock(pos);
        if (!WorldUtils.empty.contains(block)) {
            return false;
        }
        for (final Entity entity : EntityUtils.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityItem)) {
                if (entity instanceof EntityXPOrb) {
                    continue;
                }
                if (bb.intersects(entity.getEntityBoundingBox())) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    public static void setTimer(final float speed) {
        EntityUtils.mc.timer.tickLength = 50.0f / speed;
    }
    
    public static void resetTimer() {
        EntityUtils.mc.timer.tickLength = 50.0f;
    }
    
    public static boolean isInLiquid() {
        if (EntityUtils.mc.player == null) {
            return false;
        }
        if (EntityUtils.mc.player.fallDistance >= 3.0f) {
            return false;
        }
        boolean inLiquid = false;
        final AxisAlignedBB bb = (EntityUtils.mc.player.getRidingEntity() != null) ? EntityUtils.mc.player.getRidingEntity().getEntityBoundingBox() : EntityUtils.mc.player.getEntityBoundingBox();
        final int y = (int)bb.minY;
        for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; ++x) {
            for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; ++z) {
                final Block block = EntityUtils.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (!(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
    
    public static Vec3d getEyePos(final Entity entity) {
        return entity.getPositionVector().add(0.0, (double)entity.getEyeHeight(), 0.0);
    }
    
    public static boolean isNaked(final EntityLivingBase entity) {
        boolean flag = false;
        for (final ItemStack stack : entity.getArmorInventoryList()) {
            if (stack.getItem() != Items.AIR || !stack.isEmpty()) {
                flag = true;
                break;
            }
        }
        return !flag;
    }
}
