//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.client;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;

public class ClientColor extends Module
{
    public static ColorSetting color;
    
    public ClientColor() {
        super("ClientColor", "Customize the client's colors", Category.CLIENT);
    }
    
    static {
        ClientColor.color = new ColorSetting("Color", 255, 0, 0, 255, true);
    }
}
