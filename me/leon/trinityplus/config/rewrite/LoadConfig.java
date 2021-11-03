//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.config.rewrite;

import me.leon.trinityplus.main.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.events.settings.*;
import me.leon.trinityplus.utils.misc.*;
import me.leon.trinityplus.managers.*;
import org.json.simple.parser.*;
import org.json.simple.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.utils.rendering.*;
import me.leon.trinityplus.gui.frame.*;
import me.leon.trinityplus.hud.*;
import java.util.*;
import java.io.*;

public class LoadConfig
{
    public static void load() {
        try {
            Trinity.settingsDispatcher.post(new EventLoadConfig(EventStage.PRE, Trinity.currentPreset));
            loadHUD();
            loadGUI();
            loadModules();
            loadHudEditor();
            Trinity.settingsDispatcher.post(new EventLoadConfig(EventStage.POST, Trinity.currentPreset));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void loadHudEditor() {
    }
    
    public static void loadModules() {
        FileUtils.makeIfDoesntExist(Trinity.currentPreset.getModsFile());
        for (final File file : Trinity.currentPreset.getModsFile().listFiles()) {
            try {
                final Module mod = ModuleManager.getMod(file.getName().replace(".json", ""));
                final JSONParser p = new JSONParser();
                final FileReader f = new FileReader(file);
                final JSONObject main = (JSONObject)p.parse((Reader)f);
                mod.setEnabled((boolean)main.get((Object)"enabled"));
                mod.setVisible((boolean)main.get((Object)"visible"));
                mod.setKey(((Long)main.get((Object)"bind")).intValue());
                final JSONObject settings = (JSONObject)main.get((Object)"settings");
                for (final String key : settings.keySet()) {
                    final Setting s = mod.getSetting(key);
                    final Object val = settings.get((Object)key);
                    s.parseJson(main, key, val);
                }
                f.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void loadGUI() {
        FileUtils.makeIfDoesntExist(Trinity.currentPreset.getGuiFile());
        for (final File file : Trinity.currentPreset.getGuiFile().listFiles()) {
            try {
                final IFrame c = GuiUtils.findFrame(file.getName().replaceAll(".json", ""));
                final JSONParser parser = new JSONParser();
                final FileReader reader = new FileReader(file);
                final JSONObject obj = (JSONObject)parser.parse((Reader)reader);
                reader.close();
                assert c != null;
                c.setX(((Double)obj.get((Object)"x")).floatValue());
                c.setY(((Double)obj.get((Object)"y")).floatValue());
                c.setOpen((boolean)obj.get((Object)"open"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void loadHUD() {
        FileUtils.makeIfDoesntExist(Trinity.currentPreset.getHudFile());
        for (final File file : Trinity.currentPreset.getHudFile().listFiles()) {
            try {
                if (!file.isDirectory()) {
                    final Component c = Trinity.hudManager.getComponentByName(file.getName().replaceAll(".json", ""));
                    final JSONParser parser = new JSONParser();
                    final FileReader reader = new FileReader(file);
                    final JSONObject obj = (JSONObject)parser.parse((Reader)reader);
                    reader.close();
                    c.x = ((Double)obj.get((Object)"x")).floatValue();
                    c.y = ((Double)obj.get((Object)"y")).floatValue();
                    c.visible = (boolean)obj.get((Object)"visible");
                    final String anchor = (String)obj.get((Object)"anchor");
                    c.anchorPoint = ((anchor == null) ? null : AnchorPoint.valueOf(anchor));
                    final JSONObject jsonObject = (JSONObject)obj.get((Object)"settings");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void loadPreset() {
        FileUtils.makeIfDoesntExist("TrinityPlus/");
        final File main = new File("TrinityPlus/Main.txt");
        if (!main.exists()) {
            final PrintWriter writer = FileUtils.writer("TrinityPlus", "Main.txt");
            writer.write("Default");
            writer.close();
        }
        try {
            final Scanner scanner = new Scanner(new File("TrinityPlus/Main.txt"));
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                final Preset obj = PresetManager.find(line);
                if (obj == null) {
                    final Preset obj2 = Trinity.currentPreset = new Preset("Default");
                    PresetManager.presets.add(obj2);
                    SaveConfig.runStatic();
                }
                else {
                    Trinity.currentPreset = obj;
                }
            }
            scanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
