//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.managers;

import me.leon.trinityplus.events.main.*;
import me.zero.alpine.fork.listener.*;
import net.minecraft.network.play.server.*;
import me.leon.trinityplus.utils.math.*;
import java.util.function.*;
import me.leon.trinityplus.main.*;

public class TickrateManager implements Listenable
{
    public static float[] TPS;
    public long prevTime;
    public int currentTick;
    @EventHandler
    private final Listener<EventPacketRecieve> onPacketReceive;
    
    public TickrateManager() {
        this.onPacketReceive = new Listener<EventPacketRecieve>(event -> {
            if (event.getPacket() instanceof SPacketTimeUpdate) {
                if (this.prevTime != -1L) {
                    TickrateManager.TPS[this.currentTick % TickrateManager.TPS.length] = (float)MathUtils.clamp(0.0, 20.0, 20.0f / ((System.currentTimeMillis() - this.prevTime) / 1000.0f));
                    ++this.currentTick;
                }
                this.prevTime = System.currentTimeMillis();
            }
            return;
        }, (Predicate<EventPacketRecieve>[])new Predicate[0]);
        this.prevTime = -1L;
        for (int i = 0, len = TickrateManager.TPS.length; i < len; ++i) {
            TickrateManager.TPS[i] = 0.0f;
        }
        Trinity.dispatcher.subscribe(this);
    }
    
    public static float getTPS() {
        int tickCount = 0;
        float tickRate = 0.0f;
        for (final float tick : TickrateManager.TPS) {
            if (tick > 0.0f) {
                tickRate += tick;
                ++tickCount;
            }
        }
        return (float)MathUtils.roundAvoid(MathUtils.clamp(0.0, 20.0, tickRate / tickCount), 2);
    }
    
    static {
        TickrateManager.TPS = new float[20];
    }
}
