//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.transformer.debug;

import java.io.*;
import java.util.concurrent.*;

public class RuntimeDecompilerAsync extends RuntimeDecompiler implements Runnable, Thread.UncaughtExceptionHandler
{
    private final BlockingQueue<File> queue;
    private final Thread thread;
    private boolean run;
    
    public RuntimeDecompilerAsync(final File outputPath) {
        super(outputPath);
        this.queue = new LinkedBlockingQueue<File>();
        this.run = true;
        (this.thread = new Thread(this, "Decompiler thread")).setDaemon(true);
        this.thread.setPriority(1);
        this.thread.setUncaughtExceptionHandler(this);
        this.thread.start();
    }
    
    public void decompile(final File file) {
        if (this.run) {
            this.queue.offer(file);
        }
        else {
            super.decompile(file);
        }
    }
    
    public void run() {
        while (this.run) {
            try {
                final File file = this.queue.take();
                super.decompile(file);
            }
            catch (InterruptedException ex2) {
                this.run = false;
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void uncaughtException(final Thread thread, final Throwable ex) {
        this.logger.error("Async decompiler encountered an error and will terminate. Further decompile requests will be handled synchronously. {} {}", new Object[] { ex.getClass().getName(), ex.getMessage() });
        this.flush();
    }
    
    private void flush() {
        this.run = false;
        File file;
        while ((file = this.queue.poll()) != null) {
            this.decompile(file);
        }
    }
}
