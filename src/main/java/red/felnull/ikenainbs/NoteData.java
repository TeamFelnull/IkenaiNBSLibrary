package red.felnull.ikenainbs;

public class NoteData {
    private final InstrumentType instrumentType;
    private final int key;
    private final int velocity;
    private final int panning;
    private final int pitch;

    public NoteData(InstrumentType instrumentType, int key, int velocity, int panning, int pitch) {
        this.instrumentType = instrumentType;
        this.key = key;
        this.velocity = velocity;
        this.panning = panning;
        this.pitch = pitch;
    }

    public InstrumentType getInstrumentType() {
        return instrumentType;
    }

    public int getKey() {
        return key;
    }

    public int getPanning() {
        return panning;
    }

    public int getPitch() {
        return pitch;
    }

    public int getVelocity() {
        return velocity;
    }

    public static enum InstrumentType {
        PIANO(0, "block.note_block.harp"),
        DOUBLE_BASS(1, "block.note_block.bass"),
        BASS_DRUM(2, "block.note_block.basedrum"),
        SNARE_DRUM(3, "block.note_block.snare"),
        CLICK(4, "block.note_block.hat"),
        GUITAR(5, "block.note_block.guitar"),
        FLUTE(6, "block.note_block.flute"),
        BELL(7, "block.note_block.bell"),
        CHIME(8, "block.note_block.chime"),
        XYLOPHONE(9, "block.note_block.xylophone"),
        IRON_XYLOPHONE(10, "block.note_block.iron_xylophone"),
        COW_BELL(11, "block.note_block.cow_bell"),
        DIDGERIDOO(12, "block.note_block.didgeridoo"),
        BIT(13, "block.note_block.bit"),
        BANJO(14, "block.note_block.banjo"),
        PLING(15, "block.note_block.pling");

        private final int number;
        private final String registryName116;

        private InstrumentType(int number, String registryName116) {
            this.number = number;
            this.registryName116 = registryName116;
        }

        public int getNumber() {
            return number;
        }

        public String getRegistryName116() {
            return registryName116;
        }

        public static InstrumentType getTypeByNumber(int number) {
            for (InstrumentType value : values()) {
                if (value.getNumber() == number)
                    return value;
            }
            throw new IllegalStateException("Non existent instrument type");
        }

        public static InstrumentType getTypeByRegistryName116(String name) {
            for (InstrumentType value : values()) {
                if (value.getRegistryName116().equals(name))
                    return value;
            }
            throw new IllegalStateException("Non existent instrument type");
        }
    }
}
