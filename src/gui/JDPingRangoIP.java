package gui;

import controlador.Coordinador;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.util.Random;

import static controlador.Constantes.FUENTE_CONSULTAS;

public class JDPingRangoIP extends JDialog {

    private Interfaz interfaz;
    private Coordinador coordinador;
    private Thread imprimirIPs;
    private Thread buscandoIPs;
    private Random random = new Random();
    private final long MAX_TIEMPO_PING = 500;
    
    public JDPingRangoIP (Interfaz interfaz, Coordinador coordinador) {
        this.interfaz = interfaz;
        this.coordinador = coordinador;
        inicializar();
        setTitle("Introduzca las direcciones IP");
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(600, 400));
        setResizable(false);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);
    }

    public void inicializar() {
        // Crear campos de texto para la primera IP
        JTextField digitoRed1 = new JTextField(3);
        JTextField digitoRed2 = new JTextField(3);
        JTextField digitoHost1 = new JTextField(3);
        JTextField digitoHost2 = new JTextField(3);

        // Crear campos de texto para la segunda IP
        JTextField digitoHost3 = new JTextField(3);
        JTextField digitoHost4 = new JTextField(3);

        // Aumentar el tamaño de fuente
        digitoRed1.setFont(FUENTE_CONSULTAS);
        digitoRed2.setFont(FUENTE_CONSULTAS);
        digitoHost1.setFont(FUENTE_CONSULTAS);
        digitoHost2.setFont(FUENTE_CONSULTAS);
        digitoHost3.setFont(FUENTE_CONSULTAS);
        digitoHost4.setFont(FUENTE_CONSULTAS);

        // Configurar el panel con un diseño de cuadrícula
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Configurar el título y los campos de texto para la primera IP
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel tituloInicio = new JLabel("Dirección IP de inicio:");
        tituloInicio.setFont(FUENTE_CONSULTAS);
        panel.add(tituloInicio, constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 1;
        JLabel labelRed1 = new JLabel("Red 1:");
        labelRed1.setFont(FUENTE_CONSULTAS);
        panel.add(labelRed1, constraints);
        constraints.gridx = 1;
        panel.add(digitoRed1, constraints);
        constraints.gridx = 2;
        JLabel labelRed2 = new JLabel("Red 2:");
        labelRed2.setFont(FUENTE_CONSULTAS);
        panel.add(labelRed2, constraints);
        constraints.gridx = 3;
        panel.add(digitoRed2, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        JLabel labelHost1 = new JLabel("Host 1:");
        labelHost1.setFont(FUENTE_CONSULTAS);
        panel.add(labelHost1, constraints);
        constraints.gridx = 1;
        panel.add(digitoHost1, constraints);
        constraints.gridx = 2;
        JLabel labelHost2 = new JLabel("Host 2:");
        labelHost2.setFont(FUENTE_CONSULTAS);
        panel.add(labelHost2, constraints);
        constraints.gridx = 3;
        panel.add(digitoHost2, constraints);

        // Configurar el título y los campos de texto para la segunda IP
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        JLabel tituloFin = new JLabel("Dirección IP de fin:");
        tituloFin.setFont(FUENTE_CONSULTAS);
        panel.add(tituloFin, constraints);

        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        JLabel labelHost3 = new JLabel("Host 3:");
        labelHost3.setFont(FUENTE_CONSULTAS);
        panel.add(labelHost3, constraints);
        constraints.gridx = 1;
        panel.add(digitoHost3, constraints);
        constraints.gridx = 2;
        JLabel labelHost4 = new JLabel("Host 4:");
        labelHost4.setFont(FUENTE_CONSULTAS);
        panel.add(labelHost4, constraints);
        constraints.gridx = 3;
        panel.add(digitoHost4, constraints);

        JButton JBAceptar = new JButton("Ping");
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        panel.add(JBAceptar, constraints);
        panel.setBounds(0,0,300,200);

        JButton JBCancelar = new JButton("Cancelar");
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.gridx = 2;
        JBCancelar.setEnabled(false);
        panel.add(JBCancelar, constraints);

        JLabel JLBuscandoIPs = new JLabel();
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        panel.add(JLBuscandoIPs, constraints);

        JTextArea textoIPs = new JTextArea();
        textoIPs.setLineWrap(true);
        textoIPs.setWrapStyleWord(true);
        textoIPs.setEditable(false);
        JScrollPane panelScroll = new JScrollPane(textoIPs);
        panelScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelScroll.setBounds(320,20,250,320);

        JBAceptar.addActionListener(e -> {
            // Obtener y validar los valores ingresados
            try {

                digitoRed1.setEnabled(false);
                digitoRed2.setEnabled(false);

                digitoHost1.setEnabled(false);
                digitoHost2.setEnabled(false);
                digitoHost3.setEnabled(false);
                digitoHost4.setEnabled(false);

                int red1 = Integer.parseInt(digitoRed1.getText());
                int red2 = Integer.parseInt(digitoRed2.getText());
                int host1 = Integer.parseInt(digitoHost1.getText());
                int host2 = Integer.parseInt(digitoHost2.getText());
                int host3 = Integer.parseInt(digitoHost3.getText());
                int host4 = Integer.parseInt(digitoHost4.getText());

                // Validar los segmentos de IP (0-255)
                if (red1 < 0 || red1 > 255 || red2 < 0 || red2 > 255 ||
                        host1 < 0 || host1 > 255 || host2 < 0 || host2 > 255 ||
                        host3 < 0 || host3 > 255 || host4 < 0 || host4 > 255) {
                    throw new NumberFormatException();
                }

                long startIp = coordinador.IpALong(red1, red2, host1, host2);
                long endIp = coordinador.IpALong(red1, red2, host3, host4);

                imprimirIPs = new Thread(() -> {
                    buscandoIPs.start();

                    textoIPs.setText("");
                    JBCancelar.setEnabled(true);

                    try {

                        long ipContador = startIp;

                        if (coordinador.getModo() == "Prod") {
                            // produccion
                            //verifico el ping para cada ip del rango
                            while (!Thread.currentThread().isInterrupted() && ipContador <= endIp) {
                                String ipDir = coordinador.longAIp(ipContador);
                                boolean resultadoPing = coordinador.pingProduccion(ipDir);
                                textoIPs.append(ipDir + ": " + (resultadoPing ? "Activo" : "Inactivo") + "\n");
                                ipContador++;
                            }
                        } else {
                            // simulacion
                            for (long ip = startIp; ip <= endIp; ip++) {
                                Thread.sleep(random.nextLong(MAX_TIEMPO_PING) + 100);
                                String ipDir = coordinador.longAIp(ip);
                                boolean resultadoPing = coordinador.pingSimulacion(ipDir);
                                textoIPs.append(ipDir + ": " + (resultadoPing ? "Activo" : "Inactivo") + "\n");
                            }
                        }

                        if(Thread.currentThread().isInterrupted())
                            throw new InterruptedException();

                        textoIPs.append("Ping a rango de IPs terminado.");
                        JBCancelar.setEnabled(false);

                    } catch (InterruptedException iex) {
                        textoIPs.append("Ping a rango de IPs cancelado.");
                    }

                    digitoRed1.setEnabled(true);
                    digitoRed2.setEnabled(true);
                    digitoHost1.setEnabled(true);
                    digitoHost2.setEnabled(true);
                    digitoHost3.setEnabled(true);
                    digitoHost4.setEnabled(true);
                });

                buscandoIPs = new Thread(() -> {

                    String labelBuscando = "Buscando IPs...";
                    int i = 0;
                    for (;;) {
                        try {
                            if (i < labelBuscando.length()) {
                                Thread.sleep(200);
                                JLBuscandoIPs.setText(JLBuscandoIPs.getText() + labelBuscando.charAt(i));
                                i++;
                            } else {
                                Thread.sleep(1000);
                                JLBuscandoIPs.setText("");
                                i = 0;
                            }
                        } catch (InterruptedException iex) {
                            break;
                        }
                    }
                    JLBuscandoIPs.setText("");
                });

                imprimirIPs.start();

            } catch (NumberFormatException NFE) {
                JOptionPane.showMessageDialog(null, "Por favor, introduzca valores válidos para las direcciones IP.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JBCancelar.addActionListener(e -> {
            imprimirIPs.interrupt();
            buscandoIPs.interrupt();
            digitoRed1.setEnabled(true);
            digitoRed2.setEnabled(true);
            digitoHost1.setEnabled(true);
            digitoHost2.setEnabled(true);
            digitoHost3.setEnabled(true);
            digitoHost4.setEnabled(true);

            JBCancelar.setEnabled(false);
        });

        add(panel);
        add(panelScroll);
    }

}
