/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package squarewins;

import java.util.ArrayList;

/**
 *
 * @author kbwschuler
 */
public class SquareWins {

    //is the expected result of the addition of 2 vecs
    private final int RESVECT = 0;
    //player true is player 1 and false is player 2
    private boolean player = true;
    //this is the 5*5 field
    private static int[][] field;
    //set points
    private Point points[][], commonPoint;
    //ArrayList of the setted vectors
    private ArrayList<Vector> vectorsOfPlayerBlue = new ArrayList<>();
    private ArrayList<Vector> vectorsOfPlayerRed = new ArrayList<>();
    //length of the first vector
    private double aLength;
    //length of the second vector
    private double bLength;
    //expected vectors to finish a square
    private Vector squareVectorA, squareVectorB, expectedVectorA, expectedVectorB;

    public SquareWins() {
        squareVectorA = null;
        squareVectorB = null;
        expectedVectorA = null;
        expectedVectorB = null;
    }

    //create a field
    public void createField() {
        field = new int[5][5];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                points[i][j] = new Point(i, j);
            }
        }
    }

    //implementation for adding a vector of a field
    public void addVector(Point a, Point b) {
        vectorsOfPlayerBlue.add(new Vector(a, b));
    }

    //nur mit zwei Vektoren 
    public boolean checkIfEqualLength(Vector a, Vector b) {
        aLength = Math.pow(a.getxVec(), 2.0) + Math.pow(a.getyVec(), 2.0);
        bLength = Math.pow(b.getxVec(), 2.0) + Math.pow(b.getyVec(), 2.0);
        //printOutVectorXAndY(a, b);
        if (aLength == bLength) {
            //System.out.println("Gleich lang.");
            return true;
        } else {
            //System.out.println("Nicht gleich lang.");
            return false;
        }
    }

    public void buildSquare() {
        int anz = 0;
        for (int i = 0; i < vectorsOfPlayerBlue.size(); i++) {
            for (int j = 1; j < vectorsOfPlayerBlue.size(); j++) {
                if (i != j) {
                    if (checkAll(vectorsOfPlayerBlue.get(i), vectorsOfPlayerBlue.get(j))) {
                        anz++;
                        System.out.println("build " + anz);
                        System.out.println("Vec 1:");
                        printOutPointXAndY(vectorsOfPlayerBlue.get(i).getPointA(), vectorsOfPlayerBlue.get(i).getPointB());
                        System.out.println("Vec 2:");
                        printOutPointXAndY(vectorsOfPlayerBlue.get(j).getPointA(), vectorsOfPlayerBlue.get(j).getPointB());

                        Point[] points = new Point[4];
                        points[0] = vectorsOfPlayerBlue.get(i).getPointA();
                        points[1] = vectorsOfPlayerBlue.get(i).getPointB();
                        points[2] = vectorsOfPlayerBlue.get(j).getPointA();
                        points[3] = vectorsOfPlayerBlue.get(j).getPointB();

                        //setCommonPoint
                        this.commonPoint = setCommonPoint(points);

                        //Set the quare vectors
                        setSquareVectors(vectorsOfPlayerBlue.get(i), vectorsOfPlayerBlue.get(j));
                        buildExpectedVectors();
                        if (getCornerpoint() != null) {
                            System.out.println("Diagonal-Coordination");
                            System.out.println(getCornerpoint().getX() + "/" + getCornerpoint().getY());
                        }

                        //sort the Arrays
                        setVectorsOfPlayerBlue(convertArrayList(getVectorsOfPlayerBlue()));
                        setVectorsOfPlayerRed(convertArrayList(getVectorsOfPlayerRed()));
                        //print result
                        if (getSquareVectorA() != null && getSquareVectorB() != null && getExpectedVectorA() != null && getExpectedVectorB() != null) {
                            System.out.println("Expected vectors");
                            printPointsOfVectors(expectedVectorA, expectedVectorB);
                        }
                        if (expectedVectorExists()) {
                            expectedVectorA = null;
                            expectedVectorB = null;
                            System.out.println("");
                            System.out.println("Win!");
                            System.out.println("");
                            System.out.println("");
                            System.out.println("");
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }

    //Addition of the 2 Vectors
    private Point getCornerpoint() {
        Point point = null;
        if (getSquareVectorA() != null || getSquareVectorB() != null || getExpectedVectorA() != null || getExpectedVectorB() != null) {

            point = new Point(3, 4);
        }
        return point;
    }

    //It's going to compare the points of the quarevectors and set the common vector
    public Point setCommonPoint(Point[] points) {
        for (int a = 0; a < points.length; a++) {
            for (int b = 1; b < points.length; b++) {
                if (a != b) {
                    if (checkIfPointsAreEquals(points[a], points[b])) {
                        System.out.println("(common Hinzugefügt " + points[b].getX() + "/" + points[b].getY() + ")");
                        return points[b];
                    }
                }
            }
        }
        return null;

    }

    //set the 2 vectors new to calculate the expected vectors later
    public void setSquareVectors(Vector v1, Vector v2) {
        
        if (getCommonPoint().equals(v1.getPointA())) {
            this.squareVectorA = new Vector(getCommonPoint(), v1.getPointA());
        } else {
            this.squareVectorA = new Vector(getCommonPoint(), v1.getPointB());
        }
        if (getCommonPoint().equals(v2.getPointA())) {
            this.squareVectorB = new Vector(getCommonPoint(), v2.getPointA());
        } else {
            this.squareVectorB = new Vector(getCommonPoint(), v2.getPointB());
        }
    }

    /*
        Wieso nid boolean?
        public boolean expectedVectorExists() {
        int i = 0;
        boolean expected = false;
        for (Vector v : vectors) {
            if (checkIfPointsOfVectorsAreEquals(v, expectedVectorB) || checkIfPointsOfVectorsAreEquals(v, expectedVectorA)) 
            {
                expected = true;
                break;
            }
        }
        if (expected) 
        {
            return true;
        }
        return false;
    }
     */
    //It checks the calculated expected vector if they exist,when yes then win!!!
    public boolean expectedVectorExists() {
        int i = 0;
        printOutAllVectors(vectorsOfPlayerBlue);
        if (getExpectedVectorA() != null && getExpectedVectorB() != null) {
            for (Vector v : vectorsOfPlayerBlue) {
                if (checkIfPointsOfVectorsAreEquals(v, expectedVectorA)) {
                    System.out.println("1");
                    i++;
                }
            }
            for (Vector v : vectorsOfPlayerBlue) {
                if (checkIfPointsOfVectorsAreEquals(v, expectedVectorB)) {
                    System.out.println("2");
                    i++;
                }
            }
        }
        if (i == 2) {
            return true;
        }
        return false;
    }

    //Sort and build --> TODO
    private void buildExpectedVectors() {
        if (getSquareVectorA() != null || getSquareVectorB() != null || getExpectedVectorA() != null || getExpectedVectorB() != null) {
            //if Commonpoint == Point A of Vec A then using Point B
            if (getCommonPoint().equals(getSquareVectorA().getPointA())) {
                this.expectedVectorA = new Vector(getSquareVectorA().getPointB(), getCornerpoint());
            } else {
                //else using Point A
                this.expectedVectorA = new Vector(getSquareVectorA().getPointA(), getCornerpoint());
            }
             //if Commonpoint == Point A of Vec B then using Point B
            if (getCommonPoint().equals(getSquareVectorA().getPointA())) {
                this.expectedVectorB = new Vector(getSquareVectorB().getPointA(), getCornerpoint());
            } else {
                //else using Point A
                this.expectedVectorB = new Vector(getSquareVectorB().getPointB(), getCornerpoint());
            }
        }
    }

    public boolean checkIfPointsOfVectorsAreEquals(Vector vecA, Vector vecB) {
//        System.out.println("Vec A:");
//        printOutPointXAndY(vecA.getA(), vecA.getB());
//        System.out.println("Vec B:");
//        printOutPointXAndY(vecB.getA(), vecB.getB());
        Point pointAofVec1 = vecA.getPointA();
        Point pointBofVec1 = vecA.getPointB();
        Point pointAofVec2 = vecB.getPointA();
        Point pointBofVec2 = vecB.getPointB();
        if (checkIfPointsAreEquals(pointAofVec1, pointBofVec1) && checkIfPointsAreEquals(pointAofVec2, pointBofVec2)) {
            return true;
        }
        //System.out.println("Punkte sind nicht gleich.");
        return false;
    }

    /*
    *   it checks if p1 equals p2
     */
    public boolean checkCoordinates(Point p1, Point p2) {
        if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
            return true;
        }
        return false;
    }

    public boolean checkAll(Vector a, Vector b) {
        if (checkIfEqualLength(a, b) && checkIf90Degree(a, b) && checkIfPointsOfVectorsHasEquals(a, b)) {
            return true;
        }
        return false;
    }

    public boolean checkIf90Degree(Vector a, Vector b) {
        if ((a.getxVec() * b.getxVec()) + (a.getyVec() * b.getyVec()) == RESVECT) {
            //System.out.println("Rechtwinklig!");
            return true;
        } else {
            //System.out.println("Nicht rechtwinklig!");
            return false;
        }
    }

    public Vector getDiagonale(Point a, Point b) {
        return null;
    }

    public boolean checkAllSitesIfEqualLength(ArrayList<Vector> vectors) {
        Vector temp;
        for (int i = 0; i < vectors.size(); i++) {
            for (int j = 1; j < vectors.size(); j++) {
                temp = vectors.get(i);
                if (temp != vectors.get(j)) {
                    aLength = Math.pow(temp.getxVec(), 2.0) + Math.pow(temp.getyVec(), 2.0);
                    bLength = Math.pow(vectors.get(j).getxVec(), 2.0) + Math.pow(vectors.get(j).getyVec(), 2.0);
                    if (aLength == bLength) {
                        System.out.println("Gleich lang.");
                        return true;
                    }

                }
            }

        }
        return false;

    }

    public void testCheckAllVectors(ArrayList<Vector> vectors) {
        for (int i = 0; i < vectors.size(); i++) {
            for (int j = 1; j < vectors.size(); j++) {
                if (i != j) {
                    checkIfEqualLength(vectors.get(i), vectors.get(j));
                    checkIf90Degree(vectors.get(i), vectors.get(j));
                    checkIfPointsOfVectorsHasEquals(vectors.get(i), vectors.get(j));
                }
            }
        }
        printOutAllVectors(vectors);
    }

    public void printOutAllVectors(ArrayList<Vector> vectors) {
        int count = 1;
        System.out.println("----------------------Alle Vektoren---------------------: ");
        for (Vector v : vectors) {

            System.out.println("Vektor " + count + ": " + (v.getxVec() < 0 ? "(" + v.getxVec() + ")" : v.getxVec()) + " | " + v.getyVec());
            printOutPointXAndY(v.getPointA(), v.getPointB());
            count++;
        }
    }

    public boolean checkIfPointsAreEquals(Point a, Point b) {
        if (a.getX() == b.getX() && a.getY() == b.getY()) {
            return true;
        }
        return false;
    }

    public boolean checkIfPointsOfVectorsHasEquals(Vector vecA, Vector vecB) {
//        System.out.println("Vec A:");
//        printOutPointXAndY(vecA.getA(), vecA.getB());
//        System.out.println("Vec B:");
//        printOutPointXAndY(vecB.getA(), vecB.getB());

        Point[] points = new Point[4];
        points[0] = vecA.getPointA();
        points[1] = vecA.getPointB();
        points[2] = vecB.getPointA();
        points[3] = vecB.getPointB();
        for (int i = 0; i < points.length; i++) {
            for (int j = 1; j < points.length; j++) {
                if (i != j) {
                    if (checkIfPointsAreEquals(points[i], points[j])) {
                        System.out.println("Punkte sind gleich.");
                        return true;
                    }
                }
            }
        }
        //System.out.println("Punkte sind nicht gleich.");
        return false;
    }

    //sort the arrayList
    public ArrayList<Vector> convertArrayList(ArrayList<Vector> vecs) {
        Vector temp;
        for (int i = 0; i < vecs.size(); i++) {
            temp = vecs.get(i);
            if (vecs.get(i).getPointA().getY() > vecs.get(i).getPointB().getY()) {
                temp = new Vector(vecs.get(i).getPointA(), vecs.get(i).getPointB());
            } else if (vecs.get(i).getPointA().getY() < vecs.get(i).getPointB().getY()) {
                temp = new Vector(vecs.get(i).getPointB(), vecs.get(i).getPointA());
            } else {
                if (vecs.get(i).getPointA().getX() < vecs.get(i).getPointB().getX()) {
                    temp = new Vector(vecs.get(i).getPointA(), vecs.get(i).getPointB());
                } else {
                    temp = new Vector(vecs.get(i).getPointB(), vecs.get(i).getPointA());
                }
            }
            vecs.set(i, temp);
        }
        return vecs;
    }

    //printout the vectors
    public void printPointsOfVectors(Vector vecA, Vector vecB) {
        Point[] points = new Point[4];
        points[0] = vecA.getPointA();
        points[1] = vecA.getPointB();
        points[2] = vecB.getPointA();
        points[3] = vecB.getPointB();
        System.out.println("the points of 2 Vectors");
        System.out.println("Vec1:");
        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i].getX() + "/" + points[i].getY());
            if (i == 1) {
                System.out.println("Vec2:");
            }
        }

    }

    public void printOutVectorXAndY(Vector x, Vector y) {
        System.out.println(x.getxVec() + "|" + x.getyVec() + " zu: " + y.getxVec() + "|" + y.getyVec());
    }

    public void printOutPointXAndY(Point x, Point y) {
        System.out.println("A: " + x.getX() + "/" + x.getY() + " B: " + y.getX() + "/" + y.getY());
    }

    public ArrayList<Vector> getVectors() {
        return vectorsOfPlayerBlue;
    }

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    public static int[][] getField() {
        return field;
    }

    public Point[][] getPoints() {
        return points;
    }

    public Point getCommonPoint() {
        return commonPoint;
    }

    public ArrayList<Vector> getVectorsOfPlayerBlue() {
        return vectorsOfPlayerBlue;
    }

    public ArrayList<Vector> getVectorsOfPlayerRed() {
        return vectorsOfPlayerRed;
    }

    public Vector getSquareVectorA() {
        return squareVectorA;
    }

    public Vector getSquareVectorB() {
        return squareVectorB;
    }

    public Vector getExpectedVectorA() {
        return expectedVectorA;
    }

    public Vector getExpectedVectorB() {
        return expectedVectorB;
    }

    public void setVectorsOfPlayerBlue(ArrayList<Vector> vectorsOfPlayerBlue) {
        this.vectorsOfPlayerBlue = vectorsOfPlayerBlue;
    }

    public void setVectorsOfPlayerRed(ArrayList<Vector> vectorsOfPlayerRed) {
        this.vectorsOfPlayerRed = vectorsOfPlayerRed;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SquareWins sw = new SquareWins();

        sw.addVector(new Point(2, 1, 'b', false), new Point(4, 2, 'c', false));
        sw.addVector(new Point(2, 1, 'b', false), new Point(1, 3, 'c', false));
        sw.addVector(new Point(1, 3, 'c', false), new Point(3, 4, 'c', false));
        sw.addVector(new Point(4, 2, 'c', false), new Point(3, 4, 'c', false));
        //Quare
        sw.addVector(new Point(0, 0, 'c', false), new Point(0, 1, 'c', false));
        sw.addVector(new Point(0, 0, 'c', false), new Point(1, 0, 'c', false));
        sw.addVector(new Point(1, 0, 'c', false), new Point(1, 1, 'c', false));
        sw.addVector(new Point(1, 1, 'c', false), new Point(0, 0, 'c', false));

        //System.out.println("Vektor B: " + (sw.getVectors().get(1).getxVec() < 0 ? "(" + sw.getVectors().get(1).getxVec() + ")" : sw.getVectors().get(1).getxVec()) + " - " + sw.getVectors().get(1).getyVec());
        //sw.testCheckAllVectors(sw.getVectors());
        sw.buildSquare();

        //more than 2 sides
        //sw.checkAllSitesIfEqualLength(sw.getVectors());
    }

}
