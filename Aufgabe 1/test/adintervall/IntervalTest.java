package adintervall;

import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Test;


public class IntervalTest {
    Interval nai, one, zero, real, iPos, iNeg, iNegPos;
    Random rand;

    public IntervalTest() { 
        nai = Interval.NaI;
        zero = Interval.zeroInterval;
        one = Interval.oneInterval;
        real = Interval.realInterval;
        rand = new Random();
        iPos = Int(1d,5d);
        iNeg = Int(-5d,-1d);
        iNegPos = Int(-5d, 5d);
    }  

    @Test
    public void get_bounds_test() {
        System.out.println("testing getBounds()...");
        assertFalse(Interval.NaI.getLowerBound() == Interval.NaN);
        assertFalse(Interval.NaI.getUpperBound() == Interval.NaN);
        assertTrue(iNegPos.getLowerBound() == -5d);
        assertTrue(iNegPos.getUpperBound() == 5d);
    }
    
    @Test
    public void testFactory() {
        //NaI Intervalle
        System.out.println("testing createInterval()...");
        assertTrue(nai.equals(Int(NormalInterval.NaN)));
        assertEquals(nai,Int(Interval.NaN)); // ?
        assertEquals(nai,Int(Interval.NaN,Interval.NaN)); // ?
        assertEquals(nai,Int(Double.NaN));
        assertEquals(nai,Int(Double.NaN, 0d));
        assertEquals(nai,Int(0d, Double.NaN));
        assertEquals(nai,Int(2d, 1d));
        assertEquals(nai,Int(Interval.POSITIVE_INFINITY, Interval.NEGATIVE_INFINITY));
        assertEquals(nai,Int(Interval.POSITIVE_INFINITY, Double.NaN));
        assertEquals(nai,Int(Interval.NEGATIVE_INFINITY, Double.NaN));
        assertTrue(Int(0d,0d) == Interval.zeroInterval);
        assertTrue(Int(1d,1d) == Interval.oneInterval);
        assertTrue(Int(5d,-5d) == Interval.NaI);
        assertTrue(Int(Double.NaN,5d) == Interval.NaI);
        assertTrue(Int(-5d,Double.NaN) == Interval.NaI);
        assertTrue(Int(Double.NaN,Double.NaN) == Interval.NaI);
        assertTrue(Int(Interval.NEGATIVE_INFINITY,Interval.POSITIVE_INFINITY) == Interval.realInterval);
    }

    @Test
    public void testContains() {
        System.out.println("testing contains()...");
        assertFalse(nai.contains(Double.NaN)); //Nan is a not defined number so NaN != NaN
        assertTrue(one.contains(1d));
        assertTrue(zero.contains(0d));
        assertTrue(real.contains(Interval.NEGATIVE_INFINITY));
        assertTrue(real.contains(Interval.POSITIVE_INFINITY));
        assertFalse(real.contains(Double.NaN));

        for (int c = 0; c < 100000; ++c) {
            double d = Randomdouble();
            assertTrue(real.contains(d));
            assertFalse(nai.contains(d));
            assertTrue(Int(d).contains(d));
            double e = Randomdouble();
            if (e != d)
                assertFalse(Int(d).contains(e));
        }
        
        assertFalse(Interval.NaI.contains(8d));
        assertTrue(Interval.realInterval.contains(8d));
        assertTrue(Int(-3d).contains(-3d));
    }


    @Test
    public void testEquals() {
        System.out.println("testing equals()...");
        assertEquals(nai,nai);
        assertEquals(one,one);
        assertEquals(zero,zero);
        assertEquals(real,real);
        for (int c = 0; c < 10000; ++c) {
            Interval iv = RandomInt();
            assertEquals(iv,iv);
        }
        
        assertFalse(iPos.equals(iNeg));
        //reflexiv
        assertTrue(iNeg.equals(iNeg));
        Interval iNew = Int(1d,5d);
        Interval iNew2 = Int(1d,5d);
        //symmetrisch
        assertTrue(iPos.equals(iNew));
        assertTrue(iNew.equals(iPos));
        //transitiv
        assertTrue(iPos.equals(iNew));
        assertTrue(iNew.equals(iNew2));
        assertTrue(iPos.equals(iNew2));
    }

    @Test
    public void testLength() {
        System.out.println("testing length()...");
        assertTrue(NormalInterval.isNaN(nai.length()));
        assertTrue(zero.length() == 0d);
        assertTrue(one.length() == 0d);
        assertTrue(real.length() == Interval.POSITIVE_INFINITY);

        for(int c = 0; c < 100000; ++c) {
            assertTrue(Int(Randomdouble()).length() == 0d);
        }   
        
        assertFalse(Interval.NaI.length() == Interval.NaN);
        assertTrue(Int(5d).length() == 0d);
        assertTrue(iPos.length() == 4d);
    }

