import java.util.*;

public class Report {
    /**
     * Класс Report хранит в себе 2 мапы классов MonthReportGlobalInfo и YearReportGlobalInfo, а также мапу years
     * В качестве ключа у мап идет год, в качестве значения - соответствующие классы
     * Данный класс по сути хранит в себе все месячные отчеты и годовой отчет за определенный год
     * <p>
     * В методах @setYearReport  и  @setMonthReport в классе FinanceFileReader мы проверяем по введеному ключу существует ли у нас значение в мапе
     * Если существует, мы сохраняем эти данные в буферный объект @newYear и добавляем к нему новые считанные отчеты,
     * неважно, месячные или годовые. Далее заносим в соответствующую мапу
     * <p>
     * Если не существует, тогда просто добавляем данные о считанном отчете в мапу с ключом inputYear(Введенный год пользователем)
     */
    public static HashMap<Integer, YearReportGlobalInfo> globalInfoYearReportsMap = new HashMap<>();
    public static HashMap<Integer, MonthReportGlobalInfo> globalInfoMonthReportsMap = new HashMap<>();


    public static void deleteYearReport(Integer year) {
        if (globalInfoYearReportsMap.size() > 0 && globalInfoYearReportsMap.containsKey(year)) {
            globalInfoYearReportsMap.remove(year);
        }
    }

    public void printYearReportInfo(int yearInput) {

        if (globalInfoYearReportsMap.size() > 0 && globalInfoYearReportsMap.containsKey(yearInput)) {
            System.out.println("Годовой отчет за " + yearInput);
            YearReportGlobalInfo report = globalInfoYearReportsMap.get(yearInput);
            report.printYearReport();
        } else {
            System.out.println("Введен неверный год, либо не загружены отчеты");
        }
    }

    public void printMonthReportsInfo(int yearInput) {
        if (globalInfoMonthReportsMap.size() > 0 && globalInfoMonthReportsMap.containsKey(yearInput)) {
            System.out.println("Месячные отчеты за " + yearInput);
            MonthReportGlobalInfo report = globalInfoMonthReportsMap.get(yearInput);
            report.printMonthReports();
        } else {
            System.out.println("Введен неверный год, либо не загружены отчеты");
        }
    }

    public static void compareReports(int inputYear) {
        if (globalInfoYearReportsMap.containsKey(inputYear)) {
            if (globalInfoMonthReportsMap.containsKey(inputYear)) {
                YearReportGlobalInfo yearReportGlobalInfo = globalInfoYearReportsMap.get(inputYear); //мапа месяц - yearReport
                MonthReportGlobalInfo monthReportGlobalInfo = globalInfoMonthReportsMap.get(inputYear); // Лист трат по месяцам

                for (int i = 0; i < monthReportGlobalInfo.monthReportsList.size(); i++) {
                    YearReport yearReport = yearReportGlobalInfo.yearReports.get(i + 1);
                    MonthReport monthReport = monthReportGlobalInfo.monthReportsList.get(i);
                    if (yearReport.getSumIncomeInMonth() == monthReport.getSumIncomeOfMonth()) {
                        System.out.println("Доходы за " + (i + 1) + " месяц у годового и месячного отчета равны.\n" +
                                "Сумма: " + yearReport.getSumIncomeInMonth());
                        if (yearReport.getSumExpenseInMonth() == monthReport.getSumExpenseOfMonth()) {
                            System.out.println("Расходы за " + (i + 1) + " месяц у годового и месячного отчета равны.\n" +
                                    "Сумма: " + yearReport.getSumExpenseInMonth());
                        } else {
                            System.out.println("Сумма расходов за " + (i + 1) + " месяц не совпала. " +
                                    yearReport.getSumExpenseInMonth() + " расход в годовом отчете\n" +
                                    monthReport.getSumExpenseOfMonth() + " расход в месячном отчете");
                        }
                    } else {
                        System.out.println("Сумма доходов за " + (i + 1) + " месяц не совпал. " +
                                yearReport.getSumIncomeInMonth() + " доход в годовом отчете\n" +
                                monthReport.getSumIncomeOfMonth() + " доход в месячном отчете");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Вы забыли добавить месячные отчеты за " + inputYear + " год");
            }
        } else {
            System.out.println("Вы забыли добавить годовой отчет за " + inputYear + " год");
        }
    }
}

