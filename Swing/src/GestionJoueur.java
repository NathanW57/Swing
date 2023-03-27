import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionJoueur {
            Connection connectionT;
            public GestionJoueur() throws SQLException {
                connectionT = ConnectionManager.getConnection();
            }

            public void addUser(String nom , String prenom,String sexe){
                    try {
        PreparedStatement preparedStatement = connectionT.prepareStatement("INSERT INTO JOUEUR(NOM,PRENOM,SEXE) VALUES(?,?,?)");

       // nom = Objects.requireNonNull(TextFieldNomJoueur.getText());
        //prenom = Objects.requireNonNull(TextFieldPrenomJoueur.getText());
        //sexe = Objects.requireNonNull(sexeComboBox.getSelectedItem()).toString();

        if(!nom.equals("") && !prenom.equals("")){
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, sexe);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vous venez d'ajouter un joueur \n"+nom+" "+prenom+" de sexe "+sexe, "Information", JOptionPane.INFORMATION_MESSAGE);

        }else{
            JOptionPane.showMessageDialog(null, "Il faut remplir toutes les cases", "Information", JOptionPane.INFORMATION_MESSAGE);

        }


    } catch (
    SQLException ex) {
        throw new RuntimeException(ex);
    }}


    public void supprimerJoueur(int idJoueur, String nom, String prenom, String sexe) throws SQLException {
        try {
            // Désactiver la vérification de clé étrangère pour cette opération
            String disableFkCheck = "SET foreign_key_checks = 0";
            PreparedStatement disableFkStmt = connectionT.prepareStatement(disableFkCheck);
            disableFkStmt.executeUpdate();

            // Supprimer les enregistrements dans la table MATCH_TENNIS liés au joueur
            String deleteMatchTennis = "DELETE FROM MATCH_TENNIS WHERE ID_VAINQUEUR = ?";
            PreparedStatement deleteMatchTennisStmt = connectionT.prepareStatement(deleteMatchTennis);
            deleteMatchTennisStmt.setInt(1, idJoueur);
            deleteMatchTennisStmt.executeUpdate();

            // Supprimer le joueur de la table JOUEUR
            String deleteJoueur = "DELETE FROM JOUEUR WHERE ID = ?";
            PreparedStatement deleteJoueurStmt = connectionT.prepareStatement(deleteJoueur);
            deleteJoueurStmt.setInt(1, idJoueur);
            deleteJoueurStmt.executeUpdate();

            // Réactiver la vérification de clé étrangère
            String enableFkCheck = "SET foreign_key_checks = 1";
            PreparedStatement enableFkStmt = connectionT.prepareStatement(enableFkCheck);
            enableFkStmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }


    public void updatePlayer(String nom, String prenom, String sexe, int id){
        try {
            PreparedStatement preparedStatement = connectionT.prepareStatement("UPDATE JOUEUR SET NOM = ?, PRENOM = ?, SEXE = ? WHERE ID = ?");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, sexe);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int getRawUserTable() throws SQLException {
        String queryNbRow = "SELECT count(*) as nbRow FROM joueur";
        Statement state = connectionT.createStatement();
        ResultSet nbRow = state.executeQuery(queryNbRow);

        nbRow.next();
        int countRow = nbRow.getInt("nbRow");

        return countRow;
    }
    public List<Joueur> listAllPlayer() throws SQLException {

        List<Joueur> listPlayer = new ArrayList<>();

        String queryGetAllPlayer = "SELECT ID, NOM, PRENOM, SEXE FROM joueur;";
        return getJoueurs(listPlayer, queryGetAllPlayer);
    }

    private List<Joueur> getJoueurs(List<Joueur> listPlayer, String queryGetAllPlayer) throws SQLException {
        Statement state = connectionT.createStatement();
        ResultSet setRes = state.executeQuery(queryGetAllPlayer);

        while(setRes.next()){
            int id = setRes.getInt("ID");
            String nom = setRes.getString("NOM");
            String prenom = setRes.getString("PRENOM");
            String sexe = setRes.getString("SEXE");

            Joueur player = new Joueur(id,nom,prenom,sexe);
            listPlayer.add(player);
        }
        return listPlayer;
    }


    public List<Joueur> listAllPlayerFemale() throws SQLException {
        List<Joueur> listPlayer = new ArrayList<>();

        String queryGetAllPlayerFemale = "SELECT ID, NOM, PRENOM, SEXE FROM joueur WHERE SEXE = 'F'";
        return getJoueurs(listPlayer, queryGetAllPlayerFemale);
    }


    public List<Joueur> listAllPlayerMale() throws SQLException {
        List<Joueur> listPlayer = new ArrayList<>();

        String queryGetAllPlayerMale = "SELECT ID, NOM, PRENOM, SEXE FROM joueur WHERE SEXE = 'H'";
        return getJoueurs(listPlayer, queryGetAllPlayerMale);
    }
    public List<Joueur> afficherJoueurParEpreuve(int annee, String typeEpreuve) throws SQLException {
        try {
            // Exécuter la requête SQL pour récupérer les joueurs
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("select joueur.ID,NOM,PRENOM,joueur.SEXE from joueur inner join epreuve on epreuve.ID = joueur.ID  where ANNEE = ? and TYPE_EPREUVE = ?");
            statement.setInt(1, annee);
            statement.setString(2, typeEpreuve);
            ResultSet rs = statement.executeQuery();

            // Créer une liste pour stocker les joueurs
            List<Joueur> joueurs = new ArrayList<>();

            // Ajouter les joueurs à la liste
            WhileMethod(joueurs, connection, statement, rs);

            return joueurs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Joueur> rechercherJoueurParNomEtSexe(String nom, String sexe) throws SQLException {
        List<Joueur> joueurs = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM JOUEUR WHERE NOM LIKE ? AND SEXE = ?");
            statement.setString(1, "%" + nom + "%");
            statement.setString(2, sexe);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nomJoueur = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                Joueur joueur = new Joueur(id, nomJoueur, prenom, sexe);
                joueurs.add(joueur);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }
        return joueurs;
    }


    public List<Joueur> rechercherJoueursParPrenomEtSexe(String prenom, String sexe) throws SQLException {
        List<Joueur> joueurs = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM JOUEUR WHERE PRENOM LIKE ? AND SEXE = ?");
            statement.setString(1, "%" + prenom + "%");
            statement.setString(2, sexe);
            ResultSet rs = statement.executeQuery();
            WhileMethod(joueurs, connection, statement, rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return joueurs;
    }

    private void WhileMethod(List<Joueur> joueurs, Connection connection, PreparedStatement statement, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt("ID");
            String nomJoueur = rs.getString("NOM");
            String prenomJoueur = rs.getString("PRENOM");
            String sexeJoueur = rs.getString("SEXE");
            Joueur joueur = new Joueur(id, nomJoueur, prenomJoueur, sexeJoueur);
            joueurs.add(joueur);
        }
        rs.close();
        statement.close();
        connection.close();
    }


}
