package com.haciozturk.meetingplanning;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MeetingDetails extends AppCompatActivity {

    private Button cikisyap,toplantiOlustur,beklenentoplantilar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);

        cikisyap=findViewById(R.id.btnCikisYap);
        toplantiOlustur=findViewById(R.id.btnToplantiOlustur);
        beklenentoplantilar=findViewById(R.id.btnBeklenenToplanti);




        cikisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder uyariMesaji=new AlertDialog.Builder(MeetingDetails.this);
                uyariMesaji.setTitle("Uygulamadan Çıkış Yapılacak");
                uyariMesaji.setMessage("Emin Misiniz?");
                uyariMesaji.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent=new Intent(MeetingDetails.this,MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(MeetingDetails.this, "İyi Günler", Toast.LENGTH_LONG).show();


                    }
                });
                uyariMesaji.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                uyariMesaji.show();
            }
        });





        toplantiOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(MeetingDetails.this,CreateMeeting.class);
                startActivity(intent);



            }
        });



        beklenentoplantilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MeetingDetails.this,ExpectedMeetings.class);
                startActivity(intent);

            }
        });

    }

}