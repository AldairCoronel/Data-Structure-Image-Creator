package mx.unam.ciencias.edd.dataStructureImageCreator;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Indexable;
import mx.unam.ciencias.edd.MonticuloMinimo;

/**
 * <p> Clase que crea la cadena SVG para arbol binario ordenado,
 * arbol binario completo, arbol rojinegro, arbol AVL y monticulo
 * minimo </p>
 */

class SVG_Arboles {

    /** Lista de enteros a graficar */
    private Lista<Integer> enteros;

    /** Estructura de datos a graficar */
    private String estructura;

    /** Arbol binario */
    private ArbolBinario<Integer> arbol;

    /** Profundidad */
    private int profundidad;

    /** altura */
    private int altura;

    /**anchura */
    private int anchura;

    /**
     * Constructor
     * Agrega los elementos de la lista a alguna instancia de arbol binario.
     * @param enteros enteros de la estructura.
     * @param estructura estructura de datos.
     */
    SVG_Arboles(Lista<Integer> enteros, String estructura) {
        this.enteros = enteros;
        this.estructura = estructura;
        switch (estructura) {
            case "ArbolBinarioOrdenado":
                arbol = new ArbolBinarioOrdenado<Integer>();
                break;
            case "ArbolBinarioCompleto":
            case "MonticuloMinimo":
                arbol = new ArbolBinarioCompleto<Integer>();
                break;
            case "ArbolRojinegro":
                arbol = new ArbolRojinegro<Integer>();
                break;
            case "ArbolAVL":
                arbol = new ArbolAVL<Integer>();
                break;
        }
        if (!estructura.equals("MonticuloMinimo")) {
            for (Integer e : enteros)
                arbol.agrega(e);
        }
        else {
            MonticuloMinimo<Indexable<Integer>> mm = new MonticuloMinimo<Indexable<Integer>>();
            for (Integer e : enteros) {
                Indexable<Integer> ind = new Indexable<Integer>(e, e);
                mm.agrega(ind);
            }
            while(!mm.esVacia())
                arbol.agrega(mm.elimina().getElemento());
        }
    }

    /**
     * Metodo para graficar cualquier tipo de arbol y monticulo minimo.
     * @return cadena SVG.
     */
    public String graficarArbol() {
        int radioVertice = obtenerRadio(enteros);
        profundidad = (arbol.profundidad() == 0) ? 1 : arbol.profundidad();
        altura = (enteros.getElementos() != 0) ? 300 + profundidad * 100 : 5;
        anchura = (enteros.getElementos() != 0) ?
                (int) (Math.pow(2, profundidad) * radioVertice * 2) + 800 : 5;
        StringBuilder svg = new StringBuilder();

        svg.append(SVG_Figuras_Etiquetas.xml);
        svg.append(SVG_Figuras_Etiquetas.abreSVG(altura, anchura));
        svg.append(SVG_Figuras_Etiquetas.abre_G);
        if (!arbol.esVacia()) {
            svg.append(dibujarLineas(arbol.raiz(),
                                    anchura / 2,
                                    80,
                                    (anchura / 2) / 2));
            svg.append(dibujarVertices(arbol.raiz(),
                                        ((anchura / 2) / 2),
                                        anchura / 2,
                                        80,
                                        radioVertice));
        }
        svg.append(SVG_Figuras_Etiquetas.cierra_G);
        svg.append(SVG_Figuras_Etiquetas.cierraSVG());
        return svg.toString();
    }

