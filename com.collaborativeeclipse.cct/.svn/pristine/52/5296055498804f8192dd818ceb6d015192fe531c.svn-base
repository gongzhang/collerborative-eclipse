package com.collaborativeeclipse.cct.tagdata;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.ITarget;
import com.collaborativeeclipse.cct.exception.TagCoreException;

public class TagAccessNativeDatabaseImpl implements ITagAccess {

	ITagDAO tagdao = TagDAOMemoryImpl.getInstance();
	ITargetDAO targetdao = TargetDAOMemoryImpl.getInstance();
	
	public ITagDAO getTagDAO() {
		return tagdao;
	}

	public void setTagDAO(ITagDAO tagdao) {
		this.tagdao = tagdao;
	}

	public ITargetDAO getTargetDAO() {
		return targetdao;
	}

	public void setTargetDAO(ITargetDAO targetdao) {
		this.targetdao = targetdao;
	}

	@Override
	public void addTag(ITag tag) throws TagCoreException {
		if (!targetdao.hasTarget(tag.getTarget().getHandleIdentifier())){
			targetdao.addTarget(tag.getTarget());
			tagdao.addTag(tag);
			System.out.println("添加新的target的标签");
		}
		else{
			if (!tagdao.hasTag(tag))
				tagdao.addTag(tag);
			System.out.println("添加已经存在的target的标签");
		}
		
		FileOutputStream fos;
		DataOutputStream dos;
		try {
			fos = new FileOutputStream("TANDB.TXT");
			dos = new DataOutputStream(fos);
			dos.writeBytes(tag.getData());
			dos.writeBytes(tag.getTarget().getHandleIdentifier());
			dos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ITag> getTagsFromTarget(ITarget target) {
		return tagdao.getTagsByTarget(target);
	}

	@Override
	public void removeTag(ITag tag) {
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// ObjectOutputStream oos;
		// try {
		// oos = new ObjectOutputStream(baos);
		// oos.writeObject(tag.getData());
		//
		// byte[] data = baos.toByteArray();

		tagdao.remove(tag);
		//			
		// oos.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//		

	}

	@Override
	public void removeTarget(ITarget target) {
		tagdao.removeByTarget(target);
		targetdao.removeByIndentifier(target.getHandleIdentifier());

	}

}
