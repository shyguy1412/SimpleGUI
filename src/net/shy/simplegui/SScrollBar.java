package net.shy.simplegui;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 * A Simple ScrollBar for ScrollPanels
 * 
 * @author Shy
 *
 */
public class SScrollBar extends SAbstractSlider {

	public static final int HORIZONTAL = 0x01;
	public static final int VERTICAL = 0x02;

	private SScrollPanel parent;

	private int orientation;

	private int width;

	private float scrollbarLength;

	private int backgroundColor;
	private int scrollbarColor;
	private int hoverColor;
	private int borderColor;
	private float borderWidth;
	private boolean showBorder;

	/**
	 * 
	 * @param app         Reference to the PApplet instance
	 * @param parent      Parent ScrollPanel
	 * @param orientation ScrollBar Orientation
	 */
	public SScrollBar(PApplet app, SScrollPanel parent, int orientation) {
		super(app, 0, 0, 0);
		this.orientation = orientation;
		this.setDefaultStyling();
		// app.unregisterMethod("draw", this);
		this.container = parent;
		this.parent = parent;
	}

	/**
	 * Returns the ScrollBar position on the screen.
	 * 
	 * @return
	 */
	private float getScrollBarScreenPosition() {
		return PApplet.map((float)this.getSliderPos(), 0, (float)this.length, 0, (float)this.length - this.scrollbarLength);
	}

	/**
	 * Include scroll offset in local calculation
	 */
	@Override
	public PVector globalToLocal(float x, float y) {
		return super.globalToLocal(x, y).add(this.parent.getScrollTranslation());
	}

	@Override
	protected PGraphics createGraphics() {
		int h = PApplet.floor((float)this.length);
		int w = PApplet.floor(this.width);

		if (this.orientation == SScrollBar.HORIZONTAL) {
			int t = w;
			w = h;
			h = t;
		}

		int borderOffset = this.showBorder ? PApplet.floor(this.borderWidth) : 0;

		PGraphics graphics = app.createGraphics(w + borderOffset, h + borderOffset);
		graphics.beginDraw();

		if (this.showBorder) {
			graphics.stroke(this.borderColor);
			graphics.strokeWeight(this.borderWidth);
		} else {
			graphics.noStroke();
		}
		graphics.fill(this.backgroundColor);

		graphics.rect(borderOffset / 2f, borderOffset / 2f, w, h);

		graphics.fill(app.lerpColor(this.scrollbarColor, this.hoverColor, this.getFadeProgress()));

		int sh = PApplet.floor(this.scrollbarLength);
		int sw = w;

		if (this.orientation == SScrollBar.HORIZONTAL) {
			graphics.rect(borderOffset / 2f + this.getScrollBarScreenPosition(), borderOffset / 2f, sh, sw);
		} else {
			graphics.rect(borderOffset / 2f, borderOffset / 2f + this.getScrollBarScreenPosition(), sw, sh);
		}

		graphics.endDraw();
		return graphics;
	}

	@Override
	protected void sliderDragged(float x, float y) {
		if (this.orientation == SScrollBar.HORIZONTAL) {
			this.setSliderPos(PApplet.map(x - this.scrollbarLength / 2f, 0, (float)this.length - this.scrollbarLength, 0, (float)this.length));
		} else {
			this.setSliderPos(PApplet.map(y - this.scrollbarLength / 2f, 0, (float)this.length - this.scrollbarLength, 0, (float)this.length));
		}
	}

	@Override
	protected boolean bodyCollision(int x, int y) {
		float orientedWidth = this.width;
		float orientedHeight = (float)this.length;

		if (this.orientation == SScrollBar.HORIZONTAL) {
			float temp = orientedWidth;
			orientedWidth = orientedHeight;
			orientedHeight = temp;
		}

		return (x > this.pos.x && x < this.pos.x + orientedWidth && y > this.pos.y && y < this.pos.y + orientedHeight);
	}

	@Override
	protected boolean sliderCollision(int x, int y) {
		float xPos, yPos, sw, sh;

		if (this.orientation == SScrollBar.HORIZONTAL) {
			xPos = this.borderWidth / 2f + this.getScrollBarScreenPosition() + this.pos.x;
			yPos = this.borderWidth / 2f + this.pos.y;
			sh = PApplet.floor(this.width);
			sw = PApplet.floor(this.scrollbarLength);

		} else {
			xPos = this.borderWidth / 2f + this.pos.x;
			yPos = this.borderWidth / 2f + this.getScrollBarScreenPosition() + this.pos.y;
			sh = PApplet.floor(this.scrollbarLength);
			sw = PApplet.floor(this.width);

		}
		return (x > xPos && x < xPos + sw && y > yPos && y < yPos + sh);
	}

	@Override
	protected PVector getDragOffset(float x, float y) {
		float xOff = (this.pos.x + this.getScrollBarScreenPosition() + this.scrollbarLength / 2f - this.borderWidth) - x;
		float yOff = (this.pos.y + this.getScrollBarScreenPosition() + this.scrollbarLength / 2f - this.borderWidth) - y;
		return new PVector(xOff, yOff);
	}

	// GETTERS AND SETTERS//
	public void setDefaultStyling() {
		this.backgroundColor = app.color(200);
		this.scrollbarColor = app.color(150);
		this.hoverColor = app.color(100);
		this.borderColor = app.color(0);
		this.borderWidth = 1f;
		this.fadeTime = 0;
		this.showBorder = false;
	}

	public int getOrientation() {
		return orientation;
	}

	public int getWidth() {
		return width;
	}

	protected void setWidth(int width) {
		this.mutatedFlag = true;
		this.width = PApplet.constrain(width, 0, Integer.MAX_VALUE);
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.mutatedFlag = true;
		this.backgroundColor = backgroundColor;
	}

	public int getScrollbarColor() {
		return scrollbarColor;
	}

	public void setScrollbarColor(int scrollbarColor) {
		this.mutatedFlag = true;
		this.scrollbarColor = scrollbarColor;
	}

	public int getHoverColor() {
		return hoverColor;
	}

	public void setHoverColor(int hoverColor) {
		this.mutatedFlag = true;
		this.hoverColor = hoverColor;
	}

	public int getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(int borderColor) {
		this.mutatedFlag = true;
		this.borderColor = borderColor;
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

	public float getScrollbarLength() {
		return scrollbarLength;
	}

	public void setScrollbarLength(float scrollbarLength) {
		this.mutatedFlag = true;
		this.scrollbarLength = scrollbarLength;
	}

	public SScrollPanel getParent() {
		return parent;
	}

}
