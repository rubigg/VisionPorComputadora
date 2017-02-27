import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rubi on 24/02/2017.
 */

public class ConvolutionKernel {

    public static void main (String[] args) {

        int ancho; //ancho de la matriz
        int alto; //alto de la matriz

        // edge Detection--outline
      int [][] matrix = { { -1, -1, -1 },
            { -1,  8, -1 },
            { -1, -1, -1 } };

       //Sharpen
       /* int [][] matrix = { { 0, -1, 0 },
                { -1, 5, -1 },
                { 0, -1, 0 } };*/

        //custom
        /*int [][] matrix = { { 0, 0, 0 },
                { 0,  1, 0 },
                { 0, 0, 0} };
*/
        File f = null;
        BufferedImage img=null;

        try {
            f = new File("C:\\Users\\Rubi\\Documents\\bosque.jpg");
            img = ImageIO.read(f);
        }catch (IOException ioe){System.out.println("No se encuentra la imagen");}

        ancho=img.getWidth();//ancho de la matriz
        alto=img.getHeight();//alto de la matriz

        int p=0, a=0, r=0, g=0, b=0, suma=0;

        int tamañoMatrix=3;
        int s = tamañoMatrix / 2;

        for (int i=s; i<alto-s; i++){
            for (int j=s; j<ancho-s; j++){

                int cont = 0; //acumulador a cero

                for (int k=0; k<tamañoMatrix; k++){
                    for (int l=0; l<tamañoMatrix; l++ ){

                        p= img.getRGB(j,i);

                        a = (matrix[k][l] * (p>>24)&0xff);
                        r = (matrix[k][l] * (p>>16)&0xff);
                        g = (matrix[k][l] * (p>>8)&0xff);
                        b = (matrix[k][l] *  p&0xff);

                        cont ++;

                    }//for l

                } //for k

                //a=a/cont; r=r/cont; g=g/cont; b=b/cont;
                //suma=suma/cont;

                suma = (a+r+b+g)/3;
                p = (suma<<24)|(suma<<16)|(suma<<8)|(suma);

                img.setRGB(j,i,p);

            } //for j
        }//for i

        try{
            f= new File("C:\\Users\\Rubi\\Documents\\bosqueKernel1.jpg");
            ImageIO.write(img, "jpg", f);

        }catch (IOException ioe){System.out.println("No se encuentra la imagen");}

    }//main

} //clase




