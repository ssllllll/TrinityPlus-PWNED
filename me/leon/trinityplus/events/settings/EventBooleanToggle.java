//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.settings;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.*;

public class EventBooleanToggle extends TrinityEvent
{
    private final BooleanSetting set;
    private boolean mode;
    
    public EventBooleanToggle(final EventStage stage, final BooleanSetting set, final boolean mode) {
        super(stage);
        this.set = set;
        this.mode = mode;
    }
    
    public boolean isMode() {
        return this.mode;
    }
    
    public void setMode(final boolean mode) {
        this.mode = mode;
    }
    
    public BooleanSetting getSet() {
        return this.set;
    }
}
