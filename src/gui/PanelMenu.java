package gui;

import controlador.Coordinador;
import modelo.Conexion;
import modelo.Equipo;
import negocio.roles.RoleContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelMenu extends JPanel {
    private Interfaz interfaz;
    private Coordinador coordinador;

    JButton JBConexiones = new JButton("Mostrar Conexiones");
    JButton JBPingEquipo = new JButton("Ping Equipo");
    JButton JBPingIP = new JButton("Ping a rango de IP");
    JButton JBPingEquipoAEquipo = new JButton("Ping Equipo/Equipo");
    JButton JBMapaActual = new JButton("Mostrar Mapa Actual");
    JButton JBDetectarProblemas = new JButton("Detectar Problemas");

    public PanelMenu(Interfaz interfaz, RoleContext roleContext) {
        this.interfaz = interfaz;
        this.coordinador = interfaz.getCoordinador();

        JBConexiones.addActionListener(new ManejadorBotonesMenu());
        JBPingEquipo.addActionListener(new ManejadorBotonesMenu());
        JBPingIP.addActionListener(new ManejadorBotonesMenu());
        JBPingEquipoAEquipo.addActionListener(new ManejadorBotonesMenu());
        JBMapaActual.addActionListener(new ManejadorBotonesMenu());
        JBDetectarProblemas.addActionListener(new ManejadorBotonesMenu());

        // Agregar botones al panel
        this.add(JBConexiones);
        this.add(JBPingEquipo);
        this.add(JBPingIP);
        this.add(JBPingEquipoAEquipo);
        this.add(JBMapaActual);
        this.add(JBDetectarProblemas);


        this.revalidate();
        this.repaint();
    }


    /**
     * Clase interna que maneja los eventos de acción de los botones del menú.
     */
    private class ManejadorBotonesMenu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == JBConexiones) {
                Equipo equipoOrigen = interfaz.elegirEquipo(coordinador.listarEquipos(), " origen");
                Equipo equipoDestino = interfaz.elegirEquipo(coordinador.listarEquipos(), " destino");
                coordinador.getCalculo().cargarDatos(coordinador.listarConexiones(), coordinador.listarEquipos());
                ArrayList<Conexion> recorrido = (ArrayList<Conexion>) coordinador.calcularMasRapido(equipoOrigen, equipoDestino);
                interfaz.mostrarConexiones(recorrido);
            } else if (e.getSource() == JBPingEquipo) {
                Equipo equipo = interfaz.elegirEquipo(coordinador.listarEquipos(), "l que se quiere saber el ping");
                coordinador.getCalculo().cargarDatos(coordinador.listarConexiones(), coordinador.listarEquipos());
                interfaz.resultadoPingEquipo(coordinador.getCalculo().ping(equipo));
                // Implementar funcionalidad para hacer ping a un equipo
            } else if (e.getSource() == JBPingIP) {
                interfaz.mostrarSeleccionDeIPs();
                // Implementar funcionalidad para hacer ping de un equipo a otro
            } else if (e.getSource() == JBPingEquipoAEquipo) {
                // Implementar funcionalidad para hacer ping a una IP
            } else if (e.getSource() == JBMapaActual) {
                // Implementar funcionalidad para mostrar el mapa actual
            } else if (e.getSource() == JBDetectarProblemas) {
                // Implementar funcionalidad para detectar problemas
            }
        }
    }

}
