package kane.alassane.telecomIRC;


import org.jivesoftware.smack.XMPPConnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TelecomIRCActivity extends Activity {
	
	private final static String SERVER_HOST = "resel.fr";
	private final static int SERVER_PORT = 5222;
	private final static String SERVICE_NAME = "resel.fr";	
    
	private Button connexion;
	private EditText user;
	private EditText password;
	private XMPPConnection m_connection;
	private SharedPreferences localPreferences;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);	
		setContentView(R.layout.main);
		
		localPreferences = getPreferences(Context.MODE_PRIVATE);
		String userStocke = localPreferences.getString("user", "");
		String passStocke = localPreferences.getString("pass", "");

		
		connexion = (Button)findViewById(R.id.connexion);
		user = (EditText)findViewById(R.id.edit_user);
		password = (EditText)findViewById(R.id.edit_password);
		
		user.setText(userStocke);
		password.setText(passStocke);

	       
		connexion.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {
					
				Editor localEditor = localPreferences.edit();
				localEditor.putString("user", user.getText().toString());
				localEditor.putString("pass", password.getText().toString());
				localEditor.commit();
				
				Intent intentVersAcceuil = new Intent (TelecomIRCActivity.this,AccueilActivity.class);
				intentVersAcceuil.putExtra("login", user.getText().toString());
				intentVersAcceuil.putExtra("pass", password.getText().toString());
				startActivity(intentVersAcceuil);
			}
		});	
		
		

		
    }
}