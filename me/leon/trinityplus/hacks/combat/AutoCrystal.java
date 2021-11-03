//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat;

import me.leon.trinityplus.hacks.combat.autocrystal.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.events.main.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.utils.world.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.main.*;
import java.util.function.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import me.leon.trinityplus.utils.rendering.*;
import java.text.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.utils.entity.*;

public class AutoCrystal extends Module
{
    public static final Timer breakTimer;
    public static final Timer placeTimer;
    public static final Timer totemTimer;
    public static BooleanSetting main;
    public static SliderSetting iterations;
    public static ModeSetting noSuicide;
    public static SliderSetting pauseHealth;
    public static BooleanSetting Place;
    public static ModeSetting placeMode;
    public static ModeSetting heuristic;
    public static SliderSetting placeDelay;
    public static SliderSetting placeRange;
    public static SliderSetting placeRangeWalls;
    public static SliderSetting minTargetDamagePlace;
    public static SliderSetting maxSelfDamagePlace;
    public static ModeSetting rayTracePlaceMode;
    public static SliderSetting offsetPlace;
    public static BooleanSetting packetPlace;
    public static BooleanSetting placeSwing;
    public static ModeSetting swingArmPlace;
    public static BooleanSetting packetSwingPlace;
    public static ModeSetting switchPlace;
    public static ModeSetting multiPlace;
    public static BooleanSetting bounds;
    public static BooleanSetting limit;
    public static BooleanSetting doubleCheck;
    public static BooleanSetting antiTotem;
    public static SliderSetting antiTotemTime;
    public static SliderSetting antiTotemMin;
    public static SliderSetting antiTotemMaxSelf;
    public static BooleanSetting overrideFacePlaceTotem;
    public static BooleanSetting Break;
    public static ModeSetting breakMode;
    public static SliderSetting breakDelay;
    public static SliderSetting breakRange;
    public static SliderSetting breakRangeWalls;
    public static SliderSetting minTargetDamageBreak;
    public static SliderSetting maxSelfDamageBreak;
    public static BooleanSetting packetBreak;
    public static ModeSetting rayTraceBreakMode;
    public static BooleanSetting breakSwing;
    public static ModeSetting swingArmBreak;
    public static BooleanSetting packetSwingBreak;
    public static SliderSetting offsetBreak;
    public static SliderSetting breakAttempts;
    public static ModeSetting syncMode;
    public static BooleanSetting rotate;
    public static BooleanSetting placeRotate;
    public static BooleanSetting breakRotate;
    public static BooleanSetting clientRotate;
    public static BooleanSetting logic;
    public static ModeSetting logicMode;
    public static ModeSetting version;
    public static BooleanSetting timing;
    public static ModeSetting timingMode;
    public static BooleanSetting safe;
    public static SliderSetting safeTime;
    public static BooleanSetting facePlace;
    public static KeybindSetting forceBind;
    public static BooleanSetting armorBreaker;
    public static SliderSetting armorBreakerScale;
    public static SliderSetting facePlaceMinHealth;
    public static SliderSetting facePlaceMinDamage;
    public static BooleanSetting rendering;
    public static ModeSetting renderMode;
    public static SliderSetting renderWidth;
    public static SliderSetting renderHeight;
    public static SliderSetting clawHeight;
    public static ColorSetting outlineColor;
    public static ColorSetting fillColor;
    public static BooleanSetting renderDamage;
    public static ColorSetting renderDamageColor;
    public static BooleanSetting multithreading;
    public static BooleanSetting threadedPlace;
    public static BooleanSetting threadedBreak;
    public static BooleanSetting threadedTargeting;
    public static BooleanSetting targeting;
    public static ModeSetting targetingMode;
    public static SliderSetting targetRange;
    public static BooleanSetting players;
    public static BooleanSetting friends;
    public static BooleanSetting neutral;
    public static BooleanSetting passive;
    public static BooleanSetting hostile;
    public static BooleanSetting predict1;
    public static BooleanSetting predict;
    public static SliderSetting predictTicks;
    public static BooleanSetting selfPredict;
    public static SliderSetting selfPredictTicks;
    public static BooleanSetting chorusPredict;
    public static BooleanSetting packetPredict;
    public static boolean pause;
    public static EntityLivingBase target;
    public static HashSet<CrystalPosition> placedCrystals;
    public static CrystalPosition curPosPlace;
    public static Crystal curBreakCrystal;
    public static Placer placer;
    public static Breaker breaker;
    public static Targeting targeter;
    @EventHandler
    private final Listener<EventPacketSend> sendListener;
    @EventHandler
    private final Listener<EventPacketSend> onPacketSend;
    @EventHandler
    private final Listener<EventPacketRecieve> onPacketReceive;
    @EventHandler
    private final Listener<EventTotemPop> totemPopListener;
    
