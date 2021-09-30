package cl.entel.eai.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("factunifConnector")
public class FACTUNIFDatabaseConnector extends DatabaseConnector{

    public FACTUNIFDatabaseConnector(
            @Value("${factunif.db.url}")  String url,
            @Value("${factunif.db.username}") String username,
            @Value("${factunif.db.password}") String password) {
        super(url, username, password);
    }
}
