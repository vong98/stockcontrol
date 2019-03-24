package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginAuth {
	
	private Connection connection;
	private static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private final int DB_ID = 1;
	private final int DB_PASSWORD = 3;
	private final int DB_SALT = 4;
	private final int DB_IS_ADMIN = 5;
	
	public void setConnection(Connection conn) {
		connection = conn;
	}
	
	// Returns the password hash in HEX format.
	public String generateHash(String data, String algorithm, byte[] salt) throws NoSuchAlgorithmException {
		MessageDigest disgest = MessageDigest.getInstance(algorithm);
		disgest.reset();
		disgest.update(salt);
		byte[] hash = disgest.digest(data.getBytes());
		
		return bytesToHex(hash);
	}
	
	// Returns an array of bytes  
	public byte[] createSalt() {
		byte[] bytes = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
	}
	
	public String bytesToHex(byte[] input) {
		char[] hexChars = new char[input.length * 2];
		for(int j = 0; j<input.length; j++) {
			int v = input[j] & 0xFF;
			hexChars[j * 2]= hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];			
		}
		return new String(hexChars);
	}
	
	public int credentialsValidAndType(String id, String password) {
		String sql = "SELECT * FROM users";
		ResultSet users;
		int usersLength = 0;
		try {
			PreparedStatement pstate = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE
					, ResultSet.CONCUR_UPDATABLE);
			users = pstate.executeQuery();
			users.last();
			usersLength = users.getRow();
			users.first();
			
			for(int i = 0; i < usersLength; i++){
				
				if(users.getString(DB_ID).equals(id)) {
				
					byte[] salt = users.getBytes(DB_SALT);					
					
					if(users.getString(DB_PASSWORD).equals(generateHash(password, "SHA-256", salt))) {
						
						if(users.getBoolean(DB_IS_ADMIN))
							return 1;
						else
							return 2;
					}
				} else
					users.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}
