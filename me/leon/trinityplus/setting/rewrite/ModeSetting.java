//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.setting.rewrite;

import java.util.function.*;
import org.json.simple.*;
import java.util.*;

public class ModeSetting extends Setting
{
    private ArrayList<String> values;
    private String value;
    
    public ModeSetting(final String name, final String value, final String... values) {
        this(name, (Object)null, value, s -> true, values);
    }
    
    public ModeSetting(final String name, final Object parent, final String value, final String... values) {
        this(name, parent, value, s -> true, values);
    }
    
    public ModeSetting(final String name, final String value, final Predicate<Setting> pre, final String... values) {
        this(name, (Object)null, value, pre, values);
    }
    
    public ModeSetting(final String name, final Object parent, final String value, final Predicate<Setting> pre, final String... values) {
        super(name, parent, pre);
        this.value = value;
        Collections.addAll(this.values = new ArrayList<String>(), values);
    }
    
    public void setValues(final ArrayList<String> values) {
        this.values = values;
    }
    
    public ArrayList<String> getValues() {
        return this.values;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public boolean is(final String... strings) {
        for (final String s : strings) {
            if (this.value.equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean equalsIgnoreCase(final String... strings) {
        for (final String s : strings) {
            if (this.value.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
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
            this.value = (String)j.get((Object)"value");
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
            this.value = (String)val;
        }
    }
}
