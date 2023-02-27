// 1 - pacote
package petstore;

// 2 -biblioteca
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

// 3 - Classes
public class Pet {
    //3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade Pet

    //3.2 - Metodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String (Files.readAllBytes(Paths.get(caminhoJson)));
    }
    //Incluir - Create - Post
    @Test //Identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        //Sintaxe GherKin
        //Dado - quando - Então
        //Given - When - Then

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
        ;
    }

}
