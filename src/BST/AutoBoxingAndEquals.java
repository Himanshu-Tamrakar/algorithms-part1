/**
 * Question 1 Java autoboxing and equals(). Consider  two double values a and b and their corresponding double values x and y.
 * 1. Find values such that (a == b) is true but x.equals(y) is false;
 * 2. Find values such that (a == b) is false but x.equals(y) is true;
 */
package BST;

public class AutoBoxingAndEquals {

// 1. Values such that (a==b) is true but x.equals(y) is false
// The key to this scenario lies in the special IEEE 754 floating-point representations for positive zero (0.0) and negative zero (-0.0).
// == operator: When comparing two primitive double values, the == operator considers positive zero and negative zero to be equal, returning true.
// Double.equals() method: The equals() method of the Double wrapper class,
// however, differentiates between positive and negative zero based on their underlying bit patterns, and returns false if they are different.
//double a = 0.0;
//    double b = -0.0;
//    Double x = a; // Autoboxes to Double(0.0)
//    Double y = b; // Autoboxes to Double(-0.0)
//
//System.out.println(a == b);        // true
//System.out.println(x.equals(y));   // false

// 2. Values such that (a==b) is false but x.equals(y) is true
//This scenario is not possible because x.equals(y) checks if the two Double objects contain the same primitive double value. If x.equals(y) is true, it means the underlying primitive values are numerically equal.

}
