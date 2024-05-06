package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controlleur {
	private static String motAdeviner;
	private HashSet<Lettre> mot = new HashSet<>();
	private static StringBuffer chaineTrouvee = new StringBuffer();
	@FXML
	private TextField lettreProposee;
	@FXML
	private Label tentative;
	@FXML
	private TextField motCacher;
	@FXML
	private Button proposition;
	@FXML
	private Text motDejaPropose;

	@FXML
	private ImageView pendu;

	private static int pv = 0;
	static Stage primaryStage = null;

	public static void setprimaryStage(Stage stage) {
		primaryStage = stage;
	}

	public void initialize() {
    	ArrayList<String> tab=new ArrayList<>(5);
    	tab.add("orange");
    	tab.add("rose");
    	tab.add("pinapple");
    	tab.add("poo");
    	tab.add("javaFx");
    	Random rand=new Random();
    	motAdeviner=tab.get(rand.nextInt(tab.size()));  //permets de selectionner aleatoirement un mot dans la liste tab
    	for(int i=0;i<motAdeviner.length();i++) {
    		Lettre l=new Lettre(motAdeviner.charAt(i),motAdeviner);
    		mot.add(l);
    		chaineTrouvee.append("_");
    	}// permet de 
    	
    	motCacher.setText(chaineTrouvee.toString());

		motCacher.setEditable(false);

		proposition.setDefaultButton(true);
		
		Image im=null;
		try {
		  im = new Image("C:/Users/kamen/eclipse-workspace/JeuPendu/src/application/images/pendu_0.jpg");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		pendu.setImage(im);
     
		tentative.setText("Nombre de tentative restantes : "+(6-pv));

    }

	@FXML
	void actionValiderLettre(ActionEvent event) {
		if (lettreProposee.getText().isEmpty()) {
			throw new IllegalArgumentException("Vous devez entrer une lettre");
		}
		Lettre le = new Lettre(lettreProposee.getText().charAt(0), motAdeviner);

		for (int i = 20; i < motDejaPropose.getText().length(); i = i + 2) {

			if (le.lettre == motDejaPropose.getText().charAt(i)) {
				lettreProposee.clear();
				throw new IllegalArgumentException("cette lettre a déjà été tirée"); // permet de lever une exception au
																						// cas la lettre aurait déjà été
																						// proposé
			}
		}

		if (!le.getPositions().isEmpty()) {
			for (int i=0;i<le.getPositions().size();i++) {
				int pos=le.getPositions().get(i);
				chaineTrouvee.setCharAt(pos*2, le.lettre);
				motCacher.setText(chaineTrouvee.toString());
			}
		}
		else {
			//motDejaPropose.setText(motDejaPropose.getText() + le.lettre + "-");
			motDejaPropose.setText(motDejaPropose.getText() + le.lettre );
			majPendu();
		}
		lettreProposee.clear();
		if (Controlleur.defaite()) {
			messageFin("Vous avez perdu");
			nouvellePartie();
		}
		if (Controlleur.victoire()) {
			messageFin("Vous avez gagné");
			nouvellePartie();
		}

	}

	private static boolean victoire() {
		// TODO Auto-generated method stub
		return motAdeviner.compareTo(chaineTrouvee.toString()) == 0;
	}

	private void nouvellePartie() {
		// TODO Auto-generated method stub
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("NOUVELLE PARTIE?");
		dialog.setHeaderText(null);
		dialog.setContentText("Voulez-vous faire une nouvelle partie?");
		dialog.showAndWait();
		ButtonType result = dialog.getResult();
		if (result.getButtonData() == ButtonData.OK_DONE) {
			initialize();

		}

		if (result.getButtonData() == ButtonData.CANCEL_CLOSE) {

			if (primaryStage != null) {

				Platform.runLater(() -> primaryStage.close());

			}

		}
	}

	private void messageFin(String message) {
		// TODO Auto-generated method stub
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("FIN DE PARTIE");
		dialog.setHeaderText(null);
		dialog.setContentText(message);
		dialog.showAndWait();
	}

	private static boolean defaite() {
		// TODO Auto-generated method stub
		return pv >= 6;
	}

	private void majPendu() {
		// TODO Auto-generated method stub
		pv++;

		String url = "C:/Users/kamen/eclipse-workspace/JeuPendu/images \"pendu_" + pv + ".jpg";

		Image image = new Image(url);

		pendu.setImage(image);

		tentative.setText("Nombre de tentative restanste : " + (6 - pv));
	}

}
