package adintervall;

import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Test;

public class IntervalTest {

    Interval nai, one, zero, real, iPos, iNeg, iNegPos, Pos, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13;
    double nan, d0, d1, d2, d3, d4;
    Random rand;

    public IntervalTest() {
        nai = Interval.NaI;
        zero = Interval.zeroInterval;
        one = Interval.oneInterval;
        real = Interval.realInterval;
        rand = new Random();
        iPos = Int(1d, 5d);
        iNeg = Int(-5d, -1d);
        iNegPos = Int(-5d, 5d);
        
        i1 = FactoryInterval.createInterval(1d, 2d);
        i2 = FactoryInterval.createInterval(3d, 4d);
        i3 = FactoryInterval.createInterval(1d, 3d);
        i4 = FactoryInterval.createInterval(4d, 5d);
        i5 = FactoryInterval.createInterval(1d, 5d);
        i6 = FactoryInterval.createInterval(2d, 4d);
        
        i7 = FactoryInterval.createInterval(0d, 2d);
        i8 = FactoryInterval.createInterval(0d, 4d);
        i9 = FactoryInterval.createInterval(2d, 3d);
        i10 = FactoryInterval.createInterval(4d, 9d);
        i11 = FactoryInterval.createInterval(-2d, 2d);
        i12 = FactoryInterval.createInterval(-2d, 0d);
        i13 = FactoryInterval.createInterval(-3d, -2d);
        
        nan = Interval.NaN;
        d0 = 0d;
        d1 = 1d;
        d2 = 2d;
        d3 = 3d;
        d4 = 4d;
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
        assertEquals(nai, Int(Interval.NaN)); // ?
        assertEquals(nai, Int(Interval.NaN, Interval.NaN)); // ?
        assertEquals(nai, Int(Double.NaN));
        assertEquals(nai, Int(Double.NaN, 0d));
        assertEquals(nai, Int(0d, Double.NaN));
        assertEquals(nai, Int(2d, 1d));
        assertEquals(nai, Int(Interval.POSITIVE_INFINITY, Interval.NEGATIVE_INFINITY));
        assertEquals(nai, Int(Interval.POSITIVE_INFINITY, Double.NaN));
        assertEquals(nai, Int(Interval.NEGATIVE_INFINITY, Double.NaN));
        assertTrue(Int(0d, 0d) == Interval.zeroInterval);
        assertTrue(Int(1d, 1d) == Interval.oneInterval);
        assertTrue(Int(5d, -5d) == Interval.NaI);
        assertTrue(Int(Double.NaN, 5d) == Interval.NaI);
        assertTrue(Int(-5d, Double.NaN) == Interval.NaI);
        assertTrue(Int(Double.NaN, Double.NaN) == Interval.NaI);
        assertTrue(Int(Interval.NEGATIVE_INFINITY, Interval.POSITIVE_INFINITY) == Interval.realInterval);
    }

        @Test
    public void testContains(){
    	double a = 1d;
        double b = 2d;
        double c = 3d;
        double d = 4d;
        Interval i1 = FactoryInterval.createInterval(a, b);
        Interval i2 = FactoryInterval.createInterval(b, c);
        Interval i3 = FactoryInterval.createInterval(c, d);
        
        //contains (NaI , x) = False
        assertFalse(nai.contains(a));
        
        //contains (realInterval , x) = True
        assertTrue(real.contains(a));
        
        //contains (createInterval(x), x) = True
        assertTrue(i1.contains(a));
        
        //x ungleich z daraus folgt contains(createInterval ( x), z) = False
        assertFalse(i1.contains(c));       
        
        //contains([a ,b],[a, b]) = true
        assertTrue(i1.contains(i1));
        
        //contains(realInterval, [a, b]) = contains([a,b], realInterval) = true   
        assertTrue(real.contains(i1).equals(i1.contains(real)));
        
        //contains(NaI, i1) = contains(i1, NaI) = false
        assertTrue(nai.contains(i1).equals(i1.contains(nai)));
        
    	
    }

