//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import javax.tools.*;
import javax.lang.model.type.*;

public abstract class MixinValidator implements IMixinValidator
{
    protected final ProcessingEnvironment processingEnv;
    protected final Messager messager;
    protected final IOptionProvider options;
    protected final IMixinValidator.ValidationPass pass;
    
    public MixinValidator(final IMixinAnnotationProcessor ap, final IMixinValidator.ValidationPass pass) {
        this.processingEnv = ap.getProcessingEnvironment();
        this.messager = (Messager)ap;
        this.options = (IOptionProvider)ap;
        this.pass = pass;
    }
    
    public final boolean validate(final IMixinValidator.ValidationPass pass, final TypeElement mixin, final AnnotationHandle annotation, final Collection<TypeHandle> targets) {
        return pass != this.pass || this.validate(mixin, annotation, targets);
    }
    
    protected abstract boolean validate(final TypeElement p0, final AnnotationHandle p1, final Collection<TypeHandle> p2);
    
    protected final void note(final String note, final Element element) {
        this.messager.printMessage(Diagnostic.Kind.NOTE, note, element);
    }
    
    protected final void warning(final String warning, final Element element) {
        this.messager.printMessage(Diagnostic.Kind.WARNING, warning, element);
    }
    
    protected final void error(final String error, final Element element) {
        this.messager.printMessage(Diagnostic.Kind.ERROR, error, element);
    }
    
    protected final Collection<TypeMirror> getMixinsTargeting(final TypeMirror targetType) {
        return (Collection<TypeMirror>)AnnotatedMixins.getMixinsForEnvironment(this.processingEnv).getMixinsTargeting(targetType);
    }
}
