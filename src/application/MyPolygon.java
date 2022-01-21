package application;
import java.util.Optional;
import javafx.scene.canvas.GraphicsContext;


public class MyPolygon extends MyShape {
	int num, radius;
	MyColor color;
	MyPoint center;
	double[] y;
	double[]x;
	double angle;
	MyPolygon(int n, int r,MyPoint center, MyColor color){
		super(new MyPoint(),null);
		this.num= n; this.radius=r;
		this.color = Optional.ofNullable(color).orElse(MyColor.ALICEBLUE);
		this.center = center;
		this.y = new double[n];
		this.x = new double[n];
		this.angle = ( 2*Math.PI/ n);
		}

	
	
	public double [] getXs() {
		for( int i= 0 ; i< num; i++) {
			x[i] = center.getX()+ radius *Math.sin(i* angle);
		}
		return x;
	}

	public double [] getYs() {
		for( int i= 0 ; i< num; i++) {
			y[i] = center.getY()+ radius *Math.cos(i* angle);
		}
		return y;
	}
	
	public MyPoint Center() {return center;}
	public int getAngle() {return ((num-2)*180)/num;}
	public int getSide() {return 0;}
	public double apothem() {return radius* Math.cos(180/num);}
	public double perimeter() {return getSide()* num;}
	
	public double area() {return (apothem()* perimeter())/2;}
	@Override
	public String toString() {
		return "test";
	}
	public MyRectangle getMyBoundingRectangle() {
		MyPoint TLC = new MyPoint(center.getX()- (radius),center.getY() -(radius),MyColor.PURPLE);
		return new MyRectangle(TLC, radius*2, radius*2, MyColor.PURPLE);
	}
	public boolean pointInMyShape(MyPoint p){
			MyPoint p2 = new MyPoint(9999, p.getY(),null);
			MyLine extend = new MyLine(p,p2,null);
			return false;
	}
	
	@Override
	public void draw(GraphicsContext gc) {

		gc.setFill(color.getFxColor());
	
		gc.fillPolygon(getXs(),getYs(), num);
	}
}
