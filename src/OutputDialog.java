import javax.swing.*;
import java.awt.*;

class OutputDialog extends JDialog {

    OutputDialog(boolean correct, boolean sex, int year, int month, int day) {
        this.setTitle("PeselValidator");
        this.setSize(250, 200);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLayout(null);

        //// w zasadzie po to żeby daty były 01 a nie 1 itd.
        String sDay;
        if (day < 10) sDay = "0" + day;
        else sDay = "" + day;
        String sMonth;
        if (month < 10) sMonth = "0" + month;
        else sMonth = "" + month;
        ////

        if (correct) this.getContentPane().setBackground(new Color(196, 255, 196));
        if (!correct) this.getContentPane().setBackground(new Color(255, 196, 196));

        JLabel lCorrectTitle = new JLabel("Poprawność:");
        lCorrectTitle.setBounds(10, 10, 130, 30);
        lCorrectTitle.setFont(new Font(null, Font.BOLD, 17));
        add(lCorrectTitle);

        JLabel lCorrect = new JLabel();
        if (correct) lCorrect.setText("TAK");
        else lCorrect.setText("NIE");
        lCorrect.setBounds(150, 10, 90, 30);
        lCorrect.setFont(new Font(null, Font.BOLD, 20));
        add(lCorrect);

        JLabel lBirthTitle = new JLabel("Data urodzenia:");
        lBirthTitle.setBounds(10, 45, 130, 30);
        add(lBirthTitle);

        JLabel lBirth = new JLabel();
        if (correct) lBirth.setText(sDay + "-" + sMonth + "-" + year);
        lBirth.setBounds(150, 45, 90, 30);
        add(lBirth);

        JLabel lSexTitle = new JLabel("Płeć:");
        lSexTitle.setBounds(10, 80, 130, 30);
        add(lSexTitle);

        JLabel lSex = new JLabel();
        if (correct && sex) lSex.setText("Mężczyzna");
        if (correct && !sex) lSex.setText("Kobieta");
        lSex.setBounds(150, 80, 90, 30);
        add(lSex);

        JButton bNext = new JButton("Następny");
        bNext.setBounds(10, 120, 110, 30);
        bNext.addActionListener(arg0 -> dispose());
        add(bNext);

        JButton bExit = new JButton("Wyjście");
        bExit.setBounds(130, 120, 110, 30);
        bExit.addActionListener(arg0 -> System.exit(0));
        add(bExit);

        this.getRootPane().setDefaultButton(bNext);
    }

}
