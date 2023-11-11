public class Ticket {
    private int row;
    private int seat;
    private double price;
    private Person person;

    Ticket(int row,int seat,double price,Person person){
        this.row=row;
        this.seat=seat;
        this.price=price;
        this.person=person;
    }

    void print() { // prints all information taken as input
        System.out.println("Name: "+person.getName());
        System.out.println("Surname: "+person.getSurname());
        System.out.println("Email: "+person.getEmail());
        System.out.println("Row: "+ (row+1));
        System.out.println("Seat: "+ (seat+1));
        System.out.println("Price: £"+price);
    }

    public void setRow(int row) {

        this.row = row;
    }

    public int getRow() {

        return row;
    }

    public void setSeat(int seat) {

        this.seat = seat;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    public int getSeat() {

        return seat;
    }

    public double getPrice() {

        return price;
    }


    public Person getPerson() {

        return person;
    }

    public void setPerson(Person person) {

        this.person = person;
    }

}

