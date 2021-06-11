package com.example.securemessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.ENCRYPT_MODE;

public class Encoder extends AppCompatActivity {
    static EditText key1;
    EditText message_et;
    TextView messageEnc;
    ClipboardManager cpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoder);
        key1=findViewById(R.id.etkey);
        message_et=findViewById(R.id.etEnc);
        messageEnc=findViewById(R.id.Enctv);
        cpb=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
    }

    static String encrypted=null;




    public void encrypt(View view) {
        try {


            String output = encrypt(message_et.getText().toString(), key1.getText().toString());
            encrypted=output;
            messageEnc.setText(output);
            //make a toast her e to say encypted successfully
              Toast.makeText(this,"Encrypted Successfully!!!",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void cp2(View view){
        ClipData clipData=ClipData.newPlainText("label",encrypted);
        cpb.setPrimaryClip(clipData);
        Toast.makeText(this, "Your message was copied to Clipboard", Toast.LENGTH_SHORT).show();
    }
    public String encrypt(String data, String password_text) throws Exception {
        Cipher cipher=Cipher.getInstance("AES");
        SecretKeySpec key=generateKey(password_text);
        cipher.init(ENCRYPT_MODE,key); //initialisation
        byte[] encVal = cipher.doFinal(data.getBytes("UTF-8"));
        String encryptedvalue = Base64.encodeToString(encVal, Base64.DEFAULT);

        return encryptedvalue;
    }
    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");//for using hash function SHA-256
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);//process kr bytes array ko
        byte[] key = digest.digest();////Completes the hash computation by performing final operations such as padding.
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
    }
