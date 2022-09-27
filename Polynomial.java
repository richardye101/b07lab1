import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

// Replace the array representing the coefficients by two arrays:
// one representing the non-zero coefficients (of type double) and another one
 // representing the corresponding exponents (of type int). For example, the polynomial
// 6 − 2x + 5x3 would be represented using the arrays [6, -2, 5] and [0, 1, 3]
// b. Update the existing methods accordingly

public class Polynomial{
  double [] coefficients;
  int [] exponents;

  public Polynomial(){
    coefficients = new double[]{0};
    exponents = new int[]{0};
  }

  public Polynomial(double[] coef, int[] exp){
    coefficients = new double[coef.length];
    exponents = new int[exp.length];
    for(int i = 0; i < coef.length; i++){
      coefficients[i] = coef[i];
    }
    for(int i = 0; i < exp.length; i++){
      exponents[i] = exp[i];
    }
  }

  public Polynomial add(Polynomial other){

    // Need to determine the smaller array, and then set the array to add to to the opposite one
    double [] newCoef;
    int [] newExp;
    int termCount = 0;

    // get largest exponent, assume all polynomials have the form where largest exp is at the end
    int largestExp = Math.max(exponents[exponents.length-1], other.exponents[other.exponents.length-1]) + 1;
    // System.out.println("Largest Exp:" + largestExp);
    newCoef = new double[largestExp];
    newExp = new int[largestExp];

    for(int i = 0; i < coefficients.length; i++){
      int curExp = exponents[i];
      newExp[curExp] = curExp;
      newCoef[curExp] = coefficients[i];
    }
    for(int i = 0; i < other.coefficients.length; i++){
      int curExp = other.exponents[i];
      newExp[curExp] = curExp;
      newCoef[curExp] = newCoef[curExp] + other.coefficients[i];
    }
    for(int i = 0; i < largestExp; i++){
      // System.out.print(newCoef[i] + "x^" + newExp[i] + "+");
      if(newCoef[i] != 0){
        termCount++;
      }  
    }
    // System.out.print("\n");

    double [] finalCoef = new double[termCount];
    int [] finalExp = new int[termCount];

    int largestIdx = 0;
    int termIdx = 0;
    while(largestIdx < newCoef.length){
      if(newCoef[largestIdx] != 0){
        finalCoef[termIdx] = newCoef[largestIdx];
        finalExp[termIdx] = newExp[largestIdx];
        termIdx++;
      }
      largestIdx++;
    }
    // System.out.print("Final from addition");
    // for(int i = 0; i < termCount; i++){
    //   System.out.print(finalCoef[i] + "x^" + finalExp[i] + "+");
    // }
    // System.out.print("\n");
    Polynomial newPoly = new Polynomial(finalCoef, finalExp);
    return newPoly;
  }

  public double evaluate(double x){
    double result = 0;
    for(int i = 0; i < coefficients.length; i++){
      result = result + coefficients[i] * Math.pow(x,exponents[i]);
    }
    return result;
  }

  public boolean hasRoot(double root){
    return evaluate(root) == 0;
  }
  // c. Add a method named multiply that takes one argument of type Polynomial and
  // returns the polynomial resulting from multiplying the calling object and the argument.
  // The resulting polynomial should not contain redundant exponents.

