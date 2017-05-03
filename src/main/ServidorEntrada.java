package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Roberto Pedraza
 */
public class ServidorEntrada {

    private ServerSocket server;
    
    private List<String> ips;
    private List<Integer> orden;
    
    public ServidorEntrada() {
        ips = new ArrayList();
        orden = new ArrayList();
        
        crearListaOrden();
    }

    /**
     * Crea la lista de orden que indicara quien iniciara el juego.
     */
    private void crearListaOrden(){
        orden.add(1);
        orden.add(2);
        orden.add(3);
    }
    
    /**
     * Inicializa el servidor en la ip y puerto especificado.
     *
     * @param IP
     * @param puerto
     * @throws Exception
     */
    public void iniciar(String IP, int puerto) throws Exception {
        server = new ServerSocket();
        server.bind(new InetSocketAddress(IP, puerto));

        new Thread(new Runnable() {
            public void run() {

                try {
                    while (true) {
                        System.out.println("Esperando conexion..");
                        Socket peerNuevo = server.accept();
                        
                        Random random = new Random();
                        
                        int indexOrden = random.nextInt(orden.size());
                        
                        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(peerNuevo.getOutputStream()));
                        out.writeObject(ips);
                        out.writeObject(String.valueOf(orden.get(indexOrden)));
                        out.flush();
                        
                        orden.remove(indexOrden);
                        
                        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(peerNuevo.getInputStream()));
                        String ip = (String) in.readObject();
                        int puerto =(Integer) in.readObject();
                        
                        ips.add(ip +" " +String.valueOf(puerto));
                        System.out.println("Conexion realizada con " + peerNuevo.getLocalPort());
                        peerNuevo.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
