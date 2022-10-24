import java.util.ArrayList;

public class YearReport {
    /**
     * Класс YearReport при считывании годового отчета является стартовой единицей. В нем я создал List доходов(incomes)
     * и расходов(expenses), а также суммарные доходы и расходы за месяц (поля: sumIncomeInMonth и sumExpenseInMonth)
     * Как видишь, они запривачены, в них значения добавляются в зависимости от методов addIncome или addExpense.
     * Т.е. на вход прилетают данные в один из двух методов.
     * Если доход(addIncome), то с помощью сеттера считаем суммарный доход (поле SumIncomeInMonth), если расход - аналогично.
     * (поле SumExpenseMonth);
     * + добавляем в список
     *
     * Для чего я делал список: 1) я подумал, вдруг отчет оформлен некорректно и, допустим, там есть записи что-то вроде:
     *
     * 01,4874843,true
     * 01,82773,false
     * 02,983874,true
     * 01,3287823,true
     *
     * Как видишь, в первой строчке и последней введен один и тот же месяц, сумма и доход
     * т.е за один месяц два дохода, я решил, что лучше оставить выбор пользователю, что делать с этой суммой
     * 1.Добавить
     * 2.Пропустить
     * Логику выбора пользователя прописал в YearReportGlobalInfo
     *                         2) при желании можно вынуть значения из этого списка.
     */
    private int sumIncomeInMonth;
    private int sumExpenseInMonth;

    ArrayList<Integer> incomes = new ArrayList<>();
    ArrayList<Integer> expenses = new ArrayList<>();

    private void setSumExpenseInMonth(int sumExpenseInMonth) {
        this.sumExpenseInMonth += sumExpenseInMonth;
    }
    public int getSumExpenseInMonth() {
        return sumExpenseInMonth;
    }

    private void setSumIncomeInMonth(int sumIncomeInMonth) {
        this.sumIncomeInMonth += sumIncomeInMonth;
    }
    public int getSumIncomeInMonth() {
        return sumIncomeInMonth;
    }

    public void addExpense(Integer amount) {
        expenses.add(amount);
        setSumExpenseInMonth(amount);
        System.out.println("Добавлена сумма " + amount +
                " за " + YearReportGlobalInfo.getCurrentMonth() + " месяц. Сумма расхода : " + getSumExpenseInMonth());
        System.out.println(expenses + " - список расходов за месяц");
        System.out.println();
    }

    public void addIncome(Integer amount) {
        incomes.add(amount);
        setSumIncomeInMonth(amount);
        System.out.println("Добавлена сумма " + amount + " за " + YearReportGlobalInfo.getCurrentMonth() + " месяц. Сумма дохода: " + getSumIncomeInMonth());
        System.out.println(incomes + " - список доходов за месяц");
        System.out.println();
    }


}
