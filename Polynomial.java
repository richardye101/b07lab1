// It has one field representing the coefficients of the polynomial using an array of
//   1
// ii. It has a no-argument constructor that sets the polynomial to zero (i.e. the corresponding array would be [0])
// iii. It has a constructor that takes an array of double as an argument and sets the coefficients accordingly
// iv. It has a method named add that takes one argument of type Polynomial and returns the polynomial resulting from adding the calling object and the argument
// v. It has a method named evaluate that takes one argument of type double representing a value of x and evaluates the polynomial accordingly. For example, if the polynomial is 6 − 2𝑥 + 5𝑥3 and evaluate(-1) is invoked, the result should
// be 3.
// vi. It has a method named hasRoot that takes one argument of type double and
// determines whether this value is a root of the polynomial or not. Note that a root
// is a value of x for which the polynomial evaluates to zero.

public class Polynomial{
  double [] coefficients;

  public Polynomial(){
    coefficients = new double[]{0};
  }

  public Polynomial(double[] poly){
    coefficients = new double[poly.length];
    for(int i = 0; i < poly.length; i++){
      coefficients[i] = poly[i];
    }
  }

  public Polynomial add(Polynomial other){

// Need to determine the smaller array, and then set the array to add to to the opposite one
    double [] toAddTo;
    int length = Math.min(coefficients.length, other.coefficients.length);
    if(coefficients.length == length){
      toAddTo = other.coefficients.clone();
    }else{
      toAddTo = coefficients.clone();
    }

    for(int i = 0; i < length; i++){
      toAddTo[i] = coefficients[i] + other.coefficients[i];
    }
    Polynomial newPoly = new Polynomial(toAddTo);
    return newPoly;
  }

  public double evaluate(double x){
    double result = 0;
    for(int i = 0; i < coefficients.length; i++){
      result = result + coefficients[i] * Math.pow(x,i);
    }
    return result;
  }

  public boolean hasRoot(double root){
    return evaluate(root) == 0;
  }
}
