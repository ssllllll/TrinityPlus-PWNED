//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import me.leon.trinityplus.mixins.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.main.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;
import me.leon.trinityplus.events.main.*;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager implements IMixin
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacketPre(final Packet<?> packet, final CallbackInfo callbackInfo) {
        final EventPacketSend event = new EventPacketSend(EventStage.PRE, (Packet)packet);
        Trinity.dispatcher.post(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelReadPre(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callbackInfo) {
        final EventPacketRecieve event = new EventPacketRecieve(EventStage.PRE, (Packet)packet);
        Trinity.dispatcher.post(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
