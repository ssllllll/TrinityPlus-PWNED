//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.tools.obfuscation.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.tools.obfuscation.mirror.*;

public interface IObfuscationDataProvider
{
     <T> ObfuscationData<T> getObfEntryRecursive(final MemberInfo p0);
    
     <T> ObfuscationData<T> getObfEntry(final MemberInfo p0);
    
     <T> ObfuscationData<T> getObfEntry(final IMapping<T> p0);
    
    ObfuscationData<MappingMethod> getObfMethodRecursive(final MemberInfo p0);
    
    ObfuscationData<MappingMethod> getObfMethod(final MemberInfo p0);
    
    ObfuscationData<MappingMethod> getRemappedMethod(final MemberInfo p0);
    
    ObfuscationData<MappingMethod> getObfMethod(final MappingMethod p0);
    
    ObfuscationData<MappingMethod> getRemappedMethod(final MappingMethod p0);
    
    ObfuscationData<MappingField> getObfFieldRecursive(final MemberInfo p0);
    
    ObfuscationData<MappingField> getObfField(final MemberInfo p0);
    
    ObfuscationData<MappingField> getObfField(final MappingField p0);
    
    ObfuscationData<String> getObfClass(final TypeHandle p0);
    
    ObfuscationData<String> getObfClass(final String p0);
}
