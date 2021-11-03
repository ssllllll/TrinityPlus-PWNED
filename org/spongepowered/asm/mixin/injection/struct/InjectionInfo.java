//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.struct.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.refmap.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.*;
import com.google.common.base.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;

public abstract class InjectionInfo extends SpecialMethodInfo implements ISliceContext
{
    protected final boolean isStatic;
    protected final Deque<MethodNode> targets;
    protected final MethodSlices slices;
    protected final String atKey;
    protected final List<InjectionPoint> injectionPoints;
    protected final Map<Target, List<InjectionNodes.InjectionNode>> targetNodes;
    protected Injector injector;
    protected InjectorGroupInfo group;
    private final List<MethodNode> injectedMethods;
    private int expectedCallbackCount;
    private int requiredCallbackCount;
    private int maxCallbackCount;
    private int injectedCallbackCount;
    
    protected InjectionInfo(final MixinTargetContext mixin, final MethodNode method, final AnnotationNode annotation) {
        this(mixin, method, annotation, "at");
    }
    
    protected InjectionInfo(final MixinTargetContext mixin, final MethodNode method, final AnnotationNode annotation, final String atKey) {
        super(mixin, method, annotation);
        this.targets = new ArrayDeque<MethodNode>();
        this.injectionPoints = new ArrayList<InjectionPoint>();
        this.targetNodes = new LinkedHashMap<Target, List<InjectionNodes.InjectionNode>>();
        this.injectedMethods = new ArrayList<MethodNode>(0);
        this.expectedCallbackCount = 1;
        this.requiredCallbackCount = 0;
        this.maxCallbackCount = Integer.MAX_VALUE;
        this.injectedCallbackCount = 0;
        this.isStatic = Bytecode.methodIsStatic(method);
        this.slices = MethodSlices.parse(this);
        this.atKey = atKey;
        this.readAnnotation();
    }
    
    protected void readAnnotation() {
        if (this.annotation == null) {
            return;
        }
        final String type = "@" + Bytecode.getSimpleName(this.annotation);
        final List<AnnotationNode> injectionPoints = this.readInjectionPoints(type);
        this.findMethods(this.parseTargets(type), type);
        this.parseInjectionPoints(injectionPoints);
        this.parseRequirements();
        this.injector = this.parseInjector(this.annotation);
    }
    
    protected Set<MemberInfo> parseTargets(final String type) {
        final List<String> methods = Annotations.getValue(this.annotation, "method", false);
        if (methods == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing method name", type, this.method.name));
        }
        final Set<MemberInfo> members = new LinkedHashSet<MemberInfo>();
        for (final String method : methods) {
            try {
                final MemberInfo targetMember = MemberInfo.parseAndValidate(method, this.mixin);
                if (targetMember.owner != null && !targetMember.owner.equals(this.mixin.getTargetClassRef())) {
                    throw new InvalidInjectionException(this, String.format("%s annotation on %s specifies a target class '%s', which is not supported", type, this.method.name, targetMember.owner));
                }
                members.add(targetMember);
            }
            catch (InvalidMemberDescriptorException ex) {
                throw new InvalidInjectionException(this, String.format("%s annotation on %s, has invalid target descriptor: \"%s\". %s", type, this.method.name, method, this.mixin.getReferenceMapper().getStatus()));
            }
        }
        return members;
    }
    
    protected List<AnnotationNode> readInjectionPoints(final String type) {
        final List<AnnotationNode> ats = Annotations.getValue(this.annotation, this.atKey, false);
        if (ats == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing '%s' value(s)", type, this.method.name, this.atKey));
        }
        return ats;
    }
    
    protected void parseInjectionPoints(final List<AnnotationNode> ats) {
        this.injectionPoints.addAll(InjectionPoint.parse((IMixinContext)this.mixin, this.method, this.annotation, (List)ats));
    }
    
