package kane.alassane.telecomIRC;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.Base64;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.muc.MultiUserChat;

import android.app.Activity;
import android.net.Proxy;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

public class ConnectionSalonActivity extends Activity {
	
	private final static String SERVER_HOST = "resel.fr";
	private final static int SERVER_PORT = 5222;
	private final static String SERVICE_NAME = "resel.fr";	
	private final static String LOGIN = "akane@resel.fr";
	private final static String PASSWORD = "Ousma1400";
	private final static String PSEUDO = "akane";
	
	private List<String> m_discussionThread;
	private ArrayAdapter<String> m_discussionThreadAdapter;
	private XMPPConnection xc;
	private Handler m_handler;
	private static MultiUserChat muc;

    public void onCreate(Bundle savedInstanceState) {


		try {
	    	ConnectionConfiguration config = new ConnectionConfiguration(SERVER_HOST, SERVER_PORT, SERVICE_NAME);
	        xc = new XMPPConnection(config);
	        xc.connect();
	        xc.login(LOGIN, PASSWORD);
	        Presence presence = new Presence(Presence.Type.available);
	        xc.sendPacket(presence);
	        muc = new MultiUserChat(xc,"tbirc@conference.resel.fr");
			muc.join(PSEUDO);
			PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
		xc.addPacketListener(new PacketListener() {
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
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
    }
	
	

	private Socket socket;
	private final int PORT = 31457;
	private String gamerBot;

	public ConnectionSalonActivity(XMPPConnection xc, String gamerBot) {
		this.gamerBot = gamerBot;
		this.xc = xc;
	}

	void connect() throws IOException {
		ServerSocket s = new ServerSocket(PORT);

		while (true) {

			try {
				socket = s.accept();
				System.out.println("Client accepté !");
				
				muc.sendMessage("/me a rejoint la partie !");
				String buffer = "";
				
				InputStreamReader in = new InputStreamReader(socket.getInputStream(), "UTF-8");

				while (socket.isConnected()) {
					int c = in.read();
					if (c >= 0) {
						if (c >= 0xff) {
							if (buffer.startsWith("pline ")) {
								muc.sendMessage(buffer.substring(7));
							}
							Message message = new Message(gamerBot);
							message.setType(Type.chat);
							message.setBody(Base64.encodeBytes(buffer.getBytes()));
							System.out.println("Send : " + message.getBody());
							xc.sendPacket(message);
							buffer = "";
						} else {
							buffer += Character.toString((char) c);
						}
					} else 
						break;
				}
				socket.close();
				System.out.println("Socket fermée");
				muc.sendMessage("/me a quitté la partie !");
				Message message = new Message(gamerBot);
				message.setType(Type.chat);
				message.setBody("close socket");
				xc.sendPacket(message);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void processPacket(Packet packet) {
		if (packet instanceof Message) {
			Message message = (Message) packet;
			System.out.println(message.getBody());
			try {
				socket.getOutputStream().write(Base64.decode(message.getBody()));
				socket.getOutputStream().write(0xff);
				socket.getOutputStream().flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
