import java.util.*;

public class BackEndPlayground {
    

    public static void main (String args[])
    {
        boolean on = true;
        while (on == true)
        {
            Scanner s = new Scanner(System.in);
            String answer = s.nextLine();

            if (answer.equals("done"))
            {
                on = false;
                s.close();
            }

            System.out.println("ANSWER: " + answer);

        }
    }
}
