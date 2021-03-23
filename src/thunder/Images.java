package thunder;

import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;
public class Images {
    public static BufferedImage sky;
    public static BufferedImage[] Boss;
    public static BufferedImage Bullet;
    public static BufferedImage[] Airplane;
    public static BufferedImage[] Hero;
    public static BufferedImage[] Bee;
    public static BufferedImage[] BigAirplane;
    public static BufferedImage gameStar;
    public static BufferedImage gameOver;
    public static BufferedImage pause;
    static {//静态块，初始化静态图片，系统自动先加载静态块后调用构造器(只加载一次静态块)
        sky = readImage("background0.bmp");
        gameStar = readImage("gamestart.jpg");
        gameOver = readImage("gameover.jpg");
        pause = readImage("pause.jpg");
        Bullet = readImage("bullet3.png");
        Hero = new BufferedImage[5];
        Hero[0] = readImage("1090.png");
        Hero[1] = readImage("1091.png");
        Hero[2] = readImage("1092.png");
        Hero[3] = readImage("1093.png");
        Hero[4] = readImage("1094.png");
        Airplane = new BufferedImage[2];
        BigAirplane = new BufferedImage[2];
        Bee = new BufferedImage[2];
        Boss = new BufferedImage[2];
        BigAirplane[0] = readImage("bigairplane0.png");
        Bee[0] = readImage("bee.png");
        Airplane[0] = readImage("airplane0.png");
        Boss[0] = readImage("Boss0.gif");
        //遍历数组图片
        for(int i=1;i<Airplane.length;i++){
            BigAirplane[i] = readImage("bom"+i+".png");
            Bee[i] = readImage("bom"+i+".png");
            Airplane[i] = readImage("bom"+i+".png");

            BufferedImage img = new BufferedImage(1466,7911,sky.getType());
            Graphics x = img.getGraphics();
            x.drawImage(sky, 0,0,1466,7911,null);
            x.dispose();
            sky = img;
            //sky = changeImage(sky);
            gameOver = changeImage(gameOver);
            pause = changeImage(pause);
            gameStar = changeImage(gameStar);
        }
    }
    //读取并拉长图片大小
    public static BufferedImage changeImage(BufferedImage name){
        int width = World.WIDTH;
        int height = World.HEIGHT;
        try{
            BufferedImage img = new BufferedImage(width,height,name.getType());
            Graphics x = img.getGraphics();
            x.drawImage(name, 0,0,width,height,null);
            x.dispose();
            name = img;
            return name;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    //读取与FlyingObject在同package中的图片
    public static BufferedImage readImage(String fileName){
        try{
            BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
            return img;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}

