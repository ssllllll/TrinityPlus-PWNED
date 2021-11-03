//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.entity.player.*;
import me.leon.trinityplus.utils.math.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.utils.world.*;
import me.leon.trinityplus.utils.entity.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class HoleFill extends Module
{
    public static BooleanSetting doubleHoles;
    public static SliderSetting bps;
    public static SliderSetting range;
    public static BooleanSetting toggle;
    public static BooleanSetting packet;
    public static BooleanSetting antiGlitch;
    public static BooleanSetting rotate;
    public static BooleanSetting smart;
    public static SliderSetting distance;
    public static BooleanSetting predict;
    public static SliderSetting ticks;
    
    public HoleFill() {
        super("HoleFill", "Fills holes near you", Category.COMBAT);
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        final int oldSlot = HoleFill.mc.player.inventory.currentItem;
        int placed = 0;
        final ArrayList<HoleUtils.Hole> holeList = HoleUtils.holes((float)HoleFill.range.getValue(), -1);
        if (HoleFill.smart.getValue()) {
            final EntityPlayer target = (EntityPlayer)EntityUtils.getTarget(true, false, false, false, false, 10.0, EntityUtils.toMode("Closest"));
            if (target == null) {
                return;
            }
            final Vec3d vec = HoleFill.predict.getValue() ? target.getPositionVector() : MathUtils.extrapolatePlayerPosition((Entity)target, (int)HoleFill.ticks.getValue());
            final Vec3d vec3d;
            final Entity entity;
            HoleUtils.Hole hole;
            final ArrayList list;
            holeList.removeIf(e -> {
                if (e instanceof HoleUtils.SingleHole) {
                    return vec3d.squareDistanceTo(new Vec3d((Vec3i)((HoleUtils.SingleHole)e).pos).add(0.5, 0.5, 0.5)) >= HoleFill.distance.getValue() * HoleFill.distance.getValue();
                }
                else {
                    hole = HoleUtils.getHole(EntityUtils.getEntityPosFloored(entity), 1);
                    if (hole instanceof HoleUtils.DoubleHole && list.contains(hole)) {
                        return true;
                    }
                    else if (vec3d.squareDistanceTo(new Vec3d((Vec3i)e.pos).add(0.5, 0.5, 0.5)) >= HoleFill.distance.getValue() * HoleFill.distance.getValue()) {
                        return true;
                    }
                    else {
                        return vec3d.squareDistanceTo(new Vec3d((Vec3i)e.pos1).add(0.5, 0.5, 0.5)) >= HoleFill.distance.getValue() * HoleFill.distance.getValue();
                    }
                }
            });
        }
        if (holeList.isEmpty()) {
            return;
        }
        if (!this.matCheck()) {
            return;
        }
        for (final HoleUtils.Hole hole2 : holeList) {
            if (placed >= HoleFill.bps.getValue()) {
                continue;
            }
            if (hole2 instanceof HoleUtils.SingleHole && !EntityUtils.isIntercepted(((HoleUtils.SingleHole)hole2).pos) && WorldUtils.empty.contains(WorldUtils.getBlock(((HoleUtils.SingleHole)hole2).pos))) {
                BlockUtils.placeBlock(((HoleUtils.SingleHole)hole2).pos, HoleFill.rotate.getValue(), false, true, HoleFill.packet.getValue(), true, HoleFill.antiGlitch.getValue());
                ++placed;
            }
            if (placed >= HoleFill.bps.getValue()) {
                continue;
            }
            if (!(hole2 instanceof HoleUtils.DoubleHole) || !HoleFill.doubleHoles.getValue()) {
                continue;
            }
            if (placed >= HoleFill.bps.getValue()) {
                continue;
            }
            final HoleUtils.DoubleHole doubleH = (HoleUtils.DoubleHole)hole2;
            if (this.getDist(doubleH.pos) && !EntityUtils.isIntercepted(doubleH.pos) && WorldUtils.empty.contains(WorldUtils.getBlock(doubleH.pos))) {
                BlockUtils.placeBlock(doubleH.pos, HoleFill.rotate.getValue(), false, true, HoleFill.packet.getValue(), true, HoleFill.antiGlitch.getValue());
                ++placed;
            }
            if (placed >= HoleFill.bps.getValue()) {
                continue;
            }
            if (!this.getDist(doubleH.pos1) || EntityUtils.isIntercepted(doubleH.pos1) || !WorldUtils.empty.contains(WorldUtils.getBlock(doubleH.pos1))) {
                continue;
            }
            BlockUtils.placeBlock(doubleH.pos1, HoleFill.rotate.getValue(), false, true, HoleFill.packet.getValue(), true, HoleFill.antiGlitch.getValue());
            ++placed;
        }
        InventoryUtil.switchToSlot(oldSlot);
        if (placed == 0 && holeList.isEmpty() && HoleFill.toggle.getValue()) {
            this.toggleWithMessage("Finished Holefilling, toggling!");
        }
    }
    
    private boolean getDist(final BlockPos pos) {
        return !this.nullCheck() && pos != null && pos.add(0.5, 0.5, 0.5).distanceSq(HoleFill.mc.player.posX, HoleFill.mc.player.posY + HoleFill.mc.player.eyeHeight, HoleFill.mc.player.posZ) < Math.pow(HoleFill.range.getValue(), 2.0);
    }
    
    private boolean matCheck() {
        if (!InventoryUtil.switchTo(Item.getItemFromBlock(Blocks.OBSIDIAN)) && !InventoryUtil.switchTo(Item.getItemFromBlock(Blocks.ENDER_CHEST)) && HoleFill.toggle.getValue()) {
            this.toggleWithMessage("Cannot find materials!");
        }
        return this.isEnabled();
    }
    
    static {
        HoleFill.doubleHoles = new BooleanSetting("DoubleHoles", false);
        HoleFill.bps = new SliderSetting("Blocks Per Tick", 1.0, 3.0, 8.0, true);
        HoleFill.range = new SliderSetting("Range", 1.0, 3.0, 8.0, true);
        HoleFill.toggle = new BooleanSetting("Toggle", false);
        HoleFill.packet = new BooleanSetting("Packet", false);
        HoleFill.antiGlitch = new BooleanSetting("AntiGlitch", false);
        HoleFill.rotate = new BooleanSetting("Rotate", false);
        HoleFill.smart = new BooleanSetting("Smart", true, true);
        HoleFill.distance = new SliderSetting("Distance", HoleFill.smart, 0.0, 3.0, 8.0, false);
        HoleFill.predict = new BooleanSetting("Predict", HoleFill.smart, true);
        HoleFill.ticks = new SliderSetting("Ticks", HoleFill.smart, 1.0, 3.0, 5.0, true);
    }
}
