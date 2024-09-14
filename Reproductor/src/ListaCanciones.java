/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dennisse
 */
public class ListaCanciones {

    private Cancion cabeza; 

    public ListaCanciones() {
        this.cabeza = null;
    }

    public Cancion getCabeza() {
        return cabeza;
    }

    public void agregarCancion(Cancion nuevaCancion) {
        if (cabeza == null) {
            cabeza = nuevaCancion;
        } else {
            Cancion actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevaCancion);
        }
    }

    public Cancion obtenerCancion(int index) {
        Cancion actual = cabeza;
        int contador = 0;
        while (actual != null) {
            if (contador == index) {
                return actual;
            }
            actual = actual.getSiguiente();
            contador++;
        }
        return null; 
    }

    public int getTamano() {
        int tamano = 0;
        Cancion actual = cabeza;
        while (actual != null) {
            tamano++;
            actual = actual.getSiguiente();
        }
        return tamano;
    }
}
