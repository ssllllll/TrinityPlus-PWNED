//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.managers;

import java.util.concurrent.*;
import me.leon.trinityplus.utils.world.Rotation.*;
import me.leon.trinityplus.utils.world.location.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.events.main.*;
import java.util.function.*;
import net.minecraft.network.play.client.*;
import me.leon.trinityplus.utils.world.*;
import net.minecraftforge.common.*;
import me.leon.trinityplus.main.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class SpoofingManager implements Listenable
{
    public static LinkedBlockingQueue<Rotation> rotationQueue;
    public static Rotation serverRotation;
    public static Rotation currentRotation;
    public static LinkedBlockingQueue<Location> locationQueue;
    public static Location serverLocation;
    public static Location currentLocation;
    public static boolean cancel;
    @EventHandler
    private final Listener<SpoofEvent> rotationEventListener;
    @EventHandler
    private final Listener<EventPacketSend> onPacketSend;
    
    public SpoofingManager() {
        this.rotationEventListener = new Listener<SpoofEvent>(event -> {
            if (SpoofingManager.cancel) {
                return;
            }
            else {
                try {
                    if (SpoofingManager.currentRotation != null && SpoofingManager.currentRotation.packet) {
                        event.cancel();
                        event.yaw = SpoofingManager.currentRotation.yaw;
                        event.pitch = SpoofingManager.currentRotation.pitch;
                        event.hasRotation = true;
                    }
                    if (SpoofingManager.currentLocation != null) {
                        event.cancel();
                        event.posX = SpoofingManager.currentLocation.getPosX();
                        event.posY = SpoofingManager.currentLocation.getPosY();
                        event.posZ = SpoofingManager.currentLocation.getPosZ();
                        event.onGround = SpoofingManager.currentLocation.isOnGround();
                        event.hasLocation = true;
                    }
                }
                catch (Exception ex) {}
                return;
            }
        }, (Predicate<SpoofEvent>[])new Predicate[0]);
        this.onPacketSend = new Listener<EventPacketSend>(event -> {
            if (SpoofingManager.cancel) {
                return;
            }
            else {
                if (SpoofingManager.currentRotation != null && !SpoofingManager.rotationQueue.isEmpty() && event.getPacket() instanceof CPacketPlayer && ((CPacketPlayer)event.getPacket()).rotating) {
                    SpoofingManager.serverRotation = new Rotation(((CPacketPlayer)event.getPacket()).yaw, ((CPacketPlayer)event.getPacket()).pitch, true, Priority.Lowest);
                }
                if (SpoofingManager.currentLocation != null && !SpoofingManager.locationQueue.isEmpty() && event.getPacket() instanceof CPacketPlayer && ((CPacketPlayer)event.getPacket()).moving) {
                    SpoofingManager.serverLocation = new Location(((CPacketPlayer)event.getPacket()).x, ((CPacketPlayer)event.getPacket()).y, ((CPacketPlayer)event.getPacket()).z, ((CPacketPlayer)event.getPacket()).onGround, Priority.Lowest);
                }
                return;
            }
        }, (Predicate<EventPacketSend>[])new Predicate[0]);
        MinecraftForge.EVENT_BUS.register((Object)this);
        Trinity.dispatcher.subscribe(this);
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (SpoofingManager.cancel) {
            return;
        }
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) {
            return;
        }
        SpoofingManager.rotationQueue.stream().sorted(Comparator.comparing(rotation -> rotation.priority.getPriority()));
        SpoofingManager.locationQueue.stream().sorted(Comparator.comparing(rotation -> rotation.getPriority().getPriority()));
        if (SpoofingManager.currentRotation != null) {
            SpoofingManager.currentRotation.cancel();
            SpoofingManager.currentRotation = null;
        }
        if (!SpoofingManager.rotationQueue.isEmpty()) {
            (SpoofingManager.currentRotation = SpoofingManager.rotationQueue.poll()).updateRotations();
        }
        if (SpoofingManager.currentLocation != null) {
            SpoofingManager.currentLocation = null;
        }
        if (!SpoofingManager.locationQueue.isEmpty()) {
            SpoofingManager.currentLocation = SpoofingManager.locationQueue.poll();
        }
    }
    
    static {
        SpoofingManager.rotationQueue = new LinkedBlockingQueue<Rotation>();
        SpoofingManager.serverRotation = null;
        SpoofingManager.currentRotation = null;
        SpoofingManager.locationQueue = new LinkedBlockingQueue<Location>();
        SpoofingManager.serverLocation = null;
        SpoofingManager.currentLocation = null;
        SpoofingManager.cancel = false;
    }
}
