package solutions;

interface   MediaPlayer{
    void play(String source);
    void pause();
    boolean isPlaying();
}

interface MediaDownload{
    void download(String sourceUrl);
}


interface MediaStream{
    void streamLive(String Url);
}

interface MediaRecord{
    void record(String destination);
}

class VideoPlayer implements MediaPlayer,MediaStream,MediaRecord{

    private boolean playing = false;
    private boolean liveStarted = false;

    @Override
    public void play(String source) {
        if(!liveStarted){
            System.out.println("[WARN] playing without live stream started.");
        }
        playing = true;
    }

    @Override
    public void pause() {
        playing = false;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public void record(String destination) {
        // recording
    }

    @Override
    public void streamLive(String Url) {
        liveStarted = true;
    }
}

class AudioPlayer implements MediaPlayer,MediaDownload{

    private boolean playing = false;

    @Override
    public void download(String sourceUrl) {
        //download
    }

    @Override
    public void play(String source) {
        playing = true;
    }

    @Override
    public void pause() {
        playing = false;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }
}



public class MediaLSTIspSolution {
    public static void main(String[] args) {

        AudioPlayer ap = new AudioPlayer();
        ap.play("song.mp3");
        System.out.println("Audio playing: " + ap.isPlaying());
        ap.pause();

        VideoPlayer cam = new VideoPlayer();
        cam.streamLive("rtsp://camera");
        cam.play("rtsp://camera");
        cam.play("rtsp://camera");


    }
}
