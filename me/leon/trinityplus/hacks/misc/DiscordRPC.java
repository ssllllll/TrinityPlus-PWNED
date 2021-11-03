//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.misc;

import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.utils.misc.*;
import me.leon.trinityplus.main.*;

public class DiscordRPC extends Module
{
    public DiscordRPC() {
        super("DiscordRPC", "Displays a custom Discord Rich Presence", Category.MISC);
    }
    
    @Override
    public void onEnable() {
        if (!this.nullCheck()) {
            MessageUtil.sendClientMessage("Discord Rich Presence started!", true);
        }
        RPCHandler.start();
    }
    
    @Override
    public void onDisable() {
        if (!this.nullCheck()) {
            MessageUtil.sendClientMessage("Discord Rich Presence shutdown!", true);
        }
        RPCHandler.shutdown();
    }
}
