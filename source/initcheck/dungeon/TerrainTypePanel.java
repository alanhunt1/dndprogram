package initcheck.dungeon;

import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.database.TerrainTypes;
import initcheck.database.TerrainTypesDAO;
import initcheck.graphics.TiledGridPanel;
import initcheck.images.ImageSelectionDialog;
import initcheck.images.ImageSelectionOwner;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TerrainTypePanel extends TiledGridPanel implements ImageSelectionOwner{

	private static final long serialVersionUID = 1L;

	private JTextField terrainName = new JTextField();
	
	private JTextField movement = new JTextField(5);
	
	private JButton iconButton = new JButton();
	
	private JButton image1Button = new JButton();
	
	private JButton image2Button = new JButton();
	
	private JButton image3Button = new JButton();
	
	private ImageIcon icon;
	
	private ImageIcon image1;

	private ImageIcon image2;
	
	private ImageIcon image3;
	
	private String iconFileName;
	
	private String image1FileName;
	
	private String image2FileName;
	
	private String image3FileName;
	
	JCheckBox point = new JCheckBox("Point");
	
	JCheckBox line = new JCheckBox("Line");
	
	JCheckBox area = new JCheckBox("Area");
	
	JCheckBox flood = new JCheckBox("Flood");
	
	private PanelButton save = new PanelButton("save");
	
	private TerrainPreviewPanel previewSmall = new TerrainPreviewPanel(5);
	
	private TerrainPreviewPanel previewMed = new TerrainPreviewPanel(20);
	
	private TerrainPreviewPanel previewLarge = new TerrainPreviewPanel(60);
	
	private RenderTypeChooser renderType = new RenderTypeChooser();
	
	private TerrainTypes terrain;
	
	private int imageSelection = 0;
	
	public TerrainTypePanel(){
		super(InitImage.lightRocks);
		init(new TerrainTypes());
	}
	
	public TerrainTypePanel(TerrainTypes t){
		super(InitImage.lightRocks);
		init(t);
		terrain = t;
	}
	
	public void setTerrainType(TerrainTypes t){
		terrain = t;
		terrainName.setText(t.getTerrainName());
		iconFileName = t.getIcon();
		if (t.getIcon() != null && !t.getIcon().equals("")){
			icon = new ImageIcon(t.getIcon());
		}else{
			icon = InitImage.qmarkIcon;
		}
		iconButton.setIcon(icon);
		point.setSelected(t.isPoint());
		line.setSelected(t.isLine());
		area.setSelected(t.isArea());
		flood.setSelected(t.isFlood());
		previewSmall.setImage(image1);
		previewMed.setImage(image1);
		previewLarge.setImage(image1);
		image1FileName = t.getImage1();
		image2FileName = t.getImage2();
		image3FileName = t.getImage3();
		
		
		if (t.getImage1() != null && !t.getImage1().equals("")){
			image1 = new ImageIcon(t.getImage1());
		}else{
			image1 = InitImage.qmarkIcon;
		}
		image1Button.setIcon(image1);
		
		if (t.getImage2() != null && !t.getImage2().equals("")){
			image2 = new ImageIcon(t.getImage2());
		}else{
			image2 = InitImage.qmarkIcon;
		}
		image2Button.setIcon(image2);
		
		if (t.getImage3() != null && !t.getImage3().equals("")){
			image3 = new ImageIcon(t.getImage3());
		}else{
			image3 = InitImage.qmarkIcon;
		}
		image3Button.setIcon(image3);
		renderType.setSelectedItem(t.getRenderType());
		movement.setText(t.getMovementRate());
	}
	
	public void init(TerrainTypes t){
		
		movement.setText(t.getMovementRate());
		
		JPanel previewPanel = new JPanel();
		previewPanel.setLayout(new GridLayout(3,1));
		previewPanel.add(previewSmall);
		previewPanel.add(previewMed);
		previewPanel.add(previewLarge);
		
		
		terrainName.setText(t.getTerrainName());
		doLayout(terrainName, 0);
		setWeightX(1);
		setWeightY(1);
		doLayout(previewPanel, 1, 1, 10);
		setWeightX(0);
		setWeightY(0);
		incYPos();
		if (t.getIcon() != null && !t.getIcon().equals("")){
			icon = new ImageIcon(t.getIcon());
		}else{
			icon = InitImage.qmarkIcon;
		}
		iconButton.setIcon(icon);
		
		doLayout(iconButton);
		incYPos();
		doLayout(renderType);
		
		
		renderType.setSelectedItem(t.getRenderType());
		incYPos();
		JPanel modePanel = new JPanel();
		modePanel.setOpaque(false);
		
		point.setSelected(t.isPoint());
		
		line.setSelected(t.isLine());
		
		area.setSelected(t.isArea());
		
		flood.setSelected(t.isFlood());
		
		modePanel.add(point);
		modePanel.add(line);
		modePanel.add(area);
		modePanel.add(flood);
		
		doLayout(modePanel);
		incYPos();
		JPanel movementPanel = new JPanel();
		movementPanel.setOpaque(false);
		movementPanel.add(new JLabel("Movement Rate (0-1)"));
		movementPanel.add(movement);
		doLayout(movementPanel);
		
		incYPos();
		
		if (t.getImage1() != null && !t.getImage1().equals("")){
			image1 = new ImageIcon(t.getImage1());
		}else{
			image1 = InitImage.qmarkIcon;
		}
		image1Button.setIcon(image1);
		
		if (t.getImage2() != null && !t.getImage2().equals("")){
			image2 = new ImageIcon(t.getImage2());
		}else{
			image2 = InitImage.qmarkIcon;
		}
		image2Button.setIcon(image2);
		
		if (t.getImage3() != null && !t.getImage3().equals("")){
			image3 = new ImageIcon(t.getImage3());
		}else{
			image3 = InitImage.qmarkIcon;
		}
		image3Button.setIcon(image3);
		
		previewSmall.setImage(image1);
		previewMed.setImage(image1);
		previewLarge.setImage(image1);
		
		JPanel imagePanel = new JPanel();
		imagePanel.setOpaque(false);
		imagePanel.add(image1Button);
		imagePanel.add(image2Button);
		imagePanel.add(image3Button);
		
		doLayout(imagePanel);
		incYPos();
		doLayout(save);
		

		save.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveTerrainType();
					}
				}
		);
		
		iconButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setIconFile(0);
					}
				}
		);
		
		image1Button.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setIconFile(1);
					}
				}
		);
		
		image2Button.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setIconFile(2);
					}
				}
		);
		
		image3Button.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setIconFile(3);
					}
				}
		);
		
	}
	
	public void setIconFile(int i){
		imageSelection = i;
		@SuppressWarnings("unused")
		ImageSelectionDialog isd = new ImageSelectionDialog(null, this, "images");
	}
	
	public void saveTerrainType(){
			terrain.setArea(area.isSelected());
			terrain.setFlood(flood.isSelected());
			terrain.setIcon(iconFileName);
			terrain.setImage1(image1FileName);
			terrain.setImage2(image2FileName);
			terrain.setImage3(image3FileName);
			terrain.setLine(line.isSelected());
			terrain.setMovementRate(movement.getText());
			terrain.setPoint(point.isSelected());
			terrain.setRenderType((String)renderType.getSelectedItem());
			terrain.setTerrainName(terrainName.getText());
			TerrainTypesDAO ttdb = new TerrainTypesDAO();
			ttdb.addOrUpdateTerrainTypes(terrain);
	}
	
	public void setImage(String s) {
		switch (imageSelection){
		case 0:
			iconFileName = s;
			icon = new ImageIcon(s);
			iconButton.setIcon(icon);
			break;
		case 1:
			image1FileName = s;
			image1 = new ImageIcon(s);
			image1Button.setIcon(image1);
			previewSmall.setImage(icon);
			previewMed.setImage(icon);
			previewLarge.setImage(icon);
			break;
		case 2: 
			image2FileName = s;
			image2 = new ImageIcon(s);
			image2Button.setIcon(image2);
			break;
		case 3: 
			image3FileName = s;
			image3 = new ImageIcon(s);
			image3Button.setIcon(image3);
			break;
		}
	}
}
