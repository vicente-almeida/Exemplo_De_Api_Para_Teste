import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class TestaCliente {

    String enderecoApi = "http://localhost:8080";
    String recursoCliente = "/cliente";
    String recursoApagaTodos = "/apagaTodos";

    @Test
    @DisplayName("Quando pegar lista de clientes sem adicionar clientes,Então a lista deve estar vazia")
    public void pegaTodosClientes() {
        apagaTodosClientes();
        String respostaEsperada = "{}";
        given().
                contentType(JSON).
        when().
                get(enderecoApi).
        then().
                statusCode(200).assertThat().
                body(new IsEqual(respostaEsperada));


    }

    @Test
    @DisplayName("Quando cadastrar um cliente,Então o cliente é cadastrado com sucesso")
    public void cadastroCliente() {


        String corporequisicao = "{\n" +
                " \"nome\": \"Vicente\",\n" +
                " \"idade\": \"40\",\n" +
                " \"id\": \"1458\"\n" +
                "}";

        String respostaEsperada = "{\"1458\":" +
                "{\"nome\":\"Vicente\"," +
                "\"idade\":40," +
                "\"id\":1458," +
                "\"risco\":0}" +
                "}";


        given().
                contentType(JSON).
                body(corporequisicao).
        when().
                post(enderecoApi + recursoCliente).
        then().
                statusCode(201).
                assertThat().
                body(new IsEqual(respostaEsperada));

        apagaTodosClientes();


    }

    @Test
    @DisplayName("Quando atualizar um cliente,Então o cliente é atualizado com sucesso")
    public void atualizarCliente() {


        String corpoRequisicao = "{\n" +
                " \"nome\": \"Vicente\",\n" +
                " \"idade\": \"40\",\n" +
                " \"id\": \"1458\"\n" +
                "}";

        String corpoAtualizadorequisicao = "{\n" +
                " \"nome\": \"Vicente de Almeida\",\n" +
                " \"idade\": \"60\",\n" +
                " \"id\": \"1458\"\n" +
                "}";

        String respostaEsperada = "{\"1458\":" +
                "{\"nome\":\"Vicente de Almeida\"," +
                "\"idade\":60," +
                "\"id\":1458," +
                "\"risco\":0}" +
                "}";


        given().
                contentType(JSON).
                body(corpoRequisicao).
                when().
                post(enderecoApi + recursoCliente);

        given().
                contentType(JSON).
                body(corpoAtualizadorequisicao).
                when().
                put(enderecoApi + recursoCliente).
                then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));


    }

    @Test
    @DisplayName("Quando deletar um cliente,Então o cliente é deletado com sucesso")
    public void deletarCliente() {

        String idParaApagar = "/1458";


        String corpoRequisicao = "{\n" +
                " \"nome\": \"Vicente\",\n" +
                " \"idade\": \"40\",\n" +
                " \"id\": \"1458\"\n" +
                "}";


        String respostaEsperada = "CLIENTE REMOVIDO: { NOME: Vicente, IDADE: 40, ID: 1458 }";


        given().
                contentType(JSON).
                body(corpoRequisicao).
                when().
                post(enderecoApi + recursoCliente);

        given().
                contentType(JSON).

                when().
                delete(enderecoApi + recursoCliente + idParaApagar).
                then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));


    }

    public void apagaTodosClientes() {
        String respostaEsperada = "{}";

        given().
                contentType(JSON).

        when().
                delete(enderecoApi + recursoCliente + recursoApagaTodos).
        then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));


    }
}
