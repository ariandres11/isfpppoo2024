package gui;

import controlador.Coordinador;
import excepciones.*;
import modelo.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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

    private void agregarOpciones () {
        JTOpciones = new JTabbedPane();

        crearTablas();

        JTOpciones.addTab("Equipos", JTEquipos);
        JTOpciones.addTab("Conexiones", JTConexiones);
        JTOpciones.addTab("Ubicaciones", JTUbicaciones);
        JTOpciones.addTab("Tipos de Equipo", JTTipoEquipo);
        JTOpciones.addTab("Tipos de Puerto", JTTipoPuerto);
        JTOpciones.addTab("Tipos de Cable", JTTipoCable);

        // Desabilita el boton modificar si se seleccionar ver los datos de las conexiones
        JTOpciones.addChangeListener(e -> {
            if (Objects.equals(JTOpciones.getTitleAt(JTOpciones.getSelectedIndex()), "Conexiones") || Objects.equals(JTOpciones.getTitleAt(JTOpciones.getSelectedIndex()), "Tipo de cable") || Objects.equals(JTOpciones.getTitleAt(JTOpciones.getSelectedIndex()), "Tipos de cable") || JTOpciones.getTitleAt(JTOpciones.getSelectedIndex()) == "Tipos de puerto" ) {
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
        modelo.addRow(new String[] {"Código", "Descripción", "Marca", "Modelo", "Estado"});

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
        modelo.addRow(new String[] {"Equipo 1","Equipo 2","Cable", "Puerto 1", "Puerto 2", "Estado"});

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
        modelo.addRow(new String[] {"Código","Descripción"});

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
        modelo.addRow(new String[] {"Código","Descripción", "Velocidad(Mbps)"});

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
        modelo.addRow(new String[] {"Código","Descripción"});

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
        modelo.addRow(new String[] {"Código","Descripción", "Velocidad(Mbps)"});

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

            String tituloTabla = JTOpciones.getTitleAt(JTOpciones.getSelectedIndex());

            switch(tituloTabla){
                case "Equipos":
                    JDialog JDAgregarEquipo = new JDialog();
                    JDAgregarEquipo.setSize(540,480);
                    JDAgregarEquipo.setResizable(false);
                    setTitle("Agregar Equipo");

                    List<String> direccionesIP = new ArrayList<String>();
                    List<TipoPuerto> tipoPuertos = new ArrayList<TipoPuerto>();
                    List<Integer> puertosCantidad = new ArrayList<Integer>();

                    final int JL_X_COORD = 20;
                    final int JL_Y_COORD = 15;
                    final int WIDTH = 120;
                    final int HEIGHT = 20;

                    JLabel JLCodigo = new JLabel("Codigo (*):");
                    JLCodigo.setBounds(JL_X_COORD,JL_Y_COORD,WIDTH,HEIGHT);
                    JLabel JLDescripcion = new JLabel("Descripción:");
                    JLDescripcion.setBounds(JL_X_COORD,JL_Y_COORD*3,WIDTH,HEIGHT);
                    JLabel JLMarca = new JLabel("Marca: ");
                    JLMarca.setBounds(JL_X_COORD,JL_Y_COORD*5,WIDTH,HEIGHT);
                    JLabel JLModelo = new JLabel("Modelo: ");
                    JLModelo.setBounds(JL_X_COORD,JL_Y_COORD*7,WIDTH,HEIGHT);
                    JLabel JLTipoEquipo = new JLabel("Tipo de Equipo: ");
                    JLTipoEquipo.setBounds(JL_X_COORD,JL_Y_COORD*9,WIDTH,HEIGHT);
                    JLabel JLUbicacion = new JLabel("Ubicación: ");
                    JLUbicacion.setBounds(JL_X_COORD,JL_Y_COORD*11,WIDTH,HEIGHT);
                    JLabel JLEstado = new JLabel("Estado (*): ");
                    JLEstado.setBounds(JL_X_COORD,JL_Y_COORD*13,WIDTH,HEIGHT);

                    JTextField JTFCodigo = new JTextField();
                    JTFCodigo.setBounds(JL_X_COORD*6,JL_Y_COORD,WIDTH,HEIGHT);
                    JTextField JTFDescripcion = new JTextField();
                    JTFDescripcion.setBounds(JL_X_COORD*6,JL_Y_COORD*3,WIDTH,HEIGHT);
                    JTextField JTFMarca = new JTextField();
                    JTFMarca.setBounds(JL_X_COORD*6,JL_Y_COORD*5,WIDTH,HEIGHT);
                    JTextField JTFModelo = new JTextField();
                    JTFModelo.setBounds(JL_X_COORD*6,JL_Y_COORD*7,WIDTH,HEIGHT);


                    JComboBox<String> JCBTipoEquipo = new JComboBox<String>();
                    JCBTipoEquipo.setBounds(JL_X_COORD*6,JL_Y_COORD*9,WIDTH/2,HEIGHT);
                    JComboBox<String> JCBUbicacion = new JComboBox<String>();
                    JCBUbicacion.setBounds(JL_X_COORD*6,JL_Y_COORD*11,WIDTH/2,HEIGHT);
                    JToggleButton JTBEstado = new JToggleButton();
                    JTBEstado.setBounds(JL_X_COORD*6,JL_Y_COORD*13,WIDTH,HEIGHT);
                    JTBEstado.setText("Inactivo");
                    JTBEstado.addChangeListener(e2 -> {
                        if( JTBEstado.isSelected() ) JTBEstado.setText("Activo");
                        else JTBEstado.setText("Inactivo");
                    });

                    for (TipoEquipo equipo : coordinador.listarTipoEquipos()) {
                        JCBTipoEquipo.addItem(equipo.getCodigo());
                    }

                    for (Ubicacion ubicacion : coordinador.listarUbicaciones()) {
                        JCBUbicacion.addItem(ubicacion.getCodigo());
                    }

                    JLabel JLTipoPuerto = new JLabel("Tipo de Puerto/Cantidad (*): ");
                    JLTipoPuerto.setBounds(JL_X_COORD*13,JL_Y_COORD,WIDTH*2,HEIGHT);
                    JTextField JTFPuertoCantidad = new JTextField();
                    JTFPuertoCantidad.setBounds(JL_X_COORD*17,JL_Y_COORD*3,WIDTH/2,HEIGHT);

                    JComboBox<String> JCBPuertos = new JComboBox<String>();
                    JCBPuertos.setBounds(JL_X_COORD*13,JL_Y_COORD*3,WIDTH/2,HEIGHT);

                    for (TipoPuerto puerto : coordinador.listarTipoPuertos()) {
                        JCBPuertos.addItem(puerto.getCodigo());
                    }

                    JTextArea JTAPuertosAgregados = new JTextArea();
                    JTAPuertosAgregados.setBounds(JL_X_COORD*13,JL_Y_COORD*5,WIDTH*2,HEIGHT*7);
                    JTAPuertosAgregados.setLineWrap(true);
                    JTAPuertosAgregados.setWrapStyleWord(true);
                    JTAPuertosAgregados.setEditable(false);

                    JButton JBAgregarPuerto = new JButton("+");
                    JBAgregarPuerto.setBounds(JL_X_COORD*21,JL_Y_COORD*3,WIDTH/2,HEIGHT);
                    JBAgregarPuerto.addActionListener(e4 -> {

                        TipoPuerto tipoPuerto = new TipoPuerto();

                        tipoPuerto = coordinador.buscarTipoPuerto(JCBPuertos.getItemAt(
                                JCBPuertos.getSelectedIndex()));

                        // verifica que se ingrese un puerto valido
                        if (!tipoPuertos.contains(tipoPuerto) && JTFPuertoCantidad.getText().matches("[0-9]+")) {
                            tipoPuertos.add(tipoPuerto);
                            puertosCantidad.add(Integer.parseInt(JTFPuertoCantidad.getText()));

                            JTAPuertosAgregados.setText(JTAPuertosAgregados.getText() +
                                    JCBPuertos.getItemAt(JCBPuertos.getSelectedIndex()) +
                                    "," + JTFPuertoCantidad.getText() + "; ");
                        } else {
                            JOptionPane.showMessageDialog(JDAgregarEquipo, "Puerto repetido/ cantidad de " +
                                                            "puertos invalida. Por favor, vuelva a ingresar los datos.");
                        }
                    });

                    JLabel JLDireccionesIP = new JLabel("DireccionesIP: ");
                    JLDireccionesIP.setBounds(JL_X_COORD*13,JL_Y_COORD*15,WIDTH,HEIGHT);
                    JTextField JTFDireccionIP = new JTextField();
                    JTFDireccionIP.setBounds(JL_X_COORD*13,JL_Y_COORD*17,WIDTH,HEIGHT);

                    JTextArea JTADireccionesIPAgregadas = new JTextArea();
                    JTADireccionesIPAgregadas.setBounds(JL_X_COORD*13,JL_Y_COORD*19,WIDTH*2,HEIGHT*7);
                    JTADireccionesIPAgregadas.setLineWrap(true);
                    JTADireccionesIPAgregadas.setWrapStyleWord(true);
                    JTADireccionesIPAgregadas.setEditable(false);

                    JButton JBAgregarDireccionIP = new JButton("+");
                    JBAgregarDireccionIP.setBounds(JL_X_COORD*21,JL_Y_COORD*17,WIDTH/2,HEIGHT);
                    JBAgregarDireccionIP.addActionListener(e5 -> {

                        String direccionIP = JTFDireccionIP.getText();

                        // verifica si ya se ingreso la direccion IP + si posee el formato correcto
                        if(!direccionesIP.contains(direccionIP) && coordinador.verificarIP(direccionIP)) {
                            direccionesIP.add(direccionIP);

                            JTADireccionesIPAgregadas.setText(JTADireccionesIPAgregadas.getText() +
                                    direccionIP + ", ");
                        } else {
                            JOptionPane.showMessageDialog(JDAgregarEquipo, "Direccion IP repetida/ con formato " +
                                                            "erroneo. Por favor, vuelva a ingresar una direccion IP");
                        }
                    });

                    JButton JBAgregarEquipo = new JButton("Agregar");
                    JBAgregarEquipo.setBounds(JL_X_COORD*3,JL_Y_COORD*20,WIDTH,HEIGHT*2);
                    JBAgregarEquipo.addActionListener(e6 -> {
                        String codigoEquipo = JTFCodigo.getText();
                        String descripcionEquipo = JTFDescripcion.getText();
                        String marcaEquipo = JTFMarca.getText();
                        String modeloEquipo = JTFModelo.getText();
                        Ubicacion ubicacionEquipo = coordinador.buscarUbicacion(JCBUbicacion.getItemAt(JCBUbicacion.getSelectedIndex()));;
                        TipoEquipo tipoEquipo = coordinador.buscarTipoEquipo(JCBTipoEquipo.getItemAt(JCBTipoEquipo.getSelectedIndex()));
                        boolean estadoEquipo = JTBEstado.isSelected();

                        // verifica que la lista de puertos no este vacia + que el codigo del equipo no sea repetido ni
                        // que sea vacio
                        if (!tipoPuertos.isEmpty() && !codigoEquipo.isEmpty() &&
                                !coordinador.listarEquipoCodigos().contains(codigoEquipo)) {

                            Equipo equipoNuevo = new Equipo(codigoEquipo, descripcionEquipo, marcaEquipo, modeloEquipo,
                                    puertosCantidad.getFirst(), tipoPuertos.getFirst(),
                                    ubicacionEquipo, tipoEquipo, estadoEquipo);

                            if (tipoPuertos.size() > 1) {
                                for (int i = 1; i < tipoPuertos.size(); i++) {
                                    equipoNuevo.agregarPuerto(puertosCantidad.get(i), tipoPuertos.get(i));
                                }
                            }

                            equipoNuevo.setDireccionesIP(direccionesIP);

                            try {
                                coordinador.agregarEquipo(equipoNuevo);
                                actualizarTablaEquipos();
                                JOptionPane.showMessageDialog(JDAgregarEquipo, "Equipo agregado exitosamente.");
                                JDAgregarEquipo.dispose();
                            } catch (EquipoRepetidoException EREe) {
                                JOptionPane.showMessageDialog(JDAgregarEquipo, EREe.getMessage());
                            }
                        } else {
                            JOptionPane.showMessageDialog(JDAgregarEquipo, "No se pudo agregar el equipo nuevo, " +
                                                            "verifique los datos ingresados.");
                        }
                    });

                    JPanel JPAgregarEquipo = new JPanel();
                    JPAgregarEquipo.setLayout(null);

                    JPAgregarEquipo.add(JLCodigo);
                    JPAgregarEquipo.add(JTFCodigo);

                    JPAgregarEquipo.add(JLDescripcion);
                    JPAgregarEquipo.add(JTFDescripcion);

                    JPAgregarEquipo.add(JLMarca);
                    JPAgregarEquipo.add(JTFMarca);

                    JPAgregarEquipo.add(JLModelo);
                    JPAgregarEquipo.add(JTFModelo);

                    JPAgregarEquipo.add(JLTipoEquipo);
                    JPAgregarEquipo.add(JCBTipoEquipo);

                    JPAgregarEquipo.add(JLUbicacion);
                    JPAgregarEquipo.add(JCBUbicacion);

                    JPAgregarEquipo.add(JLEstado);
                    JPAgregarEquipo.add(JTBEstado);

                    JPAgregarEquipo.add(JLTipoPuerto);
                    JPAgregarEquipo.add(JCBPuertos);
                    JPAgregarEquipo.add(JTFPuertoCantidad);
                    JPAgregarEquipo.add(JBAgregarPuerto);
                    JPAgregarEquipo.add(JTAPuertosAgregados);

                    JPAgregarEquipo.add(JLDireccionesIP);
                    JPAgregarEquipo.add(JTFDireccionIP);
                    JPAgregarEquipo.add(JBAgregarDireccionIP);
                    JPAgregarEquipo.add(JTADireccionesIPAgregadas);

                    JPAgregarEquipo.add(JBAgregarEquipo);

                    JDAgregarEquipo.add(JPAgregarEquipo);
                    JDAgregarEquipo.setModalityType(ModalityType.APPLICATION_MODAL);
                    JDAgregarEquipo.setVisible(true);
                    break;
                case "Conexiones":
                    JDialog JDAgregarConexion = new JDialog();
                    JDAgregarConexion.setSize(500,250);
                    JDAgregarConexion.setResizable(false);
                    setTitle("Agregar Conexion");

                    JLabel JLEquipo1 = new JLabel("Equipo 1 (*): ");
                    JLabel JLEquipo2 = new JLabel("Equipo 2 (*): ");
                    JLabel JLPuerto1 = new JLabel("Puerto 1 (*): ");
                    JLabel JLPuerto2 = new JLabel("Puerto 2 (*): ");
                    JLabel JLTipoCable = new JLabel("Tipo de Cable (*): ");

                    JComboBox<String> JCBEquipo1 = new JComboBox<String>();
                    JComboBox<String> JCBEquipo2 = new JComboBox<String>();
                    JComboBox<String> JCBPuerto1 = new JComboBox<String>();
                    JComboBox<String> JCBPuerto2 = new JComboBox<String>();
                    JComboBox<String> JCBTipoCable = new JComboBox<String>();

                    JCBEquipo1.addItemListener(e14 -> {
                        JCBPuerto1.removeAllItems();
                        for(Equipo equipo : coordinador.listarEquipos()) {
                            if(equipo.getCodigo() == JCBEquipo1.getItemAt(JCBEquipo1.getSelectedIndex())) {
                                for (TipoPuerto tipoPuerto : equipo.getTipoPuertos()) {
                                    JCBPuerto1.addItem(tipoPuerto.getCodigo());
                                }
                                break;
                            }
                        }
                    });

                    JCBEquipo2.addItemListener(e15 -> {
                        JCBPuerto2.removeAllItems();
                        for(Equipo equipo : coordinador.listarEquipos()) {
                            if(equipo.getCodigo() == JCBEquipo2.getItemAt(JCBEquipo2.getSelectedIndex())) {
                                for (TipoPuerto tipoPuerto : equipo.getTipoPuertos()) {
                                    JCBPuerto2.addItem(tipoPuerto.getCodigo());
                                }
                                break;
                            }
                        }
                    });

                    for (Equipo equipo : coordinador.listarEquipos()) {
                        JCBEquipo1.addItem(equipo.getCodigo());
                        JCBEquipo2.addItem(equipo.getCodigo());
                    }

                    for (TipoPuerto puerto : coordinador.listarTipoPuertos()) {
                        JCBPuerto1.addItem(puerto.getCodigo());
                        JCBPuerto2.addItem(puerto.getCodigo());
                    }

                    for (TipoCable cable : coordinador.listarTipoCables()) {
                        JCBTipoCable.addItem(cable.getCodigo());
                    }

                    JButton JBAgregarConexion = new JButton("Agregar");
                    JBAgregarConexion.addActionListener(e3 -> {
                        String codigoEquipo1 = JCBEquipo1.getItemAt(JCBEquipo1.getSelectedIndex());
                        String codigoEquipo2 = JCBEquipo2.getItemAt(JCBEquipo2.getSelectedIndex());
                        String codigoPuerto1 = JCBPuerto1.getItemAt(JCBPuerto1.getSelectedIndex());
                        String codigoPuerto2 = JCBPuerto2.getItemAt(JCBPuerto2.getSelectedIndex());
                        String codigoTipoCable = JCBTipoCable.getItemAt(JCBTipoCable.getSelectedIndex());

                        Equipo equipo1 = coordinador.buscarEquipo(codigoEquipo1);
                        Equipo equipo2 = coordinador.buscarEquipo(codigoEquipo2);
                        TipoPuerto puerto1 = coordinador.buscarTipoPuerto(codigoPuerto1);
                        TipoPuerto puerto2 = coordinador.buscarTipoPuerto(codigoPuerto2);
                        TipoCable cable1 = coordinador.buscarTipoCable(codigoTipoCable);

                        Conexion conexion = new Conexion(equipo1, equipo2, cable1, puerto1, puerto2, false );

                        try {
                            coordinador.agregarConexion(conexion);
                            actualizarTablaConexiones();
                            JOptionPane.showMessageDialog(this, "Conexión agregada exitosamente.");
                            JDAgregarConexion.dispose();
                        } catch (ConexionRepetidaException CREe) {
                            JOptionPane.showMessageDialog(this, CREe.getMessage());
                        }
                    });

                    JPanel JPAgregarConexion = new JPanel();
                    JPAgregarConexion.add(JLEquipo1);
                    JPAgregarConexion.add(JCBEquipo1);
                    JPAgregarConexion.add(JLEquipo2);
                    JPAgregarConexion.add(JCBEquipo2);
                    JPAgregarConexion.add(JLPuerto1);
                    JPAgregarConexion.add(JCBPuerto1);
                    JPAgregarConexion.add(JLPuerto2);
                    JPAgregarConexion.add(JCBPuerto2);
                    JPAgregarConexion.add(JLTipoCable);
                    JPAgregarConexion.add(JCBTipoCable);
                    JPAgregarConexion.add(JBAgregarConexion);

                    JDAgregarConexion.add(JPAgregarConexion);
                    JDAgregarConexion.setModalityType(ModalityType.APPLICATION_MODAL);
                    JDAgregarConexion.setVisible(true);
                    break;
                case "Ubicaciones":
                    JDialog JDAgregarUbicacion = new JDialog();
                    JDAgregarUbicacion.setSize(300,100);
                    JDAgregarUbicacion.setResizable(false);
                    JDAgregarUbicacion.setTitle("Agregar Ubicacion");

                    JLabel JLCodigoUbicacion = new JLabel("Codigo: ");
                    JLabel JLDescripcionUbicacion = new JLabel("Descripcion: ");

                    JTextField JTFCodigoUbicacion = new JTextField();
                    JTFCodigoUbicacion.setPreferredSize(new Dimension(70,20));
                    JTextField JTFDescripcionUbicacion = new JTextField();
                    JTFDescripcionUbicacion.setPreferredSize(new Dimension(70,20));

                    JButton JBAgregarUbicacion = new JButton("Agregar");
                    JBAgregarUbicacion.addActionListener(e1 -> {
                        String codigoUbicacion = JTFCodigoUbicacion.getText();
                        String descripcionUbicacion = JTFDescripcionUbicacion.getText();

                        Ubicacion ubicacion = new Ubicacion(codigoUbicacion,descripcionUbicacion);

                        try {
                            coordinador.agregarUbicacion(ubicacion);
                            actualizarTablaUbicaciones();
                            JOptionPane.showMessageDialog(JDAgregarUbicacion, "Ubicacion agregada exitosamente.");
                            JDAgregarUbicacion.dispose();
                        } catch (UbicacionRepetidaException UREe) {
                            JOptionPane.showMessageDialog(this, UREe.getMessage());
                        }
                    });

                    JPanel JPAgregarUbicacion = new JPanel();
                    JPAgregarUbicacion.add(JLCodigoUbicacion);
                    JPAgregarUbicacion.add(JTFCodigoUbicacion);
                    JPAgregarUbicacion.add(JLDescripcionUbicacion);
                    JPAgregarUbicacion.add(JTFDescripcionUbicacion);
                    JPAgregarUbicacion.add(JBAgregarUbicacion);

                    JDAgregarUbicacion.add(JPAgregarUbicacion);
                    JDAgregarUbicacion.setModalityType(ModalityType.APPLICATION_MODAL);
                    JDAgregarUbicacion.setVisible(true);

                    break;
                case "Tipos de Equipo":

                    JDialog JDAgregarTipoEquipo = new JDialog();
                    JDAgregarTipoEquipo.setSize(300,100);
                    JDAgregarTipoEquipo.setResizable(false);
                    JDAgregarTipoEquipo.setTitle("Agregar Ubicacion");

                    JLabel JLCodigoTipoEquipo = new JLabel("Codigo: ");
                    JLabel JLDescripcionTipoEquipo = new JLabel("Descripcion: ");

                    JTextField JTFCodigoTipoEquipo = new JTextField();
                    JTFCodigoTipoEquipo.setPreferredSize(new Dimension(70,20));
                    JTextField JTFDescripcionTipoEquipo = new JTextField();
                    JTFDescripcionTipoEquipo.setPreferredSize(new Dimension(70,20));

                    JButton JBAgregarTipoEquipo = new JButton("Agregar");
                    JBAgregarTipoEquipo.addActionListener(e1 -> {
                        String codigoTipoEquipo = JTFCodigoTipoEquipo.getText();
                        String descripcionTipoEquipo = JTFDescripcionTipoEquipo.getText();

                        TipoEquipo tipoEquipo = new TipoEquipo();
                        tipoEquipo.setCodigo(codigoTipoEquipo);
                        tipoEquipo.setDescripcion(descripcionTipoEquipo);

                        try {
                            //coordinador.agregarTipoEquipo(tipoEquipo);
                            actualizarTablaTipoEquipos();
                            JOptionPane.showMessageDialog(JDAgregarTipoEquipo, "Tipo de equipo agregado correctamente.");
                            JDAgregarTipoEquipo.dispose();
                        } catch (TipoEquipoRepetidoException TER) {
                            JOptionPane.showMessageDialog(this, TER.getMessage());
                        }
                    });

                    JPanel JPAgregarTipoEquipo = new JPanel();
                    JPAgregarTipoEquipo.add(JLCodigoTipoEquipo);
                    JPAgregarTipoEquipo.add(JTFCodigoTipoEquipo);
                    JPAgregarTipoEquipo.add(JLDescripcionTipoEquipo);
                    JPAgregarTipoEquipo.add(JTFDescripcionTipoEquipo);
                    JPAgregarTipoEquipo.add(JBAgregarTipoEquipo);

                    JDAgregarTipoEquipo.add(JPAgregarTipoEquipo);
                    JDAgregarTipoEquipo.setModalityType(ModalityType.APPLICATION_MODAL);
                    JDAgregarTipoEquipo.setVisible(true);

                    break;
                case "Tipos de Puerto":
                    break;
                case "Tipos de Cable":
                    break;
            }
        });

        //Modificar

        JBModificar.addActionListener(e -> {
            String mensajeTitulo = "Modificar";
            int mensajeModo = JOptionPane.PLAIN_MESSAGE;

            String tituloTabla = JTOpciones.getTitleAt(JTOpciones.getSelectedIndex());

            JLabel JLCodigo = new JLabel("Codigo: ");
            JLabel JLDescripcion = new JLabel("Descripcion: ");
            switch(tituloTabla) {

                case "Equipos":
                    JDialog JDModificarEquipo = new JDialog();
                    JDModificarEquipo.setSize(300,500);
                    JDModificarEquipo.setResizable(false);
                    JDModificarEquipo.setTitle("Elegir Equipo a Modificar");

                    JLCodigo = new JLabel("Codigo: ");
                    JLDescripcion = new JLabel("Descripcion: ");
                    JLabel JLMarca = new JLabel("Marca: ");
                    JLabel JLModelo = new JLabel("Modelo: ");

                    JComboBox JCBCodigoEquipo = new JComboBox<String>();

                    for (Equipo equipo : coordinador.listarEquipos()) {
                        JCBCodigoEquipo.addItem(equipo.getCodigo());
                    }

                    JButton JBModificarEquipo = new JButton("Modificar");
                    JBModificarEquipo.addActionListener(e11 -> {
                        //
                        //
                        //
                        //
                    });

                    JPanel JPModificarEquipo = new JPanel();

                    JPModificarEquipo.add(JLCodigo);
                    JPModificarEquipo.add(JCBCodigoEquipo);
                    JPModificarEquipo.add(JLDescripcion);
                    JPModificarEquipo.add(JLMarca);
                    JPModificarEquipo.add(JLModelo);
                    JPModificarEquipo.add(JBModificarEquipo);

                    JDModificarEquipo.add(JPModificarEquipo);
                    JDModificarEquipo.setModalityType(ModalityType.APPLICATION_MODAL);
                    JDModificarEquipo.setVisible(true);
                    break;
                    //Conexiones no se modifican, solo se eliminan y agregan
                case "Ubicaciones":
                    JDialog JDModificarUbicacion = new JDialog();
                    JDModificarUbicacion.setSize(300,100);
                    JDModificarUbicacion.setResizable(false);
                    JDModificarUbicacion.setTitle("Elegir Ubicacion a Modificar");

                    JLCodigo = new JLabel("Codigo: ");
                    JLDescripcion = new JLabel("Descripcion: ");

                    JComboBox JCBCodigoUbicacion = new JComboBox<String>();
                    JLabel finalJLDescripcion = JLDescripcion;
                    JCBCodigoUbicacion.addItemListener(e12 -> {
                        for (Ubicacion ubicacion : coordinador.listarUbicaciones()) {
                            if(ubicacion.getCodigo() == JCBCodigoUbicacion.getItemAt(JCBCodigoUbicacion.getSelectedIndex())) {
                                finalJLDescripcion.setText("Descripcion: " + ubicacion.getDescripcion());
                            }
                        }
                    });

                    for(Ubicacion ubicacion : coordinador.listarUbicaciones()) {
                        JCBCodigoUbicacion.addItem(ubicacion.getCodigo());
                    }

                    JButton JBModificarUbicacion = new JButton("Modificar");
                    JBModificarUbicacion.addActionListener(e13 -> {
                        //
                        //
                        //
                        //
                    });

                    JPanel JPModificarUbicacion = new JPanel();

                    JPModificarUbicacion.add(JLCodigo);
                    JPModificarUbicacion.add(JCBCodigoUbicacion);
                    JPModificarUbicacion.add(JLDescripcion);
                    JPModificarUbicacion.add(JBModificarUbicacion);

                    JDModificarUbicacion.add(JPModificarUbicacion);
                    JDModificarUbicacion.setModalityType(ModalityType.APPLICATION_MODAL);
                    JDModificarUbicacion.setVisible(true);
                    break;
                case "Tipos de Equipo":
                    break;
                case "Tipos de Puerto":
                    break;
                case "Tipos de Cable":
                    break;

            }


        });

        //Eliminar

        JBAEliminar.addActionListener(e -> {
            String mensajeTitulo = "Eliminar";
            int mensajeModo = JOptionPane.PLAIN_MESSAGE;

                    String tituloTabla = JTOpciones.getTitleAt(JTOpciones.getSelectedIndex());

                    switch(tituloTabla) {

                        case "Equipos":
                            /*
                            JDialog JDEliminarEquipo = new JDialog();
                            JDEliminarEquipo.setSize(300,300);
                            JDEliminarEquipo.setResizable(false);
                            JDEliminarEquipo.setTitle("Eliminar Equipo");

                            JLabel JLCodigo = new JLabel("Codigo: ");
                            JLabel JLDescripcion = new JLabel("descripcion: ");
                            JLabel JLMarca = new JLabel("marca: ");
                            JLabel JLModelo = new JLabel("modelo: ");
                            JLabel JLUbicacion = new JLabel("Ubicacion");

                            JComboBox<String> JCBEquipo = new JComboBox<String>();
                            JCBEquipo.addItemListener(e9 -> {
                                Equipo equipoInfo = null;
                                equipoInfo = coordinador.buscarEquipo(JCBEquipo.getItemAt(JCBEquipo.getSelectedIndex()));

                                JLDescripcion.setText("descripcion: " + equipoInfo.getDescripcion());
                                JLMarca.setText("marca: " + equipoInfo.getMarca());
                                JLModelo.setText("modelo: " + equipoInfo.getModelo());
                                JLUbicacion.setText("ubicacion: " + equipoInfo.getUbicacion().getDescripcion());

                            });

                            for (Equipo equipo : coordinador.listarEquipos()) {
                                JCBEquipo.addItem(equipo.getCodigo());
                            }

                            JButton JBEliminarEquipo = new JButton("Eliminar");
                            JBEliminarEquipo.addActionListener(e10 -> {
                                Equipo equipo = null;
                                equipo = coordinador.buscarEquipo(JCBEquipo.getItemAt(JCBEquipo.getSelectedIndex()));

                                coordinador.borrarEquipo(equipo);
                                actualizarTablaEquipos();
                                JOptionPane.showMessageDialog(JDEliminarEquipo,"Equipo eliminado exitosamente.");
                                JDEliminarEquipo.dispose();
                            });

                            JPanel JPEliminarEquipo = new JPanel();
                            JPEliminarEquipo.add(JLCodigo);
                            JPEliminarEquipo.add(JCBEquipo);
                            JPEliminarEquipo.add(JLDescripcion);
                            JPEliminarEquipo.add(JLMarca);
                            JPEliminarEquipo.add(JLModelo);
                            JPEliminarEquipo.add(JLUbicacion);
                            JPEliminarEquipo.add(JBEliminarEquipo);

                            JDEliminarEquipo.add(JPEliminarEquipo);
                            JDEliminarEquipo.setModalityType(ModalityType.APPLICATION_MODAL);
                            JDEliminarEquipo.setVisible(true);
                            */

                            int filaEquipo = JTEquipos.getSelectedRow();
                            if( filaEquipo != -1) {
                                String codigoEquipo = JTEquipos.getModel().getValueAt(filaEquipo, 0).toString();

                                Equipo equipo = coordinador.buscarEquipo(codigoEquipo);
                                try {
                                    coordinador.borrarEquipo(equipo);
                                    actualizarTablaEquipos();
                                    JOptionPane.showMessageDialog(this, "Equipo eliminado exitosamente.");
                                } catch (EquipoNoExistenteException | EquipoEnUsoException ENEEe) {
                                    JOptionPane.showMessageDialog(this, ENEEe.getMessage());
                                }
                            }
                            break;
                        case "Conexiones":
                                /*
                            JDialog JDEliminarConexion = new JDialog();
                                JDEliminarConexion.setSize(300,300);
                                JDEliminarConexion.setResizable(false);
                                JDEliminarConexion.setTitle("Eliminar Conexion");

                                JLabel JLCodigoConexion = new JLabel("Conexion: ");
                                JLabel JLEquipo1 = new JLabel("Equipo1: ");
                                JLabel JLEquipo2 = new JLabel("Equipo2: ");
                                JLabel JLCable = new JLabel("Cable");

                                JComboBox<String> JCBConexiones = new JComboBox<String>();
                                JCBConexiones.addItemListener(e7 -> {

                                    Conexion conexionInfo = (Conexion) coordinador.listarConexiones().toArray()[JCBConexiones.getSelectedIndex()];

                                    JLEquipo1.setText("Equipo1: " + conexionInfo.getEquipo1Codigo() + ", " +
                                            conexionInfo.getEquipo1Descripcion());
                                    JLEquipo2.setText("Equipo2: " + conexionInfo.getEquipo2Codigo() + ", " +
                                            conexionInfo.getEquipo2Descripcion());
                                    JLCable.setText("Cable: " + conexionInfo.getTipoCableCodigo() + ", " +
                                            conexionInfo.getTipoCableDescripcion());

                                });

                                for (int i=0 ; i<coordinador.listarConexiones().size(); i++) {
                                    JCBConexiones.addItem(String.valueOf(i));
                                }

                                JButton JBEliminarConexion = new JButton("Eliminar");
                                JBEliminarConexion.addActionListener(e8 -> {
                                    Conexion conexion = (Conexion) coordinador.listarConexiones().toArray()[JCBConexiones.getSelectedIndex()];

                                    coordinador.borrarConexion(conexion);
                                    actualizarTablaConexiones();
                                    JOptionPane.showMessageDialog(JDEliminarConexion,"Conexion eliminada exitosamente.");
                                    JDEliminarConexion.dispose();
                                });

                                JPanel JPEliminarConexion = new JPanel();

                                JPEliminarConexion.add(JLCodigoConexion);
                                JPEliminarConexion.add(JCBConexiones);
                                JPEliminarConexion.add(JLEquipo1);
                                JPEliminarConexion.add(JLEquipo2);
                                JPEliminarConexion.add(JLCable);
                                JPEliminarConexion.add(JBEliminarConexion);

                                JDEliminarConexion.add(JPEliminarConexion);
                                JDEliminarConexion.setModalityType(ModalityType.APPLICATION_MODAL);
                                JDEliminarConexion.setVisible(true);
                                */

                            int filaConexion = JTConexiones.getSelectedRow();

                            if( filaConexion != -1 ) {
                                String codigoEquipo1 = JTConexiones.getModel().getValueAt(filaConexion, 0).toString();
                                String codigoEquipo2 = JTConexiones.getModel().getValueAt(filaConexion,1).toString();
                                String codigoTipoCable = JTConexiones.getModel().getValueAt(filaConexion,2).toString();

                                Conexion conexion = coordinador.buscarConexion(codigoEquipo1, codigoEquipo2, codigoTipoCable);
                                try {
                                    coordinador.borrarConexion(conexion);
                                    actualizarTablaConexiones();
                                    JOptionPane.showMessageDialog(this,"Conexion eliminada exitosamente.");
                                } catch (ConexionNoExistenteException CNEe) {
                                    JOptionPane.showMessageDialog(this, CNEe.getMessage());
                                }

                            }
                            break;
                        case "Ubicaciones":
                            int filaUbicacion = JTUbicaciones.getSelectedRow();
                            if( filaUbicacion != -1 ) {
                                String codigoUbicacion = JTUbicaciones.getModel().getValueAt(filaUbicacion, 0).toString();

                                Ubicacion ubicacion = coordinador.buscarUbicacion(codigoUbicacion);
                                try {
                                    coordinador.borrarUbicacion(ubicacion);
                                    actualizarTablaUbicaciones();
                                    JOptionPane.showMessageDialog(this, "Ubicacion eliminada exitosamente.");
                                } catch (UbicacionNoExistenteException UNEEe) {
                                    JOptionPane.showMessageDialog(this, UNEEe.getMessage());
                                }
                            }
                            break;
                        case "Tipos de Equipo":
                            int filaTipoEquipo = JTTipoEquipo.getSelectedRow();
                            if( filaTipoEquipo != -1) {
                                String codigoTipoEquipo = JTTipoEquipo.getModel().getValueAt(filaTipoEquipo, 0).toString();

                                TipoEquipo tipoEquipo = coordinador.buscarTipoEquipo(codigoTipoEquipo);
                                try {
                                    coordinador.borrarTipoEquipo(tipoEquipo);
                                    actualizarTablaTipoEquipos();
                                    JOptionPane.showMessageDialog(this, "Tipo de equipo eliminado exitosamente.");
                                } catch (TipoEquipoEnUsoException TEEUEe) {
                                    JOptionPane.showMessageDialog(this, TEEUEe.getMessage());
                                }
                            }
                            break;
                        case "Tipos de Puerto":
                            int filaTipoPuerto = JTTipoPuerto.getSelectedRow();
                            if( filaTipoPuerto != -1) {
                                String codigoTipoPuerto = JTTipoPuerto.getModel().getValueAt(filaTipoPuerto, 0).toString();

                                TipoPuerto tipoPuerto = coordinador.buscarTipoPuerto(codigoTipoPuerto);
                                try {
                                    coordinador.borrarTipoPuerto(tipoPuerto);
                                    actualizarTablaTipoPuertos();
                                    JOptionPane.showMessageDialog(this, "Tipo de Puerto eliminado exitosamente.");
                                } catch (TipoPuertoEnUsoException TPEUEe) {
                                    JOptionPane.showMessageDialog(this, TPEUEe.getMessage());
                                }
                            }
                            break;
                        case "Tipos de Cable":
                            int filaTipoCable =  JTTipoCable.getSelectedRow();
                            if( filaTipoCable!= -1) {
                                String codigoTipoCable = JTTipoCable.getModel().getValueAt(filaTipoCable,0).toString();

                                TipoCable tipoCable = coordinador.buscarTipoCable(codigoTipoCable);
                                try {
                                    coordinador.borrarTipoCable(tipoCable);
                                    actualizarTablaTipoCables();
                                    JOptionPane.showMessageDialog(this, "Tipo de cable eliminado exitosamente.");
                                } catch (TipoCableEnUsoException TCEUEe) {
                                    JOptionPane.showMessageDialog(this, TCEUEe.getMessage());
                                }
                            }
                            break;
                    }
        });

        JPBotones.add(JBAgregar);
        JPBotones.add(JBModificar);
        JPBotones.add(JBAEliminar);

        this.add(JPBotones, BorderLayout.SOUTH);
    }

}
