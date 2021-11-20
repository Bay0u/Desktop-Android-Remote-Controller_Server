import net.coobird.thumbnailator.Thumbnails;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.Arrays;

import javax.imageio.ImageIO;


public class server {

    public static void main(String[] args) {
        try {
            Robot robot = new Robot();

            BufferedImage screenShot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            BufferedImage newSizedImage = Thumbnails.of(screenShot)
                    .outputFormat("jpg")
                    .size(800, 600)
                    .keepAspectRatio(true)
                    .asBufferedImage();

            File file = new File("screen-capture.jpg");
            boolean status = ImageIO.write(newSizedImage, "jpg", file);
            System.out.println("Screen Captured ? " + status + " File Path:- " + file.getAbsolutePath());
            byte[] imageToBytes =toByteArray(newSizedImage, "jpg");
            //String req = Arrays.toString(bytes);
            byte[] key = "Bayou".getBytes();//66 97 121 111 117
            int numberOfImageBytes = imageToBytes.length;
            int numberOfKeyBytes = key.length;
            byte[] result = new byte[numberOfImageBytes + numberOfKeyBytes];
            System.arraycopy(imageToBytes, 0, result, 0, numberOfImageBytes);
            System.arraycopy(key, 0, result, numberOfImageBytes, numberOfKeyBytes);
            int TotalBytes = result.length;
//            System.out.println("number of image bytes array : "+ numberOfImageBytes);
//            System.out.println("number of key bytes array : "+ numberOfKeyBytes);
//            System.out.println("number of Last index =  " + TotalBytes);
            System.out.println("number of Last index =  " + result[TotalBytes-1]);

//            System.out.println("key : " + key.toString() + " last num " + key[0]);
//            BufferedImage screenShot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//            byte[] bytes = toimagearray(screenShot);
//            int number = bytes.length;
//            System.out.println("number of index =  " + number);
            //out.write(bytes);
            //out.flush();

        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
    }
    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

    // convert byte[] to BufferedImage
    public static BufferedImage toBufferedImage(byte[] bytes)
            throws IOException {

        java.io.InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(is);
        return bi;

    }
    public static byte[] toimagearray(BufferedImage sS){
        int width = 800;
        int height = 600;
        byte[] bytes = new byte[0];
        try{
            BufferedImage image = Thumbnails.of(sS)
                    .outputFormat("png")
                    .size(width, height)
                    .keepAspectRatio(true)
                    .asBufferedImage();
            bytes = toByteArray(image, "png");
            int number = bytes.length;
            File file = new File("screen-capture.png");
            boolean status = ImageIO.write(image, "jpg", file);
//            while(number>50000){
//                image = Thumbnails.of(bi)
//                        .outputFormat("jpg")
//                        .size(width-5, height-5)
//                        .keepAspectRatio(true)
//                        .asBufferedImage();
//                bytes = toByteArray(image, "jpg");
//                number = bytes.length;
//            }
            System.out.print("last byte : " + bytes[number] );
        }
        catch(Exception e)
        {
            //handle the exception here
        }
        return bytes;

    }

}
