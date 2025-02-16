package com.example.calculadora;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    private Button btn_Nueve;
    @FXML
    private Label Pantalla;
    @FXML
    private Label Operaciones;

    private ArrayList<String> numeros = new ArrayList<>();
    private ArrayList<String> opers = new ArrayList<>();
    private double numero1 = 0;
    private double numero2 = 0;
    private String operacion = "";

    {
        numeros.add("1");
        numeros.add("2");
        numeros.add("3");
        numeros.add("4");
        numeros.add("5");
        numeros.add("6");
        numeros.add("7");
        numeros.add("8");
        numeros.add("9");
        numeros.add("10");
        opers.add("+");
        opers.add("-");
        opers.add("X");
        opers.add("/");
    }

    //Operaciones
    @FXML
    public void onClickBorrar(ActionEvent actionEvent) {
        Pantalla.setText("");
        Operaciones.setText("");
    }

    public void onClickDividir(ActionEvent actionEvent) {
        //en caso de tener ya un simbolo + no poder poner otro
        if (!Pantalla.getText().contains("/")) {
            Operaciones.setText(Pantalla.getText() + "/");
            operacion = "/";
        }
    }

    public void onClickMultiplicar(ActionEvent actionEvent) {
        //en caso de tener ya un simbolo + no poder poner otro
        if (!Pantalla.getText().contains("X")) {
            Operaciones.setText(Pantalla.getText() + "x");
            operacion = "X";
        }
    }

    public void onClickRestar(ActionEvent actionEvent) {
        //en caso de tener ya un simbolo + no poder poner otro
        if (!Pantalla.getText().contains("-")) {
            Operaciones.setText(Pantalla.getText() + "-");
            operacion = "-";
        }
    }

    public void onClickSumar(ActionEvent actionEvent) {
        //en caso de tener ya un simbolo + no poder poner otro
        if (!Pantalla.getText().contains("+")) {
            Operaciones.setText(Pantalla.getText() + "+");
            operacion = "+";
        }
    }

    public void onClickResultado(ActionEvent actionEvent) {
        double resultado = 0;


        //evaluacion de si es division por 0
        if (operacion.equals("")) {
            Pantalla.setText(Pantalla.getText());
        } else if (numero2 == 0) {
            Pantalla.setText("ERROR");
        } else {

            switch (operacion) {
                case "+":
                    resultado = numero1 + numero2;
                    break;
                case "-":
                    resultado = numero1 - numero2;
                    break;
                case "X":
                    resultado = numero1 * numero2;
                    break;
                case "/":
                    resultado = numero1 / numero2;
                    break;
            }
            int resultadoInt = (int) resultado;
            Pantalla.setText(resultadoInt + "");

            operacion = "";
        }
    }

    public void onClickBorrarHistorial(ActionEvent actionEvent) {
    }

    public void onClickPorcentaje(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "%");
    }

    @FXML
    //Numeros
    private void onClickNueve(ActionEvent evt) {
        if(numDecimal()){
            Pantalla.setText(Pantalla.getText() + "9");
        } else{
            Pantalla.setText("9");
        }

        if (!Operaciones.getText().isEmpty()) {
            Operaciones.setText(Operaciones.getText() + "9");
        }

        //q lugar ocupa en la operacion
        if (operacion.isEmpty()) {
            numero1 = 9;
        } else {
            numero2 = 9;
        }
    }

    public void onClickOcho(ActionEvent actionEvent) {
        if(numDecimal()){
            Pantalla.setText(Pantalla.getText() + "8");
        } else{
            Pantalla.setText("8");
        }
        if (!Operaciones.getText().isEmpty()) {
            Operaciones.setText(Operaciones.getText() + "8");
        }

        //q lugar ocupa en la operacion
        if (operacion.isEmpty()) {
            numero1 = 8;
        } else {
            numero2 = 8;
        }
    }

    public void onClickSiete(ActionEvent actionEvent) {
        if(numDecimal()){
            Pantalla.setText(Pantalla.getText() + "7");
        } else{
            Pantalla.setText("7");
        }
        if (!Operaciones.getText().isEmpty()) {
            Operaciones.setText(Operaciones.getText() + "7");
        }

        //q lugar ocupa en la operacion
        if (operacion.isEmpty()) {
            numero1 = 7;
        } else {
            numero2 = 7;
        }
    }

    public void onClickSeis(ActionEvent actionEvent) {
        if(numDecimal()){
            Pantalla.setText(Pantalla.getText() + "6");
        } else{
            Pantalla.setText("6");
        }

        if (!Operaciones.getText().isEmpty()) {
            Operaciones.setText(Operaciones.getText() + "6");
        }

        //q lugar ocupa en la operacion
        if (operacion.isEmpty()) {
            numero1 = 6;
        } else {
            numero2 = 6;
        }
    }

    public void onClickCinco(ActionEvent actionEvent) {
        if(numDecimal()){
            Pantalla.setText(Pantalla.getText() + "5");
        } else{
            Pantalla.setText("5");
        }

        if (!Operaciones.getText().isEmpty()) {
            Operaciones.setText(Operaciones.getText() + "5");
        }

        //q lugar ocupa en la operacion
        if (operacion.isEmpty()) {
            numero1 = 5;
        } else {
            numero2 = 5;
        }
    }

    public void onClickCuatro(ActionEvent actionEvent) {
        if(numDecimal()){
            Pantalla.setText(Pantalla.getText() + "4");
        } else{
            Pantalla.setText("4");
        }

        if (!Operaciones.getText().isEmpty()) {
            Operaciones.setText(Operaciones.getText() + "4");
        }

        //q lugar ocupa en la operacion
        if (operacion.isEmpty()) {
            numero1 = 4;
        } else {
            numero2 = 4;
        }
    }

    public void onClickTres(ActionEvent actionEvent) {
        if(numDecimal()){
            Pantalla.setText(Pantalla.getText() + "3");
        } else{
            Pantalla.setText("3");
        }

        if (!Operaciones.getText().isEmpty()) {
            Operaciones.setText(Operaciones.getText() + "3");
        }

        //q lugar ocupa en la operacion
        if (operacion.isEmpty()) {
            numero1 = 3;
        } else {
            numero2 = 3;
        }
    }

    public void onClickDos(ActionEvent actionEvent) {
        if(numDecimal()){
            Pantalla.setText(Pantalla.getText() + "2");
        } else{
            Pantalla.setText("2");
        }

        if (!Operaciones.getText().isEmpty()) {
            Operaciones.setText(Operaciones.getText() + "2");
        }

        //q lugar ocupa en la operacion
        if (operacion.isEmpty()) {
            numero1 = 2;
        } else {
            numero2 = 2;
        }
    }

    public void onClickUno(ActionEvent actionEvent) {
        if(numDecimal()){
            Pantalla.setText(Pantalla.getText() + "1");
        } else{
            Pantalla.setText("1");
        }

        if (!Operaciones.getText().isEmpty()) {
            Operaciones.setText(Operaciones.getText() + "1");
        }

        //q lugar ocupa en la operacion
        if (operacion.isEmpty()) {
            numero1 = 1;
        } else {
            numero2 = 1;
        }
    }

    public void onClickCero(ActionEvent actionEvent) {
        if(numDecimal()){
            Pantalla.setText(Pantalla.getText() + "0");
        } else{
            Pantalla.setText("0");
        }

        if (!Operaciones.getText().isEmpty()) {
            Operaciones.setText(Operaciones.getText() + "0");
        }

        //q lugar ocupa en la operacion
        if (operacion.isEmpty()) {
            numero1 = 0;
        } else {
            numero2 = 0;
        }
    }

    public void onClickPunto(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + ".");
    }
    private boolean numDecimal(){
        if(Pantalla.getText().contains(".")){
            return true;
        }
        return false;
    }
}