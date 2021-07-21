package com.nikitagru.search.config;

import com.nikitagru.search.search.Searcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/***
 * Класс расписания запуска алгоритма поиска аэропорта
 */
@Component
public class ScheduledTasks {

    @Autowired
    private ApplicationArguments applicationArguments;

    /***
     * Алгоритм поиска
     */
    @Scheduled(fixedRate = 5000)
    public void findAirports() {
        int searchColumn = 2;
        if (applicationArguments.getSourceArgs().length != 0) {
            try {
                searchColumn = Integer.parseInt(applicationArguments.getSourceArgs()[0]);
            } catch (NumberFormatException e) {
                System.out.println("В качестве номера колонки введено не число");
                e.printStackTrace();
            }
        }
        Searcher searcher = new Searcher(searchColumn);
        searcher.showAirports();
    }
}
