public class Task {

    private Instruction instruction;
    private SequentialVector sequentialVector;
    private double parameter;
    private SequentialVector parameterVector;

    public Task(Instruction i, SequentialVector v, double d) {
        this.instruction = i;
        this.sequentialVector = v;
        this.parameter = d;
    }

    public Task(Instruction i, SequentialVector aVector, SequentialVector anotherVector) {
        this.instruction = i;
        this.sequentialVector = aVector;
        this.parameterVector = anotherVector;
    }

    public Task(Instruction i, SequentialVector v) {
        this.instruction = i;
        this.sequentialVector = v;
    }

    public double parameter() {
        return parameter;
    }

    public SequentialVector parameterVector() {
        return parameterVector;
    }

    public Instruction instruction() {
        return instruction;
    }

    public SequentialVector sequentialVector() {
        return sequentialVector;
    }
}
