package bitcoin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PortfolioPerformance {

    private static final List<Price> PRICES = List.of(
                new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 1, 5, 0, 0), new BigDecimal("35464.53")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 2, 5, 0, 0), new BigDecimal("35658.76")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 3, 5, 0, 0), new BigDecimal("36080.06")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 3, 13, 0, 0), new BigDecimal("37111.11")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 6, 5, 0, 0), new BigDecimal("38041.47")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 7, 5, 0, 0), new BigDecimal("34029.61")));

    private static final List<Transaction> TRANSACTIONS = List.of(
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 1, 9, 0, 0), new BigDecimal("0.012")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 1, 15, 0, 0), new BigDecimal("-0.007")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 4, 9, 0, 0), new BigDecimal("0.017")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 5, 9, 0, 0), new BigDecimal("-0.01")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 7, 9, 0, 0), new BigDecimal("0.1")));

    // Complete this method to return a list of daily portfolio values with one record for each day from the 01-09-2021-07-09-2021 in ascending date order
    public static List<DailyPortfolioValue> getDailyPortfolioValues() {
        Map<LocalDateTime, BigDecimal> recordPrice = new HashMap<>();
        Map<LocalDateTime, BigDecimal> recordTrans = new HashMap<>();
        List<LocalDateTime> recordTime = List.of(
                LocalDateTime.of(2021, Month.SEPTEMBER, 1, 23, 59, 59),
                LocalDateTime.of(2021, Month.SEPTEMBER, 2, 23, 59, 59),
                LocalDateTime.of(2021, Month.SEPTEMBER, 3, 23, 59, 59),
                LocalDateTime.of(2021, Month.SEPTEMBER, 4, 23, 59, 59),
                LocalDateTime.of(2021, Month.SEPTEMBER, 5, 23, 59, 59),
                LocalDateTime.of(2021, Month.SEPTEMBER, 6, 23, 59, 59),
                LocalDateTime.of(2021, Month.SEPTEMBER, 7, 23, 59, 59));
        List<DailyPortfolioValue> res = new LinkedList<>();
        int i = 0;
        BigDecimal lastTran = new BigDecimal(0);
        //calculate the transaction on each day
        for(LocalDateTime time : recordTime){
            while (i<TRANSACTIONS.size() && time.getDayOfMonth() == TRANSACTIONS.get(i).effectiveDate().getDayOfMonth()) {
                lastTran = TRANSACTIONS.get(i).numberOfBitcoins().add(lastTran);
                recordTrans.put(time, lastTran);
                i++;
            }
            if (i < TRANSACTIONS.size() && time.getDayOfMonth() != TRANSACTIONS.get(i).effectiveDate().getDayOfMonth())
                recordTrans.put(time, lastTran);
        }
        //calculate the bitcoin price on each day
        i = 0;
        for(LocalDateTime time : recordTime){
            while (i<PRICES.size() && time.getDayOfMonth() == PRICES.get(i).effectiveDate().getDayOfMonth()) {
                lastTran = PRICES.get(i).price();
                recordPrice.put(time, lastTran);
                i++;
            }
            if (i < PRICES.size() && time.getDayOfMonth() != PRICES.get(i).effectiveDate().getDayOfMonth())
                recordPrice.put(time, lastTran);
        }
        //calculate the daily portfolio values on each day
        for(int n = 0; n < recordTime.size(); n++){
            LocalDateTime temp = recordTime.get(n);
            res.add(new DailyPortfolioValue(temp.toLocalDate(), new BigDecimal(String.valueOf(recordPrice.get(temp).multiply(recordTrans.get(temp))))));
        }
        return res;
    }
}
