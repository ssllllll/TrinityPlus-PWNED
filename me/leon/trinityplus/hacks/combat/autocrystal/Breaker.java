//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat.autocrystal;

import me.leon.trinityplus.utils.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.combat.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.utils.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import java.util.function.*;
import java.util.stream.*;

public class Breaker implements Util, Listenable
{
    private List<Crystal> filtered;
    private Mapper mapper;
    private boolean safe;
    private Timer safeTimer;
    
    public Breaker() {
        this.safe = false;
        this.safeTimer = new Timer();
        this.mapper = new Mapper(this);
        this.filtered = new ArrayList<Crystal>();
    }
    
    public boolean breakCrystals() {
        if (Utility.nullCheck()) {
            return false;
        }
        if (!AutoCrystal.Break.getValue()) {
            return false;
        }
        if (!AutoCrystal.breakTimer.hasPassAndReset((int)AutoCrystal.breakDelay.getValue() * 50)) {
            return false;
        }
        if (Utility.getSelfHealth() <= AutoCrystal.pauseHealth.getValue()) {
            return false;
        }
        AutoCrystal.curBreakCrystal = null;
        if (AutoCrystal.threadedBreak.getValue()) {
            new Mapper(this).start();
        }
        else {
            this.mapper.run();
        }
        for (final Crystal crystal : this.filtered) {
            final String value = AutoCrystal.breakMode.getValue();
            int n = -1;
            switch (value.hashCode()) {
                case -2006019822: {
                    if (value.equals("Only Own")) {
                        n = 0;
                        break;
                    }
                    break;
                }
                case 79996329: {
                    if (value.equals("Smart")) {
                        n = 1;
                        break;
                    }
                    break;
                }
                case 65921: {
                    if (value.equals("All")) {
                        n = 2;
                        break;
                    }
                    break;
                }
            }
            Label_0394: {
                switch (n) {
                    case 0:
                    case 1: {
                        if (AutoCrystal.target == null) {
                            Utility.reset();
                            return false;
                        }
                        if (AutoCrystal.breakMode.getValue().equalsIgnoreCase("Only Own")) {
                            boolean found = false;
                            for (final CrystalPosition pos : AutoCrystal.placedCrystals) {
                                if (pos.getBase().equals((Object)crystal.getBase())) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                continue;
                            }
                        }
                        if (!this.filterDamage(crystal)) {
                            break Label_0394;
                        }
                        AutoCrystal.curBreakCrystal = crystal;
                        for (int a = 0; a < AutoCrystal.breakAttempts.getValue(); ++a) {
                            this.attackCrystal(AutoCrystal.curBreakCrystal);
                        }
                        if (AutoCrystal.syncMode.getValue().equalsIgnoreCase("Instant")) {
                            AutoCrystal.curBreakCrystal.getCrystal().setDead();
                        }
                        break Label_0394;
                    }
                    case 2: {
                        AutoCrystal.curBreakCrystal = crystal;
                        for (int a = 0; a < AutoCrystal.breakAttempts.getValue(); ++a) {
                            this.attackCrystal(AutoCrystal.curBreakCrystal);
                        }
                        continue;
                    }
                }
            }
        }
        if (AutoCrystal.curBreakCrystal != null) {
            Utility.swingHand(true);
            return true;
        }
        return false;
    }
    
    private boolean filterReachable(final EntityEnderCrystal crystal) {
        final String value = AutoCrystal.rayTraceBreakMode.getValue();
        switch (value) {
            case "Leon": {
                final Vec3d vec = RaytraceUtils.rayTraceLeon((Entity)crystal);
                final boolean walls = vec == null;
                if (walls) {
                    if (WorldUtils.isWithin(AutoCrystal.breakRangeWalls.getValue(), Utility.eyePos(), crystal.getPositionVector())) {
                        return true;
                    }
                    break;
                }
                else {
                    if (WorldUtils.isWithin(AutoCrystal.breakRange.getValue(), Utility.eyePos(), vec)) {
                        return true;
                    }
                    break;
                }
                break;
            }
            case "Offset":
            case "Simple": {
                final boolean walls2 = RaytraceUtils.rayTraceSimple(crystal.getPositionVector(), AutoCrystal.rayTraceBreakMode.getValue().equalsIgnoreCase("Offset") ? AutoCrystal.offsetBreak.getValue() : 0.0);
                if (walls2) {
                    if (WorldUtils.isWithin(AutoCrystal.breakRangeWalls.getValue(), Utility.eyePos(), crystal.getPositionVector())) {
                        return true;
                    }
                    break;
                }
                else {
                    if (WorldUtils.isWithin(AutoCrystal.breakRange.getValue(), Utility.eyePos(), crystal.getPositionVector())) {
                        return true;
                    }
                    break;
                }
                break;
            }
        }
        return false;
    }
    
