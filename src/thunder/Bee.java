package thunder;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends FlyingObject implements EnemyScore{
    private int xspeed ;
    private int yspeed;
    private int awardTypy;
    //构造器，设置长、宽、速度和奖励值（随机0-1的奖励）
    public Bee(){
        super(51,60);
        xspeed = 2;
        yspeed = 1;
        Random rand = new Random();
        awardTypy = rand.nextInt(2);
    }
    //判断是否被击中，爆破图结束则移除
    int index = 1;
    public BufferedImage getImage() {
        if(isLife())
            return Images.Bee[0];
        if(isDead()) {
            BufferedImage img = Images.Bee[index++];
            if(index==Images.Bee.length) {
                state = REMOVE;
            }
            return img;
        }
        return null;
    }
    //移动反向
    public void step() {
        x+=xspeed;//左右方向
        y+=yspeed;//向下
        if(x<0||x>=World.WIDTH-width)
            xspeed*=-1;//切换方向
    }
    @Override
    public int getScore() {
        return 5;
    }
}

