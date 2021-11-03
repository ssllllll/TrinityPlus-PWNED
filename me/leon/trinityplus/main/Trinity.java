//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.main;

import net.minecraftforge.fml.common.*;
import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.managers.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import org.apache.logging.log4j.*;
import net.minecraft.launchwrapper.*;
import me.zero.alpine.fork.bus.*;
import me.leon.trinityplus.config.rewrite.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.hacks.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.zero.alpine.fork.listener.*;

@Mod(modid = "trinity+", name = "Trinity+", version = "0.1", acceptedMinecraftVersions = "[1.12.2]")
public class Trinity
{
    public static final String MODID = "trinity+";
    public static final String CLIENTNAME = "Trinity+";
    public static final String VERSION = "0.1";
    public static Logger LOGGER;
    public static EventBus dispatcher;
    public static EventBus settingsDispatcher;
    public static ClickGui clickGui;
    public static CapeManager capeManager;
    public static ModuleManager moduleManager;
    public static PresetManager presetManager;
    public static FriendManager friendManager;
    public static SpoofingManager spoofingManager;
    public static TickrateManager tickrateManager;
    public static HudManager hudManager;
    public static Preset currentPreset;
    public static NotificationManager notificationManager;
    public static TotempopManager totempopManager;
    private static boolean obfEnv;
    public static boolean finishLoading;
    @Mod.Instance
    private static Trinity INSTANCE;
    
    public Trinity() {
        Trinity.INSTANCE = this;
    }
    
    public static boolean isObfEnv() {
        return Trinity.obfEnv;
    }
    
    @Mod.EventHandler
    public void init(final FMLPostInitializationEvent event) {
        Trinity.finishLoading = true;
    }
    
    @Mod.EventHandler
    public void init(final FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)this);
        Trinity.LOGGER = LogManager.getLogger("Trinity+");
        Trinity.obfEnv = Launch.blackboard.get("fml.deobfuscatedEnvironment");
        Trinity.LOGGER.info("Trinity+ Pre Initialization: ----------------------");
        Trinity.dispatcher = new EventManager();
        Trinity.settingsDispatcher = new EventManager();
        Trinity.moduleManager = new ModuleManager();
        Trinity.capeManager = new CapeManager();
        Trinity.friendManager = new FriendManager();
        Trinity.spoofingManager = new SpoofingManager();
        Trinity.tickrateManager = new TickrateManager();
        Trinity.hudManager = new HudManager();
        Trinity.presetManager = new PresetManager();
        Trinity.notificationManager = new NotificationManager();
        Trinity.totempopManager = new TotempopManager();
        LoadConfig.loadPreset();
        LoadConfig.loadModules();
        LoadConfig.loadHUD();
        Trinity.clickGui = new ClickGui();
        LoadConfig.loadGUI();
        Runtime.getRuntime().addShutdownHook((Thread)new SaveConfig());
        Runtime.getRuntime().addShutdownHook(new Thread(RPCHandler::shutdown));
        if (ModuleManager.getMod((Class<? extends Module>)ClickGUI.class).getKey() == 0) {
            ModuleManager.getMod((Class<? extends Module>)ClickGUI.class).setKey(54);
        }
        ModuleManager.modules.forEach(m -> Trinity.settingsDispatcher.subscribe(m));
        Trinity.LOGGER.info("Trinity+ Post Initialization: ----------------------");
    }
    
    @SubscribeEvent
    public void toggleKeys(final InputEvent.KeyInputEvent event) {
        if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null && Keyboard.isCreated() && Keyboard.getEventKeyState() && Keyboard.getEventKey() != 0) {
            for (final Module mod : ModuleManager.modules) {
                if (mod.getKey() == Keyboard.getEventKey()) {
                    mod.toggle();
                }
            }
        }
    }
    
    static {
        Trinity.finishLoading = false;
    }
}
