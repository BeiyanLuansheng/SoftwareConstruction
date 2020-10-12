/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    	for(int i=0; i<4; i++) {
    		turtle.forward(sideLength);
    		turtle.turn(90);
    	}
        //throw new RuntimeException("implement me!");
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
    	return (sides - 2) * 180.00 / sides;
    	//throw new RuntimeException("implement me!");
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
    	return (int)(360 / (180 - angle) + 0.5);
        //throw new RuntimeException("implement me!");
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
    	double degrees = 180.0 - calculateRegularPolygonAngle(sides);
    	for(int i=0; i<sides; i++) {
    		turtle.forward(sideLength);
    		turtle.turn(degrees);
    	}
    	//throw new RuntimeException("implement me!");
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	double targetBearing = Math.atan((double)(targetY-currentY) / (targetX-currentX)) / Math.PI * 180.0;
    	if(targetBearing < 0) targetBearing += 360.0;
    	if(currentBearing <= 90) currentBearing = 90 - currentBearing;
    	else currentBearing =  360 + 90 - currentBearing;
    	if(targetBearing > currentBearing) return 360.0 + currentBearing - targetBearing;
    	else return currentBearing - targetBearing;
        //throw new RuntimeException("implement me!");
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
    	List<Double> degrees = new ArrayList<>();
    	degrees.add((Double)calculateBearingToPoint(0, (int)xCoords.get(0), (int)yCoords.get(0),
    												(int)xCoords.get(1), (int)yCoords.get(1)));
    	for(int i=1; i<xCoords.size()-1; i++) {
    		degrees.add((Double)calculateBearingToPoint((double)degrees.get(i-1), (int)xCoords.get(i),
    								(int)yCoords.get(i),(int)xCoords.get(i+1), (int)yCoords.get(i+1)));
    	}
    	return degrees;
        //throw new RuntimeException("implement me!");
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
    	Set<Point> hull = new HashSet<Point>();
    	Point[] pointsArray = new Point[points.size()];
    	points.toArray(pointsArray);
    	if(pointsArray.length < 3) return points;
    	int l=0;
    	for(int i=0; i<pointsArray.length; i++)
    		if(pointsArray[i].x() < pointsArray[l].x())
    			l = i;
    	int p=l, q;
    	do {
    		hull.add(pointsArray[p]);
    		q = (p + 1) % (pointsArray.length - 1);
    		for(int i=0; i<pointsArray.length; i++) {
    			double ans = (pointsArray[i].y() - pointsArray[p].y()) * (pointsArray[q].x() - pointsArray[i].x())
    						- (pointsArray[i].x() - pointsArray[p].x()) * (pointsArray[q].y() - pointsArray[i].y());
    			if(ans < 0) q = i;
    		}
    		p = q;
    	}while(p != l);
    	return hull;
        //throw new RuntimeException("implement me!");
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        //throw new RuntimeException("implement me!");
    	for (int i = 0; i < 1000; i++) {
			turtle.forward(i/3);	//缩短每步的步长
			switch((i/100)%10) {
    		case 0:
    			turtle.color(PenColor.BLACK);
    			break;
    		case 1:
    			turtle.color(PenColor.GRAY);
    			break;
    		case 2:
    			turtle.color(PenColor.BLUE);
    			break;
    		case 3:
    			turtle.color(PenColor.CYAN);
    			break;
    		case 4:
    			turtle.color(PenColor.GREEN);
    			break;
    		case 5:
    			turtle.color(PenColor.MAGENTA);
    			break;
    		case 6:
    			turtle.color(PenColor.ORANGE);
    			break;
    		case 7:
    			turtle.color(PenColor.PINK);
    			break;
    		case 8:
    			turtle.color(PenColor.RED);
    			break;
    		case 9:
    			turtle.color(PenColor.YELLOW);
    			break;
    		}
			turtle.turn(100);
		}
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);
        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }

}
