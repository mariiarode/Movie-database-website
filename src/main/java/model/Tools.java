package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Tools {
    public static String parsePage(String input, String valid) {
        String output = "main";
        String[] pages = valid.split(";");
        if (input == null) {
            input = "main";
        }

        String[] var4 = pages;
        int var5 = pages.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String proper = var4[var6];
            if (input.equals(proper)) {
                return input;
            }
        }

        return output;
    }


    public static byte[] downloadImage(String imageUrl) throws IOException {
        try (InputStream in = new URL(imageUrl).openStream();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) != -1) {
                baos.write(buffer, 0, n);
            }
            return baos.toByteArray();
        }
    }
}
