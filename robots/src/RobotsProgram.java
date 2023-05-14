import gui.MainApplicationFrame;
import gui.state.LocalizationState;
import gui.state.SaveState;
import localization.Localization;

import java.awt.Frame;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram {
    public static void main(String[] args) {
        initlang();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame();
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }

    public static void initlang() {
        SaveState<LocalizationState> saveState = new SaveState<>();
        try {
            LocalizationState localizationState = saveState.recoverT(
                    Path.of("localization.json"),
                    LocalizationState.class
            );
            Localization.changeLocalisation(localizationState.locale());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
