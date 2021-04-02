package net.nilsramstoeck.simplegui;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 * A Simple slider.
 * 
 * @author Nils Ramstoeck
 *
 */
public class SSlider extends SAbstractSlider {

	private float step;

	private float minValue;
	private float maxValue;

	private float height;
	private float handleWidth;

	private int backgroundColor;
	private int handleColor;
	private int borderColor;
	private int hoverBorderColor;
	private int hoverHandleColor;
	private int hoverBackgroundColor;

	private float borderWidth;
	private boolean showBorder;

	public SSlider(PApplet app, float x, float y, float length) {
		this(app, x, y, length, 0, 1, 0.001f);
	}

	public SSlider(PApplet app, float x, float y, float length, float min, float max) {
		this(app, x, y, length, min, max, 1);
	}

	/**
	 * 
	 * /**
	 * 
	 * @param app    Reference to the PApplet instance
	 * @param x      Component x Position in container space
	 * @param y      Component y Position in container space
	 * @param length Slider length in pixels
	 * @param min    Slider start value
	 * @param max    Slider end value
	 * @param step   Slider value steps
	 */
	public SSlider(PApplet app, float x, float y, float length, float min, float max, float step) {
		super(app, x, y, length);
		this.minValue = min;
		this.maxValue = max;
		this.step = step;
		this.setDefaultStyling();
	}

	@Override
	protected boolean sliderCollision(int x, int y) {
		if (this.g == null) return false;
		float border = this.showBorder ? this.borderWidth : 0;
		int handleX = PApplet.floor(this.getSliderScreenPosition() + border + this.pos.x);
		int handleY = PApplet.floor(border + 0.5f + this.pos.y);
		int handleW = PApplet.floor(this.handleWidth);
		int handleH = PApplet.ceil(this.g.height - 1f - border * 2f);
		return (x > handleX && x < handleX + handleW && y > handleY && y < handleY + handleH);
	}

	@Override
	protected boolean bodyCollision(int x, int y) {
		if (this.g == null) return false;
		return (x > this.pos.x && x < this.pos.x + this.g.width && y > this.pos.y && y < this.pos.y + this.g.height);
	}

	@Override
	protected PGraphics createGraphics() {

		float border = this.showBorder ? this.borderWidth : 0;
		int intLength = PApplet.ceil((float) length + border * 2);
		int intHeight = PApplet.ceil(height + border * 2);

		PGraphics graphics = app.createGraphics(intLength, intHeight);
		graphics.beginDraw();

		if (this.showBorder) {
			graphics.stroke(graphics.lerpColor(this.borderColor, this.hoverBorderColor, this.getFadeProgress()));
			graphics.strokeWeight(this.borderWidth);
		} else {
			graphics.noStroke();
		}

		graphics.fill(graphics.lerpColor(this.backgroundColor, this.hoverBackgroundColor, this.getFadeProgress()));

		int bodyX = PApplet.floor((border - 0.5f) / 2f);
		int bodyY = PApplet.floor((border - 0.5f) / 2f);
		int bodyW = PApplet.floor(intLength - border - 0.5f);
		int bodyH = PApplet.floor(intHeight - border - 0.5f);

		graphics.rect(bodyX, bodyY, bodyW, bodyH);

		graphics.noStroke();
		graphics.fill(app.lerpColor(this.handleColor, this.hoverHandleColor, this.getFadeProgress()));

		int handleX = PApplet.floor(this.getSliderScreenPosition() + border);
		int handleY = PApplet.floor(border);
		int handleW = PApplet.floor(this.handleWidth);
		int handleH = PApplet.ceil(intHeight - 1f - border * 2f);

		graphics.rect(handleX, handleY, handleW, handleH);

		graphics.endDraw();
		return graphics;
	}

	@Override
	protected void sliderDragged(float x, float y) {
		float pos = PApplet.map(x - this.pos.x - this.handleWidth / 2f, 0, (float) this.length - this.handleWidth, 0, (float) this.length);
		float exactValue = PApplet.map(pos / (float) length, 0, 1, this.minValue, this.maxValue);
		double steps = PApplet.round(exactValue / step);
		double actualValue = Math.round(steps * step * 1000) / 1000.0;

		this.setValue(actualValue);
	}

	@Override
	protected PVector getDragOffset(float x, float y) {
		PVector offset = new PVector();
		offset.x = (this.pos.x + this.getSliderScreenPosition() + this.handleWidth / 2) - x;
		return offset;
	}

	// GETTERS AND SETTERS//

	/**
	 * Sets all styling options to default
	 */
	public void setDefaultStyling() {
		this.height = 15;
		this.handleWidth = 20;
		this.backgroundColor = app.color(200);
		this.hoverBackgroundColor = app.color(200);
		this.handleColor = app.color(150);
		this.hoverHandleColor = app.color(100);
		this.borderColor = app.color(0);
		this.hoverBorderColor = app.color(0);
		this.borderWidth = 1;
		this.showBorder = true;
		this.fadeTime = 0.1f;
	}

	/**
	 * Gets the Slider Screen position
	 * 
	 * @return Screen position of the slider
	 */
	private float getSliderScreenPosition() {
		return PApplet.map((float) this.getSliderPos(), 0, (float) this.length, 0, (float) this.length - this.handleWidth - this.borderWidth / 2f);
	}

	@Override
	public void setValue(double value) {
		value = PApplet.constrain((float) value, minValue, maxValue);
		super.setValue(PApplet.map((float) value, this.minValue, this.maxValue, 0, 1));
	}

	@Override
	public double getValue() {
		return Math.round(PApplet.map((float) super.getValue(), 0, 1, this.minValue, this.maxValue) * 10000) / 10000.0;
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

	public int getHandleColor() {
		return handleColor;
	}

	public void setHandleColor(int handleColor) {
		this.mutatedFlag = true;
		this.handleColor = handleColor;
	}

	public void setHandleColor(int r, int g, int b) {
		this.mutatedFlag = true;
		this.handleColor = app.color(r, g, b);
	}

	public int getHoverHandleColor() {
		return this.hoverHandleColor;
	}

	public void setHoverHandleColor(int hoverHandleColor) {
		this.mutatedFlag = true;
		this.hoverHandleColor = hoverHandleColor;
	}

	public void setHoverHandleColor(int r, int g, int b) {
		this.mutatedFlag = true;
		this.hoverHandleColor = app.color(r, g, b);
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
