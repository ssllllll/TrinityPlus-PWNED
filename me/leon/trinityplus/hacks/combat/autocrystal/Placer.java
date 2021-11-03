//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat.autocrystal;

import me.leon.trinityplus.utils.*;
import me.zero.alpine.fork.listener.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.hacks.combat.*;
import me.leon.trinityplus.utils.entity.*;
import me.leon.trinityplus.utils.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.stream.*;
import java.util.*;

public class Placer implements Util, Listenable
{
    private Mapper mapper;
    private final Class<? extends Entity>[] excluded;
    
    public Placer() {
        this.excluded = (Class<? extends Entity>[])new Class[] { EntityExpBottle.class, EntityEnderPearl.class, EntityItem.class, EntityLivingBase.class };
        this.mapper = new Mapper(this);
    }
    
    public void placeCrystal() {
        if (Utility.nullCheck()) {
            return;
        }
        if (!AutoCrystal.Place.getValue()) {
            return;
        }
        if (AutoCrystal.threadedPlace.getValue()) {
            new Mapper(this).start();
        }
        else {
            this.mapper.run();
        }
        if (AutoCrystal.curPosPlace == null) {
            return;
        }
        if (EntityUtils.isInHole(AutoCrystal.target) && AutoCrystal.limit.getValue() && AutoCrystal.target.hurtTime != 0) {
            return;
        }
        if (!AutoCrystal.placeTimer.hasPassAndReset((int)AutoCrystal.placeDelay.getValue() * 50)) {
            return;
        }
        if (Utility.getSelfHealth() + 1.0f < AutoCrystal.pauseHealth.getValue() && (AutoCrystal.noSuicide.getValue().equalsIgnoreCase("Place") || AutoCrystal.noSuicide.getValue().equalsIgnoreCase("Both"))) {
            return;
        }
        final int oldslot = Placer.mc.player.inventory.currentItem;
        final String value = AutoCrystal.switchPlace.getValue();
        switch (value) {
            case "None": {
                if (Placer.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && Placer.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                    return;
                }
                Placer.mc.playerController.updateController();
                break;
            }
            case "Packet":
            case "Normal": {
                if (InventoryUtil.amountInHotbar(Items.END_CRYSTAL, false) <= 0) {
                    return;
                }
                InventoryUtil.switchToSlot(InventoryUtil.find(Items.END_CRYSTAL));
                break;
            }
        }
        this.place(AutoCrystal.curPosPlace.getBase());
        Utility.swingHand(false);
        AutoCrystal.placedCrystals.add(AutoCrystal.curPosPlace);
        if (AutoCrystal.switchPlace.getValue().equalsIgnoreCase("Packet")) {
            Placer.mc.player.inventory.currentItem = oldslot;
            Placer.mc.playerController.updateController();
        }
    }
    
    private boolean filterReachable(final BlockPos pos) {
        final String value = AutoCrystal.rayTracePlaceMode.getValue();
        switch (value) {
            case "Leon": {
                final Vec3d vec = RaytraceUtils.rayTraceLeon(pos);
                final boolean walls = vec == null;
                if (walls) {
                    final EnumFacing facing = Utility.getEnumFacing(true, pos);
                    final Vec3d rayTraceVec = new Vec3d(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5).add(new Vec3d(facing.getDirectionVec()).scale(0.5099999904632568));
                    if (WorldUtils.isWithin(AutoCrystal.placeRangeWalls.getValue(), Utility.eyePos(), rayTraceVec)) {
                        return true;
                    }
                    break;
                }
                else {
                    if (WorldUtils.isWithin(AutoCrystal.placeRange.getValue(), Utility.eyePos(), vec) || !AutoCrystal.doubleCheck.getValue()) {
                        return true;
                    }
                    break;
                }
                break;
            }
            case "Offset":
            case "Simple": {
                final EnumFacing facing2 = Utility.getEnumFacing(true, pos);
                final Vec3d rayTraceVec2 = new Vec3d(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5).add(new Vec3d(facing2.getDirectionVec()).scale(0.5099999904632568));
                final boolean walls2 = RaytraceUtils.rayTraceSimple(rayTraceVec2, AutoCrystal.rayTracePlaceMode.getValue().equalsIgnoreCase("Offset") ? AutoCrystal.offsetPlace.getValue() : 0.0);
                if (walls2) {
                    if (WorldUtils.isWithin(AutoCrystal.placeRangeWalls.getValue(), Utility.eyePos(), rayTraceVec2)) {
                        return true;
                    }
                    break;
                }
                else {
                    if (WorldUtils.isWithin(AutoCrystal.placeRange.getValue(), Utility.eyePos(), rayTraceVec2) || !AutoCrystal.doubleCheck.getValue()) {
                        return true;
                    }
                    break;
                }
                break;
            }
        }
        return false;
    }
    
    private boolean filterDamage(final CrystalPosition possible) {
        if (Utility.eyePos().squareDistanceTo(new Vec3d((Vec3i)possible.getBase())) > AutoCrystal.placeRange.getValue() * AutoCrystal.placeRange.getValue()) {
            return false;
        }
        final float maxSelf = (AutoCrystal.totemTimer.getDelta() < 0L) ? AutoCrystal.maxSelfDamagePlace.floatValue() : AutoCrystal.antiTotemMaxSelf.floatValue();
        final float minTarget = (AutoCrystal.totemTimer.getDelta() < 0L) ? AutoCrystal.minTargetDamagePlace.floatValue() : AutoCrystal.antiTotemMin.floatValue();
        final float minTargetHole = (AutoCrystal.totemTimer.getDelta() < 0L && AutoCrystal.overrideFacePlaceTotem.getValue()) ? AutoCrystal.facePlaceMinDamage.floatValue() : AutoCrystal.antiTotemMin.floatValue();
        if (!EntityUtils.isInHole(AutoCrystal.target)) {
            if (possible.getSelfDamage() <= maxSelf) {
                return possible.getTargetDamage() > minTarget;
            }
        }
        else if (AutoCrystal.facePlace.getValue()) {
            if (AutoCrystal.forceBind.down()) {
                return true;
            }
            if (AutoCrystal.target instanceof EntityPlayer && ((EntityUtils.getArmor((EntityPlayer)AutoCrystal.target, AutoCrystal.armorBreakerScale.getValue()) && AutoCrystal.armorBreaker.getValue()) || Utility.getTargetHealth() < AutoCrystal.facePlaceMinHealth.getValue() || possible.getTargetDamage() > minTargetHole) && possible.getSelfDamage() <= maxSelf) {
                return possible.getTargetDamage() > minTargetHole;
            }
        }
        return false;
    }
    
