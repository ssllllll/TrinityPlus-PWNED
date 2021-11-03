//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.service;

import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.tools.obfuscation.*;
import javax.tools.*;
import java.util.*;

public final class ObfuscationServices
{
    private static ObfuscationServices instance;
    private final ServiceLoader<IObfuscationService> serviceLoader;
    private final Set<IObfuscationService> services;
    
    private ObfuscationServices() {
        this.services = new HashSet<IObfuscationService>();
        this.serviceLoader = ServiceLoader.load(IObfuscationService.class, this.getClass().getClassLoader());
    }
    
    public static ObfuscationServices getInstance() {
        if (ObfuscationServices.instance == null) {
            ObfuscationServices.instance = new ObfuscationServices();
        }
        return ObfuscationServices.instance;
    }
    
    public void initProviders(final IMixinAnnotationProcessor ap) {
        try {
            for (final IObfuscationService service : this.serviceLoader) {
                if (!this.services.contains(service)) {
                    this.services.add(service);
                    final String serviceName = service.getClass().getSimpleName();
                    final Collection<ObfuscationTypeDescriptor> obfTypes = (Collection<ObfuscationTypeDescriptor>)service.getObfuscationTypes();
                    if (obfTypes == null) {
                        continue;
                    }
                    for (final ObfuscationTypeDescriptor obfType : obfTypes) {
                        try {
                            final ObfuscationType type = ObfuscationType.create(obfType, ap);
                            ap.printMessage(Diagnostic.Kind.NOTE, (CharSequence)(serviceName + " supports type: \"" + type + "\""));
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (ServiceConfigurationError serviceError) {
            ap.printMessage(Diagnostic.Kind.ERROR, (CharSequence)(serviceError.getClass().getSimpleName() + ": " + serviceError.getMessage()));
            serviceError.printStackTrace();
        }
    }
    
    public Set<String> getSupportedOptions() {
        final Set<String> supportedOptions = new HashSet<String>();
        for (final IObfuscationService provider : this.serviceLoader) {
            final Set<String> options = (Set<String>)provider.getSupportedOptions();
            if (options != null) {
                supportedOptions.addAll(options);
            }
        }
        return supportedOptions;
    }
    
    public IObfuscationService getService(final Class<? extends IObfuscationService> serviceClass) {
        for (final IObfuscationService service : this.serviceLoader) {
            if (serviceClass.getName().equals(service.getClass().getName())) {
                return service;
            }
        }
        return null;
    }
}
