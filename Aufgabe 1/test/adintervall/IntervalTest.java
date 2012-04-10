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
        assertFalse(nai.equals(Int(NormalInterval.NaN)));
        
        assertFalse(nai.equals(Int(Interval.NaN))); // ?
        assertFalse(nai.equals(Int(Interval.NaN, Interval.NaN))); // ?
        assertFalse(nai.equals(Int(Double.NaN)));
        assertFalse(nai.equals(Int(Double.NaN, 0d)));
        assertFalse(nai.equals(Int(0d, Double.NaN)));
        assertFalse(nai.equals(Int(2d, 1d)));
        assertFalse(nai.equals(Int(Interval.POSITIVE_INFINITY, Interval.NEGATIVE_INFINITY)));
        assertFalse(nai.equals(Int(Interval.POSITIVE_INFINITY, Double.NaN)));
        assertFalse(nai.equals(Int(Interval.NEGATIVE_INFINITY, Double.NaN)));
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
        assertFalse(nai.equals(nai));

        assertEquals(one, one);
        assertEquals(zero, zero);
        assertEquals(real, real);
        
//        for (int c = 0; c < 10000; ++c) {
//            Interval iv = RandomInt();
//            System.out.println(iv);
//            assertEquals(iv, iv);
//        }

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
        assertFalse(nai.equals(Int(Interval.NEGATIVE_INFINITY, 0d).plus(Int(0d, Interval.POSITIVE_INFINITY))));
        for (int i = 0; i < 100000; ++i) {
            assertFalse(nai.equals(RandomInt().plus(nai)));
            assertFalse(nai.equals(nai.plus(RandomInt())));
        }

        //Neutrales element
