//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.client;

import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.setting.rewrite.*;

public class TestModule extends Module
{
    private static final BooleanSetting one;
    private static final BooleanSetting two;
    private static final BooleanSetting three;
    private static final TextBoxSetting text;
    private static final BooleanSetting var1;
    private static final BooleanSetting predicate1;
    private static final ModeSetting var2;
    private static final BooleanSetting predicate2;
    
    public TestModule() {
        super("TestModule", "Testing purposes", Category.CLIENT);
    }
    
    static {
        one = new BooleanSetting("One", true);
        two = new BooleanSetting("Two", TestModule.one, true);
        three = new BooleanSetting("Three", TestModule.two, true);
        text = new TextBoxSetting("TextBox", "test");
        var1 = new BooleanSetting("Var1", true);
        predicate1 = new BooleanSetting("Test2", true, s -> TestModule.var1.getValue());
        var2 = new ModeSetting("Var2", "popbob", new String[] { "fitmc", "popbob", "cringe" });
        predicate2 = new BooleanSetting("Test2", true, s -> TestModule.var2.is("cringe"));
    }
}
