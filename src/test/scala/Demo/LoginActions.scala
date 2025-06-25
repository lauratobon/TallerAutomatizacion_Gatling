package Demo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import Demo.Data._ // Para acceder a url, email, password

object LoginActions {

  // Define la petición de login como un ChainBuilder reutilizable
  val userLogin = exec(http("Login User")
    .post("users/login")
    .body(StringBody(s"""{"email": "$email", "password": "$password"}""")).asJson
    .check(status.is(200))
    .check(jsonPath("$.token").saveAs("authToken")) // Guarda el token en la sesión
  )
}
