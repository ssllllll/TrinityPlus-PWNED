//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.setting.rewrite;

import java.util.function.*;
import org.json.simple.*;
import java.util.*;

public class BooleanSetting extends Setting
{
    private final boolean canEnable;
    private boolean value;
    
    public BooleanSetting(final String name, final boolean value, final boolean canEnable) {
        this(name, null, value, canEnable);
    }
    
    public BooleanSetting(final String name, final boolean value) {
        this(name, null, value);
    }
    
    public BooleanSetting(final String name, final Object parent, final boolean value) {
        this(name, parent, value, true);
    }
    
    public BooleanSetting(final String name, final Object parent, final boolean value, final boolean canEnable) {
        super(name, parent);
        this.value = value;
        this.canEnable = canEnable;
    }
    
    public BooleanSetting(final String name, final boolean value, final Predicate<Setting> pre) {
        this(name, null, value, pre);
    }
    
    public BooleanSetting(final String name, final boolean value, final boolean canEnable, final Predicate<Setting> pre) {
        this(name, null, value, canEnable, pre);
    }
    
    public BooleanSetting(final String name, final Object parent, final boolean value, final Predicate<Setting> pre) {
        this(name, parent, value, true, pre);
    }
    
    public BooleanSetting(final String name, final Object parent, final boolean value, final boolean canEnable, final Predicate<Setting> pre) {
        super(name, parent, pre);
        this.value = value;
        this.canEnable = canEnable;
    }
    
    public boolean getValue() {
        return this.value;
    }
    
    public void setValue(final boolean value) {
        if (this.canEnable) {
            this.value = value;
        }
    }
    
    @Override
    public Object getJsonString() {
        if (this.getSubSettings().isEmpty()) {
            return this.value;
        }
        final JSONObject s = new JSONObject();
        s.put((Object)"value", (Object)this.value);
        final JSONObject a = new JSONObject();
        for (final Setting l : this.getSubSettings()) {
            a.put((Object)l.getName(), l.getJsonString());
        }
        s.put((Object)"settings", (Object)a);
        return s;
    }
    
    @Override
    public void parseJson(final JSONObject main, final String key, final Object val) {
        if (val instanceof JSONObject) {
            final JSONObject j = (JSONObject)val;
            this.value = (boolean)j.get((Object)"value");
            final JSONObject sets = (JSONObject)j.get((Object)"settings");
            for (final String k : sets.keySet()) {
                final Object v = sets.get((Object)k);
                final String s;
                final Object o;
                this.getSubSettings().forEach(e -> {
                    if (e.getName().equals(s)) {
                        e.parseJson(main, s, o);
                    }
                });
            }
        }
        else {
            this.value = (boolean)val;
        }
    }
}
