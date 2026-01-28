package services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import domain.PurchaseOrder;

public class AuditoriaService {

    public Map<Long, Double> procesarAuditoriaMasiva(List<PurchaseOrder> ordenes) {

        // Filtrado secuencial
        List<PurchaseOrder> ordenesFiltradas = ordenes.stream()
            .filter(o -> o.getLineItems() != null && o.getLineItems().size() > 3)
            .filter(o -> o.getPlaced_on().isAfter(LocalDateTime.now().minusYears(1)))
            .toList();

        ExecutorService executor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
        );

        try {
            // Enviar tareas al executor (Callable<Optional<PurchaseOrder>>)
        List<Future<Optional<PurchaseOrder>>> futures =
            ordenesFiltradas.stream()
             .map(o -> executor.submit(() ->
            Optional.of(o).filter(this::validarFraude) //si validarFraude(o) → true → mantiene el valor | si false → Optional.empty()
        ))
        .toList();

            // Recolectar resultados
            return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return Optional.<PurchaseOrder>empty();
                    } catch (ExecutionException e) {
                        return Optional.<PurchaseOrder>empty();
                    }
                })
                .flatMap(Optional::stream)
                .collect(Collectors.toMap(
                    PurchaseOrder::getId,
                    o -> o.getTotal().doubleValue()
                ));

        } finally {
            executor.shutdown();
        }
    }

    private boolean validarFraude(PurchaseOrder o) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return o.getTotal() < 5000;
    }
}
