//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hud.components;

import me.leon.trinityplus.hud.*;
import net.minecraft.client.renderer.*;
import me.leon.trinityplus.utils.rendering.*;
import net.minecraft.entity.*;

public class RagDollComponent extends Component
{
    public RagDollComponent() {
        super("RagDoll");
        this.visible = true;
        this.x = 0.0f;
        this.y = 0.0f;
    }
    
    public void render() {
        this.drawBackground();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        RenderUtils.drawEntityOnScreen((int)this.x + 25, (int)this.y + 100, 50, this.y + 13.0f, (EntityLivingBase)this.mc.player);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }
    
    public float width() {
        return 50.0f;
    }
    
    public float height() {
        return 105.0f;
    }
}
