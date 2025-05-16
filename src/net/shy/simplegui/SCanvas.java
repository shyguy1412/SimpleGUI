package net.shy.simplegui;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class SCanvas extends SComponent {

  public PGraphics g;

  public SCanvas(PApplet app, float x, float y, int w, int h) {
    super(app, x, y);
    g = app.createGraphics(w, h);
  }

  @Override
  public PVector globalToLocal(float x, float y) {
    return super.globalToLocal(x, y).sub(pos.x, pos.y);
  }

  @Override
  public void render(PGraphics g) {
    super.render(g);
    this.mutatedFlag = true;
  }

  @Override
  protected PGraphics createGraphics() {
    return g;
  }

}
