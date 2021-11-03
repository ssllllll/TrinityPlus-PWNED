//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.movement;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoSlow extends Module
{
    public static BooleanSetting gui;
    public static BooleanSetting food;
    
    public NoSlow() {
        super("NoSlow", "Walk at full speed when using items", Category.MOVEMENT);
    }
    
    @SubscribeEvent
    public void onInputUpdate(final InputUpdateEvent event) {
        if (NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding() && NoSlow.food.getValue()) {
            final MovementInput movementInput = event.getMovementInput();
            movementInput.moveStrafe *= 5.0f;
            final MovementInput movementInput2 = event.getMovementInput();
            movementInput2.moveForward *= 5.0f;
        }
    }
    
    static {
        NoSlow.gui = new BooleanSetting("Gui", true);
        NoSlow.food = new BooleanSetting("Food", true);
    }
}
