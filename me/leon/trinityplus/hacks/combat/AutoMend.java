//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.init.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.utils.world.*;
import me.leon.trinityplus.utils.world.Rotation.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.utils.misc.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;

public class AutoMend extends Module
{
    public static ModeSetting mode;
    public static BooleanSetting clientRotations;
    public static BooleanSetting predict;
    public static BooleanSetting packet;
    public static KeybindSetting macroBind;
    public static BooleanSetting stopVals;
    public static SliderSetting boots;
    public static SliderSetting leggings;
    public static SliderSetting chestPlate;
    public static SliderSetting helmet;
    private int oldslot;
    private boolean started;
    
    public AutoMend() {
        super("AutoMend", "Automatically mends armor", Category.COMBAT);
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        this.start(AutoMend.mode.getValue().equalsIgnoreCase("Toggle"));
    }
    
    @Override
    public void onDisable() {
        if (this.nullCheck()) {
            return;
        }
        this.end();
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (AutoMend.mode.getValue().equalsIgnoreCase("Hold")) {
            if (!AutoMend.macroBind.down()) {
                if (this.started) {
                    this.end();
                    this.started = false;
                }
                return;
            }
            if (!this.started) {
                this.start(true);
                this.started = true;
            }
        }
        final int xpSlot = InventoryUtil.find(Items.EXPERIENCE_BOTTLE);
        if (xpSlot == -1) {
            return;
        }
        if (AutoMend.clientRotations.getValue()) {
            AutoMend.mc.player.rotationPitch = 90.0f;
        }
        else {
            SpoofingManager.rotationQueue.add(new Rotation(90.0f, AutoMend.mc.player.rotationYaw, true, Priority.Normal));
        }
        if (AutoMend.packet.getValue()) {
            AutoMend.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(xpSlot));
        }
        else {
            InventoryUtil.switchToSlot(xpSlot);
        }
        if (AutoMend.predict.getValue()) {
            final int totalXp = AutoMend.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityXPOrb).filter(entity -> entity.getDistanceSq((Entity)AutoMend.mc.player) <= 1.0).mapToInt(entity -> entity.xpValue).sum();
            if (totalXp * 2 >= EntityUtils.getTotalArmor((EntityPlayer)AutoMend.mc.player)) {
                this.end();
                this.toggleWithMessage("Done Mending! Toggling!");
                return;
            }
            this.checkArmor();
            this.mend();
        }
        else {
            this.checkArmor();
            this.mend();
        }
        if (EntityUtils.isNaked((EntityLivingBase)AutoMend.mc.player)) {
            this.end();
            if (AutoMend.mode.getValue().equalsIgnoreCase("Toggle")) {
                this.toggleWithMessage("Done Mending! Toggling!");
            }
            else {
                this.started = false;
                MessageUtil.sendClientMessage("Done Mending!", true);
            }
        }
    }
    
    private void checkArmor() {
        for (final ItemStack stack : AutoMend.mc.player.getArmorInventoryList()) {
            if (stack != null) {
                if (stack.getItem() == Items.AIR) {
                    continue;
                }
                final float dmg = (stack.getMaxDamage() - stack.getItemDamage()) / (float)stack.getMaxDamage() * 100.0f;
                final int foundSlot = this.findEmptySlot();
                if (stack.getItem() == Items.DIAMOND_BOOTS && dmg >= AutoMend.boots.getValue() && foundSlot != -1) {
                    AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, 8, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
                    AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, foundSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
                }
                if (stack.getItem() == Items.DIAMOND_CHESTPLATE && dmg >= AutoMend.chestPlate.getValue() && foundSlot != -1) {
                    AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
                    AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, foundSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
                }
                if (stack.getItem() == Items.DIAMOND_LEGGINGS && dmg >= AutoMend.leggings.getValue() && foundSlot != -1) {
                    AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, 7, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
                    AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, foundSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
                }
                if (stack.getItem() != Items.DIAMOND_HELMET || dmg < AutoMend.helmet.getValue() || foundSlot == -1) {
                    continue;
                }
                AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, 5, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
                AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, foundSlot, 0, ClickType.PICKUP, (EntityPlayer)AutoMend.mc.player);
            }
        }
    }
    
    private void end() {
        AutoArmor.pause = false;
        AutoCrystal.pause = false;
        if (AutoMend.packet.getValue()) {
            AutoMend.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
        }
        else {
            InventoryUtil.switchToSlot(this.oldslot);
        }
    }
    
    private void start(final boolean hold) {
        AutoArmor.pause = true;
        AutoArmor.checkCrafting = true;
        if (hold) {
            AutoCrystal.pause = true;
        }
        this.oldslot = AutoMend.mc.player.inventory.currentItem;
    }
    
    private int findEmptySlot() {
        for (int a = 1; a < 5; ++a) {
            final ItemStack stack = (ItemStack)AutoMend.mc.player.inventoryContainer.getInventory().get(a);
            if (stack == ItemStack.EMPTY || stack == null || stack.getItem() == Items.AIR) {
                return a;
            }
        }
        for (int a = 9; a < 36; ++a) {
            final ItemStack stack = (ItemStack)AutoMend.mc.player.inventoryContainer.getInventory().get(a);
            if (stack == ItemStack.EMPTY || stack == null || stack.getItem() == Items.AIR) {
                return a;
            }
        }
        return -1;
    }
    
    private void mend() {
        AutoMend.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
    }
    
    static {
        AutoMend.mode = new ModeSetting("Mode", "Toggle", new String[] { "Toggle", "Hold" });
        AutoMend.clientRotations = new BooleanSetting("ClientRotations", false);
        AutoMend.predict = new BooleanSetting("Predict", true);
        AutoMend.packet = new BooleanSetting("Packet", true);
        AutoMend.macroBind = new KeybindSetting("Hold Bind", 24);
        AutoMend.stopVals = new BooleanSetting("StopVals", true, false);
        AutoMend.boots = new SliderSetting("Boots", AutoMend.stopVals, 0.0, 90.0, 100.0, true);
        AutoMend.leggings = new SliderSetting("Leggings", AutoMend.stopVals, 0.0, 90.0, 100.0, true);
        AutoMend.chestPlate = new SliderSetting("Chestplate", AutoMend.stopVals, 0.0, 90.0, 100.0, true);
        AutoMend.helmet = new SliderSetting("Helmet", AutoMend.stopVals, 0.0, 90.0, 100.0, true);
    }
}
