import org.mariadb.jdbc.Connection;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Globalist extends JDialog {
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JButton ajouterJoueur;
    private JButton editerJoueur;
    private JButton supprimerJoueur;
    private JButton AjouterTournois;
    private JButton EditerTournois;
    private JButton SupprimerTournois;
    private JTextField textFieldRechercheTournois;
    private JButton afficherToutLesVainqueursButton;
    private JTextField textFieldRechercherParPrenomMatchFinaliste;
    private JTextField textFieldRechercheParNomMatchFinaliste;
    private JButton RechercheParNomMatch;
    private JButton ValiderRechercheParPrenomMatch;
    private JTextField textFieldEpreuveAnnee;
    private JTextField textFieldEpreuveEpreuve;
    private JButton ValidationRechercheEpreuve;
    private JTextField textFieldRechercheJoueurNom;
    private JTextField textFieldRechercheJoueurPrenom;
    private JComboBox<String> SexeComboBoxRecherche;
    private JTextField textFieldIdEdit;
    private JTextField NouveauPrenomJoueur;
    private JTextField NouveauNomJoueur;
    private JComboBox<String> ChangeSexe;
    private JButton voirToutLesJoueursButton;
    private JPanel toutLesJoursPanel;
    private JPanel PanelJoueur;
    private JTable table1Joueur;
    private JScrollPane jscrollpane;
    private JButton rechercheToutesLesFillesButton;
    private JButton rechercheToutLesJoueursHommeButton;
    private JTextField textFieldCodeTournois;
    private JButton afficherTournoisButton;
    private JTable table2Tournois;
    private JTextField textFieldEditerNomTournois;
    private JTextField textFieldEditerCodeTournois;
    private JTextField textFieldEditerIDTournois;
    private JTable table1Finaliste;
    private JButton afficherToutLesFinalistesButton;
    private JTable table1JoueurMatchVainqueur;
    private JList<Joueur> list1;
    private JTextField textFieldRechercherParPrenomMatchVainqueur;
    private JTextField textFieldRechercherParNomMatchVainqueur;
    private JButton ValiderVainqueurPrenom;
    private JButton ValiderVainqueurNom;
    private JTable table2Epreuve;
    private JList<Tournois> list3Tournois;

    public Globalist() throws SQLException {

        SexeComboBoxRecherche.addItem("F");
        SexeComboBoxRecherche.addItem("H");
        ChangeSexe.addItem("F");
        ChangeSexe.addItem("H");
        setContentPane(contentPane);
        setModal(true);
        GestionJoueur joueur1;
        try {
            joueur1 = new GestionJoueur();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        GestionTournois gestionTournois;
        gestionTournois = new GestionTournois();


        ajouterJoueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getActionCommand().equals("Ajouter un Joueur")) {
                    JTextField nomField = new JTextField();
                    JTextField prenomField = new JTextField();
                    JComboBox<String> sexeComboBoxe = new JComboBox<>(new String[]{"F", "H"});
                    Object[] fields = {
                            "Nom:", nomField,
                            "Prenom:", prenomField,
                            "Sexe:", sexeComboBoxe
                    };

                    int result = JOptionPane.showConfirmDialog(null, fields, "Ajouter un joueur", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String nom = nomField.getText();
                        String prenom = prenomField.getText();
                        String sexe = Objects.requireNonNull(sexeComboBoxe.getSelectedItem()).toString();

                        joueur1.addUser(nom, prenom, sexe);
                    }
                }
            }
        });

        supprimerJoueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Joueur joueurSelectionne = list1.getSelectedValue();

                if (joueurSelectionne != null) {
                    int id = joueurSelectionne.getId();
                    String nom = joueurSelectionne.getNom();
                    String prenom = joueurSelectionne.getPrenom();
                    String sexe = joueurSelectionne.getSexe();

                    try {
                        joueur1.supprimerJoueur(id, nom, prenom, sexe);

                        JOptionPane.showMessageDialog(null, "Le joueur " + nom + " " + prenom + " " + "a été supprimé avec succès.");

                        DefaultListModel<Joueur> model = (DefaultListModel<Joueur>) list1.getModel();
                        model.removeElement(joueurSelectionne);

                        // Réinitialisation des champs texte
                        NouveauNomJoueur.setText("");
                        NouveauPrenomJoueur.setText("");
                        ChangeSexe.setSelectedIndex(0);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la suppression du joueur.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun joueur n'a été sélectionné.");
                }
            }
        });


        editerJoueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Editer")) {
                    String nom = NouveauNomJoueur.getText();
                    String prenom = NouveauPrenomJoueur.getText();
                    String sexe = Objects.requireNonNull(ChangeSexe.getSelectedItem()).toString();
                    int id = Integer.parseInt(textFieldIdEdit.getText());
                    System.out.println(id);

                    String ancienNom = "";
                    String ancienPrenom = "";
                    String ancienSexe = "";
                    try {
                        PreparedStatement preparedStatement = joueur1.connectionT.prepareStatement("SELECT NOM, PRENOM, SEXE FROM JOUEUR WHERE ID = ?");
                        preparedStatement.setInt(1, id);
                        ResultSet rs = preparedStatement.executeQuery();
                        if (rs.next()) {
                            ancienNom = rs.getString("NOM");
                            ancienPrenom = rs.getString("PRENOM");
                            ancienSexe = rs.getString("SEXE");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    joueur1.updatePlayer(nom, prenom, sexe, id);

                    String message = "La personne avec l'ancien nom " + ancienNom + ", le prénom " + ancienPrenom + " et le sexe " + ancienSexe + " a été modifiée en " + nom + ", " + prenom + ", " + sexe + ".";
                    JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        voirToutLesJoueursButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] columns = {"ID", "NOM", "PRENOM", "SEXE"};

                int countRawDatabe = 0;
                try {
                    countRawDatabe = new GestionJoueur().getRawUserTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String[][] data = new String[countRawDatabe][4];

                List<Joueur> listPlayer = null;
                try {
                    listPlayer = new GestionJoueur().listAllPlayer();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listPlayer.size(); i++) {
                    data[i][0] = String.valueOf(listPlayer.get(i).getId());
                    data[i][1] = listPlayer.get(i).getNom();
                    data[i][2] = listPlayer.get(i).getPrenom();
                    data[i][3] = listPlayer.get(i).getSexe();
                }

                DefaultTable model = new DefaultTable(data, columns);
                table1Joueur.setModel(model);


            }
        });
        rechercheToutesLesFillesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String[] columns = {"ID", "NOM", "PRENOM", "SEXE"};

                int countRawDatabe = 0;
                try {
                    countRawDatabe = new GestionJoueur().getRawUserTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String[][] data = new String[countRawDatabe][4];

                List<Joueur> listPlayerFemale = null;
                try {
                    listPlayerFemale = new GestionJoueur().listAllPlayerFemale();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listPlayerFemale.size(); i++) {
                    data[i][0] = String.valueOf(listPlayerFemale.get(i).getId());
                    data[i][1] = listPlayerFemale.get(i).getNom();
                    data[i][2] = listPlayerFemale.get(i).getPrenom();
                    data[i][3] = listPlayerFemale.get(i).getSexe();
                }

                DefaultTable model = new DefaultTable(data, columns);
                table1Joueur.setModel(model);
            }
        });
        rechercheToutLesJoueursHommeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String[] columns = {"ID", "NOM", "PRENOM", "SEXE"};

                int countRawDatabe = 0;
                try {
                    countRawDatabe = new GestionJoueur().getRawUserTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String[][] data = new String[countRawDatabe][4];

                List<Joueur> listPlayerMale = null;
                try {
                    listPlayerMale = new GestionJoueur().listAllPlayerMale();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listPlayerMale.size(); i++) {
                    data[i][0] = String.valueOf(listPlayerMale.get(i).getId());
                    data[i][1] = listPlayerMale.get(i).getNom();
                    data[i][2] = listPlayerMale.get(i).getPrenom();
                    data[i][3] = listPlayerMale.get(i).getSexe();
                }

                DefaultTable model = new DefaultTable(data, columns);
                table1Joueur.setModel(model);
            }

        });
        AjouterTournois.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getActionCommand().equals("Ajouter un Tournoi")) {
                    JTextField nomField = new JTextField();
                    JTextField codeField = new JTextField();
                    Object[] fields = {
                            "Nom:", nomField,
                            "Code:", codeField,
                    };

                    int result = JOptionPane.showConfirmDialog(null, fields, "Ajouter un tournoi", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String nom = nomField.getText();
                        String code = codeField.getText();

                        gestionTournois.addTournois(nom, code);
                    }
                }
            }
        });
        afficherTournoisButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String[] columns = {"ID", "NOM", "CODE"};

                int countRawDatabe = 0;
                try {
                    countRawDatabe = new GestionTournois().getRawTournoiTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String[][] data = new String[countRawDatabe][3];

                List<Tournois> listTournois = null;
                try {
                    listTournois = new GestionTournois().listAllTournoi();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listTournois.size(); i++) {
                    data[i][0] = String.valueOf(listTournois.get(i).getId());
                    data[i][1] = listTournois.get(i).getNom();
                    data[i][2] = listTournois.get(i).getCode();
                }

                DefaultTable model = new DefaultTable(data, columns);
                table2Tournois.setModel(model);
            }

        });
        SupprimerTournois.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int id = Integer.parseInt(textFieldEditerIDTournois.getText());
                String nom = textFieldEditerNomTournois.getText();
                String code = textFieldEditerCodeTournois.getText();
                try {
                    gestionTournois.deleteTournoi(nom, code, id);
                    textFieldEditerNomTournois.setText("");
                    textFieldEditerCodeTournois.setText("");
                    textFieldEditerIDTournois.setText("");

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        EditerTournois.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String nom = textFieldEditerNomTournois.getText();
                String code = textFieldEditerCodeTournois.getText();
                int id = Integer.parseInt(textFieldEditerIDTournois.getText());
                System.out.println(id);


                gestionTournois.updateTournoi(nom, code, id);
                textFieldEditerNomTournois.setText("");
                textFieldEditerCodeTournois.setText("");
                textFieldEditerIDTournois.setText("");
            }
        });
        afficherToutLesVainqueursButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<JoueurPerso> listeMatchVainqueur = null;
                super.mouseClicked(e);
                String[] columns = {"ID", "NOM", "PRENOM", "SEXE", "ANNEE", "TYPE_EPREUVE", "NOM", "CODE"};

                int countRawDatabe = 0;
                try {
                    countRawDatabe = new GestionMatch().getRawMatchTableVainqueur();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String[][] data = new String[countRawDatabe][8];

                try {
                    listeMatchVainqueur = new GestionMatch().selectAllMatchVainqueur();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listeMatchVainqueur.size(); i++) {
                    data[i][0] = String.valueOf(listeMatchVainqueur.get(i).getId());
                    data[i][1] = listeMatchVainqueur.get(i).getNom();
                    data[i][2] = listeMatchVainqueur.get(i).getPrenom();
                    data[i][3] = listeMatchVainqueur.get(i).getSexe();
                    data[i][4] = String.valueOf(listeMatchVainqueur.get(i).getAnnee());
                    data[i][5] = listeMatchVainqueur.get(i).getTypeEpreuve();
                    data[i][6] = listeMatchVainqueur.get(i).getNomTournoi();
                    data[i][7] = listeMatchVainqueur.get(i).getCodeTournoi();

                }
                System.out.println(listeMatchVainqueur);

                DefaultTable model = new DefaultTable(data, columns);
                table1JoueurMatchVainqueur.setModel(model);
                ;
            }

        });
        afficherToutLesFinalistesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<JoueurPerso> listeMatchFinal = null;
                super.mouseClicked(e);
                String[] columns = {"ID", "NOM", "PRENOM", "SEXE", "ANNEE", "TYPE_EPREUVE", "NOM", "CODE"};

                int countRawDatabe = 0;
                try {
                    countRawDatabe = new GestionMatch().getRawMatchTableFinaliste();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String[][] data = new String[countRawDatabe][8];

                try {
                    listeMatchFinal = new GestionMatch().selectAllMatchFinaliste();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listeMatchFinal.size(); i++) {
                    data[i][0] = String.valueOf(listeMatchFinal.get(i).getId());
                    data[i][1] = listeMatchFinal.get(i).getNom();
                    data[i][2] = listeMatchFinal.get(i).getPrenom();
                    data[i][3] = listeMatchFinal.get(i).getSexe();
                    data[i][4] = String.valueOf(listeMatchFinal.get(i).getAnnee());
                    data[i][5] = listeMatchFinal.get(i).getTypeEpreuve();
                    data[i][6] = listeMatchFinal.get(i).getNomTournoi();
                    data[i][7] = listeMatchFinal.get(i).getCodeTournoi();

                }

                DefaultTable model = new DefaultTable(data, columns);
                table1Finaliste.setModel(model);
                ;
            }

        });
        ValiderRechercheParPrenomMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<JoueurPerso> listeMatchFinalPrenom = null;
                String[] columns = {"ID", "NOM", "PRENOM", "SEXE", "ANNEE", "TYPE_EPREUVE", "NOM", "CODE"};

                int countRawDatabe = 0;
                try {
                    countRawDatabe = new GestionMatch().getRawMatchTableFinaliste();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String[][] data = new String[countRawDatabe][8];

                try {
                    listeMatchFinalPrenom = new GestionMatch().selectPrenom(
                            textFieldRechercherParPrenomMatchFinaliste.getText());
                    System.out.println(listeMatchFinalPrenom);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listeMatchFinalPrenom.size(); i++) {
                    data[i][0] = String.valueOf(listeMatchFinalPrenom.get(i).getId());
                    data[i][1] = listeMatchFinalPrenom.get(i).getNom();
                    data[i][2] = listeMatchFinalPrenom.get(i).getPrenom();
                    data[i][3] = listeMatchFinalPrenom.get(i).getSexe();
                    data[i][4] = String.valueOf(listeMatchFinalPrenom.get(i).getAnnee());
                    data[i][5] = listeMatchFinalPrenom.get(i).getTypeEpreuve();
                    data[i][6] = listeMatchFinalPrenom.get(i).getNomTournoi();
                    data[i][7] = listeMatchFinalPrenom.get(i).getCodeTournoi();

                    DefaultTable model = new DefaultTable(data, columns);
                    table1Finaliste.setModel(model);
                }
            }
        });
        RechercheParNomMatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<JoueurPerso> listMatchfinalNom = null;
                String[] columns = {"ID", "NOM", "PRENOM", "SEXE", "ANNEE", "TYPE_EPREUVE", "NOM", "CODE"};

                int countRawDatabe = 0;
                try {
                    countRawDatabe = new GestionMatch().getRawMatchTableFinaliste();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String[][] data = new String[countRawDatabe][8];

                try {
                    listMatchfinalNom = new GestionMatch().selectNom(
                            textFieldRechercheParNomMatchFinaliste.getText());
                    System.out.println(listMatchfinalNom);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listMatchfinalNom.size(); i++) {
                    data[i][0] = String.valueOf(listMatchfinalNom.get(i).getId());
                    data[i][1] = listMatchfinalNom.get(i).getNom();
                    data[i][2] = listMatchfinalNom.get(i).getPrenom();
                    data[i][3] = listMatchfinalNom.get(i).getSexe();
                    data[i][4] = String.valueOf(listMatchfinalNom.get(i).getAnnee());
                    data[i][5] = listMatchfinalNom.get(i).getTypeEpreuve();
                    data[i][6] = listMatchfinalNom.get(i).getNomTournoi();
                    data[i][7] = listMatchfinalNom.get(i).getCodeTournoi();

                    DefaultTable model = new DefaultTable(data, columns);
                    table1Finaliste.setModel(model);
                }
            }
        });
        textFieldRechercheJoueurNom.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String recherche = textFieldRechercheJoueurNom.getText().trim();
                String sexe = Objects.requireNonNull(SexeComboBoxRecherche.getSelectedItem()).toString();

                List<Joueur> joueurs = null;
                try {
                    joueurs = new GestionJoueur().rechercherJoueurParNomEtSexe(recherche, sexe);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                DefaultListModel<Joueur> listModel = new DefaultListModel<>();

                if (!recherche.isEmpty()) {
                    for (Joueur joueur : joueurs) {
                        listModel.addElement(joueur);
                    }
                }

                list1.setModel(listModel);
            }
        });

        SexeComboBoxRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recherche = textFieldRechercheJoueurNom.getText().trim();
                String sexe = Objects.requireNonNull(SexeComboBoxRecherche.getSelectedItem()).toString();
                List<Joueur> joueurs = new ArrayList<>();
                try {
                    Connection connection = (Connection) ConnectionManager.getConnection();
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM JOUEUR WHERE NOM LIKE ? AND SEXE = ?");
                    statement.setString(1, "%" + recherche + "%");
                    statement.setString(2, sexe);
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt("ID");
                        String nom = rs.getString("NOM");
                        String prenom = rs.getString("PRENOM");
                        Joueur joueur = new Joueur(id, nom, prenom, sexe);
                        joueurs.add(joueur);
                    }
                    rs.close();
                    statement.close();
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                DefaultListModel<Joueur> listModel = new DefaultListModel<>();

                if (!recherche.isEmpty()) {
                    for (Joueur joueur : joueurs) {
                        listModel.addElement(joueur);
                    }
                }

                list1.setModel(listModel);
            }
        });


        // Ajouter un ListSelectionListener à la JList
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                NouveauNomJoueur.setText("");
                NouveauPrenomJoueur.setText("");
                ChangeSexe.setSelectedItem("");
                textFieldIdEdit.setText("");


                Joueur joueurSelectionne = list1.getSelectedValue();


                if (joueurSelectionne != null) {
                    NouveauNomJoueur.setText(joueurSelectionne.getNom());
                    NouveauPrenomJoueur.setText(joueurSelectionne.getPrenom());
                    ChangeSexe.setSelectedItem(joueurSelectionne.getSexe());
                    textFieldIdEdit.setText(String.valueOf(joueurSelectionne.getId()));
                }
            }
        });


        textFieldRechercheJoueurPrenom.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String recherche = textFieldRechercheJoueurPrenom.getText().trim();
                String sexe = Objects.requireNonNull(SexeComboBoxRecherche.getSelectedItem()).toString();

                List<Joueur> joueurs = null;
                try {
                    joueurs = new GestionJoueur().rechercherJoueursParPrenomEtSexe(recherche, sexe);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                DefaultListModel<Joueur> listModel = new DefaultListModel<>();

                if (!recherche.isEmpty()) {
                    for (Joueur joueur : joueurs) {
                        listModel.addElement(joueur);
                    }
                }

                list1.setModel(listModel);
            }
        });

        ValiderVainqueurNom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<JoueurPerso> listMatchVainqueurNom = null;
                String[] columns = {"ID", "NOM", "PRENOM", "SEXE", "ANNEE", "TYPE_EPREUVE", "NOM", "CODE"};

                int countRawDatabe = 0;
                try {
                    countRawDatabe = new GestionMatch().getRawMatchTableVainqueur();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String[][] data = new String[countRawDatabe][8];

                try {
                    listMatchVainqueurNom = new GestionMatch().selectNomVainqueur(
                            textFieldRechercherParNomMatchVainqueur.getText());
                    System.out.println(listMatchVainqueurNom);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listMatchVainqueurNom.size(); i++) {
                    data[i][0] = String.valueOf(listMatchVainqueurNom.get(i).getId());
                    data[i][1] = listMatchVainqueurNom.get(i).getNom();
                    data[i][2] = listMatchVainqueurNom.get(i).getPrenom();
                    data[i][3] = listMatchVainqueurNom.get(i).getSexe();
                    data[i][4] = String.valueOf(listMatchVainqueurNom.get(i).getAnnee());
                    data[i][5] = listMatchVainqueurNom.get(i).getTypeEpreuve();
                    data[i][6] = listMatchVainqueurNom.get(i).getNomTournoi();
                    data[i][7] = listMatchVainqueurNom.get(i).getCodeTournoi();
                }

                DefaultTableModel model = new DefaultTableModel(data, columns);
                table1JoueurMatchVainqueur.setModel(model);
            }
        });


        ValidationRechercheEpreuve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeEpreuve = textFieldEpreuveEpreuve.getText();
                int annee = Integer.parseInt(textFieldEpreuveAnnee.getText());

                try {
                    List<Joueur> joueurs = joueur1.afficherJoueurParEpreuve(annee, typeEpreuve);

                    int countRawDatabe = 0;
                    try {
                        countRawDatabe = new GestionMatch().getRawMatchTable1();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    String[] columns = {"ID", "NOM", "PRENOM", "SEXE"};
                    String[][] data = new String[countRawDatabe][4];

                    for (int i = 0; i < joueurs.size(); i++) {
                        data[i][0] = String.valueOf(joueurs.get(i).getId());
                        data[i][1] = joueurs.get(i).getNom();
                        data[i][2] = joueurs.get(i).getPrenom();
                        data[i][3] = joueurs.get(i).getSexe();
                    }

                    DefaultTableModel model = new DefaultTableModel(data, columns);
                    table2Epreuve.setModel(model);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        textFieldRechercheTournois.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String recherche = textFieldRechercheTournois.getText().trim();

                List<Tournois> tournois = null;
                try {
                    tournois = new GestionTournois().rechercherTournoiParNom(recherche);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                DefaultListModel<Tournois> listModel = new DefaultListModel<>();

                if (!recherche.isEmpty()) {
                    for (Tournois tournois1 : tournois) {
                        listModel.addElement(tournois1);
                    }
                }

                list3Tournois.setModel(listModel);
            }
        });

        list3Tournois.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Tournois tournoisSelectionne = list3Tournois.getSelectedValue();
                    if (tournoisSelectionne != null) {
                        textFieldEditerNomTournois.setText(tournoisSelectionne.getNom());
                        textFieldEditerCodeTournois.setText(tournoisSelectionne.getCode());
                        textFieldEditerIDTournois.setText(String.valueOf(tournoisSelectionne.getId()));
                    }
                }
            }
        });


        ValiderVainqueurPrenom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<JoueurPerso> listMatchVainqueurPrenom = null;
                String[] columns = {"ID", "NOM", "PRENOM", "SEXE", "ANNEE", "TYPE_EPREUVE", "NOM", "CODE"};

                int countRawDatabe = 0;
                try {
                    countRawDatabe = new GestionMatch().getRawMatchTableVainqueur();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String[][] data = new String[countRawDatabe][8];

                try {
                    listMatchVainqueurPrenom = new GestionMatch().selectPrenomVainqueur(
                            textFieldRechercherParPrenomMatchVainqueur.getText());
                    System.out.println(listMatchVainqueurPrenom);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for (int i = 0; i < listMatchVainqueurPrenom.size(); i++) {
                    data[i][0] = String.valueOf(listMatchVainqueurPrenom.get(i).getId());
                    data[i][1] = listMatchVainqueurPrenom.get(i).getNom();
                    data[i][2] = listMatchVainqueurPrenom.get(i).getPrenom();
                    data[i][3] = listMatchVainqueurPrenom.get(i).getSexe();
                    data[i][4] = String.valueOf(listMatchVainqueurPrenom.get(i).getAnnee());
                    data[i][5] = listMatchVainqueurPrenom.get(i).getTypeEpreuve();
                    data[i][6] = listMatchVainqueurPrenom.get(i).getNomTournoi();
                    data[i][7] = listMatchVainqueurPrenom.get(i).getCodeTournoi();
                }

                DefaultTableModel model = new DefaultTableModel(data, columns);
                table1JoueurMatchVainqueur.setModel(model);
            }
        });
    }

    public static void main(String[] args) throws SQLException {
        Globalist dialog = new Globalist();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
