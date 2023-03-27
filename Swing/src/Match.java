import java.util.List;

public class Match {
    private int id;
    private int idEpreuve;
    private int idVainqueur;
    private int idFinaliste;

    private Joueur joueurs;

    public Joueur getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(Joueur joueurs) {
        this.joueurs = joueurs;
    }

    public Match(int idVainqueur, Joueur joueurs) {
        this.idVainqueur = idVainqueur;
        this.joueurs = joueurs;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEpreuve() {
        return idEpreuve;
    }

    public void setIdEpreuve(int idEpreuve) {
        this.idEpreuve = idEpreuve;
    }

    public int getIdVainqueur() {
        return idVainqueur;
    }

    public void setIdVainqueur(int idVainqueur) {
        this.idVainqueur = idVainqueur;
    }

    public int getIdFinaliste() {
        return idFinaliste;
    }

    public void setIdFinaliste(int idFinaliste) {
        this.idFinaliste = idFinaliste;
    }
}
