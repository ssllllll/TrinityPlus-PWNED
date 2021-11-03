//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import net.minecraft.entity.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.notification.*;
import me.leon.trinityplus.managers.*;
import java.util.function.*;

public class EventTotemPop extends TrinityEvent
{
    private final EntityLivingBase entity;
    private final int times;
    @EventHandler
    private final Listener<EventTotemPop> packetRecieveListener;
    
    public EventTotemPop(final EventStage stage, final EntityLivingBase entity, final int times) {
        super(stage);
        final Notification notification;
        this.packetRecieveListener = new Listener<EventTotemPop>(event -> {
            new Notification("Totem Pop", event.entity.getName() + " Popped a totem", NotifType.INFO);
            NotificationManager.add(notification);
            return;
        }, (Predicate<EventTotemPop>[])new Predicate[0]);
        this.entity = entity;
        this.times = times;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public int getTimes() {
        return this.times;
    }
}
