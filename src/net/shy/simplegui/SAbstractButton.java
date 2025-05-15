package net.shy.simplegui;

import java.util.HashSet;

import net.shy.simplegui.events.SClickListener;
import net.shy.simplegui.events.SClickable;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.event.MouseEvent;

/**
 * Abstract class for all Buttons. Handles Mouse events, firing click events and
 * the fading on hover
 * 
 * @author Shy
 *
 */
public abstract class SAbstractButton extends SComponent implements SClickable {

	/**
	 * If the button is being hovered over
	 */
	protected boolean hovered = false;

	/**
	 * Amount of time in seconds to fade on hover
	 */
	protected float fadeTime = 0;

	/**
	 * Moment the fading started in milliseconds
	 */
	private long fadeStart = 0;

	/**
	 * Set of all ClickListeners
	 */
	private HashSet<SClickListener> listeners = new HashSet<>();

	protected SAbstractButton(PApplet app, float x, float y) {
		super(app, x, y);
		this.app.registerMethod("mouseEvent", this);
	}

	/**
	 * Processing mouse event. Handles collision, hover detection and click
	 * detection;
	 * 
	 * @param e MouseEvent
	 */
	public void mouseEvent(MouseEvent e) {
		if (!this.visible) return;

		boolean phov = this.hovered;

		PVector local = this.globalToLocal(app.mouseX, app.mouseY);

		this.hovered = this.collision((int) local.x, (int) local.y);

		// if not hovered before, but now start fade
		if ((!phov && this.hovered) || (phov && !this.hovered)) {
			this.fadeStart = System.currentTimeMillis();
			this.mutatedFlag = true;
		}

		switch (e.getAction()) {
		case MouseEvent.CLICK:
			this.handleMouseClicked();
			break;
		}
	}

	/**
	 * Handler for the MouseClicked event. Dispatches the ClickEvent and calls
	 * {@link #click()}
	 */
	private void handleMouseClicked() {
		if (this.hovered) {
			this.dispatchClickEvent();
			this.click();
		}
	}

	/**
	 * After done rendering, sets the mutatedFlag if currently fading
	 */
	@Override
	public void render(PGraphics g) {
		super.render(g);
		// if currently fading, set mutated flag
		float progress = this.hovered ? this.getFadeProgress() : 1 - this.getFadeProgress();

		if (progress < 1) {
			this.mutatedFlag = true;
		}
	}

	/**
	 * Returns current fading progress as percentage between 0 and 1
	 * 
	 * @return fading progress
	 */
	protected float getFadeProgress() {
		float progress = PApplet.constrain((System.currentTimeMillis() - this.fadeStart) / ((this.fadeTime + +PApplet.EPSILON) * 1000), 0, 1);
		if (this.hovered) {
			return progress;
		} else {
			return 1 - progress;
		}
	}

	/**
	 * Defines the collision of a button with the mouse cursor. All matrix
	 * transformations as well as container offsets must be applied to the mouse
	 * before checking collision
	 * 
	 * @param x Mouse X with the same point of reference as Button X
	 * @param y Mouse Y with the same point of reference as Button Y
	 * @return if the mouse is over the button
	 */
	protected abstract boolean collision(int x, int y);

	/**
	 * Gets called when the button is pressed. Can also be triggered manually
	 */
	public abstract void click();

	// SClickable interface implementation
	public void addClickListener(SClickListener listener) {
		this.listeners.add(listener);
	}

	public void removeClickListener(SClickListener listener) {
		this.listeners.add(listener);
	}

	public void dispatchClickEvent() {
		for (SClickListener listener : this.listeners) {
			listener.onClick(this);
		}
	}

	// GETTERS AND SETTERS//
	public float getFadeTime() {
		return fadeTime;
	}

	public void setFadeTime(float fadeTime) {
		this.fadeTime = fadeTime;
	}

}
