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
    }
}