package main;

public class Task {

    private Instruction instruction;
    private SequentialVector sequentialVector;
    private double parameter;
    private SequentialVector parameterVector;
    private SequentialVector maskVector;
    private boolean executed;
    private int order;

    public Task(int o, Instruction i, SequentialVector v, double d) {
        order = o;
        instruction = i;
        sequentialVector = v;
        parameter = d;
        executed = false;
    }

    public Task(int o, Instruction i, SequentialVector aVector, SequentialVector anotherVector) {
        order = o;
        instruction = i;
        sequentialVector = aVector;
        parameterVector = anotherVector;
    }

    public Task(int o, Instruction i, SequentialVector v) {
        order = o;
        instruction = i;
        sequentialVector = v;
    }

    public Task(int o, Instruction i, SequentialVector a, SequentialVector b, SequentialVector m) {
        order = o;
        instruction = i;
        sequentialVector = a;
        parameterVector = b;
        maskVector = m;
    }

    public double parameter() {
        return parameter;
    }

    public SequentialVector parameterVector() {
        return parameterVector;
    }

    public SequentialVector maskVector() {
        return maskVector;
    }

    public Instruction instruction() {
        return instruction;
    }

    public SequentialVector sequentialVector() {
        return sequentialVector;
    }

    public void execute() {
        executed = true;
    }

    public int order() {
        return order;
    }
}
