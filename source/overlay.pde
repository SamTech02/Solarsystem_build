void overlay() {
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
