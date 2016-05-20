package sima.sync.server.swing;

import sima.sync.server.Constants;
import sima.sync.server.Instance;
import sima.sync.server.Main;
import sima.sync.server.allocation.DownloadElement;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainScreenBuilder implements Runnable {
    public JFrame mainFrame;
    public JLabel connection;
    public JLabel packetSecond;
    public JLabel upRate;
    public JLabel downRate;
    public JLabel fileBacklog;
    public JLabel threadState[] = new JLabel[Constants.LOGICAL_CORES];
    public JLabel inBuffer;

    @Override
    public void run() {
        MainListener mainListener = new MainListener();
        long start = System.currentTimeMillis();
        mainFrame = new JFrame("SimaSync Server");
        Box statusBox = Box.createHorizontalBox();
        statusBox.setPreferredSize(new Dimension(380, 0));
        TitledBorder statusBoxBorder = new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Status Panel");
        statusBoxBorder.setTitleFont(statusBoxBorder.getTitleFont().deriveFont(18.0f));
        statusBox.setBorder(statusBoxBorder);
        {//Fill up Status panel
            Box column1 = Box.createVerticalBox();
            column1.setAlignmentY(Component.TOP_ALIGNMENT);
            Box column2 = Box.createVerticalBox();
            column2.setAlignmentY(Component.TOP_ALIGNMENT);
            //Version
            createStatusRow(column1, column2, "Version", new JLabel(Constants.VERSION + ", " + Constants.DATA_PACKET_SIZE + " block size"));
            //Connection
            connection = new JLabel("Initializing...");
            createStatusRow(column1, column2, "Connection Status", connection);
            //Packets/second
            packetSecond = new JLabel("N/A p/s");
            createStatusRow(column1, column2, "Data packet rate", packetSecond);
            //Up rate
            upRate = new JLabel("N/A kbyte/s");
            createStatusRow(column1, column2, "Upload rate", upRate);
            //Down rate
            downRate = new JLabel("N/A kbytes");
            createStatusRow(column1, column2, "Download rate", downRate);
            //File backlog
            fileBacklog = new JLabel("0 file(s) pending");
            createStatusRow(column1, column2, "Files pending", fileBacklog);
            //Hash threads state
            for (int i = 0; i < Constants.LOGICAL_CORES; i++) {
                threadState[i] = new JLabel("Sleeping...");
                createStatusRow(column1, column2, "State of #" + i + " thread", threadState[i]);
            }
            //In buffer usage
            inBuffer = new JLabel("0/0 kbytes (N/A %)");
            createStatusRow(column1, column2, "In buffer usage", inBuffer);
            //Finally...
            statusBox.add(column1);
            statusBox.add(Box.createRigidArea(new Dimension(2, 0)));
            JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
            sep.setMaximumSize(new Dimension(4, Integer.MAX_VALUE));
            statusBox.add(sep);
            statusBox.add(Box.createRigidArea(new Dimension(2, 0)));
            statusBox.add(column2);
        }
        //Create and fill up the menu
        JMenuBar menuBar = new JMenuBar();
        {
            int acceleratorMods = InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK;
            {//Top-level menu "File"
                JMenu menuFile = new JMenu("File");
                menuBar.add(menuFile);
                {//Item menu "open files"
                    JMenuItem itemFileOpen = new JMenuItem("Open files...", KeyEvent.VK_O);
                    itemFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, acceleratorMods));
                    itemFileOpen.setActionCommand("FO");
                    itemFileOpen.addActionListener(mainListener);
                    itemFileOpen.setToolTipText("Open a dialog to choose files, which will be added to the queue for uploading.");
                    menuFile.add(itemFileOpen);
                }
                {//Item menu "output folder"
                    JMenuItem itemFileOutFolder = new JMenuItem("Select destination folder...", KeyEvent.VK_R);
                    itemFileOutFolder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, acceleratorMods));
                    itemFileOutFolder.setActionCommand("FD");
                    itemFileOutFolder.addActionListener(mainListener);
                    itemFileOutFolder.setToolTipText("Select which folder is used to store incoming files.");
                    menuFile.add(itemFileOutFolder);
                }
                {//Item menu "exit"
                    JMenuItem itemFileExit = new JMenuItem("Exit application...", KeyEvent.VK_E);
                    itemFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
                    itemFileExit.setActionCommand("GE");
                    itemFileExit.addActionListener(mainListener);
                    itemFileExit.setToolTipText("One of many ways to exit the application");
                    menuFile.add(itemFileExit);
                }
            }
            {//Top-level menu "Actions"
                JMenu menuActions = new JMenu("Actions");
                menuBar.add(menuActions);
                {//Item menu "resume all"
                    JMenuItem itemActionsAR = new JMenuItem("Resume all");
                    itemActionsAR.setActionCommand("AAR");
                    itemActionsAR.addActionListener(mainListener);
                    menuActions.add(itemActionsAR);
                }
                {//Item menu "pause all"
                    JMenuItem itemActionsAP = new JMenuItem("Pause all");
                    itemActionsAP.setActionCommand("AAP");
                    itemActionsAP.addActionListener(mainListener);
                    menuActions.add(itemActionsAP);
                }
                {//Item menu "cancel all"
                    JMenuItem itemActionsAC = new JMenuItem("Cancel all");
                    itemActionsAC.setActionCommand("AAC");
                    itemActionsAC.addActionListener(mainListener);
                    menuActions.add(itemActionsAC);
                }
                {//Item menu "accept in"
                    JMenuItem itemActionsIA = new JMenuItem("Accept all incoming");
                    itemActionsIA.setActionCommand("AIA");
                    itemActionsIA.addActionListener(mainListener);
                    menuActions.add(itemActionsIA);
                }
                {//Item menu "deny in"
                    JMenuItem itemActionsID = new JMenuItem("Deny all incoming");
                    itemActionsID.setActionCommand("AID");
                    itemActionsID.addActionListener(mainListener);
                    menuActions.add(itemActionsID);
                }
                {//Item menu "resume in"
                    JMenuItem itemActionsIR = new JMenuItem("Resume all incoming");
                    itemActionsIR.setActionCommand("AIR");
                    itemActionsIR.addActionListener(mainListener);
                    menuActions.add(itemActionsIR);
                }
                {//Item menu "pause in"
                    JMenuItem itemActionsIP = new JMenuItem("Pause all incoming");
                    itemActionsIP.setActionCommand("AIP");
                    itemActionsIP.addActionListener(mainListener);
                    menuActions.add(itemActionsIP);
                }
                {//Item menu "cancel in"
                    JMenuItem itemActionsIC = new JMenuItem("Cancel all incoming");
                    itemActionsIC.setActionCommand("AIC");
                    itemActionsIC.addActionListener(mainListener);
                    menuActions.add(itemActionsIC);
                }
                {//Item menu "resume out"
                    JMenuItem itemActionsOR = new JMenuItem("Resume uploading");
                    itemActionsOR.setActionCommand("AOR");
                    itemActionsOR.addActionListener(mainListener);
                    menuActions.add(itemActionsOR);
                }
                {//Item menu "pause out"
                    JMenuItem itemActionsOP = new JMenuItem("Pause uploading");
                    itemActionsOP.setActionCommand("AOP");
                    itemActionsOP.addActionListener(mainListener);
                    menuActions.add(itemActionsOP);
                }
                {//Item menu "cancel out"
                    JMenuItem itemActionsOC = new JMenuItem("Cancel uploading");
                    itemActionsOC.setActionCommand("AOC");
                    itemActionsOC.addActionListener(mainListener);
                    menuActions.add(itemActionsOC);
                }
            }
            {//Top-level menu "Extras"
                JMenu menuExtras = new JMenu("Extras");
                menuBar.add(menuExtras);
                {//Item menu "settings"
                    JMenuItem itemExtrasSettings = new JMenuItem("Settings", KeyEvent.VK_S);
                    itemExtrasSettings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, acceleratorMods));
                    itemExtrasSettings.setActionCommand("ES");
                    itemExtrasSettings.addActionListener(mainListener);
                    itemExtrasSettings.setToolTipText("Open a dialog to tweak the settings.");
                    menuExtras.add(itemExtrasSettings);
                }
                {//Item menu "credits/licences"
                    JMenuItem itemExtrasCredits = new JMenuItem("Credits/Licences", KeyEvent.VK_C);
                    itemExtrasCredits.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, acceleratorMods));
                    itemExtrasCredits.setActionCommand("EC");
                    itemExtrasCredits.addActionListener(mainListener);
                    itemExtrasCredits.setToolTipText("Open a dialog to read the credits and the licences of the software used for this app.");
                    menuExtras.add(itemExtrasCredits);
                }
            }
        }
        //Generate list
        JList<DownloadElement> list = new JList<>(Instance.storage);
        list.setCellRenderer(new Renderer.DownloadListRenderer());
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        TitledBorder listBorder = new TitledBorder(new LineBorder(new Color(0xE0E0E0), 2, true), "Operations list");
        listBorder.setTitleFont(listBorder.getTitleFont().deriveFont(20.0f));
        list.setBorder(listBorder);
        list.setOpaque(false);
        JScrollPane scroll = new JScrollPane(list);
        //Add everything to the content pane.
        Container contentPane = mainFrame.getContentPane();
        contentPane.add(statusBox, BorderLayout.LINE_END);
        contentPane.add(scroll, BorderLayout.CENTER);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.exit(0);
            }
        });
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.pack();
        mainFrame.setVisible(true);//Must be last statement!
        Instance.log.info("Gui completed (" + (System.currentTimeMillis() - start) + " milli secs)");
    }

    private static final float FONT_SIZE = 15f;
    private static final Font STATUS_BOLD = UIManager.getFont("Label.font").deriveFont(Font.BOLD, FONT_SIZE);
    private static final Font STATUS_NORMAL = UIManager.getFont("Label.font").deriveFont(Font.PLAIN, FONT_SIZE);

    private void createStatusRow(Box column1, Box column2, String keyStr, JComponent comp) {
        JLabel keyLabel = new JLabel(keyStr);
        keyLabel.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
        keyLabel.setFont(STATUS_BOLD);
        comp.setFont(STATUS_NORMAL);
        column1.add(keyLabel);
        column1.add(Box.createRigidArea(new Dimension(0, 3)));
        column2.add(comp);
        column2.add(Box.createRigidArea(new Dimension(0, 3)));
    }
}