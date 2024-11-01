package gui;

import controlador.Coordinador;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import negocio.roles.RoleContext;
import negocio.roles.concrete_role_strategies.AdminRoleStrategy;
import negocio.roles.concrete_role_strategies.UserRoleStrategy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelOpcionesSuperior extends JPanel{

    private Interfaz interfaz;
    private Coordinador coordinador;
    private RoleContext roleContext;
    private Logger logger = Logger.getLogger(PanelOpcionesSuperior.class);

    JButton JBCambiarRol = new JButton("Cambiar rol");
    JButton JBDatosRed = new JButton("Administrar datos de Red");

    public PanelOpcionesSuperior(Interfaz interfaz, RoleContext roleContext) {
        this.interfaz = interfaz;
        this.coordinador = interfaz.getCoordinador();
        this.roleContext = roleContext;

        JBCambiarRol.addActionListener(new ManejadorBotonesSuperiores());
        JBDatosRed.addActionListener(new ManejadorBotonesSuperiores());

        this.add(JBCambiarRol);
        this.add(JBDatosRed);
        JBDatosRed.setVisible(false);

        //roleContext.mostrarOpciones(this);
        this.revalidate();
        this.repaint();
    }

    public void activarBotonAdmin() {
        this.JBDatosRed.setVisible(true);
        this.JBDatosRed.setEnabled(true);
        revalidate();
        repaint();
    }

    public void desactivarBotonAdmin() {
        this.JBDatosRed.setVisible(false);
        this.JBDatosRed.setEnabled(false);
        revalidate();
        repaint();
    }

    /**
     * Clase interna que maneja los eventos de acción de los botones del menú.
     */
    private class ManejadorBotonesSuperiores implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == JBDatosRed) {
                FrameDatosRed datosRed = new FrameDatosRed(coordinador);
            }
            if (e.getSource() == JBCambiarRol) {
                String[] opciones = {"admin", "user"};
                String rolSeleccionado = (String) JOptionPane.showInputDialog(
                        null,
                        "Selecciona el rol:",
                        "Cambio de Rol",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );
                if ("admin".equals(rolSeleccionado)) {
                    coordinador.setRoleStrategy(new AdminRoleStrategy());
                    logger.setLevel(Level.DEBUG);
                    roleContext.mostrarOpciones(PanelOpcionesSuperior.this);
                } else {
                    coordinador.setRoleStrategy(new UserRoleStrategy());
                    logger.setLevel(Level.INFO);
                    roleContext.mostrarOpciones(PanelOpcionesSuperior.this);
                }
            }
        }
    }

}
