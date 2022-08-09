import java.io.Serializable;

public class Vol implements Serializable, Comparable<Vol> {

    private static final long serialVersionUID = 13006660013L;

    // attributs de classe
    public static int nbVols = 0;

    // attributs d'instance
    protected int numVol, nbRes;
    protected String destination;
    protected Date depart;
    protected Avion unAvion;

    // constructeurs
    Vol(int numVol, Avion unAvion) {
        this.numVol = numVol;
        nbVols++;
    }

    Vol(int numVol, String dest, Date depart, int nbRes, Avion unAvion) {
        this.unAvion = unAvion;
        this.numVol = numVol;
        setDestination(dest);
        this.depart = depart;
        setNbRes(nbRes);
        nbVols++;
    }

    // accesseurs
    public int getNumVol() {
        return this.numVol;
    }

    public String getDestination() {
        return this.destination;
    }

    public Date getDepart() {
        return this.depart;
    }

    public int getNbRes() {
        return this.nbRes;
    }

    public Avion getAvion() {
        return this.unAvion;
    }

    // mutateurs
    public void setDestination(String uneDestination) {
        if (uneDestination.length() > 20) {
            this.destination = uneDestination.substring(0, 20);
        } else {
            this.destination = uneDestination;
        }
    }

    public void setDepart(Date uneDate) {
        this.depart = uneDate;
    }

    public void setNbRes(int unNbRes) {
        if (unNbRes >= 0) {
            this.nbRes = unNbRes;
        } else {
            System.out.println("nombre de réservations invalide");
        }
    }

    // autres méthodes
    // méthode pour écriture formatée dans le fichier
    public String ecritureFichier() {
        return this.numVol + ";" + this.destination + ";" +
                this.depart.ecritureFichier() + ";" + this.nbRes + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        Vol autreVol = (Vol) obj;
        if (this.numVol == autreVol.getNumVol()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.destination.length() < 15) {
            return this.numVol + "\t" + this.destination + "\t\t" +
                    this.depart.toString() + "\t" + this.nbRes + "\n";
            // } else if (this.destination.length() < 15) {
            // return this.numVol + "\t" + this.destination + "\t\t" +
            // this.depart.toString() + "\t" + this.nbRes + "\n";
        } else {
            return this.numVol + "\t" + this.destination + "\t" +
                    this.depart.toString() + "\t" + this.nbRes + "\n";
        }
    }

    public String retireVolTxt() {
        return "Le vol numéro "+ this.getNumVol()+  " à destination de " + this.getDestination() + " a été retiré.";
    }

    public String modifierDateTxt() {
        return "La date de départ pour le vol numéro "+ this.getNumVol()+  " à été modifié a " + this.getDepart();
    }

    public String ajouterVolTxt() {
        return "Le vol numéro "+ this.getNumVol()+  " à destination de " + this.getDestination() + " a été ajouté.";
    }

    public String reserverVolTxt(int nbReserve) {
        return "Vous avez pris " + nbReserve + " pour le vol numéro "+ this.getNumVol()+  " à destination de " + this.getDestination();
    }

    @Override
    public int compareTo(Vol unVol) {
        return this.numVol - unVol.getNumVol();
    }

}
