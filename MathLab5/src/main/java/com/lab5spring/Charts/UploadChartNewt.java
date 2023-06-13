package com.lab5spring.Charts;

import com.lab5spring.Form;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.OutputStream;

import static org.jfree.chart.ChartUtils.writeChartAsJPEG;

@Controller
public class UploadChartNewt {

    public static double[][] points;
    public static double x;
    public static Double result;
    public static int number;
    public static double[][] newtonPoints;

    @GetMapping("/chartnewt")
    public void handleChart(HttpServletResponse response, Form form, Model model) throws IOException {
        response.setContentType("image/jpeg");
        model.addAttribute("form", form);
        OutputStream out = response.getOutputStream();
        Chart drawChart = new Chart();
        writeChartAsJPEG(out, drawChart.drawChart(points, x, result, number, newtonPoints, "Newton"), 600, 600);
    }
}
