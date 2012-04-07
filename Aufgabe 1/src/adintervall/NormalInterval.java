package adintervall;

import java.util.Arrays;

public class NormalInterval implements Interval{

    private double lowerbound,upperbound;
    
    NormalInterval(double d1, double d2){
     this.lowerbound = d1;
     this.upperbound = d2;
    }

    public static boolean isNaN(double v)
    {
            return (v != v);
    }
    
    @Override
    public double getLowerBound() {
        return this.lowerbound;
    }

    @Override
    public double getUpperBound() {
        return this.upperbound;
    }

    @Override
    public double length() {
        return this.upperbound-this.lowerbound;
    }

    @Override
    public Boolean contains(double value) {
        if(value != value){
            return false;
        }
        return this.lowerbound<=value && value<=this.upperbound;           
    }

    @Override
    public Interval plus(Interval other) 
    {
        if (other == null)
        {
            return Interval.NaI;
        }
       
        double[] d = {lowerbound+other.getLowerBound(), lowerbound+other.getUpperBound(), upperbound+other.getLowerBound(), upperbound+other.getUpperBound()};
        Arrays.sort(d);

        return FactoryInterval.createInterval(d[0],d[d.length -1]);
    }

    @Override
    public Interval minus(Interval other) {
        if (other == null)
        {
            return Interval.NaI;
        }
        
        double[] d = {lowerbound-other.getLowerBound(), lowerbound-other.getUpperBound(), upperbound-other.getLowerBound(),upperbound-other.getUpperBound()};
        Arrays.sort(d);

        return FactoryInterval.createInterval(d[0],d[d.length -1]);
    }

    @Override
    public Interval multi(Interval other) {
        if (other == null)
            return Interval.NaI;
       
        double[] d = {lowerbound*other.getLowerBound(), lowerbound*other.getUpperBound(), upperbound*other.getLowerBound(), upperbound*other.getUpperBound()};
        Arrays.sort(d);

        return FactoryInterval.createInterval(d[0],d[d.length -1]);
    }

    @Override
    public Interval div(Interval other) {
        if (other == null)
            return Interval.NaI;
        if (other.contains(0d))
            return multi(Interval.realInterval);
        return multi(FactoryInterval.createInterval(1 / other.getUpperBound(), 1 / other.getLowerBound()));
    }

    @Override
    public Interval plus(double other) {
        return this.plus(FactoryInterval.createInterval(other, other));
    }

    @Override
    public Interval minus(double other) {
         return this.minus(FactoryInterval.createInterval(other, other));
    }

    @Override
    public Interval multi(double other) {
         return this.multi(FactoryInterval.createInterval(other, other));
    }
    
    @Override
    public String toString(){
        return "[ " + getLowerBound() + " , " + getUpperBound() + " ]";
    }
    
    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null)
            return false;
        if(o instanceof Double)
            return (this.lowerbound == (double) o && this.upperbound == (double) o);
        else if (o instanceof Interval) {
            Interval other = (Interval) o;
            return (this.lowerbound == other.getLowerBound() && this.upperbound == other.getUpperBound());
        } else
            return false;
    }
}