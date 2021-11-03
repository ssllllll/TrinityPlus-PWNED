//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import me.leon.trinityplus.events.*;

public class LocationSpoofEvent extends TrinityEvent
{
    public final SpoofEvent location;
    
    public LocationSpoofEvent(final EventStage stage, final SpoofEvent location) {
        super(stage);
        this.location = location;
    }
}
