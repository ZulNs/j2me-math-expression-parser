package MathParser;

public abstract class Help {
    public static final String[] FUNCTIONS = {
    //  0             1             2             3             4
        "ABS",        "ACOS",       "ACOSH",      "ASIN",       "ASINH",   // 0
        "ATAN",       "ATAN2",      "ATANH",      "AVEDEV",     "AVERAGE", // 5
        "CEILING",    "COMBIN",     "COS",        "COSH",       "DEGREES", //10
        "DEVSQ",      "DISTANCE",   "DMS2DEC",    "EVEN",       "EXP",     //15
        "FACT",       "FACTDOUBLE", "FISHER",     "FISHERINV",  "FLOOR",   //20
        "FRAC",       "GCD",        "GEOMEAN",    "HEADING",    "INT",     //25
        "LATITUDE",   "LCM",        "LN",         "LOG",        "LOG10",   //30
        "LONGITUDE",  "MAX",        "MEAN",       "MEDIAN",     "MIN",     //35
        "MOD",        "MODE",       "MROUND",     "MULTINOMIAL","ODD",     //40
        "PERMUT",     "PI",         "POWER",      "PRODUCT",    "RADIANS", //45
        "RAND",       "RANDBETWEEN","ROUND",      "ROUNDDOWN",  "ROUNDUP", //50
        "SIGN",       "SIN",        "SINH",       "SQRT",       "SQRTPI",  //55
        "STDEV",      "STDEVP",     "SUM",        "SUMSQ",      "TAN",     //60
        "TANH",       "TRUNC",      "VAR",        "VARP"                   //65
    };
    
    public static final int[] FUNCTION_ARGS = {
    //  0             1             2             3             4
        0x0001,       0x0001,       0x0001,       0x0001,       0x0001,    // 0
        0x0001,       0x0002,       0x0001,       0xff01,       0xff01,    // 5
        0x0201,       0x0002,       0x0001,       0x0001,       0x0001,    //10
        0xff01,       0x0004,       0x0301,       0x0001,       0x0001,    //15
        0x0001,       0x0001,       0x0001,       0x0001,       0x0201,    //20
        0x0001,       0xff01,       0xff01,       0x0004,       0x0001,    //25
        0x0004,       0xff01,       0x0001,       0x0201,       0x0001,    //30
        0x0004,       0xff01,       0xff01,       0xff01,       0xff01,    //35
        0x0002,       0xff01,       0x0002,       0xff01,       0x0001,    //40
        0x0002,       0x0000,       0x0002,       0xff01,       0x0001,    //45
        0x0000,       0x0002,       0x0201,       0x0002,       0x0201,    //50
        0x0001,       0x0001,       0x0001,       0x0001,       0x0001,    //55
        0xff01,       0xff01,       0xff01,       0xff01,       0x0001,    //60
        0x0001,       0x0201,       0xff01,       0xff01                   //65
    };
    
