package gui;

import controlador.Constantes;
import controlador.Coordinador;
import modelo.Conexion;
import modelo.Equipo;
import org.apache.log4j.Logger;
import negocio.roles.concrete_role_strategies.UserRoleStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

import static controlador.Constantes.FUENTE_CONSULTAS;

/**
 * La clase Interfaz es una ventana principal que representa
 * la interfaz gráfica del sistema de gestión de red de computadoras.
 */
public class Interfaz extends JFrame {
    private Coordinador coordinador;
    private final int MAX_ANCHO = 1400;
    private final int MAX_ALTO = 800;
    private JPanel panelMenu;
    private JPanel panelGrafo1;
    private JPanel panelOpcionesSuperior;
    private static final Logger logger = Logger.getLogger(Interfaz.class);

    /**
     * Constructor de la clase Interfaz.
     * Inicializa la ventana con título, tamaño y operación de cierre.
     */
    public  Interfaz() {
    }

    public void iniciar(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.setTitle("Red de Computadoras");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MAX_ANCHO, MAX_ALTO);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        //Por defecto el rol del usuario es user
        coordinador.setRoleStrategy(new UserRoleStrategy());
        panelGrafo1 = new JPanel();
        add(panelGrafo1, BorderLayout.CENTER);
        //Mostrar titulo
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

        coordinador.cargarDatos();
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
        if(resultado.isEmpty()){
            JOptionPane.showMessageDialog(this, "No se encontraron conexiones", "Resultado del Recorrido", JOptionPane.INFORMATION_MESSAGE);

        }else{
            JOptionPane.showMessageDialog(this, resultado.toString(), "Resultado del Recorrido", JOptionPane.INFORMATION_MESSAGE);
        }
        if(panelGrafo1 != null){
            panelGrafo1.setVisible(false);
            remove(panelGrafo1);
        }
        panelGrafo1 = new PanelGrafo(recorrido, coordinador).getPanelGrafo();
        panelGrafo1.setVisible(true);
        add(panelGrafo1);

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
            panelMenu = coordinador.crearPanelMenu();
            add(panelMenu, BorderLayout.SOUTH);
        }
        panelMenu.setSize(800, 200);
        this.revalidate();
        this.repaint();
    }

    public void agregarPanelSuperior() {
        panelOpcionesSuperior = coordinador.crearPanelOpcionesSuperior();
        add(panelOpcionesSuperior, BorderLayout.NORTH);
        this.revalidate();
        this.repaint();
    }

    public void seleccionarIPs() {
        // Crear campos de texto para la primera IP
        JTextField digitoRed1 = new JTextField(3);
        JTextField digitoRed2 = new JTextField(3);
        JTextField digitoHost1 = new JTextField(3);
        JTextField digitoHost2 = new JTextField(3);

        // Crear campos de texto para la segunda IP
        JTextField digitoHost3 = new JTextField(3);
        JTextField digitoHost4 = new JTextField(3);

        // Aumentar el tamaño de fuente
        digitoRed1.setFont(FUENTE_CONSULTAS);
        digitoRed2.setFont(FUENTE_CONSULTAS);
        digitoHost1.setFont(FUENTE_CONSULTAS);
        digitoHost2.setFont(FUENTE_CONSULTAS);
        digitoHost3.setFont(FUENTE_CONSULTAS);
        digitoHost4.setFont(FUENTE_CONSULTAS);

        // Configurar el panel con un diseño de cuadrícula
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Configurar el título y los campos de texto para la primera IP
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel tituloInicio = new JLabel("Dirección IP de inicio:");
        tituloInicio.setFont(FUENTE_CONSULTAS);
        panel.add(tituloInicio, constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 1;
        JLabel labelRed1 = new JLabel("Red 1:");
        labelRed1.setFont(FUENTE_CONSULTAS);
        panel.add(labelRed1, constraints);
        constraints.gridx = 1;
        panel.add(digitoRed1, constraints);
        constraints.gridx = 2;
        JLabel labelRed2 = new JLabel("Red 2:");
        labelRed2.setFont(FUENTE_CONSULTAS);
        panel.add(labelRed2, constraints);
        constraints.gridx = 3;
        panel.add(digitoRed2, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        JLabel labelHost1 = new JLabel("Host 1:");
        labelHost1.setFont(FUENTE_CONSULTAS);
        panel.add(labelHost1, constraints);
        constraints.gridx = 1;
        panel.add(digitoHost1, constraints);
        constraints.gridx = 2;
        JLabel labelHost2 = new JLabel("Host 2:");
        labelHost2.setFont(FUENTE_CONSULTAS);
        panel.add(labelHost2, constraints);
        constraints.gridx = 3;
        panel.add(digitoHost2, constraints);

        // Configurar el título y los campos de texto para la segunda IP
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        JLabel tituloFin = new JLabel("Dirección IP de fin:");
        tituloFin.setFont(FUENTE_CONSULTAS);
        panel.add(tituloFin, constraints);

        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        JLabel labelHost3 = new JLabel("Host 3:");
        labelHost3.setFont(FUENTE_CONSULTAS);
        panel.add(labelHost3, constraints);
        constraints.gridx = 1;
        panel.add(digitoHost3, constraints);
        constraints.gridx = 2;
        JLabel labelHost4 = new JLabel("Host 4:");
        labelHost4.setFont(FUENTE_CONSULTAS);
        panel.add(labelHost4, constraints);
        constraints.gridx = 3;
        panel.add(digitoHost4, constraints);

        panel.setPreferredSize(new Dimension(600, 200));

        // Mostrar el cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(null, panel,
                "Por favor, introduzca las direcciones IP", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Obtener y validar los valores ingresados
            try {
                int red1 = Integer.parseInt(digitoRed1.getText());
                int red2 = Integer.parseInt(digitoRed2.getText());
                int host1 = Integer.parseInt(digitoHost1.getText());
                int host2 = Integer.parseInt(digitoHost2.getText());
                int host3 = Integer.parseInt(digitoHost3.getText());
                int host4 = Integer.parseInt(digitoHost4.getText());

                // Validar los segmentos de IP (0-255)
                if (red1 < 0 || red1 > 255 || red2 < 0 || red2 > 255 ||
                        host1 < 0 || host1 > 255 || host2 < 0 || host2 > 255 ||
                        host3 < 0 || host3 > 255 || host4 < 0 || host4 > 255) {
                    throw new NumberFormatException();
                }

                // Llamar al método de ping con las IPs ingresadas
                Map<String, Boolean> resultadosPing = coordinador.pingIPS(red1, red2, host1, host2, host3, host4);

                // Mostrar los resultados del ping
                StringBuilder resultados = new StringBuilder();
                for (Map.Entry<String, Boolean> entry : resultadosPing.entrySet()) {
                    String ip = entry.getKey();
                    Boolean respuesta = entry.getValue();
                    resultados.append(ip).append(": ").append(respuesta ? "activo" : "inactivo").append("\n");
                }
                // Mostrar los resultados en un cuadro de diálogo
                JOptionPane.showMessageDialog(null, resultados.toString(), "Resultados del rango de IP", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, introduzca valores válidos para las direcciones IP.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void advertencia(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia: ", JOptionPane.WARNING_MESSAGE);

    }


}





