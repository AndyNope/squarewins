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
    //Array / coordination of the Points
    private Point[][] pointOfPlayerBlue = new Point[5][5];
    private Point[][] pointOfPlayerRed = new Point[5][5];
    
    //length of the first vector
    private double aLength;
    //length of the second vector
    private double bLength;
    //expected vectors to finish a square
    private Vector squareVectorA, squareVectorB;

    public SquareWins() {
        squareVectorA = null;
        squareVectorB = null;
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
    public void addVectorBlue(Point a, Point b) {
        vectorsOfPlayerBlue.add(new Vector(a, b));
    }
    
    //implementation for adding a vector of a field
    public void addVectorRed(Point a, Point b) {
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

    public void buildSquare(ArrayList<Vector> vectors, boolean color) {
        int anz = 0;
        for (int i = 0; i < vectors.size(); i++) {
            for (int j = 1; j < vectors.size(); j++) {
                if (i != j) {
                    if (checkAll(vectors.get(i), vectors.get(j))) {
                        anz++;
                        System.out.println("build " + anz);
                        System.out.println("Vec 1:");
                        printOutPointXAndY(vectors.get(i).getPointA(), vectors.get(i).getPointB());
                        System.out.println("Vec 2:");
                        printOutPointXAndY(vectors.get(j).getPointA(), vectors.get(j).getPointB());

                        

                        //setCommonPoint
                        this.commonPoint = setCommonPoint(vectors.get(i),vectors.get(j));

                        Point cornerPoint = getCornerpoint(commonPoint, vectors.get(i), vectors.get(j));
                        if (cornerPoint != null) {
                            System.out.println("Diagonal-Coordination");
                            System.out.println(cornerPoint.getX() + "/" + cornerPoint.getY());
                        }
                        //Set the quare vectors
                        setSquareVectors(vectors.get(i), vectors.get(j), commonPoint);

                        //sort the Arrays
//                        if (color) {
//                            setVectorsOfPlayerBlue(convertArray(vectors));
//                        } else {
//                            setVectorsOfPlayerRed(convertArray(vectors));
//                        }
                        if (color) {
                            if (expectedVectorExists(getVectorsOfPlayerBlue(), cornerPoint)) {
                                System.out.println("");
                                System.out.println("Win!");
                                System.out.println("");
                                System.out.println("");
                                System.out.println("");
                                System.exit(0);
                            }
                        } else {
                            if (expectedVectorExists(getVectorsOfPlayerRed(), cornerPoint)) {
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
    }

    //Addition of the 2 Vectors
    private Point getCornerpoint(Point common, Vector vA, Vector vB) {
        Point point = null;
        //Lenght of vectors
        int xLenght= 0, yLenght = 0;

        //if Point A of Vec A is equal with common point
        if (checkIfPointsAreEquals(vA.getPointA(), common)) {
            //Use Point B of vector A
            if (checkIfPointsAreEquals(vB.getPointA(), common)) {
                xLenght = vB.getPointB().getX() - common.getX();
                yLenght = vB.getPointB().getY() - common.getY();
            }else{
                xLenght = vB.getPointA().getX() - common.getX();
                yLenght = vB.getPointA().getY() - common.getY();
            }
            point = new Point(vA.getPointB().getX() + xLenght, vA.getPointB().getY() + yLenght);
        } else {
            if (checkIfPointsAreEquals(vA.getPointA(), common)) {
                xLenght = vA.getPointB().getX() - common.getX();
                yLenght = vA.getPointB().getY() - common.getY();
            }else{
                xLenght = vA.getPointA().getX() - common.getX();
                yLenght = vA.getPointA().getY() - common.getY();
            }
            point = new Point(vB.getPointA().getX() + xLenght, vB.getPointA().getY() + yLenght);
        }
        return point;
    }

    //It's going to compare the points of the quarevectors and set the common vector
    public Point setCommonPoint(Vector v1, Vector v2) {
        Point[] points = new Point[4];
                        points[0] = v1.getPointA();
                        points[1] = v1.getPointB();
                        points[2] = v2.getPointA();
                        points[3] = v2.getPointB();
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
    public void setSquareVectors(Vector v1, Vector v2, Point commonPoint) {

        if (commonPoint.equals(v1.getPointA())) {
            this.squareVectorA = new Vector(commonPoint, v1.getPointA());
        } else {
            this.squareVectorA = new Vector(commonPoint, v1.getPointB());
        }
        if (!commonPoint.equals(v2.getPointA())) {
            this.squareVectorB = new Vector(commonPoint, v2.getPointA());
        } else {
            this.squareVectorB = new Vector(commonPoint, v2.getPointB());
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
    public boolean expectedVectorExists(ArrayList<Vector> vectors, Point diagonalPoint) {
        System.out.println(diagonalPoint.getX() + "/" + diagonalPoint.getY());
        for (Vector v : vectors) {
            if (checkIfPointsAreEquals(diagonalPoint, v.getPointA()) || checkIfPointsAreEquals(diagonalPoint, v.getPointB()) ) {
                return true;
            }
            printOutPointXAndY(v.getPointA(), v.getPointB());
        }
        return false;
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
    public ArrayList<Vector> convertArray(ArrayList<Vector> vecs) {
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
            System.out.println(vecs.get(i).getPointA().getY() + " / " + vecs.get(i).getPointB().getY());
        }
        return vecs;
    }

    //sort the arrayList
    public Vector[] convertArray(Vector[] vecs) {
        Vector temp;
        for (int i = 0; i < vecs.length; i++) {
            temp = vecs[i];
            if (vecs[i].getPointA().getY() > vecs[i].getPointB().getY()) {
                temp = new Vector(vecs[i].getPointA(), vecs[i].getPointB());
            } else if (vecs[i].getPointA().getY() < vecs[i].getPointB().getY()) {
                temp = new Vector(vecs[i].getPointB(), vecs[i].getPointA());
            } else {
                if (vecs[i].getPointA().getX() < vecs[i].getPointB().getX()) {
                    temp = new Vector(vecs[i].getPointA(), vecs[i].getPointB());
                } else {
                    temp = new Vector(vecs[i].getPointB(), vecs[i].getPointA());
                }
            }
            vecs[i] = temp;
//            System.out.println(vecs[i].getPointA().getY() + " / " + vecs[i].getPointB().getY());
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

//        sw.addVectorBlue(new Point(3, 2, 'b', true), new Point(5, 3, 'b', true));
//        sw.addVectorBlue(new Point(3, 2, 'b', true), new Point(2, 4, 'b', true));
//        sw.addVectorBlue(new Point(2, 4, 'b', true), new Point(4, 5, 'b', true));
//        sw.addVectorBlue(new Point(5, 3, 'b', true), new Point(4, 5, 'b', true));
//        Quare
        sw.addVectorRed(new Point(0, 9, 'r', true), new Point(0, 1, 'r', true));
        sw.addVectorRed(new Point(0, 0, 'r', true), new Point(1, 0, 'r', true));
        sw.addVectorRed(new Point(1, 0, 'r', true), new Point(1, 1, 'r', true));
        sw.addVectorRed(new Point(1, 1, 'r', true), new Point(0, 0, 'r', true));

        //System.out.println("Vektor B: " + (sw.getVectors().get(1).getxVec() < 0 ? "(" + sw.getVectors().get(1).getxVec() + ")" : sw.getVectors().get(1).getxVec()) + " - " + sw.getVectors().get(1).getyVec());
        //sw.testCheckAllVectors(sw.getVectors());
        sw.buildSquare(sw.getVectorsOfPlayerBlue(), true);

        //more than 2 sides
        //sw.checkAllSitesIfEqualLength(sw.getVectors());
    }

}
