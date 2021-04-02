package net.nilsramstoeck.simplegui;

import java.lang.reflect.Method;

import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * A Simple, Rectangular Button
 * 
 * @author Nils Ramstoeck
 *
 */
public class SButton extends SAbstractButton {

	/**
	 * Width of the button
	 */
	private float width;

	/**
	 * Height of the button
	 */
	private float height;

	/**
	 * Button label
	 */
	private String label;

	/**
	 * Button callback function
	 */
	private String callback;

	private int backgroundColor;
	private int labelColor;
	private int borderColor;
	private int hoverBackgroundColor;
	private int hoverLabelColor;
	private int hoverBorderColor;
	public float borderWidth;
	private float borderRadius;
	private boolean showBorder;

	public SButton(PApplet app, float x, float y, float w, float h) {
		this(app, x, y, w, h, "", "");
	}

	public SButton(PApplet app, float x, float y, float w, float h, String label) {
		this(app, x, y, w, h, label, "");
	}

	/**
	 * 
	 * @param app      Reference to the PApplet instance
	 * @param x        Component x Position in container space
	 * @param y        Component y Position in container space
	 * @param w        Button width
	 * @param h        Button height
	 * @param label    Button label
	 * @param callback Method name of a callback function defined in {@code app}
	 */
	public SButton(PApplet app, float x, float y, float w, float h, String label, String callback) {
		super(app, x, y);
		this.width = w;
		this.height = h;
		this.setLabel(label);
		this.setCallback(callback);
		this.setDefaultStyling();

		this.setVisible(true);
	}

	@Override
	public void click() {
		this.callback();
	}

	/**
	 * Tries to call the callback function. Called in {@link #click()}
	 */
	public void callback() {
		try {
			Method method = app.getClass().getMethod(callback);
			method.invoke(app);
		} catch (Exception e) {
			// Do nothing...
		}
	}

	@Override
	protected PGraphics createGraphics() {
		
		float border = this.showBorder?this.borderWidth:0;

		int intWidth = PApplet.floor(this.width + PApplet.ceil(border * 2f));
		int intHeight = PApplet.floor(this.height + PApplet.ceil(border * 2f));

		PGraphics graphics = app.createGraphics(intWidth, intHeight);
		graphics.beginDraw();

		if (this.showBorder) {
			graphics.stroke(graphics.lerpColor(borderColor, hoverBorderColor, this.getFadeProgress()));
			graphics.strokeWeight(border);
		} else {
			graphics.noStroke();
		}
		graphics.fill(graphics.lerpColor(backgroundColor, hoverBackgroundColor, this.getFadeProgress()));

		int bodyX = PApplet.floor((border - 0.5f) / 2f);
		int bodyY = PApplet.floor((border - 0.5f) / 2f);
		int bodyW = PApplet.ceil(intWidth - border - 0.5f);
		int bodyH = PApplet.ceil(intHeight - border - 0.5f);

		graphics.rect(bodyX, bodyY, bodyW, bodyH, this.borderRadius);

		float textX = graphics.textSize / graphics.textWidth(this.label) * this.width;
		float textY = graphics.textSize / (graphics.textAscent() + graphics.textDescent()) * this.height;

		graphics.noStroke();
		graphics.fill(graphics.lerpColor(labelColor, hoverLabelColor, this.getFadeProgress()));
		graphics.textAlign(PApplet.CENTER, PApplet.CENTER);
		graphics.textSize(Math.min(textX, textY) * 0.9f);

		graphics.text(this.label, graphics.width / 2, graphics.height / 2 - graphics.textDescent() / 2);

		graphics.endDraw();
		return graphics;
	}

	@Override
	protected boolean collision(int x, int y) {
		if (this.g == null) return false;
		return (x > this.pos.x && x < this.pos.x + this.g.width && y > this.pos.y && y < this.pos.y + this.g.height);
	}

	// getters and setters//

	/**
	 * Sets all styling options to default
	 */
	private void setDefaultStyling() {
		this.backgroundColor = 255;
		this.hoverBackgroundColor = 150;
		this.labelColor = 0;
		this.hoverLabelColor = 0;
		this.borderColor = 0;
		this.hoverBorderColor = 0;
		this.borderWidth = 1;
		this.borderRadius = 0;
		this.fadeTime = 0.1f;
		this.showBorder = true;
		this.mutatedFlag = true;
	}

	///// GETTERS AND SETTERS/////
	public float getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.mutatedFlag = true;
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.mutatedFlag = true;
		this.height = height;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.mutatedFlag = true;
		this.label = label;
	}

	public void setCallback(String callback) {
		this.mutatedFlag = true;
		this.callback = callback;
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

	public int getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(int labelColor) {
		this.mutatedFlag = true;
		this.labelColor = labelColor;
	}

	public void setLabelColor(int r, int g, int b) {
		this.mutatedFlag = true;
		this.labelColor = app.color(r, g, b);
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

	public int getHoverLabelColor() {
		return hoverLabelColor;
	}

	public void setHoverLabelColor(int hoverLabelColor) {
		this.mutatedFlag = true;
		this.hoverLabelColor = hoverLabelColor;
	}

	public void setHoverLabelColor(int r, int g, int b) {
		this.mutatedFlag = true;
		this.hoverLabelColor = app.color(r, g, b);
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

	public void setShowBorder(boolean showBorder) {
		this.mutatedFlag = true;
		this.showBorder = showBorder;
	}

	public boolean getShowBorder() {
		return showBorder;
	}

	public String getCallback() {
		return callback;
	}

	public void setWidth(float width) {
		this.mutatedFlag = true;
		this.width = width;
	}

	public void setHeight(float height) {
		this.mutatedFlag = true;
		this.height = height;
	}

}
