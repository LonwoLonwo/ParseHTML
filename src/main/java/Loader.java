import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.nio.file.Files;
import java.util.Set;

public class Loader {
    public static void main(String[] args) {

        String filePath = "src\\main\\resources\\data\\code.html";
        String htmlFile = parseFile(filePath);

        Document doc = Jsoup.parse(htmlFile);

        Set<String> links = new HashSet<>();

        Elements elements = doc.select("img[src~=(?i)\\.(png|jpe?g)]");
        for(Element e : elements){
            links.add(e.attr("src"));
        }

        savePictures(links);

    }

    private static String parseFile(String path){
        StringBuilder builder = new StringBuilder();

        try{
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> builder.append(line + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    private static void savePictures(Set<String> links){
        int i = 0;
        try {
            for(String link : links) {
                i++;
                URL url = new URL(link);
                BufferedImage img = ImageIO.read(url);
                if(link.endsWith(".jpg")) {
                    String pathName = "src\\main\\resources\\images\\00" + i + ".jpg";
                    File file = new File(pathName);
                    ImageIO.write(img, "jpg", file);
                }
                else if(link.endsWith(".png")){
                    String pathName = "src\\main\\resources\\images\\00" + i + ".png";
                    File file = new File(pathName);
                    ImageIO.write(img, "png", file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
