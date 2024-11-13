package negocio;

import controlador.Coordinador;
import modelo.Conexion;
import modelo.Equipo;
import modelo.TipoPuerto;
import org.apache.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;

import java.net.InetAddress;

import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * La clase Calculo gestiona el grafo no dirigido que representa las conexiones entre equipos.
 */
public class Calculo {
    // Elementos del grafo
    private DefaultUndirectedWeightedGraph<Equipo, Conexion> grafoNoDirigido;
    private Coordinador coordinador;
    private static final Logger logger = Logger.getLogger(Calculo.class);


    /**
     * Constructor de la clase Calculo.
     * Inicializa el grafo no dirigido.
     */
    public Calculo() {
        grafoNoDirigido = new DefaultUndirectedWeightedGraph<>(Conexion.class);
    }

    /**
     * Obtiene el grafo no dirigido.
     *
     * @return el grafo no dirigido.
     */
    public Graph<Equipo, Conexion> getGrafoNoDirigido() {
        return grafoNoDirigido;
    }

    /**
     * Obtiene el coordinador.
     *
     * @return el coordinador.
     */
    public Coordinador getCoordinador() {
        return coordinador;
    }

    /**
     * Establece el coordinador.
     *
     * @param coordinador el nuevo coordinador.
     */
    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    /**
     * Carga los datos de las conexiones y equipos en el grafo.
     * Limpia el grafo antes de cargar nuevos datos.
     *
     * @param conexiones lista de conexiones a cargar.
     * @param equipos    lista de equipos a cargar.
     */
    public void cargarDatos(List<Conexion> conexiones, List<Equipo> equipos) {
        // Limpiar el grafo antes de cargar nuevos datos
        grafoNoDirigido = new DefaultUndirectedWeightedGraph<>(Conexion.class);
        TreeMap<String, Equipo> mapaEquipos = new TreeMap<>();

        // Cargar los equipos
        for (Equipo equipo : equipos) {
            mapaEquipos.put(equipo.getCodigo(), equipo);
        }
        logger.info("Equipos cargados: " + mapaEquipos.toString());

        // Cargar conexiones
        for (Conexion conexion : conexiones) {
            Equipo equipo1 = mapaEquipos.get(conexion.getEquipo1().getCodigo());
            Equipo equipo2 = mapaEquipos.get(conexion.getEquipo2().getCodigo());
            if (equipo1 != null && equipo2 != null) {
                // Cargar el grafo no dirigido
                grafoNoDirigido.addVertex(equipo1);
                grafoNoDirigido.addVertex(equipo2);
                if (grafoNoDirigido.containsVertex(equipo1) && grafoNoDirigido.containsVertex(equipo2)) {
                    grafoNoDirigido.addEdge(equipo1, equipo2, conexion);
                    // Calcular la velocidad máxima y asignarla como peso
                    double velMaxima = calcularVelMaxima(conexion, conexion.getTipoPuertoEquipo1(), conexion.getTipoPuertoEquipo2());
                    if (grafoNoDirigido.containsEdge(conexion)) {
                        grafoNoDirigido.setEdgeWeight(conexion, velMaxima);
                    }
                }
            } else {
                logger.debug("El equipo 1, el equipo 2, o ambos son nulos ");
            }
        }
        //Agregar equipos aislados
        for (Equipo equipo : equipos) {
            if(!grafoNoDirigido.containsVertex(equipo)){
                grafoNoDirigido.addVertex(mapaEquipos.get(equipo.getCodigo()));
            }
        }
    }

    /**
     * Calcula la velocidad máxima de una conexión en base a los tipos de puertos y la velocidad del cable.
     *
     * @param conexion      La conexión a evaluar.
     * @param tipoPuertoEq1 El tipo de puerto del equipo 1.
     * @param tipoPuertoEq2 El tipo de puerto del equipo 2.
     * @return La velocidad máxima de la conexión.
     */
    public double calcularVelMaxima(Conexion conexion, TipoPuerto tipoPuertoEq1, TipoPuerto tipoPuertoEq2) {
        double minimo = Math.min(Math.min(conexion.getTipoCable().getVelocidad(), tipoPuertoEq1.getVelocidad()), tipoPuertoEq2.getVelocidad());
        return 1 / minimo;
    }

