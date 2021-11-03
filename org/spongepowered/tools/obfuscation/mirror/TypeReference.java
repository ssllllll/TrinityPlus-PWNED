//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.mirror;

import java.io.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;

public class TypeReference implements Serializable, Comparable<TypeReference>
{
    private static final long serialVersionUID = 1L;
    private final String name;
    private transient TypeHandle handle;
    
    public TypeReference(final TypeHandle handle) {
        this.name = handle.getName();
        this.handle = handle;
    }
    
    public TypeReference(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getClassName() {
        return this.name.replace('/', '.');
    }
    
    public TypeHandle getHandle(final ProcessingEnvironment processingEnv) {
        if (this.handle == null) {
            final TypeElement element = processingEnv.getElementUtils().getTypeElement(this.getClassName());
            try {
                this.handle = new TypeHandle(element);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this.handle;
    }
    
    @Override
    public String toString() {
        return String.format("TypeReference[%s]", this.name);
    }
    
    @Override
    public int compareTo(final TypeReference other) {
        return (other == null) ? -1 : this.name.compareTo(other.name);
    }
    
    @Override
    public boolean equals(final Object other) {
        return other instanceof TypeReference && this.compareTo((TypeReference)other) == 0;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
