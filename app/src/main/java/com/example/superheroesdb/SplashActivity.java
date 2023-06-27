package com.example.superheroesdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    // Gestos
    // Fuente Ejemplo GestureDetectorExample - Tema 6
    private GestureDetector gestos;

    // Animaciones
    // Fuente Ejemplo ViewTransitionExample - Tema 4
    private Animation animacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Inicializar gestos
       gestos = new GestureDetector(this, new EscuchaGestos());

        ImageView logo = findViewById(R.id.ivLogo);
        Button boton = findViewById(R.id.btnEntrar);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirHome();
            }
        });


        // Crear animación logo
        animacion = AnimationUtils.loadAnimation(this, R.anim.animacion_logo);
        logo.setAnimation(animacion);

    }

    private void abrirHome(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    // Gestos
    // Sin esto no funcionan los gestos
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestos.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    // Aquí se implementarían los gestos necesarios
    class EscuchaGestos extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {

            // Deslizar a la izquierda para ir a Home
            if ((e1.getX() - e2.getX())>0){
                abrirHome();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}