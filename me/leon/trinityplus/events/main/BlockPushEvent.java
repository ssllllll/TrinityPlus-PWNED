//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import me.leon.trinityplus.events.*;

public class BlockPushEvent extends TrinityEvent
{
    public double var1;
    public double var2;
    public double var3;
    
    public BlockPushEvent(final double var1, final double var2, final double var3, final EventStage stage) {
        super(stage);
        this.var1 = var1;
        this.var2 = var2;
        this.var3 = var3;
    }
}
