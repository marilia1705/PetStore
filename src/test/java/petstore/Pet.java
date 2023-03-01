// 1 - pacote
package petstore;

// 2 -biblioteca
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.Argument;
import org.hamcrest.Matcher;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

// 3 - Classes
public class Pet {
    //3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade Pet

    //3.2 - Metodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String (Files.readAllBytes(Paths.get(caminhoJson)));
    }
    //Incluir - Create - Post
    @Test (priority = 1) //Identifica o método ou função como um teste para o TestNG
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
                .body("name", is("Zeze"))
                .body("status", is("available"))
                .body("category.name", is("MMRXMV"))
                .body("tags.name", contains("data"))
        ;
    }
    @Test (priority = 2)
    public void consultarPet(){
        String petId = "2404196314";

        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Zeze"))
                .body("category.name", is ("MMRXMV"))
                .body("status", is("available"))
        .extract()
                .path ("category.name")
        ;
        System.out.println("o token é " + token);
    }
    @Test (priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Zeze"))
                .body( "status", is("sold"))
        ;
    }

    @Test (priority = 4)
    public void execluirPet(){
        String petId = "2404196314";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;

    }

}
