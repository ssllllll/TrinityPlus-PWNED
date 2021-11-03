//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.setting.rewrite;

import java.util.*;
import java.util.function.*;
import org.json.simple.*;

public abstract class Setting
{
    private Object parent;
    private Object superParent;
    private final String name;
    private final ArrayList<Setting> settings;
    protected final Predicate<Setting> DEFAULT_PREDICATE;
    protected final Predicate<Setting> predicate;
    
    public abstract Object getJsonString();
    
    public abstract void parseJson(final JSONObject p0, final String p1, final Object p2);
    
    public Setting(final String name) {
        this.DEFAULT_PREDICATE = (s -> true);
        this.name = name;
        this.parent = null;
        this.settings = new ArrayList<Setting>();
        this.predicate = this.DEFAULT_PREDICATE;
    }
    
    public Setting(final String name, final Object parent) {
        this.DEFAULT_PREDICATE = (s -> true);
        this.name = name;
        this.parent = parent;
        this.settings = new ArrayList<Setting>();
        this.predicate = this.DEFAULT_PREDICATE;
    }
    
    public Setting(final String name, final Object parent, final Predicate<Setting> predicate) {
        this.DEFAULT_PREDICATE = (s -> true);
        this.name = name;
        this.parent = parent;
        this.settings = new ArrayList<Setting>();
        this.predicate = predicate;
    }
    
    public Object getParent() {
        return this.parent;
    }
    
    public void setParent(final Object parent) {
        this.parent = parent;
    }
    
    public Object getSuperParent() {
        return this.superParent;
    }
    
    public void setSuperParent(final Object superParent) {
        this.superParent = superParent;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ArrayList<Setting> getSubSettings() {
        return this.settings;
    }
    
    public void addSetting(final Setting setting) {
        this.settings.add(setting);
    }
    
    public Predicate<Setting> getPredicate() {
        return this.predicate;
    }
    
    public boolean test() {
        return this.predicate.test(this);
    }
}
