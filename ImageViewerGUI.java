import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.awt.image.RescaleOp;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class ImageViewerGUI extends JFrame implements ActionListener {
    JButton selectFileButton;
    JButton showImageButton;
    JButton resizeButton;
    JButton grayscaleButton;
    JButton brightnessButton;
    JButton closeButton;
    JButton showResizeButton;
    JButton showBrightnessButton;
    JButton backButton;
    JTextField widthTextField;
    JTextField heightTextField;
    JTextField brightnessTextField;
    String filePath = "Users/Negin/Pictures/Saved Pictures/Bird.JPG";
    File file;
    JFileChooser fileChooser = new JFileChooser(filePath);
    int h = 900;
    int w = 1200;
    float brightenFactor = 1;

    ImageViewerGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);

        mainPanel();

    }


    public void mainPanel() {
        // Create main panel for adding to Frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));
        buttonsPanel.setBounds(200,100,300,100);

        // Create buttons
        selectFileButton = new JButton("Select File");
        showImageButton = new JButton("Show Image");
        resizeButton = new JButton("Resize Image");
        grayscaleButton = new JButton("Gray Scale");
        brightnessButton = new JButton("Adjust Brightness");
        closeButton = new JButton("Close");

        // Add action listeners to buttons
        selectFileButton.addActionListener(this);
        showImageButton.addActionListener(this);
        resizeButton.addActionListener(this);
        grayscaleButton.addActionListener(this);
        brightnessButton.addActionListener(this);
        closeButton.addActionListener(this);

        // Adding all buttons to Grid panel
        buttonsPanel.add(selectFileButton);
        buttonsPanel.add(showImageButton);
        buttonsPanel.add(brightnessButton);
        buttonsPanel.add(grayscaleButton);
        buttonsPanel.add(resizeButton);
        buttonsPanel.add(closeButton);

        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);

        // add main panel to our frame
        this.add(mainPanel);
    }
    public void resizePanel(){

        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(new GridLayout(3, 2));
        resizePanel.setSize(700,300);
        showResizeButton=new JButton("result");
        JLabel widthText=new JLabel("width :");
        resizePanel.add(widthText);
        widthTextField=new JTextField("");
        resizePanel.add(widthTextField);
        JLabel heightText=new JLabel("heigh :");
        resizePanel.add(heightText);
        heightTextField=new JTextField("");
        resizePanel.add(heightTextField);

        resizePanel.add(showResizeButton);
        backButton.addActionListener(this);
        resizePanel.add(backButton);
        showResizeButton.addActionListener(this);

        this.getContentPane().removeAll();
        this.add(resizePanel);
        this.repaint();
        this.revalidate();
    }

    public void brightnessPanel() {
        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(null);

        JLabel brightnessLabel = new JLabel("Enter f\n (must be between 0 and 1)");
        brightnessLabel.setBounds(100,80, 200,50);
        brightnessTextField = new JTextField();
        brightnessTextField.setBounds(400, 80, 200,50);

        backButton = new JButton("Back");
        backButton.setBounds(115, 180, 100,38);
        backButton.addActionListener(this);

        showBrightnessButton = new JButton("Show Brightness");  // Add this line
        showBrightnessButton.setBounds(475, 180, 100,38);
        showBrightnessButton.addActionListener(this);

        brightnessPanel.add(brightnessLabel);
        brightnessPanel.add(brightnessTextField);
        brightnessPanel.add(backButton);
        brightnessPanel.add(showBrightnessButton);

        this.getContentPane().removeAll();
        this.add(brightnessPanel);
        this.repaint();
        this.revalidate();
    }
    public void chooseFileImage() {
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            filePath = file.getAbsolutePath();
            JOptionPane.showMessageDialog(null, "Selected File: " + filePath);
        }
    }

    public void showOriginalImage() {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(w, h, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(icon);
            JScrollPane scrollPane = new JScrollPane(label);

            JFrame tempFrame = new JFrame();
            tempFrame.setTitle("Image Viewer");
            tempFrame.setSize(w + 20, h + 20);
            tempFrame.setVisible(true);
            tempFrame.setResizable(true);
            tempFrame.add(scrollPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void grayScaleImage() {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(),
                    BufferedImage.TYPE_BYTE_GRAY);
            ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
            op.filter(image, grayImage);

            ImageIcon icon = new ImageIcon(grayImage.getScaledInstance(w, h, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(icon);
            JScrollPane scrollPane = new JScrollPane(label);

            JFrame tempFrame = new JFrame();
            tempFrame.setTitle("Image Viewer");
            tempFrame.setSize(w + 20, h + 20);
            tempFrame.setVisible(true);
            tempFrame.setResizable(true);
            tempFrame.add(scrollPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showResizeImage(int w, int h) throws IOException {

        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JLabel pic=new JLabel();
        file=fileChooser.getSelectedFile();
        BufferedImage bufferedImage= ImageIO.read(file);
        Image scaleImage = bufferedImage.getScaledInstance(w, h,Image.SCALE_DEFAULT);
        ImageIcon icon=new ImageIcon(scaleImage);
        pic.setIcon(icon);
        tempPanel.add(pic);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void showBrightnessImage(float f) throws IOException {

        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel();
        file = fileChooser.getSelectedFile();

        BufferedImage bufferedImage = ImageIO.read(file);
        RescaleOp op = new RescaleOp(f,0, null);
        BufferedImage image = op.filter(bufferedImage, bufferedImage);

        ImageIcon imageIcon = new ImageIcon(image);
        label.setIcon(imageIcon);


        tempPanel.add(label);
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel, BorderLayout.CENTER);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectFileButton) {
            chooseFileImage();
        } else if(showImageButton == e.getSource()){
            showOriginalImage();
        } else if (e.getSource() == resizeButton) {
            resizePanel();
        } else if (e.getSource() == grayscaleButton) {
            grayScaleImage();
        } else if (e.getSource() == brightnessButton) {
            brightnessPanel();
        } else if (e.getSource() == closeButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (e.getSource() == showResizeButton) {
            try {
                int width = Integer.parseInt(widthTextField.getText());
                int height = Integer.parseInt(heightTextField.getText());
                showResizeImage(width, height);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input for width or height. Please enter a valid number.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while resizing the image.");
            }
        } else if (e.getSource() == showBrightnessButton) {
            try {
                float brightnessAdjustment = Float.parseFloat(brightnessTextField.getText());
                if (brightnessAdjustment < 0 || brightnessAdjustment > 1) {
                    JOptionPane.showMessageDialog(null, "Invalid input for brightness adjustment. Please enter a number between 0 and 1.");
                } else {
                    showBrightnessImage(brightnessAdjustment);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input for brightness adjustment. Please enter a valid number.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while adjusting the brightness of the image.");
            }
        } else if (e.getSource() == backButton) {
            getContentPane().removeAll();
            mainPanel();
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ImageViewerGUI imageViewerGUI = new ImageViewerGUI();
            }
        });
    }
}




