// java
package fotofun;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting FotoFun...");

        String inputPath = "src/fotofun/imgs/IMG_8600.jpg";
        File source = new File(inputPath);
        if (!source.exists()) {
            System.err.println("Source not found: " + source.getPath());
            return;
        }

        System.out.println("Loading: " + source.getPath());
        try {
            FotoFun original = FotoFun.uploadJpg(source);

            String parent = source.getParent();
            String name = source.getName();
            int dot = name.lastIndexOf('.');
            String base = dot > 0 ? name.substring(0, dot) : name;
            String ext = dot > 0 ? name.substring(dot) : ".jpg";

            File shiftedFile = new File(parent, base + "_shifted" + ext);
            File brightFile  = new File(parent, base + "_bright" + ext);

            System.out.println("Creating shifted copy...");
            FotoFun shifted = original.copy();
            shifted.shiftColors();
            shifted.save(shiftedFile);
            System.out.println("Saved: " + shiftedFile.getPath());

            System.out.println("Creating brightened copy...");
            FotoFun bright = original.copy();
            bright.brighten();
            bright.save(brightFile);
            System.out.println("Saved: " + brightFile.getPath());
        } catch (Exception e) {
            System.err.println("Processing failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
