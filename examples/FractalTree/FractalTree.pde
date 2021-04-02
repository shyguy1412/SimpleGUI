import net.nilsramstoeck.simplegui.*;
import net.nilsramstoeck.simplegui.events.*;

float angle; //stores angle of branches
SSlider slider;

void setup(){
  size(500, 700);
  slider = new SSlider(this, 20, 20, 150, 0, 180, 0.11);
}

void draw() {
  background(51);
  
  //set angle to value on slider
  angle = radians((float)slider.getValue());

  stroke(255);
  push();
  translate(width/2, height);
  branch(height/4, 10);
  pop();
}

void branch(float len, float d){
  //draw line len length
  strokeWeight(2);
  line(0, 0, 0, -len);
  translate(0, -len);

  //point connecting the branches
  strokeWeight(d);
  point(0, 0);
  strokeWeight(2);

  if (len > 4) {
    push();
    rotate(angle);
    branch(len * 0.67, d * 0.8);
    pop();

    push();
    rotate(-angle);
    branch(len * 0.67, d * 0.8);
    pop();
  }
}
