//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.setting;

import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.gui.button.*;
import me.leon.trinityplus.setting.rewrite.*;
import java.awt.*;
import org.lwjgl.input.*;
import java.util.*;

public class KeybindComponent extends ISetting<KeybindSetting>
{
    private boolean binding;
    
    public KeybindComponent(final IComponent parent, final ButtonComponent superParent, final int offset) {
        super(parent, (IButton)superParent, (Setting)null, offset);
        this.binding = false;
    }
    
    public KeybindComponent(final IComponent parent, final IButton superParent, final Setting set, final int offset) {
        super(parent, superParent, set, offset);
        this.binding = false;
    }
    
    public void render(final Point point) {
        if (this.set != null) {
            this.drawBack(point, this.binding ? ("Binding" + this.getDots()) : ("Bind: " + Keyboard.getKeyName(((KeybindSetting)this.set).getKeycode())), false);
            if (this.open) {
                this.getSets().forEach(e -> e.render(point));
            }
        }
        else {
            this.drawBack(point, this.binding ? ("Binding" + this.getDots()) : ("Bind: " + Keyboard.getKeyName(((ButtonComponent)this.superParent).getMod().getKey())), false);
        }
    }
    
    private String getDots() {
        String x = "";
        if (this.binding) {
            final float progress = (int)(System.currentTimeMillis() % 3000L) / 1000.0f;
            if (progress > 0.0f && progress <= 1.0f) {
                x = ".";
            }
            else if (progress > 1.0f && progress <= 2.0f) {
                x = "..";
            }
            else if (progress > 2.0f && progress <= 3.0f) {
                x = "...";
            }
        }
        return x;
    }
    
    public void update(final Point point) {
        if (this.open) {
            this.getSets().forEach(e -> e.update(point));
        }
    }
    
    public boolean buttonClick(final int button, final Point point) {
        if (this.onButton(point)) {
            switch (button) {
                case 0: {
                    this.binding = !this.binding;
                    return true;
                }
                case 1: {
                    this.open = !this.open;
                    return true;
                }
            }
        }
        if (this.open) {
            for (final IComponent c : this.getSets()) {
                if (c.buttonClick(button, point)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean buttonRelease(final int button, final Point point) {
        if (this.open) {
            for (final ISetting<?> sub : this.getSets()) {
                if (sub.buttonRelease(button, point)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean keyTyped(final char chr, final int code) {
        if (this.set != null) {
            if (this.open) {
                for (final ISetting<?> sub : this.getSets()) {
                    if (sub.keyTyped(chr, code)) {
                        return true;
                    }
                }
            }
        }
        else if (this.binding) {
            ((ButtonComponent)this.superParent).getMod().setKey((code == 211) ? 0 : code);
            this.binding = false;
            return true;
        }
        return false;
    }
}