    /**
     * Encuentra el camino más rápido entre dos equipos usando el algoritmo de Dijkstra.
     *
     * @param equipo1 El equipo de origen.
     * @param equipo2 El equipo de destino.
     * @return Una lista de conexiones que representan el camino más rápido.
     */
    public List<Conexion> masRapido(Equipo equipo1, Equipo equipo2) {
        cargarDatos(coordinador.listarConexiones(), coordinador.listarEquipos());
        // Crear una copia del grafo
        DefaultUndirectedWeightedGraph<Equipo, Conexion> rapido = new DefaultUndirectedWeightedGraph<>(Conexion.class);
        Map<String, Equipo> res = new HashMap<>();

        // Copiar los vértices
        for (Equipo result : grafoNoDirigido.vertexSet()) {
            res.put(result.getCodigo(), result);  // Mapea el código del equipo con el objeto equipo
            rapido.addVertex(result);             // Agrega el equipo al grafo copiado rapido
        }

        boolean equipo1TieneConexiones = false;
        boolean equipo2TieneConexiones = false;

        // Copiar las aristas con sus pesos en ambos sentidos
        for (Conexion result : grafoNoDirigido.edgeSet()) {
            Equipo source = grafoNoDirigido.getEdgeSource(result);
            Equipo target = grafoNoDirigido.getEdgeTarget(result);
            double velocidadInvertida = (double) 1 / result.getTipoCable().getVelocidad();

            if (source != null && target != null) {
                Conexion edge = rapido.addEdge(res.get(source.getCodigo()), res.get(target.getCodigo()));
                if (edge != null) {
                    edge.setEquipo1(source);
                    edge.setEquipo2(target);
                    edge.setTipoCable(result.getTipoCable());
                    edge.setTipoPuertoEquipo1(source.getTipoPuertoInicial());
                    edge.setTipoPuertoEquipo2(target.getTipoPuertoInicial());
                    rapido.setEdgeWeight(edge, velocidadInvertida);

                    if (source.equals(equipo1) || target.equals(equipo1)) {
                        equipo1TieneConexiones = true;
                    }
                    if (source.equals(equipo2) || target.equals(equipo2)) {
                        equipo2TieneConexiones = true;
                    }
                }
            }
        }

        // Encontrar el camino más corto con el algoritmo de Dijkstra
        DijkstraShortestPath<Equipo, Conexion> dijkstraAlg = new DijkstraShortestPath<>(rapido);
        GraphPath<Equipo, Conexion> path = dijkstraAlg.getPath(equipo1, equipo2);

        List<Conexion> caminoMasRapido = new ArrayList<>();
        if (path != null) {
            caminoMasRapido.addAll(path.getEdgeList());
        } else {
            coordinador.advertencia("No hay camino entre " + equipo1.getCodigo() + " y " + equipo2.getCodigo());
            logger.info("No hay camino entre " + equipo1.getCodigo() + " y " + equipo2.getCodigo());
        }

        // Verificar las conexiones en el camino más rápido
        for (Conexion conexion : caminoMasRapido) {
            if (conexion.getEquipo1() == null || conexion.getEquipo2() == null) {
                logger.info("Conexión inválida en el camino: " + conexion);
            }
        }

        imprimirGrafos();
        return caminoMasRapido;
    }

