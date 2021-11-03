//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.button;

import me.leon.trinityplus.config.rewrite.*;
import me.leon.trinityplus.gui.setting.*;
import me.leon.trinityplus.gui.frame.*;
import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.setting.rewrite.*;
import java.awt.*;
import me.leon.trinityplus.main.*;

public class PresetButton extends IButton
{
    private final Preset preset;
    private final TextBoxComponent name;
    private final BooleanComponent delete;
    
    public PresetButton(final FrameComponent parent, final Preset preset, final int offset) {
        super(parent, offset);
        this.preset = preset;
        this.name = new TextBoxComponent(parent, this, new TextBoxSetting("Name", "trollage"), offset + 14);
        this.delete = new BooleanComponent(parent, this, new BooleanSetting("Delete", false), offset + 14);
    }
    
    public void render(final Point point) {
        this.drawBack(point, this.preset.name, Trinity.currentPreset == this.preset);
    }
    
    public void update(final Point point) {
        if (!this.name.isTyping()) {
            this.preset.name = this.name.getSet().getValue();
        }
    }
    
    public void unload() {
    }
    
    public boolean buttonClick(final int button, final Point point) {
        if (this.onButton(point) && button == 0) {
            this.preset.load();
        }
        return true;
    }
    
    public boolean buttonRelease(final int button, final Point point) {
        return false;
    }
    
    public boolean keyTyped(final char chr, final int code) {
        return !this.open && this.name.keyTyped(chr, code);
    }
    
    public float height() {
        return this.open ? (28.0f + this.name.height()) : 14.0f;
    }
    
    public String description() {
        return null;
    }
}
