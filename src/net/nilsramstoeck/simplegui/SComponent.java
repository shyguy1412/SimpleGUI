package net.nilsramstoeck.simplegui;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/** 
 * Base class for all SimpleGUI components Currently, SComponents will never be
 * eligible for garbage collection, as registering the component to the PApplets
 * draw event means there will always be a reference to it
 * 
 * @author Nils Ramstoeck
 *
 */
public abstract class SComponent {

	/**
	 * Reference to the Processing app instance
	 */
	protected PApplet app;

	/**
	 * Component position relative to its parent container
	 */
	public PVector pos;

	/**
	 * Component rotation in radians
	 */
	protected float rotation = 0;

	/**
	 * Parent container (PApplet if null)
	 */
	protected SContainer container = null;

	/**
	 * Flag to indicate if a component has changed in any way. Causes the Component
	 * graphics to be updated
	 */
	protected boolean mutatedFlag = true;

	/**
	 * Component Graphics object. This is what will be drawn to the screen. This is
	 * not meant to be modified, as all modification will be lost if the components
	 * graphics are updated
	 */
	protected PGraphics g;

	/**
	 * Component Visibility. If set to false, the component will not be drawn to the
	 * screen.
	 */
	protected boolean visible = true;

	/**
	 * 
	 * @param app Reference to the PApplet instance
	 * @param x   Component x Position in container space
	 * @param y   Component y Position in container space
	 */
	protected SComponent(PApplet app, float x, float y) {
		this.app = app;
		this.pos = new PVector(x, y);

		this.app.registerMethod("draw", this);
	}

	/**
	 * Generates the Component Graphics
	 * 
	 * @return Component Graphics
	 */
	protected abstract PGraphics createGraphics();

	/**
	 * Called after PApplet draw cycle. Renders the component. As components with
	 * parent containers are unregistered for this event, this will only render
	 * Components with the PApplet as container. Containers are responsible for
	 * rendering their children into their own graphics
	 */
	public void draw() {
		this.render(app.g);
	}

	/**
	 * Renders the component to the target canvas
	 */
	public void render(PGraphics canvas) {
		if (!this.visible) return;
		try {
			if (this.mutatedFlag) {
				this.g = this.createGraphics();
				this.mutatedFlag = false;
			}
			this.g.resetMatrix(); // undo any potential matrix manipulation
			canvas.push();
			canvas.resetMatrix();
			canvas.translate(this.pos.x, this.pos.y);
			canvas.rotate(this.rotation);
			canvas.image(this.g, 0, 0);
			canvas.pop();
		} catch (Exception e) {
			e.printStackTrace();
			// Do Nothing...
		}
	}

	/**
	 * Converts global coordinates into coordinates in the components container
	 * space
	 * 
	 * @param x Global x Coordinate
	 * @param y Global y Coordinate
	 * @return PVector with local coordinates
	 */
	public PVector globalToLocal(float x, float y) {
		if (this.container == null) {
			PVector local = new PVector(x, y);
//			PMatrix mat = app.getMatrix();
//			mat.invert();
//			local = mat.mult(local, null);
			return local;
		} else {
			return container.globalToLocal(x, y).sub(this.container.pos);
		}
	}

	//GETTERS AND SETTERS//
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public PGraphics getGraphics() {
		return g;
	}

}
