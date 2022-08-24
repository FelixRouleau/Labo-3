import java.io.Serializable;

public class Avion implements Serializable {

    private static final long serialVersionUID = 123456789L;

    // Attributs de classe
    String[] rayonActionCategorie = { "Court", "Moyen", "Long" };
    String[] classeSiegeCategorie = { "Première", "Affaires", "Économique" };

    // Attributs d'instance
    private String modeleAvion;
    private int maxPlaces;
    private int rayonAction; // 0 = court, 1 = moyen, 2 = long
    private int classeSiege; // 0 = premiere, 1 = affaire, 2 = Econo

    // Constructeurs

    Avion() {// par défaut

    }

    Avion(String typeAvion, int maxPlaces, int rayonAction, int classeSiege) {
        this.modeleAvion = typeAvion;
        this.maxPlaces = maxPlaces;
        this.rayonAction = rayonAction;
        this.classeSiege = classeSiege;
    }

    public String getTypeAvion() {
        return this.modeleAvion;
    }

    public int getNbPlaces() {
        return this.maxPlaces;
    }

    public int getRayonAction() {
        return this.rayonAction;
    }

    // Retourne le string associée
    public String getRayonActionString() {
        return rayonActionCategorie[this.rayonAction];
    }

    public int getClasseSiege() {
        return this.classeSiege;
    }

    public String getClasseSiegeString() {
        return classeSiegeCategorie[this.classeSiege];
    }

    public String toString() {
        String info = "Spécifications de l'avion : \n";
        return info + "Modèle : " + this.modeleAvion + "\n Capacité : " + this.maxPlaces + "\n Rayon d'action : "
                + this.getRayonActionString() + "\n Classe de siège : " + this.getClasseSiegeString();
    }

}
