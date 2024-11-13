package gui;

import controlador.Coordinador;

import modelo.Equipo;
import modelo.Conexion;
import modelo.Ubicacion;
import modelo.TipoEquipo;
import modelo.TipoPuerto;
import modelo.TipoCable;

import excepciones.EquipoRepetidoException;
import excepciones.UbicacionRepetidaException;
import excepciones.ConexionRepetidaException;
import excepciones.TipoEquipoRepetidoException;
import excepciones.TipoPuertoRepetidoException;
import excepciones.TipoCableRepetidoException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;

import static controlador.Constantes.JL_X_COORD;
import static controlador.Constantes.JL_Y_COORD;
import static controlador.Constantes.WIDTH;
import static controlador.Constantes.HEIGHT;
import static controlador.Constantes.AGREGAR_EQUIPO_MAX_ALTO;
import static controlador.Constantes.AGREGAR_EQUIPO_MAX_ANCHO;
import static controlador.Constantes.AGREGAR_CONEXION_MAX_ALTO;
import static controlador.Constantes.AGREGAR_CONEXION_MAX_ANCHO;
import static controlador.Constantes.AGREGAR_UBICACION_MAX_ALTO;
import static controlador.Constantes.AGREGAR_UBICACION_MAX_ANCHO;
import static controlador.Constantes.AGREGAR_TIPOEQUIPO_MAX_ALTO;
import static controlador.Constantes.AGREGAR_TIPOEQUIPO_MAX_ANCHO;
import static controlador.Constantes.AGREGAR_TIPOPUERTO_MAX_ALTO;
import static controlador.Constantes.AGREGAR_TIPOPUERTO_MAX_ANCHO;
import static controlador.Constantes.AGREGAR_TIPOCABLE_MAX_ALTO;
import static controlador.Constantes.AGREGAR_TIPOCABLE_MAX_ANCHO;

import static controlador.Constantes.JTF_DIMENSIONES;

public class JDAgregar extends JDialog {

    private Coordinador coordinador;
    private FrameDatosRed datosRed;

    public JDAgregar (Coordinador coordinador, FrameDatosRed datosRed) {
        this.coordinador = coordinador;
        this.datosRed = datosRed;
    }

