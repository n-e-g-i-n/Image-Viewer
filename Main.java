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
    String filePath = "C:/Users/Negin/Pictures/Saved Pictures/Bird.JPG";
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
        // Create buttons
        selectFileButton = new JButton("Select File");
        showImageButton = new JButton("Show Image");
        resizeButton = new JButton("Resize Image");
        grayscaleButton = new JButton("Grayscale Image");
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
    public void resizePanel() {
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(null);

        // Create labels and text fields
        JLabel widthLabel = new JLabel("Width:");
        widthLabel.setBounds(50, 50, 100, 30);
        resizePanel.add(widthLabel);

        widthTextField = new JTextField();
        widthTextField.setBounds(150, 50, 100, 30);
        resizePanel.add(widthTextField);

        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setBounds(50, 100, 100, 30);
        resizePanel.add(heightLabel);

        heightTextField = new JTextField();
        heightTextField.setBounds(150, 100, 100, 30);
        resizePanel.add(heightTextField);

        // Create buttons
        showResizeButton = new JButton("Show Resized Image");
        showResizeButton.setBounds(100, 150, 200, 30);
        showResizeButton.addActionListener(this);
        resizePanel.add(showResizeButton);

        backButton = new JButton("Back");
        backButton.setBounds(100, 200, 200, 30);
        backButton.addActionListener(this);
        resizePanel.add(backButton);

        this.add(resizePanel);
    }

    public void brightnessPanel() {
        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(null);

        // Create labels and text fields
        JLabel brightnessLabel = new JLabel("Brightness Adjusment: ");
        brightnessLabel.setBounds(50, 50, 200, 30);
        brightnessPanel.add(brightnessLabel);

        brightnessTextField = new JTextField();
        brightnessTextField.setBounds(250, 50, 100, 30);
        brightnessPanel.add(brightnessTextField);

        // Create buttons
        showBrightnessButton = new JButton("Show Adjusted Brightness Image");
        showBrightnessButton.setBounds(100, 100, 250, 30);
        showBrightnessButton.addActionListener(this);
        brightnessPanel.add(showBrightnessButton);

        backButton = new JButton("Back");
        backButton.setBounds(100, 150, 250, 30);
        backButton.addActionListener(this);
        brightnessPanel.add(backButton);

        this.add(brightnessPanel);
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
    public void showResizeImage(int w, int h) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
            g2d.dispose();

            ImageIcon icon = new ImageIcon(resizedImage.getScaledInstance(w, h, Image.SCALE_SMOOTH));
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

    public void showBrightnessImage(float f) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            RescaleOp rescaleOp = new RescaleOp(f * brightenFactor, 0.0f, null);
            BufferedImage adjustedImage = rescaleOp.filter(image, null);

            ImageIcon icon = new ImageIcon(adjustedImage.getScaledInstance(w, h, Image.SCALE_SMOOTH));
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
            int width = Integer.parseInt(widthTextField.getText());
            int height = Integer.parseInt(heightTextField.getText());
            showResizeImage(width, height);
        } else if (e.getSource() == showBrightnessButton) {
            float brightnessAdjustment = Float.parseFloat(brightnessTextField.getText());
            showBrightnessImage(brightnessAdjustment);
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




