package com.collaborativeeclipse.cct.collaborative;

import com.collaborativeeclipse.cct.CodeTagCore;
import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.exception.TagCoreException;
import com.collaborativeeclipse.cct.util.TagPaser;
import com.collaborativeeclipse.im.IMCore;
import com.collaborativeeclipse.im.IMessageRecieveHandler;

public class MessageRecieveHandler implements IMessageRecieveHandler {

	public MessageRecieveHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void messageRecieved(String buddy, String str) {
		System.out.println("message recieved");
		if (str.startsWith(SymbolConsts.TAGSIGNAL)) {
			System.out.println("Tag info recieved");
			ITag tag = new TagPaser().parserXml(str.substring(
					SymbolConsts.TAGSIGNAL.length(), str
							.indexOf(IMCore.MESSAGESTARTSIGNAL)));
			CodeTagCore.getInstance().addTag(tag);
		}
	}

}
