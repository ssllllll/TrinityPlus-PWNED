//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import net.minecraft.util.*;
import me.leon.trinityplus.events.*;

public class TransformFirstPersonEvent extends TrinityEvent
{
    private final EnumHandSide enumHandSide;
    
    public TransformFirstPersonEvent(final EventStage stage, final EnumHandSide enumHandSide) {
        super(stage);
        this.enumHandSide = enumHandSide;
    }
    
    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }
}
