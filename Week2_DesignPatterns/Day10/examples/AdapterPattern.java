/**
 * EXAMPLE: Adapter Pattern
 */
public class AdapterPattern {
    
    // Target interface
    interface MediaPlayer {
        void play(String filename);
    }
    
    // Adaptee - incompatible interface
    static class AdvancedMediaPlayer {
        public void playMp4(String file) {
            System.out.println("Playing MP4: " + file);
        }
        
        public void playVlc(String file) {
            System.out.println("Playing VLC: " + file);
        }
    }
    
    // Adapter
    static class MediaAdapter implements MediaPlayer {
        private AdvancedMediaPlayer advancedPlayer;
        
        public MediaAdapter() {
            this.advancedPlayer = new AdvancedMediaPlayer();
        }
        
        @Override
        public void play(String filename) {
            if (filename.endsWith(".mp4")) {
                advancedPlayer.playMp4(filename);
            } else if (filename.endsWith(".vlc")) {
                advancedPlayer.playVlc(filename);
            }
        }
    }
    
    public static void main(String[] args) {
        MediaPlayer player = new MediaAdapter();
        player.play("movie.mp4");
        player.play("movie.vlc");
    }
}
