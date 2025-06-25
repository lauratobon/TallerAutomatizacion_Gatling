package Demo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import Demo.Data._
import Demo.LoginActions._ 

class AddContactTest extends Simulation{

  // 1 Http Conf
  val httpConf = http.baseUrl(url)
    .acceptHeader("application/json")
    //Verificar de forma general para todas las solicitudes
    .check(status.is(200))

  // 2 Scenario Definition 
  val scn = scenario("Create contact").
    .exec(userLogin) // Primero, realizo el login para obtener el authToken y guardarlo en la sesión
    .exec(http("Create Contact Request") // Luego, hago la petición para crear el contacto
      .post("contacts")
      .header("Authorization", "Bearer ${authToken}") // Usa el authToken guardado en la sesión
      .body(StringBody(s"""{ "firstName": "Lau", "lastName": "Tobon", "birthdate": "1970-01-01", "email": "jdoe@fake.com", "phone": "8005555555", "street1": "1 Main St.", "street2": "Apartment A", "city": "Anytown", "stateProvince": "KS", "postalCode": "12345", "country": "USA" }""")).asJson
      .check(status.is(201)) // Espera un 201 Created para esta petición específica
    )

  // 3 Load Scenario
  setUp(
    scn.inject(rampUsersPerSec(5).to(15).during(30))
  ).protocols(httpConf);
}
