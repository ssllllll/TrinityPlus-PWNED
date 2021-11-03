//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.client;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;

public class Manager extends Module
{
    public static final TextBoxSetting prefix;
    
    public Manager() {
        super("Manager", "Manage the client", Category.CLIENT);
    }
    
    static {
        prefix = new TextBoxSetting("Prefix", ".");
    }
}
