void overlayklicked() {
  if (mouseposadd(6, height-100, width-12, 106)) {
    if (mousepos(48, 13, height-13, height-46)) {
      keys[1]=!keys[1];
    } else if (mouseposadd(200, height-93, 40, 40)) {
      keys[2]=!keys[2];
      removeplot();
    }
    if (mouseposadd(200, height-47, 40, 40)) {
      keys[0]=!keys[0];
      sound();
    } else if (mouseposadd(widthhalf-89, height-60, 40, 40)) {
      IntoThePast();
    } else if (mouseposadd(widthhalf-43, height-60, 40, 40)) {
      IntoThePast2();
    } else if (mouseposadd(widthhalf+3, height-60, 40, 40)) {
      IntoTheFuture2();
    } else if (mouseposadd(widthhalf+49, height-60, 40, 40)) {
      IntoTheFuture();
    } else if (mouseposadd(widthhalf+200, height-50, 200, 20)) {
      bigger=(short)round((mouseX-widthhalf-200)/5);
    } else if (mouseposadd(widthhalf-450, height-50, 300, 20)) {
      minute[0]=(short)floor((mouseX-(widthhalf-475))/50);
      if(cam[1]==planets.size()-2&&minute[0]==2&&!keys[2]&&cam[0]==2)minute[0]=7;
    } else if (mouseposadd(width-247, height-70, 30, 30)) {
      cam[0]=-1;
    } else if (mouseposadd(width-247, height-37, 30, 30)) {
      if (cam[1]>0&&cam[0]>2)cam[1]--;
    } else if (mouseposadd(width-214, height-70, 30, 30)) {
      cam[0]=0;
      setcam(-99121.75, -35423.0, 53040.004, -0.3009999, 4.2179995);
    } else if (mouseposadd(width-214, height-37, 30, 30)) {
      cam[0]=3;
    } else if (mouseposadd(width-181, height-70, 30, 30)) {
      cam[0]=1;
      setcam(-47619.594, -357769.72, 39219.223, -1.4707963, 4.3289995);
    } else if (mouseposadd(width-181, height-37, 30, 30)) {
      cam[0]=4;
    } else if (mouseposadd(width-148, height-70, 30, 30)) {
      cam[0]=2;
    } else if (mouseposadd(width-148, height-37, 30, 30)) {
      if (cam[1]<planets.size()-1&&cam[0]>2)cam[1]++;
    }
  }
}
boolean mousepos (int xmax, int xmin, int ymax, int ymin) {
  return (mouseX<xmax&&mouseX>xmin&&mouseY<ymax&&mouseY>ymin);
}
