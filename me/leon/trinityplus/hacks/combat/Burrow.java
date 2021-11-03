//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat;

import me.leon.trinityplus.setting.rewrite.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.events.main.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import java.util.function.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import me.leon.trinityplus.utils.world.*;
import net.minecraft.util.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraft.network.play.client.*;

public class Burrow extends Module
{
    private final ModeSetting mode;
    private final ModeSetting blocksToUse;
    private final ModeSetting backupBlock;
    private final BooleanSetting rotate;
    private final BooleanSetting preventBlockPush;
    private final BooleanSetting sneak;
    private final BooleanSetting packetPlace;
    private final SliderSetting fakeClipHeight;
    public static BooleanSetting smart;
    public static SliderSetting distance;
    public static BooleanSetting predict;
    public static SliderSetting ticks;
    private int oldSelection;
    private BlockPos playerPos;
    private boolean burrowing;
    @EventHandler
    public Listener<BlockPushEvent> onBurrowPush;
    
    public Burrow() {
        super("InstantBurrow", "Instantly Burrow / glitch yourself into a block to avoid being crystalled", Category.COMBAT);
        this.mode = new ModeSetting("Mode", "Packet", new String[] { "Normal", "Packet", "Double" });
        this.blocksToUse = new ModeSetting("Blocks", "Obsidian", new String[] { "Obsidian", "EChest", "Skull" });
        this.backupBlock = new ModeSetting("Backup", "EChest", new String[] { "Obsidian", "EChest", "Skull" });
        this.rotate = new BooleanSetting("Rotate", true);
        this.preventBlockPush = new BooleanSetting("NoBlockPush", true);
        this.sneak = new BooleanSetting("Sneak", false);
        this.packetPlace = new BooleanSetting("PacketPlace", false);
        this.fakeClipHeight = new SliderSetting("Clip", -5.0, 2.0, 5.0, false);
        this.oldSelection = -1;
        this.onBurrowPush = new Listener<BlockPushEvent>(event -> {
            if (this.preventBlockPush.getValue()) {
                event.cancel();
            }
        }, (Predicate<BlockPushEvent>[])new Predicate[0]);
    }
    
    private Block getCurrBlock() {
        Block index = null;
        final String value = this.blocksToUse.getValue();
        switch (value) {
            case "Obsidian": {
                index = Blocks.OBSIDIAN;
                break;
            }
            case "EChest": {
                index = Blocks.ENDER_CHEST;
                break;
            }
            case "Skull": {
                index = (Block)Blocks.SKULL;
                break;
            }
        }
        return index;
    }
    
    private Block getBackBlock() {
        Block index = null;
        final String value = this.backupBlock.getValue();
        switch (value) {
            case "Obsidian": {
                index = Blocks.OBSIDIAN;
                break;
            }
            case "EChest": {
                index = Blocks.ENDER_CHEST;
                break;
            }
            case "Skull": {
                index = (Block)Blocks.SKULL;
                break;
            }
        }
        return index;
    }
    
    private void switchToBlock() {
        this.oldSelection = Burrow.mc.player.inventory.currentItem;
        int newSelection = InventoryUtil.getBlockInHotbar(this.getCurrBlock());
        if (newSelection == -1) {
            newSelection = InventoryUtil.getBlockInHotbar(this.getBackBlock());
        }
        if (newSelection != -1) {
            if (!this.packetPlace.getValue()) {
                Burrow.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(newSelection));
            }
            else {
                InventoryUtil.switchToSlot(newSelection);
            }
        }
        else {
            this.setEnabled(false);
        }
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        this.playerPos = new BlockPos(Burrow.mc.player.posX, Burrow.mc.player.posY, Burrow.mc.player.posZ);
        if (!WorldUtils.empty.contains(WorldUtils.getBlock(this.playerPos.up(2))) && !Burrow.smart.getValue()) {
            this.toggleWithMessage("There is a block above your head!");
            return;
        }
        if (this.check()) {
            return;
        }
        this.jump();
        if (this.mode.getValue().equalsIgnoreCase("Packet")) {
            this.burrow();
        }
    }
    
    @Override
    public void onDisable() {
        if (this.oldSelection != -1) {
            if (!this.packetPlace.getValue()) {
                Burrow.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSelection));
            }
            else {
                InventoryUtil.switchToSlot(this.oldSelection);
            }
        }
        this.oldSelection = -1;
        this.burrowing = false;
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if ((this.mode.getValue().equalsIgnoreCase("Double") || this.mode.getValue().equalsIgnoreCase("Normal")) && Burrow.mc.player.posY >= this.playerPos.getY() + 1.12) {
            this.burrow();
        }
    }
    
    private void burrow() {
        this.switchToBlock();
        BlockUtils.placeBlock(this.playerPos, EnumHand.MAIN_HAND, this.rotate.getValue(), this.packetPlace.getValue(), this.sneak.getValue());
        this.rubberBand();
        this.setEnabled(false);
    }
    
    public void fakeJump() {
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 0.4, Burrow.mc.player.posZ, true));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 0.75, Burrow.mc.player.posZ, true));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 1.01, Burrow.mc.player.posZ, true));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 1.15, Burrow.mc.player.posZ, true));
    }
    
    private boolean check() {
        if (Burrow.mc.world.getBlockState(this.playerPos).getBlock() == this.getCurrBlock() || Burrow.mc.world.getBlockState(this.playerPos).getBlock() == this.getBackBlock()) {
            this.setEnabled(false);
            return true;
        }
        if (InventoryUtil.amountBlockInHotbar(this.getCurrBlock()) <= 0 && InventoryUtil.amountBlockInHotbar(this.getBackBlock()) <= 0) {
            this.setEnabled(false);
            return true;
        }
        return false;
    }
    
    private void jump() {
        if (this.mode.getValue().equalsIgnoreCase("Normal") || this.mode.getValue().equalsIgnoreCase("Double")) {
            Burrow.mc.player.jump();
        }
        else if (this.mode.getValue().equalsIgnoreCase("Packet")) {
            this.fakeJump();
        }
    }
    
    private void rubberBand() {
        if (this.mode.getValue().equalsIgnoreCase("Packet") || this.mode.getValue().equalsIgnoreCase("Normal")) {
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + this.fakeClipHeight.getValue(), Burrow.mc.player.posZ, false));
        }
        else if (this.mode.getValue().equalsIgnoreCase("Double")) {
            Burrow.mc.player.jump();
        }
    }
    
    static {
        Burrow.smart = new BooleanSetting("Smart", true, true);
        Burrow.distance = new SliderSetting("Distance", Burrow.smart, 0.0, 1.0, 3.0, false);
        Burrow.predict = new BooleanSetting("Predict", Burrow.smart, true);
        Burrow.ticks = new SliderSetting("Ticks", Burrow.smart, 1.0, 3.0, 5.0, true);
    }
}
