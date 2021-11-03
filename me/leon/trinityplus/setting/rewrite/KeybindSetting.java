//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.setting.rewrite;

import java.util.function.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import org.json.simple.*;
import java.util.*;

public class KeybindSetting extends Setting
{
    private int keycode;
    
    public KeybindSetting(final String name, final int keycode) {
        this(name, null, keycode, s -> true);
    }
    
    public KeybindSetting(final String name, final Object parent, final int keycode) {
        this(name, parent, keycode, s -> true);
    }
    
    public KeybindSetting(final String name, final int keycode, final Predicate<Setting> pre) {
        this(name, null, keycode, pre);
    }
    
    public KeybindSetting(final String name, final Object parent, final int keycode, final Predicate<Setting> pre) {
        super(name, parent, pre);
        this.keycode = keycode;
    }
    
    public int getKeycode() {
        return this.keycode;
    }
    
    public void setKeycode(final int keycode) {
        this.keycode = keycode;
    }
    
    public char getChar() {
        return (char)this.keycode;
    }
    
    public boolean down() {
        return this.down(true);
    }
    
    public boolean down(final boolean guis) {
        return (!guis || Minecraft.getMinecraft().currentScreen == null) && Keyboard.isKeyDown(this.keycode);
    }
    
    @Override
    public Object getJsonString() {
        if (this.getSubSettings().isEmpty()) {
            return this.keycode;
        }
        final JSONObject s = new JSONObject();
        s.put((Object)"value", (Object)this.keycode);
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
            this.keycode = ((Long)j.get((Object)"value")).intValue();
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
            this.keycode = ((Long)val).intValue();
        }
    }
}
