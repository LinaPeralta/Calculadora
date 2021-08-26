package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView preguntaText;
    private EditText respuestaUsuario;
    private TextView contadorText;
    private Button responder;
    private Button tryAgainBtn;
    private TextView puntajeText;
    private Pregunta preguntaActual;
    private int tiempoRestante;
    private int puntaje;
    //private int posicion;
    private int tiempoPulsado;
    private boolean isPressing = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preguntaText  = findViewById(R.id.preguntaText);
        respuestaUsuario = findViewById(R.id.respuestaUsuario);
        contadorText = findViewById(R.id.contadorText);
        puntajeText = findViewById(R.id.puntajeText);
        responder = findViewById(R.id.responder);
        tryAgainBtn = findViewById(R.id.tryAgainBtn);

        puntaje = 0;
        tiempoRestante = 30;
        contadorText.setText(" " + tiempoRestante);
        new Thread(
                () -> {
                    while (tiempoRestante>0) {
                        try {

                            tiempoRestante--;
                            runOnUiThread(
                                    () -> {
                                        contadorText.setText("" + tiempoRestante);
                                    }
                            );

                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Log.e("ERROR", e.toString());
                        }
                    }
                }
                ).start();

        generarNuevaPregunta();



       preguntaText.setText(preguntaActual.getPregunta());
       contadorText.setText(" " + tiempoRestante);
       puntajeText.setText("Puntaje: " + puntaje);

       //TRY AGAIN
       tryAgainBtn.setVisibility(View.GONE);
       tryAgain();

       
       responder.setOnClickListener(
               (view) -> {
                   verificarRespuesta();
               }
       );

       tryAgainBtn.setOnClickListener(
               (view) -> {
                   tryAgainBtn.setVisibility(View.GONE);
                   tiempoRestante = 30;
                   contadorText.setText(""+tiempoRestante);
                   tryAgain();
               }
       );


       //skip pregunta
        tiempoPulsado = 0;

        preguntaText.setOnTouchListener(
                (view,event) -> {
                   switch(event.getAction()){
                       case MotionEvent.ACTION_DOWN:
                           isPressing = true;

                           new Thread(()->{
                               tiempoPulsado = 0;
                               while(tiempoPulsado <1500) {
                                   try {
                                       Thread.sleep(150);
                                       tiempoPulsado+=150;
                                       if (!isPressing) {
                                           return;
                                       }
                                   } catch (InterruptedException e) {

                                   }
                               }
                                  runOnUiThread(()->{
                                      Toast.makeText(this,"pasaron 1.5 seg",Toast.LENGTH_SHORT).show();
                                  });


                           }).start();
                           break;

                       case MotionEvent.ACTION_UP:
                         isPressing = false;
                           break;
                   }
                   return true;
                });

    }

    private void verificarRespuesta() {

        String respuestaText = respuestaUsuario.getText().toString();
        int respuestaInt = (int) Integer.parseInt(respuestaText);

        if (respuestaInt == preguntaActual.getRespuesta()){
            //correcto
            Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();
            puntaje += 5;
            puntajeText.setText("Puntaje: "+puntaje);
        } else {
            //incorrecto
            Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();

        }
        generarNuevaPregunta();
    }

    public void generarNuevaPregunta() {
     preguntaActual = new Pregunta();
     preguntaText.setText(preguntaActual.getPregunta());
    }

    public void tryAgain(){
        new Thread(
                ()->{
                    while(tiempoRestante >0){
                        try{
                            tiempoRestante--;
                            runOnUiThread(
                                    ()-> {
                                        contadorText.setText(" "+tiempoRestante);
                                        if(tiempoRestante == 0 ){
                                            tryAgainBtn.setVisibility(View.VISIBLE);

                                        }
                                    }
                            );
                            Thread.sleep(1000);
                        } catch (Exception e){

                        }
                    }
                }
        ).start();
    }
}