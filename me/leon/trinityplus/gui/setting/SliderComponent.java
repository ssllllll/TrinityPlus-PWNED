//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.setting;

import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.gui.button.*;
import me.leon.trinityplus.setting.rewrite.*;
import java.awt.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.utils.misc.*;
import me.leon.trinityplus.utils.rendering.*;
import java.util.*;

public class SliderComponent extends ISetting<SliderSetting>
{
    private boolean dragging;
    
    public SliderComponent(final IComponent parent, final IButton superParent, final Setting set, final int offset) {
        super(parent, superParent, set, offset);
        this.dragging = false;
    }
    
    public void render(final Point point) {
        final float realY = this.superParent.parent().getY() + this.offset;
        final float realX = this.superParent.parent().getX();
        this.drawRect(realX, realY, realX + this.getWidth(), realY + 14.0f, this.getColor(point, false));
        final float width = (float)(this.xOffset() + (((SliderSetting)this.set).getValue() - ((SliderSetting)this.set).getMin()) / (((SliderSetting)this.set).getMax() - ((SliderSetting)this.set).getMin()) * (this.getWidth() - this.xOffset()));
        this.drawRect(this.getFrame().getX() + this.xOffset(), this.getFrame().getY() + this.offset, this.getFrame().getX() + width, this.getFrame().getY() + this.offset + 14.0f, ClickGUI.sliderColor.getValue());
        FontUtil.drawString(((SliderSetting)this.set).getName() + " " + ((SliderSetting)this.set).getValue(), realX + this.xOffset(), realY + (14 - FontUtil.getFontHeight()) / 2.0f, ClickGUI.settingNameColor.getValue());
        if (this.open) {
            this.getSets().forEach(e -> e.render(point));
        }
    }
    
    public void update(final Point point) {
        if (this.dragging) {
            if (point.x == this.getFrame().getX() + this.getWidth()) {
                ((SliderSetting)this.set).setValue(((SliderSetting)this.set).getMax());
            }
            else if (point.x == this.getFrame().getX() + this.xOffset()) {
                ((SliderSetting)this.set).setValue(((SliderSetting)this.set).getMin());
            }
            else {
                GuiUtils.slider((SliderSetting)this.set, point.x, this.getFrame().getX() + this.xOffset(), this.getWidth() - this.xOffset());
            }
        }
        this.getSets().forEach(e -> e.update(point));
    }
    
    public boolean buttonClick(final int button, final Point point) {
        if (this.onButton(point)) {
            return this.dragging = true;
        }
        for (final ISetting<?> sub : this.getSets()) {
            if (sub.buttonClick(button, point)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean buttonRelease(final int button, final Point point) {
        this.dragging = false;
        for (final ISetting<?> sub : this.getSets()) {
            if (sub.buttonRelease(button, point)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean keyTyped(final char chr, final int code) {
        for (final ISetting<?> sub : this.getSets()) {
            if (sub.keyTyped(chr, code)) {
                return true;
            }
        }
        return false;
    }
}
