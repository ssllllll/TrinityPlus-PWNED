//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.misc;

import me.leon.trinityplus.hacks.*;
import org.lwjgl.input.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public class MCP extends Module
{
    private boolean clicked;
    
    public MCP() {
        super("MCP", "throws pearls", Category.MISC);
        this.clicked = false;
    }
    
    @Override
    public void onUpdate() {
        if (Mouse.isButtonDown(2)) {
            if (!this.clicked) {
                final int pearl = InventoryUtil.find((Class<? extends Item>)ItemEnderPearl.class);
                final boolean bl;
                final boolean offhand = bl = (MCP.mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL);
                if (pearl != -1 || offhand) {
                    final int old = MCP.mc.player.inventory.currentItem;
                    if (!offhand) {
                        InventoryUtil.switchToSlot(pearl);
                    }
                    MCP.mc.playerController.processRightClick((EntityPlayer)MCP.mc.player, (World)MCP.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                    if (!offhand) {
                        InventoryUtil.switchToSlot(old);
                    }
                }
                this.clicked = true;
            }
            else {
                this.clicked = false;
            }
        }
    }
}
