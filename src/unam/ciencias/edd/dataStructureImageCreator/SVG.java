package mx.unam.ciencias.edd.dataStructureImageCreator;

import mx.unam.ciencias.edd.Lista;

/**
 * <p> Clase que se encarga de recibir una estructura y regresar el valor
 * obtenido de los metodos que llama de otras clases </p>
 * esto como intermediario para agilizar la creacion de instancias de las
 * clases que se encargan de construir la cadena SVG.
 */

class SVG {

    /**
     * Constructor vacio
     */
    public SVG() {}

    /**
     * Metodo que se encarga de hacer las llamadas para graficar listas,
     * pilas y colas.
     * @param ed nombre de la estructura de datos.
     * @param enteros enteros de la estructura.
     * @return cadenaSVG.
     */
    public static String graficarListaPilaCola(String ed, Lista<Integer> enteros) {
        SVG_Lista_Pila_Cola lista_pila_cola = new SVG_Lista_Pila_Cola(enteros, ed);
        switch (ed) {
            case "Lista":
                return lista_pila_cola.graficarLista();
            case "Pila":
                return lista_pila_cola.graficarPila();
            case "Cola":
                return lista_pila_cola.graficarCola();
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Metodo que se encarga de hacer las llamadas para graficar
     * arboles binarios completos, arboles binarios ordenados,
     * arboles rojinegros, arboles AVL y monticulos minimos.
     * @param ed nombre de la estructura de datos.
     * @param enteros enteros de la estructura.
     * @return cadenaSVG.
     */
    public static String graficarArbol(String ed, Lista<Integer> enteros) {
        SVG_Arboles arboles = new SVG_Arboles(enteros, ed);
        return arboles.graficarArbol();
    }

    /**
     * Metodo que se encarga de hacer las llamadas para graficar
     * a las graficas.
     * @param enteros enteros de la estructura.
     * @return cadenaSVG.
     */
    public static String graficarGrafica(Lista<Integer> enteros) {
        SVG_Grafica grafica = new SVG_Grafica(enteros);
        return grafica.graficarGrafica();
    }

}
