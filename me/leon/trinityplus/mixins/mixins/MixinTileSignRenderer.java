//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import me.leon.trinityplus.mixins.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.hacks.render.*;
import me.leon.trinityplus.managers.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ TileEntitySignRenderer.class })
public class MixinTileSignRenderer implements IMixin
{
    @Inject(method = { "render" }, at = { @At("HEAD") }, cancellable = true)
    public void render(final TileEntitySign te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha, final CallbackInfo info) {
        if (ModuleManager.getMod((Class)NoRender.class).isEnabled() && NoRender.signs.getValue()) {
            info.cancel();
        }
    }
}
