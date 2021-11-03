//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.setting.rewrite;

import java.util.function.*;
import org.json.simple.*;
import java.util.*;

public class SliderSetting extends Setting
{
    private double value;
    private final double min;
    private final double max;
    private final boolean onlyInt;
    
    public SliderSetting(final String name, final double min, final double value, final double max, final boolean onlyInt) {
        this(name, null, min, value, max, onlyInt, s -> true);
    }
    
    public SliderSetting(final String name, final Object parent, final double min, final double value, final double max, final boolean onlyInt) {
        this(name, parent, min, value, max, onlyInt, s -> true);
    }
    
    public SliderSetting(final String name, final double min, final double value, final double max, final boolean onlyInt, final Predicate<Setting> pre) {
        this(name, null, min, value, max, onlyInt, pre);
    }
    
    public SliderSetting(final String name, final Object parent, final double min, final double value, final double max, final boolean onlyInt, final Predicate<Setting> pre) {
        super(name, parent, (Predicate)pre);
        this.value = value;
        this.min = min;
        this.max = max;
        this.onlyInt = onlyInt;
    }
    
    public double getValue() {
        return this.value;
    }
    
    public float floatValue() {
        return (float)this.value;
    }
    
    public int intValue() {
        return (int)this.value;
    }
    
    public void setValue(final double value) {
        this.value = value;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public boolean isOnlyInt() {
        return this.onlyInt;
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
            if (this.onlyInt) {
                this.value = ((Double)j.get((Object)"value")).intValue();
            }
            else {
                this.value = ((Double)j.get((Object)"value")).floatValue();
            }
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
        else if (this.onlyInt) {
            this.value = ((Double)val).intValue();
        }
        else {
            this.value = ((Double)val).floatValue();
        }
    }
}
