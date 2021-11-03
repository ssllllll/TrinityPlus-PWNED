//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.world;

import me.leon.trinityplus.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import me.leon.trinityplus.utils.entity.*;
import java.util.*;

public class HoleUtils implements Util
{
    public static Hole getHole(final BlockPos pos, final int height) {
        boolean a = false;
        for (int b = 0; b < height; ++b) {
            if (!WorldUtils.empty.contains(WorldUtils.getBlock(pos.add(0, b + 1, 0)))) {
                a = true;
            }
        }
        if (a) {
            return null;
        }
        if (WorldUtils.empty.contains(WorldUtils.getBlock(pos)) && !WorldUtils.empty.contains(WorldUtils.getBlock(pos.down()))) {
            if ((WorldUtils.getBlock(pos.north()) instanceof BlockObsidian || WorldUtils.getBlock(pos.north()) == Blocks.BEDROCK) && (WorldUtils.getBlock(pos.south()) instanceof BlockObsidian || WorldUtils.getBlock(pos.south()) == Blocks.BEDROCK) && (WorldUtils.getBlock(pos.east()) instanceof BlockObsidian || WorldUtils.getBlock(pos.east()) == Blocks.BEDROCK) && (WorldUtils.getBlock(pos.west()) instanceof BlockObsidian || WorldUtils.getBlock(pos.west()) == Blocks.BEDROCK)) {
                if (WorldUtils.getBlock(pos.north()) instanceof BlockObsidian || WorldUtils.getBlock(pos.east()) instanceof BlockObsidian || WorldUtils.getBlock(pos.south()) instanceof BlockObsidian || WorldUtils.getBlock(pos.west()) instanceof BlockObsidian) {
                    return new SingleHole(pos, material.OBSIDIAN);
                }
                return new SingleHole(pos, material.BEDROCK);
            }
            else {
                final BlockPos[] dir = { pos.west(), pos.north(), pos.east(), pos.south() };
                BlockPos pos2 = null;
                for (final BlockPos dir2 : dir) {
                    if (WorldUtils.empty.contains(WorldUtils.getBlock(dir2))) {
                        pos2 = dir2;
                        break;
                    }
                }
                if (pos2 == null || WorldUtils.empty.contains(WorldUtils.getBlock(pos2.down()))) {
                    return null;
                }
                final BlockPos[] dir3 = { pos2.west(), pos2.north(), pos2.east(), pos2.south() };
                int checked = 0;
                boolean obi = false;
                EnumFacing facing = null;
                for (final BlockPos pos3 : dir3) {
                    if (pos3 != pos) {
                        if (WorldUtils.getBlock(pos3) instanceof BlockObsidian) {
                            obi = true;
                            ++checked;
                        }
                        if (WorldUtils.getBlock(pos3) == Blocks.BEDROCK) {
                            ++checked;
                        }
                    }
                }
                for (final BlockPos pos3 : dir) {
                    if (pos3 != pos2) {
                        if (WorldUtils.getBlock(pos3) instanceof BlockObsidian) {
                            obi = true;
                            ++checked;
                        }
                        if (WorldUtils.getBlock(pos3) == Blocks.BEDROCK) {
                            ++checked;
                        }
                    }
                }
                for (final EnumFacing facing2 : EnumFacing.values()) {
                    if (pos.add(facing2.getXOffset(), facing2.getYOffset(), facing2.getZOffset()).equals((Object)pos2)) {
                        facing = facing2;
                    }
                }
                if (checked >= 6) {
                    return new DoubleHole(pos, pos2, obi ? material.OBSIDIAN : material.BEDROCK, facing);
                }
            }
        }
        return null;
    }
    
    public static ArrayList<Hole> holes(final float r, final int height) {
        final ArrayList<Hole> holes = new ArrayList<Hole>();
        for (final BlockPos pos : WorldUtils.getSphere(PlayerUtils.getPlayerPosFloored(), r, (int)r, false, true, 0)) {
            final Hole hole = getHole(pos, height);
            if (hole instanceof DoubleHole) {
                boolean a = false;
                for (final Hole hole2 : holes) {
                    if (hole2 instanceof DoubleHole && ((DoubleHole)hole2).contains((DoubleHole)hole)) {
                        a = true;
                        break;
                    }
                }
                if (a) {
                    continue;
                }
            }
            if (hole != null) {
                holes.add(hole);
            }
        }
        return holes;
    }
    
    public enum type
    {
        DOUBLE, 
        SINGLE;
    }
    
    public enum material
    {
        BEDROCK, 
        OBSIDIAN;
    }
    
    public static class Hole
    {
        public type type;
        public material mat;
        
        public Hole(final type type, final material mat) {
            this.type = type;
            this.mat = mat;
        }
    }
    
    public static final class SingleHole extends Hole
    {
        public BlockPos pos;
        
        public SingleHole(final BlockPos pos, final material mat) {
            super(type.SINGLE, mat);
            this.pos = pos;
        }
    }
    
    public static final class DoubleHole extends Hole
    {
        public BlockPos pos;
        public BlockPos pos1;
        public EnumFacing dir;
        
        public DoubleHole(final BlockPos pos, final BlockPos pos1, final material mat, final EnumFacing dir) {
            super(type.DOUBLE, mat);
            this.pos = pos;
            this.pos1 = pos1;
            this.dir = dir;
        }
        
        public boolean contains(final BlockPos pos) {
            return this.pos == pos || this.pos1 == pos;
        }
        
        public boolean contains(final DoubleHole pos) {
            return pos.pos.equals((Object)this.pos) || pos.pos.equals((Object)this.pos1) || pos.pos1.equals((Object)this.pos) || pos.pos1.equals((Object)this.pos1);
        }
        
        public boolean equals(final DoubleHole pos) {
            return (pos.pos1.equals((Object)this.pos) || pos.pos1.equals((Object)this.pos1)) && (pos.pos.equals((Object)this.pos) || pos.pos.equals((Object)this.pos1));
        }
    }
}
