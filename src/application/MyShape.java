package application;

import java.lang.Math;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.canvas.GraphicsContext;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;


interface MyShapeInterface{
	MyRectangle getMyBoundingRectangle();
	boolean pointInMyShape(MyPoint p);
	public static final Button btn = new Button();
	
	public static MyRectangle overlapMyRectangles(MyRectangle R1, MyRectangle R2) {
		int x1= R1.getTLC().getX();
		int y1= R1.getTLC().getY();
		int w1= R1.getW();
		int h1= R1.getH();
	
		int x2= R2.getTLC().getX();
		int y2= R2.getTLC().getY();
		int w2= R2.getW();
		int h2= R2.getH();
		
		if(y1 + h1 <y2 || y1>y2 +h2) return null;
		
		if(x1+w1 < x2 || x1> x2 +w2) return null;
		
		int xmax = Math.max(x1,x2);
		int ymax = Math.max(y1, y2);
		int xmin = Math.min(x1 + w1, x2+ w2);
		int ymin = Math.min(y1 + h1, y2+h2);
		
		MyPoint p = new MyPoint(xmax, ymax, null);
		
		
		return new MyRectangle(p, Math.abs(xmin - xmax), Math.abs(ymin - ymax), null);
	}
	
	public static MyRectangle overlapMyShapes(MyShape S1, MyShape S2) {
		MyRectangle R1 = S1.getMyBoundingRectangle();
		MyRectangle R2 = S2.getMyBoundingRectangle();
		
		return overlapMyRectangles(R1,R2);
	}
	
	public static List<MyPoint> intersectMyShapes(MyShape S1, MyShape S2){
		MyRectangle R1 = S1.getMyBoundingRectangle();
		MyRectangle R2 = S2.getMyBoundingRectangle();
		MyRectangle R = overlapMyShapes(R1, R2);
		
		if(R!= null) {
			int x = R.getTLC().getX();
			int y = R.getTLC().getY();
			int w = R.getW();
			int h = R.getH();
			
			List<MyPoint> intersect = new ArrayList<MyPoint>();
			
			for(int i = 0; i< w ; i++) {
				int xi = x +i;
				for(int j = 0; j<h;j++) {
					MyPoint p = new MyPoint(xi,y+j, null);
					if(S1.pointInMyShape(p) && S2.pointInMyShape(p)) {
						p.setColor(MyColor.ALICEBLUE);
						intersect.add(p);
					}
				}
			}
			return intersect;
		}
		else {
			return null;
		}
	}
	
	public default Canvas drawIntersectMyShapes(MyShape S1,MyShape S2) {
		List<MyPoint> intersect = intersectMyShapes(S1,S2);
		Canvas overlayCV = new Canvas();
		
		overlayCV.setWidth(1280);
		overlayCV.setHeight(720);
		
		GraphicsContext overlayGC = overlayCV.getGraphicsContext2D();
		S1.draw(overlayGC);
		S2.draw(overlayGC);
		for(MyPoint p : intersect) {
			p.draw(overlayGC);
		}
		
		return overlayCV;
	}
}
enum MyColor{
	//list of colors COLOR(r,g,b,opacity)
	ALICEBLUE(240,248,255,255),
	BLACK(0,0,0,255),
	RED(255,0,0,255),
	BLUE(0,0,255,255),
	YELLOW(255,255,0,255),
	SILVER(192,192,192,255),
	GRAY(128,128,128,255),
	MAROON	(128,0,0,255),
	OLIVE(128,128,0,255),
	GREEN(0,128,0,255),
	LIME(0,255,0,255),
	PURPLE(128,0,128,255),
	TEAL(0,128,128,255),
	NAVY(0,0,128,255),
	FUCHY(255,22,57,255),
	LIGHTGREEN(125,188,57,255),
	ROSE(255,194,199,255),
	BEIGE(222,187,133,255),
	CRIMSON(220,20,60,225),
	BROWN(165,42,42,225),
	FIREBRICK(178,34,34,255),
	CORAL(255,127,80,255),
	SALMON(250,128,114,255),
	PALEGREEN(152,251,152,255),
	SPRINGREEN(0,255,127,255),
	AQUAMARINE(102,205,170,255),
	TURQUOISE(64,224,208,255),
	SKYBLUE(135,206,250,255),
	CORNFLOWER(100,149,237,255),
	SLATEBLUE(123,104,238,255),
	PLUM(221,160,221,255),
	ORCHID(218,112,214,255),
	PALEVIOLET(219,112,147,255),
	HOTPINK(255,182,193,255),
	LEMON(255,250,205,255),
	ROSYBROWN(188,143,143,255),
	BURLYWOOD(22,184,135,255),
	KHAKI(240,230,140,255);
	
	
	
	
	private int r;
	private int g;
	private int b;
	private int a;
	private int argb;
	
