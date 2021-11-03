//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.movement;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.main.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.network.play.server.*;
import java.util.function.*;

public class Velocity extends Module
{
    public static SliderSetting x;
    public static SliderSetting y;
    public static SliderSetting z;
    public static BooleanSetting reverse;
    @EventHandler
    private final Listener<EventPacketRecieve> packetRecieveListener;
    
    public Velocity() {
        super("Velocity", "Anti-KB", Category.MOVEMENT);
        SPacketExplosion packet;
        SPacketEntityVelocity packet2;
        this.packetRecieveListener = new Listener<EventPacketRecieve>(event -> {
            if (!this.nullCheck()) {
                if (event.getPacket() instanceof SPacketExplosion) {
                    if (Velocity.x.getValue() == 0.0 && Velocity.z.getValue() == 0.0 && Velocity.y.getValue() == 0.0) {
                        event.cancel();
                        return;
                    }
                    else {
                        packet = (SPacketExplosion)event.getPacket();
                        packet.motionX = (float)(Velocity.reverse.getValue() ? (-Velocity.x.getValue() / 100.0 * packet.motionX) : (Velocity.x.getValue() / 100.0 * packet.motionX));
                        packet.motionY = (float)(Velocity.reverse.getValue() ? (-Velocity.y.getValue() / 100.0 * packet.motionY) : (Velocity.y.getValue() / 100.0 * packet.motionY));
                        packet.motionZ = (float)(Velocity.reverse.getValue() ? (-Velocity.z.getValue() / 100.0 * packet.motionZ) : (Velocity.z.getValue() / 100.0 * packet.motionZ));
                        Velocity.mc.player.updateEntityActionState();
                    }
                }
                if (event.getPacket() instanceof SPacketEntityVelocity) {
                    if (Velocity.x.getValue() == 0.0 && Velocity.z.getValue() == 0.0 && Velocity.y.getValue() == 0.0) {
                        event.cancel();
                    }
                    else {
                        packet2 = (SPacketEntityVelocity)event.getPacket();
                        if (packet2.entityID == Velocity.mc.player.entityId) {
                            packet2.motionX = (int)(Velocity.reverse.getValue() ? (-Velocity.x.getValue() / 100.0 * packet2.motionX) : (Velocity.x.getValue() / 100.0 * packet2.motionX));
                            packet2.motionY = (int)(Velocity.reverse.getValue() ? (-Velocity.y.getValue() / 100.0 * packet2.motionY) : (Velocity.y.getValue() / 100.0 * packet2.motionY));
                            packet2.motionZ = (int)(Velocity.reverse.getValue() ? (-Velocity.z.getValue() / 100.0 * packet2.motionZ) : (Velocity.z.getValue() / 100.0 * packet2.motionZ));
                        }
                    }
                }
            }
        }, (Predicate<EventPacketRecieve>[])new Predicate[0]);
    }
    
    static {
        Velocity.x = new SliderSetting("X modifier", 0.0, 0.0, 100.0, true);
        Velocity.y = new SliderSetting("Y modifier", 0.0, 0.0, 100.0, true);
        Velocity.z = new SliderSetting("Z modifier", 0.0, 0.0, 100.0, true);
        Velocity.reverse = new BooleanSetting("Reverse", false);
    }
}
