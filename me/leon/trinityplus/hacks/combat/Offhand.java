//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.leon.trinityplus.utils.world.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class Offhand extends Module
{
    public static ModeSetting offhand;
    public static SliderSetting switchHealth;
    public static BooleanSetting calcCrystalDamage;
    public static SliderSetting calculateDist;
    public static ModeSetting calcMode;
    public static BooleanSetting forceGapple;
    public static BooleanSetting forceStr;
    public static BooleanSetting onlySword;
    public static BooleanSetting reverse;
    public static BooleanSetting autoTotem;
    private static int lastSlot;
    private static int endSlot;
    private static int gapSlot;
    private static int totSlot;
    private static int strSlot;
    
    public Offhand() {
        super("Offhand", "Puts things in your offhand / mainhand", Category.COMBAT);
    }
    
    @Override
    public void onUpdate() {
        if (this.pCheck()) {
            return;
        }
        if (Offhand.mc.currentScreen != null) {
            return;
        }
        for (int a = 9; a < 36; ++a) {
            final Item item = ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(a)).getItem();
            if (item == Items.END_CRYSTAL) {
                Offhand.endSlot = a;
            }
            if (item == Items.POTIONITEM && ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(a)).stackTagCompound.toString().split(":")[2].contains("strength")) {
                Offhand.strSlot = a;
            }
            if (item == Items.TOTEM_OF_UNDYING) {
                Offhand.totSlot = a;
            }
            if (item == Items.GOLDEN_APPLE) {
                Offhand.gapSlot = a;
            }
        }
        for (int a = 36; a < 45; ++a) {
            final Item item = ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(a)).getItem();
            if (Offhand.endSlot == -1 && item == Items.END_CRYSTAL) {
                Offhand.endSlot = a;
            }
            if (Offhand.strSlot == -1 && item == Items.POTIONITEM && ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(a)).stackTagCompound.toString().split(":")[2].contains("strength")) {
                Offhand.strSlot = a;
            }
            if (Offhand.totSlot == -1 && item == Items.TOTEM_OF_UNDYING) {
                Offhand.totSlot = a;
            }
            if (Offhand.gapSlot == -1 && item == Items.GOLDEN_APPLE) {
                Offhand.gapSlot = a;
            }
        }
        if (Offhand.mc.currentScreen instanceof GuiInventory) {
            this.clean();
            return;
        }
        if ((this.calc() || this.getHealth() <= Offhand.switchHealth.getValue()) && Offhand.autoTotem.getValue()) {
            this.switchTo(Modes.TOTEM);
        }
        else if (!Offhand.forceGapple.getValue() && !Offhand.forceStr.getValue()) {
            this.switchTo(this.toMode(Offhand.offhand.getValue()));
        }
        else if (Offhand.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            if (Offhand.onlySword.getValue() && !(Offhand.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                this.switchTo(this.toMode(Offhand.offhand.getValue()));
                this.clean();
                return;
            }
            if (Offhand.forceStr.getValue() && Offhand.forceStr.getValue() && !Offhand.mc.player.isPotionActive(MobEffects.STRENGTH)) {
                this.switchTo(Modes.POT);
            }
            else if (Offhand.forceGapple.getValue()) {
                this.switchTo(Modes.GAP);
            }
            else {
                this.switchTo(this.toMode(Offhand.offhand.getValue()));
            }
        }
        else {
            this.switchTo(this.toMode(Offhand.offhand.getValue()));
        }
        this.clean();
    }
    
    private void clean() {
        Offhand.totSlot = -1;
        Offhand.gapSlot = -1;
        Offhand.strSlot = -1;
        Offhand.endSlot = -1;
    }
    
    private boolean calc() {
        if (!Offhand.calcCrystalDamage.getValue()) {
            return false;
        }
        for (final Entity entity : Offhand.mc.world.loadedEntityList) {
            if (entity.getDistanceSq((Entity)Offhand.mc.player) <= Math.pow(Offhand.calculateDist.getValue(), 2.0) && entity instanceof EntityEnderCrystal && this.getHealth() - DamageUtils.calculateDamage(entity.posX, entity.posY, entity.posZ, (Entity)Offhand.mc.player) <= (Offhand.calcMode.getValue().equalsIgnoreCase("SwitchHealth") ? Offhand.switchHealth.getValue() : 1.0)) {
                return true;
            }
        }
        return false;
    }
    
    private float getHealth() {
        return Offhand.mc.player.getHealth() + Offhand.mc.player.getAbsorptionAmount();
    }
    
    private void switchToSlot(final int from) {
        if (from == -1) {
            return;
        }
        Offhand.lastSlot = from;
        Offhand.mc.playerController.windowClick(Offhand.mc.player.inventoryContainer.windowId, from, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.player);
        Offhand.mc.playerController.windowClick(Offhand.mc.player.inventoryContainer.windowId, Offhand.reverse.getValue() ? (Offhand.mc.player.inventory.currentItem + 36) : 45, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.player);
        Offhand.mc.playerController.windowClick(Offhand.mc.player.inventoryContainer.windowId, from, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.player);
        Offhand.mc.playerController.updateController();
    }
    
    private void switchToSlot(final int from, final boolean main) {
        if (from == -1) {
            return;
        }
        Offhand.lastSlot = from;
        Offhand.mc.playerController.windowClick(Offhand.mc.player.inventoryContainer.windowId, from, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.player);
        Offhand.mc.playerController.windowClick(Offhand.mc.player.inventoryContainer.windowId, main ? (Offhand.mc.player.inventory.currentItem + 36) : 45, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.player);
        Offhand.mc.playerController.windowClick(Offhand.mc.player.inventoryContainer.windowId, from, 0, ClickType.PICKUP, (EntityPlayer)Offhand.mc.player);
        Offhand.mc.playerController.updateController();
    }
    
    private void switchTo(final Modes mode) {
        if (mode == Modes.CRYSTAL && ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(45)).getItem() != Items.END_CRYSTAL) {
            this.switchToSlot(Offhand.endSlot);
        }
        if (mode == Modes.GAP && ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(45)).getItem() != Items.GOLDEN_APPLE) {
            this.switchToSlot(Offhand.gapSlot);
        }
        if (mode == Modes.POT && ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(45)).getItem() != Items.POTIONITEM) {
            this.switchToSlot(Offhand.strSlot);
        }
        if (mode == Modes.TOTEM && ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(45)).getItem() != Items.TOTEM_OF_UNDYING) {
            this.switchToSlot(Offhand.totSlot);
        }
    }
    
    private void switchToMain(final Modes mode) {
        if (mode == Modes.CRYSTAL && ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(45)).getItem() != Items.END_CRYSTAL) {
            this.switchToSlot(Offhand.endSlot, true);
        }
        if (mode == Modes.GAP && ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(45)).getItem() != Items.GOLDEN_APPLE) {
            this.switchToSlot(Offhand.gapSlot, true);
        }
        if (mode == Modes.POT && ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(45)).getItem() != Items.POTIONITEM) {
            this.switchToSlot(Offhand.strSlot, true);
        }
        if (mode == Modes.TOTEM && ((ItemStack)Offhand.mc.player.inventoryContainer.getInventory().get(45)).getItem() != Items.TOTEM_OF_UNDYING) {
            this.switchToSlot(Offhand.totSlot, true);
        }
    }
    
    private Modes toMode(final String string) {
        switch (string) {
            case "Totem": {
                return Modes.TOTEM;
            }
            case "Gapple": {
                return Modes.GAP;
            }
            case "Crystal": {
                return Modes.CRYSTAL;
            }
            default: {
                return null;
            }
        }
    }
    
    static {
        Offhand.offhand = new ModeSetting("Mode", "Crystal", new String[] { "Crystal", "Totem", "Gapple", "None" });
        Offhand.switchHealth = new SliderSetting("Switch", 0.0, 16.0, 36.0, true);
        Offhand.calcCrystalDamage = new BooleanSetting("Calc", true, true);
        Offhand.calculateDist = new SliderSetting("Distance", Offhand.calcCrystalDamage, 1.0, 8.0, 16.0, false);
        Offhand.calcMode = new ModeSetting("CalcMode", Offhand.calcCrystalDamage, "PlayerHealth", new String[] { "PlayerHealth", "SwitchHealth" });
        Offhand.forceGapple = new BooleanSetting("Force Gapple", true);
        Offhand.forceStr = new BooleanSetting("Force Pot", false);
        Offhand.onlySword = new BooleanSetting("OnlySword", true);
        Offhand.reverse = new BooleanSetting("Reverse", false);
        Offhand.autoTotem = new BooleanSetting("AutoTotem", true);
        Offhand.lastSlot = -1;
        Offhand.endSlot = -1;
        Offhand.gapSlot = -1;
        Offhand.totSlot = -1;
        Offhand.strSlot = -1;
    }
    
    private enum Modes
    {
        POT, 
        CRYSTAL, 
        TOTEM, 
        GAP;
    }
}
