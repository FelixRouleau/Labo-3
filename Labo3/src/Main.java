import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

public class Main {

    static final int MAX_VOLS = 36;
    static final String COMPAGNIE = "CIE AIR RELAX";
    static final String FICHIER_VOLS_TXT = "src/Documents/Cie_Air_Relax.txt";
    static final String FICHIER_VOLS_OBJ = "src/Documents/Cie_Air_Relax.obj";
    static JTextArea sortie;
    static ArrayList<Avion> flotteCompagnie = new ArrayList<Avion>();
    static Map<Integer, Vol> listeMapVols = new HashMap<Integer, Vol>();

    public static void main(String[] args) throws Exception {

        int choix;
        creerFlotte();
        chargerFichierPourUnMap();

        do {
            choix = menu();
            switch (choix) {
                case 1:
                    listerVols();
                    break;
                case 2:
                    // insererVol();
                    break;
                case 3:
                    // retirerVol();
                    break;
                case 4:
                    // modifierDate();
                    break;
                case 5:
                    // reserverVol();
                    break;
                case 6:
                    Utilitaires.sauvegarderFichierObjets(listeMapVols, FICHIER_VOLS_OBJ);
                    JOptionPane.showMessageDialog(null,
                            "Merci d'avoir utilisé le sytème de gestion de vols " +
                                    "de " + COMPAGNIE + "!",
                            "Au Revoir",
                            JOptionPane.PLAIN_MESSAGE);
                    break;
                default:
                    afficherMsg("Choix invalide!", "ATTENTION");
            }
        } while (choix != 6);
        //
        // System.exit(0);

    }

    // méthodes privées
    private static void afficherMsg(String msg, String titre) {
        JOptionPane.showMessageDialog(null, msg, titre,
                JOptionPane.PLAIN_MESSAGE);
    }

    private static int menu() {
        String msg = "                       GESTION DES VOLS\n" +
                "1. Liste des vols\n" +
                "2. Ajout d'un vol\n" +
                "3. Retrait d'un vol\n" +
                "4. Modification de la date de départ\n" +
                "5. Réservation d'un vol\n" +
                "6. Sauvegarder et terminer\n" +
                "                       Faites votre choix: ";
        try {
            int choix = Integer.parseInt(JOptionPane.showInputDialog(null, msg,
                    COMPAGNIE, JOptionPane.PLAIN_MESSAGE));
            return choix;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void chargerFichierPourUnMap() {
        try {
            File f = new File(FICHIER_VOLS_OBJ);

            if (f.exists()) {
                listeMapVols = (HashMap<Integer, Vol>) Utilitaires.chargerFichierObjets(FICHIER_VOLS_OBJ);
            } else {
                ArrayList<ArrayList<String>> listeAttributs = Utilitaires.chargerFichierTexte(FICHIER_VOLS_TXT,
                        ";");
                listeAttributs.forEach((donneesLivre) -> {
                    char typeVol;
                    int numVol;
                    String dest;
                    Date depart;
                    int nbRes;
                    typeVol = donneesLivre.get(0).charAt(0);
                    numVol = Integer.parseInt(donneesLivre.get(1));
                    dest = donneesLivre.get(2);
                    depart = convertDateFichierTxt(donneesLivre.get(3));
                    nbRes = Integer.parseInt(donneesLivre.get(4));
                    switch (typeVol) {
                        case 'R':
                            listeMapVols.put(numVol,
                                    new VolRegulier(numVol, dest, depart, nbRes, flotteCompagnie.get(1)));
                            break;
                        case 'B':
                            listeMapVols.put(numVol,
                                    new VolBasPrix(numVol, dest, depart, nbRes, flotteCompagnie.get(0)));
                            break;
                        case 'C':
                            listeMapVols.put(numVol,
                                    new VolCharter(numVol, dest, depart, nbRes, flotteCompagnie.get(2)));
                            break;
                        case 'P':
                            listeMapVols.put(numVol,
                                    new VolPrive(numVol, dest, depart, nbRes, flotteCompagnie.get(3)));
                            break;
                    }

                });
                listeMapVols = ((TreeMap<Integer, Vol>) listeMapVols).descendingMap();//
                // Notre Map en ordre décroissant
            }
        } catch (Exception e) {
            System.out.println("Problème");
        }
    }

    public static Date convertDateFichierTxt(String donnees) {
        Date depart;
        String elemsDate[] = new String[3];
        elemsDate = donnees.split(" ");
        depart = new Date(Integer.parseInt(elemsDate[0]), Integer.parseInt(elemsDate[1]),
                Integer.parseInt(elemsDate[2]));
        return depart;
    }

    public static void creerFlotte() {
        Avion avionBasPrix = new Avion("Airbus A320", 280, 1, 2);
        Avion avionRegulier = new Avion("Boeing 737", 340, 2, 2);
        Avion avionCharter = new Avion("Hawker 800xp", 8, 1, 1);
        Avion avionPrive = new Avion("Cessna Caravan", 14, 1, 0);
        Avion avionRegulierCourrierCourt = new Avion("Boeing 737 MAX 8", 210, 0, 2); // Avion pour la courte distance
        flotteCompagnie.add(avionBasPrix);
        flotteCompagnie.add(avionRegulier);
        flotteCompagnie.add(avionCharter);
        flotteCompagnie.add(avionPrive);
        flotteCompagnie.add(avionRegulierCourrierCourt);
    }

    public static void afficherEntete() {
        sortie = new JTextArea(20, 80);
        sortie.setFont(new Font("monospace", Font.PLAIN, 12));
        sortie.append("\t\tLISTE DES VOLS\n\n");
        sortie.append("#\tDESTINATION\t\tDÉPART\t# RÉSERVATIONS\n");

    }

    public static void listerVols() {
        int choix;

        choix = Integer
                .parseInt(JOptionPane.showInputDialog(null, "Entrez votre choix", "",
                        JOptionPane.PLAIN_MESSAGE));

        if (choix == 1) {
            for (Integer cle : listeMapVols.keySet()) {
                sortie.append(listeMapVols.get(cle).toString());
            }

            sortie.append("Nombre de vols = " + listeMapVols.size());
            JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
        } else {
            new MyFrame();
        }

    }

}
