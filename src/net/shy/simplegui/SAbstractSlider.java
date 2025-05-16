package net.shy.simplegui;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.event.MouseEvent;

/**
 * Abstract for all Sliders including ScrollBars
 * 
 * @author Shy
 *
 */
public abstract class SAbstractSlider extends SComponent {

	/**
	 * Length of the slider in pixels
	 */
	protected double length;

	/**
	 * Current position of the slider handle
	 */
	private double handlePos = 0;

	/**
	 * If the slider body is hovered
	 */
	private boolean bodyHovered = false;

	/**
	 * If the slider handle is hovered
	 */
	private boolean handleHovered = false;

	/**
	 * If the slider is attached to the mouse
	 */
	private boolean attached = false;

	/**
	 * Amount of time in seconds to fade on hover
	 */
	protected float fadeTime = 0;

	/**
	 * Moment the fading started in milliseconds
	 */
	private long fadeStart = 0;

	/**
	 * Offset of the mouse to the slider handle, if the slider handle is being
	 * dragged. This prevents the handle from jumping if its not picked up in the
	 * center
	 */
	private PVector dragOffset;

	/**
	 * 
	 * @param app    Reference to the PApplet instance
	 * @param x      Component x Position in container space
	 * @param y      Component y Position in container space
	 * @param length Slider length in pixels
	 */
	public SAbstractSlider(PApplet app, float x, float y, float length) {
		super(app, x, y);
		this.length = PApplet.max(PApplet.EPSILON, length);
		app.registerMethod("mouseEvent", this);
	}

	public void disable(){
		this.app.unregisterMethod("mouseEvent", this);
	}


	/**
	 * Processing mouse event. Handles collision, hover detection and click
	 * detection;
	 * 
	 * @param e MouseEvent
	 */
	public void mouseEvent(MouseEvent e) {
		if (!this.visible) return;

		boolean pSliderHovered = this.handleHovered;
		PVector local = this.globalToLocal(app.mouseX, app.mouseY);

		this.bodyHovered = this.bodyCollision((int) local.x, (int) local.y);
		this.handleHovered = this.sliderCollision((int) local.x, (int) local.y);

		// if not hovered before, but now start fade
		if ((!pSliderHovered && this.handleHovered) || (pSliderHovered && !this.handleHovered)) {
			this.startFade();
		}

		switch (e.getAction()) {
		case MouseEvent.PRESS:
			this.handleMousePressed();
			break;
		case MouseEvent.RELEASE:
			this.handleMouseReleased();
		case MouseEvent.DRAG:
			this.handleMouseDragged();
		}

	}

	/**
	 * Handler for the MouseDragged event
	 */
	private void handleMouseDragged() {
		if (this.attached) {
			PVector local = this.globalToLocal(app.mouseX, app.mouseY);
			this.sliderDragged(local.x + this.dragOffset.x, local.y + this.dragOffset.y);
		}
	}

	/**
	 * Handler for the MousePressed event
	 */
	private void handleMousePressed() {
		if (this.bodyHovered || this.handleHovered) {
			this.attached = true;
			PVector local = this.globalToLocal(app.mouseX, app.mouseY);
			if (this.handleHovered) {
				this.dragOffset = this.getDragOffset(local.x, local.y);
			} else {
				this.handleHovered = true;
				this.dragOffset = new PVector();
			}
			this.handleMouseDragged();
			this.startFade();
		}
	}

	/**
	 * Handler for the MouseReleased event
	 */
	private void handleMouseReleased() {
		if (this.attached) {
			this.attached = false;
		}
	}

	/**
	 * Begins the fading process
	 */
	private void startFade() {
		this.fadeStart = System.currentTimeMillis();
		this.mutatedFlag = true;
	}

	/**
	 * Returns current fading progress as percentage between 0 and 1
	 * 
	 * @return fading progress
	 */
	protected float getFadeProgress() {
		float progress = PApplet.constrain((System.currentTimeMillis() - this.fadeStart) / ((this.fadeTime + +PApplet.EPSILON) * 1000), 0, 1);
		if (this.handleHovered) {
			return progress;
		} else {
			return 1 - progress;
		}
	}

	/**
	 * After done rendering, sets the mutatedFlag if currently fading
	 */
	@Override
	public void render(PGraphics g) {
		super.render(g);
		// if currently fading, set mutated flag
		float progress = this.handleHovered ? this.getFadeProgress() : 1 - this.getFadeProgress();

		if (progress < 1) {
			this.mutatedFlag = true;
		}
	}

	/**
	 * Is called when the slider is dragged to a new position
	 * 
	 * @param x Local x position
	 * @param y Local y position
	 */
	protected abstract void sliderDragged(float x, float y);

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected abstract PVector getDragOffset(float x, float y);

	/**
	 * Defines the collision of the slider with the mouse cursor. All matrix
	 * transformations as well as container offsets must be applied to the mouse
	 * before checking collision
	 * 
	 * @param x Mouse X with the same point of reference as Button X
	 * @param y Mouse Y with the same point of reference as Button Y
	 * @return if the mouse is over the button
	 */
	protected abstract boolean sliderCollision(int x, int y);

	/**
	 * Defines the collision of the slider body with the mouse cursor. All matrix
	 * transformations as well as container offsets must be applied to the mouse
	 * before checking collision
	 * 
	 * @param x Mouse X with the same point of reference as Button X
	 * @param y Mouse Y with the same point of reference as Button Y
	 * @return if the mouse is over the button
	 */
	protected abstract boolean bodyCollision(int x, int y);

	// GETTERS AND SETTERS//

	public float getFadeTime() {
		return fadeTime;
	}

	public void setFadeTime(float fadeTime) {
		this.fadeTime = fadeTime;
	}

	/*
	 * Takes a percentage and sets the slider value to it
	 */
	public void setValue(double value) {
		if (value < 0) {
			value = 0;
		}
		if (value > 1) {
			value = 1;
		}
		this.handlePos = length * value;
		this.mutatedFlag = true;
	}

	/**
	 * Value as percentage between 0 and 1
	 * 
	 * @return Value
	 */
	public double getValue() {
		return handlePos / length;
	}

	protected float getSliderPos() {
		return (float) handlePos;
	}

	/**
	 * Sets the Slider position. Slider position is constrained between 0 and
	 * {@link #length}
	 * 
	 * @param sliderPos new Slider position
	 */
	protected void setSliderPos(float sliderPos) {
		this.mutatedFlag = true;
		this.handlePos = PApplet.constrain(sliderPos, 0, (float) this.length);
	}

	public float getLength() {
		return (float) length;
	}

	/**
	 * Sets the slider length. Constrained to be always >0
	 * 
	 * @param length new Length
	 */
	public void setLength(float length) {
		this.mutatedFlag = true;
		this.length = PApplet.max(length, PApplet.EPSILON);
	}

}