    public static final String[] FUNCTION_DESCS = {
        // ABS
        "Returns the absolute value of a number n. The absolute value of n " +
            "is n without its sign.\nn is the real number of which you want " +
            "the absolute value.",
        // ACOS
        "Returns the arccosine, or inverse cosine, of a number n. The " +
            "arccosine is the angle whose cosine is n. The returned angle " +
            "is given in radians in the range 0 (zero) to pi.\nn is the " +
            "cosine of the angle you want and must be from -1 to 1.\nIf you " +
            "want to convert the result from radians to degrees, multiply " +
            "it by 180/PI() or use the DEGREES function.",
        // ACOSH
        "Returns the inverse hyperbolic cosine of a number n.\nn is any " +
            "real number and must be greater than or equal to 1.\nThe " +
            "inverse hyperbolic cosine is the value whose hyperbolic " +
            "cosine is n, so ACOSH(COSH(n)) equals n.",
        // ASIN
        "Returns the arcsine, or inverse sine, of a number n. The arcsine " +
            "is the angle whose sine is n. The returned angle is given in " +
            "radians in the range -pi/2 to pi/2.\nn is the sine of the " +
            "angle you want and must be from -1 to 1.\nTo express the " +
            "arcsine in degrees, multiply the result by 180/PI() or use " +
            "the DEGREES function.",
        // ASINH
        "Returns the inverse hyperbolic sine of a number n. The inverse " +
            "hyperbolic sine is the value whose hyperbolic sine is n, so " +
            "ASINH(SINH(n)) equals n.\nn is any real number.",
        // ATAN
        "Returns the arctangent, or inverse tangent, of a number n. The " +
            "arctangent is the angle whose tangent is n. The returned angle " +
            "is given in radians in the range -pi/2 to pi/2.\nn is the " +
            "tangent of the angle you want.\nTo express the arctangent in " +
            "degrees, multiply the result by 180/PI() or use the DEGREES " +
            "function.",
        // ATAN2
        "Returns the arctangent, or inverse tangent, of the specified x- " +
            "and y-coordinates. The arctangent is the angle from the " +
            "x-axis to a line containing the origin (0,0) and a point with " +
            "coordinates (n1,n2). The angle is given in radians between " +
            "-pi and pi, excluding -pi.\nn1 is the x-coordinate of the " +
            "point.\nn2 is the y-coordinate of the point.\nA positive " +
            "result represents a counterclockwise angle from the x-axis; a " +
            "negative result represents a clockwise angle.\nATAN2(n1, n2) " +
            "equals ATAN(n2/n1), except that n1 can equal 0 in ATAN2.\nIf " +
            "both n1 and n2 are 0, ATAN2 returns the infinite result.\nTo " +
            "express the arctangent in degrees, multiply the result by " +
            "180/PI() or use the DEGREES function.",
        // ATANH
        "Returns the inverse hyperbolic tangent of a number n. n is any " +
            "real number and must be between -1 and 1 (excluding -1 and 1). " +
            "The inverse hyperbolic tangent is the value whose hyperbolic " +
            "tangent is n, so ATANH(TANH(n)) equals n.",
        // AVEDEV
        "Returns the average of the absolute deviations of data points from " +
            "their mean. AVEDEV is a measure of the variability in a data " +
            "set.\nn1,[n2]...[n255] are 1 to 255 arguments for which you " +
            "want the average of the absolute deviations.\nn1 required.\n" +
            "n2,... optional.\n AVEDEV is influenced by the unit of " +
            "measurement in the input data.",
        // AVERAGE
        "Returns the average (arithmetic mean) of the arguments. AVERAGE " +
            "and MEAN are similar.\nn1,[n2]...[n255] are 1 to 255 " +
            "arguments for which you want the average.\nn1 required.\n" +
            "n2,... optional.\nThe AVERAGE function measures central " +
            "tendency, which is the location of the center of a group of " +
            "numbers in a statistical distribution.\nAverage, which is the " +
            "arithmetic mean, and is calculated by adding a group of " +
            "numbers and then dividing by the count of those numbers.",
        // CEILING
        "Returns number n1 rounded up, away from zero, to the nearest " +
            "multiple of significance n2.\nn1 is the value you want to " +
            "round.\nn2 is the multiple to which you want to round.\n" +
            "Regardless of the sign of number, a value is rounded up when " +
            "adjusted away from zero. If number is an exact multiple of " +
            "significance, no rounding occurs.\nIf number and significance " +
            "have different signs, CEILING returns the undefined result. ",
        // COMBIN
        "Returns the number of combinations for a given number of items. " +
            "Use COMBIN to determine the total possible number of groups " +
            "for a given number of items.\nn1 is the number of items.\nn2 " +
            "is the number of items in each combination.\nNumeric arguments " +
            "are truncated to integers.\nIf n1<0, n2<0, or n1<n2, COMBIN " +
            "returns the undefined result.\nA combination is any set or " +
            "subset of items, regardless of their internal order. " +
            "Combinations are distinct from permutations, for which the " +
            "internal order is significant.",
        // COS
        "Returns the cosine of the given angle n.\nn is the angle in " +
            "radians for which you want the cosine.\nIf the angle is in " +
            "degrees, either multiply the angle by PI()/180 or use the " +
            "RADIANS function to convert the angle to radians.",
        // COSH
        "Returns the hyperbolic cosine of a number n.\nn is any real number " +
            "for which you want to find the hyperbolic cosine.",
        // DEGREES
        "Converts radians into degrees.\nn is the angle in radians that you " +
            "want to convert.",
        // DEVSQ
        "Returns the sum of squares of deviations of data points from their " +
            "sample mean.\nn1,[n2]...[n255] are 1 to 255 arguments for " +
            "which you want to calculate the sum of squared deviations.\nn1 " +
            "required.\nn2,... optional.",
        // DISTANCE
        "Calculates the shortest distance between two points on the Earth's " +
            "surface, it uses haversine formula.\nn1 & n2 respectively are " +
            "the latitude & the longitude of the first point, n3 & n4 are " +
            "the latitude & the longitude of the second point.\nAll input " +
            "values in decimal degrees and output result in km(s).\nUse " +
            "negative value for both southern latitude and west longitude.",
        // DMS2DEC
        "Converts the number in DMS (Degrees, Minutes, Seconds) format into " +
            "decimal degrees.\nRespectively n1 is the value of Degrees, n2 " +
            "is the value of Minutes, and n3 is the value of Seconds.",
        // EVEN
        "Returns number rounded up to the nearest even integer. You can use " +
            "this function for processing items that come in twos. For " +
            "example, a packing crate accepts rows of one or two items. The " +
            "crate is full when the number of items, rounded up to the " +
            "nearest two, matches the crate's capacity.\nRegardless of the " +
            "sign of number, a value is rounded up when adjusted away from " +
            "zero. If number is an even integer, no rounding occurs.",
        // EXP
        "Returns e raised to the power of number n. The constant e equals " +
            "2.718281828459045, the base of the natural logarithm.\nn is " +
            "the exponent applied to the base e.\nTo calculate powers of " +
            "other bases, use the exponentiation operator (^).\nEXP is the " +
            "inverse of LN, the natural logarithm of number.",
        // FACT
        "Returns the factorial of a number n. The factorial of a number n " +
            "is equal to 1*2*3*...*n.\nn is the nonnegative number for " +
            "which you want the factorial. If number is not an integer, it " +
            "is truncated.\nFACT(n) = n!",
        // FACTDOUBLE
        "Returns the double factorial of a number n.\nn is the value for " +
            "which to return the double factorial. If number is not an " +
            "integer, it is truncated.\nIf number is negative, FACTDOUBLE " +
            "returns the undefined result.\nFACTDOUBLE(n) = n!!",
        // FISHER
        "Returns the Fisher transformation at n. This transformation " +
            "produces a function that is normally distributed rather than " +
            "skewed. Use this function to perform hypothesis testing on the " +
            "correlation coefficient.\nn is a numeric value for which you " +
            "want the transformation.\nIf n≤-1 or if n≥1, FISHER " +
            "returns the undefined result.",
        // FISHERINV
        "Returns the inverse of the Fisher transformation. Use this " +
            "transformation when analyzing correlations between ranges or " +
            "arrays of data. If y = FISHER(x), then FISHERINV(y) = x.\nn is " +
            "the value for which you want to perform the inverse of the " +
            "transformation.",
        // FLOOR
        "Rounds number n1 down, toward zero, to the nearest multiple of " +
            "significance n2.\nn1 is the numeric value you want to round.\n" +
            "n2 is the multiple to which you want to round.\nIf n1 and n2 " +
            "have different signs, FLOOR returns the undefined result.\n" +
            "Regardless of the sign of number, a value is rounded down when " +
            "adjusted away from zero. If number is an exact multiple of " +
            "significance, no rounding occurs.",
        // FRAC
        "Returns the fractional part of a number n.\nn is the real number " +
            "you want to get its fractional part.\nFRAC removes the integer" +
            "part of a number n.",
        // GCD
        "Returns the greatest common divisor of two or more integers. The " +
            "greatest common divisor is the largest integer that divides " +
            "both number n1 and number n2 without a remainder.\n" +
            "n1,[n2]...[n255] are 1 to 255 values. If any value is not an " +
            "integer, it is truncated.\nn1 required.\nn2,... optional.\nIf " +
            "any argument is less than zero, GCD returns the undefined " +
            "result.\nOne divides any value evenly.\nA prime number has " +
            "only itself and one as even divisors.",
        // GEOMEAN
        "Returns the geometric mean of an array or range of positive data. " +
            "For example, you can use GEOMEAN to calculate average growth " +
            "rate given compound interest with variable rates.\n" +
            "n1,[n2]...[n255] are 1 to 255 arguments for which you want to " +
            "calculate the mean.\nn1 required.\nn2,... optional.\nIf any " +
            "data point ≤ 0, GEOMEAN returns the undefined result.",
        // HEADING
        "Determines the degrees of the deviation angle between the line that " +
            "connecting the two points on the Earth's surface relative to " +
            "the North-South line (meridian) which rotated clockwise from " +
            "the North.\nn1 & n2 respectively are the latitude & the " +
            "longitude of the first point, n3 & n4 are the latitude & the " +
            "longitude of the second point.\nThe resulting angle is in the " +
            "range between 0° to 360°.\nUse negative value for both southern " +
            "latitude and west longitude.",
        // INT
        "Returns the rounded number down to an integer.\nn is the real " +
            "number you want to round down to an integer.",
        // LATITUDE
        "Determines the latitude of the destination point from a starting " +
            "point with an angle of heading and a specific distance.\nn1 & " +
            "n2 respectively are the latitude & the longitude of the " +
            "starting point, n3 is the angel of heading, and n4 is the " +
            "distance in km(s).\nThe resulting latitude is in the range " +
            "between -90° to 90°, which positive value for northern latitude " +
            "and negative value for southern latitude.",
        // LCM
        "Returns the least common multiple of integers. The least common " +
            "multiple is the smallest positive integer that is a multiple " +
            "of all integer arguments n1, n2, and so on. Use LCM to add " +
            "fractions with different denominators.\nn1,[n2]...[n255] are 1 " +
            "to 255 values for which you want the least common multiple. If " +
            "value is not an integer, it is truncated.\nn1 required.\n" +
            "n2,... optional.\nIf any argument is less than zero, LCM " +
            "returns the undefined result.",
        // LN
        "Returns the natural logarithm of a number n. Natural logarithms " +
            "are based on the constant e (2.718281828459045).\nn is the " +
            "positive real number for which you want the natural logarithm." +
            "\nLN is the inverse of the EXP function.",
        // LOG
        "Returns the logarithm of a number n1 to the base n2 you specify.\n" +
            "n1 is the positive real number for which you want the " +
            "logarithm.\nn2 is the base of the logarithm. If base is " +
            "omitted, it is assumed to be 10.",
        // LOG10
        "Returns the base-10 logarithm of a number n.\nn is the positive " +
            "real number for which you want the base-10 logarithm.",
        // LONGITUDE
        "Determines the longitude of the destination point from a starting " +
            "point with an angle of heading and a specific distance.\nn1 & " +
            "n2 respectively are the latitude & the longitude of the " +
            "starting point, n3 is the angel of heading, and n4 is the " +
            "distance in km(s).\nThe resulting longitude is in the range " +
            "between -180° to 180°, which positive value for east longitude " +
            "and negative value for west longitude.",
        // MAX
        "Returns the largest value in a set of values.\nn1,[n2]...[n255] " +
            "are 1 to 255 numbers for which you want to find the maximum " +
            "value.\nn1 required.\nn2,... optional.",
        // MEAN
        "Returns the average (arithmetic mean) of the arguments. MEAN " +
            "and AVERAGE are similar.\nn1,[n2]...[n255] are 1 to 255 " +
            "arguments for which you want the arithmetic mean.\nn1 " +
            "required.\nn2,... optional.\nThe MEAN function measures " +
            "central tendency, which is the location of the center of a " +
            "group of numbers in a statistical distribution.\nMean, which " +
            "is the average, and is calculated by adding a group of " +
            "numbers and then dividing by the count of those numbers.",
        // MEDIAN
        "Returns the median of the given numbers. The median is the number " +
            "in the middle of a set of numbers.\nn1,[n2]...[n255] are 1 to " +
            "255 numbers for which you want the median.\nn1 required.\n" +
            "n2,... optional.\nThe MEDIAN function measures central " +
            "tendency, which is the location of the center of a group of " +
            "numbers in a statistical distribution.\nMedian, which is the " +
            "middle number of a group of numbers; that is, half the numbers " +
            "have values that are greater than the median, and half the " +
            "numbers have values that are less than the median.",
        // MIN
        "Returns the smallest number in a set of values.\nn1,[n2]...[n255] " +
            "are 1 to 255 numbers for which you want to find the minimum " +
            "value.\nn1 required.\nn2,... optional.",
        // MOD
        "Returns the remainder after number n1 is divided by divisor n2.\n" +
            "n1 is the number for which you want to find the remainder.\n" +
            "n2 is the number by which you want to divide number.",
        // MODE
        "Returns the most frequently occurring, or repetitive, value in an " +
            "array or range of data.\nn1,[n2]...[n255] are 1 to 255 " +
            "arguments for which you want to calculate the mode.\nn1 " +
            "required.\nn2,... optional.\nIf the data set contains no " +
            "duplicate data points, MODE returns undefined result.\nThe " +
            "MODE function measures central tendency, which is the location " +
            "of the center of a group of numbers in a statistical " +
            "distribution.\nMode, which is the most frequently occurring " +
            "number in a group of numbers.",
        // MROUND
        "Returns a number rounded to the desired multiple.\nn1 is the value " +
            "to round.\nn2 is the multiple to which you want to round n1.\n" +
            "MROUND rounds up, away from zero, if the remainder of dividing " +
            "number by multiple is greater than or equal to half the value " +
            "of multiple.",
        // MULTINOMIAL
        "Returns the ratio of the factorial of a sum of values to the " +
            "product of factorials.\nn1,[n2]...[n255] are 1 to 255 values " +
            "for which you want the multinomial.\nn1 required.\nn2,... " +
            "optional.\nIf any argument is less than zero, MULTINOMIAL " +
            "returns the undefined result.",
        // ODD
        "Returns number rounded up to the nearest odd integer.\nRegardless " +
            "of the sign of number, a value is rounded up when adjusted " +
            "away from zero. If number is an odd integer, no rounding occurs.",
        // PERMUT
        "Returns the number of permutations for a given number of objects " +
            "that can be selected from number objects. A permutation is any " +
            "set or subset of objects or events where internal order is " +
            "significant. Permutations are different from combinations, " +
            "for which the internal order is not significant. Use this " +
            "function for lottery-style probability calculations.\nn1 is an " +
            "integer that describes the number of objects.\nn2 is an " +
            "integer that describes the number of objects in each " +
            "permutation.\nBoth arguments are truncated to integers.\nIf " +
            "n1<0 or if n2<0 or if n1<n2, PERMUT returns the undefined result.",
        // PI
        "Returns the number 3.141592653589793, the mathematical constant " +
            "pi, accurate to 16 digits.",
        // POWER
        "Returns the result of a number n1 raised to a given power n2.\n" +
            "n1 is a base number.\nn2 is the exponent used to raise the " +
            "base number to.\nPOWER(n1,n2) = n1^n2.",
        // PRODUCT
        "Multiplies all the numbers given as arguments and returns the " +
            "product.\nn1,[n2]...[n255] are 1 to 255 values that you want " +
            "to multiply.\nn1 required.\nn2,... optional.\n" +
            "PRODUCT(n1,n2,n3) = n1*n2*n3.\nPRODUCT(1,2,...,n) = FACT(n).",
        // RADIANS
        "Converts degrees to radians.\nn is an angle in degrees that you " +
            "want to convert.",
        // RAND
        "Returns an uniformly distributed random real number greater than " +
            "or equal to 0 and less than 1.",
        // RANDBETWEEN
        "Returns a random integer number between the numbers you specify.\n" +
            "n1 is the smallest integer RANDBETWEEN will return.\nn2 is the " +
            "largest integer RANDBETWEEN will return.\nIf n1>n2, " +
            "RANDBETWEEN returns the undefined result.",
        // ROUND
        "Rounds a number to a specified number of digits.\nn1 is the number " +
            "that you want to round.\nn2 is the number of digits to which " +
            "you want to round the n1 argument.\nIf n2>0, then n1 is " +
            "rounded to the specified number of decimal places.\nIf n2=0, " +
            "n1 is rounded to the nearest integer.\nIf n2<0, n1 is rounded " +
            "to the left of the decimal point.\nThe default value for n2 is " +
            "(zero).\nTo always round up (away from zero), use the ROUNDUP " +
            "function.\nTo always round down (toward zero), use the " +
            "ROUNDDOWN function.\nTo round a number to a specific multiple " +
            "(for example, to round to the nearest 0.5), use the MROUND " +
            "function.",
        // ROUNDDOWN
        "Rounds a number down, toward zero.\nn1 is any real number that you " +
            "want rounded down.\nn2 is the number of digits to which you " +
            "want to round n1.\nROUNDDOWN behaves like ROUND, except that " +
            "it always rounds a number down.\nIf n2>0, then n1 is rounded " +
            "down to the specified number of decimal places.\nIf n2=0, " +
            "then n1 is rounded down to the nearest integer.\nIf n2<0, then " +
            "n1 is rounded down to the left of the decimal point.",
        // ROUNDUP
        "Rounds a number up, away from 0 (zero).\nn1 is any real number " +
            "that you want rounded up.\nn2 is the number of digits to which " +
            "you want to round number.\nROUNDUP behaves like ROUND, except " +
            "that it always rounds a number up.\nIf n2>0, then n1 is " +
            "rounded up to the specified number of decimal places.\nIf " +
            "n2=0, then n1 is rounded up to the nearest integer.\nIf n2<0, " +
            "then n1 is rounded up to the left of the decimal point.",
        // SIGN
        "Determines the sign of a number n. Returns 1 if n is positive, " +
            "zero (0) if the n is 0, and -1 if the n is negative.\nn is any " +
            "real number.",
        // SIN
        "Returns the sine of the given angle n.\nn is the angle in radians " +
            "for which you want the sine.\nIf your argument is in degrees, " +
            "multiply it by PI()/180 or use the RADIANS function to " +
            "convert it to radians.",
        // SINH
        "Returns the hyperbolic sine of a number n.\nn is any real number.",
        // SQRT
        "Returns a positive square root.\nn is the number for which you " +
            "want the square root.\nIf n is negative, SQRT returns " +
            "undefined result.",
        // SQRTPI
        "Returns the square root of (n*pi).\nn is the number by which pi " +
            "is multiplied.\nIf n<0, SQRTPI returns the undefined result.",
        // STDEV
        "Estimates standard deviation based on a sample. The standard " +
            "deviation is a measure of how widely values are dispersed " +
            "from the average value (the mean).\nn1,[n2]...[n255] are 1 to " +
            "255 number arguments corresponding to a sample of a population." +
            "\nn1 required.\nn2,... optional.\nSTDEV assumes that its " +
            "arguments are a sample of the population. If your data " +
            "represents the entire population, then compute the standard " +
            "deviation using STDEVP.\nThe standard deviation is calculated " +
            "using the \"n-1\" method.",
        // STDEVP
        "Calculates standard deviation based on the entire population given " +
            "as arguments. The standard deviation is a measure of how " +
            "widely values are dispersed from the average value (the mean)." +
            "\nn1,[n2]...[n255] are 1 to 255 number arguments corresponding " +
            "to a population.\nn1 required.\nn2,... optional.\nSTDEVP " +
            "assumes that its arguments are the entire population. If your " +
            "data represents a sample of the population, then compute the " +
            "standard deviation using STDEV.\nFor large sample sizes, " +
            "STDEV and STDEVP return approximately equal values.\nThe " +
            "standard deviation is calculated using the \"n\" method.",
        // SUM
        "Adds all the numbers that you specify as arguments.\n" +
            "n1,[n2]...[n255] are 1 to 255 values that you want to add.\n" +
            "n1 required.\nn2,... optional.",
        // SUMSQ
        "Returns the sum of the squares of the arguments.\nn1,[n2]...[n255] " +
            "are 1 to 255 arguments for which you want the sum of the " +
            "squares.\nn1 required.\nn2,... optional.",
        // TAN
        "Returns the tangent of the given angle n.\nn is the angle in " +
            "radians for which you want the tangent.\nIf your argument is " +
            "in degrees, multiply it by PI()/180 or use the RADIANS " +
            "function to convert it to radians.",
        // TANH
        "Returns the hyperbolic tangent of a number n.\nn is any real number.",
        // TRUNC
        "Truncates a number to an integer by removing the fractional part " +
            "of a number n1.\nn1 is the number you want to truncate.\n" +
            "n2 is a number specifying the precision of the truncation. " +
            "The default value for n2 is 0 (zero).",
        // VAR
        "Estimates variance based on a sample.\nn1,[n2]...[n255] are 1 to " +
            "255 number arguments corresponding to a sample of a population." +
            "\nn1 required.\nn2,... optional.\nVAR assumes that its " +
            "arguments are a sample of the population. If your data " +
            "represents the entire population, then compute the variance " +
            "by using VARP.",
        // VARP
        "Calculates variance based on the entire population.\n" +
            "n1,[n2]...[n255] are 1 to 255 number arguments corresponding " +
            "to a population.\nn1 required.\nn2,... optional.\nVARP " +
            "assumes that its arguments are the entire population. If your " +
            "data represents a sample of the population, then compute the " +
            "variance by using VAR."
    };
    
    public static final String getArgsText(int index) {
        String s = "";
        switch (FUNCTION_ARGS[index]) {
            case 0x0001: s = "n"; break;
            case 0x0002: s = "n1,n2"; break;
            case 0x0004: s = "n1,n2,n3,n4"; break;
            case 0x0201: s = "n1,[n2]"; break;
            case 0x0301: s = "n1,[n2],[n3]"; break;
            case 0xff01: s = "n1,[n2],...,[n255]"; break;
            default:
        } 
        return "(" + s + ")";
    }
}
