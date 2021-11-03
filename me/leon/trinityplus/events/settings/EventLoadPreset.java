//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.settings;

import me.leon.trinityplus.config.rewrite.*;
import me.leon.trinityplus.events.*;

public class EventLoadPreset extends TrinityEvent
{
    private final Preset obj;
    
    public EventLoadPreset(final EventStage stage, final Preset obj) {
        super(stage);
        this.obj = obj;
    }
    
    public Preset getObj() {
        return this.obj;
    }
}
