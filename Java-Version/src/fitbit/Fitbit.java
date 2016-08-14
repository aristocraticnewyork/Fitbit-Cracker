package fitbit;

import java.util.*;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * Unofficial Fitbit client for knowing if credentials are correct to login or not.
 *
 *
 * @author Aurélien SCHILTZ
 *
 */
public class Fitbit {


    private static final String AJAX_API_URL = "https://www.fitbit.com/ajaxapi";

    private static final String LOGIN_URL = "https://www.fitbit.com/login";


    /**
     * Creates a new Fitbit instance to test the Credentials
     *
     * @param email email address used to authenticate with Fitbit website
     * @param password password used to authenticate with Fitbit website
     * @return Fitbit client
     * @throws FitbitAuthException
     */
    public static Fitbit testCredentials( String email, String password ) throws FitbitAuthException {
        return new Fitbit( email, password );
    }


    /**
     * Constructor attempts to authenticate based on provided credentials.
     *
     * @param email address on Fitbit account
     * @param password of Fitbit account
     * @throws FitbitAuthException if authentication fails
     */
    public Fitbit( String email, String password ) throws FitbitAuthException {
        boolean isWorking = authenticate( email, password );
        if (isWorking)
            Accounts.getInstance().save(email, password, true);
        else
            Accounts.getInstance().save(email, password, false);
    }


    protected boolean authenticate( String email, String password ) throws FitbitAuthException {
        boolean isWorking = false;
        Element isError = null;
        Proxies proxiesInstance = Proxies.getInstance();
        try {
            Map.Entry<String, Integer> proxy = proxiesInstance.getNextProxy();
            Connection.Response loginFormHttp = Jsoup.connect(LOGIN_URL)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .method(Connection.Method.GET)
                    .proxy(proxy.getKey(), proxy.getValue())
                    .timeout(10000)
                    .execute();
            Document docToParse = loginFormHttp.parse();
            Element loginForm = docToParse.getElementById("loginForm");
            Elements hiddenInputs = loginForm.select("input[type=hidden]");
            Map<String, String> dataPost = new HashMap<>();
            dataPost.put("email", email);
            dataPost.put("password", password);
            dataPost.put("rememberMe", "true");

            for (Element input : hiddenInputs) {
                dataPost.put(input.attr("name"), input.attr("value"));
            }
            Connection.Response responseLogin = Jsoup.connect(LOGIN_URL)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .data(dataPost)
                    .method(Connection.Method.POST)
                    .proxy(proxy.getKey(), proxy.getValue())
                    .timeout(10000)
                    .cookies(loginFormHttp.cookies())
                    .execute();
            Document docResponse = responseLogin.parse();
            isError = docResponse.getElementById("wrapper-messages");
            isWorking = (isError != null) ? false : true;
            proxiesInstance.workingProxies += 1;

        } catch( Exception e ) {
            isWorking = authenticate(email, password);
        }
        return isWorking;
    }

}
