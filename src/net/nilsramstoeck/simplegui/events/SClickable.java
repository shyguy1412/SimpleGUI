package net.nilsramstoeck.simplegui.events;

/**
 * Interface for Clickable Components
 * @author Nils Ramstoeck
 *
 */
public interface SClickable {

	public abstract void addClickListener(SClickListener listener);
	public abstract void removeClickListener(SClickListener listener);
	public abstract void dispatchClickEvent();
}
