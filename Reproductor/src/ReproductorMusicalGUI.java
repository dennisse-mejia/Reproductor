
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javazoom.jl.player.Player;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Dennisse
 */
import com.mpatric.mp3agic.*;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ReproductorMusicalGUI extends JFrame {

    private JButton btnPlay, btnStop, btnPause, btnAdd, btnSelect;
    private JLabel lblImagen, lblInfo;
    private ListaCanciones listaCanciones;
    private Cancion cancionActual;
    private BasicPlayer player;
    private boolean pausado = false;

    public ReproductorMusicalGUI() {
        listaCanciones = new ListaCanciones();
        player = new BasicPlayer();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Reproductor Musical");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Botones
        btnPlay = new JButton("Play");
        btnPause = new JButton("Pause");
        btnStop = new JButton("Stop");
        btnAdd = new JButton("Add");
        btnSelect = new JButton("Select");

        lblImagen = new JLabel("Imagen de la canción", JLabel.CENTER);
        lblInfo = new JLabel("Info de la canción", JLabel.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 5));
        panelBotones.add(btnPlay);
        panelBotones.add(btnPause);
        panelBotones.add(btnStop);
        panelBotones.add(btnAdd);
        panelBotones.add(btnSelect);

        add(panelBotones, BorderLayout.SOUTH);
        add(lblImagen, BorderLayout.CENTER);
        add(lblInfo, BorderLayout.NORTH);

        // Acciones de botones
        btnPlay.addActionListener(e -> reproducir());
        btnPause.addActionListener(e -> pausar());
        btnStop.addActionListener(e -> detener());
        btnAdd.addActionListener(e -> agregarCancion());
        btnSelect.addActionListener(e -> seleccionarCancion());
    }

    private void agregarCancion() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos MP3", "mp3"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            String nombreArchivo = archivo.getName().toLowerCase();

            if (!nombreArchivo.endsWith(".mp3")) {
                JOptionPane.showMessageDialog(this, "Error: Solo se permiten archivos MP3.", "Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            try {
                Mp3File mp3file = new Mp3File(archivo);
                String nombre = archivo.getName();
                String artista = "Desconocido";
                String duracion = "00:00";
                String tipoMusica = "Desconocido";
                ImageIcon imagen = null;

                if (mp3file.hasId3v2Tag()) {
                    ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                    nombre = id3v2Tag.getTitle() != null ? id3v2Tag.getTitle() : archivo.getName();
                    artista = id3v2Tag.getArtist() != null ? id3v2Tag.getArtist() : "Desconocido";
                    duracion = convertirDuracion(mp3file.getLengthInSeconds());
                    tipoMusica = id3v2Tag.getGenreDescription() != null ? id3v2Tag.getGenreDescription() : "Desconocido";

                    byte[] imagenData = id3v2Tag.getAlbumImage();
                    if (imagenData != null) {
                        imagen = new ImageIcon(imagenData);
                    }
                }

                Cancion nuevaCancion = new Cancion(nombre, artista, duracion, tipoMusica, imagen, archivo.getPath());
                listaCanciones.agregarCancion(nuevaCancion);
                JOptionPane.showMessageDialog(this, "Canción agregada a la lista.");

                mostrarTodasLasCanciones();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar el archivo mp3.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String convertirDuracion(long segundosTotales) {
        long minutos = segundosTotales / 60;
        long segundos = segundosTotales % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    private void seleccionarCancion() {
        mostrarTodasLasCanciones();

        String input = JOptionPane.showInputDialog("Selecciona el índice de la canción para reproducir:");
        try {
            int indice = Integer.parseInt(input);
            cancionActual = listaCanciones.obtenerCancion(indice);

            if (cancionActual != null) {
                lblImagen.removeAll(); 
                lblImagen.revalidate();
                lblImagen.repaint();

                lblInfo.setText("<html>"
                        + "Nombre: " + cancionActual.getNombre() + "<br>"
                        + "Artista: " + cancionActual.getArtista() + "<br>"
                        + "Duración: " + cancionActual.getDuracion() + "<br>"
                        + "Tipo de música: " + cancionActual.getTipoMusica()
                        + "</html>");

                if (cancionActual.getImagen() != null) {
                    Image imagenEscalada = cancionActual.getImagen().getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new ImageIcon(imagenEscalada));
                    lblImagen.setText("");
                } else {
                    lblImagen.setText("No hay imagen disponible.");
                    lblImagen.setIcon(null);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Índice no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarImagenCancion(String rutaArchivo) {
        try {
            Mp3File mp3file = new Mp3File(rutaArchivo);
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                byte[] imagenData = id3v2Tag.getAlbumImage();
                if (imagenData != null) {
                    ImageIcon icon = new ImageIcon(imagenData);
                    lblImagen.setIcon(icon);
                } else {
                    lblImagen.setText("No hay imagen de álbum disponible.");
                    lblImagen.setIcon(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblImagen.setText("Error al cargar la imagen.");
            lblImagen.setIcon(null);
        }
    }

    private void reproducir() {
        if (cancionActual != null) {
            try {
                if (pausado) {
                    player.resume();
                } else {
                    detener(); 
                    player.open(new File(cancionActual.getRutaArchivo()));  
                    player.play();
                }
                pausado = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay ninguna canción seleccionada.");
        }
    }

    private void pausar() {
        if (!pausado && player != null) {
            try {
                player.pause(); 
                pausado = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void detener() {
        try {
            if (player != null) {
                player.stop(); 
            }
            pausado = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarTodasLasCanciones() {
        JPanel panelCanciones = new JPanel();
        panelCanciones.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.BOTH;

        Cancion actual = listaCanciones.getCabeza();
        int indice = 0;

        while (actual != null) {
            gbc.gridx = 0;  
            gbc.gridy = indice; 

            if (actual.getImagen() != null) {
                ImageIcon imagenEscalada = new ImageIcon(actual.getImagen().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                JLabel lblImagenCancion = new JLabel(imagenEscalada);
                panelCanciones.add(lblImagenCancion, gbc);
            }

            gbc.gridx = 1;  
            gbc.gridy = indice; 

            JLabel lblInfoCancion = new JLabel("<html>"
                    + "Índice: " + indice + "<br>"
                    + "Nombre: " + actual.getNombre() + "<br>"
                    + "Artista: " + actual.getArtista() + "<br>"
                    + "Duración: " + actual.getDuracion() + "<br>"
                    + "Tipo de música: " + actual.getTipoMusica()
                    + "</html>");
            panelCanciones.add(lblInfoCancion, gbc);

            actual = actual.getSiguiente();
            indice++;
        }

        lblImagen.removeAll(); 
        lblImagen.setLayout(new BorderLayout());
        lblImagen.add(new JScrollPane(panelCanciones), BorderLayout.CENTER);  
        lblImagen.revalidate();  
        lblImagen.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReproductorMusicalGUI().setVisible(true));
    }
}
