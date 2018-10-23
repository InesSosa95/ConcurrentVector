public class Task {

    private Instruction instruction;
    private SequentialVector sequentialVector;
    private double parameter;

    public Task(Instruction i, SequentialVector v, double d) {
        this.instruction = i;
        this.sequentialVector = v;
        this.parameter = d;
    }

    public double parameter() {
        return parameter;
    }

    public Instruction instruction() {
        return instruction;
    }

    public SequentialVector sequentialVector() {
        return sequentialVector;
    }
}
