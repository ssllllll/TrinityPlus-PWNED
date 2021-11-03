//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import net.minecraft.network.*;
import me.leon.trinityplus.events.*;

public class EventPacketSend extends TrinityEvent
{
    private final Packet<?> packet;
    
    public EventPacketSend(final EventStage stage, final Packet<?> packet) {
        super(stage);
        this.packet = packet;
    }
    
    public Packet<?> getPacket() {
        return this.packet;
    }
}
