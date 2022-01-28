void simbottomenu() {
  menu.beginDraw();
  menu.strokeJoin(ROUND);
  menu.background(0, 0);
  menu.strokeWeight(2);
  menu.stroke(70);
  menu.fill(40, 170);
  menu.rect(6, height-100, width-12, 106, 7);
  menu.image(SamTech, width-99, height-97,90, 30);
  menu.fill(40);
  if (!keys[1]) {
    menu.rect(13, height-46, 15, 32, 2);
    menu.rect(33, height-46, 15, 32, 2);
  } else {
    menu.triangle(18, height-48, 18, height-12, 41, height-30);
  }
  menu.fill(255);
  menu.textFont(Fonts[0]);
  menu.textAlign(RIGHT, BOTTOM);
  menu.text(getClass().getName(), width-9, height-3);
  menu.textAlign(CENTER, BOTTOM);
  menu.textFont(Fonts[7]);
  menu.text("step size", widthhalf-300, height-50);
  menu.text("object size", widthhalf+300, height-50);
  menu.textFont(Fonts[10]);
  menu.text("CAMERA", width-184, height-73);
  menu.textFont(Fonts[9]);
  String out="0";
  if (slower<=slowercap)out=""+faster*60/slower;
  menu.text(out+"steps/second", widthhalf, height-4);
  menu.text("1m", widthhalf-450, height-14);
  menu.text("5m", widthhalf-400, height-14);
  menu.text("20m", widthhalf-350, height-14);
  menu.text("1h", widthhalf-300, height-14);
  menu.text("6h", widthhalf-250, height-14);
  menu.text("12h", widthhalf-200, height-14);
  menu.text("1d", widthhalf-150, height-14);
  menu.textFont(Fonts[8]);
  menu.text("SPEED", widthhalf, height-65);
  menu.textAlign(LEFT, TOP);
  menu.text(loadsetup[0], 9, height-100);
  menu.textFont(Fonts[7]);
  menu.text(planets.size()+" Objects", 9, height-75);
  menu.textAlign(LEFT, CENTER);
  out="";
  if (time[1]<10)out+="0"+time[1]+"-";
  else out+=time[1]+"-";
  if (time[2]<10)out+="0"+time[2];
  else out+=time[2];
  out+="-"+time[3]+"  ";
  if (time[0]<10)out+="0"+time[0]+":";
  else out+=time[0]+":";
  if (minute[1]<10)out+="0"+(short)minute[1];
  else out+=(short)minute[1];
  menu.text(out, 60, height-33);
  menu.strokeWeight(1);
  simbottomenubuttons();
  if (framerate<warningnum&&state=="SIM") {
    menu.image(speed, width-63, height-65, 50, 50);
  }
  menu.endDraw();
}
void simbottomenubuttons() {
  menu.stroke(70);
  menu.fill(60, 50);
  menu.rect(200, height-93, 40, 40, 5);
  menu.rect(200, height-47, 40, 40, 5);
  menu.rect(246, height-93, 40, 40, 5);
  menu.rect(246, height-47, 40, 40, 5);
  menu.rect(widthhalf-43, height-60, 40, 40);
  menu.rect(widthhalf+3, height-60, 40, 40);
  menu.rect(widthhalf-89, height-60, 40, 40);
  menu.rect(widthhalf+49, height-60, 40, 40);
  menu.rect(width-247, height-70, 30, 30, 2);
  menu.rect(width-247, height-37, 30, 30, 2);
  menu.rect(width-214, height-70, 30, 30, 2);
  menu.rect(width-214, height-37, 30, 30, 2);
  menu.rect(width-181, height-70, 30, 30, 2);
  menu.rect(width-181, height-37, 30, 30, 2);
  menu.rect(width-148, height-70, 30, 30, 2);
  menu.rect(width-148, height-37, 30, 30, 2);
  if (keys[2]) {
    menu.stroke(220);
  } else {
    menu.stroke(10);
  }
  menu.noFill();
  menu.strokeWeight(2);
  menu.arc(220, height-73, 24, 24, -PI, 1.7);
  menu.strokeWeight(8);
  menu.point(208, height-73);
  menu.strokeWeight(0);
  if (keys[0]) {
    menu.stroke(220);
    menu.fill(220);
  } else {
    menu.stroke(10);
    menu.fill(10);
  }
  menu.rect(205, height-32, 7, 10);
  menu.triangle(205, height-27, 219, height-41, 219, height-13);
  menu.strokeWeight(1);
  menu.noFill();
  menu.arc(221, height-27, 10, 10, -HALF_PI+0.1, HALF_PI-0.1);
  menu.arc(221, height-27, 30, 30, -HALF_PI+0.3, HALF_PI-0.3);
  menu.arc(221, height-27, 20, 20, -HALF_PI+0.2, HALF_PI-0.2);
  menu.stroke(220);
  menu.fill(220);
  menu.triangle(widthhalf-14, height-52, widthhalf-14, height-28, widthhalf-33, height-40);
  menu.triangle(widthhalf+14, height-52, widthhalf+14, height-28, widthhalf+33, height-40);
  menu.triangle(widthhalf+57, height-52, widthhalf+57, height-28, widthhalf+69, height-40);
  menu.triangle(widthhalf+71, height-52, widthhalf+71, height-28, widthhalf+83, height-40);
  menu.triangle(widthhalf-57, height-52, widthhalf-57, height-28, widthhalf-69, height-40);
  menu.triangle(widthhalf-71, height-52, widthhalf-71, height-28, widthhalf-83, height-40);
  menu.strokeWeight(2);
  menu.line(widthhalf-450, height-40, widthhalf-150, height-40);
  menu.line(widthhalf-450, height-43, widthhalf-450, height-37);
  menu.line(widthhalf-400, height-43, widthhalf-400, height-37);
  menu.line(widthhalf-350, height-43, widthhalf-350, height-37);
  menu.line(widthhalf-300, height-43, widthhalf-300, height-37);
  menu.line(widthhalf-250, height-43, widthhalf-250, height-37);
  menu.line(widthhalf-200, height-43, widthhalf-200, height-37);
  menu.line(widthhalf-150, height-43, widthhalf-150, height-37);
  menu.line(widthhalf+200, height-40, widthhalf+400, height-40);
  menu.strokeWeight(10);
  menu.point(widthhalf+200+bigger*5, height-40);
  menu.point(widthhalf-450+minute[0]*50, height-40);
  menu.strokeWeight(2);
  if (cam[0]<3) {
    menu.stroke(20);
    menu.fill(20);
  }
  menu.triangle(width-224, height-30, width-224, height-14, width-241, height-22);
  menu.triangle(width-141, height-30, width-141, height-14, width-123, height-22);
  menu.textAlign(LEFT, TOP);
  menu.textFont(Fonts[0]);
  activatedbutton(-1);
  menu.line(width-222, height-55,width-242, height-55);
  menu.line(width-222, height-55,width-225, height-52);
  menu.line(width-222, height-55,width-225, height-58);
  menu.line(width-242, height-55,width-239, height-52);
  menu.line(width-242, height-55,width-239, height-58);
  menu.line(width-232, height-45,width-232, height-65);
  menu.line(width-232, height-45,width-235, height-48);
  menu.line(width-232, height-45,width-229, height-48);
  menu.line(width-232, height-65,width-235, height-62);
  menu.line(width-232, height-65,width-229, height-62);
  activatedbutton(0);
  menu.text("standard",width-214, height-70);
  activatedbutton(1);
  //menu.rect(width-247, height-70, 30, 30, 2);
  menu.strokeWeight(5);
  menu.point(width-166, height-55);
  menu.point(width-162, height-51);
  menu.point(width-171, height-65);
  menu.noFill();
  menu.strokeWeight(2);
  menu.arc(width-166, height-55, 23, 23, -HALF_PI-0.3, HALF_PI+0.2);
  menu.arc(width-166, height-55, 12, 12, -HALF_PI-1, HALF_PI-0.7);
  activatedbutton(2);
  menu.text("don't\nknow",width-148, height-70);
  activatedbutton(3);
  menu.text("planet",width-214, height-37);
  activatedbutton(4);
  menu.text("from\nplanet",width-181, height-37);
}
void activatedbutton(int in) {
  if (cam[0]==in) {
    menu.stroke(220);
    menu.fill(220);
  } else {
    menu.stroke(10);
    menu.fill(10);
  }
}
