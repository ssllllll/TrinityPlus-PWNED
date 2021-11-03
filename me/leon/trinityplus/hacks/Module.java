//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks;

import me.zero.alpine.fork.listener.*;
import net.minecraft.client.*;
import java.util.*;
import me.leon.trinityplus.setting.rewrite.*;
import net.minecraftforge.common.*;
import me.leon.trinityplus.main.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.leon.trinityplus.utils.misc.*;

public abstract class Module implements Listenable
{
    protected static volatile Minecraft mc;
    private final ArrayList<Setting> settings;
    private boolean visible;
    private String name;
    private String description;
    private Category cat;
    private int key;
    private boolean enabled;
    
    public Module(final String name, final String description, final Category cat) {
        this.name = name;
        this.description = description;
        this.cat = cat;
        this.enabled = false;
        this.visible = true;
        this.settings = new ArrayList<Setting>();
    }
    
    public Module(final String name, final String description, final Category cat, final boolean visible) {
        this.name = name;
        this.description = description;
        this.cat = cat;
        this.enabled = false;
        this.visible = visible;
        this.settings = new ArrayList<Setting>();
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void onUpdate() {
    }
    
    public void onEnable0() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        Trinity.dispatcher.subscribe(this);
    }
    
    public void onDisable0() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        Trinity.dispatcher.unsubscribe(this);
    }
    
    @SubscribeEvent
    public void onUpdate0(final TickEvent.ClientTickEvent event) {
        this.onUpdate();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public Category getCategory() {
        return this.cat;
    }
    
    public void setCategory(final Category cat) {
        this.cat = cat;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        if (enabled != this.enabled) {
            this.enabled = enabled;
            if (this.enabled) {
                this.onEnable0();
                this.onEnable();
            }
            else {
                this.onDisable0();
                this.onDisable();
            }
        }
        else if (this.enabled) {
            this.onEnable0();
        }
        else {
            this.onDisable0();
        }
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public Module setVisible(final boolean visible) {
        this.visible = visible;
        return this;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }
    
    public boolean shouldSave() {
        return true;
    }
    
    public String getHudInfo() {
        return null;
    }
    
    public void addSetting(final Setting set) {
        this.settings.add(set);
    }
    
    public ArrayList<Setting> getSettings() {
        return this.settings;
    }
    
    public Setting getSetting(final String set) {
        return this.settings.stream().filter(e -> e.getName().equals(set)).findFirst().get();
    }
    
    protected boolean pCheck() {
        return Module.mc.player == null;
    }
    
    protected boolean wCheck() {
        return Module.mc.world == null;
    }
    
    protected boolean nullCheck() {
        return Module.mc.world == null || Module.mc.player == null;
    }
    
    protected void toggleWithMessage(final String message) {
        this.setEnabled(false);
        MessageUtil.sendClientMessage(message, true);
    }
    
    static {
        Module.mc = Minecraft.getMinecraft();
    }
}
