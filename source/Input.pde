void keyPressed() { //<>//
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
    int rand=round(random(filenames.length-0.5));
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
void keyReleased() {
  if (keyCode==SHIFT)keyCode=1;
  for (int i=0; i<controll.length; i++) {
    if (keyCode==controlls[i])controll[i]=false;
  }
}
void mouseWheel(MouseEvent event) {
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
void mousePressed() {
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
void sound() {
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
