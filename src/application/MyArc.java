package application;
import java.util.Optional;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;


class Slice{
	MyPoint center;
	int radius;
	double prob; 
	MyColor color;
	double startAngle, angle, endEngle;
	
	//Constructor
	Slice(MyPoint p, int r, double startAngle, double angle, MyColor color){
			this.center = p;
			this.radius = r;
			this.startAngle = startAngle;
			this.angle = angle;
			this.color = color;
	}
	
	//get methods
	public MyPoint getCenter() {return center;}
	public int getRadius() {return radius;}
	public double getStartAngle() {return startAngle;}
	public double getAngle() {return angle;}
	public MyColor getcolor() {return color;}
	
	//Computational methods
	public double area() {return 0.5 * angle * Math.pow(radius, 2);}
	public double perimeter() {return (double) radius* (Math.toRadians(angle));}
	
	//Drawing the slice
	public void draw(GraphicsContext gc) {
		gc.setFill(color.getFxColor());
		gc.fillArc(center.getX()-radius, center.getY()-radius, radius *2, radius*2, startAngle, angle, ArcType.ROUND);
	}
	public String toString() {
		return "Slice: Center(" + center.getX() + ", "+ center.getY()+  ") Radius " + radius+ " (Starting Angle, Angle): ("
				+ radius + " (Starting Angle, Angle): (" + startAngle + ", " + angle +"), " + color.getAWTColor();
	}
}

public class MyArc extends MyShape{
	MyPoint center;
	MyPoint p1,p2;
	double startAngle;
	double angle;
	double rStartAngle, rAngle, rEndAngle;
	MyColor color; 
	
	MyOval O; 
	int a,b;
	
	//Constructor 1
	MyArc(MyPoint p, int a, int b, double startAngle, double angle, MyColor color){
		super(new MyPoint(), null);
		this.center = p; this.a =a; this.b = b;
		this.startAngle = startAngle;
		this.angle = angle;
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
		this.rStartAngle = Math.toRadians(startAngle);
		this.rAngle = Math.toRadians(angle);
		this.rEndAngle = Math.toRadians(startAngle + angle);
		
		int x = center.getX();
		int y = center.getY();
		int x1 = (int)(x+ (double)(a*b)/Math.sqrt(Math.pow(b, 2)+Math.pow(a* Math.tan(rStartAngle),2)));
		int y1 = (int)(y+ (double)(a*b*Math.tan(rStartAngle))/ Math.sqrt(Math.pow(b,2)+ Math.pow(a * Math.tan(rStartAngle),2 )));
		int x2 = (int)(x+ (double)(a*b)/Math.sqrt(Math.pow(b, 2)+Math.pow(a* Math.tan(rEndAngle),2)));
		int y2 = (int)(y+ (double)(a*b*Math.tan(rEndAngle))/ Math.sqrt(Math.pow(b,2)+ Math.pow(a * Math.tan(rEndAngle),2 )));
		this.p1 = new MyPoint(x1, y1, null); this.p2 = new MyPoint(x2,y2,null);
		this.O = new MyOval(center, a, b, color);
	}
	
	//Constructor 2
	MyArc(MyOval O, double startAngle, double angle, MyColor color){
		super(new MyPoint(), null);
		
		this.O = O;
		this.a = O.getWidth();
		this.b = O.getHeight();
		this.center = O.getCenter();
		
		this.startAngle = startAngle;
		this.angle = angle;
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
		this.rStartAngle = Math.toRadians(startAngle);
		this.rAngle = Math.toRadians(angle);
		this.rEndAngle = Math.toRadians(startAngle + angle);
		
		int x = center.getX();
		int y = center.getY();
		int x1 = (int)(x+ (double)(a*b)/Math.sqrt(Math.pow(b, 2)+Math.pow(a* Math.tan(rStartAngle),2)));
		int y1 = (int)(y+ (double)(a*b*Math.tan(rStartAngle))/ Math.sqrt(Math.pow(b,2)+ Math.pow(a * Math.tan(rStartAngle),2 )));
		int x2 = (int)(x+ (double)(a*b)/Math.sqrt(Math.pow(b, 2)+Math.pow(a* Math.tan(rEndAngle),2)));
		int y2 = (int)(y+ (double)(a*b*Math.tan(rEndAngle))/ Math.sqrt(Math.pow(b,2)+ Math.pow(a * Math.tan(rEndAngle),2 )));
		this.p1 = new MyPoint(x1, y1, null); this.p2 = new MyPoint(x2,y2,null);
	}
	
	//Constructor 3
	MyArc(MyOval O, MyPoint p1, MyPoint p2, MyColor color){
		super( new MyPoint(), null);
		
		this.O =O;
		this.a = O.getWidth(); this.b = O.getHeight();
		this.center = O.getCenter();
		
		this.p1 = p1; this.p2 = p2;
		this.startAngle = center.angleX(p1);
		this.angle = center.angleX(p2) - startAngle;
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
		this.rStartAngle = Math.toRadians(startAngle);
		this.rAngle = Math.toRadians(angle);
		this.rEndAngle = Math.toRadians(startAngle + angle);
	}
	
	//get methods
	public MyPoint getCenter() { return center;}
	public double getStartAngle() { return startAngle;}
	public double getAngle() { return angle;}
	public MyOval getOval() { return O;}
	public MyColor getColor() {return color;}
	
	//Computational methods
	@Override
	public double area() {
		double hpw = (double) (b+a);
		double hnw = (double) (b-a);
		return (double)(0.5*a*b * (rAngle - (Math.atan((hnw * Math.sin(2.0* rEndAngle))/
									(hpw +hnw * Math.cos(2.0*rEndAngle)))-
									Math.atan((hnw * Math.sin(2.0 * rStartAngle))
									/ (hpw + hnw * Math.cos(2.0 * rStartAngle))))));
	}
	
	@Override
	public double perimeter() {return (double)(0.5 * Math.PI / Math.sqrt(2)* p1.distanceFrom(p2));}
	
	// MyShapeInterface methods
	public MyRectangle getMyBoundingRectangle() { return O.getMyBoundingRectangle();}	
	public boolean pointInMyShape(MyPoint p ) {
		double pAngle = center.angleX(p);
		int dx = center.getX() - p.getX();
		int dy = center.getY() - p.getY();
		
		return Math.pow(b * dx,2)+
				Math.pow(a *dy,  2) <= Math.pow( a* b, 2) && pAngle >= startAngle && pAngle <= startAngle + angle;
	}
	//Drawing the arc
	@Override
	public void draw (GraphicsContext GC) {
		GC.setFill(color.getFxColor());
		int x = center.getX()- (a/2);
		int y = center.getY() -(b/2);
		GC.fillArc(x, y, a, b, startAngle, angle, ArcType.ROUND);
	}
	@Override
	public String toString() {
		return "Arc: Center " + center + " Oval Width " + 2 * a + " Oval Height " + 2 *b + " (Start Angle, Anfle): ("
				+ startAngle + ", "+ angle + "), " + "Perimeter " + perimeter() + " Area " + area() + " " + color;
	}	
}

