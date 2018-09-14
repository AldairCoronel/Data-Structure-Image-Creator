package mx.unam.ciencias.edd.dataStructureImageCreator;

import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import java.util.Iterator;

/**
 * <p> Clase que crea la cadena SVG para las graficas </p>
 */

class SVG_Grafica {

    /** Lista de enteros a graficar */
    private Lista<Integer> enteros;

    /** Grafica */
    private Grafica<Integer> grafica;

    /**
     * Constructor
     * @param enteros enteros de la estructura.
     * @param estructura estructura de datos.
     */
    SVG_Grafica(Lista<Integer> enteros) {
        this.enteros = enteros;
        grafica = new Grafica<>();
        Iterator<Integer> iterador = enteros.iterator();
        while (iterador.hasNext()) {
            int u = iterador.next();
            int v = iterador.next();
            if (u == v) {
            	if (grafica.contiene(u)) {
            	    System.err.println("Error: vertice duplicado.");
            	    System.exit(-1);
            	}
            	else
            		grafica.agrega(u);
            }
            else {
            	if (!grafica.contiene(u))
                    grafica.agrega(u);
                if (!grafica.contiene(v))
                    grafica.agrega(v);
                if (grafica.sonVecinos(u,v)) {
                	System.err.println("Error: arista duplicada.");
                	System.exit(-1);
                }
                else
                    grafica.conecta(u,v);
            }
        }
    }

    /**
     * Clase interna privada que guarda las coordenadas y el elemento.
     */
    private class VerticeUbicacion {

        public double coordenadaX;
        public double coordenadaY;
        public int elemento;

        public VerticeUbicacion (double x, double y, int e){
            coordenadaX = x;
            coordenadaY = y;
            elemento = e;
        }

    }

    /**
     * Metodo para graficar una grafica.
     * @return cadena SVG.
     */
    public String graficarGrafica() {
        double altura = (enteros.getElementos() != 0) ? enteros.getElementos() * 60 : 5;
        double anchura = altura;
        double grado = obtenerGrado();
        double coordenadaCentroX = anchura / 2;
        double coordenadaCentroY = altura / 2;
        double radio = anchura - coordenadaCentroX - 25;

        StringBuilder svg = new StringBuilder();

        svg.append(SVG_Figuras_Etiquetas.xml);
        svg.append(SVG_Figuras_Etiquetas.abreSVG(altura, anchura));
        svg.append(SVG_Figuras_Etiquetas.abre_G);
        double grados = 0;
        VerticeUbicacion[] misVertices = new VerticeUbicacion[grafica.getElementos()];
        int indice = 0;
        for (Integer e : grafica) {
            double x = coordenadaCentroX + (radio * Math.cos((Math.PI * grados)/180));
            double y = coordenadaCentroY + (radio * Math.sin((Math.PI * grados)/180));
            VerticeUbicacion verticeUbicacion = new VerticeUbicacion(x, y, e);
            misVertices[indice++] = verticeUbicacion;
            grados += grado;
        }
        svg.append(dibujaAristas(misVertices));
        for ( int x = 0; x < misVertices.length; x++) {
       		VerticeUbicacion v = misVertices[x];
       		svg.append(SVG_Figuras_Etiquetas.dibujaVerticeGrafica(
       					v.coordenadaX, v.coordenadaY, 20, String.valueOf(v.elemento)));
        }
        svg.append(SVG_Figuras_Etiquetas.cierra_G);
        svg.append(SVG_Figuras_Etiquetas.cierraSVG());
        return svg.toString();
    }

    /**
     * Metodo auxiliar para dibujar las aristas de la grafica.
     * @param vertice vertice de VerticeUbicacion[]
     * @return cadena SVG.
     */
    private String dibujaAristas(VerticeUbicacion[] vertice) {
        String auxiliar = "";
        for (int x = 0; x < vertice.length; x++) {
            for (int y = x + 1; y < vertice.length; y++) {
                if (grafica.sonVecinos(vertice[x].elemento,vertice[y].elemento))
                    auxiliar += SVG_Figuras_Etiquetas.dibujarLinea(
                                vertice[x].coordenadaX,
                                vertice[x].coordenadaY,
                                vertice[y].coordenadaX,
                                vertice[y].coordenadaY);
            }
        }
        return auxiliar;
    }

    /**
     * Metodo auxiliar para obtener el grado de separacion de los
     * vertices.
     * @return grado de separacion.
     */
    private double obtenerGrado() {
        return 360.0 / enteros.getElementos();
    }

}
