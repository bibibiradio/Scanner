package com.alipay.security.dbaudit.consistenthash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256HashGenerator implements HashGenerator {
	private MessageDigest md = null;
	static private HashGenerator hashGenerator = null;
	
	static public HashGenerator getHashGenerator(){
		if(hashGenerator == null){
			hashGenerator = new Sha256HashGenerator();
		}
		return hashGenerator;
	}
	
	public Sha256HashGenerator(){
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public byte[] getHash(byte[] needHashBytes) {
		// TODO Auto-generated method stub
		md.update(needHashBytes);
		return md.digest();
	}

}
