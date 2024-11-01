package dao.secuencial;

import dao.TipoEquipoDAO;
import modelo.TipoEquipo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * La clase TipoEquipoSecuencialDAO implementa la interfaz {@link TipoEquipoDAO}
 * y proporciona la lógica para las operaciones CRUD sobre los tipos de equipo
 * utilizando almacenamiento secuencial.
 */
public class TipoEquipoSecuencialDAO implements TipoEquipoDAO {
    private List<TipoEquipo> listaTiposEquipo;
    private String nombre;
    private boolean actualizar;
    private static final Logger logger = Logger.getLogger(UbicacionSecuencialDAO.class);

    /**
     * Constructor de la clase TipoEquipoSecuencialDAO.
     * Inicializa el nombre del archivo y el estado de actualización.
     */
    public TipoEquipoSecuencialDAO() {
        ResourceBundle rb = ResourceBundle.getBundle("secuencial");
        nombre = rb.getString("tiposEquipo");
        actualizar = true;
    }

    /**
     * Lee los tipos de equipo desde un archivo.
     *
     * @param file El nombre del archivo a leer.
     * @return Una lista de tipos de equipo leídos desde el archivo.
     */
    private List<TipoEquipo> readFromFile(String file) {
        List<TipoEquipo> list = new ArrayList<>();
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(file));
            inFile.useDelimiter("\\s*;\\s*");
            while (inFile.hasNext()) {
                TipoEquipo tipo = new TipoEquipo();
                tipo.setCodigo(inFile.next());
                tipo.setDescripcion(inFile.next());
                list.add(tipo);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            //System.err.println("Error opening file.");
            //fileNotFoundException.printStackTrace();
            logger.error("Error al abrir el archivo: {}" + file, fileNotFoundException);
        } catch (NoSuchElementException noSuchElementException) {
            //System.err.println("Error in file record structure");
            //noSuchElementException.printStackTrace();
            logger.error("Error in file record structure", noSuchElementException);
        } catch (IllegalStateException illegalStateException) {
            //System.err.println("Error reading from file.");
            //illegalStateException.printStackTrace();
            logger.error("Error reading from file.", illegalStateException);
        } finally {
            if (inFile != null)
                inFile.close();
        }
        return list;
    }

    /**
     * Escribe los tipos de equipo a un archivo.
     *
     * @param list La lista de tipos de equipo a escribir.
     * @param file El nombre del archivo a escribir.
     */
    private void writeToFile(List<TipoEquipo> list, String file) {
        Formatter outFile = null;
        try {
            outFile = new Formatter(file);
            for (TipoEquipo tipo : list) {
                outFile.format("%s;%s;\n", tipo.getCodigo(), tipo.getDescripcion());
            }
        } catch (FileNotFoundException fileNotFoundException) {
            //System.err.println("Error creating file.");
            logger.error("Error al crear el archivo: {}" + file, fileNotFoundException);
        } catch (FormatterClosedException formatterClosedException) {
            //System.err.println("Error writing to file.");
            logger.error("Error al escribir el archivo", formatterClosedException);

        } finally {
            if (outFile != null)
                outFile.close();
        }
    }

    /**
     * Inserta un nuevo tipo de equipo en la lista y actualiza el archivo.
     *
     * @param tipoEquipo El tipo de equipo a insertar.
     */
    @Override
    public void insertar(TipoEquipo tipoEquipo) {
        listaTiposEquipo.add(tipoEquipo);
        writeToFile(listaTiposEquipo, nombre);
        actualizar = true;
        logger.info("Tipo de equipo insertado: " + tipoEquipo);
    }

    /**
     * Actualiza un tipo de equipo existente en la lista y en el archivo.
     *
     * @param tipoEquipo El tipo de equipo a actualizar.
     * la forma hecha en EquipoSecuencialDAO es diferente a esta ya que, antes detectaba cuando un equipo
     * se agregaba correctamente, aca lo que hace es detectar cuando por ejemplo no se encuentra un tipo equipo
     */
    @Override
    public void actualizar(TipoEquipo tipoEquipo) {
        int pos = listaTiposEquipo.indexOf(tipoEquipo);
        listaTiposEquipo.set(pos, tipoEquipo);
        writeToFile(listaTiposEquipo, nombre);
        actualizar = true;
        logger.info("Tipo equipo actualizado: " + tipoEquipo);
    }

    /**
     * Borra un tipo de equipo de la lista y del archivo.
     *
     * @param tipoEquipo El tipo de equipo a borrar.
     */
    @Override
    public void borrar(TipoEquipo tipoEquipo) {
        listaTiposEquipo.remove(tipoEquipo);
        writeToFile(listaTiposEquipo, nombre);
        actualizar = true;
        logger.info("TipoEquipo eliminado correctamente" + tipoEquipo);
    }

    /**
     * Busca todos los tipos de equipo en la lista y actualiza si es necesario.
     *
     * @return Una lista con todos los tipos de equipo.
     */
    @Override
    public List<TipoEquipo> buscarTodos() {
        if (actualizar) {
            listaTiposEquipo = readFromFile(nombre);
            actualizar = false;
        }
        logger.info("Lista de equipos obtenida: " + listaTiposEquipo.size());
        return listaTiposEquipo;
    }
}
