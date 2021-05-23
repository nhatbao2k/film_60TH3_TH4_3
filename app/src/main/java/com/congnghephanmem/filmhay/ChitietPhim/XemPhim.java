package com.congnghephanmem.filmhay.ChitietPhim;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.congnghephanmem.filmhay.R;

public class XemPhim extends AppCompatActivity {
    Intent intent;
    VideoView xemphim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_phim);
        intent = getIntent();
        xemphim = (VideoView) findViewById(R.id.xemphim);
        xemphim.setVideoPath(intent.getStringExtra("link"));
        xemphim.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });


    }
}