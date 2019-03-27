package br.pro.hashi.ensino.desagil.aps.model;

public class OrGate extends Gate{
    private final NandGate[] nand;

    public OrGate(){
        super(2);
        nand = new NandGate[3];
        nand[0] = new NandGate();
        nand[1] = new NandGate();
        nand[2] = new NandGate();

        nand[2].connect(0, nand[0]);
        nand[2].connect(1, nand[1]);

    }

    @Override
    public boolean read(){
        return nand[2].read();
    }

    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        if (inputPin < 0 || inputPin > 1) {
            throw new IndexOutOfBoundsException(inputPin);
        }

        if (inputPin == 0) {
            nand[0].connect(0, emitter);
            nand[0].connect(1, emitter);
        }

        if (inputPin == 1) {
            nand[1].connect(0, emitter);
            nand[1].connect(1, emitter);
        }

    }
}