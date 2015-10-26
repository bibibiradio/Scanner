package com.alipay.security.dbaudit.consistenthash;

public interface HashGenerator {
	public byte[] getHash(byte[] needHashBytes);
}
