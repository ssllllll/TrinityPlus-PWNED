//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.json.simple.parser;

import java.io.*;

public interface ContentHandler
{
    void startJSON() throws ParseException, IOException;
    
    void endJSON() throws ParseException, IOException;
    
    boolean startObject() throws ParseException, IOException;
    
    boolean endObject() throws ParseException, IOException;
    
    boolean startObjectEntry(final String p0) throws ParseException, IOException;
    
    boolean endObjectEntry() throws ParseException, IOException;
    
    boolean startArray() throws ParseException, IOException;
    
    boolean endArray() throws ParseException, IOException;
    
    boolean primitive(final Object p0) throws ParseException, IOException;
}
