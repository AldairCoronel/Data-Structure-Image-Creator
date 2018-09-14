package mx.unam.ciencias.edd.dataStructureImageCreator;

import mx.unam.ciencias.edd.dataStructureImageCreator.Graficador;
import mx.unam.ciencias.edd.Lista;

/**
 * Proyecto 2. -Graficador SVG de las estructuras de datos vista en el curso-
 * @author Aldair Coronel Ruiz
 */
public class DataStructureImageCreator {
    public static void main(String[] args) {
        /** Estructura de datos a graficar */
        String estructura;

        /** Lista de enteros de la estructura */
        Lista<Integer> enteros;

        Graficador graficador = new Graficador(args);
        estructura = graficador.getEstructura();
        enteros = graficador.getEnteros();
        System.out.print(graficador.imprimirSVG(estructura, enteros));
    }
}
