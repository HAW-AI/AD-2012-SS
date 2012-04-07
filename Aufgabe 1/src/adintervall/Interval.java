package adintervall;

public interface Interval {
    
        public static final double NaN = 0.0d / 0.0;
        public static final double NEGATIVE_INFINITY = -1.0 / 0.0;
        public static final double POSITIVE_INFINITY = 1.0 / 0.0;
    
        // Literale
        Interval zeroInterval = new NormalInterval(0.0,0.0);
        Interval oneInterval = new NormalInterval(1.0, 1.0);
        Interval NaI =  new NormalInterval(NaN, NaN);
        Interval realInterval = new NormalInterval(NEGATIVE_INFINITY, POSITIVE_INFINITY);

        // accessor
        double getLowerBound();
        double getUpperBound();
        boolean equals(Object other);
        double length();
        Boolean contains(double value);
       
        // producer
        Interval plus(Interval other);
        Interval minus(Interval other);
        Interval multi(Interval other);
        Interval div(Interval other);

        Interval plus(double other);
        Interval minus(double other);
        Interval multi(double other);
}

