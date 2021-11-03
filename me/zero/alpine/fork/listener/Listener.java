//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zero.alpine.fork.listener;

import java.util.function.*;
import net.jodah.typetools.*;

public class Listener<T> implements EventHook<T>
{
    private final Class<T> target;
    private final EventHook<T> hook;
    private final Predicate<T>[] filters;
    private final int priority;
    
    @SafeVarargs
    public Listener(final EventHook<T> hook, final Predicate<T>... filters) {
        this(hook, 0, (Predicate[])filters);
    }
    
    @SafeVarargs
    public Listener(final EventHook<T> hook, final int priority, final Predicate<T>... filters) {
        this.hook = hook;
        this.priority = priority;
        this.target = (Class<T>)TypeResolver.resolveRawArgument((Class)EventHook.class, (Class)hook.getClass());
        this.filters = filters;
    }
    
    public Class<T> getTarget() {
        return this.target;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public void invoke(final T event) {
        if (this.filters.length > 0) {
            for (final Predicate<T> filter : this.filters) {
                if (!filter.test(event)) {
                    return;
                }
            }
        }
        this.hook.invoke((Object)event);
    }
}
