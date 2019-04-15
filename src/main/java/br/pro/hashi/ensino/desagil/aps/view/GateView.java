package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GateView extends JPanel implements ItemListener {
    private final Switch[] switches;
    private final Gate gate;

    private final JCheckBox[] inputBoxes;
    private final JCheckBox outputBox;
    public GateView(Gate gate) {
        this.gate = gate;


        int inputSize = gate.getInputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }

        outputBox = new JCheckBox();

        JLabel inputLabel = new JLabel("Input");
        JLabel outputLabel = new JLabel("Output");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(inputLabel);
        for (JCheckBox inputBox : inputBoxes) {
            add(inputBox);
        }
        add(outputLabel);
        add(outputBox);

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }

        outputBox.setEnabled(false);

        update();
    }

    private void update() {

        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        boolean result = gate.read();

        outputBox.setSelected(result);
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }
}