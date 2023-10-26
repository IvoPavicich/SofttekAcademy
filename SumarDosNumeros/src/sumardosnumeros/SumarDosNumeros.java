
package sumardosnumeros;
import javax.swing.JOptionPane;

public class SumarDosNumeros{
    public static void main(String[] args) {
        // Declaración de variables
        double numero1, numero2, suma;

        // Solicitar al usuario que ingrese el primer número
        numero1 = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el primer número:"));
        
        // Solicitar al usuario que ingrese el segundo número
        numero2 = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el segundo número:"));
       

        // Se realiza la suma de los numeros ingresados
        suma = numero1 + numero2;

        // Mostrar el resultado por consola
        System.out.println("La suma de los numeros ingresados es igual a " + suma);
    }
}

