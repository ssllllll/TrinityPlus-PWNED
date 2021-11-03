//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.tools.*;
import java.util.*;
import javax.annotation.processing.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.lang.reflect.*;

class AnnotatedMixinElementHandlerOverwrite extends AnnotatedMixinElementHandler
{
    AnnotatedMixinElementHandlerOverwrite(final IMixinAnnotationProcessor ap, final AnnotatedMixin mixin) {
        super(ap, mixin);
    }
    
    public void registerMerge(final ExecutableElement method) {
        this.validateTargetMethod(method, (AnnotationHandle)null, new AnnotatedMixinElementHandler.AliasedElementName((Element)method, AnnotationHandle.MISSING), "overwrite", true, true);
    }
    
    public void registerOverwrite(final AnnotatedElementOverwrite elem) {
        final AnnotatedMixinElementHandler.AliasedElementName name = new AnnotatedMixinElementHandler.AliasedElementName(elem.getElement(), elem.getAnnotation());
        this.validateTargetMethod((ExecutableElement)elem.getElement(), elem.getAnnotation(), name, "@Overwrite", true, false);
        this.checkConstraints((ExecutableElement)elem.getElement(), elem.getAnnotation());
        if (elem.shouldRemap()) {
            for (final TypeHandle target : this.mixin.getTargets()) {
                if (!this.registerOverwriteForTarget(elem, target)) {
                    return;
                }
            }
        }
        if (!"true".equalsIgnoreCase(this.ap.getOption("disableOverwriteChecker"))) {
            final Diagnostic.Kind overwriteErrorKind = "error".equalsIgnoreCase(this.ap.getOption("overwriteErrorLevel")) ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
            final String javadoc = this.ap.getJavadocProvider().getJavadoc(elem.getElement());
            if (javadoc == null) {
                this.ap.printMessage(overwriteErrorKind, "@Overwrite is missing javadoc comment", elem.getElement());
                return;
            }
            if (!javadoc.toLowerCase().contains("@author")) {
                this.ap.printMessage(overwriteErrorKind, "@Overwrite is missing an @author tag", elem.getElement());
            }
            if (!javadoc.toLowerCase().contains("@reason")) {
                this.ap.printMessage(overwriteErrorKind, "@Overwrite is missing an @reason tag", elem.getElement());
            }
        }
    }
    
    private boolean registerOverwriteForTarget(final AnnotatedElementOverwrite elem, final TypeHandle target) {
        final MappingMethod targetMethod = target.getMappingMethod(elem.getSimpleName(), elem.getDesc());
        final ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod);
        if (obfData.isEmpty()) {
            Diagnostic.Kind error = Diagnostic.Kind.ERROR;
            try {
                final Method md = ((ExecutableElement)elem.getElement()).getClass().getMethod("isStatic", (Class<?>[])new Class[0]);
                if (md.invoke(elem.getElement(), new Object[0])) {
                    error = Diagnostic.Kind.WARNING;
                }
            }
            catch (Exception ex2) {}
            this.ap.printMessage(error, "No obfuscation mapping for @Overwrite method", elem.getElement());
            return false;
        }
        try {
            this.addMethodMappings(elem.getSimpleName(), elem.getDesc(), (ObfuscationData)obfData);
        }
        catch (Mappings.MappingConflictException ex) {
            elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, (CharSequence)("Mapping conflict for @Overwrite method: " + ex.getNew().getSimpleName() + " for target " + target + " conflicts with existing mapping " + ex.getOld().getSimpleName()));
            return false;
        }
        return true;
    }
    
    static class AnnotatedElementOverwrite extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
    {
        private final boolean shouldRemap;
        
        public AnnotatedElementOverwrite(final ExecutableElement element, final AnnotationHandle annotation, final boolean shouldRemap) {
            super((Element)element, annotation);
            this.shouldRemap = shouldRemap;
        }
        
        public boolean shouldRemap() {
            return this.shouldRemap;
        }
    }
}
