import java.io.File;


public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        File source = new File("imgs/heart.jpg");
        FotoFun uploaded = FotoFun.uploadJpg(source);
        File copy = new File("imgs/heart_copy.jpg");
        uploaded.save(copy);
        System.out.println("Copied " + source.getName() + " to " + copy.getName());
    }
}
