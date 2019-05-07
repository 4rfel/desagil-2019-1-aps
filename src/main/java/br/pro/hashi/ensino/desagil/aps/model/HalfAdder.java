package br.pro.hashi.ensino.desagil.aps.model;

public class HalfAdder extends Gate {

    public NandGate nandOne;
    public NandGate nandTop;
    public NandGate nandBottom;
    public NandGate nandSum;
    public NandGate nandCarry;


    public HalfAdder() {
        super("HalfAdder", 2, 2);

        nandOne = new NandGate();

        nandTop = new NandGate();

        nandBottom = new NandGate();

        nandCarry = new NandGate();
        nandCarry.connect(0, nandTop);
        nandCarry.connect(1, nandBottom);

        nandSum = new NandGate();
        nandSum.connect(0, nandCarry);
        nandSum.connect(1, nandOne);

    }

    @Override
    public boolean read(int outputPin){
        if (outputPin != 0 & outputPin != 1) {
            throw new IndexOutOfBoundsException(outputPin);
        }
        if (outputPin == 1) {
            return nandSum.read();
        } else {
            return nandCarry.read();
        }
    }

    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        switch (inputPin) {
            case 0:
                nandOne.connect(0, emitter);

                nandTop.connect(0, emitter);
                nandTop.connect(1, emitter);
                break;
            case 1:
                nandOne.connect(1, emitter);

                nandBottom.connect(0, emitter);
                nandBottom.connect(1, emitter);
                break;
            default:
                throw new IndexOutOfBoundsException(inputPin);
        }
    }
}
