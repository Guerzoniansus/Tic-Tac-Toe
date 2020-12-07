package tictactoe;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioPlayer {

    public static Map<String, Sound> soundMap = new HashMap<String, Sound>();

    public static Music music;

    public static boolean isMusicMuted;
    public static boolean isTikMuted;

    public static void load() {

        try {
            isTikMuted = false;
            isMusicMuted = false;

            soundMap.put("tik", new Sound("res/tik.ogg"));
            soundMap.put("pling", new Sound("res/pling.ogg"));
            soundMap.put("game_over", new Sound("res/game_over.ogg"));
            soundMap.put("click", new Sound("res/click.ogg"));
            soundMap.put("swoosh", new Sound("res/swoosh.ogg"));

            music = new Music("res/music.ogg");
            music.loop(1, 0.2f);

        } catch(SlickException e) {
            e.printStackTrace();
        }

    }

    public static void playSound(String soundName) {
        Sound sound = soundMap.get(soundName);
        sound.play();
    }

    public static void playSound(String soundName, float volume) {
        Sound sound = soundMap.get(soundName);

        if (isTikMuted) {
            if (sound == soundMap.get("tik"))
                return;
        }

        sound.play(1, volume);
    }

    public static void toggleTikMute() {
        if (isTikMuted) {
            isTikMuted = false;
        }

        else {
            isTikMuted = true;
        }

        playSound("swoosh");
    }

    public static void toggleMusicMute() {
        if (isMusicMuted) {
            isMusicMuted = false;
            resumeMusic();
        }

        else {
            isMusicMuted = true;
            pauseMusic();
        }

        playSound("swoosh");
    }

    public static void pauseMusic() {
        music.pause();
    }

    public static void resumeMusic() {
        music.resume();
    }
}
