package guiii;
import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class FavoriteFilmGUI extends JFrame {
    private JTextField nameText;
    private JTextField ageText;
    private JTextField favoriteMovieText;
    private JTextField favoriteCharacterText;
    private JTextField deleteIdText;
    private JTextArea textArea;
    private DatabaseManager databaseManager;

    public FavoriteFilmGUI() {
        databaseManager = new DatabaseManager();

        setTitle("Form Film Kesukaan");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(200, 162, 200));
        panel.setLayout(null);
        add(panel);

        placeComponents(panel);
        displayData();
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        JLabel nameLabel = new JLabel("Nama:");
        nameLabel.setBounds(10, 20, 160, 25);
        panel.add(nameLabel);

        nameText = new JTextField(20);
        nameText.setBounds(180, 20, 160, 25);
        panel.add(nameText);

        JLabel ageLabel = new JLabel("Umur:");
        ageLabel.setBounds(10, 60, 160, 25);
        panel.add(ageLabel);

        ageText = new JTextField(20);
        ageText.setBounds(180, 60, 160, 25);
        panel.add(ageText);

        JLabel favoriteMovieLabel = new JLabel("Film Favorit:");
        favoriteMovieLabel.setBounds(10, 100, 160, 25);
        panel.add(favoriteMovieLabel);

        favoriteMovieText = new JTextField(20);
        favoriteMovieText.setBounds(180, 100, 160, 25);
        panel.add(favoriteMovieText);

        JLabel favoriteCharacterLabel = new JLabel("Karakter Favorit:");
        favoriteCharacterLabel.setBounds(10, 140, 160, 25);
        panel.add(favoriteCharacterLabel);

        favoriteCharacterText = new JTextField(20);
        favoriteCharacterText.setBounds(180, 140, 160, 25);
        panel.add(favoriteCharacterText);

        JButton saveButton = new JButton("Simpan");
        saveButton.setBounds(10, 180, 160, 25);
        panel.add(saveButton);

        JButton deleteButton = new JButton("Hapus Semua Data");
        deleteButton.setBounds(180, 180, 160, 25);
        panel.add(deleteButton);

        JLabel deleteIdLabel = new JLabel("ID untuk Dihapus:");
        deleteIdLabel.setBounds(10, 220, 160, 25);
        panel.add(deleteIdLabel);

        deleteIdText = new JTextField(20);
        deleteIdText.setBounds(180, 220, 160, 25);
        panel.add(deleteIdText);

        JButton deleteByIdButton = new JButton("Hapus Data");
        deleteByIdButton.setBounds(350, 220, 160, 25);
        panel.add(deleteByIdButton);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 260, 560, 180);
        panel.add(scrollPane);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String ageStr = ageText.getText();
                String favoriteMovie = favoriteMovieText.getText();
                String favoriteCharacter = favoriteCharacterText.getText();

                if (!name.isEmpty() && !ageStr.isEmpty() && !favoriteMovie.isEmpty() && !favoriteCharacter.isEmpty()) {
                    try {
                        int age = Integer.parseInt(ageStr);
                        FavoriteFilm film = new FavoriteFilm(name, age, favoriteMovie, favoriteCharacter);
                        databaseManager.saveFavoriteFilm(film);
                        displayData();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Umur harus berupa angka.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(panel, "Gagal menyimpan data: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Semua kolom harus diisi.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(panel, "Apakah Anda yakin ingin menghapus semua data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        databaseManager.deleteAllFavoriteFilms();
                        displayData();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(panel, "Gagal menghapus data: " + ex.getMessage());
                    }
                }
            }
        });

        deleteByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = deleteIdText.getText();
                if (!idStr.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idStr);
                        databaseManager.deleteFavoriteFilmById(id);
                        displayData();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "ID harus berupa angka.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(panel, "Gagal menghapus data: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Kolom ID tidak boleh kosong.");
                }
            }
        });
    }

    private void displayData() {
        try {
            List<FavoriteFilm> films = databaseManager.getAllFavoriteFilms();
            StringBuilder data = new StringBuilder();
            for (FavoriteFilm film : films) {
                data.append(film.getId()).append(". Nama: ").append(film.getName())
                    .append(", Umur: ").append(film.getAge())
                    .append(", Film Favorit: ").append(film.getFavoriteMovie())
                    .append(", Karakter Favorit: ").append(film.getFavoriteCharacter())
                    .append("\n");
            }
            textArea.setText(data.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FavoriteFilmGUI();
            }
        });
    }
}
