/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keysecureapp;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;

import com.ingrian.security.nae.*;
import java.security.*;
import javax.crypto.*;
import com.ingrian.security.nae.NAEKey;
import com.ingrian.security.nae.NAESession;
import com.ingrian.security.nae.UserKeysDetail;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
/**
 *
 * @author Maciej
 */
public class KeySecureApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
     
        String  pid  = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
       
        System.out.println("PID: " + pid );
        
        String username = "Secure1";
        String password = "Pass1234!";
        
        String newKeyName = "AESKey_Jabber4";
        
      //          Security.addProvider(new IngrianProvider());
        NAESession session = NAESession.getSession(username, password.toCharArray());        
        UserKeysDetail keyNames = NAEKey.getKeyNames(session);

            
           
        System.out.println("Key count: " + keyNames.getKeyCount());
	System.out.println("Total Keys: " + keyNames.getTotalKeys());
	System.out.println("KeyNames: " + keyNames.getKeyNames());
        System.out.println("\n\n");
        
    //    System.exit(0);
        
        
        
        
       	int offset = 0;
	int max = 1; 
        
   	String attributeName = null;
	String attributeValue = null;
	String fingerprint = null;     
        
        String text_to_encrypt = "This text will be encrypted";     
        String encrypted_text = "" ;
        
        try
        {
            
      //  Security.addProvider(new IngrianProvider());
    //    NAESession session = NAESession.getSession(username, password.toCharArray());        
    //    UserKeysDetail keyNames = NAEKey.getKeyNames(session);

            
           
        System.out.println("Key count: " + keyNames.getKeyCount());
	System.out.println("Total Keys: " + keyNames.getTotalKeys());
	System.out.println("KeyNames: " + keyNames.getKeyNames());
        System.out.println("\n\n");
 
        // kmip 
        
        
       // KeyGenerator kg = KeyGenerator.getInstance("AES", "IngrianProvider");  
       KeyGenerator kg = KeyGenerator.getInstance("AES");
        NAEParameterSpec spec = new NAEParameterSpec( newKeyName, true, true, session);
        kg.init(spec); 
        SecretKey secret_key = kg.generateKey();
                
        
        

        
        // szyfrowanie 
           
        System.out.println("MSG: " + text_to_encrypt);  
       
        
        
        
        
        
        NAEKey key = NAEKey.getSecretKey(newKeyName, session);
           
        byte[] byte_key = key.export();
           
        
        
        
        
        
        
        for (int i = 0 ; i < byte_key.length ; i++)
        {

             System.out.print(String.format("%02x", byte_key[i]));

        }
        System.out.print("\n");
          
            
        
        
        
        
        
        
     //   Cipher aes_cipher =  Cipher.getInstance("AES/ECB/PKCS5Padding", "IngrianProvider");
         Cipher aes_cipher =  Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec aes_key_spec = new SecretKeySpec(byte_key, "AES");
         
        aes_cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = aes_cipher.doFinal(text_to_encrypt.getBytes());
         

        
        
        
        
        
        
        System.out.print("ENC: ");
        for (int i = 0 ; i < encrypted.length ; i++)
        {

              System.out.print(String.format("%02x", encrypted[i]));
              encrypted_text = encrypted_text + String.format("%02x", encrypted[i]) ;

        }
             
            
        byte[] byte_to_decrypt = DatatypeConverter.parseHexBinary(encrypted_text);
        aes_cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = aes_cipher.doFinal(byte_to_decrypt);
        String decrypted_text = new String(decrypted, "UTF-8");
        System.out.println("\nDEC: " + decrypted_text);
            
         Thread.sleep(10000000);
        
        }
        catch (Exception e)
        {
            
            System.out.println("ERR: " + e.getMessage());
            
        }
        
        
      
        
        
    }
    
}
