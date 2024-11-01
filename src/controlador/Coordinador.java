package controlador;

import gui.Interfaz;
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

    public Red getRed() { return red; }
    public void setRed(Red red) { this.red = red; }
    public Calculo getCalculo() { return calculo; }
    public void setCalculo(Calculo calculo) { this.calculo = calculo; }
    public Interfaz getInterfaz() { return interfaz; }
    public void setInterfaz(Interfaz interfaz) { this.interfaz = interfaz; }

    public RoleContext getRoleContext() { return roleContext; }
    public void setRoleStrategy(RoleStrategy strategy) {
        this.roleContext.setRoleStrategy(strategy);
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

    public List<Conexion> calcularMasRapido(Equipo equipo1, Equipo equipo2) {
        return calculo.masRapido(equipo1, equipo2);
    }

    public void advertencia(String advertencia){
        interfaz.advertencia(advertencia);
    }

    public Map<String, Boolean> pingIPS (int dirRed1, int dirRed2, int dirHost1p1, int dirHost1p2, int dirHost2p1, int dirHost2p2) {
        return getCalculo().pingEntreIPs(dirRed1,dirRed2,dirHost1p1,dirHost1p2,dirHost2p1,dirHost2p2);
    }



}
