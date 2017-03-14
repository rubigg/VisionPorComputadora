/**
 * Created by Rubi on 07/03/2017.
 */

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class KMeans {

    public static void main( String [] args ) {

        Scanner entrada = new Scanner(System.in);
        //inicializacion de dos imagenes una de salida y otra de entrada
        BufferedImage original=null;
        BufferedImage resultado;

        int k; //numero de centroides
       System.out.println ("\nDime numero de clusters K");
        k = entrada.nextInt();


        try{
            File f= new File("C:\\Users\\Rubi\\Documents\\cogollero.jpg");
            original=ImageIO.read(f);

        }catch (IOException ioe){System.out.println("No se encuentra la imagen");}

        try{
            resultado = propiedadesImagen(original, k);
            File f= new File("C:\\Users\\Rubi\\Documents\\1235.jpg");
            ImageIO.write(resultado, "jpg", f);

        }catch (IOException ioe){System.out.println("No se pudo procesar");}

    }//main

    private static BufferedImage propiedadesImagen( BufferedImage original, int k ) //metodo de tipo BufferedImage
    {
        int ancho = original.getWidth(); //ancho de la matriz
        int alto = original.getHeight(); //alto de la matriz

        //nueva imagen interna que contenga las propiedades para la imagen
        BufferedImage kImagen = new BufferedImage( ancho, alto, original.getType());

        //uso Java 2D
        Graphics2D g2d = kImagen.createGraphics(); //creando los graficos de la imagen en 2d
        g2d.drawImage( original, 0, 0, ancho, alto, null ); //metodo de java 2d

        // recorremos la matriz para leer los valores RGB de la imagen
        //sacar el ancho y el alto
        int[] imagenRGB = new int[(ancho*alto)];
        int counter = 0;
        for (int x=0; x<ancho; x++) {
            for(int y=0; y<alto; y++) {
                imagenRGB[counter++] = kImagen.getRGB(x, y);
            }
        }

        //se manda llamar el algoritmo para q actualice los valores RGB de la imagen
        Algoritmokmeans(imagenRGB, k);

        //escribimos los nuevos valores de RGB en la imagen recorriendo la matriz
        counter = 0;
        for( int x=0; x<ancho; x++) {
            for( int y=0; y<alto; y++) {
                kImagen.setRGB(x, y, imagenRGB[counter++]);
            }
        }
        return kImagen; //devolvemos la imagen

    }//metodo propiedadesImagen

    //metodo q hace kmeans  //resibe un arreglo de pixeles es decir la imagen y el numero de centroides a procesar
    private static void Algoritmokmeans(int[] pixeles, int k)
    {
        int[] centroidesActuales = new int[k]; //arreglo que guardara los centroides q se generen
        int[] centroidesPrevios = new int[k];//arreglo que guardara los centroides previos utilizados

        //arreglos que a RGB del tamaño de k
        int[] p = new int[k];
        int[] a = new int[k];
        int[] r = new int[k];
        int[] g = new int[k];
        int[] b = new int[k];

        int[] cluster = new int[pixeles.length]; //arreglo de puntos que contiene el tamaño de la imagen
        int centro = 0;

        //PASO 1 -inicia seleccionando los k centroides aleatoriamente
        for (int i=0; i<k; i++) { //ciclo para seleccionar aleatoriamente los centroides de los pixeles de la imagen
            Random random = new Random();
            int valorCentro;
            valorCentro = pixeles[random.nextInt(pixeles.length)];
            centroidesActuales[i] = valorCentro; //el nuevo valor del centroide se le asigna al actual
        }

        do {
            //ciclo para actualizar los clusters
            for (int i=0; i<centroidesActuales.length; i++) {
                centroidesPrevios[i] = centroidesActuales[i]; //inicializar los k centroides
                p[i] = 0; a[i] = 0; r[i] = 0;
                g[i] = 0; b[i] = 0;
            }

            for (int i=0; i<pixeles.length; i++ ) {
                double distanciaMaxima = Double.MAX_VALUE;//maximo valor de esa variable double

                //PASO 2- Asigna cada pixel o punto a la lista de los centroides
                for (int j=0; j<centroidesActuales.length; j++) {
                    //se calcula la distancia entre el centroide actual y cada uno de los puntos
                  double distanciaActual = calcularDistancia(pixeles[i], centroidesActuales[j] );
                //PASO 3- Se recalcula el punto centroide para cada cluster y se vuelve a iterar.
                    //si la distancia actual es menor a la distancia maxima q existe
                    //entonces la maxima y la actual es igual y es centro sera el mismo
                    if (distanciaActual < distanciaMaxima) {
                        distanciaMaxima = distanciaActual;
                        centro = j;
                    }
                }

                //se recorre el pixel
                cluster[i] = centro;
                p[centro]++;
                a[centro] += ((pixeles[i]>>24)&0xff);
                r[centro] += ((pixeles[i]>>16)&0xff);
                g[centro] += ((pixeles[i]>>8)&0xff);
                b[centro] += (pixeles[i]&0xff);
            }

            for (int i=0; i<centroidesActuales.length; i++)
            {
                //operacion de los pixeles
                int al =   (int)((double)a[i] / (double)p[i]);
                int red =   (int)((double)r[i] / (double)p[i]);
                int green = (int)((double)g[i] / (double)p[i]);
                int blue =  (int)((double)b[i] /  (double)p[i]);

                //modificacion de los pixeles
                centroidesActuales[i] = ((al&0xff)<<24)|((red&0xff)<<16)|((green&0xff)<<8)|(blue&0xff);
            }

        }
        //PASO 4-se repite el proceso hasta que no haya ningun movimiento en el centroide
        while(!parada(centroidesPrevios, centroidesActuales));//hasta que el metodo de parada sea diferente

        //Ciclo para establecer los centroides actuales en los pixeles de la imagen
        for (int j=0; j<pixeles.length; j++) {
            pixeles[j] = centroidesActuales[cluster[j]];
        }
    }

    //condicion de parada
    private static boolean parada(int[] centroidesPrevios, int[] centroidesActuales)
    {
        //recorriendo los centroides actuales se verifica que si los centrides previos
        //en la posision i son diferente igual a los centriodes actuales en la posision i
        for (int i=0; i<centroidesActuales.length; i++)
        {
           //condicion de parada para cuando el centroide anterior es diferente igual a el centroide actual
            /*if (centroidesPrevios[i] != centroidesActuales[i] ){
                return false;
            }*/
           //condicion de parada para cuando los centroides actuales sean iguales a 1000
            if (centroidesActuales[i] >= 1000){
                return false;
            }

        } //for

        //si el centriode viejo y el centroide nuevo son iguales se detiene el recorrido de centriodes
        return true;

    }//metodo parada

    private static double calcularDistancia(int pixelA, int pixelB)
    {
        //la raiz cuadrada de la sumatoria de la resta de los pixeles en RGB elevado al cuadrado
        int a = ((pixelA>>24)&0xff) - ((pixelB>>24)&0xff);
        int red = ((pixelA>>16)&0xff) - ((pixelB>>16)&0xff);
        int green = ((pixelA>>8)&0xff)  - ((pixelB>>8)&0xff);
        int blue = ((pixelA)&0xff)  - ((pixelB)&0xff);

        return Math.sqrt( Math.pow(a, 2) + Math.pow(red, 2)+ Math.pow(green, 2) + Math.pow(blue, 2));
    }//metodo calcularDistancia

}//clase
