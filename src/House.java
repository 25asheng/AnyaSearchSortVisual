import processing.core.PApplet;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;

public class House {
    private double lotSize;
    private int livingArea;
    private int bedrooms;
    private double bathrooms;
    private int price;
    private int colorRGB;
    private int greenRGB = 100255000;
    private final int ROOM_MARGIN = 1;

    public House(double sizeLot, int areaLiving, int roomBeds, double roomBaths){
        lotSize = sizeLot; //0.11 to 2.29 acres
        livingArea = areaLiving; //840 to 2894 feet
        bedrooms = roomBeds; //2 to 7 bedrooms
        bathrooms = roomBaths; //1 to 2.5 bathrooms
        colorRGB = 200180126;

        price = (int)(7010.0761 * lotSize + 103.58401 * livingArea - 13763.72 * bedrooms + 26412.262 * bathrooms + 19902.723);


    }

    public double getLotSize(){
        return lotSize;
    }

    public int getLivingArea(){
        return livingArea;
    }

    public int getBedrooms(){
        return bedrooms;
    }
    public double getBathrooms(){
        return bathrooms;
    }

    public int getPrice(){
        return price;
    }

    public int comparePrice(House other){
        if(price < other.getPrice()){
            return -1;
        } else if(price > other.getPrice()){
            return 1;
        } else {
            return 0;
        }
    }

    public void display(int x, int y, int width, int height){
        int lotWidth = (int)(Math.sqrt(lotSize * Math.pow(width,2)/2.0)); //2 is the current max value of lotSize
        int livingWidth = (int)(Math.sqrt(livingArea / 43560.0 * Math.pow(width,2)/2.0)); //43560 is how many square feet are in one acre

        Main.app.noStroke();
        Main.app.fill(250, 225, 157);
        Main.app.rect(x,y,width + 30,height + 100);

        Main.app.fill(greenRGB/1000000,(greenRGB/1000)%1000,greenRGB%1000); //green
        Main.app.rect(x + (width - lotWidth)/2, y + (height - lotWidth)/2, lotWidth, lotWidth); //drawing the lot

        Main.app.fill(colorRGB/1000000,(colorRGB/1000)%1000,colorRGB%1000); //chosen color
        Main.app.rect(x + (width - livingWidth)/2, y + height/2 - livingWidth, livingWidth, livingWidth); //drawing the living area

        Main.app.fill(colorRGB/1000000 - 50,(colorRGB/1000)%1000 - 50,colorRGB%1000 - 50); //chosen darker color
        Main.app.triangle(x + (width - livingWidth)/2, y + height/2 - livingWidth, x + (width + livingWidth)/2, y + height/2 - livingWidth,x + width/2, y + (height - 3 * livingWidth)/2); //drawing the roof

        Main.app.fill(10,10,10);
        Main.app.textSize(10);
        Main.app.text(bedrooms + "br/" + bathrooms + "ba",x + (width - lotWidth)/2,y + (height + lotWidth)/2 + 13);
        Main.app.fill(10,100,10);
        Main.app.textSize(18);
        Main.app.text("$" + price,x + (width - lotWidth)/2,y + (height + lotWidth)/2 + 40);
    }


    public void turnGrey(){
        greenRGB = 150150150;
        colorRGB = 100100100;
    }

    public void turnBack(){
        greenRGB = 100255000;
        colorRGB = 200180126;
    }

}
