package controlador;

import java.awt.Font;
import java.awt.Dimension;

/**
 * La clase Constantes define una serie de constantes utilizadas en la aplicación.
 */
public class Constantes {

    ///////////////////////
    public static final int INTERFAZ_MAX_ANCHO = 1400;
    public static final int INTERFAZ_MAX_ALTO = 800;

    public static final int PANELMENU_MAX_ANCHO = 800;
    public static final int PANELMENU_MAX_ALTO = 200;

    public static final int AGREGAR_EQUIPO_MAX_ANCHO = 540;
    public static final int AGREGAR_EQUIPO_MAX_ALTO = 480;

    public static final int AGREGAR_CONEXION_MAX_ANCHO = 500;
    public static final int AGREGAR_CONEXION_MAX_ALTO = 150;

    public static final int AGREGAR_UBICACION_MAX_ANCHO = 300;
    public static final int AGREGAR_UBICACION_MAX_ALTO = 100;

    public static final int AGREGAR_TIPOEQUIPO_MAX_ANCHO = 300;
    public static final int AGREGAR_TIPOEQUIPO_MAX_ALTO = 100;

    public static final int AGREGAR_TIPOPUERTO_MAX_ANCHO = 300;
    public static final int AGREGAR_TIPOPUERTO_MAX_ALTO = 100;

    public static final int AGREGAR_TIPOCABLE_MAX_ANCHO = 300;
    public static final int AGREGAR_TIPOCABLE_MAX_ALTO = 100;

    public static final Dimension JTF_DIMENSIONES = new Dimension(70,20);

    public static final int JL_X_COORD = 20;
    public static final int JL_Y_COORD = 15;
    public static final int JL_WIDTH = 120;
    public static final int JL_HEIGHT = 20;

    //////////////////////////////////////////////////

    public static final int JTF_COLUMNAS = 3;
    public static final long MAX_TIEMPO_PING = 500;
    public static final Dimension RANGOIP_DIMENSIONES = new Dimension(600, 400);

    /////////////////////////////////////////////////////

    public static final int PANELGRAFO_MAX_ANCHO = 500;
    public static final int PANELGRAFO_MAX_ALTO = 500;

    public static final String GRAFO_EQUIPO_ACTIVO_COLOR = "green";
    public static final String GRAFO_EQUIPO_INACTIVO_COLOR = "red";
    public static final String GRAFO_EQUIPO_ACTIVO = "ACTIVO";
    public static final String GRAFO_EQUIPO_INACTIVO = "INACTIVO";

    public static final String GRAFO_CONEXION_ACTIVO_COLOR = "green";
    public static final String GRAFO_CONEXION_INACTIVO_COLOR = "red";

    ///////////////////////////////////////////////

    public static final String COORDINADOR_MODO_PROD = "Prod";
    public static final String COORDINADOR_MODO_SIM = "Sim";

    ///////////////////////////////////////////////
    public static final int DATOSRED_MAX_ANCHO = 850;
    public static final int DATOSRED_MAX_ALTO = 500;

    ///////////////////////////////////////////

    public static final Font FUENTE_CONSULTAS = new Font("Arial", Font.BOLD, 20);

    public static final Font FUENTE_OPCIONES = new Font("Arial", Font.BOLD, 20);
}
