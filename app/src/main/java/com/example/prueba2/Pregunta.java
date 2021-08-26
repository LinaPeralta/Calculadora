package com.example.prueba2;

public class Pregunta {

    private int operandoA;
    private int operandoB;
    private String operador;
    private String[] operadores = {"+","-","x","/"};

    //constructor

    public Pregunta(){

        this.operandoA = (int) (Math.random()*11);
        this.operandoB = (int) ((Math.random()*11)+1);
        int posicion = (int)(Math.random()*4);
        this.operador = operadores [posicion];

    }

    //metodos

    public String getPregunta(){

        return operandoA + " " + operador + " " + operandoB;
    }

    public int getRespuesta(){
        int respuesta = 0;

        switch (operador){
            case "+":
                respuesta = this.operandoA + this.operandoB;
                break;

            case "-":
                respuesta = this.operandoA - this.operandoB;
                break;

            case "x":
                respuesta = this.operandoA * this.operandoB;
                break;

            case "/":
                respuesta = this.operandoA / this.operandoB;
                break;

        }
        return respuesta;
    }



}
