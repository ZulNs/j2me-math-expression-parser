package MathParser;

import java.util.Random;

public abstract class ExtMath {
    public static final double abs(double n) {
        return Math.abs(n);
    }
    
    public static final double acosh(double n) {
        return ieee754_log(n + Math.sqrt(n * n - 1.0));
    }
    
    public static final double asinh(double n) {
        return ieee754_log(n + Math.sqrt(n * n + 1.0));
    }
    
    public static final double atanh(double n) {
        return ieee754_log((1.0 + n) / (1.0 - n)) / 2.0;
    }
    
    public static final double aveDev(double[] args) {
        double mean = mean(args);
        double sum = 0.0;
        int n = args.length;
        for (int i = 0; i < n; i++)
            sum += Math.abs(args[i] - mean);
        return sum / n;
    }
    
    public static final double average(double[] args) {
        return mean(args);
    }
    
    public static final double ceiling(double n) {
        return Math.ceil(n);
    }
    
    public static final double ceiling(double n, double s) {
        if (n == 0.0 || s == 0.0) return 0.0;
        if (sign(n) != sign(s))   return Double.NaN;
        double nds = (n / s);
        if (nds < 0.0) nds = Math.floor(nds);
        else           nds = Math.ceil(nds);
        return nds * s;
    }
    
    public static final double combin(double n, double k) {
        int in = (int) n;
        int ik = (int) k;
        if (in < 0 || ik < 0 || ik > in) return Double.NaN;
        int r = in - ik;
        if (r == 0) return 1.0;
        if (r > ik) {
            int tmp = ik;
            ik = r;
            r = tmp;
        }
        double res = 1.0;
        while (r > 1 || in > ik) {
            if (in > ik) { res *= in; in--; }
            if (r > 1)   { res /= r;  r--; }
        }
        return res;
    }
    
    public static final double cos(double n) {
        return Math.cos(n);
    }
    
    public static final double cosh(double n) {
        return (ieee754_exp(n) + ieee754_exp(-n)) / 2.0;
    }
    
    public static final double degrees(double r) {
        return Math.toDegrees(r);
    }
    
    public static final double devSq(double[] args) {
        double mean = mean(args);
        double sum = 0.0, d;
        int n = args.length;
        for (int i = 0; i < n; i++) {
            d = args[i] - mean;
            sum += d * d;
        }
        return sum;
    }
    
    public static final double dms2dec(double[] args) {
        double deg = args[0], min = 0.0, sec = 0.0;
        if (args.length >= 2) min = args[1];
        if (args.length == 3) sec = args[2];
        return Math.abs(deg) + Math.abs(min) / 60.0 + Math.abs(sec) / 3600.0;
    }
    
    public static final double even(double n) {
        return roundUp(n / 2.0) * 2.0;
    }
    
    public static final double exp(double n) {
        return ieee754_exp(n);
    }
    
    private static double exp10(double d) {
        int dig = new Double(d).intValue();
        double rs = Double.parseDouble("1e" + Integer.toString(dig));
        return rs;
    }
    
    public static final double fact(double n) {
        int in = (int) n;
        if (in < 0) return Double.NaN;
        if (in == 0) return 1.0;
        return fact(in, 1, 1);
    }
    
    private static final double fact(int from, int to, int step) {
        double res = 1.0;
        int f = from;
        while (f != to && res < Double.MAX_VALUE) {
            res *= f;
            f -= step;
        }
        return res;
    }
    
    public static final double factDouble(double n) {
        int in = (int) n;
        if (in < 0)      return Double.NaN;
        if (in % 2 == 0) return fact(in, 0, 2);
        else             return fact(in, 1, 2);
    }
    
    public static final double fisher(double x) {
        return ieee754_log((1.0 + x) / (1.0 - x)) / 2.0;
    }
    
    public static final double fisherInv(double y) {
        double tmp = exp(2.0 * y);
        return (tmp - 1.0) / (tmp + 1.0);
    }
    
    public static final double floor(double n) {
        return Math.floor(n);
    }
    
    public static final double floor(double n, double s) {
        if (n == 0.0)           return n;
        if (s == 0.0)           return Double.POSITIVE_INFINITY;
        if (sign(n) != sign(s)) return Double.NaN;
        return trunc(n / s) * s;
    }
    
    public static final double frac(double n) {
        return n - trunc(n);
    }
    
    public static final double geoMean(double[] args) {
        int n = args.length;
        double res = 1.0;
        for (int i = 0; i < n; i++) {
            if (args[i] <= 0.0) return Double.NaN;
            res *= args[i];
        }
        return power(res, 1.0 / n);
    }
    
    public static final double integer(double n) {
        return Math.floor(n);
    }
    
