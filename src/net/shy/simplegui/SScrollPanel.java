package net.shy.simplegui;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 * A Simple ScrollPanel
 * 
 * @author Shy
 *
 */
public class SScrollPanel extends SContainer {

	private int width;
	private int height;
	private int actualWidth;
	private int actualHeight;

	private int backgroundColor;
	private float borderWidth;
	private int borderColor;
	private int scrollbarWidth;

	private float borderRadius;
	private boolean showBorder;

	private SScrollBar verticalBar = null;
	private SScrollBar horizontalBar = null;

	/**
	 * 
	 * @param app       Reference to the PApplet instance
	 * @param x         ScrollPanel x Position in container space
	 * @param y         ScrollPanel y Position in container space
	 * @param viewportW Width of the ScrollPanel ViewPort
	 * @param viewportH Height of the ScrollPanel ViewPort
	 * @param actualW   Actual width of the ScrollPanel. Must be >= width
	 * @param actualH   Actual height of the ScrollPanel. Must be >= height
	 */
	public SScrollPanel(PApplet app, float x, float y, int viewportW, int viewportH, int actualW, int actualH) {
		super(app, x, y);
		this.width = viewportW;
		this.height = viewportH;

		this.actualWidth = PApplet.max(this.width, actualW);
		this.actualHeight = PApplet.max(this.height, actualH);

		if (this.actualHeight > this.height) {
			this.verticalBar = new SScrollBar(app, this, SScrollBar.VERTICAL);
		}

		if (this.actualWidth > this.width) {
			this.horizontalBar = new SScrollBar(app, this, SScrollBar.HORIZONTAL);
		}

		this.setDefaultStyling();
	}

	protected PVector getScrollTranslation() {
		// SCROLL TRANSLATION
		float translateX = 0, translateY = 0;
		if (this.horizontalBar != null) {
			translateX = PApplet.map((float) this.horizontalBar.getValue(), 0, 1, 0, (this.actualWidth - this.width));
		}
		if (this.verticalBar != null) {
			translateY = PApplet.map((float) this.verticalBar.getValue(), 0, 1, 0, (this.actualHeight - this.height));
		}
		return new PVector(-translateX, -translateY);
	}

	@Override
	public PVector globalToLocal(float x, float y) {
		return super.globalToLocal(x, y).sub(this.getScrollTranslation());
	}

	@Override
	public void render(PGraphics g) {
		if (this.horizontalBar != null) {
			this.mutatedFlag |= this.horizontalBar.mutatedFlag;
		}
		if (this.verticalBar != null) {
			this.mutatedFlag |= this.verticalBar.mutatedFlag;
		}
		super.render(g);
	}

	@Override
	protected PGraphics createGraphics() {

		float borderOffset = this.showBorder ? this.borderWidth / 2f : 0;

		PGraphics graphics = app.createGraphics(this.width, this.height);
		graphics.beginDraw();

		graphics.fill(this.backgroundColor);
		if (this.showBorder) {
			graphics.stroke(this.borderColor);
			graphics.strokeWeight(this.borderWidth);
		} else {
			graphics.noStroke();
		}

		graphics.rect(borderOffset, borderOffset, this.width - borderOffset * 2, this.height - borderOffset * 2,
				this.borderRadius);

		PVector scrollTranslation = this.getScrollTranslation();

		graphics.push();
		graphics.translate(scrollTranslation.x, scrollTranslation.y);

		for (SComponent comp : this.components) {
			comp.render(graphics);
		}
		graphics.pop();

		// Vertical Scrollbar

		if (this.verticalBar != null) {
			float scrollbarLength = graphics.height - this.scrollbarWidth - borderOffset * 4
					+ this.verticalBar.getBorderWidth() / 4;

			if (this.horizontalBar != null) {
				scrollbarLength -= this.horizontalBar.getBorderWidth() / 4;
			}

			scrollbarLength += 1.5;

			float scrollbarHandleLength = this.height / (this.actualHeight * 1f) * scrollbarLength;

			this.verticalBar.setScrollbarLength(scrollbarHandleLength);
			this.verticalBar.setLength(scrollbarLength);
			this.verticalBar.setWidth(this.scrollbarWidth);

			float scrollbarX = graphics.width - this.scrollbarWidth - this.verticalBar.getBorderWidth() - borderOffset * 2;
			float scrollbarY = borderOffset * 2;

			scrollbarX += 1.5f;
			scrollbarY -= 0.5f;

			this.verticalBar.pos = new PVector(scrollbarX, scrollbarY);
			this.verticalBar.render(graphics);
		}

		if (this.horizontalBar != null) {
			float scrollbarLength = graphics.width - this.scrollbarWidth - borderOffset * 4
					+ this.horizontalBar.getBorderWidth() / 4;

			if (this.verticalBar != null) {
				scrollbarLength -= this.verticalBar.getBorderWidth() / 4;
			}

			scrollbarLength += 1.5;

			float scrollbarX = borderOffset * 2;
			float scrollbarY = graphics.height - this.scrollbarWidth - this.horizontalBar.getBorderWidth() - borderOffset * 2;

			scrollbarX -= 0.5f;
			scrollbarY += 1.5f;

			float scrollbarHandleLength = (this.width / (this.actualWidth * 1f) * scrollbarLength);

			this.horizontalBar.setScrollbarLength(scrollbarHandleLength);
			this.horizontalBar.setLength(scrollbarLength);
			this.horizontalBar.setWidth(this.scrollbarWidth);
			this.horizontalBar.pos = new PVector(scrollbarX, scrollbarY);
			this.horizontalBar.render(graphics);
		}

		graphics.endDraw();
		return graphics;

	}

	// GETTERS AND SETTERS//

	/**
	 * Sets all styling options to default
	 */
	public void setDefaultStyling() {
		this.backgroundColor = app.color(255, 255);
		this.borderColor = app.color(0);
		this.borderWidth = 1.5f;
		this.borderRadius = 0;
		this.scrollbarWidth = 15;
		this.showBorder = true;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.mutatedFlag = true;
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
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

	public float getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(float borderWidth) {
		this.mutatedFlag = true;
		this.borderWidth = borderWidth;
	}

	public int getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(int borderColor) {
		this.mutatedFlag = true;
		this.borderColor = borderColor;
	}

	public float getBorderRadius() {
		return borderRadius;
	}

	public void setBorderRadius(float borderRadius) {
		this.mutatedFlag = true;
		this.borderRadius = borderRadius;
	}

	public boolean isShowBorder() {
		return showBorder;
	}

	public void setShowBorder(boolean showBorder) {
		this.mutatedFlag = true;
		this.showBorder = showBorder;
	}

	public SScrollBar getVerticalBar() {
		return verticalBar;
	}

	public SScrollBar getHorizontalBar() {
		return horizontalBar;
	}

	public int getActualWidth() {
		return actualWidth;
	}

	public void setActualWidth(int actualWidth) {
		this.mutatedFlag = true;
		this.actualWidth = PApplet.max(this.width, actualWidth);
	}

	public int getActualHeight() {
		return actualHeight;
	}

	public void setActualHeight(int actualHeight) {
		this.mutatedFlag = true;
		this.actualHeight = PApplet.max(this.height, actualHeight);
	}

	public int getScrollbarWidth() {
		return scrollbarWidth;
	}

	public void setScrollbarWidth(int scrollbarWidth) {
		this.mutatedFlag = true;
		this.scrollbarWidth = scrollbarWidth;
	}

}
