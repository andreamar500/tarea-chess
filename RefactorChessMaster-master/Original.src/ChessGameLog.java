import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

// Interfaz que define el método de actualización del observador.
interface GameLogObserver {
    void updateLog(String s);
}

/**
 * A scrollable textArea representing the game log. (i.e. moves made by each
 * player)
 * 
 * This class is now a subject in the Observer pattern, which allows it to
 * notify its observers (GameLogObserver objects) when new moves are added to
 * the log.
 * 
 * This implementation of the Observer pattern uses the "pull" model, where the
 * observers request the data they need from the subject rather than the
 * subject pushing the data to the observers.
 * 
 * This implementation is also a Singleton, meaning that there is only one
 * ChessGameLog object in the entire program.
 * 
 * @author Ben Katz (bakatz)
 * @version 2021.03.05
 */
public class ChessGameLog extends JScrollPane implements GameLogObserver, Serializable {

    // Singleton instance of ChessGameLog
    private static ChessGameLog instance = null;

    // List of observers (GameLogObserver objects) that have subscribed to the log.
    transient private ArrayList<GameLogObserver> observers;

    private JTextArea textArea;

    // Private constructor to enforce Singleton pattern
    ChessGameLog() {
        super(new JTextArea("", 5, 30), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        textArea = ((JTextArea) this.getViewport().getView());
        observers = new ArrayList<>();
    }

    // Get the Singleton instance of ChessGameLog
    public static ChessGameLog getInstance() {
        if (instance == null) {
            instance = new ChessGameLog();
        }
        return instance;
    }

    // Adds a new observer to the list of observers
    public void addObserver(GameLogObserver observer) {
        observers.add(observer);
    }

    // Removes an observer from the list of observers
    public void removeObserver(GameLogObserver observer) {
        observers.remove(observer);
    }

    // Notifies all observers that a new move has been added to the log
    private void notifyObservers(String s) {
        for (GameLogObserver observer : observers) {
            observer.updateLog(s);
        }
    }

    // Adds a new line of text to the log and notifies observers
    public void addToLog(String s) {
        if (textArea.getText().length() > 0) {
            textArea.setText(textArea.getText() + "\n" + new Date() + " - " + s);
        } else {
            textArea.setText(new Date() + " - " + s);
        }
        notifyObservers(s);
    }

    // Clears the log
    public void clearLog() {
        textArea.setText("");
    }

    // Gets the most recent statement added to the log
    public String getLastLog() {
        int indexOfLastNewLine = textArea.getText().lastIndexOf("\n");
        if (indexOfLastNewLine < 0) {
            return textArea.getText();
        }
        return textArea.getText().substring(indexOfLastNewLine + 1);
    }

    // Implementation of GameLogObserver interface
    // Called by the ChessBoard object when a new move is made
    public void updateLog(String s) {
        addToLog(s);
    }
}
