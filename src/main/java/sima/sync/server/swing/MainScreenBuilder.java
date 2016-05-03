package sima.sync.server.swing;

import sima.sync.server.Constants;
import sima.sync.server.Main;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainScreenBuilder implements Runnable {
    public JFrame mainFrame;
    private MainListener mainListener = new MainListener();
    public JLabel statusVersion;
    public JLabel statusConnection;

    @Override
    public void run() {
        long startNano = System.nanoTime();
        mainFrame = new JFrame("SimaSync Server");
        Box statusBox = Box.createHorizontalBox();
        statusBox.setPreferredSize(new Dimension(380, 0));
        TitledBorder statusBoxBorder = new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Status Panel");
        statusBoxBorder.setTitleFont(statusBoxBorder.getTitleFont().deriveFont(16f));
        statusBox.setBorder(statusBoxBorder);
        {//Fill up status panel
            Box column1 = Box.createVerticalBox();
            column1.setAlignmentY(Component.TOP_ALIGNMENT);
            Box column2 = Box.createVerticalBox();
            column2.setAlignmentY(Component.TOP_ALIGNMENT);
            //Version
            statusVersion = new JLabel(Constants.VERSION);
            createStatusRow(column1, column2, "Version", statusVersion);
            //Connection
            statusConnection = new JLabel("This is getting somewhere...");
            createStatusRow(column1, column2, "Connection Status", statusConnection);
            //Finally...
            statusBox.add(column1);
            JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
            separator.setMaximumSize(new Dimension(5, Integer.MAX_VALUE));
            statusBox.add(separator);
            statusBox.add(column2);
        }
        JPanel main = new JPanel(new BorderLayout());
        //TODO List
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
        //Add everything to the content pane.
        Container contentPane = mainFrame.getContentPane();
        contentPane.add(statusBox, BorderLayout.LINE_END);
        contentPane.add(main, BorderLayout.CENTER);
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
        Constants.log.info("Gui completed (" + ((System.nanoTime() - startNano) / 1000000) + " milli secs)");
    }

    private static final float FONT_SIZE = 13f;
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