//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat.autocrystal;

import me.leon.trinityplus.utils.*;
import me.leon.trinityplus.hacks.combat.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.leon.trinityplus.utils.world.Rotation.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import me.leon.trinityplus.utils.world.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.utils.entity.*;

public class Utility implements Util
{
    public static void swingHand(final boolean Break) {
        if (!Break) {
            final String value = AutoCrystal.swingArmPlace.getValue();
            switch (value) {
                case "Mainhand": {
                    if (AutoCrystal.packetSwingPlace.getValue()) {
                        Utility.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        break;
                    }
                    Utility.mc.player.swingArm(EnumHand.MAIN_HAND);
                    break;
                }
                case "Offhand": {
                    if (AutoCrystal.packetSwingPlace.getValue()) {
                        Utility.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
                        break;
                    }
                    Utility.mc.player.swingArm(EnumHand.OFF_HAND);
                    break;
                }
                case "Both": {
                    if (AutoCrystal.packetSwingPlace.getValue()) {
                        Utility.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
                        Utility.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        break;
                    }
                    Utility.mc.player.swingArm(EnumHand.MAIN_HAND);
                    Utility.mc.player.swingArm(EnumHand.OFF_HAND);
                    break;
                }
            }
        }
        else if (AutoCrystal.curBreakCrystal != null) {
            final String value2 = AutoCrystal.swingArmBreak.getValue();
            switch (value2) {
                case "Mainhand": {
                    if (AutoCrystal.packetSwingBreak.getValue()) {
                        Utility.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        break;
                    }
                    Utility.mc.player.swingArm(EnumHand.MAIN_HAND);
                    break;
                }
                case "Offhand": {
                    if (AutoCrystal.packetSwingBreak.getValue()) {
                        Utility.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
                        break;
                    }
                    Utility.mc.player.swingArm(EnumHand.OFF_HAND);
                    break;
                }
                case "Both": {
                    if (AutoCrystal.packetSwingBreak.getValue()) {
                        Utility.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
                        Utility.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        break;
                    }
                    Utility.mc.player.swingArm(EnumHand.MAIN_HAND);
                    Utility.mc.player.swingArm(EnumHand.OFF_HAND);
                    break;
                }
            }
        }
    }
    
    public static void rotate(final boolean Break, final Vec3d vec) {
        if (Break && AutoCrystal.breakRotate.getValue()) {
            RotationUtils.rotateTowards(vec, !AutoCrystal.clientRotate.getValue(), Priority.Highest);
        }
        if (!Break && AutoCrystal.placeRotate.getValue()) {
            RotationUtils.rotateTowards(vec, !AutoCrystal.clientRotate.getValue(), Priority.Highest);
        }
    }
    
    public static EnumHand getCrystalHand() {
        if (Utility.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            return EnumHand.MAIN_HAND;
        }
        if (Utility.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            return EnumHand.OFF_HAND;
        }
        return null;
    }
    
    public static EnumFacing getEnumFacing(final boolean rayTrace, final BlockPos placePosition) {
        final RayTraceResult result = Utility.mc.world.rayTraceBlocks(new Vec3d(Utility.mc.player.posX, Utility.mc.player.posY + Utility.mc.player.getEyeHeight(), Utility.mc.player.posZ), new Vec3d(placePosition.getX() + 0.5, placePosition.getY() + 0.5, placePosition.getZ() + 0.5));
        if (placePosition.getY() == 255) {
            return EnumFacing.DOWN;
        }
        if (rayTrace) {
            return (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        }
        return EnumFacing.UP;
    }
    
    public static float calcDamage(final EntityEnderCrystal crystal, final EntityLivingBase entity) {
        return calcDamage(crystal.getPositionVector(), entity);
    }
    
    public static float calcDamage(final BlockPos pos, final EntityLivingBase entity) {
        return calcDamage(new Vec3d(pos.x + 0.5, pos.y + 1.0, pos.z + 0.5), entity);
    }
    
    public static float calcDamage(final Vec3d vec, final EntityLivingBase entity) {
        if (entity == null) {
            return 0.0f;
        }
        if (entity == Utility.mc.player) {
            return DamageUtils.calculateDamage(vec, (Entity)entity, AutoCrystal.selfPredict.getValue(), (int)AutoCrystal.selfPredictTicks.getValue());
        }
        return DamageUtils.calculateDamage(vec, (Entity)entity, AutoCrystal.predict.getValue(), (int)AutoCrystal.predictTicks.getValue());
    }
    
    public static float getSelfHealth() {
        return Utility.mc.player.getHealth() + Utility.mc.player.getAbsorptionAmount();
    }
    
    public static float getTargetHealth() {
        return AutoCrystal.target.getHealth() + AutoCrystal.target.getAbsorptionAmount();
    }
    
    public static boolean nullCheck() {
        return Utility.mc.player == null || Utility.mc.world == null;
    }
    
    public static Vec3d eyePos() {
        return EntityUtils.getEyePos((Entity)Utility.mc.player);
    }
    
    public static void reset() {
        AutoCrystal.placeTimer.reset();
        AutoCrystal.breakTimer.reset();
        AutoCrystal.placedCrystals.clear();
        AutoCrystal.curPosPlace = null;
        AutoCrystal.curBreakCrystal = null;
    }
}
