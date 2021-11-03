//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.network.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.events.main.*;
import me.leon.trinityplus.main.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ NetHandlerPlayClient.class })
public class MixinNetHandlerPlayClient
{
    @Inject(method = { "handleSpawnObject" }, at = { @At("RETURN") }, cancellable = true)
    public void handleSpawnObject(final SPacketSpawnObject packet, final CallbackInfo info) {
        final EventSpawnObject event = new EventSpawnObject(EventStage.POST, packet);
        Trinity.dispatcher.post(event);
    }
}
