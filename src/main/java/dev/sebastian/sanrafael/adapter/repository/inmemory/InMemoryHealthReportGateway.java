package dev.sebastian.sanrafael.adapter.repository.inmemory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.port.HealthReportGateway;
import dev.sebastian.sanrafael.core.port.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
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
    public ReportDTO getHealthReport(URL healthEndpoint) throws JsonProcessingException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(healthEndpoint.toString()))
                .GET()
                .build();
        try{
            HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

            if(response.statusCode()==200){
                return objectMapper.readValue(
                    response.body(),
                        ReportDTO.class
                );
            }else {
                throw new  RuntimeException("HTTP error: status code " + response.statusCode());
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to get health report" + healthEndpoint, e);
        }
    }
}
