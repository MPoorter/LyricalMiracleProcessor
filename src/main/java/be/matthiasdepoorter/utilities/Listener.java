package be.matthiasdepoorter.utilities;

@FunctionalInterface
public interface Listener {

	void changed(ListenerEvent event);
}
