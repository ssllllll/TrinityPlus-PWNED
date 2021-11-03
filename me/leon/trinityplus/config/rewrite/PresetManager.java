//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.config.rewrite;

import me.leon.trinityplus.utils.misc.*;
import java.io.*;
import java.util.*;

public class PresetManager
{
    public static ArrayList<Preset> presets;
    
    public PresetManager() {
        FileUtils.makeIfDoesntExist(SaveConfig.mainDir);
        for (final File file : SaveConfig.mainDir.listFiles()) {
            if (file.isDirectory()) {
                PresetManager.presets.add(new Preset(file.getName()));
            }
        }
    }
    
    public static Preset find(final String name) {
        for (final Preset pre : PresetManager.presets) {
            if (pre.name.equals(name)) {
                return pre;
            }
        }
        return null;
    }
    
    static {
        PresetManager.presets = new ArrayList<Preset>();
    }
}
