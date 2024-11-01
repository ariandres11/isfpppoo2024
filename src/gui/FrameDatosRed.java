package gui;

import controlador.Coordinador;
import modelo.*;
import org.jgrapht.alg.util.Pair;

import javax.swing.*;
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
        JTOpciones.addTab("Tipos de equipo", JTTipoEquipo);
        JTOpciones.addTab("Tipos de puerto", JTTipoPuerto);
        JTOpciones.addTab("Tipos de cable", JTTipoCable);

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
        ArrayList<Equipo> arrayEquipos = (ArrayList<Equipo>) coordinador.listarEquipos();
        ArrayList<Conexion> arrayConexiones = (ArrayList<Conexion>) coordinador.listarConexiones();
        ArrayList<Ubicacion> arrayUbicaciones = (ArrayList<Ubicacion>) coordinador.listarUbicaciones();
        ArrayList<TipoEquipo> arrayTiposEquipo = (ArrayList<TipoEquipo>) coordinador.listarTipoEquipos();
        ArrayList<TipoPuerto> arrayTiposPuerto = (ArrayList<TipoPuerto>) coordinador.listarTipoPuertos();
        ArrayList<TipoCable> arrayTiposCable = (ArrayList<TipoCable>) coordinador.listarTipoCables();

        // Tabla equipos
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("codigo");
        modelo.addColumn("descripcion");
        modelo.addColumn("marca");
        modelo.addColumn("modelo");
        modelo.addRow(new String[] {"Código", "Descripción", "Marca", "Modelo"});

        Object[] fila = new Object[4];
        for (Equipo equipo : arrayEquipos) {
            fila[0] = equipo.getCodigo();
            fila[1] = equipo.getDescripcion();
            fila[2] = equipo.getMarca();
            fila[3] = equipo.getModelo();
            modelo.addRow(fila);
        }

        JTEquipos = new JTable();
        JTEquipos.setModel(modelo);

        //Tabla conexiones
        modelo = new DefaultTableModel();
        modelo.addColumn("Equipo 1");
        modelo.addColumn("Equipo 2");
        modelo.addColumn("Cable");
        modelo.addRow(new String[] {"Equipo 1","Equipo 2","Cable"});

        fila = new Object[3];
        for (Conexion conexion : arrayConexiones) {
            fila[0] = conexion.getEquipo1().getCodigo();
            fila[1] = conexion.getEquipo2().getCodigo();
            fila[2] = conexion.getTipoCable().getCodigo();
            modelo.addRow(fila);
        }

        JTConexiones = new JTable();
        JTConexiones.setModel(modelo);

        //Tabla ubicaciones
        modelo = new DefaultTableModel();
        modelo.addColumn("codigo");
        modelo.addColumn("descripcion");
        modelo.addRow(new String[] {"Código","Descripción"});

        fila = new Object[2];
        for (Ubicacion ubicacion : arrayUbicaciones) {
            fila[0] = ubicacion.getCodigo();
            fila[1] = ubicacion.getDescripcion();
            modelo.addRow(fila);
        }

        JTUbicaciones = new JTable();
        JTUbicaciones.setModel(modelo);
        
        //Tabla tipos de equipo
        modelo = new DefaultTableModel();
        modelo.addColumn("codigo");
        modelo.addColumn("descripcion");
        modelo.addRow(new String[] {"Código","Descripción"});

        fila = new Object[2];
        for(TipoEquipo tipoEq : arrayTiposEquipo){
            fila[0] = tipoEq.getCodigo();
            fila[1] = tipoEq.getDescripcion();
            modelo.addRow(fila);
        }

        JTTipoEquipo = new JTable();
        JTTipoEquipo.setModel(modelo);

        //Tabla tipos de puerto

        modelo = new DefaultTableModel();
        modelo.addColumn("codigo");
        modelo.addColumn("descripcion");
        modelo.addRow(new String[] {"Código","Descripción"});

        fila = new Object[2];

        for(TipoPuerto tipoPuerto : arrayTiposPuerto){
            fila[0] = tipoPuerto.getCodigo();
            fila[1] = tipoPuerto.getDescripcion();
            modelo.addRow(fila);
        }

        JTTipoPuerto = new JTable();
        JTTipoPuerto.setModel(modelo);

        //Tabla tipos de cable

        modelo = new DefaultTableModel();
        modelo.addColumn("codigo");
        modelo.addColumn("descripcion");
        modelo.addColumn("velocidad");
        modelo.addRow(new String[] {"Código","Descripción", "Velocidad Mbps"});

        fila = new Object[3];
        for(TipoCable tipoCable : arrayTiposCable){
            fila[0] = tipoCable.getCodigo();
            fila[1] = tipoCable.getDescripcion();
            fila[2] = tipoCable.getVelocidad();
            modelo.addRow(fila);
        }

        JTTipoCable = new JTable();
        JTTipoCable.setModel(modelo);
    }

    public void agregarBotones() {
        final Dimension DIMENSION_JTF = new Dimension(70,20);
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
                    JDAgregarEquipo.setSize(450,400);
                    JDAgregarEquipo.setResizable(false);
                    setTitle("Agregar Equipo");

                    List<String> direccionesIP = new ArrayList<String>();
                    List<Pair<TipoPuerto,Integer>> puertos = new ArrayList<Pair<TipoPuerto,Integer>>();

                    JLabel JLCodigo = new JLabel("Codigo (*):");
                    JLabel JLDescripcion = new JLabel("Descripción:");
                    JLabel JLMarca = new JLabel("Marca: ");
                    JLabel JLModelo = new JLabel("Modelo: ");
                    JLabel JLDireccionesIP = new JLabel("DireccionesIP: ");
                    JLabel JLTipoPuerto = new JLabel("Tipo de Puerto/Cantidad (*): ");
                    JLabel JLTipoEquipo = new JLabel("Tipo de Equipo: ");
                    JLabel JLUbicacion = new JLabel("Ubicación: ");
                    JLabel JLEstado = new JLabel("Estado (*): ");

                    JTextField JTFCodigo = new JTextField();
                    JTFCodigo.setPreferredSize(DIMENSION_JTF);
                    JTextField JTFDescripcion = new JTextField();
                    JTFDescripcion.setPreferredSize(DIMENSION_JTF);
                    JTextField JTFMarca = new JTextField();
                    JTFMarca.setPreferredSize(DIMENSION_JTF);
                    JTextField JTFModelo = new JTextField();
                    JTFModelo.setPreferredSize(DIMENSION_JTF);
                    JTextField JTFPuertosAgregados = new JTextField();
                    JTFPuertosAgregados.setPreferredSize(new Dimension(350,100));
                    JTFPuertosAgregados.enableInputMethods(false);
                    JTextField JTFPuertoCantidad = new JTextField();
                    JTFPuertoCantidad.setPreferredSize(DIMENSION_JTF);
                    JTextField JTFDireccionIP = new JTextField();
                    JTFDireccionIP.setPreferredSize(DIMENSION_JTF);
                    JTextField JTFDireccionesIPAgregadas = new JTextField();
                    JTFDireccionesIPAgregadas.setPreferredSize(new Dimension(350,100));
                    JTFDireccionesIPAgregadas.enableInputMethods(false);

                    JComboBox<String> JCBPuertos = new JComboBox<String>();
                    JComboBox<String> JCBTipoEquipo = new JComboBox<String>();
                    JComboBox<String> JCBUbicacion = new JComboBox<String>();

                    for (TipoPuerto puerto : coordinador.listarTipoPuertos()) {
                        JCBPuertos.addItem(puerto.getCodigo());
                    }

                    for (TipoEquipo equipo : coordinador.listarTipoEquipos()) {
                        JCBTipoEquipo.addItem(equipo.getCodigo());
                    }

                    for (Ubicacion ubicacion : coordinador.listarUbicaciones()) {
                        JCBUbicacion.addItem(ubicacion.getCodigo());
                    }

                    JToggleButton JTBEstado = new JToggleButton();
                    JTBEstado.setText("Activo");

                    JButton JBAgregarPuerto = new JButton("+");
                    JButton JBAgregarDireccionIP = new JButton("+");
                    JButton JBAgregarEquipo = new JButton("Agregar");

                    JBAgregarPuerto.addActionListener(e4 -> {

                        TipoPuerto tipoPuerto = new TipoPuerto();

                        for (TipoPuerto puerto : coordinador.listarTipoPuertos()) {
                            if (puerto.getCodigo() == JCBPuertos.getItemAt(JCBPuertos.getSelectedIndex())) {
                                tipoPuerto = puerto;
                                break;
                            }
                        }

                        puertos.add(new Pair<TipoPuerto, Integer>
                                (tipoPuerto, Integer.parseInt(JTFPuertoCantidad.getText())));

                        JTFPuertosAgregados.setText(JTFPuertosAgregados.getText() +
                                JCBPuertos.getItemAt(JCBPuertos.getSelectedIndex()) +
                                " " + JTFPuertoCantidad.getText() + ", ");
                    });

                    JBAgregarDireccionIP.addActionListener(e5 -> {

                        direccionesIP.add(JTFDireccionIP.getText());

                        JTFDireccionesIPAgregadas.setText(JTFDireccionesIPAgregadas.getText() +
                                JTFDireccionIP.getText() + ", ");
                    });

                    JBAgregarEquipo.addActionListener(e6 -> {
                        String codigoEquipo = JTFCodigo.getText();
                        String descripcionEquipo = JTFDescripcion.getText();
                        String marcaEquipo = JTFMarca.getText();
                        String modeloEquipo = JTFModelo.getText();
                        Ubicacion ubicacionEquipo = null;
                        TipoEquipo tipoEquipo = null;
                        boolean estadoEquipo;

                        for (Ubicacion ubicacion : coordinador.listarUbicaciones()) {
                            if (ubicacion.getCodigo() == JCBUbicacion.getItemAt(JCBUbicacion.getSelectedIndex())) {
                                ubicacionEquipo = ubicacion;
                                break;
                            }
                        }

                        for (TipoEquipo equipo : coordinador.listarTipoEquipos()) {
                            if (equipo.getCodigo() == JCBTipoEquipo.getItemAt(JCBTipoEquipo.getSelectedIndex())) {
                                tipoEquipo = equipo;
                                break;
                            }
                        }

                        estadoEquipo = JTBEstado.isSelected();

                        Equipo equipoNuevo = new Equipo(codigoEquipo, descripcionEquipo, marcaEquipo, modeloEquipo,
                                puertos.getFirst().getSecond(), puertos.getFirst().getFirst(),
                                ubicacionEquipo, tipoEquipo, estadoEquipo);


                        if(puertos.size() > 1) {
                            for (Pair<TipoPuerto, Integer> puertoPar : puertos) {
                                if(puertos.indexOf(puertoPar) != 0)
                                    equipoNuevo.agregarPuerto(puertoPar.getSecond(), puertoPar.getFirst());
                            }
                        }

                        coordinador.getRed().agregarEquipo(equipoNuevo);
                        JOptionPane.showMessageDialog(this,"Equipo agregado exitosamente.");

                    });

                    JPanel JPAgregarEquipo = new JPanel();

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
                    JPAgregarEquipo.add(JTFPuertosAgregados);

                    JPAgregarEquipo.add(JLDireccionesIP);
                    JPAgregarEquipo.add(JTFDireccionIP);
                    JPAgregarEquipo.add(JBAgregarDireccionIP);
                    JPAgregarEquipo.add(JTFDireccionesIPAgregadas);

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
                        String codigoEquipo1 = JCBEquipo1.getSelectedItem().toString();
                        String codigoEquipo2 = JCBEquipo2.getSelectedItem().toString();
                        String codigoPuerto1 = JCBPuerto1.getSelectedItem().toString();
                        String codigoPuerto2 = JCBPuerto2.getSelectedItem().toString();
                        String codigoTipoCable = JCBTipoCable.getSelectedItem().toString();

                        Equipo equipo1 = new Equipo();
                        Equipo equipo2 = new Equipo();
                        TipoPuerto puerto1 = new TipoPuerto();
                        TipoPuerto puerto2 = new TipoPuerto();
                        TipoCable cable1 = new TipoCable();

                        for (Equipo equipo : coordinador.listarEquipos()) {
                            if(equipo.getCodigo() == codigoEquipo1) {
                                equipo1 = equipo;
                            } else if (equipo.getCodigo() == codigoEquipo2) {
                                equipo2 = equipo;
                            }
                        }

                        for (TipoPuerto puerto : coordinador.listarTipoPuertos()) {
                            if (puerto.getCodigo() == codigoPuerto1) {
                                puerto1 = puerto;
                            } else if (puerto.getCodigo() == codigoPuerto2) {
                                puerto2 = puerto;
                            }
                        }

                        for (TipoCable cable : coordinador.listarTipoCables()) {
                            if (cable.getCodigo() == codigoTipoCable) {
                                cable1 = cable;
                                break;
                            }
                        }

                        Conexion conexion = new Conexion(equipo1, equipo2, cable1, puerto1, puerto2, false );

                        coordinador.getRed().agregarConexion(conexion);
                        JOptionPane.showMessageDialog(this,"Conexión agregada exitosamente.");

                        this.dispose();
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

                        coordinador.getRed().agregarUbicacion(ubicacion);
                        JOptionPane.showMessageDialog(JDAgregarUbicacion,"Ubicacion agregada exitosamente.");
                        JDAgregarUbicacion.dispose();
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

                                for (Equipo equipo : coordinador.listarEquipos()) {
                                    if(equipo.getCodigo() == JCBEquipo.getItemAt(JCBEquipo.getSelectedIndex())) {
                                        equipoInfo = equipo;
                                        break;
                                    }
                                }
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
                                for (Equipo equipo1 : coordinador.listarEquipos()) {
                                    if(equipo1.getCodigo() == JCBEquipo.getItemAt(JCBEquipo.getSelectedIndex())) {
                                        equipo = equipo1;
                                        break;
                                    }
                                }

                                coordinador.getRed().borrarEquipo(equipo);
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
                            break;
                        case "Conexiones":
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

                                    JLEquipo1.setText("Equipo1: " + conexionInfo.getEquipo1().getCodigo() + ", " +
                                            conexionInfo.getEquipo1().getDescripcion());
                                    JLEquipo2.setText("Equipo2: " + conexionInfo.getEquipo2().getCodigo() + ", " +
                                            conexionInfo.getEquipo2().getDescripcion());
                                    JLCable.setText("Cable: " + conexionInfo.getTipoCable().getCodigo() + ", " +
                                            conexionInfo.getTipoCable().getDescripcion());

                                });

                                for (int i=0 ; i<coordinador.listarConexiones().size(); i++) {
                                    JCBConexiones.addItem(String.valueOf(i));
                                }

                                JButton JBEliminarConexion = new JButton("Eliminar");
                                JBEliminarConexion.addActionListener(e8 -> {
                                    Conexion conexion = (Conexion) coordinador.listarConexiones().toArray()[JCBConexiones.getSelectedIndex()];

                                    coordinador.getRed().borrarConexion(conexion);
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
                                break;
                        case "Ubicaciones":
                            //JOptionPane.showMessageDialog(this,"Eliminar Ubicacion.", mensajeTitulo, mensajeModo);
                            JDialog JDEliminarUbicacion = new JDialog();
                            JDEliminarUbicacion.setSize(300,100);
                            JDEliminarUbicacion.setResizable(false);
                            JDEliminarUbicacion.setTitle("Eliminar Ubicacion");

                            JLabel JLCodigoUbicacion = new JLabel("Codigo: ");

                            JComboBox<String> JCBEliminarUbicacion = new JComboBox<String>();
                            for (Ubicacion ubicacion : coordinador.getRed().getUbicaciones()) {
                                JCBEliminarUbicacion.addItem(ubicacion.getCodigo());
                            }

                            JButton JBEliminarUbicacion = new JButton("Eliminar");
                            JBEliminarUbicacion.addActionListener(e2 -> {
                                Ubicacion ubicacion = null;

                                for (Ubicacion ubicacion1 : coordinador.getRed().getUbicaciones()) {
                                    if( ubicacion1.getCodigo() == JCBEliminarUbicacion.getSelectedItem()) {
                                        ubicacion = ubicacion1;
                                        break;
                                    }
                                }

                                coordinador.getRed().borrarUbicacion(ubicacion);
                                JOptionPane.showMessageDialog(JDEliminarUbicacion,"Ubicacion eliminada exitosamente.");
                                JDEliminarUbicacion.dispose();
                            });

                            JPanel JPEliminarUbicacion = new JPanel();
                            JPEliminarUbicacion.add(JLCodigoUbicacion);
                            JPEliminarUbicacion.add(JCBEliminarUbicacion);
                            JPEliminarUbicacion.add(JBEliminarUbicacion);

                            JDEliminarUbicacion.add(JPEliminarUbicacion);
                            JDEliminarUbicacion.setModalityType(ModalityType.APPLICATION_MODAL);
                            JDEliminarUbicacion.setVisible(true);
                            break;
                        case "Tipos de Equipo":
                            break;
                        case "Tipos de Puerto":
                            break;
                        case "Tipos de Cable":
                            break;
                    }

        });

        JPBotones.add(JBAgregar);
        JPBotones.add(JBModificar);
        JPBotones.add(JBAEliminar);

        this.add(JPBotones, BorderLayout.SOUTH);
    }

    /* prueba
    public static void main(String[] args) {
        FrameDatosRed datos = new FrameDatosRed();
    }
    */
}
