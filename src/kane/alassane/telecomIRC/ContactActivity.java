package kane.alassane.telecomIRC;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class ContactActivity extends TabActivity {

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.contact);
	        
	        Resources res = getResources();
	        TabHost tabHost = getTabHost();
	        TabHost.TabSpec spec;
	        Intent intent;
	                
	        // Tab : contact en ligne
	        intent = new Intent().setClass(this,ContactEnLigneActivity.class);
	        spec = tabHost.newTabSpec("En lignes").setIndicator("En lignes", res.getDrawable(R.drawable.enligne_icon)).setContent(intent);
	        tabHost.addTab(spec);
	        
	        // Tab : contact hors ligne
	        intent = new Intent().setClass(this,ContactHorsLigneActivity.class);
	        spec = tabHost.newTabSpec("Hors Lignes").setIndicator("Hors Lignes", res.getDrawable(R.drawable.horsligne_icon)).setContent(intent);
	        tabHost.addTab(spec);
	                   
	        // Tab : tout les contact
	        intent = new Intent().setClass(this,ContactAllActivity.class);
	        spec = tabHost.newTabSpec("All contacts").setIndicator("All contacts", res.getDrawable(R.drawable.all_contact)).setContent(intent);
	        tabHost.addTab(spec);  
	        
	        tabHost.setCurrentTab(0);
   
	    }
}
