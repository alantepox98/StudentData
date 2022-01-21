package application;

import java.util.Optional;

import javafx.scene.canvas.GraphicsContext;

public class MyCircle extends MyOval{
	
	MyPoint center;
	int radius;
	MyColor color;
	MyCircle(MyPoint p,int r, MyColor color ){
		super(p,r,r,null);
		this.center = p;this.radius=r;
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
	}
	
	public int getX() {return center.getX();}
	public int getY() {return center.getY();}
	public int getRadius() {return radius;}
	public MyPoint getCenter() {return center;}
	
	public boolean similarObject(MyShape S) {
		if(S.getClass().toString().equals("class MyCircle")) {
			MyCircle C= (MyCircle) S;
			return (this.radius == C.getRadius());
		}
		else {
			return false;
		}
	}
	@Override
	public void draw(GraphicsContext gc) {
		int x = center.getX()-radius/2;
		int y = center.getY() -radius/2;
		
		gc.setFill(color.getFxColor());
		gc.fillOval(x,y, radius,radius);
	}
	public boolean pointInMyShape(MyPoint p) {
		int x = center.getX()- (radius/2);
		int y = center.getY() -(radius/2);
		MyPoint c = new MyPoint(x,y,null);

			return center.distanceFrom(p) <=radius/2;
		
	}
	@Override
	public String toString() {
		return "Circle Center ("+ center.getX()+", "+center.getY()+") Abscissa from x: -"+getA()+" to x: "+getB()+
				" Perimeter: "+ perimeter() + " Area: "+ area();
	}
}
