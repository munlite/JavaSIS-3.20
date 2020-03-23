package pro.sisit.javacourse;

import org.w3c.dom.ls.LSOutput;
import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.RouteType;
import pro.sisit.javacourse.optimal.Transport;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathFinder {

    /**
     * Возвращает оптимальный транспорт для переданной задачи.
     * Если deliveryTask равна null, то оптимальеый транспорт тоже равен null.
     * Если список transports равен null, то оптимальеый транспорт тоже равен null.
     */
    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {
        // ToDo: realize me!
        if(filterByRouteTypeAndVolume(deliveryTask, transports).isPresent()) {
            Transport transport = findingCheapOption(filterByRouteTypeAndVolume(deliveryTask, transports).get(), deliveryTask.getRoutes());
            return transport;
        } else return null;

    }

    /**
     * Метод поиска дешового маршрута
     * @param transports лист с транспортом, который подходит по способу доставки
     * @param routeList лист с маршрутами
     * @return самый дешевый вариант перевозки
     */
    private Transport findingCheapOption(List<Transport> transports, List<Route> routeList) {
        BigDecimal min = Collections.max(transports.stream().map(Transport::getPrice).collect(Collectors.toList()))
                .multiply(Collections.max(routeList.stream().map(Route::getLength).collect(Collectors.toList())));
        Transport minTransport = transports.get(0);
        for (Transport t : transports)
            for (Route r : routeList) {
                if (t.getType() == r.getType() && min.compareTo(t.getPrice().multiply(r.getLength())) == 1) {
                    min = t.getPrice().multiply(r.getLength());
                    minTransport = t;
                }
            }
        return minTransport;
    }

    /**
     * Метод филтрации транспорта по способу доставки
     * @param deliveryTask Заказ
     * @param transports весь транспор, доступный для доставки
     * @return лист с транспортом
     */
    private Optional<List<Transport>> filterByRouteTypeAndVolume(DeliveryTask deliveryTask, List<Transport> transports) {

        if (deliveryTask != null && transports != null) {
            List<Route> listRoute = deliveryTask.getRoutes();
            Optional<List<Transport>> transportOprionalList = Optional.of(transports);
            return transportOprionalList = Optional.of(transports.stream()
                    .filter(transport -> {
                        for (Route r : listRoute) {
                            if (transport.getType() == r.getType() && transport.getVolume().compareTo(deliveryTask.getVolume()) >= 0)
                                return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toList()));

        } else {
            return Optional.empty();
        }
    }
}
