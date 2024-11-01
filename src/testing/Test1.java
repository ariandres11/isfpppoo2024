package testing;

import dao.secuencial.TipoCableSecuencialDAO;
import modelo.TipoCable;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test1 {

    /*los métodos write y read from file de TipoCable
    Momentaneamente se utilizan como públicos UNICAMENTE
    Para este test.*/
    @Test
    public void testWriteAndRead() {
        // Crear un objeto TipoCable
        TipoCable tipoCable = new TipoCable();
        tipoCable.setCodigo("C5");
        tipoCable.setDescripcion("UTP Cat.5");
        tipoCable.setVelocidad(100);

        // Crear una lista de TipoCable
        List<TipoCable> listaTiposCable = new ArrayList<>();
        listaTiposCable.add(tipoCable);

        // Escribir la lista en un archivo
        String archivo = "tipos_cable.txt";
        TipoCableSecuencialDAO dao = new TipoCableSecuencialDAO();
        dao.writeToFile(listaTiposCable, archivo);

        // Leer el archivo
        List<TipoCable> listaLeida = dao.readFromFile(archivo);

        // Verificar que se haya leído correctamente
        assertEquals(1, listaLeida.size());
        TipoCable tipoCableLeido = listaLeida.get(0);
        assertEquals("C5", tipoCableLeido.getCodigo());
        assertEquals("UTP Cat.5", tipoCableLeido.getDescripcion());
        assertEquals(100, tipoCableLeido.getVelocidad());
    }

    @Test
    public void testWriteToFileListaConVariosElementos() {
        // Crear una lista con varios elementos de TipoCable
        List<TipoCable> listaTiposCable = new ArrayList<>();
        TipoCable tipoCable1 = new TipoCable();
        tipoCable1.setCodigo("C5");
        tipoCable1.setDescripcion("UTP Cat.5");
        tipoCable1.setVelocidad(100);
        listaTiposCable.add(tipoCable1);
        TipoCable tipoCable2 = new TipoCable();
        tipoCable2.setCodigo("C6");
        tipoCable2.setDescripcion("UTP Cat.6");
        tipoCable2.setVelocidad(200);
        listaTiposCable.add(tipoCable2);

        // Escribir la lista en un archivo
        String archivo = "tipos_cable.txt";
        TipoCableSecuencialDAO dao = new TipoCableSecuencialDAO();
        dao.writeToFile(listaTiposCable, archivo);

        // Verificar que el archivo se haya creado
        File file = new File(archivo);
        assertTrue(file.exists());

        // Verificar que el contenido del archivo sea correcto
        List<TipoCable> listaLeida = dao.readFromFile(archivo);
        assertEquals(2, listaLeida.size());
        TipoCable tipoCableLeido1 = listaLeida.get(0);
        assertEquals("C5", tipoCableLeido1.getCodigo());
        assertEquals("UTP Cat.5", tipoCableLeido1.getDescripcion());
        assertEquals(100, tipoCableLeido1.getVelocidad());
        TipoCable tipoCableLeido2 = listaLeida.get(1);
        assertEquals("C6", tipoCableLeido2.getCodigo());
        assertEquals("UTP Cat.6", tipoCableLeido2.getDescripcion());
        assertEquals(200, tipoCableLeido2.getVelocidad());
    }

}
