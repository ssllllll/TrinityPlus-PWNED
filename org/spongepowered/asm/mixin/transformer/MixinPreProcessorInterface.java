//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.lib.tree.*;

class MixinPreProcessorInterface extends MixinPreProcessorStandard
{
    MixinPreProcessorInterface(final MixinInfo mixin, final MixinInfo.MixinClassNode classNode) {
        super(mixin, classNode);
    }
    
    @Override
    protected void prepareMethod(final MixinInfo.MixinMethodNode mixinMethod, final ClassInfo.Method method) {
        if (!Bytecode.hasFlag((MethodNode)mixinMethod, 1) && !Bytecode.hasFlag((MethodNode)mixinMethod, 4096)) {
            throw new InvalidInterfaceMixinException((IMixinInfo)this.mixin, "Interface mixin contains a non-public method! Found " + method + " in " + this.mixin);
        }
        super.prepareMethod(mixinMethod, method);
    }
    
    @Override
    protected boolean validateField(final MixinTargetContext context, final FieldNode field, final AnnotationNode shadow) {
        if (!Bytecode.hasFlag(field, 8)) {
            throw new InvalidInterfaceMixinException((IMixinInfo)this.mixin, "Interface mixin contains an instance field! Found " + field.name + " in " + this.mixin);
        }
        return super.validateField(context, field, shadow);
    }
}
