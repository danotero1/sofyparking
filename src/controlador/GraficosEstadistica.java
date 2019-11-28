package controlador;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import entidades.Parqueadero;
import modelo.ParqueaderoModelo;

@ManagedBean(name="GraficosEstadistica")
@SessionScoped
public class GraficosEstadistica {
	
	private StreamedContent graphicText;
	private ParqueaderoModelo parqueadero = new ParqueaderoModelo();
	private StreamedContent chart;

    @PostConstruct
	public void init() {
        try {
            //Graphic Text
            BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bufferedImg.createGraphics();
            g2.drawString("This is a text", 0, 10);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImg, "png", os);
            graphicText = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png"); 

            //Chart
            JFreeChart jfreechart = ChartFactory.createPieChart("Parqueaderos", createDataset(), true, true, false);
            File chartFile = new File("dynamichart");
            ChartUtils.saveChartAsPNG(chartFile, jfreechart, 375, 300);
            chart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
		} 
        catch (Exception e) {
			e.printStackTrace();
		}
	}

    public StreamedContent getGraphicText() {
        return graphicText;
    }
		
	public StreamedContent getChart() {
		return chart;
	}
	
	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
//	
		List<Parqueadero> par = parqueadero.findAll();
		for (int i = 0; i < par.size(); i++) {
			dataset.setValue(par.get(i).getCiudad(), new Double(par.get(i).getIdTarifa()));
		}
		return dataset;
	}

}
