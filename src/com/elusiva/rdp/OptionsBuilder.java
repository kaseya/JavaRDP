package com.elusiva.rdp;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.awt.*;
import java.util.Locale;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 1, 2010
 * Time: 5:59:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class OptionsBuilder {
    private static Logger logger = Logger.getLogger("com.elusiva.rdp");
    private String[] arguments;
    private Getopt getOptions;
    private Options option;

    public OptionsBuilder(String[] args) {
        this.arguments = args;
        this.option = new Options();
        this.getOptions = new Getopt("properJavaRDP", args,"bc:d:f::g:k:l:m:n:p:s:t:T:u:o:r:", createLongOptions());

    }

    public Options populate() {
        boolean fKdeHack = false;
        int c;
        String arg;
        while ((c = getOptions.getopt()) != -1) {
            switch (c) {
                case 0:
                    parseLongArguments();
                    break;

                case 'o':
                    option.setBPPImpl(Integer.parseInt(getOptions.getOptarg()));
                    break;
                case 'b':
                    option.disableLowLatency();
                    break;
                case 'm':
                    option.setMapFile(getOptions.getOptarg());
                    break;
                case 'c':
                    option.setDirectory(getOptions.getOptarg());
                    break;
                case 'd':
                    option.setDomain(getOptions.getOptarg());
                    break;
                case 'f':
                    Dimension screen_size = Toolkit.getDefaultToolkit()
                            .getScreenSize();
                    // ensure width a multiple of 4
                    option.setWidth(screen_size.width & ~3);
                    option.setHeight(screen_size.height);
                    option.enableFullScreen();
                    arg = getOptions.getOptarg();
                    if (arg != null) {
                        if (arg.charAt(0) == 'l')
                            fKdeHack = true;
                        else {
                            System.err.println(Rdesktop.PROGRAM_NAME
                                    + ": Invalid fullscreen option '" + arg + "'");
                            Rdesktop.usage();
                        }
                    }
                    break;
                case 'g':
                    arg = getOptions.getOptarg();
                    int cut = arg.indexOf("x", 0);
                    if (cut == -1) {
                        System.err.println(Rdesktop.PROGRAM_NAME + ": Invalid geometry: " + arg);
                        Rdesktop.usage();
                    }
                    option.setWidth(Integer.parseInt(arg.substring(0, cut)) & ~3);
                    option.setHeight(Integer.parseInt(arg.substring(cut + 1)));
                    logger.info("width x height" + option.getWidth() + "x" + option.getHeight());
                    break;
                case 'k':
                    arg = getOptions.getOptarg();
                    //Options.keylayout = KeyLayout.strToCode(arg);
                    if (option.isInvalidKeylayout()) {
                        System.err.println(Rdesktop.PROGRAM_NAME + ": Invalid key layout: " + arg);
                        Rdesktop.usage();
                    }
                    break;
                case 'l':
                    arg = getOptions.getOptarg();
                    switch (arg.charAt(0)) {
                        case 'd':
                        case 'D':
                            logger.setLevel(Level.DEBUG);
                            break;
                        case 'i':
                        case 'I':
                            logger.setLevel(Level.INFO);
                            break;
                        case 'w':
                        case 'W':
                            logger.setLevel(Level.WARN);
                            break;
                        case 'e':
                        case 'E':
                            logger.setLevel(Level.ERROR);
                            break;
                        case 'f':
                        case 'F':
                            logger.setLevel(Level.FATAL);
                            break;
                        default:
                            System.err.println(Rdesktop.PROGRAM_NAME + ": Invalid debug level: "
                                    + arg.charAt(0));
                            Rdesktop.usage();
                    }
                    break;
                case 'n':
                    option.setHostname(getOptions.getOptarg());
                    break;
                case 'p':
                    option.setPassword(getOptions.getOptarg());

                    break;
                case 's':
                    option.setCommand(getOptions.getOptarg());
                    break;
                case 'u':
                    option.setUsername(getOptions.getOptarg());
                    break;
                case 't':
                    arg = getOptions.getOptarg();
                    try {
                        option.setPort(Integer.parseInt(arg));
                    } catch (NumberFormatException nex) {
                        System.err.println(Rdesktop.PROGRAM_NAME + ": Invalid port number: "
                                + arg);
                        Rdesktop.usage();
                    }
                    break;
                case 'T':
                    option.setWindowTitle(getOptions.getOptarg().replace('_', ' '));
                    logger.info("Windows Title" + getOptions.getOptarg().replace('_', ' '));
                    break;
                case 'r':
                    option.setLicencePath(getOptions.getOptarg());
                    break;

                case '?':
                default:
                    Rdesktop.usage();
                    break;

            }
        }

        if (fKdeHack) {
            option.setHeight(option.getHeight() - 46);
        }

        InitializeOSConstants();
        return option;
    }

    private Options parseLongArguments() {
        String arg;
        switch (getOptions.getLongind()) {
            case 0:
                option.enableKeyboardDebug();
                break;
            case 1:
                option.enableHexdumpDebug();
                break;
            case 2:
                break;
            case 3:
                arg = getOptions.getOptarg();
                PropertyConfigurator.configure(arg);
                logger.info("Log4j using config file " + arg);
                break;
            case 4:
                option.enableShowTools(); 
                break;
            case 5:
                option.enableAltkeyQuiet();
                break;
            case 6:
                option.disableRemapHash();
                break;
            case 7:
                option.disablePacketEncryption();
                break;
            case 8:
                option.disableUseOfRdp5();
                //Options.server_bpp = 8;
                option.setBPPImpl(8);
                break;
            case 9:
                option.enableSsl();
                break;
            case 10:
                option.disableMenu();
                break;
            case 11:
                option.enableConsoleSession();
                break;
            case 12:
                option.enableLoadLicence();
                break;
            case 13:
                option.enableSaveLicence();
                break;
            case 14:
                option.enablePersistentBitmapCaching();
                break;
            case 15:
                option.embedInBrowser();
                break;
            case 16:
                 logger.info("Error Handler " + getOptions.getOptarg());
                option.setErrorHandler(getOptions.getOptarg());
                break;
            case 17:
                logger.info("Disconnect Handler " + getOptions.getOptarg());
                option.setDisconnectHandler(getOptions.getOptarg());
                break;
            default:
                Rdesktop.usage();
        }
        return option;
    }

    private LongOpt[] createLongOptions() {
        logger.info("Locale" + Locale.getDefault());
      
              
        StringBuffer sb = new StringBuffer();
        LongOpt[] alo = new LongOpt[18];
        alo[0] = new LongOpt("debug_key", LongOpt.NO_ARGUMENT, null, 0);
        alo[1] = new LongOpt("debug_hex", LongOpt.NO_ARGUMENT, null, 0);
        alo[2] = new LongOpt("no_paste_hack", LongOpt.NO_ARGUMENT, null, 0);
        alo[3] = new LongOpt("log4j_config", LongOpt.REQUIRED_ARGUMENT, sb, 0);
        alo[4] = new LongOpt("packet_tools", LongOpt.NO_ARGUMENT, null, 0);
        alo[5] = new LongOpt("quiet_alt", LongOpt.NO_ARGUMENT, sb, 0);
        alo[6] = new LongOpt("no_remap_hash", LongOpt.NO_ARGUMENT, null, 0);
        alo[7] = new LongOpt("no_encryption", LongOpt.NO_ARGUMENT, null, 0);
        alo[8] = new LongOpt("use_rdp4", LongOpt.NO_ARGUMENT, null, 0);
        alo[9] = new LongOpt("use_ssl", LongOpt.NO_ARGUMENT, null, 0);
        alo[10] = new LongOpt("enable_menu", LongOpt.NO_ARGUMENT, null, 0);
        alo[11] = new LongOpt("console", LongOpt.NO_ARGUMENT, null, 0);
        alo[12] = new LongOpt("load_licence", LongOpt.NO_ARGUMENT, null, 0);
        alo[13] = new LongOpt("save_licence", LongOpt.NO_ARGUMENT, null, 0);
        alo[14] = new LongOpt("persistent_caching", LongOpt.NO_ARGUMENT, null, 0);
        alo[15] = new LongOpt("embed_in_browser", LongOpt.NO_ARGUMENT, null, 0);
        alo[16] = new LongOpt("error_handler", LongOpt.REQUIRED_ARGUMENT, sb, 0);
        alo[17] = new LongOpt("disconnect_handler", LongOpt.REQUIRED_ARGUMENT, sb, 0);

        return alo;
    }

    public String parseServerName(Options option) {
        String server = null;
        if (getOptions.getOptind() < arguments.length) {
            int colonat = arguments[arguments.length - 1].indexOf(":", 0);
            if (colonat == -1) {
                server = arguments[arguments.length - 1];
            } else {
                server = arguments[arguments.length - 1].substring(0, colonat);
                option.setPort(Integer.parseInt(arguments[arguments.length - 1]
                        .substring(colonat + 1)));
            }

            if (server.equalsIgnoreCase("localhost")) server = "127.0.0.1";
        } else {
            System.err.println(Rdesktop.PROGRAM_NAME + ": A server name is required!");
            Rdesktop.usage();
        }
        return server;
    }

    void InitializeOSConstants() {
        String java = System.getProperty("java.specification.version");
        logger.info("Java version is " + java);

        String os = System.getProperty("os.name");
        String osvers = System.getProperty("os.version");

        if (os.equals("Windows 2000") || os.equals("Windows XP"))
            option.enableBuiltInLicence();

        logger.info("Operating System is " + os + " version " + osvers);

        if (os.startsWith("Linux"))
            Constants.OS = Constants.LINUX;
        else if (os.startsWith("Windows"))
            Constants.OS = Constants.WINDOWS;
        else if (os.startsWith("Mac"))
            Constants.OS = Constants.MAC;

        if (Constants.OS == Constants.MAC)
            option.disableCapsSendsUpAndDown();
        return;
    }
}
