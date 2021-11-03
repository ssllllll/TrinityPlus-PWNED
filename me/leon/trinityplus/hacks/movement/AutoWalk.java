//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.movement;

import me.leon.trinityplus.hacks.*;
import net.minecraft.client.settings.*;

public class AutoWalk extends Module
{
    public AutoWalk() {
        super("AutoWalk", "automatically walks", Category.MOVEMENT);
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        KeyBinding.setKeyBindState(AutoWalk.mc.gameSettings.keyBindForward.getKeyCode(), true);
    }
}
