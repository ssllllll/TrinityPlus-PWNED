//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.player;

import me.leon.trinityplus.events.main.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import java.util.function.*;

public class NoPush extends Module
{
    @EventHandler
    public Listener<BlockPushEvent> onBurrowPush;
    
    public NoPush() {
        super("NoPush", "cancels any block push", Category.PLAYER);
        this.onBurrowPush = new Listener<BlockPushEvent>(event -> event.cancel(), (Predicate<BlockPushEvent>[])new Predicate[0]);
    }
}
