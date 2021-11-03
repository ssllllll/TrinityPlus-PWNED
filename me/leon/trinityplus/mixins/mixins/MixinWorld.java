//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import me.leon.trinityplus.mixins.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.hacks.render.*;
import me.leon.trinityplus.managers.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ World.class })
public class MixinWorld implements IMixin
{
    @Inject(method = { "getRainStrength" }, at = { @At("HEAD") }, cancellable = true)
    public void getRainStrength(final float delta, final CallbackInfoReturnable<Float> info) {
        if (ModuleManager.getMod((Class)NoRender.class).isEnabled() && NoRender.weather.getValue()) {
            info.cancel();
            info.setReturnValue((Object)0.0f);
        }
    }
}
