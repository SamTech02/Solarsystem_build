void simulate() {
  if (speedcount>=slower&&slower<=slowercap) {
    speedcount=0;
    if (faster>0) {
      for (int ii=0; ii<faster; ii++) {
        accelerate();
        moveplanets();
        time(true);
      }
    } else if (faster<0) {
      for (int ii=0; ii>faster; ii--) {
        moveplanets();
        accelerate();
        time(false);
      }
    }
  }
  speedcount++;
}
void accelerate() {
  for (int i = 0; i<planets.size(); i++) { 
    planets.get(i).accelerate(i, faster>0);
  }
}
void moveplanets() {
  for (int i = 0; i<planets.size(); i++) { 
    planet temp = planets.get(i);
    if (keys[2]&&plott[1]>plott[0]) {
      temp.plot();
    }
    temp.move(faster>0);
    if ((temp.position[0]/au>14||temp.position[1]/au>14||temp.position[2]/au>14||temp.position[0]/au<-14||temp.position[1]/au<-14||temp.position[2]/au<-14)) {
      planets.remove(i);
      i--;
    }
  }
  if (keys[2]&&plott[1]>plott[0])plott[1]=0;
  plott[1]++;
}
void time(Boolean Intime) {
  if (Intime) {
    minute[1]+=minute[minute[0]+2];
    if (minute[1]-(minute[1]%60)>0) {
      time[0]+=(minute[1]-(minute[1]%60))/60;
      minute[1]%=60;
      if (time[0]-(time[0]%24)>0) {
        time[1]+=(time[0]-(time[0]%24))/24;
        time[0]%=24;
        if (time[1]>year[time[2]-1]) {
          time[1]-=year[time[2]-1];
          time[2]++;
          if (time[2]>12) {
            time[3]++;
            time[2]-=12;
          }
        }
      }
    }
  } else {
    minute[1]-=minute[minute[0]+2];
    while (minute[1]<0) {
      time[0]-=1;
      minute[1]+=60;
      while (time[0]<0) {
        time[0]+=24;
        time[1]--;
        if (time[1]==0) {
          time[2]--;
          if (time[2]==0) {
            time[3]--;
            time[2]=12;
          }
          time[1]=year[time[2]-1];
        }
      }
    }
  }
}
void simulationbackground() {
  hint(ENABLE_DEPTH_TEST);
  hint(ENABLE_OPTIMIZED_STROKE);
  background(5);
  pushMatrix();
  translate(0, 0, 0);
  camera(xpos*1., ypos*1., zpos*1., -cos(rotx*1.)*sin(roty*1.)*1.+xpos*1., -sin(rotx*1.)+ypos*1., cos(rotx*1.)*cos(roty*1.)+zpos*1., 0, 1, 0);
  paint();
  popMatrix();
  translate(0, 0, 0);
  hint(DISABLE_OPTIMIZED_STROKE);
  hint(DISABLE_DEPTH_TEST);
}
void paint() {
  movementspeed=60;
  for (int i = 0; i<planets.size(); i++) { 
    planet temp = planets.get(i);
    if(cam[1]!=i||!(cam[0]==3))temp.paint();
    strokeCap(ROUND);
    if (keys[2])temp.drawplot();
  }
}
void removeplot() {
  if (!keys[2]) {
    for (int i=0; i<planets.size(); i++) {
      planets.get(i).deleteplot();
    }
  }
}
