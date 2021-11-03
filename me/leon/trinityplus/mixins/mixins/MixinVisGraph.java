//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import me.leon.trinityplus.mixins.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.events.main.*;
import me.leon.trinityplus.main.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ VisGraph.class })
public class MixinVisGraph implements IMixin
{
    @Inject(method = { "setOpaqueCube" }, at = { @At("HEAD") }, cancellable = true)
    public void setOpaqueCube(final BlockPos pos, final CallbackInfo info) {
        final EventSetOpaqueCube event = new EventSetOpaqueCube(EventStage.PRE);
        Trinity.dispatcher.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
