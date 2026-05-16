package dev.sebastian.sanrafael.adapter.repository.inmemory;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.port.HealthReportGateway;
import dev.sebastian.sanrafael.core.port.ProductRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Repository
public class InMemoryHealthReportGateway implements HealthReportGateway {


    /*
    * Repositorio interno para realizar las consultas sobre si existe un producto.
    */
    private final ProductRepository productRepository;

    /*
    * El cliente Http nativo de java usado tipo hilo contable de forma segura.
    */
    private final HttpClient httpClient= HttpClient.newHttpClient();

    /*
    * Se utiliza esta función para transformar cadenas de texto que tienen formato JSON a
    * objetos Java.
    */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*
    * Unico constructor para la adición de dependencias.
    * @param productRepository Repositorio de productos requeridos para buscar la entidad por su
    * nombre.
    */
    public InMemoryHealthReportGateway(ProductRepository productRepository) {
        this.productRepository=productRepository;
    }

    /*
    * Recupera el reporte de salud de un producto en especifico.
    * @param productName es el unico dato a la cual se va a buscar.
    * */
    @Override
    public ReportDTO getHealthReport(String productName) {
        Product product = productRepository.findByName(productName);

        if(product == null){
            throw new IllegalArgumentException("Product not found" + productName);
        }

        String url = product.healthURL().toString();

        /*
        * Construcción de la estructura de la petición Get mediante el patrón builder
        */
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try{
            /*
            * Este envia la petición de forma sincrona llegando a esperar la respuesta del servidor.
            */
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            /*
            * Validar si la petición fue exitosa
            */
            if(response.statusCode() == 200){
                /*
                 * Transforma la cadena de texto JSON del cuerpo al esperado por parte del DTO.
                 */
                return objectMapper.readValue(response.body(), ReportDTO.class);
            }else{
                /*
                * Controla explicitamente errores devueltos por el servidor externo.
                * */
                throw new RuntimeException("HTTP error: status code " + response.statusCode());
            }
        } catch (Exception e) {
    throw new RuntimeException("Failed to fetch health report via HTTPCLIENT for product:" + productName,e);
}
    }
}
