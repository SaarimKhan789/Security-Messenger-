package com.example.securemessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Decoder extends AppCompatActivity {
    EditText key2;

    ClipboardManager cpb;
    EditText message_dt;
    TextView messageDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);

        key2=findViewById(R.id.dt_key);


        message_dt=findViewById(R.id.dtenc);
        messageDec=findViewById(R.id.dectv);


        cpb=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public void decrypt(View view) {
        try {

            ClipData clipData = ClipData.newPlainText("label", Encoder.encrypted);
            cpb.setPrimaryClip(clipData);
            if (TextUtils.isEmpty(key2.getText())) {
                key2.setError("Please Enter the Key!!");
            } else {
                if (!Encoder.key1.getText().toString().equalsIgnoreCase(key2.getText().toString())) {
                    Toast.makeText(this, "Enter Correct Key. Try Again", Toast.LENGTH_SHORT).show();
                }
                String output = decrypt(message_dt.getText().toString(), key2.getText().toString());// outputstring was there in place of inptext
                messageDec.setText(output);
                Toast.makeText(this, "Your message Decrypted Successfully!!", Toast.LENGTH_SHORT).show();

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String decrypt(String data, String passwd_text)throws  Exception {
        SecretKeySpec key=generateKey(passwd_text);

        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedvalue = Base64.decode(data, Base64.DEFAULT);//pehle vo base64 me encoded tha, to decode to karna padega na
        byte[] decvalue = c.doFinal(decodedvalue);//final decoding operation
        String decryptedvalue = new String(decvalue, "UTF-8");//converting bytes into string
        return decryptedvalue;
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