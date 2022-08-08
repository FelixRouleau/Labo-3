public class VolRegulier extends Vol {

    // Attributs d'instance
    private Avion unAvion;
    private boolean repasFourni;
    private boolean servicePayant;
    private boolean alcool;
    private boolean divertissement;
    private boolean wiFi;

    VolRegulier(int numVol, Avion uAvion) {
        super(numVol);
        // this.unAvion = new Avion();
    }

    VolRegulier(int numVol, String dest, Date depart, int nbRes, Avion unAvion) {
        super(numVol, dest, depart, nbRes);
        this.unAvion = unAvion;
        this.repasFourni = false;
        this.servicePayant = true;
        this.alcool = true;
        this.divertissement = true;
        this.wiFi = true;

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
        String infos = "";
        infos += "\nRepas fourni : " + getRepasFourni();
        infos += "\nService Payants : " + getServicePayant();
        infos += "\nAlcool : " + getAlcool();
        infos += "\nDivertissement : " + getDivertissement();
        infos += "\nWi-Fi : " + getWiFi();
        return infos;
    }

    public String toString() {
        return super.toString();// + unAvion.toString() + getInfo();
    }

}
