public class Tournois {
    private String nom;
    private String code;
    private int id;


    public Tournois(int id,String nom, String code) {
        this.id = id;
        this.nom = nom;
        this.code = code;

    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "ID : "+id+" Nom : "+nom+" Code : "+code;
    }

}
