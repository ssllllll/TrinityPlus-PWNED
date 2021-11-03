//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import me.leon.trinityplus.mixins.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.hacks.render.*;
import me.leon.trinityplus.managers.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ LayerArmorBase.class })
public class MixinLayerArmorBase implements IMixin
{
    private EntityLivingBase b;
    
    @Inject(method = { "renderArmorLayer" }, at = { @At("HEAD") }, cancellable = true)
    public void renderArmorLayer(final EntityLivingBase p_Entity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale, final EntityEquipmentSlot slotIn, final CallbackInfo info) {
        if (ModuleManager.getMod((Class)NoRender.class).isEnabled() && NoRender.armor.getValue()) {
            info.cancel();
        }
        this.b = p_Entity;
    }
    
    @ModifyVariable(method = { "renderArmorLayer" }, name = { "headPitch" }, at = @At("HEAD"))
    public float getHeadPitch(final float headPitch) {
        if (SpoofingManager.currentRotation != null && this.b == MixinLayerArmorBase.mc.player) {
            return SpoofingManager.currentRotation.pitch;
        }
        return headPitch;
    }
}
