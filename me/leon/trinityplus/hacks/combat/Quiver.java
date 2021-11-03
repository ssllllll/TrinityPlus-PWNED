//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.utils.entity.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.utils.world.*;
import me.leon.trinityplus.utils.world.Rotation.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class Quiver extends Module
{
    public static SliderSetting delay;
    public static SliderSetting holdLength;
    public static ModeSetting main;
    public static ModeSetting secondary;
    private final Timer delayTimer;
    private final Timer holdTimer;
    private int stage;
    private ArrayList<Integer> map;
    private int strSlot;
    private int speedSlot;
    private int oldSlot;
    
    public Quiver() {
        super("Quiver", "Shoots an arrow over you", Category.COMBAT);
        this.delayTimer = new Timer();
        this.holdTimer = new Timer();
        this.stage = 0;
        this.strSlot = -1;
        this.speedSlot = -1;
        this.oldSlot = 1;
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        InventoryUtil.switchTo((Item)Items.BOW);
        this.clean();
        this.oldSlot = Quiver.mc.player.inventory.currentItem;
        Quiver.mc.gameSettings.keyBindUseItem.pressed = false;
    }
    
    @Override
    public void onDisable() {
        if (this.nullCheck()) {
            return;
        }
        InventoryUtil.switchToSlot(this.oldSlot);
        Quiver.mc.gameSettings.keyBindUseItem.pressed = false;
        this.clean();
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (Quiver.mc.currentScreen != null) {
            return;
        }
        if (InventoryUtil.find((Item)Items.BOW) == -1) {
            this.toggleWithMessage("Couldn't find bow in inventory! Toggling!");
            return;
        }
        SpoofingManager.rotationQueue.add(new Rotation(-90.0f, Quiver.mc.player.rotationYaw, true, Priority.High));
        if (this.stage == 0) {
            this.map = this.mapArrows();
            for (final int a : this.map) {
                final ItemStack arrow = (ItemStack)Quiver.mc.player.inventoryContainer.getInventory().get(a);
                if ((PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRENGTH) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRONG_STRENGTH) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.LONG_STRENGTH)) && this.strSlot == -1) {
                    this.strSlot = a;
                }
                if ((PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.SWIFTNESS) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.LONG_SWIFTNESS) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRONG_SWIFTNESS)) && this.speedSlot == -1) {
                    this.speedSlot = a;
                }
            }
            ++this.stage;
        }
        else if (this.stage == 1) {
            if (!this.delayTimer.hasPassAndReset((int)Quiver.delay.getValue())) {
                return;
            }
            ++this.stage;
        }
        else if (this.stage == 2) {
            this.switchTo(Quiver.main.getValue());
            ++this.stage;
        }
        else if (this.stage == 3) {
            if (!this.delayTimer.hasPassAndReset((int)Quiver.delay.getValue())) {
                return;
            }
            ++this.stage;
        }
        else if (this.stage == 4) {
            Quiver.mc.gameSettings.keyBindUseItem.pressed = true;
            this.holdTimer.reset();
            ++this.stage;
        }
        else if (this.stage == 5) {
            if (!this.holdTimer.hasPassed((int)Quiver.holdLength.getValue())) {
                return;
            }
            this.holdTimer.reset();
            ++this.stage;
        }
        else if (this.stage == 6) {
            Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Quiver.mc.player.getHorizontalFacing()));
            Quiver.mc.player.resetActiveHand();
            Quiver.mc.gameSettings.keyBindUseItem.pressed = false;
            ++this.stage;
        }
        else if (this.stage == 7) {
            if (!this.delayTimer.hasPassAndReset((int)Quiver.delay.getValue())) {
                return;
            }
            ++this.stage;
        }
        else if (this.stage == 8) {
            this.map = this.mapArrows();
            this.strSlot = -1;
            this.speedSlot = -1;
            for (final int a : this.map) {
                final ItemStack arrow = (ItemStack)Quiver.mc.player.inventoryContainer.getInventory().get(a);
                if ((PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRENGTH) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRONG_STRENGTH) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.LONG_STRENGTH)) && this.strSlot == -1) {
                    this.strSlot = a;
                }
                if ((PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.SWIFTNESS) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.LONG_SWIFTNESS) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRONG_SWIFTNESS)) && this.speedSlot == -1) {
                    this.speedSlot = a;
                }
            }
            ++this.stage;
        }
        if (this.stage == 9) {
            this.switchTo(Quiver.secondary.getValue());
            ++this.stage;
        }
        else if (this.stage == 10) {
            if (!this.delayTimer.hasPassAndReset((int)Quiver.delay.getValue())) {
                return;
            }
            ++this.stage;
        }
        else if (this.stage == 11) {
            Quiver.mc.gameSettings.keyBindUseItem.pressed = true;
            this.holdTimer.reset();
            ++this.stage;
        }
        else if (this.stage == 12) {
            if (!this.holdTimer.hasPassed((int)Quiver.holdLength.getValue())) {
                return;
            }
            this.holdTimer.reset();
            ++this.stage;
        }
        else if (this.stage == 13) {
            Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Quiver.mc.player.getHorizontalFacing()));
            Quiver.mc.player.resetActiveHand();
            Quiver.mc.gameSettings.keyBindUseItem.pressed = false;
            ++this.stage;
        }
        else if (this.stage == 14) {
            final ArrayList<Integer> map = this.mapEmpty();
            if (!map.isEmpty()) {
                final int a = map.get(0);
                Quiver.mc.playerController.windowClick(Quiver.mc.player.inventoryContainer.windowId, a, 0, ClickType.PICKUP, (EntityPlayer)Quiver.mc.player);
            }
            ++this.stage;
        }
        else if (this.stage == 15) {
            this.setEnabled(false);
        }
    }
    
    private void switchTo(final String mode) {
        if (mode.equalsIgnoreCase("Strength") && this.strSlot != -1) {
            this.switchTo(this.strSlot);
        }
        if (mode.equalsIgnoreCase("Speed") && this.speedSlot != -1) {
            this.switchTo(this.speedSlot);
        }
    }
    
    private ArrayList<Integer> mapArrows() {
        final ArrayList<Integer> map = new ArrayList<Integer>();
        for (int a = 9; a < 45; ++a) {
            if (((ItemStack)Quiver.mc.player.inventoryContainer.getInventory().get(a)).getItem() instanceof ItemTippedArrow) {
                final ItemStack arrow = (ItemStack)Quiver.mc.player.inventoryContainer.getInventory().get(a);
                if (PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRENGTH) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRONG_STRENGTH) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.LONG_STRENGTH)) {
                    map.add(a);
                }
                if (PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.SWIFTNESS) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.LONG_SWIFTNESS) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRONG_SWIFTNESS)) {
                    map.add(a);
                }
            }
        }
        return map;
    }
    
    private ArrayList<Integer> mapEmpty() {
        final ArrayList<Integer> map = new ArrayList<Integer>();
        for (int a = 9; a < 45; ++a) {
            if (((ItemStack)Quiver.mc.player.inventoryContainer.getInventory().get(a)).getItem() instanceof ItemAir || Quiver.mc.player.inventoryContainer.getInventory().get(a) == ItemStack.EMPTY) {
                map.add(a);
            }
        }
        return map;
    }
    
    private void switchTo(final int from) {
        if (from == 9) {
            return;
        }
        Quiver.mc.playerController.windowClick(Quiver.mc.player.inventoryContainer.windowId, from, 0, ClickType.PICKUP, (EntityPlayer)Quiver.mc.player);
        Quiver.mc.playerController.windowClick(Quiver.mc.player.inventoryContainer.windowId, 9, 0, ClickType.PICKUP, (EntityPlayer)Quiver.mc.player);
        Quiver.mc.playerController.windowClick(Quiver.mc.player.inventoryContainer.windowId, from, 0, ClickType.PICKUP, (EntityPlayer)Quiver.mc.player);
        Quiver.mc.playerController.updateController();
    }
    
    private boolean hasEffect(final String mode) {
        return (mode.equalsIgnoreCase("Strength") && Quiver.mc.player.isPotionActive(MobEffects.STRENGTH)) || (mode.equalsIgnoreCase("Speed") && Quiver.mc.player.isPotionActive(MobEffects.SPEED));
    }
    
    private void clean() {
        this.holdTimer.reset();
        this.delayTimer.reset();
        this.map = null;
        this.speedSlot = -1;
        this.strSlot = -1;
        this.stage = 0;
    }
    
    static {
        Quiver.delay = new SliderSetting("Delay", 0.0, 200.0, 1000.0, true);
        Quiver.holdLength = new SliderSetting("HoldLength", 0.0, 200.0, 1000.0, true);
        Quiver.main = new ModeSetting("Main", "Strength", new String[] { "Strength", "Speed", "None" });
        Quiver.secondary = new ModeSetting("Secondary", "Strength", new String[] { "Strength", "Speed", "None" });
    }
}