    @Test
    public void testPlus() {
        System.out.println("testing plus()...");
        //NaI-ergebnisse
        assertEquals(nai, Int(Interval.NEGATIVE_INFINITY, 0d).plus(Int(0d, Interval.POSITIVE_INFINITY)));
        for(int i = 0; i < 100000; ++i) {
            assertEquals(nai, RandomInt().plus(nai));
            assertEquals(nai, nai.plus(RandomInt()));
        }

        //Neutrales element
        for(int i = 0; i < 100000; ++i) {
            Interval iv = RandomInt();
            assertEquals(iv, iv.plus(Interval.zeroInterval));
            assertEquals(nai, iv.plus(nai));
            if (!iv.equals(nai))
                assertEquals(real, iv.plus(real));
        }       
        
        assertEquals(iPos.plus(iNeg),Int(-4d, 4d));
        assertEquals(Int(Interval.NEGATIVE_INFINITY,-1d).plus(Int(1d,Interval.POSITIVE_INFINITY)), Interval.NaI);
        assertEquals(iNegPos.plus(Interval.NaI), Interval.NaI);
        assertEquals(iNeg.plus(Interval.zeroInterval), iNeg);
        assertEquals(Interval.zeroInterval.plus(iPos), iPos);
    }

    @Test
    public void testMinus(){
        System.out.println("testing minus()...");

        //Nai Ergebnisse
        assertEquals(nai, Int(Interval.NEGATIVE_INFINITY).minus(Int(Interval.NEGATIVE_INFINITY)));
        assertEquals(nai, Int(Interval.POSITIVE_INFINITY).minus(Int(Interval.POSITIVE_INFINITY)));
        for(int i = 0; i < 100000; i++){
            assertEquals(nai, RandomInt().minus(nai));
            assertEquals(nai, nai.minus(RandomInt()));
        }

        //Neutrales Element
        for(int i = 0; i < 100000 ; i++){
            Interval iv = RandomInt();
            assertEquals(iv, iv.minus(zero));
            assertEquals(nai, iv.minus(nai));
            if(!iv.equals(nai)){
                assertEquals(real, real.minus(iv));
            }
        }
        
        assertEquals(iPos.minus(iNeg),Int(2d, 10d));
        assertEquals(Int(Interval.NEGATIVE_INFINITY,-1d).minus(Int(1d,Interval.POSITIVE_INFINITY)), Int(Interval.NEGATIVE_INFINITY,-2d));
        assertEquals(iNegPos.minus(Interval.NaI), Interval.NaI);
        assertEquals(iNeg.minus(Interval.zeroInterval), iNeg);
        assertEquals(Interval.zeroInterval.minus(iPos), iNeg);
    }

    @Test
        public void testMul(){
            System.out.println("testing mul()...");

            //NaI-Ergebnisse
            assertEquals(nai, Int(0.0).multi(Int(Interval.POSITIVE_INFINITY)));
            assertEquals(nai, Int(0.0).multi(Int(Interval.NEGATIVE_INFINITY)));
            assertEquals(nai, Int(Interval.POSITIVE_INFINITY).multi(Int(0.0)));
            assertEquals(nai, Int(Interval.NEGATIVE_INFINITY).multi(Int(0.0)));
            assertEquals(nai, Int(Interval.NEGATIVE_INFINITY).multi(nai));
            assertEquals(nai, Int(Interval.POSITIVE_INFINITY).multi(nai));
            assertEquals(nai, nai.multi(nai));



            //Infinity Ergebnisse
            assertEquals(real, Int(Interval.NEGATIVE_INFINITY,Interval.POSITIVE_INFINITY).multi(Int(Interval.NEGATIVE_INFINITY,Interval.POSITIVE_INFINITY)));
            assertEquals(real, Int(-1d, Interval.POSITIVE_INFINITY).multi(Int(Interval.NEGATIVE_INFINITY)));
            assertEquals(real, Int(-1d,Interval.POSITIVE_INFINITY).multi(Int(Interval.NEGATIVE_INFINITY)));
            assertEquals(real, Int(-1d,Interval.POSITIVE_INFINITY).multi(Interval.POSITIVE_INFINITY));
            assertEquals(Int(Interval.POSITIVE_INFINITY), Int(1d, Interval.POSITIVE_INFINITY).multi(Interval.POSITIVE_INFINITY));
            assertEquals(Int(Interval.POSITIVE_INFINITY), Int(Interval.NEGATIVE_INFINITY, -1d).multi(Int(Interval.NEGATIVE_INFINITY)));
            assertEquals(real, Int(Interval.NEGATIVE_INFINITY, 1d).multi(Int(Interval.NEGATIVE_INFINITY)));
            assertEquals(Int(Interval.NEGATIVE_INFINITY), Int(Interval.NEGATIVE_INFINITY, -1d).multi(Interval.POSITIVE_INFINITY));
            assertEquals(real, Int(Interval.NEGATIVE_INFINITY, 1d).multi(Int(Interval.POSITIVE_INFINITY)));
            assertEquals(real, real.multi(real));

            //Neutrales Element
            for(int i = 0; i< 100000;i++){
                Interval iv = RandomInt();

                assertEquals(iv, iv.multi(one));
                assertEquals(nai, iv.multi(nai));
            }
            
            assertEquals(iPos.multi(iNeg), Int(-25d, -1d));
            assertEquals(Int(Interval.NEGATIVE_INFINITY,-1d).multi(Int(1d,Interval.POSITIVE_INFINITY)), Int(Interval.NEGATIVE_INFINITY,-1d));
            assertEquals(iNegPos.multi(Interval.NaI), Interval.NaI);
            assertEquals(iNeg.multi(Interval.oneInterval), iNeg);
            assertEquals(Interval.oneInterval.multi(iPos), iPos);
            //assertEquals(iNeg.multi(Interval.zeroInterval), Interval.zeroInterval);				 		funktioniert, aber in Java scheinbar 0 != -0
            assertEquals(Interval.zeroInterval.multi(iPos), Interval.zeroInterval);                      
            assertEquals(Interval.zeroInterval.multi(Interval.realInterval), Interval.NaI);
        }