    /**
     * Metodo auxiliar para dibujar las lineas del arbol.
     * @param vertice vertice del arbol.
     * @param x x coordenada.
     * @param y y coordenada.
     * @param mitad esta se va a ir dividiendo cada vez que se llame recursivamente.
     * @return cadena SVG.
     */
    private String dibujarLineas(VerticeArbolBinario vertice, double x, double y, double mitad) {
        String auxiliar = "";
        if (vertice.hayIzquierdo() && vertice.hayDerecho())
            auxiliar += SVG_Figuras_Etiquetas.dibujarLinea(x, y, x - mitad, y + 115) +
                        SVG_Figuras_Etiquetas.dibujarLinea(x, y, x + mitad, y + 115) +
                        dibujarLineas(vertice.izquierdo(), x - mitad, y + 115, mitad / 2) +
                        dibujarLineas(vertice.derecho(), x + mitad, y + 115, mitad / 2);
        else if (vertice.hayIzquierdo())
            auxiliar += SVG_Figuras_Etiquetas.dibujarLinea(x, y, x - mitad, y + 115) +
                        dibujarLineas(vertice.izquierdo(), x - mitad, y + 115, mitad / 2);
        else if (vertice.hayDerecho())
            auxiliar += SVG_Figuras_Etiquetas.dibujarLinea(x, y, x + mitad, y + 115) +
                        dibujarLineas(vertice.derecho(), x + mitad, y + 115, mitad / 2);
        return auxiliar;
    }

    /**
     * Metodo auxiliar para dibujar los vertices del arbol.
     * @param vertice vertice del arbol.
     * @param mitad esta se va a ir dividiendo cada vez que se llame recursivamente.
     * @param x x coordenada.
     * @param y y coordenada.
     * @param radio radio de mis vertices.
     * @return cadena SVG.
     */
    private String dibujarVertices(VerticeArbolBinario<Integer> vertice, double mitad, double x, double y, double radio) {
        String auxiliar = "";
        if (vertice != null) {
            String balanceAVL = "(" + String.valueOf(vertice.altura()) + "/" + String.valueOf(obtenerBalanceAVL(vertice)) + ")";
            auxiliar += SVG_Figuras_Etiquetas.dibujarVertice(x, y, radio,
                                                            vertice.get().toString(),
                                                            obtenerColor(vertice),
                                                            vertice,
                                                            estructura.equals("ArbolAVL"),
                                                            balanceAVL);
            if (vertice.hayIzquierdo() && vertice.hayDerecho())
                auxiliar += dibujarVertices(vertice.izquierdo(), mitad / 2, x - mitad, y + 115, radio) +
                    dibujarVertices(vertice.derecho(), mitad / 2, x + mitad, y + 115, radio);
            else if (vertice.hayIzquierdo())
                auxiliar += dibujarVertices(vertice.izquierdo(), mitad / 2, x - mitad, y + 115, radio);
            else if (vertice.hayDerecho())
                auxiliar += dibujarVertices(vertice.derecho(), mitad / 2, x + mitad, y + 115, radio);
        }
        return auxiliar;
    }

    /**
     * Metodo auxiliar que en caso de que mi arbol sea AVL
     * obtiene el balance.
     * @param vertice la cadena del vertice.
     * @return balance AVL.
     */
    private int obtenerBalanceAVL(VerticeArbolBinario<Integer> vertice) {
        int izq = -1, der = -1;
        if (vertice.hayIzquierdo())
            izq = obtenerAltura(vertice.izquierdo());
        if (vertice.hayDerecho())
            der = obtenerAltura(vertice.derecho());
        return izq - der;
     }

     /**
      * Método auxiliar que obtiene la altura del vértice
      * @param vertice vértice del cual buscamos la altura
      * @return altura la altura de ese vértice
      */
     private int obtenerAltura(VerticeArbolBinario<Integer> vertice){
         return (vertice == null) ? -1 : vertice.altura();
     }


    /**
     * Metodo auxiliar que obtiene el color del vertice.
     * @param vertice vertice del arbol.
     * @return el color (rojo o negro si es rojinegro), en otro caso blanco.
     */
    private String obtenerColor(VerticeArbolBinario<Integer> vertice) {
        String color;
        if (estructura.equals("ArbolRojinegro"))
            if (vertice.toString().substring(0 , 1).equals("N"))
                color = "black";
            else
                color = "red";
        else
            color = "white";
        return color;
    }

    /**
     * Metodo auxiliar para obtener el radio del vertice.
     * @param iterable Iterable de enteros.
     * @return radio del vertice.
     */
    private int obtenerRadio(Iterable<Integer> iterable) {
        int maximo = 0;
        int radioV = 15;
        for (Integer e : iterable)
            if (e.toString().length() > maximo)
                maximo = e.toString().length();
        if (maximo >= 4)
            radioV = ((maximo - 3) * 5) + radioV;
        return radioV;
    }

}
