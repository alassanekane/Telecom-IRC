package kane.alassane.telecomIRC;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class AccueilActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Recuperation des login et mdp
		 final String login = getIntent().getExtras().getString("login");
		 final String pass = getIntent().getExtras().getString("pass");
		
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
			setContentView(R.layout.accueil);
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.barre);	
		
			
			ImageButton reglage = (ImageButton)findViewById(R.id.btn_reglage);
			reglage.setOnClickListener(new OnClickListener() {		
				public void onClick(View v) {
					Intent intentVersReglage = new Intent (AccueilActivity.this,ReglageActivity.class);
					startActivity(intentVersReglage);
				}
			});
			
			ImageButton salon = (ImageButton)findViewById(R.id.btn_salon);
			salon.setOnClickListener(new OnClickListener() {		
				public void onClick(View v) {
					Intent intentVersSalon = new Intent (AccueilActivity.this,SalonLectureActivity.class);
					intentVersSalon.putExtra("login", login);
					intentVersSalon.putExtra("pass", pass);
					startActivity(intentVersSalon);
				}
			});
			
			ImageButton contact = (ImageButton)findViewById(R.id.btn_contact);
			contact.setOnClickListener(new OnClickListener() {		
				public void onClick(View v) {
					Intent intentVersContact = new Intent (AccueilActivity.this,ContactActivity.class);
					startActivity(intentVersContact);
				}
			});
			
			ImageButton profil = (ImageButton)findViewById(R.id.btn_profil);
			profil.setOnClickListener(new OnClickListener() {		
				public void onClick(View v) {
					Intent intentVersProfil = new Intent (AccueilActivity.this,ProfilActivity.class);
					intentVersProfil.putExtra("login", login);
					startActivity(intentVersProfil);
				}
			});
			
			
	
	}
	
}
