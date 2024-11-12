package gui;

import controlador.Coordinador;
import excepciones.*;
import modelo.*;

import javax.swing.*;

public class JDEliminar extends JDialog {

    private Coordinador coordinador;
    private FrameDatosRed datosRed;

    public JDEliminar (Coordinador coordinador, FrameDatosRed datosRed) {
        this.coordinador = coordinador;
        this.datosRed = datosRed;
    }

    public void ventanaEliminar(String tituloTabla, int filaSeleccionada) {

        switch(tituloTabla) {
            case "Equipos":
                int filaEquipo = filaSeleccionada;
                if( filaEquipo != -1) {
                    String codigoEquipo = datosRed.getJTEquipos().getModel().getValueAt(filaEquipo, 0).toString();

                    Equipo equipo = coordinador.buscarEquipo(codigoEquipo);
                    int resultado = JOptionPane.showConfirmDialog(null ,"Seguro que quiere eliminar el Equipo: "
                                    + "\nCodigo: " + equipo.getCodigo()
                                    + "\nDescripcion: " + equipo.getDescripcion()
                                    + "\nTipo de Equipo: " + equipo.getTipoEquipo().getCodigo() + ", " + equipo.getTipoEquipo().getDescripcion()
                                    + "\nMarca: " + equipo.getMarca()
                                    + "\nModelo: " + equipo.getModelo()
                                    + "\nUbicacion: " + equipo.getUbicacion().getCodigo() + ", " + equipo.getUbicacion().getDescripcion()
                            , "Eliminar Equipo"
                            , JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (resultado == JOptionPane.OK_OPTION) {
                        try {
                            coordinador.borrarEquipo(equipo);
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaEquipos();
                            JOptionPane.showMessageDialog(this, "Equipo eliminado exitosamente.");
                        } catch (EquipoNoExistenteException | EquipoEnUsoException ENEEe) {
                            JOptionPane.showMessageDialog(this, ENEEe.getMessage());
                        }
                    }
                }
                break;
            case "Conexiones":

                int filaConexion = filaSeleccionada;

                if( filaConexion != -1 ) {
                    String codigoEquipo1 = datosRed.getJTConexiones().getModel().getValueAt(filaConexion, 0).toString();
                    String codigoEquipo2 = datosRed.getJTConexiones().getModel().getValueAt(filaConexion,1).toString();
                    String codigoTipoCable = datosRed.getJTConexiones().getModel().getValueAt(filaConexion,2).toString();

                    Conexion conexion = coordinador.buscarConexion(codigoEquipo1, codigoEquipo2, codigoTipoCable);
                    int resultado = JOptionPane.showConfirmDialog(null , "Seguro que quiere eliminar la Conexion: "
                                    + "\nEquipo 1: " + conexion.getEquipo1().getCodigo() + ", " + conexion.getEquipo1().getDescripcion()
                                    + "\nEquipo 2: " + conexion.getEquipo2().getCodigo() + ", " + conexion.getEquipo2().getDescripcion()
                                    + "\nCable: " + conexion.getTipoCable().getCodigo() + conexion.getTipoCable().getDescripcion()
                            , "Eliminar Conexion"
                            , JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (resultado == JOptionPane.OK_OPTION) {
                        try {
                            coordinador.borrarConexion(conexion);
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaConexiones();
                            JOptionPane.showMessageDialog(this, "Conexion eliminada exitosamente.");
                        } catch (ConexionNoExistenteException CNEe) {
                            JOptionPane.showMessageDialog(this, CNEe.getMessage());
                        }
                    }
                }
                break;
            case "Ubicaciones":
                int filaUbicacion = filaSeleccionada;
                if( filaUbicacion != -1 ) {
                    String codigoUbicacion = datosRed.getJTUbicaciones().getModel().getValueAt(filaUbicacion, 0).toString();

                    Ubicacion ubicacion = coordinador.buscarUbicacion(codigoUbicacion);
                    int resultado = JOptionPane.showConfirmDialog(null , "Seguro que quiere eliminar la Ubicacion:"
                                    + "\nCodigo: " + ubicacion.getCodigo()
                                    + "\nDescripcion: " + ubicacion.getDescripcion()
                            , "Eliminar Ubicacion"
                            , JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (resultado == JOptionPane.OK_OPTION) {
                        try {
                            coordinador.borrarUbicacion(ubicacion);
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaUbicaciones();
                            JOptionPane.showMessageDialog(this, "Ubicacion eliminada exitosamente.");
                        } catch (UbicacionNoExistenteException | UbicacionEnUsoException UNEEe) {
                            JOptionPane.showMessageDialog(this, UNEEe.getMessage());
                        }
                    }
                }
                break;
            case "Tipos de Equipo":
                int filaTipoEquipo = filaSeleccionada;
                if( filaTipoEquipo != -1) {
                    String codigoTipoEquipo = datosRed.getJTTipoEquipo().getModel().getValueAt(filaTipoEquipo, 0).toString();

                    TipoEquipo tipoEquipo = coordinador.buscarTipoEquipo(codigoTipoEquipo);
                    int resultado = JOptionPane.showConfirmDialog(null , "Seguro que quiere eliminar el Tipo de Equipo:"
                                    + "\nCodigo: " + tipoEquipo.getCodigo()
                                    + "\nDescripcion: " + tipoEquipo.getDescripcion()
                            , "Eliminar Tipo de Equipo"
                            , JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (resultado == JOptionPane.OK_OPTION) {
                        try {
                            coordinador.borrarTipoEquipo(tipoEquipo);
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaTipoEquipos();
                            JOptionPane.showMessageDialog(this, "Tipo de equipo eliminado exitosamente.");
                        } catch (TipoEquipoEnUsoException TEEUEe) {
                            JOptionPane.showMessageDialog(this, TEEUEe.getMessage());
                        }
                    }
                }
                break;
            case "Tipos de Puerto":
                int filaTipoPuerto = filaSeleccionada;
                if( filaTipoPuerto != -1) {
                    String codigoTipoPuerto = datosRed.getJTTipoPuerto().getModel().getValueAt(filaTipoPuerto, 0).toString();

                    TipoPuerto tipoPuerto = coordinador.buscarTipoPuerto(codigoTipoPuerto);
                    int resultado = JOptionPane.showConfirmDialog(null , "Seguro que quiere eliminar el Tipo de Puerto:"
                                    + "\nCodigo: " + tipoPuerto.getCodigo()
                                    + "\nDescripcion: " + tipoPuerto.getDescripcion()
                                    + "\nVelocidad: " + tipoPuerto.getVelocidad()
                            , "Eliminar Tipo de Puerto"
                            , JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (resultado == JOptionPane.OK_OPTION) {
                        try {
                            coordinador.borrarTipoPuerto(tipoPuerto);
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaTipoPuertos();
                            JOptionPane.showMessageDialog(this, "Tipo de Puerto eliminado exitosamente.");
                        } catch (TipoPuertoEnUsoException TPEUEe) {
                            JOptionPane.showMessageDialog(this, TPEUEe.getMessage());
                        }
                    }
                }
                break;
            case "Tipos de Cable":
                int filaTipoCable =  filaSeleccionada;
                if( filaTipoCable!= -1) {
                    String codigoTipoCable = datosRed.getJTTipoCable().getModel().getValueAt(filaTipoCable,0).toString();

                    TipoCable tipoCable = coordinador.buscarTipoCable(codigoTipoCable);
                    int resultado = JOptionPane.showConfirmDialog(null , "Seguro que quiere eliminar el Tipo de Cable:"
                                    + "\nCodigo: " + tipoCable.getCodigo()
                                    + "\nDescripcion: " + tipoCable.getDescripcion()
                                    + "\nVelocidad: " + tipoCable.getVelocidad()
                            , "Eliminar Tipo de Cable"
                            , JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (resultado == JOptionPane.OK_OPTION) {
                        try {
                            coordinador.borrarTipoCable(tipoCable);
                            coordinador.cargarDatos();
                            datosRed.actualizarTablaTipoCables();
                            JOptionPane.showMessageDialog(this, "Tipo de cable eliminado exitosamente.");
                        } catch (TipoCableEnUsoException TCEUEe) {
                            JOptionPane.showMessageDialog(this, TCEUEe.getMessage());
                        }
                    }
                }
                break;
        }
    }
}
