import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class FinanceFileReader {
    //Счетчик проведенных дней за проектом: 5
    private Integer inputYear;
    private String relativePath;
    //Удалил ненужную мапу, сейчас должно стать понятнее

    // По твоему совету убрал листы. Совет супер!
    // Ниже оставил комменты

    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public void readYearReport(int year) {
        this.inputYear = year;
        //Проверка на то, считывали ли мы отчет ранее
        if (!Report.globalInfoYearReportsMap.containsKey(inputYear)) {
            relativePath = "resources/y." + year + ".csv";
            String yearFile = readFileContentsOrNull(relativePath);
            setYearReportToObjects(yearFile);
        }
        else {
            System.out.println("Годовой отчет за " + inputYear + " год уже существует");
        }
    }

    /** Логика метода ниже отличается от логики метода @setMonthReportToObjects
     * Здесь он читает и преобразовывает построчно И отправляет в ОДНУ считанную строчку в
     * @yearReportGlobalInfo.addYearReport. То есть это строчка, которая по сути содержит
     * месяц-сумму-указание дохода/расхода идет аж до самого класса @YearReport. Поле месяц
     * отпадает на уровне класса @YearGlobalInfo, т.к там есть мапа месяц --> класс YearReport,
     * а поля "сумма" И "указание дохода/расхода" доходят до @YearReport и заносятся в лист Доходов/Расходов
     * ЗА МЕСЯЦ. Сейчас идем и читаем документацию в класс YearReport.
     *
     * В методе же @setMonthReportToObjects считывается ОДНА строчка, проходит проверки,
     * корректируется (.trim() .replaceAll("\\s+", "") и снова засовывается в другой массив
     * уже КОРРЕКТНЫХ строк. И только после отправляет в метод @monthReportGlobalInfo.addMonthReport скорректированных
     * строк. Далее данные в методе @monthReportGlobalInfo.addMonthReport уже сплитуются по запятой.
     * Еще раз, туда прилетает массив строк типа:
     * String[] correcMonthReport =
     * [0] - [Коньки,true,50,2000]
     * [1] - [Новогодняя ёлка,true,1,100000]
     * [2] - [Ларёк с кофе,true,3,50000]
     * ...
     * Далее сплитуются и построчо идут в метод @addInfo класса MonthReport
     *
     * На этом я все, отправляю проект тебе еще раз :)
     */
    private void setYearReportToObjects(String yearFile) {
        YearReportGlobalInfo yearReportGlobalInfo = new YearReportGlobalInfo();
        if(Report.globalInfoYearReportsMap.containsKey(inputYear)) {
            yearReportGlobalInfo = Report.globalInfoYearReportsMap.get(inputYear);
        }
        String[] linesFromReport = yearFile.split("\n");
        String[] reportsVariables;

        Integer monthNumber;
        Integer amount;
        boolean isExpense;
        for (int i = 1; i < linesFromReport.length; i++) {
            String line = linesFromReport[i];
            reportsVariables = line.split(",");
            if (isCorrectVariablesFromYearReport(reportsVariables[0], reportsVariables[1], reportsVariables[2], i)) {

                reportsVariables[0] = reportsVariables[0].trim(); //месяц
                reportsVariables[1] = reportsVariables[1].replaceAll("\\s+",""); //значение
                reportsVariables[2] = reportsVariables[2].trim().toLowerCase(); // true/false

                monthNumber = Integer.parseInt(reportsVariables[0]);
                amount = Integer.parseInt(reportsVariables[1]);
                isExpense = Boolean.parseBoolean(reportsVariables[2]);

                yearReportGlobalInfo.addYearReport(monthNumber, amount, isExpense);
                Report.globalInfoYearReportsMap.put(inputYear, yearReportGlobalInfo);

            } else {
                System.out.println("Данные могут записаны без учета данной строки. Записать?(Да/Нет)");
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();
                if (s.equalsIgnoreCase("Да")) {

                } else {
                    //Возможно, в методе ниже будет баг, когда у нас есть месячный отчет, он удалит весь(и годовой и месячный отчеты)
                    //Бага не обнаружено (:
                    Report.deleteYearReport(inputYear);
                    break;
                }
            }
        }
    }

    private boolean isCorrectVariablesFromYearReport(String month, String amount, String isExpense, int i) {
        if (isExpense.trim().equalsIgnoreCase("false") || isExpense.trim().equalsIgnoreCase("true")) {
            if (isCorrectMonth(month)) {
                if (isCorrectValue(amount)) {
                    return true;
                } else {
                    System.out.println("В строке " + (i + 1) + " файла " + relativePath +
                            " допущена ошибка. Некорректно введена сумма во втором столбце");
                    return false;
                }
            } else {
                System.out.println("В строке " + (i + 1) + " файла " + relativePath +
                        " допущена ошибка. Некорректно введен месяц");

                return false;
            }
        } else {
            System.out.println("В строке " + (i + 1) + " файла " + relativePath +
                    " допущена ошибка.\n" +
                    "Возможно, некорректно введено указание траты/дохода");
            return false;
        }
    }

    private static boolean isCorrectMonth(String month) {
        String[] correctValues = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        boolean isCorrect = false;
        for (int i = 0; i < correctValues.length; i++) {
            if (correctValues[i].equals(month.trim())) {
                isCorrect = true;
            }
        }
        return isCorrect;
    }

    private static boolean isCorrectValue(String value) {
        //Достаточная простенькая проверочка, насколько ты видишь, хз что еще придумать со своими знаниями
        //Можно конечно сделать условие, чтобы заканчивалась на цифры
        value = value.replaceAll("\\s+","");
        if (value.startsWith("-")) {
            System.out.println("Введено отрицательное значение");
            return false;
        } else if (value.startsWith("0")) {
            System.out.println("Введена некорректное значение(Начинается с 0");
            return false;
        }
        else if(value.equals("")){
            System.out.println("Введено пустое значение");
            return false;
        }
        else return true;
    }

    public void readMonthReport(int year) {
        //проверка на то, считывали ли отчет ранее
        this.inputYear = year;
        if (!Report.globalInfoMonthReportsMap.containsKey(inputYear)) {
            for (int i = 1; i < 4; i++) {
                relativePath = "resources/m." + year + "0" + i + ".csv";
                String monthFile = readFileContentsOrNull(relativePath);
                setMonthReportToObjects(monthFile, i);
            }

        }
        else{
            System.out.println("Месячный отчет за " + inputYear + " год уже существует");
        }

    }

    private void setMonthReportToObjects(String monthFile, int currentMonth) {
        MonthReportGlobalInfo monthReportGlobalInfo = new MonthReportGlobalInfo();
        if(Report.globalInfoMonthReportsMap.containsKey(inputYear)) {
            monthReportGlobalInfo = Report.globalInfoMonthReportsMap.get(inputYear);
        }
        String[] linesFromReport = monthFile.split("\n");
        String[] correctMonthReport = new String[linesFromReport.length - 1]; //не забываем, что 0 строчка у linesFromReport ненужная

        for (int i = 1; i < linesFromReport.length; i++) {
            String line = linesFromReport[i];
            String[] monthVariables = line.split(",");
            if (isCorrectVariablesFromMonthReport(monthVariables[0], monthVariables[1], monthVariables[2], monthVariables[3],i)) {

                monthVariables[0] = monthVariables[0].trim();   //Item
                monthVariables[1] = monthVariables[1].trim().toLowerCase(); //  true/false
                monthVariables[2] = monthVariables[2].replaceAll("\\s+", ""); //quantity
                monthVariables[3] = monthVariables[3].replaceAll("\\s+", ""); // sumOfOne

                correctMonthReport[i-1] = monthVariables[0] + "," + monthVariables[1] + "," + monthVariables[2] + "," + monthVariables[3];

            }
            else {
                System.out.println("Отчет оформлен некорректно. Переделайте отчет, исправив указанные ошибки");
                return;
            }
        }
        monthReportGlobalInfo.addMonthReport(correctMonthReport, currentMonth);
        Report.globalInfoMonthReportsMap.put(inputYear,monthReportGlobalInfo);
    }
    private boolean isCorrectVariablesFromMonthReport(String item, String isExpense, String quantity, String sumOfOne, int i){
        if(isExpense.trim().equalsIgnoreCase("false") || isExpense.trim().equalsIgnoreCase("true")){
            if(isCorrectItem(item)){
                if(isCorrectValue(quantity) && isCorrectValue(sumOfOne)) {
                    return true;
                }
                else{
                    System.out.println("В строке " + (i+1) + " файла " + relativePath +
                            " допущена ошибка. Некорректно введена трата/доход");
                    return false;
                }
            }
            else{
                System.out.println("В строке " + (i+1) + " файла " + relativePath +
                        " допущена ошибка. Некорректно введено наименование");

                return false;
            }
        }
        else{
            System.out.println("В строке " + (i+1) + " файла " + relativePath +
                    " допущена ошибка.\n" +
                    "Возможно, некорректно введены наимнование товара или его указание (трата/доход)");
            return false;
        }
    }
    private boolean isCorrectItem(String item){
        String[] notCorrectValues = {"0", "2", "3", "4", "5", "6", "7", "8", "9", "1"};
        boolean isNotCorrectBegin = false;
        boolean isEmpty = false;
        for (int i = 0; i < notCorrectValues.length; i++) {
            if(item.startsWith(notCorrectValues[i])){
                isNotCorrectBegin = true;
            }
        }
        if(item.equals("") || item == null || item.equals(null)){
            isEmpty = true;
        }
        return !(isEmpty || isNotCorrectBegin);
    }

    public void compareReports(int inputYear){
        if(Report.globalInfoMonthReportsMap.containsKey(inputYear) || Report.globalInfoYearReportsMap.containsKey(inputYear)) {
            Report.compareReports(inputYear);
        }
        else{
            System.out.println("За " + inputYear + " год не были считаны месячные и годовые отчеты");
            System.out.println();
        }
    }
}