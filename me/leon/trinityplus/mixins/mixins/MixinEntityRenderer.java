//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import me.leon.trinityplus.mixins.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.managers.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.hacks.misc.*;
import me.leon.trinityplus.hacks.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityRenderer.class })
public class MixinEntityRenderer implements IMixin
{
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffect(final float ticks, final CallbackInfo info) {
        if (ModuleManager.getMod((Class)NoRender.class).isEnabled() && NoRender.hurtCam.getValue()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;"))
    public Entity getMouseOver(final Minecraft minecraft) {
        if (((FreeCam)ModuleManager.getMod((Class)FreeCam.class)).check() || ModuleManager.getMod((Class)FreeLook.class).isEnabled()) {
            return (Entity)Minecraft.getMinecraft().player;
        }
        return Minecraft.getMinecraft().renderViewEntity;
    }
}
