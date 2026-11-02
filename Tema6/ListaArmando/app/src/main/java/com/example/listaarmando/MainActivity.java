package com.example.listaarmando;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

        // 1. Los 8 juegos
        ArrayList<Videojuego> videojuegos = new ArrayList<>();
        videojuegos.add(new Videojuego("Zelda: Breath of the Wild", "Nintendo Switch", R.drawable.baseline_sports_esports_24));
        videojuegos.add(new Videojuego("FIFA 23", "PlayStation 5", R.drawable.baseline_sports_soccer_24));
        videojuegos.add(new Videojuego("Minecraft", "PC", R.drawable.baseline_extension_24));
        videojuegos.add(new Videojuego("Halo Infinite", "Xbox Series X", R.drawable.baseline_military_tech_24));
        videojuegos.add(new Videojuego("Super Mario Odyssey", "Nintendo Switch", R.drawable.baseline_sports_esports_24));
        videojuegos.add(new Videojuego("EA Sports FC 24", "PlayStation 5", R.drawable.baseline_sports_soccer_24));
        videojuegos.add(new Videojuego("Terraria", "PC", R.drawable.baseline_extension_24));
        videojuegos.add(new Videojuego("Call of Duty", "Xbox Series X", R.drawable.baseline_military_tech_24));

        // 2. La lista: cómo se colocan las filas y quién las pinta
        RecyclerView rvLista = findViewById(R.id.rvLista);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvLista.setAdapter(new AdaptadorVideojuegos(videojuegos));
    }
}