    public static final double ln(double n) {
        return ieee754_log(n);
    }
    
    public static final double log(double n, double base) {
        return ieee754_log(n) / ieee754_log(base);
    }
    
    public static final double log10(double n) {
        return ieee754_log(n) / ieee754_log(10.0);
    }
    
    public static final double max(double[] args) {
        int l = args.length, i = 1;
        double res = args[0];
        while (i < l) { res = Math.max(res, args[i]); i++; }
        return res;
    }
    
    public static final double mean(double[] args) {
        return sum(args) / args.length;
    }
    
    public static final double median(double[] args) {
        int l = args.length;
        double tmp;
        for (int i = 0; i < l - 1; i++) { // sorting args[]
            for (int j = i + 1; j < l; j++) {
                if (args[i] > args[j]) {
                    tmp = args[i];
                    args[i] = args[j];
                    args[j] = tmp;
                }
            }
        }
        int mid = l / 2;
        if (l % 2 == 1) return args[mid];
        return (args[mid - 1] + args[mid]) / 2.0;
    }
    
    public static final double min(double[] args) {
        int l = args.length, i = 1;
        double res = args[0];
        while (i < l) { res = Math.min(res, args[i]); i++; }
        return res;
    }
    
    public static final double mod(double n, double div) {
        double realMod = n % div;
        if (sign(n) != sign(div))
            return realMod + div;
        return realMod;
    }
    
    public static final double mode(double[] args) {
        int l = args.length;
        int count, maxCount = 0;
        double value = 0;
        for (int i = 0; i < l; i++) {
            count = 0;
            for (int j = 0; j < l; j++)
                if (args[i] == args[j]) count++;
            if (count > maxCount) {
                maxCount = count;
                value = args[i];
            }
        }
        if (maxCount == 1) return Double.NaN;
        return value;
    }
    
    public static final double mRound(double n, double m) {
        if (m == 0.0) return 0.0;
        if (sign(n) != sign(m)) return Double.NaN;
        return round(n / m) * m;
    }
    
    public static final double multiNomial(double[] args) {
        int l = args.length, n, sum = 0;
        for (int i = 0; i < l; i++) {
            n = (int) args[i];
            if (n < 0) return Double.NaN;
            sum += n;
        }
        double res = fact(sum);
        for (int i = 0; i < l; i++) {
            n = (int) args[i];
            res /= fact(n);
        }
        return res;
    }
    
    public static final double odd(double n) {
        double r = roundUp(n);
        if (r % 2 == 0.0) return r + sign(n);
        return r;
    }
    
    public static final double permut(double n, double k) {
        int in = (int) n, ik = (int) k;
        if (in < 0 || ik < 0 || ik > in) return Double.NaN;
        return fact(in, in - ik, 1);
    }
    
    public static final double pi() {
        return Math.PI;
    }
    
    public static final double power(double n, double p) {
        if (n < 0.0) {
            if (frac(p) != 0.0) return Double.NaN;
            if (frac(p) == 0.0 && p % 2 != 0.0)
                return -ieee754_pow(n, p);
        }
        return ieee754_pow(n, p);
    }
    
    public static final double product(double[] args) {
        int l = args.length;
        double res = args[0];
        for (int i = 1; i < l; i++)
            res *= args[i];
        return res;
    }
    
    public static final double radians(double d) {
        return Math.toRadians(d);
    }
    
    public static final double rand() {
        return new Random().nextDouble();
    }
    
    public static final double randBetween(double bottom, double top) {
        double b = trunc(bottom), t = trunc(top);
        if (b > t) return Double.NaN;
        return trunc(new Random().nextDouble() * (t - b + 1.0)) + b;
    }
    
    public static final double round(double n) {
        if (n < 0.0) return Math.ceil(n - 0.5);
        return              Math.floor(n + 0.5);
    }
    
    public static final double round(double n, double digit) {
        double d = exp10(digit);
        return round(n * d) / d;
    }
    
    public static final double roundDown(double n, double digit) {
        return trunc(n, digit);
    }
    
    public static final double roundUp(double n) {
        if (n < 0.0) return Math.floor(n);
        if (n > 0.0) return Math.ceil(n);
        return n;
    }
    
    public static final double roundUp(double n, double digit) {
        double d = exp10(digit);
        double tmp = n * d;
        if (tmp < 0.0)  tmp = Math.floor(tmp);
        else            tmp = Math.ceil(tmp);
        return tmp / d;
    }
    
    public static final double sign(double n) {
        if (n < 0.0) return -1.0;
        if (n > 0.0) return  1.0;
        return n;
    }
    
    public static final double sin(double n) {
        return Math.sin(n);
    }
    
