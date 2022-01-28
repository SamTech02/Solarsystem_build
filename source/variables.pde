//-------------------------------------------------- LOGO & other images -----------------------------------------------------------------------------------
static PImage SamTech;
static PImage LOGO[]=new PImage[3];
static PImage speed;
//-------------------------------------------------- Text and optimization ---------------------------------------------------------------------------------
static PFont Fonts[]=new PFont[11];
final float sizeIncrement = 0.5;
float currentSize = 5;
float bestSize = 5;
String[][] info;
String[] loadsetup;
String[] Import;
String savename="";
String str="";
PFont strs;
//-------------------------------------------------- constants ---------------------------------------------------------------------------------------------
final float grav=6.672;
final float au=149597870.700;
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