  public Polynomial multiply(Polynomial other){
    Polynomial current; 
    current = multiplyOneTermToPolynomial(coefficients[0], exponents[0], other);
    // System.out.println("Initial");
    // current.printCoefficients();
    // current.printExponents();
    Polynomial temp;
    for(int i = 1; i<coefficients.length;i++){
        temp = multiplyOneTermToPolynomial(coefficients[i], exponents[i], other);
        // System.out.println("Multiplying the " + i + "th term");
        // System.out.println("Temp");
        // temp.printCoefficients();
        // temp.printExponents();
        Polynomial added = current.add(temp);
        // System.out.println("Added");
        // added.printCoefficients();
        // added.printExponents();
        current = added;
    }
    // System.out.println("Current");
    // current.printCoefficients();
    // current.printExponents();
    return current;
  }

// d. Add a constructor that takes one argument of type File and initializes the polynomial
 // based on the contents of the file. You can assume that the file contains one line with
 // no whitespaces representing a valid polynomial. For
// example: the line 5-3x2+7x8 corresponds to the polynomial 5 − 3x2 + 7x8
// Fall 2022
// 5x+2x2-6x4
// Hint: you might want to use the following methods: split of the String class,
// parseInt of the Integer class, and parseDouble of the Double class
  public Polynomial(File content) throws FileNotFoundException{
    Scanner scan = new Scanner(content);
		String n = scan.nextLine();
    // System.out.println("The input polynomial from file: " + n);
    
    int nTerms;
    
    // split on + or -'s
    // 5x-3x2+7x8 -> 5x, -3x2, 7x8
    // 5-3x2+7x8 -> 5, -3x2, 7x8
    String [] pos = n.split("\\+");
    nTerms = pos.length;

    for(String term : pos){
      if(term.contains("-")){
        String [] subTerms = term.split("\\-");
        nTerms = nTerms - 1 + subTerms.length;
      }
    }
    // System.out.println("Number of terms:" + nTerms);

    coefficients = new double[nTerms];
    exponents = new int[nTerms];
    int nIdx = 0; // counts position in final coefficients and exponents

    // loop through terms split by positives
    int posIdx = 0;
    while(posIdx<pos.length){
      String term = pos[posIdx];
      // System.out.println("Pos split term: " + term);
      // loop through negative terms within the positive term
      if(term.contains("-")){
        // System.out.println("This term contains negatives");
        String [] subTerms = term.split("\\-");
        int subIdx = 0;
        while(subIdx < subTerms.length){
          // System.out.println(subTerms[subIdx]);
          coefficients[nIdx] = getCeofFromString(subTerms[subIdx]);
          exponents[nIdx] = getExpFromString(subTerms[subIdx]);
          // since terms have been split by "-", then the coefficients are negative
          if(subIdx>0){
            coefficients[nIdx] = coefficients[nIdx] * -1.0;
          }
          nIdx++;
          subIdx++;
        }
      }
      else{
        // System.out.println(term);
        coefficients[nIdx] = getCeofFromString(term);
        exponents[nIdx] = getExpFromString(term);
        nIdx++;
      }
      posIdx++;
    }
  }
  
  // e. Add a method named saveToFile that takes one argument of type String representing
  // a file name and saves the polynomial in textual format in the corresponding file (similar to the format used in part d)
  public void saveToFile(String filename) throws IOException{
    String finalPoly = "";
    for(int i = 0; i < coefficients.length;i++){
      String coef = coefficients[i] + "";
      if(coefficients[i] > 0 && i > 0){
        coef = "+" + coefficients[i];
      }
      finalPoly += coef + "x" + exponents[i];
    }
    // System.out.println("Polynomial to save: " + finalPoly);

    File nFile = new File(filename);
		nFile.createNewFile();
    PrintStream ps = new PrintStream(filename);
		ps.println(finalPoly);
  }
  // f. You can add any supplementary methods you might find useful
  // added:

  // multiplyOneTermToPolynomial: used to help in multiplying polynomials
  private Polynomial multiplyOneTermToPolynomial(double coef, int exp, Polynomial other){
    double [] newCoef = new double[other.coefficients.length];
    int [] newExp = new int[other.coefficients.length];
    // System.out.println("The term multiplied to the other poly");
    for(int i = 0; i<other.coefficients.length; i++){
      newCoef[i] = coef * other.coefficients[i];
      newExp[i] = exp + other.exponents[i];
      // System.out.print(newCoef[i] + "x^" + newExp[i] + "+");
    }
    // System.out.print("\n");
    return new Polynomial(newCoef, newExp);
  }

  // Functions used in reading polynomial from a file
  // getCeofFromString
  private double getCeofFromString(String term){
    String [] coefExp = term.split("x");

    double coef = Double.parseDouble(coefExp[0]);
    return coef;
  }
  // getExpFromString
  private int getExpFromString(String term){
    String [] coefExp = term.split("x");
    if(coefExp.length > 1){
      int exp = Integer.parseInt(coefExp[1]);
      return exp;
    }
    else if(term.contains("x")){
      return 1;
    }
    else return 0;
  }

  // Easy print methods for debugging 
  public void printCoefficients(){
    System.out.print("Coefficients: ");
    for(int i = 0; i < coefficients.length; i++){
      System.out.print(coefficients[i] + ", ");
    }
    System.out.print("\n");
  }
  public void printExponents(){
    System.out.print("Exponents: ");
    for(int i = 0; i < exponents.length; i++){
      System.out.print(exponents[i] + ", ");
    }
    System.out.print("\n");
  }
}
