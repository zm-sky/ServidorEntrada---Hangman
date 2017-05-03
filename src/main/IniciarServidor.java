/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


/**
 *
 * @author Roberto Pedraza
 */
public class IniciarServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServidorEntrada servidor = new ServidorEntrada();
            servidor.iniciar("localhost", 6000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
