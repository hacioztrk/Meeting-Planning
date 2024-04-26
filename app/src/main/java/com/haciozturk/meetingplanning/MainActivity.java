package com.haciozturk.meetingplanning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // ortak şifreye sahip olan 4 tane  kullanıcı adları var
    private final   String[] kullanicilar = {"Ali", "Ayse", "Mehmet", "Merve"};
    private final String ortakParola = "iste2357";

    private EditText kullaniciAdi;
    private EditText sifre;
    private Button girisYap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kullaniciAdi=findViewById(R.id.girisKullaniciAdi);
        sifre=findViewById(R.id.girisParola);
        girisYap=findViewById(R.id.btnGirisYap);


        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kullaniciAdiGir = kullaniciAdi.getText().toString();
                String sifreGir = sifre.getText().toString();

                if(bilgileriKarsilastir(kullaniciAdiGir,sifreGir)){

                    Toast.makeText(MainActivity.this, "Hoş Geldiniz", Toast.LENGTH_LONG).show();


                    Intent intent=new Intent(MainActivity.this,MeetingDetails.class);
                    startActivity(intent);



                    SharedPreferences preferences = getSharedPreferences("GIRIS_YAPAN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("KULLANICI_ADI", kullaniciAdi.getText().toString());
                    editor.apply();


                }
                else {

                    Toast.makeText(MainActivity.this, "Kullanıcı adı veya şifre yanlış", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    private boolean bilgileriKarsilastir(String kullaniciAdiGir, String sifreGir) {
        for (int i = 0; i < kullanicilar.length; i++) {
            if (kullaniciAdiGir.equals(kullanicilar[i]) && sifreGir.equals(ortakParola)) {
                return true;
            }
        }
        return false;
    }
}