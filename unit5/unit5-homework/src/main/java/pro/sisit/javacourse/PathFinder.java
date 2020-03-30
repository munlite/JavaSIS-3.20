package pro.sisit.javacourse;


import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.RouteType;
import pro.sisit.javacourse.optimal.Transport;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class PathFinder {

    /**
     * Возвращает оптимальный транспорт для переданной задачи.
     * Если deliveryTask равна null, то оптимальеый транспорт тоже равен null.
     * Если список transports равен null, то оптимальеый транспорт тоже равен null.
     */
    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {

        Transport transport = Optional
                .ofNullable(findingCheapOption(filterByRouteTypeAndVolume(deliveryTask, transports),
                        deliveryTask))
                .orElse(null);
        return transport;

    }

    /**
     * Метод поиска дешового маршрута
     * @param transports лист с транспортом, который подходит по способу доставки
     * @param deliveryTask
     * @return самый дешевый вариант перевозки
     */
    public Transport findingCheapOption(List<Transport> transports,
                                        DeliveryTask deliveryTask) {
        if (deliveryTask != null) {
            Map<RouteType, BigDecimal> allValues = deliveryTask.getRoutes()
                    .stream()
                    .collect(Collectors.toMap(Route::getType, Route::getLength));
            return Optional.ofNullable(transports)
                    .map(data -> transports
                            .stream()
                            .filter(transport -> allValues.containsKey(transport.getType()))
                            .min(Comparator.comparing(transport ->
                                    transport.getPrice()
                                            .multiply(allValues.get(transport.getType()))))
                            .get())
                    .orElse(null);
        } else {
            return null;
        }
    }


    /**
     * Метод филтрации транспорта по способу доставки
     * @param deliveryTask Заказ
     * @param transports весь транспор, доступный для доставки
     * @return лист с транспортом
     */
    public List<Transport> filterByRouteTypeAndVolume(DeliveryTask deliveryTask, List<Transport> transports) {

        if (deliveryTask != null && transports != null) {
            Map<RouteType, BigDecimal> allValues = deliveryTask.getRoutes()
                    .stream()
                    .collect(Collectors.toMap(Route::getType, Route::getLength));
            return transports.stream()
                    .filter(transport -> transport.getVolume()
                            .compareTo(deliveryTask.getVolume()) >= 0)
                    .filter(transport -> allValues.containsKey(transport.getType()))
                    .collect(Collectors.toList());
        }
            else {
            return null;
        }

    }

}
