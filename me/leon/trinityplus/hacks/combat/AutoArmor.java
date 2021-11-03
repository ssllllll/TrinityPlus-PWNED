//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.utils.world.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import java.util.*;

public class AutoArmor extends Module
{
    public static SliderSetting delay;
    public static BooleanSetting elytra;
    public static BooleanSetting noThorns;
    public static BooleanSetting searchCrafting;
    public static boolean pause;
    public static boolean checkCrafting;
    private final Timer timer;
    private final ArrayList<Item> leggings;
    private final ArrayList<Item> helmets;
    private final ArrayList<Item> chestplates;
    private final ArrayList<Item> boots;
    
    public AutoArmor() {
        super("AutoArmor", "Replaces armor", Category.COMBAT);
        this.timer = new Timer();
        this.leggings = new ArrayList<Item>((Collection<? extends Item>)Arrays.asList(Items.LEATHER_LEGGINGS, Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.DIAMOND_LEGGINGS));
        this.helmets = new ArrayList<Item>((Collection<? extends Item>)Arrays.asList(Items.LEATHER_HELMET, Items.IRON_HELMET, Items.GOLDEN_HELMET, Items.CHAINMAIL_HELMET, Items.DIAMOND_HELMET));
        this.chestplates = new ArrayList<Item>(Arrays.asList((Item)Items.LEATHER_CHESTPLATE, (Item)Items.IRON_CHESTPLATE, (Item)Items.GOLDEN_CHESTPLATE, (Item)Items.CHAINMAIL_CHESTPLATE, (Item)Items.DIAMOND_CHESTPLATE, Items.ELYTRA));
        this.boots = new ArrayList<Item>((Collection<? extends Item>)Arrays.asList(Items.LEATHER_BOOTS, Items.IRON_BOOTS, Items.GOLDEN_BOOTS, Items.CHAINMAIL_BOOTS, Items.DIAMOND_BOOTS));
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (AutoArmor.pause) {
            return;
        }
        if (AutoArmor.mc.currentScreen != null) {
            return;
        }
        final int[] refined = this.refineList(this.mapArmor());
        int switched = 0;
        boolean hadtowait = false;
        for (int a = 0; a < 4; ++a) {
            if (refined[a] != -1) {
                final ItemStack armor = (ItemStack)AutoArmor.mc.player.inventoryContainer.getInventory().get(a + 5);
                if (armor == null || armor.getItem() == Items.AIR) {
                    if (!this.timer.hasPassAndReset((int)AutoArmor.delay.getValue())) {
                        hadtowait = true;
                    }
                    else {
                        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, refined[a], 0, ClickType.PICKUP, (EntityPlayer)AutoArmor.mc.player);
                        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, a + 5, 0, ClickType.PICKUP, (EntityPlayer)AutoArmor.mc.player);
                        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, refined[a], 0, ClickType.PICKUP, (EntityPlayer)AutoArmor.mc.player);
                        ++switched;
                    }
                }
            }
        }
        if (!hadtowait && switched == 0) {
            AutoArmor.checkCrafting = false;
        }
    }
    
    private ArrayList<Integer> mapArmor() {
        final ArrayList<Integer> map = new ArrayList<Integer>();
        if (AutoArmor.checkCrafting || AutoArmor.searchCrafting.getValue()) {
            for (int a = 1; a < 5; ++a) {
                final Item item = ((ItemStack)AutoArmor.mc.player.inventoryContainer.getInventory().get(a)).getItem();
                if (item instanceof ItemArmor || item instanceof ItemElytra) {
                    map.add(a);
                }
            }
        }
        for (int a = 9; a < 36; ++a) {
            final Item item = ((ItemStack)AutoArmor.mc.player.inventoryContainer.getInventory().get(a)).getItem();
            if (item instanceof ItemArmor || item instanceof ItemElytra) {
                map.add(a);
            }
        }
        return map;
    }
    
    private int[] refineList(final ArrayList<Integer> map) {
        int legSlot = -1;
        int chestSlot = -1;
        int bootsSlot = -1;
        int helmetSlot = -1;
        for (final int slot : map) {
            final ItemStack item = (ItemStack)AutoArmor.mc.player.inventoryContainer.getInventory().get(slot);
            if (EnchantmentHelper.getEnchantments(item).containsKey(Enchantment.getEnchantmentByID(7)) && AutoArmor.noThorns.getValue()) {
                continue;
            }
            if (this.leggings.contains(item.getItem())) {
                if (legSlot == -1) {
                    legSlot = slot;
                }
                else {
                    final Item leg = ((ItemStack)AutoArmor.mc.player.inventoryContainer.getInventory().get(legSlot)).getItem();
                    if (((ItemArmor)item.getItem()).damageReduceAmount >= ((ItemArmor)leg).damageReduceAmount) {
                        continue;
                    }
                    legSlot = slot;
                }
            }
            else if (this.boots.contains(item.getItem())) {
                if (bootsSlot == -1) {
                    bootsSlot = slot;
                }
                else {
                    final Item boot = ((ItemStack)AutoArmor.mc.player.inventoryContainer.getInventory().get(bootsSlot)).getItem();
                    if (((ItemArmor)item.getItem()).damageReduceAmount >= ((ItemArmor)boot).damageReduceAmount) {
                        continue;
                    }
                    bootsSlot = slot;
                }
            }
            else if (this.chestplates.contains(item.getItem())) {
                if (chestSlot == -1) {
                    chestSlot = slot;
                }
                else {
                    final Item chest = ((ItemStack)AutoArmor.mc.player.inventoryContainer.getInventory().get(chestSlot)).getItem();
                    if (item.getItem() instanceof ItemElytra && AutoArmor.elytra.getValue()) {
                        chestSlot = slot;
                    }
                    if (chest instanceof ItemElytra && !(item.getItem() instanceof ItemElytra)) {
                        continue;
                    }
                    if (!(item.getItem() instanceof ItemArmor) || !(chest instanceof ItemArmor) || ((ItemArmor)item.getItem()).damageReduceAmount >= ((ItemArmor)chest).damageReduceAmount) {
                        continue;
                    }
                    chestSlot = slot;
                }
            }
            else {
                if (!this.helmets.contains(item.getItem())) {
                    continue;
                }
                if (helmetSlot == -1) {
                    helmetSlot = slot;
                }
                else {
                    final Item helmet = ((ItemStack)AutoArmor.mc.player.inventoryContainer.getInventory().get(helmetSlot)).getItem();
                    if (((ItemArmor)item.getItem()).damageReduceAmount >= ((ItemArmor)helmet).damageReduceAmount) {
                        continue;
                    }
                    helmetSlot = slot;
                }
            }
        }
        return new int[] { helmetSlot, chestSlot, legSlot, bootsSlot };
    }
    
    static {
        AutoArmor.delay = new SliderSetting("Delay", 0.0, 200.0, 1000.0, true);
        AutoArmor.elytra = new BooleanSetting("PreferElytra", false);
        AutoArmor.noThorns = new BooleanSetting("NoThorns", true);
        AutoArmor.searchCrafting = new BooleanSetting("CraftingSlots", true);
        AutoArmor.pause = false;
        AutoArmor.checkCrafting = false;
    }
}
