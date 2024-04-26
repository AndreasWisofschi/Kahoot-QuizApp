package wahoot.client.ui.view;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.db.models.Student;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChartView extends ResizeableFXMLView<StackPane> {

    /**
     * View represents a graph of student progress over past week
     */
    @FXML
    private BarChart<String, Number> barchart;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;


    /**
     * Constructs a ChartView object for a given student
     * @param student student the chart view is generated for
     */
    public ChartView(Student student) {

        List<Student.Observation> observations = student.getObservations();

        DateTimeFormatter format = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = now.minusDays(7);


        LocalDateTime finalThen1 = then;

        List<Student.Observation> last7days = student.getObservations().stream().filter((x)-> (x.getDate().equals(Date.valueOf(LocalDate.now()))) || x.getDate().before(Date.valueOf(LocalDate.now())) && x.getDate().after(Date.valueOf(finalThen1.toLocalDate()))).toList();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

        int i = 0;

        while(then.isBefore(now) || then.isEqual(now)) {
            then = then.plusDays(1);

            LocalDateTime finalThen = then;

            long count = last7days.stream().filter((x) -> fmt.format(x.getDate()).equals(fmt.format(java.sql.Date.valueOf(finalThen.toLocalDate())))).count();

            double points = last7days.stream().filter((x) -> fmt.format(x.getDate()).equals(fmt.format(java.sql.Date.valueOf(finalThen.toLocalDate())))).map(Student.Observation::getScore).reduce((double) 0, Double::sum);

            XYChart.Series series1 = new XYChart.Series();

            DayOfWeek doy = then.getDayOfWeek();

            String name = doy.getDisplayName(TextStyle.FULL, Locale.CANADA);



            series1.getData().add(new XYChart.Data(name, (int) points));

            barchart.getData().add(series1);

            i++;

        }


    }
}
