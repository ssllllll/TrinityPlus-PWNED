//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.misc;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.main.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.network.play.server.*;
import java.util.function.*;

public class AutoKit extends Module
{
    public static final TextBoxSetting kitname;
    @EventHandler
    private final Listener<EventPacketRecieve> packetRecieveListener;
    
    public AutoKit() {
        super("AutoKit", "Auto /kits", Category.MISC);
        this.packetRecieveListener = new Listener<EventPacketRecieve>(event -> {
            if (event.getPacket() instanceof SPacketRespawn && AutoKit.mc.player.isDead) {
                AutoKit.mc.player.sendChatMessage("/kit " + AutoKit.kitname.getValue());
            }
        }, (Predicate<EventPacketRecieve>[])new Predicate[0]);
    }
    
    static {
        kitname = new TextBoxSetting("Kit name", "name");
    }
}
