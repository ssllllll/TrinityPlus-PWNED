//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.struct;

import javax.tools.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.annotation.processing.*;

public class InjectorRemap
{
    private final boolean remap;
    private Message message;
    private int remappedCount;
    
    public InjectorRemap(final boolean remap) {
        this.remap = remap;
    }
    
    public boolean shouldRemap() {
        return this.remap;
    }
    
    public void notifyRemapped() {
        ++this.remappedCount;
        this.clearMessage();
    }
    
    public void addMessage(final Diagnostic.Kind kind, final CharSequence msg, final Element element, final AnnotationHandle annotation) {
        this.message = new Message(kind, msg, element, annotation);
    }
    
    public void clearMessage() {
        this.message = null;
    }
    
    public void dispatchPendingMessages(final Messager messager) {
        if (this.remappedCount == 0 && this.message != null) {
            this.message.sendTo(messager);
        }
    }
}
