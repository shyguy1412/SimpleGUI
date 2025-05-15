package net.shy.simplegui;

import java.util.HashSet;

import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Base class for all components that can contain other components like Panels
 * and ScrollPanels. The Position of a containers child is always relative to
 * the containers position. Containers are responsible of rendering their
 * children, as they are unregistered from the PApplet draw event. A Containers
 * graphics should not be drawn on, as all changes will be lost if any child or
 * the container itself mutates. Containers can contain other containers.
 * 
 * @author Shy
 *
 */
public abstract class SContainer extends SComponent {

	/**
	 * Set of all child components
	 */
	protected HashSet<SComponent> components = new HashSet<>();

	/**
	 * 
	 * @param app Reference to the PApplet instance
	 * @param x   Component x Position in container space
	 * @param y   Component y Position in container space
	 */
	protected SContainer(PApplet app, float x, float y) {
		super(app, x, y);
	}

	/**
	 * Cascades child mutatedFlag to containers mutatedFlag
	 */
	@Override
	public void render(PGraphics g) {
		for (SComponent comp : this.components) {
			this.mutatedFlag |= comp.mutatedFlag;
		}
		super.render(g);
	}

	public void addComponent(SComponent comp) {
		if (this.components.add(comp)) {
			comp.container = this;
			comp.app.unregisterMethod("draw", comp);
		}
	}

	public void removeComponent(SComponent comp) {
		if (this.components.remove(comp)) {
			comp.container = null;
			comp.app.registerMethod("draw", comp);
		}
		;
	}

}
