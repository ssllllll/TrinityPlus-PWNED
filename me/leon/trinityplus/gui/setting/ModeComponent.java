//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.setting;

import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.gui.button.*;
import me.leon.trinityplus.setting.rewrite.*;
import java.awt.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;

public class ModeComponent extends ISetting<ModeSetting>
{
    public ModeComponent(final IComponent parent, final ButtonComponent superParent, final int offset) {
        super(parent, (IButton)superParent, (Setting)null, offset);
    }
    
    public ModeComponent(final IComponent parent, final IButton superParent, final Setting set, final int offset) {
        super(parent, superParent, set, offset);
    }
    
    public void render(final Point point) {
        if (this.set != null) {
            this.drawBack(point, ((ModeSetting)this.set).getName() + " " + ChatFormatting.WHITE + ((ModeSetting)this.set).getValue(), false);
            this.updateOffset();
            if (this.open) {
                this.getSets().forEach(e -> e.render(point));
            }
        }
        else {
            this.drawBack(point, "Visible: " + (((ButtonComponent)this.superParent).getMod().isVisible() ? "True" : "False"), false);
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
                    return true;
                }
                case 0: {
                    if (this.set != null) {
                        int v = ((ModeSetting)this.set).getValues().indexOf(((ModeSetting)this.set).getValue());
                        if (v == ((ModeSetting)this.set).getValues().size() - 1) {
                            v = 0;
                        }
                        else {
                            ++v;
                        }
                        ((ModeSetting)this.set).setValue(((ModeSetting)this.set).getValues().get(v));
                        return true;
                    }
                    ((ButtonComponent)this.superParent).getMod().setVisible(!((ButtonComponent)this.superParent).getMod().isVisible());
                    break;
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
        if (this.open) {
            for (final ISetting<?> sub : this.getSets()) {
                if (sub.keyTyped(chr, code)) {
                    return true;
                }
            }
        }
        return false;
    }
}
