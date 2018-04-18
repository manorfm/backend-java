package br.com.timer.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyUtil {

	/**
    * Gera um valor rash da mensagem passando um salt
    * 
    * @param message mensagem
    * @param salt salt
    * @return mensagem em hash
    * @throws NoSuchAlgorithmException 
    * @throws UnsupportedEncodingException 
    */
   public static String doHash(String message, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
       synchronized (salt) {
           String output = "";

           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(salt.getBytes());

           byte[] digest = md.digest(message.getBytes("UTF-16LE"));
           StringBuilder hexString = new StringBuilder();
           for (int i = 0; i < digest.length; i++) {
               hexString.append(Integer.toHexString(0xFF & digest[i]));
           }
           output = hexString.toString();

           return output;
       }
   }
   
   public static String encodeBase64(String message) {
	   byte[] encodedBytes = Base64.getEncoder().encode(message.getBytes());
	   return String.valueOf(encodedBytes);
   }
}
