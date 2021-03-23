package thunder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;

public class World extends JLabel{
    //Button play,stop;
    public static final int WIDTH = 1466;
    public static final int HEIGHT = 1100;
    public static final int GAME_STAR = -1;
    public static final int RUNNING = 0;
    public static final int PAUSE = 1;
    public static final int GAME_OVER = 2;
    private int state;

    private Sky sky = new Sky();
    private HERO hero = new HERO();
    private FlyingObject[] enemies = {};
    private Bullet[] bullets = {};

    private int index = 0;
    private int shootIndex = 0;
    private int score=0;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean shoot = false;
    private boolean pellet = false;
    //按计时器进行循环动作
    public void isOutOfBounds(){
        for (int i=0;i<enemies.length;i++) {
            if (enemies[i].isOutOfBounds()||enemies[i].isRemove()) {
                enemies[i] = enemies[enemies.length - 1];
                enemies = Arrays.copyOf(enemies, enemies.length - 1);
            }
        }
        for (int i=0;i<bullets.length;i++){
            if (bullets[i].isOutOfBounds()) {
                bullets[i]=bullets[bullets.length-1];
                bullets=Arrays.copyOf(bullets,bullets.length-1);
            }
        }
    }
    //计时器、键盘、鼠标主要动作
    public void action() {
        //鼠标控制
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                hero.moveTo(x, y);
            }
            //鼠标离开窗口后暂停
            public void mouseExited(MouseEvent e){
                if(state == RUNNING)
                    state = PAUSE;
            }
            //鼠标离开窗口后运行
            public void mouseEntered(MouseEvent e){
                if(state == PAUSE)
                    state = RUNNING;
            }
            public void mousePressed(MouseEvent e) {
                int key = e.getButton();
                switch (key) {
                    case MouseEvent.BUTTON1: shoot = true;break;
                    case MouseEvent.BUTTON3: pellet = true;break;
                }
            }
            public void mouseReleased(MouseEvent e) {
                int key = e.getButton();
                switch (key) {
                    case MouseEvent.BUTTON1:
                        shoot = false;
                        break;
                    case MouseEvent.BUTTON3:
                        pellet = false;
                        break;
                }
            }
        };
        this.addMouseListener(mouseAdapter);//处理鼠标事件
        this.addMouseMotionListener(mouseAdapter);
        state = GAME_STAR;
        this.requestFocus();
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (state) {
                    case GAME_STAR:processStartKey(key);break;
                    case RUNNING:processRunningKey(key);break;
                    case PAUSE:processPauseKey(key);break;
                    case GAME_OVER:processGameOverKey(key);break;
                }
                        if (up && !down && !left && !right)
                            hero.moveUp();
                        else if (!up && down & !left && !right)
                            hero.moveDown();
                        else if (!up && down & left && !right) {
                            hero.moveDown();
                            hero.moveLeft();
                        } else if (up && !down & !left && right) {
                            hero.moveUp();
                            hero.moveRight();
                        } else if (up && !down & left && !right) {
                            hero.moveUp();
                            hero.moveLeft();
                        } else if (!up && down & !left && right) {
                            hero.moveDown();
                            hero.moveRight();
                        } else if (!up && !down & left && !right) {
                            hero.moveLeft();
                        } else if (!up && !down & !left && right) {
                            hero.moveRight();
                        }
                        repaint();
                }
            public void keyReleased (KeyEvent e){
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_A:
                        left = false;
                        break;
                    case KeyEvent.VK_W:
                        up = false;
                        break;
                    case KeyEvent.VK_S:
                        down = false;
                        break;
                    case KeyEvent.VK_D:
                        right = false;
                        break;
                }
            }
        });
        Timer time = new Timer();
        int interval =10;//定义时间间隔
        time.schedule(new TimerTask(){  //使用匿名内部方法，重写run方法
            public void run() {
                if(state==RUNNING) {
                enter();
                //if(shoot)
                shoot();
                isOutOfBounds();
                BulletIsShoot();
                steps();
                if(hero.getHP(0)<=0)
                    state=GAME_OVER;
                }
                repaint();
            }
        }, interval, interval);//开始第一次前段间隔，第二次以后的间隔
    }
    //游戏开始时键盘控制
    public void processStartKey(int key) {
        switch (key){
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_SPACE:
                state = RUNNING;
                break;
        }
    }
    //游戏结束时键盘控制
    public void processGameOverKey(int key) {
        switch (key){
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_SPACE:
                state = RUNNING;
                break;
        }
    }
    //游戏暂停时键盘控制
    public void processPauseKey(int key) {
        switch (key){
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_P:
                state = PAUSE;
                break;
            case KeyEvent.VK_C:
                state = RUNNING;
                break;
        }
    }
    //游戏运行时键盘控制
    public void processRunningKey(int key) {
        switch (key) {
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            case KeyEvent.VK_P:
                state = PAUSE;
                break;
            case KeyEvent.VK_SPACE:
                new Music().musicStop();
                Music.musics[0].loop();
                break;
            case KeyEvent.VK_LEFT:
                new Music().musicStop();
                Music.musicPlayLast();
                break;
            case KeyEvent.VK_RIGHT:
                new Music().musicStop();
                Music.musicPlayNext();
                break;
        }
    }
    //入场动作，调整入场速度
    public void enter() {
        index++;
        if(index%50==0) {
            FlyingObject obj = nextOne();
            enemies = Arrays.copyOf(enemies, enemies.length+1);//数组扩容
            enemies[enemies.length-1] = obj;//添加一个于数组末
        }
    }
    //射击动作，调整射速
    public void shoot() {
        shootIndex++;
        if(shootIndex%20==0) {
            Bullet[] bu = hero.bullet();
            bullets = Arrays.copyOf(bullets, bullets.length+bu.length);//数组扩容
            System.arraycopy(bu, 0, bullets, bullets.length-bu.length, bu.length);//数组追加
            hero.getMP(10);
        }
    }
    //射中、碰撞后的动作
    public void BulletIsShoot(){
        for(int i=0;i<bullets.length;i++){
            Bullet b=bullets[i];
            for(int j=0;j<enemies.length;j++){
                FlyingObject f=enemies[j];
                if (b.isLife()&&f.isLife()&&f.isHit(b)){
                    f.goDead();
                    b.goDead();
                    if (f instanceof EnemyScore){
                        EnemyScore es=(EnemyScore)f;
                        score+=es.getScore();
                    }
                }
                if(hero.isLife()&&f.isLife()&&f.isHit(hero)){
                    f.goDead();
                    hero.getHP(10);
                    if (f instanceof EnemyScore) {
                        EnemyScore es = (EnemyScore) f;
                        score += es.getScore();
                    }
                }
            }
        }
    }
    //图片（背景、每个敌人、每发子弹）运动
    public void steps() {
        sky.step();
        for (int i = 0; i < enemies.length; i++) {
            enemies[i].step();
        }
        for (int i = 0; i < bullets.length; i++) {
            bullets[i].step();
        }
    }
    //随机敌人入场动作
    public FlyingObject nextOne() {
        Random random =new Random();
        int type = random.nextInt(4);
        if(type==0) {
            return new Bee();
        }else if(type==1) {
            return new Airplane();
        }else {
            return new BigAirplane();
        }
    }
    //抽象类方法，重写paint方法，获取每个敌人、子弹
    public void paint(Graphics g) {
        g.drawImage(sky.getImage(),sky.x,sky.y,null);
        g.drawImage(sky.getImage(),sky.x,sky.getY1(),null);
        g.drawImage(hero.getImage(),hero.x,hero.y,null);
        for(int i=0;i<enemies.length;i++) {
            FlyingObject f = enemies[i];
            g.drawImage(f.getImage(),f.x,f.y,null);
        }
        for(int i=0;i<bullets.length;i++) {
            FlyingObject b = bullets[i];
            g.drawImage(b.getImage(),b.x,b.y,null);
        }
        g.setColor(Color.RED);
        g.draw3DRect(42,28,100,10,true);
        g.fillRect(42,28,hero.getHP(0),10);
        g.setColor(Color.BLUE);
        g.draw3DRect(42,48,100,10,true);
        g.fillRect(42,48,hero.getMP(0),10);
        //绘制暂停、结束图
        switch (state) {
            case GAME_STAR:
                g.drawImage(sky.getGameStar(), 0, 0, null);
                break;
            case PAUSE:
                g.drawImage(sky.getPause(), 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(sky.getGameOver(), 0, 0, null);
                score=0;
                sky=new Sky();
                hero=new HERO();
                enemies=new FlyingObject[0];
                bullets=new Bullet[0];
                break;
        }
        //绘制分数
        int x = 0;
        int y = 20;
        int color = 0x800080;
        g.setColor(new Color(color));
        Font f =
                new Font(Font.SERIF,Font.BOLD,20);
        g.setFont(f);
        g.drawString("SCORE:"+score,x,y);
        y+=20;
        color = 0xFF0000;
        g.setColor(new Color(color));
        g.drawString("HP:",x,y);
        y+=20;
        color = 0x0000FF;
        g.setColor(new Color(color));
        g.drawString("MP:",x,y);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("雷电I");//创建一个JFrame对象，设置标题
        World world = new World();
        //JLabel l = new JLabel();//使用标签输出gif
        //Icon icon = new ImageIcon("src/thunder/bg.gif");
        //l.setIcon(icon);
        //world.add(l);
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗口关闭规则
        frame.setSize(WIDTH,HEIGHT);//窗口大小
        frame.setLocationRelativeTo(null); //设置窗口相对于指定组件的位置，如果组件当前未显示或者为 null，则此窗口将置于屏幕的中央
        frame.setVisible(true); //窗口可视化,尽快调用paint方法
        world.action();
    }

}
