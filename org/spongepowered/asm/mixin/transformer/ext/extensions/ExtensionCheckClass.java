//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.transformers.*;
import org.spongepowered.asm.lib.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.throwables.*;

public class ExtensionCheckClass implements IExtension
{
    @Override
    public boolean checkActive(final MixinEnvironment environment) {
        return environment.getOption(MixinEnvironment.Option.DEBUG_VERIFY);
    }
    
    @Override
    public void preApply(final ITargetClassContext context) {
    }
    
    @Override
    public void postApply(final ITargetClassContext context) {
        try {
            context.getClassNode().accept((ClassVisitor)new CheckClassAdapter((ClassVisitor)new MixinClassWriter(2)));
        }
        catch (RuntimeException ex) {
            throw new ValidationFailedException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public void export(final MixinEnvironment env, final String name, final boolean force, final byte[] bytes) {
    }
    
    public static class ValidationFailedException extends MixinException
    {
        private static final long serialVersionUID = 1L;
        
        public ValidationFailedException(final String message, final Throwable cause) {
            super(message, cause);
        }
        
        public ValidationFailedException(final String message) {
            super(message);
        }
        
        public ValidationFailedException(final Throwable cause) {
            super(cause);
        }
    }
}
