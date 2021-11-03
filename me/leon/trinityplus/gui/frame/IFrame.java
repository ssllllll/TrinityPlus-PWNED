//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.frame;

import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.gui.button.*;
import java.util.*;
import java.awt.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.utils.rendering.*;
import me.leon.trinityplus.utils.misc.*;

public abstract class IFrame implements IComponent
{
    protected float x;
    protected float y;
    protected float lastY;
    protected float dragX;
    protected float dragY;
    protected boolean open;
    protected boolean dragging;
    protected Category category;
    protected ArrayList<IButton> buttons;
    
    protected IFrame(final Category c, final float x, final float y) {
        this.category = c;
        this.x = x;
        this.y = y;
        this.dragging = false;
        this.open = false;
        this.buttons = new ArrayList<IButton>();
    }
    
    @Override
    public float height() {
        float h = 15.0f;
        if (this.open) {
            for (final IButton b : this.buttons) {
                h += b.height();
            }
        }
        return h;
    }
    
    @Override
    public float xOffset() {
        return 0.0f;
    }
    
    @Override
    public IComponent parent() {
        return null;
    }
    
    @Override
    public String description() {
        return null;
    }
    
    protected boolean onButton(final Point point) {
        return GuiUtils.onButton(this.x, this.y, this.x + this.getWidth(), this.y + 15.0f, point);
    }
    
    protected void drawBack() {
        this.drawRect(this.x, this.y, this.x + this.getWidth(), this.y + 14.0f, ClickGUI.frameColor.getValue());
        if (ClickGUI.frameSeparator.is("Normal")) {
            RenderUtils.drawRect(this.x, this.y + 14.0f, this.x + this.getWidth(), this.y + 15.0f, ClickGUI.separatorColor.getValue());
        }
        FontUtil.drawString(this.category.name(), this.x + 5.0f, this.y + (14 - FontUtil.getFontHeight()) / 2.0f, ClickGUI.nameColorButton.getValue());
    }
    
    protected void updateOffset() {
        int offset = 15;
        for (final IButton button : this.buttons) {
            button.setOffset(offset);
            offset += (int)button.height();
        }
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public float getLastY() {
        return this.lastY;
    }
    
    public void setLastY(final float y) {
        this.lastY = y;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public ArrayList<IButton> getButtons() {
        return this.buttons;
    }
    
    public boolean isDragging() {
        return this.dragging;
    }
    
    public IFrame setDragging(final boolean dragging) {
        this.dragging = dragging;
        return this;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public IFrame setOpen(final boolean open) {
        this.open = open;
        return this;
    }
}
