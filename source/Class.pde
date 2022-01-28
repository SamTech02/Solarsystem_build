class planet { //<>//
  String name;
  double position[]=new double[3];
  double v[]={10, 10, 10};
  double mass=100;
  float diameter;
  String texture;
  color farbe=color(255);
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
  void ini() {
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
  void move(Boolean Intime) {
    if (move) {
      short times = 1;
      if (!Intime)times = -1;
      for (int i=0; i<3; i++) {
        position[i]+=v[i]/calculation*times*(minute[minute[0]+2]/60.);
      }
    }
  }
  void accelerate(int ID, Boolean Intime) {
    for (int ii=ID+1; ii<planets.size(); ii++) {
        planet obj2 = planets.get(ii);
        double a, distance;
        distance=Math.pow(Math.pow((position[0]-obj2.position[0]), 2)+Math.pow((position[1]-obj2.position[1]), 2)+Math.pow((position[2]-obj2.position[2]), 2), 0.5);
        a=((grav*pow( 10, -11)*obj2.mass)/(Math.pow(distance*calculation*1000, 2)))*12960;
        float time = 1;
        if (!Intime)time = -1;
        if (distance>0.000001) {
          v[0]-=(position[0]-obj2.position[0])*a/distance*time*(minute[minute[0]+2]/60.);  
          v[1]-=(position[1]-obj2.position[1])*a/distance*time*(minute[minute[0]+2]/60.);
          v[2]-=(position[2]-obj2.position[2])*a/distance*time*(minute[minute[0]+2]/60.);
          a=((grav*pow( 10, -11)*mass)/(Math.pow(distance*calculation*1000, 2)))*12960;
          obj2.v[0]-=(obj2.position[0]-position[0])*a/distance*time*(minute[minute[0]+2]/60.);  
          obj2.v[1]-=(obj2.position[1]-position[1])*a/distance*time*(minute[minute[0]+2]/60.);
          obj2.v[2]-=(obj2.position[2]-position[2])*a/distance*time*(minute[minute[0]+2]/60.);
        }else println(ID+"   "+ii);
    }
  }
  void paint() {
    pushMatrix();
    translate((float)(position[0]/maszstab*calculation), (float)(-position[2]/maszstab*calculation), (float)(-position[1])/maszstab*calculation);
    double distance=Math.pow(Math.pow((position[0]/maszstab*calculation-xpos), 2)+Math.pow((-position[2]/maszstab*calculation-ypos), 2)+Math.pow((-position[1]/maszstab*calculation-zpos), 2), 0.5);
    if (diameter/maszstab<movementspeed&&distance/diameter*20<1)movementspeed=diameter/maszstab;
    float scale=(float)(Math.pow(Math.pow((position[0]/maszstab*calculation-xpos), 2)+Math.pow((-position[2]/maszstab*calculation-ypos), 2)+Math.pow((-position[1]/maszstab*calculation-zpos), 2), 0.5)/maszstab);
    scale(bigger+1);
    shape(globes[0]);
    scale(scale/(bigger+1));
    shape(globes[1]);
    popMatrix();
  }
  String data() {
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
  void plot() {
    plotting.add(new Position(position));
    if (plotting.size()>plotsize)plotting.remove(0);
  }
  void deleteplot() {
    while (plotting.size()>0) {
      plotting.remove(0);
    }
  }
  void drawplot() {
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
    double[] getpos() {
      return pos;
    }
  }
}
