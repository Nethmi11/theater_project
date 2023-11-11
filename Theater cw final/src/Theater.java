import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Theater {
    static int[] row1 = new int[12];
    static int[] row2 = new int[16];
    static int[] row3 = new int[20];
    static int[][] theater_arr = {row1, row2, row3};
    static int rownum, seatnum;
    static Scanner input = new Scanner(System.in);
    static ArrayList<Ticket> tickets_list = new ArrayList<>();//Array list of Ticket objects

    public static void main(String[] args) {
        System.out.println("            Welcome to the New Theater              ");

        boolean needNewOption = true;
        while (needNewOption) {
            System.out.println("----------------------------------------------------");
            System.out.println("                      OPTIONS                      ");
            System.out.println("Please select an option: \n");
            System.out.println("1)Buy a ticket");
            System.out.println("2)Print seating area");
            System.out.println("3)Cancel ticket");
            System.out.println("4)List available seats");
            System.out.println("5)Save to file");
            System.out.println("6)Load from file");
            System.out.println("7)Print ticket information and total price");
            System.out.println("8)Sort tickets by price");
            System.out.println("0)Quit");
            System.out.println("-----------------------------------------------------");


            System.out.print("Enter your option: ");
            int option = intType_checking(); //input.nextInt();
            //switch case
            switch (option) {
                case 0:
                    needNewOption = false;
                    break;
                case 1:
                    buy_ticket();
                    break;
                case 2:
                    print_seating_area();
                    break;
                case 3:
                    cancel_ticket();
                    break;
                case 4:
                    show_available();
                    break;
                case 5:
                    save();
                    break;
                case 6:
                    load();
                    break;
                case 7:
                    show_tickets_info();
                    break;
                case 8:
                    sort_tickets();
                    break;
                default:
                    System.out.println("Your number is invalid,Please re-enter a number");

            }
            System.out.println(" ");

        }
        System.out.print("You successfully quit the program! ");
    }

    static int intType_checking() { //checks if the input is an integer
        int number;
        while (true) {
            try {
                number = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Invalid, integer should be entered!Re-enter number: ");
                input.next();
            }
        }
        return number;
    }

    static void range_checking() { //checks if row number and seat number inputs are within the array range
        outerloop:
        while (true) {
            System.out.print("Please enter the row num 1-3: ");
            rownum = (intType_checking() - 1);
            if (rownum >= 0 && rownum < theater_arr.length) {
                innerloop:
                while (true) {
                    System.out.print("Please enter the col num in the specific row row1-max-12, row2-max-16, row3-max-20: ");
                    seatnum = (intType_checking() - 1);
                    if (seatnum >= 0 && seatnum < theater_arr[rownum].length) {
                        break outerloop;
                    } else {
                        System.out.println("Your column number is invalid");
                    }
                }
            } else {
                System.out.println("Your row number was invalid");
            }
        }
    }

    static void buy_ticket() {
    /* gets all the information and books a ticket, by updating in the theater_arr and creates Ticket objects and adds
     it to the array-list:tickets_list */

        System.out.print("PLease enter your name: ");
        String name = input.next();
        System.out.print("PLease enter your surname: ");
        String surname = input.next();
        System.out.print("Please enter your email: ");
        String email = input.next();
        System.out.print("Please enter the price: ");
        double price = input.nextDouble();
        while(true) {
            range_checking();
            if (theater_arr[rownum][seatnum] == 0) {
                theater_arr[rownum][seatnum] = 1;
                System.out.println("Your seat is now booked row:" + (rownum + 1) + " ,seat:" + (seatnum + 1));

                Person person = new Person(name, surname, email);
                Ticket ticket = new Ticket(rownum, seatnum, price, person);
                tickets_list.add(ticket);
                break;
            } else {
                System.out.print("Seat occupied! Do you want to re-enter? (Y/N): ");
                String choice=input.next();
                if(choice.equalsIgnoreCase("N")){
                    System.out.println("No seat booked");
                    break;
                }
            }
        }

    }


    static void print_seating_area() { //visually prints out the seating arrangement - booked and available

        System.out.println("    *************     ");
        System.out.println("    *   STAGE   *     ");
        System.out.println("    *************     ");

        for (int rownum = 0; rownum < theater_arr.length; rownum++) {
            for (int colnum = 0; colnum <4-(2*rownum); colnum++) {
                System.out.print(" ");
            }
            for (int colnum = 0; colnum < theater_arr[rownum].length; colnum++) {
                if (colnum== theater_arr[rownum].length/2) {
                    System.out.print(" ");
                }
                if (theater_arr[rownum][colnum] == 0) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }

            }System.out.println();
        }

    }

    static void cancel_ticket(){
    /* gets the row and seat number of the ticket required to cancel,then updates as unbooked in the theater_arr and
       removes the ticket from the tickets list array */

        range_checking(); //gets row and seat number inputs and updates the theater arr
        if (theater_arr[rownum][seatnum]==1) {
            theater_arr[rownum][seatnum] = 0;
            System.out.println("Your ticket at  row: "+(rownum+1)+" colnum: " +(seatnum+1)+" is now cancelled");

            //removes the ticket holding the specific row and seat number from the arraylist of ticket objects
            ListIterator<Ticket> iterator=tickets_list.listIterator();
            while (iterator.hasNext()) {
                Ticket ticket = iterator.next();
                if (ticket.getRow() == rownum && ticket.getSeat() == seatnum) {
                    iterator.remove();
                }
            }

        }else {
            System.out.println("This ticket number has not been booked! ");
        }

    }

    static void show_tickets_info(){
        /* prints out the information(name,surname,email,row,seat) of each ticket from the arraylist of ticket objects */

        double total_price=0;
        for (Ticket ticket:tickets_list){
            System.out.println("Ticket "+ (tickets_list.indexOf(ticket)+1));
            ticket.print();
            total_price+=ticket.getPrice();
            System.out.println(" ");
        }
        System.out.println("The total price is: "+total_price);
    }

    static void sort_tickets(){ //sorts the arraylist of Ticket objects and prints it out

        ArrayList<Ticket> tcopylist=new ArrayList<Ticket>(tickets_list); /* creates a copy of the tickets_list so that
        the original list remains unsorted when required for later use */

        //sorting the copied array by price
        for(int i=0;i< tcopylist.size()-1;i++){
            for(int j=0;j< tcopylist.size()-1-i;j++){
                if(tcopylist.get(j).getPrice()>tcopylist.get(j+1).getPrice()){
                    Ticket temp=tcopylist.get(j);
                    tcopylist.set(j,tcopylist.get(j+1)); //use of set methods as objects can't be exchanged in element way
                    tcopylist.set(j+1,temp);
                }
            }
        }

        for (Ticket ticket:tcopylist){ //printing sorted arraylist
            System.out.println("Ticket "+ (tcopylist.indexOf(ticket)+1));
            ticket.print();
            System.out.println(" ");
        }

    }

    static void show_available() {  //prints out the seat numbers available in each row
        for (int rownum = 0; rownum < theater_arr.length; rownum++) {
            System.out.print("Seats avaliable in row " + (rownum+1) + ": ");
            for (int seatnum = 0; seatnum < theater_arr[rownum].length; seatnum++) {
                if(theater_arr[rownum][seatnum]==0) {
                    if (seatnum == theater_arr[rownum].length - 1) {
                        System.out.print(seatnum + 1);
                    } else {
                        System.out.print(seatnum + 1 + ",");
                    }
                }
            }System.out.println();
        }
    }

    static void save() { //saves booked and unbooked seats to the file
        try {
            FileWriter writer = new FileWriter("CWArray.txt");
            for (int rownum = 0; rownum < theater_arr.length; rownum++) {
                writer.write("Row "+(rownum+1)+ ": ");
                for (int seatnum = 0; seatnum < theater_arr[rownum].length; seatnum++) {
                    writer.write(Integer.toString(theater_arr[rownum][seatnum]));
                    writer.write(" ");
                }writer.write("\n");
            }
            System.out.println("You successfully saved to the file.");
            writer.close();

        }catch(IOException e){
            System.out.println("An error occurred.");
        }
    }

    static void load() { //retrieves and prints out the information saved previously to the file
        try {
            System.out.println("contents loaded");
            File file = new File("CWArray.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                System.out.println(scan.nextLine());
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
    }

}

/* REFERENCES
    Cancel- https://www.java67.com/2018/12/how-to-remove-objects-or-elements-while-iterating-Arraylist-java.html
    swapping elements in arraylist - https://www.codevscolor.com/java-swap-elements-arraylist
    bubble sort- https://www.youtube.com/watch?v=F13_wsHDIG4
    load - https://www.youtube.com/watch?v=lHFlAYaNfdo
 */