    public static final double sinh(double n) {
        return (ieee754_exp(n) - ieee754_exp(-n)) / 2.0;
    }
    
    public static final double sqrt(double n) {
        return Math.sqrt(n);
    }
    
    public static final double sqrtPi(double n) {
        return Math.sqrt(n * Math.PI);
    }
    
    public static final double stDev(double[] args) {
        return Math.sqrt(var(args));
    }
    
    public static final double stDevP(double[] args) {
        return Math.sqrt(varP(args));
    }
    
    public static final double sum(double[] args) {
        double res = args[0];
        for (int i = 1; i < args.length; i++)
            res += args[i];
        return res;
    }
    
    public static final double sumSq(double[] args) {
        double res = 0.0;
        for (int i = 0; i < args.length; i++)
            res += args[i] * args[i];
        return res;
    }
    
    public static final double tan(double n) {
        return Math.tan(n);
    }
    
    public static final double tanh(double n) {
        return sinh(n) / cosh(n);
    }
    
    public static final double trunc(double n) {
        if (n < 0.0) return Math.ceil(n);
        if (n > 0.0) return Math.floor(n);
        return n;
    }
    
    public static final double trunc(double n, double digit) {
        double d = exp10(digit);
        return trunc(n * d) / d;
    }
    
    public static final double var(double[] args) {
        return devSq(args) / (args.length - 1);
    }
    
    public static final double varP(double[] args) {
        return devSq(args) / args.length;
    }
    
    /*************************************************************************
    * All these formula are for calculations on the basis of a spherical     *
    * earth (ignoring ellipsoidal effects) – which is accurate enough for    *
    * most purposes...                                                       *
    * (In fact, the earth is very slightly ellipsoidal; using a spherical    *
    * model gives errors typically up to 0.3%)                               *
    *========================================================================*
    * Source: http://www.movable-type.co.uk/scripts/latlong.html             *
    *************************************************************************/
    
    private static final double earthRad = 6371;
                                // the Earth radius (mean radius)
    
    public static final double distance(double lat1, double lon1,
                                        double lat2, double lon2) {
        double la1 = Math.toRadians(lat1),
               lo1 = Math.toRadians(lon1),
               la2 = Math.toRadians(lat2),
               lo2 = Math.toRadians(lon2),
               dla = Math.sin((la2 - la1) / 2),
               dlo = Math.sin((lo2 - lo1) / 2),
               a   = dla * dla + dlo * dlo * Math.cos(la1) * Math.cos(la2),
               c   = 2 * atan2(Math.sqrt(1 - a), Math.sqrt(a));
        return earthRad * c; // Earth radius (mean radius) = 6,371 km
    }
    
    public static final double heading(double lat1, double lon1,
                                       double lat2, double lon2) {
        double la1 = Math.toRadians(lat1),
               lo1 = Math.toRadians(lon1),
               la2 = Math.toRadians(lat2),
               lo2 = Math.toRadians(lon2),
               dlo = lo2 - lo1,
               x   = Math.cos(la1) * Math.sin(la2) -
                     Math.sin(la1) * Math.cos(la2) * Math.cos(dlo),
               y   = Math.sin(dlo) * Math.cos(la2),
               hdg = Math.toDegrees(atan2(x, y));
        return (hdg + 360) % 360;
    }
    
    public static final double latitude(double lat1, double lon1,
                                        double hdng, double dist) {
        double la1 = Math.toRadians(lat1),
               hdg = Math.toRadians(hdng),
               ad  = dist / earthRad,
               la2 = asin(Math.sin(la1) * Math.cos(ad) +
                     Math.cos(la1) * Math.sin(ad) * Math.cos(hdg));
        return Math.toDegrees(la2);
    }
    
    public static final double longitude(double lat1, double lon1,
                                         double hdng, double dist) {
        double la1 = Math.toRadians(lat1),
               lo1 = Math.toRadians(lon1),
               hdg = Math.toRadians(hdng),
               ad  = dist / earthRad,
               la2 = asin(Math.sin(la1) * Math.cos(ad) +
                     Math.cos(la1) * Math.sin(ad) * Math.cos(hdg)),
               lo2 = lo1 + atan2(Math.cos(ad) - Math.sin(la1) *
                     Math.sin(la2), Math.sin(hdg) * Math.sin(ad) *
                     Math.cos(la1));
        lo2 = Math.toDegrees(lo2);
        if (lo2 > 180) lo2 -= 360;
        return lo2;
    }
    
    /**
     * ====================================================================== *
     * Implements the Euclidean Algorithm a.k.a Euclid's Algorithm method for *
     * computing Greatest Common Divisor (GCD) & Least Common Multiple (LCM). *
     * ====================================================================== *
     * Source: http://en.wikipedia.org/wiki/Euclid's_algorithm                *
     * ====================================================================== *
     */
    
