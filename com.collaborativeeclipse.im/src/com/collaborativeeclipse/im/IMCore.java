package com.collaborativeeclipse.im;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.collaborativeeclipse.im.views.LabelInfo;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public class IMCore {

	public static String MESSAGESTARTSIGNAL = "#_message_#";

	private String username = null;

	private String password = null;

	private XMPPConnection connection = null;

	private String buddy;

	private Map<String, Chat> chatMap = new HashMap<String, Chat>();

	private MessageListener messageListener;

	private static String LINKTAG_LABEL = "Linktag";

	private static IMCore core = null;

	private IMCore() {
		messageListener = new MessageListener() {

			public void processMessage(Chat arg0, Message arg1) {
			}
		};

	}

	public static IMCore getCore() {
		if (core == null) {
			core = new IMCore();
		}

		return core;
	}

	public String[] getBuddies() {
		if (connection == null) {
			return null;
		}

		Collection<RosterEntry> entries = connection.getRoster().getEntries();
		Iterator<RosterEntry> iter = entries.iterator();
		List<String> buddies = new ArrayList<String>();
		while (iter.hasNext()) {
			RosterEntry entry = iter.next();
			String user = entry.getUser();
			Presence precense = connection.getRoster().getPresence(user);
			String status = precense.getStatus();
			// if (status != null && status.startsWith(LINKTAG_LABEL)) {
			buddies.add(user);
			// }

		}

		if (buddies.size() > 0) {
			String[] ret = new String[buddies.size()];
			buddies.toArray(ret);
			return ret;
		}

		return null;
	}

	public boolean login(String username2, String password2) {
		// if (username == null || password == null) {
		// return false;
		// }
		this.username = username2;
		this.password = password2;

		int port = 5222;
		String domain = "gmail.com";
		String host = "talk.google.com";
		ConnectionConfiguration config = new ConnectionConfiguration(host,
				port, domain);

		connection = new XMPPConnection(config);
		try {
			connection.connect();
			if (username != null && password != null) {
				System.out.println(username + ":" + password);
				connection.login(username, password);
				connection.addPacketListener(new PacketListener() {

					public void processPacket(Packet packet) {

						System.out.println(packet.toXML());

						if (!(packet instanceof Message)) {
							return;
						}

						final Message m = (Message) packet;
						if (m.getBody().length() == 0) {
							return;
						}

						String body = m.getBody();

						String from = m.getFrom();
						from = from.substring(0, from.indexOf("/"));

						DisplayManager.getDisplayManager().recive(from, body);

					}

				}, new MessageTypeFilter(Message.Type.chat));

				connection.addPacketListener(new PacketListener() {

					@Override
					public void processPacket(Packet present) {
						// TODO Auto-generated method stub

					}

				}, new PacketTypeFilter(Presence.class));

				Presence presence = new Presence(Presence.Type.available);
				presence.setStatus(LINKTAG_LABEL);
				connection.sendPacket(presence);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		LabelInfo.getLabelInfo().setLoginUser(connection.getUser());

		return true;
	}

	protected String getBetween(String str, String start, String end) {

		if (str.indexOf(start) >= 0 && str.indexOf(end) > 0) {
			return str.substring(str.indexOf(start) + start.length(), str
					.indexOf(end));
		}

		return new String("");
	}

	public void logout() {
		if (connection != null && connection.isAuthenticated()) {
			Presence presence = new Presence(Presence.Type.unavailable);
			connection.disconnect(presence);

			selectBuddy(null);
		}
	}

	public void setUsername(String newUsername) {
		this.username = newUsername;
	}

	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	public String getUser() {
		if (connection != null && connection.isAuthenticated()) {

			return username;
		}

		return null;
	}

	public void selectBuddy(String buddy) {
		this.buddy = buddy;

		LabelInfo.getLabelInfo().setUser(buddy);
	}

	public String getBuddy() {
		return buddy;
	}

	public void sendMessage(String buddy2, String str) {
		Chat chat = getChat(buddy2);

		try {
			chat.sendMessage(str);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	public Chat getChat(String buddy) {
		Chat chat = chatMap.get(buddy);
		if (chat == null) {
			chat = connection.getChatManager().createChat(buddy,
					messageListener);
			chatMap.put(buddy, chat);
		}

		return chat;
	}
}
