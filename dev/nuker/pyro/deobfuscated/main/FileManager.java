// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.FileInputStream;

import dev.nuker.pyro.deobfuscated.gui.altmanager.Alt;
import dev.nuker.pyro.deobfuscated.gui.altmanager.Manager;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import dev.nuker.pyro.deobfuscated.managers.DirectoryManager;
import java.io.File;

public class FileManager
{
    public File salDir;
    
    public FileManager() {
        try {
            this.salDir = new File(DirectoryManager.Get().GetCurrentDirectory() + File.separator + "Pyro");
            if (!this.salDir.exists()) {
                this.salDir.mkdirs();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void saveAlts() {
        try {
            final File file = new File(this.salDir.getAbsolutePath(), "alts.txt");
            final PrintWriter writer = new PrintWriter(new FileWriter(file));
            for (final Alt alt : Manager.altList) {
                writer.println(alt.getFileLine());
            }
            writer.close();
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
    
    public void loadAlts() {
        try {
            final File file = new File(this.salDir.getAbsolutePath(), "alts.txt");
            final FileInputStream fstream = new FileInputStream(file);
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String rLine;
            while ((rLine = br.readLine()) != null) {
                final String curLine = rLine;
                try {
                    if (curLine.contains(":") && !curLine.trim().endsWith(":")) {
                        final String[] altInfo = curLine.split(":");
                        final Alt theAlt = new Alt(altInfo[0], altInfo[1]);
                        if (Manager.altList.contains(theAlt)) {
                            continue;
                        }
                        Manager.altList.add(theAlt);
                    }
                    else {
                        if (curLine.isEmpty() || curLine == null || curLine.trim().isEmpty()) {
                            continue;
                        }
                        final Alt theAlt2 = new Alt(curLine.replace(":", "").trim());
                        if (Manager.altList.contains(theAlt2)) {
                            continue;
                        }
                        Manager.altList.add(theAlt2);
                    }
                }
                catch (Exception error) {
                    error.printStackTrace();
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
