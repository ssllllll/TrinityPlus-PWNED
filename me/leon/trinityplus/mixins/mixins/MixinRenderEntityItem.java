//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import me.leon.trinityplus.mixins.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.item.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.hacks.render.*;
import me.leon.trinityplus.managers.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderEntityItem.class })
public class MixinRenderEntityItem implements IMixin
{
    @Inject(method = { "doRender" }, at = { @At("HEAD") }, cancellable = true)
    public void doRender(final EntityItem entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo info) {
        if (ModuleManager.getMod((Class)NoRender.class).isEnabled() && NoRender.items.getValue()) {
            info.cancel();
        }
    }
}
