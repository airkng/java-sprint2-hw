import java.util.HashMap;
import java.util.Scanner;

public class YearReportGlobalInfo {
    /**
     * Старая документация, ничего нового в ней нет. Прикрепил пару комментов к некоторым моментам ниже непосредственно в коде
     * Класс YearReportGlobalInfo хранит в себе мапу, в качестве ключа идет месяц, в качестве значения класс @YearReport.
     * Аналогично статической мапе в классе Reports, я вынимаю значения у существующей пары ключ-значения, добавляю туда новые данные
     * и убираю в мапу.
     * Если у значение мапы не вынимается по ключу, просто создаю новую пару.
     *
     * В классе находится глобальная информация о считанном из годовом отчете:
     * Сум. доход, сум. расход, прибыль, ср. доход, ср расход   за ГОД
     * Поле @yearReportsCount хранит инфу, сколько всего месяцев было считано
     * Поле @currentMonth нужно для отображения месяца, в методе @chooseAction
     *
     * Логика addYear:
     * 1. Смотрим существует ли мапа. Если да, вынимаем оттуда объект
     * 2. Определяем доход/расход
     * 3. Добавляем в соответстующий список
     * 4. Кладем в мапу
     * 5. Считаю значения всех переменных выше
     *
     * Единственное, на что стоит обратить внимание - условие 95 строки:
     *      "Если сумма является доходом И лист доходов у вынутого из мапы значения не пустой ИЛИ
     *      сумма яв-ся расходом И лист расходов у вынутого из мапы значения не пустой."
     * Тогда я выкидываю метод @chooseAction и дальше уже определяю, что делать с этим
     * см.документацию в YearReport
     */

    private static Integer currentMonth = 0;
    //эта статик переменная используется еще в классе @YearReport для отображения добавленной траты за текущий месяц
    // Интересно послушать твои комменты

    // Убрал все поля (Сум.дохода, расхода, среднего доходоа и тд.) за год по твоему совету
    // Я почему их добавил: я подумал, что, если годовой отчет содержит очень много информации:
    // все 12 месяцев с суммами и повторяющиеся месячные траты, то лучше сразу их посчитать, вместо того, чтобы потом перебирать мапу,
    // вынимать оттуда значения, считать каждое поле
    private Integer yearReportsCount = 0; //счетчик считанных месяцев

    HashMap<Integer, YearReport> yearReports = new HashMap<>();

    public static int getCurrentMonth(){
        return currentMonth;
    }
    public Integer getTotalProfit() {
       Integer totalProfit = getTotalIncome() - getTotalExpense();
        return totalProfit;
    }
    public Integer getTotalIncome() {
        Integer totalIncome = 0;
        for (Integer month: yearReports.keySet()) {
            totalIncome += yearReports.get(month).getSumIncomeInMonth();
        }
        return totalIncome;
    }
    public Integer getTotalExpense(){
        Integer totalExpense = 0;
        for (Integer month: yearReports.keySet()) {
            totalExpense += yearReports.get(month).getSumExpenseInMonth();
        }
        return totalExpense;
    }
    public Integer getYearReportsCount() {
        return yearReportsCount;
    }
    private Double getAverageIncome() {
        Double averageIncome = Double.valueOf(getTotalIncome()) / yearReportsCount;
        return averageIncome;
    }
    private Double getAverageExpense() {
        Double averageExpense = Double.valueOf(getTotalExpense()) / yearReportsCount;
        return averageExpense;
    }


    private void setCurrentMonth(Integer currentMonth){
        YearReportGlobalInfo.currentMonth = currentMonth;
    }


    public void addYearReport(Integer month, Integer amount, boolean isExpense) {
        YearReport newYearReport;
        setCurrentMonth(month);
        //Как раз-таки я и сделал эту реализацию с учетом повторных месячных трат в годовом отчете
        //следующее условие if - (newYearReport.getIncomes.size() >= 1 ...) и находит эту повторную трату и потом
        //выкидывает выбор пользователю че с ней делать
        if (yearReports.containsKey(month)) {
            newYearReport = yearReports.get(month);
            if (newYearReport.getIncomes().size() >= 1 && (!isExpense) || (newYearReport.getExpenses().size() >= 1 && isExpense)) {
                byte userCommand = chooseAction(amount);
                if (userCommand == 1) { //Добавить еще одну сумму за месяц
                    if (isExpense) {
                        newYearReport.addExpense(amount);
                    } else {
                        newYearReport.addIncome(amount);
                    }
                yearReports.put(month, newYearReport);
                } //иначе пропускаем значение
                else {
                    System.out.println("Сумма " + amount + " пропущена");
                }
            } else {
                if (isExpense) {
                    newYearReport.addExpense(amount);
                } else {
                    newYearReport.addIncome(amount);
                }
                yearReports.put(month, newYearReport);

            }
        } else {
            yearReportsCount += 1; //счетчик считанных месяцев
            newYearReport = new YearReport();
            if (isExpense) {
                newYearReport.addExpense(amount);
            } else {
                newYearReport.addIncome(amount);
            }
            yearReports.put(month, newYearReport);
        }
    }
    //Byte сделал чтобы памяти меньше жрал
    private byte chooseAction(Integer amount){
        Scanner scanner = new Scanner(System.in);
        System.out.println("В месяце " + getCurrentMonth() + " годового отчета обнаружена новая сумма: " + amount + "\nВыберите действие: ");
        System.out.println("1 - добавить в месяц");
        System.out.println("2 - пропустить");

        byte command = 0;
        boolean isCorrectInput = true;

        while (isCorrectInput){
            String inputInfo = scanner.nextLine();
            if(inputInfo.equalsIgnoreCase("1")){
                command = Byte.parseByte(inputInfo);
                isCorrectInput = false;
            }
            else if(inputInfo.equalsIgnoreCase("2")){
                command = Byte.parseByte(inputInfo);
                isCorrectInput = false;
            }
            else{
                System.out.println("Введено некорректное значение. Попробуйте еще раз");
            }
        }
        return command;
    }

    public void printYearReport() {
        System.out.println("В отчете содержались " + getYearReportsCount() + " месяцев");
        System.out.println("Общий годовой доход " + getTotalIncome());
        System.out.println("Общий годовой расход " + getTotalExpense());
        System.out.println("Прибыль: " + getTotalProfit());
        System.out.println("Средний месячный доход: " + getAverageIncome());
        System.out.println("Средний месячный расход: " + getAverageExpense());
        System.out.println();
    }



}
