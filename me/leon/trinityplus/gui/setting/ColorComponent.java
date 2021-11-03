//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.setting;

import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.gui.button.*;
import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.utils.misc.*;
import java.awt.*;
import me.leon.trinityplus.utils.rendering.skeet.*;
import me.leon.trinityplus.utils.rendering.*;
import me.leon.trinityplus.utils.math.*;

public class ColorComponent extends ISetting<ColorSetting>
{
    private boolean draggingPicker;
    private boolean draggingHue;
    private boolean draggingAlpha;
    private boolean draggingSpeed;
    private float pickerPosMin;
    private float pickerPosMax;
    private float huePosMin;
    private float huePosMax;
    private float alphaPosMin;
    private float alphaPosMax;
    private float speedPosMin;
    private float speedPosMax;
    private float rainbowMin;
    private float rainbowMax;
    
    public ColorComponent(final IComponent parent, final IButton superParent, final Setting set, final int offset) {
        super(parent, superParent, set, offset);
        this.draggingPicker = false;
        this.draggingHue = false;
        this.draggingAlpha = false;
        this.draggingSpeed = false;
        set.getSubSettings().clear();
        this.subs.clear();
    }
    
    public void render(final Point point) {
        if (!this.open) {
            this.drawBack(point, ((ColorSetting)this.set).getName(), false);
            this.drawRect(this.getFrame().getX() + this.getWidth() - 13.0f, this.getFrame().getY() + this.offset + 2.0f, this.getFrame().getX() + this.getWidth() - 3.0f, this.getFrame().getY() + this.offset + 12.0f, ((ColorSetting)this.set).getValue());
        }
        else {
            float realY = this.getFrame().getY() + this.offset;
            final float realX = this.getFrame().getX();
            final float width = this.getFrame().getWidth();
            this.drawRect(realX, realY, realX + this.getWidth(), realY + 14.0f, this.getColor(point, false));
            this.drawRect(realX, realY + 14.0f, realX + this.getWidth(), realY + 14.0f + width + 13.0f + 13.0f + 14.0f, ClickGUI.backgroundColor.getValue());
            this.drawRect(realX + this.getWidth() - 13.0f, realY + 2.0f, realX + this.getWidth() - 3.0f, realY + 12.0f, ((ColorSetting)this.set).getValue());
            FontUtil.drawString(((ColorSetting)this.set).getName(), realX + this.parent.xOffset() + 3.0f, realY + (14 - FontUtil.getFontHeight()) / 2.0f, ClickGUI.settingNameColor.getValue());
            RenderUtils.drawColorPickerSquare(realX + this.xOffset(), realY + 14.0f, realX + width, realY + 14.0f + (width - this.xOffset()), ((ColorSetting)this.set).hue, ((ColorSetting)this.set).getA());
            RenderUtils.drawOutlineRect(realX + this.xOffset(), realY + 14.0f, realX + this.getWidth(), realY + 14.0f + (this.getWidth() - this.xOffset()), 1.0f, Color.WHITE);
            this.pickerPosMin = realY + 14.0f;
            this.pickerPosMax = realY + 14.0f + (this.getWidth() - this.xOffset());
            RenderUtils.scissor(new Quad(realX + this.xOffset(), realY + 15.0f, realX + this.getWidth(), realY + 14.0f + (this.getWidth() - this.xOffset())));
            RenderUtils.drawCircle(realX + this.xOffset() + ((ColorSetting)this.set).s * (width - this.xOffset()), realY + 14.0f + (1.0f - ((ColorSetting)this.set).br) * (width - this.xOffset()), 3.0f, 1.0f, Color.WHITE);
            RenderUtils.restoreScissor();
            realY += 17.0f + (width - this.xOffset());
            RenderUtils.drawHueRect(realX + this.xOffset(), realY, this.getWidth() - this.xOffset(), 10.0f);
            RenderUtils.drawRect(realX + this.xOffset() + GuiUtils.sliderWidth(0.0f, ((ColorSetting)this.set).hue, 1.0f, this.getWidth() - this.xOffset() - 1.0f), realY, realX + this.xOffset() + 1.0f + GuiUtils.sliderWidth(0.0f, ((ColorSetting)this.set).hue, 1.0f, this.getWidth() - this.xOffset() - 1.0f), realY + 10.0f, Color.BLACK);
            RenderUtils.drawOutlineRect(realX + this.xOffset(), realY, realX + this.getWidth(), realY + 10.0f, 1.0f, Color.WHITE);
            this.huePosMin = realY;
            this.huePosMax = realY + 10.0f;
            realY += 13.0f;
            RenderUtils.scissor(new Quad(realX + this.xOffset() - 1.0f, realY - 1.0f, realX + this.getWidth(), realY + 11.0f));
            for (int xOff = 0; xOff < realX + this.getWidth() + 10.0f; xOff += 5) {
                RenderUtils.drawRect((float)xOff, realY, (float)(xOff + 5), realY + 5.0f, (xOff % 2 == 0) ? new Color(0, 0, 0, 50) : new Color(255, 255, 255, 50));
                RenderUtils.drawRect((float)xOff, realY + 5.0f, (float)(xOff + 5), realY + 10.0f, (xOff % 2 == 0) ? new Color(255, 255, 255, 50) : new Color(0, 0, 0, 50));
            }
            RenderUtils.restoreScissor();
            RenderUtils.drawGradientRect(realX + this.xOffset(), realY, realX + this.getWidth(), realY + 10.0f, new Color(0, 0, 0, 0), RenderUtils.alpha(((ColorSetting)this.set).getValue(), 255), new Color(0, 0, 0, 0), RenderUtils.alpha(((ColorSetting)this.set).getValue(), 255));
            RenderUtils.drawRect(realX + this.xOffset() + GuiUtils.sliderWidth(0.0f, (float)((ColorSetting)this.set).getA(), 255.0f, this.getWidth() - this.xOffset() - 1.0f), realY, realX + this.xOffset() + 1.0f + GuiUtils.sliderWidth(0.0f, (float)((ColorSetting)this.set).getA(), 255.0f, this.getWidth() - this.xOffset() - 1.0f), realY + 10.0f, Color.BLACK);
            RenderUtils.drawOutlineRect(realX + this.xOffset(), realY, realX + this.getWidth(), realY + 10.0f, 1.0f, Color.WHITE);
            this.alphaPosMin = realY;
            this.alphaPosMax = realY + 10.0f;
            realY += 13.0f;
            RenderUtils.drawRect(realX + this.xOffset() + 13.0f, realY, realX + this.xOffset() + 13.0f + GuiUtils.sliderWidth(0.0f, (float)((ColorSetting)this.set).speed, 5.0f, this.getWidth() - this.xOffset()), realY + 10.0f, ClickGUI.sliderColor.getValue());
            FontUtil.drawString("Speed " + ((ColorSetting)this.set).speed, realX + this.xOffset() + 16.0f, realY + (10 - FontUtil.getFontHeight()) / 2.0f, Color.WHITE);
            RenderUtils.drawOutlineRect(realX + this.xOffset() + 13.0f, realY, realX + this.getWidth(), realY + 10.0f, 1.0f, Color.WHITE);
            this.speedPosMin = realY;
            this.speedPosMax = realY + 10.0f;
            if (((ColorSetting)this.set).rainbow) {
                RenderUtils.drawGradientRect(realX + this.xOffset(), realY, realX + this.xOffset() + 10.0f, realY + 10.0f, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN);
            }
            RenderUtils.drawOutlineRect(realX + this.xOffset(), realY, realX + this.xOffset() + 10.0f, realY + 10.0f, 1.0f, Color.WHITE);
            this.rainbowMin = this.speedPosMin;
            this.rainbowMax = this.speedPosMax;
        }
    }
    
