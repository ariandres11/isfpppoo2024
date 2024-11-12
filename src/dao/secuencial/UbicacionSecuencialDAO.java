package dao.secuencial;

import dao.UbicacionDAO;
import modelo.Ubicacion;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.ResourceBundle;


import org.apache.log4j.Logger;

/**
 * La clase UbicacionSecuencialDAO implementa la interfaz {@link UbicacionDAO}
 * y proporciona la lógica para las operaciones CRUD sobre las ubicaciones
 * utilizando almacenamiento secuencial.
 */
public class UbicacionSecuencialDAO implements UbicacionDAO {
    private List<Ubicacion> listaUbicaciones;
    private String nombre;
    private boolean actualizar;
    private static final Logger logger = Logger.getLogger(UbicacionSecuencialDAO.class);


    /**
     * Constructor de la clase UbicacionSecuencialDAO.
     * Inicializa el nombre del archivo y el estado de actualización.
     */
    public UbicacionSecuencialDAO() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        nombre = rb.getString("ubicaciones");
        actualizar = true;
    }

    /**
     * Lee las ubicaciones desde un archivo.
     *
     * @param file El nombre del archivo a leer.
     * @return Una lista de ubicaciones leídas desde el archivo.
     */
    private List<Ubicacion> readFromFile(String file) {
        List<Ubicacion> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                Ubicacion ubicacion = new Ubicacion();

                ubicacion.setCodigo(inFile.next());
                ubicacion.setDescripcion(inFile.next());

                list.add(ubicacion);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            //System.err.println("Error opening file.");
           // fileNotFoundException.printStackTrace();
            logger.error("Error al abrir archivo: " + file, fileNotFoundException);
        } catch (NoSuchElementException noSuchElementException) {
            //System.err.println("Error in file record structure");
            //noSuchElementException.printStackTrace();
            logger.error("Error en la estructura", noSuchElementException);
        } catch (IllegalStateException illegalStateException) {
            //System.err.println("Error reading from file.");
            //illegalStateException.printStackTrace();
            logger.error("Error al leer archivo: " + file, illegalStateException);
        } finally {
            if (inFile != null)
                inFile.close();
        }
        return list;
    }

    /**
     * Escribe las ubicaciones a un archivo.
     *
     * @param list La lista de ubicaciones a escribir.
     * @param file El nombre del archivo a escribir.
     */
    private void writeToFile(List<Ubicacion> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (Ubicacion ubicacion : list) {
                outFile.format("%s;%s;\n", ubicacion.getCodigo(), ubicacion.getDescripcion());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            //System.err.println("Error creating file.");
            logger.error("Error al crear el archivo");
        } catch (FormatterClosedException formatterClosedException) {
            //System.err.println("Error writing to file.");
            logger.error("Error al escribir en un archivo");
        } finally {
            if (outFile != null)
                outFile.close();
        }
    }

    /**
     * Inserta una nueva ubicación en la lista y actualiza el archivo.
     *
     * @param ubicacion La ubicación a insertar.
     */
    @Override
    public void insertar(Ubicacion ubicacion) {
        listaUbicaciones.add(ubicacion);
        writeToFile(listaUbicaciones, nombre);
        actualizar = true;
        logger.info("Ubicación insertada: " + ubicacion.getCodigo() + " - " + ubicacion.getDescripcion());
    }

    /**
     * Actualiza una ubicación existente en la lista y en el archivo.
     *
     * @param ubicacion La ubicación a actualizar.
     */
    @Override
    public void actualizar(Ubicacion ubicacion) {
        int pos = listaUbicaciones.indexOf(ubicacion);
        listaUbicaciones.set(pos, ubicacion);
        writeToFile(listaUbicaciones, nombre);
        actualizar = true;
        logger.info("Ubicación actualizada: " + ubicacion.getCodigo() + " - " + ubicacion.getDescripcion());
    }

    /**
     * Borra una ubicación de la lista y del archivo.
     *
     * @param ubicacion La ubicación a borrar.
     */
    @Override
    public void borrar(Ubicacion ubicacion) {
        listaUbicaciones.remove(ubicacion);
        writeToFile(listaUbicaciones, nombre);
        actualizar = true;
        logger.info("Ubicación borrada: " + ubicacion.getCodigo() + " - " + ubicacion.getDescripcion());
    }

    /**
     * Busca todas las ubicaciones en la lista y actualiza si es necesario.
     *
     * @return Una lista con todas las ubicaciones.
     */
    @Override
    public List<Ubicacion> buscarTodos() {
        if (actualizar) {
            listaUbicaciones = readFromFile(nombre);
            actualizar = false;
        }
        logger.info("Lista de ubicaciones cargadas: " + listaUbicaciones.size());
        return listaUbicaciones;
    }
}
