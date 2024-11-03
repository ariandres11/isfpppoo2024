package controlador;

import gui.Interfaz;
import gui.PanelMenu;
import gui.PanelOpcionesSuperior;
import modelo.*;
import negocio.Calculo;
import negocio.roles.RoleContext;
import negocio.roles.RoleStrategy;
import negocio.roles.concrete_role_strategies.UserRoleStrategy;

import java.util.List;
import java.util.Map;

public class Coordinador {
    private Red red;
    private Calculo calculo;
    private Interfaz interfaz;
    private RoleContext roleContext;

    public Coordinador() {
        this.roleContext = new RoleContext();
        //Por defecto siempre empezará en usuario
        this.roleContext.setRoleStrategy(new UserRoleStrategy());
    }

    public void setRed(Red red) { this.red = red; }
    public void setCalculo(Calculo calculo) { this.calculo = calculo; }
    public void setInterfaz(Interfaz interfaz) { this.interfaz = interfaz; interfaz.iniciar(this);}
    public PanelMenu crearPanelMenu() { return new PanelMenu(interfaz, roleContext); }
    public PanelOpcionesSuperior crearPanelOpcionesSuperior() { return new PanelOpcionesSuperior(interfaz, roleContext); }
    public void setRoleStrategy(RoleStrategy strategy) {
        this.roleContext.setRoleStrategy(strategy);
    }
    public void agregarEquipo(Equipo equipo){
        red.agregarEquipo(equipo);
    }
    public void agregarConexion(Conexion conexion){
        red.agregarConexion(conexion);
    }

    public void agregarUbicacion(Ubicacion ubicacion){
        red.agregarUbicacion(ubicacion);
    }

    public void borrarConexion(Conexion conexion){
        red.borrarConexion(conexion);
    }

    public void borrarEquipo(Equipo equipo){
        red.borrarEquipo(equipo);
    }

    public void borrarUbicacion(Ubicacion ubicacion){
        red.borrarUbicacion(ubicacion);
    }
    public List<Conexion> listarConexiones() {
        return red.getConexiones();
    }

    public List<Equipo> listarEquipos() {
        return red.getEquipos();
    }

    public List<Ubicacion> listarUbicaciones() {
        return red.getUbicaciones();
    }
    public List<TipoPuerto> listarTipoPuertos() { return red.getTipoPuertos(); }

    public List<TipoCable> listarTipoCables() { return red.getTipoCables(); }

    public List<TipoEquipo> listarTipoEquipos() {return red.getTipoEquipos(); }

    public List<String> listaIPs() {
        return calculo.listarIps(red.getEquipos());
    }


    public Equipo buscarEquipo(String codigoEquipo) { return red.buscarEquipo(codigoEquipo); }

    public Conexion buscarConexion(String codigoEquipo1, String codigoEquipo2, String codigoTipoCable) {
        return red.buscarConexion(codigoEquipo1, codigoEquipo2, codigoTipoCable);
    }

    public Ubicacion buscarUbicacion(String codigoUbicacion) { return red.buscarUbicacion(codigoUbicacion); }

    public TipoEquipo buscarTipoEquipo(String codigoTipoEquipo) { return red.buscarTipoEquipo(codigoTipoEquipo); }

    public TipoPuerto buscarTipoPuerto(String codigoTipoPuerto) { return red.buscarTipoPuerto(codigoTipoPuerto); }

    public TipoCable buscarTipoCable(String codigoTipoCable) { return red.buscarTipoCable(codigoTipoCable); }


    public List<Conexion> calcularMasRapido(Equipo equipo1, Equipo equipo2) {
        return calculo.masRapido(equipo1, equipo2);
    }

    public void cargarDatos(){
        calculo.cargarDatos(red.getConexiones(),red.getEquipos());
    }

    public void advertencia(String advertencia){
        interfaz.advertencia(advertencia);
    }

    public Map<String, Boolean> pingIPS (int dirRed1, int dirRed2, int dirHost1p1, int dirHost1p2, int dirHost2p1, int dirHost2p2) {
        return calculo.pingEntreIPs(dirRed1,dirRed2,dirHost1p1,dirHost1p2,dirHost2p1,dirHost2p2);
    }

    public boolean ping(Equipo equipo){
        return calculo.ping(equipo);
    }


}
