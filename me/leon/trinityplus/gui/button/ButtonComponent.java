//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.button;

import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.gui.frame.*;
import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.gui.setting.*;
import java.util.*;
import java.awt.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.utils.rendering.*;
import me.leon.trinityplus.utils.misc.*;

public class ButtonComponent extends IButton
{
    private final ArrayList<ISetting<?>> settings;
    private final Module mod;
    private long aniEnd;
    private float p1;
    private float p2;
    
    public ButtonComponent(final FrameComponent parent, final Module mod, final int offset) {
        super(parent, offset);
        this.mod = mod;
        this.settings = new ArrayList<ISetting<?>>();
        int s_off = offset + 14;
        for (final Setting s : mod.getSettings()) {
            if (s instanceof ColorSetting) {
                this.settings.add(new ColorComponent(this, this, s, s_off));
            }
            if (s instanceof BooleanSetting) {
                this.settings.add(new BooleanComponent(this, this, s, s_off));
            }
            if (s instanceof SliderSetting) {
                this.settings.add(new SliderComponent(this, this, s, s_off));
            }
            if (s instanceof KeybindSetting) {
                this.settings.add(new KeybindComponent(this, this, s, s_off));
            }
            if (s instanceof ModeSetting) {
                this.settings.add(new ModeComponent(this, this, s, s_off));
            }
            if (s instanceof TextBoxSetting) {
                this.settings.add(new TextBoxComponent(this, this, s, s_off));
            }
            s_off += 14;
        }
        this.settings.add(new KeybindComponent(this, this, s_off));
        s_off += 14;
        this.settings.add(new ModeComponent(this, this, s_off));
    }
    
    @Override
    public void render(final Point point) {
        this.drawBack(point, this.mod.getName(), this.mod.isEnabled());
        final float progress = 1.0f - (this.aniEnd - System.currentTimeMillis()) / 500.0f;
        final float y = this.getParent().getY() + this.offset;
        final float x = this.getParent().getX() + this.getWidth();
        if (progress < 1.0f) {
            if (this.open) {
                this.p1 = y + 9.0f - progress * 4.0f;
                this.p2 = y + 5.0f + progress * 4.0f;
            }
            else {
                this.p1 = y + 5.0f + progress * 4.0f;
                this.p2 = y + 9.0f - progress * 4.0f;
            }
        }
        else if (this.open) {
            this.p1 = y + 5.0f;
            this.p2 = y + 9.0f;
        }
        else {
            this.p1 = y + 9.0f;
            this.p2 = y + 5.0f;
        }
        final String value = ClickGUI.mode.getValue();
        switch (value) {
            case "Arrow": {
                RenderUtils.drawLine(x - 7.0f, this.p1, x - 5.0f, this.p2, 1.0f, ClickGUI.gearColor.getValue());
                RenderUtils.drawLine(x - 5.0f, this.p2, x - 3.0f, this.p1, 1.0f, ClickGUI.gearColor.getValue());
                break;
            }
            case "...": {
                FontUtil.drawString("...", this.parent.getX() + this.parent.getWidth() - 11.0f, this.parent.getY() + this.offset + 2.0f, ClickGUI.gearColor.getValue());
                break;
            }
        }
        this.updateOffset();
        if (this.open) {
            this.getSets().forEach(e -> e.render(point));
        }
    }
    
    @Override
    public void update(final Point point) {
        if (this.open) {
            this.settings.forEach(e -> e.update(point));
        }
    }
    
    @Override
    public void unload() {
        this.settings.forEach(ISetting::unload);
    }
    
    @Override
    public boolean buttonClick(final int button, final Point point) {
        if (this.onButton(point)) {
            switch (button) {
                case 0: {
                    this.mod.toggle();
                    break;
                }
                case 1: {
                    this.open = !this.open;
                    this.aniEnd = System.currentTimeMillis() + 500L;
                    break;
                }
            }
            return true;
        }
        if (this.open) {
            for (final ISetting<?> set : this.getSets()) {
                if (set.buttonClick(button, point)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean buttonRelease(final int button, final Point point) {
        if (this.open) {
            for (final ISetting<?> set : this.getSets()) {
                if (set.buttonRelease(button, point)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean keyTyped(final char chr, final int code) {
        if (this.open) {
            for (final ISetting<?> set : this.getSets()) {
                if (set.keyTyped(chr, code)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public float height() {
        if (this.open) {
            int h = 14;
            for (final ISetting<?> s : this.getSets()) {
                h += (int)s.height();
            }
            return (float)h;
        }
        return 14.0f;
    }
    
    @Override
    public String description() {
        return this.mod.getDescription();
    }
    
    private void updateOffset() {
        int offset = this.getOffset() + 14;
        for (final ISetting<?> s : this.getSets()) {
            s.setOffset(offset);
            offset += (int)s.height();
        }
    }
    
    public ArrayList<ISetting<?>> getSettings() {
        return this.settings;
    }
    
    public Module getMod() {
        return this.mod;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    private ArrayList<ISetting<?>> getSets() {
        final ArrayList<ISetting<?>> toReturn = new ArrayList<ISetting<?>>();
        for (final ISetting<?> s : this.settings) {
            if (s.getSet() == null) {
                toReturn.add(s);
            }
            else {
                if (!((Setting)s.getSet()).test()) {
                    continue;
                }
                toReturn.add(s);
            }
        }
        return toReturn;
    }
}
