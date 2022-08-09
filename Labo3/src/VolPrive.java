public class VolPrive extends Vol {
    // Attributs d'instance
    private Avion unAvion;
    private boolean repasFourni;
    private boolean servicePayant;
    private boolean alcool;
    private boolean divertissement;
    private boolean wiFi;
    private boolean danseuse;
    private boolean fumeur;

    VolPrive(int numVol, Avion unAvion) {
        super(numVol, unAvion);
        // this.unAvion = new Avion();
    }

    VolPrive(int numVol, String dest, Date depart, int nbRes, Avion unAvion) {
        super(numVol, dest, depart, nbRes, unAvion);
        this.unAvion = unAvion;
        this.repasFourni = true;
        this.servicePayant = false;
        this.alcool = true;
        this.divertissement = true;
        this.wiFi = true;
        this.danseuse = true;
        this.fumeur = true;

    }

    public Avion getAvion() {
        return this.unAvion;
    }

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
            return "Oui (Inclus)";
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

    public String getDanseuse() {
        if (!this.danseuse) {
            return "Non";
        } else {
            return "Oui";
        }
    }

    public String getFumeur() {
        if (!this.fumeur) {
            return "Non";
        } else {
            return "Oui";
        }
    }

    public String getInfo() {
        String infos = "Accomodations sur ce vol : \n";
        infos += "Repas fourni : " + getRepasFourni();
        infos += "    Service Payants : " + getServicePayant();
        infos += "    Alcool : " + getAlcool();
        infos += "    Divertissement : " + getDivertissement();
        infos += "    Wi-Fi : " + getWiFi();
        infos += "    Danseuse : " + getDanseuse();
        infos += "    Fumeur : " + getFumeur() + "\n\n";
        return infos;
    }

    public String toString() {
        return super.toString() + getInfo();// + unAvion.toString() + getInfo();
    }
}
