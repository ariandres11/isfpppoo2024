package controlador;

import gui.Interfaz;
import modelo.Red;
import negocio.Calculo;
import org.apache.log4j.Logger;

public class AplicacionConsultas {
    public Red red;
    public Calculo calculo;
    public Interfaz interfaz;
    public Coordinador coordinador;
    private static final Logger logger = Logger.getLogger(AplicacionConsultas.class);

    public static void main(String[] args) {
        AplicacionConsultas aplicacionConsultas = new AplicacionConsultas();
        aplicacionConsultas.iniciar();
     //aplicacionConsultas.consultar(Constantes.MOSTRAR_CONEXIONES);
    }

    /**
     * Inicializa la aplicación, creando una interfaz y un coordinador, y
     * estableciendo las relaciones entre ellos.
     */
    public void iniciar() {
        // Se establecen las relaciones entre el coordinador y las demás partes de la app
        this.red = Red.getRed();
        this.calculo = new Calculo();
        this.coordinador = new Coordinador();
        this.interfaz = new Interfaz();
        coordinador.setInterfaz(interfaz);
        coordinador.setCalculo(calculo);
        calculo.setCoordinador(coordinador);
        coordinador.setRed(red);

        //Debug para confirmar que el coordinador pasa las conexiones y equipos correctamente
        logger.debug("Conexiones: " + coordinador.listarConexiones().size() + " Equipos: " + coordinador.listarEquipos().size());

        calculo.cargarDatos(coordinador.listarConexiones(), coordinador.listarEquipos());
    }

}