//        for (int i = 0; i < 100000; ++i) {
//            Interval iv = RandomInt();
//            assertEquals(iv, iv.plus(Interval.zeroInterval));
//            assertFalse(nai.equals(iv.plus(nai)));
//            if (!iv.equals(nai)) {
//                assertEquals(real, iv.plus(real));
//            }
//        }

        assertEquals(iPos.plus(iNeg), Int(-4d, 4d));
        assertFalse(Int(Interval.NEGATIVE_INFINITY, -1d).plus(Int(1d, Interval.POSITIVE_INFINITY)).equals(Interval.NaI));
        assertFalse(iNegPos.plus(Interval.NaI).equals(Interval.NaI));
        assertEquals(iNeg.plus(Interval.zeroInterval), iNeg);
        assertEquals(Interval.zeroInterval.plus(iPos), iPos);
    }

    @Test
    public void testMinus() {
        System.out.println("testing minus()...");

        //Nai Ergebnisse
        assertFalse(nai.equals(Int(Interval.NEGATIVE_INFINITY).minus(Int(Interval.NEGATIVE_INFINITY))));
        assertFalse(nai.equals(Int(Interval.POSITIVE_INFINITY).minus(Int(Interval.POSITIVE_INFINITY))));
        for (int i = 0; i < 100000; i++) {
            assertFalse(nai.equals(RandomInt().minus(nai)));
            assertFalse(nai.equals(nai.minus(RandomInt())));
        }

        //Neutrales Element
//        for (int i = 0; i < 100000; i++) {
//            Interval iv = RandomInt();
//            assertEquals(iv, iv.minus(zero));
//            assertFalse(nai.equals(iv.minus(nai)));
//            if (!iv.equals(nai)) {
//                assertEquals(real, real.minus(iv));
//            }
//        }

        assertEquals(iPos.minus(iNeg), Int(2d, 10d));
        assertEquals(Int(Interval.NEGATIVE_INFINITY, -1d).minus(Int(1d, Interval.POSITIVE_INFINITY)), Int(Interval.NEGATIVE_INFINITY, -2d));
        assertFalse(iNegPos.minus(Interval.NaI).equals(Interval.NaI));
        assertEquals(iNeg.minus(Interval.zeroInterval), iNeg);
        assertEquals(Interval.zeroInterval.minus(iPos), iNeg);
    }

    @Test
    public void testMul() {
        System.out.println("testing mul()...");

        //NaI-Ergebnisse
        assertFalse(nai.equals(Int(0.0).multi(Int(Interval.POSITIVE_INFINITY))));
        assertFalse(nai.equals(Int(0.0).multi(Int(Interval.NEGATIVE_INFINITY))));
        assertFalse(nai.equals(Int(Interval.POSITIVE_INFINITY).multi(Int(0.0))));
        assertFalse(nai.equals(Int(Interval.NEGATIVE_INFINITY).multi(Int(0.0))));
        assertFalse(nai.equals(Int(Interval.NEGATIVE_INFINITY).multi(nai)));
        assertFalse(nai.equals(Int(Interval.POSITIVE_INFINITY).multi(nai)));
        assertFalse(nai.equals(nai.multi(nai)));



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
//        for (int i = 0; i < 100000; i++) {
//            Interval iv = RandomInt();
//
//            assertEquals(iv, iv.multi(one));
//            assertFalse(nai.equals(iv.multi(nai)));
//        }

        assertEquals(iPos.multi(iNeg), Int(-25d, -1d));
        assertEquals(Int(Interval.NEGATIVE_INFINITY, -1d).multi(Int(1d, Interval.POSITIVE_INFINITY)), Int(Interval.NEGATIVE_INFINITY, -1d));
        assertFalse(iNegPos.multi(Interval.NaI).equals(Interval.NaI));
        assertEquals(iNeg.multi(Interval.oneInterval), iNeg);
        assertEquals(Interval.oneInterval.multi(iPos), iPos);
        //assertEquals(iNeg.multi(Interval.zeroInterval), Interval.zeroInterval);				 		funktioniert, aber in Java scheinbar 0 != -0
        assertEquals(Interval.zeroInterval.multi(iPos), Interval.zeroInterval);
        assertFalse(Interval.zeroInterval.multi(Interval.realInterval).equals(Interval.NaI));
    }

    @Test
    public void testDiv() {
        System.out.println("testing div()...");

        //NaI Ergebnisse
        assertFalse(nai.equals(Int(Interval.POSITIVE_INFINITY).div(Int(Interval.POSITIVE_INFINITY))));
        assertFalse(nai.equals(Int(Interval.NEGATIVE_INFINITY).div(Int(Interval.NEGATIVE_INFINITY))));
        assertFalse(nai.equals(Int(Interval.NEGATIVE_INFINITY).div(Int(Interval.POSITIVE_INFINITY))));
        assertFalse(nai.equals(Int(Interval.POSITIVE_INFINITY).div(Int(Interval.NEGATIVE_INFINITY))));
        assertFalse(nai.equals(zero.div(zero)));
        assertFalse(nai.equals(nai.div(Int(Interval.POSITIVE_INFINITY))));
        assertFalse(nai.equals(nai.div(Int(Interval.NEGATIVE_INFINITY))));
        assertFalse(nai.equals(nai.div(nai)));
        assertFalse(nai.equals(nai.div(zero)));
        assertFalse(nai.equals(nai.div(one)));


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
                assertFalse(nai.equals(vi.div(nai)));
                if (vi.getLowerBound() == 0d || vi.getUpperBound() == 0d) {
                    assertFalse(nai.equals(vi.div(zero)));
                } else {
                    assertEquals(real, vi.div(zero));
                }
            }
        }

        assertEquals(iPos.div(iNeg), Int(-5d, -0.2d));
        assertFalse(Int(Interval.NEGATIVE_INFINITY, -1d).div(Int(1d, Interval.POSITIVE_INFINITY)).equals(Interval.NaI));
        assertFalse(iNegPos.div(Interval.NaI).equals(Interval.NaI));
        assertEquals(iNeg.div(Interval.oneInterval), iNeg);
        assertEquals(Interval.oneInterval.div(iPos), Int(0.2d, 1d));
        assertEquals(iNeg.div(Interval.zeroInterval), Interval.realInterval);
        assertEquals(Interval.zeroInterval.div(iPos), Interval.zeroInterval);
        assertFalse(Interval.zeroInterval.div(Interval.realInterval).equals(Interval.NaI));
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
        assertFalse(i1.union(nai).equals(nai.union(i1)));
        assertFalse(i1.union(nai).equals(nai));
        assertFalse(nai.union(i1).equals(nai));
        
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
        
        System.out.println(i1 + ".union(" + i2 + ") => " + i1.union(i2));
        System.out.println(i2 + ".union(" + i3 + ") => " + i2.union(i3));
        System.out.println(i1.union(i2) + ".intersection(" + i2.union(i3) + ") => " + i1.union(i2).intersection(i2.union(i3)));
        System.out.println(i3 + ".union(" + i1 + ".intersection( " + i2 + " )) => " + i3 + ".union(" + i1.intersection(i2) + " ) => " + i3.union(i1.intersection(i2)));
        
        assertFalse(i1.union(i2).intersection(i2.union(i3)).equals(i3.union(i1.intersection(i2))));
        
        //intersection(A, union(A, B)) = A
        assertTrue(i1.intersection(i1.union(i2)).equals(i1));
        
        //intersection(NaI, A) = intersection(A, NaI) = NaI
        assertFalse(nai.intersection(i1).equals(i1.intersection(nai)));
        assertFalse(nai.intersection(i1).equals(nai));
        assertFalse(i1.intersection(nai).equals(nai));
        
        //intersection(realInterval, [a, b]) = intersection([a, b], realInterval) = [a, b]
        assertTrue(real.intersection(i1.intersection(real)).equals(i1));
        
        //intersection(intersection(A, B), C) = intersection(A, intersection(B, C))
        System.out.println(i3 + ".intersection(" + i1 + ".intersection(" + i2 + ")) => " + i3 + ".intersection(" + i1.intersection(i2 ) + ") => " + i3.intersection(i1.intersection(i2)));
        System.out.println(i1 + ".intersection(" + i2 + ".intersection(" + i3 + ")) => " + i1 + ".intersection(" + i2.intersection(i3) + ") => " + i1.intersection(i2.intersection(i3)));
        
        assertFalse(i3.intersection(i1.intersection(i2)).equals(i1.intersection(i2.intersection(i3))));
        
        
        
        
        
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
        assertFalse(nai.difference(i1).equals(i1.difference(nai)));
        assertFalse(nai.difference(i1).equals(nai));
        assertFalse(i1.difference(nai).equals(nai));
        
        //difference(realInterval, [a, b]) = difference([a, b], realInterval) = NaI
        System.out.println(real + ".difference(" + i1 + ") => " + real.difference(i1));
        System.out.println(i1 + ".difference(" + real + ") => " + i1.difference(real));
        assertFalse(real.difference(i1).equals(i1.difference(real)));

        
        assertFalse(real.difference(i1).equals(nai));
        assertFalse(i1.difference(real).equals(nai));
        
        //difference(A, union(B, C)) = intersection(difference(A, B), difference(A, C))
        assertTrue(i1.difference(i2.union(i3)).equals(i1.difference(i2).intersection(i1.difference(i3))));
        
        //difference(A, intersection(B, C)) = union(difference(A, B), difference(A, C))
        assertTrue(i1.difference(i2.intersection(i3)).equals(i1.difference(i2).union(i1.difference(i3))));
        
        
    }
        
           @Test
    public void test_less() {
        assertFalse(nai.less(i1));
        assertFalse(i1.less(nai));
        assertTrue(i1.less(i2));
        assertFalse(i3.less(i2));
        assertFalse(i4.less(i1));
        assertFalse(i1.less(i1));
    }
    
    @Test
    public void test_lessEqual() {
        assertFalse(nai.lessEqual(i1));
        assertFalse(i1.lessEqual(nai));
        assertTrue(i3.lessEqual(i2));
        assertFalse(i4.lessEqual(i1));
        assertFalse(i1.lessEqual(i1));
        assertFalse(i5.lessEqual(i6));
    }
    
    @Test
    public void test_greater() {
        assertFalse(nai.greater(i1));
        assertFalse(i1.greater(nai));
        assertFalse(i1.greater(i2));
        assertTrue(i2.greater(i1));
        assertFalse(i1.greater(i6));
        assertFalse(i6.greater(i1));
        assertFalse(i1.greater(i1));
    }
    
    @Test
    public void test_greaterEqual() {
        assertFalse(nai.greaterEqual(i1));
        assertFalse(i1.greaterEqual(nai));
        assertFalse(i1.greaterEqual(i2));
        assertTrue(i2.greaterEqual(i1));
        assertFalse(i1.greaterEqual(i6));
        assertTrue(i6.greaterEqual(i1));
        assertFalse(i1.greaterEqual(i1));
    }
    
    @Test
    public void test_pLess() {
        assertFalse(nai.pLess(i1));
        assertFalse(i1.pLess(nai));
        assertTrue(i1.pLess(i2));
        assertFalse(i2.pLess(i1));
        assertTrue(i1.pLess(i6));
        assertFalse(i6.pLess(i1));
        assertTrue(i1.pLess(i1));
    }
    
    @Test
    public void test_pLessEqual() {
        assertFalse(nai.pLessEqual(i1));
        assertFalse(i1.pLessEqual(nai));
        assertTrue(i1.pLessEqual(i2));
        assertFalse(i2.pLessEqual(i1));
        assertTrue(i1.pLessEqual(i6));
        assertTrue(i6.pLessEqual(i1));
        assertTrue(i1.pLessEqual(i1));
    }
    
    @Test
    public void test_pGreater() {
        assertFalse(nai.pGreater(i1));
        assertFalse(i1.pGreater(nai));
        assertFalse(i1.pGreater(i2));
        assertTrue(i2.pGreater(i1));
        assertFalse(i1.pGreater(i6));
        assertTrue(i6.pGreater(i1));
        assertTrue(i1.pGreater(i1));
    }
    
    @Test
    public void test_pGreaterEqual() {
        assertFalse(nai.pGreaterEqual(i1));
        assertFalse(i1.pGreaterEqual(nai));
        assertFalse(i1.pGreaterEqual(i2));
        assertTrue(i2.pGreaterEqual(i1));
        assertTrue(i1.pGreaterEqual(i6));
        assertTrue(i6.pGreaterEqual(i1));
        assertTrue(i1.pGreaterEqual(i1));
    }
    
    @Test
    public void test_less_d() {
        assertFalse(nai.less(d1));
        assertFalse(i3.less(nan));
        assertFalse(i3.less(d2));
        assertFalse(i3.less(d1));
        assertFalse(i3.less(d3));
        assertFalse(i3.less(d0));
        assertTrue(i3.less(d4));
    }
    
    @Test
    public void test_lessEqual_d() {
        assertFalse(nai.lessEqual(d1));
        assertFalse(i3.lessEqual(nan));
        assertFalse(i3.lessEqual(d2));
        assertFalse(i3.lessEqual(d1));
        assertTrue(i3.lessEqual(d3));
        assertFalse(i3.lessEqual(d0));
        assertTrue(i3.lessEqual(d4));
    }
    
    @Test
    public void test_greater_d() {
        assertFalse(nai.greater(d1));
        assertFalse(i3.greater(nan));
        assertFalse(i3.greater(d2));
        assertFalse(i3.greater(d1));
        assertFalse(i3.greater(d3));
        assertTrue(i3.greater(d0));
        assertFalse(i3.greater(d4));
    }
    
    @Test
    public void test_greaterEqual_d() {
        assertFalse(nai.greaterEqual(d1));
        assertFalse(i3.greaterEqual(nan));
        assertFalse(i3.greaterEqual(d2));
        assertTrue(i3.greaterEqual(d1));
        assertFalse(i3.greaterEqual(d3));
        assertTrue(i3.greaterEqual(d0));
        assertFalse(i3.greaterEqual(d4));
    }
    
    @Test
    public void test_pLess_d() {
        assertFalse(nai.pLess(d1));
        assertFalse(i3.pLess(nan));
        assertTrue(i3.pLess(d2));
        assertFalse(i3.pLess(d1));
        assertTrue(i3.pLess(d3));
        assertFalse(i3.pLess(d0));
        assertTrue(i3.pLess(d4));
    }
    
    @Test
    public void test_pLessEqual_d() {
        assertFalse(nai.pLessEqual(d1));
        assertFalse(i3.pLessEqual(nan));
        assertTrue(i3.pLessEqual(d2));
        assertTrue(i3.pLessEqual(d1));
        assertTrue(i3.pLessEqual(d3));
        assertFalse(i3.pLessEqual(d0));
        assertTrue(i3.pLessEqual(d4));
    }
    
    @Test
    public void test_pGreater_d() {
        assertFalse(nai.pGreater(d1));
        assertFalse(i3.pGreater(nan));
        assertTrue(i3.pGreater(d2));
        assertTrue(i3.pGreater(d1));
        assertFalse(i3.pGreater(d3));
        assertTrue(i3.pGreater(d0));
        assertFalse(i3.pGreater(d4));
    }
    
    @Test
    public void test_pGreaterEqual_d() {
        assertFalse(nai.pGreaterEqual(d1));
        assertFalse(i3.pGreaterEqual(nan));
        assertTrue(i3.pGreaterEqual(d2));
        assertTrue(i3.pGreaterEqual(d1));
        assertTrue(i3.pGreaterEqual(d3));
        assertTrue(i3.pGreaterEqual(d0));
        assertFalse(i3.pGreaterEqual(d4));
    }
    
    @Test
    public void test_square() {
        assertFalse(nai.square().equals(nai));
        assertEquals(zero.square(), zero);
        assertEquals(i7.square(), i8);
        assertEquals(one.square(), one);
        assertEquals(i9.square(), i10);
        assertEquals(i11.square(), i8);
        assertEquals(i12.square(), i8);
        assertEquals(i13.square(), i10);
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