    private static final long gcd(long n1, long n2) {
        long a = n1, b = n2, tmp;
        while (b > 0) {
            tmp = b;
            b = a % b;
            a = tmp;
        }
        return a;
    }
    
    public static final double gcd(double[] args) {
        long n, res = new Double(args[0]).longValue();
        for (int i = 0; i < args.length; i++) {
            n = new Double(args[i]).longValue();
            if (n < 0) return Double.NaN;
            res = gcd(res, n);
        }
        return new Double(res).doubleValue();
    }
    
    private static final long lcm(long n1, long n2) {
        return n1 * (n2 / gcd(n1, n2));
    }
    
    public static final double lcm(double[] args) {
        long n, res = new Double(args[0]).longValue();
        for (int i = 0; i < args.length; i++) {
            n = new Double(args[i]).longValue();
            if (n < 0) return Double.NaN;
            res = lcm(res, n);
        }
        return new Double(res).doubleValue();
    }
    
    /**
     * =================================================================== *
     * Implements the methods which are in the standard J2SE's Math class, *
     * but are not in J2ME's.                                              *
     * =================================================================== *
     * Source: http://www.java2s.com/Code/Java/Development-Class/          *
     *         ImplementsthemethodswhichareinthestandardJ2SEsMath          *
     *         classbutarenotininJ2MEs.htm                                 *
     * =================================================================== *
     */
    
    private static final double PIover2 = Math.PI / 2;
    private static final double PIover4 = Math.PI / 4;
    private static final double PIover6 = Math.PI / 6;
    private static final double PIover12 = Math.PI / 12;
    private static final double ATAN_CONSTANT = 1.732050807569;

    public static double acos(double a) {
        if (Double.isNaN(a) || Math.abs(a) > 1.0) return Double.NaN;
        double aSquared = a * a;
        double arcCosine = atan2(a, Math.sqrt(1 - aSquared));
        return arcCosine;
    }

    public static double asin(double a) {
        if (Double.isNaN(a) || Math.abs(a) > 1.0) return Double.NaN;
        if (a == 0.0) return a;
        double aSquared = a * a;
        double arcSine = atan2(Math.sqrt(1 - aSquared), a);
        return arcSine;
    }

    public static double atan(double a) {
        if (Double.isNaN(a)) return Double.NaN;
        if (a == 0.0) return a;
        double al = a;
        boolean negative = false;
        boolean greaterThanOne = false;
        int i = 0;
        if (al < 0.0 ) {
            al = -al;
            negative = true;
        }
        if (al > 1.0) {
            al = 1.0 / al;
            greaterThanOne = true;
        }
        double t;
        for (; al > PIover12; al *= t) {
            i++;
            t = al + ATAN_CONSTANT;
            t = 1.0 / t;
            al *= ATAN_CONSTANT;
            al--;
        }
        double aSquared = al * al;
        double arcTangent = aSquared + 1.4087812;
        arcTangent = 0.55913709 / arcTangent;
        arcTangent += 0.60310578999999997;
        arcTangent -= 0.051604539999999997 * aSquared;
        arcTangent *= al;
        for (; i > 0; i--) {
            arcTangent += PIover6;
        }
        if (greaterThanOne) arcTangent = PIover2 - arcTangent;
        if (negative) arcTangent = -arcTangent;
        return arcTangent;
    }
  
    public static double atan2(double x, double y) {
        if (Double.isNaN(y) || Double.isNaN(x)) return Double.NaN;
        else if (Double.isInfinite(y)) {
            if (y > 0.0 ) {
                if (Double.isInfinite(x)) {
                    if (x > 0.0) return PIover4;
                    else return 3.0 * PIover4;
                }
                else if (x != 0.0) return PIover2;
            }
            else {
                if (Double.isInfinite(x)) {
                    if (x > 0.0) return -PIover4;
                    else return -3.0 * PIover4;
                }
                else if (x != 0.0) return -PIover2;
            }
        }
        else if (y == 0.0) {
            if (x > 0.0) return y;
            else if ( x < 0.0 ) return Math.PI;
        }
        else if (Double.isInfinite(x)) {
            if (x > 0.0) {
                if (y > 0.0) return 0.0;
                else if (y < 0.0) return -0.0;
            }
            else {
                if (y > 0.0) return Math.PI;
                else if (y < 0.0) return -Math.PI;
            }
        }
        else if (x == 0.0) {
            if (y > 0.0) return PIover2;
            else if (y < 0.0) return -PIover2;
        }
        
        // Implementation a simple version ported from a PASCAL implementation:
        //   http://everything2.com/index.pl?node_id=1008481
        
        double arcTangent;
        if (Math.abs(x) > Math.abs(y)) arcTangent = atan(y / x);
        else {
            arcTangent = atan(x / y);
            if (arcTangent < 0.0) arcTangent = -PIover2 - arcTangent;
            else arcTangent = PIover2 - arcTangent;
        }
        if (x < 0.0) {
            if ( y < 0 ) arcTangent -= Math.PI;
            else arcTangent += Math.PI;
        }
        return arcTangent;
    }
    
