import java.util.*;
import java.util.Map.Entry;

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
        chargerVols();

        do {
            choix = menu();
            switch (choix) {
                case 1:
                    listerVols();
                    break;
                case 2:
                    ajouterVol();
                    break;
                case 3:
                    retirerVol();
                    break;
                case 4:
                    modifierDate();
                    break;
                case 5:
                    reserverVol();
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

    public static void chargerVols() {
        try {
            File f = new File(FICHIER_VOLS_OBJ);

            if (f.exists()) {
                listeMapVols = (HashMap<Integer, Vol>) Utilitaires.chargerFichierObjets(FICHIER_VOLS_OBJ);
            } else {
                ArrayList<ArrayList<String>> listeAttributs = Utilitaires.chargerFichierTexte(FICHIER_VOLS_TXT,
                        ";");
                listeAttributs.forEach((donneesVols) -> {
                    char typeVol;
                    int numVol;
                    String dest;
                    Date depart;
                    int nbRes;
                    typeVol = donneesVols.get(0).charAt(0);
                    numVol = Integer.parseInt(donneesVols.get(1));
                    dest = donneesVols.get(2);
                    depart = convertDateFichierTxt(donneesVols.get(3));
                    nbRes = Integer.parseInt(donneesVols.get(4));
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

        // ajout des avions a l'ArrayList flotteCompagnie
        flotteCompagnie.add(avionBasPrix);
        flotteCompagnie.add(avionRegulier);
        flotteCompagnie.add(avionCharter);
        flotteCompagnie.add(avionPrive);
        flotteCompagnie.add(avionRegulierCourrierCourt);
    }

    public static void afficherEntete() {
        sortie = new JTextArea(30, 80);
        sortie.setFont(new Font("monospace", Font.PLAIN, 12));
        sortie.append("\t\tLISTE DES VOLS\n\n");
        sortie.append("# VOL\tDESTINATION\t\tDÉPART\tNOMBRE DE RÉSERVATIONS\n\n");

    }

    public static int nbVols;

    public static void listerVols() {
        JScrollPane scrollPane = new JScrollPane();
        int choix;

        String msg = "Sélectionner comment vous voulez afficher les vols\n";
        msg += "1- Lister tous les vols\n2- Lister les vols bas prix\n3- Lister les vols réguliers\n4- Lister les vols charter\n5- Lister les vols privé\n6- Quitter";
        msg += "\nEntrez votre choix";

        
        do {
            choix = Integer
                .parseInt(JOptionPane.showInputDialog(null, msg, "",
                        JOptionPane.PLAIN_MESSAGE));
        switch(choix) {
            case 1:
                sortie = new JTextArea();
                listerTout();
                if(listeMapVols.size() > 5){
                    scrollPane.setViewportView(sortie);
                    JOptionPane.showMessageDialog(null, scrollPane, null, JOptionPane.PLAIN_MESSAGE);
                }else {
                JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
                }
                break;
            
            case 2:
                sortie = new JTextArea();
                listerBasPrix();
                JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
                break;
            
            case 3:
                sortie = new JTextArea();
                listerRegulier();
                JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
                break;

            case 4:
                sortie = new JTextArea();
                listerCharter();
                JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
                break;

            case 5:
                sortie = new JTextArea();
                listerPrive();
                    // scrollPane.setViewportView(sortie);
                    // JOptionPane.showMessageDialog(null, scrollPane, null, JOptionPane.PLAIN_MESSAGE);
                    JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
                break;
            
            case 6:
                break;

            default :
                afficherMsg("Choix invalide", "Erreur");
                break;
            
            
        }
    }while(choix !=6);
    }

    //Méthodes pour rechercher un vol avec son numéro de vol qui est aussi la clé du HashMap

    public static boolean rechercherVol(int numVol) {
        boolean keyExist = false;

        if(listeMapVols.containsKey(numVol)){
            keyExist = true;
        }else{
            keyExist = false;
        }
        return keyExist;
     }

    // Début des méthodes pour lister tout les types de vols

    public static void listerTout() {
        TreeMap<Integer, Vol> listHashMapTrie = new TreeMap<>(listeMapVols); // Convert HashMap to TreeMap
        Set<Entry<Integer, Vol>> mappings = listHashMapTrie.entrySet(); // Returns the ordered TreeMap too a Set (So no duplicate) in ascending order
        nbVols = 0;
        afficherEntete();
            for (Entry<Integer, Vol> mapping : mappings) {
                    sortie.append(mapping.getValue().toString());
                    nbVols++;
            }
            sortie.append("Nombre de vols = " + nbVols);
    }
    

    public static void listerBasPrix() {
        nbVols = 0;
        afficherEntete();
            for (Integer cle : listeMapVols.keySet()) {
                if(listeMapVols.get(cle) instanceof VolBasPrix){
                    sortie.append(listeMapVols.get(cle).toString());
                    nbVols++;
                }
            }
            sortie.append("Nombre de vols = " + nbVols);
    }

    public static void listerRegulier() {
        nbVols = 0;
        afficherEntete();
            for (Integer cle : listeMapVols.keySet()) {
                if(listeMapVols.get(cle) instanceof VolRegulier){
                    sortie.append(listeMapVols.get(cle).toString());
                    nbVols++;
                }
            }
            sortie.append("Nombre de vols = " + nbVols);
    }

    public static void listerCharter() {
        nbVols = 0;
        afficherEntete();
            for (Integer cle : listeMapVols.keySet()) {
                if(listeMapVols.get(cle) instanceof VolCharter){
                    sortie.append(listeMapVols.get(cle).toString());
                    nbVols++;
                }
            }
            sortie.append("Nombre de vols = " + nbVols);
    }

    public static void listerPrive() {
        nbVols = 0;
        afficherEntete();
            for (Integer cle : listeMapVols.keySet()) {
                if(listeMapVols.get(cle) instanceof VolPrive){
                    sortie.append(listeMapVols.get(cle).toString());
                    nbVols++;
                }
            }
            sortie.append("Nombre de vols = " + nbVols);
            
    }

    // Fin des méthodes pour lister les vols


    public static void listerVolsRadioButtons() {
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

    // Début des méthodes pour l'ajout d'un vol

    public static void ajouterVol() {
        int numVol;
        int typeVol;
        int option;
        String msgTypeVol = "Quel type de vol voulez vous ajouter :\n";
        msgTypeVol += "1- Bas prix\n2- Régulier\n3- Chartered\n4- Privé";

        JTextField destination = new JTextField();
        JTextField jour = new JTextField();
        JTextField mois = new JTextField();
        JTextField annee = new JTextField();
        JTextField nbRes = new JTextField();
        Object[] message = {"Destination",destination, 
                            "Nombre de réservation",nbRes,
                            "Date de départ\n",
                            "Jour", jour,
                            "Mois", mois,
                            "Année", annee};

        numVol = Integer.parseInt(JOptionPane.showInputDialog(null, "Entrez le numéro de vol à ajouter", "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE));

        if(!rechercherVol(numVol)){
            typeVol = Integer.parseInt(JOptionPane.showInputDialog(null, msgTypeVol, "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE));
            switch(typeVol){
                case 1:
                    option = JOptionPane.showConfirmDialog(null, message, "AJOUT D'UN VOL", JOptionPane.OK_CANCEL_OPTION);
                    if(option == JOptionPane.OK_OPTION){
                        Date dateDepart = new Date(Integer.parseInt(jour.getText()), Integer.parseInt(mois.getText()), Integer.parseInt(annee.getText()));
                        listeMapVols.put(numVol, new VolBasPrix(numVol, destination.getText(), dateDepart, Integer.parseInt(nbRes.getText()), flotteCompagnie.get(0)));
                    }  
                    break; 
                case 2:
                    option = JOptionPane.showConfirmDialog(null, message, "AJOUT D'UN VOL", JOptionPane.OK_CANCEL_OPTION);
                    if(option == JOptionPane.OK_OPTION){
                        // Random un type d'avion régulier pour le vol régulier. Soit l'index 1 ou 4 du ArrayList flotteCompagnie
                        Random r = new Random();
                        int[] randomRegularFlight = new int[] {1, 4};
                        int indexAvion = r.nextInt(randomRegularFlight.length);

                        Date dateDepart = new Date(Integer.parseInt(jour.getText()), Integer.parseInt(mois.getText()), Integer.parseInt(annee.getText()));
                        listeMapVols.put(numVol, new VolRegulier(numVol, destination.getText(), dateDepart, Integer.parseInt(nbRes.getText()), flotteCompagnie.get(randomRegularFlight[indexAvion])));
                    }
                    break;
                case 3:
                    option = JOptionPane.showConfirmDialog(null, message, "AJOUT D'UN VOL", JOptionPane.OK_CANCEL_OPTION);
                    if(option == JOptionPane.OK_OPTION){
                        Date dateDepart = new Date(Integer.parseInt(jour.getText()), Integer.parseInt(mois.getText()), Integer.parseInt(annee.getText()));
                        listeMapVols.put(numVol, new VolCharter(numVol, destination.getText(), dateDepart, Integer.parseInt(nbRes.getText()), flotteCompagnie.get(2)));
                    }
                    break;
                case 4:
                    option = JOptionPane.showConfirmDialog(null, message, "AJOUT D'UN VOL", JOptionPane.OK_CANCEL_OPTION);
                    if(option == JOptionPane.OK_OPTION){
                        Date dateDepart = new Date(Integer.parseInt(jour.getText()), Integer.parseInt(mois.getText()), Integer.parseInt(annee.getText()));
                        listeMapVols.put(numVol, new VolPrive(numVol, destination.getText(), dateDepart, Integer.parseInt(nbRes.getText()), flotteCompagnie.get(3)));
                    }
                    break;
                     
            }
            afficherMsg(listeMapVols.get(numVol).ajouterVolTxt(), "Confirmation");
        }else{
            JOptionPane.showMessageDialog(null, "Le numéro de vol existe déja!", "ERREUR", JOptionPane.WARNING_MESSAGE);
        }
    }

    

     // Fin des méthodes pour l'ajout d'un vol


     // Méthodes pour le retrait d'un vol

     public static void retirerVol(){
        int numVol;
        int option;
        String msg ="Voulez vous retirer ce vol? Vol numéro : ";

        numVol = Integer.parseInt(JOptionPane.showInputDialog(null, "Entrez le numéro de vol retirer", "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE));
        if(rechercherVol(numVol)){
            msg += listeMapVols.get(numVol).getNumVol();
            option = JOptionPane.showConfirmDialog(null, msg, "Confirmation", JOptionPane.OK_CANCEL_OPTION);
            if(option == JOptionPane.OK_OPTION){
                afficherMsg(listeMapVols.get(numVol).retireVolTxt(), "Confirmation");
                listeMapVols.remove(numVol);
            }            
        } else {
            afficherMsg("Numéro de vol inexistant", "Erreur");
        }
     }


     // Méthodes pour modifier la date de départ d'un vol

     public static void modifierDate() {
        JTextField jour = new JTextField();
        JTextField mois = new JTextField();
        JTextField annee = new JTextField();
        Object[] message = {"Nouvelle date de départ\n",
        "Jour", jour,
        "Mois", mois,
        "Année", annee};
        int numVol;
        int option;


        numVol = Integer.parseInt(JOptionPane.showInputDialog(null, "Entrez le numéro de vol où vous désirez changez la date.", "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE));
        if(rechercherVol(numVol)){
            option = JOptionPane.showConfirmDialog(null, message, "AJOUT D'UN VOL", JOptionPane.OK_CANCEL_OPTION);
            if(option == JOptionPane.OK_OPTION){
                Date dateDepart = new Date(Integer.parseInt(jour.getText()), Integer.parseInt(mois.getText()), Integer.parseInt(annee.getText()));
            listeMapVols.get(numVol).setDepart(dateDepart);
            afficherMsg(listeMapVols.get(numVol).modifierDateTxt(), "Confirmation");
             }
        }
    }

    //Méthodes pour prendre une réservation sur un vol

    public static void reserverVol(){
        int numVol;
        int option;
        int nbReserve;
        String msg ="Voulez vous réserver ce vol? Vol numéro : ";

        numVol = Integer.parseInt(JOptionPane.showInputDialog(null, "Entrez le numéro de vol où vous désirez réserver.", "RÉSERVATION", JOptionPane.PLAIN_MESSAGE));
        if(rechercherVol(numVol)){
            msg += listeMapVols.get(numVol).getNumVol();
            msg += "\nIl reste " + nbPlacesRestantes(numVol) + " places.";
            option = JOptionPane.showConfirmDialog(null, msg, "Confirmation", JOptionPane.OK_CANCEL_OPTION);
            if(option == JOptionPane.OK_OPTION){
                nbReserve = Integer.parseInt(JOptionPane.showInputDialog(null, "Combien de siège voulez vous réservé?", "RÉSERVATION", JOptionPane.PLAIN_MESSAGE));
                if(nbReserve <= nbPlacesRestantes(numVol)){
                    listeMapVols.get(numVol).setNbRes(nbReserve + listeMapVols.get(numVol).nbRes);
                afficherMsg(listeMapVols.get(numVol).reserverVolTxt(nbReserve), "Confirmation");
                }else{
                    afficherMsg("Il ne reste pas assez de place", "Erreur");
                }
                
                }            
            }else {
            afficherMsg("Numéro de vol inexistant", "Erreur");
            }
        }

    public static int nbPlacesRestantes(int numVol) {
       int nbPlacesRestantes;

       nbPlacesRestantes = listeMapVols.get(numVol).getAvion().getNbPlaces() - listeMapVols.get(numVol).getNbRes();
       return nbPlacesRestantes;
        

    }


}
