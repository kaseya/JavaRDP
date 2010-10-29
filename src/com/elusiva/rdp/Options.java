/* Options.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:14 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Global static storage of user-definable options
 */
package com.elusiva.rdp;

import java.awt.image.DirectColorModel;

public class Options {
    
    public static final int DIRECT_BITMAP_DECOMPRESSION = 0;
    public static final int BUFFEREDIMAGE_BITMAP_DECOMPRESSION = 1;
    public static final int INTEGER_BITMAP_DECOMPRESSION = 2;
    
    private int bitmap_decompression_store = INTEGER_BITMAP_DECOMPRESSION;
  
    
    
	private boolean low_latency = true; // disables bandwidth saving tcp packets
	private int keylayout = 0x809; // UK by default
	private String username = "root"; // -u username
	private String domain = ""; // -d domain
	private String password = ""; // -p password
	private String hostname = ""; // -n hostname
	private String command = "";  // -s command
	private String directory = ""; // -d directory
	private String windowTitle = "Elusiva Everywhere"; // -T windowTitle
	private int width = 800; // -g widthxheight
	private int height = 600; // -g widthxheight
	private int port = 3389; // -t port
	private boolean fullscreen = false;
	private boolean built_in_licence = false;
    
	private boolean load_licence = false;
	private boolean save_licence = false;

    private String licence_path = "./";
    
    private boolean debug_keyboard = false;
	private boolean debug_hexdump = false;

    
	private boolean enable_menu = true;
	//public static boolean paste_hack = true;
	
	private boolean altkey_quiet = false;
	private boolean caps_sends_up_and_down = true;
	private boolean remap_hash = true;
	private boolean useLockingKeyState = true;
	
	private boolean use_rdp5 = true;
	private int server_bpp = 24;							// Bits per pixel
	private int Bpp = (server_bpp + 7) / 8;				// Bytes per pixel
	
	private int bpp_mask =  0xFFFFFF >> 8 * (3 - Bpp);	// Correction value to ensure only the relevant
			                                                    // number of bytes are used for a pixel
    
    private int imgCount = 0;

    private boolean embeded_in_browser = false;
    private String error_handler;
    private String disconnect_handler;

    private int server_rdp_version;

	private int win_button_size = 0;	/* If zero, disable single app mode */
	private boolean bitmap_compression = true;
    private boolean persistent_bitmap_caching = false;
    private boolean bitmap_caching = false;
    private boolean precache_bitmaps = false;
    private boolean polygon_ellipse_orders = false;
	private boolean sendmotion = true;
	private boolean orders = true;
	private boolean encryption = true;
	private boolean packet_encryption = true;
	private boolean desktop_save = true;
	private boolean grab_keyboard = true;
	private boolean hide_decorations = false;
	private boolean console_session = false;
	private boolean owncolmap;

	private boolean use_ssl = false;
	private boolean map_clipboard = true;
    private int rdp5_performanceflags =   Rdp.RDP5_NO_CURSOR_SHADOW | Rdp.RDP5_NO_CURSORSETTINGS |
                                                Rdp.RDP5_NO_FULLWINDOWDRAG | Rdp.RDP5_NO_MENUANIMATIONS |
                                                Rdp.RDP5_NO_THEMING | Rdp.RDP5_NO_WALLPAPER;
    private static boolean save_graphics = false;
    
    
    private DirectColorModel colour_model = new DirectColorModel(24,0xFF0000,0x00FF00,0x0000FF);
    private int logonflags = Rdp.RDP_LOGON_NORMAL;
    private String mapFile = "en-gb";
    private boolean showTools = false;

    /**
     * Set a new value for the server's bits per pixel
     * @param server_bpp New bpp value
     */
    public void setBPPImpl(int server_bpp){
		this.setServerDisplayColourDepthBits(server_bpp);
		this.setColourDepthInBytes((server_bpp + 7) / 8);
		if(isServerDisplayColourDepthEightBits()) setColourDepthCorrectionMaskPerBytes(0xFF);
        else setColourDepthCorrectionMaskPerBytes(0xFFFFFF);
        setColourModel(new DirectColorModel(24,0xFF0000,0x00FF00,0x0000FF));
	}

