public class Driver {

     void strength(String pswd)
    {
        int rules = 0;

        if(pswd.length()>=8)
        {
            rules++;
        }
        if(pswd.matches(".*[A-Z].*"))
        {
            rules++;
        }
        if(pswd.matches(".*\\d.*"))
        {
            rules++;
        }
        if(pswd.matches(".*[\\W_].*"))
        {
            rules++;
        }

        if((rules==0)||(rules==1))
        {
            System.out.println("Weak Password");
        }
        else if((rules==2)||(rules==3))
        {
            System.out.println("Medium Password");
        }
        else
        {
            System.out.println("Strong Password");
        }

    }


}
