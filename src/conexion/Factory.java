package conexion;

import java.util.Hashtable;
import java.util.ResourceBundle;

public class Factory {
    private static Hashtable<String, Object> instancias = new Hashtable<>();

    public static Object getInstancia(String objectName){
        try {
            Object obj = instancias.get(objectName);

            if(obj == null){
                ResourceBundle rb = ResourceBundle.getBundle("factory");
                String sClassName = rb.getString(objectName);
                obj = Class.forName(sClassName).newInstance();

                //Agregar la nueva instancia a la tabla
                instancias.put(objectName, obj);
            }
            return obj;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
