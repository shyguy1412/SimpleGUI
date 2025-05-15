package net.shy.simplegui;

import processing.core.PApplet;

/**
 * Abstract for all toggleable buttons
 * @author Shy
 *
 */
public abstract class SToggleButton extends SAbstractButton{

	protected boolean state = false;

	protected SToggleButton(PApplet app, float x, float y) {
		super(app, x, y);
	}

	@Override
	public void click(){
		this.setState(!this.state);
	}
	
	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.mutatedFlag = true;
		this.state = state;
		
	}
	
}
