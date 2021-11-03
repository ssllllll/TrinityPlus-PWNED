//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.setting.rewrite;

import java.util.function.*;
import org.json.simple.*;
import java.util.*;

public class TextBoxSetting extends Setting
{
    private String value;
    public int typeSpace;
    
    public TextBoxSetting(final String name, final String value) {
        this(name, null, value, s -> true);
    }
    
    public TextBoxSetting(final String name, final Object parent, final String value) {
        this(name, parent, value, s -> true);
    }
    
    public TextBoxSetting(final String name, final String value, final Predicate<Setting> pre) {
        this(name, null, value, pre);
    }
    
    public TextBoxSetting(final String name, final Object parent, final String value, final Predicate<Setting> pre) {
        super(name, parent, (Predicate)pre);
        this.value = value;
        this.typeSpace = value.length();
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
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
    
    public void parseJson(final JSONObject main, final String key, final Object val) {
        if (val instanceof JSONObject) {
            final JSONObject j = (JSONObject)val;
            this.value = (String)j.get((Object)"value");
            this.typeSpace = this.value.length();
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
            this.typeSpace = this.value.length();
        }
    }
}
