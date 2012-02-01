package kane.alassane.telecomIRC;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactHorsLigneActivity extends Activity {
	ListView lvListe;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.horsligne);
		
		lvListe = (ListView)findViewById(R.id.listeContactAbsents);
		String[] listeStrings = {"France","Allemagne","Russie","JSFEZ","AKANE","Russie","JSFEZ","AKANE","Russie","JSFEZ","AKANE","Russie"};
		lvListe.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeStrings));
		
		
	}
}
