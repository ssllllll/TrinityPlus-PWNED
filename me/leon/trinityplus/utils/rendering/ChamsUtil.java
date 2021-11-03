//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.rendering;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import me.leon.trinityplus.hacks.render.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.main.*;
import net.minecraft.client.renderer.*;
import org.jetbrains.annotations.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.managers.*;

public class ChamsUtil extends RenderPlayer
{
    public ChamsUtil() {
        super(Minecraft.instance.renderManager);
    }
    
    public void doRender(final Chams.ExtraInfo info, final double x, final double y, final double z, final float partialTicks) {
        final AbstractClientPlayer entity = (AbstractClientPlayer)info.entity;
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = 0.0f;
        final boolean shouldSit = entity.isRiding() && entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit();
        this.mainModel.isRiding = shouldSit;
        this.mainModel.isChild = entity.isChild();
        try {
            final float f = this.interpolateRotation(info.lastYawOffset, info.yawOffset, partialTicks);
            final float f2 = this.interpolateRotation(info.lastYaw, info.yaw, partialTicks);
            final float f3 = f2 - f;
            this.renderLivingAt(entity, x, y, z);
            this.applyRotations(entity, info.ageInTicks, f, partialTicks);
            final float f4 = this.prepareScale((EntityLivingBase)entity, partialTicks);
            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations((EntityLivingBase)entity, 0.0f, 0.0f, partialTicks);
            this.mainModel.setRotationAngles(0.0f, 0.0f, info.ageInTicks, f3, info.headPitch, f4, (Entity)entity);
            final boolean flag1 = this.setDoRenderBrightness((EntityLivingBase)entity, partialTicks);
            this.renderModel(entity, 0.0f, 0.0f, info.ageInTicks, f3, info.headPitch, f4);
            if (flag1) {
                this.unsetBrightness();
            }
            GlStateManager.depthMask(true);
            GlStateManager.disableRescaleNormal();
        }
        catch (Exception var20) {
            Trinity.LOGGER.info("Couldn't render entity! ");
            var20.printStackTrace();
        }
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
    
    public boolean bindEntityTexture(@NotNull final AbstractClientPlayer e) {
        final ResourceLocation resourcelocation = this.getEntityTexture(e);
        if (resourcelocation == null) {
            this.bindTexture(DefaultPlayerSkin.getDefaultSkin(e.entityUniqueID));
            return false;
        }
        this.bindTexture(resourcelocation);
        return true;
    }
    
    protected void renderModel(@NotNull final AbstractClientPlayer entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor) {
        final boolean flag = this.isVisible((EntityLivingBase)entitylivingbaseIn);
        final boolean flag2 = !flag && !entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer)Minecraft.getMinecraft().player);
        if (flag || flag2) {
            if (!this.bindEntityTexture(entitylivingbaseIn)) {
                return;
            }
            if (flag2) {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
            final TrinityEvent event = new TrinityEvent(EventStage.PRE);
            ((Chams)ModuleManager.getMod((Class)Chams.class)).chams(event, this.mainModel, (Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, false, 0.0f, true);
            if (!event.isCancelled()) {
                this.mainModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            }
            if (flag2) {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
        }
    }
}
