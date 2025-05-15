import net.shy.simplegui.*;
import net.shy.simplegui.events.*;

SButton btn;
SSlider slider;

SCheckBox chbx;

SRadioButton radio1;
SRadioButton radio2;
SRadioButton radio3;
SRadioButton radio4;

SRadioButton radio5;
SRadioButton radio6;
SRadioButton radio7;
SRadioButton radio8;

void setup() {
  size(500, 200);

  //Button at position x:20 y:20 with width:100 height:50
  //Label:My Button Callback:myCallback
  btn = new SButton(this, 20, 20, 100, 30, "My Button", "myCallback");

  //Slider at position x:20 y:50 with length:150(px)
  //with values from 0-10, steps in 0.1 increments
  slider = new SSlider(this, 20, 90, 150, 0, 10, 0.1);

  //checkbox at position x:140 y:20 with size:15(px)
  chbx = new SCheckBox(this, 140, 20, 15);

  //Create four radiobuttons of class "Class1"
  radio1 = new SRadioButton(this, 300, 20, 7, "Radio1", "Class1");
  radio2 = new SRadioButton(this, 325, 20, 7, "Radio2", "Class1");
  radio3 = new SRadioButton(this, 350, 20, 7, "Radio3", "Class1");
  radio4 = new SRadioButton(this, 375, 20, 7, "Radio4", "Class1");

  //Create four radiobuttons of class "Class2"
  radio5 = new SRadioButton(this, 300, 40, 7, "Radio5", "Class2");
  radio6 = new SRadioButton(this, 325, 40, 7, "Radio6", "Class2");
  radio7 = new SRadioButton(this, 350, 40, 7, "Radio7", "Class2");
  radio8 = new SRadioButton(this, 375, 40, 7, "Radio8", "Class2");
}

void draw() {
  background(230);

  noStroke();
  fill(0);

  //display slider value (cast to float since getValue() returns a double)
  text((float)slider.getValue(), 170, 100);

  //display checkbox state
  if (chbx.getState()) {
    text("I am checked!", 165, 30);
  } else {
    text("I am not checked :(", 165, 30);
  }

  //display currently selected radiobutton of Class1
  SRadioButton activeRadio1 = SRadioButton.getActive("Class1");
  if (activeRadio1 != null) {
    text(activeRadio1.getName(), 400, 30);
  }

  //display currently selected radiobutton of Class2
  SRadioButton activeRadio2 = SRadioButton.getActive("Class2");
  if (activeRadio2 != null) {
    text(activeRadio2.getName(), 400, 50);
  }

}

void myCallback() {
  println("Ouch!");
}
