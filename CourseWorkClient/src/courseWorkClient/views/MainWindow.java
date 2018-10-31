package courseWorkClient.views;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

import courseWorkClient.common.DataBaseInterface;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JButton btnDep1;
	private JButton btnDep2;
	private JButton btnDep3;
	private JButton btnInterpol;
	private JButton btnClear;
	private JComboBox<String> cmbbxChooseLane;
	private JPanel panel;
	private JComboBox cmbbxChooseTime;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		initComponents();
		createEvents();
	}
	
	void initComponents()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panel = new JPanel();
		panel.setBackground(Color.GRAY);
		
		btnDep1 = new JButton("Dep1");
		btnDep2 = new JButton("Dep2");
		btnDep3 = new JButton("Dep3");
		btnInterpol = new JButton("Interpol");
		btnClear = new JButton("Clear");
		cmbbxChooseLane = new JComboBox<String>();
		fillComboBox();
		
		cmbbxChooseTime = new JComboBox();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(cmbbxChooseLane, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDep1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDep2)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDep3)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnInterpol)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnClear)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cmbbxChooseTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(274))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
							.addGap(20))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(37)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbbxChooseLane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDep1)
						.addComponent(btnDep2)
						.addComponent(btnDep3)
						.addComponent(btnInterpol)
						.addComponent(btnClear)
						.addComponent(cmbbxChooseTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
					.addGap(27))
		);
		panel.setLayout(new BorderLayout(0, 0));
		contentPane.setLayout(gl_contentPane);
		
	}
	
	void createEvents()
	{	
		btnDep1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataBaseInterface dbInterface = new DataBaseInterface();
				String sql = "SELECT volume, occupancy FROM coursework.lane WHERE name = ?";
				try
				{
					PreparedStatement pstmt = dbInterface.connect().prepareStatement(sql);
					pstmt.setString(1, cmbbxChooseLane.getSelectedItem().toString());
					ResultSet rs = pstmt.executeQuery();
					XYDataset dataset = createDataset(rs);
					JFreeChart chart = plotPoints(dataset, "volume", "occupancy");
					ChartPanel chartPanel = new ChartPanel(chart);
					chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
			        chartPanel.setBackground(Color.white);
			        panel.removeAll();
					panel.add(chartPanel, BorderLayout.CENTER);
					panel.validate();
					
				}
				catch(SQLException ex)
				{
					ex.printStackTrace();
				}
				dbInterface.disconnect();
				
			}
		});
		
		btnDep2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnDep3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
	}
	
	private void fillComboBox()
	{
		DataBaseInterface dbInterface = new DataBaseInterface();
		String sql = "SELECT DISTINCT name FROM coursework.lane";
		try
		{
			PreparedStatement pstmt = dbInterface.connect().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){cmbbxChooseLane.addItem(rs.getString(1));}
			rs.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		dbInterface.disconnect();
	}
	
	private JFreeChart plotPoints(XYDataset dataset, String xAxis, String yAxis)
	{
		JFreeChart chart = ChartFactory.createScatterPlot(
                "", 
                xAxis, 
                yAxis, 
                dataset
        );
		XYPlot plot = chart.getXYPlot();
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesLinesVisible(0, false);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

		return chart;
	}
	
	private XYDataset createDataset(ResultSet rs) throws SQLException
	{ 
		XYSeries series = new XYSeries("Your orders");
		while (rs.next())
		{
			series.add(rs.getFloat(1), rs.getFloat(2));
		}
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
	}
}
