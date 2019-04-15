package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener, MouseListener {
    private final Switch[] switches;
    private final Gate gate;

    private final JCheckBox[] inputBoxes;
    private final JCheckBox outputBox;

    // Novos atributos necessários para esta versão da interface.
    private final Image image;
    private Color color;
    private Light light;
    private int R = 255;
    private int G = 0;
    private int B = 0;

    public GateView(Gate gate) {

        // Como subclasse de FixedPanel, esta classe agora
        // exige que uma largura e uma altura sejam fixadas.
        super(250, 220);

        light = new Light();
//        int R = 255;
//        int G = 0;
//        int B = 0;

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

        boolean i = true;
        for (JCheckBox inputBox : inputBoxes) {
            if (inputSize == 1) {
                add(inputBox, 10, 220 / 2 - 7, 15, 15);
            }
            if (inputSize == 2) {
                if (i) {
                    add(inputBox, 10, 66, 15, 15);
                    i = false;
                } else {
                    add(inputBox, 10, 148, 15, 15);
                }
            }
        }
//        add(outputBox,250,220/2-25, 50,50);
        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }

        outputBox.setEnabled(false);

//        color = Light.getR();

        // Usamos esse carregamento nos Desafios, vocês lembram?
        // imagens cortesia do rafael almada
        String name = gate.toString() + ".jpg";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        // Toda componente Swing tem uma lista de observadores
        // que reagem quando algum evento de mouse acontece.
        // Usamos o método addMouseListener para adicionar a
        // própria componente, ou seja "this", nessa lista.
        // Só que addMouseListener espera receber um objeto
        // do tipo MouseListener como parâmetro. É por isso que
        // adicionamos o "implements MouseListener" lá em cima.
        addMouseListener(this);
//
//        weightField.addActionListener(this);
//        radiusField.addActionListener(this);
//
//        resultField.setEnabled(false);
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

        light.connect(0, gate);

        light.setR(R);
        light.setG(G);
        light.setB(B);

        int r = light.getR();
        int g = light.getG();
        int b = light.getB();
        color = new Color(r, g, b);
        repaint();


    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        // Se o clique foi dentro do quadrado colorido...
        int centerX = 210 + 15 / 2;
        int centerY = 220 / 2 - 15 / 2;
//        if (x >= 210 && x < 210+15 && y >= 220/2-15/2&& y < 220/2) {
        if ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) <= 15 * 15) {
            // ...então abrimos a janela seletora de cor...
            color = JColorChooser.showDialog(this, null, color);
            // ...e chamamos repaint para atualizar a tela.
            R = color.getRed();
            G = color.getGreen();
            B = color.getBlue();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 0, 0, 221, 221, this);

        // Desenha um quadrado cheio.
        g.setColor(color);
        g.fillOval(210, 220 / 2 - 15 / 2, 15, 15);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}
