package negocio.roles;

import gui.PanelOpcionesSuperior;

public class RoleContext {
    private RoleStrategy strategy;

    public void setRoleStrategy(RoleStrategy strategy) {
        this.strategy = strategy;
    }

    public void mostrarOpciones(PanelOpcionesSuperior panelOpcionesSuperior) {
        strategy.mostrarOpciones(panelOpcionesSuperior);
    }

}
