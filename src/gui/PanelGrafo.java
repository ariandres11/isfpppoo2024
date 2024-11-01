package gui;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import modelo.Conexion;
import modelo.Equipo;

import javax.swing.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class PanelGrafo extends JPanel {
    private List<Conexion> listaConexiones;
    private List<Equipo> listaEquipos;
    private ResourceBundle resourceBundle;

    public PanelGrafo(List<Conexion> listaConexiones, List<Equipo> listaEquipos) {
        this.listaConexiones = listaConexiones;
        this.listaEquipos = listaEquipos;
        this.resourceBundle = ResourceBundle.getBundle("iconosTipoEquipo");
    }

    private String getVertexStyle(mxGraph mxGraph, Equipo equipo) {
        String tipoEquipoCodigo = equipo.getTipoEquipo().getCodigo();
        String imagePath = resourceBundle.getString(tipoEquipoCodigo);
        String colorBorde = equipo.isEstado() ? "green" : "red";
        String estado = equipo.isEstado() ? "ACTIVO" : "INACTIVO";
        Map<String, Object> style = new Hashtable<>();

        if (imagePath != null) {
            style.put("shape", "image");
            style.put("image", Objects.requireNonNull(getClass().getClassLoader().getResource(imagePath)).toString());
        } else {
            style.put("shape", "ellipse");
            style.put("fillColor", "#C3D9FF");
        }

        style.put("fontSize", 16);
        style.put("fontColor", colorBorde);
        style.put("fontStyle", 1);  // Negrita
        style.put("verticalLabelPosition", "top");  // Posicionar el texto arriba del vértice
        style.put("verticalAlign", "bottom");      // Alinear el texto abajo

        String styleName = tipoEquipoCodigo + "_" + estado + "_STYLE";
        mxGraph.getStylesheet().putCellStyle(styleName, style);
        return styleName;
    }

    public JPanel getPanelGrafo() {
        if ((listaConexiones != null && !listaConexiones.isEmpty()) || (listaEquipos != null && !listaEquipos.isEmpty())) {
            mxGraph mxGrafo = new mxGraph();
            Object parent = mxGrafo.getDefaultParent();
            HashMap<String, Object> vertexMap = new HashMap<>();

            mxGrafo.getModel().beginUpdate();
            try {
                // Primero, agregar todos los equipos como vértices sueltos
                for (Equipo equipo : listaEquipos) {
                    String id = equipo.getCodigo();
                    String vertexStyle = getVertexStyle(mxGrafo, equipo);
                    if (!vertexMap.containsKey(id)) {
                        Object vertex = mxGrafo.insertVertex(parent, id, id, 100, 100, 80, 80, vertexStyle);
                        vertexMap.put(id, vertex);
                    }
                }

                // Luego, agregar las conexiones entre los equipos
                for (Conexion conexion : listaConexiones) {
                    String id1 = conexion.getEquipo1().getCodigo();
                    String id2 = conexion.getEquipo2().getCodigo();

                    Object equipo1 = vertexMap.get(id1);
                    if (equipo1 == null) {
                        String vertexStyle = getVertexStyle(mxGrafo, conexion.getEquipo1());
                        equipo1 = mxGrafo.insertVertex(parent, id1, id1, 100, 100, 80, 80, vertexStyle);
                        vertexMap.put(id1, equipo1);
                    }

                    Object equipo2 = vertexMap.get(id2);
                    if (equipo2 == null) {
                        String vertexStyle = getVertexStyle(mxGrafo, conexion.getEquipo2());
                        equipo2 = mxGrafo.insertVertex(parent, id2, id2, 100, 100, 80, 80, vertexStyle);
                        vertexMap.put(id2, equipo2);
                    }

                    // Crear conexiones entre los vértices sin puntas de flecha
                    Object eq1 = vertexMap.get(conexion.getEquipo1().getCodigo());
                    Object eq2 = vertexMap.get(conexion.getEquipo2().getCodigo());
                    String style = "edgeStyle=orthogonalEdgeStyle;rounded=1;strokeColor=black;strokeWidth=2;fontSize=20;fontColor=black;backgroundColor=white;";
                    mxGrafo.insertEdge(parent, null, conexion.getTipoCable().getVelocidad(), eq1, eq2, style);
                }
            } finally {
                mxGrafo.getModel().endUpdate();
            }

            mxGrafo.setAllowDanglingEdges(false);
            mxGrafo.setAllowLoops(false);
            mxGrafo.setMultigraph(false);

            JPanel panelNuevo = new JPanel();
            mxGraphComponent graphComponent = new mxGraphComponent(mxGrafo);
            panelNuevo.add(graphComponent);
            panelNuevo.setVisible(true);
            panelNuevo.setSize(500, 500);

            mxCircleLayout layout = new mxCircleLayout(mxGrafo);
            layout.execute(parent);
            graphComponent.zoomAndCenter();

            return panelNuevo;
        } else {
            // Si no hay equipos ni conexiones, lanzar una advertencia y retornar null
            System.out.println("Advertencia: No hay equipos ni conexiones para mostrar.");
            return null;
        }
    }
}