    /**
     * ================================================================== *
     * IEEE-754 on J2ME: the real exp, log and pow methods                *
     * ================================================================== *
     * Source: http://www.java.net/node/691932                            *
     * ================================================================== *
     * Portions copyright Sun Microsystems as below.                      *
     * This was ported from the FDLIBM C-library.                         *
     * ================================================================== *
     * Copyright (C) 2004 by Sun Microsystems, Inc. All rights reserved.  *
     *                                                                    *
     * Permission to use, copy, modify, and distribute this software is   *
     * freely granted, provided that this notice preserved.               *
     * ================================================================== *
     * FDLIBM = Freely Distributable LIBM is a C math library for machine *
     *     that support IEEE 754 floating-point arithmetic.               *
     * FDLIBM is intended to provide a reasonable portable, reference     *
     *     quality math library.                                          *
     * ================================================================== *
     */
    
    private static final double
        zero   =  0.0,
        one    =  1.0,
        two    =  2.0,
        tiny   =  1.0e-300,
        huge   =  1.0e+300,
        two53  =  9007199254740992.0,
        two54  =  1.80143985094819840000e+16,
        twom54 =  5.55111512312578270212e-17,
        P1     =  1.66666666666666019037e-01,
        P2     = -2.77777777770155933842e-03,
        P3     =  6.61375632143793436117e-05,
        P4     = -1.65339022054652515390e-06,
        P5     =  4.13813679705723846039e-08;

    private static final long
        HI_MASK = 0xffffffff00000000L,
        LO_MASK = 0x00000000ffffffffL;

    private static final int
        HI_SHIFT = 32;
    
    private static final double
        twom1000    =  9.33263618503218878990e-302,
        o_threshold =  7.09782712893383973096e+02,
        u_threshold = -7.45133219101941108420e+02,
        invln2      =  1.44269504088896338700e+00;

    private static final double[]
        halF  = new double[] { 0.5, -0.5 },
        ln2HI = new double[] { 6.93147180369123816490e-01,
                              -6.93147180369123816490e-01 },
        ln2LO = new double[] { 1.90821492927058770002e-10,
                              -1.90821492927058770002e-10 };

    private static final double
        bp[]    = {1.0, 1.5, },
        dp_h[]  = {0.0, 5.84962487220764160156e-01, },
        dp_l[]  = {0.0, 1.35003920212974897128e-08, },
        L1      =  5.99999999999994648725e-01,
        L2      =  4.28571428578550184252e-01,
        L3      =  3.33333329818377432918e-01,
        L4      =  2.72728123808534006489e-01,
        L5      =  2.30660745775561754067e-01,
        L6      =  2.06975017800338417784e-01,
        lg2     =  6.93147180559945286227e-01,
        lg2_h   =  6.93147182464599609375e-01,
        lg2_l   = -1.90465429995776804525e-09,
        ovt     =  8.0085662595372944372e-0017,
        cp      =  9.61796693925975554329e-01,
        cp_h    =  9.61796700954437255859e-01,
        cp_l    = -7.02846165095275826516e-09,
        ivln2   =  1.44269504088896338700e+00,
        ivln2_h =  1.44269502162933349609e+00,
        ivln2_l =  1.92596299112661746887e-08;
    
