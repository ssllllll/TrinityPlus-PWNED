//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.collect.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.points.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.invoke.*;
import com.google.common.base.*;
import org.spongepowered.asm.mixin.injection.*;

public class ModifyConstantInjectionInfo extends InjectionInfo
{
    private static final String CONSTANT_ANNOTATION_CLASS;
    
    public ModifyConstantInjectionInfo(final MixinTargetContext mixin, final MethodNode method, final AnnotationNode annotation) {
        super(mixin, method, annotation, "constant");
    }
    
    protected List<AnnotationNode> readInjectionPoints(final String type) {
        List<AnnotationNode> ats = (List<AnnotationNode>)super.readInjectionPoints(type);
        if (ats.isEmpty()) {
            final AnnotationNode c = new AnnotationNode(ModifyConstantInjectionInfo.CONSTANT_ANNOTATION_CLASS);
            c.visit("log", (Object)Boolean.TRUE);
            ats = (List<AnnotationNode>)ImmutableList.of((Object)c);
        }
        return ats;
    }
    
    protected void parseInjectionPoints(final List<AnnotationNode> ats) {
        final Type returnType = Type.getReturnType(this.method.desc);
        for (final AnnotationNode at : ats) {
            this.injectionPoints.add(new BeforeConstant(this.getContext(), at, returnType.getDescriptor()));
        }
    }
    
    protected Injector parseInjector(final AnnotationNode injectAnnotation) {
        return (Injector)new ModifyConstantInjector((InjectionInfo)this);
    }
    
    protected String getDescription() {
        return "Constant modifier method";
    }
    
    public String getSliceId(final String id) {
        return Strings.nullToEmpty(id);
    }
    
    static {
        CONSTANT_ANNOTATION_CLASS = Constant.class.getName().replace('.', '/');
    }
}
