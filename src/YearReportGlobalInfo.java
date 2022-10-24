import java.util.HashMap;
import java.util.Scanner;

public class YearReportGlobalInfo {
    /**
     * Класс YearReportGlobalInfo хранит в себе мапу, в качестве ключа идет месяц, в качестве значения класс @YearReport.
     * Аналогично мапе в классе Reports, я вынимаю значения у существующей пары ключ-значения, добавляю туда новые данные
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
     * Единственное, на что стоит обратить внимание - условие 89 строки:
     *      "Если сумма является доходом И лист доходов у вынутого из мапы значения не пустой ИЛИ
     *      сумма яв-ся расходом И лист расходов у вынутого из мапы значения не пустой."
     * Тогда я выкидываю метод @chooseAction и дальше уже определяю, что делать с этим
     * см.документацию в YearReport
     */

    private static Integer currentMonth = 0;
    private Integer totalIncome = 0; //Доход за год
    private Integer totalExpense = 0;
    private Integer totalProfit = 0; //Прибыль за год

    private Double averageIncome = 0.0; //Средний доход за год
    private Double averageExpense = 0.0;
    private Integer yearReportsCount = 0; //счетчик считанных месяцев

    HashMap<Integer, YearReport> yearReports = new HashMap<>();

    public static int getCurrentMonth(){
        return currentMonth;
    }
    public Integer getTotalProfit() {
        return totalProfit;
    }
    public Integer getTotalIncome() {
        return totalIncome;
    }
    public Integer getTotalExpense(){
        return totalExpense;
    }
    public  Integer getYearReportsCount() {
        return yearReportsCount;
    }
    private Double getAverageIncome() {
        return averageIncome;
    }
    private Double getAverageExpense() {
        return averageExpense;
    }


    private void setCurrentMonth(Integer currentMonth){
        YearReportGlobalInfo.currentMonth = currentMonth;
    }
    private void setTotalIncome(Integer totalIncome) {
        this.totalIncome = getTotalIncome() + totalIncome;
    }
    private void setTotalExpense(Integer totalExpense) {
        this.totalExpense = getTotalExpense() + totalExpense;
    }
    private void calculateTotalProfit(){
        totalProfit = getTotalIncome() - getTotalExpense();
    }

    private void calculateAverageIncome(){
        averageIncome = Double.valueOf(getTotalIncome()) / yearReportsCount;
    }
    private void calculateAverageExpense(){
        averageExpense = Double.valueOf(getTotalExpense()) / yearReportsCount;
    }

    public void addYearReport(Integer month, Integer amount, boolean isExpense){
        YearReport newYearReport;
        setCurrentMonth(month);
        if(yearReports.containsKey(month)){
            newYearReport = yearReports.get(month);
            if(newYearReport.incomes.size() >= 1 && (!isExpense) || (newYearReport.expenses.size() >= 1 && isExpense)){
                byte userCommand = chooseAction(amount);
                if (userCommand == 1) { //Добавить еще одну сумму за месяц
                    if (isExpense) {
                        newYearReport.addExpense(amount);
                        setTotalExpense(amount);
                    }
                    else {
                        newYearReport.addIncome(amount);
                        setTotalIncome(amount);
                    }
                    yearReports.put(month, newYearReport);
                } else {
                    System.out.println("Сумма " + amount + " пропущена");
                }
            }
            else {
                if (isExpense) {
                    newYearReport.addExpense(amount);
                    setTotalExpense(amount);
                }
                else {
                    newYearReport.addIncome(amount);
                    setTotalIncome(amount);
                }
                yearReports.put(month, newYearReport);

            }
        }
        else{
            yearReportsCount += 1; //счетчик считанных месяцев
            newYearReport = new YearReport();
                if(isExpense) {
                    newYearReport.addExpense(amount);
                    setTotalExpense(amount);
                }
                else{
                    newYearReport.addIncome(amount);
                    setTotalIncome(amount);
                }
            yearReports.put(month, newYearReport);
           }
        calculateTotalProfit();
        calculateAverageIncome();
        calculateAverageExpense();
    }
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
        System.out.println("Прибыль: " + getTotalProfit());
        System.out.println("Общий годовой доход " + getTotalIncome());
        System.out.println("Общий годовой расход " + getTotalExpense());
        System.out.println("Средний месячный доход: " + getAverageIncome());
        System.out.println("Средний месячный расход: " + getAverageExpense());
    }



}
