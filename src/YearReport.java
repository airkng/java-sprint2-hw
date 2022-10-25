import java.util.ArrayList;
public class YearReport {
    /**
     * Класс YearReport при считывании ГОДОВОГО отчета является стартовой единицей. В нем я создал List доходов(incomes)
     * и расходов(expenses), а также суммарные доходы и расходы ЗА МЕСЯЦ. (поля: sumIncomeInMonth и sumExpenseInMonth)
     * <p>
     * Да-да за месяц. Класс @YearReport хранит СПИСОК
     * ДОХОДОВ и СПИСОК РАСХОДОВ за МЕСЯЦ. Потому что в классе @YearReportGlobalInfo существует мапа  @yearReports, которая
     * хранит ключ --> месяц, значение --> класс @YearReport
     * <p>
     * Как видишь, они запривачены, в них значения добавляются в зависимости от методов addIncome или addExpense.
     * Т.е. на вход прилетают данные в один из двух методов.
     * Если доход(addIncome), то с помощью сеттера считаем суммарный доход (поле SumIncomeInMonth), если расход - аналогично.
     * (поле SumExpenseMonth);
     * + добавляем в список
     * <p>
     * Для чего я делал список:
     *      1) я подумал, вдруг отчет оформлен некорректно и, допустим, там есть записи что-то вроде:
     * <p>
     * 01,4874843,true
     * 01,82773,false
     * 02,983874,true
     * 01,3287823,true
     * <p>
     * Как видишь, в первой строчке и последней введен один и тот же месяц, сумма и доход
     * т.е за один месяц два дохода, я решил, что лучше оставить выбор пользователю, что делать с этой суммой
     * 1.Добавить
     * 2.Пропустить
     * Логику выбора пользователя прописал в YearReportGlobalInfo
     *      2) при желании можно вынуть значения из этого списка.
     *  Переходим в @YearGlobalInfo
     */
    private int sumIncomeInMonth;
    private int sumExpenseInMonth;

    private ArrayList<Integer> incomes = new ArrayList<>();
    private ArrayList<Integer> expenses = new ArrayList<>();

    //TODO: теперь заприватил листы и создал геттеры
    public ArrayList<Integer> getIncomes() {
        return incomes;
    }

    public ArrayList<Integer> getExpenses() {
        return expenses;
    }

    private void increaseSumExpenseInMonth(int sumExpenseInMonth) {
        this.sumExpenseInMonth += sumExpenseInMonth;
    }

    public int getSumExpenseInMonth() {
        return sumExpenseInMonth;
    }

    private void increaseSumIncomeInMonth(int sumIncomeInMonth) {
        this.sumIncomeInMonth += sumIncomeInMonth;
    }

    public int getSumIncomeInMonth() {
        return sumIncomeInMonth;
    }

    public void addExpense(Integer amount) {
        expenses.add(amount);
        increaseSumExpenseInMonth(amount);
        System.out.println("Добавлена сумма " + amount +
                " за " + YearReportGlobalInfo.getCurrentMonth() + " месяц. Сумма расхода : " + getSumExpenseInMonth());
        System.out.println(expenses + " - список расходов за месяц");
        System.out.println();
    }

    public void addIncome(Integer amount) {
        incomes.add(amount);
        increaseSumIncomeInMonth(amount);
        System.out.println("Добавлена сумма " + amount + " за " + YearReportGlobalInfo.getCurrentMonth() + " месяц. Сумма дохода: " + getSumIncomeInMonth());
        System.out.println(incomes + " - список доходов за месяц");
        System.out.println();
    }
}
