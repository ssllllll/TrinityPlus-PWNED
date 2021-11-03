//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.events.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.model.*;
import me.leon.trinityplus.events.main.*;
import me.leon.trinityplus.main.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderEnderCrystal.class })
public class MixinRenderEntityCrystal
{
    @Shadow
    @Final
    private static ResourceLocation ENDER_CRYSTAL_TEXTURES;
    private static ResourceLocation glint;
    
    @Redirect(method = { "doRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderModelBaseHook(final ModelBase model, final Entity entity, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        final EventRenderEntityCrystal event = new EventRenderEntityCrystal(EventStage.PRE, (EntityEnderCrystal)entity, (ModelEnderCrystal)model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Trinity.dispatcher.post(event);
        if (!event.isCancelled()) {
            model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }
    
    static {
        MixinRenderEntityCrystal.glint = new ResourceLocation("textures/glint.png");
    }
}
