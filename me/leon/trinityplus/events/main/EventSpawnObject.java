//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import net.minecraft.network.play.server.*;
import me.leon.trinityplus.events.*;

public class EventSpawnObject extends TrinityEvent
{
    private final SPacketSpawnObject packet;
    
    public EventSpawnObject(final EventStage stage, final SPacketSpawnObject packet) {
        super(stage);
        this.packet = packet;
    }
    
    public SPacketSpawnObject getPacket() {
        return this.packet;
    }
}