    public void ventanaAgregar(String tituloTabla) {

        switch(tituloTabla){
            case "Equipos":
                //JDialog JDAgregarEquipo = new JDialog();
                setSize(AGREGAR_EQUIPO_MAX_ANCHO,AGREGAR_EQUIPO_MAX_ALTO);
                setResizable(false);
                setTitle("Agregar Equipo");

                List<String> direccionesIP = new ArrayList<>();
                List<TipoPuerto> tipoPuertos = new ArrayList<>();
                List<Integer> puertosCantidad = new ArrayList<>();


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

                JComboBox<String> JCBPuertos = new JComboBox<>();
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
                JBAgregarPuerto.setBounds(JL_X_COORD*20,JL_Y_COORD*3,WIDTH/2,HEIGHT);
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
                        JOptionPane.showMessageDialog(this, "Puerto repetido/ cantidad de " +
                                "puertos invalida. Por favor, vuelva a ingresar los datos.");
                    }
                });

                JButton JBEliminarPuerto = new JButton("-");
                JBEliminarPuerto.setBounds(JL_X_COORD*23,JL_Y_COORD*3,WIDTH/2,HEIGHT);
                JBEliminarPuerto.addActionListener(e -> {
                    TipoPuerto tipoPuerto = new TipoPuerto();

                    tipoPuerto = coordinador.buscarTipoPuerto(JCBPuertos.getItemAt(
                            JCBPuertos.getSelectedIndex()));
                    // verifica que el Puerto a eliminar exista en la lista de puertos agregados
                    if (tipoPuertos.contains(tipoPuerto) && puertosCantidad.contains(
                            Integer.parseInt(JTFPuertoCantidad.getText()))) {

                        String puertosInfo = "";
                        int cantidadIndex = tipoPuertos.indexOf(tipoPuerto);
                        tipoPuertos.remove(tipoPuerto);
                        puertosCantidad.remove(cantidadIndex);

                        for (int i=0 ; i<tipoPuertos.size(); i++) {
                            puertosInfo += tipoPuertos.get(i).getCodigo() + "," +
                                    puertosCantidad.get(i).toString() + "; ";
                        }
                        JTAPuertosAgregados.setText(puertosInfo);
                    } else {
                        JOptionPane.showMessageDialog(this, "Puerto no agregado/ cantidad de " +
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
                JBAgregarDireccionIP.setBounds(JL_X_COORD*20,JL_Y_COORD*17,WIDTH/2,HEIGHT);
                JBAgregarDireccionIP.addActionListener(e5 -> {

                    String direccionIP = JTFDireccionIP.getText();

                    // verifica si ya se ingreso la direccion IP + si posee el formato correcto
                    if(!direccionesIP.contains(direccionIP) && coordinador.verificarIP(direccionIP)) {
                        direccionesIP.add(direccionIP);

                        JTADireccionesIPAgregadas.setText(JTADireccionesIPAgregadas.getText() +
                                direccionIP + "; ");
                    } else {
                        JOptionPane.showMessageDialog(this, "Direccion IP repetida/ con formato " +
                                "erroneo. Por favor, vuelva a ingresar una direccion IP");
                    }
                });

                JButton JBEliminarDireccionIP = new JButton("-");
                JBEliminarDireccionIP.setBounds(JL_X_COORD*23,JL_Y_COORD*17,WIDTH/2,HEIGHT);
                JBEliminarDireccionIP.addActionListener(e -> {
                    String direccionIP = JTFDireccionIP.getText();

                    if(direccionesIP.contains(direccionIP) && coordinador.verificarIP(direccionIP)) {

                        String direccionesInfo = "";
                        direccionesIP.remove(direccionIP);

                        for(String direccionIP1 : direccionesIP) {
                            direccionesInfo += direccionIP1 + "; ";
                        }
                        JTADireccionesIPAgregadas.setText(direccionesInfo);

                    } else {
                        JOptionPane.showMessageDialog(this, "Direccion IP no agregada. " +
                                "Por favor, vuelva a ingresar una direccion IP.");
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
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaEquipos();
                            JOptionPane.showMessageDialog(this, "Equipo agregado exitosamente.");
                            this.dispose();
                        } catch (EquipoRepetidoException EREe) {
                            JOptionPane.showMessageDialog(this, EREe.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo agregar el equipo nuevo, " +
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
                JPAgregarEquipo.add(JBEliminarPuerto);
                JPAgregarEquipo.add(JTAPuertosAgregados);

                JPAgregarEquipo.add(JLDireccionesIP);
                JPAgregarEquipo.add(JTFDireccionIP);
                JPAgregarEquipo.add(JBAgregarDireccionIP);
                JPAgregarEquipo.add(JBEliminarDireccionIP);
                JPAgregarEquipo.add(JTADireccionesIPAgregadas);

                JPAgregarEquipo.add(JBAgregarEquipo);

                add(JPAgregarEquipo);
                setModalityType(ModalityType.APPLICATION_MODAL);
                setVisible(true);

                break;
            case "Conexiones":

                setSize(AGREGAR_CONEXION_MAX_ANCHO,AGREGAR_CONEXION_MAX_ALTO);
                setResizable(false);
                setTitle("Agregar Conexion");

                JLabel JLEquipo1 = new JLabel("Equipo 1 (*): ");
                JLabel JLEquipo2 = new JLabel("Equipo 2 (*): ");
                JLabel JLPuerto1 = new JLabel("Puerto 1 (*): ");
                JLabel JLPuerto2 = new JLabel("Puerto 2 (*): ");
                JLabel JLTipoCable = new JLabel("Tipo de Cable (*): ");
                JLabel JLEstado1 = new JLabel("Estado: ");

                JToggleButton JTBEstado1 = new JToggleButton();
                JTBEstado1.setText("Inactivo");
                JTBEstado1.addChangeListener(e2 -> {
                    if( JTBEstado1.isSelected() ) JTBEstado1.setText("Activo");
                    else JTBEstado1.setText("Inactivo");
                });

                JComboBox<String> JCBEquipo1 = new JComboBox<>();
                JComboBox<String> JCBEquipo2 = new JComboBox<>();
                JComboBox<String> JCBPuerto1 = new JComboBox<>();
                JComboBox<String> JCBPuerto2 = new JComboBox<>();
                JComboBox<String> JCBTipoCable = new JComboBox<>();

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
                    boolean estadoConexion = JTBEstado1.isSelected();

                    Conexion conexion = new Conexion(equipo1, equipo2, cable1, puerto1, puerto2, estadoConexion );

                    try {
                        coordinador.agregarConexion(conexion);
                        coordinador.cargarDatos();
                        datosRed.actualizarTablaConexiones();
                        JOptionPane.showMessageDialog(this, "Conexión agregada exitosamente.");
                        dispose();
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
                JPAgregarConexion.add(JLEstado1);
                JPAgregarConexion.add(JTBEstado1);
                JPAgregarConexion.add(JBAgregarConexion);

                add(JPAgregarConexion);
                setModalityType(ModalityType.APPLICATION_MODAL);
                setVisible(true);
                break;
            case "Ubicaciones":
                setSize(AGREGAR_UBICACION_MAX_ANCHO,AGREGAR_UBICACION_MAX_ALTO);
                setResizable(false);
                setTitle("Agregar Ubicacion");

                JLabel JLCodigoUbicacion = new JLabel("Codigo: ");
                JLabel JLDescripcionUbicacion = new JLabel("Descripcion: ");

                JTextField JTFCodigoUbicacion = new JTextField();
                JTFCodigoUbicacion.setPreferredSize(JTF_DIMENSIONES);
                JTextField JTFDescripcionUbicacion = new JTextField();
                JTFDescripcionUbicacion.setPreferredSize(JTF_DIMENSIONES);

                JButton JBAgregarUbicacion = new JButton("Agregar");
                JBAgregarUbicacion.addActionListener(e1 -> {
                    String codigoUbicacion = JTFCodigoUbicacion.getText();
                    String descripcionUbicacion = JTFDescripcionUbicacion.getText();

                    Ubicacion ubicacion = new Ubicacion(codigoUbicacion,descripcionUbicacion);

                    try {
                        coordinador.agregarUbicacion(ubicacion);
                        datosRed.actualizarTablaUbicaciones();
                        JOptionPane.showMessageDialog(this, "Ubicacion agregada exitosamente.");
                        dispose();
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

                add(JPAgregarUbicacion);
                setModalityType(ModalityType.APPLICATION_MODAL);
                setVisible(true);

                break;
            case "Tipos de Equipo":

                setSize(AGREGAR_TIPOEQUIPO_MAX_ANCHO, AGREGAR_TIPOEQUIPO_MAX_ALTO);
                setResizable(false);
                setTitle("Agregar Tipo de Equipo");

                JLabel JLCodigoTipoEquipo = new JLabel("Codigo: ");
                JLabel JLDescripcionTipoEquipo = new JLabel("Descripcion: ");

                JTextField JTFCodigoTipoEquipo = new JTextField();
                JTFCodigoTipoEquipo.setPreferredSize(JTF_DIMENSIONES);
                JTextField JTFDescripcionTipoEquipo = new JTextField();
                JTFDescripcionTipoEquipo.setPreferredSize(JTF_DIMENSIONES);

                JButton JBAgregarTipoEquipo = new JButton("Agregar");
                JBAgregarTipoEquipo.addActionListener(e1 -> {
                    String codigoTipoEquipo = JTFCodigoTipoEquipo.getText();
                    String descripcionTipoEquipo = JTFDescripcionTipoEquipo.getText();

                    TipoEquipo tipoEquipo = new TipoEquipo();
                    tipoEquipo.setCodigo(codigoTipoEquipo);
                    tipoEquipo.setDescripcion(descripcionTipoEquipo);

                    try {
                        coordinador.agregarTipoEquipo(tipoEquipo);
                        datosRed.actualizarTablaTipoEquipos();
                        JOptionPane.showMessageDialog(this, "Tipo de equipo agregado correctamente.");
                        dispose();
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

                add(JPAgregarTipoEquipo);
                setModalityType(ModalityType.APPLICATION_MODAL);
                setVisible(true);

                break;
            case "Tipos de Puerto":


                setSize(AGREGAR_TIPOPUERTO_MAX_ANCHO, AGREGAR_TIPOPUERTO_MAX_ALTO);
                setResizable(false);
                setTitle("Agregar Tipo de Puerto");

                JLabel JLCodigoTipoPuerto = new JLabel("Codigo: ");
                JLabel JLDescripcionTipoPuerto = new JLabel("Descripcion: ");
                JLabel JLVelocidadTipoPuerto = new JLabel("Velocidad: ");
                JTextField JTFCodigoTipoPuerto = new JTextField();
                JTFCodigoTipoPuerto.setPreferredSize(JTF_DIMENSIONES);
                JTextField JTFDescripcionTipoPuerto = new JTextField();
                JTFDescripcionTipoPuerto.setPreferredSize(JTF_DIMENSIONES);
                JTextField JTFVelocidadTipoPuerto = new JTextField();
                JTFVelocidadTipoPuerto.setPreferredSize(JTF_DIMENSIONES);

                JButton JBAgregarTipoPuerto = new JButton("Agregar");
                JBAgregarTipoPuerto.addActionListener(e1 -> {
                    String codigoTipoPuerto = JTFCodigoTipoPuerto.getText();
                    String descripcionTipoPuerto = JTFDescripcionTipoPuerto.getText();
                    int velocidadTipoPuerto = Integer.parseInt(JTFVelocidadTipoPuerto.getText());

                    TipoPuerto tipoPuerto = new TipoPuerto();
                    tipoPuerto.setCodigo(codigoTipoPuerto);
                    tipoPuerto.setDescripcion(descripcionTipoPuerto);
                    tipoPuerto.setVelocidad(velocidadTipoPuerto);

                    try {
                        coordinador.agregarTipoPuerto(tipoPuerto);
                        datosRed.actualizarTablaTipoPuertos();
                        JOptionPane.showMessageDialog(this, "Tipo de Puerto agregado correctamente.");
                        dispose();
                    } catch (TipoPuertoRepetidoException TPR) {
                        JOptionPane.showMessageDialog(this, TPR.getMessage());
                    }
                });

                JPanel JPAgregarTipoPuerto = new JPanel();
                JPAgregarTipoPuerto.add(JLCodigoTipoPuerto);
                JPAgregarTipoPuerto.add(JTFCodigoTipoPuerto);
                JPAgregarTipoPuerto.add(JLDescripcionTipoPuerto);
                JPAgregarTipoPuerto.add(JTFDescripcionTipoPuerto);
                JPAgregarTipoPuerto.add(JLVelocidadTipoPuerto);
                JPAgregarTipoPuerto.add(JTFVelocidadTipoPuerto);
                JPAgregarTipoPuerto.add(JBAgregarTipoPuerto);

                add(JPAgregarTipoPuerto);
                setModalityType(ModalityType.APPLICATION_MODAL);
                setVisible(true);

                break;
            case "Tipos de Cable":

                setSize(AGREGAR_TIPOCABLE_MAX_ANCHO,AGREGAR_TIPOCABLE_MAX_ALTO);
                setResizable(false);
                setTitle("Agregar Tipo de Cable");

                JLabel JLCodigoTipoCable = new JLabel("Codigo: ");
                JLabel JLDescripcionTipoCable = new JLabel("Descripcion: ");
                JLabel JLVelocidadTipoCable = new JLabel("Velocidad: ");

                JTextField JTFCodigoTipoCable = new JTextField();
                JTFCodigoTipoCable.setPreferredSize(JTF_DIMENSIONES);
                JTextField JTFDescripcionTipoCable = new JTextField();
                JTFDescripcionTipoCable.setPreferredSize(JTF_DIMENSIONES);
                JTextField JTFVelocidadTipoCable = new JTextField();
                JTFVelocidadTipoCable.setPreferredSize(JTF_DIMENSIONES);

                JButton JBAgregarTipoCable= new JButton("Agregar");
                JBAgregarTipoCable.addActionListener(e1 -> {
                    String codigoTipoCable = JTFCodigoTipoCable.getText();
                    String descripcionTipoCable = JTFDescripcionTipoCable.getText();
                    int velocidadTipoCable = Integer.parseInt(JTFVelocidadTipoCable.getText());

                    TipoCable tipoCable = new TipoCable();
                    tipoCable.setCodigo(codigoTipoCable);
                    tipoCable.setDescripcion(descripcionTipoCable);
                    tipoCable.setVelocidad(velocidadTipoCable);

                    try {
                        coordinador.agregarTipoCable(tipoCable);
                        datosRed.actualizarTablaTipoCables();
                        JOptionPane.showMessageDialog(this, "Tipo de Cable agregado correctamente.");
                        dispose();
                    } catch (TipoCableRepetidoException TCR) {
                        JOptionPane.showMessageDialog(this, TCR.getMessage());
                    }
                });

                JPanel JPAgregarTipoCable = new JPanel();
                JPAgregarTipoCable.add(JLCodigoTipoCable);
                JPAgregarTipoCable.add(JTFCodigoTipoCable);
                JPAgregarTipoCable.add(JLDescripcionTipoCable);
                JPAgregarTipoCable.add(JTFDescripcionTipoCable);
                JPAgregarTipoCable.add(JLVelocidadTipoCable);
                JPAgregarTipoCable.add(JTFVelocidadTipoCable);
                JPAgregarTipoCable.add(JBAgregarTipoCable);

                add(JPAgregarTipoCable);
                setModalityType(ModalityType.APPLICATION_MODAL);
                setVisible(true);
                break;
        }
    }
}
