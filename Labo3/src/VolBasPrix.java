
public class VolBasPrix extends Vol {

    // Attributs d'instance
    private Avion unAvion;
    private boolean repasFourni;
    private boolean servicePayant;
    private boolean alcool;
    private boolean divertissement;
    private boolean wiFi;

    VolBasPrix(int numVol) {
        super(numVol);
        // this.unAvion = new Avion();
    }

    VolBasPrix(int numVol, String dest, Date depart, int nbRes, Avion unAvion) {
        super(numVol, dest, depart, nbRes, unAvion);
        this.repasFourni = false;
        this.servicePayant = true;
        this.alcool = true;
        this.divertissement = false;
        this.wiFi = false;
    }

    // public Avion getAvion() {
    // return this.unAvion;
    // }

    public String getRepasFourni() {
        if (!this.repasFourni) {
            return "Non";
        } else {
            return "Oui";
        }

    }

    public String getServicePayant() {
        if (!this.servicePayant) {
            return "Non";
        } else {
            return "Oui";
        }
    }

    public String getAlcool() {
        if (!this.alcool) {
            return "Non";
        } else {
            return "Oui (Payant)";
        }
    }

    public String getDivertissement() {
        if (!this.divertissement) {
            return "Non";
        } else {
            return "Oui";
        }
    }

    public String getWiFi() {
        if (!this.wiFi) {
            return "Non";
        } else {
            return "Oui";
        }
    }

    public String getInfo() {
        String infos = "Accomodations sur ce vol : ";
        infos += "\nRepas fourni : " + getRepasFourni();
        infos += "\tService Payants : " + getServicePayant();
        infos += "\tAlcool : " + getAlcool();
        infos += "\tDivertissement : " + getDivertissement();
        infos += "\tWi-Fi : " + getWiFi() + "\n\n";
        return infos;
    }

    public String toString() {
        return super.toString() + getInfo(); // + unAvion.toString() + getInfo();
    }

}