    private static final double ieee754_exp(double x) {
        double y, c, t;
        double hi = 0, lo = 0;
        int k = 0 ;
        int xsb, hx, lx;
        long yl;
        long xl = Double.doubleToLongBits(x);
        hx = (int) ((long) xl >> HI_SHIFT);
        xsb = (hx >> 31) & 1;
        hx &= 0x7fffffff;
        if (hx >= 0x40862E42) {
            if (hx >= 0x7ff00000) {
                lx = (int) ((long) xl & LO_MASK);
                if (((hx & 0xfffff) | lx) != 0)
                return x + x;
                else return (xsb == 0) ? x : 0.0;
            }
            if (x > o_threshold) return huge * huge;
            if (x < u_threshold) return twom1000 * twom1000;
        }
        if (hx > 0x3fd62e42) {
            if (hx < 0x3FF0A2B2) {
                hi = x - ln2HI[xsb];
                lo = ln2LO[xsb];
                k = 1 - xsb - xsb;
            }
            else {
                k = (int) (invln2 * x + halF[xsb]);
                t = k;
                hi = x - t * ln2HI[0];
                lo = t * ln2LO[0];
            }
            x = hi - lo;
        }
        else if (hx < 0x3e300000) {
            if(huge + x > one) return one + x;
        }
        t = x * x;
        c = x - t * (P1 + t * (P2 + t * (P3 + t * (P4 + t * P5))));
        if (k == 0) return one - ((x * c) / (c - 2.0) - x);
        else y = one - ((lo - (x * c) / (2.0 - c)) - hi);
        yl = Double.doubleToLongBits(y);
        if (k >= -1021) {
            yl += ((long) k << (20 + HI_SHIFT));
            return Double.longBitsToDouble(yl);
        }
        else {
            yl += ((long) (k + 1000) << (20 + HI_SHIFT));
            return Double.longBitsToDouble(yl) * twom1000;
        }
    }
    
    private static final double
        ln2_hi = 6.93147180369123816490e-01,
        ln2_lo = 1.90821492927058770002e-10,
        Lg1 = 6.666666666666735130e-01,
        Lg2 = 3.999999999940941908e-01,
        Lg3 = 2.857142874366239149e-01,
        Lg4 = 2.222219843214978396e-01,
        Lg5 = 1.818357216161805012e-01,
        Lg6 = 1.531383769920937332e-01,
        Lg7 = 1.479819860511658591e-01;
    
    private static final double ieee754_log(double x) {
        double hfsq, f, s, z, R, w, t1, t2, dk;
        int k, hx, lx, i, j;
        long xl = Double.doubleToLongBits(x);
        hx = (int) (xl >> HI_SHIFT);
        lx = (int) (xl & LO_MASK);
        k=0;
        if (hx < 0x00100000) {
            if (((hx & 0x7fffffff) | lx) == 0)
                return -two54 / zero;
            if (hx < 0) return (x - x) / zero;
            k -= 54;
            x *= two54;
            hx = (int) (Double.doubleToLongBits(x) >> HI_SHIFT);
        }
        if (hx >= 0x7ff00000) return x + x;
        k += (hx >> 20) - 1023;
        hx &= 0x000fffff;
        i = (hx + 0x95f64) & 0x100000;
        x = Double.longBitsToDouble(((long) (hx | (i ^ 0x3ff00000)) <<
            HI_SHIFT) | (Double.doubleToLongBits(x) & LO_MASK));
        k += (i >> 20);
        f = x - 1.0;
        if ((0x000fffff & (2 + hx)) < 3) {
            if (f == zero)
                if (k == 0) return zero;
                else {
                    dk = (double) k;
                    return dk * ln2_hi + dk * ln2_lo;
                }
            R = f * f * (0.5 - 0.33333333333333333 * f);
            if (k == 0) return f - R;
            else {
                dk= (double) k;
                return dk * ln2_hi - ((R - dk * ln2_lo) - f);
            }
        }
        s = f / (2.0 + f);
        dk = (double) k;
        z = s * s;
        i = hx - 0x6147a;
        w = z * z;
        j = 0x6b851 - hx;
        t1= w * (Lg2 + w * (Lg4 + w * Lg6));
        t2= z * (Lg1 + w * (Lg3 + w * (Lg5 + w * Lg7)));
        i |= j;
        R = t2 + t1;
        if (i > 0) {
            hfsq = 0.5 * f * f;
            if (k == 0) return f - (hfsq - s * (hfsq + R));
            else
                return dk * ln2_hi - ((hfsq - (s * (hfsq + R) +
                       dk * ln2_lo)) - f);
        }
        else {
            if (k == 0) return f - s * (f - R);
            else return dk * ln2_hi - ((s * (f - R) - dk * ln2_lo) - f);
        }
    }
    
