package dao.secuencial;

import dao.TipoCableDAO;
import modelo.TipoCable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * La clase TipoCableSecuencialDAO implementa la interfaz {@link TipoCableDAO}
 * y proporciona la lógica para las operaciones CRUD sobre los tipos de cable
 * utilizando almacenamiento secuencial.
 */
public class TipoCableSecuencialDAO implements TipoCableDAO {
    private List<TipoCable> listaTiposCable;
    private String nombre;
    private boolean actualizar;
    private static final Logger logger = Logger.getLogger(UbicacionSecuencialDAO.class);


    /**
     * Constructor de la clase TipoCableSecuencialDAO.
     * Inicializa el nombre del archivo y el estado de actualización.
     */
    public TipoCableSecuencialDAO() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        nombre = rb.getString("tiposCable");
        actualizar = true;
    }

    /**
     * Lee los tipos de cable desde un archivo.
     *
     * @param file El nombre del archivo a leer.
     * @return Una lista de tipos de cable leídos desde el archivo.
     */
    public List<TipoCable> readFromFile(String file) {
        List<TipoCable> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                TipoCable tipo = new TipoCable();
                tipo.setCodigo(inFile.next());
                tipo.setDescripcion(inFile.next());
                tipo.setVelocidad(inFile.nextInt());
                list.add(tipo);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            //System.err.println("Error opening file.");
            //fileNotFoundException.printStackTrace();
            logger.error("Error al abrir el archivo: {}", fileNotFoundException);
        } catch (NoSuchElementException noSuchElementException) {
            //System.err.println("Error in file record structure");
            //noSuchElementException.printStackTrace();
            logger.error("Error en la estructura del archivo", noSuchElementException);
        } catch (IllegalStateException illegalStateException) {
            //System.err.println("Error reading from file.");
            //illegalStateException.printStackTrace();
            logger.error("Error al leer el archivo", illegalStateException);
        } finally {
            if (inFile != null)
                inFile.close();
        }
        return list;
    }

    /**
     * Escribe los tipos de cable a un archivo.
     *
     * @param list La lista de tipos de cable a escribir.
     * @param file El nombre del archivo a escribir.
     */
    public void writeToFile(List<TipoCable> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (TipoCable tipo : list) {
                outFile.format("%s;%s;%s;\n", tipo.getCodigo(), tipo.getDescripcion(), tipo.getVelocidad());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            //System.err.println("Error creating file.");
            logger.error("Error al crear el archivo: ", fileNotFoundException);
        } catch (FormatterClosedException formatterClosedException) {
            System.err.println("Error writing to file.");
            logger.error("Error writing to file.", formatterClosedException);
        } finally {
            if (outFile != null)
                outFile.close();
        }
    }

    /**
     * Inserta un nuevo tipo de cable en la lista y actualiza el archivo.
     *
     * @param tipoCable El tipo de cable a insertar.
     */
    @Override
    public void insertar(TipoCable tipoCable) {
        listaTiposCable.add(tipoCable);
        writeToFile(listaTiposCable, nombre);
        actualizar = true;
        logger.info("TipoCable insertado: "+ tipoCable.getCodigo());

    }

    /**
     * Borra un tipo de cable de la lista y del archivo.
     *
     * @param tipoCable El tipo de cable a borrar.
     */
    @Override
    public void borrar(TipoCable tipoCable) {
        listaTiposCable.remove(tipoCable);
        writeToFile(listaTiposCable, nombre);
        actualizar = true;
        logger.info("TipoCable eliminado: " + tipoCable.getCodigo());

    }

    /**
     * Busca todos los tipos de cable en la lista y actualiza si es necesario.
     *
     * @return Una lista con todos los tipos de cable.
     */
    @Override
    public List<TipoCable> buscarTodos() {
        if (actualizar) {
            listaTiposCable = readFromFile(nombre);
            actualizar = false;
        }
        return listaTiposCable;
    }

    /**
     * Carga los tipos de cable desde la fuente de datos.
     *
     * @return Una tabla hash con los tipos de cable.
     */
    private Hashtable<String, TipoCable> cargarTiposCable() {
        Hashtable<String, TipoCable> tiposCable = new Hashtable<>();
        TipoCableDAO tipoCableDAO = new TipoCableSecuencialDAO();
        List<TipoCable> lista = tipoCableDAO.buscarTodos();
        for (TipoCable tipo : lista)
            tiposCable.put(tipo.getCodigo(), tipo);
        logger.info("Tipo de cable cargado en el HashTable.");
        return tiposCable;
    }
}
