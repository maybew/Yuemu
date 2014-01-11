package yuemu.jfreechart;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart {
	
	EncodeProcesser processer = new EncodeProcesser();
	
	
	private final int WIDTH = 700;
	private final int HEIGHT = 500;
	
	// 创建一个饼图
		public JFreeChart getChart(String[] keys, long[] values, String title) {
			DefaultPieDataset dataset = new DefaultPieDataset();
			for (int i = 0; i < keys.length; i++) {
				dataset.setValue(keys[i], values[i]);
			}
			JFreeChart chart = ChartFactory.createPieChart(title, dataset, true,
					true, false);
			processer.encodingForPP(chart);
			PiePlot pp = (PiePlot)chart.getPlot();
			pp.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1} ({2})"));
			return chart;
		}
		
		// 根据得到的图形获得其URL地址
		public String getURL(JFreeChart chart, HttpSession session)
				throws IOException {
			return ServletUtilities.saveChartAsPNG(chart, WIDTH, HEIGHT, null, session);
		}
}