    public void reset_applet_params(){
		if (Common.underApplet) {
            setWidth(800);
            setHeight(600);
            setPort(3389);
            disableFullScreen();
            enableMenu();
            setErrorHandler("");
            setDisconnectHandler("");
            setServerDisplayColourDepthBits(24);
        }
	}

    public boolean isDisconnectHandlerAvailable() {
        return this.disconnect_handler != null && ! this.disconnect_handler.isEmpty();
    }

    public boolean isErrorHandlerAvailable() {
       return this.error_handler != null && ! this.error_handler.isEmpty();
   }

    public String getDisconnectHandler() {
        return this.isDisconnectHandlerAvailable() ? this.disconnect_handler : "" ;
    }


    public String getErrorHandler() {
        return this.isErrorHandlerAvailable() ? this.error_handler : "" ;
    }

    public boolean isEmbededInBrowser() {
        return embeded_in_browser;
    }

    public void disableLowLatency() {
        low_latency = false;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void enableFullScreen() {
        setFullscreen(true);
    }

    public void disableFullScreen() {
        setFullscreen(false);
    }


    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPassword(String password) {
       this.password= password;
        logonflags |= Rdp.RDP_LOGON_AUTO;
   }


    public void setCommand(String command) {
       this.command= command;
   }


    public void setUsername(String userName) {
       this.username= userName;
   }

    public void setPort(int port) {
       this.port = port;
   }


    public int getBitmapDecompressionStore() {
        return bitmap_decompression_store;
    }

    public boolean isLowLatency() {
        return low_latency;
    }

    public int getKeylayout() {
        return keylayout;
    }

    public boolean isInvalidKeylayout() {
        return keylayout == -1;
    }

    public void setKeylayout(int keylayout) {
        this.keylayout = keylayout;
    }

    public String getUsernameImpl() {
        return username;
    }

    public String getUsernameTenCharacterLong() {
		 return username.length() > 9 ? username.substring(0,9): username;
    }

    public boolean isUsernameSet() {
      return username != null && username.length() > 0;
    }

    public String getDomain() {
        return domain;
    }

    public String getPassword() {
        return password;
    }

    public String getHostname() {
        return hostname;
    }


    public String getCommand() {
        return command;
    }


    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }


    public int getPort() {
        return port;
    }


    public boolean isFullscreen() {
        return fullscreen;
    }


    public boolean isNotFullscreen() {
        return ! fullscreen;
    }


    private void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isBuiltInLicence() {
        return built_in_licence;
    }

    public void enableBuiltInLicence() {
        this.built_in_licence = true;
    }

    public boolean isLoadLicenceEnabled() {
        return load_licence;
    }


    public void enableLoadLicence() {
        this.load_licence = true;
    }


    public boolean isSaveLicenceEnabled() {
        return save_licence;
    }


    public void enableSaveLicence() {
        this.save_licence = true;
    }


    public String getLicencePath() {
        return licence_path;
    }

    public void setLicencePath(String licence_path) {
        this.licence_path = licence_path;
    }

    public boolean isKeyboardDebugEnabled() {
        return debug_keyboard;
    }

    public void enableKeyboardDebug() {
        this.debug_keyboard = true;
    }

    public boolean isHexdumpDebugEnabled() {
        return debug_hexdump;
    }

    public void enableHexdumpDebug() {
        this.debug_hexdump = true;
    }


    public boolean isMenuEnabled() {
           return enable_menu;
    }

    public void enableMenu() {
        this.enable_menu = true;
    }

    public void disableMenu() {
        this.enable_menu = false;
    }


    public boolean isAltkeyQuietEnabled() {
        return altkey_quiet;
    }

    public void enableAltkeyQuiet() {
        this.altkey_quiet = true;
    }

    public boolean isCapsSendsUpAndDownEnabled() {
        return caps_sends_up_and_down;
    }

    public boolean isCapsSendsUpAndDownNotEnabled() {
        return ! caps_sends_up_and_down;
    }

    public void disableCapsSendsUpAndDown() {
        this.caps_sends_up_and_down = false;
    }

    public boolean isRemapHashEnabled() {
          return remap_hash;
      }

    public boolean disableRemapHash() {
          return remap_hash;
      }