    private boolean filterPlaceable(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final AxisAlignedBB bb = new AxisAlignedBB(boost).expand(0.0, 1.0, 0.0);
        if ((Placer.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && Placer.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) || (!AutoCrystal.version.getValue().equalsIgnoreCase("1.13+") && (Placer.mc.world.getBlockState(boost).getBlock() != Blocks.AIR || Placer.mc.world.getBlockState(boost.up()).getBlock() != Blocks.AIR))) {
            return false;
        }
        final Iterator<Entity> iterator = (Iterator<Entity>)Placer.mc.world.getEntitiesWithinAABB((Class)Entity.class, bb).iterator();
        if (!iterator.hasNext()) {
            return true;
        }
        final Entity en = iterator.next();
        final String value = AutoCrystal.multiPlace.getValue();
        switch (value) {
            case "Full": {
                return en.isDead;
            }
            case "Semi": {
                return en.isDead && new AxisAlignedBB(en.getPositionVector().add(-0.5, -1.0, -0.5), en.getPositionVector().add(0.5, 0.0, 0.5)).intersects(new AxisAlignedBB(blockPos));
            }
            default: {
                return !(en instanceof EntityPlayer) && !(en instanceof EntityExpBottle) && !(en instanceof EntityItem);
            }
        }
    }
    
    private void place(final BlockPos pos) {
        final EnumFacing facing = Utility.getEnumFacing(AutoCrystal.bounds.getValue(), pos);
        final Vec3d vec = new Vec3d(facing.getDirectionVec()).scale(0.5).add(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5);
        Utility.rotate(false, vec);
        if (AutoCrystal.packetPlace.getValue()) {
            final CPacketPlayerTryUseItemOnBlock packet = new CPacketPlayerTryUseItemOnBlock();
            packet.position = pos;
            packet.hand = Utility.getCrystalHand();
            packet.placedBlockDirection = facing;
            packet.facingX = (float)vec.x - pos.getX();
            packet.facingY = (float)vec.y - pos.getY();
            packet.facingZ = (float)vec.z - pos.getZ();
            Placer.mc.player.connection.sendPacket((Packet)packet);
        }
        else {
            Placer.mc.playerController.processRightClickBlock(Placer.mc.player, Placer.mc.world, pos, facing, vec, Utility.getCrystalHand());
        }
    }
    
    private float heuristic(final CrystalPosition pos) {
        final String value = AutoCrystal.heuristic.getValue();
        switch (value) {
            case "Target": {
                return pos.getTargetDamage();
            }
            case "Self": {
                return pos.getSelfDamage();
            }
            case "Subtract": {
                return pos.getTargetDamage() - pos.getSelfDamage();
            }
            case "Atomic": {
                return (float)(pos.getTargetDamage() - (pos.getSelfDamage() + Placer.mc.player.getPositionVector().distanceTo(pos.getCrystalVec())));
            }
            default: {
                return 0.0f;
            }
        }
    }
    
    private static class Mapper extends Thread
    {
        private final Placer placer;
        
        private Mapper(final Placer placer) {
            this.placer = placer;
        }
        
        @Override
        public void run() {
            if (Utility.nullCheck()) {
                return;
            }
            if (!AutoCrystal.Place.getValue()) {
                return;
            }
            if (AutoCrystal.target == null) {
                Utility.reset();
                return;
            }
            final List<CrystalPosition> filtered = WorldUtils.getSphere(EntityUtils.getEntityPosFloored((Entity)Util.mc.player), (float)AutoCrystal.placeRange.getValue(), (int)AutoCrystal.placeRange.getValue(), false, true, 0).stream().filter(x$0 -> this.placer.filterPlaceable(x$0)).filter(x$0 -> this.placer.filterReachable(x$0)).map(e -> new CrystalPosition(Utility.calcDamage(e, AutoCrystal.target), Utility.calcDamage(e, (EntityLivingBase)Util.mc.player), e)).filter(x$0 -> this.placer.filterDamage(x$0)).collect((Collector<? super Object, ?, List<CrystalPosition>>)Collectors.toList());
            CrystalPosition finalPosition;
            if (AutoCrystal.placeMode.getValue().equals("Damage")) {
                if (AutoCrystal.heuristic.getValue().equals("Self")) {
                    finalPosition = filtered.stream().min(Comparator.comparing(x$0 -> this.placer.heuristic(x$0))).orElse(null);
                }
                else {
                    finalPosition = filtered.stream().max(Comparator.comparing(x$0 -> this.placer.heuristic(x$0))).orElse(null);
                }
            }
            else {
                finalPosition = filtered.stream().min(Comparator.comparing(e -> e.getCrystalVec().squareDistanceTo(AutoCrystal.target.getPositionVector()))).orElse(null);
            }
            AutoCrystal.curPosPlace = finalPosition;
        }
    }
}
