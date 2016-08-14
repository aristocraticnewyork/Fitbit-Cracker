package fitbit;

import java.io.*;
import java.util.*;

import static java.lang.System.exit;

/**
 * Created by aurelienschiltz on 11/08/2016.
 */
public class Proxies {
    private static Proxies ourInstance = new Proxies();


    public Map<String, Integer> proxiesList;
    /* The iterator to have the next entry of the proxiesList */
    public Iterator<Map.Entry<String, Integer>> it2 = null;
    /* A count to know if among the whole list of proxies at least 1 proxy worked */
    public Integer workingProxies = 0;

    public static Proxies getInstance() {
        return ourInstance;
    }

    private Proxies() {
        File working = new File("proxies.txt");
        if(!working.exists() && !working.isDirectory()) {
            try {
                working.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            proxiesList = new LinkedHashMap<>();
            FileReader input = null;
            try {
                input = new FileReader("proxies.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader bufRead = new BufferedReader(input);
            String myLine = null;
            try {
                while ( (myLine = bufRead.readLine()) != null)
                {
                    String[] proxy = myLine.split(":");
                    proxiesList.put( proxy[0], new Integer(proxy[1]) );
                }
                Set<Map.Entry<String, Integer>> setLhm = proxiesList.entrySet();
                it2 = setLhm.iterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map.Entry<String, Integer> getNextProxy()
    {
        if (!it2.hasNext()){
            if (workingProxies == 0)
                System.exit(1);
            workingProxies = 0;
            Set<Map.Entry<String, Integer>> setLhm = proxiesList.entrySet();
            it2 = setLhm.iterator();
        }
        Map.Entry<String, Integer> e = it2.next();
        return e;
    }


}
