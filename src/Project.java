public class Project {
    public static void main(String[] args) {
        //If there is command line arg print err and exit
        if (args.length != 0) {
            System.err.print("No command line arguments needed");
            System.exit(1);
        }

        //Set canvas
        StdDraw.setCanvasSize(800, 600);
        StdDraw.setPenRadius(1);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 600);

        //Buttons
        Button startGame = new Button(400.32, 79.68, 108, 31, null, 1);
        Button sheep = new Button(245.5, 474, 64.5, 57, null, 1);
        Button cow = new Button(586, 474, 103, 57, null, 1);
        Button plain = new Button(242, 248.64, 94, 70, null, 1);
        Button factory = new Button(577, 248.64, 152, 70, null, 1);
        Button homeScreen = new Button(736.6, 580, 59.5, 19, "images/Home.png", 2);
        Button plantone = new Button(302, 55, 31, 31, null, 2);
        Button planttwo = new Button(382.08, 55, 31, 31, null, 2);
        Button plantthree = new Button(461.76, 55, 31, 31, null, 2);
        Button cleanUp = new Button(655, 104, 93, 14.5, null, 2);
        Button revive = new Button(655, 43, 93, 14.5, null, 2);

        int screen = 1;
        int setting = 0;
        int f = 0; //Frame
        int health = 5; //Health bar
        int hunger = 1; //0 = hungry, 5 = full, depletes by 1 per 50 frame

        //Sheep
        boolean sheepIncluded = false;
        boolean sDead = false;
        boolean sFlip = false;
        double ranSX = 400;

        //Cow
        boolean cowIncluded = false;
        boolean cDead = false;
        boolean cFlip = false;
        double ranCX = 400;

        //Plants
        boolean plantI = false;
        boolean plantII = false;
        boolean plantIII = false;
        double ranXI = -1;
        double ranXII = -1;
        double ranXIII = -1;
        double plantYI = 500;
        double plantYII = 500;
        double plantYIII = 500;

        //Waste
        double[] wX = new double[0];
        double wY = 162;

        //Clouds
        boolean drawn = false;
        double ranCIX = -1;
        double ranCIIX = -1;
        double ranCIY = -1;
        double ranCIIY = -1;

        StdDraw.enableDoubleBuffering();

        while (true) {
            StdDraw.clear();

            if (StdDraw.isMousePressed()) {

                //Get coordinates
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                System.out.println(x + " " + y);

                //Screen 1
                if (screen == 1) {
                    //Check which images/buttons are clicked
                    if (sheep.checkClick(x, y)) {
                        System.out.println("SHEEP");
                        cow.setArrPo(false);
                        sheep.setArrPo(true);
                        sheepIncluded = true;
                        cowIncluded = false;
                    }

                    if (cow.checkClick(x, y)) {
                        System.out.println("COW");
                        sheep.setArrPo(false);
                        cow.setArrPo(true);
                        sheepIncluded = false;
                        cowIncluded = true;
                    }

                    if (plain.checkClick(x, y)) {
                        System.out.println("PLAIN");
                        factory.setArrPo(false);
                        plain.setArrPo(true);
                        setting = 2;
                    }

                    if (factory.checkClick(x, y)) {
                        System.out.println("FACTORY");
                        plain.setArrPo(false);
                        factory.setArrPo(true);
                        setting = 3;
                    }

                    if (startGame.checkClick(x, y)) {
                        if ((setting == 2 || setting == 3) && (cowIncluded || sheepIncluded)) {
                            screen = 2;
                        }
                    }
                }

                //Screen 2
                if (screen == 2) {

                    //Home button pressed
                    if (homeScreen.checkClick(x, y)) {
                        System.out.println("HOME");
                        drawn = false;
                        plantI = false;
                        plantII = false;
                        plantIII = false;
                        cDead = false;
                        sDead = false;
                        wX = new double[0];
                        health = 5;
                        hunger = 1;
                        screen = 1;
                    }

                    //Plant 1 button pressed
                    if (plantone.checkClick(x, y)) {
                        System.out.println("PLANT ONE");
                        plantI = true;
                        //Drop plant from the sky
                        plantYI = 600;
                        ranXI = (Math.random() * 750);
                    }

                    //Plant 2 button pressed
                    if (planttwo.checkClick(x, y)) {
                        System.out.println("PLANT TWO");
                        plantII = true;
                        //Drop plant from the sky
                        plantYII = 600;
                        ranXII = (Math.random() * 750);
                    }

                    //Plant 3 button pressed
                    if (plantthree.checkClick(x, y)) {
                        System.out.println("PLANT THREE");
                        plantIII = true;
                        //Drop plant from the sky
                        plantYIII = 600;
                        ranXIII = (Math.random() * 750);
                    }

                    //Clean up button pressed
                    if (cleanUp.checkClick(x, y)) {
                        System.out.println("CLEAN");
                        if (wX.length >= 1) {
                            wX = Project.removeW(wX);
                        }

                    }

                    //Revive button pressed
                    if (revive.checkClick(x, y)) {
                        System.out.println("REVIVE");
                        health = 5;
                        hunger = 1;
                        if (cDead) {
                            cDead = false;
                        }
                        if (sDead) {
                            sDead = false;
                        }
                    }
                }
            }

            //Start screen
            if (screen == 1) {
                StdDraw.picture(400, 300, "images/StartScreen.png");
            }

            //Draw the game screen
            else if (screen == 2) {
                //Setting
                if (setting == 2) {
                    StdDraw.picture(400, 300, "images/Plain.png");
                }
                if (setting == 3) {
                    StdDraw.picture(400, 300, "images/Factory.png");
                }

                //Clouds
                if(!drawn) {
                    ranCIX = (Math.random() * 750);
                    ranCIIX = (Math.random() * 750);
                    ranCIY = (Math.random() * 170 + 400);
                    ranCIIY = (Math.random() * 170 + 400);

                    drawn = true;
                }
                StdDraw.picture(ranCIX, ranCIY, "images/Cloud.png");
                StdDraw.picture(ranCIIX, ranCIIY, "images/Cloud.png");
                System.out.println("Clouds: " + ranCIX + " " + ranCIIX + " " + ranCIY + " " + ranCIIY);

                if (cowIncluded) {
                    if (health == 0) {
                        cDead = true;
                    }

                    if (!cDead) {
                        if (!cFlip) {
                            StdDraw.picture(ranCX, 220, "images/RightCow.png");

                            //Plant eaten, increase hunger bar
                            if (Project.plantEaten(ranCX, 220, 100, 100, ranXI, plantYI, 16, 21.5) && plantI) {
                                plantI = false;

                                //Generate waste
                                wX = Project.addWX(wX, ranCX - 40);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }
                            if (Project.plantEaten(ranCX, 220, 100, 100, ranXII, plantYII, 36, 24) && plantII) {
                                plantII = false;

                                //Generate waste
                                wX = Project.addWX(wX, ranCX - 40);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }
                            if (Project.plantEaten(ranCX, 220, 100, 100, ranXIII, plantYIII, 24, 24) && plantIII) {
                                plantIII = false;

                                //Generate waste
                                wX = Project.addWX(wX, ranCX - 40);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }

                            //Moves cow
                            ranCX += 2;

                            //Cow turns direction
                            if (ranCX >= 790) {
                                cFlip = true;
                            }


                        } else if (cFlip) {
                            StdDraw.picture(ranCX, 220, "images/LeftCow.png");

                            //Plant eaten, increase hunger bar
                            if (Project.plantEaten(ranCX, 220, 100, 100, ranXI, plantYI, 16, 21.5) && plantI) {
                                plantI = false;

                                //Generate waste
                                wX = Project.addWX(wX, ranCX + 40);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }
                            if (Project.plantEaten(ranCX, 220, 100, 100, ranXII, plantYII, 36, 24) && plantII) {
                                plantII = false;

                                //Generate wast
                                wX = Project.addWX(wX, ranCX + 40);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }
                            if (Project.plantEaten(ranCX, 220, 100, 100, ranXIII, plantYIII, 24, 24) && plantIII) {
                                plantIII = false;

                                //Generate waste
                                wX = Project.addWX(wX, ranCX + 40);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }

                            //Move cow
                            ranCX -= 2;

                            //Cow changes direction
                            if (ranCX <= 10) {
                                cFlip = false;
                            }

                        }
                    }

                    if (cDead) {
                        StdDraw.picture(ranCX, 200, "images/DeadCow.png");
                    }

                    //Draw waste
                    for (int i = 0; i < wX.length; i++) {
                        if (!cFlip) {
                            Project.drawW( wX[i], wY, "images/Waste.png");
                        }
                        if (cFlip) {
                            Project.drawW(wX[i], wY, "images/Waste.png");
                        }
                    }

                }

                if (sheepIncluded) {
                    if (health == 0) {
                        sDead = true;
                    }

                    if (!sDead) {
                        if (!sFlip) {
                            StdDraw.picture(ranSX, 192, "images/RightSheep.png");

                            //If plant eaten, increase hunger bar
                            if (Project.plantEaten(ranSX, 192, 80, 80, ranXI, plantYI, 16, 21.5) && plantI) {
                                plantI = false;

                                //Generate waste
                                wX = Project.addWX(wX, ranSX - 30);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }

                            if (Project.plantEaten(ranSX, 192, 80, 80, ranXII, plantYII, 36, 24) && plantII) {
                                plantII = false;

                                wX = Project.addWX(wX, ranSX - 30);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }
                            if (Project.plantEaten(ranSX, 192, 80, 80, ranXIII, plantYIII, 24, 24) && plantIII) {
                                plantIII = false;

                                wX = Project.addWX(wX, ranSX - 30);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }

                            //Sheep moves
                            ranSX += 2;

                            //Change dir
                            if (ranSX >= 790) {
                                sFlip = true;
                            }

                        } else if (sFlip) {
                            StdDraw.picture(ranSX, 192, "images/LeftSheep.png");

                            //If plant eaten, increase hunger bar
                            if (Project.plantEaten(ranSX, 192, 80, 80, ranXI, plantYI, 16, 21.5) && plantI) {
                                plantI = false;

                                wX = Project.addWX(wX, ranSX + 30);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }
                            if (Project.plantEaten(ranSX, 192, 80, 80, ranXII, plantYII, 36, 24) && plantII) {
                                plantII = false;

                                wX = Project.addWX(wX, ranSX + 30);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }
                            if (Project.plantEaten(ranSX, 192, 80, 80, ranXIII, plantYIII, 24, 24) && plantIII) {
                                plantIII = false;

                                wX = Project.addWX(wX, ranSX + 30);

                                if (hunger < 5) {
                                    hunger++;
                                }
                            }

                            //Move
                            ranSX -= 2;

                            //Change dir
                            if (ranSX <= 10) {
                                sFlip = false;
                            }

                        }


                    }

                    if (sDead) {
                        StdDraw.picture(ranSX, 192, "images/DeadSheep.png");
                    }

                    //Draw waste
                    for (int i = 0; i < wX.length; i++) {
                        if (!sFlip) {
                            Project.drawW( wX[i], wY, "images/Waste.png");
                        }
                        if (sFlip) {
                            Project.drawW(wX[i], wY, "images/Waste.png");
                        }
                    }

                }

                if (plantI) {
                    System.out.println("DRAWING PLANT ONE");
                    StdDraw.picture(ranXI, plantYI, "images/Plantone.png");
                    if (plantYI > 195) {
                        plantYI -= 10;
                    }
                }

                if (plantII) {
                    System.out.println("DRAWING PLANT TWO");
                    StdDraw.picture(ranXII, plantYII, "images/Planttwo.png");
                    if (plantYII > 195) {
                        plantYII -= 10;
                    }
                }

                if (plantIII) {
                    System.out.println("DRAWING PLANT THREE");
                    StdDraw.picture(ranXIII, plantYIII, "images/Plantthree.png");
                    if (plantYIII > 195) {
                        plantYIII -= 10;
                    }
                }

                //Health bars
                if ((hunger == 0) && (f % 50 == 0)) {
                    health--;
                }
                if (health == 5) {
                    StdDraw.picture(118.5, 92, "images/5GreenBar.png");
                } else if (health == 4) {
                    StdDraw.picture(118.5, 92, "images/4GreenBar.png");
                } else if (health == 3) {
                    StdDraw.picture(118.5, 92, "images/3GreenBar.png");
                } else if (health == 2) {
                    StdDraw.picture(118.5, 92, "images/2GreenBar.png");
                } else if (health == 1) {
                    StdDraw.picture(118.5, 92, "images/1GreenBar.png");
                } else if (health <= 0) {
                    StdDraw.picture(118.5, 92, "images/0GreenBar.png");
                }

                //Hunger bars
                if ((f != 0) && (f % 50 == 0)) {
                    if (hunger > 0) {
                        hunger--;
                    }
                }
                if (hunger == 5) {
                    StdDraw.picture(118.5, 22, "images/5GreenBar.png");
                } else if (hunger == 4) {
                    StdDraw.picture(118.5, 22, "images/4GreenBar.png");
                } else if (hunger == 3) {
                    StdDraw.picture(118.5, 22, "images/3GreenBar.png");
                } else if (hunger == 2) {
                    StdDraw.picture(118.5, 22, "images/2GreenBar.png");
                } else if (hunger == 1) {
                    StdDraw.picture(118.5, 22, "images/1GreenBar.png");
                } else if (hunger == 0) {
                    StdDraw.picture(118.5, 22, "images/0GreenBar.png");
                }


            }

            if (screen == 1) {
                sheep.drawButton(false, true, false, false);
                cow.drawButton(true, false, false, false);
                plain.drawButton(false, false, true, false);
                factory.drawButton(false, false, false, true);
            }

            if (screen == 2) {
                homeScreen.drawButton(false, false, false, false);
                plantone.drawButton(false, false, false, false);
                planttwo.drawButton(false, false, false, false);
                plantthree.drawButton(false, false, false, false);
                cleanUp.drawButton(false, false, false, false);
                revive.drawButton(false, false, false, false);

                System.out.println(f);
                f++;
            }

            StdDraw.show();
            StdDraw.pause(100);
        }
    }

    //Method to check whether the animal eats the plant
    public static boolean plantEaten(double aX, double aY, double aHW, double aHH, double pX, double pY, double pHW, double pHH) {
        //Check whether animal coordinates overlap with plant's coordinates
        boolean xOverlap = false;
        boolean yOverlap = false;

        if (((aX - aHW) <= (pX - pHW)) && ((pX + pHW) <= (aX + aHW))) {
            xOverlap = true;
        }

        if (((aY - aHH) <= (pY - pHH)) && ((pY + pHH) <= (aY + aHH))) {
            yOverlap = true;
        }

        return (xOverlap && yOverlap);
    }

    //Draw waste
    public static void drawW(double x, double y, String image) {
            StdDraw.picture(x, y, image);

    }

    //Remove waste
    public static double[] removeW(double[] arr) {
        double[] newArr = new double[arr.length - 1];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = arr[i + 1];
        }
        return newArr;
    }

    //Add waste
    public static double[] addWX(double[] arr, double wx) {
        double[] newA = new double[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            newA[i] = arr[i];
        }
        newA[arr.length] = wx;
        return newA;
    }

}