//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import net.minecraft.entity.*;
import me.leon.trinityplus.events.*;

public class EventRenderEntityModelAll extends TrinityEvent
{
    private final Entity entity;
    
    public EventRenderEntityModelAll(final EventStage stage, final Entity entity) {
        super(stage);
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
