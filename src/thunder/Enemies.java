package thunder;

public class Enemies {
    protected int xstep;
    protected int ystep;
    protected int x;
    protected int y;
    public void step(){}
    public void shoot(){}
    //敌人射击
    public Bullet[] bullet() {
        Bullet[] bullet = new Bullet[1];
        bullet[0] = new Bullet(this.x+2*xstep,this.y- ystep);
        return bullet;
    }
}
