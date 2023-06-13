package com.lab5spring;

import com.lab5spring.Charts.UploadChart;
import com.lab5spring.Charts.UploadChartNewt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class IOData {

    @GetMapping("/")
    public String welcomePage() {
        return "welcomePage";
    }

    @GetMapping("/data")
    public String input(Model model) {
        model.addAttribute("form", new Form());
        return "index";
    }

    @GetMapping("/func")
    public String function(Model model) {
        model.addAttribute("form", new Form());
        return "functions";
    }

    @GetMapping("/file")
    public String file(Model model) {
        model.addAttribute("file", new File());
        return "file";
    }


    @GetMapping("/resultTable")
    public String outTable(@ModelAttribute Form form, Model model) {
        Function function = new Function();
        double[][] points = function.convertToArray(form.getInputData());
        LagrangeMethod lagrangeMethod = new LagrangeMethod();
        NewtonMethod newtonMethod = new NewtonMethod();
        newtonMethod.getCountValue(points, form.getX());
        Double result = 0.0;
        UploadChart.points = points;
        UploadChart.x = form.getX();
        UploadChart.number = 0;

        UploadChartNewt.points = points;
        UploadChartNewt.x = form.getX();
        UploadChartNewt.number = 0;

        result = newtonMethod.getCountValue(points, form.getX());
        model.addAttribute("result", result);
        model.addAttribute("resultTable", lagrangeMethod.interpolation(points));
        UploadChartNewt.result = result;

        result = lagrangeMethod.L(points, form.getX());
        model.addAttribute("resultNewt", result);
        model.addAttribute("resultTableNewt", newtonMethod.interpolation(points));
        UploadChart.result = result;

        //UploadChartNewt.newtonPoints = newtonMethod.interpolation(points); // СТРАЯ ХУЕТА
        UploadChartNewt.newtonPoints = newtonMethod.interpolatePoints(points); // НОвая ХУИТА
       // UploadChart.lagrangePoints = lagrangeMethod.interpolation(points); // Добавлено
        UploadChart.lagrangePoints = lagrangeMethod.Lpoint(points); // НОвая ХУИТА

        return "resultTable";
    }

    @GetMapping("/resultFunc")
    public String outFunc(@ModelAttribute Form form, Model model) {
        Function function = new Function();
        LagrangeMethod lagrangeMethod = new LagrangeMethod();
        NewtonMethod newtonMethod = new NewtonMethod();
        double[][] points = function.calculateFunc(form.getInputData(), form.getNumberOfFunction());
        double result = 0;
        UploadChart.points = points;
        UploadChart.x = form.getX();
        UploadChart.number = form.getNumberOfFunction();
        if(form.getNumberOfFunction()==1){
            if(form.getInputData().get(0)<0) {
                return "error";
            }

        }
        UploadChartNewt.points = points;
        UploadChartNewt.x = form.getX();
        UploadChartNewt.number = form.getNumberOfFunction();

        result = lagrangeMethod.L(points, form.getX());
        model.addAttribute("resultFunc", result);
        model.addAttribute("resultTable", lagrangeMethod.interpolation(points));
        UploadChart.result = result;

        result = newtonMethod.getCountValue(points, form.getX());
        model.addAttribute("resultFuncNewt", result);
        model.addAttribute("resultTableNewt", newtonMethod.interpolation(points));
        UploadChartNewt.result = result;

        UploadChartNewt.newtonPoints = newtonMethod.interpolatePoints(points); // НОвая ХУИТА
        UploadChart.lagrangePoints = lagrangeMethod.Lpoint(points); // НОвая ХУИТА


        return "resultFunction";
    }


    @GetMapping("/resultFiles")
    public String resultFile(@ModelAttribute File file, Model model) throws IOException {

            Form form = getDataFromFile(file.getFileName());
            Function function = new Function();

            double[][] points = function.convertToArray(form.getInputData());
            LagrangeMethod lagrangeMethod = new LagrangeMethod();
            NewtonMethod newtonMethod = new NewtonMethod();
            newtonMethod.getCountValue(points, form.getX());
            Double result = 0.0;
            UploadChart.points = points;
            UploadChart.x = form.getX();
            UploadChart.number = 0;

            UploadChartNewt.points = points;
            UploadChartNewt.x = form.getX();
            UploadChartNewt.number = 0;

            result = newtonMethod.getCountValue(points, form.getX());
            model.addAttribute("result", result);
            model.addAttribute("resultTable", newtonMethod.interpolation(points));
            UploadChartNewt.result = result;

            result = lagrangeMethod.L(points, form.getX());
            model.addAttribute("resultNewt", result);
            model.addAttribute("resultTableNewt", lagrangeMethod.interpolation(points));
            UploadChart.result = result;

        //UploadChartNewt.newtonPoints = newtonMethod.interpolation(points); // СТРАЯ ХУЕТА
        UploadChartNewt.newtonPoints = newtonMethod.interpolatePoints(points); // НОвая ХУИТА
        // UploadChart.lagrangePoints = lagrangeMethod.interpolation(points); // Добавлено
        UploadChart.lagrangePoints = lagrangeMethod.Lpoint(points); // НОвая ХУИТА

        return "resultTable";

    }

    private Form getDataFromFile(String filePath) throws IOException {
        try {
            System.out.println(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/irinaabakumova/"+filePath)));
            String count = reader.readLine();
            if(!(isNumber(count))) throw new NumberFormatException("Не верное значениe");
            int countPoint = Integer.parseInt(count);
            Double[] xValues;
            Double[] yValues;
            ArrayList<Double> point= new ArrayList<>();
            String[] xValuesString = reader.readLine().split(" ");
            if (xValuesString.length != countPoint) throw new NumberFormatException("Количество x недостаточно");
            xValues = Arrays.stream(xValuesString).map(value -> Double.parseDouble(value.replaceAll(",", "."))).toArray(Double[]::new);
            String[] yValuesString = reader.readLine().split(" ");
            if (yValuesString.length != countPoint) throw new NumberFormatException("Количество y недостаточно");
            yValues = Arrays.stream(yValuesString).map(value -> Double.parseDouble(value.replaceAll(",", "."))).toArray(Double[]::new);
            String X = reader.readLine();
            if(!(isNumber(X))) throw new NumberFormatException("Не верное значениe");
            double x = Double.parseDouble(X);
            for (int i = 0; i < countPoint; i++) {
                point.add(xValues[i]);
            }
            for (int i = 0; i < countPoint; i++) {
                point.add(yValues[i]);
            }
            return new Form(point, x , 0);

        }catch (NumberFormatException e){
            return null;
        }
    }

    public boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}