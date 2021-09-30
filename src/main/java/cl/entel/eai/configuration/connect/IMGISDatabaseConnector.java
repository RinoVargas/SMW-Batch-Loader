package cl.entel.eai.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("imgisConnector")
public class IMGISDatabaseConnector extends DatabaseConnector{

    public IMGISDatabaseConnector(
            @Value("${imgis.db.url}")  String url,
            @Value("${imgis.db.username}") String username,
            @Value("${imgis.db.password}") String password) {
        super(url, username, password);
    }
}
