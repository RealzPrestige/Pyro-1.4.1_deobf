//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.imgur;

import com.google.gson.JsonObject;
import java.io.IOException;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.TextComponentString;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.io.OutputStreamWriter;
import java.util.Base64;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.io.ByteArrayOutputStream;

public class ImgurUploader
{
    private ByteArrayOutputStream baos;
    private ExecutorService exService;
    private CopyToClipboard copyToClipboard;
    
    public ImgurUploader() {
        this.baos = new ByteArrayOutputStream();
        this.exService = Executors.newCachedThreadPool();
        this.copyToClipboard = new CopyToClipboard();
    }
    
    public void uploadImage(final BufferedImage bufferedImage) {
        int responseCode;
        URL url;
        HttpURLConnection con;
        byte[] imageInByte;
        String encoded;
        OutputStreamWriter wr;
        String data;
        final BufferedReader bufferedReader;
        BufferedReader rd;
        StringBuilder stb;
        String line;
        final Object o;
        JsonObject jsonObject;
        String result;
        ITextComponent uploadstr;
        ITextComponent linkText;
        final TextComponentString textComponentString;
        ITextComponent errorText;
        ITextComponent report;
        ITextComponent link;
        ITextComponent hover;
        this.exService.execute(() -> {
            Thread.currentThread().setName("Imgur Image Uploading");
            responseCode = 0;
            try {
                url = new URL("https://api.imgur.com/3/image");
                con = (HttpURLConnection)url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", "Client-ID bfea9c11835d95c");
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.connect();
                Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Uploading image...", true);
                ImageIO.write(bufferedImage, "png", this.baos);
                this.baos.flush();
                imageInByte = this.baos.toByteArray();
                encoded = Base64.getEncoder().encodeToString(imageInByte);
                wr = new OutputStreamWriter(con.getOutputStream());
                data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(encoded, "UTF-8");
                wr.write(data);
                wr.flush();
                new BufferedReader(new InputStreamReader(con.getInputStream()));
                rd = bufferedReader;
                stb = new StringBuilder();
                while (true) {
                    line = rd.readLine();
                    if (o != null) {
                        stb.append(line).append("\n");
                    }
                    else {
                        break;
                    }
                }
                responseCode = con.getResponseCode();
                System.out.println("Response Code: " + responseCode);
                wr.close();
                rd.close();
                jsonObject = new JsonParser().parse(stb.toString()).getAsJsonObject();
                result = jsonObject.get("data").getAsJsonObject().get("link").getAsString();
                uploadstr = (ITextComponent)new TextComponentString("Uploaded to ");
                linkText = (ITextComponent)new TextComponentString(result);
                linkText.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result));
                linkText.getStyle().setUnderlined(Boolean.valueOf(true));
                linkText.getStyle().setColor(TextFormatting.AQUA);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(uploadstr.appendSibling(linkText));
                if (dev.nuker.pyro.deobfuscated.module.misc.ImgurUploader.CopyToClipboard.getValue()) {
                    if (this.copyToClipboard.copy(result)) {
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString("Copied to clipboard!"));
                        System.out.println("Copied " + result + " to clipboard");
                    }
                    else {
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString("Unable to save to clipboard"));
                        System.out.println("Unable to save " + result + "to clipboard");
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                new TextComponentString("Something went wrong! Response code: " + responseCode);
                errorText = (ITextComponent)textComponentString;
                report = (ITextComponent)new TextComponentString("If this keeps happening please report the issue ");
                link = (ITextComponent)new TextComponentString("here");
                hover = (ITextComponent)new TextComponentString("github.com/DarkEyeDragon/ScreenshotUploader/issues");
                link.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/DarkEyeDragon/ScreenshotUploader/issues"));
                link.getStyle().setColor(TextFormatting.AQUA);
                link.getStyle().setUnderlined(Boolean.valueOf(true));
                link.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(errorText);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(report.appendSibling(link));
            }
        });
    }
}
