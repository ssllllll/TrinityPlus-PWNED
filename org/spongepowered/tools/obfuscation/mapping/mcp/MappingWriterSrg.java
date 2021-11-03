//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.mapping.mcp;

import org.spongepowered.tools.obfuscation.mapping.common.*;
import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.io.*;
import java.util.*;

public class MappingWriterSrg extends MappingWriter
{
    public MappingWriterSrg(final Messager messager, final Filer filer) {
        super(messager, filer);
    }
    
    public void write(final String output, final ObfuscationType type, final IMappingConsumer.MappingSet<MappingField> fields, final IMappingConsumer.MappingSet<MappingMethod> methods) {
        if (output == null) {
            return;
        }
        PrintWriter writer = null;
        try {
            writer = this.openFileWriter(output, type + " output SRGs");
            this.writeFieldMappings(writer, fields);
            this.writeMethodMappings(writer, methods);
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
    
    protected void writeFieldMappings(final PrintWriter writer, final IMappingConsumer.MappingSet<MappingField> fields) {
        for (final IMappingConsumer.MappingSet.Pair<MappingField> field : fields) {
            writer.println(this.formatFieldMapping(field));
        }
    }
    
    protected void writeMethodMappings(final PrintWriter writer, final IMappingConsumer.MappingSet<MappingMethod> methods) {
        for (final IMappingConsumer.MappingSet.Pair<MappingMethod> method : methods) {
            writer.println(this.formatMethodMapping(method));
        }
    }
    
    protected String formatFieldMapping(final IMappingConsumer.MappingSet.Pair<MappingField> mapping) {
        return String.format("FD: %s/%s %s/%s", ((MappingField)mapping.from).getOwner(), ((MappingField)mapping.from).getName(), ((MappingField)mapping.to).getOwner(), ((MappingField)mapping.to).getName());
    }
    
    protected String formatMethodMapping(final IMappingConsumer.MappingSet.Pair<MappingMethod> mapping) {
        return String.format("MD: %s %s %s %s", ((MappingMethod)mapping.from).getName(), ((MappingMethod)mapping.from).getDesc(), ((MappingMethod)mapping.to).getName(), ((MappingMethod)mapping.to).getDesc());
    }
}
