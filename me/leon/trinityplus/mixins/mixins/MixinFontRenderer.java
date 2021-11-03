//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import me.leon.trinityplus.mixins.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.main.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.utils.misc.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ FontRenderer.class })
public abstract class MixinFontRenderer implements IMixin
{
    @Inject(method = { "getStringWidth(Ljava/lang/String;)I" }, at = { @At("HEAD") }, cancellable = true)
    public void getStringWidth(final String text, final CallbackInfoReturnable<Integer> cir) {
        if (Trinity.finishLoading && Font.enabled() && Font.vanilla.getValue()) {
            cir.setReturnValue((Object)(int)FontUtil.getStringWidth(text));
            cir.cancel();
        }
    }
    
    @Inject(method = { "drawString(Ljava/lang/String;FFIZ)I" }, at = { @At("HEAD") }, cancellable = true)
    public void renderStringHook(final String text, final float x, final float y, final int color, final boolean dropShadow, final CallbackInfoReturnable<Integer> info) {
        if (Trinity.finishLoading && Font.enabled() && Font.vanilla.getValue()) {
            final float result = FontUtil.drawString(text, x, y, color, dropShadow);
            info.setReturnValue((Object)(int)result);
            GlStateManager.enableAlpha();
            info.cancel();
        }
    }
    
    @Redirect(method = { "drawStringWithShadow" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;FFIZ)I"))
    public int drawCustomFontStringWithShadow(final FontRenderer fontRenderer, final String text, final float x, final float y, final int color, final boolean dropShadow) {
        if (Font.enabled() && Font.vanilla.getValue()) {
            return (int)FontUtil.drawString(text, x, y, new Color(color));
        }
        return fontRenderer.drawString(text, x, y, color, dropShadow);
    }
}
