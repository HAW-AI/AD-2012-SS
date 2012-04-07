package adintervall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NormalInterval implements Interval{

    private Double lowerbound,upperbound;
    
    NormalInterval(Double d1, Double d2){
     this.lowerbound = d1;
     this.upperbound = d2;
    }
    
  
    
    @Override
    public Double getLowerBound() {
        return this.lowerbound;
    }

    @Override
    public Double getUpperBound() {
        return this.upperbound;
    }

    @Override
    public Double length() {
        return this.upperbound-this.lowerbound;
    }

    @Override
    public Boolean contains(Double value) {
        if(value == null || Double.isNaN(value)){
            return false;
        }
        return this.lowerbound<=value && value<=this.upperbound;           
    }

    @Override
    public Interval plus(Interval other) {
       if (other == null)
           return Interval.NaI;
       ArrayList<Double> al = new ArrayList<>(Arrays.asList(new Double[] {lowerbound+other.getLowerBound(), lowerbound+other.getUpperBound(), 
                                                                          upperbound+other.getLowerBound(), upperbound+other.getUpperBound()}));
        Collections.sort(al);        
        return FactoryInterval.createInterval(al.get(0),al.get(al.size() - 1));
    }

    @Override
    public Interval minus(Interval other) {
        if (other == null)
            return Interval.NaI;
        ArrayList<Double> al = new ArrayList<>(Arrays.asList(new Double[] {lowerbound-other.getLowerBound(), lowerbound-other.getUpperBound(), 
                                                                           upperbound-other.getLowerBound(),upperbound-other.getUpperBound()}));
        Collections.sort(al);
        
        return FactoryInterval.createInterval(al.get(0),al.get(al.size() - 1));
    }

    @Override
    public Interval multi(Interval other) {
        if (other == null)
            return Interval.NaI;
        ArrayList<Double> al = new ArrayList<>(Arrays.asList(new Double[] {lowerbound*other.getLowerBound(), lowerbound*other.getUpperBound(), 
                                                                           upperbound*other.getLowerBound(), upperbound*other.getUpperBound()}));
        Collections.sort(al);        
        return FactoryInterval.createInterval(al.get(0),al.get(al.size() - 1));
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
    public Interval plus(Double other) {
        return this.plus(FactoryInterval.createInterval(other, other));
    }

    @Override
    public Interval minus(Double other) {
         return this.minus(FactoryInterval.createInterval(other, other));
    }

    @Override
    public Interval multi(Double other) {
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
            return (this.lowerbound.equals(other.getLowerBound()) && this.upperbound.equals(other.getUpperBound()));
        } else
            return false;
    }
}
