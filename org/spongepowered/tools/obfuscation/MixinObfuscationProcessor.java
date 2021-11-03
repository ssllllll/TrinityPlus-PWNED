//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import org.spongepowered.asm.mixin.*;
import java.lang.annotation.*;
import javax.lang.model.element.*;
import javax.tools.*;
import javax.lang.model.*;
import java.util.*;

public abstract class MixinObfuscationProcessor extends AbstractProcessor
{
    protected AnnotatedMixins mixins;
    
    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.mixins = AnnotatedMixins.getMixinsForEnvironment(processingEnv);
    }
    
    protected void processMixins(final RoundEnvironment roundEnv) {
        this.mixins.onPassStarted();
        for (final Element elem : roundEnv.getElementsAnnotatedWith((Class<? extends Annotation>)Mixin.class)) {
            if (elem.getKind() == ElementKind.CLASS || elem.getKind() == ElementKind.INTERFACE) {
                this.mixins.registerMixin((TypeElement)elem);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, (CharSequence)"Found an @Mixin annotation on an element which is not a class or interface", elem);
            }
        }
    }
    
    protected void postProcess(final RoundEnvironment roundEnv) {
        this.mixins.onPassCompleted(roundEnv);
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        try {
            return SourceVersion.valueOf("RELEASE_8");
        }
        catch (IllegalArgumentException ex) {
            return super.getSupportedSourceVersion();
        }
    }
    
    @Override
    public Set<String> getSupportedOptions() {
        return SupportedOptions.getAllOptions();
    }
}
