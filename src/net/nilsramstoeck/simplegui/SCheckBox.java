package net.nilsramstoeck.simplegui;

import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * A Simple CheckBox
 * 
 * @author Nils Ramstoeck
 *
 */
public class SCheckBox extends SToggleButton {

	/**
	 * CheckBox width and height
	 */
	private float size;

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
	 * @param app  Reference to the PApplet instance
	 * @param x    CheckBox x Position in container space
	 * @param y    CheckBox y Position in container space
	 * @param size CheckBox size
	 */
	public SCheckBox(PApplet app, float x, float y, float size) {
		super(app, x, y);
		this.size = size;
		this.setDefaultStyling();
	}

	@Override
	protected boolean collision(int x, int y) {
		if (this.g == null) return false;
		return (x > this.pos.x && x < this.pos.x + this.g.width && y > this.pos.y && y < this.pos.y + this.g.height);
	}

	@Override
	protected PGraphics createGraphics() {
		float border = this.showBorder?this.borderWidth:0;

		int intSize = PApplet.floor(this.size + border * 2f);

		PGraphics graphics = this.app.createGraphics(intSize, intSize);
		graphics.beginDraw();

		if (this.showBorder) {
			graphics.stroke(graphics.lerpColor(this.borderColor, this.hoverBorderColor, this.getFadeProgress()));
			graphics.strokeWeight(border);
		} else {
			graphics.noStroke();
		}

		graphics.fill(graphics.lerpColor(this.backgroundColor, this.hoverBackgroundColor, this.getFadeProgress()));

		int bodyX = PApplet.floor((border - 0.5f) / 2f);
		int bodyY = PApplet.floor((border - 0.5f) / 2f);
		int bodyW = PApplet.ceil(intSize - border - 0.5f);
		int bodyH = PApplet.ceil(intSize - border - 0.5f);

		graphics.rect(bodyX, bodyY, bodyW, bodyH);

		if (this.getState()) {
			graphics.noStroke();
			graphics.fill(graphics.lerpColor(this.markerColor, this.hoverMarkerColor, this.getFadeProgress()));

			float markerSize = intSize * 0.8f - border * 2f;

			graphics.rectMode(PApplet.CENTER);
			graphics.rect(graphics.width / 2f, graphics.height / 2f, markerSize, markerSize);
		}

		graphics.endDraw();
		return graphics;
	}

	// GETTERS AND SETTERS//

	/**
	 * Sets all styling options to default
	 */
	public void setDefaultStyling() {
		this.backgroundColor = app.color(255);
		this.hoverBackgroundColor = app.color(150);
		this.markerColor = app.color(0);
		this.hoverMarkerColor = app.color(0);
		this.borderColor = app.color(0);
		this.hoverBorderColor = app.color(0);
		this.borderWidth = 2;
		this.fadeTime = 0.1f;
		this.showBorder = true;
		this.mutatedFlag = true;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.mutatedFlag = true;
		this.size = size;
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

	public int getHoverBorderColor() {
		return hoverBorderColor;
	}

	public void setHoverBorderColor(int r, int g, int b) {
		this.mutatedFlag = true;
		this.hoverBorderColor = app.color(r, g, b);
	}

	public void setHoverBorderColor(int hoverBorderColor) {
		this.mutatedFlag = true;
		this.hoverBorderColor = hoverBorderColor;
	}

	public boolean getShowBorder() {
		return showBorder;
	}

	public void setShowBorder(boolean showBorder) {
		this.mutatedFlag = true;
		this.showBorder = showBorder;
	}

}
