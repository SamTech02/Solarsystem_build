void mainmenu() {
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

void test(planet temp){
  pg.beginDraw();
  pg.background(100,0);
  pg.translate(pg.width/2,pg.height/2);
  pg.rotateY(frames*1.0/100);
  pg.shape(temp.globes[2]);
  pg.endDraw();
}
void infosize() {
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
boolean testFontSize(float s, String words[], int widths, int heights) {
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



String[] listFileNames(String dir) {
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
void mainmenupressed() {
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
void credits() {
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
void pause() {
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
void pausepressed() {
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
void savemen() {
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
void savemenpressed() {
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
void loadloadmenu() {
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
void loadmenu() {
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
void quit() {
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
void quitpressed() {
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
void backimg() {
  String[] filenames = listFileNames(sketchPath()+"/data/textures/");
  image(loadImage("textures/"+filenames[round(random(filenames.length-0.5))]), 0, 0, width, height);
}
boolean mouseposadd(int x, int y, int xplus, int yplus) {
  return mousepos(x+xplus, x, y+yplus, y );
}
void loadmenupressed() {
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
void screen() {
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
