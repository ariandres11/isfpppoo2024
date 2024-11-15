package gui;

import controlador.Coordinador;
import modelo.Conexion;
import modelo.Equipo;
import negocio.roles.RoleContext;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static controlador.Constantes.FUENTE_OPCIONES;

public class PanelMenu extends JPanel {
    private Interfaz interfaz;
    private Coordinador coordinador;

    JButton JBConexiones = new JButton("Mostrar Conexiones");
    JButton JBPingEquipo = new JButton("Ping Equipo");
    JButton JBPingIP = new JButton("Ping a rango de IP");
    JButton JBMapaActual = new JButton("Mostrar Mapa Actual");
    JButton JBDetectarProblemas = new JButton("Detectar Problemas");

    public PanelMenu(Interfaz interfaz, RoleContext roleContext) {
        this.interfaz = interfaz;
        this.coordinador = interfaz.getCoordinador();

        JBConexiones.addActionListener(new ManejadorBotonesMenu());
        JBPingEquipo.addActionListener(new ManejadorBotonesMenu());
        JBPingIP.addActionListener(new ManejadorBotonesMenu());
        JBMapaActual.addActionListener(new ManejadorBotonesMenu());
        JBDetectarProblemas.addActionListener(new ManejadorBotonesMenu());

        // Definir tamaños preferidos
        Dimension buttonSize = new Dimension(250, 80);
        JBConexiones.setFont(FUENTE_OPCIONES);
        JBPingEquipo.setFont(FUENTE_OPCIONES);
        JBPingIP.setFont(FUENTE_OPCIONES);
        JBMapaActual.setFont(FUENTE_OPCIONES);
        JBDetectarProblemas.setFont(FUENTE_OPCIONES);

        JBConexiones.setPreferredSize(buttonSize);
        JBPingEquipo.setPreferredSize(buttonSize);
        JBPingIP.setPreferredSize(buttonSize);
        JBMapaActual.setPreferredSize(buttonSize);
        JBDetectarProblemas.setPreferredSize(buttonSize);

        // Usar un LayoutManager que respete los tamaños preferidos
        setLayout(new FlowLayout());

        // Agregar botones al panel
        this.add(JBConexiones);
        this.add(JBPingEquipo);
        this.add(JBPingIP);
        this.add(JBMapaActual);
        this.add(JBDetectarProblemas);

        this.revalidate();
        this.repaint();
    }

    public JButton getJBConexiones() {
        return JBConexiones;
    }

    public void setJBConexiones(JButton JBConexiones) {
        this.JBConexiones = JBConexiones;
    }

    public JButton getJBPingEquipo() {
        return JBPingEquipo;
    }

    public void setJBPingEquipo(JButton JBPingEquipo) {
        this.JBPingEquipo = JBPingEquipo;
    }

    public JButton getJBPingIP() {
        return JBPingIP;
    }

    public void setJBPingIP(JButton JBPingIP) {
        this.JBPingIP = JBPingIP;
    }

    public JButton getJBMapaActual() {
        return JBMapaActual;
    }

    public void setJBMapaActual(JButton JBMapaActual) {
        this.JBMapaActual = JBMapaActual;
    }

    public JButton getJBDetectarProblemas() {
        return JBDetectarProblemas;
    }

    public void setJBDetectarProblemas(JButton JBDetectarProblemas) {
        this.JBDetectarProblemas = JBDetectarProblemas;
    }

    /**
     * Clase interna que maneja los eventos de acción de los botones del menú.
     */
    private class ManejadorBotonesMenu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == JBConexiones) {
                coordinador.cargarDatos();
                Equipo equipoOrigen = interfaz.elegirEquipo(coordinador.listarEquipos(), "origen");
                Equipo equipoDestino = interfaz.elegirEquipo(coordinador.listarEquipos(), "destino");
                coordinador.cargarDatos();
                ArrayList<Conexion> recorrido = (ArrayList<Conexion>) coordinador.calcularMasRapido(equipoOrigen, equipoDestino);
                interfaz.mostrarConexiones(recorrido);
            } else if (e.getSource() == JBPingEquipo) {
                interfaz.resultadoPingEquipo(coordinador.ping());
            } else if (e.getSource() == JBPingIP) {
                JDPingRangoIP ventanaRangoIP = new JDPingRangoIP(interfaz, coordinador);
            } else if (e.getSource() == JBMapaActual) {
                // Implementar la acción
                coordinador.cargarDatos();
                ArrayList<Conexion> recorrido = (ArrayList<Conexion>) coordinador.listarConexiones();
                interfaz.mostrarConexiones(recorrido);
            } else if (e.getSource() == JBDetectarProblemas) {
                Equipo equipo = interfaz.elegirEquipo(coordinador.listarEquipos(), "el que se quiere conocer los problemas");
                coordinador.cargarDatos();
                ArrayList<Conexion> recorrido = (ArrayList<Conexion>) coordinador.detectarProblemas(equipo);
                interfaz.mostrarConexiones(recorrido);
            }
        }
    }
}
