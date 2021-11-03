//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.render;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraftforge.client.event.*;
import me.leon.trinityplus.utils.entity.*;
import me.leon.trinityplus.utils.world.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.utils.rendering.*;
import java.awt.*;

public class HoleESP extends Module
{
    public static ModeSetting mode;
    public static ModeSetting outline;
    public static BooleanSetting own;
    public static ModeSetting ownMode;
    public static ModeSetting ownOutline;
    public static SliderSetting ownCheckHeight;
    public static SliderSetting ownHeight;
    public static SliderSetting ownClawHeight;
    public static SliderSetting ownWidth;
    public static ColorSetting ownBedrockColor;
    public static ColorSetting ownObsidianColor;
    public static SliderSetting ownOffset;
    public static SliderSetting range;
    public static SliderSetting checkHeight;
    public static SliderSetting height;
    public static SliderSetting clawHeight;
    public static SliderSetting width;
    public static BooleanSetting doubleHoles;
    public static ColorSetting bedrockColor;
    public static ColorSetting obsidianColor;
    public static SliderSetting offset;
    
    public HoleESP() {
        super("HoleESP", "Highlights holes", Category.RENDER);
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        final List<BlockPos> poss = WorldUtils.getSphere(PlayerUtils.getPlayerPosFloored(), (float)HoleESP.range.getValue(), (int)HoleESP.range.getValue(), false, true, 0);
        final List<HoleUtils.Hole> cachedHoles = new ArrayList<HoleUtils.Hole>();
        for (final BlockPos pos : poss) {
            final HoleUtils.Hole hole = HoleUtils.getHole(pos, (int)HoleESP.checkHeight.getValue());
            if (hole != null) {
                if (hole instanceof HoleUtils.DoubleHole) {
                    if (!HoleESP.doubleHoles.getValue()) {
                        continue;
                    }
                    boolean a = false;
                    for (final HoleUtils.Hole hole2 : cachedHoles) {
                        if (hole2 instanceof HoleUtils.DoubleHole && ((HoleUtils.DoubleHole)hole2).contains((HoleUtils.DoubleHole)hole)) {
                            a = true;
                            break;
                        }
                    }
                    if (a) {
                        continue;
                    }
                    cachedHoles.add(hole);
                }
                else {
                    cachedHoles.add(hole);
                }
            }
        }
        for (final HoleUtils.Hole hole3 : cachedHoles) {
            if (hole3 instanceof HoleUtils.DoubleHole) {
                final HoleUtils.DoubleHole pos2 = (HoleUtils.DoubleHole)hole3;
                if (HoleESP.mc.player.getDistanceSqToCenter(pos2.pos) > Math.pow(HoleESP.range.getValue(), 2.0)) {
                    continue;
                }
                if (HoleESP.mc.player.getDistanceSqToCenter(pos2.pos1) > Math.pow(HoleESP.range.getValue(), 2.0)) {
                    continue;
                }
                this.drawHoleESP(hole3);
            }
            if (hole3 instanceof HoleUtils.SingleHole) {
                final HoleUtils.SingleHole pos3 = (HoleUtils.SingleHole)hole3;
                if (HoleESP.mc.player.getDistanceSqToCenter(pos3.pos) > Math.pow(HoleESP.range.getValue(), 2.0)) {
                    continue;
                }
                this.drawHoleESP(hole3);
            }
        }
        this.drawOwnHoleESP();
    }
    
    private void drawHoleESP(final HoleUtils.Hole hole) {
        final HoleUtils.Hole hole2 = HoleUtils.getHole(PlayerUtils.getPlayerPosFloored(), (int)HoleESP.checkHeight.getValue());
        if (HoleESP.own.getValue() && hole2 != null) {
            if (hole2 instanceof HoleUtils.DoubleHole && hole instanceof HoleUtils.DoubleHole) {
                final HoleUtils.DoubleHole pos = (HoleUtils.DoubleHole)hole2;
                final HoleUtils.DoubleHole pos2 = (HoleUtils.DoubleHole)hole;
                if (pos.contains(pos2)) {
                    return;
                }
            }
            if (hole2 instanceof HoleUtils.SingleHole && hole instanceof HoleUtils.SingleHole) {
                final HoleUtils.SingleHole pos3 = (HoleUtils.SingleHole)hole2;
                final HoleUtils.SingleHole pos4 = (HoleUtils.SingleHole)hole;
                if (pos3.pos.equals((Object)pos4.pos)) {
                    return;
                }
            }
        }
        AxisAlignedBB bb;
        if (hole instanceof HoleUtils.DoubleHole) {
            final HoleUtils.DoubleHole pos5 = (HoleUtils.DoubleHole)hole;
            bb = new AxisAlignedBB(pos5.pos);
            bb = bb.expand((double)pos5.dir.getXOffset(), (double)pos5.dir.getYOffset(), (double)pos5.dir.getZOffset());
        }
        else {
            final HoleUtils.SingleHole pos6 = (HoleUtils.SingleHole)hole;
            bb = new AxisAlignedBB(pos6.pos);
        }
        final String value = HoleESP.mode.getValue();
        switch (value) {
            case "Glow": {
                Tessellator.drawBBSlab(bb.offset(0.0, HoleESP.offset.getValue(), 0.0), (float)HoleESP.height.getValue(), this.getColor(hole, false));
                break;
            }
            case "Fill": {
                Tessellator.drawBBFill(bb, this.getColor(hole, false));
                break;
            }
        }
        final String value2 = HoleESP.outline.getValue();
        switch (value2) {
            case "Claw": {
                Tessellator.drawBBClaw(bb.contract(0.0, 1.0, 0.0), (float)HoleESP.width.getValue(), (float)HoleESP.clawHeight.getValue(), this.getColor(hole, false));
                break;
            }
            case "Outline": {
                Tessellator.drawBBOutline(bb.contract(0.0, 1.0, 0.0), (float)HoleESP.width.getValue(), this.getColor(hole, false));
                break;
            }
        }
    }
    
