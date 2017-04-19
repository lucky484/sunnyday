package com.f2b2c.eco.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PBKDF2加密
 * @author color.wu
 *
 */
public class Pbkdf2Encryption {  
	
	private static Logger logger=LoggerFactory.getLogger(Pbkdf2Encryption.class);
	  
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";  
  
    /** 
     * 盐的长度 
     */  
    public static final int SALT_BYTE_SIZE = 8;  
  
    /** 
     * 生成密文的长度 
     */  
    public static final int HASH_BIT_SIZE = 128;  
  
    /** 
     * 迭代次数 
     */  
    public static final int PBKDF2_ITERATIONS = 1000;  
  
  
    /** 
     * 生成密文 
     *  
     * @param password 
     *            明文密码 
     * @param salt 
     *            盐值 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     */  
    private static String getEncryptedPassword(String password, String salt) throws NoSuchAlgorithmException,  
            InvalidKeySpecException {  
    	if(salt==null||password==null){
    		return null;
    	}
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), PBKDF2_ITERATIONS, HASH_BIT_SIZE);  
        SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);  
        return toHex(f.generateSecret(spec).getEncoded());  
    }
    
    /**
     * 加密
     * @param password
     * @param salt
     * @return
     * @throws InvalidKeySpecException 
     * @throws NoSuchAlgorithmException 
     */
    public static String encode(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
    	String encodePassword=getEncryptedPassword(password,salt);
    	String randomSalt=generateSalt(salt.length());
    	Integer iterations=PBKDF2_ITERATIONS;
    	return String.format("pbkdf2:sha1:%s$%s$%s%s", iterations.toString(),salt,encodePassword,randomSalt);
    }
    
    /**
     * 加密
     * @param password
     * @param salt
     * @return
     * @throws InvalidKeySpecException 
     * @throws NoSuchAlgorithmException 
     */
    public static String encode(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
    	String salt=generateSalt(null);
    	String encodePassword=getEncryptedPassword(password,salt);
    	String randomSalt=generateSalt(salt.length());
    	Integer iterations=PBKDF2_ITERATIONS;
    	return String.format("pbkdf2:sha1:%s$%s$%s%s", iterations.toString(),salt,encodePassword,randomSalt);
    }
    
    /** 
     * 对输入的密码进行验证 
     *  
     * @param attemptedPassword 
     *            待验证的密码 
     * @param encryptedPassword 
     *            密文 
     * @param salt 
     *            盐值 
     * @return 是否验证成功 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     */  
    public static boolean checkPassword(String attemptedPassword, String encryptedPassword, String salt)  
            throws NoSuchAlgorithmException, InvalidKeySpecException {  
    	 if(StringUtils.isEmpty(salt)||StringUtils.isEmpty(attemptedPassword)||StringUtils.isEmpty(encryptedPassword)){
         	return false;
         }
    	// 用相同的盐值对用户输入的密码进行加密  
        String encryptedAttemptedPassword = encode(attemptedPassword, salt);
        int length=salt.length();
        
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败  
        return encryptedAttemptedPassword.substring(0, encryptedAttemptedPassword.length()-length).equals(encryptedPassword.substring(0, encryptedPassword.length()-length));  
    } 
    
   public static boolean checkPassword(String attemptedPassword, String encryptedPassword) {  
  	 if(StringUtils.isEmpty(attemptedPassword)||StringUtils.isEmpty(encryptedPassword)){
      	return false;
      }
  	 String salt=getSalt(encryptedPassword);
 	// 用相同的盐值对用户输入的密码进行加密  
     String encryptedAttemptedPassword;
	try {
		encryptedAttemptedPassword = encode(attemptedPassword, salt);
	} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
		logger.error(e.getMessage());
		return false;
	}
     int length=salt.length();
     
     // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败  
     return encryptedAttemptedPassword.substring(0, encryptedAttemptedPassword.length()-length).equals(encryptedPassword.substring(0, encryptedPassword.length()-length));  
   } 
   
   /**
    * 获取盐值
    * @param encodedPassword
    * @return
    */
   private static String getSalt(String encodedPassword){
	   if(StringUtils.isEmpty(encodedPassword)){
		   return null;
	   }
	   int startIndex=encodedPassword.indexOf('$');
	   int endIndex=encodedPassword.lastIndexOf('$');
	   String salt=encodedPassword.substring(startIndex+1,endIndex);
	   if(StringUtils.isNotEmpty(salt)){
		   return salt;
	   }else{
		   return null;
	   }
   }
    
    /** 
     * 通过提供加密的强随机数生成器 生成盐 
     *  
     * @return 
     * @throws NoSuchAlgorithmException 
     */  
    public static String generateSalt(Integer length) throws NoSuchAlgorithmException {  
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[null==length?SALT_BYTE_SIZE/2:length/2];  
        random.nextBytes(salt); 
        //long number=generateRandomNumber(null==length?SALT_BYTE_SIZE/4:length/4);
        return toHex(salt);  
    } 
    
    /** 
     * 产生n位随机数 
     * @return 
     */  
    private long generateRandomNumber(int n){  
        if(n<1){  
            throw new IllegalArgumentException("随机数位数必须大于0");  
        }  
        return (long)(Math.random()*9*Math.pow(10,n-1)) + (long)Math.pow(10,n-1);  
    }  
  
    /** 
     * 十六进制字符串转二进制字符串 
     *  
     * @param   hex         the hex string 
     * @return              the hex string decoded into a byte array       
     */  
    private static byte[] fromHex(String hex) {  
        byte[] binary = new byte[hex.length() / 2];  
        for (int i = 0; i < binary.length; i++) {  
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);  
        }  
        return binary;  
    }  
  
    /** 
     * 二进制字符串转十六进制字符串 
     *  
     * @param   array       the byte array to convert 
     * @return              a length*2 character string encoding the byte array       
     */  
    private static String toHex(byte[] array) {  
        BigInteger bi = new BigInteger(1, array);  
        String hex = bi.toString(16);  
        int paddingLength = (array.length * 2) - hex.length();  
        if (paddingLength > 0)  
            return String.format("%0" + paddingLength + "d", 0) + hex;  
        else  
            return hex;  
    }  
}  
