//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.render;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.settings.*;
import me.zero.alpine.fork.listener.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.hacks.*;
import java.util.function.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import me.leon.trinityplus.utils.rendering.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.client.shader.*;
import java.util.*;

public class ESP extends Module
{
    public static ModeSetting mode;
    public static SliderSetting width;
    public static ColorSetting color;
    public static BooleanSetting targeting;
    public static SliderSetting range;
    public static BooleanSetting players;
    public static BooleanSetting mobs;
    public static BooleanSetting passive;
    public static BooleanSetting neutral;
    public static BooleanSetting exp;
    public static BooleanSetting items;
    public static BooleanSetting crystals;
    public static BooleanSetting misc;
    @EventHandler
    private final Listener<EventModeChange> changeListener;
    private final LinkedHashSet<Entity> entityList;
    private final ShaderHelper shaderHelper;
    private final Framebuffer frameBuffer;
    
    public String getHudInfo() {
        return ESP.mode.getValue();
    }
    
    public ESP() {
        super("ESP", "Highlights entities", Category.RENDER);
        this.changeListener = new Listener<EventModeChange>(event -> {
            if (event.getSet() == ESP.mode) {
                this.resetGlow();
            }
            return;
        }, (Predicate<EventModeChange>[])new Predicate[0]);
        this.entityList = new LinkedHashSet<Entity>();
        this.shaderHelper = new ShaderHelper(new ResourceLocation("shaders/post/esp_outline.json"), new String[] { "final" });
        this.frameBuffer = this.shaderHelper.getFrameBuffer("final");
    }
    
    @SubscribeEvent
    public void onRender(final EntityViewRenderEvent.FogColors event) {
        for (final Framebuffer shader : this.shaderHelper.shader.listFramebuffers) {
            shader.setFramebufferColor(event.getRed(), event.getGreen(), event.getBlue(), 0.0f);
        }
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        Tessellator.start2();
        final String value = ESP.mode.getValue();
        switch (value) {
            case "Box": {
                for (final Entity entity : this.entityList) {
                    Tessellator.drawBBOutline(entity.getRenderBoundingBox().offset(0.0 - entity.posX, 0.0 - entity.posY, 0.0 - entity.posZ).offset(EntityUtils.getInterpolatedPos(entity, event.getPartialTicks())), (float)ESP.width.getValue(), ESP.color.getValue());
                }
                break;
            }
            case "Shader": {
                this.drawEntities();
                this.drawShader();
                break;
            }
        }
        Tessellator.end2();
    }
    
    private void drawEntities() {
        this.frameBuffer.framebufferClear();
        this.frameBuffer.bindFramebuffer(false);
        final boolean prevRenderOutlines = ESP.mc.renderManager.renderOutlines;
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
        for (final Entity entity : this.entityList) {
            GlStateManager.enableTexture2D();
            GlStateManager.enableCull();
            final Render<Entity> renderer = (Render<Entity>)ESP.mc.renderManager.getEntityRenderObject(entity);
            if (renderer == null) {
                continue;
            }
            final float partialTicks = ESP.mc.getRenderPartialTicks();
            final float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
            final Vec3d pos = EntityUtils.getInterpolatedPos(entity, partialTicks).subtract(ESP.mc.renderManager.renderPosX, ESP.mc.renderManager.renderPosY, ESP.mc.renderManager.renderPosZ);
            renderer.setRenderOutlines(true);
            renderer.doRender(entity, pos.x, pos.y, pos.z, yaw, partialTicks);
            renderer.setRenderOutlines(prevRenderOutlines);
        }
        GlStateManager.disableTexture2D();
    }
    
