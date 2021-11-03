//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.misc;

import me.leon.trinityplus.setting.rewrite.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.events.main.*;
import me.leon.trinityplus.events.settings.*;
import me.leon.trinityplus.utils.world.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.network.play.client.*;
import java.util.function.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import me.leon.trinityplus.utils.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import me.leon.trinityplus.hacks.render.*;
import me.leon.trinityplus.managers.*;
import net.minecraft.world.*;
import me.leon.trinityplus.hacks.client.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import org.jetbrains.annotations.*;

public class FreeCam extends Module
{
    public static final ModeSetting mode;
    private static final SliderSetting speed;
    @EventHandler
    private final Listener<EventPacketSend> packetListener;
    @EventHandler
    private final Listener<BlockPushEvent> pushListener;
    @EventHandler
    private final Listener<EventSetOpaqueCube> cubeListener;
    @EventHandler
    private final Listener<MoveEvent> moveEventListener;
    @EventHandler
    private final Listener<EventModeChange> toggleListener;
    private EntityPlayerCamera other;
    private final Timer timer;
    
    @Override
    public String getHudInfo() {
        return FreeCam.mode.getValue();
    }
    
    public FreeCam() {
        super("FreeCam", "Out-of-body experience", Category.MISC);
        this.packetListener = new Listener<EventPacketSend>(event -> {
            if ((event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketUseEntity || event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) && FreeCam.mode.getValue().equalsIgnoreCase("Normal")) {
                event.cancel();
            }
            return;
        }, (Predicate<EventPacketSend>[])new Predicate[0]);
        this.pushListener = new Listener<BlockPushEvent>(event -> {
            if (FreeCam.mode.getValue().equalsIgnoreCase("Normal")) {
                event.cancel();
            }
            return;
        }, (Predicate<BlockPushEvent>[])new Predicate[0]);
        this.cubeListener = new Listener<EventSetOpaqueCube>(event -> event.cancel(), (Predicate<EventSetOpaqueCube>[])new Predicate[0]);
        this.moveEventListener = new Listener<MoveEvent>(event -> {
            if (FreeCam.mode.getValue().equalsIgnoreCase("Normal")) {
                MotionUtils.doStrafe((float)(FreeCam.speed.getValue() / 10.0));
                event.y = this.getY() * FreeCam.speed.getValue();
                FreeCam.mc.player.noClip = true;
                event.cancel();
            }
            return;
        }, (Predicate<MoveEvent>[])new Predicate[0]);
        this.toggleListener = new Listener<EventModeChange>(event -> {
            if (event.getSet() == FreeCam.mode) {
                this.toggle();
                this.toggle();
            }
            return;
        }, (Predicate<EventModeChange>[])new Predicate[0]);
        this.timer = new Timer();
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            this.setEnabled(false);
        }
    }
    
    @Override
    public void onDisable() {
        if (this.nullCheck()) {
            return;
        }
        if (this.other == null) {
            return;
        }
        FreeCam.mc.world.removeEntity((Entity)this.other);
        if (FreeCam.mode.getValue().equalsIgnoreCase("Normal")) {
            FreeCam.mc.player.setPositionAndRotation(this.other.posX, this.other.posY, this.other.posZ, this.other.rotationYaw, this.other.rotationPitch);
        }
        this.setRender((Entity)FreeCam.mc.player);
    }
    
    @SubscribeEvent
    public void onRender(final TickEvent.RenderTickEvent event) {
        if (this.nullCheck()) {
            this.setEnabled(false);
            return;
        }
        if (!FreeCam.mode.getValue().equalsIgnoreCase("Camera")) {
            return;
        }
        if (FreeCam.mc.currentScreen != null) {
            return;
        }
        if (this.other == null) {
            return;
        }
        double cameraYaw = this.other.rotationYaw;
        double cameraPitch = this.other.rotationPitch;
        final float f = FreeCam.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float f2 = f * f * f * 8.0f;
        final double dx = Mouse.getDX() * f2 * 0.15;
        final double dy = Mouse.getDY() * f2 * 0.15;
        cameraYaw += dx;
        cameraPitch -= dy;
        cameraPitch = MathHelper.clamp(cameraPitch, -90.0, 90.0);
        this.other.rotationPitch = (float)cameraPitch;
        this.other.rotationYaw = (float)cameraYaw;
        final float moveForward = this.getMoveForward();
        final float moveStrafe = this.getMoveStrafe();
        final Vec3d vec = MathUtils.interpolateStrafe(this.other.rotationYaw, (float)this.timer.getDelta(), 1000.0f, (float)(FreeCam.speed.getValue() * 5.0), moveForward, moveStrafe, (float)(-this.getY()));
        this.timer.reset();
        final double x = vec.x;
        final double y = vec.y;
        final double z = vec.z;
        this.other.noClip = true;
        this.other.move(MoverType.PLAYER, x, y, z);
        this.other.inventory.copyInventory(FreeCam.mc.player.inventory);
    }
    
    @SubscribeEvent
    public void onRenderHand(final RenderHandEvent event) {
        event.setCanceled(true);
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        ModuleManager.getMod(FreeLook.class).setEnabled(false);
        (this.other = new EntityPlayerCamera((World)FreeCam.mc.world, FreeCam.mc.player.gameProfile)).copyLocationAndAnglesFrom((Entity)FreeCam.mc.player);
        this.other.inventory.copyInventory(FreeCam.mc.player.inventory);
        this.other.noClip = true;
        this.other.inventory.copyInventory(FreeCam.mc.player.inventory);
        this.other.setLocationAndAngles(FreeCam.mc.player.posX, FreeCam.mc.player.posY, FreeCam.mc.player.posZ, FreeCam.mc.player.rotationYaw, FreeCam.mc.player.rotationPitch);
        this.other.setRotationYawHead(FreeCam.mc.player.rotationYaw);
        FreeCam.mc.world.addEntityToWorld(-1234, (Entity)this.other);
        this.timer.reset();
        if (FreeCam.mode.getValue().equalsIgnoreCase("Camera")) {
            this.setRender((Entity)this.other);
        }
    }
    
    private float getMoveForward() {
        if (FreeCam.mc.gameSettings.keyBindForward.isKeyDown() && FreeCam.mc.gameSettings.keyBindBack.isKeyDown()) {
            return 0.0f;
        }
        if (FreeCam.mc.gameSettings.keyBindBack.isKeyDown()) {
            return -1.0f;
        }
        if (FreeCam.mc.gameSettings.keyBindForward.isKeyDown()) {
            return 1.0f;
        }
        return 0.0f;
    }
    
    private float getMoveStrafe() {
        if (FreeCam.mc.gameSettings.keyBindLeft.isKeyDown() && FreeCam.mc.gameSettings.keyBindRight.isKeyDown()) {
            return 0.0f;
        }
        if (FreeCam.mc.gameSettings.keyBindRight.isKeyDown()) {
            return -1.0f;
        }
        if (FreeCam.mc.gameSettings.keyBindLeft.isKeyDown()) {
            return 1.0f;
        }
        return 0.0f;
    }
    
    private double getY() {
        if (FreeCam.mc.gameSettings.keyBindSneak.isKeyDown() && FreeCam.mc.gameSettings.keyBindJump.isKeyDown()) {
            return 0.0;
        }
        if (FreeCam.mc.gameSettings.keyBindJump.isKeyDown()) {
            return 1.0;
        }
        if (FreeCam.mc.gameSettings.keyBindSneak.isKeyDown()) {
            return -1.0;
        }
        return 0.0;
    }
    
    private void setRender(final Entity entity) {
        FreeCam.mc.renderViewEntity = entity;
        if (ModuleManager.getMod((Class<? extends Module>)ClickGUI.class).isEnabled()) {
            ClickGUI.loadShader();
        }
    }
    
    public boolean check() {
        return this.isEnabled() && FreeCam.mode.getValue().equalsIgnoreCase("Camera") && !this.nullCheck();
    }
    
    static {
        mode = new ModeSetting("Mode", "Camera", new String[] { "Camera", "Normal" });
        speed = new SliderSetting("Speed", 1.0, 4.5, 20.0, false);
    }
    
    private static class EntityPlayerCamera extends EntityOtherPlayerMP
    {
        public EntityPlayerCamera(final World worldIn, final GameProfile gameProfileIn) {
            super(worldIn, gameProfileIn);
        }
        
        public boolean isInvisible() {
            return true;
        }
        
        public boolean isInvisibleToPlayer(@NotNull final EntityPlayer player) {
            return FreeCam.mode.getValue().equalsIgnoreCase("Camera") || (FreeCam.mode.getValue().equalsIgnoreCase("Normal") && player != FreeCam.mc.player);
        }
        
        public boolean isSpectator() {
            return false;
        }
    }
}
