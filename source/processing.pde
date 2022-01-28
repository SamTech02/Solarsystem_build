void startit(String in) {
  switch(in) {
  case "0":
    tint(255);
    image(LOGO[0], 0, 0, width, height);
    frames++;
    if (frames==100) {
      state="START_1";
      frames=0;
    }
    break;
  case "1":
    tint(255, 2);
    image(LOGO[1], 0, 0, width, height);
    frames++;
    if (frames==70) {
      state="START_2";
      frames=0;
    }
    delay(1);
    break;
  case "2":
    tint(255, 5);
    image(LOGO[2], 0, 0, width, height);
    frames++;
    if (frames==200) {
      state="START_3";
      frames=0;
    }
    delay(4);
    break;
  case "3":
    tint(255);
    state="START_4";
    finished=false;
    thread("initialise");
    break;
  case "4":
    background(0);
    strokeWeight(3);
    image(LOGO[2], 0, 0, width, height);
    noFill();
    stroke(255);
    rect(width/2-300, height-280, 600, 20, 4);
    fill(255);
    noStroke();
    rect(width/2-300, height-280, 600*percent, 20, 4);
    textFont(Fonts[2]);
    textAlign(CENTER, TOP);
    text("Loading", width/2, height-250);
    if (!finished) {
    } else {
      state="MAINMENU";
      start=false;
      delay(100);
    }
    break;
  case "A":
    thread("load");
    percent=0;
    finished=false;
    state="START_B";
    break;
  case "B":
    background(0);
    strokeWeight(3);
    image(LOGO[2], 0, 0, width, height);
    noFill();
    stroke(255);
    rect(width/2-300, height-280, 600, 20, 4);
    fill(255);
    noStroke();
    rect(width/2-300, height-280, 600*percent, 20, 4);
    textFont(Fonts[2]);
    textAlign(CENTER, TOP);
    text("Loading", width/2, height-250);
    if (!finished) {
    } else {
      state="MAINMENU";
      start=false;
      delay(100);
    }
    break;
  }
}
