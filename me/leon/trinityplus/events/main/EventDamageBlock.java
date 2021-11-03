//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import net.minecraft.util.math.*;
import net.minecraft.util.*;
import me.leon.trinityplus.events.*;

public class EventDamageBlock extends TrinityEvent
{
    private final BlockPos pos;
    private final EnumFacing direction;
    
    public EventDamageBlock(final EventStage stage, final BlockPos pos, final EnumFacing dir) {
        super(stage);
        this.pos = pos;
        this.direction = dir;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public EnumFacing getDirection() {
        return this.direction;
    }
}
