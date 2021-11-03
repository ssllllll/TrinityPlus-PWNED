//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.render;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.main.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import java.util.function.*;
import org.lwjgl.input.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;

public class FreeLook extends Module
{
    public static SliderSetting range;
    private static float cameraYaw;
    private static float cameraPitch;
    private static EntityPlayerCamera camera;
    private int flag;
    @EventHandler
    private final Listener<EventSetOpaqueCube> cubeListener;
    
    public FreeLook() {
        super("FreeLook", "LUNAR MODE", Category.RENDER);
        this.flag = -1;
        this.cubeListener = new Listener<EventSetOpaqueCube>(event -> event.cancel(), (Predicate<EventSetOpaqueCube>[])new Predicate[0]);
    }
    
    private void updateCamera() {
        if (this.nullCheck() || FreeLook.camera == null) {
            return;
        }
        if (FreeLook.mc.inGameHasFocus) {
            final float f = FreeLook.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float f2 = f * f * f * 8.0f;
            final double dx = Mouse.getDX() * f2 * 0.15;
            final double dy = Mouse.getDY() * f2 * 0.15;
            FreeLook.cameraYaw += (float)dx;
            FreeLook.cameraPitch += (float)(dy * -1.0);
            FreeLook.cameraPitch = MathHelper.clamp(FreeLook.cameraPitch, -90.0f, 90.0f);
            FreeLook.cameraYaw = MathHelper.clamp(FreeLook.cameraYaw, FreeLook.cameraYaw - 100.0f, FreeLook.cameraYaw + 100.0f);
            FreeLook.camera.rotationPitch = FreeLook.cameraPitch;
            FreeLook.camera.rotationYaw = FreeLook.cameraYaw;
        }
    }
    
    private void updateCamera2() {
        final double x = FreeLook.mc.player.posX + FreeLook.range.getValue() * Math.cos(Math.toRadians(FreeLook.cameraPitch));
        final double z = FreeLook.mc.player.posY + FreeLook.range.getValue() * Math.sin(Math.toRadians(FreeLook.cameraPitch));
        final double dist = Math.abs(FreeLook.mc.player.posX - x);
        final double x2 = FreeLook.mc.player.posX + dist * Math.cos(Math.toRadians(FreeLook.cameraYaw - 90.0f));
        final double z2 = FreeLook.mc.player.posZ + dist * Math.sin(Math.toRadians(FreeLook.cameraYaw - 90.0f));
        this.setPosition((Entity)FreeLook.camera, x2, z, z2);
        FreeLook.camera.inventory.copyInventory(FreeLook.mc.player.inventory);
    }
    
    @SubscribeEvent
    public void onRespawn(final PlayerEvent.PlayerRespawnEvent event) {
        this.setEnabled(false);
    }
    
    @SubscribeEvent
    public void onRender(final TickEvent.RenderTickEvent event) {
        if (this.nullCheck() || FreeLook.camera == null) {
            return;
        }
        this.updateCamera();
        this.updateCamera2();
    }
    
    @SubscribeEvent
    public void onRender(final RenderHandEvent event) {
        event.setCanceled(true);
    }
    
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        FreeLook.cameraPitch = FreeLook.mc.player.rotationPitch;
        FreeLook.cameraYaw = FreeLook.mc.player.rotationYaw;
        FreeLook.camera = new EntityPlayerCamera(FreeLook.mc.player.gameProfile);
        if (FreeLook.mc.gameSettings.thirdPersonView != 0) {
            this.flag = FreeLook.mc.gameSettings.thirdPersonView;
        }
        final EntityPlayer player = (EntityPlayer)FreeLook.mc.player;
        if (player != null) {
            FreeLook.camera.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
            FreeLook.camera.setRotationYawHead(player.rotationYaw);
        }
        FreeLook.mc.world.addEntityToWorld(-9283, (Entity)FreeLook.camera);
        FreeLook.mc.renderViewEntity = (Entity)FreeLook.camera;
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            this.setEnabled(false);
        }
    }
    
    public void onDisable() {
        if (this.nullCheck() || FreeLook.camera == null) {
            return;
        }
        FreeLook.mc.renderViewEntity = (Entity)FreeLook.mc.player;
        FreeLook.mc.world.removeEntity((Entity)FreeLook.camera);
        if (this.flag != -1) {
            FreeLook.mc.gameSettings.thirdPersonView = this.flag;
            this.flag = -1;
        }
    }
    
    private void setPosition(final Entity en, final double x, final double y, final double z) {
        this.setPosition(en, x, y, z, en.rotationYaw, en.rotationPitch);
    }
    
    private void setPosition(final Entity en, final double x, final double y, final double z, final float yaw, final float cameraPitch) {
        en.posX = x;
        en.prevPosX = x;
        en.posY = y;
        en.prevPosY = y;
        en.posZ = z;
        en.prevPosZ = z;
        en.rotationYaw = yaw;
        en.rotationPitch = cameraPitch;
    }
    
    static {
        FreeLook.range = new SliderSetting("Range", 0.0, 5.0, 7.0, false);
    }
    
    private static class EntityPlayerCamera extends EntityOtherPlayerMP
    {
        public EntityPlayerCamera(final GameProfile gameProfileIn) {
            super((World)FreeLook.mc.world, gameProfileIn);
        }
        
        public boolean isInvisible() {
            return true;
        }
        
        public boolean isInvisibleToPlayer(final EntityPlayer player) {
            return true;
        }
        
        public boolean isSpectator() {
            return false;
        }
    }
}
