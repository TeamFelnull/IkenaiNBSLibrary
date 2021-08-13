package red.felnull.ikenainbs;

import red.felnull.ikenainbs.util.DataUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
//https://github.com/leduyquang753/NBS-Player
//https://opennbs.org/nbs
public class NBS {
    private int nextByte;
    private final byte[] rawData;
    private final boolean old;
    private final int version;
    private final int instrumentCount;
    private final int length;
    private final int layerCount;
    private final String name;
    private final String author;
    private final String originalAuthor;
    private final String description;
    private final int tempo;
    private final boolean autoSaving;
    private final int autoSavingDuration;
    private final int timeSignature;
    private final int minutesSpent;
    private final int leftClicks;
    private final int rightClicks;
    private final int noteBlocksAdded;
    private final int noteBlocksRemoved;
    private final String importFileName;
    private final boolean loop;
    private final int maxLoopCount;
    private final int loopStartTick;
    private final Map<Integer, Layer> layers = new HashMap<>();

    public NBS(InputStream stream) throws IOException {
        this(DataUtils.readAllByte(stream));
    }

    public NBS(byte[] data) {
        this.rawData = data;
        boolean oldFlg1 = data[next()] != 0;
        boolean oldFlg2 = data[next()] != 0;
        this.old = oldFlg1 || oldFlg2;
        this.version = old ? -1 : data[next()] & 0xFF;
        this.instrumentCount = old ? 16 : data[next()] & 0xFF;
        if (old) nextByte = 0;
        this.length = readShort();
        this.layerCount = readShort();
        this.name = readString();
        this.author = readString();
        this.originalAuthor = readString();
        this.description = readString();
        this.tempo = readShort();
        this.autoSaving = readBoolean();
        this.autoSavingDuration = rawData[next()] & 0xFF;
        this.timeSignature = rawData[next()] & 0xFF;
        this.minutesSpent = readInt();
        this.leftClicks = readInt();
        this.rightClicks = readInt();
        this.noteBlocksAdded = readInt();
        this.noteBlocksRemoved = readInt();
        this.importFileName = readString();
        this.loop = !old && readBoolean();
        this.maxLoopCount = old ? 0 : rawData[next()] & 0xFF;
        this.loopStartTick = old ? 0 : readShort();

        Map<Integer, Map<Integer, NoteData>> notes = new HashMap<>();
        Set<Integer> layerIds = new HashSet<>();
        int tick = -1;
        while (true) {
            int jumpsTick = readShort();
            if (jumpsTick == 0)
                break;
            tick += jumpsTick;
            int layer = -1;
            while (true) {
                int jumpLayer = readShort();
                if (jumpLayer == 0)
                    break;
                layer += jumpLayer;
                int instrument = rawData[next()] & 0xFF;
                int key = rawData[next()] & 0xFF;
                int velocity = old ? 100 : rawData[next()] & 0xFF;
                int panning = old ? 100 : rawData[next()] & 0xFF;
                int pitch = old ? 0 : readSignedShort();

                layerIds.add(layer);

                if (!notes.containsKey(layer))
                    notes.put(layer, new HashMap<>());

                notes.get(layer).put(tick, new NoteData(NoteData.InstrumentType.getTypeByNumber(instrument), key, velocity, panning, pitch));
            }
        }
        for (Integer layerId : layerIds) {
            String name = readString();
            boolean lock = !old && readBoolean();
            int volume = rawData[next()] & 0xFF;
            int stereo = old ? 100 : rawData[next()] & 0xFF;
            layers.put(layerId, new Layer(name, lock, volume, stereo));
            if (notes.containsKey(layerId)) {
                layers.get(layerId).getNoteData().putAll(notes.get(layerId));
            }
        }
    }

    private boolean readBoolean() {
        return rawData[next()] >= 1;
    }

    private String readString() {
        int length = readInt();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = (char) rawData[next()];
            sb.append(ch);
        }
        return sb.toString();
    }

    private int readInt() {
        int num1 = rawData[next()] & 0xFF;
        int num2 = rawData[next()] & 0xFF;
        int num3 = rawData[next()] & 0xFF;
        int num4 = rawData[next()] & 0xFF;
        return num1 + (num2 << 8) + (num3 << 16) + (num4 << 24);
    }

    private int readSignedShort() {
        int num1 = rawData[next()] & 0xFF;
        int num2 = rawData[next()] & 0xFF;
        return (num1) + (num2 << 8);
    }

    private int readShort() {
        int num1 = rawData[next()];
        int num2 = rawData[next()];
        return (num1) + (num2 << 8);
    }

    private int next() {
        return nextByte++;
    }

    public byte[] getRawData() {
        return rawData;
    }

    public int getVersion() {
        return version;
    }

    public boolean isOld() {
        return old;
    }

    public int getInstrumentCount() {
        return instrumentCount;
    }

    public int getLength() {
        return length;
    }

    public int getLayerCount() {
        return layerCount;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getOriginalAuthor() {
        return originalAuthor;
    }

    public String getDescription() {
        return description;
    }

    public int getTempo() {
        return tempo;
    }

    public boolean isAutoSaving() {
        return autoSaving;
    }

    public int getAutoSavingDuration() {
        return autoSavingDuration;
    }

    public int getTimeSignature() {
        return timeSignature;
    }

    public int getMinutesSpent() {
        return minutesSpent;
    }

    public int getLeftClicks() {
        return leftClicks;
    }


    public int getRightClicks() {
        return rightClicks;
    }

    public int getNoteBlocksAdded() {
        return noteBlocksAdded;
    }

    public int getNoteBlocksRemoved() {
        return noteBlocksRemoved;
    }

    public String getImportFileName() {
        return importFileName;
    }

    public boolean isLoop() {
        return loop;
    }

    public int getMaxLoopCount() {
        return maxLoopCount;
    }

    public int getLoopStartTick() {
        return loopStartTick;
    }

    public Map<Integer, Layer> getLayers() {
        return layers;
    }
}
