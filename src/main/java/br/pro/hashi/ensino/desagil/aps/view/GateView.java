package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GateView extends JPanel implements ActionListener {

    private final Gate gate;

    private final JCheckBox inputA;
    private final JCheckBox inputB;
    private final JCheckBox output;

    private final Switch switchA = new Switch();
    private final Switch switchB = new Switch();

    public GateView(Gate gate) {
        this.gate = gate;

        inputA = new JCheckBox("Input A");
        inputB = new JCheckBox("Input B");
        output = new JCheckBox("Output");
        output.setEnabled(false);

        // Criando labels por estética:
        JLabel inputLabel = new JLabel("Inputs");
        JLabel outputLabel = new JLabel("Output");

        inputA.addActionListener(this);
        inputB.addActionListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if (gate.getInputSize() == 2) {
            add(inputLabel);
            add(inputA);
            add(inputB);
            add(outputLabel);
            add(output);

            this.gate.connect(0, switchA);
            this.gate.connect(1, switchB);
        } else {
            add(inputLabel);
            add(inputA);
            add(outputLabel);
            add(output);

            this.gate.connect(0, switchA);
        }

        update();
    }

    private void update() {
        if (inputA.isSelected()) {
            switchA.turnOn();
        } else {
            switchA.turnOff();
        }

        if (inputB.isSelected()) {
            switchB.turnOn();
        } else {
            switchB.turnOff();
        }

        output.setSelected(this.gate.read());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }
}