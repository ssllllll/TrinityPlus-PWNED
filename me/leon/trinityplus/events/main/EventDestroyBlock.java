//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import net.minecraft.util.math.*;
import me.leon.trinityplus.events.*;

public class EventDestroyBlock extends TrinityEvent
{
    private final BlockPos pos;
    
    public EventDestroyBlock(final EventStage stage, final BlockPos pos) {
        super(stage);
        this.pos = pos;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
}
