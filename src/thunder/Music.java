package thunder;

import javax.swing.*;
import java.applet.AudioClip;
import java.io.File;

public class Music {
    public static java.applet.AudioClip musics[]=new AudioClip[2];
    private static int chapter;
    static {
        musics[0] =  readMusic("music/music0.wav");
        musics[1] =  readMusic("music/music1.wav");
    }
    public static AudioClip readMusic(String fileName){
        try{
            AudioClip img = JApplet.newAudioClip(new File(fileName).toURI().toURL());
            return img;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    public static void musicStop(){
        for (int i=0;i<musics.length;i++){
            musics[i].stop();
        }
    }
    //播放下一首音乐
    public static int musicPlayNext(){
        chapter=(chapter==musics.length - 1?0:(++chapter));
        musics[chapter].loop();
        return chapter;
    }
    //播放上一首音乐
    public static int musicPlayLast(){
        chapter=((chapter==0)?musics.length - 1:(--chapter));
        musics[chapter].loop();
        return chapter;
    }
}
