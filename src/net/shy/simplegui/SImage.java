package net.shy.simplegui;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class SImage extends SComponent {

  public PImage i;

  public SImage(PApplet app, float x, float y) {
    super(app, x, y);
  }

  @Override
  public PVector globalToLocal(float x, float y) {
    return super.globalToLocal(x, y).sub(pos.x, pos.y);
  }

  @Override
  public void render(PGraphics g) {
    if (i != null)
      g.image(i, pos.x, pos.y);
    this.mutatedFlag = true;
  }

  @Override
  protected PGraphics createGraphics() {
    return null;
  }

}
