package adintervall;

public interface Interval {
        // Literale
        Interval zeroInterval = new NormalInterval(new Double(0),new Double(0));
        Interval oneInterval = new NormalInterval(new Double(1), new Double(1));
        Interval NaI =  new NormalInterval(Double.NaN,Double.NaN);
        Interval realInterval = new NormalInterval(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);

        // accessor
        Double getLowerBound();
        Double getUpperBound();
        boolean equals(Object other);
        Double length();
        Boolean contains(Double value);
       
        // producer
        Interval plus(Interval other);
        Interval minus(Interval other);
        Interval multi(Interval other);
        Interval div(Interval other);

        Interval plus(Double other);
        Interval minus(Double other);
        Interval multi(Double other);
}