    /**
     * Encuentra y devuelve todos los equipos alcanzables desde un equipo dado.
     *
     * @param equipo El equipo desde el cual empezar la búsqueda.
     * @return Un conjunto de equipos alcanzables.
     * @throws IllegalArgumentException si el equipo no está presente en el grafo.
     */
    public List<Conexion> encontrarEquiposAlcanzables(Equipo equipo) {
        cargarDatos(coordinador.listarConexiones(), coordinador.listarEquipos());

        if (!grafoNoDirigido.containsVertex(equipo)) {
            logger.error("El equipo " + equipo.getCodigo() + " no está dentro del grafo.");
            throw new IllegalArgumentException("El grafo debe contener el vértice inicial.");
        }

        // Usar ConnectivityInspector de JGraphT para encontrar los componentes conectados
        ConnectivityInspector<Equipo, Conexion> inspector = new ConnectivityInspector<>(grafoNoDirigido);
        Set<Equipo> equiposAlcanzables = inspector.connectedSetOf(equipo);

        //Ahora, del set obtener todas las conexiones
        List<Conexion> conexionesAlcanzables = new ArrayList<>();
        for (Equipo equipoAlcanzable : equiposAlcanzables) {
            for (Conexion conexion : grafoNoDirigido.edgesOf(equipoAlcanzable)) {
                if(conexionesAlcanzables.contains(conexion)) continue;
                conexionesAlcanzables.add(conexion);
            }
        }

        // Imprimir los equipos alcanzables
        imprimirEquiposAlcanzables(equipo, equiposAlcanzables);

        return conexionesAlcanzables;
    }

    /**
     * Imprime los equipos alcanzables desde un equipo dado.
     *
     * @param equipo El equipo desde el cual se comenzó la búsqueda.
     * @param equiposAlcanzables El conjunto de equipos alcanzables.
     */
    private void imprimirEquiposAlcanzables(Equipo equipo, Set<Equipo> equiposAlcanzables) {
        logger.info("Equipos alcanzables desde " + equipo.getCodigo() + ": ");
        for (Equipo eq : equiposAlcanzables) {
            logger.info(eq);
        }
    }


    /**
     * Imprime los vértices y aristas del grafo no dirigido.
     */
    public void imprimirGrafos() {
        logger.info("Vértices en grafoNoDirigido: " + grafoNoDirigido.vertexSet());
        logger.info("Aristas en grafoNoDirigido: " + grafoNoDirigido.edgeSet());

    }

    /**
     * Metodo para listar todas las IPS de los equipos disponibles
     * @param equipos lista de equipos
     * @return lista de ips
     */
    public List<String> listarIps(List<Equipo> equipos) {
        Set<String> todasLasIPs = new HashSet<>(); // Utilizamos un Set para evitar duplicados

        for (Equipo equipo : equipos) {
            todasLasIPs.addAll(equipo.getDireccionesIP()); // Agrega todas las IPs del equipo
            logger.info("Equipo: " + equipo + ". Direcciones IP: " + equipo.getDireccionesIP());
            if (equipo.getDireccionesIP() != null) {
                todasLasIPs.addAll(equipo.getDireccionesIP());
            }

        }
        return new ArrayList<>(todasLasIPs); // Convertimos el Set a una lista
    }

    /**
     * Realiza un ping real entre un rango de direcciones IP, probando si cada ip esta conectada o no.
     * @param dirRed1 primer segmento de la dirección IP de inicio.
     * @param dirRed2 segundo segmento de la dirección IP de inicio.
     * @param dirHost1p1 primer segmento del rango del host de inicio.
     * @param dirHost1p2 segundo segmento del rango del host de inicio.
     * @param dirHost2p1 primer segmento del rango del host de fin.
     * @param dirHost2p2 segundo segmento del rango del host de fin.
     * @return Un mapa de direcciones IP y sus resultados de ping reales (true si hay respuesta).
     */
    public Map<String, Boolean> pingRangoIPs(int dirRed1, int dirRed2, int dirHost1p1, int dirHost1p2, int dirHost2p1, int dirHost2p2) {
        //mapa donde se guardan los resultados
        Map<String, Boolean> resultados = new HashMap<>();
        //convierte los 6 enteros en direcciones IP de inicio y fin
        long startIp = convertirIpALong(dirRed1, dirRed2, dirHost1p1, dirHost1p2);
        long endIp = convertirIpALong(dirRed1, dirRed2, dirHost2p1, dirHost2p2);

        //verifico el ping para cada ip del rango
        for (long ip = startIp; ip <= endIp; ip++) {
            String ipDir = convertirLongAIp(ip);
            boolean resultadoPing = ping(ipDir);
            resultados.put(ipDir, resultadoPing);
        }
        return resultados;
    }

