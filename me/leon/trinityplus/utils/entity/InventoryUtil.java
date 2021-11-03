//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.entity;

import me.leon.trinityplus.utils.*;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class InventoryUtil implements Util
{
    public static int findFirst(final Class<? extends Item> clazz) {
        int b = -1;
        for (int a = 0; a < 9; ++a) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(a).getItem().getClass().equals(clazz)) {
                b = a;
                break;
            }
        }
        return b;
    }
    
    public static int find(final Class<? extends Item> clazz) {
        int b = -1;
        for (int a = 0; a < 9; ++a) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(a).getItem().getClass().equals(clazz)) {
                b = a;
            }
        }
        return b;
    }
    
    public static int findFirst(final Item item) {
        int b = -1;
        for (int a = 0; a < 9; ++a) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(a).getItem() == item) {
                b = a;
                break;
            }
        }
        return b;
    }
    
    public static int find(final Item item) {
        int b = -1;
        for (int a = 0; a < 9; ++a) {
            if (InventoryUtil.mc.player.inventory.getStackInSlot(a).getItem() == item) {
                b = a;
            }
        }
        return b;
    }
    
    public static boolean switchTo(final Item item) {
        final int a = find(item);
        if (a == -1) {
            return false;
        }
        InventoryUtil.mc.player.inventory.currentItem = a;
        InventoryUtil.mc.playerController.updateController();
        return true;
    }
    
    public static int getBlockInHotbar(final Block block) {
        for (int i = 0; i < 9; ++i) {
            final Item item = InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (item instanceof ItemBlock && ((ItemBlock)item).getBlock().equals(block)) {
                return i;
            }
        }
        return -1;
    }
    
    public static void switchToSlot(final Block block) {
        if (getBlockInHotbar(block) != -1 && InventoryUtil.mc.player.inventory.currentItem != getBlockInHotbar(block)) {
            InventoryUtil.mc.player.inventory.currentItem = getBlockInHotbar(block);
            InventoryUtil.mc.playerController.updateController();
        }
    }
    
    public static int findHotbarBlock(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (clazz.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (clazz.isInstance(block)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static void switchToSlot(final int slot) {
        if (slot != -1 && InventoryUtil.mc.player.inventory.currentItem != slot) {
            InventoryUtil.mc.player.inventory.currentItem = slot;
            InventoryUtil.mc.playerController.updateController();
        }
    }
    
    public static int amountInHotbar(final Item item) {
        return amountInHotbar(item, true);
    }
    
    public static int amountInHotbar(final Item item, final boolean offhand) {
        int quantity = 0;
        for (int i = 44; i > 35; --i) {
            final ItemStack stackInSlot = InventoryUtil.mc.player.inventoryContainer.getSlot(i).getStack();
            if (stackInSlot.getItem() == item) {
                quantity += stackInSlot.getCount();
            }
        }
        if (InventoryUtil.mc.player.getHeldItemOffhand().getItem() == item && offhand) {
            quantity += InventoryUtil.mc.player.getHeldItemOffhand().getCount();
        }
        return quantity;
    }
    
    public static int amountBlockInHotbar(final Block block) {
        return amountInHotbar(new ItemStack(block).getItem());
    }
    
    public static int amountBlockInHotbar(final Block block, final boolean offhand) {
        return amountInHotbar(new ItemStack(block).getItem(), offhand);
    }
}
