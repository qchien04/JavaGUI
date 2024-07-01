/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

public class Xuong implements Serializable{
    private int ma;
    private String ten;
    private double hso;

    public Xuong(){ 
        this.ma = 0;
        this.ten = "";
        this.hso = 0;
    }
    public Xuong(int ma, String ten, double hso) {
        this.ma = ma;
        this.ten = ten;
        this.hso = hso;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getHso() {
        return hso;
    }

    public void setHso(double hso) {
        this.hso = hso;
    }
    public Object[] toObject(){
        return new Object[]{
            ma,ten,hso
        };
    }
    
}
