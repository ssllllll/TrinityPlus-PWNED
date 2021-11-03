//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.setting;

import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.gui.button.*;
import me.leon.trinityplus.setting.rewrite.*;
import java.util.*;
import java.awt.*;
import me.leon.trinityplus.hacks.client.*;

public class BooleanComponent extends ISetting<BooleanSetting>
{
    public BooleanComponent(final IComponent parent, final IButton superParent, final Setting set, final int offset) {
        super(parent, superParent, set, offset);
    }
    
    public void render(final Point point) {
        this.drawBack(point, ((BooleanSetting)this.set).getName(), ((BooleanSetting)this.set).getValue());
        if (!this.subs.isEmpty()) {
            this.drawArrow();
        }
        this.updateOffset();
        if (this.open) {
            this.getSets().forEach(e -> e.render(point));
        }
    }
    
    public void update(final Point point) {
        if (this.open) {
            this.getSets().forEach(e -> e.update(point));
        }
    }
    
    public boolean buttonClick(final int button, final Point point) {
        if (this.onButton(point)) {
            switch (button) {
                case 1: {
                    this.open = !this.open;
                    this.aniEnd = System.currentTimeMillis() + 500L;
                    return true;
                }
                case 0: {
                    ((BooleanSetting)this.set).setValue(!((BooleanSetting)this.set).getValue());
                    return true;
                }
            }
        }
        if (this.open) {
            for (final ISetting<?> sub : this.getSets()) {
                if (sub.buttonClick(button, point)) {
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
        if (this.open) {
            for (final ISetting<?> sub : this.getSets()) {
                if (sub.keyTyped(chr, code)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    protected Color getColor(final Point point, final boolean enabled) {
        if (this.onButton(point)) {
            if (enabled) {
                return ClickGUI.enabledBooleanColor.getValue().brighter();
            }
            return ClickGUI.disabledBooleanColor.getValue().brighter();
        }
        else {
            if (enabled) {
                return ClickGUI.enabledBooleanColor.getValue();
            }
            return ClickGUI.disabledBooleanColor.getValue();
        }
    }
}
