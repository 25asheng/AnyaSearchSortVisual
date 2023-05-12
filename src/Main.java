import processing.core.PApplet;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;

public class Main extends PApplet{
    public static PApplet app;
    public ArrayList<House> thisList = new ArrayList<House>();
    public int bottom, top;
    public int targetPrice;
    public String targetString = "";
    public final int WIDTH = 80;
    public final int HEIGHT = 90;
    public int count = 0;
    public boolean sorted = false;
    public boolean entered = false;
    public boolean finishedSearching = false;
    public int inputLength = 0;
    public boolean isFirstTime = true;

    public static void main(String[] args){
        PApplet.main("Main");
    }
    public Main(){
        super();
        app = this;
    }



    public void settings(){
        size(1440,800);
    }

    public void setup(){
        background(250,225,157);

        /*
        Table table = loadTable("My House Data - Sheet1.csv");
        for (TableRow row : table.rows()) {
            int livingArea = row.getInt("lotSize");
            int lotSize = row.getInt("livingArea");

            int bedrooms = row.getInt("bedrooms");
            double bathrooms = row.getDouble("bathrooms");

            thisList.add(new House(year, cat));
        }
         */

        //building sorted-by-price ArrayList
        thisList.add(new House(0.09,906,2,1)); //113,265
        thisList.add(new House(0.43,1600,3,1.5));//186,978
        thisList.add(new House(0.21,924,2,1));//115,971
        thisList.add(new House(0.83,1632,3,1.5));//193,097
        thisList.add(new House(0.32,1576,3,2.5));//210,133
        thisList.add(new House(0.19,1944,4,1));//193,959
        thisList.add(new House(0.68,1152,4,1));//115,355
        thisList.add(new House(1.94,1416,3,1.5));//178,504
        thisList.add(new House(1.21,1662,4,1.5));//185,105
        thisList.add(new House(0.11,1383,3,2));//175,463
        thisList.add(new House(0.11,840,2,1));//106,569
        thisList.add(new House(0.14,1300,3,1.5));//153,870
        thisList.add(new House(1,1056,3,1));//121,418
        thisList.add(new House(0.88,1092,3,1));//124,306
        thisList.add(new House(0.92,1624,3,2));//206,105

        scramble();
        top = thisList.size() - 1;
    }

        public void draw(){
            noStroke();
            fill(250,225,157);
            rect(100,100,1200,150);
            if(!isFirstTime) {
                fill(0, 0, 0);
            } else {
                fill(250, 225, 157);
            }
            isFirstTime = false;
            textSize(60);
            text("Anya's Search and Sort Visualizer!", 100, 100);
            textSize(30);
            text("Sort houses by price, then search for ideal prices as listed in green", 100, 155);

            for(int i = 0; i < thisList.size(); i++) {
                thisList.get(i).display(5 + i * 95, 250, WIDTH, HEIGHT);
            }

            fill(200,200,200);
            rect(100,400,550,100); //input box
            fill(0,0,0);
            textSize(40);
            text("Target Price: $" + targetString, 120, 460);

            if(!sorted){
                noStroke();
                fill(250, 225, 157);
                rect(80, 530, 900, 100);

                fill(0,0,0);
                textSize(20);
                text("Press 's' to sort the houses by price!", 100, 550);
            } else {

                if (entered) {
                    noStroke();
                    fill(250, 225, 157);
                    rect(80, 530, 900, 50);

                    fill(0, 0, 0);
                    textSize(20);
                    text("Press space to iterate through the binary search", 100, 550);
                } else {
                    noStroke();
                    fill(250, 225, 157);
                    rect(80, 530, 900, 50);
                    fill(0, 0, 0);
                    textSize(20);
                    text("Enter in your target price with , or integers 0 to 9 and press shift to clear input, then enter when done", 100, 550);
                }
            }
            if(finishedSearching){
                fill(0, 0, 0);
                textSize(30);
                text("Press 'r' to retry!", 700, 600);
            }
        }

    private int binarySearchRecursive(int bottom, int top, double TARGET_PRICE){
        int mid = (bottom + top) / 2;
        if(bottom > top){
            return -1;
        } else if(thisList.get(mid).getPrice() == targetPrice){
            return mid;
        } else if(thisList.get(mid).getPrice() < targetPrice){
            return binarySearchRecursive(mid+1, top, targetPrice);
        } else {
            return binarySearchRecursive(bottom, mid-1, targetPrice);
        }
    }

