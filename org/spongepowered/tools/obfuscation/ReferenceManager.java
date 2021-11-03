//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.asm.mixin.refmap.*;
import java.io.*;
import javax.lang.model.element.*;
import javax.tools.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import java.util.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

public class ReferenceManager implements IReferenceManager
{
    private final IMixinAnnotationProcessor ap;
    private final String outRefMapFileName;
    private final List<ObfuscationEnvironment> environments;
    private final ReferenceMapper refMapper;
    private boolean allowConflicts;
    
    public ReferenceManager(final IMixinAnnotationProcessor ap, final List<ObfuscationEnvironment> environments) {
        this.refMapper = new ReferenceMapper();
        this.ap = ap;
        this.environments = environments;
        this.outRefMapFileName = this.ap.getOption("outRefMapFile");
    }
    
    public boolean getAllowConflicts() {
        return this.allowConflicts;
    }
    
    public void setAllowConflicts(final boolean allowConflicts) {
        this.allowConflicts = allowConflicts;
    }
    
    public void write() {
        if (this.outRefMapFileName == null) {
            return;
        }
        PrintWriter writer = null;
        try {
            writer = this.newWriter(this.outRefMapFileName, "refmap");
            this.refMapper.write((Appendable)writer);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (Exception ex2) {}
            }
        }
    }
    
    private PrintWriter newWriter(final String fileName, final String description) throws IOException {
        if (fileName.matches("^.*[\\\\/:].*$")) {
            final File outFile = new File(fileName);
            outFile.getParentFile().mkdirs();
            this.ap.printMessage(Diagnostic.Kind.NOTE, (CharSequence)("Writing " + description + " to " + outFile.getAbsolutePath()));
            return new PrintWriter(outFile);
        }
        final FileObject outResource = this.ap.getProcessingEnvironment().getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", fileName, new Element[0]);
        this.ap.printMessage(Diagnostic.Kind.NOTE, (CharSequence)("Writing " + description + " to " + new File(outResource.toUri()).getAbsolutePath()));
        return new PrintWriter(outResource.openWriter());
    }
    
    public ReferenceMapper getMapper() {
        return this.refMapper;
    }
    
    public void addMethodMapping(final String className, final String reference, final ObfuscationData<MappingMethod> obfMethodData) {
        for (final ObfuscationEnvironment env : this.environments) {
            final MappingMethod obfMethod = (MappingMethod)obfMethodData.get(env.getType());
            if (obfMethod != null) {
                final MemberInfo remappedReference = new MemberInfo((IMapping)obfMethod);
                this.addMapping(env.getType(), className, reference, remappedReference.toString());
            }
        }
    }
    
    public void addMethodMapping(final String className, final String reference, final MemberInfo context, final ObfuscationData<MappingMethod> obfMethodData) {
        for (final ObfuscationEnvironment env : this.environments) {
            final MappingMethod obfMethod = (MappingMethod)obfMethodData.get(env.getType());
            if (obfMethod != null) {
                final MemberInfo remappedReference = context.remapUsing(obfMethod, true);
                this.addMapping(env.getType(), className, reference, remappedReference.toString());
            }
        }
    }
    
    public void addFieldMapping(final String className, final String reference, final MemberInfo context, final ObfuscationData<MappingField> obfFieldData) {
        for (final ObfuscationEnvironment env : this.environments) {
            final MappingField obfField = (MappingField)obfFieldData.get(env.getType());
            if (obfField != null) {
                final MemberInfo remappedReference = MemberInfo.fromMapping((IMapping)obfField.transform(env.remapDescriptor(context.desc)));
                this.addMapping(env.getType(), className, reference, remappedReference.toString());
            }
        }
    }
    
    public void addClassMapping(final String className, final String reference, final ObfuscationData<String> obfClassData) {
        for (final ObfuscationEnvironment env : this.environments) {
            final String remapped = (String)obfClassData.get(env.getType());
            if (remapped != null) {
                this.addMapping(env.getType(), className, reference, remapped);
            }
        }
    }
    
    protected void addMapping(final ObfuscationType type, final String className, final String reference, final String newReference) {
        final String oldReference = this.refMapper.addMapping(type.getKey(), className, reference, newReference);
        if (type.isDefault()) {
            this.refMapper.addMapping((String)null, className, reference, newReference);
        }
        if (!this.allowConflicts && oldReference != null && !oldReference.equals(newReference)) {
            throw new ReferenceConflictException(oldReference, newReference);
        }
    }
    
    public static class ReferenceConflictException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;
        private final String oldReference;
        private final String newReference;
        
        public ReferenceConflictException(final String oldReference, final String newReference) {
            this.oldReference = oldReference;
            this.newReference = newReference;
        }
        
        public String getOld() {
            return this.oldReference;
        }
        
        public String getNew() {
            return this.newReference;
        }
    }
}
