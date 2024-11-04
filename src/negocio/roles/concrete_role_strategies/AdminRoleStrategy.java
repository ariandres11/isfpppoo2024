package negocio.roles.concrete_role_strategies;

import dao.secuencial.UbicacionSecuencialDAO;
import gui.PanelOpcionesSuperior;
import negocio.roles.RoleStrategy;
import org.apache.log4j.Logger;

public class AdminRoleStrategy implements RoleStrategy {
    private static final org.apache.log4j.Logger logger = Logger.getLogger(AdminRoleStrategy.class);

    public void mostrarOpciones(PanelOpcionesSuperior panelOpcionesSuperior) {
        logger.info("Mostrando menú de usuario.");
        panelOpcionesSuperior.activarBotonAdmin();
    }
}
