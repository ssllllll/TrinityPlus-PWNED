//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.tools.*;
import org.spongepowered.asm.mixin.*;
import javax.lang.model.type.*;
import java.util.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

public class AnnotatedMixinElementHandlerSoftImplements extends AnnotatedMixinElementHandler
{
    AnnotatedMixinElementHandlerSoftImplements(final IMixinAnnotationProcessor ap, final AnnotatedMixin mixin) {
        super(ap, mixin);
    }
    
    public void process(final AnnotationHandle implementsAnnotation) {
        if (!this.mixin.remap()) {
            return;
        }
        final List<AnnotationHandle> interfaces = implementsAnnotation.getAnnotationList("value");
        if (interfaces.size() < 1) {
            this.ap.printMessage(Diagnostic.Kind.WARNING, "Empty @Implements annotation", this.mixin.getMixin(), implementsAnnotation.asMirror());
            return;
        }
        for (final AnnotationHandle interfaceAnnotation : interfaces) {
            final Interface.Remap remap = interfaceAnnotation.getValue("remap", Interface.Remap.ALL);
            if (remap == Interface.Remap.NONE) {
                continue;
            }
            try {
                final TypeHandle iface = new TypeHandle(interfaceAnnotation.getValue("iface"));
                final String prefix = interfaceAnnotation.getValue("prefix");
                this.processSoftImplements(remap, iface, prefix);
            }
            catch (Exception ex) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected error: " + ex.getClass().getName() + ": " + ex.getMessage(), this.mixin.getMixin(), interfaceAnnotation.asMirror());
            }
        }
    }
    
    private void processSoftImplements(final Interface.Remap remap, final TypeHandle iface, final String prefix) {
        for (final ExecutableElement method : iface.getEnclosedElements(ElementKind.METHOD)) {
            this.processMethod(remap, iface, prefix, method);
        }
        for (final TypeHandle superInterface : iface.getInterfaces()) {
            this.processSoftImplements(remap, superInterface, prefix);
        }
    }
    
    private void processMethod(final Interface.Remap remap, final TypeHandle iface, final String prefix, final ExecutableElement method) {
        final String name = method.getSimpleName().toString();
        final String sig = TypeUtils.getJavaSignature(method);
        final String desc = TypeUtils.getDescriptor(method);
        if (remap != Interface.Remap.ONLY_PREFIXED) {
            final MethodHandle mixinMethod = this.mixin.getHandle().findMethod(name, sig);
            if (mixinMethod != null) {
                this.addInterfaceMethodMapping(remap, iface, null, mixinMethod, name, desc);
            }
        }
        if (prefix != null) {
            final MethodHandle prefixedMixinMethod = this.mixin.getHandle().findMethod(prefix + name, sig);
            if (prefixedMixinMethod != null) {
                this.addInterfaceMethodMapping(remap, iface, prefix, prefixedMixinMethod, name, desc);
            }
        }
    }
    
    private void addInterfaceMethodMapping(final Interface.Remap remap, final TypeHandle iface, final String prefix, final MethodHandle method, final String name, final String desc) {
        final MappingMethod mapping = new MappingMethod(iface.getName(), name, desc);
        final ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(mapping);
        if (obfData.isEmpty()) {
            if (remap.forceRemap()) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, "No obfuscation mapping for soft-implementing method", method.getElement());
            }
            return;
        }
        this.addMethodMappings(method.getName(), desc, (ObfuscationData)this.applyPrefix(obfData, prefix));
    }
    
    private ObfuscationData<MappingMethod> applyPrefix(final ObfuscationData<MappingMethod> data, final String prefix) {
        if (prefix == null) {
            return data;
        }
        final ObfuscationData<MappingMethod> prefixed = new ObfuscationData<MappingMethod>();
        for (final ObfuscationType type : data) {
            final MappingMethod mapping = data.get(type);
            prefixed.put(type, mapping.addPrefix(prefix));
        }
        return prefixed;
    }
}
