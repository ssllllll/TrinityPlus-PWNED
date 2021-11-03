//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;

public class InvokerInfo extends AccessorInfo
{
    public InvokerInfo(final MixinTargetContext mixin, final MethodNode method) {
        super(mixin, method, (Class)Invoker.class);
    }
    
    protected AccessorInfo.AccessorType initType() {
        return AccessorInfo.AccessorType.METHOD_PROXY;
    }
    
    protected Type initTargetFieldType() {
        return null;
    }
    
    protected MemberInfo initTarget() {
        return new MemberInfo(this.getTargetName(), null, this.method.desc);
    }
    
    public void locate() {
        this.targetMethod = this.findTargetMethod();
    }
    
    private MethodNode findTargetMethod() {
        return (MethodNode)this.findTarget(this.classNode.methods);
    }
}
