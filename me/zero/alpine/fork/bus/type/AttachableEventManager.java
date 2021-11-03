//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zero.alpine.fork.bus.type;

import me.zero.alpine.fork.bus.*;
import java.util.*;
import me.zero.alpine.fork.listener.*;

public class AttachableEventManager extends EventManager implements AttachableEventBus
{
    private final List<EventBus> attached;
    
    public AttachableEventManager() {
        this.attached = new ArrayList<EventBus>();
    }
    
    public void subscribe(final Listenable listenable) {
        super.subscribe(listenable);
        if (!this.attached.isEmpty()) {
            this.attached.forEach(bus -> bus.subscribe(listenable));
        }
    }
    
    public void subscribe(final Listener listener) {
        super.subscribe(listener);
        if (!this.attached.isEmpty()) {
            this.attached.forEach(bus -> bus.subscribe(listener));
        }
    }
    
    public void unsubscribe(final Listenable listenable) {
        super.unsubscribe(listenable);
        if (!this.attached.isEmpty()) {
            this.attached.forEach(bus -> bus.unsubscribe(listenable));
        }
    }
    
    public void unsubscribe(final Listener listener) {
        super.unsubscribe(listener);
        if (!this.attached.isEmpty()) {
            this.attached.forEach(bus -> bus.unsubscribe(listener));
        }
    }
    
    public void post(final Object event) {
        super.post(event);
        if (!this.attached.isEmpty()) {
            this.attached.forEach(bus -> bus.post(event));
        }
    }
    
    public void attach(final EventBus bus) {
        if (!this.attached.contains(bus)) {
            this.attached.add(bus);
        }
    }
    
    public void detach(final EventBus bus) {
        this.attached.remove(bus);
    }
}
