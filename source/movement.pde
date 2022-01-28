void move() {
  if (controll[0]) {
    xpos-=(float)sin(roty)*movementspeed;
    zpos+=(float)cos(roty)*movementspeed;
  }
  if (controll[2]) {
    xpos+=(float)sin(roty+HALF_PI)*movementspeed;
    zpos-=(float)cos(roty+HALF_PI)*movementspeed;
  }
  if (controll[1]) {
    xpos+=(float)sin(roty)*movementspeed;
    zpos-=(float)cos(roty)*movementspeed;
  }
  if (controll[3]) {
    xpos-=(float)sin(roty+HALF_PI)*movementspeed;
    zpos+=(float)cos(roty+HALF_PI)*movementspeed;
  }
  if (controll[4])ypos-=movementspeed*0.5;
  if (controll[5])ypos+=movementspeed*0.5;
  if (controll[6]) {
    xpos-=cos(rotx)*sin(roty)*movementspeed;
    ypos-=sin(rotx)*          movementspeed;
    zpos+=cos(rotx)*cos(roty)*movementspeed;
  }
  if (controll[7]) {
    xpos+=cos(rotx)*sin(roty)*movementspeed;
    ypos+=sin(rotx)*          movementspeed;
    zpos-=cos(rotx)*cos(roty)*movementspeed;
  }
  cam();
}
void cam() {
  roty+=(mouseX-widthhalf)*0.001;
  if (mouseX!=widthhalf) {
    robby.mouseMove(widthhalf, mouseY);
    mouseX=widthhalf;
  }
  if (roty>TWO_PI) {
    roty-=TWO_PI;
  }
  if (roty<0) {
    roty+=TWO_PI;
  }
  rotx=rotx-(mouseY-heighthalf)*0.001;
  if (mouseY!=heighthalf) {
    robby.mouseMove(mouseX, heighthalf);
    mouseY=heighthalf;
  }
  if (rotx>=HALF_PI-0.1) {
    rotx=HALF_PI-0.1;
  }
  if (rotx<=-HALF_PI+0.1) {
    rotx=-HALF_PI+0.1;
  }
}
void setcam(float x, float y, float z, float rotatex, float rotatey){
  xpos=x*1.;
  ypos=y*1.;
  zpos=z*1.;
  rotx=rotatex*1.;
  roty=rotatey*1.;
}
void IntoTheFuture() {
  if (faster>0) {
    if (slower==1) {
      faster*=2;
    } else {
      slower=(short)floor(slower/2);
    }
  } else {
    if (floor(faster/2)<0) {
      faster=floor(faster/2);
    } else {
      if (slower>slowercap) {
        faster=1;
        slower=slowercap;
      } else {
        slower*=2;
      }
    }
  }
}
void IntoTheFuture2() {
  if (faster>0) {
    if (slower==1) {
      faster+=1;
    } else {
      slower-=1;
    }
  } else {
    if (faster+1<0) {
      faster+=1;
    } else {
      if (slower>slowercap) {
        faster=1;
        slower=slowercap;
      } else {
        slower+=1;
      }
    }
  }
}
void IntoThePast() {
  if (faster<0) {
    if (slower==1) {
      faster*=2;
    } else {
      slower=(short)floor(slower/2);
    }
  } else {
    if (floor(faster/2)>0) {
      faster=floor(faster/2);
    } else {
      if (slower>slowercap) {
        faster=-1;
        slower=slowercap;
      } else {
        slower*=2;
      }
    }
  }
}
void IntoThePast2() {
  if (faster<0) {
    if (slower==1) {
      faster-=1;
    } else {
      slower-=1;
    }
  } else {
    if (faster-1>0) {
      faster-=1;
    } else {
      if (slower>slowercap) {
        faster=-1;
        slower=slowercap;
      } else {
        slower+=1;
      }
    }
  }
}
void resetmouse() {
  noCursor();
  mouseY=heighthalf;
  mouseX=widthhalf;
  robby.mouseMove(mouseX, heighthalf);
  mouseY=heighthalf;
  mouseX=widthhalf;
  if (inoverlay)cursor();
}
