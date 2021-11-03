//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.mapping.mcp;

import org.spongepowered.tools.obfuscation.mapping.common.*;
import javax.annotation.processing.*;
import java.nio.charset.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import org.spongepowered.asm.obfuscation.mapping.mcp.*;
import org.spongepowered.asm.mixin.throwables.*;
import java.io.*;
import com.google.common.io.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

public class MappingProviderSrg extends MappingProvider
{
    public MappingProviderSrg(final Messager messager, final Filer filer) {
        super(messager, filer);
    }
    
    public void read(final File input) throws IOException {
        final BiMap<String, String> packageMap = (BiMap<String, String>)this.packageMap;
        final BiMap<String, String> classMap = (BiMap<String, String>)this.classMap;
        final BiMap<MappingField, MappingField> fieldMap = (BiMap<MappingField, MappingField>)this.fieldMap;
        final BiMap<MappingMethod, MappingMethod> methodMap = (BiMap<MappingMethod, MappingMethod>)this.methodMap;
        Files.readLines(input, Charset.defaultCharset(), (LineProcessor)new LineProcessor<String>() {
            public String getResult() {
                return null;
            }
            
            public boolean processLine(final String line) throws IOException {
                if (Strings.isNullOrEmpty(line) || line.startsWith("#")) {
                    return true;
                }
                final String type = line.substring(0, 2);
                final String[] args = line.substring(4).split(" ");
                if (type.equals("PK")) {
                    packageMap.forcePut((Object)args[0], (Object)args[1]);
                }
                else if (type.equals("CL")) {
                    classMap.forcePut((Object)args[0], (Object)args[1]);
                }
                else if (type.equals("FD")) {
                    fieldMap.forcePut((Object)new MappingFieldSrg(args[0]).copy(), (Object)new MappingFieldSrg(args[1]).copy());
                }
                else {
                    if (!type.equals("MD")) {
                        throw new MixinException("Invalid SRG file: " + input);
                    }
                    methodMap.forcePut((Object)new MappingMethod(args[0], args[1]), (Object)new MappingMethod(args[2], args[3]));
                }
                return true;
            }
        });
    }
    
    public MappingField getFieldMapping(MappingField field) {
        if (field.getDesc() != null) {
            field = (MappingField)new MappingFieldSrg(field);
        }
        return (MappingField)this.fieldMap.get((Object)field);
    }
}