    private int binarySearchIterative() {
        count++;
        if (bottom <= top) {
            int mid = (bottom + top) / 2;
            if (thisList.get(mid).getPrice() == targetPrice) {
                finishedSearching = true;
                return mid;
            } else if (thisList.get(mid).getPrice() < targetPrice) {
                for(int i = bottom; i <= mid; i++){
                    thisList.get(i).turnGrey();
                }
                bottom = mid + 1;
            } else {
                for(int i = mid; i <= top; i++){
                    thisList.get(i).turnGrey();
                }
                top = mid - 1;
            }
        }
        if(bottom > top) {
            finishedSearching = true;
        }
        return -1;

    }

    private void selectionSort(){
        int minIndex, minPrice;
        for (int i = 0; i < thisList.size() - 1; i++){
            minPrice = thisList.get(i).getPrice();
            minIndex = i;
            for(int j = 1 + i; j < thisList.size(); j++){
                if(minPrice > thisList.get(j).getPrice()){
                    minPrice = thisList.get(j).getPrice();
                    minIndex = j;
                }
            }
            House temp = thisList.get(i);
            thisList.set(i,thisList.get(minIndex));
            thisList.set(minIndex,temp);
        }
        sorted = true;
    }


    public void keyPressed() {
        int ascii = key;
        System.out.println(ascii);

        if(!sorted) {
            if (key == 's') {
                selectionSort();
            }
        } else {
            if (entered) {
                if (key == ' ' && !finishedSearching) { //press space to iterate and search
                    int index = binarySearchIterative();
                    fill(0, 0, 0);
                    textSize(30);

                    if (index != -1) {
                        text("Found " + targetString + " in " + count + " tries at index: " + index, 700, 500);
                    } else if (index == -1 && finishedSearching) {
                        text("Target price was not found. Would you like to try again?", 700, 500);
                    }
                }
            } else if (ascii == 65535){ //shift
                clearInput();
            } else if (48 <= ascii && 57 >= ascii) { //numbers 0 to 9
                noStroke();
                fill(250, 225, 157);
                rect(80, 580, 650, 200);
                if (inputLength < 10) {
                    if (!(key == '0' && inputLength == 0)) {
                        inputLength++;
                        targetString += key;
                    }
                    targetPrice = targetPrice * 10 + ascii - 48;
                    System.out.println(inputLength);
                }
            } else if (ascii == 8 && targetString.length() != 0){ //delete key
                System.out.println("delete");
                noStroke();
                fill(250, 225, 157);
                rect(80, 580, 650, 200);

                if(!(targetString.charAt(targetString.length()-1) == ',')){
                    inputLength--;
                    targetPrice = targetPrice / 10;
                }
                targetString = targetString.substring(0,targetString.length()-1);

            } else if (key == ','){ //concatenates targetString but not targetPrice or inputLength
                noStroke();
                fill(250, 225, 157);
                rect(80, 580, 650, 200);

                targetString += ",";
            } else if (ascii == 10) { //enter key allows to start searching and stops allowing input
                entered = true;

                noStroke();
                fill(250, 225, 157);
                rect(80, 580, 650, 200);
            } else { //reminder to allow for proper user input
                noStroke();
                fill(250, 225, 157);
                rect(80, 580, 600, 200);

                fill(0, 0, 0);
                textSize(20);
                text("please type an integer from 0 to 9 or ,", 100, 600);
            }

            if (key == 'r' && finishedSearching) { //resets
                reset();
            }
        }

    }

    private void reset(){
        entered = false;
        finishedSearching = false;
        sorted = false;

        fill(200,200,200); //"clear" "box" for target price
        rect(100,400,600,100);

        fill(250, 225, 157);
        rect(80, 400, 1350, 410); //"delete" text

        count = 0;
        top = thisList.size() - 1;
        bottom = 0;
        targetPrice = 0;
        targetString = "";
        inputLength = 0;

        for(House h: thisList){ //return color
            h.turnBack();
        }
        scramble();
    }

    private void clearInput(){
        targetPrice = 0;
        targetString = "";
        inputLength = 0;
    }

    private void scramble(){
        for(int i = 0; i < thisList.size(); i++){
            int randInd = (int)(Math.random()*thisList.size());
            House temp = thisList.get(i);
            thisList.set(i,thisList.get(randInd));
            thisList.set(randInd,temp);
        }
    }
}