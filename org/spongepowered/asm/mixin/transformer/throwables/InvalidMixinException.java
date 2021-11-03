//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.refmap.*;

public class InvalidMixinException extends MixinException
{
    private static final long serialVersionUID = 2L;
    private final IMixinInfo mixin;
    
    public InvalidMixinException(final IMixinInfo mixin, final String message) {
        super(message);
        this.mixin = mixin;
    }
    
    public InvalidMixinException(final IMixinContext context, final String message) {
        this(context.getMixin(), message);
    }
    
    public InvalidMixinException(final IMixinInfo mixin, final Throwable cause) {
        super(cause);
        this.mixin = mixin;
    }
    
    public InvalidMixinException(final IMixinContext context, final Throwable cause) {
        this(context.getMixin(), cause);
    }
    
    public InvalidMixinException(final IMixinInfo mixin, final String message, final Throwable cause) {
        super(message, cause);
        this.mixin = mixin;
    }
    
    public InvalidMixinException(final IMixinContext context, final String message, final Throwable cause) {
        super(message, cause);
        this.mixin = context.getMixin();
    }
    
    public IMixinInfo getMixin() {
        return this.mixin;
    }
}
