//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.rendering;

import me.leon.trinityplus.utils.*;
import java.util.*;
import net.minecraft.util.*;
import me.leon.trinityplus.main.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.resources.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;

public class ShaderHelper implements Util
{
    private final HashMap<String, Framebuffer> frameBufferMap;
    public ShaderGroup shader;
    private boolean frameBuffersInitialized;
    
    public ShaderHelper(final ResourceLocation location, final String... names) {
        this.frameBufferMap = new HashMap<String, Framebuffer>();
        this.frameBuffersInitialized = false;
        if (!OpenGlHelper.shadersSupported) {
            Trinity.LOGGER.warn("Shaders are unsupported by OpenGL!");
        }
        if (this.isIntegrated()) {
            Trinity.LOGGER.warn("Running on Intel Integrated Graphics!");
        }
        try {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
            (this.shader = new ShaderGroup(ShaderHelper.mc.getTextureManager(), (IResourceManager)ShaderHelper.mc.resourceManager, ShaderHelper.mc.framebuffer, location)).createBindFramebuffers(ShaderHelper.mc.displayWidth, ShaderHelper.mc.displayHeight);
        }
        catch (Exception e) {
            Trinity.LOGGER.warn("Failed to load shaders");
            e.printStackTrace();
        }
        for (final String name : names) {
            this.frameBufferMap.put(name, this.shader.getFramebufferRaw(name));
        }
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onTick0(final TickEvent.ClientTickEvent event) {
        if (this.shader == null) {
            return;
        }
        int w = 0;
        int h = 0;
        if (event.phase == TickEvent.Phase.START) {
            w = ShaderHelper.mc.displayWidth;
            h = ShaderHelper.mc.displayHeight;
        }
        else if (event.phase == TickEvent.Phase.END && (w != ShaderHelper.mc.displayWidth || h != ShaderHelper.mc.displayHeight)) {
            this.shader.createBindFramebuffers(ShaderHelper.mc.displayWidth, ShaderHelper.mc.displayHeight);
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (!this.frameBuffersInitialized && this.shader != null) {
            this.shader.createBindFramebuffers(ShaderHelper.mc.displayWidth, ShaderHelper.mc.displayHeight);
            this.frameBuffersInitialized = true;
        }
    }
    
    public Framebuffer getFrameBuffer(final String name) {
        return this.frameBufferMap.get(name);
    }
    
    private boolean isIntegrated() {
        return GlStateManager.glGetString(7936).contains("Intel");
    }
}
