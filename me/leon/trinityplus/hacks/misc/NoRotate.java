//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.misc;

import me.leon.trinityplus.events.main.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import java.util.function.*;
import net.minecraft.entity.player.*;

public class NoRotate extends Module
{
    @EventHandler
    private final Listener<EventPacketRecieve> PacketEvent;
    
    public NoRotate() {
        super("NoRotate", "Prevents the server from rotating you", Category.MISC);
        EntityPlayer entityplayer;
        SPacketPlayerPosLook packetIn;
        double d0;
        double d2;
        double d3;
        this.PacketEvent = new Listener<EventPacketRecieve>(p_Event -> {
            if (!this.nullCheck()) {
                if (p_Event.getPacket() instanceof SPacketPlayerPosLook && NoRotate.mc.player != null && Objects.requireNonNull(NoRotate.mc.getConnection()).doneLoadingTerrain) {
                    p_Event.cancel();
                    entityplayer = (EntityPlayer)NoRotate.mc.player;
                    packetIn = (SPacketPlayerPosLook)p_Event.getPacket();
                    d0 = packetIn.getX();
                    d2 = packetIn.getY();
                    d3 = packetIn.getZ();
                    if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X)) {
                        d0 += entityplayer.posX;
                    }
                    else {
                        entityplayer.motionX = 0.0;
                    }
                    if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
                        d2 += entityplayer.posY;
                    }
                    else {
                        entityplayer.motionY = 0.0;
                    }
                    if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
                        d3 += entityplayer.posZ;
                    }
                    else {
                        entityplayer.motionZ = 0.0;
                    }
                    entityplayer.setPosition(d0, d2, d3);
                    NoRotate.mc.getConnection().sendPacket((Packet)new CPacketConfirmTeleport(packetIn.getTeleportId()));
                    NoRotate.mc.getConnection().sendPacket((Packet)new CPacketPlayer.PositionRotation(entityplayer.posX, entityplayer.getEntityBoundingBox().minY, entityplayer.posZ, packetIn.yaw, packetIn.pitch, false));
                }
            }
        }, (Predicate<EventPacketRecieve>[])new Predicate[0]);
    }
}
