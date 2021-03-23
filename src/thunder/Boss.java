package thunder;

import java.awt.image.BufferedImage;

public class Boss extends FlyingObject{
    private int hp=100;
    public Boss(int height, int width, int x, int y,int hp) {
        super(height, width, x, y);
        this.hp=hp;
    }
    int index=0;
    public BufferedImage getImage() {
        if(isLife()) {
            return Images.Boss[0];
        }else if(isDead()) {
            BufferedImage img = Images.Boss[index++];
            if(index==Images.Boss.length) {
                state = REMOVE;
            }
            return img;
        }
        return null;
    }
}
