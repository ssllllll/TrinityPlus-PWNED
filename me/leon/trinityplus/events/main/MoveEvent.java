//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import net.minecraft.entity.*;
import me.leon.trinityplus.events.*;

public class MoveEvent extends TrinityEvent
{
    public double x;
    public double y;
    public double z;
    public MoverType type;
    
    public MoveEvent(final MoverType type, final double x, final double y, final double z, final EventStage stage) {
        super(stage);
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
