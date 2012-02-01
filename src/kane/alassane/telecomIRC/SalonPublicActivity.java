package kane.alassane.telecomIRC;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SalonPublicActivity extends Activity{
	
	ListView lvListe;
	String[] listeStrings = {"JSFEZ","AKANE","Russie"};
	LayoutInflater factory;
	View alertDialogView;
	AlertDialog.Builder adb;
	Button btn_lecteurs;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salonpublic);

		//Creation de la boite de dialogue
		factory = LayoutInflater.from(this);
		alertDialogView = factory.inflate(R.layout.dialoglistelecteurs, null);
        adb = new AlertDialog.Builder(this);
        adb.setView(alertDialogView);
        adb.setTitle("Liste des Lecteurs");
        //Gerer la liste des utilisateurs
		lvListe = (ListView)alertDialogView.findViewById(R.id.utilListe);
		lvListe.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeStrings));
        //Action du bouton "Lecteurs"
		OnClickListener boutonConnexion = new OnClickListener(){
        	public void onClick(View actuelView){
        		adb.show();
        	}
        };
        btn_lecteurs = (Button)findViewById(R.id.lecteurs);
        btn_lecteurs.setOnClickListener(boutonConnexion);
 		
    }   

}
