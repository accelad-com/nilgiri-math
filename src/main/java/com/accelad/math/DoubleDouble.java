package com.accelad.math;

import com.google.common.base.Objects;
import com.google.common.math.DoubleMath;

import java.io.Serializable;

public strictfp class DoubleDouble implements Serializable, Comparable<DoubleDouble>, Cloneable {

    private static final long serialVersionUID = 1L;

    public static final DoubleDouble ZERO = new DoubleDouble();

    public static final DoubleDouble HALF = new DoubleDouble(0.5);

    public static final DoubleDouble PI = new DoubleDouble(3.141592653589793116e+00,
            1.224646799147353207e-16);

    public static final DoubleDouble TWO_PI = new DoubleDouble(6.283185307179586232e+00,
            2.449293598294706414e-16);

    public static final DoubleDouble PI_2 = new DoubleDouble(1.570796326794896558e+00,
            6.123233995736766036e-17);

    public static final DoubleDouble E = new DoubleDouble(2.718281828459045091e+00,
            1.445646891729250158e-16);

    public static final DoubleDouble NaN = new DoubleDouble(Double.NaN, Double.NaN);

    public static final DoubleDouble POSITIVE_INFINITY = new DoubleDouble(Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY);

    public static final DoubleDouble NEGATIVE_INFINITY = new DoubleDouble(Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY);

    public static final double EPS = 1.23259516440783e-32; /* = 2^-106 */

    private static final double SPLIT = 134217729.0D; // 2^27+1, for IEEE double

    private static final int MAX_PRINT_DIGITS = 32;
    private static final DoubleDouble TEN = new DoubleDouble(10.0);
    private static final DoubleDouble LOG_TEN = TEN.log();
    public static final DoubleDouble ONE = new DoubleDouble(1.0);
    public static final DoubleDouble TWO = new DoubleDouble(2.0);
    private static final String SCI_NOT_EXPONENT_CHAR = "E";
    private static final String SCI_NOT_ZERO = "0.0E0";

    private static int magnitude(double x) {
        final double xAbs = Math.abs(x);
        final double xLog10 = Math.log(xAbs) / Math.log(10);
        int xMag = (int) Math.floor(xLog10);

        final double xApprox = Math.pow(10, xMag);
        if (xApprox * 10 <= xAbs) {
            xMag += 1;
        }

        return xMag;
    }

    public static DoubleDouble parse(String str) {
        int i = 0;
        final int strlen = str.length();

        // skip leading whitespace
        while (Character.isWhitespace(str.charAt(i))) {
            i++;
        }

        // check for sign
        boolean isNegative = false;
        if (i < strlen) {
            final char signCh = str.charAt(i);
            if (signCh == '-' || signCh == '+') {
                i++;
                if (signCh == '-') {
                    isNegative = true;
                }
            }
        }

        // scan all digits and accumulate into an integral value
        // Keep track of the location of the decimal point (if any) to allow
        // scaling later
        final DoubleDouble val = new DoubleDouble();

        int numDigits = 0;
        int numBeforeDec = 0;
        int exp = 0;
        while (true) {
            if (i >= strlen) {
                break;
            }
            final char ch = str.charAt(i);
            i++;
            if (Character.isDigit(ch)) {
                final double d = ch - '0';
                val.selfMultiply(TEN);
                // MD: need to optimize this
                val.selfAdd(new DoubleDouble(d));
                numDigits++;
                continue;
            }
            if (ch == '.') {
                numBeforeDec = numDigits;
                continue;
            }
            if (ch == 'e' || ch == 'E') {
                final String expStr = str.substring(i);
                // this should catch any format problems with the exponent
                try {
                    exp = Integer.parseInt(expStr);
                } catch (final NumberFormatException ex) {
                    throw new NumberFormatException(
                            "Invalid exponent " + expStr + " in string " + str);
                }
                break;
            }
            throw new NumberFormatException(
                    "Unexpected character '" + ch + "' at position " + i + " in string " + str);
        }

        if (numBeforeDec == 0) {
            numBeforeDec = numDigits;
        }
        DoubleDouble val2;

        // scale the number correctly
        final int numDecPlaces = numDigits - numBeforeDec - exp;
        if (numDecPlaces == 0) {
            val2 = val;
        } else if (numDecPlaces > 0) {
            final DoubleDouble scale = TEN.pow(numDecPlaces);
            val2 = val.divide(scale);
        } else {
            final DoubleDouble scale = TEN.pow(-numDecPlaces);
            val2 = val.multiply(scale);
        }
        // apply leading sign, if any
        if (isNegative) {
            return val2.negate();
        }
        return val2;

    }

    private static String stringOfChar(char ch, int len) {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len; i++) {
            buf.append(ch);
        }
        return buf.toString();
    }

    public static DoubleDouble valueOf(double x) {
        return new DoubleDouble(x);
    }

    public static DoubleDouble valueOf(String str) {
        return parse(str);
    }

    private double hi = 0.0;

    private double lo = 0.0;

    public DoubleDouble() {
        init(0.0);
    }

    public DoubleDouble(double x) {
        init(x);
    }

    public DoubleDouble(double hi, double lo) {
        init(hi, lo);
    }

    public DoubleDouble(DoubleDouble dd) {
        init(dd);
    }

    public DoubleDouble(String str) {
        this(parse(str));
    }

    public DoubleDouble abs() {
        if (isNaN()) {
            return NaN;
        }
        if (isNegative()) {
            return negate();
        }
        return new DoubleDouble(this);
    }

    public DoubleDouble acos() {
        if (isNaN()) {
            return NaN;
        }
        if ((this.abs()).gt(DoubleDouble.valueOf(1.0))) {
            return NaN;
        }
        final DoubleDouble s = PI_2.subtract(this.asin());
        return s;
    }

    /*
     * // experimental private DoubleDouble selfAdd(double yhi, double ylo) {
     * double H, h, T, t, S, s, e, f; S = hi + yhi; T = lo + ylo; e = S - hi; f
     * = T - lo; s = S-e; t = T-f; s = (yhi-e)+(hi-s); t = (ylo-f)+(lo-t); e =
     * s+T; H = S+e; h = e+(S-H); e = t+h;
     *
     * double zhi = H + e; double zlo = e + (H - zhi); hi = zhi; lo = zlo;
     *
     * return this; }
     */

    public DoubleDouble add(DoubleDouble y) {
        if (isNaN()) {
            return this;
        }
        return new DoubleDouble(this).selfAdd(y);
    }

    public DoubleDouble asin() {
        // Return the arcsine of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        if ((this.abs()).gt(DoubleDouble.valueOf(1.0))) {
            return NaN;
        }
        if (this.equals(DoubleDouble.valueOf(1.0))) {
            return PI_2;
        }
        if (this.equals(DoubleDouble.valueOf(-1.0))) {
            return PI_2.negate();
        }
        final DoubleDouble square = this.multiply(this);
        DoubleDouble s = new DoubleDouble(this);
        DoubleDouble sOld;
        DoubleDouble t = new DoubleDouble(this);
        DoubleDouble w;
        double n = 1.0;
        double numn;
        double denomn;
        do {
            n += 2.0;
            numn = n - 2.0;
            denomn = n - 1.0;
            t = t.divide(DoubleDouble.valueOf(denomn));
            t = t.multiply(DoubleDouble.valueOf(numn));
            t = t.multiply(square);
            w = t.divide(DoubleDouble.valueOf(n));
            sOld = s;
            s = s.add(w);
        } while (s.ne(sOld));
        return s;
    }

    public DoubleDouble atan() {
        if (isNaN()) {
            return NaN;
        }
        DoubleDouble s;
        DoubleDouble sOld;
        if (this.equals(DoubleDouble.valueOf(1.0))) {
            s = PI_2.divide(DoubleDouble.valueOf(2.0));
        } else if (this.equals(DoubleDouble.valueOf(-1.0))) {
            s = PI_2.divide(DoubleDouble.valueOf(-2.0));
        } else if (this.abs().lt(DoubleDouble.valueOf(1.0))) {
            final DoubleDouble msquare = (this.multiply(this)).negate();
            s = new DoubleDouble(this);

            DoubleDouble t = new DoubleDouble(this);
            DoubleDouble w;
            double n = 1.0;
            do {
                n += 2.0;
                t = t.multiply(msquare);
                w = t.divide(DoubleDouble.valueOf(n));
                sOld = s;
                s = s.add(w);
            } while (s.ne(sOld));
        } else {
            final DoubleDouble msquare = (this.multiply(this)).negate();
            s = this.reciprocal().negate();
            DoubleDouble t = new DoubleDouble(s);
            DoubleDouble w;
            double n = 1.0;
            do {
                n += 2.0;
                t = t.divide(msquare);
                w = t.divide(DoubleDouble.valueOf(n));
                sOld = s;
                s = s.add(w);
            } while (s.ne(sOld));
            if (isPositive()) {
                s = s.add(PI_2);
            } else {
                s = s.subtract(PI_2);
            }
        }
        return s;
    }

    public DoubleDouble BernoulliA(int n) {
        // For PI/2 < x < PI, sum from k = 1 to infinity of
        // ((-1)**(k-1))*sin(kx)/(k**2) =
        // x*ln(2) - sum from k = 1 to infinity of
        // ((-1)**(k-1))*(2**(2k) - 1)*B2k*(x**(2*k+1))/(((2*k)!)*(2*k)*(2*k+1))
        // Compute the DoubleDouble Bernoulli number Bn
        // Ported from subroutine BERNOA in
        // Computation of Special Functions by Shanjie Zhang and Jianming Jin
        // I thought of creating a version with all the Bernoulli numbers from
        // B0 to Bn-1 passed in as an input to calculate Bn. However, according
        // Zhang and Jin using the correct zero values for B3, B5, B7 actually
        // gives
        // a much worse result than using the incorrect intermediate B3, B5, B7
        // values caluclated by this algorithm.
        int m;
        int k;
        int j;
        DoubleDouble s;
        DoubleDouble r;
        DoubleDouble temp;
        if (n < 0) {
            return NaN;
        } else if ((n >= 3) && (((n - 1) % 2) == 0)) {
            // B2*n+1 = 0 for n = 1,2,3
            return DoubleDouble.valueOf(0.0);
        }
        final DoubleDouble BN[] = new DoubleDouble[n + 1];
        BN[0] = DoubleDouble.valueOf(1.0);
        if (n == 0) {
            return BN[0];
        }
        BN[1] = DoubleDouble.valueOf(-0.5);
        if (n == 1) {
            return BN[1];
        }
        for (m = 2; m <= n; m++) {
            s = (DoubleDouble.valueOf(m)).add(DoubleDouble.valueOf(1.0));
            s = s.reciprocal();
            s = (DoubleDouble.valueOf(0.5)).subtract(s);
            for (k = 2; k <= m - 1; k++) {
                r = DoubleDouble.valueOf(1.0);
                for (j = 2; j <= k; j++) {
                    temp = (DoubleDouble.valueOf(j)).add(DoubleDouble.valueOf(m));
                    temp = temp.subtract(DoubleDouble.valueOf(k));
                    temp = temp.divide(DoubleDouble.valueOf(j));
                    r = r.multiply(temp);
                } // for (j = 2; j <= k; j++)
                temp = r.multiply(BN[k]);
                s = s.subtract(temp);
            } // for (k = 2; k <= m-1; k++)
            BN[m] = s;
        } // for (m = 2; m <= n; m++)
        return BN[n];
    }

    public DoubleDouble BernoulliB(int n) {
        // B2n = ((-1)**(n-1))*2*((2*n)!)*(1 + 1/(2**(2*n)) + 1/(3**(2*n)) +
        // ...)/((2*PI)**(2*n))
        // = ((-1)**(n-1))*2*(1 + 1/(2**(2*n)) + 1/(3**(2*n)) + ...) * product
        // from m = 1 to 2n of m/(2*PI)
        // for n = 1, 2, 3, ...
        // Compute the DoubleDouble Bernoulli number Bn
        // More efficient than BernoulliA
        // Ported from subroutine BERNOB in
        // Computation of Special Functions by Shanjie Zhang and Jianming Jin
        int k;
        DoubleDouble r1;
        DoubleDouble twoPISqr;
        DoubleDouble r2;
        DoubleDouble s;
        DoubleDouble sOld;
        DoubleDouble temp;
        if (n < 0) {
            return NaN;
        } else if ((n >= 3) && (((n - 1) % 2) == 0)) {
            // B2*n+1 = 0 for n = 1,2,3
            return DoubleDouble.valueOf(0.0);
        }
        final DoubleDouble bn[] = new DoubleDouble[n + 1];
        bn[0] = DoubleDouble.valueOf(1.0);
        if (n == 0) {
            return bn[0];
        }
        bn[1] = DoubleDouble.valueOf(-0.5);
        if (n == 1) {
            return bn[1];
        }
        bn[2] = (DoubleDouble.valueOf(1.0)).divide(DoubleDouble.valueOf(6.0));
        if (n == 2) {
            return bn[2];
        }
        r1 = ((DoubleDouble.valueOf(1.0)).divide(PI)).sqr();
        twoPISqr = TWO_PI.multiply(TWO_PI);
        for (int m = 4; m <= n; m += 2) {
            temp = (DoubleDouble.valueOf(m)).divide(twoPISqr);
            temp = (DoubleDouble.valueOf(m - 1d)).multiply(temp);
            r1 = (r1.multiply(temp)).negate();
            r2 = DoubleDouble.valueOf(1.0);
            s = DoubleDouble.valueOf(1.0);
            k = 2;
            do {
                sOld = s;
                s = (DoubleDouble.valueOf(1.0)).divide(valueOf(k++));
                s = s.pow(m);
                r2 = r2.add(s);
            } while (s.ne(sOld));
            bn[m] = r1.multiply(r2);
        } // for (m = 4; m <= n; m+=2)
        return bn[n];
    }

    /*
     *
     * // experimental public DoubleDouble selfDivide(DoubleDouble y) { double
     * hc, tc, hy, ty, C, c, U, u; C = hi/y.hi; c = SPLIT*C; hc =c-C; u =
     * SPLIT*y.hi; hc = c-hc; tc = C-hc; hy = u-y.hi; U = C * y.hi; hy = u-hy;
     * ty = y.hi-hy; u = (((hc*hy-U)+hc*ty)+tc*hy)+tc*ty; c =
     * ((((hi-U)-u)+lo)-C*y.lo)/y.hi; u = C+c;
     *
     * hi = u; lo = (C-u)+c; return this; }
     */

    public DoubleDouble ceil() {
        if (isNaN()) {
            return NaN;
        }
        final double fhi = Math.ceil(hi);
        double flo = 0.0;
        // Hi is already integral. Ceil the low word
        if (fhi == hi) {
            flo = Math.ceil(lo);
            // do we need to renormalize here?
        }
        return new DoubleDouble(fhi, flo);
    }

    public DoubleDouble round() {
        return this.add(new DoubleDouble(0.5)).floor();
    }

    public DoubleDouble Ci() {
        final DoubleDouble x = new DoubleDouble(this);
        final DoubleDouble Ci = DoubleDouble.valueOf(0.0);
        final DoubleDouble Si = DoubleDouble.valueOf(0.0);
        cisia(x, Ci, Si);
        return Ci;
    }

    public void cisia(DoubleDouble x, DoubleDouble Ci, DoubleDouble Si) {
        // Purpose: Compute cosine and sine integrals Si(x) and
        // Ci(x) (x >= 0)
        // Input x: Argument of Ci(x) and Si(x)
        // Output: Ci(x), Si(x)
        final DoubleDouble bj[] = new DoubleDouble[101];
        // Euler's constant
        final DoubleDouble el = DoubleDouble
                .valueOf(.57721566490153286060651209008240243104215933593992);
        DoubleDouble x2;
        DoubleDouble xr;
        int k;
        int m;
        DoubleDouble xa1;
        DoubleDouble xa0;
        DoubleDouble xa;
        DoubleDouble xs;
        DoubleDouble xg1;
        DoubleDouble xg2;
        DoubleDouble xcs;
        DoubleDouble xss;
        DoubleDouble xf;
        DoubleDouble xg;
        int i1;
        int i2;
        DoubleDouble var1;
        DoubleDouble var2;

        x2 = x.multiply(x);
        if (x.isZero()) {
            Ci = DoubleDouble.valueOf(-1.0E300);
            Si = DoubleDouble.valueOf(0.0);
        } else if (x.le(DoubleDouble.valueOf(16.0))) {
            xr = (DoubleDouble.valueOf(-0.25)).multiply(x2);
            Ci = (el.add(x.log())).add(xr);
            for (k = 2; k <= 40; k++) {
                xr = ((((DoubleDouble.valueOf(-0.5)).multiply(xr))
                        .multiply(DoubleDouble.valueOf(k - 1)))
                        .divide(DoubleDouble.valueOf(k * k * (2 * k - 1)))).multiply(x2);
                Ci = Ci.add(xr);
                if ((xr.abs()).lt((Ci.abs()).multiply(DoubleDouble.valueOf(DoubleDouble.EPS)))) {
                    break;
                }
            } // for (k = 2; k <= 40; k++)
            xr = new DoubleDouble(x);
            Si = new DoubleDouble(x);
            for (k = 1; k <= 40; k++) {
                xr = (((((DoubleDouble.valueOf(-0.5)).multiply(xr))
                        .multiply(DoubleDouble.valueOf(2 * k - 1))).divide(DoubleDouble.valueOf(k)))
                        .divide(DoubleDouble.valueOf(4 * k * k + 4 * k + 1))).multiply(x2);
                Si = Si.add(xr);
                if ((xr.abs()).lt((Si.abs()).multiply(DoubleDouble.valueOf(DoubleDouble.EPS)))) {
                    return;
                }
            } // for (k = 1; k <= 40; k++)

        } // else if x <= 16
        else if (x.le(DoubleDouble.valueOf(32.0))) {
            m = (((DoubleDouble.valueOf(47.2)).add((DoubleDouble.valueOf(0.82)).multiply(x)))
                    .trunc()).intValue();
            xa1 = DoubleDouble.valueOf(0.0);
            xa0 = DoubleDouble.valueOf(1.0E-100);
            for (k = m; k >= 1; k--) {
                xa = ((((DoubleDouble.valueOf(4.0)).multiply(DoubleDouble.valueOf(k)))
                        .multiply(xa0)).divide(x)).subtract(xa1);
                bj[k - 1] = new DoubleDouble(xa);
                xa1 = new DoubleDouble(xa0);
                xa0 = new DoubleDouble(xa);
            } // for (k = m; k >= 1; k--)
            xs = new DoubleDouble(bj[0]);
            for (k = 2; k <= m; k += 2) {
                xs = xs.add((DoubleDouble.valueOf(2.0)).multiply(bj[k]));
            }
            bj[0] = bj[0].divide(xs);
            for (k = 1; k <= m; k++) {
                bj[k] = bj[k].divide(xs);
            }
            xr = DoubleDouble.valueOf(1.0);
            xg1 = new DoubleDouble(bj[0]);
            for (k = 1; k <= m; k++) {
                i1 = (2 * k - 1) * (2 * k - 1);
                var1 = DoubleDouble.valueOf(i1);
                i2 = k * (2 * k + 1) * (2 * k + 1);
                var2 = DoubleDouble.valueOf(i2);
                xr = ((((DoubleDouble.valueOf(0.25)).multiply(xr)).multiply(var1)).divide(var2))
                        .multiply(x);
                xg1 = xg1.add(bj[k].multiply(xr));
            }
            xr = DoubleDouble.valueOf(1.0);
            xg2 = new DoubleDouble(bj[0]);
            for (k = 1; k <= m; k++) {
                i1 = (2 * k - 3) * (2 * k - 3);
                var1 = DoubleDouble.valueOf(i1);
                i2 = k * (2 * k - 1) * (2 * k - 1);
                var2 = DoubleDouble.valueOf(i2);
                xr = ((((DoubleDouble.valueOf(0.25)).multiply(xr)).multiply(var1)).divide(var2))
                        .multiply(x);
                xg2 = xg2.add(bj[k].multiply(xr));
            }
            xcs = ((DoubleDouble.valueOf(0.5)).multiply(x)).cos();
            xss = ((DoubleDouble.valueOf(0.5)).multiply(x)).sin();
            Ci = (((el.add(x.log())).subtract((x.multiply(xss)).multiply(xg1)))
                    .add(((DoubleDouble.valueOf(2.0)).multiply(xcs)).multiply(xg2)))
                    .subtract(((DoubleDouble.valueOf(2.0)).multiply(xcs)).multiply(xcs));
            Si = (((x.multiply(xcs)).multiply(xg1))
                    .add(((DoubleDouble.valueOf(2.0)).multiply(xss)).multiply(xg2)))
                    .subtract(x.sin());
        } // else if x <= 32
        else {
            xr = DoubleDouble.valueOf(1.0);
            xf = DoubleDouble.valueOf(1.0);
            for (k = 1; k <= 9; k++) {
                i1 = k * (2 * k - 1);
                var1 = DoubleDouble.valueOf(i1);
                xr = (((DoubleDouble.valueOf(-2.0)).multiply(xr)).multiply(var1)).divide(x2);
                xf = xf.add(xr);
            }
            xr = x.reciprocal();
            xg = new DoubleDouble(xr);
            for (k = 1; k <= 8; k++) {
                i1 = (2 * k + 1) * k;
                var1 = DoubleDouble.valueOf(i1);
                xr = (((DoubleDouble.valueOf(-2.0)).multiply(xr)).multiply(var1)).divide(x2);
                xg = xg.add(xr);
            }
            Ci = ((xf.multiply(x.sin())).divide(x)).subtract((xg.multiply(x.cos())).divide(x));
            Si = (DoubleDouble.PI_2.subtract((xf.multiply(x.cos())).divide(x)))
                    .subtract((xg.multiply(x.sin())).divide(x));
        }
        return;
    }

    @Override
    public int compareTo(DoubleDouble other) {

        if (hi < other.hi) {
            return -1;
        }
        if (hi > other.hi) {
            return 1;
        }
        if (lo < other.lo) {
            return -1;
        }
        if (lo > other.lo) {
            return 1;
        }
        return 0;
    }

    public DoubleDouble cos() {
        boolean negate = false;
        // Return the cosine of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        DoubleDouble twoPIFullTimes;
        DoubleDouble twoPIremainder;
        if ((this.abs()).gt(TWO_PI)) {
            twoPIFullTimes = (this.divide(TWO_PI)).trunc();
            twoPIremainder = this.subtract(TWO_PI.multiply(twoPIFullTimes));
        } else {
            twoPIremainder = this;
        }
        if (twoPIremainder.gt(PI)) {
            twoPIremainder = twoPIremainder.subtract(PI);
            negate = true;
        } else if (twoPIremainder.lt(PI.negate())) {
            twoPIremainder = twoPIremainder.add(PI);
            negate = true;
        }
        final DoubleDouble msquare = (twoPIremainder.multiply(twoPIremainder)).negate();
        DoubleDouble s = DoubleDouble.valueOf(1.0);
        DoubleDouble sOld;
        DoubleDouble t = DoubleDouble.valueOf(1.0);
        double n = 0.0;
        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(msquare);
            sOld = s;
            s = s.add(t);
        } while (s.ne(sOld));
        if (negate) {
            s = s.negate();
        }
        return s;
    }

    public DoubleDouble cosh() {
        // Return the cosh of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        final DoubleDouble square = this.multiply(this);
        DoubleDouble s = DoubleDouble.valueOf(1.0);
        DoubleDouble sOld;
        DoubleDouble t = DoubleDouble.valueOf(1.0);
        double n = 0.0;
        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(square);
            sOld = s;
            s = s.add(t);
        } while (s.ne(sOld));
        return s;
    }

    public DoubleDouble divide(DoubleDouble y) {
        double hc, tc, hy, ty, C, c, U, u;
        C = hi / y.hi;
        c = SPLIT * C;
        hc = c - C;
        u = SPLIT * y.hi;
        hc = c - hc;
        tc = C - hc;
        hy = u - y.hi;
        U = C * y.hi;
        hy = u - hy;
        ty = y.hi - hy;
        u = (((hc * hy - U) + hc * ty) + tc * hy) + tc * ty;
        c = ((((hi - U) - u) + lo) - C * y.lo) / y.hi;
        u = C + c;

        final double zhi = u;
        final double zlo = (C - u) + c;
        return new DoubleDouble(zhi, zlo);
    }

    public double doubleValue() {
        return hi + lo;
    }

    public String dump() {
        return "DD<" + hi + ", " + lo + ">";
    }

    public DoubleDouble exp() {
        boolean invert = false;
        // Return the exponential of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }

        if (doubleValue() > 690) {
            return DoubleDouble.POSITIVE_INFINITY;
        }

        if (doubleValue() < -690) {
            return DoubleDouble.ZERO;
        }

        DoubleDouble x = this;
        if (x.lt(DoubleDouble.valueOf(0.0))) {
            // Much greater precision if all numbers in the series have the same
            // sign.
            x = x.negate();
            invert = true;
        }
        DoubleDouble s = (DoubleDouble.valueOf(1.0)).add(x);
        DoubleDouble sOld;
        DoubleDouble t = new DoubleDouble(x);
        double n = 1.0;

        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(x);
            sOld = s;
            s = s.add(t);
        } while (s.ne(sOld));
        if (invert) {
            s = s.reciprocal();
        }
        return s;

    }

    private String extractSignificantDigits(boolean insertDecimalPoint, int[] magnitude) {
        DoubleDouble y = this.abs();
        // compute *correct* magnitude of y
        int mag = magnitude(y.hi);
        final DoubleDouble scale = TEN.pow(mag);
        y = y.divide(scale);

        // fix magnitude if off by one
        if (y.gt(TEN)) {
            y = y.divide(TEN);
            mag += 1;
        } else if (y.lt(ONE)) {
            y = y.multiply(TEN);
            mag -= 1;
        }

        final int decimalPointPos = mag + 1;
        final StringBuilder buf = new StringBuilder();
        final int numDigits = MAX_PRINT_DIGITS - 1;
        for (int i = 0; i <= numDigits; i++) {
            if (insertDecimalPoint && i == decimalPointPos) {
                buf.append('.');
            }
            final int digit = (int) y.hi;

            if (digit < 0) {
                break;
            }
            boolean rebiasBy10 = false;
            char digitChar;
            if (digit > 9) {
                // set flag to re-bias after next 10-shift
                rebiasBy10 = true;
                // output digit will end up being '9'
                digitChar = '9';
            } else {
                digitChar = (char) ('0' + digit);
            }
            buf.append(digitChar);
            y = y.subtract(DoubleDouble.valueOf(digit).multiply(TEN));
            if (rebiasBy10) {
                y.selfAdd(TEN);
            }

            boolean continueExtractingDigits = true;

            final int remMag = magnitude(y.hi);
            if (remMag < 0 && Math.abs(remMag) >= (numDigits - i)) {
                continueExtractingDigits = false;
            }
            if (!continueExtractingDigits) {
                break;
            }
        }
        magnitude[0] = mag;
        return buf.toString();
    }

    public DoubleDouble factorial(int fac) {
        DoubleDouble prod;
        if (fac < 0) {
            return NaN;
        }
        if ((fac >= 0) && (fac <= 1)) {
            return DoubleDouble.valueOf(1.0);
        }
        prod = DoubleDouble.valueOf(fac--);
        while (fac > 1) {
            prod = prod.multiply(DoubleDouble.valueOf(fac--));
        }
        return prod;
    }

    public DoubleDouble floor() {
        if (isNaN()) {
            return NaN;
        }
        final double fhi = Math.floor(hi);
        double flo = 0.0;
        // Hi is already integral. Floor the low word
        if (fhi == hi) {
            flo = Math.floor(lo);
        }
        // do we need to renormalize here?
        return new DoubleDouble(fhi, flo);
    }

    public boolean ge(DoubleDouble y) {
        return (hi > y.hi) || (hi == y.hi && lo >= y.lo);
    }

    double getHighComponent() {
        return hi;
    }

    double getLowComponent() {
        return lo;
    }

    private String getSpecialNumberString() {
        if (isZero()) {
            return "0.0";
        }
        if (isNaN()) {
            return "NaN ";
        }
        return null;
    }

    public boolean gt(DoubleDouble y) {
        return (hi > y.hi) || (hi == y.hi && lo > y.lo);
    }

    private void init(double x) {
        init(x, 0.0);
    }

    private void init(double hi, double lo) {
        this.hi = hi;
        this.lo = lo;
    }

    private void init(DoubleDouble dd) {
        init(dd.hi, dd.lo);
    }

    public int intValue() {
        return (int) hi;
    }

    public boolean isInfinite() {
        return Double.isInfinite(hi);
    }

    public boolean isNaN() {
        return Double.isNaN(hi);
    }

    public boolean isNegative() {
        return hi < 0.0 || (hi == 0.0 && lo < 0.0);
    }

    public boolean isPositive() {
        return hi > 0.0 || (hi == 0.0 && lo > 0.0);
    }

    public boolean isZero() {
        return hi == 0.0 && lo == 0.0;
    }

    public boolean isInteger() {
        return ((hi == Math.floor(hi)) && !Double.isInfinite(hi)) && ((lo == Math
                .floor(lo)) && !Double.isInfinite(lo));
    }
    /*------------------------------------------------------------
     *   Conversion Functions
     *------------------------------------------------------------
     */

    public boolean le(DoubleDouble y) {
        return (hi < y.hi) || (hi == y.hi && lo <= y.lo);
    }

    public DoubleDouble log() {
        // Return the natural log of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        if (isZero()) {
            return NEGATIVE_INFINITY;
        }

        if (isNegative()) {
            return NaN;
        }

        DoubleDouble number = this;
        int intPart = 0;
        while (number.gt(E)) {
            number = number.divide(E);
            intPart++;
        }
        while (number.lt(E.reciprocal())) {
            number = number.multiply(E);
            intPart--;
        }

        final DoubleDouble num = number.subtract(DoubleDouble.valueOf(1.0));
        final DoubleDouble denom = number.add(DoubleDouble.valueOf(1.0));
        final DoubleDouble ratio = num.divide(denom);
        final DoubleDouble ratioSquare = ratio.multiply(ratio);
        DoubleDouble s = DoubleDouble.valueOf(2.0).multiply(ratio);
        DoubleDouble sOld;
        DoubleDouble t = new DoubleDouble(s);
        DoubleDouble w;
        double n = 1.0;

        do {
            n += 2.0;
            t = t.multiply(ratioSquare);
            w = t.divide(DoubleDouble.valueOf(n));
            sOld = s;
            s = s.add(w);
        } while (s.ne(sOld));
        return s.add(DoubleDouble.valueOf(intPart));
    }

    public DoubleDouble log10() {
        return this.log().divide(LOG_TEN);
    }

    /*------------------------------------------------------------
     *   Predicates
     *------------------------------------------------------------
     */

    public boolean lt(DoubleDouble y) {
        return (hi < y.hi) || (hi == y.hi && lo < y.lo);
    }

    public DoubleDouble max(DoubleDouble x) {
        if (this.ge(x)) {
            return this;
        } else {
            return x;
        }
    }

    public DoubleDouble min(DoubleDouble x) {
        if (this.le(x)) {
            return this;
        } else {
            return x;
        }
    }

    public DoubleDouble multiply(DoubleDouble y) {
        if (isNaN()) {
            return this;
        }
        if (y.isNaN()) {
            return y;
        }
        return (new DoubleDouble(this)).selfMultiply(y);
    }

    public boolean ne(DoubleDouble y) {
        return hi != y.hi || lo != y.lo;
    }

    public DoubleDouble negate() {
        if (isNaN()) {
            return this;
        }
        return new DoubleDouble(-hi, -lo);
    }

    public DoubleDouble pow(DoubleDouble x) {
        boolean invert = false;
        if (x.isNaN() || x.isInfinite()) {
            return NaN;
        } else if (isInfinite() || isNaN()) {
            return NaN;
        }
        if (x.isZero()) {
            return ONE;
        } else {
            if (isZero()) {
                return ZERO;
            } else if (isNegative()) {
                if (x.isInteger())
                    return pow(x.intValue());
                else
                    return NaN;
            } else {
                final DoubleDouble loga = this.log();
                DoubleDouble base = x.multiply(loga);
                if (base.lt(ZERO)) {
                    // Much greater precision if all numbers in the series have the same
                    // sign.
                    base = base.negate();
                    invert = true;
                }
                DoubleDouble s = ONE.add(base);
                DoubleDouble sOld;
                DoubleDouble t = new DoubleDouble(base);
                double n = 1.0;

                do {
                    n += 1.0;
                    t = t.divide(DoubleDouble.valueOf(n));
                    t = t.multiply(base);
                    sOld = s;
                    s = s.add(t);
                } while (s.ne(sOld));
                if (invert) {
                    s = s.reciprocal();
                }
                return s;
            }
        }
    }

    public DoubleDouble pow(int exp) {
        if (exp == 0.0) {
            return valueOf(1.0);
        }

        DoubleDouble r = new DoubleDouble(this);
        DoubleDouble s = valueOf(1.0);
        int n = Math.abs(exp);

        if (n > 1) {
            /* Use binary exponentiation */
            while (n > 0) {
                if (n % 2 == 1) {
                    s.selfMultiply(r);
                }
                n /= 2;
                if (n > 0) {
                    r = r.sqr();
                }
            }
        } else {
            s = r;
        }

        /* Compute the reciprocal if n is negative. */
        if (exp < 0) {
            return s.reciprocal();
        }
        return s;
    }

    public DoubleDouble reciprocal() {
        double hc, tc, hy, ty, bigC, c, bigU, u;
        bigC = 1.0 / hi;
        c = SPLIT * bigC;
        hc = c - bigC;
        u = SPLIT * hi;
        hc = c - hc;
        tc = bigC - hc;
        hy = u - hi;
        bigU = bigC * hi;
        hy = u - hy;
        ty = hi - hy;
        u = (((hc * hy - bigU) + hc * ty) + tc * hy) + tc * ty;
        c = (((1.0 - bigU) - u) - bigC * lo) / hi;

        final double zhi = bigC + c;
        final double zlo = (bigC - zhi) + c;
        return new DoubleDouble(zhi, zlo);
    }

    public DoubleDouble rint() {
        if (isNaN()) {
            return this;
        }
        // may not be 100% correct
        final DoubleDouble plus5 = this.add(new DoubleDouble(0.5));
        return plus5.floor();
    }

    private DoubleDouble selfAdd(DoubleDouble y) {
        double bigH, h, bigT, t, bigS, s, e, f;
        bigS = hi + y.hi;
        bigT = lo + y.lo;
        e = bigS - hi;
        f = bigT - lo;
        s = bigS - e;
        t = bigT - f;
        s = (y.hi - e) + (hi - s);
        t = (y.lo - f) + (lo - t);
        e = s + bigT;
        bigH = bigS + e;
        h = e + (bigS - bigH);
        e = t + h;

        final double zhi = bigH + e;
        final double zlo = e + (bigH - zhi);
        hi = zhi;
        lo = zlo;

        return this;
    }

    /*------------------------------------------------------------
     *   Output
     *------------------------------------------------------------
     */

    private DoubleDouble selfMultiply(DoubleDouble y) {
        double hx, tx, hy, ty, bigC, c;
        bigC = SPLIT * hi;
        hx = bigC - hi;
        c = SPLIT * y.hi;
        hx = bigC - hx;
        tx = hi - hx;
        hy = c - y.hi;
        bigC = hi * y.hi;
        hy = c - hy;
        ty = y.hi - hy;
        c = ((((hx * hy - bigC) + hx * ty) + tx * hy) + tx * ty) + (hi * y.lo + lo * y.hi);
        final double zhi = bigC + c;
        hx = bigC - zhi;
        final double zlo = c + hx;
        hi = zhi;
        lo = zlo;
        return this;
    }

    public DoubleDouble si() {
        final DoubleDouble x = new DoubleDouble(this);
        final DoubleDouble ci = DoubleDouble.valueOf(0.0);
        final DoubleDouble si = DoubleDouble.valueOf(0.0);
        cisia(x, ci, si);
        return si;
    }

    public int signum() {
        if (isPositive()) {
            return 1;
        }
        if (isNegative()) {
            return -1;
        }
        return 0;
    }

    public DoubleDouble sin() {
        boolean negate = false;
        // Return the sine of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        DoubleDouble twoPIFullTimes;
        DoubleDouble twoPIremainder;
        if ((this.abs()).gt(TWO_PI)) {
            twoPIFullTimes = (this.divide(TWO_PI)).trunc();
            twoPIremainder = this.subtract(TWO_PI.multiply(twoPIFullTimes));
        } else {
            twoPIremainder = this;
        }
        if (twoPIremainder.gt(PI)) {
            twoPIremainder = twoPIremainder.subtract(PI);
            negate = true;
        } else if (twoPIremainder.lt(PI.negate())) {
            twoPIremainder = twoPIremainder.add(PI);
            negate = true;
        }
        final DoubleDouble msquare = (twoPIremainder.multiply(twoPIremainder)).negate();
        DoubleDouble s = new DoubleDouble(twoPIremainder);
        DoubleDouble sOld;
        DoubleDouble t = new DoubleDouble(twoPIremainder);
        double n = 1.0;
        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(msquare);
            sOld = s;
            s = s.add(t);
        } while (s.ne(sOld));
        if (negate) {
            s = s.negate();
        }
        return s;
    }

    public DoubleDouble sinh() {
        // Return the sinh of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        final DoubleDouble square = this.multiply(this);
        DoubleDouble s = new DoubleDouble(this);
        DoubleDouble sOld;
        DoubleDouble t = new DoubleDouble(this);
        double n = 1.0;
        do {
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            n += 1.0;
            t = t.divide(DoubleDouble.valueOf(n));
            t = t.multiply(square);
            sOld = s;
            s = s.add(t);
        } while (s.ne(sOld));
        return s;
    }

    public DoubleDouble sqr() {
        return this.multiply(this);
    }

    public DoubleDouble sqrt() {
        /*
         * Strategy: Use Karp's trick: if x is an approximation to sqrt(a), then
         *
         * sqrt(a) = a*x + [a - (a*x)^2] * x / 2 (approx)
         *
         * The approximation is accurate to twice the accuracy of x. Also, the
         * multiplication (a*x) and [-]*x can be done with only half the
         * precision.
         */

        if (isZero()) {
            return new DoubleDouble(0.0);
        }

        if (isNegative()) {
            return NaN;
        }

        final double x = 1.0 / Math.sqrt(hi);
        final double ax = hi * x;

        final DoubleDouble axdd = new DoubleDouble(ax);
        final DoubleDouble diffSq = this.subtract(axdd.sqr());
        final double d2 = diffSq.hi * (x * 0.5);

        return axdd.add(new DoubleDouble(d2));
    }

    public DoubleDouble subtract(DoubleDouble y) {
        if (isNaN()) {
            return this;
        }
        return add(y.negate());
    }

    public DoubleDouble tan() {
        // Return the tangent of a DoubleDouble number
        if (isNaN()) {
            return NaN;
        }
        DoubleDouble piFullTimes;
        DoubleDouble piRemainder;
        if ((this.abs()).gt(PI)) {
            piFullTimes = (this.divide(PI)).trunc();
            piRemainder = this.subtract(PI.multiply(piFullTimes));
        } else {
            piRemainder = this;
        }
        if (piRemainder.gt(PI_2)) {
            piRemainder = piRemainder.subtract(PI);
        } else if (piRemainder.lt(PI_2.negate())) {
            piRemainder = piRemainder.add(PI);
        }
        if (piRemainder.equals(PI_2)) {
            return POSITIVE_INFINITY;
        } else if (piRemainder.equals(PI_2.negate())) {
            return NEGATIVE_INFINITY;
        }
        int twon;
        DoubleDouble twotwon;
        DoubleDouble twotwonm1;
        final DoubleDouble square = piRemainder.multiply(piRemainder);
        DoubleDouble s = new DoubleDouble(piRemainder);
        DoubleDouble sOld;
        DoubleDouble t = new DoubleDouble(piRemainder);
        int n = 1;
        do {
            n++;
            twon = 2 * n;
            t = t.divide(factorial(twon));
            twotwon = (DoubleDouble.valueOf(2.0)).pow(twon);
            twotwonm1 = twotwon.subtract(DoubleDouble.valueOf(1.0));
            t = t.multiply(twotwon);
            t = t.multiply(twotwonm1);
            t = t.multiply(BernoulliB(n));
            t = t.multiply(square);
            sOld = s;
            s = s.add(t);
        } while (s.ne(sOld));
        return s;
    }

    public String toSciNotation() {
        // special case zero, to allow as
        if (isZero()) {
            return SCI_NOT_ZERO;
        }

        final String specialStr = getSpecialNumberString();
        if (specialStr != null) {
            return specialStr;
        }

        final int[] magnitude = new int[1];
        final String digits = extractSignificantDigits(false, magnitude);
        final String expStr = SCI_NOT_EXPONENT_CHAR + magnitude[0];

        // should never have leading zeroes
        // MD - is this correct? Or should we simply strip them if they are
        // present?
        if (digits.charAt(0) == '0') {
            throw new IllegalStateException("Found leading zero: " + digits);
        }

        // add decimal point
        String trailingDigits = "";
        if (digits.length() > 1) {
            trailingDigits = digits.substring(1);
        }
        final String digitsWithDecimal = digits.charAt(0) + "." + trailingDigits;

        if (this.isNegative()) {
            return "-" + digitsWithDecimal + expStr;
        }
        return digitsWithDecimal + expStr;
    }

    /*------------------------------------------------------------
     *   Input
     *------------------------------------------------------------
     */

    public String toStandardNotation() {
        final String specialStr = getSpecialNumberString();
        if (specialStr != null) {
            return specialStr;
        }

        final int[] magnitude = new int[1];
        final String sigDigits = extractSignificantDigits(true, magnitude);
        final int decimalPointPos = magnitude[0] + 1;

        String num = sigDigits;
        // add a leading 0 if the decimal point is the first char
        if (sigDigits.charAt(0) == '.') {
            num = "0" + sigDigits;
        } else if (decimalPointPos < 0) {
            num = "0." + stringOfChar('0', -decimalPointPos) + sigDigits;
        } else if (sigDigits.indexOf('.') == -1) {
            // no point inserted - sig digits must be smaller than magnitude of
            // number
            // add zeroes to end to make number the correct size
            final int numZeroes = decimalPointPos - sigDigits.length();
            final String zeroes = stringOfChar('0', numZeroes);
            num = sigDigits + zeroes + ".0";
        }

        if (this.isNegative()) {
            return "-" + num;
        }
        return num;
    }

    @Override
    public String toString() {
        // final int mag = magnitude(hi);
        // if (mag >= -3 && mag <= 20) {
        // return toStandardNotation();
        // } else if ((mag <= -301) || (mag >= 301)) {
        // return String.valueOf(hi);
        // }
        // return toSciNotation();

        return Double.toString(doubleValue());
    }

    public DoubleDouble trunc() {
        if (isNaN()) {
            return NaN;
        }
        if (isPositive()) {
            return floor();
        } else {
            return ceil();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hi, lo);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DoubleDouble) {
            DoubleDouble that = (DoubleDouble) object;
            return DoubleMath.fuzzyEquals(hi, that.hi, 1E-12) && DoubleMath
                    .fuzzyEquals(lo, that.lo, 1E-12);
        }
        return false;
    }
}
