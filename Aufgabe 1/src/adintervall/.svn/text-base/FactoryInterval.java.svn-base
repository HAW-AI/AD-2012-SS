package adintervall;

public final class FactoryInterval{
    private FactoryInterval() {}
    public static Interval createInterval(Double val1, Double val2) {
        if(val1 == null || val2 == null || Double.isNaN(val1) || Double.isNaN(val2) || val1 > val2)
            return Interval.NaI;
        else if (val1.equals(0d) && val2.equals(0d))
            return Interval.zeroInterval;
        else if (val1.equals(1d) && val2.equals(1d))
            return Interval.oneInterval;
        else if (val1.equals(Double.NEGATIVE_INFINITY) && val2.equals(Double.POSITIVE_INFINITY))
            return Interval.realInterval;
        else
            return new NormalInterval(val1, val2);
    }

    public static Interval createInterval(Double val) {
        return createInterval(val, val);
    }
}