    public void update(final Point point) {
        final float realX = this.getFrame().getX();
        final float realY = this.getFrame().getY() + this.offset;
        if (this.draggingSpeed) {
            final float f1 = (float)GuiUtils.slider(0.0, 20.0, point.x, realX + this.xOffset() + 13.0f, this.getWidth() - this.xOffset(), 0);
            ((ColorSetting)this.set).speed = MathUtils.roundToPlace(f1 / 4.0f, 2);
        }
        if (this.draggingPicker) {
            ((ColorSetting)this.set).s = (float)MathUtils.clamp(0.0, 1.0, (point.x - (realX + this.xOffset())) / (this.getWidth() - this.xOffset()));
            ((ColorSetting)this.set).br = (float)(1.0 - MathUtils.clamp(0.0, 1.0, (point.y - (realY + 14.0f)) / (this.getWidth() - this.xOffset())));
        }
        if (this.draggingHue) {
            ((ColorSetting)this.set).hue = (float)GuiUtils.slider(0.0, 360.0, point.x, realX + this.xOffset(), this.getWidth() - this.xOffset(), 2) / 360.0f;
        }
        if (this.draggingAlpha) {
            ((ColorSetting)this.set).setA((int)GuiUtils.slider(0.0, 255.0, point.x, realX + this.xOffset(), this.getWidth() - this.xOffset(), 0));
        }
        final Color color = new Color(Color.HSBtoRGB(((ColorSetting)this.set).hue, ((ColorSetting)this.set).s, ((ColorSetting)this.set).br));
        ((ColorSetting)this.set).setR(color.getRed()).setG(color.getGreen()).setB(color.getBlue());
    }
    
