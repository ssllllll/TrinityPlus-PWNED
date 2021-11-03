//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.managers;

import me.leon.trinityplus.utils.*;
import java.util.concurrent.*;
import me.zero.alpine.fork.listener.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import me.leon.trinityplus.main.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.events.main.*;
import java.util.function.*;
import net.minecraftforge.common.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

public class TotempopManager implements Util, Listenable
{
    public static ConcurrentHashMap<EntityLivingBase, Integer> totemMap;
    @EventHandler
    private final Listener<EventPacketRecieve> packetRecieveListener;
    
    public TotempopManager() {
        SPacketEntityStatus packet;
        EntityLivingBase entity;
        int times;
        this.packetRecieveListener = new Listener<EventPacketRecieve>(event -> {
            if (TotempopManager.mc.player == null || TotempopManager.mc.world == null) {
                return;
            }
            else {
                if (event.getPacket() instanceof SPacketEntityStatus) {
                    packet = (SPacketEntityStatus)event.getPacket();
                    entity = (EntityLivingBase)packet.getEntity((World)TotempopManager.mc.world);
                    if (packet.getOpCode() == 35) {
                        if (TotempopManager.totemMap.containsKey(entity)) {
                            times = TotempopManager.totemMap.get(entity) + 1;
                            Trinity.dispatcher.post(new EventTotemPop(EventStage.PRE, entity, times));
                            TotempopManager.totemMap.remove(entity);
                            TotempopManager.totemMap.put(entity, times);
                        }
                        else {
                            Trinity.dispatcher.post(new EventTotemPop(EventStage.PRE, entity, 1));
                            TotempopManager.totemMap.put(entity, 1);
                        }
                    }
                }
                return;
            }
        }, (Predicate<EventPacketRecieve>[])new Predicate[0]);
        TotempopManager.totemMap = new ConcurrentHashMap<EntityLivingBase, Integer>();
        Trinity.dispatcher.subscribe(this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    private static void update() {
        for (final EntityLivingBase entity : TotempopManager.totemMap.keySet()) {
            if (!TotempopManager.mc.world.loadedEntityList.contains(entity)) {
                TotempopManager.totemMap.remove(entity);
            }
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (TotempopManager.mc.player == null || TotempopManager.mc.world == null) {
            TotempopManager.totemMap.clear();
            return;
        }
        update();
    }
    
    public static int getPops(final EntityLivingBase entity) {
        if (TotempopManager.totemMap.containsKey(entity)) {
            return TotempopManager.totemMap.get(entity);
        }
        return 0;
    }
    
    public static int getPops(final String name) {
        boolean flag = false;
        EntityLivingBase e = null;
        for (final Entity entity : TotempopManager.mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase && entity.getName().equals(name)) {
                flag = true;
                e = (EntityLivingBase)entity;
                break;
            }
        }
        if (flag && TotempopManager.totemMap.containsKey(e)) {
            return TotempopManager.totemMap.get(e);
        }
        return 0;
    }
}
