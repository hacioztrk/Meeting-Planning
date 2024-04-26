package com.haciozturk.meetingplanning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ExpectedMeetings extends AppCompatActivity {
    private Button toplantiyaKatil,toplantiBilgileriniSil,anasayfa;
    private EditText kullaniciadi1,toplantiparola1;

    private ArrayList<String> katilanKisiler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expected_meetings);

        anasayfa=findViewById(R.id.btnAnaSayfayaDon);
        toplantiyaKatil=findViewById(R.id.btnToplantiyaKatil);
        kullaniciadi1=findViewById(R.id.editTextkullanici1);
        toplantiparola1=findViewById(R.id.editTextparola1);

        toplantiBilgileriniSil=findViewById(R.id.silToplantiButton);


        anasayfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ExpectedMeetings.this,MeetingDetails.class);
                startActivity(intent);
            }
        });






        Intent yeniGelenIntent = getIntent();
        if (yeniGelenIntent != null) {
            if (yeniGelenIntent.hasExtra("KULLANICI_ADI")) {
                String gelenKullaniciAdi = yeniGelenIntent.getStringExtra("KULLANICI_ADI");


                SharedPreferences preferences = getSharedPreferences("ToplantiBilgileri", MODE_PRIVATE);
                String toplantiKuranKullaniciAdi = preferences.getString("KULLANICI_ADI", "");


                if (gelenKullaniciAdi.equals(toplantiKuranKullaniciAdi)) {
                    katilanKisiler.add(gelenKullaniciAdi);


                    SharedPreferences sharedPreferences = getSharedPreferences("KatilanKisiler", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Set<String> updatedKatilanKisilerSet = new HashSet<>(katilanKisiler);
                    editor.putStringSet("katilanKisiler", updatedKatilanKisilerSet);
                    editor.apply();
                }
            }
        }








        SharedPreferences preferences = getSharedPreferences("ToplantiBilgileri", MODE_PRIVATE);
        String kullaniciAdi = preferences.getString("KULLANICI_ADI", "");
        String toplantiBasligi = preferences.getString("TOPLANTI_BASLIGI", "");
        String toplantiKonusu=preferences.getString("TOPLANTI_KONUSU","");
        String toplantiTarihi = preferences.getString("TOPLANTI_TARIHI", "");
        String rastgeleSifre = preferences.getString("RASTGELE_SIFRE", "");



        TextView txtKullaniciAdi = findViewById(R.id.txtKullaniciAdi);
        TextView txtToplantiBasligi = findViewById(R.id.txtToplantiBasligi);
        TextView txtToplantiKonusu=findViewById(R.id.txtToplantiKonusu);
        TextView txtToplantiTarihi = findViewById(R.id.txtToplantiTarihi);
        TextView txtRastgeleSifre = findViewById(R.id.txtRastgeleSifre);


        txtKullaniciAdi.setText("Kullanıcı Adı: " + kullaniciAdi);
        txtToplantiBasligi.setText("Toplantı Başlığı: " + toplantiBasligi);
        txtToplantiKonusu.setText("Toplantı Konusu: "+toplantiKonusu);
        txtToplantiTarihi.setText("Toplantı Tarihi: " + toplantiTarihi);
        txtRastgeleSifre.setText("Toplantı Şifresi: " + rastgeleSifre);



        SharedPreferences sharedPreferences = getSharedPreferences("KatilanKisiler", Context.MODE_PRIVATE);
        Set<String> katilanKisilerSet = sharedPreferences.getStringSet("katilanKisiler", new HashSet<>());
        katilanKisiler = new ArrayList<>(katilanKisilerSet);


        TextView katilanKisilerTextView = findViewById(R.id.textViewKatilanKisiler);


        String katilanKisilerMetin = "Toplantıya Katılan Kişiler\n";
        for (String katilan : katilanKisiler) {
            katilanKisilerMetin += katilan + "\n";
        }
        katilanKisilerTextView.setText(katilanKisilerMetin);



        toplantiyaKatil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kullaniciAdiGirilen = kullaniciadi1.getText().toString();
                String parolaGirilen = toplantiparola1.getText().toString();



                SharedPreferences sharedPreferences = getSharedPreferences("KatilanKisiler", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();



                if (kullaniciAdiGirilen.isEmpty()||parolaGirilen.isEmpty()) {

                    Toast.makeText(ExpectedMeetings.this,"Bilgileri giriniz",Toast.LENGTH_LONG).show();
                    return;

                }




                if (katilanKisiler.contains(kullaniciAdiGirilen))

                {

                    Toast.makeText(ExpectedMeetings.this, "Toplnatıya kayıtlısınız!!!", Toast.LENGTH_LONG).show();


                }


                else {



                    SharedPreferences preferences = getSharedPreferences("GIRIS_YAPAN", MODE_PRIVATE);
                    String girisYapanKullanici = preferences.getString("KULLANICI_ADI", "");


                    if (!girisYapanKullanici.equals(kullaniciAdiGirilen)) {
                        Toast.makeText(ExpectedMeetings.this, "Sadece kendi adınızla kayıt yapabilirsiniz!!!", Toast.LENGTH_LONG).show();
                    } else {
                        if (parolaGirilen.equals(rastgeleSifre)) {
                            Toast.makeText(ExpectedMeetings.this, "Toplantıya başarılı bir şekilde katıldınız", Toast.LENGTH_LONG).show();

                            katilanKisiler.add(kullaniciAdiGirilen);

                            Set<String> updatedKatilanKisilerSet = new HashSet<>(katilanKisiler);
                            editor.putStringSet("katilanKisiler", updatedKatilanKisilerSet);
                            editor.apply();
                        } else {
                            Toast.makeText(ExpectedMeetings.this, "Şifreyi yanlış girdiniz!!!", Toast.LENGTH_LONG).show();
                        }
                    }



                    Set<String> updatedKatilanKisilerSet = new HashSet<>(katilanKisiler);
                    editor.putStringSet("katilanKisiler", updatedKatilanKisilerSet);
                    editor.apply();


                    String katilanKisilerMetin = "Toplantıya Katılan Kişiler\n";
                    for (String katilan : katilanKisiler) {
                        katilanKisilerMetin += katilan + "\n";
                    }
                    katilanKisilerTextView.setText(katilanKisilerMetin);


                }


            }

        });


        toplantiBilgileriniSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("ToplantiBilgileri", MODE_PRIVATE);
                SharedPreferences sharedPreferences = getSharedPreferences("KatilanKisiler", MODE_PRIVATE);

                String olusturanKullaniciAdi = preferences.getString("KULLANICI_ADI", "");
                


                if (preferences.getAll().isEmpty() && sharedPreferences.getAll().isEmpty()) {
                    Toast.makeText(ExpectedMeetings.this, "Toplantı bilgileri ve katılan kişiler zaten boş.", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences gecisYapanPreferences = getSharedPreferences("GIRIS_YAPAN", MODE_PRIVATE);
                    String girisYapanKullaniciAdi = gecisYapanPreferences.getString("KULLANICI_ADI", "");

                    if (girisYapanKullaniciAdi.equals(olusturanKullaniciAdi)) {





                        AlertDialog.Builder uyariMesaji = new AlertDialog.Builder(ExpectedMeetings.this);
                        uyariMesaji.setTitle("Toplantı Bilgilerini ve Kayıtları Sil");
                        uyariMesaji.setMessage("Emin Misiniz?");
                        uyariMesaji.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor toplantiEditor = preferences.edit();
                                toplantiEditor.clear();
                                toplantiEditor.apply();

                                SharedPreferences.Editor katilanKisilerEditor = sharedPreferences.edit();
                                katilanKisilerEditor.clear();
                                katilanKisilerEditor.apply();

                                Intent newIntent = new Intent(ExpectedMeetings.this, MeetingDetails.class);
                                startActivity(newIntent);
                            }
                        });
                        uyariMesaji.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        uyariMesaji.show();
                    } else {
                        Toast.makeText(ExpectedMeetings.this, "Toplnatıyı sadece oluşturan silebilir!!!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}