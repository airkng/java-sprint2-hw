import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class FinanceFileReader {
    //Счетчик проведенных дней за проектом: 4
    private Integer inputYear;
    private String relativePath;


    private static HashMap<Integer, Report> reportsMap = new HashMap<>(); //Мапа, в которой содержатся годовые и месячные отчеты (Ключ - год)

    private ArrayList<Integer> inputDataYearReports = new ArrayList<>(); //Лист ранее считанных годовых отчетов
    private ArrayList<Integer> inputDataMonthReports = new ArrayList<>(); //Лист ранее считанных месячных отчетов

    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public void readYearReport(int year) {
        if (!isInputYearReportExist(year)) {
            relativePath = "resources/y." + year + ".csv";
            String yearFile = readFileContentsOrNull(relativePath);
            setYearReportToObjects(yearFile);
        }
    }

    private boolean isInputYearReportExist(int inputYear) {
        this.inputYear = inputYear;
        if (inputDataYearReports.contains(inputYear)) {
            System.out.println("Годовой отчет за " + inputYear + " год уже существует");
            return true;
        } else {
            inputDataYearReports.add(inputYear);
            return false;
        }
    }

    private void setYearReportToObjects(String yearFile) {
        Report report = new Report();
        if(reportsMap.size() > 0 && reportsMap.containsKey(inputYear)) {
            report = reportsMap.get(inputYear);
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

                report.addYearReport(inputYear, monthNumber, amount, isExpense);
                reportsMap.put(inputYear, report);

            } else {
                System.out.println("Данные могут записаны без учета данной строки. Записать?(Да/Нет)");
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();
                if (s.equalsIgnoreCase("Да")) {

                } else {
                    //Возможно, в методе ниже будет баг, когда у нас есть месячный отчет, он удалит весь(и годовой и месячный отчеты)
                    //Бага не обнаружено (:
                    report.deleteYearReport(inputYear);
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
        //Достаточная простенькая проверочка, насколько ты видишь, хз что еще придумать со своими знанниями
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
        if (!isInputMonthReportExist(year)) {
            for (int i = 1; i < 4; i++) {
                relativePath = "resources/m." + year + "0" + i + ".csv";
                String monthFile = readFileContentsOrNull(relativePath);
                setMonthReportToObjects(monthFile, i);
            }

        }

    }

    private boolean isInputMonthReportExist(int inputYear) {
        this.inputYear = inputYear;
        if (inputDataMonthReports.contains(inputYear)) {
            System.out.println("Месячные отчеты за " + inputYear + " год уже существуют");
            return true;
        } else {
            inputDataMonthReports.add(inputYear);
            return false;
        }
    }

    private void setMonthReportToObjects(String monthFile, int currentMonth) {
        Report report = new Report();
        if(reportsMap.size() > 0 && reportsMap.containsKey(inputYear)) {
            report = reportsMap.get(inputYear);
        }
        String[] linesFromReport = monthFile.split("\n");
        String[] correctMonthReport = new String[linesFromReport.length];

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
        report.addMonthReport(correctMonthReport, inputYear, currentMonth);
        reportsMap.put(inputYear, report);
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
        if(reportsMap.size() > 0 && reportsMap.containsKey(inputYear)){
            Report report = reportsMap.get(inputYear);
            report.compareReports(inputYear);
        }
        else{
            System.out.println("Отсутствуют годовые и месячные отчеты за данный год");
        }
    }
}