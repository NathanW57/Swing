import javax.swing.*;
import java.sql.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionTournois {

            Connection connection;

            public GestionTournois() throws SQLException {
                connection = ConnectionManager.getConnection();
            }

            public void addTournois(String nom , String code)  {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tournoi(NOM,CODE) VALUES(?,?)");
                    if(!nom.equals("") && !code.equals("")){
                        preparedStatement.setString(1,nom);
                        preparedStatement.setString(2,code);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Vous venez d'ajouter un tournoi \n"+nom+" "+code, "Information", JOptionPane.INFORMATION_MESSAGE);

                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Il faut remplir toutes les cases", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

                }

    public List<Tournois> listAllTournoi() throws SQLException {
        List<Tournois> listTournoi = new ArrayList<>();

        String queryGetAllTournoi = "SELECT id, nom, code FROM tournoi";
        Statement state = connection.createStatement();
        ResultSet setRes = state.executeQuery(queryGetAllTournoi);

        whileMethod(listTournoi, setRes);
        return listTournoi;
    }

    public int getRawTournoiTable() throws SQLException {
        String queryNbRow = "SELECT count(*) as nbRow FROM tournoi";
        Statement state = connection.createStatement();
        ResultSet nbRow = state.executeQuery(queryNbRow);

        nbRow.next();

        return nbRow.getInt("nbRow");
    }


    public void deleteTournoi(String nom , String code,int id ) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tournoi WHERE NOM = ? AND CODE = ? AND tournoi.ID = ?");
        System.out.println(preparedStatement);

        if(!nom.equals("") && !code.equals("")){
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, code);
            preparedStatement.setInt(3,id);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vous venez de supprimer le tournoi \n"+nom+" "+code, "Information", JOptionPane.INFORMATION_MESSAGE);

        }
        else{
            JOptionPane.showMessageDialog(null, "Il faut remplir toutes les cases", "Information", JOptionPane.INFORMATION_MESSAGE);

        }

}

    public void updateTournoi(String nom , String code, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tournoi SET NOM = ?,CODE = ? WHERE ID=?");
            if (!nom.equals("") && !code.equals("")) {
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, code);
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Vous venez de modifier le tournoi avec l'id : "+id, "Information", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "Il faut remplir toutes les cases", "Information", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<Tournois> rechercherTournoiParNom(String nom) throws SQLException {
        List<Tournois> tournois = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT id, nom, code FROM tournoi WHERE LOWER(nom) LIKE ?");
            statement.setString(1, "%" + nom + "%");
            ResultSet rs = statement.executeQuery();
            whileMethod(tournois, rs);
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }
        return tournois;
    }

    private void whileMethod(List<Tournois> tournois, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt("ID");
            String nomJoueur = rs.getString("NOM");
            String code = rs.getString("CODE");
            Tournois tournoi = new Tournois(id, nomJoueur,code);
            tournois.add(tournoi);
        }
    }


}