    public boolean buttonClick(final int button, final Point point) {
        if (this.onButton(point)) {
            if (button == 1) {
                this.open = !this.open;
            }
            return true;
        }
        if (this.open) {
            if (this.onSpeed(point) && button == 0) {
                return this.draggingSpeed = true;
            }
            if (this.onRainbow(point) && button == 0) {
                ((ColorSetting)this.set).rainbow = !((ColorSetting)this.set).rainbow;
                return true;
            }
            if (this.onPicker(point) && button == 0) {
                return this.draggingPicker = true;
            }
            if (this.onHue(point) && button == 0) {
                return this.draggingHue = true;
            }
            if (this.onAlpha(point) && button == 0) {
                return this.draggingAlpha = true;
            }
        }
        return false;
    }
    
    public boolean buttonRelease(final int button, final Point point) {
        this.draggingPicker = false;
        this.draggingHue = false;
        this.draggingSpeed = false;
        return this.draggingAlpha = false;
    }
    
    public boolean keyTyped(final char chr, final int code) {
        return false;
    }
    
    @Override
    public float height() {
        if (this.open) {
            return this.getWidth() + 14.0f + 13.0f + 13.0f + 14.0f;
        }
        return 14.0f;
    }
    
    @Override
    public float xOffset() {
        return this.parent.xOffset() + 3.0f;
    }
    
    private boolean onSpeed(final Point p) {
        return GuiUtils.onButton(this.getFrame().getX() + this.xOffset() + 13.0f, this.speedPosMin, this.getFrame().getX() + this.getWidth(), this.speedPosMax, p);
    }
    
    private boolean onRainbow(final Point p) {
        return GuiUtils.onButton(this.getFrame().getX() + this.xOffset(), this.rainbowMin, this.getFrame().getX() + this.xOffset() + 10.0f, this.rainbowMax, p);
    }
    
    private boolean onPicker(final Point p) {
        return GuiUtils.onButton(this.getFrame().getX() + this.xOffset(), this.pickerPosMin, this.getFrame().getX() + this.getWidth(), this.pickerPosMax, p);
    }
    
    private boolean onHue(final Point p) {
        return GuiUtils.onButton(this.getFrame().getX() + this.xOffset(), this.huePosMin, this.getFrame().getX() + this.getWidth(), this.huePosMax, p);
    }
    
    private boolean onAlpha(final Point p) {
        return GuiUtils.onButton(this.getFrame().getX() + this.xOffset(), this.alphaPosMin, this.getFrame().getX() + this.getWidth(), this.alphaPosMax, p);
    }
}
