import java.util.HashMap;
import java.util.Scanner;

/**
 * Короче тут я уже устал писать документацию, в двух словах. 1) Понял, что переменные лучше не хранить в полях, а считать
 * когда будут нужны.
 * В мапах ниже хранятся значения доходов и расходов. Почему мапа? А вдруг повторная трата, которая уже есть в этом списке:
 * Коньки,TRUE,50,2000
 * Новогодняя ёлка,TRUE,1,100000
 * Коньки, true, 100, 100 000
 *
 * Повторный расход как видишь. В методах этого класса, я ловлю эти исключения, предоставляя выбор пользователю,че с ней делать
 * Короче, *бал я в р*т эти исключения, ты бы знал, сколько я листов исписал, чтобы логику этой еб*тории прописать, аахха
 *
 * Еще раз, этот класс относится ТОЛЬКО к МЕСЯЧНОМУ ОТЧЕТУ при считывании месячных отчетов. Класс содержит информацию ОДНОГО месяца
 * его расходы, доходы, самую прибыльную вещь месяца, самый дорогой расход месяца.
 *
 * А класс MonthReportGlobalInfo сожержит лист экземпляров класса MonthReport, потому что я точно уверен, что месяцы у меня точно идут
 * по порядку из-за их наименования в папке resources (m.202101.csv). То есть это точно Январь. Однако я все равно на всякий случай
 * тащу переменную month аж до этого класса, чтобы наверняка быть уверенным, что это месяц январь, и тут проще сделать помесячный вывод
 * информации (по требованию ТЗ яндекс Практикума)
 */
public class MonthReport {
    private int sumIncomeOfMonth;
    private int sumExpenseOfMonth;
    private int currentMonth;
    private int maxIncomeValue;
    private int maxExpenseValue;
    private String maxIncomeCategory;
    private String maxExpenseCategory;

    HashMap<String, Integer> incomes = new HashMap<>();
    HashMap<String, Integer> expenses = new HashMap<>();

    private void setSumExpenseOfMonth(int sumExpenseOfMonth){
        this.sumExpenseOfMonth += sumExpenseOfMonth;
    }
    private void setSumIncomeOfMonth(int sumIncomeOfMonth) {
        this.sumIncomeOfMonth += sumIncomeOfMonth;
    }
    private void setCurrentMonth(int currentMonth){
        this.currentMonth = currentMonth;
    }
    private void setMaxIncomeValue(int maxIncomeValue) {
        this.maxIncomeValue = maxIncomeValue;
    }
    private void setMaxExpenseValue(int maxExpenseValue) {
        this.maxExpenseValue = maxExpenseValue;
    }
    private void setMaxExpenseCategory(String maxExpenseCategory) {
        this.maxExpenseCategory = maxExpenseCategory;
    }
    private void setMaxIncomeCategory(String maxIncomeCategory) {
        this.maxIncomeCategory = maxIncomeCategory;
    }

    public int getSumExpenseOfMonth(){
        return sumExpenseOfMonth;
    }
    public int getSumIncomeOfMonth() {
        return sumIncomeOfMonth;
    }
    public int getCurrentMonth(){
        return currentMonth;
    }
    public int getMaxIncomeValue() {
        return maxIncomeValue;
    }
    public int getMaxExpenseValue() {
        return maxExpenseValue;
    }
    public String getMaxIncomeCategory() {
        return maxIncomeCategory;
    }
    public String getMaxExpenseCategory() {
        return maxExpenseCategory;
    }


    public void addInfo(String item, Integer amount, boolean isExpense, Integer currentMonth) {
        setCurrentMonth(currentMonth);
        if (isExpense) {
            addExpense(item, amount);
        } else {
            addIncome(item, amount);
        }
    }

    private void addExpense(String item, Integer expense) {
        int expensesSize = expenses.size();
        boolean containsKey = expenses.containsKey(item);
        if(expensesSize > 0 && containsKey){
            int inputInfo = chooseAction(item, expense);
            if(inputInfo == 1){
                expenses.remove(item);
                expenses.put(item, expense);
                calculateExpense(); // пересчитывает суммарный расход за месяц
            }
            else if(inputInfo == 2){
                expenses.put(item, expenses.get(item) + expense);
                setSumExpenseOfMonth(expense);
            }
            else if(inputInfo == 3){
                expenses.remove(item);
                calculateExpense(); //пересчитывает суммарый расход за месяц
                System.out.println("Категория расходов " + item + " удалена");

            }
            else {
                System.out.println("Категория " + item +" расход " + expense + " пропущена");

            }

        }
        else{
            expenses.put(item, expense);
            setSumExpenseOfMonth(expense);
        }
    }

