package gui;

import controlador.Coordinador;

import modelo.Equipo;
import modelo.Ubicacion;
import modelo.TipoEquipo;
import modelo.TipoPuerto;

import excepciones.EquipoRepetidoException;
import excepciones.TipoEquipoRepetidoException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



import java.util.List;

import static controlador.Constantes.JL_X_COORD;
import static controlador.Constantes.JL_Y_COORD;
import static controlador.Constantes.JL_WIDTH;
import static controlador.Constantes.JL_HEIGHT;
import static controlador.Constantes.AGREGAR_EQUIPO_MAX_ALTO;
import static controlador.Constantes.AGREGAR_EQUIPO_MAX_ANCHO;
import static controlador.Constantes.AGREGAR_UBICACION_MAX_ALTO;
import static controlador.Constantes.AGREGAR_UBICACION_MAX_ANCHO;
import static controlador.Constantes.AGREGAR_TIPOEQUIPO_MAX_ALTO;
import static controlador.Constantes.AGREGAR_TIPOEQUIPO_MAX_ANCHO;
import static controlador.Constantes.JTF_DIMENSIONES;

public class JDModificar extends JDialog {

    private Coordinador coordinador;
    private FrameDatosRed datosRed;

    public JDModificar (Coordinador coordinador, FrameDatosRed datosRed) {
        this.coordinador = coordinador;
        this.datosRed = datosRed;
    }

