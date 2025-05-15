package net.shy.simplegui.events;

/**
 * Interface for Clickable Components
 * @author Shy
 *
 */
public interface SClickable {

	public abstract void addClickListener(SClickListener listener);
	public abstract void removeClickListener(SClickListener listener);
	public abstract void dispatchClickEvent();
}
