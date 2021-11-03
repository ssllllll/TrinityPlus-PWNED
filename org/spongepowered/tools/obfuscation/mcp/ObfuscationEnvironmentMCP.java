//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.mcp;

import org.spongepowered.tools.obfuscation.*;
import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.mapping.mcp.*;

public class ObfuscationEnvironmentMCP extends ObfuscationEnvironment
{
    protected ObfuscationEnvironmentMCP(final ObfuscationType type) {
        super(type);
    }
    
    @Override
    protected IMappingProvider getMappingProvider(final Messager messager, final Filer filer) {
        return (IMappingProvider)new MappingProviderSrg(messager, filer);
    }
    
    @Override
    protected IMappingWriter getMappingWriter(final Messager messager, final Filer filer) {
        return (IMappingWriter)new MappingWriterSrg(messager, filer);
    }
}
