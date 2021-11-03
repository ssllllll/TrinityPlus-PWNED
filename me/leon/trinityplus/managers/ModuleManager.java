//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.managers;

import me.leon.trinityplus.utils.client.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.hacks.combat.*;
import me.leon.trinityplus.hacks.misc.*;
import me.leon.trinityplus.hacks.movement.*;
import me.leon.trinityplus.hacks.render.*;
import me.leon.trinityplus.hacks.exploits.*;
import me.leon.trinityplus.hacks.player.*;
import me.leon.trinityplus.hacks.*;
import java.util.*;
import java.util.stream.*;

public class ModuleManager
{
    public static ArrayList<Module> modules;
    
    public ModuleManager() {
        ModuleManager.modules = new ArrayList<Module>();
        SettingUtil.list = ModuleManager.modules;
        SettingUtil.addMod(new Font());
        SettingUtil.addMod(new HUD());
        SettingUtil.addMod(new HUDeditor());
        SettingUtil.addMod(new ClickGUI());
        SettingUtil.addMod(new ClientColor());
        SettingUtil.addMod(new KillAura());
        SettingUtil.addMod(new AutoCrystal());
        SettingUtil.addMod(new Surround());
        SettingUtil.addMod(new AutoTrap());
        SettingUtil.addMod(new Burrow());
        SettingUtil.addMod(new Offhand());
        SettingUtil.addMod(new HoleFill());
        SettingUtil.addMod(new AutoMend());
        SettingUtil.addMod(new AutoArmor());
        SettingUtil.addMod(new Quiver());
        SettingUtil.addMod(new NoRotate());
        SettingUtil.addMod(new DiscordRPC());
        SettingUtil.addMod(new FreeCam());
        SettingUtil.addMod(new ChatSuffix());
        SettingUtil.addMod(new FakePlayer());
        SettingUtil.addMod(new Timer());
        SettingUtil.addMod(new TotempopCounter());
        SettingUtil.addMod(new MCF());
        SettingUtil.addMod(new MCP());
        SettingUtil.addMod(new AutoKit());
        SettingUtil.addMod(new Velocity());
        SettingUtil.addMod(new AutoSprint());
        SettingUtil.addMod(new AutoWalk());
        SettingUtil.addMod(new IceSpeed());
        SettingUtil.addMod(new NoVoid());
        SettingUtil.addMod(new NoSlow());
        SettingUtil.addMod(new Parkour());
        SettingUtil.addMod(new ReverseStep());
        SettingUtil.addMod(new Step());
        SettingUtil.addMod(new Speed());
        SettingUtil.addMod(new Yaw());
        SettingUtil.addMod(new VClip());
        SettingUtil.addMod(new FullBright());
        SettingUtil.addMod(new FreeLook());
        SettingUtil.addMod(new NoRender());
        SettingUtil.addMod(new HoleESP());
        SettingUtil.addMod(new Chams());
        SettingUtil.addMod(new ESP());
        SettingUtil.addMod(new Tracers());
        SettingUtil.addMod(new PenisESP());
        SettingUtil.addMod(new ViewModel());
        SettingUtil.addMod(new PacketFly());
        SettingUtil.addMod(new Reach());
        SettingUtil.addMod(new PacketLogger());
        SettingUtil.addMod(new ChorusExploit());
        SettingUtil.addMod(new FastUse());
        SettingUtil.addMod(new NoPush());
        SettingUtil.addMod(new SpeedMine());
        SettingUtil.list = null;
    }
    
    public static Module getMod(final String name) {
        return ModuleManager.modules.stream().filter(mod0 -> mod0.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    
    public static Module getMod(final String name, final Category cat) {
        return ModuleManager.modules.stream().filter(mod0 -> mod0.getName().equalsIgnoreCase(name) && mod0.getCategory() == cat).findFirst().orElse(null);
    }
    
    public static Module getMod(final Class<? extends Module> clazz) {
        return ModuleManager.modules.stream().filter(mod0 -> mod0.getClass() == clazz).findFirst().orElse(null);
    }
    
    public static List<Module> getMods(final Category c) {
        return ModuleManager.modules.stream().filter(mod -> mod.getCategory() == c).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
    }
}
