// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class ImgurUploader extends Module
{
    public static final Value<Boolean> CopyToClipboard;
    
    public ImgurUploader() {
        super("ImgurUploader", new String[] { "ImgUpload" }, "Automatically uploads images taken with F2 to imgur.", "NONE", -1, ModuleType.MISC);
    }
    
    static {
        CopyToClipboard = new Value<Boolean>("Copy", new String[] { "Clipboard" }, "Copies the link to the clipboard", true);
    }
}
