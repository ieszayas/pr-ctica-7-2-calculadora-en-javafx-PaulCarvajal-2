package com.example.calculadora;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController  {
    @FXML
    private Button btn_Nueve;
    @FXML
    private Label Pantalla;
    @FXML
    private Label Operaciones;

    @FXML
    private void onClickNueve(ActionEvent evt){
        Pantalla.setText(Pantalla.getText() + "9");
    }


    public void onClickBorrar(ActionEvent actionEvent) {
        Pantalla.setText("");
    }

    public void onClickSiete(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "7");
    }

    public void onClickCuatro(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "4");
    }

    public void onClickUno(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "1");
    }

    public void onClickBorrarHistorial(ActionEvent actionEvent) {
    }

    public void onClickOcho(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "8");
    }

    public void onClickCinco(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "5");
    }

    public void onClickDos(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "2");
    }

    public void onClickCero(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "0");
    }

    public void onClickPorcentaje(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "%");
    }

    public void onClickSeis(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "6");
    }

    public void onClickTres(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "3");
    }

    public void onClickPunto(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + ".");
    }

    public void onClickDividir(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "/");
    }

    public void onClickMultiplicar(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "X");
    }

    public void onClickRestar(ActionEvent actionEvent) {
        Pantalla.setText(Pantalla.getText() + "-");
    }

    public void onClickSumar(ActionEvent actionEvent) {
        if(!Pantalla.getText().contains("+")) {
            Pantalla.setText(Pantalla.getText() + "+");
        }


    }

    public void onClickResultado(ActionEvent actionEvent) {
    }
}