    @Test
    public void testEquals() {
        System.out.println("testing equals()...");
        assertEquals(nai, nai);

        assertEquals(one, one);
        assertEquals(zero, zero);
        assertEquals(real, real);
        for (int c = 0; c < 10000; ++c) {
            Interval iv = RandomInt();
            assertEquals(iv, iv);
        }

        assertFalse(iPos.equals(iNeg));
        //reflexiv
        assertTrue(iNeg.equals(iNeg));
        Interval iNew = Int(1d, 5d);
        Interval iNew2 = Int(1d, 5d);
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

        for (int c = 0; c < 100000; ++c) {
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
        for (int i = 0; i < 100000; ++i) {
            assertEquals(nai, RandomInt().plus(nai));
            assertEquals(nai, nai.plus(RandomInt()));
        }

        //Neutrales element
        for (int i = 0; i < 100000; ++i) {
            Interval iv = RandomInt();
            assertEquals(iv, iv.plus(Interval.zeroInterval));
            assertEquals(nai, iv.plus(nai));
            if (!iv.equals(nai)) {
                assertEquals(real, iv.plus(real));
            }
        }

        assertEquals(iPos.plus(iNeg), Int(-4d, 4d));
        assertEquals(Int(Interval.NEGATIVE_INFINITY, -1d).plus(Int(1d, Interval.POSITIVE_INFINITY)), Interval.NaI);
        assertEquals(iNegPos.plus(Interval.NaI), Interval.NaI);
        assertEquals(iNeg.plus(Interval.zeroInterval), iNeg);
        assertEquals(Interval.zeroInterval.plus(iPos), iPos);
    }

    @Test
    public void testMinus() {
        System.out.println("testing minus()...");

        //Nai Ergebnisse
        assertEquals(nai, Int(Interval.NEGATIVE_INFINITY).minus(Int(Interval.NEGATIVE_INFINITY)));
        assertEquals(nai, Int(Interval.POSITIVE_INFINITY).minus(Int(Interval.POSITIVE_INFINITY)));
        for (int i = 0; i < 100000; i++) {
            assertEquals(nai, RandomInt().minus(nai));
            assertEquals(nai, nai.minus(RandomInt()));
        }

        //Neutrales Element
        for (int i = 0; i < 100000; i++) {
            Interval iv = RandomInt();
            assertEquals(iv, iv.minus(zero));
            assertEquals(nai, iv.minus(nai));
            if (!iv.equals(nai)) {
                assertEquals(real, real.minus(iv));
            }
        }

        assertEquals(iPos.minus(iNeg), Int(2d, 10d));
        assertEquals(Int(Interval.NEGATIVE_INFINITY, -1d).minus(Int(1d, Interval.POSITIVE_INFINITY)), Int(Interval.NEGATIVE_INFINITY, -2d));
        assertEquals(iNegPos.minus(Interval.NaI), Interval.NaI);
        assertEquals(iNeg.minus(Interval.zeroInterval), iNeg);
        assertEquals(Interval.zeroInterval.minus(iPos), iNeg);
    }

    @Test
    public void testMul() {
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
        assertEquals(real, Int(Interval.NEGATIVE_INFINITY, Interval.POSITIVE_INFINITY).multi(Int(Interval.NEGATIVE_INFINITY, Interval.POSITIVE_INFINITY)));
        assertEquals(real, Int(-1d, Interval.POSITIVE_INFINITY).multi(Int(Interval.NEGATIVE_INFINITY)));
        assertEquals(real, Int(-1d, Interval.POSITIVE_INFINITY).multi(Int(Interval.NEGATIVE_INFINITY)));
        assertEquals(real, Int(-1d, Interval.POSITIVE_INFINITY).multi(Interval.POSITIVE_INFINITY));
        assertEquals(Int(Interval.POSITIVE_INFINITY), Int(1d, Interval.POSITIVE_INFINITY).multi(Interval.POSITIVE_INFINITY));
        assertEquals(Int(Interval.POSITIVE_INFINITY), Int(Interval.NEGATIVE_INFINITY, -1d).multi(Int(Interval.NEGATIVE_INFINITY)));
        assertEquals(real, Int(Interval.NEGATIVE_INFINITY, 1d).multi(Int(Interval.NEGATIVE_INFINITY)));
        assertEquals(Int(Interval.NEGATIVE_INFINITY), Int(Interval.NEGATIVE_INFINITY, -1d).multi(Interval.POSITIVE_INFINITY));
        assertEquals(real, Int(Interval.NEGATIVE_INFINITY, 1d).multi(Int(Interval.POSITIVE_INFINITY)));
        assertEquals(real, real.multi(real));

        //Neutrales Element
        for (int i = 0; i < 100000; i++) {
            Interval iv = RandomInt();

            assertEquals(iv, iv.multi(one));
            assertEquals(nai, iv.multi(nai));
        }

        assertEquals(iPos.multi(iNeg), Int(-25d, -1d));
        assertEquals(Int(Interval.NEGATIVE_INFINITY, -1d).multi(Int(1d, Interval.POSITIVE_INFINITY)), Int(Interval.NEGATIVE_INFINITY, -1d));
        assertEquals(iNegPos.multi(Interval.NaI), Interval.NaI);
        assertEquals(iNeg.multi(Interval.oneInterval), iNeg);
        assertEquals(Interval.oneInterval.multi(iPos), iPos);
        //assertEquals(iNeg.multi(Interval.zeroInterval), Interval.zeroInterval);				 		funktioniert, aber in Java scheinbar 0 != -0
        assertEquals(Interval.zeroInterval.multi(iPos), Interval.zeroInterval);
        assertEquals(Interval.zeroInterval.multi(Interval.realInterval), Interval.NaI);
    }

    @Test
    public void testDiv() {
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
        for (int i = 0; i < 10000; i++) {
            Interval vi = RandomInt();
            if (vi != nai) {
                assertEquals(vi, vi.div(one));
                assertEquals(nai, vi.div(nai));
                if (vi.getLowerBound() == 0d || vi.getUpperBound() == 0d) {
                    assertEquals(nai, vi.div(zero));
                } else {
                    assertEquals(real, vi.div(zero));
                }
            }
        }

        assertEquals(iPos.div(iNeg), Int(-5d, -0.2d));
        assertEquals(Int(Interval.NEGATIVE_INFINITY, -1d).div(Int(1d, Interval.POSITIVE_INFINITY)), Interval.NaI);
        assertEquals(iNegPos.div(Interval.NaI), Interval.NaI);
        assertEquals(iNeg.div(Interval.oneInterval), iNeg);
        assertEquals(Interval.oneInterval.div(iPos), Int(0.2d, 1d));
        assertEquals(iNeg.div(Interval.zeroInterval), Interval.realInterval);
        assertEquals(Interval.zeroInterval.div(iPos), Interval.zeroInterval);
        assertEquals(Interval.zeroInterval.div(Interval.realInterval), Interval.NaI);
    }

    private Interval Int(double a, double b) {
        return FactoryInterval.createInterval(a, b);
    }

    private Interval Int(double a) {
        return Int(a, a);
    }

    private Interval RandomInt() {
        return Int((double) rand.nextInt(10000) - 5000, (double) rand.nextInt(100000));
    }

    private double Randomdouble() {
        return (double) rand.nextInt(100000) - 50000;
    }

    @Test
    public void testequalsnew() {
        Interval i1 = FactoryInterval.createInterval(1.0, 2.0);
        Interval i2 = FactoryInterval.createInterval(1.0, 2.0);
        Interval i3 = FactoryInterval.createInterval(1.0, 2.0);
        assertTrue(i1.equals(i1));
        assertTrue(i2.equals(i2));
        assertTrue(i1.equals(i2));
        assertTrue(i2.equals(i1));
        assertTrue(i1.equals(i2) == i2.equals(i1));
        assertTrue((i1.equals(i2) && i2.equals(i3)) == i1.equals(i3));


    }

    @Test
    public void testnewNai() {
        assertFalse(nai.equals(nai));
    }
    
    @Test
    public void testKomm(){
        //Test der Kommutativität
    	double a = 1d;
        double b = 2d;
        double c = 3d;
        double d = 4d;
        Interval i1 = FactoryInterval.createInterval(a, b);
        Interval i2 = FactoryInterval.createInterval(b, c);
        Interval i3 = FactoryInterval.createInterval(c, d);
        
        
    	assertTrue(i1.plus(b).equals(i1.plusKom(b)));
    	assertTrue(i1.multi(b).equals(i1.multiKom(b)));
    }

    @Test
    public void testUnion() {
    	double a = 1d;
        double b = 2d;
        double c = 3d;
        double d = 4d;
        Interval i1 = FactoryInterval.createInterval(a, b);
        Interval i2 = FactoryInterval.createInterval(b, c);
        Interval i3 = FactoryInterval.createInterval(c, d);
        
         
          //union(NaI, A) = union(A, NaI) = NaI
        assertTrue(i1.union(nai).equals(nai.union(i1)));
        assertTrue(i1.union(nai).equals(nai));
        assertTrue(nai.union(i1).equals(nai));
        
        //union(A, A) = A
        assertTrue(i1.union(i1).equals(i1));
        
        //union(A, B) = union(B, A)
        assertTrue(i1.union(i2).equals(i2.union(i1)));
        
        //union(realInterval, [a, b]) = union([a, b], realInterval) = realInterval
        assertTrue(i1.union(real).equals(real.union(i1)));
        
        //union(union(A, B), C) = union(A, union(B, C))
        assertTrue(i1.union(i2).union(i3).equals(i1.union(i2.union(i3))));
 
    }

    @Test 
    public void testIntersection(){
    	
    	double a = 1d;
        double b = 2d;
        double c = 3d;
        double d = 4d;
        Interval i1 = FactoryInterval.createInterval(a, b);
        Interval i2 = FactoryInterval.createInterval(b, c);
        Interval i3 = FactoryInterval.createInterval(c, d);
        
        Interval i4 = FactoryInterval.createInterval(b, d);
    	
    	//union(A, intersection(A, B)) = A
        assertTrue(i1.union(i1.intersection(i2)).equals(i1));
        
    	//intersection(union(A, C), union(B, C)) = union(C, intersection(A, B))
        assertTrue(i1.union(i3).intersection(i2.union(i3)).equals(i3.union(i1.intersection(i2))));
        
        //intersection(A, union(A, B)) = A
        assertTrue(i1.intersection(i1.union(i2)).equals(i1));
        
        //intersection(NaI, A) = intersection(A, NaI) = NaI
        assertTrue(nai.intersection(i1).equals(i1.intersection(nai)));
        assertTrue(nai.intersection(i1).equals(nai));
        assertTrue(i1.intersection(nai).equals(nai));
        
        //intersection(realInterval, [a, b]) = intersection([a, b], realInterval) = [a, b]
        assertTrue(real.intersection(i1.intersection(real)).equals(i1));
        
        //intersection(intersection(A, B), C) = intersection(A, intersection(B, C))
        assertTrue(i3.intersection(i1.intersection(i2)).equals(i1.intersection(i2.intersection(i3))));
        
        //intersection(A, B) = intersection(B, A)
        assertTrue(i1.intersection(i2).equals(i2.intersection(i1)));
        
        //intersection(A, A) = A
        assertTrue(i1.intersection(i1).equals(i1));
        
    	
    }

        @Test
    public void testDifference(){
    	double a = 1d;
        double b = 2d;
        double c = 3d;
        double d = 4d;
        Interval i1 = FactoryInterval.createInterval(a, b);
        Interval i2 = FactoryInterval.createInterval(b, c);
        Interval i3 = FactoryInterval.createInterval(c, d);
    	
    	//difference(NaI, A) = difference(A, NaI) = NaI
        assertTrue(nai.difference(i1).equals(i1.difference(nai)));
        assertTrue(nai.difference(i1).equals(nai));
        assertTrue(i1.difference(nai).equals(nai));
        
        //difference(realInterval, [a, b]) = difference([a, b], realInterval) = NaI
        assertTrue(real.difference(i1).equals(i1.difference(real)));
        assertTrue(real.difference(i1).equals(nai));
        assertTrue(i1.difference(real).equals(nai));
        
        //difference(A, union(B, C)) = intersection(difference(A, B), difference(A, C))
        assertTrue(i1.difference(i2.union(i3)).equals(i1.difference(i2).intersection(i1.difference(i3))));
        
        //difference(A, intersection(B, C)) = union(difference(A, B), difference(A, C))
        assertTrue(i1.difference(i2.intersection(i3)).equals(i1.difference(i2).union(i1.difference(i3))));
        
        
    }

//    @Test
//    public void testassoziativ() {
//        assertEquals(FactoryInterval.union(
//                FactoryInterval.union(iPos, iNeg), iNegPos),
//                FactoryInterval.union(iNegPos, FactoryInterval.union(iPos, iNeg)));
//        assertEquals(FactoryInterval.union(
//                FactoryInterval.intersection(iPos, iNeg), iNegPos),
//                FactoryInterval.intersection(iNegPos, FactoryInterval.intersection(iPos, iNeg)));
//
//    }
//
//    @Test
//    public void testkommutativ() {
//        assertEquals(
//                FactoryInterval.union(iNeg, iPos),
//                FactoryInterval.union(iPos, iNeg));
//        assertEquals(
//                FactoryInterval.intersection(iNeg, iPos),
//                FactoryInterval.intersection(iPos, iNeg));
//    }
//
//    @Test
//    public void testIdempotenz() {
//        assertEquals(FactoryInterval.intersection(iPos, iPos), iPos);
//        assertEquals(FactoryInterval.union(iPos, iPos), iPos);
//    }
//
//    @Test
//    public void testmengenundlength() {
//       assertEquals((FactoryInterval.union(iPos, iNegPos)).length(), iPos.length() + iNegPos.length());
//        assertEquals(FactoryInterval.union(iPos, iNegPos),iNegPos);
//        Interval i1 = FactoryInterval.union(iPos, iNegPos);
//        assertTrue(i1.contains(iPos));
//        assertTrue(i1.contains(iNegPos));
//    }
//    
//    @Test
//    public void testvergleiche() {
//        Interval i1 = FactoryInterval.createInterval(1d, 3d);
//        Interval i2 = FactoryInterval.createInterval(2d, 4d) ;
//        assertTrue(i1.pLess(i2));
//        assertFalse(i1.less(i2));
//        assertTrue(one.equals(1d));
//        assertTrue(! nai.equals(nai));
//        
//    }
    
    
}