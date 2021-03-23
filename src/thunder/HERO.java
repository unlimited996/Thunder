package thunder;

import java.awt.image.BufferedImage;

public class HERO extends FlyingObject{
    private static int HP;
    private static int MP;
    private int fire;
    int index = 0;
    //构造器，设置长、宽、生命、火力、键盘移速
    public HERO(){
        super(295,549,300,300);
        HP = 100;
        MP=0;
        fire = 0;
        speed = 20;
    }
    //每10ms，切换图片0和1
    public BufferedImage getImage() {
            return Images.Hero[index++ % Images.Hero.length];
    }
    //子弹数量条件和位置
    public Bullet[] bullet() {
        int xstep = this.width/4;
        int ystep = 20;
        if(fire>0) {
            Bullet[] bullet = new Bullet[2];
            bullet[0] = new Bullet(this.x+1*xstep,this.y- ystep);
            bullet[1] = new Bullet(this.x+3*xstep,this.y- ystep);
            fire-=2;
            return bullet;
        }else {
            Bullet[] bullet = new Bullet[1];
            bullet[0] = new Bullet(this.x+2*xstep,this.y- ystep);
            return bullet;
        }
    }
    public int getHP(int cost) {
        if(HP<=0)
            state=DEAD;
        HP=HP-cost;
        return HP;
    }
    public void setHP(int HP) {
        HERO.HP = HP;
    }
    public int getMP(int cost) {
        if(MP>=0&&MP<100){
            MP+=1;
        }else if(MP<0){
            MP=0;
        }else {
            MP=100;
        }
        MP=MP-cost;
        return MP;
    }
    public void setMP(int MP) {
        HERO.MP = MP;
    }
    public void step() {
    }
    //鼠标移动的坐标设置
    public void moveTo(int x,int y) {
        this.x=x-this.width/2;
        this.y=y-this.height/2;
    }
    public void moveUp(){
        if(y>-height/2) {
            y -= speed;
        }else{
            y+=(World.HEIGHT+height/2);
        }
    }
    public void moveDown(){
        if(y<World.HEIGHT-height/2) {
            y += speed;
        }else{
            y-=(World.HEIGHT+height/2);
        }
    }
    public void moveRight(){
        if(x<World.WIDTH-width/2) {
            x += speed;
        }else{
            x-=(World.WIDTH+width/2);
        }
    }
    public void moveLeft(){
        if(x>-width/2) {
            x -= speed;
        }else{
            x+=(World.WIDTH+width/2);
        }
    }
    //碰撞条件
    public boolean hitEnemy(FlyingObject object) {
        int x1=this.x-object.width;
        int x2=this.x+this.width;
        int y1=this.y-object.height;
        int y2=this.y+this.height;
        int x=object.x;
        int y=object.y;
        return x>x1&&x<=x2&&y>y1&&y<=y2;
    }
}

