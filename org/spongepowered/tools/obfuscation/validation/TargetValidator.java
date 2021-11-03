//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.validation;

import org.spongepowered.tools.obfuscation.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.lang.model.element.*;
import org.spongepowered.asm.mixin.gen.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.type.*;

public class TargetValidator extends MixinValidator
{
    public TargetValidator(final IMixinAnnotationProcessor ap) {
        super(ap, IMixinValidator.ValidationPass.LATE);
    }
    
    public boolean validate(final TypeElement mixin, final AnnotationHandle annotation, final Collection<TypeHandle> targets) {
        if ("true".equalsIgnoreCase(this.options.getOption("disableTargetValidator"))) {
            return true;
        }
        if (mixin.getKind() == ElementKind.INTERFACE) {
            this.validateInterfaceMixin(mixin, targets);
        }
        else {
            this.validateClassMixin(mixin, targets);
        }
        return true;
    }
    
    private void validateInterfaceMixin(final TypeElement mixin, final Collection<TypeHandle> targets) {
        boolean containsNonAccessorMethod = false;
        for (final Element element : mixin.getEnclosedElements()) {
            if (element.getKind() == ElementKind.METHOD) {
                final boolean isAccessor = AnnotationHandle.of(element, (Class)Accessor.class).exists();
                final boolean isInvoker = AnnotationHandle.of(element, (Class)Invoker.class).exists();
                containsNonAccessorMethod |= (!isAccessor && !isInvoker);
            }
        }
        if (!containsNonAccessorMethod) {
            return;
        }
        for (final TypeHandle target : targets) {
            final TypeElement targetType = target.getElement();
            if (targetType != null && targetType.getKind() != ElementKind.INTERFACE) {
                this.error("Targetted type '" + target + " of " + mixin + " is not an interface", (Element)mixin);
            }
        }
    }
    
    private void validateClassMixin(final TypeElement mixin, final Collection<TypeHandle> targets) {
        final TypeMirror superClass = mixin.getSuperclass();
        for (final TypeHandle target : targets) {
            final TypeMirror targetType = target.getType();
            if (targetType != null && !this.validateSuperClass(targetType, superClass)) {
                this.error("Superclass " + superClass + " of " + mixin + " was not found in the hierarchy of target class " + targetType, (Element)mixin);
            }
        }
    }
    
    private boolean validateSuperClass(final TypeMirror targetType, final TypeMirror superClass) {
        return TypeUtils.isAssignable(this.processingEnv, targetType, superClass) || this.validateSuperClassRecursive(targetType, superClass);
    }
    
    private boolean validateSuperClassRecursive(final TypeMirror targetType, final TypeMirror superClass) {
        if (!(targetType instanceof DeclaredType)) {
            return false;
        }
        if (TypeUtils.isAssignable(this.processingEnv, targetType, superClass)) {
            return true;
        }
        final TypeElement targetElement = (TypeElement)((DeclaredType)targetType).asElement();
        final TypeMirror targetSuper = targetElement.getSuperclass();
        return targetSuper.getKind() != TypeKind.NONE && (this.checkMixinsFor(targetSuper, superClass) || this.validateSuperClassRecursive(targetSuper, superClass));
    }
    
    private boolean checkMixinsFor(final TypeMirror targetType, final TypeMirror superClass) {
        for (final TypeMirror mixinType : this.getMixinsTargeting(targetType)) {
            if (TypeUtils.isAssignable(this.processingEnv, mixinType, superClass)) {
                return true;
            }
        }
        return false;
    }
}