    protected void parseRequirements() {
        this.group = this.mixin.getInjectorGroups().parseGroup(this.method, this.mixin.getDefaultInjectorGroup()).add(this);
        final Integer expect = Annotations.getValue(this.annotation, "expect");
        if (expect != null) {
            this.expectedCallbackCount = expect;
        }
        final Integer require = Annotations.getValue(this.annotation, "require");
        if (require != null && require > -1) {
            this.requiredCallbackCount = require;
        }
        else if (this.group.isDefault()) {
            this.requiredCallbackCount = this.mixin.getDefaultRequiredInjections();
        }
        final Integer allow = Annotations.getValue(this.annotation, "allow");
        if (allow != null) {
            this.maxCallbackCount = Math.max(Math.max(this.requiredCallbackCount, 1), allow);
        }
    }
    
    protected abstract Injector parseInjector(final AnnotationNode p0);
    
    public boolean isValid() {
        return this.targets.size() > 0 && this.injectionPoints.size() > 0;
    }
    
    public void prepare() {
        this.targetNodes.clear();
        for (final MethodNode targetMethod : this.targets) {
            final Target target = this.mixin.getTargetMethod(targetMethod);
            final InjectorTarget injectorTarget = new InjectorTarget((ISliceContext)this, target);
            this.targetNodes.put(target, this.injector.find(injectorTarget, (List)this.injectionPoints));
            injectorTarget.dispose();
        }
    }
    
    public void inject() {
        for (final Map.Entry<Target, List<InjectionNodes.InjectionNode>> entry : this.targetNodes.entrySet()) {
            this.injector.inject((Target)entry.getKey(), (List)entry.getValue());
        }
        this.targets.clear();
    }
    
    public void postInject() {
        for (final MethodNode method : this.injectedMethods) {
            this.classNode.methods.add(method);
        }
        final String description = this.getDescription();
        final String refMapStatus = this.mixin.getReferenceMapper().getStatus();
        final String dynamicInfo = this.getDynamicInfo();
        if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.injectedCallbackCount < this.expectedCallbackCount) {
            throw new InvalidInjectionException(this, String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded. %s%s", description, this.method.name, this.method.desc, this.mixin, this.expectedCallbackCount, this.injectedCallbackCount, refMapStatus, dynamicInfo));
        }
        if (this.injectedCallbackCount < this.requiredCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded. %s%s", description, this.method.name, this.method.desc, this.mixin, this.injectedCallbackCount, this.requiredCallbackCount, refMapStatus, dynamicInfo));
        }
        if (this.injectedCallbackCount > this.maxCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, %d succeeded of %d allowed.%s", description, this.method.name, this.method.desc, this.mixin, this.injectedCallbackCount, this.maxCallbackCount, dynamicInfo));
        }
    }
    
    public void notifyInjected(final Target target) {
    }
    
    protected String getDescription() {
        return "Callback method";
    }
    
    public String toString() {
        return describeInjector(this.mixin, this.annotation, this.method);
    }
    
    public Collection<MethodNode> getTargets() {
        return this.targets;
    }
    
    public MethodSlice getSlice(final String id) {
        return this.slices.get(this.getSliceId(id));
    }
    
    public String getSliceId(final String id) {
        return "";
    }
    
    public int getInjectedCallbackCount() {
        return this.injectedCallbackCount;
    }
    
    public MethodNode addMethod(final int access, final String name, final String desc) {
        final MethodNode method = new MethodNode(327680, access | 0x1000, name, desc, (String)null, (String[])null);
        this.injectedMethods.add(method);
        return method;
    }
    
    public void addCallbackInvocation(final MethodNode handler) {
        ++this.injectedCallbackCount;
    }
    
