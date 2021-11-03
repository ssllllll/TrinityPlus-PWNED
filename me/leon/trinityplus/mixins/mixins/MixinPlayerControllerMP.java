//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.main.*;
import org.spongepowered.asm.mixin.injection.*;
import me.leon.trinityplus.events.main.*;

@Mixin({ PlayerControllerMP.class })
public class MixinPlayerControllerMP
{
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> info) {
        final EventDamageBlock event = new EventDamageBlock(EventStage.PRE, posBlock, directionFacing);
        Trinity.dispatcher.post(event);
        if (event.isCancelled()) {
            info.setReturnValue((Object)false);
            info.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDestroyBlock(final BlockPos posBlock, final CallbackInfoReturnable<Boolean> info) {
        final EventDestroyBlock event = new EventDestroyBlock(EventStage.PRE, posBlock);
        Trinity.dispatcher.post(event);
        if (event.isCancelled()) {
            info.setReturnValue((Object)false);
            info.cancel();
        }
    }
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void clickBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> info) {
        final EventClickBlock event = new EventClickBlock(EventStage.PRE, posBlock, directionFacing);
        Trinity.dispatcher.post(event);
        if (event.isCancelled()) {
            info.setReturnValue((Object)false);
            info.cancel();
        }
    }
}
