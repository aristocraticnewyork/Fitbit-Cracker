package fitbit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.System.exit;

/**
 * Created by aurelienschiltz on 11/08/2016.
 */
public class Accounts {
    private static Accounts ourInstance = new Accounts();

    public static Accounts getInstance() {
        return ourInstance;
    }

    private Accounts() {
        File working = new File("workingAccounts.txt");
        if(!working.exists() && !working.isDirectory()) {
            try {
                working.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File bad = new File("badAccounts.txt");
        if(!bad.exists() && !bad.isDirectory()) {
            try {
                bad.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // tries to create new file in the system
    }

    public void save(String email, String password, boolean working)
    {
        BufferedWriter out = null;
        try
        {
            FileWriter fstream = null;
            if (working)
                fstream = new FileWriter("workingAccounts.txt", true);
            else
                fstream = new FileWriter("badAccounts.txt", true);

            out = new BufferedWriter(fstream);
            out.write(email+":"+password);
        }
        catch (IOException e)
        {
            exit(0);
        }
        finally
        {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
