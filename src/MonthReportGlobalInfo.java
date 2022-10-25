import java.util.ArrayList;

public class MonthReportGlobalInfo {

    public ArrayList<MonthReport> monthReportsList = new ArrayList<>();

    public void addMonthReport(String[] monthInfo, int currentMonth){
        MonthReport monthReport = new MonthReport();
        Integer amount;
        boolean isExpense;
        for (int i = 0; i < monthInfo.length; i++) {
            String line = monthInfo[i];
            String[] valuesFromLine = line.split(",");
            amount = Integer.parseInt(valuesFromLine[2]) * Integer.parseInt(valuesFromLine[3]);
            isExpense = Boolean.parseBoolean(valuesFromLine[1]);
            monthReport.addInfo(valuesFromLine[0], amount, isExpense, currentMonth);
        }
        monthReportsList.add(monthReport);
    }
    public void printMonthReports(){
        for (MonthReport monthReport : monthReportsList) {
            monthReport.printReportInfo();
        }
    }
}
