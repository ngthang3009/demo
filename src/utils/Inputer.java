/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class Inputer {
    private static Scanner sc = new Scanner(System.in);
    public static double inputDouble(String prompt, double a, double b) {
        double result;
        while(true){
           System.out.println(prompt);
        try{
            result = Double.parseDouble(sc.nextLine().trim());
            if (result >= a && result <= b){
                return result;
            }
          System.out.println("Vui long nhap trong khoang"+ a + "den" + b);
        }catch (NumberFormatException e){
            System.out.println("Vui long nhap so thuc");
        }
}
  
}
      public static int inputInt(String prompt, int a, int b) {
        int result;
        while(true){
            System.out.println(prompt);
          try{
              result = Integer.parseInt(sc.nextLine());
              if (result >= a && result <= b){
                  return result;
              }
              System.out.println("Vui long nhap trong khoang"+ a + "den" + b);
          } catch (NumberFormatException e){
              System.out.println("Vui long nhap so");
          }
       } 
    }
      
      public  static String inputString(String prompt){
          System.out.println(prompt);
          return sc.nextLine().trim();
      }
}
