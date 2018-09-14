package mx.unam.ciencias.edd.dataStructureImageCreator;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.dataStructureImageCreator.SVG;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
  * <p>Clase que se encarga de graficar las estructuras de datos que se han
  * cubierto durante el curso utilizando SVG. </p>
  * El programa escribe su salida en la salida estandar, y recibe su entrada
  * a traves de un nombre de archivo o de la entrada estandar.
  * Se ignoran los espacios, los siguientes caracteres hasta el fin de linea
  * cuando se encuentra una almohadilla '#'.
  */

class Graficador {

    /** Archivo del cual se va a graficar */
    private String archivo;

    /** Estructura de datos a graficar */
    private String estructura;

    /** Lista de enteros de la estructura */
    private Lista<Integer> enteros;

    /** Lista de cadenas del archivo (incluyendo enteros)*/
    private Lista<String> cadenas;

    /**
     * Constructor
     */
    public Graficador(String[] args) {
        enteros = new Lista<>();
        cadenas = new Lista<>();
        archivo = "";
        leerArgumentos(args, checaEntradaEstandar(args));
    }

    /**
     * Metodo auxiliar para ver si es entrada estandar.
     * @param args arreglo de argumentos.
     * @return true si es entrada estandar | false si no.
     */
    private boolean checaEntradaEstandar(String[] args) {
        return args.length == 0;
    }

    /**
     * Metodo auxiliar que prepara para la lectura del archivo.
     * desde la entrada estandar o del archivo en si.
     * @param args arreglo de argumentos.
     * @param entradaEstandar booleano que nos dice si es entrada estandar.
     */
    private void leerArgumentos(String[] args, boolean entradaEstandar) {
        BufferedReader bufferedReader;
        /** Si es entrada estandar */
        if (entradaEstandar){
        	try {
        		if (System.in.available() == 0) {
        			System.err.println("Error: la entrada es vacia.");
        			System.exit(-1);
        		}
            	bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            	leerArchivo(bufferedReader);
            } catch (Exception e) { }
        }
        /** Si no lo es */
        else {
            try {
                bufferedReader = new BufferedReader(new FileReader(args[0]));
                leerArchivo(bufferedReader);
            } catch (FileNotFoundException e) {
            	System.err.println("Error: archivo no exite.");
            	System.exit(-1);
            }
        }
    }

    /**
     * Metodo auxiliar que lee el archivo .
     * @param bufferedReader bufferedReader creado anteriormente.
     */
    private void leerArchivo(BufferedReader bufferedReader) {
        String renglon;
        try {
            while ((renglon = bufferedReader.readLine()) != null) {
                if (renglon.indexOf('#') != -1)
                    renglon = renglon.substring(0, renglon.indexOf('#'));
                renglon = renglon.trim();
                if (renglon.length() > 0)
                    for (String cadena : renglon.split("\\s+"))
                        cadenas.agrega(cadena);
            }
        } catch (IOException io) {
            System.err.println("Ha ocurrido un error al leer.");
            System.exit(-1);
        }
        if (cadenas.getElementos() != 0) {
       		estructura = obtenerEstructura(cadenas.eliminaPrimero());
       		obtenerEnteros(cadenas);
       	}
       	else {
       		System.err.println("Error: archivo vacio.");
       		System.exit(-1);
       	}
    }

    /**
     * Metodo para imprimir la cadena SVG.
     * @param estructura estructura que se grafica.
     * @param enteros lista de enteros de la estructura.
     * @return cadena SVG.
     */
    public static String imprimirSVG(String estructura, Lista<Integer> enteros) {
        switch (estructura) {
            case "Lista":
            case "Pila":
            case "Cola":
                return SVG.graficarListaPilaCola(estructura, enteros);
            case "ArbolBinarioCompleto":
            case "ArbolBinarioOrdenado":
            case "ArbolRojinegro":
            case "ArbolAVL":
            case "MonticuloMinimo":
                return SVG.graficarArbol(estructura, enteros);
            case "Grafica":
                if (enteros.getElementos() % 2 != 0) {
                    System.err.println("Error: cantidad impar.");
                    System.exit(-1);
                }
                return SVG.graficarGrafica(enteros);
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Metodo auxiliar para obtener los enteros del archivo
     * y asignarlos a la lista.
     * @param cadenas lista de cadenas leidas del archivo.
     */
    private void obtenerEnteros(Lista<String> cadenas) {
        for (String cadena : cadenas)
            if (esEntero(cadena))
                enteros.agrega(Integer.parseInt(cadena));
    }

    /**
     * Metodo auxiliar para checar si la representacion en cadena
     * es entero.
     * @param cadena cadena.
     * @return true si la cadena es un entero | false si no lo es.
     */
    private boolean esEntero(String cadena) {
        try {
            Integer.parseInt(cadena);
        } catch (NumberFormatException n) {
            System.err.println("Error: todos deben ser enteros.");
            System.exit(-1);
        }
        return true;
    }

    /**
     * Metodo auxiliar para verificar que la estructura de datos
     * encontrada en el archivo exista.
     * @param estructura cadena que presuntamente es una estructura.
     * @return regresa la estructura si se encuentra.
     */
    private String obtenerEstructura(String estructura) {
        switch (estructura) {
            case "Lista":
                break;
            case "Pila":
                break;
            case "Cola":
                break;
            case "ArbolBinarioCompleto":
                break;
            case "ArbolBinarioOrdenado":
                break;
            case "ArbolRojinegro":
                break;
            case "ArbolAVL":
                break;
            case "Grafica":
                break;
            case "MonticuloMinimo":
                break;
            default:
                System.err.println("Error: estructura '" + estructura +
                                    "' no encontrada.");
                System.exit(-1);
        }
        return estructura;
    }

    /**
     * Metodo para obtener los enteros del archivo.
     * @return lista de enteros del archivo.
     */
    public Lista<Integer> getEnteros() {
        return enteros;
    }

    /**
     * Metodo para obtener la estructura del archivo.
     * @return estructura del archivo.
     */
    public String getEstructura() {
        return estructura;
    }

}
