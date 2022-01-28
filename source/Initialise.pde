void initialise() {
  soundini();
  Fonts[0] = createFont("SansSerif", 10);
  Fonts[1] = createFont("SansSerif", 25);
  Fonts[3] = createFont("SansSerif", 45);
  Fonts[4] = createFont("SansSerif", 50);
  Fonts[5] = createFont("SansSerif", 180);
  Fonts[6] = createFont("SansSerif", 40);
  Fonts[7] = createFont("SansSerif", 15);
  Fonts[8] = createFont("SansSerif", 22);
  Fonts[9] = createFont("SansSerif", 12);
  Fonts[10] = createFont("SansSerif", 18);
  percent+=0.1;
  delay(round(random(20))+30);
  speed=loadImage("menu/"+"speed.png");
  SamTech=loadImage("menu/"+"SamTechText.png");
  SamTech.resize(132, 40);
  setcam();
  inicontrolls();
  inikeys();
  main=new buttons("RESUME\nSETTINGS\nLOAD\nQUIT", 250, heighthalf+45, 230, 60, 80);
  paused=new buttons("RESUME\nSETTINGS\nSAVE\nLOAD\nQUIT", widthhalf, heighthalf+45, 180, 60, 80);
  savebutton[0]=new buttons("SAVE", widthhalf-87, heighthalf+36, 165, 40, 0, 1);
  savebutton[1]=new buttons("CANCEL", widthhalf+87, heighthalf+36, 165, 40, 0, 1);
  bigger=0;
  inoverlay=true;
  percent+=0.1;
  delay(round(random(20))+30);
  initializesystem(loadStrings(loadsetup[1]),0.6);
}

void setcam() {
  setcam(-99121.75, -35423.0, 53040.004, -0.3009999, 4.2179995);
}
void load(){
  initializesystem(loadStrings(loadsetup[1]), 0.8);
}
void initializesystem(String load[],float perc) {
  String temp[]=load[0].split("_");
  time[3]=(int)Double.parseDouble(temp[0]);
  time[2]=(int)Double.parseDouble(temp[1]);
  time[1]=(int)Double.parseDouble(temp[2]);
  time[0]=(int)Double.parseDouble(temp[3]);
  minute[0]=3;
  percent+=0.1;
  delay(round(random(20))+300);
  minute[1]=(short)Double.parseDouble(temp[4]);
  for (int i=1; i<load.length; i++) {
    temp=load[i].split("_");
    planet create =new planet(temp);
    planets.add(create);
    percent+=perc/(load.length-1);
    delay(round(random(20))+30);
  }
  cam[0]=0;
  cam[1]=0;
  delay(50);
  finished=true;
  percent+=0.1;
}

void inicontrolls() {
  Import=loadStrings("save.controlls");
  for (int i=0; i<controlls.length; i++) {
    controlls[i]=(short)Double.parseDouble(Import[2*i+1]);
  }
}
void inikeys() {
  Import=loadStrings("save.keys");
  for (int i=0; i<keys.length; i++) {
    keyss[i]=(short)Double.parseDouble(Import[2*i+1]);
  }
  keys[0]=true;
  keys[2]=true;
}
void soundini() {
  file.amp(0.25);
  file.amp(0);
  file.loop();
}
