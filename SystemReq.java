// 3. Layouts & Formatting
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
// 2. Standard Java Core Libraries (For Native HW Detection)
import java.lang.Runtime;                 // For CPU cores, total memory, free memory
import java.io.File;                      // For disk space & storage detection
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;              // For network IP and hostname
import java.nio.file.FileSystem;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.awt.GraphicsEnvironment;      // For monitor resolution and display info
import java.awt.GraphicsDevice;
// 1. Swing UI Components
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public abstract class SystemReq extends JFrame implements ActionListener {
    JLabel label;
    

    public static void main(String[] args) {
        
    // UI Components / Interface
        //frame setup
        JFrame frame = new JFrame();
        frame.setSize(650, 650);
        frame.setTitle("System Components");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon imageicon = new ImageIcon("settings.jpg"); // adding imageicon
        frame.setIconImage(imageicon.getImage()); //setting image icon
        frame.setBackground(new Color(255, 255, 255));

        //Border
        Border border = BorderFactory.createDashedBorder(Color.black);
        Border  paddingBorder = BorderFactory.createEmptyBorder(15, 10, 10, 10);
        Border compound = BorderFactory.createCompoundBorder(border, paddingBorder);
        
        //Cpu title
        JLabel cpuTitle = new JLabel();
        cpuTitle.setText("CPU:");
        cpuTitle.setBounds(120, 40, 100, 30);
        cpuTitle.setFont(new Font("Segoe UI", Font.BOLD ,25));
        cpuTitle.setVisible(true);


        //The Cpu Info
        JLabel cpu = new JLabel();
        CPUDetector cpuo = new CPUDetector();
        cpu.setText("1.7Intel(R) Core(TM) i5-4210U CPU0GHz (test)");
        cpu.setBounds(50, 120, 190, 170);
        cpu.setHorizontalAlignment(JLabel.CENTER);
        cpu.setVerticalAlignment(JLabel.TOP);
        cpu.setBorder(compound);
        cpu.setIconTextGap(20);
        cpu.setFont(new Font("Segoe UI", Font.BOLD ,15));
        cpu.setText("<html><body style='width: 120px; text-align: center;'>"+ cpuo.getCPUInfoHTML() +"</body></html>");
        cpu.setVisible(true);

        // Secondary info
        JLabel cpuHertz = new JLabel();
        cpuHertz.setBounds(50, 240, 190, 50);
        cpuHertz.setVisible(true);
        cpuHertz.setText(cpuo.getCPUhertzHTML());
        cpuHertz.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cpuHertz.setForeground(new Color(133, 133, 133));
        cpuHertz.setHorizontalTextPosition(JLabel.CENTER);
        cpuHertz.setHorizontalAlignment(JLabel.CENTER);


        //Border
        Border sborder = BorderFactory.createDashedBorder(Color.black);
        Border  spaddingBorder = BorderFactory.createEmptyBorder(15, 10, 10, 10);
        Border scompound = BorderFactory.createCompoundBorder(sborder, spaddingBorder);
        
        //Gpu title
        JLabel gpuTitle = new JLabel();
        gpuTitle.setText("GPU:");
        gpuTitle.setBounds(420, 40, 100, 30);
        gpuTitle.setFont(new Font("Segoe UI", Font.BOLD ,25));
        gpuTitle.setVisible(true);


        //The Gpu Info
        JLabel gpu = new JLabel();
        gpu.setText("NVIDIA GeForce 840M (test)");
        GPUDetector gpuo = new GPUDetector();
        gpu.setBounds(350, 120, 190, 170);
        gpu.setHorizontalAlignment(JLabel.CENTER);
        gpu.setVerticalAlignment(JLabel.TOP);
        gpu.setBorder(scompound);
        gpu.setIconTextGap(20);
        gpu.setFont(new Font("Segoe UI", Font.BOLD ,15));
        gpu.setText("<html><body style='width: 120px; text-align: center;'>" + gpuo.getGPUInfoHTML()+ "</body></html>");
        gpu.setVisible(true);
        // Secondary info
        JLabel gpuVRAM = new JLabel();
        gpuVRAM.setBounds(350, 240, 190, 50);
        gpuVRAM.setVisible(true);
        gpuVRAM.setText(gpuo.getTotalVRAMHTML());
        gpuVRAM.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gpuVRAM.setForeground(new Color(133, 133, 133));
        gpuVRAM.setHorizontalTextPosition(JLabel.CENTER);
        gpuVRAM.setHorizontalAlignment(JLabel.CENTER);

        //RAM title
        JLabel RAMTitle = new JLabel();
        RAMTitle.setText("RAM Installed:");
        RAMTitle.setBounds(70, 340, 260, 30);
        RAMTitle.setHorizontalTextPosition(JLabel.CENTER);
        RAMTitle.setFont(new Font("Segoe UI", Font.BOLD ,25));
        RAMTitle.setVisible(true);


        //The RAM Info
        JLabel RAM = new JLabel();
        RAMDetector ramo = new RAMDetector();
        RAM.setText("12.0 GB (test)");
        RAM.setBounds(50, 420, 190, 170);
        RAM.setHorizontalAlignment(JLabel.CENTER);
        RAM.setVerticalAlignment(JLabel.TOP);
        RAM.setBorder(scompound);
        RAM.setIconTextGap(20);
        RAM.setFont(new Font("Segoe UI", Font.BOLD ,15));
        RAM.setText("<html><body style='width: 120px; text-align: center;'>"+ ramo.getRAMSizeHTML()+"</body></html>");
        RAM.setVisible(true);



        // Secondary info
        JLabel RAMType = new JLabel();
        RAMType.setBounds(50, 540, 190, 50);
        RAMType.setVisible(true);
        RAMType.setText(ramo.getDDRTypeHTML());
        RAMType.setFont(new Font("Segoe UI", Font.BOLD, 16));
        RAMType.setForeground(new Color(133, 133, 133));
        RAMType.setHorizontalTextPosition(JLabel.CENTER);
        RAMType.setHorizontalAlignment(JLabel.CENTER);


//Disk title
        JLabel TotalDisk = new JLabel();
        TotalDisk.setText("Storage:");
        TotalDisk.setBounds(390, 340, 260, 30);
        TotalDisk.setHorizontalTextPosition(JLabel.CENTER);
        TotalDisk.setFont(new Font("Segoe UI", Font.BOLD ,25));
        TotalDisk.setVisible(true);


        //The Disk Info
        JLabel disk = new JLabel();
        DiskDetector disko = new DiskDetector();
        disk.setText("1 terra(test)");
        disk.setBounds(350, 420, 190, 170);
        disk.setHorizontalAlignment(JLabel.CENTER);
        disk.setVerticalAlignment(JLabel.TOP);
        disk.setBorder(scompound);
        disk.setIconTextGap(20);
        disk.setFont(new Font("Segoe UI", Font.BOLD ,15));
        disk.setText("<html><body style='width: 120px; text-align: center;'>"+disko.DiskintoHTML()+ "<h2>GB</h2>" +"</body></html>");
        disk.setVisible(true);


        
        // Secondary info
        JLabel diskused = new JLabel();
        diskused.setBounds(350, 540, 190, 50);
        diskused.setVisible(true);
        diskused.setText(disko.getStorageUsageHTML());
        diskused.setFont(new Font("Segoe UI", Font.BOLD, 16));
        diskused.setForeground(new Color(133, 133, 133));
        diskused.setHorizontalTextPosition(JLabel.CENTER);
        diskused.setHorizontalAlignment(JLabel.CENTER);

        //Finishing/ Adding Components to Frame
        frame.add(cpuTitle);
        frame.add(cpu);
        frame.add(cpuHertz);
        frame.add(gpuVRAM);
        frame.add(gpu);
        frame.add(gpuTitle);
        frame.add(RAMType);
        frame.add(RAM);
        frame.add(RAMTitle);
        frame.add(TotalDisk);
        frame.add(disk);
        frame.add(diskused);
        frame.setVisible(true);

        

        
    // Detection
            
        }
    

    @Override
    public void actionPerformed(ActionEvent e) {
    
    }
}


