package io.github.gifDecompiler;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GifSplitter {

    public List<BufferedImage> splitGif(File file, boolean write) throws IOException {
        List<BufferedImage> list = new ArrayList<>();
        ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
        reader.setInput(ImageIO.createImageInputStream(new FileInputStream(file)), false);
        BufferedImage lastImage = reader.read(0);

        list.add(lastImage);
        if (write) {
            ImageIO.write(lastImage, "PNG", new File(0 + ".png"));
        }


        for (int i = 1; i < reader.getNumImages(true); i++) {
            BufferedImage image = makeImageForIndex(reader, i, lastImage);
            list.add(image);

            if (write) {
                ImageIO.write(image, "PNG", new File(i + ".png"));
            }
        }

        return list;
    }

    private BufferedImage makeImageForIndex(ImageReader reader, int index, BufferedImage lastImage) throws IOException {
        BufferedImage image = reader.read(index);
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        if (lastImage != null) {
            newImage.getGraphics().drawImage(lastImage, 0, 0, null);
        }
        newImage.getGraphics().drawImage(image, 0, 0, null);

        return newImage;
    }
}
