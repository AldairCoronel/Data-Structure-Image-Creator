package mx.unam.ciencias.edd.dataStructureImageCreator;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * <p> Esta es una clase auxiliar para la creacion de mi cadena SVG </p>
 * Contiene los metodos primitivos para la crecion de figuras:
 * - vertices
 * - flechas
 * - cuadrados
 * - linea
 * Ademas de etiquetas necesarias en la sintaxis SVG y constantes
 * (todo es estatico) que nos ayudan en tener un codigo mas ordenado.
 */

class SVG_Figuras_Etiquetas {

    /** Lista de constantes de apoyo SVG*/
    static final String xml = "<?xml version='1.0' encoding='UTF-8' ?>\n";

    /** Etiquetas de SVG */
    static final String abre_G = "<g>\n";
    static final String cierra_G = "</g>\n";
    static final String abre_DEFS = "<defs>\n";
    static final String cierra_DEFS = "</defs>\n";

    /** Atributos usados regularmente */
    static final String bold = "bold";
    static final String sans_serif = "sans-serif";
    static final String middle = "middle";
    static final String white = "white";
    static final String black = "black";
    static final String red = "red";

    /**
     * Metodo para iniciar el SVG.
     * @param altura altura de mi formato.
     * @param anchura anchura de mi formato.
     * @return cadena SVG.
     */
    static String abreSVG(double altura, double anchura) {
        return String.format("<svg height='%f' width='%f'>\n", altura, anchura);
    }

    /**
     * Metodo para cerrar mi SVG.
     * @return cadena SVG.
     */
    static String cierraSVG() {
        return "</svg>\n";
    }

    /**
     * Metodo para abrir una etiqueda <g> con id.
     * @param id id de la etiqueta.
     * @return cadena SVG.
     */
    static String abre_G_ID(String id) {
        return String.format("<g id='%s'>\n", id);
    }

    /**
     * Metodo para dibujar una linea.
     * @param x1 x1 coordenada.
     * @param y1 y1 coordenada.
     * @param x2 x2 coordenada.
     * @param y2 y2 coordenada.
     * @return cadena SVG.
     */
    static String dibujarLinea(double x1, double y1, double x2, double y2) {
        return String.format("<line x1='%f' y1='%f' x2='%f' y2='%f' stroke='%s' " +
                            " stroke-width='%d' />\n", x1, y1, x2, y2, black, 2);
    }

    /**
     * Metodo para dibujar un vertice de grafica.
     * @param x x coordenada.
     * @param y y coordenada.
     * @param radio radio del vertice.
     * @param cadena cadena contenida.
     * @return cadena SVG.
     */
    static String dibujaVerticeGrafica(double x, double y, double radio, String cadena) {
        String circulo, texto;
        circulo = String.format("<circle cx='%f' cy='%f' r='%f' stroke='%s'" +
                                " stroke-width='%d' fill='%s' />\n",
                                x, y, radio, black, 2, white);
        texto = String.format("<text x='%f' y='%f' fill='%s' font-family='%s'" +
                                " font-size='%d' text-anchor='%s'>%s</text>\n",
                                x, y + 4, black, sans_serif, 11, middle, cadena);
        return circulo + texto;
    }

    /**
     * Metodo para dibujar un vertice.
     * @param x x coordenada.
     * @param y y coordenada.
     * @param radio radio del vertice.
     * @param cadena cadena contenida.
     * @param colorVertice color del vertice.
     * @param esAVL booleano para ver si es arbolAVL.
     * @param cadenaAVL el balance y altura del arbolAVL.
     * @return cadena SVG.
     */
    static String dibujarVertice(double x, double y, double radio, String cadena,
    String colorVertice, VerticeArbolBinario<Integer> vertice, boolean esAVL, String cadenaAVL) {
        String circulo, texto, textoAVL;
        int coordenadaBalance = obtenerCoordenadaBalance(vertice);
        circulo = String.format("<circle cx='%f' cy='%f' r='%f' stroke='%s'" +
                                " stroke-width='%d' fill='%s' />\n",
                                x, y, radio, black, 2, colorVertice);
        texto = String.format("<text x='%f' y='%f' fill='%s' font-family='%s'" +
                                " font-size='%d' text-anchor='%s'>%s</text>\n",
                                x, y + 4,
                                colorVertice.equals(white) ? black : white,
                                sans_serif, 11, middle, cadena);
        textoAVL = String.format("<text x='%f' y='%f' fill='%s' font-family='%s'" +
                                " font-size='%d' font-weight='%s' text-anchor='%s'>%s</text>\n",
                                x + coordenadaBalance, y - 17, black, sans_serif,
                                8, bold, middle, cadenaAVL);
        return circulo + texto + (esAVL ? textoAVL : "");
    }

    /**
     * Metodo auxiliar para obtener la coordenada
     * en donde imprimir el balance AVL.
     * @param vertice vertice.
     * @return valor de coordenada.
     */
    private static int obtenerCoordenadaBalance(VerticeArbolBinario<Integer> vertice){
        if (!vertice.hayPadre())
            return 0;
        else if (vertice.padre().hayDerecho() && vertice.padre().derecho() == vertice)
            return 10;
        else
            return -10;
    }

    /**
     * Metodo para dibujar una flecha.
     * @param flecha tipo de flecha.
     * @return cadena SVG.
     */
    static String dibujaFlecha(String flecha) {
        return String.format("<text x='%d' y='%d' fill='%s' font-family='%s'" +
                            " font-size='%d' text-anchor='%s'>%s</text>\n",
                            0, 0, black, sans_serif, 21, middle, flecha);
    }

    /**
     * Metodo para dibujar un cuadrado.
     * @param x x coordenada.
     * @param y y coordenada.
     * @param altura altura del cuadrado.
     * @param anchura anchura del cuadrado.
     * @param cadena cadena contenida.
     * @param estructura estructura que se esta graficando.
     * @return cadena SVG.
     */
    static String dibujaCuadrado(int x, int y, int altura, int anchura, String cadena, String estructura) {
        String rectangulo, texto;
        rectangulo = String.format("<rect " + "x='%d' y='%d' height='%d'" +
                        " width='%d' fill='%s' stroke='%s' stroke-width='%d' />\n",
                                x, y, altura, anchura, white, black, 2);
        texto = String.format("<text " + "x='%d' y='%d' fill='%s' font-family='%s' "+
                                " font-size='%d' text-anchor='%s'>%s</text>\n",
                                (estructura.equals("Pila")) ? 40 : x + 30,
                                (estructura.equals("Pila")) ? y + 25 : 35,
                                 black, sans_serif, 16, middle, cadena);
        return rectangulo + texto;
    }

    /**
     * Metodo para crear tag <use>.
     * @param id id del tag.
     * @param x x coordenada.
     * @param y y coordenada.
     * @return cadena SVG.
     */
    static String crearTag(String id, int x, int y) {
              return String.format("<use xlink:href='#%s' x='%d' y='%d' />\n", id, x, y);
    }

}