    public AutoCrystal() {
        super("AutoCrystal", "Nagasaki", Category.COMBAT);
        CPacketPlayerTryUseItemOnBlock packet;
        int id;
        final Iterator<Entity> iterator;
        Entity e;
        this.sendListener = new Listener<EventPacketSend>(event -> {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && AutoCrystal.packetPredict.getValue()) {
                packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                if (WorldUtils.getBlock(packet.position) == Blocks.OBSIDIAN && AutoCrystal.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
                    id = 0;
                    AutoCrystal.mc.world.loadedEntityList.iterator();
                    while (iterator.hasNext()) {
                        e = iterator.next();
                        if (e.entityId > id) {
                            id = e.entityId;
                        }
                    }
                    this.attackByID(id + 1);
                    this.attackByID(id + 2);
                    this.attackByID(id + 3);
                    this.attackByID(id + 4);
                    this.attackByID(id + 5);
                    Trinity.LOGGER.info((Object)id);
                }
            }
            return;
        }, (Predicate<EventPacketSend>[])new Predicate[0]);
        final Packet rawPacket;
        this.onPacketSend = new Listener<EventPacketSend>(event -> {
            rawPacket = event.getPacket();
            if (rawPacket instanceof CPacketUseEntity) {
                AutoCrystal.breaker.instaSync((CPacketUseEntity)rawPacket);
            }
            return;
        }, (Predicate<EventPacketSend>[])new Predicate[0]);
        final Packet rawPacket2;
        SPacketSoundEffect soundPacket;
        final SPacketSoundEffect sPacketSoundEffect;
        this.onPacketReceive = new Listener<EventPacketRecieve>(event -> {
            rawPacket2 = event.getPacket();
            if (rawPacket2 instanceof SPacketSoundEffect) {
                AutoCrystal.breaker.soundSync((SPacketSoundEffect)event.getPacket());
            }
            if (rawPacket2 instanceof SPacketSpawnObject) {
                AutoCrystal.breaker.sequential((SPacketSpawnObject)event.getPacket());
            }
            if (event.getPacket() instanceof SPacketSoundEffect) {
                soundPacket = (SPacketSoundEffect)event.getPacket();
                if (soundPacket.getSound() == SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT && AutoCrystal.chorusPredict.getValue()) {
                    AutoCrystal.mc.world.loadedEntityList.spliterator().forEachRemaining(player -> {
                        if (player instanceof EntityPlayer && player != AutoCrystal.mc.player && ((Entity)player).getDistance(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ()) <= AutoCrystal.targetRange.getValue()) {
                            ((Entity)player).setEntityBoundingBox(((Entity)player).getEntityBoundingBox().offset(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ()));
                        }
                    });
                }
            }
            return;
        }, (Predicate<EventPacketRecieve>[])new Predicate[0]);
        this.totemPopListener = new Listener<EventTotemPop>(event -> AutoCrystal.totemTimer.setTime(System.currentTimeMillis() + AutoCrystal.antiTotemTime.intValue() * 50L), (Predicate<EventTotemPop>[])new Predicate[0]);
        AutoCrystal.placedCrystals = new HashSet<CrystalPosition>();
        AutoCrystal.targeter = new Targeting();
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (AutoCrystal.timingMode.getValue().equalsIgnoreCase("Fast")) {
            return;
        }
        if (AutoCrystal.pause) {
            return;
        }
        if (AutoCrystal.threadedTargeting.getValue()) {
            new Targeting().start();
        }
        else {
            AutoCrystal.targeter.run();
        }
        this.autoCrystal();
    }
    
    @SubscribeEvent
    public void onFastTick(final TickEvent event) {
        try {
            if (this.nullCheck()) {
                return;
            }
            if (!AutoCrystal.timingMode.getValue().equalsIgnoreCase("Fast")) {
                return;
            }
            if (AutoCrystal.pause) {
                return;
            }
            if (AutoCrystal.threadedTargeting.getValue()) {
                final Targeting t = new Targeting();
                t.start();
            }
            else {
                AutoCrystal.targeter.run();
            }
            this.autoCrystal();
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (AutoCrystal.curPosPlace == null) {
            return;
        }
        if (!AutoCrystal.rendering.getValue()) {
            return;
        }
        final AxisAlignedBB pos = new AxisAlignedBB(AutoCrystal.curPosPlace.getBase());
        final String value = AutoCrystal.renderMode.getValue();
        switch (value) {
            case "Both": {
                Tessellator.drawBBOutline(pos, (float)AutoCrystal.renderWidth.getValue(), AutoCrystal.outlineColor.getValue());
                Tessellator.drawBBFill(pos, AutoCrystal.fillColor.getValue());
                break;
            }
            case "Outline": {
                Tessellator.drawBBOutline(pos, (float)AutoCrystal.renderWidth.getValue(), AutoCrystal.outlineColor.getValue());
                break;
            }
            case "Fill": {
                Tessellator.drawBBFill(pos, AutoCrystal.fillColor.getValue());
                break;
            }
            case "Claw": {
                Tessellator.drawBBClaw(pos, (float)AutoCrystal.renderWidth.getValue(), (float)AutoCrystal.clawHeight.getValue(), AutoCrystal.outlineColor.getValue());
                break;
            }
            case "Slab": {
                Tessellator.drawBBSlab(pos, (float)AutoCrystal.renderHeight.getValue(), AutoCrystal.outlineColor.getValue());
                break;
            }
        }
        if (AutoCrystal.renderDamage.getValue()) {
            final DecimalFormat format = new DecimalFormat("##.#");
            final String formatted = format.format(AutoCrystal.curPosPlace.getTargetDamage());
            Tessellator.drawTextFromBlock(AutoCrystal.curPosPlace.getBase(), formatted, AutoCrystal.renderDamageColor.getValue().getRGB(), 1.0f);
        }
    }
    
    @Override
    public String getHudInfo() {
        return (AutoCrystal.target == null) ? null : AutoCrystal.target.getName();
    }
    
    private void autoCrystal() {
        for (int a = 0; a < AutoCrystal.iterations.getValue(); ++a) {
            AutoCrystal.placedCrystals.removeIf(id -> WorldUtils.getRange(new Vec3d(id.getBase().x + 0.5, id.getBase().y + 0.5, id.getBase().z + 0.5)) > 10.0);
            if (!AutoCrystal.timingMode.is("Sequential") || (AutoCrystal.timingMode.is("Sequential") && AutoCrystal.safe.getValue() && !AutoCrystal.breaker.isSafe() && AutoCrystal.breaker.getSafeTimer().hasPassed(AutoCrystal.safeTime.intValue()))) {
                final String value = AutoCrystal.logicMode.getValue();
                switch (value) {
                    case "PlaceBreak": {
                        AutoCrystal.placer.placeCrystal();
                        AutoCrystal.breaker.breakCrystals();
                        break;
                    }
                    case "BreakPlace": {
                        AutoCrystal.breaker.breakCrystals();
                        AutoCrystal.placer.placeCrystal();
                        break;
                    }
                }
                AutoCrystal.breaker.setSafe(false);
                AutoCrystal.breaker.getSafeTimer().reset();
            }
            else {
                AutoCrystal.placer.placeCrystal();
            }
        }
    }
    
    private void attackByID(final int entityId) {
        final CPacketUseEntity sequentialCrystal = new CPacketUseEntity();
        sequentialCrystal.entityId = entityId;
        sequentialCrystal.action = CPacketUseEntity.Action.ATTACK;
        AutoCrystal.mc.player.connection.sendPacket((Packet)sequentialCrystal);
    }
    
    static {
        breakTimer = new Timer();
        placeTimer = new Timer();
        totemTimer = new Timer();
        AutoCrystal.main = new BooleanSetting("Main", true, false);
        AutoCrystal.iterations = new SliderSetting("Iterations", AutoCrystal.main, 1.0, 1.0, 3.0, true);
        AutoCrystal.noSuicide = new ModeSetting("NoSuicide", AutoCrystal.main, "Both", new String[] { "Place", "Destroy", "None", "Both" });
        AutoCrystal.pauseHealth = new SliderSetting("Pause Health", AutoCrystal.main, 0.0, 6.0, 20.0, true);
        AutoCrystal.Place = new BooleanSetting("Place", true, true);
        AutoCrystal.placeMode = new ModeSetting("Place Mode", AutoCrystal.Place, "Closest", new String[] { "Closest", "Damage" });
        AutoCrystal.heuristic = new ModeSetting("Preference", AutoCrystal.Place, "Target", new String[] { "Target", "Self", "Subtract", "Atomic" });
        AutoCrystal.placeDelay = new SliderSetting("Place Delay", AutoCrystal.Place, 0.0, 0.0, 20.0, true);
        AutoCrystal.placeRange = new SliderSetting("Range", AutoCrystal.Place, 0.5, 5.0, 6.0, false);
        AutoCrystal.placeRangeWalls = new SliderSetting("Walls Range", AutoCrystal.Place, 0.0, 3.5, 6.0, false);
        AutoCrystal.minTargetDamagePlace = new SliderSetting("Min Target Damage", AutoCrystal.Place, 0.0, 6.0, 20.0, false);
        AutoCrystal.maxSelfDamagePlace = new SliderSetting("Max Self Damage", AutoCrystal.Place, 0.0, 8.0, 20.0, false);
        AutoCrystal.rayTracePlaceMode = new ModeSetting("RayTrace Mode", AutoCrystal.Place, "Simple", new String[] { "Leon", "Simple", "Offset" });
        AutoCrystal.offsetPlace = new SliderSetting("Offset", AutoCrystal.Place, 0.0, 1.0, 3.0, false);
        AutoCrystal.packetPlace = new BooleanSetting("Packet Place", AutoCrystal.Place, true);
        AutoCrystal.placeSwing = new BooleanSetting("Swing", AutoCrystal.Place, false, false);
        AutoCrystal.swingArmPlace = new ModeSetting("Swing Arm", AutoCrystal.placeSwing, "Mainhand", new String[] { "Mainhand", "Offhand", "Both", "None" });
        AutoCrystal.packetSwingPlace = new BooleanSetting("Packet Swing", AutoCrystal.placeSwing, false);
        AutoCrystal.switchPlace = new ModeSetting("Switch", AutoCrystal.Place, "Packet", new String[] { "Packet", "Normal", "None" });
        AutoCrystal.multiPlace = new ModeSetting("Multiplace", AutoCrystal.Place, "None", new String[] { "None", "Semi", "Full" });
        AutoCrystal.bounds = new BooleanSetting("Bounds", AutoCrystal.Place, true);
        AutoCrystal.limit = new BooleanSetting("Limit", AutoCrystal.Place, false);
        AutoCrystal.doubleCheck = new BooleanSetting("DoubleCheck", AutoCrystal.Place, true);
        AutoCrystal.antiTotem = new BooleanSetting("Anti-Totem", AutoCrystal.Place, true);
        AutoCrystal.antiTotemTime = new SliderSetting("TotemTime", AutoCrystal.antiTotem, 0.0, 3.0, 20.0, true);
        AutoCrystal.antiTotemMin = new SliderSetting("MinDMG", AutoCrystal.antiTotem, 0.0, 3.0, 10.0, false);
        AutoCrystal.antiTotemMaxSelf = new SliderSetting("MaxSelfDMG", AutoCrystal.antiTotem, 0.0, 3.0, 10.0, false);
        AutoCrystal.overrideFacePlaceTotem = new BooleanSetting("Override", AutoCrystal.antiTotem, true);
        AutoCrystal.Break = new BooleanSetting("Break", true, true);
        AutoCrystal.breakMode = new ModeSetting("Break Mode", AutoCrystal.Break, "Smart", new String[] { "Smart", "All", "Only Own" });
        AutoCrystal.breakDelay = new SliderSetting("Break Delay", AutoCrystal.Break, 0.0, 0.0, 20.0, true);
        AutoCrystal.breakRange = new SliderSetting("Range", AutoCrystal.Break, 0.5, 5.0, 6.0, false);
        AutoCrystal.breakRangeWalls = new SliderSetting("Walls Range", AutoCrystal.Break, 0.0, 3.0, 6.0, false);
        AutoCrystal.minTargetDamageBreak = new SliderSetting("Min Target Damage", AutoCrystal.Break, 0.0, 6.0, 20.0, false);
        AutoCrystal.maxSelfDamageBreak = new SliderSetting("Max Self Damage", AutoCrystal.Break, 0.0, 8.0, 20.0, false);
        AutoCrystal.packetBreak = new BooleanSetting("Packet Break", AutoCrystal.Break, true);
        AutoCrystal.rayTraceBreakMode = new ModeSetting("RayTrace Mode", AutoCrystal.Break, "Simple", new String[] { "Leon", "Simple", "Offset" });
        AutoCrystal.breakSwing = new BooleanSetting("Swing", AutoCrystal.Place, false, false);
        AutoCrystal.swingArmBreak = new ModeSetting("Swing Arm", AutoCrystal.breakSwing, "Mainhand", new String[] { "Mainhand", "Offhand", "Both", "None" });
        AutoCrystal.packetSwingBreak = new BooleanSetting("Packet Swing", AutoCrystal.breakSwing, false);
        AutoCrystal.offsetBreak = new SliderSetting("Offset", AutoCrystal.Break, 0.0, 1.0, 3.0, false);
        AutoCrystal.breakAttempts = new SliderSetting("Break Attempts", AutoCrystal.Break, 1.0, 3.0, 10.0, true);
        AutoCrystal.syncMode = new ModeSetting("Sync", AutoCrystal.Break, "None", new String[] { "Sound", "Instant", "None" });
        AutoCrystal.rotate = new BooleanSetting("Rotate", true, false);
        AutoCrystal.placeRotate = new BooleanSetting("Place", AutoCrystal.rotate, false);
        AutoCrystal.breakRotate = new BooleanSetting("Break", AutoCrystal.rotate, false);
        AutoCrystal.clientRotate = new BooleanSetting("Client", AutoCrystal.rotate, false);
        AutoCrystal.logic = new BooleanSetting("Logic", true, false);
        AutoCrystal.logicMode = new ModeSetting("Logic Mode", AutoCrystal.logic, "PlaceBreak", new String[] { "PlaceBreak", "BreakPlace" });
        AutoCrystal.version = new ModeSetting("Version", AutoCrystal.logic, "1.12.2", new String[] { "1.13+", "1.12.2" });
        AutoCrystal.timing = new BooleanSetting("Timing", true, false);
        AutoCrystal.timingMode = new ModeSetting("Mode", AutoCrystal.timing, "Tick", new String[] { "Tick", "Fast", "Sequential" });
        AutoCrystal.safe = new BooleanSetting("Safe", AutoCrystal.timing, true, s -> AutoCrystal.timingMode.is("Sequential"));
        AutoCrystal.safeTime = new SliderSetting("Safe Time", AutoCrystal.timing, 0.0, 100.0, 1000.0, true, s -> AutoCrystal.timingMode.is("Sequential"));
        AutoCrystal.facePlace = new BooleanSetting("FacePlace", true, true);
        AutoCrystal.forceBind = new KeybindSetting("Force Faceplace", AutoCrystal.facePlace, 24);
        AutoCrystal.armorBreaker = new BooleanSetting("Armor Breaker", AutoCrystal.facePlace, true);
        AutoCrystal.armorBreakerScale = new SliderSetting("Armor Scale", AutoCrystal.facePlace, 0.0, 30.0, 100.0, true);
        AutoCrystal.facePlaceMinHealth = new SliderSetting("Min Health", AutoCrystal.facePlace, 0.0, 8.0, 36.0, true);
        AutoCrystal.facePlaceMinDamage = new SliderSetting("Min Damage", AutoCrystal.facePlace, 0.0, 2.4, 8.0, false);
        AutoCrystal.rendering = new BooleanSetting("Rendering", true, true);
        AutoCrystal.renderMode = new ModeSetting("RenderMode", AutoCrystal.rendering, "Both", new String[] { "Both", "Outline", "Fill", "Claw", "Slab" });
        AutoCrystal.renderWidth = new SliderSetting("Width", AutoCrystal.rendering, 0.1, 0.5, 3.0, false);
        AutoCrystal.renderHeight = new SliderSetting("Height", AutoCrystal.rendering, -1.0, 0.0, 1.0, false);
        AutoCrystal.clawHeight = new SliderSetting("ClawHeight", AutoCrystal.rendering, 0.0, 0.3, 1.0, false);
        AutoCrystal.outlineColor = new ColorSetting("OutLine Color", AutoCrystal.rendering, 0, 255, 255, 120, false);
        AutoCrystal.fillColor = new ColorSetting("Fill Color", AutoCrystal.rendering, 0, 255, 255, 120, false);
        AutoCrystal.renderDamage = new BooleanSetting("Render Damage", AutoCrystal.rendering, true);
        AutoCrystal.renderDamageColor = new ColorSetting("Render Color", AutoCrystal.rendering, 255, 255, 255, 255, false);
        AutoCrystal.multithreading = new BooleanSetting("Multithreading", true, false);
        AutoCrystal.threadedPlace = new BooleanSetting("Place", AutoCrystal.multithreading, false);
        AutoCrystal.threadedBreak = new BooleanSetting("Break", AutoCrystal.multithreading, false);
        AutoCrystal.threadedTargeting = new BooleanSetting("Targeting", AutoCrystal.multithreading, false);
        AutoCrystal.targeting = new BooleanSetting("Targeting", true, false);
        AutoCrystal.targetingMode = new ModeSetting("Mode", AutoCrystal.targeting, "Closest", new String[] { "Closest", "Lowest Health", "Highest Health" });
        AutoCrystal.targetRange = new SliderSetting("Range", AutoCrystal.targeting, 0.5, 5.0, 15.0, false);
        AutoCrystal.players = new BooleanSetting("Players", AutoCrystal.targeting, true);
        AutoCrystal.friends = new BooleanSetting("Friends", AutoCrystal.targeting, false);
        AutoCrystal.neutral = new BooleanSetting("Neutral", AutoCrystal.targeting, false);
        AutoCrystal.passive = new BooleanSetting("Passive", AutoCrystal.targeting, false);
        AutoCrystal.hostile = new BooleanSetting("Hostile", AutoCrystal.targeting, false);
        AutoCrystal.predict1 = new BooleanSetting("Predict", true, false);
        AutoCrystal.predict = new BooleanSetting("Predict", AutoCrystal.predict1, true);
        AutoCrystal.predictTicks = new SliderSetting("Predict Ticks", AutoCrystal.predict1, 0.0, 3.0, 10.0, true);
        AutoCrystal.selfPredict = new BooleanSetting("Self Predict", AutoCrystal.predict1, true);
        AutoCrystal.selfPredictTicks = new SliderSetting("Self Ticks", AutoCrystal.predict1, 0.0, 3.0, 10.0, true, s -> AutoCrystal.selfPredict.getValue());
        AutoCrystal.chorusPredict = new BooleanSetting("Chorus Predict", AutoCrystal.predict1, true);
        AutoCrystal.packetPredict = new BooleanSetting("Packet Predict", AutoCrystal.predict1, true);
        AutoCrystal.pause = false;
        AutoCrystal.target = null;
        AutoCrystal.curPosPlace = null;
        AutoCrystal.curBreakCrystal = null;
        AutoCrystal.placer = new Placer();
        AutoCrystal.breaker = new Breaker();
    }
    
    private static class Targeting extends Thread
    {
        @Override
        public void run() {
            AutoCrystal.target = EntityUtils.getTarget(AutoCrystal.players.getValue(), AutoCrystal.neutral.getValue(), AutoCrystal.friends.getValue(), AutoCrystal.hostile.getValue(), AutoCrystal.passive.getValue(), AutoCrystal.targetRange.getValue(), EntityUtils.toMode(AutoCrystal.targetingMode.getValue()));
        }
    }
}
