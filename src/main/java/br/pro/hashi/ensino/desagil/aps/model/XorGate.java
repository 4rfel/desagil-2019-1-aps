package br.pro.hashi.ensino.desagil.aps.model;


public class XorGate extends Gate{
    private final NandGate[] nand;

    public XorGate(){
        super(2);
        nand = new NandGate[4];
        nand[0] = new NandGate();
        nand[1] = new NandGate();
        nand[2] = new NandGate();
        nand[3] = new NandGate();

        nand[3].connect(0, nand[1]);
        nand[3].connect(1, nand[2]);

        nand[1].connect(1, nand[0]);
        nand[2].connect(0, nand[0]);

    }

    @Override
    public boolean read(){
        return nand[3].read();
    }

    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        if (inputPin < 0 || inputPin > 1) {
            throw new IndexOutOfBoundsException(inputPin);
        }

        if (inputPin == 0) {
            nand[0].connect(0, emitter);
            nand[1].connect(0, emitter);
        }
        if (inputPin == 1) {
            nand[0].connect(1, emitter);
            nand[2].connect(1, emitter);
        }
    }
}