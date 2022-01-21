package application;
import java.util.Optional;
import javafx.scene.canvas.GraphicsContext;

public class MyLine extends MyShape {
	MyPoint p;
	MyPoint q;
	MyPoint [] PLine = new MyPoint[2];
	MyColor color;
	
	MyLine(MyPoint p1, MyPoint p2, MyColor color){
		super(new MyPoint(),null);
		this.p = p1; this.q = p2;
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
	}
	
	public MyPoint [] getLine() {
		return PLine;
	}
	public MyColor getColor() {return color;}
	public MyPoint getP1() {return p;}
	public MyPoint getP2() {return q;}
	
	public double lineLength() {
		return p.distanceFrom(q);
	}
	public double lineAngle() {
		return q.angleX(p);
	}
	
	
	public double perimeter() {return (int) lineLength();}
	
	
	public double area() {return 0;}
	
	public MyRectangle getMyBoundingRectangle() {
		int x1 = p.getX(); int y1 = p.getY();
		int x2 = q.getX(); int y2 = q.getY();
		MyPoint pTLC = new MyPoint(Math.min(x1, x2),Math.min(y1, y2),null);
		
		return new MyRectangle(pTLC, Math.abs(x1-x2), Math.abs(y1 - y2), null);
	}
	
	public boolean pointInMyShape(MyPoint a) {
		return (p.distanceFrom(a) + q.distanceFrom(a)== lineLength());
	}
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color.getFxColor());
		gc.strokeLine(p.getX(), p.getY(), q.getX(), q.getY());
	}
	@Override
	public String toString() {
		return "Line Start from "+getP2()+ " to " + getP1()+" Length: "+ lineLength()+
				" Line Angle: "+lineAngle();
	}
}
