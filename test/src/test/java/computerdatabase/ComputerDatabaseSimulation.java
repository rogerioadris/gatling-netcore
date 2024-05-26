package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This sample is based on our official tutorials:
 * <ul>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/quickstart">Gatling quickstart tutorial</a>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/advanced">Gatling advanced tutorial</a>
 * </ul>
 */
public class ComputerDatabaseSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol =
        http.baseUrl("http://localhost:5018")
            .acceptHeader("application/json")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Naitzel Testing");

    ScenarioBuilder weatherforecast = scenario("Weatherforecast").exec(
        http("Weatherforecast").get("/weatherforecast")
    );

    {
        setUp(
            weatherforecast.injectOpen(
                constantUsersPerSec(2).during(10),
                constantUsersPerSec(5).during(15).randomized(),
                rampUsersPerSec(6).to(600).during(Duration.ofMinutes(1)),
                rampUsersPerSec(600).to(1200).during(Duration.ofMinutes(2))
            )
        ).protocols(httpProtocol);
    }
}
