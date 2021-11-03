//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import net.minecraft.tileentity.*;
import me.leon.trinityplus.mixins.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.tileentity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.hacks.render.*;
import me.leon.trinityplus.managers.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ TileEntityChestRenderer.class })
public class MixinTileEntityChestRenderer extends TileEntitySpecialRenderer<TileEntityChest> implements IMixin
{
    @Inject(method = { "render" }, at = { @At("HEAD") }, cancellable = true)
    public void render(final TileEntityChest te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha, final CallbackInfo info) {
        if ((x != y || y != z || x != 0.0) && NoRender.chests.getValue() && ModuleManager.getMod((Class)NoRender.class).isEnabled()) {
            info.cancel();
        }
    }
}
