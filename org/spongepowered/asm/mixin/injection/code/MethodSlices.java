//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public final class MethodSlices
{
    private final InjectionInfo info;
    private final Map<String, MethodSlice> slices;
    
    private MethodSlices(final InjectionInfo info) {
        this.slices = new HashMap<String, MethodSlice>(4);
        this.info = info;
    }
    
    private void add(final MethodSlice slice) {
        final String id = this.info.getSliceId(slice.getId());
        if (this.slices.containsKey(id)) {
            throw new InvalidSliceException((ISliceContext)this.info, slice + " has a duplicate id, '" + id + "' was already defined");
        }
        this.slices.put(id, slice);
    }
    
    public MethodSlice get(final String id) {
        return this.slices.get(id);
    }
    
    @Override
    public String toString() {
        return String.format("MethodSlices%s", this.slices.keySet());
    }
    
    public static MethodSlices parse(final InjectionInfo info) {
        final MethodSlices slices = new MethodSlices(info);
        final AnnotationNode annotation = info.getAnnotation();
        if (annotation != null) {
            for (final AnnotationNode node : Annotations.getValue(annotation, "slice", true)) {
                final MethodSlice slice = MethodSlice.parse((ISliceContext)info, node);
                slices.add(slice);
            }
        }
        return slices;
    }
}
