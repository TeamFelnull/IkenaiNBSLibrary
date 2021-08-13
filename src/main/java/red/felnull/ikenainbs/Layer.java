package red.felnull.ikenainbs;

import java.util.HashMap;
import java.util.Map;

public class Layer {
    private final Map<Integer,NoteData> noteData = new HashMap<>();
    private final String name;
    private final boolean lock;
    private final int volume;
    private final int stereo;

    public Layer(String name, boolean lock, int volume, int stereo) {
        this.name = name;
        this.lock = lock;
        this.volume = volume;
        this.stereo = stereo;
    }

    public boolean isLock() {
        return lock;
    }

    public String getName() {
        return name;
    }


    public int getStereo() {
        return stereo;
    }

    public int getVolume() {
        return volume;
    }

    public Map<Integer, NoteData> getNoteData() {
        return noteData;
    }
}
