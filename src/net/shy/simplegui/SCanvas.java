package net.shy.simplegui;

import processing.core.PApplet;
import processing.core.PGraphics;

public class SCanvas extends SComponent {

  public PGraphics g;

  public SCanvas(PApplet app, float x, float y, int w, int h) {
    super(app, x, y);
    g = app.createGraphics(w, h);
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
