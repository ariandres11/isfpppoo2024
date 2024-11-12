package gui;

import controlador.Coordinador;

import modelo.Equipo;
import modelo.Conexion;
import modelo.Ubicacion;
import modelo.TipoEquipo;
import modelo.TipoPuerto;
import modelo.TipoCable;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.Objects;

public class FrameDatosRed extends JDialog {

    private Coordinador coordinador;
    private static final int MAX_ANCHO = 850;
    private static final int MAX_ALTO = 500;
    private JTabbedPane JTOpciones;
    private JButton JBAgregar;
    private JButton JBModificar;
    private JButton JBAEliminar;
    private JTable JTEquipos;
    private JTable JTConexiones;
    private JTable JTUbicaciones;
    //Nuevos
    private JTable JTTipoEquipo;
    private JTable JTTipoPuerto;
    private JTable JTTipoCable;

    public FrameDatosRed (Coordinador coordinador) {
        this.coordinador = coordinador;

        this.setTitle("Datos de Red: ");
        //this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        this.setSize(MAX_ANCHO, MAX_ALTO);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.agregarOpciones();
        this.agregarBotones();
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setVisible(true);
    }

    public JTable getJTEquipos() {
        return JTEquipos;
    }

    public JTable getJTConexiones() {
        return JTConexiones;
    }

    public JTable getJTUbicaciones() {
        return JTUbicaciones;
    }

    public JTable getJTTipoEquipo() {
        return JTTipoEquipo;
    }

    public JTable getJTTipoPuerto() {
        return JTTipoPuerto;
    }

    public JTable getJTTipoCable() {
        return JTTipoCable;
    }

    public JTable obtenerTablaSeleccionada(String tablaSeleccionada) {
        switch (tablaSeleccionada) {
            case "Equipos" : return getJTEquipos();
            case "Conexiones" : return getJTConexiones();
            case "Ubicaciones" : return getJTUbicaciones();
            case "Tipos de Equipo" : return  getJTTipoEquipo();
            case "Tipos de Puerto" : return getJTTipoPuerto();
            case "Tipos de Cable" : return  getJTTipoCable();
            default : return null;
        }
    }

    private void agregarOpciones () {
        JTOpciones = new JTabbedPane();

        crearTablas();

        JTOpciones.addTab("Equipos", new JScrollPane(JTEquipos));
        JTOpciones.addTab("Conexiones", new JScrollPane(JTConexiones));
        JTOpciones.addTab("Ubicaciones", new JScrollPane(JTUbicaciones));
        JTOpciones.addTab("Tipos de Equipo", new JScrollPane(JTTipoEquipo));
        JTOpciones.addTab("Tipos de Puerto", new JScrollPane(JTTipoPuerto));
        JTOpciones.addTab("Tipos de Cable", new JScrollPane(JTTipoCable));

        // Desabilita el boton modificar si se seleccionar ver los datos de las conexiones
        JTOpciones.addChangeListener(e -> {
            if (Objects.equals(JTOpciones.getTitleAt(JTOpciones.getSelectedIndex()), "Conexiones") || Objects.equals(JTOpciones.getTitleAt(JTOpciones.getSelectedIndex()), "Tipos de Cable") || JTOpciones.getTitleAt(JTOpciones.getSelectedIndex()) == "Tipos de Puerto" ) {
                JBModificar.setEnabled(false);
            } else {
                JBModificar.setEnabled(true);
            }
        });

        this.add(JTOpciones);
    }

    private void crearTablas () {
        // Tabla equipos
        JTEquipos = new JTable();
        actualizarTablaEquipos();

        //Tabla conexiones
        JTConexiones = new JTable();
        actualizarTablaConexiones();

        //Tabla ubicaciones
        JTUbicaciones = new JTable();
        actualizarTablaUbicaciones();

        //Tabla tipos de equipo
        JTTipoEquipo = new JTable();
        actualizarTablaTipoEquipos();

        //Tabla tipos de puerto
        JTTipoPuerto = new JTable();
        actualizarTablaTipoPuertos();

        //Tabla tipos de cable
        JTTipoCable = new JTable();
        actualizarTablaTipoCables();
    }

    public void actualizarTablaEquipos() {
        ArrayList<Equipo> arrayEquipos = (ArrayList<Equipo>) coordinador.listarEquipos();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("codigo");
        modelo.addColumn("descripcion");
        modelo.addColumn("marca");
        modelo.addColumn("modelo");
        modelo.addColumn("estado");

        Object[] fila = new Object[modelo.getColumnCount()];
        for (Equipo equipo : arrayEquipos) {
            fila[0] = equipo.getCodigo();
            fila[1] = equipo.getDescripcion();
            fila[2] = equipo.getMarca();
            fila[3] = equipo.getModelo();
            fila[4] = equipo.isEstado();
            modelo.addRow(fila);
        }
        JTEquipos.setModel(modelo);
    }

