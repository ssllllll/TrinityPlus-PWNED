//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.frame;

import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.gui.button.*;
import java.util.*;
import java.awt.*;
import me.leon.trinityplus.utils.rendering.*;

public class FrameComponent extends IFrame
{
    public FrameComponent(final Category c, final float x, final float y) {
        super(c, x, y);
        int offset = 15;
        for (final Module mod : ModuleManager.getMods(this.category)) {
            this.buttons.add((IButton)new ButtonComponent(this, mod, offset));
            offset += 14;
        }
    }
    
    @Override
    public void render(final Point point) {
        this.drawBack();
        if (this.open) {
            this.updateOffset();
            this.buttons.forEach(e -> e.render(point));
        }
    }
    
    @Override
    public void update(final Point point) {
        if (this.dragging) {
            this.x = point.x - this.dragX;
            this.y = point.y - this.dragY;
        }
        if (this.open) {
            this.buttons.forEach(e -> e.update(point));
        }
    }
    
    @Override
    public void unload() {
        this.dragging = false;
    }
    
    @Override
    public boolean buttonClick(final int button, final Point point) {
        if (GuiUtils.onButton(this.x, this.y, this.x + this.getWidth(), this.y + 15.0f, point)) {
            switch (button) {
                case 0: {
                    this.dragging = true;
                    this.dragX = point.x - this.x;
                    this.dragY = point.y - this.y;
                    return true;
                }
                case 1: {
                    this.open = !this.open;
                    return true;
                }
            }
        }
        this.updateOffset();
        if (this.open) {
            for (final IButton c : this.buttons) {
                if (c.buttonClick(button, point)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean buttonRelease(final int button, final Point point) {
        this.dragging = false;
        if (this.open) {
            for (final IButton c : this.buttons) {
                if (c.buttonRelease(button, point)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean keyTyped(final char chr, final int code) {
        if (this.open) {
            for (final IButton c : this.buttons) {
                if (c.keyTyped(chr, code)) {
                    return true;
                }
            }
        }
        return false;
    }
}
