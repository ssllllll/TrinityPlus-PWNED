//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zero.alpine.fork.listener;

import java.util.function.*;

public class MethodRefListener<T> extends Listener<T>
{
    private Class<T> target;
    
    @SafeVarargs
    public MethodRefListener(final Class<T> target, final EventHook<T> hook, final Predicate<T>... filters) {
        super((EventHook)hook, (Predicate[])filters);
        this.target = target;
    }
    
    @SafeVarargs
    public MethodRefListener(final Class<T> target, final EventHook<T> hook, final int priority, final Predicate<T>... filters) {
        super((EventHook)hook, priority, (Predicate[])filters);
        this.target = target;
    }
    
    public Class<T> getTarget() {
        return this.target;
    }
}
