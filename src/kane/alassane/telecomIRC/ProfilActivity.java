package kane.alassane.telecomIRC;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilActivity extends Activity{
	    /** Called when the activity is first created. */
	    
	    private TextView pseudo;
	    private TextView nom;
	    private TextView prenom;
	    private TextView mail;

	    
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.profil);
	        
	        final String login = getIntent().getExtras().getString("login");
	        
	        pseudo = (TextView)findViewById(R.id.champPseudo);
	        nom = (TextView)findViewById(R.id.champNom);
	        prenom = (TextView)findViewById(R.id.champPrenom);
	        mail = (TextView)findViewById(R.id.champMail);
	        
	        pseudo.setText(login);
	        mail.setText(login+"@telecom-bretagne.eu");
			
	        
	    }
}
