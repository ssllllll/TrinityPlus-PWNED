//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import me.leon.trinityplus.hacks.misc.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.hacks.render.*;
import me.leon.trinityplus.hacks.movement.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { MovementInputFromOptions.class }, priority = 10000)
public class MixinMovementInputFromOptions extends MovementInput
{
    @Redirect(method = { "updatePlayerMoveState" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean isKeyPressed(final KeyBinding keyBinding) {
        if (!((FreeCam)ModuleManager.getMod((Class)FreeCam.class)).check() && !ModuleManager.getMod((Class)FreeLook.class).isEnabled()) {
            if (ModuleManager.getMod((Class)NoSlow.class).isEnabled() && NoSlow.gui.getValue() && Minecraft.getMinecraft().currentScreen != null && !(Minecraft.getMinecraft().currentScreen instanceof GuiChat) && Minecraft.getMinecraft().player != null) {
                return Keyboard.isKeyDown(keyBinding.getKeyCode());
            }
        }
        else if (((FreeCam)ModuleManager.getMod((Class)FreeCam.class)).check()) {
            return false;
        }
        return keyBinding.isKeyDown();
    }
}
