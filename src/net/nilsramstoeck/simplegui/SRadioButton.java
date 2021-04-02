package net.nilsramstoeck.simplegui;

import java.util.HashMap;
import java.util.HashSet;

import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * A Simple RadioButton
 * 
 * @author Nils Ramstoeck
 *
 */
public class SRadioButton extends SToggleButton {

	/**
	 * Class identifier for the radiobutton.
	 */
	private String radioclass;
	private float radius;

	private int backgroundColor;
	private int markerColor;
	private int borderColor;
	private int hoverBackgroundColor;
	private int hoverMarkerColor;
	private int hoverBorderColor;

	private float borderWidth;
	private boolean showBorder;

	/**
	 * 
	 * @param app        Reference to the PApplet instance
	 * @param x          RadioButton x Position in container space
	 * @param y          RadioButton y Position in container space
	 * @param r          RadioButton radius
	 * @param radioclass RadioButton class
	 */
	public SRadioButton(PApplet app, float x, float y, float r, String radioclass) {
		super(app, x, y);
		this.radius = r;
		this.radioclass = radioclass;
		SRadioButtonMaster.addRadioButton(this);
		this.setDefaultStyling();
	}

	@Override
	public void click() {
		this.setState(true);
	}

	@Override
	protected boolean collision(int x, int y) {
		if (this.g == null) return false;
		return PApplet.dist(this.pos.x + this.g.width/2, this.pos.y + this.g.height/2, x, y) < PApplet.max(this.g.width/2, this.g.height/2);
	}

	@Override
	protected PGraphics createGraphics() {
		int intRadius = PApplet.floor(this.radius);
		int intSize = PApplet.ceil(intRadius * 2 + this.borderWidth * 2);
		float border = this.showBorder ? this.borderWidth : 0;

		PGraphics graphics = app.createGraphics(intSize, intSize);
		graphics.beginDraw();

		if (this.showBorder) {
			graphics.stroke(app.lerpColor(this.borderColor, this.hoverBorderColor, this.getFadeProgress()));
			graphics.strokeWeight(this.borderWidth);
		} else {
			graphics.noStroke();
		}

		graphics.fill(app.lerpColor(this.backgroundColor, this.hoverBackgroundColor, this.getFadeProgress()));

		int bodyX = PApplet.floor(graphics.width / 2f);
		int bodyY = PApplet.floor(graphics.height / 2f);
		int bodyR = PApplet.floor(graphics.width - border);

		graphics.circle(bodyX, bodyY, bodyR);

		if (this.getState()) {
			graphics.noStroke();
			graphics.fill(graphics.lerpColor(this.markerColor, this.hoverMarkerColor, this.getFadeProgress()));
			graphics.circle(graphics.width / 2f, graphics.height / 2f, intRadius * 1.2f);
		}

		graphics.endDraw();
		return graphics;
	}

	/**
	 * Manages the RadioButtons so they behave correctly. e.g. Only one active
	 * button per class.
	 * 
	 * @author Nils Ramstoeck
	 *
	 */
	private static class SRadioButtonMaster {
		private static final HashMap<String, HashSet<SRadioButton>> radioClassCollection = new HashMap<>();

		private static void addRadioButton(SRadioButton radiobutton) {
			SRadioButtonMaster.radioClassCollection.putIfAbsent(radiobutton.radioclass, new HashSet<>());
			SRadioButtonMaster.radioClassCollection.get(radiobutton.radioclass).add(radiobutton);
		}

		private static void transferRadioButton(SRadioButton radiobutton, String oldClass, String newClass) {
			SRadioButtonMaster.radioClassCollection.get(oldClass).remove(radiobutton);
			radiobutton.radioclass = newClass;
			SRadioButtonMaster.addRadioButton(radiobutton);
		}

		private static void activateRadioButton(SRadioButton radiobutton) {
			for (SRadioButton btn : SRadioButtonMaster.radioClassCollection.get(radiobutton.radioclass)) {
				btn.setState(false);
			}
			radiobutton.state = true;
		}
	}

	// GETTERS AND SETTERS//
	public void setDefaultStyling() {
		this.backgroundColor = app.color(255);
		this.hoverBackgroundColor = app.color(150);
		this.markerColor = app.color(0);
		this.hoverMarkerColor = app.color(0);
		this.borderColor = app.color(0);
		this.hoverBorderColor = app.color(0);
		this.fadeTime = 0.1f;
		this.borderWidth = 1;
		this.showBorder = true;
	}

	public void setRadioClass(String newClass) {
		SRadioButtonMaster.transferRadioButton(this, newClass, this.radioclass);
	}

	@Override
	public void setState(boolean state) {
		this.mutatedFlag = true;
		if (state) {
			SRadioButtonMaster.activateRadioButton(this);
		} else {
			this.state = state;
		}
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.mutatedFlag = true;
		this.radius = radius;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.mutatedFlag = true;
		this.backgroundColor = backgroundColor;
	}

	public void setBackgroundColor(int r, int g, int b) {
		this.mutatedFlag = true;
		this.backgroundColor = app.color(r, g, b);
	}

	public int getHoverBackgroundColor() {
		return hoverBackgroundColor;
	}

	public void setHoverBackgroundColor(int hoverBackgroundColor) {
		this.mutatedFlag = true;
		this.hoverBackgroundColor = hoverBackgroundColor;
	}

	public void setHoverBackgroundColor(int r, int g, int b) {
		this.mutatedFlag = true;
		this.hoverBackgroundColor = app.color(r, g, b);
	}

	public int getMarkerColor() {
		return markerColor;
	}

	public void setMarkerColor(int markerColor) {
		this.mutatedFlag = true;
		this.markerColor = markerColor;
	}

	public void setMarkerColor(int r, int g, int b) {
		this.mutatedFlag = true;
		this.markerColor = app.color(r, g, b);
	}

	public int getHoverMarkerColor() {
		return this.hoverMarkerColor;
	}

	public void setHoverMarkerColor(int hoverMarkerColor) {
		this.mutatedFlag = true;
		this.hoverMarkerColor = hoverMarkerColor;
	}

	public void setHoverMarkerColor(int r, int g, int b) {
		this.mutatedFlag = true;
		this.hoverMarkerColor = app.color(r, g, b);
	}

	public int getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(int borderColor) {
		this.mutatedFlag = true;
		this.borderColor = borderColor;
	}

	public void setBorderColor(int r, int g, int b) {
		this.mutatedFlag = true;
		this.borderColor = app.color(r, g, b);
	}

	public float getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(float borderWidth) {
		this.mutatedFlag = true;
		this.borderWidth = borderWidth;
	}

	public boolean isShowBorder() {
		return showBorder;
	}

	public void setShowBorder(boolean showBorder) {
		this.mutatedFlag = true;
		this.showBorder = showBorder;
	}

	public String getRadioclass() {
		return radioclass;
	}

}
