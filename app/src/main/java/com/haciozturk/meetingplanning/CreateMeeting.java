package com.haciozturk.meetingplanning;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class CreateMeeting extends AppCompatActivity {


    private Button toplantiKaydet,anaSayfa;
    private DatePicker tarihSecici;//
    private EditText kullaniciAdi,toplantibasligi,toplantikonusu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        toplantiKaydet=findViewById(R.id.btnToplantiKaydet);
        anaSayfa=findViewById(R.id.btnAnaSayfa);
        tarihSecici=findViewById(R.id.datePicker);//
        kullaniciAdi=findViewById(R.id.olusturanKisi);
        toplantibasligi=findViewById(R.id.toplantiBasligi);
        toplantikonusu=findViewById(R.id.toplantiKonusu);


      anaSayfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreateMeeting.this,MeetingDetails.class);
                startActivity(intent);
            }
        });


        toplantiKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toplantiOlustur();

            }
        });

    }




    private void toplantiOlustur() {


        String kullaniciAdi1 = kullaniciAdi.getText().toString();
        String toplantiBasligiText = toplantibasligi.getText().toString();
        String toplantiKisaKonuText = toplantikonusu.getText().toString();




        SharedPreferences preferences = getSharedPreferences("GIRIS_YAPAN", MODE_PRIVATE);
        String girisYapanKullanici = preferences.getString("KULLANICI_ADI", "");




        if (kullaniciAdi1.isEmpty() || toplantiBasligiText.isEmpty() || toplantiKisaKonuText.isEmpty()) {

            Toast.makeText(CreateMeeting.this, "Tüm bilgileri giriniz", Toast.LENGTH_LONG).show();

        }
        else {


            if(!Objects.equals(girisYapanKullanici,kullaniciAdi1)){

                Toast.makeText(CreateMeeting.this, "Kendi Adınıza Toplantı Oluşturabilirsiniz!!!", Toast.LENGTH_LONG).show();

            }

             else {




                AlertDialog.Builder uyariMesaji = new AlertDialog.Builder(CreateMeeting.this);
                uyariMesaji.setTitle("Toplantı Kayıt Edilecek");
                uyariMesaji.setMessage("Emin Misiniz?");
                uyariMesaji.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int gun = tarihSecici.getDayOfMonth();
                        int ay = tarihSecici.getMonth();
                        int yil = tarihSecici.getYear();


                        Intent gonder = new Intent(CreateMeeting.this, ExpectedMeetings.class);
                        startActivity(gonder);




                        Intent newintent = new Intent(CreateMeeting.this, ExpectedMeetings.class);
                        newintent.putExtra("KULLANICI_ADI", kullaniciAdi1);
                        startActivity(newintent);


                        String toplantiTarihi = gun + "/" + (ay + 1) + "/" + yil;


                        String rastgeleSifre = rastgeleSifreOlustur();



                        SharedPreferences preferences = getSharedPreferences("ToplantiBilgileri", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("KULLANICI_ADI", kullaniciAdi1);
                        editor.putString("TOPLANTI_KONUSU", toplantiKisaKonuText);
                        editor.putString("TOPLANTI_BASLIGI", toplantiBasligiText);
                        editor.putString("TOPLANTI_TARIHI", toplantiTarihi);
                        editor.putString("RASTGELE_SIFRE", rastgeleSifre);
                        editor.apply();




                        Intent intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.Events.TITLE, toplantiBasligiText)
                                .putExtra(CalendarContract.Events.DESCRIPTION, toplantiKisaKonuText)
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Toplantı Yeri")
                                .putExtra(CalendarContract.Events.ALL_DAY, false);

                        Calendar startTime = Calendar.getInstance();
                        startTime.set(yil, ay, gun);

                        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.getTimeInMillis());
                        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startTime.getTimeInMillis() + 60 * 60 * 1000);
                        startActivity(intent);

                        Toast.makeText(CreateMeeting.this, "Toplantı Başarılı Bir Şekilde Kaydedildi ", Toast.LENGTH_LONG).show();







                        SharedPreferences toplantiyiKaydeden = getSharedPreferences("ToplantiBilgileri", MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = toplantiyiKaydeden.edit();
                        editor2.putString("TOPLANTI_OLUŞTURAN", kullaniciAdi1); // Oluşturan kullanıcı adını kayıt edelim
                        editor2.apply();

                    }

                });

                uyariMesaji.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                uyariMesaji.show();
            }
        }

    }


    private String rastgeleSifreOlustur() {
        String karakterler = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder rastgeleSifre = new StringBuilder();



        for (int i = 0; i < 7; i++) {
            int rastgele = (int) (karakterler.length() * Math.random());
            rastgeleSifre.append(karakterler.charAt(rastgele));
        }

        return rastgeleSifre.toString();
    }

}