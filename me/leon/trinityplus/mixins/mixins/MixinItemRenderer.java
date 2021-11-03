//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.events.main.*;
import me.leon.trinityplus.main.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.item.*;
import me.leon.trinityplus.hacks.render.*;
import me.leon.trinityplus.hacks.movement.*;
import me.leon.trinityplus.managers.*;

@Mixin({ ItemRenderer.class })
public class MixinItemRenderer
{
    @Inject(method = { "transformSideFirstPerson" }, at = { @At("HEAD") })
    public void transformSideFirstPerson(final EnumHandSide hand, final float p_187459_2_, final CallbackInfo callbackInfo) {
        final TransformFirstPersonEvent event = new TransformFirstPersonEvent(EventStage.PRE, hand);
        Trinity.dispatcher.post(event);
    }
    
    @Inject(method = { "transformEatFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void transformEatFirstPerson(final float p_187454_1_, final EnumHandSide hand, final ItemStack stack, final CallbackInfo callbackInfo) {
        final TransformFirstPersonEvent event = new TransformFirstPersonEvent(EventStage.PRE, hand);
        Trinity.dispatcher.post(event);
        if (ViewModel.cancelEating.getValue() && ModuleManager.getMod((Class)Velocity.class).isEnabled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "transformFirstPerson" }, at = { @At("HEAD") })
    public void transformFirstPerson(final EnumHandSide hand, final float p_187453_2_, final CallbackInfo callbackInfo) {
        final TransformFirstPersonEvent event = new TransformFirstPersonEvent(EventStage.PRE, hand);
        Trinity.dispatcher.post(event);
    }
}
