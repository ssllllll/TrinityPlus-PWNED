//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.bridge;

import org.objectweb.asm.commons.*;
import org.spongepowered.asm.mixin.extensibility.*;
import java.lang.reflect.*;

public final class RemapperAdapterFML extends RemapperAdapter
{
    private static final String DEOBFUSCATING_REMAPPER_CLASS = "fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
    private static final String DEOBFUSCATING_REMAPPER_CLASS_FORGE = "net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
    private static final String DEOBFUSCATING_REMAPPER_CLASS_LEGACY = "cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
    private static final String INSTANCE_FIELD = "INSTANCE";
    private static final String UNMAP_METHOD = "unmap";
    private final Method mdUnmap;
    
    private RemapperAdapterFML(final Remapper remapper, final Method mdUnmap) {
        super(remapper);
        this.logger.info("Initialised Mixin FML Remapper Adapter with {}", new Object[] { remapper });
        this.mdUnmap = mdUnmap;
    }
    
    public String unmap(final String typeName) {
        try {
            return this.mdUnmap.invoke(this.remapper, typeName).toString();
        }
        catch (Exception ex) {
            return typeName;
        }
    }
    
    public static IRemapper create() {
        try {
            final Class<?> clDeobfRemapper = getFMLDeobfuscatingRemapper();
            final Field singletonField = clDeobfRemapper.getDeclaredField("INSTANCE");
            final Method mdUnmap = clDeobfRemapper.getDeclaredMethod("unmap", String.class);
            final Remapper remapper = (Remapper)singletonField.get(null);
            return (IRemapper)new RemapperAdapterFML(remapper, mdUnmap);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static Class<?> getFMLDeobfuscatingRemapper() throws ClassNotFoundException {
        try {
            return Class.forName("net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
        }
        catch (ClassNotFoundException ex) {
            return Class.forName("cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
        }
    }
}
