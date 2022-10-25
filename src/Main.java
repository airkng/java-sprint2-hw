import java.util.*;
public class Main {
    //Я ДИКО ДИКО извиняюсь за придуманную штуку, мне ОЧЕНЬ, ОЧЕНЬ, ОЧЕНЬ СТЫДНО...
    //Ну реально, условия ТЗ были размытые.
    // TODO: немного подкорректировал и немного поменял логику

    /**
     * Значит по порядку:
     * 1) мой телеграм @airkng ^_^ ахах
     * 2) Моя программа позволяет сохранять отчеты (мес. и годовой) не только за 2021,
     * но и за любой другой год. Хоть за 2019, хоть за 1909.
     * Сохранение ГОДОВЫХ отчетов (Например, за 2010, 2017, 2022 годы) происходит в мапе @YearReportGlobalInfoReportsMap,
     * т.е МАПА - это глобальная информация(СПИСОК, ТАбЛИЦА, не знаю как выразиться) за ВСЕ считанные годы.
     * Мапа является статической по понятным причинам.
     * Ключ --> год , Значение --> экземпляр класса YearReportGlobalInfo. Подробнее этот класс опишу еще раз в документации
     * к этому классу
     *
     * Сохранение МЕСЯЧНЫХ отчетов (Допустим за 2021, 2009, 2022 года) происходит в мапе @MonthReportGlobalInfoMap,
     * т.е МАПА - это глобальная информация месячных отчетов за ВСЕ считанные годы. Аналогично мапа яв-ся статической
     * Ключ --> год , Значение --> экземпляп класса MonthReportGlobalInfo
     *
     * ОБЕ мапы расположены в классе @Report
     *
     * P.S класс MonthReport никак абсолютно не относится ни к YearReport, ни YearReportGlobalInfo
     * ЕСТЬ ДВА ТИПА ОТЧЕТОВ (месячные, годовые), друг с другом они никак не связаны. ВЗАИМОДЕЙСТВИЕ, т.е сравнение этих
     * двух типов отчетов происходит в классе @Report в методе @compareReports
     *
     * Вот логика:
     * YearReportGlobalInfo --> YearReport
     * MonthReportGlobalInfo --> MonthReport
     *
     * Дальше опишу более подробно про каждый класс в документации к каждому классу
     * Начинаем с financeFileReader
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FinanceFileReader financeFileReader = new FinanceFileReader();
        Report report = new Report();

        System.out.println("Здравствуй, пользователь. Это приложение для автоматизации бухгалтерии. \n" +
                "Выберите действие из списка меню :");
        boolean isExit = false;
        while(!isExit){
            printMenu();
            int integerUserInput = scanner.nextInt();
            switch (integerUserInput) {
                case 1: {
                    System.out.println("Введите год, за который вы хотите считать месячные отчеты: ");
                    int year = scanner.nextInt();
                    financeFileReader.readMonthReport(year);
                    break;
                }
                case 2: {

                    System.out.println("Введите год, за который вы хотите считать считать годовой отчет: ");
                    int year = scanner.nextInt();
                    financeFileReader.readYearReport(year);
                    break;
                }
                case 3: {
                    System.out.println("Введите год, за который вы хотите сравить месячный и годовой отчеты");
                    int year = scanner.nextInt();
                    financeFileReader.compareReports(year);
                    break;
                }
                case 4: {
                    //На этом этапе (4 день написания по 15 часов в день) я сижу с лицом лица. Почти завершил проект! (:
                    System.out.println("Введите год, за который вы хотите посмотреть статистику годового отчета: ");
                    int year = scanner.nextInt();
                    report.printYearReportInfo(year);
                    break;
                }
                case 5: {
                    System.out.println("Введите год, за который вы хотите посмотреть статистику месячных отчетов:");
                    int year = scanner.nextInt();
                    report.printMonthReportsInfo(year);
                    break;
                }
                case 0: {
                    isExit = true;
                    break;
                }
                default: {
                    System.out.println("Вы ввели неправильную команду. Повторите попытку.");
                }
            }

        }
        System.out.println("Приложение завершилось! ^_^ ");
    }
    private static void printMenu(){
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о годовом отчёте");
        System.out.println("5 - Вывести информацию о всех месячных отчётах");
        System.out.println("0 - Выйти из приложения");

    }
}

