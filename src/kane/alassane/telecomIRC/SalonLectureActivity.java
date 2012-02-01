package kane.alassane.telecomIRC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class SalonLectureActivity extends Activity {
		
/*
	private final static String SERVER_HOST = "resel.fr";
	private final static int SERVER_PORT = 5222;
	private final static String SERVICE_NAME = "resel.fr";	
	private final static String LOGIN = "akane@resel.fr";
	private final static String PASSWORD = "Ousma1400";
	private final static String PSEUDO = "akane";
*/
	private final static String SERVER_HOST = "talk.google.com";
	private final static int SERVER_PORT = 5222;
	private final static String SERVICE_NAME = "gmail.com";	
	private static String LOGIN = "";
	private static String PASSWORD = "";

	private List<String> m_discussionThread;
	private ArrayAdapter<String> m_discussionThreadAdapter;
	private XMPPConnection m_connection;
	private Handler m_handler;
	private static MultiUserChat muc;
	
	ListView lvListe;
	String[] listeStrings;
	LayoutInflater factory;
	View alertDialogView;
	AlertDialog.Builder adb;
	Button btn_lecteurs;
	Roster roster;
	LinkedList<RosterEntry> iter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		//Recuperation des login et mdp
		 Intent thisIntent = getIntent();
		 final String login = thisIntent.getExtras().getString("login");
		 final String pass = thisIntent.getExtras().getString("pass");
		 final String destinataire = thisIntent.getExtras().getString("destinataire");
		 LOGIN = login+"@gmail.com";
		 PASSWORD = pass;
		 
        setContentView(R.layout.salonpublic);
        m_handler = new Handler();
		try {
			initConnection();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		
		//final EditText recipient = (EditText) this.findViewById(R.id.recipient);
		final EditText message = (EditText) this.findViewById(R.id.message);		
		ListView list = (ListView) this.findViewById(R.id.thread);
		
		m_discussionThread = new ArrayList<String>();
		m_discussionThreadAdapter = new ArrayAdapter<String>(this,R.layout.multi_line_list_item, m_discussionThread);
		list.setAdapter(m_discussionThreadAdapter);
		
		Button send = (Button) this.findViewById(R.id.send);
		send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//Faudra modifier ça !!!
				String to = "alassane.kane7@gmail.com";
				//String to = recipient.getText().toString();
				String text = message.getText().toString();
		
				Message msg = new Message(to, Message.Type.chat);
				msg.setBody(text);
				m_connection.sendPacket(msg);
				
				m_discussionThread.add(0,LOGIN+">"+text);
				m_discussionThreadAdapter.notifyDataSetChanged();
				message.setText("");
			}
		});
		
		
	//Action liée au bouton lecteur
		OnClickListener boutonConnexion = new OnClickListener(){
	    	public void onClick(View actuelView){
	    		afficheLecteurs();
	    	}
	    };
	    btn_lecteurs = (Button)findViewById(R.id.lecteurs);
	    btn_lecteurs.setOnClickListener(boutonConnexion);
	}
    
    private void afficheLecteurs() {

		factory = LayoutInflater.from(this);
		alertDialogView = factory.inflate(R.layout.dialoglistelecteurs, null);
	    adb = new AlertDialog.Builder(this);
	    adb.setView(alertDialogView);
	    adb.setTitle("Liste des Lecteurs");
	    //Gerer la liste des utilisateurs
    	listeStrings=new String[1];
	    String s = m_connection.getUser();
	    int i=0;
	    while(s.charAt(i)!='@'){
	    	i++;
	    }
	    listeStrings[0] = s.substring(0,i);
		lvListe = (ListView)alertDialogView.findViewById(R.id.utilListe);
		lvListe.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listeStrings));
		adb.show();
    }
    
	private void initConnection() throws XMPPException {
		//Initialisation de la connexion
        ConnectionConfiguration config = new ConnectionConfiguration(SERVER_HOST, SERVER_PORT, SERVICE_NAME);
        m_connection = new XMPPConnection(config);
        m_connection.connect();
        m_connection.login(LOGIN, PASSWORD);
        Presence presence = new Presence(Presence.Type.available);
        m_connection.sendPacket(presence);   
        
        //enregistrement de l'écouteur de messages
		PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
		m_connection.addPacketListener(new PacketListener() {
				public void processPacket(Packet packet) {
					Message message = (Message) packet;
					if (message.getBody() != null) {
						String fromName = StringUtils.parseBareAddress(message
								.getFrom());
						m_discussionThread.add(fromName + ":");
						m_discussionThread.add(message.getBody());
						
						m_handler.post(new Runnable() {
							public void run() {
								m_discussionThreadAdapter.notifyDataSetChanged();
							}
						});
					}
				}
		}, filter);
	}
    
}