    private boolean filterDamage(final Crystal crystal) {
        return crystal.getTargetDamage() >= AutoCrystal.minTargetDamageBreak.getValue() && crystal.getSelfDamage() < AutoCrystal.maxSelfDamageBreak.getValue();
    }
    
    public void sequential(final SPacketSpawnObject packet) {
        if (Breaker.mc.world == null) {
            return;
        }
        if (AutoCrystal.pause) {
            return;
        }
        if (packet.getType() == 51 && AutoCrystal.timingMode.is("Sequential") && AutoCrystal.Break.getValue()) {
            final EntityEnderCrystal crystal = new EntityEnderCrystal((World)Breaker.mc.world, packet.getX(), packet.getY(), packet.getZ());
            if (this.filterReachable(crystal) && this.filterDamage(new Crystal(crystal))) {
                this.attackByID(packet.getEntityID());
                this.safe = true;
                this.safeTimer.reset();
            }
        }
    }
    
    public void soundSync(final SPacketSoundEffect packet) {
        if (Breaker.mc.world == null) {
            return;
        }
        if (AutoCrystal.pause) {
            return;
        }
        if (AutoCrystal.syncMode.getValue().equalsIgnoreCase("Sound") && packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
            for (final Entity e : Breaker.mc.world.loadedEntityList) {
                if (e instanceof EntityEnderCrystal && e.getDistanceSq(packet.getX(), packet.getY(), packet.getZ()) <= 36.0) {
                    e.setDead();
                }
            }
        }
    }
    
    public void instaSync(final CPacketUseEntity packet) {
        if (AutoCrystal.syncMode.getValue().equalsIgnoreCase("Instant") && packet.getEntityFromWorld((World)Breaker.mc.world) instanceof EntityEnderCrystal) {
            packet.getEntityFromWorld((World)Breaker.mc.world).setDead();
            Breaker.mc.world.removeEntityFromWorld(packet.entityId);
        }
    }
    
    private void attackCrystal(final Crystal crystal) {
        Utility.rotate(true, crystal.getCrystal().getPositionVector());
        if (AutoCrystal.packetBreak.getValue()) {
            this.attackByID(crystal.getCrystal().getEntityId());
        }
        else {
            Breaker.mc.playerController.attackEntity((EntityPlayer)Breaker.mc.player, (Entity)crystal.getCrystal());
        }
    }
    
    private void attackByID(final int entityId) {
        final CPacketUseEntity sequentialCrystal = new CPacketUseEntity();
        sequentialCrystal.entityId = entityId;
        sequentialCrystal.action = CPacketUseEntity.Action.ATTACK;
        Breaker.mc.player.connection.sendPacket((Packet)sequentialCrystal);
    }
    
    public List<Crystal> getFiltered() {
        return this.filtered;
    }
    
    public Breaker setFiltered(final List<Crystal> filtered) {
        this.filtered = filtered;
        return this;
    }
    
    public Mapper getMapper() {
        return this.mapper;
    }
    
    public Breaker setMapper(final Mapper mapper) {
        this.mapper = mapper;
        return this;
    }
    
    public boolean isSafe() {
        return this.safe;
    }
    
    public Breaker setSafe(final boolean safe) {
        this.safe = safe;
        return this;
    }
    
    public Timer getSafeTimer() {
        return this.safeTimer;
    }
    
    public Breaker setSafeTimer(final Timer safeTimer) {
        this.safeTimer = safeTimer;
        return this;
    }
    
    private static class Mapper extends Thread
    {
        private final Breaker breaker;
        
        public Mapper(final Breaker breaker) {
            this.breaker = breaker;
        }
        
        @Override
        public void run() {
            this.breaker.filtered = (List<Crystal>)Util.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).map(e -> e).filter(x$0 -> this.breaker.filterReachable(x$0)).map(Crystal::new).collect(Collectors.toList());
        }
    }
}
