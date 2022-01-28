import java.awt.AWTException;  //<>//
import java.awt.Robot;
import processing.sound.*;
import java.util.Date;
import java.math.*;
PGraphics pg, menu;
void setup() {
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
  fullScreen(P3D);
  menu = createGraphics(width, height, P2D);
  perspective(60 * DEG_TO_RAD, (float) width / (float) height, height / 2.0f / ((float) Math.tan(60 * DEG_TO_RAD / 2.0f)) / 10.0f, height / 2.0f / ((float) Math.tan(60 * DEG_TO_RAD / 2.0f)) * 10.0f*renderdistance);
  image(LOGO[0], 0, 0, width, height);
  planets = new ArrayList<planet>();
  widthhalf=width/2;
  heighthalf=height/2;
  timeswidth=((width-30)-(width-30)%600)/600;
  left=widthhalf-600*timeswidth/2;
  smooth();
  Fonts[2] = createFont("SansSerif", 35);
}
void draw() { 
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
