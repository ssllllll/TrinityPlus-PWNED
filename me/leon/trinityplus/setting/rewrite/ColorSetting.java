//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.setting.rewrite;

import java.util.function.*;
import java.awt.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.utils.rendering.*;
import org.json.simple.*;
import java.util.*;

public class ColorSetting extends Setting
{
    private int r;
    private int g;
    private int b;
    private int a;
    public double speed;
    public boolean sync;
    public boolean rainbow;
    public float hue;
    public float br;
    public float s;
    
    public ColorSetting(final String name, final int r, final int g, final int b, final int a, final boolean rainbow) {
        this(name, null, r, g, b, a, rainbow);
    }
    
    public ColorSetting(final String name, final Object parent, final int r, final int g, final int b, final int a, final boolean rainbow) {
        this(name, parent, r, g, b, a, rainbow, s -> true);
    }
    
    public ColorSetting(final String name, final int r, final int g, final int b, final int a, final boolean rainbow, final Predicate<Setting> pre) {
        this(name, null, r, g, b, a, rainbow, pre);
    }
    
    public ColorSetting(final String name, final Object parent, final int r, final int g, final int b, final int a, final boolean rainbow, final Predicate<Setting> pre) {
        super(name, parent, pre);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.br = Color.RGBtoHSB(r, g, b, new float[3])[2];
        this.s = Color.RGBtoHSB(r, g, b, new float[3])[1];
        this.hue = Color.RGBtoHSB(r, g, b, new float[3])[0];
        this.speed = 0.5;
        this.sync = false;
        this.rainbow = rainbow;
    }
    
    public int getR() {
        return this.r;
    }
    
    public ColorSetting setR(final int r) {
        this.r = r;
        return this;
    }
    
    public int getG() {
        return this.g;
    }
    
    public ColorSetting setG(final int g) {
        this.g = g;
        return this;
    }
    
    public int getB() {
        return this.b;
    }
    
    public ColorSetting setB(final int b) {
        this.b = b;
        return this;
    }
    
    public int getA() {
        return this.a;
    }
    
    public ColorSetting setA(final int a) {
        this.a = a;
        return this;
    }
    
    public Color getValue() {
        if (this.sync) {
            return ClientColor.color.getValue();
        }
        if (this.rainbow) {
            return Rainbow.getColorStatic(0.0f, (float)this.speed, this.s, this.br, this.a);
        }
        return new Color(this.r, this.g, this.b, this.a);
    }
    
    @Override
    public Object getJsonString() {
        final JSONObject s = new JSONObject();
        if (this.getSubSettings().isEmpty()) {
            this.putValues(s);
        }
        else {
            final JSONObject val = new JSONObject();
            this.putValues(val);
            s.put((Object)"value", (Object)val);
            final JSONObject a = new JSONObject();
            for (final Setting l : this.getSubSettings()) {
                a.put((Object)l.getName(), l.getJsonString());
            }
            s.put((Object)"settings", (Object)a);
        }
        return s;
    }
    
    @Override
    public void parseJson(final JSONObject main, final String key, final Object val) {
        if (val instanceof JSONObject) {
            final JSONObject j = (JSONObject)val;
            final JSONObject sets = (JSONObject)j.get((Object)"settings");
            if (sets != null) {
                this.readValues((JSONObject)j.get((Object)"value"));
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
                this.readValues((JSONObject)val);
            }
        }
    }
    
    private void putValues(final JSONObject j) {
        j.put((Object)"r", (Object)this.r);
        j.put((Object)"g", (Object)this.g);
        j.put((Object)"b", (Object)this.b);
        j.put((Object)"a", (Object)this.a);
        j.put((Object)"s", (Object)this.speed);
        j.put((Object)"sy", (Object)this.sync);
        j.put((Object)"ra", (Object)this.rainbow);
        j.put((Object)"br", (Object)this.br);
        j.put((Object)"sa", (Object)this.s);
        j.put((Object)"hue", (Object)this.hue);
    }
    
    private void readValues(final JSONObject s) {
        this.r = ((Long)s.get((Object)"r")).intValue();
        this.g = ((Long)s.get((Object)"g")).intValue();
        this.b = ((Long)s.get((Object)"b")).intValue();
        this.a = ((Long)s.get((Object)"a")).intValue();
        this.speed = (double)s.get((Object)"s");
        this.sync = (boolean)s.get((Object)"sy");
        this.rainbow = (boolean)s.get((Object)"ra");
        this.br = ((Double)s.get((Object)"br")).floatValue();
        this.s = ((Double)s.get((Object)"sa")).floatValue();
        this.hue = ((Double)s.get((Object)"hue")).floatValue();
    }
}
