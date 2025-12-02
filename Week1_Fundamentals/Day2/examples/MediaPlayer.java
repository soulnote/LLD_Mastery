/**
 * Abstraction Example: Media Player System
 * Demonstrates abstract classes, interfaces, and method overloading
 */

// Media interface - complete abstraction
interface Playable {
    void play();
    void pause();
    void stop();
    double getDuration();
}

// Volume control interface
interface VolumeControl {
    void setVolume(int volume);
    int getVolume();
    void mute();
    void unmute();
}

// Abstract Media class - partial abstraction
abstract class Media implements Playable {
    protected String title;
    protected String artist;
    protected double duration;
    protected boolean isPlaying;
    protected boolean isPaused;
    
    public Media(String title, String artist, double duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.isPlaying = false;
        this.isPaused = false;
    }
    
    // Common implementation
    @Override
    public void pause() {
        if (isPlaying && !isPaused) {
            isPaused = true;
            System.out.println("⏸️ Paused: " + title);
        } else {
            System.out.println("❌ Cannot pause - not currently playing");
        }
    }
    
    @Override
    public void stop() {
        if (isPlaying || isPaused) {
            isPlaying = false;
            isPaused = false;
            System.out.println("⏹️ Stopped: " + title);
        } else {
            System.out.println("❌ Already stopped");
        }
    }
    
    @Override
    public double getDuration() {
        return duration;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract String getMediaType();
    
    // Method overloading examples
    public void displayInfo() {
        displayInfo(false);
    }
    
    public void displayInfo(boolean detailed) {
        System.out.println("🎵 " + title + " by " + artist);
        if (detailed) {
            System.out.println("   Type: " + getMediaType());
            System.out.println("   Duration: " + duration + " minutes");
            System.out.println("   Status: " + getStatus());
        }
    }
    
    public void displayInfo(String format) {
        if ("json".equalsIgnoreCase(format)) {
            System.out.println("{\"title\":\"" + title + "\",\"artist\":\"" + artist + "\",\"duration\":" + duration + "}");
        } else {
            displayInfo(true);
        }
    }
    
    private String getStatus() {
        if (isPlaying && !isPaused) return "Playing";
        if (isPaused) return "Paused";
        return "Stopped";
    }
}

// Audio implementation
class AudioTrack extends Media implements VolumeControl {
    private String format; // MP3, WAV, FLAC
    private int volume;
    private boolean isMuted;
    
    public AudioTrack(String title, String artist, double duration, String format) {
        super(title, artist, duration);
        this.format = format;
        this.volume = 50; // Default volume
        this.isMuted = false;
    }
    
    @Override
    public void play() {
        if (!isPlaying) {
            isPlaying = true;
            isPaused = false;
            System.out.println("🎵 Playing audio: " + title + " (" + format + ")");
            System.out.println("   Volume: " + (isMuted ? "Muted" : volume + "%"));
        } else if (isPaused) {
            isPaused = false;
            System.out.println("▶️ Resumed: " + title);
        } else {
            System.out.println("❌ Already playing");
        }
    }
    
    @Override
    public String getMediaType() {
        return "Audio (" + format + ")";
    }
    
    @Override
    public void setVolume(int volume) {
        if (volume >= 0 && volume <= 100) {
            this.volume = volume;
            this.isMuted = false;
            System.out.println("🔊 Volume set to " + volume + "%");
        } else {
            System.out.println("❌ Invalid volume. Must be 0-100");
        }
    }
    
    @Override
    public int getVolume() {
        return isMuted ? 0 : volume;
    }
    
    @Override
    public void mute() {
        isMuted = true;
        System.out.println("🔇 Audio muted");
    }
    
    @Override
    public void unmute() {
        isMuted = false;
        System.out.println("🔊 Audio unmuted - Volume: " + volume + "%");
    }
}

// Video implementation
class VideoFile extends Media {
    private String resolution;
    private String codec;
    
    public VideoFile(String title, String artist, double duration, String resolution, String codec) {
        super(title, artist, duration);
        this.resolution = resolution;
        this.codec = codec;
    }
    
    @Override
    public void play() {
        if (!isPlaying) {
            isPlaying = true;
            isPaused = false;
            System.out.println("🎬 Playing video: " + title);
            System.out.println("   Resolution: " + resolution);
            System.out.println("   Codec: " + codec);
        } else if (isPaused) {
            isPaused = false;
            System.out.println("▶️ Resumed video: " + title);
        } else {
            System.out.println("❌ Already playing");
        }
    }
    
    @Override
    public String getMediaType() {
        return "Video (" + resolution + ", " + codec + ")";
    }
    
    // Video-specific methods
    public void changeResolution(String newResolution) {
        this.resolution = newResolution;
        System.out.println("📺 Resolution changed to " + newResolution);
    }
}

// Playlist - demonstrates composition and polymorphism
class Playlist {
    private String name;
    private java.util.List<Media> mediaList;
    
    public Playlist(String name) {
        this.name = name;
        this.mediaList = new java.util.ArrayList<>();
    }
    
    public void addMedia(Media media) {
        mediaList.add(media);
        System.out.println("➕ Added to playlist '" + name + "': " + media.title);
    }
    
    public void playAll() {
        System.out.println("\n🎵 Playing playlist: " + name);
        for (Media media : mediaList) {
            media.play(); // Polymorphism in action!
            try {
                Thread.sleep(1000); // Simulate playing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            media.stop();
        }
    }
    
    public void showPlaylist() {
        System.out.println("\n📋 Playlist: " + name);
        for (int i = 0; i < mediaList.size(); i++) {
            System.out.print((i + 1) + ". ");
            mediaList.get(i).displayInfo();
        }
    }
}