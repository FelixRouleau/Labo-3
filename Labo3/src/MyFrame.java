import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MyFrame extends JFrame implements ActionListener {

    JRadioButton volPrive;
    JRadioButton volRegulier;
    JRadioButton volCharter;
    JRadioButton volBasPrix;

    JTextArea info;

    MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        volPrive = new JRadioButton("Vol Privé");
        volRegulier = new JRadioButton("Vol Régulier");
        volCharter = new JRadioButton("Vol Charter");
        volBasPrix = new JRadioButton("Vol bas Prix");

        ButtonGroup group = new ButtonGroup();

        group.add(volBasPrix);
        group.add(volRegulier);
        group.add(volCharter);
        group.add(volPrive);

        volBasPrix.addActionListener(this);
        volRegulier.addActionListener(this);
        volCharter.addActionListener(this);
        volPrive.addActionListener(this);

        info = new JTextArea(20, 30);

        this.add(volBasPrix);
        this.add(volRegulier);
        this.add(volCharter);
        this.add(volPrive);

        this.add(info);

        this.pack();
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == volBasPrix) {
            for (Integer cle : Main.listeMapVols.keySet()) {
                if (Main.listeMapVols.get(cle) instanceof VolBasPrix) {
                    info.selectAll();
                    info.replaceSelection("");
                    info.append(Main.listeMapVols.get(cle).toString());

                }
            }
        } else if (e.getSource() == volRegulier) {
            for (Integer cle : Main.listeMapVols.keySet()) {
                if (Main.listeMapVols.get(cle) instanceof VolRegulier) {
                    info.selectAll();
                    info.replaceSelection("");
                    info.append(Main.listeMapVols.get(cle).toString());
                }
            }
        } else if (e.getSource() == volCharter) {
            for (Integer cle : Main.listeMapVols.keySet()) {
                if (Main.listeMapVols.get(cle) instanceof VolCharter) {
                    info.selectAll();
                    info.replaceSelection("");
                    info.append(Main.listeMapVols.get(cle).toString());
                }
            }
        } else if (e.getSource() == volPrive) {
            for (Integer cle : Main.listeMapVols.keySet()) {
                if (Main.listeMapVols.get(cle) instanceof VolPrive) {
                    info.selectAll();
                    info.replaceSelection("");
                    info.append(Main.listeMapVols.get(cle).toString());
                }
            }
        }

    }

}
