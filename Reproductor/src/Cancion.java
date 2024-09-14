
import javax.swing.ImageIcon;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dennisse
 */
public class Cancion {
    private String nombre;
    private String artista;
    private String duracion;
    private String tipoMusica;
    private ImageIcon imagen;
    private String rutaArchivo;  // Ruta del archivo de la canción
    private Cancion siguiente;

    public Cancion(String nombre, String artista, String duracion, String tipoMusica, ImageIcon imagen, String rutaArchivo) {
        this.nombre = nombre;
        this.artista = artista;
        this.duracion = duracion;
        this.tipoMusica = tipoMusica;
        this.imagen = imagen;
        this.rutaArchivo = rutaArchivo;  // Inicializamos la ruta del archivo
        this.siguiente = null;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public String getArtista() { return artista; }
    public String getDuracion() { return duracion; }
    public String getTipoMusica() { return tipoMusica; }
    public ImageIcon getImagen() { return imagen; }
    public String getRutaArchivo() { return rutaArchivo; }  // Método para obtener la ruta del archivo

    public Cancion getSiguiente() { return siguiente; }
    public void setSiguiente(Cancion siguiente) { this.siguiente = siguiente; }
}



