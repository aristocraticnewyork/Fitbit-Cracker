package fitbit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        FileReader input = null;
        try {
            input = new FileReader("accounts.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufRead = new BufferedReader(input);
        String myLine = null;

        try {
            while ( (myLine = bufRead.readLine()) != null)
            {
                String[] credentials = myLine.split(":");
                Fitbit.testCredentials( credentials[0], credentials[1] );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FitbitAuthException e) {
            e.printStackTrace();
        }
    }
}