    public void ventanaModificar(String tituloTabla, int filaSeleccionada) {
        switch(tituloTabla) {

            case "Equipos":

                int filaEquipo = filaSeleccionada;
                if( filaEquipo != -1) {
                    String codigoEquipo = datosRed.getJTEquipos().getModel().getValueAt(filaEquipo, 0).toString();

                    Equipo equipo = coordinador.buscarEquipo(codigoEquipo);

                    setSize(AGREGAR_EQUIPO_MAX_ANCHO,AGREGAR_EQUIPO_MAX_ALTO);
                    setResizable(false);
                    setTitle("Modificar Equipo");

                    List<String> direccionesIP = equipo.getDireccionesIP();
                    List<TipoPuerto> tipoPuertos = equipo.getTipoPuertos();
                    List<Integer> puertosCantidad = equipo.getPuertosCantidades();


                    JLabel JLCodigo = new JLabel("Codigo (*):" + equipo.getCodigo());
                    JLCodigo.setBounds(JL_X_COORD,JL_Y_COORD,JL_WIDTH, JL_HEIGHT);
                    JLabel JLDescripcion = new JLabel("Descripción:");
                    JLDescripcion.setBounds(JL_X_COORD,JL_Y_COORD*3,JL_WIDTH, JL_HEIGHT);
                    JLabel JLMarca = new JLabel("Marca: ");
                    JLMarca.setBounds(JL_X_COORD,JL_Y_COORD*5,JL_WIDTH, JL_HEIGHT);
                    JLabel JLModelo = new JLabel("Modelo: ");
                    JLModelo.setBounds(JL_X_COORD,JL_Y_COORD*7,JL_WIDTH, JL_HEIGHT);
                    JLabel JLTipoEquipo = new JLabel("Tipo de Equipo: ");
                    JLTipoEquipo.setBounds(JL_X_COORD,JL_Y_COORD*9,JL_WIDTH, JL_HEIGHT);
                    JLabel JLUbicacion = new JLabel("Ubicación: ");
                    JLUbicacion.setBounds(JL_X_COORD,JL_Y_COORD*11,JL_WIDTH, JL_HEIGHT);
                    JLabel JLEstado = new JLabel("Estado (*): ");
                    JLEstado.setBounds(JL_X_COORD,JL_Y_COORD*13,JL_WIDTH, JL_HEIGHT);


                    JTextField JTFDescripcion = new JTextField();
                    JTFDescripcion.setText(equipo.getDescripcion());
                    JTFDescripcion.setBounds(JL_X_COORD*6,JL_Y_COORD*3,JL_WIDTH, JL_HEIGHT);
                    JTextField JTFMarca = new JTextField();
                    JTFMarca.setText(equipo.getMarca());
                    JTFMarca.setBounds(JL_X_COORD*6,JL_Y_COORD*5,JL_WIDTH, JL_HEIGHT);
                    JTextField JTFModelo = new JTextField();
                    JTFModelo.setText(equipo.getModelo());
                    JTFModelo.setBounds(JL_X_COORD*6,JL_Y_COORD*7,JL_WIDTH, JL_HEIGHT);


                    JComboBox<String> JCBTipoEquipo = new JComboBox<>();
                    JCBTipoEquipo.setBounds(JL_X_COORD*6,JL_Y_COORD*9,JL_WIDTH/2, JL_HEIGHT);
                    JComboBox<String> JCBUbicacion = new JComboBox<>();
                    JCBUbicacion.setBounds(JL_X_COORD*6,JL_Y_COORD*11,JL_WIDTH/2, JL_HEIGHT);
                    JToggleButton JTBEstado = new JToggleButton();
                    JTBEstado.setSelected(equipo.isEstado());

                    JTBEstado.setBounds(JL_X_COORD*6,JL_Y_COORD*13,JL_WIDTH, JL_HEIGHT);
                    JTBEstado.setText(equipo.isEstado() ? "Activo" : "Inactivo");
                    JTBEstado.addChangeListener(e2 -> {
                        if( JTBEstado.isSelected() ) JTBEstado.setText("Activo");
                        else JTBEstado.setText("Inactivo");
                    });
                    JTBEstado.setSelected(equipo.isEstado());

                    for (TipoEquipo tipoEquipo : coordinador.listarTipoEquipos()) {
                        JCBTipoEquipo.addItem(tipoEquipo.getCodigo());
                    }
                    JCBTipoEquipo.setSelectedItem(equipo.getTipoEquipo().getCodigo());


                    for (Ubicacion ubicacion : coordinador.listarUbicaciones()) {
                        JCBUbicacion.addItem(ubicacion.getCodigo());
                    }
                    JCBUbicacion.setSelectedItem(equipo.getUbicacion().getCodigo());


                    JLabel JLTipoPuerto = new JLabel("Tipo de Puerto/Cantidad (*): ");
                    JLTipoPuerto.setBounds(JL_X_COORD*13,JL_Y_COORD,JL_WIDTH*2, JL_HEIGHT);
                    JTextField JTFPuertoCantidad = new JTextField();
                    JTFPuertoCantidad.setBounds(JL_X_COORD*17,JL_Y_COORD*3,JL_WIDTH/2, JL_HEIGHT);

                    JComboBox<String> JCBPuertos = new JComboBox<String>();
                    JCBPuertos.setBounds(JL_X_COORD*13,JL_Y_COORD*3,JL_WIDTH/2, JL_HEIGHT);

                    for (TipoPuerto puerto : coordinador.listarTipoPuertos()) {
                        JCBPuertos.addItem(puerto.getCodigo());
                    }

                    JTextArea JTAPuertosAgregados = new JTextArea();
                    String puertosCantidades = "";
                    for (int i=0 ; i<tipoPuertos.size() ; i++) {
                        puertosCantidades += tipoPuertos.get(i).getCodigo() + "," + puertosCantidad.get(i) + "; ";
                    }
                    JTAPuertosAgregados.setText(puertosCantidades);

                    JTAPuertosAgregados.setBounds(JL_X_COORD*13,JL_Y_COORD*5,JL_WIDTH*2, JL_HEIGHT*7);
                    JTAPuertosAgregados.setLineWrap(true);
                    JTAPuertosAgregados.setWrapStyleWord(true);
                    JTAPuertosAgregados.setEditable(false);


                    JButton JBAgregarPuerto = new JButton("+");
                    JBAgregarPuerto.setBounds(JL_X_COORD*20,JL_Y_COORD*3,JL_WIDTH/2, JL_HEIGHT);
                    JBAgregarPuerto.addActionListener(e4 -> {

                        TipoPuerto tipoPuerto = coordinador.buscarTipoPuerto(JCBPuertos.getItemAt(
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
                    JBEliminarPuerto.setBounds(JL_X_COORD*23,JL_Y_COORD*3,JL_WIDTH/2, JL_HEIGHT);
                    JBEliminarPuerto.addActionListener(e -> {

                        TipoPuerto tipoPuerto = coordinador.buscarTipoPuerto(JCBPuertos.getItemAt(
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
                    JLDireccionesIP.setBounds(JL_X_COORD*13,JL_Y_COORD*15,JL_WIDTH, JL_HEIGHT);
                    JTextField JTFDireccionIP = new JTextField();
                    JTFDireccionIP.setBounds(JL_X_COORD*13,JL_Y_COORD*17,JL_WIDTH, JL_HEIGHT);

                    JTextArea JTADireccionesIPAgregadas = new JTextArea();
                    JTADireccionesIPAgregadas.setBounds(JL_X_COORD*13,JL_Y_COORD*19,JL_WIDTH*2, JL_HEIGHT*7);
                    JTADireccionesIPAgregadas.setLineWrap(true);
                    JTADireccionesIPAgregadas.setWrapStyleWord(true);
                    JTADireccionesIPAgregadas.setEditable(false);

                    String direccionesIpInfo = "";
                    for (String dirIP : direccionesIP) {
                        direccionesIpInfo += dirIP + "; ";
                    }
                    JTADireccionesIPAgregadas.setText(direccionesIpInfo);

                    JButton JBAgregarDireccionIP = new JButton("+");
                    JBAgregarDireccionIP.setBounds(JL_X_COORD*20,JL_Y_COORD*17,JL_WIDTH/2, JL_HEIGHT);
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
                    JBEliminarDireccionIP.setBounds(JL_X_COORD*23,JL_Y_COORD*17,JL_WIDTH/2, JL_HEIGHT);
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

                    JButton JBModificarEquipo = new JButton("Modificar");
                    JBModificarEquipo.setBounds(JL_X_COORD*3,JL_Y_COORD*20,JL_WIDTH, JL_HEIGHT*2);
                    JBModificarEquipo.addActionListener(e6 -> {

                        String descripcionEquipo = JTFDescripcion.getText();
                        String marcaEquipo = JTFMarca.getText();
                        String modeloEquipo = JTFModelo.getText();
                        Ubicacion ubicacionEquipo = coordinador.buscarUbicacion(JCBUbicacion.getItemAt(JCBUbicacion.getSelectedIndex()));;
                        TipoEquipo tipoEquipo = coordinador.buscarTipoEquipo(JCBTipoEquipo.getItemAt(JCBTipoEquipo.getSelectedIndex()));
                        boolean estadoEquipo = JTBEstado.isSelected();

                        // verifica que la lista de puertos no este vacia + que el codigo del equipo no sea repetido ni
                        // que sea vacio
                        if (!tipoPuertos.isEmpty()) {

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
                                coordinador.modificarEquipo(equipoNuevo);
                                coordinador.cargarDatos();
                                datosRed.actualizarTablaEquipos();
                                JOptionPane.showMessageDialog(this, "Equipo modificado exitosamente.");
                                this.dispose();
                            } catch (EquipoRepetidoException EREe) {
                                JOptionPane.showMessageDialog(this, EREe.getMessage());
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "No se pudo modificar el equipo nuevo, " +
                                    "verifique los datos ingresados.");
                        }
                    });

                    JPanel JPModificarEquipo = new JPanel();
                    JPModificarEquipo.setLayout(null);

                    JPModificarEquipo.add(JLCodigo);

                    JPModificarEquipo.add(JLDescripcion);
                    JPModificarEquipo.add(JTFDescripcion);

                    JPModificarEquipo.add(JLMarca);
                    JPModificarEquipo.add(JTFMarca);

                    JPModificarEquipo.add(JLModelo);
                    JPModificarEquipo.add(JTFModelo);

                    JPModificarEquipo.add(JLTipoEquipo);
                    JPModificarEquipo.add(JCBTipoEquipo);

                    JPModificarEquipo.add(JLUbicacion);
                    JPModificarEquipo.add(JCBUbicacion);

                    JPModificarEquipo.add(JLEstado);
                    JPModificarEquipo.add(JTBEstado);

                    JPModificarEquipo.add(JLTipoPuerto);
                    JPModificarEquipo.add(JCBPuertos);
                    JPModificarEquipo.add(JTFPuertoCantidad);
                    JPModificarEquipo.add(JBAgregarPuerto);
                    JPModificarEquipo.add(JBEliminarPuerto);
                    JPModificarEquipo.add(JTAPuertosAgregados);

                    JPModificarEquipo.add(JLDireccionesIP);
                    JPModificarEquipo.add(JTFDireccionIP);
                    JPModificarEquipo.add(JBAgregarDireccionIP);
                    JPModificarEquipo.add(JBEliminarDireccionIP);
                    JPModificarEquipo.add(JTADireccionesIPAgregadas);

                    JPModificarEquipo.add(JBModificarEquipo);

                    add(JPModificarEquipo);
                    setModalityType(ModalityType.APPLICATION_MODAL);
                    setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(this,"Por favor, seleccione una fila.");
                }

                break;
            //Conexiones no se modifican, solo se eliminan y agregan
            case "Ubicaciones":
                int filaUbicacion = filaSeleccionada;

                if( filaUbicacion != -1){
                    String codigoUbicacion = datosRed.getJTUbicaciones().getModel().
                            getValueAt(filaUbicacion, 0).toString();

                    Ubicacion ubicacion = coordinador.buscarUbicacion(codigoUbicacion);

                    setSize(AGREGAR_UBICACION_MAX_ANCHO,AGREGAR_UBICACION_MAX_ALTO);
                    setResizable(false);
                    setTitle("Modificar Ubicacion");

                    JLabel JLCodigo = new JLabel("Codigo: " + codigoUbicacion);
                    JLabel JLDescripcion = new JLabel("Descripcion: ");

                    JTextField JTFDescripcionUbicacion = new JTextField();
                    JTFDescripcionUbicacion.setPreferredSize(JTF_DIMENSIONES);
                    JTFDescripcionUbicacion.setText(ubicacion.getDescripcion());

                    JButton JBModificarUbicacion = new JButton("Modificar");
                    JBModificarUbicacion.addActionListener(e13 -> {
                        String descripcionUbicacion = JTFDescripcionUbicacion.getText();

                        Ubicacion ubicacionNueva = new Ubicacion(codigoUbicacion, descripcionUbicacion);

                        try {
                            coordinador.modificarUbicacion(ubicacionNueva);
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaUbicaciones();
                            JOptionPane.showMessageDialog(this, "Ubicacion modificada exitosamente.");
                            this.dispose();
                        } catch (EquipoRepetidoException EREe) {
                            JOptionPane.showMessageDialog(this, EREe.getMessage());
                        }
                    });

                    JPanel JPModificarUbicacion = new JPanel();

                    JPModificarUbicacion.add(JLCodigo);
                    JPModificarUbicacion.add(JLDescripcion);
                    JPModificarUbicacion.add(JTFDescripcionUbicacion);
                    JPModificarUbicacion.add(JBModificarUbicacion);

                    add(JPModificarUbicacion);
                    setModalityType(ModalityType.APPLICATION_MODAL);
                    setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this,"Por favor, seleccione una fila.");
                }


                break;
            case "Tipos de Equipo":

                int filaTipoEquipo = filaSeleccionada;

                if(filaTipoEquipo != -1) {
                    String codigoTipoEquipo = datosRed.getJTTipoEquipo().getModel().
                            getValueAt(filaTipoEquipo, 0).toString();

                    TipoEquipo tipoEquipo = coordinador.buscarTipoEquipo(codigoTipoEquipo);

                    setSize(AGREGAR_TIPOEQUIPO_MAX_ANCHO,AGREGAR_TIPOEQUIPO_MAX_ALTO);
                    setResizable(false);
                    setTitle("Modificar Tipo de Equipo");

                    JLabel JLCodigoTipoEquipo = new JLabel("Codigo: " + codigoTipoEquipo);
                    JLabel JLDescripcionTipoEquipo = new JLabel("Descripcion: ");

                    JTextField JTFDescripcionTipoEquipo = new JTextField();
                    JTFDescripcionTipoEquipo.setText(tipoEquipo.getDescripcion());
                    JTFDescripcionTipoEquipo.setPreferredSize(JTF_DIMENSIONES);

                    JButton JBModificarTipoEquipo = new JButton("Modificar");
                    JBModificarTipoEquipo.addActionListener(e1 -> {

                        String descripcionTipoEquipo = JTFDescripcionTipoEquipo.getText();
                        tipoEquipo.setDescripcion(descripcionTipoEquipo);

                        try {
                            coordinador.modificarTipoEquipo(tipoEquipo);
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaTipoEquipos();
                            JOptionPane.showMessageDialog(this, "Tipo de equipo modificado correctamente.");
                            dispose();
                        } catch (TipoEquipoRepetidoException TER) {
                            JOptionPane.showMessageDialog(this, TER.getMessage());
                        }
                    });

                    JPanel JPModificarTipoEquipo = new JPanel();
                    JPModificarTipoEquipo.add(JLCodigoTipoEquipo);
                    JPModificarTipoEquipo.add(JLDescripcionTipoEquipo);
                    JPModificarTipoEquipo.add(JTFDescripcionTipoEquipo);
                    JPModificarTipoEquipo.add(JBModificarTipoEquipo);

                    add(JPModificarTipoEquipo);
                    setModalityType(ModalityType.APPLICATION_MODAL);
                    setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(this,"Por favor, seleccione una fila.");
                }
                break;
        }
    }

}
