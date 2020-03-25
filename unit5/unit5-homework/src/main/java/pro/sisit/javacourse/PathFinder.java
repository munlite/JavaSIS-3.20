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

        Transport transport = Optional.ofNullable(findingCheapOption(filterByRouteTypeAndVolume(deliveryTask,
                transports),
                deliveryTask)).orElse(null);
        return transport;

    }

    /**
     * Метод поиска дешового маршрута
     * @param transports лист с транспортом, который подходит по способу доставки
     * @param deliveryTask
     * @return самый дешевый вариант перевозки
     */
    public Transport findingCheapOption(List<Transport> transports, DeliveryTask deliveryTask) {
        if (transports != null && deliveryTask != null) {
            List<Route> routeList = deliveryTask.getRoutes();
            Map<RouteType, BigDecimal> allValues = routeList.stream()
                    .collect(Collectors.toMap(Route::getType, Route::getLength));
            return transports.stream()
                    .filter(transport -> allValues.containsKey(transport.getType()))
                    .min(Comparator.comparing(transport ->
                            transport.getPrice().multiply(allValues.get(transport.getType())))).get();
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
            List<Route> listRoute = deliveryTask.getRoutes();
            return transports.stream()
                    .filter(transport -> transport.getVolume().compareTo(deliveryTask.getVolume()) >= 0)
                    .filter(transport -> {
                        listRoute.stream()
                                .filter(route -> (route.getType() == transport.getType()))
                                .collect(Collectors.toList());
                    return true;})
                    .collect(Collectors.toList());
        }
            else {
                return transports = null;
        }

    }

}
