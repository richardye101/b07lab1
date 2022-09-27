import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Driver {
  public static void main(String [] args) throws IOException {
    Polynomial p = new Polynomial();
    System.out.println(p.evaluate(3));

    // 6+3x^2+4x^3+5x^4
    double [] c1 = {6,3,4,5};
    int [] e1 = {0,2,3,4};
    Polynomial p1 = new Polynomial(c1, e1);

    // 3-22x+35x^3+43x^5-9x^7
    // 3x^3-22x^5-16x^7-52x^10-9x^12
    double [] c2 = {3,-22,-16,-52,-9};
    int [] e2 = {3,5,7,10,12};
    Polynomial p2 = new Polynomial(c2, e2);

    System.out.println("Adding the two");
    Polynomial s = p1.add(p2);
    s.printCoefficients();
    s.printExponents();

    System.out.println("Multiplying the two");
    Polynomial prod = p1.multiply(p2);
    prod.printCoefficients();
    prod.printExponents();

    File polyFile = new File("productPoly2.txt");
    Polynomial fromFile = new Polynomial(polyFile);
    System.out.println("From file:");
    fromFile.printCoefficients();
    fromFile.printExponents();
    
    // BufferedReader input = new BufferedReader(new FileReader(polyFile));
    // String line = input.readLine();
    // System.out.println("Read from file " + line);

    System.out.println("Save to file ");
    prod.saveToFile("productPoly2.txt");
    
    if(s.hasRoot(1))
      System.out.println("1 is a root of s");
    else
      System.out.println("1 is not a root of s");
  }
}
