//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.utils.rendering.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.utils.misc.*;
import net.minecraft.block.*;
import me.leon.trinityplus.utils.world.*;
import me.leon.trinityplus.utils.entity.*;

public class AutoTrap extends Module
{
    public static BooleanSetting antiStep;
    public static SliderSetting bps;
    public static SliderSetting placeRange;
    public static BooleanSetting antiGlitch;
    public static BooleanSetting packet;
    public static BooleanSetting rotate;
    public static BooleanSetting strict;
    public static BooleanSetting onlyInHole;
    public static BooleanSetting toggle;
    public static BooleanSetting draw;
    public static ModeSetting drawMode;
    public static SliderSetting drawWidth;
    public static SliderSetting length;
    public static ColorSetting fillColor;
    public static ColorSetting outlineColor;
    public static BooleanSetting targeting;
    public static ModeSetting targetingMode;
    public static SliderSetting targetRange;
    public static BooleanSetting players;
    public static BooleanSetting friends;
    public static BooleanSetting neutral;
    public static BooleanSetting passive;
    public static BooleanSetting hostile;
    public static EntityLivingBase target;
    private final ArrayList<BlockPos> placed;
    private final int[][] trapBlocks;
    
    public AutoTrap() {
        super("AutoTrap", "Automatically traps your target", Category.COMBAT);
        this.placed = new ArrayList<BlockPos>();
        this.trapBlocks = new int[][] { { 0, 1, -1 }, { -1, 1, 0 }, { 0, 1, 1 }, { 1, 1, 0 } };
    }
    
