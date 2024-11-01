package gui;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Conexion;
import modelo.Equipo;
import org.apache.log4j.Logger;
import negocio.roles.concrete_role_strategies.UserRoleStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * La clase Interfaz es una ventana principal que representa
 * la interfaz gráfica del sistema de gestión de red de computadoras.
 */
public class Interfaz extends JFrame {
    private Coordinador coordinador;
    private final int MAX_ANCHO = 1000;
    private final int MAX_ALTO = 700;
    private JPanel panelMenu;
    private JPanel panelGrafo1;
    private JPanel panelOpcionesSuperior;
    private static final Logger logger = Logger.getLogger(Interfaz.class);

    /**
     * Constructor de la clase Interfaz.
     * Inicializa la ventana con título, tamaño y operación de cierre.
     */
    public Interfaz(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.setTitle("Red de Computadoras");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_ANCHO, MAX_ALTO);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        //Por defecto el rol del usuario es user
        coordinador.setRoleStrategy(new UserRoleStrategy());

        mostrarMenu();
        agregarPanelSuperior();

    }

    /**
     * Obtiene el coordinador de la interfaz.
     *
     * @return El coordinador asociado a la interfaz.
     */
    public Coordinador getCoordinador() {
        return coordinador;
    }

    /**
     * Establece el coordinador para la interfaz.
     *
     * @param coordinador El coordinador a establecer.
     */
    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
    /**
     * Muestra un cuadro de diálogo para elegir una opción.
     *
     * @return El código de la opción seleccionada.
     */
    public int elegirOpcion() {
        String[] opciones = {
                "Mostrar Conexiones",
                "Ping Equipo",
                "Ping IP",
                "Mapa Estado Actual",
                "Detectar Problemas"
        };

        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Seleccione una opción",
                "Opciones",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        switch (seleccion) {
            case 0:
                return Constantes.MOSTRAR_CONEXIONES;
            case 1:
                return Constantes.PING_EQUIPO;
            case 2:
                return Constantes.PING_IP;
            case 3:
                return Constantes.MAPA_ESTADO_ACTUAL;
            case 4:
                return Constantes.DETECTAR_PROBLEMAS;
            default:
                return -1; // En caso de que no se seleccione ninguna opción
        }
    }

    /**
     * Permite al usuario elegir un equipo de una lista.
     *
     * @param equipos La lista de equipos disponibles.
     * @param mensaje Mensaje a mostrar en el cuadro de diálogo.
     * @return El equipo seleccionado por el usuario.
     */
    public Equipo elegirEquipo(List<Equipo> equipos, String mensaje) {
        // Crear un array de nombres de equipos
        String[] nombresEquipos = equipos.stream()
                .map(Equipo::getCodigo)
                .toArray(String[]::new);

        // Mostrar la lista desplegable
        String seleccion = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione un equipo:",
                "Ingrese equipo de" + mensaje,
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombresEquipos,
                nombresEquipos[0]
        );

        // Verificar la selección
        System.out.println("Selección: " + seleccion);

        // Buscar y devolver el equipo seleccionado
        for (Equipo equipo : equipos) {
            System.out.println("Comparando: " + equipo.getCodigo() + " con " + seleccion);
            if (equipo.getCodigo().equals(seleccion)) {
                System.out.println("Equipo encontrado: " + equipo);
                return equipo;
            }
        }
        System.out.println("No se encontró el equipo.");
        return null; // En caso de que no se encuentre el equipo (no debería ocurrir)
    }


    /**
     * Muestra el resultado de un recorrido de conexiones.
     *
     * @param recorrido La lista de conexiones a mostrar.
     */
    public void mostrarConexiones(List<Conexion> recorrido) {
        StringBuilder resultado = new StringBuilder();

        for (Conexion conexion : recorrido) {
            if (conexion.getEquipo1() != null && conexion.getEquipo2() != null) {
                resultado.append("De ")
                        .append(conexion.getEquipo1().getCodigo())
                        .append(" a ")
                        .append(conexion.getEquipo2().getCodigo())
                        .append(" en ")
                        .append(conexion.getTipoCable().getVelocidad())
                        .append(" megabytes por segundo.\n");
            } else {
                System.out.println("Conexión inválida: " + conexion);
            }
        }
        JOptionPane.showMessageDialog(this, resultado.toString(), "Resultado del Recorrido", JOptionPane.INFORMATION_MESSAGE);
        panelGrafo1 = new PanelGrafo(recorrido, coordinador.listarEquipos()).getPanelGrafo();
        panelGrafo1.setVisible(true);
        add(panelGrafo1);

        this.revalidate();
        this.repaint();
    }

    public void resultadoPingEquipo(boolean ping) {
        if (ping) {
            JOptionPane.showMessageDialog(this, "Ping realizado con exito", "Ping Equipo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo realizar el ping", "Ping Equipo", JOptionPane.ERROR_MESSAGE);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Muestra el menú en la parte inferior de la interfaz.
     */
    public void mostrarMenu() {
        if (panelMenu == null) {
            panelMenu = new PanelMenu(this, coordinador.getRoleContext());
            add(panelMenu, BorderLayout.SOUTH);
        }
        panelMenu.setSize(800, 200);
        this.revalidate();
        this.repaint();
    }

    public void agregarPanelSuperior() {
        panelOpcionesSuperior = new PanelOpcionesSuperior(this, coordinador.getRoleContext());
        add(panelOpcionesSuperior, BorderLayout.NORTH);
        this.revalidate();
        this.repaint();
    }

    public void mostrarSeleccionDeIPs() {
        // Obtener la lista de todas las IPs
        List<String> ips = coordinador.listaIPs();

        if (ips.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay direcciones IP disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir la lista a un array
        String[] ipArray = ips.toArray(new String[0]);

        // Mostrar un cuadro de diálogo para que el usuario elija la IP de inicio
        String ipInicio = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP:",
                "Elegir IP de inicio",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ipArray, // Array de opciones
                ipArray[0] // Valor predeterminado
        );

        // Verificar si el usuario canceló la selección
        if (ipInicio == null) {
            JOptionPane.showMessageDialog(null, "Selección cancelada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Obtener los primeros tres segmentos de la IP de inicio seleccionada para determinar la red
        String redInicio = ipInicio.substring(0, ipInicio.lastIndexOf('.'));

        // Filtrar las IPs para obtener solo aquellas en la misma red
        List<String> ipsMismaRed = Arrays.stream(ipArray)
                .filter(ip -> ip.startsWith(redInicio + "."))
                .collect(Collectors.toList());  //convierte a la lista

        // Convertir la lista filtrada en un array para usar en JOptionPane
        String[] opcionesIpsMismaRed = ipsMismaRed.toArray(new String[0]);

        // Verificar si existen IPs en la misma red
        if (opcionesIpsMismaRed.length == 0) {
            JOptionPane.showMessageDialog(null, "No hay IPs disponibles en la misma red.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Mostrar un cuadro de diálogo para que el usuario elija la IP de fin
        String ipFin = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP:",
                "Elegir IP de fin",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesIpsMismaRed, // Array de opciones
                opcionesIpsMismaRed[0] // Valor predeterminado
        );

        // Verificar si el usuario canceló la selección
        if (ipFin == null) {
            JOptionPane.showMessageDialog(null, "Selección cancelada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Dividir ambas IPs en segmentos y convertirlos a enteros
        int[] segmentosInicio = Arrays.stream(ipInicio.split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] segmentosFin = Arrays.stream(ipFin.split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();

        // Llama al metodo pingEntreIPS con los seis enteros
        Map<String, Boolean> resultadosPing = coordinador.pingIPS(segmentosInicio[0], segmentosInicio[1], segmentosInicio[2], segmentosInicio[3],
                segmentosFin[2], segmentosFin[3]);

        // Mostrar los resultados del ping
        StringBuilder resultados = new StringBuilder();
        for (Map.Entry<String, Boolean> entry : resultadosPing.entrySet()) {
            String ip = entry.getKey();
            Boolean respuesta = entry.getValue();
            resultados.append(ip).append(": ").append(respuesta ? "true" : "false").append("\n");
        }
        // Mostrar los resultados en un cuadro de diálogo
        JOptionPane.showMessageDialog(null, resultados.toString(), "Resultados del rango de IP", JOptionPane.INFORMATION_MESSAGE);

        this.revalidate();
        this.repaint();
    }

    public void advertencia(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia: ", JOptionPane.WARNING_MESSAGE);

    }


}