    private static final double ieee754_pow(double x, double y) {
        double z, ax, z_h, z_l, p_h, p_l;
        double y1, t1, t2, r, s, t, u, v, w;
        int i, j, k, yisint, n;
        int hx, hy, ix, iy;
        int lx, ly;
        hx = (int) (Double.doubleToLongBits(x) >>> HI_SHIFT);
        lx = (int) (Double.doubleToLongBits(x)  &  LO_MASK);
        hy = (int) (Double.doubleToLongBits(y) >>> HI_SHIFT);
        ly = (int) (Double.doubleToLongBits(y)  &  LO_MASK);
        ix = hx & 0x7fffffff;
        iy = hy & 0x7fffffff;
        if ((iy | ly) == 0) return one;
        if (ix > 0x7ff00000 || ((ix == 0x7ff00000) && (lx != 0)) ||
                iy > 0x7ff00000 || ((iy == 0x7ff00000) && (ly != 0)))
            return x+y;
        yisint = 0;
        if (hx < 0) {
            if (iy >= 0x43400000) yisint = 2;
            else if (iy >= 0x3ff00000) {
                k = (iy >> 20) - 0x3ff;
                if(k > 20) {
                    j = ly >> (52 - k);
                    if ((j << (52 - k)) == ly) yisint = 2 - (j & 1);
                }
                else if (ly == 0) {
                    j = iy >> (20 - k);
                    if ((j << (20 - k)) == iy) yisint = 2 - (j & 1);
                }
            }
        }
        if(ly == 0) {
            if (iy == 0x7ff00000) {
                if (((ix - 0x3ff00000) | lx) == 0)
                    return  y - y;
                else if (ix >= 0x3ff00000)
                    return (hy >= 0) ? y: zero;
                else
                    return (hy < 0) ? -y: zero;
            }
            if(iy == 0x3ff00000) {
                if(hy < 0) return one / x;
                else       return x;
            }
            if (hy == 0x40000000) return x*x;
            if (hy == 0x3fe00000) {
                if (hx >= 0)
                    return Math.sqrt(x);
            }
        }
        ax = Math.abs(x);
        if (lx == 0) {
            if (ix == 0x7ff00000 || ix == 0 || ix == 0x3ff00000) {
                z = ax;
                if (hy < 0) z = one / z;
                if (hx < 0) {
                    if (((ix - 0x3ff00000) | yisint) == 0)
                        z = (z - z) / (z - z);
                    else if (yisint == 1)
                        z = -z;
                }
                return z;
            }
        }
        n = (hx >>> 31) + 1;
        if ((n | yisint) == 0) return (x - x) / (x - x);
        s = one;
        if ((n | (yisint - 1)) == 0) s = -one;
        if (iy > 0x41e00000) {
            if (iy > 0x43f00000) {
                if (ix <= 0x3fefffff)
                    return (hy < 0) ? huge * huge : tiny * tiny;
                if (ix >= 0x3ff00000)
                    return (hy > 0) ? huge * huge : tiny*tiny;
            }
            if (ix < 0x3fefffff)
                return (hy < 0) ? s * huge * huge : s * tiny * tiny;
            if (ix > 0x3ff00000)
                return (hy > 0) ? s * huge * huge : s * tiny * tiny;
            t = x - one;
            w = (t * t) * (0.5 - t * (0.3333333333333333333333 - t * 0.25));
            u = ivln2_h * t;
            v = t * ivln2_l - w * ivln2;
            t1 = u + v;
            t1 = Double.longBitsToDouble(Double.doubleToLongBits(t1) &
                    HI_MASK);
            t2 = v - (t1 - u);
        }
        else {
            double ss, s2, s_h, s_l, t_h, t_l;
            n = 0;
            if (ix < 0x00100000) {
                ax *= two53;
                n -= 53;
                ix = (int) (Double.doubleToLongBits(ax) >>> HI_SHIFT);
            }
            n += ((ix) >> 20) - 0x3ff;
            j  = ix & 0x000fffff;
            ix = j | 0x3ff00000;
            if (j <= 0x3988E) k = 0;
            else if (j < 0xBB67A) k = 1;
            else {
                k = 0;
                n += 1;
                ix -= 0x00100000;
            }
            ax = Double.longBitsToDouble(((long) ix << HI_SHIFT) |
                    (Double.doubleToLongBits(ax) & LO_MASK));
            u = ax - bp[k];
            v = one / (ax + bp[k]);
            ss = u * v;
            s_h = ss;
            s_h = Double.longBitsToDouble(Double.doubleToLongBits(s_h) &
                    HI_MASK);
            t_h = zero;
            t_h = Double.longBitsToDouble(((long) ((int) ((ix >> 1) |
                    0x20000000) + 0x00080000 + (k << 18)) << HI_SHIFT) |
                    (Double.doubleToLongBits(t_h) & LO_MASK));
            t_l = ax - (t_h - bp[k]);
            s_l = v * ((u - s_h * t_h) - s_h * t_l);
            s2 = ss * ss;
            r = s2 * s2 * (L1 + s2 * (L2 + s2 * (L3 + s2 * (L4 + s2 * 
                    (L5 + s2 * L6)))));
            r += s_l * (s_h + ss);
            s2  = s_h * s_h;
            t_h = 3.0 + s2 + r;
            t_h = Double.longBitsToDouble(Double.doubleToLongBits(t_h) &
                    HI_MASK);
            t_l = r - ((t_h - 3.0) - s2);
            u = s_h * t_h;
            v = s_l * t_h+t_l * ss;
            p_h = u + v;
            p_h = Double.longBitsToDouble(Double.doubleToLongBits(p_h) &
                    HI_MASK);
            p_l = v - (p_h - u);
            z_h = cp_h * p_h;
            z_l = cp_l * p_h + p_l * cp + dp_l[k];
            t = (double) n;
            t1 = (((z_h + z_l) + dp_h[k]) + t);
            t1 = Double.longBitsToDouble(Double.doubleToLongBits(t1) &
                    HI_MASK);
            t2 = z_l - (((t1 - t) - dp_h[k]) - z_h);
        }
        y1 = y;
        y1 = Double.longBitsToDouble(Double.doubleToLongBits(y1) & HI_MASK);
        p_l = (y - y1) * t1 + y * t2;
        p_h = y1 * t1;
        z = p_l + p_h;
        j = (int) (Double.doubleToLongBits(z) >>> HI_SHIFT);
        i = (int) (Double.doubleToLongBits(z)  &  LO_MASK);
        if (j >= 0x40900000) {
            if (((j - 0x40900000) | i) != 0)
                return s * huge * huge;
            else {
                if (p_l +ovt > z - p_h) return s * huge * huge;
            }
        }
        else if ((j & 0x7fffffff) >= 0x4090cc00) {
            if (((j - 0xc090cc00) | i) != 0)
                return s * tiny * tiny;
            else {
                if(p_l <= z - p_h) return s * tiny * tiny;
            }
        }
        i = j & 0x7fffffff;
        k = (i >> 20) - 0x3ff;
        n = 0;
        if(i > 0x3fe00000) {
            n = j + (0x00100000 >> (k + 1));
            k = ((n & 0x7fffffff) >> 20) - 0x3ff;
            t = zero;
            t = Double.longBitsToDouble(((long) (n & ~(0x000fffff >> k)) <<
                    HI_SHIFT) | (Double.doubleToLongBits(t) & LO_MASK));
            n = ((n & 0x000fffff) | 0x00100000) >> (20 - k);
            if (j < 0) n = -n;
            p_h -= t;
        }
        t  = p_l + p_h;
        t  = Double.longBitsToDouble(Double.doubleToLongBits(t) & HI_MASK);
        u  = t * lg2_h;
        v  = (p_l - (t - p_h)) * lg2 + t * lg2_l;
        z  = u + v;
        w  = v - (z - u);
        t  = z * z;
        t1 = z - t * (P1 + t * (P2 + t * (P3 + t * (P4 + t * P5))));
        r  = (z * t1) / (t1 - two) - (w + z * w);
        z  = one - (r - z);
        j  = (int) ((long) Double.doubleToLongBits(z) >>> HI_SHIFT);
        j += (n << 20);
        if ((j >> 20) <= 0) z = scalbn(z, n);
        else
            z = Double.longBitsToDouble(((long) j << HI_SHIFT) |
                    (Double.doubleToLongBits(z) & LO_MASK));
        return s * z;
    }
    
