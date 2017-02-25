import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rubi on 24/02/2017.
 */
public class ConvolutionKernel {

    public static void main (String Args[]){
        BufferedImage img=null;
        File f = null;


        try {
            f = new File("C:\\Users\\Rubi\\Documents\\playa.jpg");
            img = ImageIO.read(f);
        }catch (IOException ioe){}


        double entrada[][];
        int width;
        int heigth;
        double kernel[][];
        int kernelWidth = 0;
        int kernelHeight = 0;

        int imageHeight = img.getHeight();
        int imageWidth = img.getWidth();
        double salida[][] = new double[imageWidth][imageHeight];

        for (int y=0; y<imageWidth; y++)
        {
            for (int x=0; x<imageHeight; x++){
                int p = img.getRGB(x,y);
                salida[y][x]=0;

                for (int i=0; i<imageWidth; i++){
                    for (int j=0; j<imageHeight; j++){

                    }
                }
            }
        }


        for (int y=0; y<imageWidth; y++)


            try{
                f= new File("C:\\Users\\Rubi\\Documents\\customKernel.jpg");
                ImageIO.write(img, "jpg", f);

            }catch (IOException ioe){

            }
    }//main

}//clase
