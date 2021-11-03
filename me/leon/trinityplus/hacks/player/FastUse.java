//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.player;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class FastUse extends Module
{
    public static final BooleanSetting exp;
    public static final BooleanSetting obi;
    public static final BooleanSetting echests;
    public static final BooleanSetting crystals;
    public static final BooleanSetting everything;
    
    public FastUse() {
        super("FastUse", "Use items faster", Category.PLAYER);
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (FastUse.everything.getValue()) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
        if (FastUse.exp.getValue() && (FastUse.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || FastUse.mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle)) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
        if (FastUse.obi.getValue() && (Block.getBlockFromItem(FastUse.mc.player.getHeldItemMainhand().getItem()) == Blocks.OBSIDIAN || Block.getBlockFromItem(FastUse.mc.player.getHeldItemOffhand().getItem()) == Blocks.OBSIDIAN)) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
        if (FastUse.crystals.getValue() && (FastUse.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || FastUse.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)) {
            final RayTraceResult r = FastUse.mc.objectMouseOver;
            if (FastUse.mc.gameSettings.keyBindUseItem.isKeyDown() && r.typeOfHit == RayTraceResult.Type.BLOCK && !FastUse.mc.player.isHandActive()) {
                FastUse.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(r.getBlockPos(), r.sideHit, (FastUse.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, (float)r.hitVec.x, (float)r.hitVec.y, (float)r.hitVec.z));
            }
        }
        if (FastUse.echests.getValue() && (Block.getBlockFromItem(FastUse.mc.player.getHeldItemMainhand().getItem()) == Blocks.ENDER_CHEST || Block.getBlockFromItem(FastUse.mc.player.getHeldItemOffhand().getItem()) == Blocks.ENDER_CHEST)) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
    }
    
    static {
        exp = new BooleanSetting("EXP", true);
        obi = new BooleanSetting("Obsidian", true);
        echests = new BooleanSetting("Ender Chests", true);
        crystals = new BooleanSetting("Crystals", true);
        everything = new BooleanSetting("Everything", true);
    }
}
