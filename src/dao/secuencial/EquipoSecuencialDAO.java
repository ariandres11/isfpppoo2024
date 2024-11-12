package dao.secuencial;

import dao.EquipoDAO;
import dao.TipoEquipoDAO;
import dao.TipoPuertoDAO;
import dao.UbicacionDAO;

import modelo.Equipo;
import modelo.TipoPuerto;
import modelo.TipoEquipo;
import modelo.Ubicacion;

import java.util.Map;
import java.util.List;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.Formatter;
import java.util.ResourceBundle;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;


/**
 * Clase que implementa la interfaz {@link EquipoDAO} para manejar
 * operaciones sobre equipos de manera secuencial, utilizando archivos.
 */
public class EquipoSecuencialDAO implements EquipoDAO {
    private List<Equipo> listaEquipos;
    private Hashtable<String, TipoPuerto> tablaTiposPuerto;
    private Hashtable<String, TipoEquipo> tablaTiposEquipo;
    private Hashtable<String, Ubicacion> tablaUbicaciones;
    private String nombre;
    private boolean actualizar;
    private static final Logger logger = Logger.getLogger(EquipoSecuencialDAO.class);

    /**
     * Constructor que inicializa las tablas de tipos de puerto,
     * tipos de equipo y ubicaciones, y carga el nombre del archivo
     * desde un recurso.
     */
    public EquipoSecuencialDAO() {
        tablaTiposPuerto = cargarTiposPuerto();
        tablaTiposEquipo = cargarTiposEquipo();
        tablaUbicaciones = cargarUbicaciones();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        nombre = rb.getString("equipos");
        actualizar = true;
    }

    /**
     * Lee los equipos desde un archivo y los almacena en una lista.
     *
     * @param file Nombre del archivo desde el que se leerán los equipos.
     * @return Lista de equipos leídos desde el archivo.
     */
    private List<Equipo> readFromFile(String file) {
        List<Equipo> list = new ArrayList<>();
        Scanner inFile = null;
        logger.info("Intentando leer equipos desde el archivo: " + file);
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter(";\\s*");
            while (inFile.hasNextLine()) {
                String line = inFile.nextLine();
                if (line.trim().isEmpty()) continue;
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");

                String codigo = lineScanner.hasNext() ? lineScanner.next().trim() : "";
                String descripcion = lineScanner.hasNext() ? lineScanner.next().trim() : "";
                String marca = lineScanner.hasNext() ? lineScanner.next().trim() : "";
                String modelo = lineScanner.hasNext() ? lineScanner.next().trim() : "";

                // Formatear las ips
                String ips = lineScanner.hasNext() ? lineScanner.next().trim() : "";
                String[] direccionesIP = ips.isEmpty() ? new String[0] : ips.split(",");

                String cantidadPuertosStr = lineScanner.hasNext() ? lineScanner.next().trim() : "0";
                String[] cantidadPuertos = cantidadPuertosStr.split(",");

                String tiposPuertosStr = lineScanner.hasNext() ? lineScanner.next().trim() : "";
                String[] tiposPuertos = tiposPuertosStr.split(",");

                if (cantidadPuertos.length > 0 && tiposPuertos.length > 0) {
                    try {
                        int cantidadPrimerPuerto = Integer.parseInt(cantidadPuertos[0]);
                        TipoPuerto tipoPuerto = tablaTiposPuerto.get(tiposPuertos[0]);
                        if (tipoPuerto == null) {
                            logger.error("Error: tipoPuerto es null para " + tiposPuertos[0]);
                            continue;
                        }

                        Ubicacion ubicacion = lineScanner.hasNext() ? tablaUbicaciones.get(lineScanner.next().trim()) : null;
                        TipoEquipo tipoEquipo = lineScanner.hasNext() ? tablaTiposEquipo.get(lineScanner.next().trim()) : null;
                        boolean estado = lineScanner.hasNextBoolean() ? lineScanner.nextBoolean() : false;
                        Equipo equipo = new Equipo(codigo, descripcion, marca, modelo, cantidadPrimerPuerto, tipoPuerto, ubicacion, tipoEquipo, estado);

                        for (String ip : direccionesIP) {
                            equipo.agregarDireccionIP(ip);
                            logger.info("Cargando equipo: " + codigo + " con IP: " + ip);
                        }

                        if (cantidadPuertos.length > 1 && tiposPuertos.length > 1) {
                            for (int i = 1; i < cantidadPuertos.length && i < tiposPuertos.length; i++) {
                                try {
                                    int cantidad = Integer.parseInt(cantidadPuertos[i]);
                                    tipoPuerto = tablaTiposPuerto.get(tiposPuertos[i]);
                                    if (tipoPuerto == null) {
                                        logger.error("Error: tipoPuerto es null para " + tiposPuertos[i]);
                                        continue;
                                    }
                                    equipo.agregarPuerto(cantidad, tipoPuerto);
                                } catch (NumberFormatException e) {
                                    logger.error("Error al parsear cantidad de puerto: " + cantidadPuertos[i], e);
                                }
                            }
                        }
                        list.add(equipo);
                    } catch (NumberFormatException e) {
                        logger.error("Error al parsear cantidad de puerto: " + cantidadPuertos[0], e);
                    }
                } else {
                    logger.warn("Datos de puertos incompletos para equipo: " + codigo);
                }
                lineScanner.close();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("Error al abrir el archivo: " + file, fileNotFoundException);
        } catch (NoSuchElementException noSuchElementException) {
            logger.error("Error en la estructura del archivo.", noSuchElementException);
        } catch (IllegalStateException illegalStateException) {
            logger.error("Error al leer el archivo.", illegalStateException);
        } finally {
            if (inFile != null) {
                inFile.close();
            }
        }
        return list;
    }


