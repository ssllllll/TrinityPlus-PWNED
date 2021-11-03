//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mojang.realmsclient.gui.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.utils.misc.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiMainMenu.class })
public class MixinGuiMainMenu extends GuiScreen
{
    @Inject(method = { "drawScreen" }, at = { @At("TAIL") }, cancellable = true)
    public void drawText(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo info) {
        FontUtil.drawString("Trinity+" + ChatFormatting.WHITE + " " + "0.1", 2.0f, 2.0f, ClientColor.color.getValue());
    }
}