    public void actualizarTablaConexiones() {
        ArrayList<Conexion> arrayConexiones = (ArrayList<Conexion>) coordinador.listarConexiones();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo = new DefaultTableModel();
        modelo.addColumn("Equipo 1");
        modelo.addColumn("Equipo 2");
        modelo.addColumn("Cable");
        modelo.addColumn("Puerto1");
        modelo.addColumn("Puerto2");
        modelo.addColumn("estado");

        Object[] fila = new Object[modelo.getColumnCount()];
        for (Conexion conexion : arrayConexiones) {
            fila[0] = conexion.getEquipo1().getCodigo();
            fila[1] = conexion.getEquipo2().getCodigo();
            fila[2] = conexion.getTipoCable().getCodigo();
            fila[3] = conexion.getTipoPuertoEquipo1().getCodigo();
            fila[4] = conexion.getTipoPuertoEquipo2().getCodigo();
            fila[5] = conexion.isEstado();
            modelo.addRow(fila);
        }
        JTConexiones.setModel(modelo);
    }

    public void actualizarTablaUbicaciones() {
        ArrayList<Ubicacion> arrayUbicaciones = (ArrayList<Ubicacion>) coordinador.listarUbicaciones();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo = new DefaultTableModel();
        modelo.addColumn("codigo");
        modelo.addColumn("descripcion");

        Object[] fila = new Object[modelo.getColumnCount()];
        for (Ubicacion ubicacion : arrayUbicaciones) {
            fila[0] = ubicacion.getCodigo();
            fila[1] = ubicacion.getDescripcion();
            modelo.addRow(fila);
        }
        JTUbicaciones.setModel(modelo);
    }

    public void actualizarTablaTipoPuertos() {
        ArrayList<TipoPuerto> arrayTiposPuerto = (ArrayList<TipoPuerto>) coordinador.listarTipoPuertos();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo = new DefaultTableModel();
        modelo.addColumn("codigo");
        modelo.addColumn("descripcion");
        modelo.addColumn("velocidad");

        Object[] fila = new Object[modelo.getColumnCount()];
        for(TipoPuerto tipoPuerto : arrayTiposPuerto){
            fila[0] = tipoPuerto.getCodigo();
            fila[1] = tipoPuerto.getDescripcion();
            fila[2] = tipoPuerto.getVelocidad();
            modelo.addRow(fila);
        }
        JTTipoPuerto.setModel(modelo);
    }

    public void actualizarTablaTipoEquipos() {
        ArrayList<TipoEquipo> arrayTiposEquipo = (ArrayList<TipoEquipo>) coordinador.listarTipoEquipos();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("codigo");
        modelo.addColumn("descripcion");

        Object[] fila = new Object[modelo.getColumnCount()];
        for(TipoEquipo tipoEquipo : arrayTiposEquipo){
            fila[0] = tipoEquipo.getCodigo();
            fila[1] = tipoEquipo.getDescripcion();
            modelo.addRow(fila);
        }
        JTTipoEquipo.setModel(modelo);
    }

    public void actualizarTablaTipoCables() {
        ArrayList<TipoCable> arrayTiposCable = (ArrayList<TipoCable>) coordinador.listarTipoCables();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo = new DefaultTableModel();
        modelo.addColumn("codigo");
        modelo.addColumn("descripcion");
        modelo.addColumn("velocidad");

        Object[] fila = new Object[modelo.getColumnCount()];
        for(TipoCable tipoCable : arrayTiposCable){
            fila[0] = tipoCable.getCodigo();
            fila[1] = tipoCable.getDescripcion();
            fila[2] = tipoCable.getVelocidad();
            modelo.addRow(fila);
        }
        JTTipoCable.setModel(modelo);
    }

    public void agregarBotones() {
        final Dimension DIMENSION_JTF = new Dimension(80,20);
        JPanel JPBotones = new JPanel();
        JBAgregar = new JButton("Agregar");
        JBModificar = new JButton("Modificar");
        JBAEliminar = new JButton("Eliminar");

        JBAgregar.addActionListener(e -> {
            String mensajeTitulo = "Agregar";
            int mensajeModo = JOptionPane.PLAIN_MESSAGE;

            JDAgregar agregarElemento = new JDAgregar(coordinador, this);
            agregarElemento.ventanaAgregar(JTOpciones.getTitleAt(JTOpciones.getSelectedIndex()));

        });

        //Modificar

        JBModificar.addActionListener(e -> {
            String mensajeTitulo = "Modificar";
            int mensajeModo = JOptionPane.PLAIN_MESSAGE;

            String tituloTabla = JTOpciones.getTitleAt(JTOpciones.getSelectedIndex());
            JTable tablaSeleccionada = obtenerTablaSeleccionada(tituloTabla);

            JDModificar modificarElemento = new JDModificar(coordinador,this);
            modificarElemento.ventanaModificar(tituloTabla, tablaSeleccionada.getSelectedRow());
        });

        //Eliminar

        JBAEliminar.addActionListener(e -> {
            String mensajeTitulo = "Eliminar";
            int mensajeModo = JOptionPane.PLAIN_MESSAGE;

            String tituloTabla = JTOpciones.getTitleAt(JTOpciones.getSelectedIndex());
            JTable tablaSeleccionada = obtenerTablaSeleccionada(tituloTabla);

            JDEliminar eliminarElemento = new JDEliminar(coordinador,this);
            eliminarElemento.ventanaEliminar(tituloTabla, tablaSeleccionada.getSelectedRow());

        });

        JPBotones.add(JBAgregar);
        JPBotones.add(JBModificar);
        JPBotones.add(JBAEliminar);

        this.add(JPBotones, BorderLayout.SOUTH);
    }

}
