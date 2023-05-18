package gui;

import javax.swing.*;
import java.awt.*;

public class CreatorTimer extends JFrame {
    private static final int DELAY_MS = 50; // частота обновления времени в миллисекундах

    private int lifeTimeSeconds = 1400; // текущее время жизни робота в секундах
    JProgressBar progressBar;

    public CreatorTimer() {
        super("Robot Life Timer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null);

        progressBar = new JProgressBar();
        progressBar.setMaximum(1400);
        add(progressBar, BorderLayout.NORTH);

        // создаем таймер для обновления времени
        Timer timer = new Timer(DELAY_MS, e -> {
            progressBar.setValue(lifeTimeSeconds);
            if (lifeTimeSeconds <= 0) {
                ((Timer)e.getSource()).stop();// останавливаем таймер при истечении времени жизни робота
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public void setTTL(int ttl){
        System.out.println(lifeTimeSeconds);
        this.lifeTimeSeconds= ttl;
    }
}
