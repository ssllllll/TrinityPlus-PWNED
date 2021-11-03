//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.config.rewrite;

import me.leon.trinityplus.main.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.events.settings.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.utils.misc.*;
import org.json.simple.*;
import me.leon.trinityplus.setting.rewrite.*;
import java.util.*;
import java.io.*;
import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.gui.frame.*;
import me.leon.trinityplus.hud.*;

public class SaveConfig extends Thread
{
    public static final File mainDir;
    
    public static void runStatic() {
        try {
            Trinity.settingsDispatcher.post(new EventSaveConfig(EventStage.PRE, Trinity.currentPreset));
            savePreset();
            saveModules();
            saveHUD();
            saveGUI();
            saveHudEditor();
            Trinity.settingsDispatcher.post(new EventSaveConfig(EventStage.POST, Trinity.currentPreset));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveHudEditor() {
    }
    
    public static void saveModules() {
        for (final Module mod : ModuleManager.modules) {
            try {
                final PrintWriter writer = FileUtils.writer(Trinity.currentPreset.getModsFile(), mod.getName() + ".json");
                final JSONObject object = new JSONObject();
                object.put((Object)"enabled", (Object)mod.isEnabled());
                object.put((Object)"visible", (Object)mod.isVisible());
                object.put((Object)"bind", (Object)mod.getKey());
                final JSONObject settings = new JSONObject();
                for (final Setting set : mod.getSettings()) {
                    settings.put((Object)set.getName(), set.getJsonString());
                }
                object.put((Object)"settings", (Object)settings);
                writer.write(FileUtils.prettyPrint(object.toString()));
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void saveGUI() {
        for (final IFrame c : ClickGui.getFrames()) {
            try {
                final PrintWriter writer = FileUtils.writer(Trinity.currentPreset.getGuiFile(), c.getCategory().name() + ".json");
                final JSONObject object = new JSONObject();
                object.put((Object)"x", (Object)c.getX());
                object.put((Object)"y", (Object)c.getY());
                object.put((Object)"open", (Object)c.isOpen());
                writer.write(FileUtils.prettyPrint(object.toJSONString()));
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void saveHUD() {
        for (final Component c : Trinity.hudManager.comps) {
            try {
                final PrintWriter writer = FileUtils.writer(Trinity.currentPreset.getHudFile(), c.name + ".json");
                final JSONObject object = new JSONObject();
                final JSONObject settings = new JSONObject();
                object.put((Object)"x", (Object)c.x);
                object.put((Object)"y", (Object)c.y);
                object.put((Object)"anchor", (Object)((c.anchorPoint == null) ? null : c.anchorPoint.name()));
                object.put((Object)"visible", (Object)c.visible);
                for (final Setting set : c.getSettings()) {
                    settings.put((Object)set.getName(), set.getJsonString());
                }
                object.put((Object)"settings", (Object)settings);
                writer.write(FileUtils.prettyPrint(object.toJSONString()));
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void savePreset() {
        try {
            final PrintWriter writer = FileUtils.writer("TrinityPlus", "Main.txt");
            writer.write(Trinity.currentPreset.name + "\n");
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        runStatic();
    }
    
    static {
        mainDir = new File("TrinityPlus");
    }
}
