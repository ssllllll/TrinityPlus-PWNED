//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.misc;

import me.leon.trinityplus.hacks.*;
import org.lwjgl.input.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import me.leon.trinityplus.main.*;
import com.mojang.realmsclient.gui.*;
import me.leon.trinityplus.utils.misc.*;
import net.minecraft.entity.*;

public class MCF extends Module
{
    private boolean clicked;
    
    public MCF() {
        super("MCF", "middle click friend", Category.MISC);
        this.clicked = false;
    }
    
    @Override
    public void onUpdate() {
        if (Mouse.isButtonDown(2)) {
            if (!this.clicked && MCF.mc.currentScreen == null) {
                final RayTraceResult result = MCF.mc.objectMouseOver;
                final Entity entity;
                if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
                    if (Trinity.friendManager.isFriend((EntityPlayer)entity)) {
                        Trinity.friendManager.remove(entity.getName(), entity.getUniqueID());
                        MessageUtil.sendClientMessage(ChatFormatting.RED + entity.getName() + " has been unfriended", true);
                    }
                    else {
                        Trinity.friendManager.add(entity.getName(), entity.getUniqueID());
                        MessageUtil.sendClientMessage(ChatFormatting.AQUA + entity.getName() + " has been friended", true);
                    }
                }
            }
            this.clicked = true;
        }
        else {
            this.clicked = false;
        }
    }
}
