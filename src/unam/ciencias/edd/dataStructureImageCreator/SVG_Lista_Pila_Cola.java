package mx.unam.ciencias.edd.dataStructureImageCreator;

import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Lista;

/**
 * <p> Clase que crea la cadena SVG para lista, pila y cola </p>
 */

class SVG_Lista_Pila_Cola {

    /** Lista de enteros a graficar */
    private Lista<Integer> enteros;

    /** Estructura de datos a graficar */
    private String estructura;

    /**
     * Constructor
     * @param enteros enteros de la estructura.
     * @param estructura estructura de datos.
     */
    SVG_Lista_Pila_Cola(Lista<Integer> enteros, String estructura) {
        this.enteros = enteros;
        this.estructura = estructura;
    }

    /**
     * Metodo para graficar una pila.
     * @return cadena SVG.
     */
    public String graficarPila() {
        Pila<Integer> enterosPila = obtenerPila(enteros);
        int i = 0;
        StringBuilder svg = new StringBuilder();
        int altura = (enteros.getElementos() != 0) ? 20 + enteros.getElementos() * 40 : 1;
        int anchura = (enteros.getElementos() != 0) ? 80 : 1;
        svg.append(SVG_Figuras_Etiquetas.xml);
        svg.append(SVG_Figuras_Etiquetas.abreSVG(altura, anchura));
        svg.append(SVG_Figuras_Etiquetas.abre_G);
        while(!enterosPila.esVacia())
            svg.append(SVG_Figuras_Etiquetas.dibujaCuadrado(10, 10 + 40 * i++,
                            40, 60, enterosPila.saca().toString(), estructura));
        svg.append(SVG_Figuras_Etiquetas.cierra_G);
        svg.append(SVG_Figuras_Etiquetas.cierraSVG());
        return svg.toString();
    }

    /**
     * Metodo para graficar una cola.
     * @return cadena SVG.
     */
    public String graficarCola() {
        Cola<Integer> enterosCola = obtenerCola(enteros);
        int i = 0;
        StringBuilder svg = new StringBuilder();
        int altura = (enteros.getElementos() != 0) ? 60 : 1;
        int anchura = (enteros.getElementos() != 0) ? 80 * enteros.getElementos(): 1;
        String flecha = obtenerFlecha(estructura);
        svg.append(SVG_Figuras_Etiquetas.xml);
        svg.append(SVG_Figuras_Etiquetas.abreSVG(altura, anchura));
        svg.append(SVG_Figuras_Etiquetas.abre_DEFS);
        svg.append(SVG_Figuras_Etiquetas.abre_G_ID("arrow"));
        svg.append(SVG_Figuras_Etiquetas.dibujaFlecha(flecha));
        svg.append(SVG_Figuras_Etiquetas.cierra_G);
        svg.append(SVG_Figuras_Etiquetas.cierra_DEFS);
        svg.append(SVG_Figuras_Etiquetas.abre_G);
        while(!enterosCola.esVacia()) {
            svg.append(SVG_Figuras_Etiquetas.dibujaCuadrado(10 + (80 * i), 10,
                            40, 60, enterosCola.saca().toString(), estructura));
            if (i != enteros.getElementos() - 1) {
                svg.append(SVG_Figuras_Etiquetas.crearTag("arrow", 80 + i * 80, 35));
            }
            i++;
        }

        svg.append(SVG_Figuras_Etiquetas.cierra_G);
        svg.append(SVG_Figuras_Etiquetas.cierraSVG());
        return svg.toString();
    }

    /**
     * Metodo para graficar una lista.
     * @return cadena SVG.
     */
    public String graficarLista() {
        int i = 0;
        StringBuilder svg = new StringBuilder();
        int altura = (enteros.getElementos() != 0) ? 60 : 5;
        int anchura = (enteros.getElementos() != 0) ? 80 * enteros.getElementos(): 5;
        String flecha = obtenerFlecha(estructura);
        svg.append(SVG_Figuras_Etiquetas.xml);
        svg.append(SVG_Figuras_Etiquetas.abreSVG(altura, anchura));
        svg.append(SVG_Figuras_Etiquetas.abre_DEFS);
        svg.append(SVG_Figuras_Etiquetas.abre_G_ID("arrow"));
        svg.append(SVG_Figuras_Etiquetas.dibujaFlecha(flecha));
        svg.append(SVG_Figuras_Etiquetas.cierra_G);
        svg.append(SVG_Figuras_Etiquetas.cierra_DEFS);
        svg.append(SVG_Figuras_Etiquetas.abre_G);
        for (Integer e : enteros) {
            svg.append(SVG_Figuras_Etiquetas.dibujaCuadrado(10 + (80 * i),
                                                            10, 40, 60,
                                                            e.toString(),
                                                            estructura));
            if (i != enteros.getElementos() - 1) {
                svg.append(SVG_Figuras_Etiquetas.crearTag("arrow", 80 + i * 80, 35));
            }
            i++;
        }

        svg.append(SVG_Figuras_Etiquetas.cierra_G);
        svg.append(SVG_Figuras_Etiquetas.cierraSVG());
        return svg.toString();
    }

    /**
    * Metodo privado para obtener el tipo de flecha.
    * (lista o cola).
    * @param estructura estructura de datos.
    * @return tipo de flecha.
    */
    private String obtenerFlecha (String estructura) {
        return estructura.equals("Lista") ? "↔" : "→";
    }

    /**
     * Metodo privado para meter los enteros a una cola.
     * @param enteros lista de enteros.
     * @return cola de enteros.
     */
    private Cola<Integer> obtenerCola(Lista<Integer> enteros) {
        Cola<Integer> enterosCola = new Cola<>();
        for (Integer e : enteros)
            enterosCola.mete(e);
        return enterosCola;
    }

    /**
     * Metodo privado para meter los enteros a una pila.
     * @return pila de enteros.
     */
    private Pila<Integer> obtenerPila(Lista<Integer> enteros) {
        Pila<Integer> enterosPila = new Pila<>();
        for (Integer e : enteros)
            enterosPila.mete(e);
        return enterosPila;
    }

}
