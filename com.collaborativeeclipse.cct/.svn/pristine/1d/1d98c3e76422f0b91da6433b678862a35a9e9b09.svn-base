package com.collaborativeeclipse.cct.example.tagdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.ITarget;
import com.collaborativeeclipse.cct.internal.TagInternal;
import com.collaborativeeclipse.cct.tagdata.ITagAccess;

public class MockTagAccess implements ITagAccess {

	@Override
	public void addTag(ITag tag) {
		System.out.println("new tag:");
		System.out.println(tag.getData());
		System.out.println("is added to:");
		System.out.println(tag.getTarget().getHandleIdentifier());
		

	}

	@Override
	public List<ITag> getTagsFromTarget(ITarget target) {
		try {
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ObjectOutputStream oos = new ObjectOutputStream(baos);
//
//			oos.writeObject("one");
//			byte[] data = baos.toByteArray();
//			oos.close();
//
//			ByteArrayInputStream bais = new ByteArrayInputStream(data);
//			ObjectInputStream ois = new ObjectInputStream(bais);
//			Object tagdata = ois.readObject();
//			ois.close();
//
//			oos = new ObjectOutputStream(baos);

			TagInternal tag = new TagInternal();
			tag.setData("one");
			tag.setTarget(target);

			List<ITag> res = new ArrayList<ITag>();
			if (target.getHandleIdentifier().equals("1")) {

				res.add(tag);
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void removeTag(ITag tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTarget(ITarget target) {
		// TODO Auto-generated method stub

	}


}
