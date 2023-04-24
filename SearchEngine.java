import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class SearchEngineHandler implements URLHandler {
    private List<String> strings = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/add")) {
            String s = url.getQuery().substring(2); // remove the "s=" prefix
            strings.add(s);
            return "Added string: " + s;
        } else if (url.getPath().equals("/search")) {
            String query = url.getQuery().substring(2); // remove the "s=" prefix
            List<String> results = new ArrayList<>();
            for (String s : strings) {
                if (s.contains(query)) {
                    results.add(s);
                }
            }
            return String.join(", ", results);
        } else {
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchEngineHandler());

        System.out.println("Search engine started on port " + port);
    }
}