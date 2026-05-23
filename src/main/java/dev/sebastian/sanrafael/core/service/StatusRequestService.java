package dev.sebastian.sanrafael.core.service;

import dev.sebastian.sanrafael.core.dto.ReportDTO;
import dev.sebastian.sanrafael.core.entity.Product;
import dev.sebastian.sanrafael.core.entity.Report;
import dev.sebastian.sanrafael.core.port.HealthReportGateway;
import dev.sebastian.sanrafael.core.port.ProductRepository;
import dev.sebastian.sanrafael.core.port.ReportRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StatusRequestService {
    private final ProductRepository repository;
    private final HealthReportGateway healthReportGateway;
    private final ReportRepository reportRepository;

    /*
    * Se modifica el constructor para que pueda recibir las 3 dependencias.
    */
    public StatusRequestService(ProductRepository repository, HealthReportGateway healthReportGateway,  ReportRepository reportRepository) {
        this.repository = repository;
        this.healthReportGateway = healthReportGateway;
        this.reportRepository = reportRepository;
    }

    public List<Product> findAllProducts(){
        return repository.findAllProducts();
    }

    public void requestStatusOfAllProducts(){
        List<Product> products = repository.findAllProducts();

        products.forEach(product -> {
            try {
                System.out.println("Requesting service status for" + product.name());

                /*
                * Se debe realizar la consulta HTTP
                */
                ReportDTO dto = healthReportGateway.getHealthReport(product.healthURL());

                /*
                * Se crea el dominio con los datos que obtenemos en HTTPClient.
                */
                Report domainReport = new Report(
                        null,
                        new java.util.Date(),
                        List.of(),
                        dto.ramUsagePercentage(),
                        dto.diskUsagePercentage()
                );

                /*
                * Finalmente le indicamos con el saveReport que lo queremos guardar en la
                * base de datos por medio del adapatador.
                */
                reportRepository.saveReport(domainReport);

                System.out.println("Report saved in PostgreSQL successfully : " + product.name());
            }catch (Exception e){
                System.out.println("Error with the product: " + product.name() + ":" +  e.getMessage());
            }
        });
    }

}


/*
* Clase original vista en clase y realizada respectivamente en clase.
*/
//    public static final int REQUEST_FREQUENCY = 1;
//    public static TimeUnit REQUEST_FREQUENCY_UNIT = TimeUnit.SECONDS;
//
//    private final ProductRepository repository;
//    public StatusRequestService(ProductRepository repository) {
//        this.repository = repository;
//
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.scheduleAtFixedRate(this::requestStatusOfAllProducts, 0,REQUEST_FREQUENCY,
//                REQUEST_FREQUENCY_UNIT);
//    }
//    public void requestStatusOfAllProducts() {
//        List<Product> products = repository.findAllProducts();
//        products.forEach(product -> {
//            System.out.println("Solicitando etado de servicio" + product);
//        });
//        System.out.println("Termine de solicitar todos los productos");
//    }
