package thunder;

import java.awt.image.BufferedImage;
import java.util.Random;
public abstract class FlyingObject {
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected int speed;
    protected static int LIFE = 0;
    protected static int DEAD = 1;
    protected static int REMOVE = 2;
    protected int state = LIFE;
    //构造器，可变的长宽，随机x轴，固定y轴
    public FlyingObject(int height,int width){
        Random rand = new Random();
        this.height = height;
        this.width = width;
        x = rand.nextInt(World.WIDTH-width);
        y = -height;
        speed = 2;
    }
    public FlyingObject(int height,int width,int x,int y){
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;

    }
    public  void step() {
        y+=speed;
    }
    public abstract BufferedImage getImage();
    //对象状态判断
    public boolean isLife() {
        return state==LIFE;
    }
    public boolean isDead() {
        return state==DEAD;
    }
    public boolean isRemove() {
        return state==REMOVE;
    }
    public boolean isOutOfBounds(){
        return y>World.HEIGHT;
    }
    public boolean isHit(FlyingObject object){
        int x1=this.x-object.width;
        int x2=this.x+this.width;
        int y1=this.y-object.height;
        int y2=this.y+this.height;
        int x=object.x;
        int y=object.y;
        return x>x1&&x<=x2&&y>y1&&y<=y2;
    }
    public void goDead() {
        state = DEAD;
    }
}

