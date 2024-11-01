package negocio.roles.concrete_role_strategies;

import gui.PanelOpcionesSuperior;
import negocio.roles.RoleStrategy;

public class AdminRoleStrategy implements RoleStrategy {
    public void mostrarOpciones(PanelOpcionesSuperior panelOpcionesSuperior) {
        System.out.println("Mostrando menu de usuario.");
        panelOpcionesSuperior.activarBotonAdmin();
    }
}
