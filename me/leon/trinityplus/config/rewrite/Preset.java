//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.config.rewrite;

import java.io.*;
import me.leon.trinityplus.main.*;

public class Preset
{
    public File dir;
    public String name;
    
    public Preset(final String name) {
        this.dir = new File("TrinityPlus/" + name);
        this.name = name;
    }
    
    public File getModsFile() {
        return new File(this.dir.getAbsolutePath() + "/modules/");
    }
    
    public File getEditorDir() {
        return new File(this.dir.getAbsolutePath() + "/hud/editor/");
    }
    
    public File getGuiFile() {
        return new File(this.dir.getAbsolutePath() + "/gui/");
    }
    
    public File getHudFile() {
        return new File(this.dir.getAbsolutePath() + "/hud/");
    }
    
    public void load() {
        SaveConfig.runStatic();
        Trinity.currentPreset = this;
        LoadConfig.load();
    }
}
