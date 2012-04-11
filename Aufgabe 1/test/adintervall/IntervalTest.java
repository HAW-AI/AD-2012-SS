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
        System.out.println("#-- Testing Bounds --#");
        assertFalse(Interval.NaI.getLowerBound() == Interval.NaN);
        assertFalse(Interval.NaI.getUpperBound() == Interval.NaN);
        assertTrue(iNegPos.getLowerBound() == -5d);
        assertTrue(iNegPos.getUpperBound() == 5d);
        
        System.out.println(iNegPos + ".getLowerBound() => " + iNegPos.getLowerBound() + " == -5.0");
        System.out.println(iNegPos + ".getUpperBound() => " + iNegPos.getUpperBound() + " == 5.0\n");
    }

    @Test
    public void testFactory() {
        System.out.println("#-- Testing Factory --#");
        //NaI Intervalle
        assertFalse(nai.equals(Int(NormalInterval.NaN)));
        assertFalse(nai.equals(Int(Interval.NaN)));
        assertFalse(nai.equals(Int(Interval.NaN, Interval.NaN)));
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
        
        System.out.println("FactoryInterval.createInterval(NaN) => " + Int(Interval.NaN));
        System.out.println("FactoryInterval.createInterval(1d, 7d) => " + Int(1d, 7d));
        System.out.println("FactoryInterval.createInterval(-inf, +inf) => " + Int(Interval.NEGATIVE_INFINITY, Interval.POSITIVE_INFINITY) + "\n");
    }

    @Test
    public void testContains() {
        System.out.println("#-- Testing Contains --#");
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

        System.out.println(i1 + ".contains(" + i1 + ") => " + i1.contains(i1));
        System.out.println(i1 + ".contains(" + c + ") => " + i1.contains(c));
        System.out.println(real + ".contains(" + a + ") => " + real.contains(a) + "\n");

    }

    @Test
    public void testEquals() {
        System.out.println("#-- Testing Equals --#");
        assertFalse(nai.equals(nai));

        assertEquals(one, one);
        assertEquals(zero, zero);
        assertEquals(real, real);
        for (int c = 0; c < 100; ++c) {
            Interval iv = RandomDouble();
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
        
        System.out.println(iPos + ".equals(" + iNeg + ") => " + iPos.equals(iNeg));
        System.out.println(Interval.NaI + ".equals(" + Interval.NaI + ") => " + Interval.NaI.equals(Interval.NaI));
        System.out.println(iNew + ".equals(" + iNew2 + ") => " + iNew.equals(iNew2) + "\n");
    }

    @Test
    public void testLength() {
        System.out.println("#-- Testing Length --#");
        
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
        
        System.out.println(Int(5d) + ".length() => " + Int(5).length());
        System.out.println(iPos + ".length() => " + iPos.length() + "\n");
    }

    @Test
    public void testPlus() {
        System.out.println("#-- Testing Plus --#");
        
        //NaI-ergebnisse
        assertFalse(nai.equals(Int(Interval.NEGATIVE_INFINITY, 0d).plus(Int(0d, Interval.POSITIVE_INFINITY))));
        for (int i = 0; i < 100000; ++i) {
            assertFalse(nai.equals(RandomDouble().plus(nai)));
            assertFalse(nai.equals(nai.plus(RandomDouble())));
        }

        //Neutrales element
        for (int i = 0; i < 100000; ++i) {
            Interval iv = RandomDouble();
            assertEquals(iv, iv.plus(Interval.zeroInterval));
            assertFalse(nai.equals(iv.plus(nai)));
            if (!iv.equals(nai)) {
             assertEquals(real, iv.plus(real));
            }
        }

        assertEquals(iPos.plus(iNeg), Int(-4d, 4d));
        assertFalse(Int(Interval.NEGATIVE_INFINITY, -1d).plus(Int(1d, Interval.POSITIVE_INFINITY)).equals(Interval.NaI));
        assertFalse(iNegPos.plus(Interval.NaI).equals(Interval.NaI));
        assertEquals(iNeg.plus(Interval.zeroInterval), iNeg);
        assertEquals(Interval.zeroInterval.plus(iPos), iPos);
        
        System.out.println(iPos + ".plus(" + iNeg + ") => " + iPos.plus(iNeg));
        System.out.println("Interval.zeroInterval.plus(" + iPos + ") => "+ Interval.zeroInterval.plus(iPos)+ "\n");
    }

    @Test
    public void testMinus() {
        System.out.println("#-- Testing Minus --#");

        //Nai Ergebnisse
        assertFalse(nai.equals(Int(Interval.NEGATIVE_INFINITY).minus(Int(Interval.NEGATIVE_INFINITY))));
        assertFalse(nai.equals(Int(Interval.POSITIVE_INFINITY).minus(Int(Interval.POSITIVE_INFINITY))));
        for (int i = 0; i < 100000; i++) {
            assertFalse(nai.equals(RandomDouble().minus(nai)) );
            assertFalse(nai.equals(nai.minus(RandomDouble())));
        }

        //Neutrales Element
        for (int i = 0; i < 100000; i++) {
            Interval iv = RandomDouble();
             assertEquals(iv, iv.minus(zero));
            assertFalse(nai.equals(iv.minus(nai)));
            if (!iv.equals(nai)) {
            assertEquals(real, real.minus(iv));
            }
        }

        assertEquals(iPos.minus(iNeg), Int(2d, 10d));
        assertEquals(Int(Interval.NEGATIVE_INFINITY, -1d).minus(Int(1d, Interval.POSITIVE_INFINITY)), Int(Interval.NEGATIVE_INFINITY, -2d));
        assertFalse(iNegPos.minus(Interval.NaI).equals(Interval.NaI));
        assertEquals(iNeg.minus(Interval.zeroInterval), iNeg);
        assertEquals(Interval.zeroInterval.minus(iPos), iNeg);
        
        System.out.println(iPos + ".minus(" + iNeg + ") => " + iPos.minus(iNeg));
        System.out.println("Interval.zeroInterval.minus(" + iPos + ") => " + Interval.zeroInterval.minus(iPos) + "\n");
    }

    @Test
    public void testMul() {
        System.out.println("#-- Testing Multi --#");

        //NaI-Ergebnisse
        assertFalse(nai.equals(Int(0.0).multi(Int(Interval.POSITIVE_INFINITY))));
        assertFalse(nai.equals(Int(0.0).multi(Int(Interval.NEGATIVE_INFINITY))));
        assertFalse(nai.equals(Int(Interval.POSITIVE_INFINITY).multi(Int(0.0))));
        assertFalse(nai.equals(Int(Interval.NEGATIVE_INFINITY).multi(Int(0.0))));
        assertFalse(nai.equals( Int(Interval.NEGATIVE_INFINITY).multi(nai)));
        assertFalse(nai.equals(Int(Interval.POSITIVE_INFINITY).multi(nai)));
        assertFalse(nai.equals( nai.multi(nai)));



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
            Interval iv = RandomDouble();

            assertEquals(iv, iv.multi(one));
            assertFalse(nai.equals( iv.multi(nai)));
        }

        assertEquals(iPos.multi(iNeg), Int(-25d, -1d));
        assertEquals(Int(Interval.NEGATIVE_INFINITY, -1d).multi(Int(1d, Interval.POSITIVE_INFINITY)), Int(Interval.NEGATIVE_INFINITY, -1d));
        assertFalse(iNegPos.multi(Interval.NaI).equals(Interval.NaI));
        assertEquals(iNeg.multi(Interval.oneInterval), iNeg);
        assertEquals(Interval.oneInterval.multi(iPos), iPos);
        //assertEquals(iNeg.multi(Interval.zeroInterval), Interval.zeroInterval);				 		funktioniert, aber in Java scheinbar 0 != -0
        assertEquals(Interval.zeroInterval.multi(iPos), Interval.zeroInterval);
        assertFalse(Interval.zeroInterval.multi(Interval.realInterval).equals( Interval.NaI));
     
        
        System.out.println(real + ".multi(" + real + ") => " + real.multi(real));
        System.out.println(iPos + ".multi(" + iNeg + ") => " + iPos.multi(iNeg));
        System.out.println(iNeg + ".multi(Interval.oneInterval) => " + iNeg.multi(Interval.oneInterval) + "\n");
    }

    @Test
    public void testDiv() {
        System.out.println("#-- Testing Div --#");

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
            Interval vi = RandomDouble();
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

        assertFalse(iNegPos.div(Interval.NaI).equals(Interval.NaI));
        assertEquals(iNeg.div(Interval.oneInterval), iNeg);
        assertEquals(Interval.oneInterval.div(iPos), Int(0.2d, 1d));
        assertEquals(iNeg.div(Interval.zeroInterval), Interval.realInterval);
        assertEquals(Interval.zeroInterval.div(iPos), Interval.zeroInterval);
        assertFalse(Interval.zeroInterval.div(Interval.realInterval).equals(Interval.NaI));
        
        System.out.println(iNeg + ".div(Interval.oneInterval) => " + iNeg.div(Interval.oneInterval));
        System.out.println(iPos + ".div(" + iNeg + ") => " + iPos.div(iNeg));
        System.out.println(nai + ".div(" + nai + ") => " + nai.div(nai) + "\n");
    }

    private Interval Int(double a, double b) {
        return FactoryInterval.createInterval(a, b);
    }

    private Interval Int(double a) {
        return Int(a, a);
    }

    private Interval RandomDouble() {
        return FactoryInterval.createInterval((double) rand.nextDouble() - 5000, (double) rand.nextDouble());
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
        System.out.println("#-- Testing NaI != NaI --#");
        
        assertFalse(nai.equals(nai));
        
        System.out.println(nai + ".equals(" + nai + ") => " + nai.equals(nai) + "\n");
    }
    

    @Test
    public void testKomm() {
        System.out.println("#-- Testing Kommutativität --#");
        
        //Test der KommutativitÃ¤t
        double a = 1d;
        double b = 2d;
        double c = 3d;
        double d = 4d;
        Interval i1 = FactoryInterval.createInterval(a, b);
        Interval i2 = FactoryInterval.createInterval(b, c);
        Interval i3 = FactoryInterval.createInterval(c, d);


        assertTrue(i1.plus(b).equals(i1.plusKom(b)));
        assertTrue(i1.multi(b).equals(i1.multiKom(b)));
        
        System.out.println(i1 + ".plus(" + b + ") => " + i1.plus(b) + " == " + i1.plusKom(b) + " <= " + i1 + ".plusKom(" + b + ")");
        System.out.println(i1 + ".multi(" + b + ") => " + i1.multi(b) + " == " + i1.multiKom(b) + " <= " + i1 + ".multiKom(" + b + ")\n");
    }

    @Test
    public void testUnion() {
        System.out.println("#-- Testing Union --#");
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
        
        System.out.println(i1 + ".union(" + i2 + ") => " + i1.union(i2));
        System.out.println(i2 + ".union(" + i3 + ") => " + i2.union(i3) + "\n");
    }

    @Test
    public void testIntersection() {
        System.out.println("#-- Testing Intersection --#");
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
         assertFalse(i1.union(i3).intersection(i2.union(i3)).equals(i3.union(i1.intersection(i2))));

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
        System.out.println(i1 + ".intersection(" + i2 + ".intersection(" + i3 + ")) => " + i1 + ".intersection(" + i2.intersection(i3) + ") => " + i1.intersection(i2.intersection(i3)) + "\n");
        
       assertFalse(i3.intersection(i1.intersection(i2)).equals(i1.intersection(i2.intersection(i3))));

        //intersection(A, B) = intersection(B, A)
        assertTrue(i1.intersection(i2).equals(i2.intersection(i1)));

        //intersection(A, A) = A
        assertTrue(i1.intersection(i1).equals(i1));


    }

    @Test
    public void testDifference() {
        System.out.println("#-- Testing Difference --#");
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
        assertFalse(real.difference(i1).equals(i1.difference(real)));
        assertFalse(real.difference(i1).equals(nai));
        assertFalse(i1.difference(real).equals(nai));

        //difference(A, union(B, C)) = intersection(difference(A, B), difference(A, C))
        assertTrue(i1.difference(i2.union(i3)).equals(i1.difference(i2).intersection(i1.difference(i3))));

        //difference(A, intersection(B, C)) = union(difference(A, B), difference(A, C))
        assertTrue(i1.difference(i2.intersection(i3)).equals(i1.difference(i2).union(i1.difference(i3))));

        System.out.println(i1 + ".difference(" + i2.intersection(i3) + ") => " + i1.difference(i2.intersection(i3)));
        System.out.println(Int(1d, 5d) + ".difference(" + Int(3d, 5d) + ") => " + Int(1d, 5d).difference(Int(3d, 5d)));
        
        System.out.println(real + ".difference(" + i1 + ") => " + real.difference(i1));
        System.out.println(i1 + ".difference(" + real + ") => " + i1.difference(real) + "\n");
    }

           @Test
    public void test_less() {
        System.out.println("#-- Testing Less --#");
        assertFalse(nai.less(i1));
        assertFalse(i1.less(nai));
        assertTrue(i1.less(i2));
        assertFalse(i3.less(i2));
        assertFalse(i4.less(i1));
        assertFalse(i1.less(i1));
        
        System.out.println(i1 + ".less(" + i2 + ") => " + i1.less(i2));
        System.out.println(i3 + ".less(" + i2 + ") => " + i3.less(i2) + "\n");
       
}
    
    @Test
    public void test_lessEqual() {
        System.out.println("#-- Testing LessEqual --#");
        assertFalse(nai.lessEqual(i1));
        assertFalse(i1.lessEqual(nai));
        assertTrue(i3.lessEqual(i2));
        assertFalse(i4.lessEqual(i1));
        assertFalse(i1.lessEqual(i1));
        assertFalse(i5.lessEqual(i6));
        
        System.out.println(i1 + ".lessEqual(" + nai + ") => " + i1.less(nai));
        System.out.println(i3 + ".lessEqual(" + i2 + ") => " + i3.lessEqual(i2) + "\n");

    }
    
    @Test
    public void test_greater() {
        System.out.println("#-- Testing Greater --#");
        
        assertFalse(nai.greater(i1));
        assertFalse(i1.greater(nai));
        assertFalse(i1.greater(i2));
        assertTrue(i2.greater(i1));
        assertFalse(i1.greater(i6));
        assertFalse(i6.greater(i1));
        assertFalse(i1.greater(i1));
        
        System.out.println(i2 + ".greater(" + i1 + ") => " + i2.greater(i1));
        System.out.println(i1 + ".greater(" + i6 + ") => " + i1.greater(i6) + "\n");
    }
    
    @Test
    public void test_greaterEqual() {
        System.out.println("#-- Testing GreaterEqual --#");
        
        assertFalse(nai.greaterEqual(i1));
        assertFalse(i1.greaterEqual(nai));
        assertFalse(i1.greaterEqual(i2));
        assertTrue(i2.greaterEqual(i1));
        assertFalse(i1.greaterEqual(i6));
        assertTrue(i6.greaterEqual(i1));
        assertFalse(i1.greaterEqual(i1));
        
        System.out.println(i6 + ".greaterEqual(" + i1 + ") => " + i6.greaterEqual(i1));
        System.out.println(i1 + ".greaterEqual(" + i2 + ") => " + i1.greaterEqual(i2) + "\n");
        
    }
    
    @Test
    public void test_pLess() {
        System.out.println("#-- Testing ProbablyLess --#");
        
        assertFalse(nai.pLess(i1));
        assertFalse(i1.pLess(nai));
        assertTrue(i1.pLess(i2));
        assertFalse(i2.pLess(i1));
        assertTrue(i1.pLess(i6));
        assertFalse(i6.pLess(i1));
        assertTrue(i1.pLess(i1));
        
        System.out.println(i6 + ".pLess(" + i1 + ") => " + i6.pLess(i1));
        System.out.println(i1 + ".pLess(" + i6 + ") => " + i1.pLess(i6));
        System.out.println(i1 + ".pLess(" + i2 + ") => " + i1.pLess(i2) + "\n");
    }
    
    @Test
    public void test_pLessEqual() {
        System.out.println("#-- Testing ProbablyLessEqual --#");
        
        assertFalse(nai.pLessEqual(i1));
        assertFalse(i1.pLessEqual(nai));
        assertTrue(i1.pLessEqual(i2));
        assertFalse(i2.pLessEqual(i1));
        assertTrue(i1.pLessEqual(i6));
        assertTrue(i6.pLessEqual(i1));
        assertTrue(i1.pLessEqual(i1));
        
        System.out.println(i6 + ".pLessEqual(" + i1 + ") => " + i6.pLessEqual(i1));
        System.out.println(i1 + ".pLessEqual(" + nai + ") => " + i1.pLessEqual(nai));
        System.out.println(i1 + ".pLessEqual(" + i2 + ") => " + i1.pLessEqual(i2) + "\n");
    }
    
    @Test
    public void test_pGreater() {
        System.out.println("#-- Testing ProbablyGreater --#");
        
        assertFalse(nai.pGreater(i1));
        assertFalse(i1.pGreater(nai));
        assertFalse(i1.pGreater(i2));
        assertTrue(i2.pGreater(i1));
        assertFalse(i1.pGreater(i6));
        assertTrue(i6.pGreater(i1));
        assertTrue(i1.pGreater(i1));

        System.out.println(i2 + ".pGreater(" + i1 + ") => " + i2.pGreater(i1));
        System.out.println(i6 + ".pGreater(" + i1 + ") => " + i6.pGreater(i1));
        System.out.println(i1 + ".pGreater(" + i2 + ") => " + i1.pGreater(i2) + "\n");
    
    }
    
    @Test
    public void test_pGreaterEqual() {
        System.out.println("#-- Testing ProbablyGreaterEqual --#");
        
        assertFalse(nai.pGreaterEqual(i1));
        assertFalse(i1.pGreaterEqual(nai));
        assertFalse(i1.pGreaterEqual(i2));
        assertTrue(i2.pGreaterEqual(i1));
        assertTrue(i1.pGreaterEqual(i6));
        assertTrue(i6.pGreaterEqual(i1));
        assertTrue(i1.pGreaterEqual(i1));
        
        System.out.println(i2 + ".pGreaterEqual(" + i1 + ") => " + i2.pGreaterEqual(i1));
        System.out.println(i6 + ".pGreaterEqual(" + i1 + ") => " + i6.pGreaterEqual(i1));
        System.out.println(i1 + ".pGreaterEqual(" + i2 + ") => " + i1.pGreaterEqual(i2) + "\n");
    }
    
    @Test
    public void test_less_d() {
        System.out.println("#-- Testing Less double --#");
        
        assertFalse(nai.less(d1));
        assertFalse(i3.less(nan));
        assertFalse(i3.less(d2));
        assertFalse(i3.less(d1));
        assertFalse(i3.less(d3));
        assertFalse(i3.less(d0));
        assertTrue(i3.less(d4));
        
        System.out.println(i3 + ".less(" + d4 + ") => " + i3.less(d4));
        System.out.println(i3 + ".less(" + d1 + ") => " + i3.less(d1) + "\n");
    }
    
    @Test
    public void test_lessEqual_d() {
        System.out.println("#-- Testing LessEqual double --#");
        
        assertFalse(nai.lessEqual(d1));
        assertFalse(i3.lessEqual(nan));
        assertFalse(i3.lessEqual(d2));
        assertFalse(i3.lessEqual(d1));
        assertTrue(i3.lessEqual(d3));
        assertFalse(i3.lessEqual(d0));
        assertTrue(i3.lessEqual(d4));
        
        System.out.println(i3 + ".lessEqual(" + d3 + ") => " + i3.lessEqual(d3));
        System.out.println(i3 + ".lessEqual(" + d1 + ") => " + i3.lessEqual(d1) + "\n");
    }
    
    @Test
    public void test_greater_d() {
        System.out.println("#-- Testing Greater double --#");
        
        assertFalse(nai.greater(d1));
        assertFalse(i3.greater(nan));
        assertFalse(i3.greater(d2));
        assertFalse(i3.greater(d1));
        assertFalse(i3.greater(d3));
        assertTrue(i3.greater(d0));
        assertFalse(i3.greater(d4));
        
        System.out.println(i3 + ".greater(" + d0 + ") => " + i3.greater(d0));
        System.out.println(i3 + ".greater(" + d4 + ") => " + i3.greater(d4) + "\n");
    }
    
    @Test
    public void test_greaterEqual_d() {
        System.out.println("#-- Testing GreaterEqual double --#");
        
        assertFalse(nai.greaterEqual(d1));
        assertFalse(i3.greaterEqual(nan));
        assertFalse(i3.greaterEqual(d2));
        assertTrue(i3.greaterEqual(d1));
        assertFalse(i3.greaterEqual(d3));
        assertTrue(i3.greaterEqual(d0));
        assertFalse(i3.greaterEqual(d4));
        
        System.out.println(i3 + ".greaterEqual(" + d1 + ") => " + i3.greaterEqual(d1));
        System.out.println(i3 + ".greaterEqual(" + d4 + ") => " + i3.greaterEqual(d4) + "\n");
    }
    
    @Test
    public void test_pLess_d() {
        System.out.println("#-- Testing ProbablyLess double --#");
        
        assertFalse(nai.pLess(d1));
        assertFalse(i3.pLess(nan));
        assertTrue(i3.pLess(d2));
        assertFalse(i3.pLess(d1));
        assertTrue(i3.pLess(d3));
        assertFalse(i3.pLess(d0));
        assertTrue(i3.pLess(d4));
        
        System.out.println(i3 + ".pLess(" + d3 + ") => " + i3.pLess(d3));
        System.out.println(i3 + ".pLess(" + d0 + ") => " + i3.pLess(d0) + "\n");
    }
    
    @Test
    public void test_pLessEqual_d() {
        System.out.println("#-- Testing ProbablyLessEqual double --#");
        
        assertFalse(nai.pLessEqual(d1));
        assertFalse(i3.pLessEqual(nan));
        assertTrue(i3.pLessEqual(d2));
        assertTrue(i3.pLessEqual(d1));
        assertTrue(i3.pLessEqual(d3));
        assertFalse(i3.pLessEqual(d0));
        assertTrue(i3.pLessEqual(d4));
        
        System.out.println(i3 + ".pLessEqual(" + d2 + ") => " + i3.pLessEqual(d2));
        System.out.println(i3 + ".pLessEqual(" + d0 + ") => " + i3.pLessEqual(d0) + "\n");
    }
    
    @Test
    public void test_pGreater_d() {
        System.out.println("#-- Testing ProbablyGreater double --#");
        
        assertFalse(nai.pGreater(d1));
        assertFalse(i3.pGreater(nan));
        assertTrue(i3.pGreater(d2));
        assertTrue(i3.pGreater(d1));
        assertFalse(i3.pGreater(d3));
        assertTrue(i3.pGreater(d0));
        assertFalse(i3.pGreater(d4));
        
        System.out.println(i3 + ".pGreater(" + d2 + ") => " + i3.pGreater(d2));
        System.out.println(i3 + ".pGreater(" + d4 + ") => " + i3.pGreater(d4) + "\n");
    }
    
    @Test
    public void test_pGreaterEqual_d() {
        System.out.println("#-- Testing ProbablyGreaterEqual double --#");
        
        assertFalse(nai.pGreaterEqual(d1));
        assertFalse(i3.pGreaterEqual(nan));
        assertTrue(i3.pGreaterEqual(d2));
        assertTrue(i3.pGreaterEqual(d1));
        assertTrue(i3.pGreaterEqual(d3));
        assertTrue(i3.pGreaterEqual(d0));
        assertFalse(i3.pGreaterEqual(d4));
        
        System.out.println(i3 + ".pGreaterEqual(" + d1 + ") => " + i3.pGreaterEqual(d1));
        System.out.println(i3 + ".pGreaterEqual(" + d4 + ") => " + i3.pGreaterEqual(d4) + "\n");
    }
    
    @Test
    public void test_square() {
        System.out.println("#-- Testing Square --#");
        
        assertFalse(nai.square().equals(nai));
        assertEquals(zero.square(), zero);
        assertEquals(i7.square(), i8);
        assertEquals(one.square(), one);
        assertEquals(i9.square(), i10);
        assertEquals(i11.square(), i8);
        assertEquals(i12.square(), i8);
        assertEquals(i13.square(), i10);
        
        System.out.println(one + ".square() => " + one.square());
        System.out.println(i9 + ".square() => " + i9.square());
        System.out.println(i7 + ".square() => " + i7.square());
        System.out.println(zero + ".square() => " + zero.square() + "\n");
    }

    
}