    private void drawOwnHoleESP() {
        if (!HoleESP.own.getValue()) {
            return;
        }
        final HoleUtils.Hole hole = HoleUtils.getHole(PlayerUtils.getPlayerPosFloored(), (int)HoleESP.ownCheckHeight.getValue());
        if (hole != null) {
            AxisAlignedBB bb;
            if (hole instanceof HoleUtils.DoubleHole) {
                if (!HoleESP.doubleHoles.getValue()) {
                    return;
                }
                final HoleUtils.DoubleHole pos = (HoleUtils.DoubleHole)hole;
                bb = new AxisAlignedBB(pos.pos);
                bb = bb.expand((double)pos.dir.getXOffset(), (double)pos.dir.getYOffset(), (double)pos.dir.getZOffset());
            }
            else {
                final HoleUtils.SingleHole pos2 = (HoleUtils.SingleHole)hole;
                bb = new AxisAlignedBB(pos2.pos);
            }
            final String value = HoleESP.ownMode.getValue();
            switch (value) {
                case "Glow": {
                    Tessellator.drawBBSlab(bb.offset(0.0, HoleESP.offset.getValue(), 0.0), (float)HoleESP.ownHeight.getValue(), this.getColor(hole, true));
                    break;
                }
                case "Fill": {
                    Tessellator.drawBBFill(bb, this.getColor(hole, true));
                    break;
                }
            }
            final String value2 = HoleESP.ownOutline.getValue();
            switch (value2) {
                case "Claw": {
                    Tessellator.drawBBClaw(bb.contract(0.0, 1.0, 0.0), (float)HoleESP.ownWidth.getValue(), (float)HoleESP.ownClawHeight.getValue(), this.getColor(hole, true));
                    break;
                }
                case "Outline": {
                    Tessellator.drawBBOutline(bb.contract(0.0, 1.0, 0.0), (float)HoleESP.ownWidth.getValue(), this.getColor(hole, true));
                    break;
                }
            }
        }
    }
    
    private Color getColor(final HoleUtils.Hole hole, final boolean own) {
        if (own) {
            return (hole.mat == HoleUtils.material.BEDROCK) ? HoleESP.ownBedrockColor.getValue() : HoleESP.ownObsidianColor.getValue();
        }
        return (hole.mat == HoleUtils.material.BEDROCK) ? HoleESP.bedrockColor.getValue() : HoleESP.obsidianColor.getValue();
    }
    
    static {
        HoleESP.mode = new ModeSetting("Mode", "Glow", new String[] { "Fill", "Glow", "None" });
        HoleESP.outline = new ModeSetting("Outline", "Claw", new String[] { "Outline", "Claw", "None" });
        HoleESP.own = new BooleanSetting("Own", true, true);
        HoleESP.ownMode = new ModeSetting("Mode", HoleESP.own, "Glow", new String[] { "Glow", "Fill", "None" });
        HoleESP.ownOutline = new ModeSetting("Outline", HoleESP.own, "Outline", new String[] { "Outline", "Claw", "None" });
        HoleESP.ownCheckHeight = new SliderSetting("CheckHeight", HoleESP.own, 0.0, 1.5, 5.0, false);
        HoleESP.ownHeight = new SliderSetting("Height", HoleESP.own, 0.0, 0.0, 5.0, false);
        HoleESP.ownClawHeight = new SliderSetting("ClawHeight", HoleESP.own, 0.0, 0.3, 1.0, false);
        HoleESP.ownWidth = new SliderSetting("Width", HoleESP.own, 0.1, 2.0, 5.0, false);
        HoleESP.ownBedrockColor = new ColorSetting("Bedrock Color", HoleESP.own, 0, 255, 0, 150, false);
        HoleESP.ownObsidianColor = new ColorSetting("Obsidian Color", HoleESP.own, 0, 255, 0, 150, false);
        HoleESP.ownOffset = new SliderSetting("Offset", HoleESP.own, -3.0, 0.0, 3.0, true);
        HoleESP.range = new SliderSetting("Range", 0.0, 8.0, 12.0, false);
        HoleESP.checkHeight = new SliderSetting("CheckHeight", 0.0, 1.5, 5.0, false);
        HoleESP.height = new SliderSetting("Height", 0.0, 0.0, 5.0, false);
        HoleESP.clawHeight = new SliderSetting("ClawHeight", 0.0, 0.3, 1.0, false);
        HoleESP.width = new SliderSetting("Width", 0.1, 2.0, 5.0, false);
        HoleESP.doubleHoles = new BooleanSetting("Doubles", true);
        HoleESP.bedrockColor = new ColorSetting("Bedrock Color", 0, 255, 255, 150, false);
        HoleESP.obsidianColor = new ColorSetting("Obsidian Color", 255, 255, 0, 150, false);
        HoleESP.offset = new SliderSetting("Offset", -3.0, 0.0, 3.0, true);
    }
}