	MyColor(int r, int g, int b, int a){
		setR(r);
		setG(g);
		setB(b);
		setA(a);
		setARGB(r,g,b,a);
	}
	
	public void setR(int r) {if (r>=0 && r<= 255) this.r = r;}
	public void setG(int g) {if (g>=0 && g<= 255) this.g = g;}
	public void setB(int b) {if (b>=0 && b<= 255) this.b = b;}
	public void setA(int a) {if (a>=0 && a<= 255) this.a = a;}
	public void setARGB( int r, int g, int b,int a) {
		this.argb = (a<<24) & 0xFF000000 | (r<< 16) & 0x00FF0000 |
					(g<< 8) & 0x0000FF00 | b;
	}
	
	public int getR() {return r;}
	public int getG() {return g;}
	public int getB() {return b;}
	public int getA() {return a;}
	public int getARGB() {return argb;}
	
	public String getHexColor() {
		return '#' +Integer.toHexString(argb).toUpperCase();
	}
	public Color getAWTColor(){
		return Color.decode(Integer.toString(argb));
	}
	public javafx.scene.paint.Color getFxColor() {
        return javafx.scene.paint.Color.rgb(r, g, b, (double) a / 255.0);
    }
	public static MyColor [] getMyColors() {
		return MyColor.values();
	}
	public String print() {
		return this +"(" + this.r + ", "+ this.g +", " + this.b + ", " + this.a +")" + " Hexadecimal Code: " + this.getHexColor();
	}
}

class MyPoint{
	int x;
	int y;
	MyColor color;
	
	MyPoint(){
		setPoint(0,0);
		this.color = MyColor.ALICEBLUE;		
	}
	MyPoint(int x, int y, MyColor color){
		setPoint(x,y);
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);		
	}
	MyPoint(MyPoint p, MyColor color){
		setPoint(p);
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
	}
	
	public void setPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void setPoint(MyPoint p) {
		this.x = p.getX();
		this.y= p.getY();
	}
	
	public void setColor(MyColor color ) {
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public MyColor getColor() {return color;}
	
	public double distanceFromOrigin() {
		return Math.sqrt(x*x +y*y);
	}
	public double distanceFrom(MyPoint p) {
		double dx = x-p.getX();
		double dy = y-p.getY();
		return Math.sqrt(dx*dx +dy*dy);
	}
	
	public boolean equals(MyPoint p) {
		return (x ==p.getX() && y== p.getY());
	}
	public double angleX(MyPoint p) {
		double dx = (double)(p.getX()-x);
		double dy = (double)(p.getY()-y);
		return Math.toDegrees(Math.atan2(dy, dx));
	}
	public void draw(GraphicsContext gc) {
		gc.setFill(color.getFxColor());
		gc.setLineWidth(2);
		gc.strokeLine(getX(), getY(),getX(),getY());
	}
	@Override
	public String toString() {
		return "Point(" + x +", "+y+")";
	}
	
}


abstract class MyShape implements MyShapeInterface{
	MyPoint p;
	int x, y;
	MyColor color;
	
	MyShape(MyPoint p, MyColor color){
		setPoint(p);
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
	}
	MyShape(int x, int y, MyColor color){
		this.x = x; this.y= y;
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
	}
	
	public void setPoint(MyPoint p) {this.p=p;}
	public void setPoint(int x, int y) {p.setPoint(x,y);}
	
	public MyPoint getPoint() {return p;}
	public int getX() {return p.getX();}
	public int getY() {return p.getY();}
	public MyColor getColor() {return color;}
	 
	public abstract double area();
	public abstract double perimeter();
	
	public abstract void draw(GraphicsContext gc);
	
	@Override
	public String toString() {return "this is a MyShape Object";}
	
}