    private void addIncome(String item, Integer income) {
        int incomesSize = incomes.size();
        boolean containsKey = incomes.containsKey(item);
        if(incomesSize > 0 && containsKey){
            int inputInfo = chooseAction(item,income);
            if(inputInfo == 1){
                //incomes.remove(item);
                incomes.replace(item, income);
                calculateIncome();
            }
            else if(inputInfo == 2){
                incomes.put(item, incomes.get(item) + income);
                setSumIncomeOfMonth(income);
            }
            else if(inputInfo == 3){
                incomes.remove(item);
                calculateIncome();
                System.out.println("Категория доходов " + item + " удалена");
            }
            else{
                System.out.println("Категория " + item + " доход " + income + " пропущена");
            }
        }
        else {
            incomes.put(item, income);
            setSumIncomeOfMonth(income);
        }
    }
    private int chooseAction(String item, Integer amount){
        Scanner scanner = new Scanner(System.in);
        System.out.println("в месяце " + getCurrentMonth() + " обнаружено совпадение по наименованию и категории трат(Расход/Доход)" +
                "\nВыберите действие: ");
        System.out.println("1 - заменить на текущую категорию: " + item + " " + amount);
        System.out.println("2 - сохранить все значения");
        System.out.println("3 - удалить все траты категории " + item);
        System.out.println("4 - пропустить");
        int command = 0;
        boolean isCorrectInput = true;
        while (isCorrectInput){
            String inputInfo = scanner.nextLine();
            if(inputInfo.equalsIgnoreCase("1")){
                command = Integer.parseInt(inputInfo);
                isCorrectInput = false;
            }
            else if(inputInfo.equalsIgnoreCase("2")){
                command = Integer.parseInt(inputInfo);
                isCorrectInput = false;
            }
            else if(inputInfo.equalsIgnoreCase("3")) {
                command = Integer.parseInt(inputInfo);
                isCorrectInput = false;
            }
            else if(inputInfo.equalsIgnoreCase("4")){
                command = Integer.parseInt(inputInfo);
                isCorrectInput = false;
            }
            else{
                System.out.println("Введено некорректное значение. Попробуйте еще раз");
            }
        }
        return command;
    }
    private void calculateExpense(){
        sumExpenseOfMonth = 0;
        for(String key : expenses.keySet()){
            setSumExpenseOfMonth(expenses.get(key));
        }
    }
    private void calculateIncome(){
        sumIncomeOfMonth = 0;
        System.out.println("Значение sumIncomeMonth в классе MonthReport " + getSumIncomeOfMonth());
        for(String key : incomes.keySet()){
            setSumIncomeOfMonth(incomes.get(key));
        }
    }

    private void findMaxExpenseValue(){
        if(expenses.size() > 0){
            for (String category: expenses.keySet()) {
                if(getMaxExpenseValue() < expenses.get(category)){
                    setMaxExpenseValue(expenses.get(category));
                    setMaxExpenseCategory(category);
                }
            }
        }
    }
    private void findMaxIncomeValue(){
        if(incomes.size() > 0){
            for (String category: incomes.keySet()) {
                if(getMaxIncomeValue() < incomes.get(category)){
                    setMaxIncomeValue(incomes.get(category));
                    setMaxIncomeCategory(category);
                }
            }
        }
    }
    public void printReportInfo(){
        findMaxExpenseValue();
        findMaxIncomeValue();
        String month = getStringMonth();
        System.out.println("Отчет за " + month + " месяц.");
        System.out.println("Расходы: " + getSumExpenseOfMonth());
        System.out.println("Доходы: " + getSumIncomeOfMonth());
        System.out.println("Наибольший доход составила категория " + getMaxIncomeCategory() + " " + getMaxIncomeValue());
        System.out.println("Наибольший расход составила категория " + getMaxExpenseCategory() + " " + getMaxExpenseValue());
        System.out.println();
    }
    //убрал шутку и убрал break, не знал, что так можно было. Спасибо!
    private String getStringMonth() {
        switch (getCurrentMonth()) {
            case 1:
                return "Январь";
            case 2:
                return "Февраль";
            case 3:
                return "Март";
            case 4:
                return "Апрель";
            case 5:
                return "Май";
            case 6:
                return "Июнь";
            case 7:
                return "Июль";
            case 8:
                return "Август";
            case 9:
                return "Сентябрь";
            case 10:
                return "Октябрь";
            case 11:
                return "Ноябрь";
            case 12:
                return "Декабрь";
            default:
                return "Уберем эту шутку";
        }
    }
}
