//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events;

import me.zero.alpine.fork.event.type.*;

public class TrinityEvent implements ICancellable
{
    private final EventStage stage;
    private boolean canceled;
    
    public TrinityEvent(final EventStage stage) {
        this.stage = stage;
    }
    
    public EventStage getStage() {
        return this.stage;
    }
    
    @Override
    public void cancel() {
        this.canceled = true;
    }
    
    public void resume() {
        this.canceled = false;
    }
    
    @Override
    public boolean isCancelled() {
        return this.canceled;
    }
}
