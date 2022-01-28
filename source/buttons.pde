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

  void paint() {
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
  short pressed() {
    for (short i=0; i<count; i++) {
      if (mouseposadd(pos[0]-size[0]/2, pos[2]+i*(distance), size[0], size[1]))return i;
    }
    return -1;
  }
}
