package thunder;

import java.awt.image.BufferedImage;
public class Bullet extends FlyingObject{
    //构造器，设置变量x、y和图片的长、宽以及移动速度
    public Bullet(int x,int y){
        super(145,62,x,y);
        speed = 3;
    }
    //获取图片，判断是否移除图片
    public BufferedImage getImage() {
        if(isLife())
            return Images.Bullet;
        if(isDead())
            state = REMOVE;
        return null;
    }
    //移动方向
    public void step() {
        y-=speed;//向上运动
    }
    public void steps() {
        x-=speed;
        y-=speed;//向上运动
    }
    //回收判断
    public boolean isOutOfBounds() {
        return y<0;
    }
}
