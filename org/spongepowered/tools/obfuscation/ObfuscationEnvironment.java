//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.annotation.processing.*;
import javax.tools.*;
import java.io.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.type.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.util.*;
import org.spongepowered.tools.obfuscation.mapping.*;

public abstract class ObfuscationEnvironment implements IObfuscationEnvironment
{
    protected final ObfuscationType type;
    protected final IMappingProvider mappingProvider;
    protected final IMappingWriter mappingWriter;
    protected final RemapperProxy remapper;
    protected final IMixinAnnotationProcessor ap;
    protected final String outFileName;
    protected final List<String> inFileNames;
    private boolean initDone;
    
    protected ObfuscationEnvironment(final ObfuscationType type) {
        this.remapper = new RemapperProxy();
        this.type = type;
        this.ap = type.getAnnotationProcessor();
        this.inFileNames = type.getInputFileNames();
        this.outFileName = type.getOutputFileName();
        this.mappingProvider = this.getMappingProvider((Messager)this.ap, this.ap.getProcessingEnvironment().getFiler());
        this.mappingWriter = this.getMappingWriter((Messager)this.ap, this.ap.getProcessingEnvironment().getFiler());
    }
    
    @Override
    public String toString() {
        return this.type.toString();
    }
    
    protected abstract IMappingProvider getMappingProvider(final Messager p0, final Filer p1);
    
    protected abstract IMappingWriter getMappingWriter(final Messager p0, final Filer p1);
    
    private boolean initMappings() {
        if (!this.initDone) {
            this.initDone = true;
            if (this.inFileNames == null) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, (CharSequence)("The " + this.type.getConfig().getInputFileOption() + " argument was not supplied, obfuscation processing will not occur"));
                return false;
            }
            int successCount = 0;
            for (final String inputFileName : this.inFileNames) {
                final File inputFile = new File(inputFileName);
                try {
                    if (!inputFile.isFile()) {
                        continue;
                    }
                    this.ap.printMessage(Diagnostic.Kind.NOTE, (CharSequence)("Loading " + this.type + " mappings from " + inputFile.getAbsolutePath()));
                    this.mappingProvider.read(inputFile);
                    ++successCount;
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (successCount < 1) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, (CharSequence)("No valid input files for " + this.type + " could be read, processing may not be sucessful."));
                this.mappingProvider.clear();
            }
        }
        return !this.mappingProvider.isEmpty();
    }
    
    public ObfuscationType getType() {
        return this.type;
    }
    
    public MappingMethod getObfMethod(final MemberInfo method) {
        final MappingMethod obfd = this.getObfMethod(method.asMethodMapping());
        if (obfd != null || !method.isFullyQualified()) {
            return obfd;
        }
        final TypeHandle type = this.ap.getTypeProvider().getTypeHandle(method.owner);
        if (type == null || type.isImaginary()) {
            return null;
        }
        final TypeMirror superClass = type.getElement().getSuperclass();
        if (superClass.getKind() != TypeKind.DECLARED) {
            return null;
        }
        final String superClassName = ((TypeElement)((DeclaredType)superClass).asElement()).getQualifiedName().toString();
        return this.getObfMethod(new MemberInfo(method.name, superClassName.replace('.', '/'), method.desc, method.matchAll));
    }
    
    public MappingMethod getObfMethod(final MappingMethod method) {
        return this.getObfMethod(method, true);
    }
    
    public MappingMethod getObfMethod(final MappingMethod method, final boolean lazyRemap) {
        if (!this.initMappings()) {
            return null;
        }
        boolean remapped = true;
        MappingMethod mapping = null;
        for (MappingMethod md = method; md != null && mapping == null; mapping = this.mappingProvider.getMethodMapping(md), md = md.getSuper()) {}
        if (mapping == null) {
            if (lazyRemap) {
                return null;
            }
            mapping = method.copy();
            remapped = false;
        }
        final String remappedOwner = this.getObfClass(mapping.getOwner());
        if (remappedOwner == null || remappedOwner.equals(method.getOwner()) || remappedOwner.equals(mapping.getOwner())) {
            return remapped ? mapping : null;
        }
        if (remapped) {
            return mapping.move(remappedOwner);
        }
        final String desc = ObfuscationUtil.mapDescriptor(mapping.getDesc(), (ObfuscationUtil.IClassRemapper)this.remapper);
        return new MappingMethod(remappedOwner, mapping.getSimpleName(), desc);
    }
    
    public MemberInfo remapDescriptor(final MemberInfo method) {
        boolean transformed = false;
        String owner = method.owner;
        if (owner != null) {
            final String newOwner = this.remapper.map(owner);
            if (newOwner != null) {
                owner = newOwner;
                transformed = true;
            }
        }
        String desc = method.desc;
        if (desc != null) {
            final String newDesc = ObfuscationUtil.mapDescriptor(method.desc, (ObfuscationUtil.IClassRemapper)this.remapper);
            if (!newDesc.equals(method.desc)) {
                desc = newDesc;
                transformed = true;
            }
        }
        return transformed ? new MemberInfo(method.name, owner, desc, method.matchAll) : null;
    }
    
    public String remapDescriptor(final String desc) {
        return ObfuscationUtil.mapDescriptor(desc, (ObfuscationUtil.IClassRemapper)this.remapper);
    }
    
    public MappingField getObfField(final MemberInfo field) {
        return this.getObfField(field.asFieldMapping(), true);
    }
    
    public MappingField getObfField(final MappingField field) {
        return this.getObfField(field, true);
    }
    
    public MappingField getObfField(final MappingField field, final boolean lazyRemap) {
        if (!this.initMappings()) {
            return null;
        }
        MappingField mapping = this.mappingProvider.getFieldMapping(field);
        if (mapping == null) {
            if (lazyRemap) {
                return null;
            }
            mapping = field;
        }
        final String remappedOwner = this.getObfClass(mapping.getOwner());
        if (remappedOwner == null || remappedOwner.equals(field.getOwner()) || remappedOwner.equals(mapping.getOwner())) {
            return (mapping != field) ? mapping : null;
        }
        return mapping.move(remappedOwner);
    }
    
    public String getObfClass(final String className) {
        if (!this.initMappings()) {
            return null;
        }
        return this.mappingProvider.getClassMapping(className);
    }
    
    public void writeMappings(final Collection<IMappingConsumer> consumers) {
        final IMappingConsumer.MappingSet<MappingField> fields = (IMappingConsumer.MappingSet<MappingField>)new IMappingConsumer.MappingSet();
        final IMappingConsumer.MappingSet<MappingMethod> methods = (IMappingConsumer.MappingSet<MappingMethod>)new IMappingConsumer.MappingSet();
        for (final IMappingConsumer mappings : consumers) {
            fields.addAll((Collection)mappings.getFieldMappings(this.type));
            methods.addAll((Collection)mappings.getMethodMappings(this.type));
        }
        this.mappingWriter.write(this.outFileName, this.type, (IMappingConsumer.MappingSet)fields, (IMappingConsumer.MappingSet)methods);
    }
    
    final class RemapperProxy implements ObfuscationUtil.IClassRemapper
    {
        public String map(final String typeName) {
            if (ObfuscationEnvironment.this.mappingProvider == null) {
                return null;
            }
            return ObfuscationEnvironment.this.mappingProvider.getClassMapping(typeName);
        }
        
        public String unmap(final String typeName) {
            if (ObfuscationEnvironment.this.mappingProvider == null) {
                return null;
            }
            return ObfuscationEnvironment.this.mappingProvider.getClassMapping(typeName);
        }
    }
}