    /**
     * Realiza un ping simulado entre un rango de direcciones IP
     * @param dirRed1 primer segmento de la direccion ip de inicio
     * @param dirRed2 segundo segmento de la direccion ip de inicio
     * @param dirHost1p1 primer segmento del host de inicio
     * @param dirHost1p2 segundo segmento del host de inicio
     * @param dirHost2p1 primer segmento del host de fin
     * @param dirHost2p2 segundo segmento del host de fin
     * @return Un mapa de IPs y sus resultados de ping.
     */
    public Map<String, Boolean> pingEntreIPs(int dirRed1, int dirRed2, int dirHost1p1, int dirHost1p2, int dirHost2p1, int dirHost2p2) {
        //mapa donde se guardan los resultados
        Map<String, Boolean> resultados = new HashMap<>();
        //convierte los 6 enteros en direcciones IP de inicio y fin
        long startIp = convertirIpALong(dirRed1, dirRed2, dirHost1p1, dirHost1p2);
        long endIp = convertirIpALong(dirRed1, dirRed2, dirHost2p1, dirHost2p2);

        //verifico el ping para cada ip del rango
        for (long ip = startIp; ip <= endIp; ip++) {
                    String ipDir = convertirLongAIp(ip);
                    boolean resultadoPing = pingIP(ipDir);
                    resultados.put(ipDir, resultadoPing);
        }
        return resultados;
    }

    /**
     *  Realiza un ping simulado a una dirección IP especificada
     *  Busca la IP en la lista de equipos y simula el ping con el estado del equipo
     * @param ip direccion ip a la que se hara el ping
     * @return true si el equipo esta activo, false si no lo esta
     */
    public boolean pingIP (String ip) {
        for (Equipo e : coordinador.listarEquipos()) {
            if (e.getDireccionesIP().contains(ip)) {
                return ping(e);
            }
        }
        return false;
    }

    /**
     * Realiza un ping simulado a un equipo y devuelve el estado actual del equipo
     * @param equipo equipo al que se le hara el ping
     * @return true si el equipo esta activo, false si no lo esta
     */
    public boolean ping (Equipo equipo) {
        if (equipo == null) {
            return false;
        }
        logger.info(equipo.getCodigo() + ":" + equipo.isEstado());
        return equipo.isEstado();
    }

    /**
     * Realiza un ping real a una dirección IP
     * Verifica si la dirección IP es alcanzable en la red.
     *
     * @param ip La dirección IP al que le hara ping
     * @return true si la IP es alcanzable; false si no lo es
     */
    public boolean ping (String ip) {
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(1000);
        } catch (Exception e) {
            return false; // No se puede alcanzar la IP
        }
    }

    /**
     * Convierte una dirección IP representada por cuatro segmentos en un valor long.
     *
     * @param seg1 primer segmento de la dirección IP.
     * @param seg2 segundo segmento de la dirección IP.
     * @param seg3 tercer segmento de la dirección IP.
     * @param seg4 cuarto segmento de la dirección IP.
     * @return la dirección IP en formato long.
     */
    public long convertirIpALong(int seg1, int seg2, int seg3, int seg4) {
        return ((long) seg1 << 24) | ((long) seg2 << 16) | ((long) seg3 << 8) | (long) seg4;
    }

    /**
     * Convierte una dirección IP en formato long a un String
     *
     * @param ip La dirección IP en formato long.
     * @return La dirección IP en formato String (ej: "192.168.0.1").
     */
    public String convertirLongAIp(long ip) {
        return ((ip >> 24) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                (ip & 0xFF);
    }

    public boolean verificarIP(String direccionIP) {
        String regex = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
        String patron = regex + "\\."
                + regex + "\\."
                + regex + "\\."
                + regex;

        return direccionIP.matches(patron);
    }


}

