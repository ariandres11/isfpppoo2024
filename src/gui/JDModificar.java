package gui;

import controlador.Coordinador;
import excepciones.*;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class JDModificar extends JDialog {
    final int JL_X_COORD = 20;
    final int JL_Y_COORD = 15;
    final int WIDTH = 120;
    final int HEIGHT = 20;

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

                    setSize(540,480);
                    setResizable(false);
                    setTitle("Modificar Equipo");

                    List<String> direccionesIP = equipo.getDireccionesIP();
                    List<TipoPuerto> tipoPuertos = equipo.getTipoPuertos();
                    List<Integer> puertosCantidad = equipo.getPuertosCantidades();


                    JLabel JLCodigo = new JLabel("Codigo (*):" + equipo.getCodigo());
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


                    JTextField JTFDescripcion = new JTextField();
                    JTFDescripcion.setText(equipo.getDescripcion());
                    JTFDescripcion.setBounds(JL_X_COORD*6,JL_Y_COORD*3,WIDTH,HEIGHT);
                    JTextField JTFMarca = new JTextField();
                    JTFMarca.setText(equipo.getMarca());
                    JTFMarca.setBounds(JL_X_COORD*6,JL_Y_COORD*5,WIDTH,HEIGHT);
                    JTextField JTFModelo = new JTextField();
                    JTFModelo.setText(equipo.getModelo());
                    JTFModelo.setBounds(JL_X_COORD*6,JL_Y_COORD*7,WIDTH,HEIGHT);


                    JComboBox<String> JCBTipoEquipo = new JComboBox<String>();
                    JCBTipoEquipo.setBounds(JL_X_COORD*6,JL_Y_COORD*9,WIDTH/2,HEIGHT);
                    JComboBox<String> JCBUbicacion = new JComboBox<String>();
                    JCBUbicacion.setBounds(JL_X_COORD*6,JL_Y_COORD*11,WIDTH/2,HEIGHT);
                    JToggleButton JTBEstado = new JToggleButton();

                    JTBEstado.setBounds(JL_X_COORD*6,JL_Y_COORD*13,WIDTH,HEIGHT);
                    JTBEstado.setText(equipo.isEstado() ? "Activo" : "Inactivo");
                    JTBEstado.setSelected(equipo.isEstado());
                    JTBEstado.addChangeListener(e2 -> {
                        if( JTBEstado.isSelected() ) JTBEstado.setText("Activo");
                        else JTBEstado.setText("Inactivo");
                    });

                    for (TipoEquipo tipoEquipo : coordinador.listarTipoEquipos()) {
                        JCBTipoEquipo.addItem(tipoEquipo.getCodigo());
                    }
                    JCBTipoEquipo.setSelectedItem(equipo.getTipoEquipo());


                    for (Ubicacion ubicacion : coordinador.listarUbicaciones()) {
                        JCBUbicacion.addItem(ubicacion.getCodigo());
                    }
                    JCBUbicacion.setSelectedItem(equipo.getUbicacion());


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
                    String puertosCantidades = "";
                    for (int i=0 ; i<tipoPuertos.size() ; i++) {
                        puertosCantidades += tipoPuertos.get(i).getCodigo() + "," + puertosCantidad.get(i) + "; ";
                    }
                    JTAPuertosAgregados.setText(puertosCantidades);

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

                    String direccionesIpInfo = "";
                    for (String dirIP : direccionesIP) {
                        direccionesIpInfo += dirIP + "; ";
                    }
                    JTADireccionesIPAgregadas.setText(direccionesIpInfo);

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

                    JButton JBModificarEquipo = new JButton("Modificar");
                    JBModificarEquipo.setBounds(JL_X_COORD*3,JL_Y_COORD*20,WIDTH,HEIGHT*2);
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

                if( filaUbicacion != 1){
                    String codigoUbicacion = datosRed.getJTUbicaciones().getModel().
                            getValueAt(filaUbicacion, 0).toString();

                    Ubicacion ubicacion = coordinador.buscarUbicacion(codigoUbicacion);

                    setSize(300,100);
                    setResizable(false);
                    setTitle("Modificar Ubicacion");

                    JLabel JLCodigo = new JLabel("Codigo: " + codigoUbicacion);
                    JLabel JLDescripcion = new JLabel("Descripcion: ");

                    JTextField JTFDescripcionUbicacion = new JTextField();
                    JTFDescripcionUbicacion.setPreferredSize(new Dimension(70,20));
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

                    setSize(300,100);
                    setResizable(false);
                    setTitle("Modificar Tipo de Equipo");

                    JLabel JLCodigoTipoEquipo = new JLabel("Codigo: " + codigoTipoEquipo);
                    JLabel JLDescripcionTipoEquipo = new JLabel("Descripcion: ");

                    JTextField JTFDescripcionTipoEquipo = new JTextField();
                    JTFDescripcionTipoEquipo.setText(tipoEquipo.getDescripcion());
                    JTFDescripcionTipoEquipo.setPreferredSize(new Dimension(70,20));

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
            case "Tipos de Puerto":
                /*
                int filaTipoPuerto = filaSeleccionada;

                if(filaTipoPuerto != -1) {
                    String codigoTipoPuerto = datosRed.getJTTipoPuerto().getModel().
                            getValueAt(filaTipoPuerto, 0).toString();

                    TipoPuerto tipoPuerto = coordinador.buscarTipoPuerto(codigoTipoPuerto);

                    setSize(300,100);
                    setResizable(false);
                    setTitle("Modificar Tipo de Puerto");

                    JLabel JLCodigoTipoPuerto = new JLabel("Codigo: " + codigoTipoPuerto);
                    JLabel JLDescripcionTipoPuerto = new JLabel("Descripcion: ");
                    JLabel JLVelocidadTipoPuerto = new JLabel("Velocidad: ");

                    JTextField JTFDescripcionTipoPuerto = new JTextField();
                    JTFDescripcionTipoPuerto.setText(tipoPuerto.getDescripcion());
                    JTFDescripcionTipoPuerto.setPreferredSize(new Dimension(70,20));
                    JTextField JTFVelocidadTipoPuerto = new JTextField();
                    JTFVelocidadTipoPuerto.setText(Integer.toString(tipoPuerto.getVelocidad()));
                    JTFVelocidadTipoPuerto.setPreferredSize(new Dimension(70,20));

                    JButton JBModificarTipoPuerto = new JButton("Modificar");
                    JBModificarTipoPuerto.addActionListener(e1 -> {

                        String descripcionTipoPuerto = JTFDescripcionTipoPuerto.getText();
                        int velocidadTipoPuerto = Integer.parseInt(JTFVelocidadTipoPuerto.getText());

                        tipoPuerto.setDescripcion(descripcionTipoPuerto);
                        tipoPuerto.setVelocidad(velocidadTipoPuerto);

                        try {
                            coordinador.modificarTipoPuerto(tipoPuerto);
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaTipoPuertos();
                            JOptionPane.showMessageDialog(this, "Tipo de Puerto modificado correctamente.");
                            dispose();
                        } catch (TipoPuertoRepetidoException TPR) {
                            JOptionPane.showMessageDialog(this, TPR.getMessage());
                        }
                    });

                    JPanel JPModificarTipoPuerto = new JPanel();
                    JPModificarTipoPuerto.add(JLCodigoTipoPuerto);
                    JPModificarTipoPuerto.add(JLDescripcionTipoPuerto);
                    JPModificarTipoPuerto.add(JTFDescripcionTipoPuerto);
                    JPModificarTipoPuerto.add(JLVelocidadTipoPuerto);
                    JPModificarTipoPuerto.add(JTFVelocidadTipoPuerto);
                    JPModificarTipoPuerto.add(JBModificarTipoPuerto);

                    add(JPModificarTipoPuerto);
                    setModalityType(ModalityType.APPLICATION_MODAL);
                    setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this,"Por favor, seleccione una fila.");
                }
                */

                break;
            case "Tipos de Cable":
                /*
                int filaTipoCable = filaSeleccionada;

                if(filaTipoCable != -1) {

                    String codigoTipoCable = datosRed.getJTTipoCable().getModel().
                            getValueAt(filaTipoCable, 0).toString();

                    TipoCable tipoCable = coordinador.buscarTipoCable(codigoTipoCable);

                    setSize(300,100);
                    setResizable(false);
                    setTitle("Modificar Tipo de Cable");

                    JLabel JLCodigoTipoCable = new JLabel("Codigo: " + codigoTipoCable);
                    JLabel JLDescripcionTipoCable = new JLabel("Descripcion: ");
                    JLabel JLVelocidadTipoCable = new JLabel("Velocidad: ");

                    JTextField JTFDescripcionTipoCable = new JTextField();
                    JTFDescripcionTipoCable.setText(tipoCable.getDescripcion());
                    JTFDescripcionTipoCable.setPreferredSize(new Dimension(70,20));
                    JTextField JTFVelocidadTipoCable = new JTextField();
                    JTFVelocidadTipoCable.setText(Integer.toString(tipoCable.getVelocidad()));
                    JTFVelocidadTipoCable.setPreferredSize(new Dimension(70,20));

                    JButton JBModificarTipoCable= new JButton("Modificar");
                    JBModificarTipoCable.addActionListener(e1 -> {
                        String descripcionTipoCable = JTFDescripcionTipoCable.getText();
                        int velocidadTipoCable = Integer.parseInt(JTFVelocidadTipoCable.getText());

                        tipoCable.setDescripcion(descripcionTipoCable);
                        tipoCable.setVelocidad(velocidadTipoCable);

                        try {
                            coordinador.modificarTipoCable(tipoCable);
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaTipoCables();
                            JOptionPane.showMessageDialog(this, "Tipo de Cable modificado correctamente.");
                            dispose();
                        } catch (TipoCableRepetidoException TCR) {
                            JOptionPane.showMessageDialog(this, TCR.getMessage());
                        }
                    });

                    JPanel JPModificarTipoCable = new JPanel();
                    JPModificarTipoCable.add(JLCodigoTipoCable);
                    JPModificarTipoCable.add(JLDescripcionTipoCable);
                    JPModificarTipoCable.add(JTFDescripcionTipoCable);
                    JPModificarTipoCable.add(JLVelocidadTipoCable);
                    JPModificarTipoCable.add(JTFVelocidadTipoCable);
                    JPModificarTipoCable.add(JBModificarTipoCable);

                    add(JPModificarTipoCable);
                    setModalityType(ModalityType.APPLICATION_MODAL);
                    setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(this,"Por favor, seleccione una fila.");
                }
                */

                break;

        }
    }

}
