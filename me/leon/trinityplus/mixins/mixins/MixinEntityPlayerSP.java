//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import me.leon.trinityplus.mixins.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.main.*;
import me.leon.trinityplus.utils.world.Rotation.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.hacks.misc.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.hacks.render.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import me.leon.trinityplus.events.main.*;

@Mixin(value = { EntityPlayerSP.class }, priority = 10000)
public class MixinEntityPlayerSP extends AbstractClientPlayer implements IMixin
{
    public MixinEntityPlayerSP(final World worldIn, final GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") }, cancellable = true)
    public void OnUpdateWalkingPlayer(final CallbackInfo info) {
        final SpoofEvent event = new SpoofEvent(EventStage.PRE);
        Trinity.dispatcher.post(event);
        if (event.isCancelled() && !SpoofingManager.cancel) {
            info.cancel();
            RotationUtils.updateRotationPackets(event);
        }
    }
    
    @Inject(method = { "isCurrentViewEntity" }, at = { @At("HEAD") }, cancellable = true)
    public void isCurrentViewEntity(final CallbackInfoReturnable var1) {
        if (((FreeCam)ModuleManager.getMod((Class)FreeCam.class)).check() || ModuleManager.getMod((Class)FreeLook.class).isEnabled()) {
            var1.setReturnValue((Object)true);
        }
    }
    
    @Inject(method = { "isUser" }, at = { @At("HEAD") }, cancellable = true)
    private void isUser(final CallbackInfoReturnable var1) {
        if (((FreeCam)ModuleManager.getMod((Class)FreeCam.class)).check() || ModuleManager.getMod((Class)FreeLook.class).isEnabled()) {
            var1.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "resetActiveHand" }, at = { @At("HEAD") }, cancellable = true)
    public void resetActiveHand(final CallbackInfo info) {
        final EventStopHandActive event = new EventStopHandActive(EventStage.PRE);
        Trinity.dispatcher.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "move" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(final AbstractClientPlayer player, final MoverType type, final double x, final double y, final double z) {
        final MoveEvent event = new MoveEvent(type, x, y, z, EventStage.PRE);
        Trinity.dispatcher.post(event);
        if (!event.isCancelled()) {
            super.move(type, x, y, z);
        }
        else {
            super.move(type, event.x, event.y, event.z);
        }
    }
    
    @Inject(method = { "pushOutOfBlocks" }, at = { @At("HEAD") }, cancellable = true)
    public void pushOutOfBlocks(final double var1, final double var2, final double var3, final CallbackInfoReturnable ci) {
        final BlockPushEvent blockPushEvent = new BlockPushEvent(var1, var2, var3, EventStage.PRE);
        Trinity.dispatcher.post(blockPushEvent);
        if (blockPushEvent.isCancelled()) {
            ci.cancel();
        }
    }
}