    private void drawShader() {
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        if (this.shaderHelper.shader != null) {
            this.shaderHelper.shader.render(ESP.mc.getRenderPartialTicks());
        }
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        ESP.mc.framebuffer.bindFramebuffer(false);
        if (this.frameBuffer != null) {
            this.frameBuffer.framebufferRenderExt(ESP.mc.displayWidth, ESP.mc.displayHeight, false);
        }
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GlStateManager.disableCull();
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        this.entityList.clear();
        this.entityList.addAll((Collection<?>)this.getEntityList());
        final String value = ESP.mode.getValue();
        switch (value) {
            case "Glow": {
                if (!this.entityList.isEmpty()) {
                    for (final Shader in : ESP.mc.renderGlobal.entityOutlineShader.listShaders) {
                        if (in.getShaderManager().getShaderUniform("Radius") != null) {
                            in.getShaderManager().getShaderUniform("Radius").set((float)ESP.width.getValue());
                        }
                    }
                    for (final Entity in2 : ESP.mc.world.loadedEntityList) {
                        in2.glowing = this.entityList.contains(in2);
                    }
                    break;
                }
                this.resetGlow();
                break;
            }
            case "Shader": {
                for (final Shader shader : this.shaderHelper.shader.listShaders) {
                    this.setShaderSettings(shader);
                }
                break;
            }
        }
    }
    
    private ArrayList<Entity> getEntityList() {
        return EntityUtils.getESPTargets(ESP.players.getValue(), ESP.neutral.getValue(), ESP.mobs.getValue(), ESP.exp.getValue(), ESP.passive.getValue(), ESP.items.getValue(), ESP.crystals.getValue(), ESP.range.getValue());
    }
    
    private void setShaderSettings(final Shader shader) {
        if (shader.getShaderManager().getShaderUniform("color") != null) {
            shader.getShaderManager().getShaderUniform("color").set(ESP.color.getValue().getRed() / 255.0f, ESP.color.getValue().getGreen() / 255.0f, ESP.color.getValue().getBlue() / 255.0f);
        }
        if (shader.getShaderManager().getShaderUniform("outlineAlpha") != null) {
            shader.getShaderManager().getShaderUniform("outlineAlpha").set(ESP.color.getA() / 255.0f);
        }
        if (shader.getShaderManager().getShaderUniform("filledAlpha") != null) {
            shader.getShaderManager().getShaderUniform("filledAlpha").set(0.0f);
        }
        if (shader.getShaderManager().getShaderUniform("width") != null) {
            shader.getShaderManager().getShaderUniform("width").set((float)ESP.width.getValue());
        }
        if (shader.getShaderManager().getShaderUniform("Radius") != null) {
            shader.getShaderManager().getShaderUniform("Radius").set(0.0f);
        }
    }
    
    public void onDisable() {
        this.resetGlow();
    }
    
    private void resetGlow() {
        try {
            for (final Shader in : ESP.mc.renderGlobal.entityOutlineShader.listShaders) {
                if (in.getShaderManager().getShaderUniform("Radius") != null) {
                    in.getShaderManager().getShaderUniform("Radius").set(2.0f);
                }
            }
            for (final Entity in2 : ESP.mc.world.loadedEntityList) {
                in2.glowing = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        ESP.mode = new ModeSetting("Mode", "Shader", new String[] { "Shader", "Glow", "Box" });
        ESP.width = new SliderSetting("Width", 0.5, 1.0, 5.0, false);
        ESP.color = new ColorSetting("Color", 119, 0, 255, 200, false);
        ESP.targeting = new BooleanSetting("Filters", true, false);
        ESP.range = new SliderSetting("Range", ESP.targeting, 10.0, 200.0, 300.0, true);
        ESP.players = new BooleanSetting("Players", ESP.targeting, true);
        ESP.mobs = new BooleanSetting("Mobs", ESP.targeting, true);
        ESP.passive = new BooleanSetting("Passive", ESP.targeting, true);
        ESP.neutral = new BooleanSetting("Neutral", ESP.targeting, true);
        ESP.exp = new BooleanSetting("EXP", ESP.targeting, true);
        ESP.items = new BooleanSetting("Items", ESP.targeting, true);
        ESP.crystals = new BooleanSetting("Crystals", ESP.targeting, true);
        ESP.misc = new BooleanSetting("Misc", ESP.targeting, true);
    }
}
