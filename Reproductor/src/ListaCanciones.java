/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dennisse
 */
public class ListaCanciones {
    private Cancion cabeza;  // Primer elemento de la lista enlazada

    public ListaCanciones() {
        this.cabeza = null;
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
        return null;  // Si el índice no es válido
    }

    // Método para obtener el tamaño de la lista (si es necesario)
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
