package gui.adapters;

import gui.state.SaveState;

import javax.swing.*;

public class SaveStateAdapter {
    private JInternalFrame window,logWindow;
    SaveState state = new SaveState();
    public SaveStateAdapter(JInternalFrame window,JInternalFrame logWindow){
        this.window = window;
        this.logWindow = logWindow;
    }
    public void makeSave(){
        state.save(window);
        state.save(logWindow);
    }
    public void recoverSave(){
        state.recover(window);
        state.recover(logWindow);
    }
    public boolean checkFiles(){
        if (state.checkFileNotFound(window)){return true;}
        else return state.checkFileNotFound(logWindow);
    }

}
