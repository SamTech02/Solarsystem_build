void saving(String name) {
  String save[]=new String[planets.size()+1];
  save[0]=time[3]+"_"+time[2]+"_"+time[1]+"_"+time[0]+"_"+floor(minute[1]);
  for (int i=0; i<planets.size(); i++) {
    planet temp=planets.get(i);
    save[i+1]=""+temp.data();
  }
  saveStrings("data/saves/"+name+".system",save);
}
