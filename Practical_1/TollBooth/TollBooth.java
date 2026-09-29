import java.util.Scanner;
record Vehicle(String vehicleName,String vehicleNumber){}

public class TollBooth {
    public static void main(String[] args) {
        String no;
        String name;

        int totaltoll=0;
        int bike=0;
        int car=0;
        int truck=0;


        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Vehicle Number or Type done : ");
        no = sc.next();
        do
        {

            System.out.println("Enter Vehicle Name : ");
            name = sc.next();

            Vehicle v = new Vehicle(name,no);

            if(v.vehicleName().equals("bike"))
            {
                totaltoll=totaltoll+20;
                bike++;
            }
            else if(v.vehicleName().equals("car"))
            {
                totaltoll=totaltoll+50;
                car++;
            }
            else if(v.vehicleName().equals("truck"))
            {
                totaltoll=totaltoll+150;
                truck++;
            }
            System.out.println("Enter Vehicle Number or Type done : ");
            no = sc.next();

        }while(no.equals("done")!=true);

        String frequent="";
        if((bike>car)&&(bike>truck))
        {
            frequent="bike";
        }
        if((car>bike)&&(car>truck))
        {
            frequent="car";
        }
        if((truck>bike)&&(truck>car))
        {
            frequent="truck";
        }
        System.out.println("Total Toll Paid : "+totaltoll);
        System.out.println("Most Frequent : "+frequent);


    }
}
