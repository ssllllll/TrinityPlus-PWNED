//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.misc;

import me.leon.trinityplus.setting.rewrite.*;
import net.minecraft.client.entity.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.entity.item.*;
import me.leon.trinityplus.utils.world.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.server.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.main.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.events.main.*;
import net.minecraft.entity.*;
import java.util.function.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;

public class FakePlayer extends Module
{
    public static BooleanSetting inv;
    public static BooleanSetting pop;
    private EntityOtherPlayerMP fake_player;
    @EventHandler
    private final Listener<EventPacketRecieve> packetRecieveListener;
    
    public FakePlayer() {
        super("FakePlayer", "Spawns in a fakeplayer", Category.MISC);
        SPacketDestroyEntities packet;
        final int[] array;
        int length;
        int i = 0;
        int id;
        Entity entity;
        float rawDamage;
        float absorption;
        boolean hasHealthDmg;
        float health;
        int times;
        this.packetRecieveListener = new Listener<EventPacketRecieve>(event -> {
            if (FakePlayer.pop.getValue() && event.getPacket() instanceof SPacketDestroyEntities) {
                packet = (SPacketDestroyEntities)event.getPacket();
                packet.getEntityIDs();
                for (length = array.length; i < length; ++i) {
                    id = array[i];
                    entity = FakePlayer.mc.world.getEntityByID(id);
                    if (entity instanceof EntityEnderCrystal && entity.getDistanceSq((Entity)this.fake_player) < 144.0) {
                        rawDamage = DamageUtils.calculateDamage(entity.getPositionVector(), (Entity)this.fake_player);
                        absorption = this.fake_player.getAbsorptionAmount() - rawDamage;
                        hasHealthDmg = (absorption < 0.0f);
                        health = this.fake_player.getHealth() + absorption;
                        if (hasHealthDmg && health > 0.0f) {
                            this.fake_player.setHealth(health);
                            this.fake_player.setAbsorptionAmount(0.0f);
                        }
                        else if (health > 0.0f) {
                            this.fake_player.setAbsorptionAmount(absorption);
                        }
                        else {
                            this.fake_player.setHealth(2.0f);
                            this.fake_player.setAbsorptionAmount(8.0f);
                            this.fake_player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 5));
                            this.fake_player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1));
                            try {
                                FakePlayer.mc.player.connection.handleEntityStatus(new SPacketEntityStatus((Entity)this.fake_player, (byte)35));
                            }
                            catch (Exception ex) {}
                            if (TotempopManager.totemMap.containsKey(this.fake_player)) {
                                times = TotempopManager.totemMap.get(this.fake_player) + 1;
                                Trinity.dispatcher.post(new EventTotemPop(EventStage.PRE, (EntityLivingBase)this.fake_player, times));
                                TotempopManager.totemMap.remove(this.fake_player);
                                TotempopManager.totemMap.put((EntityLivingBase)this.fake_player, times);
                            }
                            else {
                                Trinity.dispatcher.post(new EventTotemPop(EventStage.PRE, (EntityLivingBase)this.fake_player, 1));
                                TotempopManager.totemMap.put((EntityLivingBase)this.fake_player, 1);
                            }
                        }
                        this.fake_player.hurtTime = 5;
                    }
                }
            }
        }, (Predicate<EventPacketRecieve>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        (this.fake_player = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.fromString("ef845538-72e9-49e5-9675-1d2995036cc3"), "Listed"))).copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
        this.fake_player.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
        this.fake_player.setHealth(FakePlayer.mc.player.getHealth() + FakePlayer.mc.player.getAbsorptionAmount());
        this.fake_player.setSneaking(FakePlayer.mc.player.isSneaking());
        if (FakePlayer.inv.getValue()) {
            this.fake_player.inventory.copyInventory(FakePlayer.mc.player.inventory);
        }
        FakePlayer.mc.world.addEntityToWorld(-100, (Entity)this.fake_player);
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            this.setEnabled(false);
        }
    }
    
    @Override
    public void onDisable() {
        try {
            if (this.nullCheck()) {
                return;
            }
            FakePlayer.mc.world.removeEntity((Entity)this.fake_player);
        }
        catch (Exception ex) {}
    }
    
    static {
        FakePlayer.inv = new BooleanSetting("CopyInv", true);
        FakePlayer.pop = new BooleanSetting("Pop", true);
    }
}