    @Override
    public String getHudInfo() {
        return (AutoTrap.target == null) ? null : AutoTrap.target.getName();
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (!AutoTrap.draw.getValue()) {
            return;
        }
        for (final BlockPos pos : this.placed) {
            final AxisAlignedBB bb = new AxisAlignedBB(pos);
            if (AutoTrap.drawMode.getValue().equalsIgnoreCase("Outline") || AutoTrap.drawMode.getValue().equalsIgnoreCase("Both")) {
                Tessellator.drawBBOutline(bb, (float)AutoTrap.drawWidth.getValue(), AutoTrap.outlineColor.getValue());
            }
            if (AutoTrap.drawMode.getValue().equalsIgnoreCase("Fill") || AutoTrap.drawMode.getValue().equalsIgnoreCase("Both")) {
                Tessellator.drawBBFill(bb, AutoTrap.fillColor.getValue());
            }
            if (AutoTrap.drawMode.getValue().equalsIgnoreCase("Claw")) {
                Tessellator.drawBBClaw(bb, (float)AutoTrap.drawWidth.getValue(), (float)AutoTrap.length.getValue(), AutoTrap.outlineColor.getValue());
            }
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        AutoTrap.target = EntityUtils.getTarget(AutoTrap.players.getValue(), AutoTrap.neutral.getValue(), AutoTrap.friends.getValue(), AutoTrap.hostile.getValue(), AutoTrap.passive.getValue(), AutoTrap.targetRange.getValue(), EntityUtils.toMode(AutoTrap.targetingMode.getValue()));
        if (AutoTrap.target == null) {
            if (AutoTrap.toggle.getValue()) {
                this.toggleWithMessage("Target not found!");
            }
            return;
        }
        this.placed.clear();
        this.autoTrap();
    }
    
    private void autoTrap() {
        final HoleUtils.Hole hole = HoleUtils.getHole(EntityUtils.getEntityPosFloored((Entity)AutoTrap.target), -1);
        final boolean inHole = EntityUtils.isInHole(AutoTrap.target);
        if (!inHole && AutoTrap.onlyInHole.getValue() && hole == null) {
            if (AutoTrap.toggle.getValue()) {
                this.toggleWithMessage("Target not in Hole!");
            }
            return;
        }
        final int old = AutoTrap.mc.player.inventory.currentItem;
        if (InventoryUtil.findHotbarBlock(BlockObsidian.class) == -1) {
            MessageUtil.sendClientMessage("No obsidian!", true);
            this.toggle();
            return;
        }
        InventoryUtil.switchToSlot(InventoryUtil.findHotbarBlock(BlockObsidian.class));
        if (hole instanceof HoleUtils.DoubleHole || (!AutoTrap.onlyInHole.getValue() && EntityUtils.getPos(0.0, 0.0, 0.0, (Entity)AutoTrap.target).size() == 2)) {
            BlockPos pos = null;
            BlockPos pos2 = null;
            if (!inHole) {
                final ArrayList<BlockPos> targetPoses = EntityUtils.getPos(0.0, 0.0, 0.0, (Entity)AutoTrap.target);
                if (targetPoses.size() <= 1) {
                    return;
                }
                pos = targetPoses.get(0);
                pos2 = targetPoses.get(1);
            }
            else if (hole instanceof HoleUtils.DoubleHole) {
                pos = ((HoleUtils.DoubleHole)hole).pos1;
                pos2 = ((HoleUtils.DoubleHole)hole).pos;
            }
            if (pos == null) {
                return;
            }
            int placed = 0;
            BlockPos medPos = null;
            boolean need = true;
            final ArrayList<BlockPos> possible = new ArrayList<BlockPos>();
            for (final int[] add : this.trapBlocks) {
                final BlockPos pos3 = pos.add(add[0], add[1], add[2]);
                final BlockPos pos4 = pos.add(add[0], add[1] + 1, add[2]);
                if (!pos4.equals((Object)pos2.add(0, 2, 0))) {
                    possible.add(pos4);
                }
                if (!pos3.equals((Object)pos2.add(0, 1, 0))) {
                    if (!(WorldUtils.getBlock(pos3) instanceof BlockEnderChest)) {
                        if (!(WorldUtils.getBlock(pos3) instanceof BlockObsidian)) {
                            if (placed < AutoTrap.bps.getValue()) {
                                if (pos3.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                                    BlockUtils.placeBlock(pos3, AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                                    this.placed.add(pos3);
                                    ++placed;
                                }
                            }
                        }
                    }
                }
            }
            if (placed >= AutoTrap.bps.getValue()) {
                return;
            }
            for (final int[] add : this.trapBlocks) {
                final BlockPos pos3 = pos2.add(add[0], add[1], add[2]);
                final BlockPos pos4 = pos2.add(add[0], add[1] + 1, add[2]);
                if (!pos4.equals((Object)pos.add(0, 2, 0))) {
                    possible.add(pos4);
                }
                if (!pos3.equals((Object)pos.add(0, 1, 0))) {
                    if (!(WorldUtils.getBlock(pos3) instanceof BlockEnderChest)) {
                        if (!(WorldUtils.getBlock(pos3) instanceof BlockObsidian)) {
                            if (placed < AutoTrap.bps.getValue()) {
                                if (pos3.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                                    BlockUtils.placeBlock(pos3, AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                                    this.placed.add(pos3);
                                    ++placed;
                                }
                            }
                        }
                    }
                }
            }
            if (placed >= AutoTrap.bps.getValue()) {
                return;
            }
            for (final BlockPos medPos2 : possible) {
                if (!WorldUtils.empty.contains(WorldUtils.getBlock(medPos2))) {
                    need = false;
                }
                else {
                    if (EntityUtils.isIntercepted(medPos2)) {
                        continue;
                    }
                    if (medPos == null) {
                        medPos = medPos2;
                    }
                    else {
                        if (medPos2.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) >= medPos.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ)) {
                            continue;
                        }
                        medPos = medPos2;
                    }
                }
            }
            final BlockPos pos5 = pos.add(0, 2, 0);
            final BlockPos pos6 = pos2.add(0, 2, 0);
            if (need && medPos != null && WorldUtils.empty.contains(WorldUtils.getBlock(pos5)) && WorldUtils.empty.contains(WorldUtils.getBlock(pos6)) && medPos.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                BlockUtils.placeBlock(medPos, AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                this.placed.add(medPos);
                ++placed;
            }
            if (placed >= AutoTrap.bps.getValue()) {
                return;
            }
            if (!(WorldUtils.getBlock(pos5) instanceof BlockEnderChest) && !(WorldUtils.getBlock(pos5) instanceof BlockObsidian) && pos5.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                BlockUtils.placeBlock(pos5, AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                this.placed.add(pos5);
                ++placed;
            }
            if (placed >= AutoTrap.bps.getValue()) {
                return;
            }
            if (!(WorldUtils.getBlock(pos6) instanceof BlockEnderChest) && !(WorldUtils.getBlock(pos6) instanceof BlockObsidian) && pos6.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                BlockUtils.placeBlock(pos6, AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                this.placed.add(pos6);
                ++placed;
            }
            if (!AutoTrap.antiStep.getValue()) {
                return;
            }
            if (placed >= AutoTrap.bps.getValue()) {
                return;
            }
            if (!(WorldUtils.getBlock(pos5.add(0, 1, 0)) instanceof BlockEnderChest) && !(WorldUtils.getBlock(pos5.add(0, 1, 0)) instanceof BlockObsidian) && pos5.add(0, 1, 0).distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                BlockUtils.placeBlock(pos5.add(0, 1, 0), AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                this.placed.add(pos5.add(0, 1, 0));
                ++placed;
            }
            if (placed >= AutoTrap.bps.getValue()) {
                return;
            }
            if (!(WorldUtils.getBlock(pos6.add(0, 1, 0)) instanceof BlockEnderChest) && !(WorldUtils.getBlock(pos6.add(0, 1, 0)) instanceof BlockObsidian) && pos6.add(0, 1, 0).distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                BlockUtils.placeBlock(pos6.add(0, 1, 0), AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                this.placed.add(pos6.add(0, 1, 0));
            }
        }
        if (hole instanceof HoleUtils.SingleHole || (!AutoTrap.onlyInHole.getValue() && EntityUtils.getPos(0.0, 0.0, 0.0, (Entity)AutoTrap.target).size() == 1)) {
            final BlockPos pos = EntityUtils.getEntityPosFloored((Entity)AutoTrap.target);
            int placed2 = 0;
            boolean need2 = true;
            BlockPos medPos = null;
            for (final int[] add2 : this.trapBlocks) {
                if (hole == null) {
                    if (!inHole) {
                        final BlockPos pos7 = pos.add(add2[0], 0, add2[2]);
                        if (!(WorldUtils.getBlock(pos7) instanceof BlockEnderChest)) {
                            if (!(WorldUtils.getBlock(pos7) instanceof BlockObsidian)) {
                                if (placed2 < AutoTrap.bps.getValue()) {
                                    if (pos7.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                                        BlockUtils.placeBlock(pos7, AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                                        this.placed.add(pos7);
                                        ++placed2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (final int[] add2 : this.trapBlocks) {
                final BlockPos pos7 = pos.add(add2[0], add2[1], add2[2]);
                if (!(WorldUtils.getBlock(pos7) instanceof BlockEnderChest)) {
                    if (!(WorldUtils.getBlock(pos7) instanceof BlockObsidian)) {
                        if (placed2 < AutoTrap.bps.getValue()) {
                            if (pos7.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                                BlockUtils.placeBlock(pos7, AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                                this.placed.add(pos7);
                                ++placed2;
                            }
                        }
                    }
                }
            }
            for (final int[] add2 : this.trapBlocks) {
                final BlockPos pos7 = pos.add(add2[0], add2[1] + 1, add2[2]);
                if (!WorldUtils.empty.contains(WorldUtils.getBlock(pos7))) {
                    need2 = false;
                }
                else if (!EntityUtils.isIntercepted(pos7)) {
                    if (medPos == null) {
                        medPos = pos7;
                    }
                    else if (pos7.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) < medPos.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ)) {
                        medPos = pos7;
                    }
                }
            }
            if (placed2 >= AutoTrap.bps.getValue()) {
                return;
            }
            if (need2 && medPos != null && WorldUtils.empty.contains(WorldUtils.getBlock(pos.add(0, 2, 0))) && medPos.distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                BlockUtils.placeBlock(medPos, AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                this.placed.add(medPos);
                ++placed2;
            }
            if (placed2 >= AutoTrap.bps.getValue()) {
                return;
            }
            if (WorldUtils.empty.contains(WorldUtils.getBlock(pos.add(0, 2, 0))) && pos.add(0, 2, 0).distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                BlockUtils.placeBlock(pos.add(0, 2, 0), AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                this.placed.add(pos.add(0, 2, 0));
                ++placed2;
            }
            if (placed2 >= AutoTrap.bps.getValue()) {
                return;
            }
            if (WorldUtils.empty.contains(WorldUtils.getBlock(pos.add(0, 3, 0))) && AutoTrap.antiStep.getValue() && pos.add(0, 3, 0).distanceSq(AutoTrap.mc.player.posX, AutoTrap.mc.player.posY + AutoTrap.mc.player.eyeHeight, AutoTrap.mc.player.posZ) <= Math.pow(AutoTrap.placeRange.getValue(), 2.0)) {
                BlockUtils.placeBlock(pos.add(0, 3, 0), AutoTrap.rotate.getValue(), AutoTrap.strict.getValue(), true, AutoTrap.packet.getValue(), true, AutoTrap.antiGlitch.getValue());
                this.placed.add(pos.add(0, 3, 0));
            }
        }
        InventoryUtil.switchToSlot(old);
        if (EntityUtils.isTrapped(AutoTrap.target, AutoTrap.antiStep.getValue()) && AutoTrap.toggle.getValue()) {
            this.toggleWithMessage("Trapped Target! Toggling!");
        }
    }
    
    static {
        AutoTrap.antiStep = new BooleanSetting("AntiStep", true);
        AutoTrap.bps = new SliderSetting("Blocks per Tick", 1.0, 8.0, 8.0, true);
        AutoTrap.placeRange = new SliderSetting("PlaceRage", 0.0, 5.5, 7.0, false);
        AutoTrap.antiGlitch = new BooleanSetting("NoGlitch", true);
        AutoTrap.packet = new BooleanSetting("Packet", false);
        AutoTrap.rotate = new BooleanSetting("Rotate", true);
        AutoTrap.strict = new BooleanSetting("Strict", false);
        AutoTrap.onlyInHole = new BooleanSetting("OnlyInHole", true);
        AutoTrap.toggle = new BooleanSetting("Toggle", true);
        AutoTrap.draw = new BooleanSetting("Rendering", true, true);
        AutoTrap.drawMode = new ModeSetting("Mode", AutoTrap.draw, "Both", new String[] { "Both", "Outline", "Fill", "Claw" });
        AutoTrap.drawWidth = new SliderSetting("Width", AutoTrap.draw, 0.1, 2.0, 5.0, false);
        AutoTrap.length = new SliderSetting("Length", AutoTrap.draw, 0.1, 0.3, 0.5, false);
        AutoTrap.fillColor = new ColorSetting("FillColor", AutoTrap.draw, 255, 0, 255, 120, false);
        AutoTrap.outlineColor = new ColorSetting("OutlineColor", AutoTrap.draw, 255, 0, 255, 120, false);
        AutoTrap.targeting = new BooleanSetting("Targeting", true, false);
        AutoTrap.targetingMode = new ModeSetting("Mode", AutoTrap.targeting, "Closest", new String[] { "Closest", "Lowest Health", "Highest Health" });
        AutoTrap.targetRange = new SliderSetting("Range", AutoTrap.targeting, 0.5, 5.0, 12.0, false);
        AutoTrap.players = new BooleanSetting("Players", AutoTrap.targeting, true);
        AutoTrap.friends = new BooleanSetting("Friends", AutoTrap.targeting, false);
        AutoTrap.neutral = new BooleanSetting("Neutral", AutoTrap.targeting, false);
        AutoTrap.passive = new BooleanSetting("Passive", AutoTrap.targeting, false);
        AutoTrap.hostile = new BooleanSetting("Hostile", AutoTrap.targeting, false);
    }
}
