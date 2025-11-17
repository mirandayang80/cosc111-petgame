public class Button {
    private double xButton;
    private double yButton;
    private double halfW;
    private double halfH;
    private String image;
    private int screen;
    private boolean arrowPointed;

    //Create button
    public Button(double x, double y, double width, double height, String imageName, int screen) {
        xButton = x;
        yButton = y;
        halfW = width;
        halfH = height;
        image = imageName;
        this.screen = screen;
    }

    //Check if button is clicked
    public boolean checkClick(double x, double y) {
        return ((xButton - halfW < x) && (xButton + halfW > x) && (yButton - halfH < y) && (yButton + halfH > y));
    }

    //Draw the button
    public void drawButton(boolean c, boolean s, boolean p, boolean f) {
        if(arrowPointed){
            if(c) {
                StdDraw.picture(xButton - 145, yButton, "images/StartScreenArrows.png");
            }
            if(s){
                StdDraw.picture(xButton - 110, yButton, "images/StartScreenArrows.png");

            }
            if(p){
                StdDraw.picture(xButton - 135, yButton, "images/StartScreenArrows.png");

            }
            if(f){
                StdDraw.picture(xButton - 157, yButton, "images/StartScreenArrows.png");

            }
        }
        else {
            if (image != null) {
                StdDraw.picture(xButton, yButton, image);
            }
        }
    }

    public void setArrPo(boolean airP){
        arrowPointed = airP;
    }

}
