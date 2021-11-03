//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.movement;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.utils.misc.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

public class VClip extends Module
{
    public static BooleanSetting autoadjust;
    public static SliderSetting offset;
    boolean isSneaking;
    
    public VClip() {
        super("VClip", "tp to y0", Category.MOVEMENT);
    }
    
    public void onEnable() {
        if (VClip.mc.player == null) {
            return;
        }
        if (VClip.mc.isSingleplayer()) {
            MessageUtil.sendClientMessage("You are in singleplayer", true);
            this.setEnabled(false);
            return;
        }
        VClip.mc.player.jump();
    }
    
    public void onDisable() {
        if (VClip.mc.player == null) {
            return;
        }
        if (this.isSneaking) {
            VClip.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)VClip.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (VClip.autoadjust.getValue()) {
            VClip.offset.setValue(-VClip.mc.player.posY - 2.0);
        }
        if (this.isSneaking) {
            VClip.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)VClip.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + VClip.offset.getValue(), VClip.mc.player.posZ, false));
        VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + 0.41999998688698, VClip.mc.player.posZ, true));
        VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + 0.7531999805211997, VClip.mc.player.posZ, true));
        VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + 1.00133597911214, VClip.mc.player.posZ, true));
        VClip.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(VClip.mc.player.posX, VClip.mc.player.posY + 1.16610926093821, VClip.mc.player.posZ, true));
        VClip.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)VClip.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        VClip.mc.player.setSneaking(false);
        this.setEnabled(false);
    }
    
    static {
        VClip.autoadjust = new BooleanSetting("Auto Adjust", true);
        VClip.offset = new SliderSetting("Offset", -70.0, 6.0, 70.0, false);
    }
}
