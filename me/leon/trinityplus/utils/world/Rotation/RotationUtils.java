//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.world.Rotation;

import me.leon.trinityplus.utils.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import me.leon.trinityplus.main.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.events.main.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.utils.world.*;

public class RotationUtils implements Util
{
    private static final Random random;
    
    public static void updateRotationPackets(final SpoofEvent event) {
        if (RotationUtils.mc.player.isSprinting() != RotationUtils.mc.player.serverSprintState) {
            if (RotationUtils.mc.player.isSprinting()) {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RotationUtils.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RotationUtils.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            RotationUtils.mc.player.serverSprintState = RotationUtils.mc.player.isSprinting();
        }
        if (RotationUtils.mc.player.isSneaking() != RotationUtils.mc.player.serverSneakState) {
            if (RotationUtils.mc.player.isSneaking()) {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RotationUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RotationUtils.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            RotationUtils.mc.player.serverSneakState = RotationUtils.mc.player.isSneaking();
        }
        if (!event.hasLocation) {
            event.posX = RotationUtils.mc.player.posX;
            event.posY = RotationUtils.mc.player.posY;
            event.posZ = RotationUtils.mc.player.posZ;
            event.onGround = RotationUtils.mc.player.onGround;
        }
        if (!event.hasRotation) {
            event.pitch = RotationUtils.mc.player.rotationPitch;
            event.yaw = RotationUtils.mc.player.rotationYaw;
        }
        final double updatedPosX = event.posX - RotationUtils.mc.player.lastReportedPosX;
        final double updatedPosY = event.posY - RotationUtils.mc.player.lastReportedPosY;
        final double updatedPosZ = event.posZ - RotationUtils.mc.player.lastReportedPosZ;
        final double updatedRotationYaw = event.yaw - RotationUtils.mc.player.lastReportedYaw;
        final double updatedRotationPitch = event.pitch - RotationUtils.mc.player.lastReportedPitch;
        final EntityPlayerSP player = RotationUtils.mc.player;
        ++player.positionUpdateTicks;
        boolean positionUpdate = updatedPosX * updatedPosX + updatedPosY * updatedPosY + updatedPosZ * updatedPosZ > 9.0E-4 || RotationUtils.mc.player.positionUpdateTicks >= 20 || event.hasLocation;
        final boolean rotationUpdate = updatedRotationYaw != 0.0 || updatedRotationPitch != 0.0 || event.hasRotation;
        if (RotationUtils.mc.player.isRiding()) {
            RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(RotationUtils.mc.player.motionX, -999.0, RotationUtils.mc.player.motionZ, event.yaw, event.pitch, event.onGround));
            positionUpdate = false;
        }
        else if (positionUpdate && rotationUpdate) {
            RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(event.posX, event.posY, RotationUtils.mc.player.posZ, event.yaw, event.pitch, event.onGround));
        }
        else if (positionUpdate) {
            RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(event.posX, event.posY, event.posZ, event.onGround));
        }
        else if (rotationUpdate) {
            RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(event.yaw, event.pitch, event.onGround));
        }
        else if (RotationUtils.mc.player.prevOnGround != event.onGround) {
            RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer(event.onGround));
        }
        if (event.hasLocation) {
            Trinity.dispatcher.post(new LocationSpoofEvent(EventStage.POST, event));
        }
        if (positionUpdate) {
            RotationUtils.mc.player.lastReportedPosX = event.posX;
            RotationUtils.mc.player.lastReportedPosY = event.posY;
            RotationUtils.mc.player.lastReportedPosZ = event.posZ;
            RotationUtils.mc.player.positionUpdateTicks = 0;
        }
        if (rotationUpdate) {
            RotationUtils.mc.player.lastReportedYaw = event.yaw;
            RotationUtils.mc.player.lastReportedPitch = event.pitch;
        }
        RotationUtils.mc.player.prevOnGround = event.onGround;
        RotationUtils.mc.player.autoJumpEnabled = RotationUtils.mc.player.mc.gameSettings.autoJump;
    }
    
    public static float[] getNeededRotations(final Vec3d vec) {
        final Vec3d eyesPos = RotationUtils.mc.player.getPositionVector().add(0.0, (double)RotationUtils.mc.player.eyeHeight, 0.0);
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { RotationUtils.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - RotationUtils.mc.player.rotationYaw), RotationUtils.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - RotationUtils.mc.player.rotationPitch) };
    }
    
    public static void rotateTowards(final Vec3d vec) {
        final float[] rots = getNeededRotations(vec);
        final Rotation rot = new Rotation(rots[1], rots[0], true);
        SpoofingManager.rotationQueue.add(rot);
    }
    
    public static void rotateTowards(final Vec3d vec, final boolean packet) {
        final float[] rots = getNeededRotations(vec);
        final Rotation rot = new Rotation(rots[1], rots[0], packet, Priority.Highest);
        SpoofingManager.rotationQueue.add(rot);
    }
    
    public static void rotateTowards(final Vec3d vec, final boolean packet, final Priority prio) {
        final float[] rots = getNeededRotations(vec);
        final Rotation rot = new Rotation(rots[1], rots[0], packet, prio);
        SpoofingManager.rotationQueue.add(rot);
    }
    
    public static void rotateRandom(final boolean packet, final Priority prio) {
        final Rotation rot = new Rotation(RotationUtils.random.nextFloat() * 360.0f - 180.0f, RotationUtils.random.nextFloat() * 360.0f - 180.0f, packet, prio);
        SpoofingManager.rotationQueue.add(rot);
    }
    
    public static void rotateRandom(final boolean packet) {
        final Rotation rot = new Rotation(RotationUtils.random.nextFloat() * 360.0f - 180.0f, RotationUtils.random.nextFloat() * 360.0f - 180.0f, packet, Priority.Highest);
        SpoofingManager.rotationQueue.add(rot);
    }
    
    public static void rotateRandom() {
        final Rotation rot = new Rotation(RotationUtils.random.nextFloat() * 360.0f - 180.0f, RotationUtils.random.nextFloat() * 360.0f - 180.0f, true, Priority.Highest);
        SpoofingManager.rotationQueue.add(rot);
    }
    
    static {
        random = new Random();
    }
}
