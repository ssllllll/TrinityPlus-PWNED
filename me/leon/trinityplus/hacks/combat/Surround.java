//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraftforge.client.event.*;
import java.awt.*;
import me.leon.trinityplus.utils.rendering.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.leon.trinityplus.utils.misc.*;
import me.leon.trinityplus.hacks.exploits.*;
import me.leon.trinityplus.managers.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.utils.world.*;
import net.minecraft.util.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import java.util.*;

public class Surround extends Module
{
    public static BooleanSetting rotate;
    public static BooleanSetting packet;
    public static BooleanSetting shift;
    public static ModeSetting center;
    public static SliderSetting bps;
    private final BlockPos[] pos;
    private BlockPos draw;
    private BlockPos startPos;
    
    public Surround() {
        super("Surround", "Surrounds your feet in obsidian", Category.COMBAT);
        this.pos = new BlockPos[] { new BlockPos(0, 0, 1), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(-1, 0, 0) };
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (this.draw != null) {
            Tessellator.drawGradientAlphaCubeOutline(new AxisAlignedBB(this.draw), 3.0f, new Color(255, 0, 255, 255));
        }
    }
    
    @Override
    public void onEnable() {
        this.startPos = PlayerUtils.getPlayerPosFloored();
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (InventoryUtil.findHotbarBlock(BlockObsidian.class) == -1) {
            MessageUtil.sendClientMessage("No obsidian!", true);
            this.toggle();
            return;
        }
        if (!this.startPos.equals((Object)PlayerUtils.getPlayerPosFloored())) {
            this.toggle();
            return;
        }
        if (Surround.shift.getValue() && !Surround.mc.gameSettings.keyBindSneak.isKeyDown()) {
            this.draw = null;
            return;
        }
        if (Surround.shift.getValue() && Surround.mc.gameSettings.keyBindSneak.isKeyDown() && (Surround.mc.player.isElytraFlying() || Surround.mc.player.capabilities.isFlying)) {
            this.draw = null;
            return;
        }
        if (ModuleManager.getMod(PacketFly.class).isEnabled()) {
            this.draw = null;
            return;
        }
        final BlockPos playerPos = PlayerUtils.getPlayerPosFloored();
        int placed = 0;
        final int oldslot = Surround.mc.player.inventory.currentItem;
        final ArrayList<BlockPos> blocks = EntityUtils.getPos(0.0, 0.0, 0.0, (Entity)Surround.mc.player);
        if (blocks.size() == 2 && Surround.center.getValue().equalsIgnoreCase("None")) {
            final BlockPos pos2 = blocks.get(1);
            final BlockPos pos3 = blocks.get(0);
            InventoryUtil.switchToSlot(InventoryUtil.findHotbarBlock(BlockObsidian.class));
            final BlockPos[] offsets = { pos3.north(), pos3.south(), pos3.east(), pos3.west() };
            final BlockPos[] offsets2 = { pos2.north(), pos2.south(), pos2.east(), pos2.west() };
            if (WorldUtils.empty.contains(WorldUtils.getBlock(pos2.down())) && !this.intersectsWithEntity(pos3)) {
                this.draw = pos2.down();
                BlockUtils.placeBlock(pos2.down(), EnumHand.MAIN_HAND, Surround.rotate.getValue(), Surround.packet.getValue(), true);
                ++placed;
            }
            if (placed > Surround.bps.getValue() - 1.0) {
                return;
            }
            if (WorldUtils.empty.contains(WorldUtils.getBlock(pos3.down())) && !this.intersectsWithEntity(pos3)) {
                this.draw = pos3.down();
                BlockUtils.placeBlock(pos3.down(), EnumHand.MAIN_HAND, Surround.rotate.getValue(), Surround.packet.getValue(), true);
                ++placed;
            }
            for (final BlockPos off : offsets) {
                if (placed <= Surround.bps.getValue() - 1.0) {
                    final Block block = WorldUtils.getBlock(off);
                    final Block block2 = WorldUtils.getBlock(off.down());
                    if (WorldUtils.empty.contains(block2) && !this.intersectsWithEntity(off.down())) {
                        this.draw = off.down();
                        BlockUtils.placeBlock(off.down(), EnumHand.MAIN_HAND, Surround.rotate.getValue(), Surround.packet.getValue(), true);
                        ++placed;
                    }
                    if (placed <= Surround.bps.getValue() - 1.0) {
                        if (WorldUtils.empty.contains(block) && !this.intersectsWithEntity(off)) {
                            BlockUtils.placeBlock(this.draw = off, EnumHand.MAIN_HAND, Surround.rotate.getValue(), Surround.packet.getValue(), true);
                            ++placed;
                        }
                    }
                }
            }
            for (final BlockPos off : offsets2) {
                if (placed <= Surround.bps.getValue() - 1.0) {
                    final Block block = WorldUtils.getBlock(off);
                    final Block block2 = WorldUtils.getBlock(off.down());
                    if (WorldUtils.empty.contains(block2) && !this.intersectsWithEntity(off.down())) {
                        this.draw = off.down();
                        BlockUtils.placeBlock(off.down(), EnumHand.MAIN_HAND, Surround.rotate.getValue(), Surround.packet.getValue(), true);
                        ++placed;
                    }
                    if (placed <= Surround.bps.getValue() - 1.0) {
                        if (WorldUtils.empty.contains(block) && !this.intersectsWithEntity(off)) {
                            BlockUtils.placeBlock(this.draw = off, EnumHand.MAIN_HAND, Surround.rotate.getValue(), Surround.packet.getValue(), true);
                            ++placed;
                        }
                    }
                }
            }
        }
        else {
            Vec3d Center = new Vec3d(PlayerUtils.getPlayerPosFloored().getX() + 0.5, (double)PlayerUtils.getPlayerPosFloored().getY(), PlayerUtils.getPlayerPosFloored().getZ() + 0.5);
            if (Surround.center.getValue().equalsIgnoreCase("Move")) {
                final double l_XDiff = Math.abs(Center.x - Surround.mc.player.posX);
                final double l_ZDiff = Math.abs(Center.z - Surround.mc.player.posZ);
                if (l_XDiff <= 0.1 && l_ZDiff <= 0.1) {
                    Center = new Vec3d(0.0, 0.0, 0.0);
                }
                else {
                    final double l_MotionX = Center.x - Surround.mc.player.posX;
                    final double l_MotionZ = Center.z - Surround.mc.player.posZ;
                    Surround.mc.player.motionX = l_MotionX / 2.0;
                    Surround.mc.player.motionZ = l_MotionZ / 2.0;
                }
            }
            else if (Surround.center.getValue().equalsIgnoreCase("Teleport")) {
                Surround.mc.player.setPosition(Center.x, Center.y, Center.z);
                Surround.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Center.x, Center.y, Center.z, Surround.mc.player.onGround));
            }
            InventoryUtil.switchToSlot(InventoryUtil.findHotbarBlock(BlockObsidian.class));
            for (final BlockPos pos4 : this.pos) {
                if (placed <= Surround.bps.getValue() - 1.0) {
                    if (WorldUtils.empty.contains(WorldUtils.getBlock(playerPos.add((Vec3i)pos4).down())) && !this.intersectsWithEntity(pos4.down())) {
                        this.draw = playerPos.add((Vec3i)pos4).down();
                        ++placed;
                        BlockUtils.placeBlock(playerPos.add((Vec3i)pos4).down(), EnumHand.MAIN_HAND, Surround.rotate.getValue(), Surround.packet.getValue(), true);
                    }
                    if (placed <= Surround.bps.getValue() - 1.0) {
                        if (WorldUtils.empty.contains(WorldUtils.getBlock(playerPos.add((Vec3i)pos4))) && !this.intersectsWithEntity(pos4)) {
                            this.draw = playerPos.add((Vec3i)pos4);
                            ++placed;
                            BlockUtils.placeBlock(playerPos.add((Vec3i)pos4), EnumHand.MAIN_HAND, Surround.rotate.getValue(), Surround.packet.getValue(), true);
                        }
                    }
                }
            }
        }
        if (placed == 0) {
            this.draw = null;
        }
        InventoryUtil.switchToSlot(oldslot);
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : Surround.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (entity instanceof EntityEnderCrystal) {
                continue;
            }
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    static {
        Surround.rotate = new BooleanSetting("Rotate", true);
        Surround.packet = new BooleanSetting("Packet", false);
        Surround.shift = new BooleanSetting("Shift Only", false);
        Surround.center = new ModeSetting("Center Mode", "Move", new String[] { "Move", "Teleport", "None" });
        Surround.bps = new SliderSetting("Blocks Per Tick", 1.0, 8.0, 8.0, true);
    }
}
