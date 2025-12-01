package fotofun;

import java.io.File;


public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting FotoFun...\n");
        File source = new File("src/fotofun/imgs/bird.jpg");
        System.out.println("Loading: " + source.getPath());
        FotoFun original = FotoFun.uploadJpg(source);

        // Create the shifted variant
        System.out.println("Shifting colors (R->G, G->B, B->R) on a copy of the original...");
        FotoFun shifted = original.copy();
        shifted.shiftColors();
        File copy = new File("src/fotofun/imgs/bird_shifted.jpg");
        shifted.save(copy);
        System.out.println("Saved shifted copy to " + copy.getName());

        // Create brightened variant
        System.out.println("Brightening image on a fresh copy of the original...");
        FotoFun bright = original.copy();
        bright.brighten();
        File brightFile = new File("src/fotofun/imgs/bird_bright.jpg");
        bright.save(brightFile);
        System.out.println("Saved brightened copy to " + brightFile.getName());
    }
}
