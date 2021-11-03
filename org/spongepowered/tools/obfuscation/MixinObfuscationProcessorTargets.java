//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import java.lang.annotation.*;
import javax.tools.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import java.util.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.*;

@SupportedAnnotationTypes({ "org.spongepowered.asm.mixin.Mixin", "org.spongepowered.asm.mixin.Shadow", "org.spongepowered.asm.mixin.Overwrite", "org.spongepowered.asm.mixin.gen.Accessor", "org.spongepowered.asm.mixin.Implements" })
public class MixinObfuscationProcessorTargets extends MixinObfuscationProcessor
{
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            this.postProcess(roundEnv);
            return true;
        }
        this.processMixins(roundEnv);
        this.processShadows(roundEnv);
        this.processOverwrites(roundEnv);
        this.processAccessors(roundEnv);
        this.processInvokers(roundEnv);
        this.processImplements(roundEnv);
        this.postProcess(roundEnv);
        return true;
    }
    
    protected void postProcess(final RoundEnvironment roundEnv) {
        super.postProcess(roundEnv);
        try {
            this.mixins.writeReferences();
            this.mixins.writeMappings();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void processShadows(final RoundEnvironment roundEnv) {
        for (final Element elem : roundEnv.getElementsAnnotatedWith((Class<? extends Annotation>)Shadow.class)) {
            final Element parent = elem.getEnclosingElement();
            if (!(parent instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, (CharSequence)("Unexpected parent with type " + TypeUtils.getElementType(parent)), elem);
            }
            else {
                final AnnotationHandle shadow = AnnotationHandle.of(elem, (Class)Shadow.class);
                if (elem.getKind() == ElementKind.FIELD) {
                    this.mixins.registerShadow((TypeElement)parent, (VariableElement)elem, shadow);
                }
                else if (elem.getKind() == ElementKind.METHOD) {
                    this.mixins.registerShadow((TypeElement)parent, (ExecutableElement)elem, shadow);
                }
                else {
                    this.mixins.printMessage(Diagnostic.Kind.ERROR, (CharSequence)"Element is not a method or field", elem);
                }
            }
        }
    }
    
    private void processOverwrites(final RoundEnvironment roundEnv) {
        for (final Element elem : roundEnv.getElementsAnnotatedWith((Class<? extends Annotation>)Overwrite.class)) {
            final Element parent = elem.getEnclosingElement();
            if (!(parent instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, (CharSequence)("Unexpected parent with type " + TypeUtils.getElementType(parent)), elem);
            }
            else if (elem.getKind() == ElementKind.METHOD) {
                this.mixins.registerOverwrite((TypeElement)parent, (ExecutableElement)elem);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, (CharSequence)"Element is not a method", elem);
            }
        }
    }
    
    private void processAccessors(final RoundEnvironment roundEnv) {
        for (final Element elem : roundEnv.getElementsAnnotatedWith((Class<? extends Annotation>)Accessor.class)) {
            final Element parent = elem.getEnclosingElement();
            if (!(parent instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, (CharSequence)("Unexpected parent with type " + TypeUtils.getElementType(parent)), elem);
            }
            else if (elem.getKind() == ElementKind.METHOD) {
                this.mixins.registerAccessor((TypeElement)parent, (ExecutableElement)elem);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, (CharSequence)"Element is not a method", elem);
            }
        }
    }
    
    private void processInvokers(final RoundEnvironment roundEnv) {
        for (final Element elem : roundEnv.getElementsAnnotatedWith((Class<? extends Annotation>)Invoker.class)) {
            final Element parent = elem.getEnclosingElement();
            if (!(parent instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, (CharSequence)("Unexpected parent with type " + TypeUtils.getElementType(parent)), elem);
            }
            else if (elem.getKind() == ElementKind.METHOD) {
                this.mixins.registerInvoker((TypeElement)parent, (ExecutableElement)elem);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, (CharSequence)"Element is not a method", elem);
            }
        }
    }
    
    private void processImplements(final RoundEnvironment roundEnv) {
        for (final Element elem : roundEnv.getElementsAnnotatedWith((Class<? extends Annotation>)Implements.class)) {
            if (elem.getKind() == ElementKind.CLASS || elem.getKind() == ElementKind.INTERFACE) {
                final AnnotationHandle implementsAnnotation = AnnotationHandle.of(elem, (Class)Implements.class);
                this.mixins.registerSoftImplements((TypeElement)elem, implementsAnnotation);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, (CharSequence)"Found an @Implements annotation on an element which is not a class or interface", elem);
            }
        }
    }
}
