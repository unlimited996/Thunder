package thunder;

import java.awt.image.BufferedImage;

public  class  Sky extends FlyingObject{
    private int y1;
    //构造器，设置长、宽、速度
    public Sky(){
        super(World.WIDTH,World.HEIGHT,0,0);
        y1 = -World.HEIGHT;
        speed = 1;
    }
    //两幅图片同时运动，按条件改变位置
    public void step() {
        y+=speed;
        y1+=speed;
        if(y>=World.HEIGHT) {
            y=-World.HEIGHT;
        }
        if(y1>=World.HEIGHT) {
            y1=-World.HEIGHT;
        }
    }
    //重写抽象类返回图片
    public BufferedImage getImage() {
        return Images.sky;
    }
    public BufferedImage getGameStar() { return Images.gameStar; }
    public BufferedImage getGameOver() { return Images.gameOver; }
    public BufferedImage getPause() {
        return Images.pause;
    }
    public int getY1() {
        return y1;
    }
}