    private void findMethods(final Set<MemberInfo> searchFor, final String type) {
        this.targets.clear();
        final int passes = this.mixin.getEnvironment().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? 2 : 1;
        for (MemberInfo member : searchFor) {
            for (int count = 0, pass = 0; pass < passes && count < 1; ++pass) {
                int ordinal = 0;
                for (final MethodNode target : this.classNode.methods) {
                    if (member.matches(target.name, target.desc, ordinal)) {
                        final boolean isMixinMethod = Annotations.getVisible(target, MixinMerged.class) != null;
                        if (member.matchAll) {
                            if (Bytecode.methodIsStatic(target) != this.isStatic || target == this.method) {
                                continue;
                            }
                            if (isMixinMethod) {
                                continue;
                            }
                        }
                        this.checkTarget(target);
                        this.targets.add(target);
                        ++ordinal;
                        ++count;
                    }
                }
                member = member.transform(null);
            }
        }
        if (this.targets.size() == 0) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s could not find any targets matching %s in the target class %s. %s%s", type, this.method.name, namesOf(searchFor), this.mixin.getTarget(), this.mixin.getReferenceMapper().getStatus(), this.getDynamicInfo()));
        }
    }
    
    private void checkTarget(final MethodNode target) {
        final AnnotationNode merged = Annotations.getVisible(target, MixinMerged.class);
        if (merged == null) {
            return;
        }
        final String owner = Annotations.getValue(merged, "mixin");
        final int priority = Annotations.getValue(merged, "priority");
        if (priority >= this.mixin.getPriority() && !owner.equals(this.mixin.getClassName())) {
            throw new InvalidInjectionException(this, String.format("%s cannot inject into %s::%s%s merged by %s with priority %d", this, this.classNode.name, target.name, target.desc, owner, priority));
        }
        if (Annotations.getVisible(target, (Class<? extends Annotation>)Final.class) != null) {
            throw new InvalidInjectionException(this, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", this, this.classNode.name, target.name, target.desc, owner));
        }
    }
    
    protected String getDynamicInfo() {
        final AnnotationNode annotation = Annotations.getInvisible(this.method, (Class<? extends Annotation>)Dynamic.class);
        String description = Strings.nullToEmpty((String)Annotations.getValue(annotation));
        final Type upstream = Annotations.getValue(annotation, "mixin");
        if (upstream != null) {
            description = String.format("{%s} %s", upstream.getClassName(), description).trim();
        }
        return (description.length() > 0) ? String.format(" Method is @Dynamic(%s)", description) : "";
    }
    
    public static InjectionInfo parse(final MixinTargetContext mixin, final MethodNode method) {
        final AnnotationNode annotation = getInjectorAnnotation(mixin.getMixin(), method);
        if (annotation == null) {
            return null;
        }
        if (annotation.desc.endsWith(Inject.class.getSimpleName() + ";")) {
            return (InjectionInfo)new CallbackInjectionInfo(mixin, method, annotation);
        }
        if (annotation.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
            return new ModifyArgInjectionInfo(mixin, method, annotation);
        }
        if (annotation.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
            return new ModifyArgsInjectionInfo(mixin, method, annotation);
        }
        if (annotation.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
            return new RedirectInjectionInfo(mixin, method, annotation);
        }
        if (annotation.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
            return new ModifyVariableInjectionInfo(mixin, method, annotation);
        }
        if (annotation.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
            return new ModifyConstantInjectionInfo(mixin, method, annotation);
        }
        return null;
    }
    
    public static AnnotationNode getInjectorAnnotation(final IMixinInfo mixin, final MethodNode method) {
        AnnotationNode annotation = null;
        try {
            annotation = Annotations.getSingleVisible(method, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
        }
        catch (IllegalArgumentException ex) {
            throw new InvalidMixinException(mixin, String.format("Error parsing annotations on %s in %s: %s", method.name, mixin.getClassName(), ex.getMessage()));
        }
        return annotation;
    }
    
    public static String getInjectorPrefix(final AnnotationNode annotation) {
        if (annotation != null) {
            if (annotation.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
                return "modify";
            }
            if (annotation.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
                return "args";
            }
            if (annotation.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
                return "redirect";
            }
            if (annotation.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
                return "localvar";
            }
            if (annotation.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
                return "constant";
            }
        }
        return "handler";
    }
    
    static String describeInjector(final IMixinContext mixin, final AnnotationNode annotation, final MethodNode method) {
        return String.format("%s->@%s::%s%s", mixin.toString(), Bytecode.getSimpleName(annotation), method.name, method.desc);
    }
    
    private static String namesOf(final Collection<MemberInfo> members) {
        int index = 0;
        final int count = members.size();
        final StringBuilder sb = new StringBuilder();
        for (final MemberInfo member : members) {
            if (index > 0) {
                if (index == count - 1) {
                    sb.append(" or ");
                }
                else {
                    sb.append(", ");
                }
            }
            sb.append('\'').append(member.name).append('\'');
            ++index;
        }
        return sb.toString();
    }
}
