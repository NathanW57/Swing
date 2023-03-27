public class JoueurPerso {
    private int id;
    private String nom;
    private String prenom;
    private String sexe;
    private int annee;
    private String typeEpreuve;
    private String nomTournoi;
    private String codeTournoi;

    public JoueurPerso(int id, String nom, String prenom, String sexe, int annee, String typeEpreuve, String nomTournoi, String codeTournoi) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.annee = annee;
        this.typeEpreuve = typeEpreuve;
        this.nomTournoi = nomTournoi;
        this.codeTournoi = codeTournoi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getTypeEpreuve() {
        return typeEpreuve;
    }

    public void setTypeEpreuve(String typeEpreuve) {
        this.typeEpreuve = typeEpreuve;
    }

    public String getNomTournoi() {
        return nomTournoi;
    }

    public void setNomTournoi(String nomTournoi) {
        this.nomTournoi = nomTournoi;
    }

    public String getCodeTournoi() {
        return codeTournoi;
    }

    public void setCodeTournoi(String codeTournoi) {
        this.codeTournoi = codeTournoi;
    }
}
