package net.nilsramstoeck.simplegui;

import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * A Simple container for Components.
 * 
 * @author Nils Ramstoeck
 *
 */
public class SPanel extends SContainer {

	private float width, height;

	// styling
	private int backgroundColor, borderColor;
	private float borderWidth;
	private float borderRadius;
	private boolean showBorder;

	/**
	 * 
	 * @param app Reference to the PApplet instance
	 * @param x   Panel x Position in container space
	 * @param y   Panel y Position in container space
	 * @param w   Panel width
	 * @param h   Panel height
	 */
	public SPanel(PApplet app, float x, float y, int w, int h) {
		super(app, x, y);
		this.width = w;
		this.height = h;
		this.setDefaultStyling();
	}

	@Override
	protected PGraphics createGraphics() {
		float border = this.showBorder?this.borderWidth:0;

		int intWidth = PApplet.floor(this.width + border * 2f);
		int intHeight = PApplet.floor(this.height + border * 2f);

		PGraphics graphics = app.createGraphics(intWidth, intHeight);
		graphics.beginDraw();

		graphics.background(app.color(255, 0, 0));

		graphics.noStroke();
		graphics.fill(this.backgroundColor);

		int bodyX = PApplet.floor((border - 0.5f) / 2f);
		int bodyY = PApplet.floor((border - 0.5f) / 2f);
		int bodyW = PApplet.ceil(intWidth - border - 0.5f);
		int bodyH = PApplet.ceil(intHeight - border - 0.5f);

		graphics.rect(bodyX, bodyY, bodyW, bodyH);

		for (SComponent comp : this.components) {
			comp.render(graphics);
		}

		if (this.showBorder) {
			graphics.stroke(this.borderColor);
			graphics.strokeWeight(border);
			graphics.noFill();
			graphics.rect(bodyX, bodyY, bodyW, bodyH, this.borderRadius);
		}

		graphics.endDraw();
		return graphics;
	}

	// GETTERS AND SETTERS//
	public void setDefaultStyling() {
		this.backgroundColor = app.color(255, 255);
		this.borderColor = app.color(0);
		this.borderWidth = 1;
		this.borderRadius = 0;
		this.showBorder = false;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.mutatedFlag = true;
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.mutatedFlag = true;
		this.height = height;
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

	public float getBorderRadius() {
		return borderRadius;
	}

	public void setBorderRadius(float borderRadius) {
		this.mutatedFlag = true;
		this.borderRadius = borderRadius;
	}

	public boolean getShowBorder() {
		return showBorder;
	}

	public void setShowBorder(boolean showBorder) {
		this.mutatedFlag = true;
		this.showBorder = showBorder;
	}

}
