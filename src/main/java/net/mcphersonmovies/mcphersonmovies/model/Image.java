package net.mcphersonmovies.mcphersonmovies.model;

import jakarta.servlet.http.Part;
import org.apache.hc.client5.http.utils.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Image {
    private String fileName;
    private byte[] image;
    private String encodedImage;

    public Image() {}

    public Image(String fileName, byte[] image) {
        this.fileName = fileName;
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public void EncodeImage() {
        encodedImage = new String(Base64.encodeBase64(image), StandardCharsets.UTF_8);
    }

    public static String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename;
            }
        }
        return null;
    }
}
