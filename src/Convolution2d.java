
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;

/**
 * Created by Rubi on 24/02/2017.
 */

public class Convolution2d {

    public static void main (String[] args) throws IOException {


        double [][] blur = { { 0.0625, 0.125, 0.0625},
                { 0.125,  0.25, 0.125 },
                { 0.0625, 0.125, 0.0625 } };

        double [][] edgeDetection = { { -1, -1, -1 },
                { -1,  8, -1 },
                { -1, -1, -1 } };

        double [][] edgeDetection2  = { { 1, 0, -1 },
                { 0,  0, 0 },
                { -1, 0, 1} };

        double [][] edgeDetection3  = { { 0, 1, 0 },
                { 1,  -4, 1 },
                { 0, 1, 0} };

        double [][] sharpen = { { 0, -1, 0 },
                { -1, 5, -1 },
                { 0, -1, 0 } };

        double [][] emboss = { { -1, -1, 0 },
                { -1,  0, 1 },
                { 0, 1, 1} };

        File f = null;
        BufferedImage imgInput=null;
        BufferedImage imgOutput=null;

        try {
            f = new File("C:\\Users\\Rubi\\Documents\\cogolleroGrayScale.bmp");
            imgInput = ImageIO.read(f);
        }catch (IOException ioe){System.out.println("No se encuentra la imagen");}

        int ancho=imgInput.getWidth();//ancho de la matriz
        int alto=imgInput.getHeight();//alto de la matriz

        imgOutput = new BufferedImage(ancho, alto, imgInput.getType());

        int tamañoMatrix = 3;
        int s = tamañoMatrix / 2;

        for (int x=0; x<ancho; x++){

            for (int y=0; y<alto; y++){

                int red=0, green=0, blue=0;

                for (int i=0; i<tamañoMatrix; i++){

                    for (int j=0; j<tamañoMatrix; j++ ){

                        int imageX = (x - s + i + ancho) % ancho;
                        int imageY = (y - s + j + alto) % alto;

                        int  p = imgInput.getRGB(imageX, imageY);

                        int r = (p>>16)&0xff;
                        int g = (p>>8)&0xff;
                        int b = p&0xff;

                        red += (r*edgeDetection3[i][j]);
                        green += (g*edgeDetection3[i][j]);
                        blue += (b*edgeDetection3[i][j]);

                    }
                }

                red =  Math.min(Math.max(red,0),255);
                green = Math.min(Math.max(green,0),255);
                blue = Math.min(Math.max(blue,0),255);

                int rgb = new Color(red, green, blue).getRGB();

                imgOutput.setRGB(x,y, rgb);
            }
        }

        try{
            f= new File("C:\\Users\\Rubi\\Documents\\cogolleroGS.bmp");
            ImageIO.write(imgOutput, "bmp", f);

        }catch (IOException ioe){System.out.println("No se encuentra la imagen");}

    }//main

} //clase

