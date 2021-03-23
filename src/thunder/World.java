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
    //����ʱ������ѭ������
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
    //��ʱ�������̡������Ҫ����
    public void action() {
        //������
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                hero.moveTo(x, y);
            }
            //����뿪���ں���ͣ
            public void mouseExited(MouseEvent e){
                if(state == RUNNING)
                    state = PAUSE;
            }
            //����뿪���ں�����
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
        this.addMouseListener(mouseAdapter);//��������¼�
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
        int interval =10;//����ʱ����
        time.schedule(new TimerTask(){  //ʹ�������ڲ���������дrun����
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
        }, interval, interval);//��ʼ��һ��ǰ�μ�����ڶ����Ժ�ļ��
    }
    //��Ϸ��ʼʱ���̿���
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
    //��Ϸ����ʱ���̿���
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
    //��Ϸ��ͣʱ���̿���
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
    //��Ϸ����ʱ���̿���
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
    //�볡�����������볡�ٶ�
    public void enter() {
        index++;
        if(index%50==0) {
            FlyingObject obj = nextOne();
            enemies = Arrays.copyOf(enemies, enemies.length+1);//��������
            enemies[enemies.length-1] = obj;//���һ��������ĩ
        }
    }
    //�����������������
    public void shoot() {
        shootIndex++;
        if(shootIndex%20==0) {
            Bullet[] bu = hero.bullet();
            bullets = Arrays.copyOf(bullets, bullets.length+bu.length);//��������
            System.arraycopy(bu, 0, bullets, bullets.length-bu.length, bu.length);//����׷��
            hero.getMP(10);
        }
    }
    //���С���ײ��Ķ���
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
    //ͼƬ��������ÿ�����ˡ�ÿ���ӵ����˶�
    public void steps() {
        sky.step();
        for (int i = 0; i < enemies.length; i++) {
            enemies[i].step();
        }
        for (int i = 0; i < bullets.length; i++) {
            bullets[i].step();
        }
    }
    //��������볡����
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
    //�����෽������дpaint��������ȡÿ�����ˡ��ӵ�
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
        //������ͣ������ͼ
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
        //���Ʒ���
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
        JFrame frame = new JFrame("�׵�I");//����һ��JFrame�������ñ���
        World world = new World();
        //JLabel l = new JLabel();//ʹ�ñ�ǩ���gif
        //Icon icon = new ImageIcon("src/thunder/bg.gif");
        //l.setIcon(icon);
        //world.add(l);
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ڹرչ���
        frame.setSize(WIDTH,HEIGHT);//���ڴ�С
        frame.setLocationRelativeTo(null); //���ô��������ָ�������λ�ã���������ǰδ��ʾ����Ϊ null����˴��ڽ�������Ļ������
        frame.setVisible(true); //���ڿ��ӻ�,�������paint����
        world.action();
    }

}
