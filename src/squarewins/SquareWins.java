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

    private final int RESVECT = 0;
    private boolean player = true;
    private static int[][] field;
    private Point points[][];
    private ArrayList<Vector> vectors = new ArrayList<>();
    private double aLength;
    private double bLength;
    private Vector expectedVectorA, expectedVectorB;

    public void createField() {
        field = new int[5][5];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                points[i][j] = new Point(i, j);
            }
        }
    }

    public void addVector(Point a, Point b) {
        vectors.add(new Vector(a, b));
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

        boolean vecAExist = false, vecBExist = false;
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
                        Point[] points = new Point[4];
                        points[0] = vectors.get(i).getPointA();
                        points[1] = vectors.get(i).getPointB();
                        points[2] = vectors.get(j).getPointA();
                        points[3] = vectors.get(j).getPointB();
                        Point commonPoint = null;
                        for (int a = 0; a < points.length; a++) {
                            for (int b = 1; b < points.length; b++) {
                                if (a != b) {
                                    if (checkIfPointsAreEquals(points[a], points[b])) {
                                        //System.out.println("Punkte sind gleich.");
                                        commonPoint = points[b];
                                        System.out.println("(common Hinzugefügt "+commonPoint.getX() +"/"+ commonPoint.getY());
                                        System.out.println("Hinzugefügt)");
                                    }
                                }
                            }
                        }
                        Point pointNotCommon;
                        if (checkCoordinates(vectors.get(i).getPointA(), commonPoint)) {
                            pointNotCommon = vectors.get(i).getPointB();
                            System.out.println(pointNotCommon.getX() +"/"+ pointNotCommon.getY());
                        } else {
                            pointNotCommon = vectors.get(i).getPointA();
                            System.out.println(pointNotCommon.getX() +"/"+ pointNotCommon.getY());
                        }
                        if (checkCoordinates(vectors.get(j).getPointA(), commonPoint)) {
                            pointNotCommon = vectors.get(j).getPointB();
                            System.out.println(pointNotCommon.getX() +"/"+ pointNotCommon.getY());
                        } else {
                            pointNotCommon = vectors.get(j).getPointA();
                            System.out.println(pointNotCommon.getX() +"/"+ pointNotCommon.getY());
                        }
//                        for (Vector vec : vectors) {
//                            if (checkAll(vec, this.aV) && checkAll(vec, this.bV)) {
//                                System.out.println("Win!!!!!");
//                                break;
//                            }
//                        }
                        
                    }
                }
            }
        }
    }
    
    
    /*
    *   it checks if p1 equals p2
    */
    public boolean checkCoordinates(Point p1, Point p2){
        if(p1.getX() == p2.getX() && p1.getY() == p2.getY()){
            return true;
        }
        return false;
    }

    public boolean checkAll(Vector a, Vector b) {
        if (checkIfEqualLength(a, b) && checkIf90Degree(a, b) && checkIfPointsOfVectorsAreEquals(a, b)) {
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
                    checkIfPointsOfVectorsAreEquals(vectors.get(i), vectors.get(j));
                }
            }
        }
        printOutAllVectors(vectors);
    }

    public void printOutAllVectors(ArrayList<Vector> vectors) {
        int count = 1;
        System.out.println("----------------------Alle Vektoren---------------------: ");
        for (Vector v : vectors) {

            System.out.println("Vektor " + count + ": " + (v.getxVec() < 0 ? "(" + v.getxVec() + ")" : v.getxVec()) + " - " + v.getyVec());

            count++;
        }
    }

    public boolean checkIfPointsAreEquals(Point a, Point b) {
        if (a.getX() == b.getX() && a.getY() == b.getY()) {
            return true;
        }
        return false;
    }

    public boolean checkIfPointsOfVectorsAreEquals(Vector vecA, Vector vecB) {
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
                        //System.out.println("Punkte sind gleich.");
                        return true;
                    }
                }
            }
        }
        //System.out.println("Punkte sind nicht gleich.");
        return false;
    }

    public void printPointsOfVectors(Vector vecA, Vector vecB) {
        Point[] points = new Point[4];
        points[0] = vecA.getPointA();
        points[1] = vecA.getPointB();
        points[2] = vecB.getPointA();
        points[3] = vecB.getPointB();
        for (int i = 0; i < points.length; i++) {
            printOutPointXAndY(points[i], new Point(-10, -10));
        }

    }

    public void printOutVectorXAndY(Vector x, Vector y) {
        System.out.println(x.getxVec() + "|" + x.getyVec() + " zu: " + y.getxVec() + "|" + y.getyVec());
    }

    public void printOutPointXAndY(Point x, Point y) {
        System.out.println("A: " + x.getX() + "/" + x.getY() + " B: " + y.getX() + "/" + y.getY());
    }

    public ArrayList<Vector> getVectors() {
        return vectors;
    }

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SquareWins sw = new SquareWins();

//        sw.addVector(new Point(2, 1, 'b', false), new Point(4, 2, 'c', false));
//        sw.addVector(new Point(2, 1, 'b', false), new Point(1, 3, 'c', false));
//        sw.addVector(new Point(1, 3, 'c', false), new Point(3, 4, 'c', false));
//        sw.addVector(new Point(4, 2, 'c', false), new Point(3, 4, 'c', false));
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
