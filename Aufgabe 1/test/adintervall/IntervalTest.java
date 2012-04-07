package adintervall;

import static org.junit.Assert.*;
import java.util.Random;

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
        assertTrue(Interval.NaI.getLowerBound().equals(Double.NaN));
        assertTrue(Interval.NaI.getUpperBound().equals(Double.NaN));
        assertTrue(iNegPos.getLowerBound().equals(-5d));
        assertTrue(iNegPos.getUpperBound().equals(5d));
    }
    
    @Test
    public void testFactory() {
        //NaI Intervalle
        System.out.println("testing createInterval()...");
        assertTrue(nai.equals(Int(Double.NaN)));
        assertEquals(nai,Int(null));
        assertEquals(nai,Int(null,null));
        assertEquals(nai,Int(Double.NaN));
        assertEquals(nai,Int(Double.NaN, 0d));
        assertEquals(nai,Int(0d, Double.NaN));
        assertEquals(nai,Int(2d, 1d));
        assertEquals(nai,Int(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertEquals(nai,Int(Double.POSITIVE_INFINITY, Double.NaN));
        assertEquals(nai,Int(Double.NEGATIVE_INFINITY, Double.NaN));
        assertTrue(Int(0d,0d) == Interval.zeroInterval);
        assertTrue(Int(1d,1d) == Interval.oneInterval);
        assertTrue(Int(5d,-5d) == Interval.NaI);
        assertTrue(Int(Double.NaN,5d) == Interval.NaI);
        assertTrue(Int(-5d,Double.NaN) == Interval.NaI);
        assertTrue(Int(Double.NaN,Double.NaN) == Interval.NaI);
        assertTrue(Int(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY) == Interval.realInterval);
    }

    @Test
    public void testContains() {
        System.out.println("testing contains()...");
        assertFalse(nai.contains(Double.NaN)); //Nan is a not defined number so NaN != NaN
        assertTrue(one.contains(1d));
        assertTrue(zero.contains(0d));
        assertTrue(real.contains(Double.NEGATIVE_INFINITY));
        assertTrue(real.contains(Double.POSITIVE_INFINITY));
        assertFalse(real.contains(Double.NaN));

        for (int c = 0; c < 100000; ++c) {
            double d = RandomDouble();
            assertTrue(real.contains(d));
            assertFalse(nai.contains(d));
            assertTrue(Int(d).contains(d));
            double e = RandomDouble();
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
        assertTrue(Double.isNaN(nai.length()));
        assertTrue(zero.length() == 0d);
        assertTrue(one.length() == 0d);
        assertTrue(real.length() == Double.POSITIVE_INFINITY);

        for(int c = 0; c < 100000; ++c) {
            assertTrue(Int(RandomDouble()).length() == 0d);
        }   
        
        assertTrue(Interval.NaI.length().equals(Double.NaN));
        assertTrue(Int(5d).length().equals(0d));
        assertTrue(iPos.length().equals(4d));
    }

    @Test
    public void testPlus() {
        System.out.println("testing plus()...");
        //NaI-ergebnisse
        assertEquals(nai, Int(Double.NEGATIVE_INFINITY, 0d).plus(Int(0d, Double.POSITIVE_INFINITY)));
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
        assertEquals(Int(Double.NEGATIVE_INFINITY,-1d).plus(Int(1d,Double.POSITIVE_INFINITY)), Interval.NaI);
        assertEquals(iNegPos.plus(Interval.NaI), Interval.NaI);
        assertEquals(iNeg.plus(Interval.zeroInterval), iNeg);
        assertEquals(Interval.zeroInterval.plus(iPos), iPos);
    }

    @Test
    public void testMinus(){
        System.out.println("testing minus()...");

        //Nai Ergebnisse
        assertEquals(nai, Int(Double.NEGATIVE_INFINITY).minus(Int(Double.NEGATIVE_INFINITY)));
        assertEquals(nai, Int(Double.POSITIVE_INFINITY).minus(Int(Double.POSITIVE_INFINITY)));
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
        assertEquals(Int(Double.NEGATIVE_INFINITY,-1d).minus(Int(1d,Double.POSITIVE_INFINITY)), Int(Double.NEGATIVE_INFINITY,-2d));
        assertEquals(iNegPos.minus(Interval.NaI), Interval.NaI);
        assertEquals(iNeg.minus(Interval.zeroInterval), iNeg);
        assertEquals(Interval.zeroInterval.minus(iPos), iNeg);
    }

    @Test
        public void testMul(){
            System.out.println("testing mul()...");

            //NaI-Ergebnisse
            assertEquals(nai, Int(0.0).multi(Int(Double.POSITIVE_INFINITY)));
            assertEquals(nai, Int(0.0).multi(Int(Double.NEGATIVE_INFINITY)));
            assertEquals(nai, Int(Double.POSITIVE_INFINITY).multi(Int(0.0)));
            assertEquals(nai, Int(Double.NEGATIVE_INFINITY).multi(Int(0.0)));
            assertEquals(nai, Int(Double.NEGATIVE_INFINITY).multi(nai));
            assertEquals(nai, Int(Double.POSITIVE_INFINITY).multi(nai));
            assertEquals(nai, nai.multi(nai));



            //Infinity Ergebnisse
            assertEquals(real, Int(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY).multi(Int(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY)));
            assertEquals(real, Int(-1d, Double.POSITIVE_INFINITY).multi(Int(Double.NEGATIVE_INFINITY)));
            assertEquals(real, Int(-1d,Double.POSITIVE_INFINITY).multi(Int(Double.NEGATIVE_INFINITY)));
            assertEquals(real, Int(-1d,Double.POSITIVE_INFINITY).multi(Double.POSITIVE_INFINITY));
            assertEquals(Int(Double.POSITIVE_INFINITY), Int(1d, Double.POSITIVE_INFINITY).multi(Double.POSITIVE_INFINITY));
            assertEquals(Int(Double.POSITIVE_INFINITY), Int(Double.NEGATIVE_INFINITY, -1d).multi(Int(Double.NEGATIVE_INFINITY)));
            assertEquals(real, Int(Double.NEGATIVE_INFINITY, 1d).multi(Int(Double.NEGATIVE_INFINITY)));
            assertEquals(Int(Double.NEGATIVE_INFINITY), Int(Double.NEGATIVE_INFINITY, -1d).multi(Double.POSITIVE_INFINITY));
            assertEquals(real, Int(Double.NEGATIVE_INFINITY, 1d).multi(Int(Double.POSITIVE_INFINITY)));
            assertEquals(real, real.multi(real));

            //Neutrales Element
            for(int i = 0; i< 100000;i++){
                Interval iv = RandomInt();

                assertEquals(iv, iv.multi(one));
                assertEquals(nai, iv.multi(nai));
            }
            
            assertEquals(iPos.multi(iNeg), Int(-25d, -1d));
            assertEquals(Int(Double.NEGATIVE_INFINITY,-1d).multi(Int(1d,Double.POSITIVE_INFINITY)), Int(Double.NEGATIVE_INFINITY,-1d));
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
            assertEquals(nai, Int(Double.POSITIVE_INFINITY).div(Int(Double.POSITIVE_INFINITY)));
            assertEquals(nai, Int(Double.NEGATIVE_INFINITY).div(Int(Double.NEGATIVE_INFINITY)));
            assertEquals(nai, Int(Double.NEGATIVE_INFINITY).div(Int(Double.POSITIVE_INFINITY)));
            assertEquals(nai, Int(Double.POSITIVE_INFINITY).div(Int(Double.NEGATIVE_INFINITY)));
            assertEquals(nai, zero.div(zero));
            assertEquals(nai, nai.div(Int(Double.POSITIVE_INFINITY)));
            assertEquals(nai, nai.div(Int(Double.NEGATIVE_INFINITY)));
            assertEquals(nai, nai.div(nai));
            assertEquals(nai, nai.div(zero));
            assertEquals(nai, nai.div(one));


            //Infinity Ergebnisse
            assertEquals(Int(Double.POSITIVE_INFINITY), Int(Double.NEGATIVE_INFINITY).div(Int(-1d)));
            assertEquals(real, Int(Double.NEGATIVE_INFINITY).div(zero));
            assertEquals(Int(Double.NEGATIVE_INFINITY), Int(Double.NEGATIVE_INFINITY).div(one));
            assertEquals(real, Int(-1d).div(zero));
            assertEquals(real, Int(1d).div(zero));
            assertEquals(Int(Double.NEGATIVE_INFINITY), Int(Double.POSITIVE_INFINITY).div(Int(-1d)));
            assertEquals(real, Int(Double.POSITIVE_INFINITY).div(zero));
            assertEquals(Int(Double.POSITIVE_INFINITY), Int(Double.POSITIVE_INFINITY).div(one));

            //Neutrales Element
            for(int i = 0; i < 10000; i++){
                Interval vi = RandomInt();
                if (vi != nai) {
                    assertEquals(vi, vi.div(one));
                    assertEquals(nai, vi.div(nai));
                    if (vi.getLowerBound().equals(0d) || vi.getUpperBound().equals(0d))
                        assertEquals(nai, vi.div(zero));
                    else
                        assertEquals(real, vi.div(zero));
                }
            }
            
            assertEquals(iPos.div(iNeg), Int(-5d, -0.2d));
            assertEquals(Int(Double.NEGATIVE_INFINITY,-1d).div(Int(1d,Double.POSITIVE_INFINITY)), Interval.NaI);
            assertEquals(iNegPos.div(Interval.NaI), Interval.NaI);
            assertEquals(iNeg.div(Interval.oneInterval), iNeg);
            assertEquals(Interval.oneInterval.div(iPos), Int(0.2d, 1d));
            assertEquals(iNeg.div(Interval.zeroInterval), Interval.realInterval);
            assertEquals(Interval.zeroInterval.div(iPos), Interval.zeroInterval);
            assertEquals(Interval.zeroInterval.div(Interval.realInterval), Interval.NaI);
        }

    private Interval Int(Double a, Double b) {
        return FactoryInterval.createInterval(a,b);
    }

    private Interval Int(Double a) {
        return Int(a,a);
    }

    private Interval RandomInt() {
        return Int((double) rand.nextInt(10000) - 5000,(double) rand.nextInt(100000));
    }

    private double RandomDouble() {
        return (double) rand.nextInt(100000) - 50000;
    }

}