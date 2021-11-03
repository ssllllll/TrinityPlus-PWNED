//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.utils.misc.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.utils.world.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraft.item.*;
import me.leon.trinityplus.utils.world.Rotation.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.managers.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class KillAura extends Module
{
    public static BooleanSetting main;
    public static BooleanSetting chat;
    public static BooleanSetting packet;
    public static BooleanSetting onlyHolding;
    public static ModeSetting Switch;
    public static BooleanSetting ranges;
    public static ModeSetting rayTraceMode;
    public static SliderSetting off;
    public static SliderSetting wallsRange;
    public static SliderSetting normalRange;
    public static BooleanSetting rotate;
    public static ModeSetting rotation;
    public static SliderSetting offset;
    public static BooleanSetting delays;
    public static ModeSetting delayMode;
    public static SliderSetting delay;
    public static BooleanSetting sync;
    public static BooleanSetting experimental;
    public static BooleanSetting armorMelt;
    public static BooleanSetting targeting;
    public static ModeSetting targetingMode;
    public static SliderSetting targetRange;
    public static BooleanSetting players;
    public static BooleanSetting friends;
    public static BooleanSetting neutral;
    public static BooleanSetting passive;
    public static BooleanSetting hostile;
    public static EntityLivingBase target;
    private final Timer timer;
    
    public KillAura() {
        super("KillAura", "Attacks Entities nearby", Category.COMBAT);
        this.timer = new Timer();
    }
    
    @Override
    public String getHudInfo() {
        return (KillAura.target == null) ? null : KillAura.target.getName();
    }
    
    @Override
    public void onEnable() {
        this.clean();
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        final EntityLivingBase target1 = KillAura.target;
        KillAura.target = EntityUtils.getTarget(KillAura.players.getValue(), KillAura.neutral.getValue(), KillAura.friends.getValue(), KillAura.hostile.getValue(), KillAura.passive.getValue(), KillAura.targetRange.getValue(), EntityUtils.toMode(KillAura.targetingMode.getValue()));
        if (KillAura.target == null) {
            this.clean();
            return;
        }
        if (target1 != KillAura.target && KillAura.chat.getValue()) {
            MessageUtil.sendClientMessage("Found new target: " + KillAura.target.getName(), true);
        }
        this.killAura();
    }
    
    private void killAura() {
        if (EntityUtils.getRange((Entity)KillAura.target) > KillAura.normalRange.getValue()) {
            return;
        }
        Vec3d vec = null;
        if (KillAura.rayTraceMode.getValue().equalsIgnoreCase("Leon")) {
            vec = RaytraceUtils.rayTraceLeon((Entity)KillAura.target);
            if (vec == null) {
                if (EntityUtils.getRange((Entity)KillAura.target) > KillAura.wallsRange.getValue()) {
                    this.clean();
                    return;
                }
                vec = KillAura.target.getPositionVector().add(0.0, (double)(KillAura.target.height / 2.0f), 0.0);
            }
        }
        else if (KillAura.rayTraceMode.getValue().equalsIgnoreCase("Simple")) {
            vec = KillAura.target.getPositionVector().add(0.0, (double)(KillAura.target.height / 2.0f), 0.0);
            if (!RaytraceUtils.rayTraceSimple((Entity)KillAura.target) && EntityUtils.getRange((Entity)KillAura.target) > KillAura.wallsRange.getValue()) {
                this.clean();
                return;
            }
        }
        else if (KillAura.rayTraceMode.getValue().equalsIgnoreCase("Offset-Simple")) {
            vec = KillAura.target.getPositionVector().add(0.0, (double)(KillAura.target.height / 2.0f), 0.0);
            if (!RaytraceUtils.rayTraceSimple((Entity)KillAura.target, KillAura.off.getValue()) && EntityUtils.getRange((Entity)KillAura.target) > KillAura.wallsRange.getValue()) {
                this.clean();
                return;
            }
        }
        final int slot = InventoryUtil.find((Class<? extends Item>)ItemSword.class);
        final int slot2 = InventoryUtil.find((Class<? extends Item>)ItemAxe.class);
        if (KillAura.Switch.getValue().equalsIgnoreCase("Normal")) {
            if (slot != -1) {
                KillAura.mc.player.inventory.currentItem = slot;
            }
            else {
                if (slot2 == -1) {
                    this.clean();
                    return;
                }
                KillAura.mc.player.inventory.currentItem = slot2;
            }
            KillAura.mc.playerController.updateController();
        }
        else if (slot != KillAura.mc.player.inventory.currentItem && slot2 != KillAura.mc.player.inventory.currentItem && KillAura.onlyHolding.getValue()) {
            this.clean();
            return;
        }
        if (KillAura.rotate.getValue()) {
            if (KillAura.rotation.getValue().equalsIgnoreCase("Random")) {
                RotationUtils.rotateRandom(true);
            }
            else {
                RotationUtils.rotateTowards(vec, KillAura.rotation.getValue().equalsIgnoreCase("Packet"));
            }
        }
        if (KillAura.delayMode.getValue().equalsIgnoreCase("Ready")) {
            if (KillAura.armorMelt.getValue()) {
                KillAura.mc.playerController.windowClick(KillAura.mc.player.inventoryContainer.windowId, 9, KillAura.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)KillAura.mc.player);
            }
            this.attackEntity((Entity)KillAura.target, true, KillAura.sync.getValue(), KillAura.packet.getValue());
            if (KillAura.armorMelt.getValue()) {
                KillAura.mc.playerController.windowClick(KillAura.mc.player.inventoryContainer.windowId, 9, KillAura.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)KillAura.mc.player);
                this.attackEntity((Entity)KillAura.target, false, KillAura.sync.getValue(), KillAura.packet.getValue());
            }
        }
        else if (KillAura.delayMode.getValue().equalsIgnoreCase("Custom") && this.timer.hasPassed((int)KillAura.delay.getValue())) {
            this.timer.reset();
            this.attackEntity((Entity)KillAura.target, false, KillAura.sync.getValue(), KillAura.packet.getValue());
        }
    }
    
    private void attackEntity(final Entity entity, final boolean cooldown, final boolean sync, final boolean packet) {
        if (!cooldown || KillAura.mc.player.getCooledAttackStrength(sync ? (20.0f - TickrateManager.getTPS()) : 0.0f) >= 1.0f) {
            if (packet) {
                KillAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
            }
            else {
                KillAura.mc.playerController.attackEntity((EntityPlayer)KillAura.mc.player, entity);
            }
            KillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
            KillAura.mc.player.resetCooldown();
        }
    }
    
    private void clean() {
        this.timer.reset();
    }
    
    static {
        KillAura.main = new BooleanSetting("Main", true, false);
        KillAura.chat = new BooleanSetting("Chat", KillAura.main, false);
        KillAura.packet = new BooleanSetting("Packet", KillAura.main, true);
        KillAura.onlyHolding = new BooleanSetting("Only Holding", KillAura.main, true);
        KillAura.Switch = new ModeSetting("Switch", KillAura.main, "None", new String[] { "None", "Normal" });
        KillAura.ranges = new BooleanSetting("Ranges", true, false);
        KillAura.rayTraceMode = new ModeSetting("RayTrace Mode", KillAura.ranges, "Leon", new String[] { "Leon", "Simple", "Offset-Simple" });
        KillAura.off = new SliderSetting("Offset", KillAura.ranges, 0.0, 1.0, 3.0, false);
        KillAura.wallsRange = new SliderSetting("Wall Range", KillAura.ranges, 0.0, 3.5, 5.0, false);
        KillAura.normalRange = new SliderSetting("Normal Range", KillAura.ranges, 1.0, 5.0, 6.0, false);
        KillAura.rotate = new BooleanSetting("Rotate", true, true);
        KillAura.rotation = new ModeSetting("Mode", KillAura.rotate, "Packet", new String[] { "Packet", "Client", "Random" });
        KillAura.offset = new SliderSetting("Offset", KillAura.rotate, 0.0, 1.0, 2.0, false);
        KillAura.delays = new BooleanSetting("Delays", true, false);
        KillAura.delayMode = new ModeSetting("Mode", KillAura.delays, "Custom", new String[] { "Custom", "Ready" });
        KillAura.delay = new SliderSetting("Delay", KillAura.delays, 10.0, 700.0, 2000.0, false);
        KillAura.sync = new BooleanSetting("Sync", KillAura.delays, false);
        KillAura.experimental = new BooleanSetting("Experimental", true, false);
        KillAura.armorMelt = new BooleanSetting("Armor Melt", KillAura.experimental, true);
        KillAura.targeting = new BooleanSetting("Targeting", true, false);
        KillAura.targetingMode = new ModeSetting("Mode", KillAura.targeting, "Closest", new String[] { "Closest", "Lowest Health", "Highest Health" });
        KillAura.targetRange = new SliderSetting("Range", KillAura.targeting, 0.5, 5.0, 10.0, false);
        KillAura.players = new BooleanSetting("Players", KillAura.targeting, true);
        KillAura.friends = new BooleanSetting("Friends", KillAura.targeting, false);
        KillAura.neutral = new BooleanSetting("Neutral", KillAura.targeting, false);
        KillAura.passive = new BooleanSetting("Passive", KillAura.targeting, false);
        KillAura.hostile = new BooleanSetting("Hostile", KillAura.targeting, false);
        KillAura.target = null;
    }
}
