
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
    private String rutaArchivo;  
    private Cancion siguiente;

    public Cancion(String nombre, String artista, String duracion, String tipoMusica, ImageIcon imagen, String rutaArchivo) {
        this.nombre = nombre;
        this.artista = artista;
        this.duracion = duracion;
        this.tipoMusica = tipoMusica;
        this.imagen = imagen;
        this.rutaArchivo = rutaArchivo; 
        this.siguiente = null;
    }

    public String getNombre() { return nombre; }
    public String getArtista() { return artista; }
    public String getDuracion() { return duracion; }
    public String getTipoMusica() { return tipoMusica; }
    public ImageIcon getImagen() { return imagen; }
    public String getRutaArchivo() { return rutaArchivo; } 

    public Cancion getSiguiente() { return siguiente; }
    public void setSiguiente(Cancion siguiente) { this.siguiente = siguiente; }
}



