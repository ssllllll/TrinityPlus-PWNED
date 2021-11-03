//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins;

import net.minecraft.client.*;

public interface IMixin
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final boolean nullCheck = IMixin.mc.player == null || IMixin.mc.world == null;
    public static final boolean pCheck = IMixin.mc.player == null;
    public static final boolean wCheck = IMixin.mc.world == null;
}
