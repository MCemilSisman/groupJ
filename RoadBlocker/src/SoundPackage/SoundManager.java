package SoundPackage;

import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
public class SoundManager
{   
    public void playSound(int sound) throws InterruptedException{
        final JFXPanel fxPanel = new JFXPanel();
        Sounds s = new Sounds();
        
        String bip = "";
        if( sound == 1 ){
            bip = s.mouseSoundAddress;
        }
        if( sound == 2){
            bip = s.collisionSoundAddress;
        }
        if( sound == 3){
            bip = s.gameMusicAddress;
        }
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        /*if(sound == 3){
            while(true){
                mediaPlayer.play(); 
                Thread.sleep(200000);
                mediaPlayer.stop();
            }
        }
        else{
            mediaPlayer.play(); 
        }*/
        mediaPlayer.play();
        
    }
            
}

       