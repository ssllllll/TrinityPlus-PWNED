//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.mapping;

import org.spongepowered.tools.obfuscation.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import java.util.*;
import com.google.common.base.*;

public interface IMappingConsumer
{
    void clear();
    
    void addFieldMapping(final ObfuscationType p0, final MappingField p1, final MappingField p2);
    
    void addMethodMapping(final ObfuscationType p0, final MappingMethod p1, final MappingMethod p2);
    
    MappingSet<MappingField> getFieldMappings(final ObfuscationType p0);
    
    MappingSet<MappingMethod> getMethodMappings(final ObfuscationType p0);
    
    public static class MappingSet<TMapping extends IMapping<TMapping>> extends LinkedHashSet<Pair<TMapping>>
    {
        private static final long serialVersionUID = 1L;
        
        public static class Pair<TMapping extends IMapping<TMapping>>
        {
            public final TMapping from;
            public final TMapping to;
            
            public Pair(final TMapping from, final TMapping to) {
                this.from = from;
                this.to = to;
            }
            
            @Override
            public boolean equals(final Object obj) {
                if (!(obj instanceof Pair)) {
                    return false;
                }
                final Pair<TMapping> other = (Pair<TMapping>)obj;
                return Objects.equal((Object)this.from, (Object)other.from) && Objects.equal((Object)this.to, (Object)other.to);
            }
            
            @Override
            public int hashCode() {
                return Objects.hashCode(new Object[] { this.from, this.to });
            }
            
            @Override
            public String toString() {
                return String.format("%s -> %s", this.from, this.to);
            }
        }
    }
}
