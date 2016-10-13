import javax.swing.JOptionPane;


public class Main {
	// git test1
	// git test2

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		while(true) {
			String pesel = JOptionPane.showInputDialog(null, "Podaj PESEL", "PeselValidator", JOptionPane.QUESTION_MESSAGE);
			
			
			////// wszystko to co zły użytkownik może wpisać źle
			boolean stringGood = true;
			if (pesel == null) {
				System.exit(0);
			}
			pesel = pesel.trim();
			if (pesel.length() != 11) {
				JOptionPane.showMessageDialog(null, "Nie właściwa długość PESELu", "Błąd", JOptionPane.ERROR_MESSAGE);
				stringGood = false;
			}
			for (int i = 0; i < pesel.length(); i++) {
				char c = pesel.charAt(i);
				if (c != '0' && c !='1' && c !='2' && c !='3' && c !='4' && c !='5' && c !='6' && c !='7' && c !='8'  && c !='9') {
					JOptionPane.showMessageDialog(null, "Wpisano nie właściwe znaki", "Błąd", JOptionPane.ERROR_MESSAGE);
					stringGood = false;
					break;
				}
			}
			//////
			
			
			if (stringGood == true) {
				int[] tab = new int[11];
				
				for (int i = 0; i < 11; i++) {
					tab[i] = Character.getNumericValue(pesel.charAt(i));
				}
				
				int checksum = tab[0] + 3*tab[1] + 7*tab[2] + 9*tab[3] + tab[4] + 3*tab[5] + 7*tab[6] + 9*tab[7] + tab[8] + 3*tab[9];
				checksum = checksum%10;
				if (checksum != 0) {
					checksum = 10 - checksum;
				}
				
				boolean correct;
				if (checksum == tab[10]) correct = true;
				else correct = false;
				
				int birthDay = 10*tab[4] + tab[5];
				if (birthDay > 31) correct = false; //ze względu na wadę algorytmu ten wers może wyłapać chociaż część błędów
				int birthMonth = 0;
				if (tab[2]==0 || tab[2]==1) birthMonth = 10*tab[2] + tab[3];
				if (tab[2]==2 || tab[2]==3) birthMonth = 10*(tab[2]-2) + tab[3];
				if (tab[2]==4 || tab[2]==5) birthMonth = 10*(tab[2]-4) + tab[3];
				if (tab[2]==6 || tab[2]==7) birthMonth = 10*(tab[2]-6) + tab[3];
				if (tab[2]==7 || tab[2]==8) birthMonth = 10*(tab[2]-8) + tab[3];
				int birthYear = 0;
				if (tab[2]==0 || tab[2]==1) birthYear = 1900 + 10*tab[0] + tab[1];
				if (tab[2]==2 || tab[2]==3) birthYear = 2000 + 10*tab[0] + tab[1];
				if (tab[2]==4 || tab[2]==5) birthYear = 2100 + 10*tab[0] + tab[1];
				if (tab[2]==6 || tab[2]==7) birthYear = 2200 + 10*tab[0] + tab[1];
				if (tab[2]==8 || tab[2]==9) birthYear = 1800 + 10*tab[0] + tab[1];
				boolean sex = true; // czy facet
				if (tab[9]%2 == 0) sex = false;
				if (tab[9]%2 == 1) sex = true;
				
				OutputDialog out = new OutputDialog(correct, sex, birthYear, birthMonth, birthDay);
				out.setVisible(true);
			}
			
		}
		
		
	}

}
