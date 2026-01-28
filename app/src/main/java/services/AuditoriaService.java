package services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import domain.PurchaseOrder;

public class AuditoriaService {

    public Map<Long,Double> procesarAuditoriaMasiva(List<PurchaseOrder> ordenes){

        // Filtrado sequencial con streams
        List<PurchaseOrder> ordenesFiltradas = ordenes.stream()
            .filter(o -> o.getLineItems() != null && o.getLineItems().size() > 3)
            .filter(o -> o.getPlaced_on().isAfter(LocalDateTime.now().minusYears(1)))
            .toList();
            
        //Pool de hilos
        ExecutorService executor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());


        try{
            //Enviar tareas al executor
            List<Future<PurchaseOrder>> futures = ordenesFiltradas.stream()
            .map(o -> executor.submit(()-> {
                if(this.validarFraude(o)){
                    return o;
                }
                return null;




            })).toList();

        //Recolectar resultados

        return futures.stream().map(future -> { 
            try{
                return future.get();
            }catch(InterruptedException | ExecutionException e) {
                return null;

            }




        })
        .filter(o -> o!=null)
        .collect(Collectors.toMap(PurchaseOrder::getId,
                                o -> o.getTotal().doubleValue()
        ));
        

        }
        finally{
            executor.shutdown();


        }




        
    }


    private boolean validarFraude(PurchaseOrder o ){


        try {
            Thread.sleep(100);

        } catch(InterruptedException e){
            Thread.currentThread().interrupt();


        }

        return o.getTotal() < 5000;


    }

}
