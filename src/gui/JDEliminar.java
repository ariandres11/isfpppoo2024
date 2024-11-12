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
                    try {
                        coordinador.borrarEquipo(equipo);
                        coordinador.cargarDatos();
                        datosRed.actualizarTablaEquipos();
                        JOptionPane.showMessageDialog(this, "Equipo eliminado exitosamente.");
                    } catch (EquipoNoExistenteException | EquipoEnUsoException ENEEe) {
                        JOptionPane.showMessageDialog(this, ENEEe.getMessage());
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
                    try {
                        coordinador.borrarConexion(conexion);
                        coordinador.cargarDatos();
                        datosRed.actualizarTablaConexiones();
                        JOptionPane.showMessageDialog(this,"Conexion eliminada exitosamente.");
                    } catch (ConexionNoExistenteException CNEe) {
                        JOptionPane.showMessageDialog(this, CNEe.getMessage());
                    }
                }
                break;
            case "Ubicaciones":
                int filaUbicacion = filaSeleccionada;
                if( filaUbicacion != -1 ) {
                    String codigoUbicacion = datosRed.getJTUbicaciones().getModel().getValueAt(filaUbicacion, 0).toString();

                    Ubicacion ubicacion = coordinador.buscarUbicacion(codigoUbicacion);
                    try {
                        coordinador.borrarUbicacion(ubicacion);
                        coordinador.cargarDatos();
                        datosRed.actualizarTablaUbicaciones();
                        JOptionPane.showMessageDialog(this, "Ubicacion eliminada exitosamente.");
                    } catch (UbicacionNoExistenteException | UbicacionEnUsoException UNEEe) {
                        JOptionPane.showMessageDialog(this, UNEEe.getMessage());
                    }
                }
                break;
            case "Tipos de Equipo":
                int filaTipoEquipo = filaSeleccionada;
                if( filaTipoEquipo != -1) {
                    String codigoTipoEquipo = datosRed.getJTTipoEquipo().getModel().getValueAt(filaTipoEquipo, 0).toString();

                    TipoEquipo tipoEquipo = coordinador.buscarTipoEquipo(codigoTipoEquipo);
                    try {
                        coordinador.borrarTipoEquipo(tipoEquipo);
                        coordinador.cargarDatos();
                        datosRed.actualizarTablaTipoEquipos();
                        JOptionPane.showMessageDialog(this, "Tipo de equipo eliminado exitosamente.");
                    } catch (TipoEquipoEnUsoException TEEUEe) {
                        JOptionPane.showMessageDialog(this, TEEUEe.getMessage());
                    }
                }
                break;
            case "Tipos de Puerto":
                int filaTipoPuerto = filaSeleccionada;
                if( filaTipoPuerto != -1) {
                    String codigoTipoPuerto = datosRed.getJTTipoPuerto().getModel().getValueAt(filaTipoPuerto, 0).toString();

                    TipoPuerto tipoPuerto = coordinador.buscarTipoPuerto(codigoTipoPuerto);
                    try {
                        coordinador.borrarTipoPuerto(tipoPuerto);
                        coordinador.cargarDatos();
                        datosRed.actualizarTablaTipoPuertos();
                        JOptionPane.showMessageDialog(this, "Tipo de Puerto eliminado exitosamente.");
                    } catch (TipoPuertoEnUsoException TPEUEe) {
                        JOptionPane.showMessageDialog(this, TPEUEe.getMessage());
                    }
                }
                break;
            case "Tipos de Cable":
                int filaTipoCable =  filaSeleccionada;
                if( filaTipoCable!= -1) {
                    String codigoTipoCable = datosRed.getJTTipoCable().getModel().getValueAt(filaTipoCable,0).toString();

                    TipoCable tipoCable = coordinador.buscarTipoCable(codigoTipoCable);
                    try {
                        coordinador.borrarTipoCable(tipoCable);
                        coordinador.cargarDatos();
                        datosRed.actualizarTablaTipoCables();
                        JOptionPane.showMessageDialog(this, "Tipo de cable eliminado exitosamente.");
                    } catch (TipoCableEnUsoException TCEUEe) {
                        JOptionPane.showMessageDialog(this, TCEUEe.getMessage());
                    }
                }
                break;
        }
    }
}
