public interface Serializable {
    void saveState(String fileName);
    void loadState(String fileName);

    abstract class SerializableAdapter implements Serializable {
        @Override
        public void saveState(String fileName) {
            // Default implementation for saveState
        }

        @Override
        public void loadState(String fileName) {
            // Default implementation for loadState
        }
    }
}