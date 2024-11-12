package dao.secuencial;

import dao.TipoPuertoDAO;
import modelo.TipoPuerto;

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
 * La clase TipoPuertoSecuencialDAO implementa la interfaz {@link TipoPuertoDAO}
 * y proporciona la lógica para las operaciones CRUD sobre los tipos de puerto
 * utilizando almacenamiento secuencial.
 */
public class TipoPuertoSecuencialDAO implements TipoPuertoDAO {
    private List<TipoPuerto> listaTiposPuerto;
    private String nombre;
    private boolean actualizar;
    private static final Logger logger = Logger.getLogger(TipoPuertoSecuencialDAO.class);


    /**
     * Constructor de la clase TipoPuertoSecuencialDAO.
     * Inicializa el nombre del archivo y el estado de actualización.
     */
    public TipoPuertoSecuencialDAO() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        nombre = rb.getString("tiposPuerto");
        actualizar = true;
    }

    /**
     * Lee los tipos de puerto desde un archivo.
     *
     * @param file El nombre del archivo a leer.
     * @return Una lista de tipos de puerto leídos desde el archivo.
     */
    private List<TipoPuerto> readFromFile(String file) {
        List<TipoPuerto> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                TipoPuerto tipo = new TipoPuerto();

                tipo.setCodigo(inFile.next());
                tipo.setDescripcion(inFile.next());
                tipo.setVelocidad(inFile.nextInt());

                list.add(tipo);
            }
            logger.info("Tipos de puerto leídos exitosamente desde el archivo: ");
        } catch (FileNotFoundException fileNotFoundException) {
            //System.err.println("Error opening file.");
            //fileNotFoundException.printStackTrace();
            logger.error("Error al abrir el archivo: " + file, fileNotFoundException);
        } catch (NoSuchElementException noSuchElementException) {
            //System.err.println("Error in file record structure");
            //noSuchElementException.printStackTrace();
            logger.error("Error en la estructura del registro del archivo: "+ file, noSuchElementException);
        } catch (IllegalStateException illegalStateException) {
            //System.err.println("Error reading from file.");
            //illegalStateException.printStackTrace();
            logger.error("Error al leer desde el archivo: " + file, illegalStateException);
        } finally {
            if (inFile != null)
                inFile.close();
        }
        return list;
    }

    /**
     * Escribe los tipos de puerto a un archivo.
     *
     * @param list La lista de tipos de puerto a escribir.
     * @param file El nombre del archivo a escribir.
     */
    private void writeToFile(List<TipoPuerto> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (TipoPuerto tipo : list) {
                outFile.format("%s;%s;%s;\n", tipo.getCodigo(), tipo.getDescripcion(), tipo.getVelocidad());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            //System.err.println("Error creating file.");
            logger.error("Error al crear el archivo: " + file, fileNotFoundException);
        } catch (FormatterClosedException formatterClosedException) {
            //System.err.println("Error writing to file.");
            logger.error("Error al escribir en el archivo: " + file, formatterClosedException);
        } finally {
            if (outFile != null)
                outFile.close();
        }
    }

    /**
     * Inserta un nuevo tipo de puerto en la lista y actualiza el archivo.
     *
     * @param tipoPuerto El tipo de puerto a insertar.
     */
    @Override
    public void insertar(TipoPuerto tipoPuerto) {
        listaTiposPuerto.add(tipoPuerto);
        writeToFile(listaTiposPuerto, nombre);
        actualizar = true;
        logger.info("Tipo de puerto insertado: {}" + tipoPuerto.getCodigo());
    }

    /**
     * Borra un tipo de puerto de la lista y del archivo.
     *
     * @param tipoPuerto El tipo de puerto a borrar.
     */
    @Override
    public void borrar(TipoPuerto tipoPuerto) {
        listaTiposPuerto.remove(tipoPuerto);
        writeToFile(listaTiposPuerto, nombre);
        actualizar = true;
        logger.info("Tipo Puerto eliminado: " + tipoPuerto.getCodigo());
    }

    /**
     * Busca todos los tipos de puerto en la lista y actualiza si es necesario.
     *
     * @return Una lista con todos los tipos de puerto.
     */
    @Override
    public List<TipoPuerto> buscarTodos() {
        if (actualizar) {
            listaTiposPuerto = readFromFile(nombre);
            actualizar = false;
        }
        logger.info("Número de tipos de puerto obtenidos: " + listaTiposPuerto.size());
        return listaTiposPuerto;
    }
}
