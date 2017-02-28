import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rubi on 24/02/2017.
 */

public class ConvolutionKernel {

    public static void main (String[] args) throws IOException {

        //blur-si
        /*double [][] matrix = { { 0.0625, 0.125, 0.0625},
                { 0.125,  0.25, 0.125 },
                { 0.0625, 0.125, 0.0625 } };*/

        // edge Detection--outline SI
      double [][] matrix = { { -1, -1, -1 },
            { -1,  8, -1 },
            { -1, -1, -1 } };

       //Sharpen -SI
        /*double [][] matrix = { { 0, -1, 0 },
                { -1, 5, -1 },
                { 0, -1, 0 } };*/

        //Emboss SI
        /*double [][] matrix = { { -1, -1, 0 },
                { -1,  0, 1 },
                { 0, 1, 1} };*/

        File f = null;
        BufferedImage imgInput=null;
        BufferedImage imgOutput=null;

        try {
            f = new File("C:\\Users\\Rubi\\Documents\\alemania.jpg");
            imgInput = ImageIO.read(f);
        }catch (IOException ioe){System.out.println("No se encuentra la imagen");}

        int ancho=imgInput.getWidth();//ancho de la matriz
        int alto=imgInput.getHeight();//alto de la matriz

        imgOutput = new BufferedImage(ancho, alto, imgInput.getType());

        double suma=0;
        int mult_factor = 1;
        int tamañoMatrix = 3;
        int s = tamañoMatrix / 2;

        for (int x=0; x<ancho; x++){

            for (int y=0; y<alto; y++){

                int cont = 0; //acumulador a cero
                int red=0, green=0, blue=0;

                for (int i=0; i<tamañoMatrix; i++){
                    for (int j=0; j<tamañoMatrix; j++ ){

                        //Cálculo de las coordenadas X y Y del píxel a
                        // multiplicar con el elemento actual del núcleo
                        int imageX = (x - s + i + ancho) % ancho;
                        int imageY = (y - s + j + alto) % alto;

                       int  p = imgInput.getRGB(imageX, imageY);
                        int r = (p>>16)&0xff;
                        int g = (p>>8)&0xff;
                        int b = p&0xff;

                //El RGB se multiplica con el elemento actual del kernel y
                        // se añade a las variables rojo, verde y azul
                        red += (r*matrix[i][j]);
                        green += (g*matrix[i][j]);
                        blue += (b*matrix[i][j]);

                        cont ++;

                    }//for i
                } //for j

                //para asegurarse de que RGB esté dentro del alcance de 255
                red =  Math.min(Math.max((red*mult_factor),0),255);
				green = Math.min(Math.max((green*mult_factor),0),255);
				blue = Math.min(Math.max((blue*mult_factor),0),255);

                // Pixel se escribe en la imagen
                imgOutput.setRGB(x,y, new Color(red, green, blue).getRGB());

            } //for j
        }//for i

        try{
            f= new File("C:\\Users\\Rubi\\Documents\\alemaniaK.jpg");
            ImageIO.write(imgOutput, "jpg", f);

        }catch (IOException ioe){System.out.println("No se encuentra la imagen");}

    }//main

} //clase