    /**
     * Escribe la lista de equipos en un archivo.
     *
     * @param list Lista de equipos a escribir.
     * @param file Nombre del archivo donde se escribirán los equipos.
     */
    private void writeToFile(List<Equipo> list, String file) {
        Formatter outFile = null;
        logger.info("Escribiendo equipos al archivo: " + file);
        try {
            outFile = new Formatter(file);
            for (Equipo equipo : list) {
                String ips = String.join(",", equipo.getDireccionesIP());
                StringBuilder cantidadPuertosBuilder = new StringBuilder();
                StringBuilder tiposPuertosBuilder = new StringBuilder();

                // Obtener la cantidad y tipos de puertos del equipo
                for (Map.Entry<String, Integer> entry : equipo.getMapaPuertos().entrySet()) {
                    tiposPuertosBuilder.append(entry.getKey());
                    cantidadPuertosBuilder.append(entry.getValue());
                    cantidadPuertosBuilder.append(",");
                    tiposPuertosBuilder.append(",");
                }

                // Eliminar el último carácter de coma si existen entradas
                if (cantidadPuertosBuilder.length() > 0) {
                    cantidadPuertosBuilder.setLength(cantidadPuertosBuilder.length() - 1);
                }
                if (tiposPuertosBuilder.length() > 0) {
                    tiposPuertosBuilder.setLength(tiposPuertosBuilder.length() - 1);
                }

                String cantidadPuertos = cantidadPuertosBuilder.toString();
                String tiposPuertos = tiposPuertosBuilder.toString();

                // Escribir los datos en el archivo en el mismo formato en el que se leen
                outFile.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%b;\n",
                        equipo.getCodigo(),
                        equipo.getDescripcion() != null ? equipo.getDescripcion() : "",
                        equipo.getMarca() != null ? equipo.getMarca() : "",
                        equipo.getModelo() != null ? equipo.getModelo() : "",
                        ips,
                        cantidadPuertos,
                        tiposPuertos,
                        equipo.getUbicacion() != null ? equipo.getUbicacion().getCodigo() : "",
                        equipo.getTipoEquipo() != null ? equipo.getTipoEquipo().getCodigo() : "",
                        equipo.isEstado());
            }
            logger.info("Equipos escritos exitosamente al archivo");
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("Error al abrir el archivo: " + file, fileNotFoundException);
        } finally {
            if (outFile != null) {
                outFile.close();
            }
        }
    }



    /**
     * Inserta un nuevo equipo en la lista y lo guarda en el archivo.
     *
     * @param equipo El equipo a insertar.
     */
    @Override
    public void insertar(Equipo equipo) {
        logger.info("Insertar equipo: " + equipo.getCodigo());
        listaEquipos.add(equipo);
        writeToFile(listaEquipos, nombre);
        actualizar = true;
        logger.info("Equipo insertado con exito.");
    }

    /**
     * Actualiza un equipo existente en la lista y guarda los cambios en el archivo.
     *
     * @param equipo El equipo a actualizar.
     */
    @Override
    public void actualizar(Equipo equipo) {
        logger.info("Actualizando equipo: " + equipo.getCodigo());
        int pos = listaEquipos.indexOf(equipo);
        listaEquipos.set(pos, equipo);
        writeToFile(listaEquipos, nombre);
        actualizar = true;
        logger.info("Equipo actualizado: " + equipo.getCodigo());

    }

    /**
     * Elimina un equipo de la lista y actualiza el archivo.
     *
     * @param equipo El equipo a eliminar.
     */
    @Override
    public void borrar(Equipo equipo) {
        logger.info("Eliminando equipo: " + equipo.getCodigo());
        listaEquipos.remove(equipo);
        writeToFile(listaEquipos, nombre);
        actualizar = true;
        logger.info("Equipo eliminado: " + equipo.getCodigo());
    }

    /**
     * Busca y devuelve todos los equipos. Si la lista no está actualizada,
     * la carga desde el archivo.
     *
     * @return Lista de todos los equipos.
     */
    @Override
    public List<Equipo> buscarTodos() {
        if (actualizar) {
            logger.info("Cargando todos los equipos desde el archivo: " + nombre);
            listaEquipos = readFromFile(nombre);
            actualizar = false;
            logger.info("Equipos cargados: " + listaEquipos.size());
        }
        return listaEquipos;
    }

    /**
     * Carga los tipos de equipo desde su respectivo DAO.
     *
     * @return Un Hashtable de tipos de equipo.
     * He agregado mensajes para indicar cuándo comienza la carga de datos, cuándo termina con éxito, y para capturar cualquier excepción que ocurra.
     * Manejo de excepciones: Envolví el código en un bloque try-catch para capturar y registrar cualquier error que pueda surgir durante la llamada al DAO.
     * Esto es para evitar que errores no manejados detengan la ejecución del programa.
     *
     */
    public Hashtable<String, TipoEquipo> cargarTiposEquipo() {
        logger.info("Cargando tipos de equipo...");
        Hashtable<String, TipoEquipo> tiposEquipo = new Hashtable<>();
        try {
            TipoEquipoDAO tipoEquipoDAO = new TipoEquipoSecuencialDAO();
            List<TipoEquipo> lista = tipoEquipoDAO.buscarTodos();
            for (TipoEquipo tipo : lista)
                tiposEquipo.put(tipo.getCodigo(), tipo);
            logger.info("Tipos de equipo cargados exitosamente.");
        } catch (Exception e) {
            logger.error("Error al cargar tipos de equipo: ", e);
        }
        return tiposEquipo;
    }

    /**
     * Carga los tipos de puerto desde su respectivo DAO.
     *
     * @return Un Hashtable de tipos de puerto.
     */
    public Hashtable<String, TipoPuerto> cargarTiposPuerto() {
        logger.info("Cargando tipos de puerto...");
        Hashtable<String, TipoPuerto> tiposPuerto = new Hashtable<>();
        try {
            TipoPuertoDAO tipoPuertoDAO = new TipoPuertoSecuencialDAO();
            List<TipoPuerto> lista = tipoPuertoDAO.buscarTodos();
            for (TipoPuerto tipo : lista) {
                tiposPuerto.put(tipo.getCodigo(), tipo);
            }
            logger.info("Tipos de puerto cargados exitosamente");
        } catch (Exception e) {
            logger.error("Error al cargar tipos de puerto: ", e);
        }
        return tiposPuerto;
    }

    /**
     * Carga las ubicaciones desde su respectivo DAO.
     *
     * @return Un Hashtable de ubicaciones.
     */
    public Hashtable<String, Ubicacion> cargarUbicaciones() {
        logger.info("Cargando ubicaciones...");
        Hashtable<String, Ubicacion> tablaUbis = new Hashtable<>();
        try {
            UbicacionDAO ubicacionDAO = new UbicacionSecuencialDAO();
            List<Ubicacion> lista = ubicacionDAO.buscarTodos();
            for (Ubicacion ubicacion : lista) {
                tablaUbis.put(ubicacion.getCodigo(), ubicacion);
            }
            logger.info("Ubicaciones cargadas exitosamente");
        } catch (Exception e) {
            logger.error("Error al cargar ubicaciones: ", e);
        }
        return tablaUbis;
    }
}