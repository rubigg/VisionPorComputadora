import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rubi on 17/02/2017.
 */
public class GreyScale {

    public static void main (String Args[]){
        BufferedImage img=null;
        File i = null;

        try {
            i = new File("C:\\Users\\Rubi\\Documents\\cogollero.jpg");
            img = ImageIO.read(i);
        }catch (IOException ioe){}

            int height = img.getHeight();//alto
            int width = img.getWidth(); //ancho

            //esqueleto para todas las imagenes, scar el ancho y el alto y recorrer la matriz
            for (int y=0; y<height; y++){
                for (int x=0; x<width; x++){

                     int p = img.getRGB(x,y);

                    int a = (p>>24)&0xff; //ff es la mascara, and es true o 1 siempre que sea permite pasar los bits que esten prendidos
                                //se recorre el pixel
                    int r = (p>>16)&0xff;
                    int g = (p>>8)&0xff;
                    int b = p&0xff;
                    //tengo los canales de los 3 colores

                    int avg=(r+g+b)/3; //operacion de los pixeles

                    p = (a<<24)|(avg<<16)|(avg<<8)|avg; //moificacion de los pixeles
                    img.setRGB(x, y, p);

                }
            }

        try{
            i = new File("C:\\Users\\Rubi\\Documents\\cogolleroGrayScale.jpg");
            ImageIO.write(img, "jpg", i);

        }catch (IOException ioe){}

    }//main

}//class
