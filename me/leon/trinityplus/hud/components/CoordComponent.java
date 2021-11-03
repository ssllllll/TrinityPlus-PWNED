//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hud.components;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hud.*;
import java.math.*;
import com.mojang.realmsclient.gui.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.utils.misc.*;

public class CoordComponent extends Component
{
    public static BooleanSetting nether;
    public static BooleanSetting simple;
    private String coords;
    
    public CoordComponent() {
        super("Coords");
        this.visible = true;
        this.x = 1.0f;
        this.y = this.res.getScaledHeight() - this.height();
        this.anchorPoint = AnchorPoint.BOTTOMLEFT;
    }
    
    private static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public void render() {
        this.drawBackground();
        final int x = (int)this.mc.player.posX;
        final int y = (int)this.mc.player.posY;
        final int z = (int)this.mc.player.posZ;
        final String[] a = String.valueOf(roundToPlace(this.getNCoord(x), 1)).split("\\.");
        final String nx = a[0] + "." + a[1].charAt(0);
        final String[] b = String.valueOf(roundToPlace(this.getNCoord(z), 1)).split("\\.");
        final String nz = b[0] + "." + b[1].charAt(0);
        if (!CoordComponent.nether.getValue()) {
            this.coords = (CoordComponent.simple.getValue() ? String.format("XYZ: %d, %d, %d", x, y, z) : String.format("X: %d | Y: %d | Z: %d", x, y, z));
        }
        else {
            this.coords = (CoordComponent.simple.getValue() ? String.format("XYZ: %d, %d, %d   " + ChatFormatting.GRAY + "[" + ChatFormatting.RESET + " XZ: %s, %s" + ChatFormatting.GRAY + " ]" + ChatFormatting.RESET, x, y, z, nx, nz) : String.format("X: %d | Y: %d | Z: %d   " + ChatFormatting.GRAY + "[" + ChatFormatting.RESET + " X: %s | Z: %s" + ChatFormatting.GRAY + " ]" + ChatFormatting.RESET, x, y, z, nx, nz));
        }
        FontUtil.drawString(this.coords, this.x + 1.0f, this.y, HUDeditor.textColor.getValue().getRGB());
    }
    
    public float width() {
        if (this.coords == null) {
            return 0.0f;
        }
        return FontUtil.getStringWidth(this.coords) + 1.0f;
    }
    
    public float height() {
        return (float)FontUtil.getFontHeight();
    }
    
    private float getNCoord(final int x) {
        if (this.mc.player.dimension == 0) {
            return (float)(Math.floor(x / 8.0f * 10.0f) / 10.0);
        }
        return x * 8.0f;
    }
    
    static {
        CoordComponent.nether = new BooleanSetting("Nether", true);
        CoordComponent.simple = new BooleanSetting("Simple", true);
    }
}
