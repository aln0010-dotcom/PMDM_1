package com.example.reproductorarmando;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ExoPlayer player;
    private SeekBar seekBar;
    private TextView tvActual;
    private TextView tvTotal;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable actualizar = new Runnable() {
        @Override
        public void run() {
            if (player != null && player.isPlaying()) {
                long actual = player.getCurrentPosition();
                long total = player.getDuration();

                if (total > 0) {
                    seekBar.setMax((int) total);
                    seekBar.setProgress((int) actual);
                    tvActual.setText(formatearTiempo(actual));
                    tvTotal.setText(formatearTiempo(total));
                }
            }
            handler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        seekBar = findViewById(R.id.seekBar);
        tvActual = findViewById(R.id.tvActual);
        tvTotal = findViewById(R.id.tvTotal);

        player = new ExoPlayer.Builder(this).build();

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/musica");
        player.setMediaItem(MediaItem.fromUri(uri));
        player.prepare();

        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(@NonNull PlaybackException error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnPausa = findViewById(R.id.btnPausa);

        btnPlay.setOnClickListener(v -> {
            if (player.getPlaybackState() == Player.STATE_ENDED) {
                player.seekTo(0);
            }
            player.play();
        });

        btnPausa.setOnClickListener(v -> player.pause());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && player != null) {
                    player.seekTo(progress);
                    tvActual.setText(formatearTiempo(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.post(actualizar);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.pause();
        }
        handler.removeCallbacks(actualizar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private String formatearTiempo(long milisegundos) {
        long segundosTotales = milisegundos / 1000;
        long minutos = segundosTotales / 60;
        long segundos = segundosTotales % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos);
    }
}