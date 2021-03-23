package thunder;

import java.awt.image.BufferedImage;
public class BigAirplane extends FlyingObject implements EnemyScore{
    //构造器，设置长、宽、速度
    public BigAirplane(){
        super(140,140);
        speed = 2;
    }
    //移动速度
    public void step() {
        y+=speed;
    }
    //判断是否被击中，在爆破图结束后回收
    int index = 1;
    public BufferedImage getImage() {
        if(isLife()) {
            return Images.BigAirplane[0];
        }else if(isDead()) {
            BufferedImage img = Images.BigAirplane[index++];
            if(index==Images.BigAirplane.length) {
                state = REMOVE;
            }
            return img;
        }
        return null;
    }
    @Override
    public int getScore() {
        return 3;
    }
}