    public boolean isUseLockingKeyStateEnabled() {
        return useLockingKeyState;
    }

    public boolean isUseLockingKeyStateNotEnabled() {
        return ! useLockingKeyState;
    }

    public void enableUseLockingKeyState() {
        this.useLockingKeyState = true;
    }

    public void disableUseLockingKeyState() {
        this.useLockingKeyState = false;
    }

    public boolean shouldUseRdp5() {
        return use_rdp5;
    }

    public void disableUseOfRdp5() {
        this.use_rdp5 = false;
    }

    public boolean isServerDisplayColourDepthFifteenBits() {
            return server_bpp == 15;
    }

    public boolean isServerDisplayColourDepthSixteenBits() {
            return server_bpp == 16;
    }

    public boolean isServerDisplayColourDepthEightBits() {
           return server_bpp == 8;
   }

    public boolean isServerDisplayColourDepthGreaterThanEightBits() {
            return server_bpp > 8;
    }

    public int getServerDisplayColourDepthInBits() {
        return server_bpp;
    }

    public void setServerDisplayColourDepthBits(int colourDepthBitsPerPixel) {
        this.server_bpp = colourDepthBitsPerPixel;
    }

    public int getColourDepthInBytes() {
        return Bpp;
    }

    public void setColourDepthInBytes(int colourDepthByterPerPixel) {
        this.Bpp = colourDepthByterPerPixel;
    }

    public int getColourDepthCorrectionMaskPerBytes() {
        return bpp_mask;
    }

    public void setColourDepthCorrectionMaskPerBytes(int bpp_mask) {
        this.bpp_mask = bpp_mask;
    }

    public int getImageCount() {
        return imgCount;
    }

    public void incrementImageCountBy(int count) {
        this.imgCount = this.imgCount + count;
    }

    public void embedInBrowser() {
        this.embeded_in_browser = true;
    }

    public String getErroHandler() {
        return error_handler;
    }

    public void setErrorHandler(String error_handler) {
        this.error_handler = error_handler;
    }

    public void setDisconnectHandler(String disconnect_handler) {
        this.disconnect_handler = disconnect_handler;
    }

    public void setColourModel(DirectColorModel colour_model) {
        this.colour_model = colour_model;
    }

    public int getServerRdpVersion() {
        return server_rdp_version;
    }

    public void setServerRdpVersion(int server_rdp_version) {
        this.server_rdp_version = server_rdp_version;
    }

    public boolean isBitmapCompressionEnabled() {
        return bitmap_compression;
    }

    public boolean isPersistentBitmapCachingEnabled() {
        return persistent_bitmap_caching;
    }

    public void enablePersistentBitmapCaching() {
        this.persistent_bitmap_caching = true;
    }

    public boolean isBitmapCachingEnabled() {
        return bitmap_caching;
    }

    public boolean isPrecacheBitmaps() {
        return precache_bitmaps;
    }

    public boolean isPolygonEllipseOrdersEnabled() {
        return polygon_ellipse_orders;
    }

    public boolean isEncryptionEnabled() {
        return encryption;
    }

    public void disableEncryption() {
        this.encryption = false;
    }

    public boolean isPacketEncryptionNotEnabled() {
        return !packet_encryption;
    }

    public void disablePacketEncryption() {
        this.packet_encryption = false;
    }

    public boolean isConsoleSession() {
        return console_session;
    }

    public void enableConsoleSession() {
        this.console_session = true;
    }

    public void enableSsl() {
        this.use_ssl = true;
    }

    public boolean isClipboardMappingEnabled() {
        return map_clipboard;
    }

    public int getRdp5PerformanceFlags() {
        return rdp5_performanceflags;
    }

    public boolean shouldSaveGraphics() {
        return save_graphics;
    }

    public int getLogonFlags() {
        return this.logonflags;
    }

    public void setMapFile(String mapFile) {
        this.mapFile = mapFile;
    }

    public String getMapFile() {
        return mapFile;
    }

    public void disableEncryptionIfPacketEncyptionNotEnabled() {
        if (isPacketEncryptionNotEnabled())
            disableEncryption();
    }

    public void enableShowTools() {
        showTools = true;
    }

    public boolean shouldShowToolsFrame() {
        return showTools;
    }
}
