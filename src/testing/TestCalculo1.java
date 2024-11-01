package testing;

import modelo.*;
import negocio.Calculo;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

/**
 * Esta clase contiene pruebas unitarias para la clase Calculo.
 */

public class TestCalculo1{

    /**
     * En este test se prueba el correcto funcionamiento de la clase Calculo,
     * más puntualmente de la función "másRapido".
     * @see Calculo#masRapido(Equipo, Equipo)
     * */
    @Test
    public void test() {

        Ubicacion u1 = new Ubicacion("SS","Sala de Servidores");
        Ubicacion u2 = new Ubicacion("L0","Laboratorio 0");

        TipoEquipo te1 = new TipoEquipo();
        te1.setCodigo("COM");
        te1.setDescripcion("Computadora");
        TipoEquipo te2 = new TipoEquipo();
        te2.setCodigo("SW");
        te2.setDescripcion("Switch");

        //Cargar equipos y conexiones
        Equipo pc1 = new Equipo();
        pc1.setDescripcion("PC para alumno");
        pc1.setCodigo("PC01");
        pc1.setUbicacion(u2);
        pc1.setTipoEquipo(te1);

        TipoPuerto tp1 = new TipoPuerto();
        tp1.setCodigo("1G");
        tp1.setVelocidad(1000);
        tp1.setDescripcion("1 Gbps");
        pc1.agregarPuerto(1,tp1);

        Equipo sw1 = new Equipo();
        sw1.setDescripcion("Switch 1");
        sw1.setCodigo("SWH1");
        sw1.setMarca("Intellinet");
        sw1.setUbicacion(u1);
        sw1.setTipoEquipo(te2);

        TipoPuerto tp2 = new TipoPuerto();
        tp2.setCodigo("100M");
        tp2.setVelocidad(100);
        tp2.setDescripcion("100 Mbps");
        sw1.agregarPuerto(8,tp2);

        Equipo sw2 = new Equipo();
        sw2.setDescripcion("Switch 2");
        sw2.setCodigo("SWH2");
        sw2.setMarca("HP Aruba");
        sw2.setModelo("1930 48G 4SFP/SFP+");
        sw2.agregarPuerto(48,tp2);
        sw2.setUbicacion(u1);
        sw2.setTipoEquipo(te2);


        Equipo sw3 = new Equipo();
        sw3.setDescripcion("Switch 3");
        sw3.setCodigo("SWH3");
        sw3.setMarca("HP Aruba");
        sw3.setModelo("1930 48G 4SFP/SFP+");
        sw3.agregarPuerto(48,tp2);
        sw3.setUbicacion(u1);
        sw3.setTipoEquipo(te2);

        //Tipos de cable

        TipoCable tc1 = new TipoCable();
        tc1.setCodigo("C5e");
        tc1.setDescripcion("UTP Cat.5e");
        tc1.setVelocidad(1000);

        TipoCable tc2 = new TipoCable();
        tc2.setCodigo("FOM");
        tc2.setDescripcion("Fibra Optica Monomodo");
        tc2.setVelocidad(10000);

        //Conexiones
        Conexion c1 = new Conexion();
        c1.setEquipo1(pc1);
        c1.setEquipo2(sw2);
        c1.setTipoPuertoEquipo1(pc1.getTipoPuertoInicial());
        c1.setTipoPuertoEquipo2(sw2.getTipoPuertoInicial());
        c1.setTipoCable(tc2);

        Conexion c2 = new Conexion();
        c2.setEquipo1(sw2);
        c2.setEquipo2(sw1);
        c2.setTipoPuertoEquipo1(sw2.getTipoPuertoInicial());
        c2.setTipoPuertoEquipo2(sw1.getTipoPuertoInicial());
        c2.setTipoCable(tc1);

        Conexion c3 = new Conexion();
        c3.setEquipo1(sw2);
        c3.setEquipo2(sw3);
        c3.setTipoPuertoEquipo1(sw2.getTipoPuertoInicial());
        c3.setTipoPuertoEquipo2(sw3.getTipoPuertoInicial());
        c3.setTipoCable(tc2);

        Conexion c4 = new Conexion();
        c4.setEquipo1(sw1);
        c4.setEquipo2(sw3);
        c4.setTipoPuertoEquipo1(sw1.getTipoPuertoInicial());
        c4.setTipoPuertoEquipo2(sw3.getTipoPuertoInicial());
        c4.setTipoCable(tc2);

        ArrayList<Conexion> conexiones = new ArrayList<Conexion>();
        conexiones.add(c1);
        conexiones.add(c3);
        conexiones.add(c4);

        ArrayList<Equipo> equipos = new ArrayList<Equipo>();
        equipos.add(pc1);
        equipos.add(sw1);
        equipos.add(sw2);
        equipos.add(sw3);

        Calculo calculo = new Calculo();
        calculo.cargarDatos(conexiones, equipos);
        System.out.println(calculo.toString());

        //Conexiones que deberia devolver:

        ArrayList<Object> conexionesPretendidas = new ArrayList<>();
        conexionesPretendidas.add(c1);
        conexionesPretendidas.add(c3);
        conexionesPretendidas.add(c4);

        /**
         * Compara la lista de conexiones calculadas manualmente con lo que devuelve el método testeado.
         * @see Calculo#masRapido(Equipo, Equipo)
         * */
        Assertions.assertEquals(conexionesPretendidas, calculo.masRapido(pc1,sw1));

    }
}