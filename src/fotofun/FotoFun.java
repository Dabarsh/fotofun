package fotofun;

import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.Color;

public class FotoFun {
    private final BufferedImage image;
    private final String format;

    public FotoFun(File sourceFile) throws Exception {
        BufferedImage read = ImageIO.read(sourceFile);
        if (read == null) {
            throw new IOException("Unable to read image data from " + sourceFile.getName());
        }
        BufferedImage converted = new BufferedImage(read.getWidth(), read.getHeight(), BufferedImage.TYPE_INT_RGB);
        converted.getGraphics().drawImage(read, 0, 0, null);
        this.image = converted;
        format = fileFormat(sourceFile.getName());
    }

    private FotoFun(BufferedImage image, String format) {
        if (image.getType() != BufferedImage.TYPE_INT_RGB) {
            BufferedImage converted = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            converted.getGraphics().drawImage(image, 0, 0, null);
            this.image = converted;
        } else {
            this.image = image;
        }
        this.format = format;
    }

    public void save(File destinationFile) throws Exception {
        ImageIO.write(image, fileFormat(destinationFile.getName()), destinationFile);
    }

    public FotoFun copy() {
        BufferedImage clone = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        clone.getGraphics().drawImage(image, 0, 0, null);
        return new FotoFun(clone, format);
    }

    public static FotoFun uploadJpg(File sourceFile) throws Exception {
        if (sourceFile == null) {
            throw new IllegalArgumentException("Source file must not be null.");
        }
        String detectedFormat = fileFormat(sourceFile.getName());
        if (!"jpg".equals(detectedFormat) && !"jpeg".equals(detectedFormat)) {
            throw new IllegalArgumentException("Only JPG images are supported.");
        }
        BufferedImage uploaded = ImageIO.read(sourceFile);
        if (uploaded == null) {
            throw new IOException("Unable to read image data from " + sourceFile.getName());
        }
        return new FotoFun(uploaded, "jpg").copy();
    }
    public void shiftColors() {
        int w = image.getWidth();
        int h = image.getHeight();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                Color c = new Color(rgb, false);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                int newR = g; // R becomes old G
                int newG = b; // G becomes old B
                int newB = r; // B becomes old R

                int newRgb = (newR << 16) | (newG << 8) | newB;
                image.setRGB(x, y, newRgb);
            }
        }
    }
    public void brighten() {
        final int INCREMENT = 100; // increase per channel (0-255)
        int w = image.getWidth();
        int h = image.getHeight();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                Color c = new Color(rgb, false);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                int newR = r + INCREMENT;
                if (newR > 255) newR = 255;
                int newG = g + INCREMENT;
                if (newG > 255) newG = 255;
                int newB = b + INCREMENT;
                if (newB > 255) newB = 255;

                int newRgb = (newR << 16) | (newG << 8) | newB;
                image.setRGB(x, y, newRgb);
            }
        }
    }

    public void grayscale() {
        int w = image.getWidth();
        int h = image.getHeight();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                Color c = new Color(rgb, false);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                int gray = (r + g + b) / 3;
                int newRgb = (gray << 16) | (gray << 8) | gray;
                image.setRGB(x, y, newRgb);
            }
        }
    }

    public void blackAndWhite() {
        int w = image.getWidth();
        int h = image.getHeight();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                Color c = new Color(rgb, false);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                int avg = (r + g + b) / 3;
                int bw = (avg >= 128) ? 255 : 0; // threshold at mid-point
                int newRgb = (bw << 16) | (bw << 8) | bw;
                image.setRGB(x, y, newRgb);
            }
        }
    }

    public void invert() {
        int w = image.getWidth();
        int h = image.getHeight();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = image.getRGB(x, y);
                Color c = new Color(rgb, false);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                int newR = 255 - r;
                int newG = 255 - g;
                int newB = 255 - b;

                int newRgb = (newR << 16) | (newG << 8) | newB;
                image.setRGB(x, y, newRgb);
            }
        }
    }

    private static String fileFormat(String name) {
        int dot = name.lastIndexOf('.') + 1;
        return dot == 0 ? "png" : name.substring(dot).toLowerCase();
    }
}
