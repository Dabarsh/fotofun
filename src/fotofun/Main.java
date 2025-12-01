package fotofun;

import java.io.File;


public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting FotoFun...\n");
        File source = new File("src/fotofun/imgs/heart.jpg");
        System.out.println("Loading: " + source.getPath());
        FotoFun uploaded = FotoFun.uploadJpg(source);

        System.out.println("Shifting colors (R->G, G->B, B->R)...");
        uploaded.shiftColors();

        File copy = new File("src/fotofun/imgs/heart_shifted.jpg");
        uploaded.save(copy);
        System.out.println("Copied " + source.getName() + " to " + copy.getName());
    }
}
