/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capapresentacion;

/**
 *
 * @author user
 */
public class calculo {
    
    
    
    public double calcularImpuesto(double subtotal) {
        double impuesto;
        
        impuesto = subtotal * 0.15;
        return impuesto;
    }
    
    public double calcularTotal(double subtotal, double impuesto) {
        double total;
        
        total = subtotal + impuesto;
        return total;
    }
    
    
    
}
