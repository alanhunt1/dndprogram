package initcheck.utils;

// BigFraction.java
// Solution to CS 20, Fall 2002 assignment 1, part 1
// cmc, 9/16/02

import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * A class to represent fractions with arbitrary precision, as
 * both numerator and denominator are stored as BigInteger objects.
 * The fraction is always stored in reduced form, and it is immutable
 * after construction.
 */

public class BigFraction
implements Comparable {

    private BigInteger numerator, denominator;
    private int sign;

    /**
     * Creates a BigFraction equivalent to <code>n/d</code>.
     * <br>Pre-condition: d must not be 0.
     * <br>Post-condition: The fraction is stored in reduced form.
     * @param n is the numerator (before reduction).
     * @param d is the denominator (before reduction).
     * @throws ArithmeticException if d is 0.
     */
    public BigFraction(BigInteger n, BigInteger d) {
        int dsign = d.signum();
        if (dsign == 0)
            throw new ArithmeticException("zero denominator");
        else if (dsign < 0) { // d is negative, so flip signs
           denominator = d.negate();
           numerator = n.negate();
        }
        else {
            numerator = n;
            denominator = d;
        }
        simplify();
    }

    /**
     * Creates a BigFraction equal to the value of the string.
     * <br>Pre-conditions:<br>
     * (1) string must be parseable to a fraction - a whole number with optional
     * '/' followed by another whole number (without any spaces). Examples:
     * <br><code>1/100000000000000000000000
     * <br>-22
     * <br>4/-16</code>
     * <br>(2) if denominator is present, it must not be 0.
     * <br>Post-condition: The fraction is stored in reduced form.
     * @param s is the value as a string.
     * @throws ArithmeticException if value of denominator is 0.
     * @throws NumberFormatException if string is not a valid fraction. */
    public BigFraction(String s) {
        // uses private constructor and parseFraction method
        this(parseFraction(s));
    }

    /**
     * Access to numerator.
     * @return numerator of this BigFraction.
     */
    public BigInteger numerator() { return numerator; }

    /**
     * Access to denominator.
     * @return denominator of this BigFraction.
     */
    public BigInteger denominator() { return denominator; }

    /**
     * Tests for fraction equal to 0.
     * @return true iff fraction is 0.
     */
    public boolean isZero() { return sign == 0; }

    /**
     * Returns the signum function of this BigFraction.
     * @return -1, 0 or 1 as the value of this BigFraction is negative,
     *  zero or positive.
     */
     public int signum() {
        return sign;
     }
     
    /**
     * Compares this BigFraction with the specified object.
     * @param o the other BigFraction.
     * @return a negative number, zero, or a positive number as this BigFraction
     *  is numerically less than, equal to, or greater than o.
     * @throws ClassCastException if o is not a BigFraction.
     */
    public int compareTo(Object o) {
        BigFraction other = (BigFraction)o; // may throw ClassCastException
        return this.minus(other).numerator.signum();
    }

    /**
     * Compares this BigFraction with the specified Object for equality.
     * @param o the Object to which this BigFraction is to be compared.
     * @return true iff the specified Object is a BigFraction whose value
     *  is numerically equal to this BigFraction.
     */
    public boolean equals(Object o) {
        if (!(o instanceof BigFraction)) return false;
        BigFraction other = (BigFraction)o;
        return numerator.equals(other.numerator) &&
               denominator.equals(other.denominator);
    }

    /**
     * Returns the hash code for this BigFraction.
     * @return hash code for this BigFraction.
     */
    public int hashCode() {
        // based on hash code "recipe" in Bloch: Effective Java, 2001, p. 38
        int result = 17; // arbitrary, constant non-zero value to start
        int multiplier = 37; // arbitrary multiplier
        // rest of recipe builds result from individual fields
        result = multiplier * result + numerator.hashCode();
        result = multiplier * result + denominator.hashCode();
        return result;
    }

    /**
     * Converts this BigFraction to a <code>double</code> value. Accuracy is
     * likely to be lost. BigInteger's <code>doubleValue</code> method is used
     * to divide the <code>double</code> value of the numerator by the
     * <code>double</code> value of the denominator.
     * @return this BigFraction converted to a <code>double</code>.
     */
    public double doubleValue() {
        return numerator.doubleValue() / denominator.doubleValue();
    }

    /**
     * Multiplicative inverse, if defined.
     * <br>Pre-condition: this BigFraction must not equal 0.
     * @return a new BigFraction, with numerator equal to this BigFraction's
     *        denominator, and denominator equal to this BigFraction's
     *        numerator.
     * @throws ArithmeticException if this BigFraction equals 0.
     */
    public BigFraction inverse() {
        if (isZero())
            throw new ArithmeticException("requested inverse of zero");
        return new BigFraction(denominator, numerator);
    }

    /**
     * Negative (additive inverse).
     * @return a new BigFraction with numerator equal to this BigFraction's numerator
     *        times -1, and denominator equal to this BigFraction's denominator.
     */
    public BigFraction negative() {
        return new BigFraction(numerator.negate(), denominator);
    }

    /**
     * Adds other BigFraction to this BigFraction.
     * @param other is the other BigFraction (to be added to this one).
     * @return new BigFraction equal to the sum of this BigFraction and other.
     */
    public BigFraction plus(BigFraction other) {
        BigInteger part1 = numerator.multiply(other.denominator),
                   part2 = denominator.multiply(other.numerator),
                   newNumer = part1.add(part2),
                   newDenom = denominator.multiply(other.denominator);
        return new BigFraction(newNumer, newDenom);
    }

    /**
     * Subtracts other BigFraction from this BigFraction.
     * @param other is the other BigFraction (to be subtracted from this one).
     * @return new BigFraction equal to the result of this subtraction.
     */
    public BigFraction minus(BigFraction other) {
        // let negative and plus do the work
        return plus(other.negative());
    }

    /**
     * Multiplies other BigFraction by this BigFraction.
     * @param other is the other BigFraction (to be multiplied by this one).
     * @return new BigFraction equal to the result of this multiplication.
     */
    public BigFraction times(BigFraction other) {
        return new BigFraction(numerator.multiply(other.numerator),
                               denominator.multiply(other.denominator));
    }

    /**
     * Divides this BigFraction by other BigFraction, unless the other
     *         BigFraction is 0.
     * @param other is the other BigFraction by which to divide this one.
     * @return new BigFraction equal to the result of this division.
     * @throws ArithmeticException if other is 0.
     */
    public BigFraction dividedBy(BigFraction other) {
        if (other.isZero())
            throw new ArithmeticException("attempt to divide by zero");
        // let inverse and times do the work
        return times(other.inverse());
    }

    /**
     * Parses the string to a BigFraction.
     * <br>Pre-conditions: same as constructor that takes a string parameter.
     * @param s the fraction as a string - with no spaces.
     * @return the BigFraction represented by the parameter.
     * @throws NumberFormatException if parameter is not a valid fraction.
     */
    public static BigFraction parseFraction(String s) {
        StringTokenizer tokens = new StringTokenizer(s, "/");
        int count = tokens.countTokens();
        if (count < 1 || count > 2)
            throw new NumberFormatException("bad fraction: " + s);
        
        // following constructions may throw NumberFormatExceptions too
        BigInteger n = new BigInteger(tokens.nextToken());
        if (count == 1)
            return new BigFraction(n, BigInteger.ONE);
        BigInteger d = new BigInteger(tokens.nextToken());
        return new BigFraction(n, d);
    }

    /**
     * A basic string representation of the fraction. Essentially, this is the
     * inverse of parseFraction(String). The result is a string with the numerator
     * value, followed for non-whole numbers by '/' and denominator value. Examples:
     * <br><code>1/100000000000000000000000
     * <br>-22</code>
     * @return the fraction as a string.
     */
    public String toString() {
        StringBuffer s = new StringBuffer(numerator.toString());
        if (!isWholeNumber())
            s.append("/" + denominator.toString());
        return s.toString();
    }

    /**
     * A formatted string representation of the fraction. The whole part of fraction
     * (if any) is formatted with commas, and the fractional part (if any) is formatted
     * with commas in both numerator and denominator. Examples:
     * <br><code>17/3</code> becomes <code>5 2/3</code>
     * <br><code>1000</code> becomes <code>1,000</code>
     * <br><code>1111/55555</code> becomes <code>1,111/55,555</code>
     * @return formatted string.
     */
    public String formatted() {
        // copy this to allow changes to the copy without changing this
        BigFraction fractionPart = new BigFraction(numerator, denominator);

        // find whole part of number, and subtract if necessary
        BigInteger wholeNumber = numerator.divide(denominator);
        boolean twoParts = !isWholeNumber() &&
                           wholeNumber.abs().compareTo(BigInteger.ONE) >= 0;
        if (twoParts) {
            BigFraction wholePart = new BigFraction(wholeNumber, BigInteger.ONE);
            fractionPart = this.minus(wholePart);
            if (sign < 0)
                fractionPart.numerator = fractionPart.numerator.negate();
        }

        // build the fractional part of the string from the right,
        //    first letting toString do some of the work
        StringBuffer s = new StringBuffer(fractionPart.toString());
        int ptr = s.length() - 1;
        int count = 0;
        while (ptr > 0) {
            if (s.charAt(ptr) == '/') {
                count = 0;
            }else count++;
            
            // insert commas every three numbers
            if (count == 3 && ptr > 0
                           && s.charAt(ptr-1) != '/'
                           && s.charAt(ptr-1) != '-') {
                s.insert(ptr, ',');
                count = 0;
            }
            ptr--;
        }

        // format whole part of number if necessary
        if (twoParts) {
            StringBuffer t = new StringBuffer(wholeNumber.toString());
            t.append(" "); // space to separate whole from fraction
            
            ptr = t.length() - 2; // points to last character in number
            count = 0;
            while (ptr > 0) {
                count++;
                if (count == 3 && ptr > 0 && t.charAt(ptr-1) != '-') {
                    t.insert(ptr, ',');
                    count = 0;
                }
                ptr--;
            }
            s.insert(0, t); // prepend to formatted string
        }
        
        return s.toString();
    }

// private utility methods

    /**
     * Private constructor essentially copies f to new fraction.
     * Used by BigFraction(String s) in conjunction with parseFraction.
     * @param f the fraction to copy.
     */
    private BigFraction(BigFraction f) {
        this(f.numerator, f.denominator);
    }

    /**
     * Simplifies the fraction. Used only by the basic constructor.
     * Reduces numerator and denominator to simplest form, and sets
     * private sign variable.
     */
    private void simplify() {
        // find greatest common divisor of numerator and denominator,
        // and divide both if gcd is non-zero
        BigInteger gcd = numerator.gcd(denominator);
        if (gcd.signum() != 0) {
            numerator = numerator.divide(gcd);
            denominator = denominator.divide(gcd);
        }
        // set sign of fraction to match sign of numerator
        sign = numerator.signum();        
    }

    /**
     * Tests for fraction equal to a whole number.
     * @return true iff denominator is 1.
     */
    private boolean isWholeNumber() {
        return denominator.equals(BigInteger.ONE);
    }

}