    public static final double scalbn (double x, int n) {
        int k, hx, lx;
        hx = (int) (Double.doubleToLongBits(x) >>> HI_SHIFT);
        lx = (int) (Double.doubleToLongBits(x)  &  LO_MASK);
        k = (hx & 0x7ff00000) >> 20;
        if (k == 0) {
            if ((lx | (hx & 0x7fffffff)) == 0) return x;
            x *= two54;
            hx = (int) (Double.doubleToLongBits(x) >>> HI_SHIFT);
            k = ((hx & 0x7ff00000) >> 20) - 54;
            if (n < -50000) return tiny * x;
        }
        if (k == 0x7ff) return x + x;
        k = k + n;
        if (k > 0x7fe) return huge * copysign(huge, x);
        if (k > 0) {
            x = Double.longBitsToDouble(((long) ((int) (hx&0x800fffff) |
                    (k << 20)) << HI_SHIFT) |
                    (Double.doubleToLongBits(x) & LO_MASK));
            return x;
        }
        if (k <= -54)
            if (n > 50000)
                 return huge * copysign(huge, x);
            else return tiny * copysign(tiny, x);
        k += 54;
        x = Double.longBitsToDouble(((long) ((int) (hx&0x800fffff)|(k<<20)) <<
                HI_SHIFT) | (Double.doubleToLongBits(x) & LO_MASK));
        return x * twom54;
    }
    
    public static final double copysign(final double x, final double y) {
        return Double.longBitsToDouble((Double.doubleToLongBits(x) &
               0x7fffffffffffffffL) |
               (Double.doubleToLongBits(y) & 0x8000000000000000L));
    }
}
