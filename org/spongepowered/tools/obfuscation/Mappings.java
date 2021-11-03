//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.util.*;
import org.spongepowered.asm.obfuscation.mapping.*;

class Mappings implements IMappingConsumer
{
    private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingField>> fieldMappings;
    private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingMethod>> methodMappings;
    private UniqueMappings unique;
    
    public Mappings() {
        this.fieldMappings = new HashMap<ObfuscationType, IMappingConsumer.MappingSet<MappingField>>();
        this.methodMappings = new HashMap<ObfuscationType, IMappingConsumer.MappingSet<MappingMethod>>();
        this.init();
    }
    
    private void init() {
        for (final ObfuscationType obfType : ObfuscationType.types()) {
            this.fieldMappings.put(obfType, (IMappingConsumer.MappingSet<MappingField>)new IMappingConsumer.MappingSet());
            this.methodMappings.put(obfType, (IMappingConsumer.MappingSet<MappingMethod>)new IMappingConsumer.MappingSet());
        }
    }
    
    public IMappingConsumer asUnique() {
        if (this.unique == null) {
            this.unique = new UniqueMappings((IMappingConsumer)this);
        }
        return (IMappingConsumer)this.unique;
    }
    
    public IMappingConsumer.MappingSet<MappingField> getFieldMappings(final ObfuscationType type) {
        final IMappingConsumer.MappingSet<MappingField> mappings = this.fieldMappings.get(type);
        return (IMappingConsumer.MappingSet<MappingField>)((mappings != null) ? mappings : new IMappingConsumer.MappingSet());
    }
    
    public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(final ObfuscationType type) {
        final IMappingConsumer.MappingSet<MappingMethod> mappings = this.methodMappings.get(type);
        return (IMappingConsumer.MappingSet<MappingMethod>)((mappings != null) ? mappings : new IMappingConsumer.MappingSet());
    }
    
    public void clear() {
        this.fieldMappings.clear();
        this.methodMappings.clear();
        if (this.unique != null) {
            this.unique.clearMaps();
        }
        this.init();
    }
    
    public void addFieldMapping(final ObfuscationType type, final MappingField from, final MappingField to) {
        IMappingConsumer.MappingSet<MappingField> mappings = this.fieldMappings.get(type);
        if (mappings == null) {
            mappings = (IMappingConsumer.MappingSet<MappingField>)new IMappingConsumer.MappingSet();
            this.fieldMappings.put(type, mappings);
        }
        mappings.add((Object)new IMappingConsumer.MappingSet.Pair((IMapping)from, (IMapping)to));
    }
    
    public void addMethodMapping(final ObfuscationType type, final MappingMethod from, final MappingMethod to) {
        IMappingConsumer.MappingSet<MappingMethod> mappings = this.methodMappings.get(type);
        if (mappings == null) {
            mappings = (IMappingConsumer.MappingSet<MappingMethod>)new IMappingConsumer.MappingSet();
            this.methodMappings.put(type, mappings);
        }
        mappings.add((Object)new IMappingConsumer.MappingSet.Pair((IMapping)from, (IMapping)to));
    }
    
    public static class MappingConflictException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;
        private final IMapping<?> oldMapping;
        private final IMapping<?> newMapping;
        
        public MappingConflictException(final IMapping<?> oldMapping, final IMapping<?> newMapping) {
            this.oldMapping = oldMapping;
            this.newMapping = newMapping;
        }
        
        public IMapping<?> getOld() {
            return this.oldMapping;
        }
        
        public IMapping<?> getNew() {
            return this.newMapping;
        }
    }
    
    static class UniqueMappings implements IMappingConsumer
    {
        private final IMappingConsumer mappings;
        private final Map<ObfuscationType, Map<MappingField, MappingField>> fields;
        private final Map<ObfuscationType, Map<MappingMethod, MappingMethod>> methods;
        
        public UniqueMappings(final IMappingConsumer mappings) {
            this.fields = new HashMap<ObfuscationType, Map<MappingField, MappingField>>();
            this.methods = new HashMap<ObfuscationType, Map<MappingMethod, MappingMethod>>();
            this.mappings = mappings;
        }
        
        public void clear() {
            this.clearMaps();
            this.mappings.clear();
        }
        
        protected void clearMaps() {
            this.fields.clear();
            this.methods.clear();
        }
        
        public void addFieldMapping(final ObfuscationType type, final MappingField from, final MappingField to) {
            if (!this.checkForExistingMapping(type, from, to, this.fields)) {
                this.mappings.addFieldMapping(type, from, to);
            }
        }
        
        public void addMethodMapping(final ObfuscationType type, final MappingMethod from, final MappingMethod to) {
            if (!this.checkForExistingMapping(type, from, to, this.methods)) {
                this.mappings.addMethodMapping(type, from, to);
            }
        }
        
        private <TMapping extends IMapping<TMapping>> boolean checkForExistingMapping(final ObfuscationType type, final TMapping from, final TMapping to, final Map<ObfuscationType, Map<TMapping, TMapping>> mappings) throws MappingConflictException {
            Map<TMapping, TMapping> existingMappings = mappings.get(type);
            if (existingMappings == null) {
                existingMappings = new HashMap<TMapping, TMapping>();
                mappings.put(type, existingMappings);
            }
            final TMapping existing = existingMappings.get(from);
            if (existing == null) {
                existingMappings.put(from, to);
                return false;
            }
            if (existing.equals(to)) {
                return true;
            }
            throw new MappingConflictException(existing, to);
        }
        
        public IMappingConsumer.MappingSet<MappingField> getFieldMappings(final ObfuscationType type) {
            return (IMappingConsumer.MappingSet<MappingField>)this.mappings.getFieldMappings(type);
        }
        
        public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(final ObfuscationType type) {
            return (IMappingConsumer.MappingSet<MappingMethod>)this.mappings.getMethodMappings(type);
        }
    }
}
