import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionMatch {
    Connection connection;

    public GestionMatch() throws SQLException {
        connection = ConnectionManager.getConnection();
    }

    public List<JoueurPerso> selectAllMatchVainqueur() throws SQLException {
        String queryGetAllMatchVainqueur = "select * from joueur inner join match_tennis mt on joueur.ID = mt.ID_VAINQUEUR inner join epreuve e on mt.ID_EPREUVE = e.ID inner join score_vainqueur sv on mt.ID = sv.ID_MATCH\n" +
                "                inner join tournoi on e.ID_TOURNOI = tournoi.ID ORDER BY ANNEE,joueur.NOM ASC";
        return getJoueurPersos(queryGetAllMatchVainqueur);

    }

    private List<JoueurPerso> getJoueurPersos(String queryGetAllMatch) throws SQLException {
        Statement state = connection.createStatement();
        ResultSet setRes = state.executeQuery(queryGetAllMatch);
        List<JoueurPerso> listPlayer = new ArrayList<>();

        while(setRes.next()){
            int id = setRes.getInt("ID");
            String nom = setRes.getString("NOM");
            String prenom = setRes.getString("PRENOM");
            String sexe = setRes.getString("SEXE");
            int annee = setRes.getInt("ANNEE");
            String typeEpreuve = setRes.getString("TYPE_EPREUVE");
            String nomTournoi = setRes.getString("TOURNOI.NOM");
            String codeTournoi = setRes.getString("CODE");

            JoueurPerso player = new JoueurPerso(id,nom,prenom,sexe,annee,typeEpreuve,nomTournoi,codeTournoi);
            listPlayer.add(player);
        }
        return listPlayer;
    }

    public List<JoueurPerso> selectAllMatchFinaliste() throws SQLException {
        String queryGetAllMatch = " select * from joueur inner join match_tennis mt on joueur.ID = mt.ID_FINALISTE\n" +
                "inner join epreuve e on mt.ID_EPREUVE = e.ID\n" +
                "inner join score_vainqueur sv on mt.ID = sv.ID_MATCH\n" +
                "inner join tournoi on e.ID_TOURNOI = tournoi.ID ORDER BY ANNEE,joueur.NOM ASC";
        return getJoueurPersos(queryGetAllMatch);

    }


    public List<JoueurPerso> selectPrenom(String prenom) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from joueur inner join match_tennis mt on joueur.ID = mt.ID_FINALISTE " +
                "inner join epreuve e on mt.ID_EPREUVE = e.ID " +
                "inner join score_vainqueur sv on mt.ID = sv.ID_MATCH " +
                "inner join tournoi on e.ID_TOURNOI = tournoi.ID WHERE PRENOM = ? ORDER BY ANNEE ASC ");
        preparedStatement.setString(1,prenom);
        ResultSet rs = preparedStatement.executeQuery();

        List<JoueurPerso> listPlayer = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt("ID");
            String nom = rs.getString("NOM");
            prenom = rs.getString("PRENOM");
            String sexe = rs.getString("SEXE");
            int annee = rs.getInt("ANNEE");
            String typeEpreuve = rs.getString("TYPE_EPREUVE");
            String nomTournoi = rs.getString("TOURNOI.NOM");
            String codeTournoi = rs.getString("CODE");

            JoueurPerso player = new JoueurPerso(id,nom,prenom,sexe,annee,typeEpreuve,nomTournoi,codeTournoi);
            listPlayer.add(player);

        }
        return listPlayer;
    }

    public List<JoueurPerso> selectNom(String nom) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from joueur inner join match_tennis mt on joueur.ID = mt.ID_FINALISTE " +
                "inner join epreuve e on mt.ID_EPREUVE = e.ID " +
                "inner join score_vainqueur sv on mt.ID = sv.ID_MATCH " +
                "inner join tournoi on e.ID_TOURNOI = tournoi.ID WHERE joueur.NOM = ? ORDER BY ANNEE ASC ");
        preparedStatement.setString(1,nom);
        ResultSet rs = preparedStatement.executeQuery();

        List<JoueurPerso> listPlayer = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt("ID");
            nom = rs.getString("NOM");
            String prenom = rs.getString("PRENOM");
            String sexe = rs.getString("SEXE");
            int annee = rs.getInt("ANNEE");
            String typeEpreuve = rs.getString("TYPE_EPREUVE");
            String nomTournoi = rs.getString("TOURNOI.NOM");
            String codeTournoi = rs.getString("CODE");

            JoueurPerso player = new JoueurPerso(id,nom,prenom,sexe,annee,typeEpreuve,nomTournoi,codeTournoi);
            listPlayer.add(player);

        }
        return listPlayer;
    }

    public int getRawMatchTableVainqueur() throws SQLException {
        String queryNbRow = " select count(*) as nbRow from joueur inner join match_tennis mt on joueur.ID = mt.ID_VAINQUEUR inner join epreuve e on mt.ID_EPREUVE = e.ID inner join score_vainqueur sv on mt.ID = sv.ID_MATCH\n" +
        "                inner join tournoi on e.ID_TOURNOI = tournoi.ID ORDER BY ANNEE,joueur.NOM ASC";
        Statement state = connection.createStatement();
        ResultSet nbRow = state.executeQuery(queryNbRow);

        nbRow.next();

        return nbRow.getInt("nbRow");
    }

    public int getRawMatchTableFinaliste() throws SQLException {
        String queryNbRow = " select count(*) as nbRow from joueur inner join match_tennis mt on joueur.ID = mt.ID_FINALISTE\n" +
                "inner join epreuve e on mt.ID_EPREUVE = e.ID\n" +
                "inner join score_vainqueur sv on mt.ID = sv.ID_MATCH\n" +
                "inner join tournoi t on e.ID_TOURNOI = t.ID";
        Statement state = connection.createStatement();
        ResultSet nbRow = state.executeQuery(queryNbRow);

        nbRow.next();

        return nbRow.getInt("nbRow");
    }

    public List<JoueurPerso> selectNomVainqueur(String nom) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from joueur inner join match_tennis mt on joueur.ID = mt.ID_VAINQUEUR " +
                "inner join epreuve e on mt.ID_EPREUVE = e.ID " +
                "inner join score_vainqueur sv on mt.ID = sv.ID_MATCH " +
                "inner join tournoi on e.ID_TOURNOI = tournoi.ID WHERE joueur.NOM = ? ORDER BY ANNEE ASC ");
        preparedStatement.setString(1,nom);
        ResultSet rs = preparedStatement.executeQuery();

        List<JoueurPerso> listPlayer = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt("ID");
            nom = rs.getString("NOM");
            String prenom = rs.getString("PRENOM");
            String sexe = rs.getString("SEXE");
            int annee = rs.getInt("ANNEE");
            String typeEpreuve = rs.getString("TYPE_EPREUVE");
            String nomTournoi = rs.getString("TOURNOI.NOM");
            String codeTournoi = rs.getString("CODE");

            JoueurPerso player = new JoueurPerso(id,nom,prenom,sexe,annee,typeEpreuve,nomTournoi,codeTournoi);
            listPlayer.add(player);

        }
        return listPlayer;
    }

    public List<JoueurPerso> selectPrenomVainqueur(String prenom) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from joueur inner join match_tennis mt on joueur.ID = mt.ID_VAINQUEUR " +
                "inner join epreuve e on mt.ID_EPREUVE = e.ID " +
                "inner join score_vainqueur sv on mt.ID = sv.ID_MATCH " +
                "inner join tournoi on e.ID_TOURNOI = tournoi.ID WHERE joueur.PRENOM = ? ORDER BY ANNEE ASC ");
        preparedStatement.setString(1,prenom);
        ResultSet rs = preparedStatement.executeQuery();

        List<JoueurPerso> listPlayer = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt("ID");
            String nom = rs.getString("NOM");
            prenom = rs.getString("PRENOM");
            String sexe = rs.getString("SEXE");
            int annee = rs.getInt("ANNEE");
            String typeEpreuve = rs.getString("TYPE_EPREUVE");
            String nomTournoi = rs.getString("TOURNOI.NOM");
            String codeTournoi = rs.getString("CODE");

            JoueurPerso player = new JoueurPerso(id,nom,prenom,sexe,annee,typeEpreuve,nomTournoi,codeTournoi);
            listPlayer.add(player);

        }
        return listPlayer;
    }
    public List<Joueur> selectByYears(String epreuve,int annee) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select joueur.ID,NOM,PRENOM,SEXE from joueur inner join epreuve on epreuve.ID = joueur.ID  where ANNEE = ? and TYPE_EPREUVE = ?");
        preparedStatement.setInt(1,annee);
        preparedStatement.setString(2,epreuve);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Joueur> listPlayer = new ArrayList<>();

        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("NOM");
            String prenom = resultSet.getString("PRENOM");
            String sexe = resultSet.getString("SEXE");

            Joueur player = new Joueur(id,nom,prenom,sexe);
            listPlayer.add(player);

        }
        return listPlayer;
    }


    public int getRawMatchTable1() throws SQLException {
        String queryNbRow = " select count(*) as nbRow from joueur inner join epreuve on epreuve.ID = joueur.ID  ";
        Statement state = connection.createStatement();
        ResultSet nbRow = state.executeQuery(queryNbRow);

        nbRow.next();

        return nbRow.getInt("nbRow");
    }

    public List<JoueurPerso> selectNomVainqueurs(String nom) throws SQLException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT J.ID, J.NOM, J.PRENOM, J.SEXE, E.ANNEE, E.TYPE_EPREUVE, T.NOM, T.CODE " +
                            "FROM ((match_tennis M " +
                            "INNER JOIN JOUEUR J ON M.ID_VAINQUEUR = J.ID) " +
                            "INNER JOIN EPREUVE E ON M.ID_EPREUVE = E.ID) " +
                            "INNER JOIN TOURNOI T ON E.ID_TOURNOI = T.ID " +
                            "WHERE J.NOM LIKE ?"
            );
            preparedStatement.setString(1, "%" + nom + "%");
            ResultSet rs = preparedStatement.executeQuery();
            List<JoueurPerso> listMatchVainqueurNom = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nomJoueur = rs.getString("NOM");
                String prenomJoueur = rs.getString("PRENOM");
                String sexeJoueur = rs.getString("SEXE");
                int annee = rs.getInt("ANNEE");
                String typeEpreuve = rs.getString("TYPE_EPREUVE");
                String nomTournoi = rs.getString("NOM");
                String codeTournoi = rs.getString("CODE");
                JoueurPerso joueur = new JoueurPerso(id, nomJoueur, prenomJoueur, sexeJoueur, annee, typeEpreuve, nomTournoi, codeTournoi);
                listMatchVainqueurNom.add(joueur);
            }
            rs.close();
            preparedStatement.close();
            connection.close();
            return listMatchVainqueurNom;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    }


