import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.awt.AWTException; 
import java.awt.Robot; 
import processing.sound.*; 
import java.util.Date; 
import java.math.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Solarsystem extends PApplet {

  //<>//




PGraphics pg, menu;
public void setup() {
  pg = createGraphics(200, 200, P3D);
  file = new SoundFile(this, "ambient");
  LOGO[0]=loadImage("menu/SamTech1.png", "png");
  LOGO[1]=loadImage("menu/SamTech2.png", "png");
  LOGO[2]=loadImage("menu/SamTech3.png", "png");
  loadsetup=loadStrings("save.setup");
  frameRate(60);
  try {
    robby = new Robot();
  }
  catch (AWTException e)
  {
    println("Robot class not supported by your system!");
    exit();
  }
  
  menu = createGraphics(width, height, P2D);
  perspective(60 * DEG_TO_RAD, (float) width / (float) height, height / 2.0f / ((float) Math.tan(60 * DEG_TO_RAD / 2.0f)) / 10.0f, height / 2.0f / ((float) Math.tan(60 * DEG_TO_RAD / 2.0f)) * 10.0f*renderdistance);
  image(LOGO[0], 0, 0, width, height);
  planets = new ArrayList<planet>();
  widthhalf=width/2;
  heighthalf=height/2;
  timeswidth=((width-30)-(width-30)%600)/600;
  left=widthhalf-600*timeswidth/2;
  
  Fonts[2] = createFont("SansSerif", 35);
}
public void draw() { 
  switch (state) {
  case "MAINLOAD":
    hint(ENABLE_DEPTH_TEST);
    simulationbackground();
    hint(DISABLE_DEPTH_TEST);
    screen();
    mainmenu();
    loadmenu();
    break;
  case "CREDITS":
    hint(ENABLE_DEPTH_TEST);
    simulationbackground();
    hint(DISABLE_DEPTH_TEST);
    screen();
    mainmenu();
    credits();
    break;
  case "SIM":
    if (!start) {
      start=true;
      if (!inoverlay)resetmouse();
    } else {
      if (!inoverlay&&cam[0]==-1)move();
      movementspeed=60;
      if (!keys[1]) {
        if (overlay=="")simulate();
      }
      if (cam[0]>2) {
        planet temp=planets.get(cam[1]);
        xpos=(float)(temp.position[0]/maszstab*calculation);
        ypos=(float)(-temp.position[2]/maszstab*calculation);
        zpos=(float)(-temp.position[1]/maszstab*calculation);
        if (cam[0]==4) {
          ypos-=temp.diameter/(maszstab)*2*(bigger+1)+150+200*log(bigger+1);
          xpos+=temp.diameter/(maszstab)*2*(bigger+1)+150+200*log(bigger+1);
          zpos+=temp.diameter/(maszstab)*2*(bigger+1)+150+200*log(bigger+1);
        }
        if (!inoverlay)cam();
      }
      frames++;
      if (frames>2000)frames=0;
      overlay();
    }
    break;
  case "ERROR":
    break;
  default: 
    break;
  }
  overlay();
  if (count[0]!=second()) {
    framerate=count[1];
    count[0]=(short)second();
    count[1]=0;
  }
  count[1]++;
}
class planet { //<>//
  String name;
  double position[]=new double[3];
  double v[]={10, 10, 10};
  double mass=100;
  float diameter;
  String texture;
  int farbe=color(255);
  boolean move=true;
  boolean lights=false;
  String info;
  PImage img;
  PShape globes[]=new PShape[3];
  ArrayList<Position> plotting=new ArrayList();
  planet(String input[]) {
    if (input.length==14) {
      name=input[0];
      position[0]=(Double.parseDouble(input[1])*au)/calculation;
      position[1]=(Double.parseDouble(input[2])*au)/calculation;
      position[2]=(Double.parseDouble(input[3])*au)/calculation;
      v[0]=Double.parseDouble(input[4])*au/24;
      v[1]=Double.parseDouble(input[5])*au/24;
      v[2]=Double.parseDouble(input[6])*au/24;
      mass=Double.parseDouble(input[7]);
      diameter=(float)Double.parseDouble(input[8]);
      texture=input[9];
      farbe=unhex("FF"+input[10].substring(1));
      if (input[11].charAt(0)=='N')move=false;
      if (input[12].charAt(0)=='Y')lights=true;
      info=input[13];
      ini();
    } else {
      exit();
    }
  }
  public void ini() {
    fill(255);
    img=loadImage("textures/"+texture+".jpg");
    strokeWeight(8);
    stroke(farbe);
    globes[0] = createShape(SPHERE, diameter/maszstab/2); 
    globes[0].setTexture(img);
    globes[2] = createShape(SPHERE, 75); 
    globes[2].setTexture(img);
    globes[1] = createShape(SPHERE, 16); 
    globes[1].setFill(farbe);
    globes[0].setStroke(false);
    globes[2].setStroke(false);
    globes[1].setStroke(false);
  }
  public void move(Boolean Intime) {
    if (move) {
      short times = 1;
      if (!Intime)times = -1;
      for (int i=0; i<3; i++) {
        position[i]+=v[i]/calculation*times*(minute[minute[0]+2]/60.f);
      }
    }
  }
  public void accelerate(int ID, Boolean Intime) {
    for (int ii=ID+1; ii<planets.size(); ii++) {
        planet obj2 = planets.get(ii);
        double a, distance;
        distance=Math.pow(Math.pow((position[0]-obj2.position[0]), 2)+Math.pow((position[1]-obj2.position[1]), 2)+Math.pow((position[2]-obj2.position[2]), 2), 0.5f);
        a=((grav*pow( 10, -11)*obj2.mass)/(Math.pow(distance*calculation*1000, 2)))*12960;
        float time = 1;
        if (!Intime)time = -1;
        if (distance>0.000001f) {
          v[0]-=(position[0]-obj2.position[0])*a/distance*time*(minute[minute[0]+2]/60.f);  
          v[1]-=(position[1]-obj2.position[1])*a/distance*time*(minute[minute[0]+2]/60.f);
          v[2]-=(position[2]-obj2.position[2])*a/distance*time*(minute[minute[0]+2]/60.f);
          a=((grav*pow( 10, -11)*mass)/(Math.pow(distance*calculation*1000, 2)))*12960;
          obj2.v[0]-=(obj2.position[0]-position[0])*a/distance*time*(minute[minute[0]+2]/60.f);  
          obj2.v[1]-=(obj2.position[1]-position[1])*a/distance*time*(minute[minute[0]+2]/60.f);
          obj2.v[2]-=(obj2.position[2]-position[2])*a/distance*time*(minute[minute[0]+2]/60.f);
        }else println(ID+"   "+ii);
    }
  }
  public void paint() {
    pushMatrix();
    translate((float)(position[0]/maszstab*calculation), (float)(-position[2]/maszstab*calculation), (float)(-position[1])/maszstab*calculation);
    double distance=Math.pow(Math.pow((position[0]/maszstab*calculation-xpos), 2)+Math.pow((-position[2]/maszstab*calculation-ypos), 2)+Math.pow((-position[1]/maszstab*calculation-zpos), 2), 0.5f);
    if (diameter/maszstab<movementspeed&&distance/diameter*20<1)movementspeed=diameter/maszstab;
    float scale=(float)(Math.pow(Math.pow((position[0]/maszstab*calculation-xpos), 2)+Math.pow((-position[2]/maszstab*calculation-ypos), 2)+Math.pow((-position[1]/maszstab*calculation-zpos), 2), 0.5f)/maszstab);
    scale(bigger+1);
    shape(globes[0]);
    scale(scale/(bigger+1));
    shape(globes[1]);
    popMatrix();
  }
  public String data() {
    String Data="";
    Data+=name+"_"+position[0]/au*calculation+"_"+position[1]/au*calculation+"_"+position[2]/au*calculation+"_"+v[0]/au*24+"_"+v[1]/au*24+"_"+v[2]/au*24+"_"+mass+"_"+diameter+"_"+texture+"_"+hex(farbe)+"_";
    if (move) {
      Data+="Y_";
    } else {
      Data+="N_";
    }
    if (lights) {
      Data+="Y_";
    } else {
      Data+="N_";
    }
    Data+=info;
    return Data;
  }
  public void plot() {
    plotting.add(new Position(position));
    if (plotting.size()>plotsize)plotting.remove(0);
  }
  public void deleteplot() {
    while (plotting.size()>0) {
      plotting.remove(0);
    }
  }
  public void drawplot() {
    fill(farbe);
    stroke(farbe);
    strokeWeight(3);
    if (plotting.size()>1) {
      Position temp1=plotting.get(0);
      Position temp2;
      for (int i=1; i<plotting.size(); i++) {
        temp2=plotting.get(i);
        line((float)(temp1.getpos()[0]/maszstab*calculation), (float)(-temp1.getpos()[2]/maszstab*calculation), (float)(-temp1.getpos()[1])/maszstab*calculation, (float)(temp2.getpos()[0]/maszstab*calculation), (float)(-temp2.getpos()[2]/maszstab*calculation), (float)(-temp2.getpos()[1])/maszstab*calculation);
        temp1=temp2;
      }
      line((float)(temp1.getpos()[0]/maszstab*calculation), (float)(-temp1.getpos()[2]/maszstab*calculation), (float)(-temp1.getpos()[1])/maszstab*calculation, (float)(position[0]/maszstab*calculation), (float)(-position[2]/maszstab*calculation), (float)(-position[1])/maszstab*calculation);
    }
  }
  class Position {
    double [] pos=new double[3];
    Position(double[] current) {
      pos[0]=current[0];
      pos[1]=current[1];
      pos[2]=current[2];
    }
    public double[] getpos() {
      return pos;
    }
  }
}
public void initialise() {
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
  percent+=0.1f;
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
  percent+=0.1f;
  delay(round(random(20))+30);
  initializesystem(loadStrings(loadsetup[1]),0.6f);
}

public void setcam() {
  setcam(-99121.75f, -35423.0f, 53040.004f, -0.3009999f, 4.2179995f);
}
public void load(){
  initializesystem(loadStrings(loadsetup[1]), 0.8f);
}
public void initializesystem(String load[],float perc) {
  String temp[]=load[0].split("_");
  time[3]=(int)Double.parseDouble(temp[0]);
  time[2]=(int)Double.parseDouble(temp[1]);
  time[1]=(int)Double.parseDouble(temp[2]);
  time[0]=(int)Double.parseDouble(temp[3]);
  minute[0]=3;
  percent+=0.1f;
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
  percent+=0.1f;
}

public void inicontrolls() {
  Import=loadStrings("save.controlls");
  for (int i=0; i<controlls.length; i++) {
    controlls[i]=(short)Double.parseDouble(Import[2*i+1]);
  }
}
public void inikeys() {
  Import=loadStrings("save.keys");
  for (int i=0; i<keys.length; i++) {
    keyss[i]=(short)Double.parseDouble(Import[2*i+1]);
  }
  keys[0]=true;
  keys[2]=true;
}
public void soundini() {
  file.amp(0.25f);
  file.amp(0);
  file.loop();
}
public void keyPressed() { //<>//
  if(key=='l'){
    println(xpos+"\n"+ypos+"\n"+zpos+"\n"+rotx+"\n"+roty+"\n");
  }
  if(key=='ß'){
    String setup="";
    for (int i=0; loadsetup[0].length()>i&&loadsetup[0].charAt(i)!='_'; i++) {
      setup+=loadsetup[0].charAt(i);
    }
    saving(setup+"_"+day()+"_"+month()+"_"+year()+"_"+hour()+"_"+minute()+"_"+second());
  }
  if (key=='j') {
    //planets.add(new planet(0, 0, au, 0.0171995*au/24, 0, 0, 43567, "4356E13", "Sh-ubble"+round(random(1,1000)), ""+round(random(7)), false, true, "Test-object"));
    String[] filenames = listFileNames(sketchPath()+"/data/textures/");
    int rand=round(random(filenames.length-0.5f));
    String temp="Sh-ubble"+round(random(1, 1000))+"_1_0_0_0_0.0171995_0_3.634E"+round(random(30))+"_43567_"+filenames[rand].substring(0, filenames[rand].length()-4)+"_"+hex(round(random(7000)))+"_Y_N_#";
    planet create =new planet(temp.split("_"));
    planets.add(create);
  }
  switch (state) {
  case "MAINLOAD":
    if (key == ESC)state="MAINMENU";
    break;
  case "CREDITS":
    if (key == ESC)state="MAINMENU";
    break;
  case "SIM":
    if (keyCode==SHIFT)keyCode=1;
    lastkey[0]=""+key;
    lastkey[1]=""+keyCode;
    if (overlay=="") {
      for (int i=0; i<controll.length; i++) {
        if (keyCode==controlls[i])controll[i]=true;
      }
      for (int i=0; i<keys.length; i++) {
        if (keyCode==keyss[i])keys[i]=!keys[i];
      }
    }
    if (keyCode==keyss[0]) {
      sound();
    }
    if (keyCode==keyss[2]) {
      removeplot();
    }
    if (overlay=="SAVE") {
      if ((((keyCode>47&&keyCode<91)||keyCode==32)&&keyCode!=59)&&savename.length()<15)savename+=key;
      if (keyCode==8) {
        String temp=savename;
        savename="";
        for (int i=0; i<temp.length()-1; i++) {
          savename=savename+temp.charAt(i);
        }
      }
    }
    break;
  }
  if (key == ESC) {
    state="MAINMENU";
    if(!inoverlay){
      inoverlay=true;
      resetmouse();
    }
    key=0;
  }
}
public void keyReleased() {
  if (keyCode==SHIFT)keyCode=1;
  for (int i=0; i<controll.length; i++) {
    if (keyCode==controlls[i])controll[i]=false;
  }
}
public void mouseWheel(MouseEvent event) {
  switch (state) {
  case "SIM":
    float e = -event.getCount();
    if (e>0)lastkey[3]="UP";
    if (e<0)lastkey[3]="DOWN";
    if (e>0) {
      IntoTheFuture();
    }
    if (0>e) {
      IntoThePast();
    }
    break;
  }
}
public void mousePressed() {
  switch (state) {
  case "SIM":
    if (mouseButton==RIGHT)lastkey[2]="RIGHT";
    if (mouseButton==LEFT)lastkey[2]="LEFT";
    if (mouseButton==CENTER)lastkey[2]="CENTER";
    /**
     if (mouseButton==LEFT) {
     switch (overlay) {
     case "":
     if (rcm[0]&&framerate<warningnum&&mouseposadd(widthhalf+120, height-118, 20, 20))warning=false;
     if (rcm[0])rcmpressed();
     break;
     case "PAUSE":
     pausepressed();
     break;
     case "QUIT":
     quitpressed();
     break;
     case "SAVE":
     savemenpressed();
     break;
     case "LOAD":
     loadmenupressed("LOAD1");
     break;
     }
     } else if (mouseButton==RIGHT) {
     if (overlay=="") {
     if (rcm[1]) {
     rcmc[0]=300;
     } else rcmc[0]=0;
     if (rcm[2]) {
     rcmc[1]=300;
     } else rcmc[1]=0;
     rcm[0]=!rcm[0];
     resetmouse();
     infosize();
     }
     }
     */
    if (mouseButton==RIGHT&&(cam[0]==-1||(cam[0]>2))) {
      inoverlay=!inoverlay;
      resetmouse();
    }
    overlayklicked();
    break;
  case "MAINMENU":
    if (mouseButton==LEFT) {
      mainmenupressed();
    }
    break;
  case "MAINLOAD":
    if (mouseButton==LEFT) {
      loadmenupressed();
    }
    break;
  }
}
public void sound() {
  if (file.isPlaying()) {
    file.pause();
    if (keys[0]) {
      keys[0]=false;
    }
  } else {
    file.play();
    if (!keys[0]) {
      keys[0]=true;
    }
  }
}
public void simulate() {
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
public void accelerate() {
  for (int i = 0; i<planets.size(); i++) { 
    planets.get(i).accelerate(i, faster>0);
  }
}
public void moveplanets() {
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
public void time(Boolean Intime) {
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
public void simulationbackground() {
  hint(ENABLE_DEPTH_TEST);
  hint(ENABLE_OPTIMIZED_STROKE);
  background(5);
  pushMatrix();
  translate(0, 0, 0);
  camera(xpos*1.f, ypos*1.f, zpos*1.f, -cos(rotx*1.f)*sin(roty*1.f)*1.f+xpos*1.f, -sin(rotx*1.f)+ypos*1.f, cos(rotx*1.f)*cos(roty*1.f)+zpos*1.f, 0, 1, 0);
  paint();
  popMatrix();
  translate(0, 0, 0);
  hint(DISABLE_OPTIMIZED_STROKE);
  hint(DISABLE_DEPTH_TEST);
}
public void paint() {
  movementspeed=60;
  for (int i = 0; i<planets.size(); i++) { 
    planet temp = planets.get(i);
    if(cam[1]!=i||!(cam[0]==3))temp.paint();
    strokeCap(ROUND);
    if (keys[2])temp.drawplot();
  }
}
public void removeplot() {
  if (!keys[2]) {
    for (int i=0; i<planets.size(); i++) {
      planets.get(i).deleteplot();
    }
  }
}
class buttons {
  String text;
  short stroked=70;
  short font=2;
  int[]pos=new int[3];
  int[] size=new int[2];
  int distance;
  int count;
  buttons(String text1, int x, int y, int wide, int high, int dist) {
    text=text1;
    pos[0]=x;
    pos[1]=y;
    size[0]=wide;
    size[1]=high;
    distance=dist;
    count=text.split("\n").length;
    pos[2]=y-(count*high+(count-1)*(dist-high))/2+5;
  }
  buttons(String text1, int x, int y, int wide, int high, int dist, int fonts) {
    text=text1;
    pos[0]=x;
    pos[1]=y;
    size[0]=wide;
    size[1]=high;
    distance=dist;
    count=text.split("\n").length;
    pos[2]=y-(count*high+(count-1)*(dist-high))/2+5;
    font=(short)fonts;
  }

  public void paint() {
    strokeWeight(2);
    stroke(70);
    fill(50);
    for (int i=0; i<count; i++) {
      rect(pos[0]-size[0]/2, pos[2]+i*(distance), size[0], size[1], 1);
    }
    textAlign(CENTER, CENTER);
    textFont(Fonts[font]);
    textLeading(distance);
    fill(200);
    text(text, pos[0], pos[1]);
  }
  public short pressed() {
    for (short i=0; i<count; i++) {
      if (mouseposadd(pos[0]-size[0]/2, pos[2]+i*(distance), size[0], size[1]))return i;
    }
    return -1;
  }
}
public void simbottomenu() {
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
public void simbottomenubuttons() {
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
  menu.arc(220, height-73, 24, 24, -PI, 1.7f);
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
  menu.arc(221, height-27, 10, 10, -HALF_PI+0.1f, HALF_PI-0.1f);
  menu.arc(221, height-27, 30, 30, -HALF_PI+0.3f, HALF_PI-0.3f);
  menu.arc(221, height-27, 20, 20, -HALF_PI+0.2f, HALF_PI-0.2f);
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
  menu.arc(width-166, height-55, 23, 23, -HALF_PI-0.3f, HALF_PI+0.2f);
  menu.arc(width-166, height-55, 12, 12, -HALF_PI-1, HALF_PI-0.7f);
  activatedbutton(2);
  menu.text("don't\nknow",width-148, height-70);
  activatedbutton(3);
  menu.text("planet",width-214, height-37);
  activatedbutton(4);
  menu.text("from\nplanet",width-181, height-37);
}
public void activatedbutton(int in) {
  if (cam[0]==in) {
    menu.stroke(220);
    menu.fill(220);
  } else {
    menu.stroke(10);
    menu.fill(10);
  }
}
public void overlayklicked() {
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
      setcam(-99121.75f, -35423.0f, 53040.004f, -0.3009999f, 4.2179995f);
    } else if (mouseposadd(width-214, height-37, 30, 30)) {
      cam[0]=3;
    } else if (mouseposadd(width-181, height-70, 30, 30)) {
      cam[0]=1;
      setcam(-47619.594f, -357769.72f, 39219.223f, -1.4707963f, 4.3289995f);
    } else if (mouseposadd(width-181, height-37, 30, 30)) {
      cam[0]=4;
    } else if (mouseposadd(width-148, height-70, 30, 30)) {
      cam[0]=2;
    } else if (mouseposadd(width-148, height-37, 30, 30)) {
      if (cam[1]<planets.size()-1&&cam[0]>2)cam[1]++;
    }
  }
}
public boolean mousepos (int xmax, int xmin, int ymax, int ymin) {
  return (mouseX<xmax&&mouseX>xmin&&mouseY<ymax&&mouseY>ymin);
}
public void move() {
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
  if (controll[4])ypos-=movementspeed*0.5f;
  if (controll[5])ypos+=movementspeed*0.5f;
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
public void cam() {
  roty+=(mouseX-widthhalf)*0.001f;
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
  rotx=rotx-(mouseY-heighthalf)*0.001f;
  if (mouseY!=heighthalf) {
    robby.mouseMove(mouseX, heighthalf);
    mouseY=heighthalf;
  }
  if (rotx>=HALF_PI-0.1f) {
    rotx=HALF_PI-0.1f;
  }
  if (rotx<=-HALF_PI+0.1f) {
    rotx=-HALF_PI+0.1f;
  }
}
public void setcam(float x, float y, float z, float rotatex, float rotatey){
  xpos=x*1.f;
  ypos=y*1.f;
  zpos=z*1.f;
  rotx=rotatex*1.f;
  roty=rotatey*1.f;
}
public void IntoTheFuture() {
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
public void IntoTheFuture2() {
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
public void IntoThePast() {
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
public void IntoThePast2() {
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
public void resetmouse() {
  noCursor();
  mouseY=heighthalf;
  mouseX=widthhalf;
  robby.mouseMove(mouseX, heighthalf);
  mouseY=heighthalf;
  mouseX=widthhalf;
  if (inoverlay)cursor();
}
public void overlay() {
  translate(0, 0);
  switch (state.split("_")[0]) {
  case "START":
    startit(state.split("_")[1]);
    break;
  case "MAINMENU":
    simulationbackground();
    screen();
    mainmenu();
    break;
  case "SIM": 
    simbottomenu();
    simulationbackground();
    image(menu, 0, 0);
    break;
  }
}
public void startit(String in) {
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
public void mainmenu() {
  hint(DISABLE_OPTIMIZED_STROKE);
  stroke(70);
  strokeWeight(3);
  fill(40);
  rect(100, heighthalf-170, 300, 380, 10);
  strokeWeight(2);
  fill(50);
  rect(widthhalf-100, height-100, 200, 60, 10);
  textAlign(CENTER, CENTER);
  textFont(Fonts[3]);
  fill(150);
  text("MAIN MENU", 250, heighthalf-140);
  fill(200);
  main.paint();
  textFont(Fonts[2]);
  text("CREDITS", widthhalf, height-75);
  image(SamTech, 10, 10);
  hint(ENABLE_OPTIMIZED_STROKE);
}

public void test(planet temp){
  pg.beginDraw();
  pg.background(100,0);
  pg.translate(pg.width/2,pg.height/2);
  pg.rotateY(frames*1.0f/100);
  pg.shape(temp.globes[2]);
  pg.endDraw();
}
public void infosize() {
  planet tempinf=planets.get(0);
  str=tempinf.info;
  if (str.charAt(0)=='#')str="There was no information available for this planet. You can create your own information about it. To do that follow the steps in the 'planet-info.txt' file.";
  currentSize = 5;
  bestSize = 5;
  String temp[] = str.split(" ");
  boolean searching = true;  
  while (searching) {
    if (testFontSize(currentSize, temp, 298, height-620)) {
      bestSize = currentSize;
      currentSize += sizeIncrement;
    } else {
      searching = false;
    }
  }
  strs=createFont("SansSerif", bestSize);
}
public boolean testFontSize(float s, String words[], int widths, int heights) {
  textSize(s);
  // calculate max lines
  int currentLine = 1;
  int maxLines = floor( heights / g.textLeading);
  boolean fitHeight = true;
  int nextWord = 0;
 
  while (fitHeight) {
    if (currentLine > maxLines) {
      fitHeight = false;
    } else {
      String temp = words[nextWord];
      // check if single word is already too wide
      if (textWidth(temp)>widths)
        return false;
 
      boolean fitWidth = true;
      // add words until string is too wide  
      while (fitWidth) {
        if (textWidth(temp) > widths) {
          currentLine++;
          fitWidth = false;
        } else {
          if (nextWord < words.length -1) {
            nextWord++;
            temp += " "+words[nextWord];
          } else
            return true;
        }
      }
    }
  }
 
  return false;
}



public String[] listFileNames(String dir) {
  File filedi = new File(dir);
  if (filedi.isDirectory()) {
    String names[] = filedi.list();
    return names;
  } else {
    // If it's not a directory
    return null;
  }
}
static int left, widthhalf, heighthalf;
short rcmc[]={0, 0};
public void mainmenupressed() {
  switch(main.pressed()) {
  case 0:
    state="SIM";
    break;
  case 1:

    break;
  case 2:
    loadloadmenu();
    state="MAINLOAD";
    break;
  case 3:
    exit();
    break;
  }
  if (mouseposadd(widthhalf-100, height-100, 200, 60))state="CREDITS";
}
public void credits() {
  fill(0, 150);
  rect(-20, -20, width+40, height+40);
  strokeWeight(2);
  stroke(70);
  fill(40, 255);
  rect(20, 20, width-40, height-40, 10);
  textFont(Fonts[4]);
  fill(150);
  textAlign(CENTER, CENTER);
  text("CREDITS", widthhalf, 60);
  fill(200);
  textFont(Fonts[2]);
  text("Music by Erokia: https://freesound.org/people/Erokia/\ntitle:'520929__erokia__elementary-wave-10'\n (https://freesound.org/people/Erokia/sounds/520929/\n\nPlanetinformation:\nText: 'https://www.wikipedia.org/'\nPlanetdata: NASA @Horizons\n(https://ssd.jpl.nasa.gov/horizons.cgi#top))\n\ntextures: solarsystemscope\n(https://www.solarsystemscope.com/textures/)", widthhalf, heighthalf-60);
  text("project rights belong to Samuel Sandrock on itch.io: https://samtech.itch.io/", widthhalf, height-60);
}
public void pause() {
  stroke(70);
  strokeWeight(3);
  fill(40);
  rect(widthhalf-100, heighthalf-210, 200, 460, 10, 10, 10, 10);
  textAlign(CENTER, CENTER);
  textFont(Fonts[3]);
  fill(150);
  text("PAUSED", widthhalf, heighthalf-180);
  paused.paint();
  if (overlay=="QUIT")quit();
  if (overlay=="SAVE")savemen();
}
public void pausepressed() {
  switch(paused.pressed()) {
  case 0:
    overlay="";
    resetmouse();
    break;
  case 1:
    overlay="SETTINGS";
    break;
  case 2:
    overlay="SAVE";
    break;
  case 3:
    loadloadmenu();
    overlay="LOAD";
    break;
  case 4:
    overlay="QUIT";
    break;
  }
}
public void savemen() {
  fill(0, 150);
  rect(-20, -20, width+40, height+40);
  strokeWeight(2);
  stroke(70);
  fill(40, 255);
  rect(widthhalf-180, heighthalf-70, 360, 140, 10);
  textAlign(CENTER, CENTER);
  textFont(Fonts[2]);
  fill(150);
  textFont(Fonts[1]);
  text("Save this system as", widthhalf, heighthalf-50);
  rect(widthhalf-167, heighthalf-25, 334, 36, 1, 1, 1, 1);
  fill(50);
  if (second()%2==0)text(savename+" ", widthhalf, heighthalf-10);
  else text((savename+"|"), widthhalf, heighthalf-10);
  savebutton[0].paint();
  savebutton[1].paint();
}
public void savemenpressed() {
  if (mouseposadd(widthhalf-170, heighthalf+20, 165, 40)&&savename.length()>0) {
    saving(savename);
    loadsetup[0]=savename;
    overlay="";
  }
  if (mouseposadd(widthhalf+5, heighthalf+20, 165, 40)) {
    savename="";
    overlay="PAUSE";
  }
}
public void loadloadmenu() {
  String[] filenames1 = listFileNames(sketchPath()+"/data/systems/");
  String[] filenames2 = listFileNames(sketchPath()+"/data/saves/");
  String[] filenames = new String[filenames1.length+filenames2.length];
  for (int i=0; i<filenames1.length; i++) {
    filenames[i]=filenames1[i];
  }
  for (int i=filenames1.length; i<filenames.length; i++) {
    filenames[i]=filenames2[i-filenames1.length];
  }
  info = new String[filenames.length][4];
  for (int i=0; i<filenames.length; i++) {
    String temp[];
    if (i<filenames1.length) {
      temp = loadStrings(sketchPath()+"/data/systems/"+filenames[i]);
      info[i][2]="/systems/"+filenames[i];
    } else {
      temp = loadStrings(sketchPath()+"/data/saves/"+filenames[i]);
      info[i][2]="/saves/"+filenames[i];
    }
    info[i][0]=filenames[i];
    info[i][1]=""+(temp.length-1);
  }
  for (int i=0; i<filenames.length; i++) {
    String temp="";
    info[i][3]=info[i][0].substring(0, info[i][0].length()-7);
    for (int ii=0; info[i][3].length()>ii&&info[i][3].charAt(ii)!='_'; ii++) {
      temp+=info[i][3].charAt(ii);
      if (info[i][3].length()>ii+1&&info[i][3].charAt(ii+1)=='_')temp+="...";
    }
    info[i][0]=temp;
  }
}
public void loadmenu() {
  fill(0, 150);
  rect(-20, -20, width+40, height+40);
  strokeWeight(2);
  stroke(70);
  fill(40, 255);
  rect(left, 20, 600*timeswidth, height-40, 10);
  textFont(Fonts[4]);
  fill(150);
  textAlign(CENTER, CENTER);
  text("LOAD SYSTEMS", widthhalf, 60);
  for (int i=0; i<info.length; i++) {
    fill(50);
    rect(left+10+600*(i%timeswidth), 120+55*((i-i%timeswidth)/timeswidth), 580, 40, 1);
    fill(200);
    textAlign(LEFT, CENTER);
    textFont(Fonts[2]);
    text(info[i][0], left+15+600*(i%timeswidth), 133+55*((i-i%timeswidth)/timeswidth));
    fill(100);
    textAlign(RIGHT, CENTER);
    text(info[i][1], left+585+600*(i%timeswidth), 134+55*((i-i%timeswidth)/timeswidth));
  }
}
public void quit() {
  fill(0, 150);
  rect(-20, -20, width+40, height+40);
  strokeWeight(2);
  stroke(70);
  fill(40, 255);
  rect(widthhalf-180, heighthalf-50, 360, 100, 10, 10, 10, 10);
  textAlign(CENTER, CENTER);
  textFont(Fonts[2]);
  fill(150);
  text("Quit without saving?", widthhalf, heighthalf-30);
  fill(50);
  rect(widthhalf-170, heighthalf, 165, 40, 1, 1, 1, 1);
  rect(widthhalf+5, heighthalf, 165, 40, 1, 1, 1, 1);
  fill(200);
  textFont(Fonts[1]);
  text("SAVE FIRST", widthhalf-87, heighthalf+17);
  text("QUIT", widthhalf+87, heighthalf+16);
}
public void quitpressed() {
  if (mouseposadd(widthhalf-170, heighthalf, 165, 40)) {
    String setup="";
    for (int i=0; loadsetup[0].length()>i&&loadsetup[0].charAt(i)!='_'; i++) {
      setup+=loadsetup[0].charAt(i);
    }
    saving(setup+"_"+day()+"_"+month()+"_"+year()+"_"+hour()+"_"+minute()+"_"+second());
    exit();
  }
  if (mouseposadd(widthhalf+5, heighthalf, 165, 40)) {
    exit();
    saveStrings("data/save.setup", loadsetup);
  }
}
public void backimg() {
  String[] filenames = listFileNames(sketchPath()+"/data/textures/");
  image(loadImage("textures/"+filenames[round(random(filenames.length-0.5f))]), 0, 0, width, height);
}
public boolean mouseposadd(int x, int y, int xplus, int yplus) {
  return mousepos(x+xplus, x, y+yplus, y );
}
public void loadmenupressed() {
  for (int i=0; i<info.length; i++) {
    if (mouseposadd(left+10+600*(i%timeswidth), 120+55*((i-i%timeswidth)/timeswidth), 580, 40)) {
      while (planets.size()!=0) { 
        planets.remove(0);
      }
      loadsetup[0]=info[i][3];
      loadsetup[1]=info[i][2];
      saveStrings("data/save.setup", loadsetup);
      state="START_A";
      frames=0;
      i=info.length;
    }
  }
}
public void screen() {
  hint(DISABLE_OPTIMIZED_STROKE);
  fill(2);
  textFont(Fonts[0]);
  noStroke();
  rect(width,0,-textWidth(""+round(framerate))-6,16);
  //if (overlay!=""||rcm[0])cursor();
  //if(rcm[0]||rcm[1]||rcm[2])rcm();
  fill(255);
  textFont(Fonts[0]);
  textAlign(RIGHT, TOP);
  text(""+round(framerate), width-3, 3);
  fill(255);
  textAlign(BASELINE);
  //pushMatrix();
  //if (rcm[1]||rcm[0])translate(35+rcmc[0], 0);
  //popMatrix();
  translate(0,0);
  if (overlay!="") {
    fill(0, 150);
    rect(-20, -20, width+40, height+40);
  }
  if (overlay=="PAUSE"||overlay=="QUIT"||overlay=="SAVE")pause();
  if (overlay=="LOAD")loadmenu();
  hint(ENABLE_OPTIMIZED_STROKE);
}
public void saving(String name) {
  String save[]=new String[planets.size()+1];
  save[0]=time[3]+"_"+time[2]+"_"+time[1]+"_"+time[0]+"_"+floor(minute[1]);
  for (int i=0; i<planets.size(); i++) {
    planet temp=planets.get(i);
    save[i+1]=""+temp.data();
  }
  saveStrings("data/saves/"+name+".system",save);
}
//-------------------------------------------------- LOGO & other images -----------------------------------------------------------------------------------
static PImage SamTech;
static PImage LOGO[]=new PImage[3];
static PImage speed;
//-------------------------------------------------- Text and optimization ---------------------------------------------------------------------------------
static PFont Fonts[]=new PFont[11];
final float sizeIncrement = 0.5f;
float currentSize = 5;
float bestSize = 5;
String[][] info;
String[] loadsetup;
String[] Import;
String savename="";
String str="";
PFont strs;
//-------------------------------------------------- constants ---------------------------------------------------------------------------------------------
final float grav=6.672f;
final float au=149597870.700f;
static int maszstab=4000;
//-------------------------------------------------- Simulation --------------------------------------------------------------------------------------------
long calculation=9;
int plott[]={7,0};
int plotsize=1000;
short bigger;
ArrayList<planet> planets;
int time[]={1, 1, 1, 1};
short minute[] = {0,0,1,5,20,60,360,720,1440,3000};
int year[]={31,28,31,30,31,30,31,31,30,31,30,31};
float his;
int faster=1;
short slower=1;
short slowercap=32;
short speedcount=0;
String create="";
//-------------------------------------------------- movement & camera -------------------------------------------------------------------------------------
Robot robby;
short controlls[]=new short[8];
static long renderdistance=10000000;
boolean controll[]=new boolean[8];
float movementspeed=60;
float xpos, ypos, zpos;
float rotx, roty;
short cam[]={-1,0};
//-------------------------------------------------- other controlls ---------------------------------------------------------------------------------------
boolean keys[]=new boolean[3];
short keyss[]=new short[3];
String lastkey[]=new String[4];
//-------------------------------------------------- menus & states ----------------------------------------------------------------------------------------
buttons main, paused;
buttons savebutton[]=new buttons[2];
short warningnum=30;
boolean inoverlay=false;
boolean warning=true;
String overlay="";
String state="START_0";
int timeswidth;
//-------------------------------------------------- other -------------------------------------------------------------------------------------------------
SoundFile file;
short[] count={0, 0};
float framerate;
long frames=0;
boolean start=false;
boolean finished = false;
float percent = 0;
  public void settings() {  fullScreen(P3D);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Solarsystem" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
