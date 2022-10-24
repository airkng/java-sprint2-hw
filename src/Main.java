import java.util.*;
public class Main {
    //Это я в начале работы решил запилить тебе шуточку:
    //Программа компилируется с третьего раза : Разработчик - "Господь Всевышний смиловался надо мною"
    //Программа компилируется со второго раза : Разработчик - "Бог умер. Теперь я есть Бог"
    //Программа компилируется с первого раза : Разработчик  -
    // ...судорожно пытается найти ошибку в дебаге. "Похоже я сломал весь проект"

    //TODO: написал подобие документации, советую почитать. Документацию для месячных отчетов не писал, там по аналогии. Начать лучше с YearReport, чтобы понять логику
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FinanceFileReader financeFileReader = new FinanceFileReader();
        Report report = new Report();

        System.out.println("Добрый день, пользователь. Это приложение для автоматизации бухгалтерии. \n" +
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
                    //Два дня спустя мне уже не до шуток... Глаза ЧООРНЫЕ ПЫПЭЦ ПРОСТО
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

