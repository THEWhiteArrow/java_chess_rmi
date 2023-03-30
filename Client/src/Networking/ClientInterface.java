package Networking;

import utility.observer.subject.RemoteSubject;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;

public interface ClientInterface extends RemoteSubject<String,GameRoom>
{
}
