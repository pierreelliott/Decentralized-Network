package util;

public interface Observable {

    void addObserver(Observer o);
    void deleteObserver(Observer o);
    void deleteObservers();
    void notifyObservers();
    void notifyObservers(Object arg);
    int countObservers();
    void setChanged();
    void clearChanged();
    boolean hasChanged();
}