    @Test
        public void testDiv(){
            System.out.println("testing div()...");

            //NaI Ergebnisse
            assertEquals(nai, Int(Interval.POSITIVE_INFINITY).div(Int(Interval.POSITIVE_INFINITY)));
            assertEquals(nai, Int(Interval.NEGATIVE_INFINITY).div(Int(Interval.NEGATIVE_INFINITY)));
            assertEquals(nai, Int(Interval.NEGATIVE_INFINITY).div(Int(Interval.POSITIVE_INFINITY)));
            assertEquals(nai, Int(Interval.POSITIVE_INFINITY).div(Int(Interval.NEGATIVE_INFINITY)));
            assertEquals(nai, zero.div(zero));
            assertEquals(nai, nai.div(Int(Interval.POSITIVE_INFINITY)));
            assertEquals(nai, nai.div(Int(Interval.NEGATIVE_INFINITY)));
            assertEquals(nai, nai.div(nai));
            assertEquals(nai, nai.div(zero));
            assertEquals(nai, nai.div(one));


            //Infinity Ergebnisse
            assertEquals(Int(Interval.POSITIVE_INFINITY), Int(Interval.NEGATIVE_INFINITY).div(Int(-1d)));
            assertEquals(real, Int(Interval.NEGATIVE_INFINITY).div(zero));
            assertEquals(Int(Interval.NEGATIVE_INFINITY), Int(Interval.NEGATIVE_INFINITY).div(one));
            assertEquals(real, Int(-1d).div(zero));
            assertEquals(real, Int(1d).div(zero));
            assertEquals(Int(Interval.NEGATIVE_INFINITY), Int(Interval.POSITIVE_INFINITY).div(Int(-1d)));
            assertEquals(real, Int(Interval.POSITIVE_INFINITY).div(zero));
            assertEquals(Int(Interval.POSITIVE_INFINITY), Int(Interval.POSITIVE_INFINITY).div(one));

            //Neutrales Element
            for(int i = 0; i < 10000; i++){
                Interval vi = RandomInt();
                if (vi != nai) {
                    assertEquals(vi, vi.div(one));
                    assertEquals(nai, vi.div(nai));
                    if (vi.getLowerBound()==  0d || vi.getUpperBound() == 0d)
                        assertEquals(nai, vi.div(zero));
                    else
                        assertEquals(real, vi.div(zero));
                }
            }
            
            assertEquals(iPos.div(iNeg), Int(-5d, -0.2d));
            assertEquals(Int(Interval.NEGATIVE_INFINITY,-1d).div(Int(1d,Interval.POSITIVE_INFINITY)), Interval.NaI);
            assertEquals(iNegPos.div(Interval.NaI), Interval.NaI);
            assertEquals(iNeg.div(Interval.oneInterval), iNeg);
            assertEquals(Interval.oneInterval.div(iPos), Int(0.2d, 1d));
            assertEquals(iNeg.div(Interval.zeroInterval), Interval.realInterval);
            assertEquals(Interval.zeroInterval.div(iPos), Interval.zeroInterval);
            assertEquals(Interval.zeroInterval.div(Interval.realInterval), Interval.NaI);
        }

    private Interval Int(double a, double b) {
        return FactoryInterval.createInterval(a,b);
    }

    private Interval Int(double a) {
        return Int(a,a);
    }

    private Interval RandomInt() {
        return Int((double) rand.nextInt(10000) - 5000,(double) rand.nextInt(100000));
    }

    private double Randomdouble() {
        return (double) rand.nextInt(100000) - 50000;
    }

}