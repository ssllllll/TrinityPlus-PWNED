//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import javax.tools.*;
import java.util.*;

@SupportedAnnotationTypes({ "org.spongepowered.asm.mixin.injection.Inject", "org.spongepowered.asm.mixin.injection.ModifyArg", "org.spongepowered.asm.mixin.injection.ModifyArgs", "org.spongepowered.asm.mixin.injection.Redirect", "org.spongepowered.asm.mixin.injection.At" })
public class MixinObfuscationProcessorInjection extends MixinObfuscationProcessor
{
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            this.postProcess(roundEnv);
            return true;
        }
        this.processMixins(roundEnv);
        this.processInjectors(roundEnv, (Class<? extends Annotation>)Inject.class);
        this.processInjectors(roundEnv, (Class<? extends Annotation>)ModifyArg.class);
        this.processInjectors(roundEnv, (Class<? extends Annotation>)ModifyArgs.class);
        this.processInjectors(roundEnv, (Class<? extends Annotation>)Redirect.class);
        this.processInjectors(roundEnv, (Class<? extends Annotation>)ModifyVariable.class);
        this.processInjectors(roundEnv, (Class<? extends Annotation>)ModifyConstant.class);
        this.postProcess(roundEnv);
        return true;
    }
    
    protected void postProcess(final RoundEnvironment roundEnv) {
        super.postProcess(roundEnv);
        try {
            this.mixins.writeReferences();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void processInjectors(final RoundEnvironment roundEnv, final Class<? extends Annotation> injectorClass) {
        for (final Element elem : roundEnv.getElementsAnnotatedWith(injectorClass)) {
            final Element parent = elem.getEnclosingElement();
            if (!(parent instanceof TypeElement)) {
                throw new IllegalStateException("@" + injectorClass.getSimpleName() + " element has unexpected parent with type " + TypeUtils.getElementType(parent));
            }
            final AnnotationHandle inject = AnnotationHandle.of(elem, (Class)injectorClass);
            if (elem.getKind() == ElementKind.METHOD) {
                this.mixins.registerInjector((TypeElement)parent, (ExecutableElement)elem, inject);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.WARNING, (CharSequence)("Found an @" + injectorClass.getSimpleName() + " annotation on an element which is not a method: " + elem.toString()));
            }
        }
    }
}
