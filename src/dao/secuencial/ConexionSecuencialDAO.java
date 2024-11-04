package dao.secuencial;

import dao.*;
import modelo.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * La clase ConexionSecuencialDAO implementa la interfaz {@link ConexionDAO} y proporciona
 * la lógica para las operaciones CRUD sobre las conexiones utilizando almacenamiento secuencial.
 */
public class ConexionSecuencialDAO implements ConexionDAO {
    private List<Conexion> listaConexiones;
    private Hashtable<String, Equipo> tablaEquipos;
    private Hashtable<String, TipoCable> tablaTiposCable;
    private Hashtable<String, TipoPuerto> tablaTiposPuerto;
    private String nombre;
    private boolean actualizar;
    private static final Logger logger = Logger.getLogger(ConexionSecuencialDAO.class);

    /**
     * Constructor de la clase ConexionSecuencialDAO.
     * Inicializa las tablas de tipos de cable, equipos y tipos de puerto.
     */
    public ConexionSecuencialDAO() {
        tablaTiposCable = cargarTiposCable();
        tablaEquipos = cargarEquipos();
        tablaTiposPuerto = cargarTiposPuerto();
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        nombre = rb.getString("conexiones");
        actualizar = true;
    }

    /**
     * Lee las conexiones desde un archivo.
     *
     * @param file El nombre del archivo a leer.
     * @return Una lista de conexiones leídas desde el archivo.
     */
    private List<Conexion> readFromFile(String file) {
        List<Conexion> list = new ArrayList<>();
        Scanner inFile = null;
        logger.info("Intentando leer conexiones desde el archivo: " + file);
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*"); // Asegura el uso del delimitador adecuado

            while (inFile.hasNextLine()) {
                String line = inFile.nextLine();
                if (line.trim().isEmpty()) continue;

                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";\\s*");

                String equipoOrigenCodigo = lineScanner.hasNext() ? lineScanner.next().trim() : "";
                String equipoDestinoCodigo = lineScanner.hasNext() ? lineScanner.next().trim() : "";
                String tipoCableCodigo = lineScanner.hasNext() ? lineScanner.next().trim() : "";
                String tipoPuertoOrigenCodigo = lineScanner.hasNext() ? lineScanner.next().trim() : "";
                String tipoPuertoDestinoCodigo = lineScanner.hasNext() ? lineScanner.next().trim() : "";

                Equipo equipoOrigen = tablaEquipos.get(equipoOrigenCodigo);
                Equipo equipoDestino = tablaEquipos.get(equipoDestinoCodigo);
                TipoCable tipoCable = tablaTiposCable.get(tipoCableCodigo);
                TipoPuerto tipoPuertoOrigen = tablaTiposPuerto.get(tipoPuertoOrigenCodigo);
                TipoPuerto tipoPuertoDestino = tablaTiposPuerto.get(tipoPuertoDestinoCodigo);
                boolean estado = lineScanner.hasNextBoolean() ? lineScanner.nextBoolean() : false;

                Conexion conexion = new Conexion(equipoOrigen, equipoDestino, tipoCable, tipoPuertoOrigen, tipoPuertoDestino, estado);
                list.add(conexion);

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
     * Escribe las conexiones a un archivo.
     *
     * @param list La lista de conexiones a escribir.
     * @param file El nombre del archivo a escribir.
     */
    private void writeToFile(List<Conexion> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (Conexion conexion : list) {
                outFile.format("%s;%s;%s;%s;%s;%s\n",
                        conexion.getEquipo1Codigo(),
                        conexion.getEquipo2Codigo(),
                        conexion.getTipoCableCodigo(),
                        conexion.getTipoPuertoEquipo1().getCodigo(),
                        conexion.getTipoPuertoEquipo2().getCodigo(),
                        conexion.isEstado());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            //System.err.println("Error creating file.");
            logger.error("Error al crear el archivo: " + file, fileNotFoundException);
        } catch (FormatterClosedException formatterClosedException) {
            //System.err.println("Error writing to file.");
            logger.error("Error al escribir el archivo " + file, formatterClosedException);

        } finally {
            if (outFile != null)
                outFile.close();
        }
    }

    /**
     * Inserta una nueva conexión en la lista y actualiza el archivo.
     *
     * @param conexion La conexión a insertar.
     */
    @Override
    public void insertar(Conexion conexion) {
        listaConexiones.add(conexion);
        writeToFile(listaConexiones, nombre);
        actualizar = true;
        logger.info("Conexión insertada: " + conexion);
    }

    /**
     * Borra una conexión de la lista y del archivo.
     *
     * @param conexion La conexión a borrar.
     */
    @Override
    public void borrar(Conexion conexion) {
        listaConexiones.remove(conexion);
        writeToFile(listaConexiones, nombre);
        actualizar = true;
        logger.info("Conexion eliminada : " + conexion);
    }

    /**
     * Busca todas las conexiones en la lista y actualiza si es necesario.
     *
     * @return Una lista con todas las conexiones.
     */
    @Override
    public List<Conexion> buscarTodas() {
        if (actualizar) {
            logger.info("Cargando lista de equipos desde el archivo: " + nombre);
            try {
                listaConexiones = readFromFile(nombre);
                actualizar = false;
                logger.info("Lista de equipos cargada con éxito.");
        } catch (Exception e) {
                logger.error("Error al cargar lista de equipos desde archivo.", e);
            }
            }
        return listaConexiones;
    }

    /**
     * Carga los equipos desde la fuente de datos.
     *
     * @return Una tabla hash con los equipos.
     */
    public Hashtable<String, Equipo> cargarEquipos() {
        Hashtable<String, Equipo> tablaEq = new Hashtable<>();
        EquipoDAO equipoDAO = new EquipoSecuencialDAO();
        List<Equipo> lista = equipoDAO.buscarTodos();
        for (Equipo equipo : lista)
            tablaEq.put(equipo.getCodigo(), equipo);
        logger.info("Equipos cargados: " + lista.size() + "equipos");
        return tablaEq;
    }

    /**
     * Carga los tipos de cable desde la fuente de datos.
     *
     * @return Una tabla hash con los tipos de cable.
     */
    public Hashtable<String, TipoCable> cargarTiposCable() {
        Hashtable<String, TipoCable> tablaTC = new Hashtable<>();
        TipoCableDAO tipoCableDAO = new TipoCableSecuencialDAO();
        List<TipoCable> lista = tipoCableDAO.buscarTodos();
        for (TipoCable tipo : lista)
            tablaTC.put(tipo.getCodigo(), tipo);
        return tablaTC;
    }

    /**
     * Carga los tipos de puerto desde la fuente de datos.
     *
     * @return Una tabla hash con los tipos de puerto.
     */
    private Hashtable<String, TipoPuerto> cargarTiposPuerto() {
        Hashtable<String, TipoPuerto> tiposPuerto = new Hashtable<>();
        TipoPuertoDAO tipoPuertoDAO = new TipoPuertoSecuencialDAO();
        List<TipoPuerto> lista = tipoPuertoDAO.buscarTodos();
        for (TipoPuerto tipo : lista)
            tiposPuerto.put(tipo.getCodigo(), tipo);
        logger.info("Tipo Puerto agregado : ");
        return tiposPuerto;
    }
}
