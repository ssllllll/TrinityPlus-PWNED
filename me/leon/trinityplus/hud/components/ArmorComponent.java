//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hud.components;

import me.leon.trinityplus.hud.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;

public class ArmorComponent extends Component
{
    public ArmorComponent() {
        super("Armor");
    }
    
    public void render() {
    }
    
    public float width() {
        return 0.0f;
    }
    
    public float height() {
        return 0.0f;
    }
    
    private void renderItem(final ItemStack itemStack, final int posX, final int posY, final int posY2) {
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        final int posY3 = (posY2 > 4) ? ((posY2 - 4) * 8 / 2) : 0;
        this.mc.getRenderItem().zLevel = -150.0f;
        RenderHelper.enableStandardItemLighting();
        this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, posX, posY + posY3);
        this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, itemStack, posX, posY + posY3);
        RenderHelper.disableStandardItemLighting();
        this.mc.getRenderItem().zLevel = 0.0f;
    }
}
