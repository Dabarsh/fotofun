package fotofun;

import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import java.awt.image.*;

public class FotoFun {
    private final BufferedImage image;
    private final String format;

    public FotoFun(File sourceFile) throws Exception {
        image = ImageIO.read(sourceFile);
        format = fileFormat(sourceFile.getName());
    }

    private FotoFun(BufferedImage image, String format) {
        this.image = image;
        this.format = format;
    }

    public void save(File destinationFile) throws Exception {
        ImageIO.write(image, fileFormat(destinationFile.getName()), destinationFile);
    }

    public FotoFun copy() {
        BufferedImage clone = new BufferedImage(image.getWidth(), image.getHeight(),
                image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType());
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
                int a = (rgb >> 24) & 0xFF;
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int newR = g; // R becomes old G
                int newG = b; // G becomes old B
                int newB = r; // B becomes old R

                int newRgb = (a << 24) | (newR << 16) | (newG << 8) | newB;
                image.setRGB(x, y, newRgb);
            }
        }
    }

    private static String fileFormat(String name) {
        int dot = name.lastIndexOf('.') + 1;
        return dot == 0 ? "png" : name.substring(dot).toLowerCase();
    }
}
