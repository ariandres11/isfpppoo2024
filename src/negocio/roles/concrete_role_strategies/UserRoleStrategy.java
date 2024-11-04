package negocio.roles.concrete_role_strategies;

import gui.PanelOpcionesSuperior;
import negocio.roles.RoleStrategy;
import org.apache.log4j.Logger;

public class UserRoleStrategy implements RoleStrategy {
    private static final org.apache.log4j.Logger logger = Logger.getLogger(UserRoleStrategy.class);

    @Override
    public void mostrarOpciones(PanelOpcionesSuperior panelOpcionesSuperior) {
        logger.info("Mostrando menú de admin.");
        panelOpcionesSuperior.desactivarBotonAdmin();
    }
}
