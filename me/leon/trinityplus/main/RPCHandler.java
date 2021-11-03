//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.main;

import club.minnced.discord.rpc.*;

public class RPCHandler
{
    private static final DiscordRPC lib;
    private static final DiscordRichPresence presence;
    
    public static void start() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user -> Trinity.LOGGER.info("RPC Started!"));
        RPCHandler.lib.Discord_Initialize("892221072148164628", handlers, true, "");
        RPCHandler.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        RPCHandler.presence.details = "Trinity+ v0.1";
        RPCHandler.presence.largeImageKey = "trinity";
        RPCHandler.presence.state = "gamin'";
        RPCHandler.presence.largeImageText = "Trinity v0.1";
        RPCHandler.lib.Discord_UpdatePresence(RPCHandler.presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                RPCHandler.lib.Discord_RunCallbacks();
                RPCHandler.presence.details = "Trinity v0.1";
                RPCHandler.presence.state = "gamin'";
                RPCHandler.presence.largeImageKey = "trinity";
                RPCHandler.presence.largeImageText = "Trinity v0.1";
                RPCHandler.lib.Discord_UpdatePresence(RPCHandler.presence);
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "RPC-Callback-Handler").start();
    }
    
    public static void shutdown() {
        RPCHandler.lib.Discord_Shutdown();
    }
    
    static {
        lib = DiscordRPC.INSTANCE;
        presence = new DiscordRichPresence();
    }
}
