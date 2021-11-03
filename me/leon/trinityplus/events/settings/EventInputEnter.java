//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.settings;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.*;

public class EventInputEnter extends TrinityEvent
{
    private final Setting input;
    
    public EventInputEnter(final EventStage stage, final Setting input) {
        super(stage);
        this.input = input;
    }
    
    public Setting getSetting() {
        return this.input;
    }
}
