package thunder;

import java.awt.image.BufferedImage;
public class Airplane extends FlyingObject implements EnemyScore{
    //构造器，设置长、宽、速度
    public Airplane(){
        super(140,140);
        speed = 2;
    }
    //移动速度
    public void step() {
        y+=speed;
    }
    //被破坏切换图片
    int index = 1;
    public BufferedImage getImage() {
        if(isLife()) {
            return Images.Airplane[0];
        }else if(isDead()) {
            BufferedImage img = Images.Airplane[index++];
            if(index==Images.Airplane.length) {//爆破图结束则移除
                state = REMOVE;
            }
            return img;
        }
        return null;
    }
    @Override
    public int getScore() {
        return 1;
    }
}

