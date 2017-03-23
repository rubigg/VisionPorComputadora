/**
 * Created by Rubi on 18/03/2017.
 */

import java.util.Scanner;

public class ClusterJerarquico {

    public static void main (String[] args)throws IndexOutOfBoundsException
    {
        Scanner entrada = new Scanner(System.in);
        int nElementos;
        int temp;

        System.out.println("Cantidad de elementos ");
        nElementos= entrada.nextInt();

        String nombrePunto[]=new String[nElementos]; //arreglo que guarda el id del punto
        int valorPunto[] = new int[nElementos]; //arreglo que guarda el valor del punto

        //solicita los datos para el clustering jerarquico
        System.out.println("Ingresa elementos ");
        for (int i=0; i<nElementos; i++)
        {
            System.out.println("Nombre\tValor");
            nombrePunto[i]=entrada.next();
            valorPunto[i]=entrada.nextInt();

        }
        //se recorre el noElementos
        for(int j=0; j<nElementos; j++)
        {
            for(int k=0; k<nElementos; k++)
            {
                if(valorPunto[j]<valorPunto[k]) //se verifican los valores del punto en las posiciones
                //uno tiene que ser menor que todos
                {
                    //ordena de forma ascendente menor a mayor
                    temp = valorPunto[j];
                    valorPunto[j] = valorPunto[k];
                    valorPunto[k] = temp;
                }
            }
        }

        for(int i=0; i<nElementos-1; i++)
        {
            System.out.println("\nPaso " + i);
            //acomo del nombre de las variables
            nombrePunto[0] = nombrePunto[0] + nombrePunto[i+1];
            nombrePunto[i+1]=" ";

            for(int l=0; l<nElementos; l++)
            {
                if(nombrePunto[l]!=" ")
                    System.out.println(nombrePunto[l]);
            }
        }

    }//main

}//clase


